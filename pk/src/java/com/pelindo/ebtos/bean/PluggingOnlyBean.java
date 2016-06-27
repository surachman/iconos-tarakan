/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.bean;

import com.qtasnim.jsf.FacesHelper;
import com.pelindo.ebtos.ejb.facade.remote.MasterContainerTypeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterPluggingReeferFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PluggingReeferServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ReeferMonitoringFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.RegistrationFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceReeferFacadeRemote;
import com.pelindo.ebtos.model.db.PluggingReeferService;
import com.pelindo.ebtos.model.db.ReeferMonitoring;
import com.pelindo.ebtos.model.db.Registration;
import com.pelindo.ebtos.model.db.ServiceReefer;
import com.pelindo.ebtos.model.db.master.MasterContainerType;
import com.pelindo.ebtos.model.db.master.MasterCustomer;
import com.pelindo.ebtos.model.db.master.MasterPluggingReefer;
import com.pelindo.ebtos.model.db.master.MasterService;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
@ManagedBean(name="pluggingOnlyBean")
@ViewScoped
public class PluggingOnlyBean implements Serializable {
    ReeferMonitoringFacadeRemote reeferMonitoringFacade = lookupReeferMonitoringFacadeRemote();
    PluggingReeferServiceFacadeRemote pluggingReeferServiceFacade = lookupPluggingReeferServiceFacadeRemote();
    MasterPluggingReeferFacadeRemote masterPluggingReeferFacade = lookupMasterPluggingReeferFacadeRemote();
    MasterContainerTypeFacadeRemote masterContainerTypeFacade = lookupMasterContainerTypeFacadeRemote();
    RegistrationFacadeRemote registrationFacade = lookupRegistrationFacadeRemote();
    ServiceReeferFacadeRemote serviceReeferFacade = lookupServiceReeferFacadeRemote();

    private List<Object[]> registrations = lookupRegistrationFacadeRemote().findRegistrationPluggingOnly();
    private List<MasterPluggingReefer> pluggingReefers;
    private List<Object[]> serviceReefers;
    private List<Object[]> reeferMonitorings;
    private List<Object[]> pluggingReeferServices;

    private Registration registration;
    private ServiceReefer serviceReefer;
    private ReeferMonitoring reeferMonitoring;
    private Date plugOnOff;
    private String no_reg;
    private Boolean kodePlug = false;
    private Boolean editCont = false;
    private Boolean changePlug = true;
    private Boolean editMon = false;

    /** Creates a new instance of PluggingOnlyBean */
    public PluggingOnlyBean() {
        registration = new Registration();
        registration.setMasterCustomer(new MasterCustomer());
        registration.setMasterService(new MasterService());
        serviceReefer = new ServiceReefer();
        serviceReefer.setMasterContainerType(new MasterContainerType());
        serviceReefer.setMasterPluggingReefer(new MasterPluggingReefer());
        reeferMonitoring = new ReeferMonitoring();
        reeferMonitoring.setServiceReefer(new ServiceReefer());
    }

    public void handleSelectRegistration(ActionEvent event) {
        no_reg = (String) event.getComponent().getAttributes().get("noReg");
        registration = registrationFacade.find(no_reg);
        pluggingReeferServices = pluggingReeferServiceFacade.findByNoRegistration(no_reg);
        serviceReefers = serviceReeferFacade.findServiceReeferByRegPlugging(no_reg);
    }

    public void handlePlugOn(ActionEvent event){
        serviceReefer = new ServiceReefer();
        serviceReefer.setMasterContainerType(new MasterContainerType());
        serviceReefer.setMasterPluggingReefer(new MasterPluggingReefer());
        kodePlug = false;
        editCont = false;
        plugOnOff = null;
    }

    public void handleClear(ActionEvent event){
        reeferMonitoring = new ReeferMonitoring();
        reeferMonitoring.setServiceReefer(new ServiceReefer());
        editMon = false;
    }

