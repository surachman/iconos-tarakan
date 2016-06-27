/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.web;

import com.qtasnim.persistence.EntityManagerHelper;
import com.qtasnim.jsf.FacesHelper;
import com.qtasnim.url.UrlHelper;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.faces.context.FacesContext;
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
 * @author USER
 */
@WebServlet(name = "BayPlanLoadReport", urlPatterns = {"/bayPlanLoadReport.pdf"})
public class BayPlanLoadReport extends HttpServlet {

    @Resource(mappedName="jdbc/pk_db")
    private DataSource ds;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletOutputStream servletOutputStream = response.getOutputStream();

        String noPpkb = request.getParameter("noPpkb");

        if (noPpkb == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND); // lempar404.
            return;
        }
        
        HashMap map = new HashMap();
        InputStream report = null;
        InputStream subreport = null;
        Connection connection = null;
        JasperPrint jp;
        ClassLoader loader = getClass().getClassLoader();

        try {
            connection = ds.getConnection();
            FacesContext facesContext = FacesHelper.getFacesContextFromServlet(request, response);
            String url = UrlHelper.resolveFullAddress(facesContext, "/bayPlan.png?isLoad=t&bay=%s&ppkb=%s");
            HttpSession session = (HttpSession) request.getSession(true);
            String username = (String) session.getAttribute("username");
            
            map.put(JRParameter.REPORT_CONNECTION, connection);
            map.put("url", url);
            map.put("username", username);
            map.put("noPpkb", noPpkb);
            
            report = loader.getResourceAsStream("/com/pelindo/ebtos/report/bayPlanLoad.jasper");
            subreport = loader.getResourceAsStream("/com/pelindo/ebtos/report/bayPlanLoad_subreport.jasper");
            response.setHeader("Content-Disposition", "inline; filename=\"bayPlanLoad_" + noPpkb + ".pdf\"");
            
            map.put("SUBREPORT_DIR", JRLoader.loadObject(subreport));
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
                Logger.getLogger(BayPlanLoadReport.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    private static void close(Connection resource) {
        if (resource != null) {
            try {
                resource.close();
            } catch (SQLException ex) {
                Logger.getLogger(BayPlanLoadReport.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
