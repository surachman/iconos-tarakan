/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.web;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;

import net.sf.jasperreports.engine.JasperPrint;

/**
 *
 * @author arie
 */
@WebServlet(name="ReportReceiving", urlPatterns={"/receiving.pdf"})
public class ReportReceiving extends HttpServlet {

    private static final int DEFAULT_BUFFER_SIZE = 10240; // 10KB.

    @Resource(mappedName="jdbc/pk_db")
    private DataSource ds;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

        ServletOutputStream servletOutputStream = response.getOutputStream();

        String noPpkb = request.getParameter("no_ppkb");
        String mode = request.getParameter("mode");

        if (noPpkb == null || mode == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND); // lempar404.
            return;
        }

        response.reset();
        response.setContentType("application/pdf");

        InputStream report = null;
        Connection connection = null;
        HashMap map = new HashMap();
        JasperPrint jp;
        ClassLoader loader = getClass().getClassLoader();

        try {
            connection = ds.getConnection();

            map.put(JRParameter.REPORT_CONNECTION, connection);
            map.put("no_ppkb", noPpkb);
            if(mode.equals("transhipment"))
            {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/transhipmentPlanList.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"transhipment_plan_list.pdf\"");
            }else{
                map.put("is", mode);
                if(mode.equals("05") || mode.equals("04"))
                {
                    report = loader.getResourceAsStream("/com/pelindo/ebtos/report/allReceivingList.jasper");
                }else if(mode.equals("03")){
                    report = loader.getResourceAsStream("/com/pelindo/ebtos/report/allReceivingCyList.jasper");
                }
                response.setHeader("Content-Disposition", "inline; filename=\"all_receiving.pdf\"");
            }
            jp = JasperFillManager.fillReport(report, map);
            byte[] file = JasperExportManager.exportReportToPdf(jp);

            // tulis file
            servletOutputStream.write(file);
            servletOutputStream.flush();
        } catch (JRException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
        } finally {
            close(report);
            close(connection);
            close(servletOutputStream);
        }
    } 

    private static void close(Closeable resource) {
        if (resource != null) {
            try {
                resource.close();
            } catch (IOException e) {
                Logger.getLogger(ReportMonitoring.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    private static void close(Connection resource) {
        if (resource != null) {
            try {
                resource.close();
            } catch (SQLException ex) {
                Logger.getLogger(ReportMonitoring.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
