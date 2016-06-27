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
@WebServlet(name = "rekapitulasiRealisasiProduksiDanPendapatanSubreport", urlPatterns = {"/rekapitulasiRealisasiProduksiDanPendapatanSubreport.pdf"})
public class RekapitulasiRealisasiProduksiDanPendapatanSubreport extends HttpServlet {
    private static final int DEFAULT_BUFFER_SIZE = 10240; // 10KB.
    private IndonesianNumberConverter indonesianNumberConverter;

    @Resource(mappedName="jdbc/pk_db")
    private DataSource ds;

    public RekapitulasiRealisasiProduksiDanPendapatanSubreport() {
        indonesianNumberConverter = new IndonesianNumberConverter();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletOutputStream servletOutputStream = response.getOutputStream();
        String bulan = request.getParameter("bulan");
        String tahun = request.getParameter("tahun");
        String mode = request.getParameter("mode");
        String ownerr = request.getParameter("ownerr");
        String equipCode=request.getParameter("equipCode");
        Integer bln = (Integer.parseInt(bulan));
        Integer thn = (Integer.parseInt(tahun));

        if (bulan == null || mode == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND); // lempar404.
            return;
        }

        InputStream report = null;
        InputStream subreport = null;
        InputStream subreport2 = null;
        Connection connection = null;
        HashMap map = new HashMap();
        JasperPrint jp;
        ClassLoader loader = getClass().getClassLoader();

        try {
            connection = ds.getConnection();
            
            map.put(JRParameter.REPORT_CONNECTION, connection);
            map.put("bulan", bln);
            map.put("tahun", thn);
            map.put("owner", ownerr);
            map.put("equipCode", equipCode);
            if (mode.equals("report06")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/Report06.jasper");
                subreport = loader.getResourceAsStream("/com/pelindo/ebtos/report/Report06_subreportReportDeliveryService.jasper");
                subreport2 = loader.getResourceAsStream("/com/pelindo/ebtos/report/Report06_subreportReceivingService.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"rekapitulasi_realisasi_pendapatan_produksi.pdf\"");
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }

            map.put("SUBREPORT_DIR1", JRLoader.loadObject(subreport)); //masukin subreport yg udah tercompile!
            map.put("SUBREPORT_DIR", JRLoader.loadObject(subreport2)); //masukin subreport yg udah tercompile!
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
                Logger.getLogger(RekapitulasiRealisasiProduksiDanPendapatanSubreport.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    private static void close(Connection resource) {
        if (resource != null) {
            try {
                resource.close();
            } catch (SQLException ex) {
                Logger.getLogger(RekapitulasiRealisasiProduksiDanPendapatanSubreport.class.getName()).log(Level.SEVERE, null, ex);
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
