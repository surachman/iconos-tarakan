/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.bean;

import com.qtasnim.url.UrlHelper;
import com.pelindo.ebtos.bean.security.LoginSessionBean;
import com.pelindo.ebtos.ejb.facade.remote.DeliveryBarangServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ReceivingBarangServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.RegistrationFacadeRemote;
import com.pelindo.ebtos.model.db.DeliveryBarangService;
import com.pelindo.ebtos.model.db.PlanningVessel;
import com.pelindo.ebtos.model.db.PreserviceVessel;
import com.pelindo.ebtos.model.db.ReceivingBarangService;
import com.pelindo.ebtos.model.db.Registration;
import com.pelindo.ebtos.model.db.master.MasterCustomer;
import com.pelindo.ebtos.model.db.master.MasterService;
import com.pelindo.ebtos.model.db.master.MasterVessel;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.apache.commons.lang3.StringEscapeUtils;
import org.primefaces.context.RequestContext;

/**
 *
 * @author dycoder
 */
@ManagedBean(name = "jobslipDeliveryReceivingGoodsBean")
@ViewScoped
public class JobslipDeliveryReceivingGoodsBean implements Serializable {

    RegistrationFacadeRemote registrationFacade = lookupRegistrationFacadeRemote();
    private List<Object[]> registrations = lookupRegistrationFacadeRemote().findRegistrationDeliveryReceivingGoods();
    private List<Object[]> listJobslip;
    private Registration registration;
    private DeliveryBarangService deliveryService;
    private ReceivingBarangService receivingService;
    private String no_reg;
    private String tipe = "";
    private String jobslip;
    private Boolean panel = true;
    private Boolean panelDel = false;
    private Boolean panelRec = false;
    private Boolean enablePrint;
    private Boolean enablePrintAll;
    private LoginSessionBean loginSessionBean;

    /** Creates a new instance of JobslipDeliveryReceivingGoodsBean */
    public JobslipDeliveryReceivingGoodsBean() {
        registration = new Registration();
        registration.setMasterService(new MasterService());
        registration.setMasterCustomer(new MasterCustomer());
        registration.setPlanningVessel(new PlanningVessel());
        registration.getPlanningVessel().setPreserviceVessel(new PreserviceVessel());
        registration.getPlanningVessel().getPreserviceVessel().setMasterVessel(new MasterVessel());
        listJobslip = new ArrayList<Object[]>();
        loginSessionBean = LoginSessionBean.getCurrentInstance();
    }

    public void handleSelectRegistration(ActionEvent event) {
        no_reg = (String) event.getComponent().getAttributes().get("noReg");
        registration = registrationFacade.find(no_reg);
        if (registration.getMasterService().getServiceCode().equalsIgnoreCase("SC019")) {
            listJobslip = lookupDeliveryBarangServiceFacadeRemote().findByReg(no_reg);
            tipe = "deliveryGoodsAll";
        } else if (registration.getMasterService().getServiceCode().equalsIgnoreCase("SC018")) {
            listJobslip = lookupReceivingBarangServiceFacadeRemote().findReceivingBarangServiceByJobSlip(no_reg);
            tipe = "receivingGoodsAll";
        }
        cekPrintAll();
    }

    public void report(ActionEvent event) {
        jobslip = (String) event.getComponent().getAttributes().get("jobSlip");
        if (registration.getMasterService().getServiceCode().equalsIgnoreCase("SC019")) {
            panelDel = true;
            deliveryService = lookupDeliveryBarangServiceFacadeRemote().find(jobslip);
            tipe = "deliveryGoods";
        } else if (registration.getMasterService().getServiceCode().equalsIgnoreCase("SC018")) {
            panelRec = true;
            receivingService = lookupReceivingBarangServiceFacadeRemote().find(jobslip);
            tipe = "receivingGoods";
        }
        panel = false;
        cekPrint();
    }

