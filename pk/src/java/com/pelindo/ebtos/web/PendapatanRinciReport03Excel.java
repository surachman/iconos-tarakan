/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.web;

import com.qtasnim.util.IndonesianNumberConverter;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;

import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JExcelApiExporter;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

/**
 *
 * @author dycoder
 */
@WebServlet(name = "PendapatanRinciReport03Excel", urlPatterns = {"/PendapatanRinciReport03Excel.csv"})
public class PendapatanRinciReport03Excel extends HttpServlet {

    @Resource(mappedName = "jdbc/pk_db")
    private DataSource ds;

    /** Creates a new instance of PerhitunganReceiving */
    public PendapatanRinciReport03Excel() {
        indonesianNumberConverter = new IndonesianNumberConverter();
        //englishNumberConverter=new EnglishNumberConverter();
    }
    private static final int DEFAULT_BUFFER_SIZE = 10240; // 10KB.
    private IndonesianNumberConverter indonesianNumberConverter;
    //private EnglishNumberConverter  englishNumberConverter;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ServletOutputStream servletOutputStream = response.getOutputStream();
        String bulan = request.getParameter("bulan");
        String tahun = request.getParameter("tahun");
        Integer bln = (Integer.parseInt(bulan));
        Integer thn = (Integer.parseInt(tahun));
        BigDecimal big = new BigDecimal(3000);
        long lon = big.longValue();
        new Date(big.longValue());
        new SimpleDateFormat("HH:mm:yyyy").format(new Date(big.longValue()));

        if (bulan == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND); // lempar404.
            return;
        }

        InputStream report = null;
        Connection connection = null;
        HashMap map = new HashMap();
        JasperPrint jp;
        ClassLoader loader = getClass().getClassLoader();

        try {
            connection = ds.getConnection();
//            response.setContentType("application/vnd.ms-excel");
            response.setContentType("text/csv");

//            map.put(JRParameter.REPORT_CONNECTION, connection);
            map.put("bulan", bln);
            map.put("tahun", thn);
            map.put("triwulan", bln);
            map.put("semester", thn);
            map.put("SUBREPORT_DIR", "/com/pelindo/ebtos/report/");

            report = loader.getResourceAsStream("/com/pelindo/ebtos/report/Report_Pendapatan_Rinci.jasper");
            response.setHeader("Content-Disposition", "inline; filename=\"Rincian Produksi Dan Pendapatan.csv\"");
            
            jp = JasperFillManager.fillReport(report, map);

            JRCsvExporter exporter = new JRCsvExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
            StringBuffer buffer = new StringBuffer();
            exporter.setParameter(JRExporterParameter.OUTPUT_STRING_BUFFER, buffer);
            exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
            exporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
            exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
            exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
            exporter.exportReport();
            servletOutputStream.write(buffer.toString().getBytes());
            response.flushBuffer();

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
                Logger.getLogger(RekapitulasiRealisasiReport07.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    private static void close(Connection resource) {
        if (resource != null) {
            try {
                resource.close();
            } catch (SQLException ex) {
                Logger.getLogger(RekapitulasiRealisasiReport07.class.getName()).log(Level.SEVERE, null, ex);
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
