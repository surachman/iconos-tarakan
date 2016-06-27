/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.bean;

import com.pelindo.ebtos.ejb.facade.remote.ServiceContTranshipmentFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceVesselFacadeRemote;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.apache.commons.lang3.StringEscapeUtils;
import org.primefaces.context.RequestContext;

/**
 *
 * @author dycoder
 */
@Named(value="transhipmentBean")
@ConversationScoped
public class TranshipmentBean implements Serializable{
    ServiceContTranshipmentFacadeRemote serviceContTranshipmentFacade = lookupServiceContTranshipmentFacadeRemote();
    ServiceVesselFacadeRemote serviceVesselFacade = lookupServiceVesselFacadeRemote();
    @Inject Conversation con;

    private List<Object[]> serviceVessels = lookupServiceVesselFacadeRemote().findServiceVesselsServiced();
    private List<Object[]> serviceContTranshipments;

    private Object[] serviceVessel;
    private String no_ppkb;
    private String mode;

    /** Creates a new instance of TranshipmentBean */
    public TranshipmentBean() {
    }

    @PostConstruct
    public void start(){
        con.begin();
    }

    public void handleSelectNoPpkb(ActionEvent event){
        System.out.print("select vessel");
        no_ppkb = (String) event.getComponent().getAttributes().get("noPpkb");
        setServiceVessel(serviceVesselFacade.findServiceVesselByPpkb(no_ppkb));
        serviceContTranshipments = serviceContTranshipmentFacade.findRekap(no_ppkb);
        for (int i = 0; i < serviceContTranshipments.size(); i++) {
            serviceContTranshipments.get(i)[0] =(Integer)(i+1);
        }
    }

    public void handleDownload(ActionEvent event){
        boolean print = false;
        if (serviceContTranshipments != null){
            print = true;
        }
        mode = "transRekap";
        RequestContext context = RequestContext.getCurrentInstance();
        context.addCallbackParam("doPrint", print); //harus ada, buat ngeflag di izinkan ngeprint ato ngga
        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3("../../../../../discharge.pdf?"
                + "mode=" + mode + "&"
                + "no_ppkb=" + no_ppkb));
    }

    public String getUrl(){
        if (serviceContTranshipments == null)
            return "#";

        mode = "transRekap";
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
	return  context.getRequestScheme() + "://" + context.getRequestServerName() + ":" + context.getRequestServerPort() + context.getRequestContextPath() + "/discharge.pdf?"
                + "mode=" + mode + "&"
                + "no_ppkb=" + no_ppkb + "";
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

    private ServiceContTranshipmentFacadeRemote lookupServiceContTranshipmentFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (ServiceContTranshipmentFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/ServiceContTranshipmentFacade!com.pelindo.ebtos.ejb.facade.remote.ServiceContTranshipmentFacadeRemote");
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
     * @return the serviceContTranshipments
     */
    public List<Object[]> getServiceContTranshipments() {
        return serviceContTranshipments;
    }

    /**
     * @param serviceContTranshipments the serviceContTranshipments to set
     */
    public void setServiceContTranshipments(List<Object[]> serviceContTranshipments) {
        this.serviceContTranshipments = serviceContTranshipments;
    }

}
