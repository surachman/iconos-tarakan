/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.web;

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
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 *
 * @author dycode-java
 */
@WebServlet(urlPatterns = {"/perhitunganJasaTerminal.pdf"})
public class PerhitunganJasaTerminal extends HttpServlet {
    public static String PERHITUNGAN_PENUMPUKAN_BONGKARAN = "perhitunganPenumpukanBongkaran";
    public static String PERHITUNGAN_PENUMPUKAN_MUATAN = "perhitunganPenumpukanMuatan";
    public static String PERHITUNGAN_HANDLING_PETIKEMAS = "perhitunganHandlingPetikemas";
    public static String PERHITUNGAN_HANDLING_PETIKEMAS_SUMMARY = "perhitunganHandlingPetikemasSummary";
    public static String PERHITUNGAN_BIAYA_HANDLING = "perhitunganBiayaHandlingPetikemas";
    public static String PERHITUNGAN_PENUMPUKAN_BONGKARAN_UC = "perhitunganPenumpukanBongkaranUc";
    public static String PERHITUNGAN_PENUMPUKAN_MUATAN_UC = "perhitunganPenumpukanMuatanUc";
    public static String PERHITUNGAN_HANDLING_UC = "perhitunganHandlingUc";
    public static String PERHITUNGAN_BIAYA_HANDLING_UC = "perhitunganBiayaHandlingUc";
    private static final int DEFAULT_BUFFER_SIZE = 10240; // 10KB.

    @Resource(mappedName="jdbc/pk_db")
    private DataSource ds;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletOutputStream servletOutputStream = response.getOutputStream();

        String noPpkb = request.getParameter("no_ppkb");
        String currCode = request.getParameter("curr_code");
        String reportName = request.getParameter("report");

