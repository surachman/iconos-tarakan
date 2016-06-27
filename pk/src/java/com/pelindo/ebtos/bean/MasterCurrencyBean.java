/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.bean;

import com.qtasnim.jsf.FacesHelper;
import com.pelindo.ebtos.ejb.facade.remote.MasterCurrencyFacadeRemote;
import com.pelindo.ebtos.model.db.master.MasterCurrency;
import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJBException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author dycoder
 */
@ManagedBean(name = "masterCurrencyBean")
@ViewScoped
public class MasterCurrencyBean implements Serializable {

    MasterCurrencyFacadeRemote currencyFacadeRemote = lookupMasterCurrencyFacadeRemote();
    private List<MasterCurrency> currencyList;
    private MasterCurrency currency;
    private Boolean cekId;

    /** Creates a new instance of MasterCurrencyBean */
    public MasterCurrencyBean() {
        currency = new MasterCurrency();
        currencyList = currencyFacadeRemote.findAll();
    }

    public List<MasterCurrency> getCurrencyList() {
        return currencyList;
    }

    public void setCurrencyList(List<MasterCurrency> currencyList) {
        this.currencyList = currencyList;
    }

    public void handleAdd(ActionEvent event) {
        currency = new MasterCurrency();
    }

    public void handleSelectTable(ActionEvent event) {
        String curr_code = (String) event.getComponent().getAttributes().get("curr_code");
        currency = currencyFacadeRemote.find(curr_code);
    }

    public void handleSaveEdit(ActionEvent event) {
        try{
            currencyFacadeRemote.edit(currency);
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info" ,"application.save.succeeded");
        } catch(EJBException ex){
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info" ,"application.save.failed");
        }
        currencyList = currencyFacadeRemote.findAll();
    }

    public void handleDelete(ActionEvent event) {
        try {
            currencyFacadeRemote.remove(currency);
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info" ,"application.delete.succeeded");
        } catch (EJBException ex) {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info" ,"application.delete.failed");
        }
        currencyList = currencyFacadeRemote.findAll();
    }

    /**
     * @return the currency
     */
    public MasterCurrency getCurrency() {
        return currency;
    }

    /**
     * @param currency the currency to set
     */
    public void setCurrency(MasterCurrency currency) {
        this.currency = currency;
    }

    /**
     * @return the cekId
     */
    public Boolean getCekId() {
        if (currency.getCurrCode() == null) {
            cekId = false;
        } else {
            cekId = true;
        }
        return cekId;
    }

    /**
     * @param cekId the cekId to set
     */
    public void setCekId(Boolean cekId) {
        this.cekId = cekId;
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
}
