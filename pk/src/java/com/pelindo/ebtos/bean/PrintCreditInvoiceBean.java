/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.bean;

import com.pelindo.ebtos.ejb.facade.IDGenerateFacade;
import com.pelindo.ebtos.ejb.facade.remote.AngsurServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.BehandleServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.CancelLoadingServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.DeliveryBarangServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.DeliveryServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.IDGeneratorFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.InvoiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MPaymentTypeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PenumpukanSusulanServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PerhitunganLiftBarangFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PluggingReeferServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ReceivingServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ReeferDischargeServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ReeferLoadServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.StrippingServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.StuffingServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.UcDeliveryServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.UcPenumpukanSusulanServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.UcReceivingServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.UncontainerizedServiceFacadeRemote;
import com.pelindo.ebtos.model.db.Invoice;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.apache.commons.lang3.StringEscapeUtils;
import org.primefaces.context.RequestContext;

/**
 *
 * @author USER
 */
@ManagedBean(name = "printCreditInvoiceBean")
@ViewScoped
public class PrintCreditInvoiceBean implements Serializable {

    private final static String LOAD = "SCHDL";
    private final static String DISC = "SCHDD";
    InvoiceFacadeRemote invoiceFacade = lookupInvoiceFacadeRemote();
    PerhitunganLiftBarangFacadeRemote perhitunganLiftBarangFacade = lookupPerhitunganLiftBarangFacadeRemote();
    UcReceivingServiceFacadeRemote ucReceivingServiceFacade = lookupUcReceivingServiceFacadeRemote();
    AngsurServiceFacadeRemote angsurServiceFacadeRemote = lookupAngsurServiceFacadeRemote();
    DeliveryBarangServiceFacadeRemote deliveryBarangServiceFacade = lookupDeliveryBarangServiceFacadeRemote();
    CancelLoadingServiceFacadeRemote cancelLoadingServiceFacadeRemote = lookupCancelLoadingServiceFacadeRemote();
    BehandleServiceFacadeRemote behandleServiceFacade = lookupBehandleServiceFacadeRemote();
    ReceivingServiceFacadeRemote receivingServiceFacadeRemote = lookupReceivingServiceFacadeRemote();
    StrippingServiceFacadeRemote strippingServiceFacade = lookupStrippingServiceFacadeRemote();
    StuffingServiceFacadeRemote stuffingServiceFacade = lookupStuffingServiceFacadeRemote();
    ReeferDischargeServiceFacadeRemote reeferDischargeServiceFacade = lookupReeferDischargeServiceFacadeRemote();
    ReeferLoadServiceFacadeRemote reeferLoadServiceFacade = lookupReeferLoadServiceFacadeRemote();
    PluggingReeferServiceFacadeRemote pluggingReeferServiceFacade = lookupPluggingReeferServiceFacadeRemote();
    UncontainerizedServiceFacadeRemote uncontainerizedServiceFacade = lookupUncontainerizedServiceFacadeRemote();
    DeliveryServiceFacadeRemote deliveryServiceFacade = lookupDeliveryServiceFacadeRemote();
    PenumpukanSusulanServiceFacadeRemote penumpukanSusulanServiceFacade = lookupPenumpukanSusulanServiceFacadeRemote();
    UcPenumpukanSusulanServiceFacadeRemote ucPenumpukanSusulanServiceFacade = lookupUcPenumpukanSusulanServiceFacadeRemote();
    UcDeliveryServiceFacadeRemote ucDeliveryServiceFacade = lookupUcDeliveryServiceFacadeRemote();
    @EJB
    private IDGeneratorFacadeRemote iDGeneratorFacadeRemote;
    private List<Object[]> invoiceCredits;
    private IDGenerateFacade iDGenerateFacade;
    private Invoice invoice = new Invoice();
    private String detail = "true";