    public void handleSelectCont(ActionEvent event){
        String id_cont = (String) event.getComponent().getAttributes().get("idCont");
        PluggingReeferService prs = new PluggingReeferService();
        prs = pluggingReeferServiceFacade.find(id_cont);
        serviceReefer.setPluggingReeferServiceId(prs.getJobSlip());
        serviceReefer.setNoPpkb(prs.getNoPpkb());
        serviceReefer.setContNo(prs.getContNo());
        serviceReefer.setMlo(prs.getMlo());
        serviceReefer.setContSize(prs.getContSize());
        serviceReefer.setMasterContainerType(prs.getMasterContainerType());
        serviceReefer.setContStatus(prs.getContStatus());
        serviceReefer.setBlNo(prs.getBlNo());
    }

    public void handleConfirm(ActionEvent event){
        boolean loggedIn = true;
        serviceReefer.setNoReg(no_reg);
        serviceReefer.setOperation("PLUGGING");
        serviceReefer.setChangePlugging(Short.parseShort("0"));
        serviceReefer.setLastPluggingCode(serviceReefer.getMasterPluggingReefer().getPluggingCode());
        if(serviceReefer.getFirstPlugOn() == null)
            serviceReefer.setFirstPlugOn(serviceReefer.getMasterPluggingReefer().getPluggingCode());

        MasterPluggingReefer mpr = new MasterPluggingReefer();
        MasterPluggingReefer mpre = new MasterPluggingReefer();
        MasterPluggingReefer mpree = new MasterPluggingReefer();
        mpr = masterPluggingReeferFacade.find(serviceReefer.getMasterPluggingReefer().getPluggingCode());
        mpre = masterPluggingReeferFacade.find(serviceReefer.getFirstPlugOn());
        mpree = masterPluggingReeferFacade.find(serviceReefer.getLastPluggingCode());

        if(kodePlug == false){
            serviceReefer.setPlugOn(plugOnOff);
            mpr.setAvailability("FALSE");
            mpre.setAvailability("FALSE");
            mpree.setAvailability("FALSE");
        } else {
            serviceReefer.setPlugOff(plugOnOff);
            if(!mpr.getPluggingCode().equals("NULL"))
                mpr.setAvailability("TRUE");
            mpre.setAvailability("TRUE");
            mpree.setAvailability("TRUE");
        }
        serviceReeferFacade.edit(serviceReefer);
        masterPluggingReeferFacade.edit(mpr);
        masterPluggingReeferFacade.edit(mpre);
        masterPluggingReeferFacade.edit(mpree);

        serviceReefers = serviceReeferFacade.findServiceReeferByRegPlugging(no_reg);
        pluggingReeferServices = pluggingReeferServiceFacade.findByNoRegistration(no_reg);
        FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
        RequestContext.getCurrentInstance().addCallbackParam("loggedIn", loggedIn);
    }

    public void handleSelectServiceReefer(ActionEvent event){
        Integer id_serReef = (Integer) event.getComponent().getAttributes().get("idSerReef");
        serviceReefer = serviceReeferFacade.find(id_serReef);
        reeferMonitorings = reeferMonitoringFacade.findByIdReefer(serviceReefer.getIdReefer());
        reeferMonitoring = new ReeferMonitoring();
        reeferMonitoring.setServiceReefer(new ServiceReefer());

        editCont = true;
        plugOnOff = serviceReefer.getPlugOn();
    }

    public void handlePlugOff(ActionEvent event){
        Integer id_serReef = (Integer) event.getComponent().getAttributes().get("idSerReef");
        serviceReefer = serviceReeferFacade.find(id_serReef);
        reeferMonitorings = reeferMonitoringFacade.findByIdReefer(serviceReefer.getIdReefer());
        reeferMonitoring = new ReeferMonitoring();
        reeferMonitoring.setServiceReefer(new ServiceReefer());

        plugOnOff = serviceReefer.getPlugOff();
        kodePlug = true;
        editCont = true;
    }

    public void enableChangePlug(){
        setChangePlug((Boolean) false);
    }

