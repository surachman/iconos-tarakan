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
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;

import net.sf.jasperreports.engine.JasperPrint;

/**
 *
 * @author dycoder
 */
@WebServlet(name = "report19", urlPatterns = {"/report19.pdf"})
public class Report19 extends HttpServlet {
    private static final int DEFAULT_BUFFER_SIZE = 10240; // 10KB.

    @Resource(mappedName="jdbc/pk_db")
    private DataSource ds;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ServletOutputStream servletOutputStream = response.getOutputStream();
        String bulan = request.getParameter("bulan");
        String tahun = request.getParameter("tahun");
        String equip = request.getParameter("equip");
        String mode = request.getParameter("mode");
        String owner = request.getParameter("owner");
        Integer bln = (Integer.parseInt(bulan));
        Integer thn = (Integer.parseInt(tahun));

        if (bulan == null || equip == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND); // lempar404.
            return;
        }
        
        response.reset();
        response.setContentType("application/pdf");

        InputStream report = null;
        Connection connection = null;
        Map map = new HashMap();
        JasperPrint jp;
        ClassLoader loader = getClass().getClassLoader();

        try {
            connection = ds.getConnection();
            
            map.put(JRParameter.REPORT_CONNECTION, connection);
            map.put("bulan", bln);
            map.put("tahun", thn);
            map.put("equip_code", equip);
            map.put("owner_code", owner);

            if (mode.equalsIgnoreCase("report19")) {
                if (owner.equals("PLND")) {
                    report = loader.getResourceAsStream("/com/pelindo/ebtos/report/Report19TPB.jasper");
                    response.setHeader("Content-Disposition", "inline; filename=\"Report19TPB" + Calendar.getInstance().getTime() + ".pdf\"");
                } else {
//                    report = loader.getResourceAsStream("/com/pelindo/ebtos/report/Report19.jasper");
                    report = loader.getResourceAsStream("/com/pelindo/ebtos/report/Report19kkt.jasper");
                    response.setHeader("Content-Disposition", "inline; filename=\"Report19" + Calendar.getInstance().getTime() + ".pdf\"");
                }
            } else if (mode.equalsIgnoreCase("report191")) {
//                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/Report19DetailPlnd.jasper");
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/Report19kktRekap.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"Report19DetailPlnd" + Calendar.getInstance().getTime() + ".pdf\"");
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
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
                Logger.getLogger(Report19.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    private static void close(Connection resource) {
        if (resource != null) {
            try {
                resource.close();
            } catch (SQLException ex) {
                Logger.getLogger(Report19.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
