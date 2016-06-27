/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.web;

import com.qtasnim.persistence.EntityManagerHelper;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
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
@WebServlet(name = "Delivery", urlPatterns = {"/delivery.pdf"})
public class ReportPerhitunganDelivery extends HttpServlet {

    private static final int DEFAULT_BUFFER_SIZE = 10240; // 10KB.

    @Resource(mappedName="jdbc/pk_db")
    private DataSource ds;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletOutputStream servletOutputStream = response.getOutputStream();

        String noReg = request.getParameter("no_reg");
        String total = request.getParameter("total");
        String tipe = request.getParameter("tipe");
        String ppnPrint = request.getParameter("ppn");
        String materaiPrint = request.getParameter("materai");
        String userId = request.getParameter("userId");
        String inclHandling = request.getParameter("inclHandling");

        BigDecimal bigDecimal = new BigDecimal(total);
        BigDecimal ppn = new BigDecimal(ppnPrint);
        BigDecimal materai = new BigDecimal(materaiPrint);

        if (noReg == null || tipe == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND); // lempar404.
            return;
        }
        response.reset();
        response.setContentType("application/pdf");

        InputStream report = null;
        InputStream subReport = null;
        Connection connection = null;
        HashMap map = new HashMap();
        JasperPrint jp;
        ClassLoader loader = getClass().getClassLoader();

        try {
            connection = ds.getConnection();
            
            map.put(JRParameter.REPORT_CONNECTION, connection);
            map.put("no_reg", noReg);
            map.put("tipe", tipe);
            map.put("ppn", ppn);
            map.put("materai", materai);
            map.put("userId", userId);
            map.put("total", bigDecimal);

            if (tipe.equals("delivery")) {
                map.put("inclHandling", inclHandling);
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/PerhitunganDelivery.jasper");
                subReport = loader.getResourceAsStream("/com/pelindo/ebtos/report/PerhitunganDelivery_subreport.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"PerhitunganDelivery.pdf\"");
            } else if (tipe.equals("behandle")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/PerhitunganBehandle.jasper");
                subReport = loader.getResourceAsStream("/com/pelindo/ebtos/report/PerhitunganBehandle_subreport.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"PerhitunganBehandle.pdf\"");
            } else if (tipe.equals("plugging")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/PerhitunganPlugging.jasper");
                subReport = loader.getResourceAsStream("/com/pelindo/ebtos/report/PerhitunganPlugging_subreport.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"PerhitunganPlugging.pdf\"");
            } else if (tipe.equals("reeferD")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/PerhitunganReeferDischarge.jasper");
                subReport = loader.getResourceAsStream("/com/pelindo/ebtos/report/PerhitunganReeferDischarge_subreport.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"PerhitunganReeferDischarge.pdf\"");
            } else if (tipe.equals("reeferL")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/PerhitunganReeferLoad.jasper");
                subReport = loader.getResourceAsStream("/com/pelindo/ebtos/report/PerhitunganReeferLoad_subreport.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"PerhitunganReeferLoad.pdf\"");
            } else if (tipe.equals("penumpukanSusulan")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/PerhitunganPenumpukanSusulan.jasper");
                subReport = loader.getResourceAsStream("/com/pelindo/ebtos/report/PerhitunganPenumpukanSusulan_subreport.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"PerhitunganPenumpukanSusulan.pdf\"");
            } else if (tipe.equals("penumpukanSusulanUC")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/PerhitunganPenumpukanSusulanUC.jasper");
                subReport = loader.getResourceAsStream("/com/pelindo/ebtos/report/PerhitunganPenumpukanSusulanUC_subreport.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"PerhitunganPenumpukanSusulanUC.pdf\"");
            } else if (tipe.equals("stuffing")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/PerhitunganStuffing.jasper");
                subReport = loader.getResourceAsStream("/com/pelindo/ebtos/report/PerhitunganStuffing_subreport.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"PerhitunganStuffing.pdf\"");
            } else if (tipe.equals("stripping")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/PerhitunganStripping.jasper");
                subReport = loader.getResourceAsStream("/com/pelindo/ebtos/report/PerhitunganStripping_subreport.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"PerhitunganStripping.pdf\"");
            } else if (tipe.equals("deliveryBarang")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/PerhitunganDeliveryBarang.jasper");
                subReport = loader.getResourceAsStream("/com/pelindo/ebtos/report/PerhitunganDeliveryBarang_subreport.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"PerhitunganDeliveryBarang.pdf\"");
            } else if (tipe.equals("deliveryUC")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/PerhitunganDeliveryUC.jasper");
                subReport = loader.getResourceAsStream("/com/pelindo/ebtos/report/PerhitunganDeliveryUC_subreport.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"PerhitunganDeliveryUC.pdf\"");
            } else if (tipe.equals("changeStatus")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/PerhitunganChangeStatus.jasper");
                subReport = loader.getResourceAsStream("/com/pelindo/ebtos/report/PerhitunganChangeStatus_subreport.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"PerhitunganChangeStatus.pdf\"");
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }

            map.put("SUBREPORT_DIR", JRLoader.loadObject(subReport)); //masukin subreport yg udah tercompile!
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
            close(subReport);
            close(connection);
            close(servletOutputStream);
        }
    }

    private static void close(Closeable resource) {
        if (resource != null) {
            try {
                resource.close();
            } catch (IOException e) {
                Logger.getLogger(ReportPerhitunganDelivery.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    private static void close(Connection resource) {
        if (resource != null) {
            try {
                resource.close();
            } catch (SQLException ex) {
                Logger.getLogger(ReportPerhitunganDelivery.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
