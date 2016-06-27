/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.bean;

import com.qtasnim.jsf.FacesHelper;
import com.pelindo.ebtos.ejb.facade.remote.MasterCommodityFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterPortFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterSettingAppFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterTarifContFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterYardFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningVesselFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ReceivingUcFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.RegistrationFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceLoadUcFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceVesselFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.UcPerhitunganLiftFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.UcReceivingServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.UncontainerizedServiceFacadeRemote;
import com.pelindo.ebtos.model.db.PlanningVessel;
import com.pelindo.ebtos.model.db.PreserviceVessel;
import com.pelindo.ebtos.model.db.Registration;
import com.pelindo.ebtos.model.db.UcPerhitunganLift;
import com.pelindo.ebtos.model.db.UcReceivingService;
import com.pelindo.ebtos.model.db.UncontainerizedService;
import com.pelindo.ebtos.model.db.master.MasterCustomer;
import com.pelindo.ebtos.model.db.master.MasterPort;
import com.pelindo.ebtos.model.db.master.MasterVessel;
import com.pelindo.ebtos.model.db.master.MasterYard;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.primefaces.context.RequestContext;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author dycode-java
 */
@ManagedBean(name = "entryReceivingUcServiceBean")
@ViewScoped
public class EntryReceivingUcServiceBean implements Serializable {

    ServiceVesselFacadeRemote serviceVesselFacade = lookupServiceVesselFacadeRemote();
    ServiceLoadUcFacadeRemote serviceLoadUcFacade = lookupServiceLoadUcFacadeRemote();
    //ReceivingUcFacadeRemote receivingUcFacade = lookupReceivingUcFacadeRemote();
    RegistrationFacadeRemote registrationFacade = lookupRegistrationFacadeRemote();
    @EJB
    MasterTarifContFacadeRemote masterTarifContFacadeRemote;
    @EJB
    UcPerhitunganLiftFacadeRemote ucPerhitunganLiftFacadeRemote;
    private List<Object[]> registrations = lookupRegistrationFacadeRemote().findRegistrationRecUC();
    private List<Object[]> receivingUcs;
    private List<Object[]> UncontainerizedList;
    private UncontainerizedService uncontainerizedService;
    private Registration registration;
    private UcReceivingService receivingUc;
    private String no_reg;
    private PlanningVessel planningVessel;
    private Boolean isEdit = false;
    private MasterSettingAppFacadeRemote masterSettingAppFacadeRemote = lookupMasterSettingAppFacadeRemote();
    private String act = null;
    private String CurrencyCode = null;
    private UcPerhitunganLift ucPerhitunganLift;
    private List<MasterYard> masterYards = lookupMasterYardFacadeRemote().findAll();
    private List<MasterPort> masterPorts = lookupMasterPortFacadeRemote().findAll();
    private List<Object[]> masterCommoditys = lookupMasterCommodityFacadeRemote().findMasterCommoditys();

    /** Creates a new instance of EntryReceivingUcServiceBean */
    public EntryReceivingUcServiceBean() {
        registration = new Registration();
        registration.setMasterCustomer(new MasterCustomer());
        registration.setPlanningVessel(new PlanningVessel());
        registration.getPlanningVessel().setPreserviceVessel(new PreserviceVessel());
        registration.getPlanningVessel().getPreserviceVessel().setMasterVessel(new MasterVessel());
        receivingUc = new UcReceivingService();
        planningVessel = new PlanningVessel();
        uncontainerizedService = new UncontainerizedService();
        this.isEdit = Boolean.FALSE;
        this.act = null;
        this.CurrencyCode = null;
        ucPerhitunganLift = new UcPerhitunganLift();
    }

    public void handleSelectRegistration(ActionEvent event) {
        no_reg = (String) event.getComponent().getAttributes().get("noReg");
        registration = registrationFacade.find(no_reg);
        receivingUcs = lookupUcReceivingServiceFacadeRemote().findByUcReceivingServices(no_reg);
        UncontainerizedList = lookupUncontainerizedServiceFacadeRemote().findByEntryReceivingUcLoad(registration.getPlanningVessel().getNoPpkb());
        planningVessel = lookupPlanningVesselFacadeRemote().find(registration.getPlanningVessel().getNoPpkb());
    }

    public void handleAdd(ActionEvent event) {
        this.isEdit = Boolean.FALSE;
        receivingUc = new UcReceivingService();
        uncontainerizedService = new UncontainerizedService();
        uncontainerizedService.setOperation("LOAD");
        uncontainerizedService.setLoadPort(masterSettingAppFacadeRemote.findImplementedPortCode());
        //receivingUc.setMasterCommodity(new MasterCommodity());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyMM");
        String tgl = dateFormat.format(Calendar.getInstance().getTime());
        String i;
        String id = lookupUcReceivingServiceFacadeRemote().generateId(tgl.substring(2));
        if (id == null) {
            i = "00001";
        } else {
            i = String.valueOf(Integer.valueOf(id) + 1);
        }

        if (i.length() == 1) {
            i = tgl + "0000" + i;
        } else if (i.length() == 2) {
            i = tgl + "000" + i;
        } else if (i.length() == 3) {
            i = tgl + "00" + i;
        } else if (i.length() == 4) {
            i = tgl + "0" + i;
        } else {
            i = tgl + i;
        }
        receivingUc.setJobslip("13" + i);
    }

