/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.bean;

import com.qtasnim.jsf.FacesHelper;
import com.pelindo.ebtos.ejb.facade.remote.MasterCustTypeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterCustomerFacadeRemote;
import com.pelindo.ebtos.model.db.master.MasterCustType;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.context.RequestContext;

/**
 *
 * @author dycoder
 */
@ManagedBean(name="masterCustTypeBean")
@ViewScoped
public class MasterCustTypeBean implements Serializable{
    MasterCustTypeFacadeRemote masterCustTypeFacade = lookupMasterCustTypeFacadeRemote();
    MasterCustomerFacadeRemote masterCustomerFacade = lookupMasterCustomerFacadeRemote();
    private MasterCustType mi;
    private List<MasterCustType> mis = lookupMasterCustTypeFacadeRemote().findAll();

    /** Creates a new instance of MasterCustTypeBean */
    public MasterCustTypeBean() {
        mi = new MasterCustType();
    }

    public MasterCustType getMi() {
        return mi;
    }

    public void setMi(MasterCustType mi) {
        this.mi = mi;
    }

    public List<MasterCustType> getMis() {
        return mis;
    }

    public void setMis(List<MasterCustType> mis) {
        this.mis = mis;
    }

    public void handleOnAdd(ActionEvent event) {
        mi = new MasterCustType();
    }

    public void handleClick(ActionEvent event) {
        Integer s = (Integer) event.getComponent().getAttributes().get("code");
        mi = masterCustTypeFacade.find(s);
    }

    public void handleSaveEdit(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        boolean loggedIn = false;
        if (mi.getName() == null) {
            loggedIn = false;
//            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Failed", "There are uncompleted form submission!");
        } else {
            loggedIn = true;
            masterCustTypeFacade.edit(mi);
            mis = masterCustTypeFacade.findAll();
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
        }
        context.addCallbackParam("loggedIn", loggedIn);
    }

    public void handleOnDelete(ActionEvent event) {
         FacesContext context = FacesContext.getCurrentInstance();
         if(masterCustomerFacade.findAllmasterCustomerByDelete(mi.getCustTypeId()).isEmpty()) {
             masterCustTypeFacade.remove(mi);
             mis = masterCustTypeFacade.findAll();
             FacesHelper.addFacesMessage(context, FacesMessage.SEVERITY_INFO, "Info", "application.delete.succeeded");
             mi = new MasterCustType();
         } else {
             FacesHelper.addFacesMessage(context, FacesMessage.SEVERITY_WARN, "Warning", "application.delete.failed_relationship");
         }
    }

    private MasterCustTypeFacadeRemote lookupMasterCustTypeFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (MasterCustTypeFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/MasterCustTypeFacade!com.pelindo.ebtos.ejb.facade.remote.MasterCustTypeFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private MasterCustomerFacadeRemote lookupMasterCustomerFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (MasterCustomerFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/MasterCustomerFacade!com.pelindo.ebtos.ejb.facade.remote.MasterCustomerFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

}
