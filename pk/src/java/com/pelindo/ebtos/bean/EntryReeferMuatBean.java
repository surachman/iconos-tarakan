/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.bean;

import com.qtasnim.jsf.FacesHelper;
import com.pelindo.ebtos.ejb.facade.remote.ReceivingServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ReeferLoadServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.RegistrationFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceReeferFacadeRemote;
import com.pelindo.ebtos.model.db.PlanningVessel;
import com.pelindo.ebtos.model.db.PreserviceVessel;
import com.pelindo.ebtos.model.db.ReceivingService;
import com.pelindo.ebtos.model.db.ReeferLoadService;
import com.pelindo.ebtos.model.db.Registration;
import com.pelindo.ebtos.model.db.master.MasterCommodity;
import com.pelindo.ebtos.model.db.master.MasterContainerType;
import com.pelindo.ebtos.model.db.master.MasterCustomer;
import com.pelindo.ebtos.model.db.master.MasterVessel;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
 * @author dycode-java
 */
@ManagedBean(name="entryReeferMuatBean")
@ViewScoped
public class EntryReeferMuatBean implements Serializable{
    ReeferLoadServiceFacadeRemote reeferLoadServiceFacade = lookupReeferLoadServiceFacadeRemote();
    ReceivingServiceFacadeRemote receivingServiceFacade = lookupReceivingServiceFacadeRemote();
    ServiceReeferFacadeRemote serviceReeferFacade = lookupServiceReeferFacadeRemote();
    RegistrationFacadeRemote registrationFacade = lookupRegistrationFacadeRemote();

    private List<Object[]> registrations = lookupRegistrationFacadeRemote().findRegistrationReeferLoad();
    private List<Object[]> reeferLoadServices;
    private List<Object[]> receivingServices;

    private Registration registration;
    private ReeferLoadService reeferLoadService;
    private String no_reg;

    /** Creates a new instance of EntryReeferMuatBean */
    public EntryReeferMuatBean() {
        registration = new Registration();
        registration.setMasterCustomer(new MasterCustomer());
        registration.setPlanningVessel(new PlanningVessel());
        registration.getPlanningVessel().setPreserviceVessel(new PreserviceVessel());
        registration.getPlanningVessel().getPreserviceVessel().setMasterVessel(new MasterVessel());
    }

    public void handleSelectRegistration(ActionEvent event) {
        no_reg = (String) event.getComponent().getAttributes().get("noReg");
        registration = registrationFacade.find(no_reg);
        setReeferLoadServices(reeferLoadServiceFacade.findReeferLoadServiceByPPKBnReg(registration.getPlanningVessel().getNoPpkb(), no_reg));
        setReceivingServices(receivingServiceFacade.findCountableLoadReefers(registration.getPlanningVessel().getNoPpkb()));
    }

    public void handleAdd(ActionEvent event){
        setReeferLoadService(new ReeferLoadService());
        getReeferLoadService().setMasterContainerType(new MasterContainerType());
        getReeferLoadService().setMasterCommodity(new MasterCommodity());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyMM");
        String tgl = dateFormat.format(Calendar.getInstance().getTime());
        String i;
        String id = reeferLoadServiceFacade.generateId(tgl.substring(2));
        if(id == null)
            i = "00001";
        else
            i = String.valueOf(Integer.valueOf(id) + 1);

        if(i.length() == 1)
            i = tgl+"0000"+i;
        else if(i.length() == 2)
            i = tgl+"000"+i;
        else if(i.length() == 3)
            i = tgl+"00"+i;
        else if(i.length() == 4)
            i = tgl+"0"+i;
        else
            i = tgl + i;
        getReeferLoadService().setJobSlip("16"+i);
    }

    public void handleSelectContPick(ActionEvent event){
        String id_cont = (String) event.getComponent().getAttributes().get("idCont");
        ReceivingService rs = receivingServiceFacade.find(id_cont);
        getReeferLoadService().setContNo(rs.getContNo());
        getReeferLoadService().setMlo(rs.getMlo());
        getReeferLoadService().setContSize(rs.getContSize());
        getReeferLoadService().setContGross(rs.getContGross());
        getReeferLoadService().setContStatus(rs.getContStatus());
        getReeferLoadService().setOverSize(rs.getOverSize());
        getReeferLoadService().setDg(rs.getDg());
        getReeferLoadService().setDgLabel(rs.getDgLabel());
        getReeferLoadService().setMasterCommodity(rs.getMasterCommodity());
        getReeferLoadService().setMasterContainerType(rs.getMasterContainerType());

    }

