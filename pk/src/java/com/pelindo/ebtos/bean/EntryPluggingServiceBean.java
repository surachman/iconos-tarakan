/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.bean;

import com.qtasnim.jsf.FacesHelper;
import com.pelindo.ebtos.ejb.facade.remote.MasterActivityFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterCommodityFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterContainerTypeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterTarifContFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterTarifHistoryFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PerhitunganMonitoringFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PerhitunganPluggingFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PluggingReeferServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.RegistrationFacadeRemote;
import com.pelindo.ebtos.model.db.PerhitunganMonitoring;
import com.pelindo.ebtos.model.db.PerhitunganPlugging;
import com.pelindo.ebtos.model.db.PluggingReeferService;
import com.pelindo.ebtos.model.db.Registration;
import com.pelindo.ebtos.model.db.master.MasterActivity;
import com.pelindo.ebtos.model.db.master.MasterCommodity;
import com.pelindo.ebtos.model.db.master.MasterContainerType;
import com.pelindo.ebtos.model.db.master.MasterCustomer;
import com.pelindo.ebtos.model.db.master.MasterService;
import java.io.Serializable;
import java.math.BigDecimal;
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
@ManagedBean(name="entryPluggingServiceBean")
@ViewScoped
public class EntryPluggingServiceBean implements Serializable{
    MasterTarifHistoryFacadeRemote masterTarifHistoryFacade = lookupMasterTarifHistoryFacadeRemote();
    MasterTarifContFacadeRemote masterTarifContFacade = lookupMasterTarifContFacadeRemote();
    MasterActivityFacadeRemote masterActivityFacade = lookupMasterActivityFacadeRemote();
    PerhitunganMonitoringFacadeRemote perhitunganMonitoringFacade = lookupPerhitunganMonitoringFacadeRemote();
    PerhitunganPluggingFacadeRemote perhitunganPluggingFacade = lookupPerhitunganPluggingFacadeRemote();
    MasterCommodityFacadeRemote masterCommodityFacade = lookupMasterCommodityFacadeRemote();
    MasterContainerTypeFacadeRemote masterContainerTypeFacade = lookupMasterContainerTypeFacadeRemote();
    PluggingReeferServiceFacadeRemote pluggingReeferServiceFacade = lookupPluggingReeferServiceFacadeRemote();
    RegistrationFacadeRemote registrationFacade = lookupRegistrationFacadeRemote();

    private List<Object[]> registrations = lookupRegistrationFacadeRemote().findRegistrationPluggingOnly();
    private List<Object[]> pluggingReeferServices;
    private List<Object[]> masterContainerTypes = lookupMasterContainerTypeFacadeRemote().findReefer();

    private Registration registration;
    private PluggingReeferService pluggingReeferService;
    private String no_reg;
    private PerhitunganMonitoring perhitunganMonitoring;
    private PerhitunganPlugging perhitunganPlugging;

    /** Creates a new instance of EntryPluggingServiceBean */
    public EntryPluggingServiceBean() {
        registration = new Registration();
        registration.setMasterCustomer(new MasterCustomer());
        registration.setMasterService(new MasterService());
        pluggingReeferService = new PluggingReeferService();
        pluggingReeferService.setMasterCommodity(new MasterCommodity());
        pluggingReeferService.setMasterContainerType(new MasterContainerType());
        perhitunganPlugging = new PerhitunganPlugging();
        perhitunganPlugging.setRegistration(new Registration());
        perhitunganPlugging.setMasterActivity(new MasterActivity());
        perhitunganMonitoring = new PerhitunganMonitoring();
        perhitunganMonitoring.setRegistration(new Registration());
        perhitunganMonitoring.setMasterActivity(new MasterActivity());
    }

    public void handleSelectRegistration(ActionEvent event) {
        no_reg = (String) event.getComponent().getAttributes().get("noReg");
        registration = registrationFacade.find(no_reg);
        pluggingReeferServices = pluggingReeferServiceFacade.findByNoRegistration(no_reg);
    }

    public void handleAdd(ActionEvent event){
        pluggingReeferService = new PluggingReeferService();
        pluggingReeferService.setMasterCommodity(new MasterCommodity());
        pluggingReeferService.setMasterContainerType(new MasterContainerType());

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyMM");
        String tgl = dateFormat.format(Calendar.getInstance().getTime());
        String i;
        String id = pluggingReeferServiceFacade.generateId(tgl.substring(2));
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
        pluggingReeferService.setJobSlip("03"+i);
    }

    public void handleSelectPluggingReefer(ActionEvent event){
        String job_slip = (String) event.getComponent().getAttributes().get("jobSlip");
        String no_cont = (String) event.getComponent().getAttributes().get("noCont");
        pluggingReeferService = pluggingReeferServiceFacade.find(job_slip);
        Integer idMon = perhitunganMonitoringFacade.findInvoicePlugging(no_cont, no_reg);
        perhitunganMonitoring = perhitunganMonitoringFacade.find(idMon);
        Integer idPlug = perhitunganPluggingFacade.findInvoicePlugging(no_cont, no_reg);
        perhitunganPlugging = perhitunganPluggingFacade.find(idPlug);
    }

