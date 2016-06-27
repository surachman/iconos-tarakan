/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.bean;

import com.pelindo.ebtos.ejb.facade.remote.ServiceVesselFacadeRemote;
import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author dycoder
 */
@ManagedBean(name = "cancelLoadingBean")
@ViewScoped
public class CancelLoadingBean implements Serializable {

    private List<Object[]> serviceVessels = lookupServiceVesselFacadeRemote().findServiceVesselsCancelLoadingConfirm();
    private Object[] serviceVessel;
    private String no_ppkb;

    /** Creates a new instance of CancelLoadingBean */
    public CancelLoadingBean() {
    }

    public void handleSelectNoPpkb(ActionEvent event) {
        no_ppkb = (String) event.getComponent().getAttributes().get("noPpkb");
        serviceVessel = lookupServiceVesselFacadeRemote().findServiceVesselByPpkb(no_ppkb);
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

    public String getNo_ppkb() {
        return no_ppkb;
    }

    public void setNo_ppkb(String no_ppkb) {
        this.no_ppkb = no_ppkb;
    }

    public List<Object[]> getServiceVessels() {
        return serviceVessels;
    }

    public void setServiceVessels(List<Object[]> serviceVessels) {
        this.serviceVessels = serviceVessels;
    }

    public Object[] getServiceVessel() {
        return serviceVessel;
    }

    public void setServiceVessel(Object[] serviceVessel) {
        this.serviceVessel = serviceVessel;
    }
}
