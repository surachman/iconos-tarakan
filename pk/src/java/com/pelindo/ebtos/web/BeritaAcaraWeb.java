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
 * @author wulan
 */
@WebServlet(name="beritaAcaraWeb" , urlPatterns="/beritaAcaraWeb.pdf")
public class BeritaAcaraWeb extends HttpServlet{
    private static final int DEFAULT_BUFFER_SIZE = 10240; // 10KB.

    @Resource(mappedName="jdbc/pk_db")
    private DataSource ds;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletOutputStream servletOutputStream = response.getOutputStream();

        String noPpkb = request.getParameter("no_ppkb");
        String tipe = request.getParameter("tipe");
        String tgl = request.getParameter("tgl");
        String foreman = request.getParameter("foreman");
        String agen = request.getParameter("agen");
        String equip=request.getParameter("equip");

        if (noPpkb == null || tipe == null) {
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
            map.put("no_ppkb", noPpkb);
            map.put("equip", equip);
            map.put("tanggal", tgl);
            map.put("foreman", foreman);
            map.put("agen", agen);
            if (tipe.equalsIgnoreCase("b")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/beritaAcaraBongkaran.jasper");
                subreport = loader.getResourceAsStream("/com/pelindo/ebtos/report/beritaAcaraBongkaran_subreport1.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"beritaAcaraBongkaran" + noPpkb +".pdf\"");
            } else if (tipe.equalsIgnoreCase("m")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/beritaAcaraMuatan.jasper");
                subreport = loader.getResourceAsStream("/com/pelindo/ebtos/report/beritaAcaraMuatan_subreport1.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"beritaAcaraMuatan" + noPpkb + ".pdf\"");
            }else if (tipe.equalsIgnoreCase("x")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/timeSheetBeritaAcara.jasper");
                subreport = loader.getResourceAsStream("/com/pelindo/ebtos/report/timeSheetBeritaAcara_subreport1.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"beritaAcaraProduktivitas" + noPpkb + ".pdf\"");
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }

            map.put("SUBREPORT_DIR", JRLoader.loadObject(subreport)); //masukin subreport yg udah tercompile!

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
                Logger.getLogger(BeritaAcaraWeb.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    private static void close(Connection resource) {
        if (resource != null) {
            try {
                resource.close();
            } catch (SQLException ex) {
                Logger.getLogger(BeritaAcaraWeb.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