        if (noPpkb == null || reportName == null) {
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
        
        try {
            ClassLoader loader = getClass().getClassLoader();
            connection = ds.getConnection();
            
            map.put(JRParameter.REPORT_CONNECTION, connection);
            map.put("no_ppkb", noPpkb);

            if (reportName.equals(PERHITUNGAN_HANDLING_PETIKEMAS)) {
                subreport = loader.getResourceAsStream("/com/pelindo/ebtos/report/" + PERHITUNGAN_HANDLING_PETIKEMAS + "_airKapalSubreport.jasper");
                map.put("airKapalSubreport", JRLoader.loadObject(subreport));
                subreport = loader.getResourceAsStream("/com/pelindo/ebtos/report/" + PERHITUNGAN_HANDLING_PETIKEMAS + "_handlingSubreport.jasper");
                map.put("handlingSubreport", JRLoader.loadObject(subreport));
                subreport = loader.getResourceAsStream("/com/pelindo/ebtos/report/" + PERHITUNGAN_HANDLING_PETIKEMAS + "_hatchMoveSubreport.jasper");
                map.put("hatchMoveSubreport", JRLoader.loadObject(subreport));
                
                subreport = loader.getResourceAsStream("/com/pelindo/ebtos/report/" + PERHITUNGAN_HANDLING_PETIKEMAS + "_pbmSubreport.jasper");
                map.put("pbmSubreport", JRLoader.loadObject(subreport));
                
                subreport = loader.getResourceAsStream("/com/pelindo/ebtos/report/" + PERHITUNGAN_HANDLING_PETIKEMAS + "_stripStufSubreport.jasper");
                map.put("stripStufSubreport", JRLoader.loadObject(subreport));
                
                subreport = loader.getResourceAsStream("/com/pelindo/ebtos/report/" + PERHITUNGAN_HANDLING_PETIKEMAS + "_angsurSubreport.jasper");
                map.put("angsurSubreport", JRLoader.loadObject(subreport));
                //masukin summary report bongkaran
                subreport = loader.getResourceAsStream("/com/pelindo/ebtos/report/" + PERHITUNGAN_HANDLING_PETIKEMAS + "_rekapContainer.jasper");
                map.put("penumpukanSummarySubreport", JRLoader.loadObject(subreport));
                if (currCode != null) {
                    map.put("curr_code", currCode);
                }
            } else if (reportName.equals(PERHITUNGAN_HANDLING_PETIKEMAS_SUMMARY)) {
//                subreport = loader.getResourceAsStream("/com/pelindo/ebtos/report/" + PERHITUNGAN_HANDLING_PETIKEMAS_SUMMARY + "_airKapalSubreport.jasper");
//                map.put("airKapalSubreport", JRLoader.loadObject(subreport));
                subreport = loader.getResourceAsStream("/com/pelindo/ebtos/report/" + PERHITUNGAN_HANDLING_PETIKEMAS_SUMMARY + "_handlingSubreport.jasper");
                map.put("handlingSubreport", JRLoader.loadObject(subreport));
                subreport = loader.getResourceAsStream("/com/pelindo/ebtos/report/" + PERHITUNGAN_HANDLING_PETIKEMAS_SUMMARY + "_hatchMoveSubreport.jasper");
                map.put("hatchMoveSubreport", JRLoader.loadObject(subreport));
                
                subreport = loader.getResourceAsStream("/com/pelindo/ebtos/report/" + PERHITUNGAN_HANDLING_PETIKEMAS_SUMMARY + "_pbmSubreport.jasper");
                map.put("pbmSubreport", JRLoader.loadObject(subreport));
                
                subreport = loader.getResourceAsStream("/com/pelindo/ebtos/report/" + PERHITUNGAN_HANDLING_PETIKEMAS_SUMMARY + "_stripStufSubreport.jasper");
                map.put("stripStufSubreport", JRLoader.loadObject(subreport));
                
                subreport = loader.getResourceAsStream("/com/pelindo/ebtos/report/" + PERHITUNGAN_HANDLING_PETIKEMAS_SUMMARY + "_angsurSubreport.jasper");
                map.put("angsurSubreport", JRLoader.loadObject(subreport));
                //masukin summary report bongkaran
                subreport = loader.getResourceAsStream("/com/pelindo/ebtos/report/" + PERHITUNGAN_HANDLING_PETIKEMAS + "_rekapContainer.jasper");
                map.put("penumpukanSummarySubreport", JRLoader.loadObject(subreport));
                if (currCode != null) {
                    map.put("curr_code", currCode);
                }
            } else if (reportName.equals(PERHITUNGAN_BIAYA_HANDLING)) {
                subreport = loader.getResourceAsStream("/com/pelindo/ebtos/report/" + PERHITUNGAN_BIAYA_HANDLING + "_biayaHandlingSubreport.jasper");
                map.put("handlingSubreport", JRLoader.loadObject(subreport));
            } else if (reportName.equals(PERHITUNGAN_PENUMPUKAN_BONGKARAN)) {
                subreport = loader.getResourceAsStream("/com/pelindo/ebtos/report/" + PERHITUNGAN_PENUMPUKAN_BONGKARAN + "_penumpukanSubreport.jasper");
                map.put("penumpukanSubreport", JRLoader.loadObject(subreport));
                subreport = loader.getResourceAsStream("/com/pelindo/ebtos/report/" + PERHITUNGAN_PENUMPUKAN_BONGKARAN + "_penumpukanSummarySubreport.jasper");
                map.put("penumpukanSummarySubreport", JRLoader.loadObject(subreport));
            } else if (reportName.equals(PERHITUNGAN_PENUMPUKAN_MUATAN)) {
                subreport = loader.getResourceAsStream("/com/pelindo/ebtos/report/" + PERHITUNGAN_PENUMPUKAN_MUATAN + "_penumpukanSubreport.jasper");
                map.put("penumpukanSubreport", JRLoader.loadObject(subreport));
                subreport = loader.getResourceAsStream("/com/pelindo/ebtos/report/" + PERHITUNGAN_PENUMPUKAN_MUATAN + "_penumpukanSummarySubreport.jasper");
                map.put("penumpukanSummarySubreport", JRLoader.loadObject(subreport));
                subreport = loader.getResourceAsStream("/com/pelindo/ebtos/report/" + PERHITUNGAN_PENUMPUKAN_MUATAN + "_masaPenumpukanSummarySubreport.jasper");
                map.put("masaPenumpukanSummarySubreport", JRLoader.loadObject(subreport));
            } else if (reportName.equals(PERHITUNGAN_HANDLING_UC)) {
                subreport = loader.getResourceAsStream("/com/pelindo/ebtos/report/" + PERHITUNGAN_HANDLING_UC + "_handlingSubreport.jasper");
                map.put("handlingSubreport", JRLoader.loadObject(subreport));

                if (currCode != null) {
                    map.put("curr_code", currCode);
                }
            } else if (reportName.equals(PERHITUNGAN_BIAYA_HANDLING_UC)) {
                subreport = loader.getResourceAsStream("/com/pelindo/ebtos/report/" + PERHITUNGAN_BIAYA_HANDLING_UC + "_biayaHandlingSubreport.jasper");
                map.put("handlingSubreport", JRLoader.loadObject(subreport));
            } else if (reportName.equals(PERHITUNGAN_PENUMPUKAN_BONGKARAN_UC)) {
                subreport = loader.getResourceAsStream("/com/pelindo/ebtos/report/" + PERHITUNGAN_PENUMPUKAN_BONGKARAN_UC + "_penumpukanSubreport.jasper");
                map.put("penumpukanSubreport", JRLoader.loadObject(subreport));
                subreport = loader.getResourceAsStream("/com/pelindo/ebtos/report/" + PERHITUNGAN_PENUMPUKAN_BONGKARAN_UC + "_penumpukanSummarySubreport.jasper");
                map.put("penumpukanSummarySubreport", JRLoader.loadObject(subreport));
            } else if (reportName.equals(PERHITUNGAN_PENUMPUKAN_MUATAN_UC)) {
                subreport = loader.getResourceAsStream("/com/pelindo/ebtos/report/" + PERHITUNGAN_PENUMPUKAN_MUATAN_UC + "_penumpukanSubreport.jasper");
                map.put("penumpukanSubreport", JRLoader.loadObject(subreport));
                subreport = loader.getResourceAsStream("/com/pelindo/ebtos/report/" + PERHITUNGAN_PENUMPUKAN_MUATAN_UC + "_penumpukanSummarySubreport.jasper");
                map.put("penumpukanSummarySubreport", JRLoader.loadObject(subreport));
                subreport = loader.getResourceAsStream("/com/pelindo/ebtos/report/" + PERHITUNGAN_PENUMPUKAN_MUATAN_UC + "_masaPenumpukanSummarySubreport.jasper");
                map.put("masaPenumpukanSummarySubreport", JRLoader.loadObject(subreport));
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                throw new RuntimeException("Unidentified report name");
            }
            
            report = loader.getResourceAsStream("/com/pelindo/ebtos/report/" + reportName + ".jasper");
            response.setHeader("Content-Disposition", "inline; iflename=\"" + reportName + ".pdf\"");
            
            
            
            
            jp = JasperFillManager.fillReport((JasperReport) JRLoader.loadObject(report), map);
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
                Logger.getLogger(PerhitunganJasaTerminal.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    private static void close(Connection resource) {
        if (resource != null) {
            try {
                resource.close();
            } catch (SQLException ex) {
                Logger.getLogger(PerhitunganJasaTerminal.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
