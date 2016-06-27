/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.bean;

import com.pelindo.ebtos.ejb.facade.remote.ServiceVesselFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.UcDeliveryServiceFacadeRemote;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
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
@ManagedBean(name = "dischargeRecapUcBean")
@ViewScoped
public class DischargeRecapUcBean implements Serializable {

    private Object[] serviceVessel;
    private List<Object[]> serviceVessels = lookupServiceVesselFacadeRemote().findServiceVesselStatusServed();
    private List<Object[]> dischargeUcRecaps;
    protected String noPPKB;
    protected String mode;
    private BigDecimal totalCharge = BigDecimal.ZERO;
    private String language = null;
    private String country = null;

    /** Creates a new instance of DischargeRecapUcBean */
    public DischargeRecapUcBean() {
        this.language = "id";
        this.country = "ID";
        serviceVessel = new Object[4];
    }

    public void handleSelectNoPPKB(ActionEvent event) {
        noPPKB = (String) event.getComponent().getAttributes().get("noPPKB");
        setServiceVessel(lookupServiceVesselFacadeRemote().findServiceVesselByPpkb(noPPKB));
        dischargeUcRecaps = lookupUcDeliveryServiceFacadeRemote().findDischargeRecapUc(noPPKB);
        BigDecimal t = BigDecimal.ZERO;
        for (Object[] o : dischargeUcRecaps) {
            this.language = (String) (o[15]);
            this.country = (String) (o[16]);
            if (o[8] == null) {
                o[8] = BigDecimal.valueOf(0.00);
            }
            if (o[12] == null) {
                o[12] = BigDecimal.valueOf(0.00);
            }
            t = t.add((BigDecimal) o[8]).add((BigDecimal) o[9]).add((BigDecimal) o[10]).add((BigDecimal) o[11]).add((BigDecimal) o[12]);
        }
        totalCharge = t;
    }

    public String getFormattedTotal() {
        String local = null;
        Locale trLocale = new Locale(language, country);
        DecimalFormat decimalFormat = (DecimalFormat) DecimalFormat.getCurrencyInstance(trLocale);
        return local = decimalFormat.format(totalCharge).toString();
    }

    public void handleDownload(ActionEvent event){
        boolean print = false;
        if (dischargeUcRecaps != null){
            print = true;
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.addCallbackParam("doPrint", print); //harus ada, buat ngeflag di izinkan ngeprint ato ngga
        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3("../../../../../discharge.pdf?"
                + "no_ppkb=" + noPPKB + "&"
                + "total=" + String.valueOf(totalCharge) + "&"
                + "mode=" + "dischargeUcRecap"));
    }

    public String getUrl() {
        if (dischargeUcRecaps == null) {
            return "#";
        }
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        return context.getRequestScheme() + "://" + context.getRequestServerName() + ":" + context.getRequestServerPort() + context.getRequestContextPath() + "/discharge.pdf?"
                + "no_ppkb=" + noPPKB + "&"
                + "total=" + String.valueOf(totalCharge) + "&"
                + "mode=" + "dischargeUcRecap" + "";
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<Object[]> getDischargeUcRecaps() {
        return dischargeUcRecaps;
    }

    public void setDischargeUcRecaps(List<Object[]> dischargeUcRecaps) {
        this.dischargeUcRecaps = dischargeUcRecaps;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getNoPPKB() {
        return noPPKB;
    }

    public void setNoPPKB(String noPPKB) {
        this.noPPKB = noPPKB;
    }

    public Object[] getServiceVessel() {
        return serviceVessel;
    }

    public void setServiceVessel(Object[] serviceVessel) {
        this.serviceVessel = serviceVessel;
    }

    public List<Object[]> getServiceVessels() {
        return serviceVessels;
    }

    public void setServiceVessels(List<Object[]> serviceVessels) {
        this.serviceVessels = serviceVessels;
    }

    public BigDecimal getTotalCharge() {
        return totalCharge;
    }

    public void setTotalCharge(BigDecimal totalCharge) {
        this.totalCharge = totalCharge;
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

    private UcDeliveryServiceFacadeRemote lookupUcDeliveryServiceFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (UcDeliveryServiceFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/UcDeliveryServiceFacade!com.pelindo.ebtos.ejb.facade.remote.UcDeliveryServiceFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
