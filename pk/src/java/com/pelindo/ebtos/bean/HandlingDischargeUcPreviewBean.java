/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.bean;

import com.pelindo.ebtos.ejb.facade.remote.ServiceVesselFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.UncontainerizedServiceFacadeRemote;
import java.io.Serializable;
import java.util.List;
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
 * @author USER
 */
@ManagedBean(name="handlingDischargeUcPreviewBean")
@ViewScoped
public class HandlingDischargeUcPreviewBean implements Serializable{

    protected ServiceVesselFacadeRemote serviceVesselFacadeRemote = lookupServiceVesselFacadeRemote();
    //protected ServiceContDischargeFacadeRemote serviceContDischargeFacadeRemote = lookupServiceContDischargeFacadeRemote();
    protected UncontainerizedServiceFacadeRemote uncontainerizedServiceFacade = lookupUncontainerizedServiceFacadeRemote();

    private Object[] serviceVessel;
    private List<Object[]> serviceVessels;
    private List<Object[]> handlingDischargePreviews;
    protected String noPPKB;
    protected String mode;


    /** Creates a new instance of DischargeMonitoringBean */
    public HandlingDischargeUcPreviewBean() {
        serviceVessels = serviceVesselFacadeRemote.findServiceVesselsServiced();
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
     * @return the dischargeMonitoring
     */
    public List<Object[]> getHandlingDischargePreviews() {
        return handlingDischargePreviews;
    }

    public void handleSelectNoPPKB(ActionEvent event){
        noPPKB = (String) event.getComponent().getAttributes().get("noPPKB");
        serviceVessel = serviceVesselFacadeRemote.findServiceVesselByPpkb(noPPKB);
        //handlingDischargePreviews = serviceContDischargeFacadeRemote.findHandlingDischargeMonitoring(noPPKB);
        handlingDischargePreviews = uncontainerizedServiceFacade.findHandlingDischarge(noPPKB);
        //for (int i = 0; i < handlingDischargePreviews.size(); i++) {
        //    handlingDischargePreviews.get(i)[0] =(Integer)(i+1);
        //}
    }

    public void handleDownload(ActionEvent event){
        boolean print = false;
        if (handlingDischargePreviews != null){
            print = true;
        }
        mode = "handlingdischargeuc";
        RequestContext context = RequestContext.getCurrentInstance();
        context.addCallbackParam("doPrint", print); //harus ada, buat ngeflag di izinkan ngeprint ato ngga
        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3("../../../../realisasi.pdf?" + "mode=" + mode + "&" + "no_ppkb=" + noPPKB));
    }

    public String getUrlHandlingDischarge(){
        if (handlingDischargePreviews == null)
            return "#";

        mode = "handlingdischargeuc";
	return FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/realisasi.pdf?"
                + "mode=" + mode + "&" + "no_ppkb=" + noPPKB + "";
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

    private UncontainerizedServiceFacadeRemote lookupUncontainerizedServiceFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (UncontainerizedServiceFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/UncontainerizedServiceFacade!com.pelindo.ebtos.ejb.facade.remote.UncontainerizedServiceFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

}
