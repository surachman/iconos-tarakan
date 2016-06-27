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
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;

import net.sf.jasperreports.engine.JasperPrint;
import javax.sql.DataSource;
import javax.annotation.Resource;

/**
 *
 * @author wulan
 */
@WebServlet(name = "GateOutReport", urlPatterns = "/GateOutReport.pdf")
public class GateOutReport extends HttpServlet {

    private static final int DEFAULT_BUFFER_SIZE = 10240; // 10KB.
    @Resource(mappedName = "jdbc/pk_db")
    private DataSource ds;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletOutputStream servletOutputStream = response.getOutputStream();

        String pelaksana_gate = request.getParameter("pelaksana_gate");
        String supervisi = request.getParameter("supervisi");
        String koordinator = request.getParameter("koordinator");
        String fromEpoch = request.getParameter("from");
        String toEpoch = request.getParameter("to");
        String no_ppkb = request.getParameter("no_ppkb");
        
        HttpSession session = (HttpSession) request.getSession(true);
        String username = (String) session.getAttribute("username");

        if (pelaksana_gate == null || supervisi == null || koordinator == null || fromEpoch == null || toEpoch == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        Date startDate = new Date(new Long(fromEpoch));
        Date endDate = new Date(new Long(toEpoch));

        InputStream report = null;
        Connection connection = null;
        HashMap map = new HashMap();
        ClassLoader loader = getClass().getClassLoader();

        try {
            connection = ds.getConnection();

            map.put(JRParameter.REPORT_CONNECTION, connection);
            map.put("username", username);
            map.put("startDate", startDate);
            map.put("endDate", endDate);
            map.put("pelaksana_gate", pelaksana_gate);
            map.put("supervisi", supervisi);
            map.put("koordinator", koordinator);
            map.put("no_ppkb", no_ppkb);

            report = loader.getResourceAsStream("/com/pelindo/ebtos/report/DaftarPetikemasKeluar.jasper");

            response.reset();
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline; filename=\"GateOutReport_" + startDate + ".pdf\"");
            
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, map);
            byte[] file = JasperExportManager.exportReportToPdf(jasperPrint);

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
                Logger.getLogger(GateOutReport.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    private static void close(Connection resource) {
        if (resource != null) {
            try {
                resource.close();
            } catch (SQLException ex) {
                Logger.getLogger(GateOutReport.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
