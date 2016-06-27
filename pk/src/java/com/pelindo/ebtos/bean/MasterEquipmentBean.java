/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.bean;

import com.qtasnim.jsf.FacesHelper;
import com.pelindo.ebtos.ejb.facade.remote.MasterEquipmentFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterEquipmentTypeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterOwnerEquipmentFacadeRemote;
import com.pelindo.ebtos.model.db.master.MasterEquipment;
import com.pelindo.ebtos.model.db.master.MasterEquipmentType;
import com.pelindo.ebtos.model.db.master.MasterOwnerEquipment;
import java.io.Serializable;
import javax.faces.event.ActionEvent;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJBException;
import org.primefaces.context.RequestContext;

/**
 *
 * @author dycoder
 */
@ManagedBean(name = "masterEquipmentBean")
@ViewScoped
public class MasterEquipmentBean implements Serializable {

    MasterEquipmentFacadeRemote equipmentFacadeRemote = lookupMasterEquipmentFacadeRemote();
    MasterEquipmentTypeFacadeRemote equipmentTypeFacadeRemote = lookupMasterEquipmentTypeFacadeRemote();
    MasterOwnerEquipmentFacadeRemote ownerEquipmentFacadeRemote = lookupMasterOwnerEquipmentFacadeRemote();
    private List<MasterEquipmentType> listMasterEquipmentTypes;
    private List<Object[]> equipmentList;
    private MasterEquipment equipment;
    private List<MasterOwnerEquipment> listMasterOwners;

    /** Creates a new instance of MasterEquipmentBean */
    public MasterEquipmentBean() {
        listMasterEquipmentTypes = lookupMasterEquipmentTypeFacadeRemote().findAll();
        listMasterOwners = lookupMasterOwnerEquipmentFacadeRemote().findAll();
        equipmentList = lookupMasterEquipmentFacadeRemote().findAllNative();
        equipment = new MasterEquipment();
        equipment.setMasterEquipmentType(new MasterEquipmentType());
        equipment.setMasterOwnerEquipment(new MasterOwnerEquipment());
    }

    public List<MasterOwnerEquipment> getListMasterOwners() {
        return listMasterOwners;
    }

    public void setListMasterOwners(List<MasterOwnerEquipment> listMasterOwners) {
        this.listMasterOwners = listMasterOwners;
    }

    public List<Object[]> getEquipmentList() {
        return equipmentList;
    }

    public void setEquipmentList(List<Object[]> equipmentList) {
        this.equipmentList = equipmentList;
    }

    public List<MasterEquipmentType> getListMasterEquipmentTypes() {
        return listMasterEquipmentTypes;
    }

    public void setListMasterEquipmentTypes(List<MasterEquipmentType> listMasterEquipmentTypes) {
        this.listMasterEquipmentTypes = listMasterEquipmentTypes;
    }

    public void handleAdd(ActionEvent event) {
        equipment = new MasterEquipment();
        equipment.setMasterEquipmentType(new MasterEquipmentType());
        equipment.setMasterOwnerEquipment(new MasterOwnerEquipment());
    }

    public void handleSaveEdit(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        boolean validate = false;

        if (equipment.getEquipCode() != null && equipment.getName() != null) {
            validate = true;
            equipment.setOwner("FALSE");
            if (equipment.getMasterOwnerEquipment().getOwnerCode().equalsIgnoreCase("PLND")) {
                equipment.setOwner("TRUE");
            }
            equipmentFacadeRemote.edit(equipment);
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
            equipmentList = lookupMasterEquipmentFacadeRemote().findAllNative();
        } else {
            validate = false;
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.save.failed");
        }
        context.addCallbackParam("validated", validate);
    }

    public void handleSelectTable(ActionEvent event) {
        String equip_code = (String) event.getComponent().getAttributes().get("equipCode");
        equipment = equipmentFacadeRemote.find(equip_code);
    }

    public void handleDelete(ActionEvent event) {
        try {
            equipmentFacadeRemote.remove(equipment);
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.delete.succeeded");
        } catch (EJBException ex) {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.delete.failed");
        }
        equipmentList = lookupMasterEquipmentFacadeRemote().findAllNative();
    }

    /**
     * @return the equipment
     */
    public MasterEquipment getEquipment() {
        return equipment;
    }

    /**
     * @param equipment the equipment to set
     */
    public void setEquipment(MasterEquipment equipment) {
        this.equipment = equipment;
    }

    private MasterEquipmentFacadeRemote lookupMasterEquipmentFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (MasterEquipmentFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/MasterEquipmentFacade!com.pelindo.ebtos.ejb.facade.remote.MasterEquipmentFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private MasterEquipmentTypeFacadeRemote lookupMasterEquipmentTypeFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (MasterEquipmentTypeFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/MasterEquipmentTypeFacade!com.pelindo.ebtos.ejb.facade.remote.MasterEquipmentTypeFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private MasterOwnerEquipmentFacadeRemote lookupMasterOwnerEquipmentFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (MasterOwnerEquipmentFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/MasterOwnerEquipmentFacade!com.pelindo.ebtos.ejb.facade.remote.MasterOwnerEquipmentFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
