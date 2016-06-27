/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.bean;

import com.qtasnim.jsf.FacesHelper;
import com.pelindo.ebtos.ejb.facade.remote.MasterBankFacadeRemote;
import com.pelindo.ebtos.model.db.master.MasterBank;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
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
@ManagedBean(name = "masterBankBean")
@ViewScoped
public class MasterBankBean implements Serializable {

    MasterBankFacadeRemote masterBankFacadeRemote = lookupMasterBankFacadeRemote();
    private List<MasterBank> masterBanks;
    private MasterBank masterBank;

    /** Creates a new instance of MasterMateraiBean */
    public MasterBankBean() {
        masterBanks = masterBankFacadeRemote.findAll();
    }

    public void handleSelect(ActionEvent event) {
        String s = (String) event.getComponent().getAttributes().get("id_bank");
        masterBank = masterBankFacadeRemote.find(s);
    }

    public void handleNew(ActionEvent event){
        masterBank = new MasterBank();
    }

    public void handleEdit(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        boolean validate = false;

        if (masterBank.isValid()) {
            validate = true;
            masterBankFacadeRemote.edit(masterBank);
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
            masterBanks = masterBankFacadeRemote.findAll();
            masterBank = null;
        } else {
            validate = false;
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.save.failed");
        }
        context.addCallbackParam("validated", validate);
    }

    public void handleResetGuid(ActionEvent event){
        if (masterBank != null){
            masterBank.setGuid(UUID.randomUUID().toString());
        }
    }

    public void handleDelete(ActionEvent event){
        if (masterBank != null){
            masterBankFacadeRemote.remove(masterBank);
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.delete.succeeded");
        }
    }

    public List<MasterBank> getMasterBanks() {
        return masterBanks;
    }

    public void setMasterBanks(List<MasterBank> masterBanks) {
        this.masterBanks = masterBanks;
    }

    public MasterBank getMasterBank() {
        return masterBank;
    }

    public void setMasterBank(MasterBank masterMaterai) {
        this.masterBank = masterMaterai;
    }

    private MasterBankFacadeRemote lookupMasterBankFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (MasterBankFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/MasterBankFacade!com.pelindo.ebtos.ejb.facade.remote.MasterBankFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
