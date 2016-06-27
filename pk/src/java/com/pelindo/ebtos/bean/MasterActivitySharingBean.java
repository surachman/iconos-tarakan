/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.bean;

import com.qtasnim.jsf.FacesHelper;
import com.pelindo.ebtos.ejb.facade.remote.MasterActivitySharingFacadeRemote;
import com.pelindo.ebtos.model.db.master.MasterActivitySharing;
import java.io.Serializable;
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
@ManagedBean(name = "masterActivitySharingBean")
@ViewScoped
public class MasterActivitySharingBean implements Serializable {
    @EJB private MasterActivitySharingFacadeRemote masterActivitySharingFacadeRemote;

    private List<MasterActivitySharing> masterActivitySharings;
    private MasterActivitySharing masterActivitySharing;

    /** Creates a new instance of MasterCurrencyBean */
    public MasterActivitySharingBean() {

    }

    @PostConstruct
    private void reconstruct(){
        masterActivitySharings = masterActivitySharingFacadeRemote.findAll();
    }

    public void handleAdd(ActionEvent event) {
        masterActivitySharing = new MasterActivitySharing();
    }

    public void handleSelectTable(ActionEvent event) {
        String selectedId = (String) event.getComponent().getAttributes().get("selectedId");
        masterActivitySharing = masterActivitySharingFacadeRemote.find(selectedId);
    }

    public void handleSaveEdit(ActionEvent event) {
        try{
            masterActivitySharingFacadeRemote.edit(masterActivitySharing);
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info" ,"application.save.succeeded");
        } catch(EJBException ex){
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info" ,"application.save.failed");
        }
        reconstruct();
    }

    public void handleDelete(ActionEvent event) {
        try {
            masterActivitySharingFacadeRemote.remove(masterActivitySharing);
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info" ,"application.delete.succeeded");
        } catch (EJBException ex) {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info" ,"application.delete.failed");
        }
        reconstruct();
    }

    // <editor-fold defaultstate="collapsed" desc="getter setter">
    public List<MasterActivitySharing> getMasterActivitySharings() {
        return masterActivitySharings;
    }

    public void setMasterActivitySharings(List<MasterActivitySharing> masterActivitySharings) {
        this.masterActivitySharings = masterActivitySharings;
    }

    public MasterActivitySharing getMasterActivitySharing() {
        return masterActivitySharing;
    }

    public void setMasterActivitySharing(MasterActivitySharing masterActivitySharing) {
        this.masterActivitySharing = masterActivitySharing;
    }
    // </editor-fold>
}