    public void tutup(ActionEvent event) {
        panel = true;
        panelDel = false;
        panelRec = false;
        if (registration.getMasterService().getServiceCode().equalsIgnoreCase("SC019")) {
            tipe = "deliveryGoodsAll";
        } else if (registration.getMasterService().getServiceCode().equalsIgnoreCase("SC018")) {
            tipe = "receivingGoodsAll";
        }
        cekPrintAll();
    }

    public void handleClick() {
        if (registration.getMasterService().getServiceCode().equalsIgnoreCase("SC019")) {
            listJobslip = lookupDeliveryBarangServiceFacadeRemote().findByReg(no_reg);
            for (Object[] csl : listJobslip) {
                deliveryService = lookupDeliveryBarangServiceFacadeRemote().find(csl[0]);
//                if (deliveryService.getCounterPrint() == null) {
//                    deliveryService.setCounterPrint(0);
//                }
                deliveryService.setCounterPrint(deliveryService.getCounterPrint() + 1);
                lookupDeliveryBarangServiceFacadeRemote().edit(deliveryService);
            }
        } else if (registration.getMasterService().getServiceCode().equalsIgnoreCase("SC018")) {
            listJobslip = lookupReceivingBarangServiceFacadeRemote().findReceivingBarangServiceByJobSlip(no_reg);
            for (Object[] csl : listJobslip) {
                receivingService = lookupReceivingBarangServiceFacadeRemote().find(csl[0]);
//                if (receivingService.getCounterPrint() == null) {
//                    receivingService.setCounterPrint(0);
//                }
                receivingService.setCounterPrint(receivingService.getCounterPrint() + 1);
                lookupReceivingBarangServiceFacadeRemote().edit(receivingService);
            }
        }
        cekPrintAll();
    }

    public void handleClick2() {
        if (registration.getMasterService().getServiceCode().equalsIgnoreCase("SC019")) {
            deliveryService = lookupDeliveryBarangServiceFacadeRemote().find(jobslip);
//            if (deliveryService.getCounterPrint() == null) {
//                deliveryService.setCounterPrint(0);
//            }
            deliveryService.setCounterPrint(deliveryService.getCounterPrint() + 1);
            lookupDeliveryBarangServiceFacadeRemote().edit(deliveryService);
        } else if (registration.getMasterService().getServiceCode().equalsIgnoreCase("SC018")) {
            receivingService = lookupReceivingBarangServiceFacadeRemote().find(jobslip);
//            if (receivingService.getCounterPrint() == null) {
//                receivingService.setCounterPrint(0);
//            }
            receivingService.setCounterPrint(receivingService.getCounterPrint() + 1);
            lookupReceivingBarangServiceFacadeRemote().edit(receivingService);
        }
        cekPrint();
    }

