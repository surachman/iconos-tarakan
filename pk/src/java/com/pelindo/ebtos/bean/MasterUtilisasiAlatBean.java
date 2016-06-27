/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.bean;

import com.qtasnim.jsf.FacesHelper;
import com.pelindo.ebtos.ejb.facade.remote.MasterUtilisasiAlatFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterEquipmentFacadeRemote;
import com.pelindo.ebtos.model.db.master.MasterUtilisasiAlat;
import java.io.Serializable;
import java.math.BigDecimal;
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
import org.primefaces.context.RequestContext;

/**
 *
 * @author wulan
 */
@ManagedBean(name = "masterUtilisasiAlatBean")
@ViewScoped
public class MasterUtilisasiAlatBean implements Serializable {

    private List<Object[]> utilisasiAlatList;
    private MasterUtilisasiAlat masterUtilisasiAlat;
    private List<Object[]> equipmentList;

    /** Creates a new instance of MasterUtilisasiAlat */
    public MasterUtilisasiAlatBean() {
        utilisasiAlatList = lookupMasterUtilisasiAlatFacadeRemote().findAllUtilisasiAlat();
        equipmentList = lookupMasterEquipmentFacadeRemote().findAllNative();
        masterUtilisasiAlat = new MasterUtilisasiAlat();
    }

    public void handleAdd(ActionEvent event){
        masterUtilisasiAlat=new MasterUtilisasiAlat();
    }

    public void handleDelete(ActionEvent event){
        try {
            lookupMasterUtilisasiAlatFacadeRemote().remove(masterUtilisasiAlat);
            utilisasiAlatList = lookupMasterUtilisasiAlatFacadeRemote().findAllUtilisasiAlat();
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.delete.succeeded");
        } catch (EJBException e) {
            e.printStackTrace();
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.delete.failed");
        }
    }

    public void handleSubmitEdit(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        boolean loggedIn = false;
        try {
            loggedIn=true;
            masterUtilisasiAlat.setTotal(masterUtilisasiAlat.getAccident().add(masterUtilisasiAlat.getBreakDown().add(masterUtilisasiAlat.getRepair().add(masterUtilisasiAlat.getWaiting().add(masterUtilisasiAlat.getMaintenance())))));
            lookupMasterUtilisasiAlatFacadeRemote().edit(masterUtilisasiAlat);
            utilisasiAlatList = lookupMasterUtilisasiAlatFacadeRemote().findAllUtilisasiAlat();
            masterUtilisasiAlat=new MasterUtilisasiAlat();
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
        } catch (EJBException e) {
            e.printStackTrace();
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.failed");
        }
        context.addCallbackParam("loggedIn", loggedIn);
    }

    public void handleEdit(ActionEvent event) {
        Integer id = (Integer) event.getComponent().getAttributes().get("idAlat");
        System.out.println(id);
        masterUtilisasiAlat = lookupMasterUtilisasiAlatFacadeRemote().find(id);
    }

    public List<Object[]> getEquipmentList() {
        return equipmentList;
    }

    public void setEquipmentList(List<Object[]> equipmentList) {
        this.equipmentList = equipmentList;
    }

    public MasterUtilisasiAlat getMasterUtilisasiAlat() {
        return masterUtilisasiAlat;
    }

    public void setMasterUtilisasiAlat(MasterUtilisasiAlat masterUtilisasiAlat) {
        this.masterUtilisasiAlat = masterUtilisasiAlat;
    }

    public List<Object[]> getUtilisasiAlatList() {
        return utilisasiAlatList;
    }

    public void setUtilisasiAlatList(List<Object[]> utilisasiAlatList) {
        this.utilisasiAlatList = utilisasiAlatList;
    }

    private MasterUtilisasiAlatFacadeRemote lookupMasterUtilisasiAlatFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (MasterUtilisasiAlatFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/MasterUtilisasiAlatFacade!com.pelindo.ebtos.ejb.facade.remote.MasterUtilisasiAlatFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
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
}
