/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.bean;

import com.qtasnim.jsf.FacesHelper;
import com.pelindo.ebtos.ejb.facade.remote.MasterCurrencyFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterMateraiFacadeRemote;
import com.pelindo.ebtos.model.db.master.MasterCurrency;
import com.pelindo.ebtos.model.db.master.MasterMaterai;
import java.io.Serializable;
import java.util.List;
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
@ManagedBean(name = "masterMateraiBean")
@ViewScoped
public class MasterMateraiBean implements Serializable {

    MasterMateraiFacadeRemote masterMateraiFacadeRemote = lookupMasterMateratFacadeRemote();
    MasterCurrencyFacadeRemote masterCurrencyFacadeRemote = lookupMasterCurrencyFacadeRemote();
    private List<MasterMaterai> masterMaterais;
    private List<MasterCurrency> masterCurrencys;
    private MasterMaterai masterMaterai;

    /** Creates a new instance of MasterMateraiBean */
    public MasterMateraiBean() {
        masterMaterais = masterMateraiFacadeRemote.findAll();
        masterCurrencys = masterCurrencyFacadeRemote.findAll();
        masterMaterai = new MasterMaterai();
        masterMaterai.setMasterCurrency(new MasterCurrency());
    }

    public List<MasterCurrency> getMasterCurrencys() {
        return masterCurrencys;
    }

    public void setMasterCurrencys(List<MasterCurrency> masterCurrencys) {
        this.masterCurrencys = masterCurrencys;
    }

    public MasterMaterai getMasterMaterai() {
        return masterMaterai;
    }

    public void setMasterMaterai(MasterMaterai masterMaterai) {
        this.masterMaterai = masterMaterai;
    }

    public List<MasterMaterai> getMasterMaterais() {
        return masterMaterais;
    }

    public void setMasterMaterais(List<MasterMaterai> masterMaterais) {
        this.masterMaterais = masterMaterais;
    }

    private MasterMateraiFacadeRemote lookupMasterMateratFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (MasterMateraiFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/MasterMateraiFacade!com.pelindo.ebtos.ejb.facade.remote.MasterMateraiFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private MasterCurrencyFacadeRemote lookupMasterCurrencyFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (MasterCurrencyFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/MasterCurrencyFacade!com.pelindo.ebtos.ejb.facade.remote.MasterCurrencyFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    public void handleSelect(ActionEvent event) {
        Integer s = (Integer) event.getComponent().getAttributes().get("id_materai");
        masterMaterai = masterMateraiFacadeRemote.find(s);
    }

    public void handleEdit(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        boolean validate = false;

        if (masterMaterai.getId() != null && masterMaterai.getNilaiMaterai() != null) {
            validate = true;
            masterMateraiFacadeRemote.edit(masterMaterai);
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
        } else {
            validate = false;
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.save.failed");
        }
        masterMaterais = masterMateraiFacadeRemote.findAll();
        context.addCallbackParam("validated", validate);
    }
}
