/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.bean;

import com.qtasnim.jsf.FacesHelper;
import com.pelindo.ebtos.ejb.facade.remote.MasterActivityFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterCommodityFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterTarifContFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterTarifHistoryFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PerhitunganLiftBarangFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningVesselFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ReceivingBarangServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.RegistrationFacadeRemote;
import com.pelindo.ebtos.model.db.PerhitunganLiftBarang;
import com.pelindo.ebtos.model.db.PlanningVessel;
import com.pelindo.ebtos.model.db.ReceivingBarangService;
import com.pelindo.ebtos.model.db.Registration;
import com.pelindo.ebtos.model.db.master.MasterCommodity;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.context.RequestContext;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import javax.ejb.EJBException;

/**
 *
 * @author dycoder
 */
@ManagedBean(name = "entryGoodsReceiving")
@ViewScoped
public class EntryGoodsReceiving implements Serializable {

    MasterTarifHistoryFacadeRemote masterTarifHistoryFacade = lookupMasterTarifHistoryFacadeRemote();
    private List<Object[]> receivingBarangServices;
    private List<Object[]> registrations = lookupRegistrationFacadeRemote().findRegistrationReceivingBarang();
    private Registration registration;
    private String no_reg;
    private ReceivingBarangService receivingBarangService;
    private List<Object[]> masterCommodityList = lookupMasterCommodityFacadeRemote().findMasterCommoditys();
    private MasterCommodity masterCommodity;
    private PlanningVessel planningVessel;
    private String no_ppkb;
    private PerhitunganLiftBarang perhitunganLiftBarang;

    /** Creates a new instance of EntryGoodsReceiving */
    public EntryGoodsReceiving() {
        registration = new Registration();
        receivingBarangService = new ReceivingBarangService();
        masterCommodity = new MasterCommodity();
        planningVessel = new PlanningVessel();
        perhitunganLiftBarang = new PerhitunganLiftBarang();
    }

    public void clear() {
        receivingBarangService = new ReceivingBarangService();
        masterCommodity = new MasterCommodity();
        perhitunganLiftBarang = new PerhitunganLiftBarang();
    }

    public void handleSelectRegistration(ActionEvent event) {
        setNo_reg((String) event.getComponent().getAttributes().get("noReg"));
        registration = lookupRegistrationFacadeRemote().find(getNo_reg());
        receivingBarangServices = lookupReceivingBarangServiceFacadeRemote().findReceivingBarangServices(registration.getNoReg());
    }

    public void handleSelectCommodity(ActionEvent event) {
        String idHT = (String) event.getComponent().getAttributes().get("idOperatorCrane");
        masterCommodity = lookupMasterCommodityFacadeRemote().find(idHT);
        receivingBarangService.setCommodityCode(masterCommodity.getCommodityCode());
    }

