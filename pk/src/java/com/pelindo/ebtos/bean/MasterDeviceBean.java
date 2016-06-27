/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.bean;

import com.qtasnim.jsf.FacesHelper;
import com.pelindo.ebtos.ejb.facade.remote.MasterDeviceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterDeviceRegistrationFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterDeviceTypeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterEquipmentFacadeRemote;
import com.pelindo.ebtos.exception.MasterSettingAppNotFoundException;
import com.pelindo.ebtos.exception.MasterSettingAppValueNotValidException;
import com.pelindo.ebtos.model.db.master.MasterDevice;
import com.pelindo.ebtos.model.db.master.MasterDeviceRegistration;
import com.pelindo.ebtos.model.db.master.MasterDeviceType;
import com.pelindo.ebtos.model.db.master.MasterEquipment;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

/**
 *
 * @author senoanggoro
 */
@ManagedBean(name="masterDeviceBean")
@ViewScoped
public class MasterDeviceBean implements Serializable {
    @EJB private MasterDeviceFacadeRemote masterDeviceFacade;
    @EJB private MasterDeviceTypeFacadeRemote masterDeviceTypeFacade;
    @EJB private MasterEquipmentFacadeRemote masterEquipmentFacade;
    @EJB private MasterDeviceRegistrationFacadeRemote masterDeviceRegistrationFacade;

    private List<MasterDevice> devices;
    private List<MasterDeviceType> deviceTypes;
    private List<MasterEquipment> masterEquipments;
    private MasterDevice device;
    private boolean isAdd = true;
    /** Creates a new instance of MasterDeviceBean */
    public MasterDeviceBean() {}

    @PostConstruct
    private void construct(){
        devices = masterDeviceFacade.findAll();
    }

    public void handleAddEditDevice(ActionEvent event){
        try {
            if (isAdd) {
                if (device.getMasterDeviceRegistration() != null)
                    device.getMasterDeviceRegistration().setDeviceId(device.getDeviceId());
                masterDeviceFacade.create(device);
            } else {
                masterDeviceFacade.edit(device);
            }
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
        } catch (RuntimeException re) {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_ERROR, "Error", "application.save.failed");
        }
        device = null;
        devices = masterDeviceFacade.findAll();
    }

    public void handleDeleteDevice(ActionEvent event){
        try {
            masterDeviceFacade.remove(device);
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.delete.succeeded");
        } catch (RuntimeException re) {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_ERROR, "Error", "application.delete.failed");
        }
        devices = masterDeviceFacade.findAll();
    }

    public void handleSelectDevice(ActionEvent event){
        String id = (String) event.getComponent().getAttributes().get("deviceId");
        isAdd = false;
        device = masterDeviceFacade.find(id);
    }

    public void handleShowMasterEquipments(ActionEvent event){
        try {
            masterEquipments = masterEquipmentFacade.findMasterEquipmentHTOnly();
        } catch (MasterSettingAppNotFoundException ex) {
            masterEquipments = masterEquipmentFacade.findAll();
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_ERROR, "Error", "application.error.ht_value_not_valid");
            Logger.getLogger(MasterDeviceBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MasterSettingAppValueNotValidException ex) {
            masterEquipments = masterEquipmentFacade.findAll();
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_ERROR, "Error", "application.error.ht_value_not_valid");
            Logger.getLogger(MasterDeviceBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void handleShowMasterDeviceTypes(ActionEvent event){
        deviceTypes = masterDeviceTypeFacade.findAll();
    }

    public void handleClearDeviceRegistration(ActionEvent event){
        if (device != null) {
            masterDeviceRegistrationFacade.remove(device.getMasterDeviceRegistration());
            device.setMasterDeviceRegistration(null);
            if (!isAdd()) {
                masterDeviceFacade.edit(device);
            }
        }
    }

    public void handleSelectMasterEquipment(ActionEvent event){
        String equipment = (String) event.getComponent().getAttributes().get("equipment");
        if (device != null && equipment!=null) {
            MasterEquipment masterEquipment = masterEquipmentFacade.find(equipment);
            MasterDeviceRegistration deviceRegistration = new MasterDeviceRegistration(device.getDeviceId());
            deviceRegistration.setMasterEquipment(masterEquipment);
            device.setMasterDeviceRegistration(deviceRegistration);
        }
    }

    public void handleSelectDeviceType(ActionEvent event){
        Integer deviceTypeCode = (Integer) event.getComponent().getAttributes().get("deviceTypeCode");
        if (device != null && deviceTypeCode!=null) {
            MasterDeviceType masterDeviceType = masterDeviceTypeFacade.find(deviceTypeCode);
            device.setMasterDeviceType(masterDeviceType);
        }
    }

    public void handleAddNewDevice(ActionEvent event){
        device = new MasterDevice();
        device.setMasterDeviceType(new MasterDeviceType());
        device.setPurchaseDate(new Date());
        isAdd = true;
    }

    public List<MasterDevice> getDevices() {
        return devices;
    }

    public void setDevices(List<MasterDevice> devices) {
        this.devices = devices;
    }

    public List<MasterDeviceType> getDeviceTypes() {
        return deviceTypes;
    }

    public void setDeviceTypes(List<MasterDeviceType> deviceTypes) {
        this.deviceTypes = deviceTypes;
    }

    public MasterDevice getDevice() {
        return device;
    }

    public void setDevice(MasterDevice device) {
        this.device = device;
    }

    public boolean isAdd(){
        return isAdd;
    }

    public List<MasterEquipment> getMasterEquipments() {
        return masterEquipments;
    }

    public void setMasterEquipments(List<MasterEquipment> masterEquipments) {
        this.masterEquipments = masterEquipments;
    }
}
