/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.web;

import com.qtasnim.persistence.EntityManagerHelper;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
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
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;

import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperRunManager;

/**
 *
 * @author dycoder
 */
@WebServlet(name = "gateInDeliveryReport", urlPatterns = {"/gateInDeliveryReport.pdf"})
public class GateInDeliveryReport extends HttpServlet {
    private static final int DEFAULT_BUFFER_SIZE = 10240; // 10KB.

    @Resource(mappedName="jdbc/pk_db")
    private DataSource ds;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletOutputStream servletOutputStream = response.getOutputStream();
        String block = request.getParameter("block");
        String slot = request.getParameter("slot");
        String row = request.getParameter("row");
        String tier = request.getParameter("tier");
        String cont_no = request.getParameter("cont_no");
        String mode = request.getParameter("mode");
        String no_ppkb = request.getParameter("no_ppkb");
        String status = request.getParameter("status");

        if (no_ppkb == null || mode == null) {
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
            map.put("no_ppkb", no_ppkb);
            map.put("blok", block);
            map.put("slot", slot);
            map.put("row", row);
            map.put("tier", tier);
            map.put("cont_no", cont_no);
            if (status.equalsIgnoreCase("PLUGG")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/gateInDeliveryPlugg.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"GateInDelivery_" + cont_no + ".pdf\"");
            } else {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/gateInDelivery.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"GateInDelivery_" + cont_no + ".pdf\"");
                //response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }

            jp = JasperFillManager.fillReport(report, map);
            byte[] file = JasperExportManager.exportReportToPdf(jp);

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
                Logger.getLogger(GateInDeliveryReport.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    private static void close(Connection resource) {
        if (resource != null) {
            try {
                resource.close();
            } catch (SQLException ex) {
                Logger.getLogger(GateInDeliveryReport.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
