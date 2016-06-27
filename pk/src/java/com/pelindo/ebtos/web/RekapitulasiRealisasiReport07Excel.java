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
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;

import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JExcelApiExporter;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

/**
 *
 * @author dycoder
 */
@WebServlet(name = "RekapitulasiRealisasiReport07Excel", urlPatterns = {"/RekapitulasiRealisasiReport07Excel.csv"})
public class RekapitulasiRealisasiReport07Excel extends HttpServlet {

    @Resource(mappedName="jdbc/pk_db")
    private DataSource ds;

    /** Creates a new instance of PerhitunganReceiving */
    public RekapitulasiRealisasiReport07Excel() {
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
        String mode = request.getParameter("mode");
        String cust_code = request.getParameter("cust_code");
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
//            response.setContentType("application/vnd.ms-excel");
            response.setContentType("text/csv");

            map.put(JRParameter.REPORT_CONNECTION, connection);
            map.put("bulan", bln);
            map.put("tahun", thn);

            System.out.println("bulan : " + bulan);
            System.out.println("tahun : " + tahun);
            System.out.println("bln : " + bln);
            System.out.println("thn : " + thn);

            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            Date start = sdf.parse(String.valueOf("01-" + bulan + "-" + tahun));

//            map.put("periode_awal", tahun + "-" + bulan + "01");
//            map.put("periode_akhir", tahun + "-" + bulan+1 + "01");
            Calendar cal = Calendar.getInstance();
            cal.setTime(start);
//            cal.set(thn, bln, new Integer(1));
            map.put("periode_awal", cal.getTime());
            cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 1);
            map.put("periode_akhir", cal.getTime());
            cal.setTime(start);
            map.put("periode_akhir_bulan_lalu", cal.getTime());
            cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) - 1);
            map.put("periode_awal_bulan_lalu", cal.getTime());

            if (mode.equals("report10")) {
                map.put("cust_code", cust_code);
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/Report10kkt.jasper");
//                subreport = loader.getResourceAsStream("/com/pelindo/ebtos/report/Report10_subreportJumlahBulanLaporan.jasper");
//                subreport2 = loader.getResourceAsStream("/com/pelindo/ebtos/report/Report10_subreportJumlahLaporanbulanlalu.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"rekapitulasi_realisasi_pendapatan_produksi10.csv\"");
            } else if (mode.equals("report07")){
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/Report07NewKKT.jasper");
//                subreport = loader.getResourceAsStream("/com/pelindo/ebtos/report/Report7KKT_subreportJumlahBulanLaporan.jasper");
//                subreport2 = loader.getResourceAsStream("/com/pelindo/ebtos/report/Report7kkt_subreportJumlahLaporanbulanlalu.jasper");
                response.setHeader("Content-Disposition", "inline; filename=rekapitulasi_realisasi_pendapatan_produksi07.csv");
            } else if(mode.equals("report11")){
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/Report11kkt.jasper");
//                subreport = loader.getResourceAsStream("/com/pelindo/ebtos/report/Report11_subreportBulanLaporan.jasper");
//                subreport2 = loader.getResourceAsStream("/com/pelindo/ebtos/report/Report11_subreportJumlahLaporanbulanlalu.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"rekapitulasi_realisasi_pendapatan_produksi11.csv\"");
            }else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
            map.put("SUBREPORT_DIR", "/com/pelindo/ebtos/report/");
//            map.put("SUBREPORT_DIR_SEKARANG", JRLoader.loadObject(subreport)); //masukin subreport yg udah tercompile!
//            map.put("SUBREPORT_DIR_LALU", JRLoader.loadObject(subreport2)); //masukin subreport yg udah tercompile!
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

//            JRXlsExporter exporter = new JRXlsExporter();
//            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
//            ByteArrayOutputStream os = new ByteArrayOutputStream();
//            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, os);
//            exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
//            exporter.setParameter(JRXlsExporterParameter.IS_AUTO_DETECT_CELL_TYPE, Boolean.TRUE);
//            exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
//            exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
//            exporter.exportReport();
//            servletOutputStream.write(os.toByteArray());
//            response.flushBuffer();

////            byte[] file = JasperExportManager.exportReportToPdf(jp);
////            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//            ByteArrayOutputStream os = new ByteArrayOutputStream();
////            JRXlsExporter exporterXLS = new JRXlsExporter();
//            JExcelApiExporter exporterXLS = new JExcelApiExporter();
//            exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jp);
//            exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, os);
//            exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
//            exporterXLS.setParameter(JRXlsExporterParameter.IS_AUTO_DETECT_CELL_TYPE, Boolean.TRUE);
//            exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
//            exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
//            exporterXLS.exportReport();
//            servletOutputStream.write(os.toByteArray());
////            response.setContentLength(byteArrayOutputStream.size());
////            byteArrayOutputStream.writeTo(servletOutputStream);
//
////            byte[] file = JasperExportManager.exportReportToPdf(jp);
////            response.setContentLength(file.length);
////            outputfile.write(outputByteArray.toByteArray());
//
//            // tulis file
////            servletOutputStream.write(byteArrayOutputStream.toByteArray());
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
