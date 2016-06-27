/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.bean;

import com.qtasnim.jsf.FacesHelper;
import com.pelindo.ebtos.ejb.facade.remote.MasterActivityFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterCustomerFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterDiscountFacadeRemote;
import com.pelindo.ebtos.model.db.master.MasterActivity;
import com.pelindo.ebtos.model.db.master.MasterCustomer;
import com.pelindo.ebtos.model.db.master.MasterDiscount;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJBException;
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
 * @author wulan
 */
@ManagedBean(name = "masterDscountBean")
@ViewScoped
public class MasterDscountBean implements Serializable {

    private MasterDiscount masterDiscount;
    private List<Object[]> masterCustomers;
    private List<Object[]> masterActivitys;
    private List<Object[]> masterDiscounts;

    /** Creates a new instance of MasterDscount */
    public MasterDscountBean() {
        masterDiscount = new MasterDiscount();
        masterDiscount.setMasterCustomer(new MasterCustomer());
        masterDiscount.setMasterActivity(new MasterActivity());
        masterActivitys = lookupMasterActivityFacadeRemote().findAllActivity();
        masterCustomers = lookupMasterCustomerFacadeRemote().findMasterCustomers();
        masterDiscounts = lookupMasterDiscountFacadeRemote().findAllDiscount();
    }

    public void handleOnAdd(ActionEvent event) {
        masterDiscount = new MasterDiscount();
        masterDiscount.setMasterCustomer(new MasterCustomer());
        masterDiscount.setMasterActivity(new MasterActivity());
    }

    public void handleEdit(ActionEvent actionEvent) {
        Integer idDisc = (Integer) actionEvent.getComponent().getAttributes().get("idTarif");
        masterDiscount = lookupMasterDiscountFacadeRemote().find(idDisc);
        BigDecimal disch = BigDecimal.ZERO;
        disch = masterDiscount.getDiscountAmount().multiply(BigDecimal.valueOf(100));
        masterDiscount.setDiscountAmount(disch);
    }

    public void handleSave(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        boolean loggedIn = false;
        try {
            loggedIn = true;
            
            if (masterDiscount.getDiscountAmount() != null) {
                masterDiscount.setDiscountAmount(masterDiscount.getDiscountAmount().divide(BigDecimal.valueOf(100)));
            }

            lookupMasterDiscountFacadeRemote().edit(masterDiscount);
            masterDiscounts = lookupMasterDiscountFacadeRemote().findAllDiscount();
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
        } catch (EJBException e) {
            e.printStackTrace();
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.save.failed");
        }
        context.addCallbackParam("loggedIn", loggedIn);
    }

    public void hanldeDelete(ActionEvent event) {
        try {
            //masterDiscount.setDiscountAmount(masterDiscount.getDiscountAmount().multiply(100));           
            lookupMasterDiscountFacadeRemote().remove(masterDiscount);
            masterDiscounts = lookupMasterDiscountFacadeRemote().findAllDiscount();
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.delete.succeeded");
        } catch (Exception e) {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.delete.failed");
        }
    }

    private MasterDiscountFacadeRemote lookupMasterDiscountFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (MasterDiscountFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/MasterDiscountFacade!com.pelindo.ebtos.ejb.facade.remote.MasterDiscountFacadeRemote");
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

    public List<Object[]> getMasterActivitys() {
        return masterActivitys;
    }

    public void setMasterActivitys(List<Object[]> masterActivitys) {
        this.masterActivitys = masterActivitys;
    }

    public List<Object[]> getMasterCustomers() {
        return masterCustomers;
    }

    public void setMasterCustomers(List<Object[]> masterCustomers) {
        this.masterCustomers = masterCustomers;
    }

    public MasterDiscount getMasterDiscount() {
        return masterDiscount;
    }

    public void setMasterDiscount(MasterDiscount masterDiscount) {
        this.masterDiscount = masterDiscount;
    }

    public List<Object[]> getMasterDiscounts() {
        return masterDiscounts;
    }

    public void setMasterDiscounts(List<Object[]> masterDiscounts) {
        this.masterDiscounts = masterDiscounts;
    }
}
