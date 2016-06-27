/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.bean;

import com.qtasnim.jsf.FacesHelper;
import com.pelindo.ebtos.ejb.facade.remote.MasterVehicleFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterVehicleTypeFacadeRemote;
import com.pelindo.ebtos.model.db.master.MasterVehicle;
import com.pelindo.ebtos.model.db.master.MasterVehicleType;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.context.RequestContext;

/**
 *
 * @author USER
 */
@ManagedBean(name="masterVehicleBean")
@ViewScoped
public class MasterVehicleBean implements Serializable {

    @EJB
    private MasterVehicleFacadeRemote masterVehicleFacadeRemote;
    @EJB
    MasterVehicleTypeFacadeRemote masterVehicleTypeFacade;
    private MasterVehicle masterVehicle;
    private List<MasterVehicleType> masterVehicleTypeList;
    private List<Object[]> masterVehicles;
    private Boolean isEdit;

    /** Creates a new instance of MasterVehicleBean */
    public MasterVehicleBean() {}

    @PostConstruct
    private void construct() {
        masterVehicles = masterVehicleFacadeRemote.findAllNative();
        setMasterVehicleTypeList(masterVehicleTypeFacade.findAll());
    }

    public void handleNewMasterVehicle(ActionEvent event) {
        masterVehicle = new MasterVehicle();
        masterVehicle.setMasterVehicleType(new MasterVehicleType());
        isEdit = false;
    }

    public void handleSelectMasterVehicle(ActionEvent event) {
        String s = (String) event.getComponent().getAttributes().get("code");
        masterVehicle = masterVehicleFacadeRemote.find(s);
        isEdit = true;
    }

    public void handleSaveEdit(ActionEvent event) {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        FacesContext currentInstance = FacesContext.getCurrentInstance();
        boolean validate = false;

        try {
            validate = true;
            masterVehicleFacadeRemote.edit(masterVehicle);
            FacesHelper.addFacesMessage(currentInstance, FacesMessage.SEVERITY_INFO, "Info","application.save.succeeded");
        } catch (RuntimeException re) {
            FacesHelper.addFacesMessage(currentInstance, FacesMessage.SEVERITY_WARN, "Info","application.save.failed");
        }

        masterVehicles = masterVehicleFacadeRemote.findAllNative();
        requestContext.addCallbackParam("validated", validate);
    }

    public void handleOnDelete(ActionEvent event) {
        try {
            masterVehicleFacadeRemote.remove(masterVehicle);
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info" ,"application.delete.succeeded");
        } catch (RuntimeException ex) {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info" ,"application.delete.failed");
        }
        
        masterVehicles = masterVehicleFacadeRemote.findAllNative();
    }

    // <editor-fold defaultstate="collapsed" desc="getter setter">
    public MasterVehicle getMasterVehicle() {
        return masterVehicle;
    }

    public void setMasterVehicle(MasterVehicle masterVehicle) {
        this.masterVehicle = masterVehicle;
    }

    public List<Object[]> getMasterVehicles() {
        return masterVehicles;
    }

    public Boolean getIsEdit() {
        return isEdit;
    }// </editor-fold>

    /**
     * @return the masterVehicleTypeList
     */
    public List<MasterVehicleType> getMasterVehicleTypeList() {
        return masterVehicleTypeList;
    }

    /**
     * @param masterVehicleTypeList the masterVehicleTypeList to set
     */
    public void setMasterVehicleTypeList(List<MasterVehicleType> masterVehicleTypeList) {
        this.masterVehicleTypeList = masterVehicleTypeList;
    }
}
