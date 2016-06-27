/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.bean;

import com.qtasnim.jsf.FacesHelper;
import com.pelindo.ebtos.ejb.facade.remote.MasterCustomerFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterVesselFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterVesselTypeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningVesselFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PreserviceVesselFacadeRemote;
import com.pelindo.ebtos.model.db.master.MasterCustomer;
import com.pelindo.ebtos.model.db.master.MasterVessel;
import com.pelindo.ebtos.model.db.master.MasterVesselType;
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
@ManagedBean(name="masterVesselBean")
@ViewScoped
public class MasterVesselBean implements Serializable{
    MasterVesselFacadeRemote masterVesselFacadeRemote = lookupMasterVesselFacadeRemote();
    MasterCustomerFacadeRemote masterCustomerFacadeRemote = lookupMasterCustomerFacadeRemote();
    MasterVesselTypeFacadeRemote masterVesselTypeFacadeRemote = lookupMasterVesselTypeFacadeRemote();
    PlanningVesselFacadeRemote planningVesselFacadeRemote = lookupPlanningVesselFacadeRemote();
    PreserviceVesselFacadeRemote preserviceVesselFacadeRemote = lookupPreserviceVesselFacadeRemote();

    private List<MasterVesselType> vesselTypes = lookupMasterVesselTypeFacadeRemote().findAll();
    private List<MasterCustomer> customers = lookupMasterCustomerFacadeRemote().findAll();

    private MasterVessel vessel;
    private MasterVesselType masterVesselType;
    private MasterCustomer masterCustomer;
    private Boolean cekId;
    private Object masterCustomerSelection;

    /** Creates a new instance of MasterVesselBean */
    public MasterVesselBean() {
        vessel = new MasterVessel();
        masterVesselType = new MasterVesselType();
        masterCustomer = new MasterCustomer();
        vessel.setMasterVesselType(new MasterVesselType());
        vessel.setMasterCustomer(new MasterCustomer());
    }

    public void handleEditDeleteButton(ActionEvent event) {
        String vessel_code = (String) event.getComponent().getAttributes().get("vessel_code");
        vessel = masterVesselFacadeRemote.find(vessel_code);
        masterCustomerSelection = new Object[]{
            vessel.getMasterCustomer().getCustCode(), vessel.getMasterCustomer().getName() + " " + vessel.getMasterCustomer().getCustCode()
        };
    }

    public void handleAdd(ActionEvent event){
        vessel = new MasterVessel();
        vessel.setMasterVesselType(new MasterVesselType());
        vessel.setMasterCustomer(new MasterCustomer());
        masterCustomerSelection = null;
    }

    public void DeleteButton(ActionEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        if(!planningVesselFacadeRemote.findPlanningVesselByVessel(vessel.getVesselCode()).isEmpty()){
             FacesHelper.addFacesMessage(context, FacesMessage.SEVERITY_WARN, "Warning", "application.delete.failed_relationship");
        } else if (!preserviceVesselFacadeRemote.findPreserviceVesselByVessel(vessel.getVesselCode()).isEmpty()){
             FacesHelper.addFacesMessage(context, FacesMessage.SEVERITY_WARN, "Warning", "application.delete.failed_relationship");
        } else {
            masterVesselFacadeRemote.remove(vessel);
            vessel = new MasterVessel();
            vessel.setMasterVesselType(new MasterVesselType());
            vessel.setMasterCustomer(new MasterCustomer());
            FacesHelper.addFacesMessage(context, FacesMessage.SEVERITY_INFO, "Info", "application.delete.succeeded");
        }
    }

    public void saveEdit(ActionEvent event) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        RequestContext context = RequestContext.getCurrentInstance();
        
