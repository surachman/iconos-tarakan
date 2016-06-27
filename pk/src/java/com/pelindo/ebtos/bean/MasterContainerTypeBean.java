/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.bean;

import com.qtasnim.jsf.FacesHelper;
import com.pelindo.ebtos.ejb.facade.remote.MasterContainerTypeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterContainerTypeInGeneralFacadeRemote;
import com.pelindo.ebtos.model.db.master.MasterContainerType;
import com.pelindo.ebtos.model.db.master.MasterContainerTypeInGeneral;
import java.io.Serializable;
import java.util.Hashtable;
import java.util.List;
import javax.faces.event.ActionEvent;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;

/**
 *
 * @author dycode-java
 */
@ManagedBean(name = "masterContainerTypeBean")
@ViewScoped
public class MasterContainerTypeBean implements Serializable {
    private MasterContainerTypeFacadeRemote masterContainerTypeFacadeRemote = lookupMasterContainerTypeFacadeRemote();
    private MasterContainerTypeInGeneralFacadeRemote containerTypeInGeneralFacadeRemote = lookupMasterContainerTypeInGeneralFacadeRemote();
    
    private MasterContainerType masterContainerType;
    private Integer idContainerType;
    private boolean delete;
    private boolean disable;
    private List<MasterContainerType> masterContainerTypes;
    private List<MasterContainerTypeInGeneral> masterContainerTypeInGenerals;

    /** Creates a new instance of MasterContainerTypeBean */
    public MasterContainerTypeBean() {
        masterContainerTypes = lookupMasterContainerTypeFacadeRemote().findAll();
        masterContainerTypeInGenerals = lookupMasterContainerTypeInGeneralFacadeRemote().findAll();
        masterContainerType = new MasterContainerType();
        disable = false;
    }

    public List<MasterContainerType> getMasterContainerTypes() {
        return masterContainerTypes;
    }

    public void setMasterContainerTypes(List<MasterContainerType> masterContainerTypes) {
        this.masterContainerTypes = masterContainerTypes;
    }
    
    public MasterContainerType getMasterContainerType() {
        return masterContainerType;
    }

    public void handleAddButton(ActionEvent event) {
        masterContainerType = new MasterContainerType();
        disable = true;
    }

    public void handleEditButton(ActionEvent event) {
        idContainerType = (Integer) event.getComponent().getAttributes().get("idContainerType");
        masterContainerType = masterContainerTypeFacadeRemote.find(idContainerType);
        disable = true;
    }

    public void saveEdit(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        boolean validate = false;
        if (masterContainerType.getContType() != null && masterContainerType.getName() != null) {
            validate = true;
            masterContainerTypeFacadeRemote.edit(masterContainerType);
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info" ,"application.save.succeeded");
            masterContainerTypes = lookupMasterContainerTypeFacadeRemote().findAll();
        } else {
            validate = false;
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info" ,"application.save.failed");
        }
        context.addCallbackParam("validated", validate);
    }

    public void handleDeleteButton(ActionEvent event) {
        idContainerType = (Integer) event.getComponent().getAttributes().get("idContainerType");
        this.delete = true;
    }

    public void handleDeleteAll(ActionEvent event) {
        this.delete = false;
    }

    public void delete(ActionEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            masterContainerTypeFacadeRemote.remove(masterContainerType);
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info" ,"application.delete.succeeded");
        } catch (EJBException ex) {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info" ,"application.delete.failed");
        }
        masterContainerTypes = lookupMasterContainerTypeFacadeRemote().findAll();
    }

    /**
     * @return the idContainerType
     */
    public int getIdContainerType() {
        return idContainerType;
    }

    /**
     * @param idContainerType the idContainerType to set
     */
    public void setIdContainerType(int idContainerType) {
        this.idContainerType = idContainerType;
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

    public List<MasterContainerTypeInGeneral> getMasterContainerTypeInGenerals() {
        return masterContainerTypeInGenerals;
    }

    public void setMasterContainerTypeInGenerals(List<MasterContainerTypeInGeneral> containerTypeInGenerals) {
        this.masterContainerTypeInGenerals = containerTypeInGenerals;
    }

    private MasterContainerTypeFacadeRemote lookupMasterContainerTypeFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (MasterContainerTypeFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/MasterContainerTypeFacade!com.pelindo.ebtos.ejb.facade.remote.MasterContainerTypeFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private MasterContainerTypeInGeneralFacadeRemote lookupMasterContainerTypeInGeneralFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (MasterContainerTypeInGeneralFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/MasterContainerTypeInGeneralFacade!com.pelindo.ebtos.ejb.facade.remote.MasterContainerTypeInGeneralFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
