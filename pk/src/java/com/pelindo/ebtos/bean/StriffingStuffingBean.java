/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.bean;

import com.qtasnim.url.UrlHelper;
import com.pelindo.ebtos.bean.security.LoginSessionBean;
import com.pelindo.ebtos.ejb.facade.remote.PlanningContLoadFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ReceivingServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.RegistrationFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceContDischargeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceReceivingFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.StrippingServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.StuffingServiceFacadeRemote;
import com.pelindo.ebtos.model.db.PlanningVessel;
import com.pelindo.ebtos.model.db.PreserviceVessel;
import com.pelindo.ebtos.model.db.ReceivingService;
import com.pelindo.ebtos.model.db.Registration;
import com.pelindo.ebtos.model.db.ServiceContDischarge;
import com.pelindo.ebtos.model.db.ServiceReceiving;
import com.pelindo.ebtos.model.db.StrippingService;
import com.pelindo.ebtos.model.db.StuffingService;
import com.pelindo.ebtos.model.db.master.MasterCustomer;
import com.pelindo.ebtos.model.db.master.MasterService;
import com.pelindo.ebtos.model.db.master.MasterVessel;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.apache.commons.lang3.StringEscapeUtils;
import org.primefaces.context.RequestContext;

/**
 *
 * @author dycoder
 */
@ManagedBean(name = "striffingStuffingBean")
@ViewScoped
public class StriffingStuffingBean implements Serializable {

    @EJB
    private ServiceContDischargeFacadeRemote serviceContDischargeFacadeRemote;
    @EJB
    private StrippingServiceFacadeRemote strippingServiceFacadeRemote;
    @EJB
    private StuffingServiceFacadeRemote stuffingServiceFacadeRemote;
    @EJB
    private ServiceReceivingFacadeRemote serviceReceivingFacadeRemote;
    @EJB
    private RegistrationFacadeRemote registrationFacade;
    @EJB
    private ReceivingServiceFacadeRemote receivingServiceFacadeRemote;
    
    private List<Object[]> registrations;
    private List<Object[]> listJobslip;
    private Registration registration;
    private Object[] serviceConDschargeObject;
    private ServiceContDischarge serviceContDischarge;
    private Object[] serviceReceivingObject;
    private ServiceReceiving serviceReceiving;
    private String no_reg;
    private String tipe = "";
    private String jobslip;
    private Boolean panel = true;
    private Boolean panelDel = false;
    private StrippingService strippingService;
    private StuffingService stuffingService;
    private Boolean panelRec = false;
    private String block;
    private Short slot;
    private Short row;
    private Short tier;
    private String tipeAll;
    private Boolean enablePrint;
    private Boolean enablePrintAll;
    private LoginSessionBean loginSessionBean;

    /** Creates a new instance of StriffingStuffingBean */
    public StriffingStuffingBean() {}

    @PostConstruct
    private void construct() {
        registration = new Registration();
        registration.setMasterService(new MasterService());
        registration.setMasterCustomer(new MasterCustomer());
        registration.setPlanningVessel(new PlanningVessel());
        registration.getPlanningVessel().setPreserviceVessel(new PreserviceVessel());
        registration.getPlanningVessel().getPreserviceVessel().setMasterVessel(new MasterVessel());
        serviceContDischarge = new ServiceContDischarge();
        serviceReceiving = new ServiceReceiving();
        loginSessionBean = LoginSessionBean.getCurrentInstance();

        registrations = registrationFacade.findRegistrationStriffingStuffing();
    }

    public void handleSelectRegistration(ActionEvent event) {
        no_reg = (String) event.getComponent().getAttributes().get("noReg");
        registration = registrationFacade.find(no_reg);
        if (registration.getMasterService().getServiceCode().equalsIgnoreCase("SC006")) {
            listJobslip = strippingServiceFacadeRemote.findStrippingServiceByReg(no_reg);
            tipeAll = "strippingAll";
        } else if (registration.getMasterService().getServiceCode().equalsIgnoreCase("SC007")) {
            listJobslip = stuffingServiceFacadeRemote.findStuffingServiceByReg(no_reg);
            tipeAll = "stuffingAll";
        }
        cekPrintAll();
    }

