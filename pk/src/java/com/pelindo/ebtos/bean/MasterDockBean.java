/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.bean;

import com.pelindo.ebtos.ejb.facade.remote.MasterDockFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningVesselFacadeRemote;
import com.pelindo.ebtos.model.db.master.MasterDock;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.event.ActionEvent;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author dycode-java
 */
@ManagedBean(name="masterDockBean")
@ViewScoped
public class MasterDockBean implements Serializable{

    @EJB private MasterDockFacadeRemote masterDockFacadeRemote;
    @EJB private PlanningVesselFacadeRemote planningVesselFacadeRemote;

    private MasterDock masterDock;
    private String idDock;
    private boolean delete;
    private boolean disable;
    private List<MasterDock> masterDocks = lookupMasterDockFacadeRemote().findAll();

    /** Creates a new instance of MasterDockBean */
    public MasterDockBean() {
        masterDock = new MasterDock();
        disable = false;
    }

    /**
     * @return the masterDock
     */
    public MasterDock getMasterDock() {
        return masterDock;
    }

    /**
     * @param masterDock the masterDock to set
     */
    public void setMasterDock(MasterDock masterDock) {
        this.masterDock = masterDock;
    }

    public List<MasterDock> getMasterDocks(){
        return masterDocks;

    }

    public void handleAddButton(ActionEvent event){
        masterDock = new MasterDock();
        disable = false;
    }

    public void handleEditButton(ActionEvent event){
        idDock = (String) event.getComponent().getAttributes().get("idDock");
        masterDock = masterDockFacadeRemote.find(idDock);
        disable = true;
    }

    public void saveEdit(ActionEvent event){
        FacesContext context = FacesContext.getCurrentInstance();
        masterDockFacadeRemote.edit(masterDock);
        context.addMessage(null, new FacesMessage("Successful", "save file id= " + masterDock.getDockCode()));
	context.addMessage(null, new FacesMessage("attention", "save is completed"));
        masterDocks = lookupMasterDockFacadeRemote().findAll();
    }

    public void handleDeleteButton(ActionEvent event){
        idDock = (String) event.getComponent().getAttributes().get("idDock");
        this.delete = true;
    }

    public void handleDeleteAll(ActionEvent event){
        this.delete = false;
    }

    public void delete(ActionEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        if (this.delete) {
            masterDock = masterDockFacadeRemote.find(idDock);
            if (planningVesselFacadeRemote.findPlanningVesselByDock(idDock).isEmpty()) {
                masterDockFacadeRemote.remove(masterDock);
                context.addMessage(null, new FacesMessage("Succesful", "delete file id= " + masterDock.getDockCode()));
            } else {
                context.addMessage(null, new FacesMessage("Aborted", "delete file id= " + masterDock.getDockCode()));
                context.addMessage(null, new FacesMessage("attention", "data is cannot deleted because have relationship with primary tabel"));
            }
        } else {
            for (MasterDock contain : masterDockFacadeRemote.findAll()) {
                if (planningVesselFacadeRemote.findPlanningVesselByDock(contain.getDockCode()).isEmpty()) {
                    masterDockFacadeRemote.remove(contain);
                    context.addMessage(null, new FacesMessage("Succesful", "delete file id= " + contain.getDockCode()));
                } else {
                    context.addMessage(null, new FacesMessage("Aborted", "delete file id= " + contain.getDockCode()));
                    context.addMessage(null, new FacesMessage("attention", "data is cannot deleted because have relationship with primary tabel"));
                }
            }
        }
        masterDocks = lookupMasterDockFacadeRemote().findAll();
    }



    /**
     * @return the idDock
     */
    public String getIdDock() {
        return idDock;
    }

    /**
     * @param idDock the idDock to set
     */
    public void setIdDock(String idDock) {
        this.idDock = idDock;
    }

    /**
     * @return the delete
     */
    public boolean isDelete() {
        return delete;
    }

    /**
     * @param delete the delete to set
     */
    public void setDelete(boolean delete) {
        this.delete = delete;
    }

    /**
     * @return the disable
     */
    public boolean isDisable() {
        return disable;
    }

    /**
     * @param disable the disable to set
     */
    public void setDisable(boolean disable) {
        this.disable = disable;
    }

    private MasterDockFacadeRemote lookupMasterDockFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (MasterDockFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/MasterDockFacade!com.pelindo.ebtos.ejb.facade.remote.MasterDockFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

}
