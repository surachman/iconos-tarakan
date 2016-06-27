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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;

import net.sf.jasperreports.engine.JasperPrint;
import javax.sql.DataSource;
import javax.annotation.Resource;

/**
 *
 * @author wulan
 */
@WebServlet(name = "daftarPetikemasMasuk", urlPatterns = "/daftarPetikemasMasuk.pdf")
public class DaftarPetikemasMasuk extends HttpServlet {

    private static final int DEFAULT_BUFFER_SIZE = 10240; // 10KB.
    @Resource(mappedName = "jdbc/pk_db")
    private DataSource ds;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletOutputStream servletOutputStream = response.getOutputStream();

        String tipe = request.getParameter("tipe");
        String pnJawab = request.getParameter("pnJawab");
        String supervisor = request.getParameter("supervisor");
        String owner = request.getParameter("owner");
        HttpSession session = (HttpSession) request.getSession(true);
        String username = (String) session.getAttribute("username");

        if (tipe == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND); // lempar404.
            return;
        }
        response.reset();
        response.setContentType("application/pdf");

        InputStream report = null;
        Connection connection = null;
        HashMap map = new HashMap();
        JasperPrint jp;
        ClassLoader loader = getClass().getClassLoader();

        try {
            connection = ds.getConnection();
            Date tanggal1 = sdf.parse(request.getParameter("tanggal1"));
            Date tanggal2 = sdf.parse(request.getParameter("tanggal2"));
            map.put(JRParameter.REPORT_CONNECTION, connection);
            map.put("tanggal1", tanggal1);
            map.put("tanggal2", tanggal2);
            map.put("penanggungJawab", pnJawab);
            map.put("admGate", supervisor);
            map.put("koordinator", supervisor);
            map.put("user", username);
            map.put("owner", owner);

            if (tipe.equalsIgnoreCase("k")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/DaftarPetikemasKeluar.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"beritaAcaraBongkaran" + Calendar.getInstance().getTime() + ".pdf\"");
            } else if (tipe.equalsIgnoreCase("m")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/DaftarPetikemasMasuk.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"beritaAcaraMuatan" + Calendar.getInstance().getTime() + ".pdf\"");
            } else if (tipe.equalsIgnoreCase("r")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/LaporanHarianKegiatanOpsLapangan.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"LaporanHarianKegiatanOpsLapangan" + Calendar.getInstance().getTime() + ".pdf\"");
            } else if (tipe.equalsIgnoreCase("d")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/LaporanPlugingReeferHarian.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"LaporanPlugingReeferHarian" + Calendar.getInstance().getTime() + ".pdf\"");
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
            
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
            close(connection);
            close(servletOutputStream);
        }
    }

    private static void close(Closeable resource) {
        if (resource != null) {
            try {
                resource.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void close(Connection resource) {
        if (resource != null) {
            try {
                resource.close();
            } catch (SQLException ex) {
                Logger.getLogger(LaporanOperasionalKapal.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
