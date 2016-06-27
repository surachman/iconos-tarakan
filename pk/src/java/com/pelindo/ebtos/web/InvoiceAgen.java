/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.web;

import com.qtasnim.persistence.EntityManagerHelper;
import com.qtasnim.util.EnglishNumberConverter;
import com.qtasnim.util.IndonesianNumberConverter;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;

import net.sf.jasperreports.engine.JasperPrint;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 *
 * @author dycoder
 */
@WebServlet(name="InvoiceAgen", urlPatterns={"/ageninvoice.pdf"})
public class InvoiceAgen extends HttpServlet {
    private static final int DEFAULT_BUFFER_SIZE = 10240; // 10KB.

    @Resource(mappedName="jdbc/pk_db")
    private DataSource ds;


    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletOutputStream servletOutputStream = response.getOutputStream();

        String noPPKB = request.getParameter("no_ppkb");
        String mode = request.getParameter("mode");
        String curr = request.getParameter("curr");
        String noReg = request.getParameter("noReg");
        String username = request.getParameter("username");
        String total = request.getParameter("total");
        String includeHandling = request.getParameter("include");
        Boolean include = Boolean.valueOf(includeHandling);

        if (noPPKB == null || mode == null || total == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND); // lempar404.
            return;
        }
        
        response.reset();
        response.setContentType("application/pdf");

        HashMap map = new HashMap();
        JasperPrint jp;
        ClassLoader loader = getClass().getClassLoader();
        InputStream report = null;
        Connection connection = null;
        String terbilang;
        IndonesianNumberConverter converter = new IndonesianNumberConverter();
        EnglishNumberConverter converterEng = new EnglishNumberConverter();
        curr = curr.trim();

        if(curr.equalsIgnoreCase("IDR"))
            terbilang = converter.convertToWord((int) Double.parseDouble(total) + "") + "Rupiah";
        else
            terbilang = converterEng.numberAsSentenceFactory(total);

        try {
            connection = ds.getConnection();
            
            map.put(JRParameter.REPORT_CONNECTION, connection);
            map.put("no_ppkb", noPPKB);
            map.put("terbilang", terbilang);
            map.put("curr_code", curr);
            map.put("include", include);
            map.put("username", username);

            if (mode.equalsIgnoreCase("HandlingDischargeInvoice")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/HandlingDischargeInvoiceTarakan.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"InvoiceTerminalServices_Discharge.pdf\"");
            } else if (mode.equalsIgnoreCase("HandlingLoadInvoice")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/HandlingLoadInvoiceTarakan.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"InvoiceTerminalServices_Load.pdf\"");
            } else if (mode.equalsIgnoreCase("HandlingInvoice")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/HandlingInvoice.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"InvoiceTerminalServices.pdf\"");
            } else if (mode.equalsIgnoreCase("HandlingLoadInvoiceCy2")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/HandlingLoadInvoiceCy2.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"InvoiceReceivingCy2.pdf\"");
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
            
            if (mode.equalsIgnoreCase("HandlingDischargeInvoice") || mode.equalsIgnoreCase("HandlingLoadInvoice")) {
//                map.put("NOTES", 
//                        (new java.util.Scanner(loader.getResourceAsStream("/com/pelindo/ebtos/report/Bentuk4-Notes.rtf"))).useDelimiter("\\A").next());
                map.put("LOGO", loader.getResourceAsStream("/com/pelindo/ebtos/report/logoPelindo.png"));
                map.put("LOGO2", loader.getResourceAsStream("/com/pelindo/ebtos/report/logoPelindo.png"));
            }

            jp = JasperFillManager.fillReport(report, map);
//            byte[] file = JasperExportManager.exportReportToPdf(jp);
//
//            servletOutputStream.write(file);
//            servletOutputStream.flush();
            JasperExportManager.exportReportToPdfStream(jp, servletOutputStream);
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
                Logger.getLogger(InvoiceAgen.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    private static void close(Connection resource) {
        if (resource != null) {
            try {
                resource.close();
            } catch (SQLException ex) {
                Logger.getLogger(InvoiceAgen.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
