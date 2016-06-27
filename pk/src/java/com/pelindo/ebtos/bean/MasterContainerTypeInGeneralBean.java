/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.bean;

import com.qtasnim.jsf.FacesHelper;
import com.pelindo.ebtos.ejb.facade.remote.MasterContainerTypeInGeneralFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterPatternFacadeRemote;
import com.pelindo.ebtos.model.db.master.MasterContainerTypeInGeneral;
import com.pelindo.ebtos.model.db.master.MasterPattern;
import java.io.Serializable;
import java.util.List;
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
 * @author senoanggoro
 */
@ManagedBean(name="masterContainerTypeInGeneralBean")
@ViewScoped
public class MasterContainerTypeInGeneralBean implements Serializable{
    private MasterContainerTypeInGeneralFacadeRemote masterContainerTypeInGeneralFacadeRemote = lookupMasterContainerTypeInGeneralFacadeRemote();
    private MasterPatternFacadeRemote masterPatternFacadeRemote = lookupMasterPatternFacadeRemote();

    private MasterContainerTypeInGeneral masterContType;
    private List<MasterContainerTypeInGeneral> masterContTypes;
    private List<MasterPattern> masterPatterns;

    /** Creates a new instance of MasterContainerTypeInGeneralBean */
    public MasterContainerTypeInGeneralBean() {
        initValue();
    }

    private void initValue(){
        masterContTypes = masterContainerTypeInGeneralFacadeRemote.findAll();
        masterPatterns = masterPatternFacadeRemote.findAll();
    }

    public void handleEditButton(ActionEvent event){
        String contTypeId = (String) event.getComponent().getAttributes().get("contTypeId");
        masterContType = masterContainerTypeInGeneralFacadeRemote.find(contTypeId);
    }

    public void saveEdit(ActionEvent event){
        try {
            masterContainerTypeInGeneralFacadeRemote.edit(masterContType);
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
        } catch (RuntimeException re) {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_ERROR, "Error", "application.save.failed");
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", re);
        }
        initValue();
    }

    public MasterContainerTypeInGeneral getMasterContType() {
        return masterContType;
    }

    public void setMasterContType(MasterContainerTypeInGeneral masterContType) {
        this.masterContType = masterContType;
    }

    public List<MasterContainerTypeInGeneral> getMasterContTypes() {
        return masterContTypes;
    }

    public void setMasterContTypes(List<MasterContainerTypeInGeneral> masterContTypes) {
        this.masterContTypes = masterContTypes;
    }

    public List<MasterPattern> getMasterPatterns() {
        return masterPatterns;
    }

    public void setMasterPatterns(List<MasterPattern> masterPatterns) {
        this.masterPatterns = masterPatterns;
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

    private MasterPatternFacadeRemote lookupMasterPatternFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (MasterPatternFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/MasterPatternFacade!com.pelindo.ebtos.ejb.facade.remote.MasterPatternFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
