/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.bean;

import com.qtasnim.jsf.FacesHelper;
import com.pelindo.ebtos.ejb.facade.remote.MasterVesselFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterVesselTypeFacadeRemote;
import com.pelindo.ebtos.model.db.master.MasterVesselType;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.event.ActionEvent;
import org.primefaces.context.RequestContext;

/**
 *
 * @author dycoder
 */
@Named(value = "masterVesselTypeBean")
@RequestScoped
public class MasterVesselTypeBean {

    @EJB
    MasterVesselTypeFacadeRemote masterVesselTypeFacadeRemote;
    @EJB
    MasterVesselFacadeRemote masterVesselFacadeRemote;
    private List<MasterVesselType> vesselTypes;
    private MasterVesselType vesselType;
    private int Vessel_type;

    /** Creates a new instance of MasterVesselTypeBean */
    public MasterVesselTypeBean() {
        vesselType = new MasterVesselType();
    }

    public List<Object[]> getMasterTypeVessels() {
        return masterVesselTypeFacadeRemote.findMasterVesselType();
    }
    
    public void addVessel (ActionEvent event){
        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage msg = null;
        boolean loggedIn = false;
        if (vesselType.getName() == null) {
            loggedIn = false;
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.failed");
        } else {
            loggedIn = true;
            
            masterVesselTypeFacadeRemote.create(vesselType);
            vesselType.setVesselTypeCode(null);
            System.out.println(vesselType.getVesselTypeCode()+"-"+vesselType.getName()+"-"+vesselType.getTonage());
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
            vesselType = new MasterVesselType();
        }
    }
    
    public void saveEdit(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage msg = null;
        boolean loggedIn = false;
        if (vesselType.getName() == null) {
            loggedIn = false;
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.failed");
        } else {
            loggedIn = true;
            masterVesselTypeFacadeRemote.edit(vesselType);
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
            vesselType = new MasterVesselType();
        }
      //FacesContext.getCurrentInstance().addMessage(null, msg);
        context.addCallbackParam("loggedIn", loggedIn);
    }

    public void handleEditDeleteButton(ActionEvent event) {
        Vessel_type = ((BigDecimal) event.getComponent().getAttributes().get("vessel_type")).intValue();
        // preserviceVessel = preserviceVesselFacadeRemote.find(booking_code);
        vesselType = masterVesselTypeFacadeRemote.find(Vessel_type);
    }

    public void delete(ActionEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        if (masterVesselFacadeRemote.findMasterVesselByCodeDelete(vesselType.getVesselTypeCode()).isEmpty()) {
            masterVesselTypeFacadeRemote.remove(vesselType);
            vesselType = new MasterVesselType();
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.delete.succeeded");
        } else {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.delete.failed");
        }
    }

    /**
     * @return the vesselTypes
     */
    public List<MasterVesselType> getVesselTypes() {
        return vesselTypes;
    }

    /**
     * @param vesselTypes the vesselTypes to set
     */
    public void setVesselTypes(List<MasterVesselType> vesselTypes) {
        this.vesselTypes = vesselTypes;
    }

    /**
     * @return the vesselType
     */
    public MasterVesselType getVesselType() {
        return vesselType;
    }

    /**
     * @param vesselType the vesselType to set
     */
    public void setVesselType(MasterVesselType vesselType) {
        this.vesselType = vesselType;
    }
}
