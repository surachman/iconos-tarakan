/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.bean;

import com.qtasnim.jsf.FacesHelper;
import com.pelindo.ebtos.ejb.facade.remote.MasterActivityFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterTarifContFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterTarifHistoryFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PerhitunganMonitoringFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PerhitunganPluggingFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ReeferDischargeServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.RegistrationFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceContDischargeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceReeferFacadeRemote;
import com.pelindo.ebtos.model.db.PerhitunganMonitoring;
import com.pelindo.ebtos.model.db.PerhitunganPlugging;
import com.pelindo.ebtos.model.db.PlanningVessel;
import com.pelindo.ebtos.model.db.PreserviceVessel;
import com.pelindo.ebtos.model.db.ReeferDischargeService;
import com.pelindo.ebtos.model.db.Registration;
import com.pelindo.ebtos.model.db.ServiceContDischarge;
import com.pelindo.ebtos.model.db.master.MasterActivity;
import com.pelindo.ebtos.model.db.master.MasterCommodity;
import com.pelindo.ebtos.model.db.master.MasterContainerType;
import com.pelindo.ebtos.model.db.master.MasterCustomer;
import com.pelindo.ebtos.model.db.master.MasterVessel;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
 * @author dycode-java
 */
@ManagedBean(name="entryDischargeReeferBean")
@ViewScoped
public class EntryDischargeReeferBean implements Serializable{
    MasterTarifHistoryFacadeRemote masterTarifHistoryFacade = lookupMasterTarifHistoryFacadeRemote();
    PerhitunganMonitoringFacadeRemote perhitunganMonitoringFacade = lookupPerhitunganMonitoringFacadeRemote();
    PerhitunganPluggingFacadeRemote perhitunganPluggingFacade = lookupPerhitunganPluggingFacadeRemote();
    ServiceReeferFacadeRemote serviceReeferFacade = lookupServiceReeferFacadeRemote();
    ReeferDischargeServiceFacadeRemote reeferDischargeServiceFacade = lookupReeferDischargeServiceFacadeRemote();
    MasterTarifContFacadeRemote masterTarifContFacade = lookupMasterTarifContFacadeRemote();
    MasterActivityFacadeRemote masterActivityFacade = lookupMasterActivityFacadeRemote();
    ServiceContDischargeFacadeRemote serviceContDischargeFacade = lookupServiceContDischargeFacadeRemote();
    RegistrationFacadeRemote registrationFacade = lookupRegistrationFacadeRemote();

    private List<Object[]> registrations = lookupRegistrationFacadeRemote().findRegistrationReeferDischarge();
    private List<Object[]> reeferDischargeServices;
    private List<Object[]> serviceContDischarges;

    private Registration registration;
    private ReeferDischargeService reeferDischargeService;
    private PerhitunganMonitoring perhitunganMonitoring;
    private PerhitunganPlugging perhitunganPlugging;
    private String no_reg;

    /** Creates a new instance of EntryDischargeReeferBean */
    public EntryDischargeReeferBean() {
        registration = new Registration();
        registration.setMasterCustomer(new MasterCustomer());
        registration.setPlanningVessel(new PlanningVessel());
        registration.getPlanningVessel().setPreserviceVessel(new PreserviceVessel());
        registration.getPlanningVessel().getPreserviceVessel().setMasterVessel(new MasterVessel());
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
        reeferDischargeServices = reeferDischargeServiceFacade.findReeferDischargeServiceByPPKBnReg(registration.getPlanningVessel().getNoPpkb(), no_reg);
        serviceContDischarges = serviceContDischargeFacade.findReeferDischargeService(registration.getPlanningVessel().getNoPpkb());
    }

    public void handleAdd(ActionEvent event){
        reeferDischargeService = new ReeferDischargeService();
        reeferDischargeService.setMasterContainerType(new MasterContainerType());
        reeferDischargeService.setMasterCommodity(new MasterCommodity());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyMM");
        String tgl = dateFormat.format(Calendar.getInstance().getTime());
        String i;
        String id = reeferDischargeServiceFacade.generateId(tgl.substring(2));
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
        reeferDischargeService.setJobSlip("15"+i);
    }