    public void report(ActionEvent event) {
        jobslip = (String) event.getComponent().getAttributes().get("jobSlip");
        if (registration.getMasterService().getServiceCode().equalsIgnoreCase("SC006")) {
            panelDel = true;
            strippingService = strippingServiceFacadeRemote.find(jobslip);
            serviceConDschargeObject = serviceContDischargeFacadeRemote.findByPpkbAndContNo(strippingService.getRegistration().getPlanningVessel().getNoPpkb(), strippingService.getContNo());

            if (serviceConDschargeObject != null) {
                serviceContDischarge = serviceContDischargeFacadeRemote.find(serviceConDschargeObject[0]);
                block = serviceContDischarge.getMasterYard().getBlock();
                slot = serviceContDischarge.getYSlot();
                row = serviceContDischarge.getYRow();
                tier = serviceContDischarge.getYTier();
            }

            tipeAll = "stripping";
        } else if (registration.getMasterService().getServiceCode().equalsIgnoreCase("SC007")) {
            stuffingService = stuffingServiceFacadeRemote.find(jobslip);
            serviceConDschargeObject = serviceContDischargeFacadeRemote.findByPpkbAndContNo(stuffingService.getRegistration().getPlanningVessel().getNoPpkb(), stuffingService.getContNo());
            serviceReceivingObject = serviceReceivingFacadeRemote.findByPpkbAndContNo(stuffingService.getRegistration().getPlanningVessel().getNoPpkb(), stuffingService.getContNo());

            if (serviceConDschargeObject != null) {
                serviceContDischarge = serviceContDischargeFacadeRemote.find(serviceConDschargeObject[0]);
                block = serviceContDischarge.getMasterYard().getBlock();
                slot = serviceContDischarge.getYSlot();
                row = serviceContDischarge.getYRow();
                tier = serviceContDischarge.getYTier();
            } else if (serviceReceivingObject != null) {
                serviceReceiving = serviceReceivingFacadeRemote.find(serviceReceivingObject[0]);
                block = serviceReceiving.getMasterYard().getBlock();
                slot = serviceReceiving.getYSlot();
                row = serviceReceiving.getYRow();
                tier = serviceReceiving.getYTier();
            }

            panelRec = true;
            tipeAll = "stuffing";
        }
        panel = false;
        cekPrint();
    }

    public void tutup(ActionEvent event) {
        panel = true;
        panelDel = false;
        panelRec = false;
        
        if (registration.getMasterService().getServiceCode().equalsIgnoreCase("SC006")) {
            tipeAll = "strippingAll";
        } else if (registration.getMasterService().getServiceCode().equalsIgnoreCase("SC007")) {
            tipeAll = "stuffingAll";
        }

        cekPrintAll();
    }

    public void handleClick() {
        if (registration.getMasterService().getServiceCode().equalsIgnoreCase("SC006")) {
            listJobslip = strippingServiceFacadeRemote.findStrippingServiceByReg(no_reg);
            for (Object[] csl : listJobslip) {
                strippingService = strippingServiceFacadeRemote.find(csl[0]);
                ReceivingService receivingService = strippingService.getExStrippingLoadContainer();
                receivingService.setCounterPrint(receivingService.getCounterPrint() + 1);
                
                strippingService.setCounterPrint(strippingService.getCounterPrint() + 1);
                strippingServiceFacadeRemote.edit(strippingService);
                receivingServiceFacadeRemote.edit(receivingService);
            }
        } else if (registration.getMasterService().getServiceCode().equalsIgnoreCase("SC007")) {
            listJobslip = stuffingServiceFacadeRemote.findStuffingServiceByReg(no_reg);
            for (Object[] csl : listJobslip) {
                stuffingService = stuffingServiceFacadeRemote.find(csl[0]);
                ReceivingService receivingService = stuffingService.getExStuffingLoadContainer();
                receivingService.setCounterPrint(receivingService.getCounterPrint() + 1);

                stuffingService.setCounterPrint(stuffingService.getCounterPrint() + 1);
                stuffingServiceFacadeRemote.edit(stuffingService);
                receivingServiceFacadeRemote.edit(receivingService);
            }
        }
        cekPrintAll();
    }

