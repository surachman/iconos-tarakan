/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.bean;

import com.pelindo.ebtos.ejb.facade.remote.ServiceContDischargeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceVesselFacadeRemote;
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
 * @author dycoder
 */
@ManagedBean(name="dischargeRecapBean")
@ViewScoped
public class DischargeRecapBean implements Serializable{

    protected ServiceVesselFacadeRemote serviceVesselFacadeRemote = lookupServiceVesselFacadeRemote();
    protected ServiceContDischargeFacadeRemote serviceContDischargeFacade = lookupServiceContDischargeFacadeRemote();

    private Object[] serviceVessel;
    private List<Object[]> serviceVessels = serviceVesselFacadeRemote.findServiceVesselStatusServed();
    private List<Object[]> dischargeRecaps;
    protected String noPPKB;
    protected String mode;
    private BigDecimal totalCharge = BigDecimal.ZERO;
    private String language = null;
    private String country = null;

    /** Creates a new instance of DischargeRecapBean */
    public DischargeRecapBean() {
        this.language = "id";
        this.country = "ID";
    }

    public void handleSelectNoPPKB(ActionEvent event){
        noPPKB = (String) event.getComponent().getAttributes().get("noPPKB");
        setServiceVessel(serviceVesselFacadeRemote.findServiceVesselByPpkb(noPPKB));
        dischargeRecaps = serviceContDischargeFacade.findRecapPenumpukan(noPPKB);
        totalCharge = BigDecimal.ZERO;
        for (int i = 0; i < dischargeRecaps.size(); i++) {
            dischargeRecaps.get(i)[0] =(Integer)(i+1);
            if (dischargeRecaps.get(i)[9] != null) {
                BigDecimal charge = (BigDecimal) dischargeRecaps.get(i)[9];
                totalCharge = totalCharge.add(charge);
            }
            this.language = (String) dischargeRecaps.get(i)[11];
            this.country = (String) dischargeRecaps.get(i)[12];
        }
    }

    public void handleDownload(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        context.addCallbackParam("doPrint", true); //harus ada, buat ngeflag di izinkan ngeprint ato ngga
        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3("../../../../../recap.pdf?mode=" + "penumpukanDisch" + "&no_ppkb=" + noPPKB + "&total_charge=" + totalCharge.toString()));
    }

    public String getUrlPenumpukan(){
        if (dischargeRecaps == null)
            return "#";

        mode = "penumpukanDisch";
	return FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/recap.pdf?"
                + "mode=" + mode + "&" + "no_ppkb=" + noPPKB + "&" + "total_charge=" + totalCharge + "";
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

    private ServiceContDischargeFacadeRemote lookupServiceContDischargeFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (ServiceContDischargeFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/ServiceContDischargeFacade!com.pelindo.ebtos.ejb.facade.remote.ServiceContDischargeFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    /**
     * @return the serviceVessels
     */
    public List<Object[]> getServiceVessels() {
        return serviceVessels;
    }

    /**
     * @param serviceVessels the serviceVessels to set
     */
    public void setServiceVessels(List<Object[]> serviceVessels) {
        this.serviceVessels = serviceVessels;
    }

    /**
     * @return the dischargeRecaps
     */
    public List<Object[]> getdischargeRecaps() {
        return dischargeRecaps;
    }

    /**
     * @param dischargeRecaps the dischargeRecaps to set
     */
    public void setdischargeRecaps(List<Object[]> dischargeRecaps) {
        this.dischargeRecaps = dischargeRecaps;
    }

    /**
     * @return the totalCharge
     */
    public BigDecimal getTotalCharge() {
        return totalCharge;
    }

    /**
     * @param totalCharge the totalCharge to set
     */
    public void setTotalCharge(BigDecimal totalCharge) {
        this.totalCharge = totalCharge;
    }

    /**
     * @return the serviceVessel
     */
    public Object[] getServiceVessel() {
        return serviceVessel;
    }

    /**
     * @param serviceVessel the serviceVessel to set
     */
    public void setServiceVessel(Object[] serviceVessel) {
        this.serviceVessel = serviceVessel;
    }

    public String getFormattedTotal() {
        String local = null;
        Locale trLocale = new Locale(language,country);
        DecimalFormat decimalFormat = (DecimalFormat) DecimalFormat.getCurrencyInstance(trLocale);
        return local = decimalFormat.format(totalCharge).toString();
    }

}
