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
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;

import net.sf.jasperreports.engine.JasperPrint;

/**
 *
 * @author dycoder
 */
@WebServlet(name = "rekapitulasiRealisasiProduksi", urlPatterns = {"/rekapitulasiRealisasiProduksi.pdf"})
public class RekapitulasiRealisasiProduksi extends HttpServlet {
    private static final int DEFAULT_BUFFER_SIZE = 10240; // 10KB.

    @Resource(mappedName="jdbc/pk_db")
    private DataSource ds;
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ServletOutputStream servletOutputStream = response.getOutputStream();
        String bulan = request.getParameter("bulan");
        String tahun = request.getParameter("tahun");
        String mode = request.getParameter("mode");
        String owner = request.getParameter("owner");

        if (bulan == null || mode == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND); // lempar404.
            return;
        }
        
        response.reset();
        response.setContentType("application/pdf");

        InputStream report = null;
        Connection connection = null;
        Map map = new HashMap();
        JasperPrint jp;
        ClassLoader loader = getClass().getClassLoader();
        Integer bln = (Integer.parseInt(bulan));
        Integer thn = (Integer.parseInt(tahun));

        try {
            connection = ds.getConnection();
            
            map.put(JRParameter.REPORT_CONNECTION, connection);
            map.put("bulan", bln);
            map.put("tahun", thn);
            if (mode.equals("ProduksiGlobal")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/Report01.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"Report01" + mode + ".pdf\"");
            } else if (mode.equals("ProduksiDetail")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/Report01.jasper");                
                response.setHeader("Content-Disposition", "inline; filename=\"Report01" + mode + ".pdf\"");
            } else if (mode.equals("ProduksiPendapatan")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/Report03.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"Report03" + mode + ".pdf\"");
            } else if (mode.equals("report7")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/Report07.jasper");                
                response.setHeader("Content-Disposition", "inline; filename=\"Report07" + mode + ".pdf\"");
            } else if (mode.equals("report8")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/Report08.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"Report08" + mode + ".pdf\"");
            } else if (mode.equals("report9")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/Report09_JumlahBulanLaporan.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"Report09_JumlahBulanLaporan" + mode + ".pdf\"");
            } else if (mode.equals("report23")) {
//                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/Report23.jasper");
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/Report23kkt.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"Report23" + mode + ".pdf\"");
            } else if (mode.equals("report24")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/report24Berth.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"report24Berth" + mode + ".pdf\"");
            } else if(mode.equals("report12")){
                map.put("owner", owner);
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/Report12.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"Report12" + mode + ".pdf\"");
            } else if(mode.equals("report18")){
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/Report18.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"Report18" + mode + ".pdf\"");
            } else if(mode.equals("report19")){
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/Report19.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"Report19" + mode + ".pdf\"");
            } else if(mode.equals("report05")){
                map.put("owner", owner);
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/Report05.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"Report05" + mode + ".pdf\"");
            } else if(mode.equals("report22")){
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/Report22kkt.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"report YOR YTP" + bln + " " + thn + ".pdf\"");
//                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/Report22.jasper");
//                response.setHeader("Content-Disposition", "inline; filename=\"Report22" + mode + ".pdf\"");
            } else if(mode.equals("report01")){
                 System.out.println("masuk");
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/Report01.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"Report01" + mode + ".pdf\"");
            } else if(mode.equals("report02")){
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/Report02.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"Report02" + mode + ".pdf\"");
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }

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
                Logger.getLogger(RekapitulasiRealisasiProduksi.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    private static void close(Connection resource) {
        if (resource != null) {
            try {
                resource.close();
            } catch (SQLException ex) {
                Logger.getLogger(RekapitulasiRealisasiProduksi.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
