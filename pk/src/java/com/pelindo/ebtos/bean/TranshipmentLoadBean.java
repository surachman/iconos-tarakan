/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.bean;

import com.pelindo.ebtos.ejb.facade.remote.ServiceContLoadFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceVesselFacadeRemote;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.apache.commons.lang3.StringEscapeUtils;
import org.primefaces.context.RequestContext;

/**
 *
 * @author dycoder
 */
@ManagedBean(name = "transhipmentLoadBean")
@ViewScoped
public class TranshipmentLoadBean implements Serializable {

    ServiceContLoadFacadeRemote serviceContLoadFacade = lookupServiceContLoadFacadeRemote();
    ServiceVesselFacadeRemote serviceVesselFacade = lookupServiceVesselFacadeRemote();
    private List<Object[]> serviceVessels = lookupServiceVesselFacadeRemote().findServiceVesselsServiced();
    private List<Object[]> serviceContLoads;
    private Object[] serviceVessel;
    private String no_ppkb;
    private String mode;

    /** Creates a new instance of TranshipmentLoadBean */
    public TranshipmentLoadBean() {
        serviceVessel = new Object[4];
    }

    public void handleSelectNoPpkb(ActionEvent event) {
        no_ppkb = (String) event.getComponent().getAttributes().get("noPpkb");
        setServiceVessel(serviceVesselFacade.findServiceVesselByPpkb(no_ppkb));
        serviceContLoads = serviceContLoadFacade.findServiceContLoadByRekap(no_ppkb);
        for (int i = 0; i < serviceContLoads.size(); i++) {
            serviceContLoads.get(i)[0] = (Integer) (i + 1);
        }
    }

    public void handleDownload(ActionEvent event){
        boolean print = false;
        if (serviceContLoads != null){
            print = true;
        }
        mode = "transLoadRekap";
        RequestContext context = RequestContext.getCurrentInstance();
        context.addCallbackParam("doPrint", print); //harus ada, buat ngeflag di izinkan ngeprint ato ngga
        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3("../../../../../transhipment.pdf?"
                + "mode=" + mode + "&"
                + "no_ppkb=" + no_ppkb));
    }

    public String getUrl() {
        if (serviceContLoads == null) {
            return "#";
        }

        mode = "transLoadRekap";
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        return context.getRequestScheme() + "://" + context.getRequestServerName() + ":" + context.getRequestServerPort() + context.getRequestContextPath() + "/transhipment.pdf?"
                + "mode=" + mode + "&"
                + "no_ppkb=" + no_ppkb + "";
    }

    private ServiceContLoadFacadeRemote lookupServiceContLoadFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (ServiceContLoadFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/ServiceContLoadFacade!com.pelindo.ebtos.ejb.facade.remote.ServiceContLoadFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
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

    /**
     * @return the serviceContLoads
     */
    public List<Object[]> getServiceContLoads() {
        return serviceContLoads;
    }

    /**
     * @param serviceContLoads the serviceContLoads to set
     */
    public void setServiceContLoads(List<Object[]> serviceContLoads) {
        this.serviceContLoads = serviceContLoads;
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
}