    public void handleSelectPlanningUC(ActionEvent event) {
        uncontainerizedService = new UncontainerizedService();
        Integer id = (Integer) event.getComponent().getAttributes().get("idd");
        uncontainerizedService = lookupUncontainerizedServiceFacadeRemote().find(id);
        System.out.println(uncontainerizedService.getTruckLoosing() + uncontainerizedService.getBlock());
    }

    public void handleConfirm(ActionEvent event) {
        boolean loggedIn = false;
        if (uncontainerizedService.getBlNo() == null) {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.save.failed");
        } else {
            loggedIn = true;
            this.handleActLift();
            BigDecimal tarif = BigDecimal.ZERO;
            if (registration.getPlanningVessel().getTipePelayaran().equalsIgnoreCase("d")) {
                tarif = masterTarifContFacadeRemote.findByCurrCodeAndActivity("IDR", act);
                setCurrencyCode("IDR");
            } else if (registration.getPlanningVessel().getTipePelayaran().equalsIgnoreCase("i")) {
                tarif = masterTarifContFacadeRemote.findByCurrCodeAndActivity("USD", act);
                setCurrencyCode("USD");
            }
            
            if (uncontainerizedService.getIdUc() == null) {
                uncontainerizedService.setStatus("UNPLANNED");
                uncontainerizedService.setIsShifting("FALSE");
                uncontainerizedService.setIsDelivery("FALSE");
                uncontainerizedService.setOperation("LOAD");
                uncontainerizedService.setNoPpkb(planningVessel.getNoPpkb());
                receivingUc.setUncontainerizedService(uncontainerizedService);
                receivingUc.setValidDate(planningVessel.getCloseRec());
                receivingUc.setReceivingDate(Calendar.getInstance().getTime());
                receivingUc.setRegistration(registration);
                lookupUcReceivingServiceFacadeRemote().create(receivingUc);
            } else {
                receivingUc.setValidDate(planningVessel.getCloseRec());
                receivingUc.setUncontainerizedService(uncontainerizedService);
                receivingUc.setReceivingDate(Calendar.getInstance().getTime());
                receivingUc.setRegistration(registration);
                lookupUcReceivingServiceFacadeRemote().edit(receivingUc);

                lookupUncontainerizedServiceFacadeRemote().edit(uncontainerizedService);
            }

            ucPerhitunganLift.setActivityCode(act);
            ucPerhitunganLift.setCharge(tarif);
            ucPerhitunganLift.setJobslip(receivingUc.getJobslip());
            ucPerhitunganLift.setNoReg(receivingUc.getRegistration().getNoReg());
            ucPerhitunganLift.setOperation("LOAD");
            ucPerhitunganLift.setTotalCharge(tarif);
            ucPerhitunganLift.setCurrCode(CurrencyCode);
            ucPerhitunganLiftFacadeRemote.edit(ucPerhitunganLift);

            receivingUcs = lookupUcReceivingServiceFacadeRemote().findByUcReceivingServices(no_reg);
            UncontainerizedList = lookupUncontainerizedServiceFacadeRemote().findByEntryReceivingUcLoad(registration.getPlanningVessel().getNoPpkb());
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
            RequestContext.getCurrentInstance().addCallbackParam("loggedIn", loggedIn);
        }
        uncontainerizedService = new UncontainerizedService();
        ucPerhitunganLift = new UcPerhitunganLift();
        receivingUc = new UcReceivingService();
    }

    public void handleActLift() {
        if (uncontainerizedService.getWeight() <= 20000) {
            setAct("P007");
        } else if (uncontainerizedService.getWeight() > 20000 && uncontainerizedService.getWeight() <= 35000) {
            setAct("P008");
        } else if (uncontainerizedService.getWeight() > 35000) {
            setAct("P009");
        }
    }

    public void handleSelectRecUC(ActionEvent event) {
        String job_slip = (String) event.getComponent().getAttributes().get("jobSlip");
        receivingUc = lookupUcReceivingServiceFacadeRemote().find(job_slip);
        uncontainerizedService = lookupUncontainerizedServiceFacadeRemote().find(receivingUc.getRegistration().getNoReg());
        Integer id = ucPerhitunganLiftFacadeRemote.findByUcPerhitunganLift(job_slip);
        ucPerhitunganLift = ucPerhitunganLiftFacadeRemote.find(id);
        this.isEdit = Boolean.TRUE;
    }

    public void handleSelectCommodity(ActionEvent event) {
        String commodity_code = (String) event.getComponent().getAttributes().get("cust_code");
        uncontainerizedService.setCommodity(commodity_code);
    }

