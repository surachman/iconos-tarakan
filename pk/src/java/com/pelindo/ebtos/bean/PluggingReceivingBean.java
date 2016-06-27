/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.bean;

import com.qtasnim.jsf.FacesHelper;
import com.qtasnim.port.util.ContainerUtilization;
import com.pelindo.ebtos.bean.security.LoginSessionBean;
import com.pelindo.ebtos.ejb.facade.remote.IDGeneratorFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterCommodityFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterContainerTypeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PluggingReeferServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.RegistrationFacadeRemote;
import com.pelindo.ebtos.model.db.PluggingReeferService;
import com.pelindo.ebtos.model.db.Registration;
import com.pelindo.ebtos.model.db.master.MasterCommodity;
import com.pelindo.ebtos.model.db.master.MasterContainerType;
import com.pelindo.ebtos.model.db.master.MasterCustomer;
import com.pelindo.ebtos.model.db.master.MasterService;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.context.RequestContext;

/**
 *
 * @author wulan
 */
@ManagedBean(name = "pluggingReceivingBean")
@ViewScoped
public class PluggingReceivingBean implements Serializable {

    @EJB
    IDGeneratorFacadeRemote iDGeneratorFacade;
    RegistrationFacadeRemote registrationFacadeRemote = lookupRegistrationFacadeRemote();
    MasterContainerTypeFacadeRemote masterContainerTypeFacadeRemote = lookupMasterContainerTypeFacadeRemote();
    MasterCommodityFacadeRemote masterCommodityFacadeRemote = lookupMasterCommodityFacadeRemote();
    PluggingReeferServiceFacadeRemote pluggingReeferServiceFacadeRemote = lookupPluggingReeferServiceFacadeRemote();
    /** Creates a new instance of PluggingReceiving */
    private PluggingReeferService pluggingReeferService;
    private List<Object[]> masterContainerTypes, masterCommoditys;
    private MasterContainerType masterContainerType;
    private List<Object[]> pluggingServices;
    private Registration registration;
    private List<Object[]> registrations;
    private String no_reg;

    public PluggingReceivingBean() {
        registration = new Registration();
        registration.setMasterCustomer(new MasterCustomer());
        registration.setMasterService(new MasterService());
        pluggingReeferService = new PluggingReeferService();
        pluggingReeferService.setMasterCommodity(new MasterCommodity());
        pluggingReeferService.setMasterContainerType(new MasterContainerType());
        LoginSessionBean loginSessionBean = LoginSessionBean.getCurrentInstance();
        //userId = loginSessionBean.getName();
        masterContainerType = new MasterContainerType();
        registrations = registrationFacadeRemote.findRegistrationPluggingOnly();
    }

    public void clear() {
        pluggingReeferService = new PluggingReeferService();
        pluggingReeferService.setMasterCommodity(new MasterCommodity());
        pluggingReeferService.setMasterContainerType(new MasterContainerType());
        masterContainerTypes = masterContainerTypeFacadeRemote.findReefer();
        masterCommoditys = masterCommodityFacadeRemote.findAllNative();
        pluggingReeferService.setContSize(Short.valueOf("20"));
    }

    public void handleAdd(ActionEvent event) {
        pluggingReeferService = new PluggingReeferService();
        pluggingReeferService.setMasterCommodity(new MasterCommodity());
        pluggingReeferService.setMasterContainerType(new MasterContainerType());
        masterContainerType = new MasterContainerType();
        registrations = registrationFacadeRemote.findRegistrationPluggingOnly();
//        pluggingReeferService.setJobSlip(iDGeneratorFacade.generateJobSlipID("03"));
        masterContainerTypes = masterContainerTypeFacadeRemote.findReefer();
        masterCommoditys = masterCommodityFacadeRemote.findAllNative();
        pluggingReeferService.setContSize(Short.valueOf("20"));
    }

    public void handleSelect(ActionEvent event) {
        String no_cont = (String) event.getComponent().getAttributes().get("noCont");
        //String idDS = pluggingReeferServiceFacadeRemote.findInvoice(no_cont, no_reg);
        pluggingReeferService = pluggingReeferServiceFacadeRemote.find(no_cont);
    }

    public void onChangeConstraintType(ValueChangeEvent event) {
        int newItem = (Integer) event.getNewValue();
        masterContainerType = masterContainerTypeFacadeRemote.find(newItem);
        pluggingReeferService.setContSize(masterContainerType.getFeetMark());
        System.out.println(newItem);
    }

