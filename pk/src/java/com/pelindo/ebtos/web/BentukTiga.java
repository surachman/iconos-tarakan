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
import net.sf.jasperreports.engine.util.JRLoader;

/**
 *
 * @author dycode-java
 */
@WebServlet(name = "Bentuktiga", urlPatterns = {"/bentuktiga.pdf"})
public class BentukTiga extends HttpServlet {
    private static final int DEFAULT_BUFFER_SIZE = 10240; // 10KB.

    @Resource(mappedName="jdbc/pk_db")
    private DataSource ds;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletOutputStream servletOutputStream = response.getOutputStream();

        String noPpkb = request.getParameter("no_ppkb");
        String userName = request.getParameter("user_name");
        String yard = request.getParameter("yard");
        String mode = request.getParameter("mode");

        if (noPpkb == null || mode == null) {
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
        
        try {
            connection = ds.getConnection();
            
            map.put(JRParameter.REPORT_CONNECTION, connection);
            map.put("no_ppkb", noPpkb);
            map.put("user_name", userName);
            map.put("yard", yard);
            
            if (mode.equals("RCM")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/RincianContainerMuatan.jasper");
                subreport = loader.getResourceAsStream("/com/pelindo/ebtos/report/RincianContainerMuatan_subreport.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"RincianContainerMuatan.pdf\"");
                map.put("SUBREPORT_DIR", JRLoader.loadObject(subreport)); //masukin subreport yg udah tercompile!
            } else if (mode.equals("UAPH")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/UtilisasiAlatPaketHandling.jasper");
                subreport = loader.getResourceAsStream("/com/pelindo/ebtos/report/UtilisasiAlatPaketHandling_subreport.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"UtilisasiAlatPaketHandling.pdf\"");
                map.put("SUBREPORT_DIR", JRLoader.loadObject(subreport)); //masukin subreport yg udah tercompile!
            } else if (mode.equals("RCR")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/RincianContainerReefer.jasper");
                subreport = loader.getResourceAsStream("/com/pelindo/ebtos/report/RincianContainerReefer_subreport.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"RincianContainerReefer.pdf\"");
                map.put("SUBREPORT_DIR", JRLoader.loadObject(subreport)); //masukin subreport yg udah tercompile!
            } else if (mode.equals("RCMI")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/RincianContainerMuatanInternal.jasper");
                subreport = loader.getResourceAsStream("/com/pelindo/ebtos/report/RincianContainerMuatanInternal_subreport.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"RincianContainerMuatanInternal.pdf\"");
                map.put("SUBREPORT_DIR", JRLoader.loadObject(subreport)); //masukin subreport yg udah tercompile!
            } else if (mode.equals("RBP")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/RekapitulasiBuktiPelayanan.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"RekapitulasiBuktiPelayanan.pdf\"");
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
                Logger.getLogger(BentukTiga.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    private static void close(Connection resource) {
        if (resource != null) {
            try {
                resource.close();
            } catch (SQLException ex) {
                Logger.getLogger(BentukTiga.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