    public void handleSelectContPick(ActionEvent event){
        Integer id_cont = (Integer) event.getComponent().getAttributes().get("idCont");
        ServiceContDischarge scd = serviceContDischargeFacade.find(id_cont);
        reeferDischargeService.setContNo(scd.getContNo());
        reeferDischargeService.setMlo(scd.getMlo());
        reeferDischargeService.setContSize(scd.getContSize());
        reeferDischargeService.setContGross(scd.getContGross());
        reeferDischargeService.setContStatus(scd.getContStatus());
        reeferDischargeService.setOverSize(scd.getOverSize());
        reeferDischargeService.setDg(scd.getDangerous());
        reeferDischargeService.setDgLabel(scd.getDgLabel());
        reeferDischargeService.setMasterCommodity(scd.getMasterCommodity());
        reeferDischargeService.setMasterContainerType(scd.getMasterContainerType());

        //set plugOnDate ambil dari service_reefer yang cont_no dan no_ppkb nya sama
        Date plugOn = serviceReeferFacade.findPlugOn(scd.getContNo(), registration.getPlanningVessel().getNoPpkb());
        reeferDischargeService.setPlugOnDate(plugOn);

        // set shift planning : (now() - plugOnDate) / 8, dibulatkan keatas
        Date now = Calendar.getInstance().getTime();
        Long i = now.getTime() - plugOn.getTime();
        int shiftPlan = (int) Math.ceil(Double.parseDouble(String.valueOf(i * 24))/(8 * 86400000));
        if(shiftPlan < 1)
            shiftPlan = 1;
        reeferDischargeService.setShiftPlanning(Short.parseShort(String.valueOf(shiftPlan)));
    }

    public void handleConfirm(ActionEvent event){
        boolean loggedIn = false;
        if(reeferDischargeService.getContNo() == null)
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Warning", "application.transaction.notcompleted");
        else {
            reeferDischargeService.setRegistration(registration);
            reeferDischargeService.setPlanningVessel(registration.getPlanningVessel());
            reeferDischargeServiceFacade.edit(reeferDischargeService);
            loggedIn = true;

            // insert ke perhitungan_plugging
            perhitunganPlugging.setRegistration(registration);
            perhitunganPlugging.setContNo(reeferDischargeService.getContNo());
            perhitunganPlugging.setMlo(reeferDischargeService.getMlo());
            perhitunganPlugging.setNoPpkb(registration.getPlanningVessel().getNoPpkb());

            // menentukan id activity
            String actPlug = null;
            if(reeferDischargeService.getContSize() == 20)
                actPlug = "D001";
            else
                actPlug = "D002";
            perhitunganPlugging.setMasterActivity(masterActivityFacade.find(actPlug));

            // set amount berdasarkan tipe pelayaran
            BigDecimal cPlugD = BigDecimal.ZERO;
            BigDecimal cPlugI = BigDecimal.ZERO;
            for(Object[] o1 : masterTarifContFacade.findAmountByActivity(actPlug)){
                if(o1[1].equals("IDR"))
                    cPlugD = (BigDecimal) o1[0];
                else
                    cPlugI = (BigDecimal) o1[0];
            }

            // insert ke perhitungan_monitoring
            perhitunganMonitoring.setRegistration(registration);
            perhitunganMonitoring.setContNo(reeferDischargeService.getContNo());
            perhitunganMonitoring.setMlo(reeferDischargeService.getMlo());
            perhitunganMonitoring.setNoPpkb(registration.getPlanningVessel().getNoPpkb());

            // menentukan id activity
            String actMon = null;
            if(reeferDischargeService.getContSize() == 20)
                actMon = "D003";
            else
                actMon = "D004";
            perhitunganMonitoring.setMasterActivity(masterActivityFacade.find(actMon));

            // set amount berdasarkan tipe pelayaran
            BigDecimal cMonD = BigDecimal.ZERO;
            BigDecimal cMonI = BigDecimal.ZERO;
            for(Object[] o1 : masterTarifContFacade.findAmountByActivity(actMon)){
                if(o1[1].equals("IDR"))
                    cMonD = (BigDecimal) o1[0];
                else
                    cMonI = (BigDecimal) o1[0];
            }

            BigDecimal cPlug = BigDecimal.ZERO;
            BigDecimal cMon = BigDecimal.ZERO;
            if (registration.getPlanningVessel().getTipePelayaran().equals("d")) {
                perhitunganPlugging.setCurrCode("IDR");
                perhitunganMonitoring.setCurrCode("IDR");
                cPlug = cPlugD;
                cMon = cMonD;
            } else {
                perhitunganPlugging.setCurrCode("USD");
                perhitunganMonitoring.setCurrCode("USD");
                cPlug = cPlugI;
                cMon = cMonI;
            }
            perhitunganPlugging.setCharge(cPlug);
            perhitunganPlugging.setTotalCharge(perhitunganPlugging.getCharge().multiply(BigDecimal.valueOf(reeferDischargeService.getShiftPlanning())));
            perhitunganPluggingFacade.edit(perhitunganPlugging);

            perhitunganMonitoring.setCharge(cMon);
            perhitunganMonitoring.setTotalCharge(perhitunganMonitoring.getCharge().multiply(BigDecimal.valueOf(reeferDischargeService.getShiftPlanning())));
            perhitunganMonitoringFacade.edit(perhitunganMonitoring);

            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
            RequestContext.getCurrentInstance().addCallbackParam("loggedIn", loggedIn);
            reeferDischargeServices = reeferDischargeServiceFacade.findReeferDischargeServiceByPPKBnReg(registration.getPlanningVessel().getNoPpkb(), no_reg);
            serviceContDischarges = serviceContDischargeFacade.findReeferDischargeService(registration.getPlanningVessel().getNoPpkb());
        }
    }

