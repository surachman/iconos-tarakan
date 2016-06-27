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
@WebServlet(name="Monitoring", urlPatterns={"/realisasi.pdf"})
public class ReportMonitoring extends HttpServlet {

    private static final int DEFAULT_BUFFER_SIZE = 10240; // 10KB.

    @Resource(mappedName="jdbc/pk_db")
    private DataSource ds;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {
        ServletOutputStream servletOutputStream = response.getOutputStream();

        String noPpkb = request.getParameter("no_ppkb");
        String mode = request.getParameter("mode");
        String includeHandling = request.getParameter("include");
        Boolean include = Boolean.valueOf(includeHandling);

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
            map.put("include", include);

            if (mode.equals("discharge")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/dischargeMonitorings.jasper");
                subreport = loader.getResourceAsStream("/com/pelindo/ebtos/report/dischargeMonitorings_subreport.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"realisasi_bongkar.pdf\"");
            } else if (mode.equals("load")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/loadMonitorings.jasper");
                subreport = loader.getResourceAsStream("/com/pelindo/ebtos/report/loadMonitorings_subreport.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"realisasi_muat.pdf\"");
            }else if (mode.equals("handlingload")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/handlingLoadPreview.jasper");
                subreport = loader.getResourceAsStream("/com/pelindo/ebtos/report/handlingLoadPreview_subreport.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"handling_load_preview.pdf\"");
                InputStream subreportContainerRecap = loader.getResourceAsStream("/com/pelindo/ebtos/report/handlingLoadPreview_containerRecapSubreport.jasper");
                map.put("SUBREPORT_CONTRECAP", JRLoader.loadObject(subreportContainerRecap));
            }else if (mode.equals("handlingloaduc")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/handlingLoadUcPreview.jasper");
                subreport = loader.getResourceAsStream("/com/pelindo/ebtos/report/handlingLoadUcPreview_subreport.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"handling_load_uc_preview.pdf\"");
            }else if (mode.equals("handlingdischarge")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/handlingDischargePreview.jasper");
                subreport = loader.getResourceAsStream("/com/pelindo/ebtos/report/handlingDischargePreview_subreport.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"handling_discharge_preview.pdf\"");
                
                InputStream subreportContainerRecap = loader.getResourceAsStream("/com/pelindo/ebtos/report/handlingDischargePreview_containerRecapSubreport.jasper");
                map.put("SUBREPORT_CONTRECAP", JRLoader.loadObject(subreportContainerRecap));
            }else if (mode.equals("handlingdischargeuc")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/handlingDischargeUcPreview.jasper");
                subreport = loader.getResourceAsStream("/com/pelindo/ebtos/report/handlingDischargeUcPreview_subreport.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"handling_discharge_uc_preview.pdf\"");
            }else if (mode.equals("countload")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/vesselHandlings.jasper");
                subreport = loader.getResourceAsStream("/com/pelindo/ebtos/report/vesselHandlings_subreport.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"perhitungan_handling_paket_muat.pdf\"");
            }else if (mode.equals("countdischarge")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/vesselHandlingDischarge.jasper");
                subreport = loader.getResourceAsStream("/com/pelindo/ebtos/report/vesselHandlingDischarge_subreport.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"perhitungan_handling_paket_bongkar.pdf\"");
            }else if (mode.equals("countdischargeno")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/vesselHandlingDischargeNo.jasper");
                subreport = loader.getResourceAsStream("/com/pelindo/ebtos/report/vesselHandlingDischargeNo_subreport.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"perhitungan_handling_paket_bongkar.pdf\"");
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
