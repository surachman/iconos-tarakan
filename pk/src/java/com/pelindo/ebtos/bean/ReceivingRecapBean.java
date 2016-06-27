/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.bean;

import com.pelindo.ebtos.ejb.facade.remote.PlanningReceivingAllocationFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningVesselFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.RecapReceivingFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ReceivingServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceReceivingFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceVesselFacadeRemote;
import com.pelindo.ebtos.model.db.RecapReceiving;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.apache.commons.lang3.StringEscapeUtils;
import org.primefaces.context.RequestContext;

/**
 *
 * @author dycode-java
 */
@ManagedBean(name = "receivingRecapBean")
@ViewScoped
public class ReceivingRecapBean implements Serializable {

    protected ServiceVesselFacadeRemote serviceVesselFacadeRemote = lookupServiceVesselFacadeRemote();
    protected ReceivingServiceFacadeRemote receivingServiceFacadeRemote = lookupReceivingServiceFacadeRemote();
    protected PlanningVesselFacadeRemote planningVesselFacadeRemote = lookupPlanningVesselFacadeRemote();
    protected ServiceReceivingFacadeRemote serviceReceivingFacadeRemote = lookupServiceReceivingFacadeRemote();
    protected RecapReceivingFacadeRemote recapReceivingFacadeRemote = lookupRecapReceivingFacadeRemote();
    protected PlanningReceivingAllocationFacadeRemote planningReceivingAllocationFacadeRemote = lookupPlanningReceivingAllocationFacadeRemote();

    private Object[] serviceVessel;
    private List<Object[]> serviceVessels;
    private List<Object[]> receivingRecaps;
    protected String noPPKB;
    protected String mode;
    private BigDecimal totalCharge = BigDecimal.ZERO;
    private Integer booking;
    private Long jobSlipAmount;
    private Long realisasiJobSlip;
    private BigDecimal cancelCharge;
    private BigDecimal totalAllCharge;
    private String selisih;
    private String cancelDocument;
    private String totalChargeView;
    private String language = null;
    private String country = null;

    /** Creates a new instance of ReceivingRecapBean */
    public ReceivingRecapBean() {
        serviceVessels = serviceVesselFacadeRemote.findServiceVesselStatusServed();
        this.language = "id";
        this.country = "ID";
    }

    /**
     * @return the serviceVessel
     */
    public Object[] getServiceVessel() {
        return serviceVessel;
    }

    /**
     * @return the serviceVessels
     */
    public List<Object[]> getServiceVessels() {
        return serviceVessels;
    }

    /**
     * @return the receivingRecaps
     */
    public List<Object[]> getReceivingRecaps() {
        return receivingRecaps;
    }

    public void handleSelectNoPPKB(ActionEvent event) {
        noPPKB = (String) event.getComponent().getAttributes().get("noPPKB");
        serviceVessel = serviceVesselFacadeRemote.findServiceVesselByPpkb(noPPKB);
        receivingRecaps = receivingServiceFacadeRemote.findReceivingRecaps(noPPKB);
        totalCharge = BigDecimal.ZERO;

        for (int i = 0; i < receivingRecaps.size(); i++) {
            if (receivingRecaps.get(i)[12] == null) {
                receivingRecaps.get(i)[12] = BigDecimal.ZERO;
            }
            this.language = (String) receivingRecaps.get(i)[14];
            this.country = (String) receivingRecaps.get(i)[15];
            BigDecimal charge = (BigDecimal) receivingRecaps.get(i)[12];
            totalCharge = totalCharge.add(charge);
        }

        //booking = planningVesselFacadeRemote.findBooking(noPPKB);
        booking = planningReceivingAllocationFacadeRemote.findAllocationBooking(noPPKB);
        jobSlipAmount = receivingServiceFacadeRemote.findJobSlipAmount(noPPKB);
        realisasiJobSlip = serviceReceivingFacadeRemote.findRealisasiJobSlip(noPPKB);

        //hitung cancel charge
        Long x = booking.longValue() - jobSlipAmount;
        Long dendaX;
        if (x > 50) {
            dendaX = (x - 50) * Long.parseLong("50000");
        } else {
            dendaX = Long.parseLong("0");
        }

        Long y = jobSlipAmount - realisasiJobSlip;
        Long dendaY = y * Long.parseLong("100000");
        Long dendaXY = dendaX + dendaY;
        cancelCharge = BigDecimal.ZERO; //BigDecimal.valueOf(dendaXY);
        totalAllCharge = cancelCharge.add(totalCharge);

        //currCode
        String tipePelayaran = planningVesselFacadeRemote.findCurrCode(noPPKB);
        RecapReceiving recapReceiving = new RecapReceiving();
        recapReceiving.setReceivingCharge(totalCharge);
        recapReceiving.setNoPpkb(noPPKB);
        recapReceiving.setBooking(booking);
        recapReceiving.setJobslipAmount(jobSlipAmount.intValue());
        recapReceiving.setJobslipReal(realisasiJobSlip.intValue());
        recapReceiving.setCancelCharge(cancelCharge);
        recapReceiving.setTotalCharge(totalAllCharge);

        if (tipePelayaran.equalsIgnoreCase("d")) {
            recapReceiving.setCurrCode("IDR");
        } else {
            recapReceiving.setCurrCode("USD");
        }

        recapReceivingFacadeRemote.edit(recapReceiving);

        selisih = x.toString() + " * Rp.  50.000,- = Rp. 0";// + dendaX.toString();
        cancelDocument = y.toString() + " * Rp.  100.000,- = Rp. 0";// + dendaY.toString();
        totalChargeView = "Rp. " + totalCharge.toString();
    }