    /** Creates a new instance of PrintCreditInvoiceBean */
    public PrintCreditInvoiceBean() {
        invoiceCredits = invoiceFacade.findInvoiceCredit();
        iDGenerateFacade = new IDGenerateFacade();
    }

    public List<Object[]> getInvoiceCredits() {
        return invoiceCredits;
    }

    public void setInvoiceCredits(List<Object[]> invoiceCredits) {
        this.invoiceCredits = invoiceCredits;
    }

    private String isPrintDetail(String tipe, String no_reg) {
        List<Object[]> listDetail = new ArrayList<Object[]>();
        if (tipe.equalsIgnoreCase("SC002")) {
            listDetail = deliveryServiceFacade.findPerhitungan(no_reg);
        } else if (tipe.equalsIgnoreCase("SC001")) {
            listDetail = receivingServiceFacadeRemote.findPerhitungan(no_reg);
        } else if (tipe.equalsIgnoreCase("SC015")) {
            listDetail = reeferDischargeServiceFacade.findPerhitungan(no_reg);
        } else if (tipe.equalsIgnoreCase("SC016")) {
            listDetail = reeferLoadServiceFacade.findPerhitungan(no_reg);
        } else if (tipe.equalsIgnoreCase("SC003")) {
            listDetail = pluggingReeferServiceFacade.findPerhitungan(no_reg);
        } else if (tipe.equalsIgnoreCase("SC006")) {
            listDetail = strippingServiceFacade.findPerhitungan(no_reg);
        } else if (tipe.equalsIgnoreCase("SC007")) {
            listDetail = stuffingServiceFacade.findPerhitungan(no_reg);
        } else if (tipe.equalsIgnoreCase("SC009")) {
            listDetail = penumpukanSusulanServiceFacade.findPerhitungan(no_reg);
        } else if (tipe.equalsIgnoreCase("SC017")) {
            listDetail = angsurServiceFacadeRemote.findPerhitungan(no_reg);
        } else if (tipe.equalsIgnoreCase("SC004")) {
            listDetail = behandleServiceFacade.findPerhitungan(no_reg);
        } else if (tipe.equalsIgnoreCase("SC014")) {
            listDetail = ucDeliveryServiceFacade.findPerhitungan(no_reg);
        } else if (tipe.equalsIgnoreCase("SC013")) {
            listDetail = ucReceivingServiceFacade.findByUcReceivingPerhitungan(no_reg);
        } else if (tipe.equalsIgnoreCase("SC021")) {
            listDetail = ucPenumpukanSusulanServiceFacade.findPerhitungan(no_reg);
        } else if (tipe.equalsIgnoreCase("SC005")) {
            listDetail = cancelLoadingServiceFacadeRemote.findCancelLoadingServiceByNoreg(no_reg);
        } else if (tipe.equalsIgnoreCase("SC018")) {
            listDetail = perhitunganLiftBarangFacade.findPerhitunganLiftBarangByList(no_reg);
        } else if (tipe.equalsIgnoreCase("SC019")) {
            listDetail = deliveryBarangServiceFacade.findPerhitungan(no_reg);
        }

        if ((listDetail.size() <= 10) || tipe.equalsIgnoreCase("SCNMA")) {
            detail = "true";
        } else {
            detail = "false";
        }

        return detail;
    }