    public void handleClick2() {
        if (registration.getMasterService().getServiceCode().equalsIgnoreCase("SC006")) {
            strippingService = strippingServiceFacadeRemote.find(jobslip);
            ReceivingService receivingService = strippingService.getExStrippingLoadContainer();
            receivingService.setCounterPrint(receivingService.getCounterPrint() + 1);
            
            strippingService.setCounterPrint(strippingService.getCounterPrint() + 1);
            strippingServiceFacadeRemote.edit(strippingService);
            receivingServiceFacadeRemote.edit(receivingService);
        } else if (registration.getMasterService().getServiceCode().equalsIgnoreCase("SC007")) {
            stuffingService = stuffingServiceFacadeRemote.find(jobslip);
            ReceivingService receivingService = stuffingService.getExStuffingLoadContainer();
            receivingService.setCounterPrint(receivingService.getCounterPrint() + 1);

            stuffingService.setCounterPrint(stuffingService.getCounterPrint() + 1);
            stuffingServiceFacadeRemote.edit(stuffingService);
            receivingServiceFacadeRemote.edit(receivingService);
        }
        cekPrint();
    }

    public void cekPrint() {
        if (loginSessionBean.isSupervisor() == true) {
            enablePrint = Boolean.FALSE;
            enablePrintAll = Boolean.FALSE;
        } else {
            if (registration.getMasterService().getServiceCode().equalsIgnoreCase("SC006")) {
                strippingService = strippingServiceFacadeRemote.find(jobslip);
                if (strippingService.getCounterPrint() != null && strippingService.getCounterPrint() >= 1) {
                    enablePrint = Boolean.TRUE;
                } else {
                    enablePrint = Boolean.FALSE;
                }
            } else if (registration.getMasterService().getServiceCode().equalsIgnoreCase("SC007")) {
                stuffingService = stuffingServiceFacadeRemote.find(jobslip);
                if (stuffingService.getCounterPrint() != null && stuffingService.getCounterPrint() >= 1) {
                    enablePrint = Boolean.TRUE;
                } else {
                    enablePrint = Boolean.FALSE;
                }
            }
        }
    }

    public void cekPrintAll() {
        List<Object[]> cekList = null;
        if (loginSessionBean.isSupervisor() == true) {
            enablePrint = Boolean.FALSE;
            enablePrintAll = Boolean.FALSE;
        } else {
            if (registration.getMasterService().getServiceCode().equalsIgnoreCase("SC006")) {
                cekList = strippingServiceFacadeRemote.findByNoregValidasiPrint(no_reg);
                if (cekList.size() >= 1) {
                    enablePrintAll = Boolean.TRUE;
                } else {
                    enablePrintAll = Boolean.FALSE;
                }
            } else if (registration.getMasterService().getServiceCode().equalsIgnoreCase("SC007")) {
                cekList = stuffingServiceFacadeRemote.findByNoregValidasiPrint(no_reg);
                if (cekList.size() >= 1) {
                    enablePrintAll = Boolean.TRUE;
                } else {
                    enablePrintAll = Boolean.FALSE;
                }
            }
        }
    }

    public void handleDownload(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesContext fc = FacesContext.getCurrentInstance();
        context.addCallbackParam("doPrint", true); //harus ada, buat ngeflag di izinkan ngeprint ato ngga
        context.addCallbackParam("target", (String) event.getComponent().getAttributes().get("target"));
        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3(UrlHelper.resolveFullAddress(fc, "/reportJobslip.pdf?tipe=" + tipeAll + "&no_reg=" + no_reg)));