    public void handleEditDelete(ActionEvent event){
        String job_slip = (String) event.getComponent().getAttributes().get("jobSlip");
        String no_cont = (String) event.getComponent().getAttributes().get("noCont");
        reeferDischargeService = reeferDischargeServiceFacade.find(job_slip);
        Integer idMon = perhitunganMonitoringFacade.findInvoice(no_cont, registration.getPlanningVessel().getNoPpkb(), no_reg);
        perhitunganMonitoring = perhitunganMonitoringFacade.find(idMon);
        Integer idPlug = perhitunganPluggingFacade.findInvoice(no_cont, registration.getPlanningVessel().getNoPpkb(), no_reg);
        perhitunganPlugging = perhitunganPluggingFacade.find(idPlug);
    }

    public void handleDelete(ActionEvent event){
        reeferDischargeServiceFacade.remove(reeferDischargeService);
        perhitunganMonitoringFacade.remove(perhitunganMonitoring);
        perhitunganPluggingFacade.remove(perhitunganPlugging);
        FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.delete.succeeded");
        reeferDischargeServices = reeferDischargeServiceFacade.findReeferDischargeServiceByPPKBnReg(registration.getPlanningVessel().getNoPpkb(), no_reg);
        serviceContDischarges = serviceContDischargeFacade.findReeferDischargeService(registration.getPlanningVessel().getNoPpkb());
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

    private ServiceContDischargeFacadeRemote lookupServiceContDischargeFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (ServiceContDischargeFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/ServiceContDischargeFacade!com.pelindo.ebtos.ejb.facade.remote.ServiceContDischargeFacadeRemote");
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

    private ReeferDischargeServiceFacadeRemote lookupReeferDischargeServiceFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (ReeferDischargeServiceFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/ReeferDischargeServiceFacade!com.pelindo.ebtos.ejb.facade.remote.ReeferDischargeServiceFacadeRemote");
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
     * @return the reeferDischargeService
     */
    public ReeferDischargeService getReeferDischargeService() {
        return reeferDischargeService;
    }

    /**
     * @param reeferDischargeService the reeferDischargeService to set
     */
    public void setReeferDischargeService(ReeferDischargeService reeferDischargeService) {
        this.reeferDischargeService = reeferDischargeService;
    }

    /**
     * @return the reeferDischargeServices
     */
    public List<Object[]> getReeferDischargeServices() {
        return reeferDischargeServices;
    }

    /**
     * @param reeferDischargeServices the reeferDischargeServices to set
     */
    public void setReeferDischargeServices(List<Object[]> reeferDischargeServices) {
        this.reeferDischargeServices = reeferDischargeServices;
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
     * @return the serviceContDischarges
     */
    public List<Object[]> getServiceContDischarges() {
        return serviceContDischarges;
    }

    /**
     * @param serviceContDischarges the serviceContDischarges to set
     */
    public void setServiceContDischarges(List<Object[]> serviceContDischarges) {
        this.serviceContDischarges = serviceContDischarges;
    }

}
