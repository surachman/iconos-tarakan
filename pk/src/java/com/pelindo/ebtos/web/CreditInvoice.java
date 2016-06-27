/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.web;

import com.qtasnim.persistence.EntityManagerHelper;
import com.qtasnim.util.EnglishNumberConverter;
import com.qtasnim.util.IndonesianNumberConverter;
import com.pelindo.ebtos.ejb.facade.remote.IDGeneratorFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.InvoiceFacadeRemote;
import com.pelindo.ebtos.model.db.Invoice;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
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
@WebServlet(name="CreditInvoice", urlPatterns={"/CreditInvoice.pdf"})
public class CreditInvoice extends HttpServlet {
    private static final int DEFAULT_BUFFER_SIZE = 10240; // 10KB.

    @Resource(mappedName="jdbc/pk_db")
    private DataSource ds;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletOutputStream servletOutputStream = response.getOutputStream();

        String noReg = request.getParameter("no_reg");
        String tipe = request.getParameter("tipe");
        String username = request.getParameter("username");
        String to = request.getParameter("to");
        String curr = request.getParameter("curr");
        String detail = request.getParameter("detail");

        if (noReg == null || tipe == null || to == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND); // lempar404.
            return;
        }

        response.reset();
        response.setContentType("application/pdf");

        InputStream report = null;
        InputStream subReport = null;
        Connection connection = null;
        String terbilang;
        IndonesianNumberConverter converter = new IndonesianNumberConverter();
        EnglishNumberConverter converterEng = new EnglishNumberConverter();
        HashMap map = new HashMap();
        JasperPrint jp;
        ClassLoader loader = getClass().getClassLoader();

        if(curr.equalsIgnoreCase("ID"))
            terbilang = converter.convertToWord(to) + "Rupiah";
        else
            terbilang = converterEng.numberAsSentenceFactory(to);

        try {
            connection = ds.getConnection();
            
            map.put(JRParameter.REPORT_CONNECTION, connection);
            map.put("no_reg", noReg);
            map.put("terbilang", terbilang);
            map.put("username", username);
            if (tipe.equalsIgnoreCase("SC002")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/InvoiceDelivery.jasper");
                if(detail.equalsIgnoreCase("true"))
                    subReport = loader.getResourceAsStream("/com/pelindo/ebtos/report/InvoiceDelivery_subreport.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"InvoiceDelivery.pdf\"");
            } else if (tipe.equalsIgnoreCase("SC001")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/InvoiceReceivingF.jasper");
                if(detail.equalsIgnoreCase("true"))
                    subReport = loader.getResourceAsStream("/com/pelindo/ebtos/report/InvoiceReceiving_subreport.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"InvoiceReceiving.pdf\"");
            } else if (tipe.equalsIgnoreCase("SC015")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/InvoiceDischargeF.jasper");
                if(detail.equalsIgnoreCase("true"))
                    subReport = loader.getResourceAsStream("/com/pelindo/ebtos/report/InvoiceDischarge_subreport.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"InvoiceDischarge.pdf\"");
            } else if (tipe.equalsIgnoreCase("SC016")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/InvoiceLoadF.jasper");
                if(detail.equalsIgnoreCase("true"))
                    subReport = loader.getResourceAsStream("/com/pelindo/ebtos/report/InvoiceLoad_subreport.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"InvoiceLoad.pdf\"");
            } else if (tipe.equalsIgnoreCase("SC003")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/InvoicePluggingF.jasper");
                if(detail.equalsIgnoreCase("true"))
                    subReport = loader.getResourceAsStream("/com/pelindo/ebtos/report/InvoicePlugging_subreport.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"InvoicePlugging.pdf\"");
            } else if (tipe.equalsIgnoreCase("SC006")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/InvoiceStripping2.jasper");
                if(detail.equalsIgnoreCase("true"))
                    subReport = loader.getResourceAsStream("/com/pelindo/ebtos/report/InvoiceStripping_subreport.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"InvoiceStripping.pdf\"");
            } else if (tipe.equalsIgnoreCase("SC007")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/InvoiceStuffingF.jasper");
                if(detail.equalsIgnoreCase("true"))
                    subReport = loader.getResourceAsStream("/com/pelindo/ebtos/report/InvoiceStuffing_subreport.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"InvoiceStuffing.pdf\"");
            } else if (tipe.equalsIgnoreCase("SC009")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/InvoicePenumpukanSusulanR.jasper");
                if(detail.equalsIgnoreCase("true"))
                    subReport = loader.getResourceAsStream("/com/pelindo/ebtos/report/InvoicePenumpukanSusulan_subreport.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"InvoicePenumpukanSusulan.pdf\"");
            } else if (tipe.equalsIgnoreCase("SC017")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/InvoiceAngsurR.jasper");
                if(detail.equalsIgnoreCase("true"))
                    subReport = loader.getResourceAsStream("/com/pelindo/ebtos/report/InvoiceAngsurPetiKemas_subreport.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"InvoiceAngsur.pdf\"");
            } else if (tipe.equalsIgnoreCase("SC004")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/InvoiceBehandleF.jasper");
                if(detail.equalsIgnoreCase("true"))
                    subReport = loader.getResourceAsStream("/com/pelindo/ebtos/report/InvoiceBehandle_subreport.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"InvoiceBehandle.pdf\"");
            } else if (tipe.equalsIgnoreCase("SC014")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/InvoiceDeliveryUCF.jasper");
                if(detail.equalsIgnoreCase("true"))
                    subReport = loader.getResourceAsStream("/com/pelindo/ebtos/report/InvoiceDeliveryUC_subreport.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"InvoiceDeliveryUC.pdf\"");
            } else if (tipe.equalsIgnoreCase("SC013")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/InvoiceReceivingUCF.jasper");
                if(detail.equalsIgnoreCase("true"))
                    subReport = loader.getResourceAsStream("/com/pelindo/ebtos/report/InvoiceReceivingUC_subreport.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"InvoiceReceivingUC.pdf\"");
            } else if (tipe.equalsIgnoreCase("SC021")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/InvoicePenumpukanSusulanUC2.jasper");
                if(detail.equalsIgnoreCase("true"))
                    subReport = loader.getResourceAsStream("/com/pelindo/ebtos/report/InvoicePenumpukanSusulanUC_subreport.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"InvoicePenumpukanSusulanUC.pdf\"");
            } else if (tipe.equalsIgnoreCase("SC005")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/InvoiceCancelLoading.jasper");
                if(detail.equalsIgnoreCase("true"))
                    subReport = loader.getResourceAsStream("/com/pelindo/ebtos/report/InvoiceCancelLoading_subreport.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"InvoiceBatalMuat.pdf\"");
            } else if (tipe.equalsIgnoreCase("SC018")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/InvoiceReceivingBarangF.jasper");
                if(detail.equalsIgnoreCase("true"))
                    subReport = loader.getResourceAsStream("/com/pelindo/ebtos/report/InvoiceReceivingBarang_subreport.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"InvoiceReceivingBarang.pdf\"");
            } else if (tipe.equalsIgnoreCase("SC019")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/InvoiceDeliveryBarangF.jasper");
                if(detail.equalsIgnoreCase("true"))
                    subReport = loader.getResourceAsStream("/com/pelindo/ebtos/report/InvoiceDeliveryBarang_subreport.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"InvoiceDeliveryBarang.pdf\"");
            } else if (tipe.equalsIgnoreCase("SCNMA")) {
                report = loader.getResourceAsStream("/com/pelindo/ebtos/report/NotaManual.jasper");
                if(detail.equalsIgnoreCase("true"))
                    subReport = loader.getResourceAsStream("/com/pelindo/ebtos/report/NotaManual_subreport.jasper");
                response.setHeader("Content-Disposition", "inline; filename=\"NotaManual.pdf\"");
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }

            if(detail.equalsIgnoreCase("false"))
                subReport  = loader.getResourceAsStream("/com/pelindo/ebtos/report/InvoiceLampiranBlank.jasper");

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
                Logger.getLogger(CreditInvoice.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    private static void close(Connection resource) {
        if (resource != null) {
            try {
                resource.close();
            } catch (SQLException ex) {
                Logger.getLogger(CreditInvoice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
