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
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
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
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperRunManager;

/**
 *
 * @author dycoder
 */
@WebServlet(name = "gateInUc", urlPatterns = {"/gateInUc.pdf"})
public class GateInUc extends HttpServlet {
    private static final int DEFAULT_BUFFER_SIZE = 10240; // 10KB.

    @Resource(mappedName="jdbc/pk_db")
    private DataSource ds;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletOutputStream servletOutputStream = response.getOutputStream();
        String blNo = request.getParameter("blNo");
        String noPpkb = request.getParameter("noPpkb");
        String mode = request.getParameter("mode");
        String vessel_name = request.getParameter("vessel_name");
        String voy_in = request.getParameter("voy_in");
        String voy_out = request.getParameter("voy_out");
        String block = request.getParameter("block");

        if (blNo == null || mode == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND); // lempar404.
            return;
        }
        
        response.reset();
        response.setContentType("application/pdf");

        InputStream report = null;
        Connection connection = null;
        Map map = new HashMap();
        byte[] data = null;
        JREmptyDataSource dataSource = new JREmptyDataSource();

        try {
            connection = ds.getConnection();
            
            map.put(JRParameter.REPORT_CONNECTION, connection);
            map.put("blNo", blNo);
            map.put("noPpkb", noPpkb);
            map.put("vessel_name", vessel_name);
            map.put("voy_in", voy_in);
            map.put("voy_out", voy_out);
            map.put("block", block);
            if (mode.equals("receivingUc")) {
                data = JasperRunManager.runReportToPdf(this.getClass().getResourceAsStream("/com/pelindo/ebtos/report/gateInReceivingUc.jasper"),
                        map, dataSource);
                response.setHeader("Content-Disposition", "inline; filename=\"gateInReceivingUc_" + mode + ".pdf\"");
            } else if (mode.equals("deliveryUc")) {
                data = JasperRunManager.runReportToPdf(this.getClass().getResourceAsStream("/com/pelindo/ebtos/report/gateInDeliveryUc.jasper"),
                        map, dataSource);
                response.setHeader("Content-Disposition", "inline; filename=\"gateInDeliveryUc_" + mode + ".pdf\"");
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }

            servletOutputStream.write(data);
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
                Logger.getLogger(GateInUc.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    private static void close(Connection resource) {
        if (resource != null) {
            try {
                resource.close();
            } catch (SQLException ex) {
                Logger.getLogger(GateInUc.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
