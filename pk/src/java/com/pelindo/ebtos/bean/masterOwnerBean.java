/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.bean;

import com.qtasnim.jsf.FacesHelper;
import com.pelindo.ebtos.ejb.facade.remote.MasterOwnerEquipmentFacadeRemote;
import com.pelindo.ebtos.model.db.master.MasterOwnerEquipment;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Dyware-Dev01
 */
@ManagedBean(name = "masterOwnerBean")
@ViewScoped
public class masterOwnerBean implements Serializable {

    MasterOwnerEquipmentFacadeRemote ownerEquipmentFacadeRemote = lookupMasterOwnerEquipmentFacadeRemote();
    MasterOwnerEquipment masterOwnerEquipment;
    List<MasterOwnerEquipment> listMasterOwnerEquipments;
    private int num1,num2;


    /** Creates a new instance of masterOwnerBean */
    public masterOwnerBean() {
        masterOwnerEquipment = new MasterOwnerEquipment();
        listMasterOwnerEquipments = ownerEquipmentFacadeRemote.findAll();
    }

    public List<MasterOwnerEquipment> getListMasterOwnerEquipments() {
        return listMasterOwnerEquipments;
    }

    public void setListMasterOwnerEquipments(List<MasterOwnerEquipment> listMasterOwnerEquipments) {
        this.listMasterOwnerEquipments = listMasterOwnerEquipments;
    }

    public MasterOwnerEquipment getMasterOwnerEquipment() {
        return masterOwnerEquipment;
    }

    public void setMasterOwnerEquipment(MasterOwnerEquipment masterOwnerEquipment) {
        this.masterOwnerEquipment = masterOwnerEquipment;
    }

    public int getNum1() {
        return num1;
    }

    public void setNum1(int num1) {
        this.num1 = num1;
    }

    public int getNum2() {
        return num2;
    }

    public void setNum2(int num2) {
        this.num2 = num2;
    }


    /** Actiont Event **/
    public void handleAdd(ActionEvent actionEvent) {
        masterOwnerEquipment = new MasterOwnerEquipment();
    }

    public void handleSaveEdit(ActionEvent actionEvent) {
        RequestContext context = RequestContext.getCurrentInstance();
        boolean validate = false;


        if (masterOwnerEquipment.getOwnerCode() != null && masterOwnerEquipment.getOwnerName() != null && masterOwnerEquipment.getProfitpelindo() != null && masterOwnerEquipment.getProfitpelindo().doubleValue() <= 100) {
            validate = true;
            double persen = 100.00 - masterOwnerEquipment.getProfitpelindo().doubleValue();
            masterOwnerEquipment.setProfitowner(BigDecimal.valueOf(persen));
            ownerEquipmentFacadeRemote.edit(masterOwnerEquipment);
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
            listMasterOwnerEquipments = ownerEquipmentFacadeRemote.findAll();
        } else {
            validate = false;
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.save.failed");
        }
        context.addCallbackParam("validated", validate);
    }

    public void handleSelect(ActionEvent event) {
        String owner_code = (String) event.getComponent().getAttributes().get("ownerCode");
        masterOwnerEquipment = ownerEquipmentFacadeRemote.find(owner_code);
    }

    public void handleDelete(ActionEvent event) {
        try {
            ownerEquipmentFacadeRemote.remove(masterOwnerEquipment);
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.delete.succeeded");
        } catch (EJBException ex) {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.delete.failed");
        }
        listMasterOwnerEquipments = ownerEquipmentFacadeRemote.findAll();
    }

    /** Facade **/
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
