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
 * @author USER
 */
@WebServlet(name = "DischargeReport", urlPatterns = {"/DischargeReport.pdf"})
public class DischargeReport extends HttpServlet {

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

        String noPpkb = request.getParameter("noPpkb");
        String supervisiOperasi = request.getParameter("supervisiOperasi");
        String foreman = request.getParameter("foreman");
        String username = request.getParameter("username");

        if (noPpkb == null || supervisiOperasi == null || username == null || foreman == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        HashMap map = new HashMap();
        InputStream report = null;
        InputStream detailSubreport = null;
        InputStream summarySubreport = null;
        Connection connection = null;
        JasperPrint jp;
        ClassLoader loader = getClass().getClassLoader();

        try {
            connection = ds.getConnection();

            report = loader.getResourceAsStream("/com/pelindo/ebtos/report/DischargeReport.jasper");
            detailSubreport = loader.getResourceAsStream("/com/pelindo/ebtos/report/DischargeReport_detailSubreport.jasper");
            summarySubreport = loader.getResourceAsStream("/com/pelindo/ebtos/report/DischargeReport_summarySubreport.jasper");

            map.put(JRParameter.REPORT_CONNECTION, connection);
            map.put("username", username);
            map.put("foreman", foreman);
            map.put("no_ppkb", noPpkb);
            map.put("supervisi_operasi", supervisiOperasi);
            map.put("DETAIL_SUBREPORT_DIR", JRLoader.loadObject(detailSubreport));
            map.put("SUMMARY_SUBREPORT_DIR", JRLoader.loadObject(summarySubreport));

            response.setHeader("Content-Disposition", "inline; filename=\"DischargeReport_" + noPpkb + ".pdf\"");

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
            close(detailSubreport);
            close(summarySubreport);
            close(connection);
            close(servletOutputStream);
        }
    }

    private static void close(Closeable resource) {
        if (resource != null) {
            try {
                resource.close();
            } catch (IOException e) {
                Logger.getLogger(DischargeReport.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    private static void close(Connection resource) {
        if (resource != null) {
            try {
                resource.close();
            } catch (SQLException ex) {
                Logger.getLogger(DischargeReport.class.getName()).log(Level.SEVERE, null, ex);
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
