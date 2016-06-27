/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.web;
import com.qtasnim.persistence.EntityManagerHelper;
import com.qtasnim.util.IndonesianNumberConverter;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
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
 * @author dycoder
 */
@WebServlet(name = "printAll", urlPatterns = {"/printAll.pdf"})
public class PrintAll extends HttpServlet {
    private static final int DEFAULT_BUFFER_SIZE = 10240; // 10KB.
    private IndonesianNumberConverter indonesianNumberConverter;

    @Resource(mappedName="jdbc/pk_db")
    private DataSource ds;
    
    public PrintAll() {
        indonesianNumberConverter=new IndonesianNumberConverter();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletOutputStream servletOutputStream = response.getOutputStream();

        String noRegistrasi = request.getParameter("noRegistrasi");        
        String mode = request.getParameter("mode");

        if (noRegistrasi == null || mode == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND); // lempar404.
            return;
        }
        
        response.reset();
        response.setContentType("application/pdf");

        InputStream report = null;
        InputStream subreport = null;
        Connection connection = null;
        HashMap map = new HashMap();
        JasperPrint jp;
        ClassLoader loader = getClass().getClassLoader();
        try {
            connection = ds.getConnection();
            
            map.put(JRParameter.REPORT_CONNECTION, connection);
            map.put("no_reg", noRegistrasi);
            if (mode.equals("receiving")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/perhitunganReceiving.jasper");
                subreport = loader.getResourceAsStream("/com/pelindo/ebtos/report/EntryReceivingReport_subreport.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"perhitungan_receiving.pdf\"");
            } else if (mode.equals("angsur")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/PerhitunganAngsurPetiKemas.jasper");
                subreport = loader.getResourceAsStream("/com/pelindo/ebtos/report/PerhitunganAngsurPetiKemas_subreport.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"perhitungan_angsur_petikemas.pdf\"");
            } else if (mode.equals("stuffingAll")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/ReportAll.jasper");
                subreport = loader.getResourceAsStream("/com/pelindo/ebtos/report/StruffingReportAll_subreporTest.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"StruffingReportAll.pdf\"");
            } else if (mode.equals("strippingAll")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/ReportAll.jasper");
                subreport = loader.getResourceAsStream("/com/pelindo/ebtos/report/StrippingReportAll_subreport.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"StrippingReportAll.pdf\"");
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }

            map.put("SUBREPORT_DIR", JRLoader.loadObject(subreport)); //masukin subreport yg udah tercompile!
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
            close(connection);
            close(servletOutputStream);
        }
    }

    private static void close(Closeable resource) {
        if (resource != null) {
            try {
                resource.close();
            } catch (IOException e) {
                Logger.getLogger(PrintAll.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    private static void close(Connection resource) {
        if (resource != null) {
            try {
                resource.close();
            } catch (SQLException ex) {
                Logger.getLogger(PrintAll.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public IndonesianNumberConverter getIndonesianNumberConverter() {
        return indonesianNumberConverter;
    }

    public void setIndonesianNumberConverter(IndonesianNumberConverter indonesianNumberConverter) {
        this.indonesianNumberConverter = indonesianNumberConverter;
    }
}
