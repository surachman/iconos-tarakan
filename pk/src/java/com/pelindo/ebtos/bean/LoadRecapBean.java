/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.bean;

import com.pelindo.ebtos.ejb.facade.remote.PlanningVesselFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.RecapPenumpukanLoadFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.RecapPenumpukanTransloadFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceReceivingFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceVesselFacadeRemote;
import com.pelindo.ebtos.model.db.RecapPenumpukanTransload;
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
@ManagedBean(name = "loadRecapBean")
@ViewScoped
public class LoadRecapBean implements Serializable {

    protected ServiceVesselFacadeRemote serviceVesselFacadeRemote = lookupServiceVesselFacadeRemote();
    protected ServiceReceivingFacadeRemote receivingServiceFacadeRemote = lookupServiceReceivingFacadeRemote();
    protected PlanningVesselFacadeRemote planningVesselFacadeRemote = lookupPlanningVesselFacadeRemote();
    protected RecapPenumpukanLoadFacadeRemote recapPenumpukanLoadFacadeRemote = lookupRecapPenumpukanLoadFacadeRemote();
    protected RecapPenumpukanTransloadFacadeRemote recapPenumpukanTransloadFacadeRemote = lookupRecapPenumpukanTransloadFacadeRemote();
    private Object[] serviceVessel;    
    private List<Object[]> serviceVessels;
    private List<Object[]> loadRecaps;
    protected String noPPKB;
    protected String mode;
    private BigDecimal totalCharge = BigDecimal.ZERO;
    private RecapPenumpukanTransload recapPenumpukanTransload;
    private String language = null;
    private String country = null;

    /** Creates a new instance of LoadRecapBean */
    public LoadRecapBean() {
        serviceVessels = serviceVesselFacadeRemote.findServiceVesselStatusServed();
        recapPenumpukanTransload = new RecapPenumpukanTransload();
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
     * @return the loadRecaps
     */
    public List<Object[]> getLoadRecaps() {
        return loadRecaps;
    }

    public void handleSelectNoPPKB(ActionEvent event) {
        String currency=null;
        noPPKB = (String) event.getComponent().getAttributes().get("noPPKB");
        serviceVessel = serviceVesselFacadeRemote.findServiceVesselByPpkb(noPPKB);
        loadRecaps = receivingServiceFacadeRemote.findLoadRecapsByPPKB(noPPKB);
        totalCharge = BigDecimal.ZERO;
        for (int i = 0; i < loadRecaps.size(); i++) {
            loadRecaps.get(i)[0] = (Integer) (i + 1);
            if (loadRecaps.get(i)[12] == null) {
                loadRecaps.get(i)[12] = BigDecimal.ZERO;
            }
            BigDecimal charge = (BigDecimal) loadRecaps.get(i)[12];
            totalCharge = totalCharge.add(charge);
            this.language = (String) loadRecaps.get(i)[14];
            this.country = (String) loadRecaps.get(i)[15];
        }
        String currCode = planningVesselFacadeRemote.findCurrCode(noPPKB);
        if (currCode.equalsIgnoreCase("d")) {
            currency="IDR";
        } else {
            currency="USD";
        }
        System.out.println("LOAD" + noPPKB);
        Integer id = recapPenumpukanTransloadFacadeRemote.findRecapPenumpukanTransloadByPpkb("LOAD", noPPKB);

        if (id != 0) {
            System.out.println("masuk edit");            
            setRecapPenumpukanTransload(recapPenumpukanTransloadFacadeRemote.find(id));
            getRecapPenumpukanTransload().setCharge(totalCharge);
            getRecapPenumpukanTransload().setNoPpkb(noPPKB);
            getRecapPenumpukanTransload().setOperation("LOAD");
            getRecapPenumpukanTransload().setCurrCode(currency);
            recapPenumpukanTransloadFacadeRemote.edit(recapPenumpukanTransload);
        } else {
            System.out.println("masuk create");
            getRecapPenumpukanTransload().setCharge(totalCharge);
            getRecapPenumpukanTransload().setNoPpkb(noPPKB);
            getRecapPenumpukanTransload().setOperation("LOAD");
            getRecapPenumpukanTransload().setCurrCode(currency);
            recapPenumpukanTransloadFacadeRemote.create(recapPenumpukanTransload);
        }
    }

     public void handleDownload(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        context.addCallbackParam("doPrint", true); //harus ada, buat ngeflag di izinkan ngeprint ato ngga
        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3("../../../../../recap.pdf?mode=" + "penumpukan" + "&no_ppkb=" + noPPKB + "&total_charge=" + totalCharge.toString()));
    }

    public String getUrlPenumpukan() {
        if (loadRecaps == null) {
            return "#";
        }

        mode = "penumpukan";
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

    private PlanningVesselFacadeRemote lookupPlanningVesselFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (PlanningVesselFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/PlanningVesselFacade!com.pelindo.ebtos.ejb.facade.remote.PlanningVesselFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private RecapPenumpukanTransloadFacadeRemote lookupRecapPenumpukanTransloadFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (RecapPenumpukanTransloadFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/RecapPenumpukanTransloadFacade!com.pelindo.ebtos.ejb.facade.remote.RecapPenumpukanTransloadFacadeRemote");
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

    private RecapPenumpukanLoadFacadeRemote lookupRecapPenumpukanLoadFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (RecapPenumpukanLoadFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/RecapPenumpukanLoadFacade!com.pelindo.ebtos.ejb.facade.remote.RecapPenumpukanLoadFacadeRemote");
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
     * @return the recapPenumpukanTransload
     */
    public RecapPenumpukanTransload getRecapPenumpukanTransload() {
        return recapPenumpukanTransload;
    }

    /**
     * @param recapPenumpukanTransload the recapPenumpukanTransload to set
     */
    public void setRecapPenumpukanTransload(RecapPenumpukanTransload recapPenumpukanTransload) {
        this.recapPenumpukanTransload = recapPenumpukanTransload;
    }

    public String getFormattedTotal() {
        String local = null;
        Locale trLocale = new Locale(language,country);
        DecimalFormat decimalFormat = (DecimalFormat) DecimalFormat.getCurrencyInstance(trLocale);
        return local = decimalFormat.format(totalCharge).toString();
    }
}
