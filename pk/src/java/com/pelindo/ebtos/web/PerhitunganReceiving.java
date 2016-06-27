/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.web;

import com.qtasnim.persistence.EntityManagerHelper;
import com.qtasnim.util.IndonesianNumberConverter;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.DecimalFormat;
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
@WebServlet(name = "perhitunganReceiving", urlPatterns = {"/perhitunganReceiving.pdf"})
public class PerhitunganReceiving extends HttpServlet {
    private static final int DEFAULT_BUFFER_SIZE = 10240; // 10KB.
    private IndonesianNumberConverter indonesianNumberConverter;

    @Resource(mappedName="jdbc/pk_db")
    private DataSource ds;

    public PerhitunganReceiving() {
        indonesianNumberConverter = new IndonesianNumberConverter();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletOutputStream servletOutputStream = response.getOutputStream();

        String noRegistrasi = request.getParameter("noRegistrasi");
        String total = request.getParameter("total");
        String mode = request.getParameter("mode");
        String status=request.getParameter("status");
        String ppnPrint = request.getParameter("ppn");
        String materaiPrint=request.getParameter("materai");
        String userId=request.getParameter("userId");

        if (noRegistrasi == null || mode == null) {
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
        BigDecimal bigDecimal1 = new BigDecimal(total);
        BigDecimal ppn = new BigDecimal(ppnPrint);
        BigDecimal materai = new BigDecimal(materaiPrint);

        try {
            connection = ds.getConnection();
            
            map.put(JRParameter.REPORT_CONNECTION, connection);
            map.put("no_reg", noRegistrasi);
            map.put("total", bigDecimal1);
            map.put("total_charge", bigDecimal1);
            map.put("ppn", ppn);
            map.put("materai", materai);
            map.put("status", status);
            map.put("userId", userId);
            if (mode.equals("receiving")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/perhitunganReceiving.jasper");
                subreport = loader.getResourceAsStream("/com/pelindo/ebtos/report/PerhitunganReceiving_subreport.jasper");
                response.setHeader("Content-Disposition", "attachment; filename=\"perhitungan_receiving.pdf\"");
                
            } else if (mode.equals("angsur")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/PerhitunganAngsurPetiKemas.jasper");
                subreport = loader.getResourceAsStream("/com/pelindo/ebtos/report/PerhitunganAngsurPetiKemas_subreport.jasper");
                response.setHeader("Content-Disposition", "attachment; filename=\"perhitungan_angsur_petikemas.pdf\"");
            } else if (mode.equals("cancelLoading")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/PerhitunganCancelLoading.jasper");
                subreport = loader.getResourceAsStream("/com/pelindo/ebtos/report/PerhitunganCancelLoading_subreport.jasper");
                response.setHeader("Content-Disposition", "attachment; filename=\"perhitungan_cancel_loading.pdf\"");
            } else if (mode.equals("goodsReceiving")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/PerhitunganGoodsReceiving.jasper");
                subreport = loader.getResourceAsStream("/com/pelindo/ebtos/report/PerhitunganGoodsReceiving_subreport.jasper");
                response.setHeader("Content-Disposition", "attachment; filename=\"perhitungan_goods_receiving.pdf\"");
            } else if (mode.equals("ReceivingUc")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/PerhitunganReceivingUc.jasper");
                subreport = loader.getResourceAsStream("/com/pelindo/ebtos/report/PerhitunganReceivingUc_subreport.jasper");
                response.setHeader("Content-Disposition", "attachment; filename=\"perhitungan_receiving_uc.pdf\"");
            } else if (mode.equals("penumpukanSusulan")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/PerhitunganPenumpukanSusulan.jasper");
                subreport = loader.getResourceAsStream("/com/pelindo/ebtos/report/PerhitunganPenumpukanSusulan_subreport.jasper");
                response.setHeader("Content-Disposition", "attachment; filename=\"PerhitunganPenumpukanSusulan.pdf\"");
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
                Logger.getLogger(PerhitunganReceiving.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    private static void close(Connection resource) {
        if (resource != null) {
            try {
                resource.close();
            } catch (SQLException ex) {
                Logger.getLogger(PerhitunganReceiving.class.getName()).log(Level.SEVERE, null, ex);
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
