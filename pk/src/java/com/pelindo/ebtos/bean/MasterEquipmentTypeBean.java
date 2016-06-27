/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.bean;

import java.io.Serializable;
import javax.inject.Named;
import javax.faces.bean.ViewScoped;
import com.pelindo.ebtos.ejb.facade.remote.MasterEquipmentTypeFacadeRemote;
import com.pelindo.ebtos.model.db.master.MasterEquipmentType;
import com.qtasnim.jsf.FacesHelper;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.primefaces.context.RequestContext;


/**
 *
 * @author USER
 */
@ManagedBean(name = "masterEquipmentTypeBean")
@ViewScoped
public class MasterEquipmentTypeBean implements Serializable{

    /**
     * Author : Surach
     */
    MasterEquipmentTypeFacadeRemote equipmentTypeFacadeRemote = lookupMasterEquipmentTypeFacadeRemote();
    private List<Object[]> equipmentList;
    private MasterEquipmentType equipmenttype;
    private String test;
    
    
    public MasterEquipmentTypeBean() {
         equipmentList = lookupMasterEquipmentTypeFacadeRemote().findAllNative();  
         equipmenttype = new MasterEquipmentType();
    }
    
    public void handleAdd (ActionEvent event){
        equipmenttype = new MasterEquipmentType();
    }
    
    public void handleSaveEdit(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        boolean validate = false;
        System.out.println(equipmenttype.getEquipmentTypeCode()+"-"+equipmenttype.getName()+"-"+test+""+equipmenttype.getCreatedBy());
        if (equipmenttype.getEquipmentTypeCode() != null && equipmenttype.getName() != null) {
            validate = true;
            
            equipmentTypeFacadeRemote.edit(equipmenttype);
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
            
            equipmentList = lookupMasterEquipmentTypeFacadeRemote().findAllNative();
        } else {
            validate = false;
            
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.save.failed");
        }
        context.addCallbackParam("validated", validate);
    }
    
    public void handleDelete(ActionEvent event){
        try {
            equipmentTypeFacadeRemote.remove(equipmenttype);
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.delete.succeeded");
        } catch (Exception e) {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.delete.failed");
        }
            equipmentList = lookupMasterEquipmentTypeFacadeRemote().findAllNative();
    }
    
    
    
    public String getTest(){
        return test;
    } 
    
    public void setTest(String test){
        this.test = test;
    }
    
    public void handleSelectTable(ActionEvent event){
        String equip_type_code = (String) event.getComponent().getAttributes().get("equipCode");
        equipmenttype = equipmentTypeFacadeRemote.find(equip_type_code);
        System.out.println(equipmenttype.getEquipmentTypeCode()+"-"+equipmenttype.getName());
    }
    
    public List<Object[]> getEquipmentLists(){
        return equipmentList;
    }
    
   public void setEquipmentLists(List<Object[]> equipmentList){
        this.equipmentList = equipmentList;
    }
    
   public MasterEquipmentType getEquipmentType(){
        return equipmenttype;
    }
    
   public void setEquipmenTtype(MasterEquipmentType equipmenttype){
        this.equipmenttype = equipmenttype;
   }
   
   
   
   
    private MasterEquipmentTypeFacadeRemote lookupMasterEquipmentTypeFacadeRemote(){
        try {
            Context c = new InitialContext();
            return (MasterEquipmentTypeFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/MasterEquipmentTypeFacade!com.pelindo.ebtos.ejb.facade.remote.MasterEquipmentTypeFacadeRemote");
            
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
   
    }
    
}