    public void handleSubmit(ActionEvent event){
        Boolean loggedIn = true;
        MasterContainerType mct = new MasterContainerType();
        mct = masterContainerTypeFacade.find(pluggingReeferService.getMasterContainerType().getContType());
        pluggingReeferService.setContSize(mct.getFeetMark());
        pluggingReeferService.setRegistration(registration);
        pluggingReeferServiceFacade.edit(pluggingReeferService);

        // insert ke perhitungan_plugging
        perhitunganPlugging.setRegistration(registration);
        perhitunganPlugging.setContNo(pluggingReeferService.getContNo());
        perhitunganPlugging.setMlo(pluggingReeferService.getMlo());

        // menentukan id activity
        String actPlug = null;
        if (pluggingReeferService.getContSize() == 20) {
            actPlug = "D001";
        } else {
            actPlug = "D002";
        }
        perhitunganPlugging.setMasterActivity(masterActivityFacade.find(actPlug));

        // set amount
        BigDecimal cPlug = BigDecimal.ZERO;
        for (Object[] o1 : masterTarifContFacade.findAmountByActivity(actPlug)) {
            if (o1[1].equals("IDR")) {
                cPlug = (BigDecimal) o1[0];
            }
        }
        perhitunganPlugging.setCurrCode("IDR");
        perhitunganPlugging.setCharge(cPlug);
        perhitunganPlugging.setTotalCharge(perhitunganPlugging.getCharge().multiply(BigDecimal.valueOf(pluggingReeferService.getShiftPlanning())));
        perhitunganPluggingFacade.edit(perhitunganPlugging);

        // insert ke perhitungan_monitoring
        perhitunganMonitoring.setRegistration(registration);
        perhitunganMonitoring.setContNo(pluggingReeferService.getContNo());
        perhitunganMonitoring.setMlo(pluggingReeferService.getMlo());

        // menentukan id activity
        String actMon = null;
        if (pluggingReeferService.getContSize() == 20) {
            actMon = "D003";
        } else {
            actMon = "D004";
        }
        perhitunganMonitoring.setMasterActivity(masterActivityFacade.find(actMon));

        // set amount
        BigDecimal cMon = BigDecimal.ZERO;
        for (Object[] o1 : masterTarifContFacade.findAmountByActivity(actMon)) {
            if (o1[1].equals("IDR")) {
                cMon = (BigDecimal) o1[0];
            }
        }
        perhitunganMonitoring.setCurrCode("IDR");
        perhitunganMonitoring.setCharge(cMon);
        perhitunganMonitoring.setTotalCharge(perhitunganMonitoring.getCharge().multiply(BigDecimal.valueOf(pluggingReeferService.getShiftPlanning())));
        perhitunganMonitoringFacade.edit(perhitunganMonitoring);

        pluggingReeferServices = pluggingReeferServiceFacade.findByNoRegistration(no_reg);
        FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
        RequestContext.getCurrentInstance().addCallbackParam("loggedIn", loggedIn);
    }

    public void handleDelete(ActionEvent event){
        pluggingReeferServiceFacade.remove(pluggingReeferService);
        perhitunganMonitoringFacade.remove(perhitunganMonitoring);
        perhitunganPluggingFacade.remove(perhitunganPlugging);
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

    private MasterContainerTypeFacadeRemote lookupMasterContainerTypeFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (MasterContainerTypeFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/MasterContainerTypeFacade!com.pelindo.ebtos.ejb.facade.remote.MasterContainerTypeFacadeRemote");
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

    private MasterTarifHistoryFacadeRemote lookupMasterTarifHistoryFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (MasterTarifHistoryFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/MasterTarifHistoryFacade!com.pelindo.ebtos.ejb.facade.remote.MasterTarifHistoryFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private PerhitunganPluggingFacadeRemote lookupPerhitunganPluggingFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (PerhitunganPluggingFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/PerhitunganPluggingFacade!com.pelindo.ebtos.ejb.facade.remote.PerhitunganPluggingFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private PerhitunganMonitoringFacadeRemote lookupPerhitunganMonitoringFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (PerhitunganMonitoringFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/PerhitunganMonitoringFacade!com.pelindo.ebtos.ejb.facade.remote.PerhitunganMonitoringFacadeRemote");
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

    /**
     * @return the masterCommoditys
     */
    public List<MasterCommodity> getMasterCommoditys() {
        return masterCommodityFacade.findAll();
    }

    /**
     * @return the pluggingReeferService
     */
    public PluggingReeferService getPluggingReeferService() {
        return pluggingReeferService;
    }

    /**
     * @param pluggingReeferService the pluggingReeferService to set
     */
    public void setPluggingReeferService(PluggingReeferService pluggingReeferService) {
        this.pluggingReeferService = pluggingReeferService;
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
     * @return the masterContainerTypes
     */
    public List<Object[]> getMasterContainerTypes() {
        return masterContainerTypes;
    }

    /**
     * @param masterContainerTypes the masterContainerTypes to set
     */
    public void setMasterContainerTypes(List<Object[]> masterContainerTypes) {
        this.masterContainerTypes = masterContainerTypes;
    }
}
