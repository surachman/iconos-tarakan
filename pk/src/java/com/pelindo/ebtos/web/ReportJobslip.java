/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.web;

import com.pelindo.ebtos.bean.security.LoginSessionBean;
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
import javax.servlet.http.HttpSession;
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
@WebServlet(name = "reportJobslip", urlPatterns = {"/reportJobslip.pdf"})
public class ReportJobslip extends HttpServlet {
    private LoginSessionBean loginSessionBean;
    private static final int DEFAULT_BUFFER_SIZE = 10240; // 10KB.

    @Resource(mappedName="jdbc/pk_db")
    private DataSource ds;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletOutputStream servletOutputStream = response.getOutputStream();

        String noReg = request.getParameter("no_reg");
        String jobSlip = request.getParameter("job_slip");
        String block = request.getParameter("block");
        String slot = request.getParameter("slot");
        String row = request.getParameter("row");
        String tier = request.getParameter("tier");
        String tipe = request.getParameter("tipe");
        HttpSession session = (HttpSession) request.getSession(true);
        String username = (String) session.getAttribute("username");
        
        if (noReg == null || tipe == null) {
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
            map.put("no_reg", noReg);
            map.put("job_slip", jobSlip);
            map.put("blok", block);
            map.put("slot", slot);
            map.put("row", row);
            map.put("tier", tier);
            map.put("user", username);
            System.out.println("tipe : " + tipe);
            System.out.println("No Reg : " + noReg);
            if (tipe.equals("delivery")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/JobslipDelivery.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"JobslipDelivery.pdf\"");
            } else if (tipe.equals("receiving")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/JobslipReceivingNew.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"JobslipReceiving.pdf\"");
            } else if (tipe.equals("discharge")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/JobslipDischarge.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"JobslipDischarge.pdf\"");
            } else if (tipe.equals("load")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/JobslipLoad.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"JobslipLoad.pdf\"");
            } else if (tipe.equals("plugging")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/JobslipPlugging.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"JobslipPlugging.pdf\"");
            }else if (tipe.equals("pluggingDel")) {
                System.out.println("masuk test " + noReg + jobSlip);
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/JobslipPluggingDel.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"JobslipPlugging.pdf\"");
            } else if (tipe.equals("stripping")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/JobslipStripping.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"JobslipStripping.pdf\"");
            } else if (tipe.equals("stuffing")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/JobSlipStruffing.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"JobSlipStruffing.pdf\"");
            } else if (tipe.equals("strippingAll")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/StrippingReportAll_subreport.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"StrippingReportAll.pdf\"");
            } else if (tipe.equals("stuffingAll")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/StruffingReportAll_subreport.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"StruffingReportAll.pdf\"");
            } else if (tipe.equals("deliveryAll")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/JobSlipDeliveryAll_subreport.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"deliveryReportAll.pdf\"");
            } else if (tipe.equals("receivingAll")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/JobSlipReceivingPrintAll_subreport.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"receivinReportAll.pdf\"");
            } else if (tipe.equals("pluggingAll")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/PluggingReeferServicePrintAll.jasper");
                subreport = loader.getResourceAsStream("/com/pelindo/ebtos/report/PluggingReeferServicePrintAll_subreport.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"PluggingReeferServiceReportAll.pdf\"");
                map.put("SUBREPORT_DIR", JRLoader.loadObject(subreport)); //masukin subreport yg udah tercompile!
            }else if (tipe.equals("pluggingAllDel")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/PluggingReeferServicePrintAll.jasper");
                subreport = loader.getResourceAsStream("/com/pelindo/ebtos/report/PluggingReeferServicePrintAll_subreportDel.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"PluggingReeferServiceReportAll.pdf\"");
                map.put("SUBREPORT_DIR", JRLoader.loadObject(subreport)); //masukin subreport yg udah tercompile!
            }else if (tipe.equals("pluggingDischargeAll")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/ReeferDischargeServicePrintAll_subreport.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"ReeferDischargeServiceReportAll.pdf\"");
            } else if (tipe.equals("pluggingLoadAll")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/ReeferLoadServicePrintAll_subreport.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"ReeferLoadServicePrintAll.pdf\"");
            } else if (tipe.equals("deliveryUC")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/JobslipDeliveryUC.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"JobslipDeliveryUC.pdf\"");
            } else if (tipe.equals("receivingUC")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/JobslipReceivingUC.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"JobslipReceivingUC.pdf\"");
            } else if (tipe.equals("deliveryGoods")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/JobslipDeliveryBarang.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"JobslipDeliveryBarang.pdf\"");
            } else if (tipe.equals("receivingGoods")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/JobslipReceivingBarang.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"JobslipReceivingBarang.pdf\"");
            } else if (tipe.equals("behandle")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/JobslipBehandle.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"JobslipBehandle.pdf\"");
            } else if (tipe.equals("cancel")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/JobslipBatalMuat.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"JobslipBatalMuat.pdf\"");
            } else if (tipe.equals("deliveryUCAll")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/JobSlipDeliveryUcAll_subreport.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"DeliveryUcPrintAll.pdf\"");
            } else if (tipe.equals("receivingUCAll")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/JobSlipReceivingUcAll_subreport.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"ReceivingUcPrintAll.pdf\"");
            } else if (tipe.equals("deliveryAll")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/JobslipDeliveryBarangSubreport.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"GoodsDeliveryPrintAll.pdf\"");
            } else if (tipe.equals("deliveryGoodsAll")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/JobslipDeliveryBarangSubreport.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"GoodsDeliveryPrintAll.pdf\"");
            } else if (tipe.equals("receivingGoodsAll")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/JobslipReceivingBarangSubreport.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"GoodsReceivingPrintAll.pdf\"");
            } else if (tipe.equals("behandleAll")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/JobslipBehandleSubreport.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"JobSlipBehandleAll.pdf\"");
            } else if (tipe.equals("cancelAll")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/JobslipBatalMuatSubreport.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"JobSlipBatalMuatAll.pdf\"");
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
                Logger.getLogger(ReportJobslip.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    private static void close(Connection resource) {
        if (resource != null) {
            try {
                resource.close();
            } catch (SQLException ex) {
                Logger.getLogger(ReportJobslip.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