    public void doFirstPrint(ActionEvent event) {
        String no_reg = (String) event.getComponent().getAttributes().get("noReg");
        BigDecimal to = (BigDecimal) event.getComponent().getAttributes().get("to");
        String curr = (String) event.getComponent().getAttributes().get("curr");
        String tipe = (String) event.getComponent().getAttributes().get("tipe");
        String username = (String) event.getComponent().getAttributes().get("username");
        String hasDetail = isPrintDetail(tipe, no_reg);
        String no_ppkb = (String) event.getComponent().getAttributes().get("noPpkb");

        RequestContext context = RequestContext.getCurrentInstance();

        if (tipe.equalsIgnoreCase(LOAD) || tipe.equalsIgnoreCase(DISC)) {
            String momod = "HandlingDischargeInvoice";
            if (tipe.equalsIgnoreCase(LOAD)) {
                momod = "HandlingLoadInvoice";
            }

            context.addCallbackParam("doPrint", true); //harus ada, buat ngeflag di izinkan ngeprint ato ngga
            context.addCallbackParam("url", StringEscapeUtils.escapeHtml3("./../../../../ageninvoice.pdf?"
                    + "no_ppkb=" + no_ppkb + "&"
                    + "mode=" + momod + "&"
                    + "username=" + username + "&"
                    + "no_reg=" + no_reg + "&"
                    + "curr=" + curr + "&"
                    + "total=" + to.toBigInteger().toString()));

        } else {
            context.addCallbackParam("doPrint", true); //harus ada, buat ngeflag di izinkan ngeprint ato ngga
            context.addCallbackParam("url", StringEscapeUtils.escapeHtml3("./../../../../CreditInvoice.pdf?no_reg=" + no_reg + "&to=" + to.toBigInteger().toString() + "&curr=" + curr + "&tipe=" + tipe + "&username=" + username + "&detail=" + hasDetail));
        }

        //find invoice by reg number

        invoice = invoiceFacade.find(no_reg);
        invoice.setNoInvoice(iDGenerateFacade.generateInvoiceID());
        invoice.setNoFakturPajak(iDGenerateFacade.generateFakturPajakID());
        invoiceFacade.edit(invoice);

        invoiceCredits = invoiceFacade.findInvoiceCredit();
    }

    public void doReprint(ActionEvent event) {
        String no_reg = (String) event.getComponent().getAttributes().get("noReg");
        BigDecimal to = (BigDecimal) event.getComponent().getAttributes().get("to");
        String curr = (String) event.getComponent().getAttributes().get("curr");
        String tipe = (String) event.getComponent().getAttributes().get("tipe");
        String username = (String) event.getComponent().getAttributes().get("username");
        String hasDetail = isPrintDetail(tipe, no_reg);
        String no_ppkb = (String) event.getComponent().getAttributes().get("noPpkb");

        RequestContext context = RequestContext.getCurrentInstance();
        if (tipe.equalsIgnoreCase(LOAD) || tipe.equalsIgnoreCase(DISC)) {
            String momod = "HandlingDischargeInvoice";
            if (tipe.equalsIgnoreCase(LOAD)) {
                momod = "HandlingLoadInvoice";
            }
            context.addCallbackParam("doPrint", true); //harus ada, buat ngeflag di izinkan ngeprint ato ngga
            context.addCallbackParam("url", StringEscapeUtils.escapeHtml3("./../../../../ageninvoice.pdf?"
                    + "no_ppkb=" + no_ppkb + "&"
                    + "mode=" + momod + "&"
                    + "username=" + username + "&"
                    + "no_reg=" + no_reg + "&"
                    + "curr=" + curr + "&"
                    + "total=" + to.toBigInteger().toString()));

        } else {
            context.addCallbackParam("doPrint", true); //harus ada, buat ngeflag di izinkan ngeprint ato ngga
            context.addCallbackParam("url", StringEscapeUtils.escapeHtml3("./../../../../CreditInvoice.pdf?no_reg=" + no_reg + "&to=" + to.toBigInteger().toString() + "&curr=" + curr + "&tipe=" + tipe + "&username=" + username + "&detail=" + hasDetail));
        }

    }

