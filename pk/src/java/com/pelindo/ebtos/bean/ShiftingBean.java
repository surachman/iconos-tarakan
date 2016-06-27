/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.bean;

import com.pelindo.ebtos.ejb.facade.remote.ServiceShiftingFacadeRemote;
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
@Named(value="shiftingBean")
@ConversationScoped
public class ShiftingBean implements Serializable{
    ServiceShiftingFacadeRemote serviceShiftingFacade = lookupServiceShiftingFacadeRemote();
    ServiceVesselFacadeRemote serviceVesselFacade = lookupServiceVesselFacadeRemote();
    @Inject Conversation con;

//    private List<Object[]> serviceVessels = lookupServiceVesselFacadeRemote().findServiceVesselsServiced();
//    Tarakan
    private List<Object[]> serviceVessels = lookupServiceVesselFacadeRemote().findServiceVesselsServicedServicingDischarge();
    private List<Object[]> serviceShiftings;

    private Object[] serviceVessel;
    private String operation;
    private String no_ppkb;
    private String mode;

    /** Creates a new instance of ShiftingBean */
    public ShiftingBean() {
        operation = "DISCHARGE";
    }

    public void handleSelectNoPpkb(ActionEvent event){
        System.out.print("select vessel");
        no_ppkb = (String) event.getComponent().getAttributes().get("noPpkb");
        setServiceVessel(serviceVesselFacade.findServiceVesselByPpkb(no_ppkb));
        setServiceShiftings(serviceShiftingFacade.findRekap(no_ppkb, operation));
        for (int i = 0; i < serviceShiftings.size(); i++) {
            serviceShiftings.get(i)[0] =(Integer)(i+1);
        }
    }

    @PostConstruct
    public void start(){
        con.begin();
    }

    public void handleDownload(ActionEvent event){
        boolean print = false;
        if (serviceShiftings != null){
            print = true;
        }
        mode = operation;
        RequestContext context = RequestContext.getCurrentInstance();
        context.addCallbackParam("doPrint", print); //harus ada, buat ngeflag di izinkan ngeprint ato ngga
        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3("../../../../discharge.pdf?"
                + "mode=" + mode + "&"
                + "no_ppkb=" + no_ppkb));
    }

    public String getUrl(){
        if (serviceShiftings == null)
            return "#";

        mode = operation;
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

    private ServiceShiftingFacadeRemote lookupServiceShiftingFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (ServiceShiftingFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/ServiceShiftingFacade!com.pelindo.ebtos.ejb.facade.remote.ServiceShiftingFacadeRemote");
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
     * @return the operation
     */
    public String getOperation() {
        return operation;
    }

    /**
     * @param operation the operation to set
     */
    public void setOperation(String operation) {
        this.operation = operation;
    }

    /**
     * @return the serviceShiftings
     */
    public List<Object[]> getServiceShiftings() {
        return serviceShiftings;
    }

    /**
     * @param serviceShiftings the serviceShiftings to set
     */
    public void setServiceShiftings(List<Object[]> serviceShiftings) {
        this.serviceShiftings = serviceShiftings;
    }

}