    public String getGenerateCode() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyMM");
        String tgl = dateFormat.format(Calendar.getInstance().getTime());
        String tempCode;
        String temp = lookupReceivingBarangServiceFacadeRemote().findReceivingBarangServiceByGenerate(tgl.substring(2));
        if (temp == null) {
            tempCode = "00001";
        } else {
            tempCode = String.valueOf(Integer.valueOf(temp) + 1);
        }
        String comCod = "";
        switch (tempCode.length()) {
            case 1:
                tempCode = "0000" + tempCode;
                break;
            case 2:
                tempCode = "000" + tempCode;
                break;
            case 3:
                tempCode = "00" + tempCode;
                break;
            case 4:
                tempCode = "0" + tempCode;
                break;
        }
        comCod = "18" + tgl + tempCode;
        return comCod;
    }

    public void handleEditDelete(ActionEvent event) {
        String job_slip = (String) event.getComponent().getAttributes().get("jobSlip");
        receivingBarangService = lookupReceivingBarangServiceFacadeRemote().find(job_slip);
        masterCommodity = lookupMasterCommodityFacadeRemote().find(receivingBarangService.getCommodityCode());
        Object[] temp;
        temp = lookupPerhitunganLiftBarangFacadeRemote().findPerhitunganLiftBarangByEdit(receivingBarangService.getJobslip(), receivingBarangService.getContNo());
        perhitunganLiftBarang = lookupPerhitunganLiftBarangFacadeRemote().find(temp[0]);
    }

    public void handleDelete(ActionEvent event) {
        try {
            lookupReceivingBarangServiceFacadeRemote().remove(receivingBarangService);
            lookupPerhitunganLiftBarangFacadeRemote().remove(perhitunganLiftBarang);
            receivingBarangServices = lookupReceivingBarangServiceFacadeRemote().findReceivingBarangServices(registration.getNoReg());
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.delete.succeeded");
            this.clear();
        } catch (EJBException ex) {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.delete.failed");
            ex.printStackTrace();
        }

    }

    public void handleCancel(ActionEvent event) {
        this.clear();
    }

    public void handleAdd(ActionEvent event) {
        this.clear();
        receivingBarangService.setJobslip(getGenerateCode());
    }

    public void handleConfirm(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();        
        boolean loggedIn = false;
        BigDecimal tarif = null;
        String jasa = null;
        String actMov = null;
        if (receivingBarangService.getContNo() == null) {
            loggedIn = false;
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.save.failed");
        } else if (receivingBarangService.getVolume() == null) {
            loggedIn = false;
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.save.failed");
        } else if (receivingBarangService.getWeight() == null) {
            loggedIn = false;
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.save.failed");
        } else if (receivingBarangService.getUnit() == null) {
            loggedIn = false;
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.save.failed");
        } else if (receivingBarangService.getCommodityCode() == null) {
            loggedIn = false;
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.save.failed");
        }else {
            loggedIn = true;
            no_ppkb = registration.getPlanningVessel().getNoPpkb();
            setPlanningVessel(lookupPlanningVesselFacadeRemote().find(no_ppkb));

            receivingBarangService.setNoPpkb(no_ppkb);
            receivingBarangService.setNoReg(registration.getNoReg());
            receivingBarangService.setValidDate(planningVessel.getCloseRec());
            //receivingBarangService.setCommodityCode(masterCommodity.getCommodityCode());
            lookupReceivingBarangServiceFacadeRemote().edit(receivingBarangService);
            if (receivingBarangService.getCountBy().equalsIgnoreCase("WEIGHT")) {
                actMov = "N001";
            } else {
                actMov = "N002";
            }
            if (registration.getPlanningVessel().getTipePelayaran().equalsIgnoreCase("d")) {
                tarif = lookupMasterTarifContFacadeRemote().findByCurrCodeAndActivity("IDR", actMov);
                jasa = "IDR";
            } else {
                tarif = lookupMasterTarifContFacadeRemote().findByCurrCodeAndActivity("USD", actMov);
                jasa = "USD";
            }
            perhitunganLiftBarang.setNoReg(receivingBarangService.getNoReg());
            perhitunganLiftBarang.setBlNo(receivingBarangService.getBlNo());
            perhitunganLiftBarang.setContNo(receivingBarangService.getContNo());
            perhitunganLiftBarang.setMlo(receivingBarangService.getMlo());
            perhitunganLiftBarang.setNoPpkb(receivingBarangService.getNoPpkb());
            perhitunganLiftBarang.setCommodityCode(receivingBarangService.getCommodityCode());
            perhitunganLiftBarang.setJobslip(receivingBarangService.getJobslip());
            perhitunganLiftBarang.setOperation("RECEIVING");
            perhitunganLiftBarang.setCurrCode(jasa);
            perhitunganLiftBarang.setActivityCode(actMov);
            perhitunganLiftBarang.setChargeEquipmet(tarif);
            lookupPerhitunganLiftBarangFacadeRemote().edit(perhitunganLiftBarang);

            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
            receivingBarangServices = lookupReceivingBarangServiceFacadeRemote().findReceivingBarangServices(registration.getNoReg());
            this.clear();
        }        
        context.addCallbackParam("loggedIn", loggedIn);
    }

    private ReceivingBarangServiceFacadeRemote lookupReceivingBarangServiceFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (ReceivingBarangServiceFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/ReceivingBarangServiceFacade!com.pelindo.ebtos.ejb.facade.remote.ReceivingBarangServiceFacadeRemote");
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

    private RegistrationFacadeRemote lookupRegistrationFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (RegistrationFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/RegistrationFacade!com.pelindo.ebtos.ejb.facade.remote.RegistrationFacadeRemote");
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

    private PerhitunganLiftBarangFacadeRemote lookupPerhitunganLiftBarangFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (PerhitunganLiftBarangFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/PerhitunganLiftBarangFacade!com.pelindo.ebtos.ejb.facade.remote.PerhitunganLiftBarangFacadeRemote");
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
     * @return the receivingBarangServices
     */
    public List<Object[]> getReceivingBarangServices() {
        return receivingBarangServices;
    }

    /**
     * @param receivingBarangServices the receivingBarangServices to set
     */
    public void setReceivingBarangServices(List<Object[]> receivingBarangServices) {
        this.setReceivingBarangServices(receivingBarangServices);
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
     * @return the no_reg
     */
    public String getNo_reg() {
        return no_reg;
    }

    /**
     * @param no_reg the no_reg to set
     */
    public void setNo_reg(String no_reg) {
        this.no_reg = no_reg;
    }

    /**
     * @return the receivingBarangService
     */
    public ReceivingBarangService getReceivingBarangService() {
        return receivingBarangService;
    }

    /**
     * @param receivingBarangService the receivingBarangService to set
     */
    public void setReceivingBarangService(ReceivingBarangService receivingBarangService) {
        this.receivingBarangService = receivingBarangService;
    }

    /**
     * @return the masterCommodityList
     */
    public List<Object[]> getMasterCommodityList() {
        return masterCommodityList;
    }

    /**
     * @param masterCommodityList the masterCommodityList to set
     */
    public void setMasterCommodityList(List<Object[]> masterCommodityList) {
        this.masterCommodityList = masterCommodityList;
    }

    /**
     * @return the masterCommodity
     */
    public MasterCommodity getMasterCommodity() {
        return masterCommodity;
    }

    /**
     * @param masterCommodity the masterCommodity to set
     */
    public void setMasterCommodity(MasterCommodity masterCommodity) {
        this.masterCommodity = masterCommodity;
    }

    /**
     * @return the serviceVessel
     */
    public PlanningVessel getPlanningVessel() {
        return planningVessel;
    }

    /**
     * @param serviceVessel the serviceVessel to set
     */
    public void setPlanningVessel(PlanningVessel planningVessel) {
        this.planningVessel = planningVessel;
    }

    /**
     * @return the no_ppkb
     */
    public String getNo_ppkb() {
        return no_ppkb;
    }

    /**
     * @param no_ppkb the no_ppkb to set
     */
    public void setNo_ppkb(String no_ppkb) {
        this.no_ppkb = no_ppkb;
    }

    /**
     * @return the perhitunganLiftBarang
     */
    public PerhitunganLiftBarang getPerhitunganLiftBarang() {
        return perhitunganLiftBarang;
    }

    /**
     * @param perhitunganLiftBarang the perhitunganLiftBarang to set
     */
    public void setPerhitunganLiftBarang(PerhitunganLiftBarang perhitunganLiftBarang) {
        this.perhitunganLiftBarang = perhitunganLiftBarang;
    }
}