    public void handleDelete(ActionEvent event) {
        try {
            lookupUcReceivingServiceFacadeRemote().remove(receivingUc);
            ucPerhitunganLiftFacadeRemote.remove(ucPerhitunganLift);
            if (uncontainerizedService.getStatus().equals("UNPLANNED")) {
                lookupUncontainerizedServiceFacadeRemote().remove(uncontainerizedService);
            }
            receivingUcs = lookupUcReceivingServiceFacadeRemote().findByUcReceivingServices(no_reg);
            UncontainerizedList = lookupUncontainerizedServiceFacadeRemote().findByEntryReceivingUcLoad(registration.getPlanningVessel().getNoPpkb());
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.delete.succeeded");
        } catch (EJBException ex) {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.delete.failed");
            ex.printStackTrace();
        }

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

    private ReceivingUcFacadeRemote lookupReceivingUcFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (ReceivingUcFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/ReceivingUcFacade!com.pelindo.ebtos.ejb.facade.remote.ReceivingUcFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private ServiceLoadUcFacadeRemote lookupServiceLoadUcFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (ServiceLoadUcFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/ServiceLoadUcFacade!com.pelindo.ebtos.ejb.facade.remote.ServiceLoadUcFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private MasterYardFacadeRemote lookupMasterYardFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (MasterYardFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/MasterYardFacade!com.pelindo.ebtos.ejb.facade.remote.MasterYardFacadeRemote");
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

    private MasterPortFacadeRemote lookupMasterPortFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (MasterPortFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/MasterPortFacade!com.pelindo.ebtos.ejb.facade.remote.MasterPortFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private ServiceVesselFacadeRemote lookupServiceVesselFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (ServiceVesselFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/ServiceVesselFacade!com.pelindo.ebtos.ejb.facade.remote.ServiceVesselFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private UncontainerizedServiceFacadeRemote lookupUncontainerizedServiceFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (UncontainerizedServiceFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/UncontainerizedServiceFacade!com.pelindo.ebtos.ejb.facade.remote.UncontainerizedServiceFacadeRemote");
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

    private UcReceivingServiceFacadeRemote lookupUcReceivingServiceFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (UcReceivingServiceFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/UcReceivingServiceFacade!com.pelindo.ebtos.ejb.facade.remote.UcReceivingServiceFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    public List<Object[]> getMasterCommoditys() {
        return masterCommoditys;
    }

    public void setMasterCommoditys(List<Object[]> masterCommoditys) {
        this.masterCommoditys = masterCommoditys;
    }

    public List<MasterPort> getMasterPorts() {
        return masterPorts;
    }

    public void setMasterPorts(List<MasterPort> masterPorts) {
        this.masterPorts = masterPorts;
    }

    public List<MasterYard> getMasterYards() {
        return masterYards;
    }

    public void setMasterYards(List<MasterYard> masterYards) {
        this.masterYards = masterYards;
    }

    public UcPerhitunganLift getUcPerhitunganLift() {
        return ucPerhitunganLift;
    }

    public void setUcPerhitunganLift(UcPerhitunganLift ucPerhitunganLift) {
        this.ucPerhitunganLift = ucPerhitunganLift;
    }

    public String getCurrencyCode() {
        return CurrencyCode;
    }

    public void setCurrencyCode(String CurrencyCode) {
        this.CurrencyCode = CurrencyCode;
    }

    public Boolean getIsEdit() {
        return isEdit;
    }

    public void setIsEdit(Boolean isEdit) {
        this.isEdit = isEdit;
    }

    public UncontainerizedService getUncontainerizedService() {
        return uncontainerizedService;
    }

    public void setUncontainerizedService(UncontainerizedService uncontainerizedService) {
        this.uncontainerizedService = uncontainerizedService;
    }

    public PlanningVessel getPlanningVessel() {
        return planningVessel;
    }

    public void setPlanningVessel(PlanningVessel planningVessel) {
        this.planningVessel = planningVessel;
    }

    public List<Object[]> getUncontainerizedList() {
        return UncontainerizedList;
    }

    public void setUncontainerizedList(List<Object[]> UncontainerizedList) {
        this.UncontainerizedList = UncontainerizedList;
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
     * @return the receivingUc
     */
    public UcReceivingService getReceivingUc() {
        return receivingUc;
    }

    /**
     * @param receivingUc the receivingUc to set
     */
    public void setReceivingUc(UcReceivingService receivingUc) {
        this.receivingUc = receivingUc;
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
     * @return the receivingUcs
     */
    public List<Object[]> getReceivingUcs() {
        return receivingUcs;
    }

    /**
     * @param receivingUcs the receivingUcs to set
     */
    public void setReceivingUcs(List<Object[]> receivingUcs) {
        this.receivingUcs = receivingUcs;
    }

    /**
     * @return the act
     */
    public String getAct() {
        return act;
    }

    /**
     * @param act the act to set
     */
    public void setAct(String act) {
        this.act = act;
    }


    private MasterSettingAppFacadeRemote lookupMasterSettingAppFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (MasterSettingAppFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/MasterSettingAppFacade!com.pelindo.ebtos.ejb.facade.remote.MasterSettingAppFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