    private InvoiceFacadeRemote lookupInvoiceFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (InvoiceFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/InvoiceFacade!com.pelindo.ebtos.ejb.facade.remote.InvoiceFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private PenumpukanSusulanServiceFacadeRemote lookupPenumpukanSusulanServiceFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (PenumpukanSusulanServiceFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/PenumpukanSusulanServiceFacade!com.pelindo.ebtos.ejb.facade.remote.PenumpukanSusulanServiceFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private UcPenumpukanSusulanServiceFacadeRemote lookupUcPenumpukanSusulanServiceFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (UcPenumpukanSusulanServiceFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/UcPenumpukanSusulanServiceFacade!com.pelindo.ebtos.ejb.facade.remote.UcPenumpukanSusulanServiceFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private UcDeliveryServiceFacadeRemote lookupUcDeliveryServiceFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (UcDeliveryServiceFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/UcDeliveryServiceFacade!com.pelindo.ebtos.ejb.facade.remote.UcDeliveryServiceFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private UncontainerizedServiceFacadeRemote lookupUncontainerizedServiceFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (UncontainerizedServiceFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/UncontainerizedServiceFacade!com.pelindo.ebtos.ejb.facade.remote.UncontainerizedServiceFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private ReeferLoadServiceFacadeRemote lookupReeferLoadServiceFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (ReeferLoadServiceFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/ReeferLoadServiceFacade!com.pelindo.ebtos.ejb.facade.remote.ReeferLoadServiceFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private ReeferDischargeServiceFacadeRemote lookupReeferDischargeServiceFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (ReeferDischargeServiceFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/ReeferDischargeServiceFacade!com.pelindo.ebtos.ejb.facade.remote.ReeferDischargeServiceFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private StuffingServiceFacadeRemote lookupStuffingServiceFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (StuffingServiceFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/StuffingServiceFacade!com.pelindo.ebtos.ejb.facade.remote.StuffingServiceFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private PerhitunganLiftBarangFacadeRemote lookupPerhitunganLiftBarangFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (PerhitunganLiftBarangFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/PerhitunganLiftBarangFacade!com.pelindo.ebtos.ejb.facade.remote.PerhitunganLiftBarangFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private UcReceivingServiceFacadeRemote lookupUcReceivingServiceFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (UcReceivingServiceFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/UcReceivingServiceFacade!com.pelindo.ebtos.ejb.facade.remote.UcReceivingServiceFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private AngsurServiceFacadeRemote lookupAngsurServiceFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (AngsurServiceFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/AngsurServiceFacade!com.pelindo.ebtos.ejb.facade.remote.AngsurServiceFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private DeliveryBarangServiceFacadeRemote lookupDeliveryBarangServiceFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (DeliveryBarangServiceFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/DeliveryBarangServiceFacade!com.pelindo.ebtos.ejb.facade.remote.DeliveryBarangServiceFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private CancelLoadingServiceFacadeRemote lookupCancelLoadingServiceFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (CancelLoadingServiceFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/CancelLoadingServiceFacade!com.pelindo.ebtos.ejb.facade.remote.CancelLoadingServiceFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private BehandleServiceFacadeRemote lookupBehandleServiceFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (BehandleServiceFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/BehandleServiceFacade!com.pelindo.ebtos.ejb.facade.remote.BehandleServiceFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private ReceivingServiceFacadeRemote lookupReceivingServiceFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (ReceivingServiceFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/ReceivingServiceFacade!com.pelindo.ebtos.ejb.facade.remote.ReceivingServiceFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private StrippingServiceFacadeRemote lookupStrippingServiceFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (StrippingServiceFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/StrippingServiceFacade!com.pelindo.ebtos.ejb.facade.remote.StrippingServiceFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private PluggingReeferServiceFacadeRemote lookupPluggingReeferServiceFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (PluggingReeferServiceFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/PluggingReeferServiceFacade!com.pelindo.ebtos.ejb.facade.remote.PluggingReeferServiceFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private MPaymentTypeFacadeRemote lookupMPaymentTypeFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (MPaymentTypeFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/MPaymentTypeFacade!com.pelindo.ebtos.ejb.facade.remote.MPaymentTypeFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private DeliveryServiceFacadeRemote lookupDeliveryServiceFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (DeliveryServiceFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/DeliveryServiceFacade!com.pelindo.ebtos.ejb.facade.remote.DeliveryServiceFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
