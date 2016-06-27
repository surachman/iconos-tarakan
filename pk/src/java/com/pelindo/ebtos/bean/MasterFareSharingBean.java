/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.bean;

import com.qtasnim.jsf.FacesHelper;
import com.pelindo.ebtos.ejb.facade.remote.MasterActivitySharingFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterOwnerEquipmentFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterTarifSharingFacadeRemote;
import com.pelindo.ebtos.model.db.master.MasterActivitySharing;
import com.pelindo.ebtos.model.db.master.MasterOwnerEquipment;
import com.pelindo.ebtos.model.db.master.MasterTarifSharing;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;

/**
 *
 * @author senoanggoro
 */
@ManagedBean(name = "masterFareSharingBean")
@ViewScoped
public class MasterFareSharingBean implements Serializable {
    @EJB private MasterTarifSharingFacadeRemote masterTarifSharingFacadeRemote;
    @EJB private MasterActivitySharingFacadeRemote masterActivitySharingFacadeRemote;
    @EJB private MasterOwnerEquipmentFacadeRemote masterOwnerEquipmentFacadeRemote;

    private List<MasterTarifSharing> masterTarifSharings;
    private MasterTarifSharing masterTarifSharing;
    private List<MasterActivitySharing> masterActivitySharings;
    private List<MasterOwnerEquipment> masterOwnerEquipments;

    /** Creates a new instance of MasterCurrencyBean */
    public MasterFareSharingBean() {

    }

    @PostConstruct
    private void reconstruct(){
        masterTarifSharings = masterTarifSharingFacadeRemote.findAll();
    }

    private void reconstructComboBox(){
        masterActivitySharings = masterActivitySharingFacadeRemote.findAll();
        masterOwnerEquipments = masterOwnerEquipmentFacadeRemote.findAll();
    }

    public void handleAdd(ActionEvent event) {
        reconstructComboBox();
        masterTarifSharing = new MasterTarifSharing();
        masterTarifSharing.setMasterActivitySharing(new MasterActivitySharing());
        masterTarifSharing.setMasterOwnerEquipment(new MasterOwnerEquipment());
        masterTarifSharing.setTmt(new Date());
    }

    public void handleEdit(ActionEvent event) {
        reconstructComboBox();
        handleSelectTable(event);
    }

    public void handleSelectTable(ActionEvent event) {
        Integer selectedId = (Integer) event.getComponent().getAttributes().get("selectedId");
        masterTarifSharing = masterTarifSharingFacadeRemote.find(selectedId);
    }

    public void handleSaveEdit(ActionEvent event) {
        try{
            masterTarifSharingFacadeRemote.edit(masterTarifSharing);
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info" ,"application.save.succeeded");
        } catch(EJBException ex){
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info" ,"application.save.failed");
        }
        reconstruct();
    }

    public void handleDelete(ActionEvent event) {
        try {
            masterTarifSharingFacadeRemote.remove(masterTarifSharing);
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info" ,"application.delete.succeeded");
        } catch (EJBException ex) {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info" ,"application.delete.failed");
        }
        reconstruct();
    }

    // <editor-fold defaultstate="collapsed" desc="getter setter">
    public MasterTarifSharing getMasterTarifSharing() {
        return masterTarifSharing;
    }

    public void setMasterTarifSharing(MasterTarifSharing masterTarifSharing) {
        this.masterTarifSharing = masterTarifSharing;
    }

    public List<MasterTarifSharing> getMasterTarifSharings() {
        return masterTarifSharings;
    }

    public void setMasterTarifSharings(List<MasterTarifSharing> masterTarifSharings) {
        this.masterTarifSharings = masterTarifSharings;
    }

    public List<MasterActivitySharing> getMasterActivitySharings() {
        return masterActivitySharings;
    }

    public void setMasterActivitySharings(List<MasterActivitySharing> masterActivitySharings) {
        this.masterActivitySharings = masterActivitySharings;
    }

    public List<MasterOwnerEquipment> getMasterOwnerEquipments() {
        return masterOwnerEquipments;
    }

    public void setMasterOwnerEquipments(List<MasterOwnerEquipment> masterOwnerEquipments) {
        this.masterOwnerEquipments = masterOwnerEquipments;
    }
    // </editor-fold>
}
