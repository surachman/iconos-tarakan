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
@WebServlet(name = "gateInReceiving", urlPatterns = {"/gateInReceiving.pdf"})
public class GateInReceiving extends HttpServlet {

    private static final int DEFAULT_BUFFER_SIZE = 10240; // 10KB.
    @Resource(mappedName = "jdbc/pk_db")
    private DataSource ds;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletOutputStream servletOutputStream = response.getOutputStream();

        String cont_no = request.getParameter("cont_no");
        String no_ppkb = request.getParameter("no_ppkb");
        String block = request.getParameter("block");
        String fr_slot = request.getParameter("fr_slot");
        String to_slot = request.getParameter("to_slot");
        String slot = request.getParameter("slot");
        String fr_row = request.getParameter("fr_row");
        String to_row = request.getParameter("to_row");
        String operation = request.getParameter("operation");

        if (no_ppkb == null 
                || cont_no == null
                || fr_row == null
                || to_row == null 
                || operation == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        response.reset();
        response.setContentType("application/pdf");

        InputStream report = null;
        InputStream subReport = null;
        Connection connection = null;
        HashMap map = new HashMap();
        JasperPrint jp;
        ClassLoader loader = getClass().getClassLoader();
        try {
            connection = ds.getConnection();

            map.put(JRParameter.REPORT_CONNECTION, connection);
            map.put("cont_no", cont_no);
            map.put("block", block);
            map.put("fr_row", fr_row);
            map.put("to_row", to_row);
            map.put("no_ppkb", no_ppkb);

            if (operation.equals("PLUGG")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/gateInReceivingPlugging.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"GateInReceiving_" + no_ppkb + ".pdf\"");
                map.put("fr_slot", fr_slot);
                map.put("to_slot", to_slot);
            } else {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/gateInReceiving.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"GateInReceiving_" + no_ppkb + ".pdf\"");
                map.put("slot", slot);
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
                Logger.getLogger(GateInReceiving.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    private static void close(Connection resource) {
        if (resource != null) {
            try {
                resource.close();
            } catch (SQLException ex) {
                Logger.getLogger(GateInReceiving.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
