/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.web;

import com.pelindo.ebtos.ejb.remote.DataStorageManagerRemote;
import com.pelindo.ebtos.report.model.IPrintable;
import com.qtasnim.jreport.data.QTBeanCollectionDataSource;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;

import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 *
 * @author dycoder
 */
@WebServlet(name = "report", urlPatterns = {"/report"})
public class Report extends HttpServlet {
    private static final int DEFAULT_BUFFER_SIZE = 10240;

    @EJB
    private DataStorageManagerRemote dataStorageManagerRemote;

    public Report() {}

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletOutputStream servletOutputStream = response.getOutputStream();

        String key = request.getParameter("key");
        HttpSession session = request.getSession(true);
        Object data = dataStorageManagerRemote.getData(session.getId(), key);

        if (data == null || !(data instanceof IPrintable)) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        IPrintable dataSource = (IPrintable) data;
        InputStream report = null;
        InputStream subreport = null;

        try {
            ClassLoader loader = getClass().getClassLoader();
            report = loader.getResourceAsStream(dataSource.getReport());
            
            Map<String, Object> parameters = new HashMap<String, Object>();

            if (dataSource.getListDataSource() != null && !dataSource.getListDataSource().isEmpty()) {
                parameters.put("REPORT_DATA_SOURCE", new QTBeanCollectionDataSource(dataSource.getListDataSource()));
            } else {
                parameters.put("REPORT_DATA_SOURCE", new JREmptyDataSource());
            }

            HashMap map = new HashMap();
            map.put("dataSource", dataSource);
            map.putAll(parameters);

            Iterator<Map.Entry<String,String>> it = dataSource.getSubReports().entrySet().iterator();

            while (it.hasNext()) {
                Map.Entry<String,String> pairs = (Map.Entry) it.next();
                subreport = loader.getResourceAsStream(pairs.getValue());

                if (subreport != null) {
                    map.put(pairs.getKey(), JRLoader.loadObject(subreport));
                } else {
                    throw new RuntimeException(String.format("subreport not found! [%s]", pairs.getValue()));
                }

                close(subreport);
            }

            JasperPrint jp = JasperFillManager.fillReport(report, map);
            byte[] file = JasperExportManager.exportReportToPdf(jp);

            response.reset();
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", String.format("inline; filename=\"%s.pdf\"", dataSource.getReportId()));
            servletOutputStream.write(file);
            servletOutputStream.flush();
        } catch (JRException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
        } finally {
            close(report);
            close(subreport);
            close(servletOutputStream);
        }
    }

    private static void close(Closeable resource) {
        if (resource != null) {
            try {
                resource.close();
            } catch (IOException e) {
                Logger.getLogger(Report.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    private static void close(Connection resource) {
        if (resource != null) {
            try {
                resource.close();
            } catch (SQLException ex) {
                Logger.getLogger(Report.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