    public void handleConfirm(ActionEvent event){
        boolean loggedIn = false;
        if(getReeferLoadService().getContNo() == null)
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Warning", "application.transaction.notcompleted");
        else {
            getReeferLoadService().setRegistration(registration);
            getReeferLoadService().setPlanningVessel(registration.getPlanningVessel());
            reeferLoadServiceFacade.edit(getReeferLoadService());
            loggedIn = true;

            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
            RequestContext.getCurrentInstance().addCallbackParam("loggedIn", loggedIn);
            setReeferLoadServices(reeferLoadServiceFacade.findReeferLoadServiceByPPKBnReg(registration.getPlanningVessel().getNoPpkb(), no_reg));
            setReceivingServices(receivingServiceFacade.findCountableLoadReefers(registration.getPlanningVessel().getNoPpkb()));
        }
    }

    public void handleSubmit(ActionEvent event){
        Boolean loggedIn = true;
        registration.setStatusService("approve");
        registrationFacade.edit(getRegistration());
        FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.registration.approved");
        RequestContext.getCurrentInstance().addCallbackParam("loggedIn", loggedIn);
    }

    public void handleEditDelete(ActionEvent event){
        String job_slip = (String) event.getComponent().getAttributes().get("jobSlip");
        setReeferLoadService(reeferLoadServiceFacade.find(job_slip));
    }

    public void handleDelete(ActionEvent event){
        reeferLoadServiceFacade.remove(getReeferLoadService());
        FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.delete.succeeded");
        setReeferLoadServices(reeferLoadServiceFacade.findReeferLoadServiceByPPKBnReg(registration.getPlanningVessel().getNoPpkb(), no_reg));
        setReceivingServices(receivingServiceFacade.findCountableLoadReefers(registration.getPlanningVessel().getNoPpkb()));
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

    private ServiceReeferFacadeRemote lookupServiceReeferFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (ServiceReeferFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/ServiceReeferFacade!com.pelindo.ebtos.ejb.facade.remote.ServiceReeferFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private ReceivingServiceFacadeRemote lookupReceivingServiceFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (ReceivingServiceFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/ReceivingServiceFacade!com.pelindo.ebtos.ejb.facade.remote.ReceivingServiceFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private ReeferLoadServiceFacadeRemote lookupReeferLoadServiceFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (ReeferLoadServiceFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/ReeferLoadServiceFacade!com.pelindo.ebtos.ejb.facade.remote.ReeferLoadServiceFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    /**
     * @return the registration
     */
    public Registration getRegistration() {
        return registration;
    }

    /**
     * @param registration the registration to set
     */
    public void setRegistration(Registration registration) {
        this.registration = registration;
    }

    /**
     * @return the registrations
     */
    public List<Object[]> getRegistrations() {
        return registrations;
    }

    /**
     * @param registrations the registrations to set
     */
    public void setRegistrations(List<Object[]> registrations) {
        this.registrations = registrations;
    }

    /**
     * @return the reeferLoadServices
     */
    public List<Object[]> getReeferLoadServices() {
        return reeferLoadServices;
    }

    /**
     * @param reeferLoadServices the reeferLoadServices to set
     */
    public void setReeferLoadServices(List<Object[]> reeferLoadServices) {
        this.reeferLoadServices = reeferLoadServices;
    }

    /**
     * @return the receivingServices
     */
    public List<Object[]> getReceivingServices() {
        return receivingServices;
    }

    /**
     * @param receivingServices the receivingServices to set
     */
    public void setReceivingServices(List<Object[]> receivingServices) {
        this.receivingServices = receivingServices;
    }

    /**
     * @return the reeferLoadService
     */
    public ReeferLoadService getReeferLoadService() {
        return reeferLoadService;
    }

    /**
     * @param reeferLoadService the reeferLoadService to set
     */
    public void setReeferLoadService(ReeferLoadService reeferLoadService) {
        this.reeferLoadService = reeferLoadService;
    }
}