    public void handleDownload(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        context.addCallbackParam("doPrint", true); //harus ada, buat ngeflag di izinkan ngeprint ato ngga
        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3("../../../../recap.pdf?mode=" + "receiving" + "&no_ppkb=" + noPPKB + "&total_charge=" + totalCharge.toString()));
    }

    public String getUrlReceiving() {
        if (receivingRecaps == null) {
            return "#";
        }

        mode = "receiving";
        return FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/recap.pdf?"
                + "mode=" + mode + "&" + "no_ppkb=" + noPPKB + "&" + "total_charge=" + totalCharge.toString() + "";
    }

    private ServiceVesselFacadeRemote lookupServiceVesselFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (ServiceVesselFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/ServiceVesselFacade!com.pelindo.ebtos.ejb.facade.remote.ServiceVesselFacadeRemote");
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

    private PlanningVesselFacadeRemote lookupPlanningVesselFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (PlanningVesselFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/PlanningVesselFacade!com.pelindo.ebtos.ejb.facade.remote.PlanningVesselFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private ServiceReceivingFacadeRemote lookupServiceReceivingFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (ServiceReceivingFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/ServiceReceivingFacade!com.pelindo.ebtos.ejb.facade.remote.ServiceReceivingFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private RecapReceivingFacadeRemote lookupRecapReceivingFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (RecapReceivingFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/RecapReceivingFacade!com.pelindo.ebtos.ejb.facade.remote.RecapReceivingFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private PlanningReceivingAllocationFacadeRemote lookupPlanningReceivingAllocationFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (PlanningReceivingAllocationFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/PlanningReceivingAllocationFacade!com.pelindo.ebtos.ejb.facade.remote.PlanningReceivingAllocationFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    /**
     * @return the totalCharge
     */
    public BigDecimal getTotalCharge() {
        return totalCharge;
    }

    /**
     * @return the Booking
     */
    public Integer getBooking() {
        return booking;
    }

    /**
     * @return the jobSlipAmount
     */
    public Long getJobSlipAmount() {
        return jobSlipAmount;
    }

    /**
     * @return the realisasiJobSlip
     */
    public Long getRealisasiJobSlip() {
        return realisasiJobSlip;
    }

    /**
     * @return the cancelCharge
     */
    public BigDecimal getCancelCharge() {
        return cancelCharge;
    }

    /**
     * @return the totalAllCharge
     */
    public BigDecimal getTotalAllCharge() {
        return totalAllCharge;
    }

    /**
     * @return the selisih
     */
    public String getSelisih() {
        return selisih;
    }

    /**
     * @return the cancelDocument
     */
    public String getCancelDocument() {
        return cancelDocument;
    }

    /**
     * @return the totalChargeView
     */
    public String getTotalChargeView() {
        return totalChargeView;
    }

    public String getFormattedTotal() {
        String local = null;
        Locale trLocale = new Locale(language, country);
        DecimalFormat decimalFormat = (DecimalFormat) DecimalFormat.getCurrencyInstance(trLocale);
        return local = decimalFormat.format(totalCharge).toString();
    }
}
