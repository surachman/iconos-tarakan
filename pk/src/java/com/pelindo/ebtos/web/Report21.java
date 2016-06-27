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
import net.sf.jasperreports.engine.util.JRLoader;

/**
 *
 * @author Dyware-Dev01
 */
@WebServlet(name = "report21", urlPatterns = {"/report21.pdf"})
public class Report21 extends HttpServlet {

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
        String equipCode = request.getParameter("equipCode");
        String mode = request.getParameter("mode");

        if (bulan == null || tahun == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND); // lempar404.
            return;
        }

        HashMap map = new HashMap();
        JasperPrint jp;
        ClassLoader loader = getClass().getClassLoader();
        InputStream report = null;
        InputStream subreport = null;
        InputStream subreport2 = null;
        Connection connection = null;
        
        try {
            connection = ds.getConnection();

            map.put(JRParameter.REPORT_CONNECTION, connection);
            map.put("bulan", bulan);
            map.put("tahun", tahun);
            map.put("equip_code", equipCode);
            if (mode.equals("report21")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/Report21.jasper");
                subreport = loader.getResourceAsStream("/com/pelindo/ebtos/report/Report21_subreport.jasper");
                subreport2 = loader.getResourceAsStream("/com/pelindo/ebtos/report/Report21_subreport21.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"REALISASI_ARUS_PETIKEMAS_TPB_KKT.pdf\"");
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
            
            map.put("SUBREPORT_21", JRLoader.loadObject(subreport)); //masukin subreport yg udah tercompile!
            map.put("SUBREPORT_DIR", JRLoader.loadObject(subreport2)); //masukin subreport yg udah tercompile!
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
            close(subreport2);
            close(connection);
            close(servletOutputStream);
        }
    }

    private static void close(Closeable resource) {
        if (resource != null) {
            try {
                resource.close();
            } catch (IOException e) {
                Logger.getLogger(Report21.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    private static void close(Connection resource) {
        if (resource != null) {
            try {
                resource.close();
            } catch (SQLException ex) {
                Logger.getLogger(Report21.class.getName()).log(Level.SEVERE, null, ex);
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
    }
    // </editor-fold>
}