    public void handleAddReeferMonitoring(ActionEvent event){
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        String dateMon = "beda";
        reeferMonitorings = reeferMonitoringFacade.findByIdReefer(serviceReefer.getIdReefer());
        for(Object[] o : reeferMonitorings){
            if (format.format((Date) o[3]).equals(format.format(reeferMonitoring.getDateMontoring()))) {
                dateMon = "sama";
            } else {
                if (dateMon.equals("sama"))
                    dateMon = "sama";
                else
                    dateMon = "beda";
            }
        }
        if (editMon == true) {
            dateMon = "beda";
        }

        if (dateMon.equals("beda")) {
            ServiceReefer sr = new ServiceReefer();
            sr = serviceReeferFacade.find(serviceReefer.getIdReefer());
            if(serviceReefer.getMasterPluggingReefer().getPluggingCode().equals("NULL")){
                serviceReeferFacade.edit(serviceReefer);

                MasterPluggingReefer mpr = new MasterPluggingReefer();
                mpr = masterPluggingReeferFacade.find(sr.getMasterPluggingReefer().getPluggingCode());
                mpr.setAvailability("TRUE");
                masterPluggingReeferFacade.edit(mpr);
            } else if (!sr.getLastPluggingCode().equals(serviceReefer.getMasterPluggingReefer().getPluggingCode())) {
                int i = serviceReefer.getChangePlugging() + 1;
                serviceReefer.setChangePlugging(Short.parseShort(String.valueOf(i)));
                serviceReefer.setLastPluggingCode(serviceReefer.getMasterPluggingReefer().getPluggingCode());
                serviceReeferFacade.edit(serviceReefer);

                MasterPluggingReefer mpr = new MasterPluggingReefer();
                mpr = masterPluggingReeferFacade.find(serviceReefer.getMasterPluggingReefer().getPluggingCode());
                mpr.setAvailability("FALSE");
                masterPluggingReeferFacade.edit(mpr);
            }
            reeferMonitoring.setServiceReefer(serviceReefer);
            reeferMonitoring.setContNo(serviceReefer.getContNo());
            reeferMonitoring.setMlo(serviceReefer.getMlo());

            reeferMonitoringFacade.edit(reeferMonitoring);
            changePlug = true;
            reeferMonitorings = reeferMonitoringFacade.findByIdReefer(serviceReefer.getIdReefer());
            reeferMonitoring = new ReeferMonitoring();
            reeferMonitoring.setServiceReefer(new ServiceReefer());
            editMon = false;
        } else
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Warning", "application.validation.monitoring");
    }

    public void handleSelectMonitoring(ActionEvent event){
        Integer id_rm = (Integer) event.getComponent().getAttributes().get("idRM");
        reeferMonitoring = reeferMonitoringFacade.find(id_rm);
        editMon = true;
    }

    public void handleExit(ActionEvent event){
        reeferMonitoring = new ReeferMonitoring();
        reeferMonitoring.setServiceReefer(new ServiceReefer());
        serviceReefers = serviceReeferFacade.findServiceReeferByRegPlugging(no_reg);
    }

