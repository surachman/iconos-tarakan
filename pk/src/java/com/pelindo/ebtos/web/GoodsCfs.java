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
 * @author dycoder
 */
@WebServlet(name = "goodsCfs", urlPatterns = {"/goodsCfs.pdf"})
public class GoodsCfs extends HttpServlet{
    private static final int DEFAULT_BUFFER_SIZE = 10240; // 10KB.

    @Resource(mappedName="jdbc/pk_db")
    private DataSource ds;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletOutputStream servletOutputStream = response.getOutputStream();

        String mode = request.getParameter("mode");
        String noPpkb = request.getParameter("no_ppkb");
        String contNo = request.getParameter("cont_no");
        
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
            if (mode.equals("stuffingGoodsReceiving")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/StuffingConfirmGoodsReport.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"StuffingConfirmGoodsReport.pdf\"");
            } else if (mode.equals("stripping")) {
                map.put("no_ppkb", noPpkb);
                map.put("cont_no", contNo);
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/RecapCfsStripping.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"RecapCfsStripping.pdf\"");
            } else if (mode.equals("stuffing")) {
                map.put("no_ppkb", noPpkb);
                map.put("cont_no", contNo);
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/RecapCfsStuffing.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"RecapCfsStuffing.pdf\"");
            } if(mode.equals("contHistMonitoring")) {
                map.put("cont_no", contNo);
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/contHistMonitoring.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"contHistMonitoring.pdf\"");
            } if(mode.equals("vesselMonitoring")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/vesselMonitoring.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"vesselMonitoring.pdf\"");
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
                Logger.getLogger(GoodsCfs.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    private static void close(Connection resource) {
        if (resource != null) {
            try {
                resource.close();
            } catch (SQLException ex) {
                Logger.getLogger(GoodsCfs.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
