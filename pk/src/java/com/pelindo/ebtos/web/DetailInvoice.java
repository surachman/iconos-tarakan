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
@WebServlet(name = "Detail", urlPatterns = {"/detail.pdf"})
public class DetailInvoice extends HttpServlet {
    private static final int DEFAULT_BUFFER_SIZE = 10240; // 10KB.

    @Resource(mappedName="jdbc/pk_db")
    private DataSource ds;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletOutputStream servletOutputStream = response.getOutputStream();

        String noReg = request.getParameter("no_reg");
        String tipe = request.getParameter("tipe");
        String userId = request.getParameter("username");
        String ppnPrint = request.getParameter("ppn");
        String materaiPrint = request.getParameter("materai");
        String total = request.getParameter("total");

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
        BigDecimal tot = new BigDecimal(total);
        BigDecimal ppn = new BigDecimal(ppnPrint);
        BigDecimal materai = new BigDecimal(materaiPrint);

        try {
            connection = ds.getConnection();
            
            map.put(JRParameter.REPORT_CONNECTION, connection);
            map.put("no_reg", noReg);
            map.put("tipe", tipe);
            map.put("userId", userId);
            map.put("ppn", ppn);
            map.put("materai", materai);
            BigDecimal bigDTotal = new BigDecimal(total);
            map.put("total", bigDTotal);

            if (tipe.equals("SC002")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/InvoicePerhitunganDelivery.jasper");
                subReport = loader.getResourceAsStream("/com/pelindo/ebtos/report/InvoicePerhitunganDelivery_subreport.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"DetailDelivery.pdf\"");
            } else if (tipe.equalsIgnoreCase("SC001")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/InvoicePerhitunganReceiving.jasper");
                subReport = loader.getResourceAsStream("/com/pelindo/ebtos/report/InvoicePerhitunganReceiving_subreport.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"DetailReceiving.pdf\"");
            } else if (tipe.equalsIgnoreCase("SC015")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/InvoicePerhitunganReeferDischarge.jasper");
                subReport = loader.getResourceAsStream("/com/pelindo/ebtos/report/InvoicePerhitunganReeferDischarge_subreport.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"DetailDischarge.pdf\"");
            } else if (tipe.equalsIgnoreCase("SC016")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/InvoicePerhitunganReeferLoad.jasper");
                subReport = loader.getResourceAsStream("/com/pelindo/ebtos/report/InvoicePerhitunganReeferLoad_subreport.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"DetailLoad.pdf\"");
            } else if (tipe.equalsIgnoreCase("SC003")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/InvoicePerhitunganPlugging.jasper");
                subReport = loader.getResourceAsStream("/com/pelindo/ebtos/report/InvoicePerhitunganPlugging_subreport.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"DetailPlugging.pdf\"");
            } else if (tipe.equalsIgnoreCase("SC006")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/InvoicePerhitunganStripping.jasper");
                subReport = loader.getResourceAsStream("/com/pelindo/ebtos/report/InvoicePerhitunganStripping_subreport.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"DetailStripping.pdf\"");
            } else if (tipe.equalsIgnoreCase("SC007")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/InvoicePerhitunganStuffing.jasper");
                subReport = loader.getResourceAsStream("/com/pelindo/ebtos/report/InvoicePerhitunganStuffing_subreport.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"DetailStuffing.pdf\"");
            } else if (tipe.equalsIgnoreCase("SC009")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/InvoicePerhitunganPenumpukanSusulan.jasper");
                subReport = loader.getResourceAsStream("/com/pelindo/ebtos/report/InvoicePerhitunganPenumpukanSusulan_subreport.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"DetailPenumpukanSusulan.pdf\"");
            } else if (tipe.equalsIgnoreCase("SC017")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/InvoicePerhitunganAngsurPetiKemas.jasper");
                subReport = loader.getResourceAsStream("/com/pelindo/ebtos/report/InvoicePerhitunganAngsurPetiKemas_subreport.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"DetailAngsur.pdf\"");
            } else if (tipe.equalsIgnoreCase("SC004")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/InvoicePerhitunganBehandle.jasper");
                subReport = loader.getResourceAsStream("/com/pelindo/ebtos/report/InvoicePerhitunganBehandle_subreport.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"DetailBehandle.pdf\"");
            } else if (tipe.equalsIgnoreCase("SC014")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/InvoicePerhitunganDeliveryUC.jasper");
                subReport = loader.getResourceAsStream("/com/pelindo/ebtos/report/InvoicePerhitunganDeliveryUC_subreport.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"DetailDeliveryUC.pdf\"");
            } else if (tipe.equalsIgnoreCase("SC013")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/InvoicePerhitunganReceivingUc.jasper");
                subReport = loader.getResourceAsStream("/com/pelindo/ebtos/report/InvoicePerhitunganReceivingUc_subreport.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"DetailReceivingUC.pdf\"");
            } else if (tipe.equalsIgnoreCase("SC021")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/InvoicePerhitunganPenumpukanSusulanUC.jasper");
                subReport = loader.getResourceAsStream("/com/pelindo/ebtos/report/InvoicePerhitunganPenumpukanSusulanUC_subreport.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"DetailPenumpukanSusulanUC.pdf\"");
            } else if (tipe.equalsIgnoreCase("SC005")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/InvoicePerhitunganCancelLoading.jasper");
                subReport = loader.getResourceAsStream("/com/pelindo/ebtos/report/InvoicePerhitunganCancelLoading_subreport.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"DetailBatalMuat.pdf\"");
            } else if (tipe.equalsIgnoreCase("SC018")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/InvoicePerhitunganGoodsReceiving.jasper");
                subReport = loader.getResourceAsStream("/com/pelindo/ebtos/report/InvoicePerhitunganGoodsReceiving_subreport.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"DetailReceivingBarang.pdf\"");
            } else if (tipe.equalsIgnoreCase("SC019")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/InvoicePerhitunganDeliveryBarang.jasper");
                subReport = loader.getResourceAsStream("/com/pelindo/ebtos/report/InvoicePerhitunganDeliveryBarang_subreport.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"DetailDeliveryBarang.pdf\"");
            } else if (tipe.equalsIgnoreCase("SC024")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/ChangeStatusDetail.jasper");
                subReport = loader.getResourceAsStream("/com/pelindo/ebtos/report/ChangeStatusDetail_subreport.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"ChangeStatusDetail.pdf\"");
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }

            map.put("SUBREPORT_DIR", JRLoader.loadObject(subReport)); //masukin subreport yg udah tercompile!
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
                Logger.getLogger(DetailInvoice.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    private static void close(Connection resource) {
        if (resource != null) {
            try {
                resource.close();
            } catch (SQLException ex) {
                Logger.getLogger(DetailInvoice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