    public void handleDelete(ActionEvent event){
        if (reeferMonitoring.getId() == null) {
            for (Object[] o : reeferMonitorings) {
                reeferMonitoring = reeferMonitoringFacade.find(Integer.valueOf(String.valueOf(o[0])));
                reeferMonitoringFacade.remove(reeferMonitoring);
            }
            MasterPluggingReefer mpr = new MasterPluggingReefer();
            MasterPluggingReefer mpre = new MasterPluggingReefer();
            MasterPluggingReefer mpree = new MasterPluggingReefer();
            mpr = masterPluggingReeferFacade.find(serviceReefer.getMasterPluggingReefer().getPluggingCode());
            mpre = masterPluggingReeferFacade.find(serviceReefer.getFirstPlugOn());
            mpree = masterPluggingReeferFacade.find(serviceReefer.getLastPluggingCode());

            if(!mpr.getPluggingCode().equals("NULL"))
                mpr.setAvailability("TRUE");
            mpre.setAvailability("TRUE");
            mpree.setAvailability("TRUE");
            masterPluggingReeferFacade.edit(mpr);
            masterPluggingReeferFacade.edit(mpre);
            masterPluggingReeferFacade.edit(mpree);

            serviceReeferFacade.remove(serviceReefer);
            serviceReefers = serviceReeferFacade.findServiceReeferByRegPlugging(no_reg);
        } else {
            reeferMonitoringFacade.remove(reeferMonitoring);
            reeferMonitorings = reeferMonitoringFacade.findByIdReefer(serviceReefer.getIdReefer());
            reeferMonitoring = new ReeferMonitoring();
            reeferMonitoring.setServiceReefer(new ServiceReefer());
            editMon = false;
        }
        pluggingReeferServices = pluggingReeferServiceFacade.findByNoRegistration(no_reg);
        FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.delete.succeeded");
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

    private PluggingReeferServiceFacadeRemote lookupPluggingReeferServiceFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (PluggingReeferServiceFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/PluggingReeferServiceFacade!com.pelindo.ebtos.ejb.facade.remote.PluggingReeferServiceFacadeRemote");
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

    private MasterPluggingReeferFacadeRemote lookupMasterPluggingReeferFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (MasterPluggingReeferFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/MasterPluggingReeferFacade!com.pelindo.ebtos.ejb.facade.remote.MasterPluggingReeferFacadeRemote");
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

    private ReeferMonitoringFacadeRemote lookupReeferMonitoringFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (ReeferMonitoringFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/ReeferMonitoringFacade!com.pelindo.ebtos.ejb.facade.remote.ReeferMonitoringFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
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
     * @return the serviceReefers
     */
    public List<Object[]> getServiceReefers() {
        return serviceReefers;
    }

    /**
     * @param serviceReefers the serviceReefers to set
     */
    public void setServiceReefers(List<Object[]> serviceReefers) {
        this.serviceReefers = serviceReefers;
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
     * @return the serviceReefer
     */
    public ServiceReefer getServiceReefer() {
        return serviceReefer;
    }

    /**
     * @param serviceReefer the serviceReefer to set
     */
    public void setServiceReefer(ServiceReefer serviceReefer) {
        this.serviceReefer = serviceReefer;
    }

    /**
     * @return the reeferMonitoring
     */
    public ReeferMonitoring getReeferMonitoring() {
        return reeferMonitoring;
    }

    /**
     * @param reeferMonitoring the reeferMonitoring to set
     */
    public void setReeferMonitoring(ReeferMonitoring reeferMonitoring) {
        this.reeferMonitoring = reeferMonitoring;
    }

    /**
     * @return the changePlug
     */
    public Boolean getChangePlug() {
        return changePlug;
    }

    /**
     * @param changePlug the changePlug to set
     */
    public void setChangePlug(Boolean changePlug) {
        this.changePlug = changePlug;
    }

    /**
     * @return the pluggingReefers
     */
    public List<MasterPluggingReefer> getPluggingReefers() {
        List<MasterPluggingReefer> pl = new ArrayList<MasterPluggingReefer>();
        for(String o : masterPluggingReeferFacade.findNotInServiceReefer()){
            MasterPluggingReefer plug = new MasterPluggingReefer();
            plug = masterPluggingReeferFacade.find(o);
            pl.add(plug);
        }
        pluggingReefers = pl;
        MasterPluggingReefer mpr = new MasterPluggingReefer();
        mpr = masterPluggingReeferFacade.find("NULL");
        if(serviceReefer.getMasterPluggingReefer().getPluggingCode() != null){
            pluggingReefers.add(serviceReefer.getMasterPluggingReefer());
            pluggingReefers.add(mpr);
        }
        return pluggingReefers;
    }

    /**
     * @return the reeferMonitorings
     */
    public List<Object[]> getReeferMonitorings() {
        return reeferMonitorings;
    }

    /**
     * @return the pluggingReeferServices
     */
    public List<Object[]> getPluggingReeferServices() {
        return pluggingReeferServices;
    }

    /**
     * @param pluggingReeferServices the pluggingReeferServices to set
     */
    public void setPluggingReeferServices(List<Object[]> pluggingReeferServices) {
        this.pluggingReeferServices = pluggingReeferServices;
    }

    /**
     * @return the kodePlug
     */
    public Boolean getKodePlug() {
        return kodePlug;
    }

    /**
     * @param kodePlug the kodePlug to set
     */
    public void setKodePlug(Boolean kodePlug) {
        this.kodePlug = kodePlug;
    }

    /**
     * @return the plugOnOff
     */
    public Date getPlugOnOff() {
        return plugOnOff;
    }

    /**
     * @param plugOnOff the plugOnOff to set
     */
    public void setPlugOnOff(Date plugOnOff) {
        this.plugOnOff = plugOnOff;
    }

    /**
     * @return the editCont
     */
    public Boolean getEditCont() {
        return editCont;
    }

    /**
     * @param editCont the editCont to set
     */
    public void setEditCont(Boolean editCont) {
        this.editCont = editCont;
    }
}