    public void cekPrint() {
        if (loginSessionBean.isSupervisor() == true) {
            enablePrint = Boolean.FALSE;
            enablePrintAll = Boolean.FALSE;
        } else {
            if (registration.getMasterService().getServiceCode().equalsIgnoreCase("SC002")) {
                deliveryService = lookupDeliveryBarangServiceFacadeRemote().find(jobslip);
                if (deliveryService.getCounterPrint() != null && deliveryService.getCounterPrint() >= 1) {
                    enablePrint = Boolean.TRUE;
                } else {
                    enablePrint = Boolean.FALSE;
                }
            } else if (registration.getMasterService().getServiceCode().equalsIgnoreCase("SC001")) {
                receivingService = lookupReceivingBarangServiceFacadeRemote().find(jobslip);
                if (receivingService.getCounterPrint() != null && receivingService.getCounterPrint() >= 1) {
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
            if (registration.getMasterService().getServiceCode().equalsIgnoreCase("SC002")) {
                cekList = lookupDeliveryBarangServiceFacadeRemote().findByNoregValidasiPrint(no_reg);
                if (cekList.size() >= 1) {
                    enablePrintAll = Boolean.TRUE;
                } else {
                    enablePrintAll = Boolean.FALSE;
                }
//                listJobslip = lookupDeliveryBarangServiceFacadeRemote().findByReg(no_reg);
//                for (Object[] csl : listJobslip) {
//                    deliveryService = lookupDeliveryBarangServiceFacadeRemote().find(csl[0]);
//                    if (deliveryService.getCounterPrint() != null && deliveryService.getCounterPrint() >= 1) {
//                        enablePrintAll = Boolean.TRUE;
//                    } else {
//                        enablePrintAll = Boolean.FALSE;
//                    }
//                }
            } else if (registration.getMasterService().getServiceCode().equalsIgnoreCase("SC001")) {
                cekList = lookupReceivingBarangServiceFacadeRemote().findByNoregValidasiPrint(no_reg);
                if (cekList.size() >= 1) {
                    enablePrintAll = Boolean.TRUE;
                } else {
                    enablePrintAll = Boolean.FALSE;
                }
//                listJobslip = lookupReceivingBarangServiceFacadeRemote().findReceivingBarangServiceByJobSlip(no_reg);
//                for (Object[] csl : listJobslip) {
//                    receivingService = lookupReceivingBarangServiceFacadeRemote().find(csl[0]);
//                    if (receivingService.getCounterPrint() != null && receivingService.getCounterPrint() >= 1) {
//                        enablePrintAll = Boolean.TRUE;
//                    } else {
//                        enablePrintAll = Boolean.FALSE;
//                    }
//                }
            }
        }
    }

    public void handleDownload(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesContext fc = FacesContext.getCurrentInstance();
        context.addCallbackParam("doPrint", true); //harus ada, buat ngeflag di izinkan ngeprint ato ngga
        context.addCallbackParam("target", (String) event.getComponent().getAttributes().get("target"));
        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3(UrlHelper.resolveFullAddress(fc, "/reportJobslip.pdf?tipe=" + tipe + "&no_reg=" + no_reg)));

        //update tabel jobslip counter print 2x
        this.handleClick();
    }

    public void handleDownloadOne(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesContext fc = FacesContext.getCurrentInstance();
        context.addCallbackParam("doPrint", true); //harus ada, buat ngeflag di izinkan ngeprint ato ngga
        context.addCallbackParam("target", (String) event.getComponent().getAttributes().get("target"));
        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3(UrlHelper.resolveFullAddress(fc, "/reportJobslip.pdf?tipe=" + tipe + "&job_slip=" + jobslip + "&no_reg=" + no_reg)));

        //update counter jobslip print 2x
        this.handleClick2();
    }

    public String getPrintUrl() {
        if (listJobslip == null) {
            return "#";
        }
        return FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/reportJobslip.pdf?"
                + "tipe=" + tipe + "&" + "no_reg=" + no_reg + "";
    }

    public String getUrl() {
//        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
//        return context.encodeResourceURL(context.getRequestScheme() + "://" + context.getRequestServerName() + ":" + context.getRequestServerPort() + context.getRequestContextPath() + "/reportJobslip.pdf?"
        return FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/reportJobslip.pdf?"
                + "no_reg=" + no_reg + "&"
                + "job_slip=" + jobslip + "&"
                + "tipe=" + tipe + "";
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

    private DeliveryBarangServiceFacadeRemote lookupDeliveryBarangServiceFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (DeliveryBarangServiceFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/DeliveryBarangServiceFacade!com.pelindo.ebtos.ejb.facade.remote.DeliveryBarangServiceFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
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
    public DeliveryBarangService getDeliveryBarangService() {
        return deliveryService;
    }

    /**
     * @param deliveryService the deliveryService to set
     */
    public void setDeliveryBarangService(DeliveryBarangService deliveryService) {
        this.deliveryService = deliveryService;
    }

    /**
     * @return the receivingService
     */
    public ReceivingBarangService getReceivingBarangService() {
        return receivingService;
    }

    /**
     * @param receivingService the receivingService to set
     */
    public void setReceivingBarangService(ReceivingBarangService receivingService) {
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
}
