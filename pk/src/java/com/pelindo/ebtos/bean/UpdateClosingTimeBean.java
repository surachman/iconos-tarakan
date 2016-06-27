/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.bean;

import com.qtasnim.jsf.FacesHelper;
import com.pelindo.ebtos.ejb.facade.remote.PlanningVesselFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceVesselFacadeRemote;
import com.pelindo.ebtos.model.db.PlanningVessel;
import com.pelindo.ebtos.model.db.ServiceVessel;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
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
@ManagedBean(name = "updateClosingTimeBean")
@ViewScoped
public class UpdateClosingTimeBean implements Serializable {

    PlanningVesselFacadeRemote planningVesselFacade = lookupPlanningVesselFacadeRemote();
    ServiceVesselFacadeRemote serviceVesselFacade = lookupServiceVesselFacadeRemote();
    private ServiceVessel serviceVessel;
    private PlanningVessel planningVessel;
    private String no_ppkb;

    /** Creates a new instance of UpdateClosingTimeBean */
    public UpdateClosingTimeBean() {
        serviceVessel = new ServiceVessel();
        planningVessel = new PlanningVessel();
    }

    public void handleSearch(ActionEvent event) {
        serviceVessel = serviceVesselFacade.find(no_ppkb);
        planningVessel = planningVesselFacade.find(no_ppkb);
        if (serviceVessel != null || planningVessel != null) {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.search.found");
        } else {
            serviceVessel = new ServiceVessel();
            planningVessel = new PlanningVessel();
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.search.notfound");
        }
    }

    public void handleClear(ActionEvent event) {
        no_ppkb = "";
        serviceVessel = new ServiceVessel();
        planningVessel = new PlanningVessel();
    }

    public void handleUpdateClosingTime(ActionEvent event) {
        // copy closing time ke planning vessel
        if (serviceVessel != null) {
            serviceVessel.setCloseRec(planningVessel.getCloseRec());
            serviceVesselFacade.edit(serviceVessel);
        }
        // save
        planningVesselFacade.edit(planningVessel);

        FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
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

    /**
     * @return the no_ppkb
     */
    public String getNo_ppkb() {
        return no_ppkb;
    }

    /**
     * @param no_ppkb the no_ppkb to set
     */
    public void setNo_ppkb(String no_ppkb) {
        this.no_ppkb = no_ppkb;
    }

    /**
     * @return the serviceVessel
     */
    public ServiceVessel getServiceVessel() {
        return serviceVessel;
    }

    /**
     * @param serviceVessel the serviceVessel to set
     */
    public void setServiceVessel(ServiceVessel serviceVessel) {
        this.serviceVessel = serviceVessel;
    }

    /**
     * @return the planningVessel
     */
    public PlanningVessel getPlanningVessel() {
        return planningVessel;
    }

    /**
     * @param planningVessel the planningVessel to set
     */
    public void setPlanningVessel(PlanningVessel planningVessel) {
        this.planningVessel = planningVessel;
    }
}