        try {
            Object custCode = new String();
            if (masterCustomerSelection instanceof String) {
                custCode = masterCustomerSelection;
            } else {
                custCode = ((Object[]) masterCustomerSelection)[0];
            }

            masterCustomer = masterCustomerFacadeRemote.find(masterCustomerSelection);
            
            if (masterCustomer == null) {
                masterCustomerSelection = null;
                FacesHelper.addFacesTextMessage(facesContext, FacesMessage.SEVERITY_ERROR, "Error", FacesHelper.getLocaleMessage("application.validation.not_found", facesContext), "addEdit:masterCustomer");
                context.addCallbackParam("loggedIn", false);
                return;
            }

            vessel.setMasterCustomer(masterCustomer);
            masterVesselFacadeRemote.edit(vessel);
            vessel = new MasterVessel();
            vessel.setMasterVesselType(new MasterVesselType());
            vessel.setMasterCustomer(new MasterCustomer());
            masterCustomerSelection = null;
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
            context.addCallbackParam("loggedIn", true);
        } catch (RuntimeException re) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", re);
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.failed");
            context.addCallbackParam("loggedIn", false);
        }
    }

    private PreserviceVesselFacadeRemote lookupPreserviceVesselFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (PreserviceVesselFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/PreserviceVesselFacade!com.pelindo.ebtos.ejb.facade.remote.PreserviceVesselFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private MasterVesselFacadeRemote lookupMasterVesselFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (MasterVesselFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/MasterVesselFacade!com.pelindo.ebtos.ejb.facade.remote.MasterVesselFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private MasterVesselTypeFacadeRemote lookupMasterVesselTypeFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (MasterVesselTypeFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/MasterVesselTypeFacade!com.pelindo.ebtos.ejb.facade.remote.MasterVesselTypeFacadeRemote");
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

    private PlanningVesselFacadeRemote lookupPlanningVesselFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (PlanningVesselFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/PlanningVesselFacade!com.pelindo.ebtos.ejb.facade.remote.PlanningVesselFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    public List<Object[]> getMasterVessels() {
        return masterVesselFacadeRemote.findMasterVessels();
    }

    /**
     * @return the vesselTypes
     */
    public List<MasterVesselType> getVesselTypes() {
        return vesselTypes;
    }

    /**
     * @param vesselTypes the vesselTypes to set
     */
    public void setVesselTypes(List<MasterVesselType> vesselTypes) {
        this.vesselTypes = vesselTypes;
    }

    /**
     * @return the customers
     */
    public List<MasterCustomer> getCustomers() {
        return customers;
    }

    /**
     * @param customers the customers to set
     */
    public void setCustomers(List<MasterCustomer> customers) {
        this.customers = customers;
    }

    /**
     * @return the vessel
     */
    public MasterVessel getVessel() {
        return vessel;
    }

    /**
     * @param vessel the vessel to set
     */
    public void setVessel(MasterVessel vessel) {
        this.vessel = vessel;
    }

    /**
     * @return the masterVesselType
     */
    public MasterVesselType getMasterVesselType() {
        return masterVesselType;
    }

    /**
     * @param masterVesselType the masterVesselType to set
     */
    public void setMasterVesselType(MasterVesselType masterVesselType) {
        this.masterVesselType = masterVesselType;
    }

    /**
     * @return the masterCustomer
     */
    public MasterCustomer getMasterCustomer() {
        return masterCustomer;
    }

    /**
     * @param masterCustomer the masterCustomer to set
     */
    public void setMasterCustomer(MasterCustomer masterCustomer) {
        this.masterCustomer = masterCustomer;
    }

    /**
     * @return the cekId
     */
    public Boolean getCekId() {
        if(vessel.getVesselCode() == null)
            cekId = false;
        else
            cekId = true;
        return cekId;
    }

    /**
     * @param cekId the cekId to set
     */
    public void setCekId(Boolean cekId) {
        this.cekId = cekId;
    }

    public Object getMasterCustomerSelection() {
        return masterCustomerSelection;
    }

    public void setMasterCustomerSelection(Object masterCustomerSelection) {
        this.masterCustomerSelection = masterCustomerSelection;
    }
}
