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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;

import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 *
 * @author USER
 */
@WebServlet(name = "YardRealizationReport", urlPatterns = {"/YardRealizationReport.pdf"})
public class YardRealizationReport extends HttpServlet {

    @Resource(mappedName="jdbc/pk_db")
    private DataSource ds;

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletOutputStream servletOutputStream = response.getOutputStream();

        DateFormat formatter;
        Date startDate = new Date();
        Date endDate = new Date();
        formatter = new SimpleDateFormat("dd/MM/yyyy");

        String username = request.getParameter("username");
        String penanggungJawab = request.getParameter("penanggungJawab");
        String koordinator = request.getParameter("koordinator");
        
        try {
            startDate = (Date) formatter.parse(request.getParameter("startDate"));
            endDate = (Date) formatter.parse(request.getParameter("endDate"));
        } catch (ParseException ex) {
            Logger.getLogger(YardRealizationReport.class.getName()).log(Level.SEVERE, null, ex);
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        if (username == null || penanggungJawab == null || koordinator == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        HashMap map = new HashMap();
        InputStream report = null;
        InputStream subreport = null;
        Connection connection = null;
        ClassLoader loader = getClass().getClassLoader();

        try {
            connection = ds.getConnection();
            
            map.put(JRParameter.REPORT_DATA_SOURCE, new JREmptyDataSource(1));
            map.put("SUBREPORT_CONNECTION", connection);
            map.put("startDate", startDate);
            map.put("endDate", endDate);
            map.put("username", username);
            map.put("penanggung_jawab", penanggungJawab);
            map.put("koordinator", koordinator);

            report = loader.getResourceAsStream("/com/pelindo/ebtos/report/YardRealizationReport.jasper");
            subreport = loader.getResourceAsStream("/com/pelindo/ebtos/report/YardRealizationReport_detailSubreport.jasper");

            map.put("SUBREPORT_DIR", JRLoader.loadObject(subreport));

            JasperPrint jasperPrint = JasperFillManager.fillReport((JasperReport) JRLoader.loadObject(report), map);
            byte[] file = JasperExportManager.exportReportToPdf(jasperPrint);

            response.setHeader("Content-Disposition", "inline; filename=\"YardRealizationReport_" + startDate + ".pdf\"");
            servletOutputStream.write(file);
            servletOutputStream.flush();
        } catch (JRException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
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
                Logger.getLogger(YardRealizationReport.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    private static void close(Connection resource) {
        if (resource != null) {
            try {
                resource.close();
            } catch (SQLException ex) {
                Logger.getLogger(YardRealizationReport.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
