/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.web;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
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
import net.sf.jasperreports.engine.util.JRLoader;

/**
 *
 * @author dycode-java
 */
@WebServlet(name="Recap", urlPatterns={"/recap.pdf"})
public class VesselProductivityRecap extends HttpServlet {
    private static final int DEFAULT_BUFFER_SIZE = 10240; // 10KB.

    @Resource(mappedName="jdbc/pk_db")
    private DataSource ds;


    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {
        ServletOutputStream servletOutputStream = response.getOutputStream();

        String noPpkb = request.getParameter("no_ppkb");
        String mode = request.getParameter("mode");
        String total = request.getParameter("total_charge");
        
        if (noPpkb == null || mode == null || total == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND); // lempar404.
            return;
        }
        response.reset();
        response.setContentType("application/pdf");

        InputStream report = null;
        InputStream subreport = null;
        Connection connection = null;
        HashMap map = new HashMap();
        JasperPrint jp;
        ClassLoader loader = getClass().getClassLoader();
        BigDecimal bigDecimal1 = new BigDecimal(total);
        
        try {
            connection = ds.getConnection();
            
            map.put(JRParameter.REPORT_CONNECTION, connection);
            map.put("no_ppkb", noPpkb);
            map.put("total_charge", bigDecimal1);

            if (mode.equals("receiving")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/receivingRecap.jasper");
                subreport = loader.getResourceAsStream("/com/pelindo/ebtos/report/receivingRecap_subreport.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"perhitungan_jasa_receiving.pdf\"");
            } else if (mode.equals("penumpukan")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/loadRecap.jasper");
                subreport = loader.getResourceAsStream("/com/pelindo/ebtos/report/loadRecap_subreport.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"perhitungan_penumpukan.pdf\"");
            }else if (mode.equals("penumpukanDisch")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/dischargeRecap.jasper");
                subreport = loader.getResourceAsStream("/com/pelindo/ebtos/report/dischargeRecap_subreport.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"perhitungan_penumpukan_dicharge.pdf\"");
            }else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
            map.put("SUBREPORT_DIR", JRLoader.loadObject(subreport)); //masukin subreport yg udah tercompile!
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
            close(subreport);
            close(connection);
            close(servletOutputStream);
        }
    }

    private static void close(Closeable resource) {
        if (resource != null) {
            try {
                resource.close();
            } catch (IOException e) {
                Logger.getLogger(VesselProductivityRecap.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    private static void close(Connection resource) {
        if (resource != null) {
            try {
                resource.close();
            } catch (SQLException ex) {
                Logger.getLogger(VesselProductivityRecap.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
