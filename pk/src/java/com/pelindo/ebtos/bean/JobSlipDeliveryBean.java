/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.bean;

import com.qtasnim.url.UrlHelper;
import com.pelindo.ebtos.bean.security.LoginSessionBean;
import com.pelindo.ebtos.ejb.facade.remote.DeliveryServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ReceivingServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.RegistrationFacadeRemote;
import com.pelindo.ebtos.model.db.DeliveryService;
import com.pelindo.ebtos.model.db.PlanningVessel;
import com.pelindo.ebtos.model.db.PreserviceVessel;
import com.pelindo.ebtos.model.db.ReceivingService;
import com.pelindo.ebtos.model.db.Registration;
import com.pelindo.ebtos.model.db.master.MasterCustomer;
import com.pelindo.ebtos.model.db.master.MasterService;
import com.pelindo.ebtos.model.db.master.MasterVessel;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.apache.commons.lang3.StringEscapeUtils;
import org.primefaces.context.RequestContext;

/**
 *
 * @author dycoder
 */
@ManagedBean(name = "jobSlipDeliveryBean")
@ViewScoped
public class JobSlipDeliveryBean implements Serializable {
    @EJB
    private ReceivingServiceFacadeRemote receivingServiceFacade;
    @EJB
    private DeliveryServiceFacadeRemote deliveryServiceFacade;
    @EJB
    private RegistrationFacadeRemote registrationFacade;
    
    private List<Object[]> registrations;
    private List<Object[]> listJobslip;
    private Registration registration;
    private DeliveryService deliveryService;
    private ReceivingService receivingService;
    private String no_reg;
    private String tipe = "";
    private String jobslip;
    private Boolean panel = Boolean.TRUE;
    private Boolean panelDel = Boolean.FALSE;
    private Boolean panelRec = Boolean.FALSE;
    private Boolean enablePrint;
    private Boolean enablePrintAll;
    private LoginSessionBean loginSessionBean;

    /** Creates a new instance of JobSlipDelivery */
    public JobSlipDeliveryBean() {

    }

    @PostConstruct
    public void construct() {
        registration = new Registration();
        registration.setMasterService(new MasterService());
        registration.setMasterCustomer(new MasterCustomer());
        registration.setPlanningVessel(new PlanningVessel());
        registration.getPlanningVessel().setPreserviceVessel(new PreserviceVessel());
        registration.getPlanningVessel().getPreserviceVessel().setMasterVessel(new MasterVessel());
        loginSessionBean = LoginSessionBean.getCurrentInstance();

        registrations = registrationFacade.findRegistrationDeliveryReceiving();
    }

    public void handleSelectRegistration(ActionEvent event) {
        no_reg = (String) event.getComponent().getAttributes().get("noReg");
        registration = registrationFacade.find(no_reg);
        if (registration.getMasterService().getServiceCode().equals("SC002")) {
            listJobslip = deliveryServiceFacade.findDeliveryServiceByReg(no_reg);
            tipe = "deliveryAll";
        } else if (registration.getMasterService().getServiceCode().equals("SC001") || registration.getMasterService().getServiceCode().equals("SC006")) {
            listJobslip = receivingServiceFacade.findReceivingServiceByNoReg(no_reg);
            tipe = "receivingAll";
        }
        cekPrintAll();
    }

    public void report(ActionEvent event) {
        jobslip = (String) event.getComponent().getAttributes().get("jobSlip");
        if (registration.getMasterService().getServiceCode().equals("SC002") || registration.getMasterService().getServiceCode().equals("SC006")) {
            panelDel = Boolean.TRUE;
            deliveryService = deliveryServiceFacade.find(jobslip);
            tipe = "delivery";
        } else if (registration.getMasterService().getServiceCode().equals("SC001")) {
            panelRec = Boolean.TRUE;
            receivingService = receivingServiceFacade.find(jobslip);
            tipe = "receiving";
        }
        panel = Boolean.FALSE;
        cekPrint();
    }

