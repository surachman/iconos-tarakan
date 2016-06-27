/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.bean;

import com.qtasnim.jsf.FacesHelper;
import com.pelindo.ebtos.ejb.facade.remote.MasterActivityFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterActivityTarifRuleFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterCurrencyFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterTarifContFacadeRemote;
import com.pelindo.ebtos.model.db.master.MasterActivity;
import com.pelindo.ebtos.model.db.master.MasterActivityTarifRule;
import com.pelindo.ebtos.model.db.master.MasterCurrency;
import com.pelindo.ebtos.model.db.master.MasterTarifCont;
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
 * @author dycoder
 */
@ManagedBean(name="masterTarifBean")
@ViewScoped
public class MasterTarifBean implements Serializable{
    
    MasterTarifContFacadeRemote masterTarifContFacade = lookupMasterTarifContFacadeRemote();
    MasterActivityFacadeRemote masterActivityFacade = lookupMasterActivityFacadeRemote();
    MasterCurrencyFacadeRemote masterCurrencyFacade = lookupMasterCurrencyFacadeRemote();
    MasterActivityTarifRuleFacadeRemote masterActivityTarifRuleFacade = lookupMasterActivityTarifRuleFacadeRemote();

    private List<Object[]> tarifConts = masterActivityTarifRuleFacade.findAllActivityTarifRule();
    private List<Object[]> currencys;
    private List<Object[]> activityConts;

    private MasterTarifCont tarifCont;
    private MasterTarifCont tarifContUS;
    private MasterActivityTarifRule tarifRule;

    /** Creates a new instance of MasterTarifBean */
    public MasterTarifBean() {
        tarifCont = new MasterTarifCont();
        tarifCont.setMasterActivity(new MasterActivity());
        tarifCont.setMasterCurrency(new MasterCurrency());
        tarifRule = new MasterActivityTarifRule();
    }

    public void handleEdit(ActionEvent event){
        Integer idTarifRule = ((Number) event.getComponent().getAttributes().get("idTarif")).intValue();
        tarifRule = masterActivityTarifRuleFacade.find(idTarifRule);
        
        Integer idTarif = masterTarifContFacade.findForUsd("IDR", tarifRule.getActivityCode());
        if (idTarif != null)
            tarifCont = masterTarifContFacade.find(idTarif);

        Integer idUS = masterTarifContFacade.findForUsd("USD", tarifRule.getActivityCode());
        if (idUS != null)
            tarifContUS = masterTarifContFacade.find(idUS);
    }

    public void handleSave(ActionEvent event){
        boolean loggedIn = false;
        if(tarifCont.getAmount() == null){
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("data not completed"));
        } else {
            loggedIn = true;
            tarifContUS.setTmt(tarifCont.getTmt());
            masterTarifContFacade.edit(tarifContUS);
            masterTarifContFacade.edit(tarifCont);
            masterActivityTarifRuleFacade.edit(tarifRule);
            tarifConts = masterTarifContFacade.findAllForMaster();
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
        }
        RequestContext.getCurrentInstance().addCallbackParam("loggedIn", loggedIn);
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

    private MasterActivityFacadeRemote lookupMasterActivityFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (MasterActivityFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/MasterActivityFacade!com.pelindo.ebtos.ejb.facade.remote.MasterActivityFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private MasterTarifContFacadeRemote lookupMasterTarifContFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (MasterTarifContFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/MasterTarifContFacade!com.pelindo.ebtos.ejb.facade.remote.MasterTarifContFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
    
    private MasterActivityTarifRuleFacadeRemote lookupMasterActivityTarifRuleFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (MasterActivityTarifRuleFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/MasterActivityTarifRuleFacade!com.pelindo.ebtos.ejb.facade.remote.MasterActivityTarifRuleFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    /**
     * @return the tarifCont
     */
    public MasterTarifCont getTarifCont() {
        return tarifCont;
    }

    /**
     * @param tarifCont the tarifCont to set
     */
    public void setTarifCont(MasterTarifCont tarifCont) {
        this.tarifCont = tarifCont;
    }

    /**
     * @return the currencys
     */
    public List<Object[]> getCurrencys() {
        return currencys;
    }

    /**
     * @param currencys the currencys to set
     */
    public void setCurrencys(List<Object[]> currencys) {
        this.currencys = currencys;
    }

    /**
     * @return the activityConts
     */
    public List<Object[]> getActivityConts() {
        return activityConts;
    }

    /**
     * @param activityConts the activityConts to set
     */
    public void setActivityConts(List<Object[]> activityConts) {
        this.activityConts = activityConts;
    }

    /**
     * @return the tarifContUS
     */
    public MasterTarifCont getTarifContUS() {
        return tarifContUS;
    }

    /**
     * @param tarifContUS the tarifContUS to set
     */
    public void setTarifContUS(MasterTarifCont tarifContUS) {
        this.tarifContUS = tarifContUS;
    }

    /**
     * @return the tarifConts
     */
    public List<Object[]> getTarifConts() {
        return tarifConts;
    }

    /**
     * @param tarifConts the tarifConts to set
     */
    public void setTarifConts(List<Object[]> tarifConts) {
        this.tarifConts = tarifConts;
    }

    public MasterActivityTarifRule getTarifRule() {
        return tarifRule;
    }

    public void setTarifRule(MasterActivityTarifRule tarifRule) {
        this.tarifRule = tarifRule;
    }
    
}
