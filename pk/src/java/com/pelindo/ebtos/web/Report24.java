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
 * @author Dyware-Dev01
 */
@WebServlet(name = "report24", urlPatterns = {"/report24.pdf"})
public class Report24 extends HttpServlet {

    @Resource(mappedName="jdbc/pk_db")
    private DataSource ds;

    //private LoginSessionBean loginSessionBean;
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
        HttpSession session = (HttpSession) request.getSession(true);
        String username = (String) session.getAttribute("username");
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
            map.put("user", username);
            if (mode.equals("report24")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/Report24.jasper");
                subreport = loader.getResourceAsStream("/com/pelindo/ebtos/report/Report24_subreport.jasper");
                subreport2 = loader.getResourceAsStream("/com/pelindo/ebtos/report/Report24_subreportDisch.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"REALISASI_PERDAGANGAN_DI_KKT.pdf\"");
            } else if (mode.equals("report20")) {
//                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/Report20.jasper");
//                subreport = loader.getResourceAsStream("/com/pelindo/ebtos/report/Report20_subreport.jasper");
//                subreport2 = loader.getResourceAsStream("/com/pelindo/ebtos/report/Report20_subreport1.jasper");
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/Report20kkt.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"REKAPITULASI_PRODUKTIVITAS_KEGIATAN_BONGKAR_MUAT_PETIKEMAS_20.pdf\"");
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }

            if (mode.equals("report24")) {
                map.put("SUBREPORT_LOAD", JRLoader.loadObject(subreport)); //masukin subreport yg udah tercompile!
                map.put("SUBREPORT_DISCH", JRLoader.loadObject(subreport2)); //masukin subreport yg udah tercompile!
            }
//            else if (mode.equals("report20")){
//                map.put("SUBREPORT_21", JRLoader.loadObject(subreport)); //masukin subreport yg udah tercompile!
//                map.put("SUBREPORT_DIR", JRLoader.loadObject(subreport2)); //masukin subreport yg udah tercompile!
//            }


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
                Logger.getLogger(Report24.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    private static void close(Connection resource) {
        if (resource != null) {
            try {
                resource.close();
            } catch (SQLException ex) {
                Logger.getLogger(Report24.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