    public void handleSave(ActionEvent event) {
        Boolean loggedIn = false;
        RequestContext context = RequestContext.getCurrentInstance();
        try {
            String job = pluggingReeferServiceFacadeRemote.findPluggingReeferValidasiInput(pluggingReeferService.getContNo(), registration.getNo_ppkb_plug(), registration.getNoReg());
            if (job != null) {
                loggedIn=false;
                FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.failed");
            } else {
                loggedIn = true;
                if (!ContainerUtilization.checkContainerNumberValidation(pluggingReeferService.getContNo()) && !((CommandButton) event.getComponent()).getId().equals("invalidContainerConfirmationButton")) {
                    context.addCallbackParam("showInvalidContainerConfirmation", true);
                    return;
                }

                MasterContainerType mct = new MasterContainerType();
                pluggingReeferService.setJobSlip(iDGeneratorFacade.generateJobSlipID("03"));
                mct = masterContainerTypeFacadeRemote.find(pluggingReeferService.getMasterContainerType().getContType());
                pluggingReeferService.setContSize(mct.getFeetMark());
                pluggingReeferService.setRegistration(registration);
                pluggingReeferService.setNoPpkb(registration.getNo_ppkb_plug());
                pluggingReeferService.setStatus("01");
                pluggingReeferServiceFacadeRemote.edit(pluggingReeferService);

                pluggingServices = pluggingReeferServiceFacadeRemote.findByNoregPlugingReefer(no_reg);
                clear();
                FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
            }

        } catch (EJBException e) {
            e.printStackTrace();
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.failed");
        }
        RequestContext.getCurrentInstance().addCallbackParam("loggedIn", loggedIn);
    }

    public void handleSelectRegistration(ActionEvent event) {
        no_reg = (String) event.getComponent().getAttributes().get("noReg");
        registration = registrationFacadeRemote.find(no_reg);
        pluggingServices = pluggingReeferServiceFacadeRemote.findByNoregPlugingReefer(no_reg);
    }

    public void handleDelete(ActionEvent event) {
        try {
            pluggingReeferServiceFacadeRemote.remove(pluggingReeferService);
            pluggingServices = pluggingReeferServiceFacadeRemote.findByNoregPlugingReefer(no_reg);
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.delete.succeeded");
            clear();
        } catch (EJBException e) {
            e.printStackTrace();
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.delete.failed");
        }

    }

    public void setMasterCommoditys(List<Object[]> masterCommoditys) {
        this.masterCommoditys = masterCommoditys;
    }

    public void setMasterContainerType(MasterContainerType masterContainerType) {
        this.masterContainerType = masterContainerType;
    }

    public void setMasterContainerTypes(List<Object[]> masterContainerTypes) {
        this.masterContainerTypes = masterContainerTypes;
    }

    public void setNo_reg(String no_reg) {
        this.no_reg = no_reg;
    }

    public void setPluggingReeferService(PluggingReeferService pluggingReeferService) {
        this.pluggingReeferService = pluggingReeferService;
    }

    public void setPluggingServices(List<Object[]> pluggingServices) {
        this.pluggingServices = pluggingServices;
    }

    public void setRegistration(Registration registration) {
        this.registration = registration;
    }

    public void setRegistrations(List<Object[]> registrations) {
        this.registrations = registrations;
    }

    public List<Object[]> getMasterCommoditys() {
        return masterCommoditys;
    }

    public MasterContainerType getMasterContainerType() {
        return masterContainerType;
    }

    public List<Object[]> getMasterContainerTypes() {
        return masterContainerTypes;
    }

    public String getNo_reg() {
        return no_reg;
    }

    public PluggingReeferService getPluggingReeferService() {
        return pluggingReeferService;
    }

    public List<Object[]> getPluggingServices() {
        return pluggingServices;
    }

    public Registration getRegistration() {
        return registration;
    }

    public List<Object[]> getRegistrations() {
        return registrations;
    }

    private RegistrationFacadeRemote lookupRegistrationFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (RegistrationFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/RegistrationFacade!com.pelindo.ebtos.ejb.facade.remote.RegistrationFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private MasterCommodityFacadeRemote lookupMasterCommodityFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (MasterCommodityFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/MasterCommodityFacade!com.pelindo.ebtos.ejb.facade.remote.MasterCommodityFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private MasterContainerTypeFacadeRemote lookupMasterContainerTypeFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (MasterContainerTypeFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/MasterContainerTypeFacade!com.pelindo.ebtos.ejb.facade.remote.MasterContainerTypeFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private PluggingReeferServiceFacadeRemote lookupPluggingReeferServiceFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (PluggingReeferServiceFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/PluggingReeferServiceFacade!com.pelindo.ebtos.ejb.facade.remote.PluggingReeferServiceFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
