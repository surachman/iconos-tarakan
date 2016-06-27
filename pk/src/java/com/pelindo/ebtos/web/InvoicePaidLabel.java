/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.web;

import com.qtasnim.jsf.FacesHelper;
import com.pelindo.ebtos.bean.security.LoginSessionBean;
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
@WebServlet(name = "InvoicePaidLabel", urlPatterns = {"/invoicePaidLabel.pdf"})
public class InvoicePaidLabel extends HttpServlet {
    private static final int DEFAULT_BUFFER_SIZE = 10240; // 10KB.

    @Resource(mappedName="jdbc/pk_db")
    private DataSource ds;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletOutputStream servletOutputStream = response.getOutputStream();

        String noReg = request.getParameter("no_reg");
        String servicecode = request.getParameter("servicecode");

        if (noReg == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND); // lempar404.
            return;
        }

        InputStream report = null;
        Connection connection = null;
        HashMap map = new HashMap();
        JasperPrint jp;
        ClassLoader loader = getClass().getClassLoader();
        LoginSessionBean login = FacesHelper.translateElExpression(String.format("#{%s}", LoginSessionBean.NAME), FacesHelper.getFacesContextFromServlet(request, response), LoginSessionBean.class);
        String username = login.getName();
        response.reset();
        response.setContentType("application/pdf");

        try {
            connection = ds.getConnection();
            
            map.put(JRParameter.REPORT_CONNECTION, connection);
            map.put("no_reg", noReg);
            map.put("username", username);

            report = loader.getResourceAsStream("/com/pelindo/ebtos/report/InvoicePaidLabel.jasper");
            response.setHeader("Content-Disposition", "inline; filename=\"invoicePaidLabel.pdf\"");

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
                Logger.getLogger(InvoicePaidLabel.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    private static void close(Connection resource) {
        if (resource != null) {
            try {
                resource.close();
            } catch (SQLException ex) {
                Logger.getLogger(InvoicePaidLabel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