    public void tutup(ActionEvent event) {
        panel = Boolean.TRUE;
        panelDel = Boolean.FALSE;
        panelRec = Boolean.FALSE;
        if (registration.getMasterService().getServiceCode().equals("SC002")) {
            tipe = "deliveryAll";
        } else if (registration.getMasterService().getServiceCode().equals("SC001") || registration.getMasterService().getServiceCode().equals("SC006")) {
            tipe = "receivingAll";
        }
        cekPrintAll();
    }

    public void handleClick(ActionEvent actionEvent) {
        if (registration.getMasterService().getServiceCode().equals("SC002")) {
            listJobslip = deliveryServiceFacade.findDeliveryServiceByReg(no_reg);
            for (Object[] csl : listJobslip) {
                deliveryService = deliveryServiceFacade.find(csl[0]);
                if (deliveryService.getCounterPrint() == null) {
                    deliveryService.setCounterPrint(0);
                }
                deliveryService.setCounterPrint(deliveryService.getCounterPrint() + 1);
                deliveryServiceFacade.edit(deliveryService);
            }
        } else if (registration.getMasterService().getServiceCode().equals("SC001") || registration.getMasterService().getServiceCode().equals("SC006")) {
            listJobslip = receivingServiceFacade.findReceivingServiceByNoReg(no_reg);
            for (Object[] csl : listJobslip) {
                receivingService = receivingServiceFacade.find(csl[0]);
                if (receivingService.getCounterPrint() == null) {
                    receivingService.setCounterPrint(0);
                }
                receivingService.setCounterPrint(receivingService.getCounterPrint() + 1);
                receivingServiceFacade.edit(receivingService);
            }
        }
        cekPrintAll();
    }

    public void handleClick2(ActionEvent actionEvent) {
        if (registration.getMasterService().getServiceCode().equals("SC002")) {
            deliveryService = deliveryServiceFacade.find(jobslip);
            if (deliveryService.getCounterPrint() == null) {
                deliveryService.setCounterPrint(0);
            }
            deliveryService.setCounterPrint(deliveryService.getCounterPrint() + 1);
            deliveryServiceFacade.edit(deliveryService);
        } else if (registration.getMasterService().getServiceCode().equals("SC001") || registration.getMasterService().getServiceCode().equals("SC006")) {
            receivingService = receivingServiceFacade.find(jobslip);
            if (receivingService.getCounterPrint() == null) {
                receivingService.setCounterPrint(0);
            }
            receivingService.setCounterPrint(receivingService.getCounterPrint() + 1);
            receivingServiceFacade.edit(receivingService);
        }
        cekPrint();
    }

    public void cekPrint() {
        if (loginSessionBean.isSupervisor() == Boolean.TRUE) {
            enablePrint = Boolean.FALSE;
            enablePrintAll = Boolean.FALSE;
        } else {
            if (registration.getMasterService().getServiceCode().equals("SC002")) {
                deliveryService = deliveryServiceFacade.find(jobslip);
                if (deliveryService.getCounterPrint() != null && deliveryService.getCounterPrint() >= 1) {
                    enablePrint = Boolean.TRUE;
                } else {
                    enablePrint = Boolean.FALSE;
                }
            } else if (registration.getMasterService().getServiceCode().equals("SC001") || registration.getMasterService().getServiceCode().equals("SC006")) {
                receivingService = receivingServiceFacade.find(jobslip);
                if (receivingService.getCounterPrint() != null && receivingService.getCounterPrint() >= 1) {
                    enablePrint = Boolean.TRUE;
                } else {
                    enablePrint = Boolean.FALSE;
                }
            }
        }
    }

