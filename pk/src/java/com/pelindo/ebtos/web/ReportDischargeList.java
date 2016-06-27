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
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;

import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 *
 * @author dycoder
 */
@WebServlet(name = "Discharge", urlPatterns = {"/discharge.pdf"})
public class ReportDischargeList extends HttpServlet {

    private static final int DEFAULT_BUFFER_SIZE = 10240; // 10KB.

    @Resource(mappedName="jdbc/pk_db")
    private DataSource ds;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletOutputStream servletOutputStream = response.getOutputStream();

        String noPpkb = request.getParameter("no_ppkb");
        String mode = request.getParameter("mode");
        String total = request.getParameter("total");

        BigDecimal bigDecimal1 = BigDecimal.ZERO;
        if(total != null)
            bigDecimal1 = new BigDecimal(total);

        if (noPpkb == null || mode == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND); // lempar404.
            return;
        }
        response.reset();
        response.setContentType("application/pdf");

        InputStream report = null;
        InputStream subReport = null;
        InputStream subReport2 = null;
        Connection connection = null;
        HashMap map = new HashMap();
        JasperPrint jp;
        ClassLoader loader = getClass().getClassLoader();

        try {
            connection = ds.getConnection();
            
            map.put(JRParameter.REPORT_CONNECTION, connection);
            map.put("no_ppkb", noPpkb);
            map.put("total", bigDecimal1);
            if (mode.equals("discharge")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/dischargeList.jasper");
                subReport = loader.getResourceAsStream("/com/pelindo/ebtos/report/dischargeList_subReport.jasper");
                subReport2 = loader.getResourceAsStream("/com/pelindo/ebtos/report/dischargeList_subReport2.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"dischargeList.pdf\"");
            } else if (mode.equals("trans")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/transhipmentList.jasper");
                subReport = loader.getResourceAsStream("/com/pelindo/ebtos/report/transhipmentList_subreport1.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"transhipmentList.pdf\"");
            } else if (mode.equals("shift")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/shiftingList.jasper");
                subReport = loader.getResourceAsStream("/com/pelindo/ebtos/report/shiftingList_subreport1.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"shiftingList.pdf\"");
            } else if (mode.equals("uc")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/uncontainerized.jasper");
                subReport = loader.getResourceAsStream("/com/pelindo/ebtos/report/uncontainerized_subreport.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"uncontainerizedList.pdf\"");
            } else if (mode.equals("DISCHARGE") || mode.equals("LOAD")) {
                map.put("operation", mode);
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/shiftingRekap.jasper");
                subReport = loader.getResourceAsStream("/com/pelindo/ebtos/report/shiftingRekap_subreport.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"shiftingRekap.pdf\"");
            } else if (mode.equals("transRekap")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/transhipmentRekap.jasper");
                subReport = loader.getResourceAsStream("/com/pelindo/ebtos/report/transhipmentRekap_subreport.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"transhipmentRekap.pdf\"");
            }else if (mode.equals("dischargeUcRecap")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/RecapDischargeUc.jasper");
                subReport = loader.getResourceAsStream("/com/pelindo/ebtos/report/RecapDischargeUc_subreport.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"dischargeUcRecap.pdf\"");
            }else if (mode.equals("loadUcRecap")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/RecapLoadUc.jasper");
                subReport = loader.getResourceAsStream("/com/pelindo/ebtos/report/RecapLoadUc_subreport.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"dischargeUcRecap.pdf\"");
            } else if(mode.equals("load")){
                HttpSession session = (HttpSession) request.getSession(true);
                String username = (String) session.getAttribute("username");
                map.put("user", username);
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/loadingList.jasper");
                subReport = loader.getResourceAsStream("/com/pelindo/ebtos/report/loadingList_subreport.jasper");
                subReport2 = loader.getResourceAsStream("/com/pelindo/ebtos/report/loadingList_subreport2.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"loadingList.pdf\"");
            }else if(mode.equals("ucload")){
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/uncontainerizedLoad.jasper");
                subReport = loader.getResourceAsStream("/com/pelindo/ebtos/report/uncontainerized_subreportLoad.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"loadingListUc.pdf\"");
            }else if(mode.equals("truckMonitoring")){
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/truckMonitoring.jasper");
                subReport = loader.getResourceAsStream("/com/pelindo/ebtos/report/truckMonitoring_subreport.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"truckMonitoring.pdf\"");
            }else{
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }

            map.put("SUBREPORT_DIR", JRLoader.loadObject(subReport)); //masukin subreport yg udah tercompile!
            
            if(mode.equals("load") || mode.equals("discharge")){
            map.put("SUBREPORT_DIR2", JRLoader.loadObject(subReport2)); //masukin subreport yg udah tercompile!                
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
            close(subReport);
            close(connection);
            close(servletOutputStream);
        }
    }

    private static void close(Closeable resource) {
        if (resource != null) {
            try {
                resource.close();
            } catch (IOException e) {
                Logger.getLogger(ReportDischargeList.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    private static void close(Connection resource) {
        if (resource != null) {
            try {
                resource.close();
            } catch (SQLException ex) {
                Logger.getLogger(ReportDischargeList.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
