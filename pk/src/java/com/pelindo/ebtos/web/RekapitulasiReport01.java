/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.web;

import com.qtasnim.util.IndonesianNumberConverter;
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
 * @author dycoder
 */
@WebServlet(name = "rekapitulasiReport01", urlPatterns = {"/rekapitulasiReport01.pdf"})
public class RekapitulasiReport01 extends HttpServlet {
    private static final int DEFAULT_BUFFER_SIZE = 10240; // 10KB.
    private IndonesianNumberConverter indonesianNumberConverter;

    @Resource(mappedName="jdbc/pk_db")
    private DataSource ds;

    public RekapitulasiReport01() {
        indonesianNumberConverter = new IndonesianNumberConverter();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletOutputStream servletOutputStream = response.getOutputStream();
        String bulan = request.getParameter("bulan");
        String tahun = request.getParameter("tahun");
        String mode = request.getParameter("mode");        
        Integer bln = (Integer.parseInt(bulan));
        Integer thn = (Integer.parseInt(tahun));

        if (bulan == null || mode == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND); // lempar404.
            return;
        }

        InputStream report = null;
        InputStream subreport0 = null;
        InputStream subreport02 = null;
        InputStream subreport03 = null;
        InputStream subreport04 = null;
        InputStream subreport05 = null;
        InputStream subreport06 = null;
        InputStream subreport07 = null;
        InputStream subreport08 = null;
        InputStream subreport09 = null;
        Connection connection = null;
        HashMap map = new HashMap();
        JasperPrint jp;
        ClassLoader loader = getClass().getClassLoader();

        try {
            connection = ds.getConnection();
            
            map.put(JRParameter.REPORT_CONNECTION, connection);
            map.put("bulan", bln);
            map.put("tahun", thn);
            if (mode.equals("report03")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/Report03.jasper");
                subreport0 = loader.getResourceAsStream("/com/pelindo/ebtos/report/Report03_subreportHal0.jasper");
                subreport02 = loader.getResourceAsStream("/com/pelindo/ebtos/report/Report03_subreportHal2.jasper");
                subreport03 = loader.getResourceAsStream("/com/pelindo/ebtos/report/Report03_subreportHal3.jasper");
                subreport04 = loader.getResourceAsStream("/com/pelindo/ebtos/report/Report03_subreportHal4.jasper");
                subreport05 = loader.getResourceAsStream("/com/pelindo/ebtos/report/Report03_subreportHal5.jasper");
                subreport06 = loader.getResourceAsStream("/com/pelindo/ebtos/report/Report03_subreportHal6.jasper");
                subreport07 = loader.getResourceAsStream("/com/pelindo/ebtos/report/Report03_subreportHal7.jasper");
                subreport08 = loader.getResourceAsStream("/com/pelindo/ebtos/report/Report03_subreportHal8.jasper");
                subreport09 = loader.getResourceAsStream("/com/pelindo/ebtos/report/Report03_subreportHal9.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"rekapitulasi_realisasi_pendapatan_produksi03.pdf\"");
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }

            map.put("SUBREPORT_DIR0", JRLoader.loadObject(subreport0)); //masukin subreport yg udah tercompile!
            map.put("SUBREPORT_DIR2", JRLoader.loadObject(subreport02)); //masukin subreport yg udah tercompile!
            map.put("SUBREPORT_DIR3", JRLoader.loadObject(subreport03)); //masukin subreport yg udah tercompile!
            map.put("SUBREPORT_DIR4", JRLoader.loadObject(subreport04)); //masukin subreport yg udah tercompile!
            map.put("SUBREPORT_DIR5", JRLoader.loadObject(subreport05)); //masukin subreport yg udah tercompile!
            map.put("SUBREPORT_DIR6", JRLoader.loadObject(subreport06)); //masukin subreport yg udah tercompile!
            map.put("SUBREPORT_DIR7", JRLoader.loadObject(subreport07)); //masukin subreport yg udah tercompile!
            map.put("SUBREPORT_DIR8", JRLoader.loadObject(subreport08)); //masukin subreport yg udah tercompile!
            map.put("SUBREPORT_DIR9", JRLoader.loadObject(subreport09)); //masukin subreport yg udah tercompile!
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
            close(subreport0);
            close(subreport02);
            close(subreport03);
            close(subreport04);
            close(subreport05);
            close(subreport06);
            close(subreport07);
            close(subreport08);
            close(subreport09);
            close(connection);
            close(servletOutputStream);
        }
    }

    private static void close(Closeable resource) {
        if (resource != null) {
            try {
                resource.close();
            } catch (IOException e) {
                Logger.getLogger(RekapitulasiReport01.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    private static void close(Connection resource) {
        if (resource != null) {
            try {
                resource.close();
            } catch (SQLException ex) {
                Logger.getLogger(RekapitulasiReport01.class.getName()).log(Level.SEVERE, null, ex);
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
