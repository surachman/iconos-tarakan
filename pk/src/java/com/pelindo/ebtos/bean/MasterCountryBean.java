/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.bean;

import com.qtasnim.jsf.FacesHelper;
import com.pelindo.ebtos.ejb.facade.remote.MasterCountryFacadeRemote;
import com.pelindo.ebtos.model.db.master.MasterCountry;
import java.io.Serializable;
import javax.faces.event.ActionEvent;
import java.util.List;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.primefaces.context.RequestContext;

/**
 *
 * @author dycoder
 */
@ManagedBean(name = "masterCountryBean")
@ViewScoped
public class MasterCountryBean implements Serializable {

    MasterCountryFacadeRemote countryFacadeRemote;
    private MasterCountry country;
    private List<Object[]> countryList;
    //private Boolean cekId;

    /** Creates a new instance of MasterCountryBean */
    public MasterCountryBean() {
        countryFacadeRemote = lookupMasterCountryFacadeRemote();
        countryList = lookupMasterCountryFacadeRemote().findAllNative();
        country = new MasterCountry();
    }

    public List<Object[]> getCountryList() {
        return countryList;
    }

    public void setCountryList(List<Object[]> countryList) {
        this.countryList = countryList;
    }

    public void handleAdd(ActionEvent event) {
        country = new MasterCountry();
    }

    public void handleSelectTable(ActionEvent event) {
        String country_code = (String) event.getComponent().getAttributes().get("countryCode");
        country = countryFacadeRemote.find(country_code);
    }

    public void handleSaveEdit(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        boolean validate = false;

        if (country.getCountryCode() != null && country.getName() != null && country.getCurrencyIsoName() != null) {
            validate = true;
            countryFacadeRemote.edit(country);
            countryList = lookupMasterCountryFacadeRemote().findAllNative();
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
        } else {
            validate = false;
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.save.failed");
        }
        context.addCallbackParam("validated", validate);
    }

    public void handleDelete(ActionEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            countryFacadeRemote.remove(country);
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info" ,"application.delete.succeeded");
        } catch (EJBException ex) {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info" ,"application.delete.failed");
        }
        countryList = lookupMasterCountryFacadeRemote().findAllNative();
    }

    /**
     * @return the country
     */
    public MasterCountry getCountry() {
        return country;
    }

    /**
     * @param country the country to set
     */
    public void setCountry(MasterCountry country) {
        this.country = country;
    }

//    /**
//     * @return the cekId
//     */
//    public Boolean getCekId() {
//        if (country.getCountryCode() == null) {
//            cekId = false;
//        } else {
//            cekId = true;
//        }
//        return cekId;
//    }
//
//    /**
//     * @param cekId the cekId to set
//     */
//    public void setCekId(Boolean cekId) {
//        this.cekId = cekId;
//    }
    private MasterCountryFacadeRemote lookupMasterCountryFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (MasterCountryFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/MasterCountryFacade!com.pelindo.ebtos.ejb.facade.remote.MasterCountryFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
