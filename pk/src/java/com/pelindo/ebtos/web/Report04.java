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
 * @author Dyware-Dev01
 */
@WebServlet(name = "report04", urlPatterns = {"/report04.pdf"})
public class Report04 extends HttpServlet {

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
        Integer bulan = Integer.parseInt(request.getParameter("bulan"));
        Integer tahun = Integer.parseInt(request.getParameter("tahun"));
        String owner = request.getParameter("owner");
        String equipCode = request.getParameter("equipCode");

        if (bulan == null || tahun == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND); // lempar404.
            return;
        }

        InputStream handling = null;
        InputStream delivery = null;
        InputStream receiving = null;
        InputStream report = null;
        Connection connection = null;
        HashMap map = new HashMap();
        JasperPrint jp;
        ClassLoader loader = getClass().getClassLoader();

        try {
            connection = ds.getConnection();
            
            map.put(JRParameter.REPORT_CONNECTION, connection);
            map.put("bulan", bulan);
            map.put("tahun", tahun);
            map.put("owner", owner);
            map.put("equipCode", equipCode);
            report = loader.getResourceAsStream("/com/pelindo/ebtos/report/Report04.jasper");
            handling = loader.getResourceAsStream("/com/pelindo/ebtos/report/Report04_subreportHandlingBM.jasper");
            delivery = loader.getResourceAsStream("/com/pelindo/ebtos/report/Report04_subreportReportDeliveryService.jasper");
            receiving = loader.getResourceAsStream("/com/pelindo/ebtos/report/Report04_subreportReceivingService.jasper");
            response.setHeader("Content-Disposition", "inline; filename=\"REALISASI_ARUS_PETIKEMAS_TPB_KONVENSIONAL_DI_PELABUHAN_BITUNG.pdf\"");

            map.put("SUBREPORT_HANDLING", JRLoader.loadObject(handling)); //masukin subreport yg udah tercompile!
            map.put("SUBREPORT_DELIVERY", JRLoader.loadObject(delivery)); //masukin subreport yg udah tercompile!
            map.put("SUBREPORT_RECEIVING", JRLoader.loadObject(receiving)); //masukin subreport yg udah tercompile!
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
            close(handling);
            close(delivery);
            close(receiving);
            close(connection);
            close(servletOutputStream);
        }
    }

    private static void close(Closeable resource) {
        if (resource != null) {
            try {
                resource.close();
            } catch (IOException e) {
                Logger.getLogger(Report04.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    private static void close(Connection resource) {
        if (resource != null) {
            try {
                resource.close();
            } catch (SQLException ex) {
                Logger.getLogger(Report04.class.getName()).log(Level.SEVERE, null, ex);
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
