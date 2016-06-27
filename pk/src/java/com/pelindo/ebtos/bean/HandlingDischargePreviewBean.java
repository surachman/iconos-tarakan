/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.bean;

import com.pelindo.ebtos.ejb.facade.remote.ReceivingServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceContDischargeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceVesselFacadeRemote;
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
@ManagedBean(name="handlingDischargePreviewBean")
@ViewScoped
public class HandlingDischargePreviewBean implements Serializable{

    protected ServiceVesselFacadeRemote serviceVesselFacadeRemote = lookupServiceVesselFacadeRemote();
    protected ServiceContDischargeFacadeRemote serviceContDischargeFacadeRemote = lookupServiceContDischargeFacadeRemote();

    private Object[] serviceVessel;
    private List<Object[]> serviceVessels;
    private List<Object[]> handlingDischargePreviews;
    protected String noPPKB;
    protected String mode;


    /** Creates a new instance of DischargeMonitoringBean */
    public HandlingDischargePreviewBean() {
//        serviceVessels = serviceVesselFacadeRemote.findServiceVesselsServiced();
        serviceVessels = serviceVesselFacadeRemote.findServiceVesselsServicedServicingDischarge();
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
        handlingDischargePreviews = serviceContDischargeFacadeRemote.findHandlingDischargeMonitoring(noPPKB);
        for (int i = 0; i < handlingDischargePreviews.size(); i++) {
            handlingDischargePreviews.get(i)[0] =(Integer)(i+1);
        }
    }

    public void handleDownload(ActionEvent event){
        boolean print = false;
        if (handlingDischargePreviews != null){
            print = true;
        }
        mode = "handlingdischarge";
        RequestContext context = RequestContext.getCurrentInstance();
        context.addCallbackParam("doPrint", print); //harus ada, buat ngeflag di izinkan ngeprint ato ngga
        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3("../../../../realisasi.pdf?" + "mode=" + mode + "&" + "no_ppkb=" + noPPKB));
    }

    public String getUrlHandlingDischarge(){
        if (handlingDischargePreviews == null)
            return "#";

        mode = "handlingdischarge";
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

    private ServiceContDischargeFacadeRemote lookupServiceContDischargeFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (ServiceContDischargeFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/ServiceContDischargeFacade!com.pelindo.ebtos.ejb.facade.remote.ServiceContDischargeFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

}
