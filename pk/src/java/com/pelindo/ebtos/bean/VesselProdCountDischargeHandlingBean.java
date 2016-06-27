/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.bean;

import com.pelindo.ebtos.ejb.facade.remote.RecapHandlingDischargeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceVesselFacadeRemote;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.apache.commons.lang3.StringEscapeUtils;
import org.primefaces.context.RequestContext;

/**
 *
 * @author dycode-java
 */
@ManagedBean(name="vesselProdCountDischargeHandlingBean")
@ViewScoped
public class VesselProdCountDischargeHandlingBean implements Serializable{

    protected ServiceVesselFacadeRemote serviceVesselFacadeRemote = lookupServiceVesselFacadeRemote();
    protected RecapHandlingDischargeFacadeRemote recapHandlingDischargeFacadeRemote = lookupRecapHandlingDischargeFacadeRemote();

    private Object[] serviceVessel;
    private List<Object[]> serviceVessels = lookupServiceVesselFacadeRemote().findServiceVesselsServiced();
    protected String noPPKB;
    private Object[] handlings;

    protected String mode;    
    private Boolean action;

    /** Creates a new instance of VesselProdCountLoadHandlingBean */
    public VesselProdCountDischargeHandlingBean() {
        action = false;
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

    private RecapHandlingDischargeFacadeRemote lookupRecapHandlingDischargeFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (RecapHandlingDischargeFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/RecapHandlingDischargeFacade!com.pelindo.ebtos.ejb.facade.remote.RecapHandlingDischargeFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
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

    public void handleSelectNoPPKB(ActionEvent event){
        noPPKB = (String) event.getComponent().getAttributes().get("noPPKB");
        serviceVessel = serviceVesselFacadeRemote.findServiceVesselByPpkb(noPPKB);
        if (action) {
            handlings = recapHandlingDischargeFacadeRemote.countHandlingDischarge(noPPKB, 1);
        } else {
            handlings = recapHandlingDischargeFacadeRemote.countHandlingDischarge(noPPKB, 0);
        }
    }

    public void handleDownload(ActionEvent event){
        boolean print = false;
        if (handlings != null){
            print = true;
        }
        mode = "countdischarge";
        RequestContext context = RequestContext.getCurrentInstance();
        context.addCallbackParam("doPrint", print); //harus ada, buat ngeflag di izinkan ngeprint ato ngga
        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3("../../../../realisasi.pdf?"
                + "mode=" + mode + "&"
                + "include=" + action.toString() + "&"
                + "no_ppkb=" + noPPKB));
    }

    public String getUrlCountDischarge(){
        if (handlings == null)
            return "#";

        mode = "countdischarge";
	return FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/realisasi.pdf?"
                + "mode=" + mode + "&"
                + "include=" + action.toString() + "&"
                + "no_ppkb=" + noPPKB + "";
    }

    public String getUrlPrintDischarge(){
        if (handlings == null)
            return "#";

        mode = "countdischarge";
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
	return  context.getRequestScheme() + "://" + context.getRequestServerName() + ":" + context.getRequestServerPort() + context.getRequestContextPath() + "/realisasi.pdf?"
                + "mode=" + mode + "&"
                + "include=" + action.toString() + "&"
                + "no_ppkb=" + noPPKB + "";
    }

    /**
     * @return the handlings
     */
    public Object[] getHandlings() {
        return handlings;
    }

    public void onChangeAction(ValueChangeEvent event) {
        action=(Boolean) event.getNewValue();
        if (action) {
            handlings = recapHandlingDischargeFacadeRemote.countHandlingDischarge(noPPKB, 1);
        } else {
            handlings = recapHandlingDischargeFacadeRemote.countHandlingDischarge(noPPKB, 0);
        }
    }

    /**
     * @return the action
     */
    public Boolean getAction() {
        return action;
    }

    /**
     * @param action the action to set
     */
    public void setAction(Boolean action) {
        this.action = action;
    }   

}
