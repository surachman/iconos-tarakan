/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.bean;

import com.qtasnim.jsf.FacesHelper;
import com.pelindo.ebtos.ejb.facade.remote.AngkutPetikemasFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterActivityFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterCustomerFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterTarifContFacadeRemote;
import com.pelindo.ebtos.model.db.AngkutPetikemas;
import com.pelindo.ebtos.model.db.master.MasterCustomer;
import java.io.Serializable;
import java.math.BigDecimal;
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
@ManagedBean(name = "angkutPetikemasBean")
@ViewScoped
public class AngkutPetikemasBean implements Serializable {
    MasterTarifContFacadeRemote masterTarifContFacade = lookupMasterTarifContFacadeRemote();
    AngkutPetikemasFacadeRemote angkutPetikemasFacade = lookupAngkutPetikemasFacadeRemote();
    MasterCustomerFacadeRemote masterCustomerFacadeRemote = lookupMasterCustomerFacadeRemote();
    MasterActivityFacadeRemote masterActivityFacadeRemote = lookupMasterActivityFacadeRemote();

    private List<Object[]> angkutPetikemasList;
    private List<Object[]> masterCustomers;

    private AngkutPetikemas angkutPetikemas;

    /** Creates a new instance of AngkutPetikemasBean */
    public AngkutPetikemasBean() {
        angkutPetikemasList = angkutPetikemasFacade.findAllNative();
        angkutPetikemas = new AngkutPetikemas();
        angkutPetikemas.setMasterCustomer(new MasterCustomer());
    }
    
    public void handleAdd(ActionEvent event){
        angkutPetikemas = new AngkutPetikemas();
        angkutPetikemas.setMasterCustomer(new MasterCustomer());
        masterCustomers = masterCustomerFacadeRemote.findMasterCustomers();
    }

    public void handleEditDeleteButton(ActionEvent event){
        Integer id_angkut = (Integer) event.getComponent().getAttributes().get("id_angkut");
        angkutPetikemas = angkutPetikemasFacade.find(id_angkut);
        masterCustomers = masterCustomerFacadeRemote.findMasterCustomers();
    }

    public void handleSave(ActionEvent event){
        Boolean loggedIn = true;
        String act_code = masterActivityFacadeRemote.translateAngkutActivityCode(angkutPetikemas.getContStatus(), angkutPetikemas.getContSize().shortValue());
 
        angkutPetikemas.setTarifCont(masterTarifContFacade.findByCurrCodeAndActivity("IDR", act_code));
        angkutPetikemas.setTotalCharge(angkutPetikemas.getTarifCont().multiply(BigDecimal.valueOf(angkutPetikemas.getJmlCont())));
        angkutPetikemasFacade.edit(angkutPetikemas);
        angkutPetikemasList = angkutPetikemasFacade.findAllNative();
        FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
        RequestContext.getCurrentInstance().addCallbackParam("loggedIn", loggedIn);
    }

    public void handleDelete(ActionEvent event){
        angkutPetikemasFacade.remove(angkutPetikemas);
        angkutPetikemasList = angkutPetikemasFacade.findAllNative();
        FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.delete.succeeded");
    }

    private AngkutPetikemasFacadeRemote lookupAngkutPetikemasFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (AngkutPetikemasFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/AngkutPetikemasFacade!com.pelindo.ebtos.ejb.facade.remote.AngkutPetikemasFacadeRemote");
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

    public AngkutPetikemas getAngkutPetikemas() {
        return angkutPetikemas;
    }

    public void setAngkutPetikemas(AngkutPetikemas angkutPetikemas) {
        this.angkutPetikemas = angkutPetikemas;
    }

    public List<Object[]> getAngkutPetikemasList() {
        return angkutPetikemasList;
    }

    public void setAngkutPetikemasList(List<Object[]> angkutPetikemasList) {
        this.angkutPetikemasList = angkutPetikemasList;
    }

    public List<Object[]> getMasterCustomers() {
        return masterCustomers;
    }

    public void setMasterCustomers(List<Object[]> masterCustomers) {
        this.masterCustomers = masterCustomers;
    }

}