        //update tabel jobslip counter print 2x
        this.handleClick();
    }

    public void handleDownloadOne(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesContext fc = FacesContext.getCurrentInstance();
        context.addCallbackParam("doPrint", true); //harus ada, buat ngeflag di izinkan ngeprint ato ngga
        context.addCallbackParam("target", (String) event.getComponent().getAttributes().get("target"));
        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3(UrlHelper.resolveFullAddress(fc, "/reportJobslip.pdf?tipe=" + tipeAll + "&job_slip=" + jobslip + "&no_reg=" + no_reg + "&block=" + block + "&slot=" + slot + "&row=" + row + "&tier=" + tier)));

        //update counter jobslip print 2x
        this.handleClick2();

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
     * @return the listJobslip
     */
    public List<Object[]> getListJobslip() {
        return listJobslip;
    }

    /**
     * @param listJobslip the listJobslip to set
     */
    public void setListJobslip(List<Object[]> listJobslip) {
        this.listJobslip = listJobslip;
    }

    /**
     * @return the tipe
     */
    public String getTipe() {
        return tipe;
    }

    /**
     * @param tipe the tipe to set
     */
    public void setTipe(String tipe) {
        this.tipe = tipe;
    }

    /**
     * @return the jobslip
     */
    public String getJobslip() {
        return jobslip;
    }

    /**
     * @param jobslip the jobslip to set
     */
    public void setJobslip(String jobslip) {
        this.jobslip = jobslip;
    }

    /**
     * @return the panel
     */
    public Boolean getPanel() {
        return panel;
    }

    /**
     * @param panel the panel to set
     */
    public void setPanel(Boolean panel) {
        this.panel = panel;
    }

    /**
     * @return the panelDel
     */
    public Boolean getPanelDel() {
        return panelDel;
    }

    /**
     * @param panelDel the panelDel to set
     */
    public void setPanelDel(Boolean panelDel) {
        this.panelDel = panelDel;
    }

    /**
     * @return the panelRec
     */
    public Boolean getPanelRec() {
        return panelRec;
    }

    /**
     * @param panelRec the panelRec to set
     */
    public void setPanelRec(Boolean panelRec) {
        this.panelRec = panelRec;
    }

    /**
     * @return the strippingService
     */
    public StrippingService getStrippingService() {
        return strippingService;
    }

    /**
     * @param strippingService the strippingService to set
     */
    public void setStrippingService(StrippingService strippingService) {
        this.strippingService = strippingService;
    }

    /**
     * @return the stuffingService
     */
    public StuffingService getStuffingService() {
        return stuffingService;
    }

    /**
     * @param stuffingService the stuffingService to set
     */
    public void setStuffingService(StuffingService stuffingService) {
        this.stuffingService = stuffingService;
    }

    /**
     * @return the serviceConDschargeObject
     */
    public Object[] getServiceConDschargeObject() {
        return serviceConDschargeObject;
    }

    /**
     * @param serviceConDschargeObject the serviceConDschargeObject to set
     */
    public void setServiceConDschargeObject(Object[] serviceConDschargeObject) {
        this.serviceConDschargeObject = serviceConDschargeObject;
    }

    /**
     * @return the block
     */
    public String getBlock() {
        return block;
    }

    /**
     * @param block the block to set
     */
    public void setBlock(String block) {
        this.block = block;
    }

    /**
     * @return the slot
     */
    public Short getSlot() {
        return slot;
    }

    /**
     * @param slot the slot to set
     */
    public void setSlot(Short slot) {
        this.slot = slot;
    }

    /**
     * @return the row
     */
    public Short getRow() {
        return row;
    }

    /**
     * @param row the row to set
     */
    public void setRow(Short row) {
        this.row = row;
    }

    /**
     * @return the tier
     */
    public Short getTier() {
        return tier;
    }

    /**
     * @param tier the tier to set
     */
    public void setTier(Short tier) {
        this.tier = tier;
    }

    /**
     * @return the serviceContDischarge
     */
    public ServiceContDischarge getServiceContDischarge() {
        return serviceContDischarge;
    }

    /**
     * @param serviceContDischarge the serviceContDischarge to set
     */
    public void setServiceContDischarge(ServiceContDischarge serviceContDischarge) {
        this.serviceContDischarge = serviceContDischarge;
    }

    /**
     * @return the serviceReceivingObject
     */
    public Object[] getServiceReceivingObject() {
        return serviceReceivingObject;
    }

    /**
     * @param serviceReceivingObject the serviceReceivingObject to set
     */
    public void setServiceReceivingObject(Object[] serviceReceivingObject) {
        this.serviceReceivingObject = serviceReceivingObject;
    }

    /**
     * @return the serviceReceiving
     */
    public ServiceReceiving getServiceReceiving() {
        return serviceReceiving;
    }

    /**
     * @param serviceReceiving the serviceReceiving to set
     */
    public void setServiceReceiving(ServiceReceiving serviceReceiving) {
        this.serviceReceiving = serviceReceiving;
    }

    /**
     * @return the tipeAll
     */
    public String getTipeAll() {
        return tipeAll;
    }

    /**
     * @param tipeAll the tipeAll to set
     */
    public void setTipeAll(String tipeAll) {
        this.tipeAll = tipeAll;
    }

    /**
     * @return the enablePrint
     */
    public Boolean getEnablePrint() {
        return enablePrint;
    }

    /**
     * @param enablePrint the enablePrint to set
     */
    public void setEnablePrint(Boolean enablePrint) {
        this.enablePrint = enablePrint;
    }

    /**
     * @return the enablePrintAll
     */
    public Boolean getEnablePrintAll() {
        return enablePrintAll;
    }

    /**
     * @param enablePrintAll the enablePrintAll to set
     */
    public void setEnablePrintAll(Boolean enablePrintAll) {
        this.enablePrintAll = enablePrintAll;
    }
}