    public void cekPrintAll() {
        List<Object[]> listValidasi = null;
        if (loginSessionBean.isSupervisor() == Boolean.TRUE) {
            enablePrint = Boolean.FALSE;
            enablePrintAll = Boolean.FALSE;
        } else {
            if (registration.getMasterService().getServiceCode().equals("SC002")) {
                listValidasi = deliveryServiceFacade.findByNoregValidasiPrint(no_reg);
                if (listValidasi.size() >= 1) {

                    enablePrintAll = Boolean.TRUE;
                } else {

                    enablePrintAll = Boolean.FALSE;
                }
            } else if (registration.getMasterService().getServiceCode().equals("SC001") || registration.getMasterService().getServiceCode().equals("SC006")) {
                listValidasi = receivingServiceFacade.findByNoregValidasiPrint(no_reg);
                if (listValidasi.size() >= 1) {

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
        context.addCallbackParam("doPrint", Boolean.TRUE);
        context.addCallbackParam("target", (String) event.getComponent().getAttributes().get("target"));
        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3(UrlHelper.resolveFullAddress(fc, "/reportJobslip.pdf?"
                + "no_reg=" + no_reg
                + "&tipe=" + tipe)));
        
        if (registration.getMasterService().getServiceCode().equals("SC002")) {
            listJobslip = deliveryServiceFacade.findDeliveryServiceByReg(no_reg);
            for (Object[] csl : listJobslip) {
                deliveryService = deliveryServiceFacade.find(csl[0]);
                if (deliveryService.getCounterPrint() == null) {
                    deliveryService.setCounterPrint(0);
                }
                deliveryService.setCounterPrint(deliveryService.getCounterPrint() + 1);
                deliveryServiceFacade.edit(deliveryService);
            }
        } else if (registration.getMasterService().getServiceCode().equals("SC001") || registration.getMasterService().getServiceCode().equals("SC006")) {
            listJobslip = receivingServiceFacade.findReceivingServiceByNoReg(no_reg);
            for (Object[] csl : listJobslip) {
                receivingService = receivingServiceFacade.find(csl[0]);
                if (receivingService.getCounterPrint() == null) {
                    receivingService.setCounterPrint(0);
                }
                receivingService.setCounterPrint(receivingService.getCounterPrint() + 1);
                receivingServiceFacade.edit(receivingService);
            }
        }
        cekPrintAll();
    }

    public void handlePrintAll(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesContext fc = FacesContext.getCurrentInstance();
        context.addCallbackParam("doPrint", Boolean.TRUE);
        context.addCallbackParam("target", (String) event.getComponent().getAttributes().get("target"));
        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3(UrlHelper.resolveFullAddress(fc, "/reportJobslip.pdf?"
                + "no_reg=" + no_reg + "&"
                + "tipe=" + tipe)));
    }

    public void handleDownloadOne(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesContext fc = FacesContext.getCurrentInstance();
        context.addCallbackParam("doPrint", Boolean.TRUE);
        context.addCallbackParam("target", (String) event.getComponent().getAttributes().get("target"));
        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3(UrlHelper.resolveFullAddress(fc, "/reportJobslip.pdf?tipe=" + tipe + "&job_slip=" + jobslip + "&no_reg=" + no_reg)));

        //update counter jobslip print 2x
        if (registration.getMasterService().getServiceCode().equals("SC002")) {
            deliveryService = deliveryServiceFacade.find(jobslip);
            deliveryService.setCounterPrint(deliveryService.getCounterPrint() + 1);
            deliveryServiceFacade.edit(deliveryService);
        } else if (registration.getMasterService().getServiceCode().equals("SC001") || registration.getMasterService().getServiceCode().equals("SC006")) {
            receivingService = receivingServiceFacade.find(jobslip);
            receivingService.setCounterPrint(receivingService.getCounterPrint() + 1);
            receivingServiceFacade.edit(receivingService);
        }
        cekPrint();
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
     * @return the deliveryService
     */
    public DeliveryService getDeliveryService() {
        return deliveryService;
    }

    /**
     * @param deliveryService the deliveryService to set
     */
    public void setDeliveryService(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    /**
     * @return the receivingService
     */
    public ReceivingService getReceivingService() {
        return receivingService;
    }

    /**
     * @param receivingService the receivingService to set
     */
    public void setReceivingService(ReceivingService receivingService) {
        this.receivingService = receivingService;
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

    public LoginSessionBean getLoginSessionBean() {
        return loginSessionBean;
    }

    public void setLoginSessionBean(LoginSessionBean loginSessionBean) {
        this.loginSessionBean = loginSessionBean;
    }
}
