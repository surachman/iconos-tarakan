/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.bean;

import com.qtasnim.url.UrlHelper;
import com.pelindo.ebtos.bean.security.LoginSessionBean;
import com.pelindo.ebtos.ejb.facade.remote.PluggingReeferServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ReeferDischargeServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ReeferLoadServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.RegistrationFacadeRemote;
import com.pelindo.ebtos.model.db.PlanningVessel;
import com.pelindo.ebtos.model.db.PreserviceVessel;
import com.pelindo.ebtos.model.db.ReeferDischargeService;
import com.pelindo.ebtos.model.db.ReeferLoadService;
import com.pelindo.ebtos.model.db.PluggingReeferService;
import com.pelindo.ebtos.model.db.Registration;
import com.pelindo.ebtos.model.db.master.MasterCustomer;
import com.pelindo.ebtos.model.db.master.MasterService;
import com.pelindo.ebtos.model.db.master.MasterVessel;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
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
@ManagedBean(name = "jobSlipReceivingBean")
@ViewScoped
public class JobSlipReceivingBean implements Serializable {

    PluggingReeferServiceFacadeRemote pluggingReeferServiceFacade = lookupPluggingReeferServiceFacadeRemote();
    ReeferDischargeServiceFacadeRemote reeferDischargeServiceFacade = lookupReeferDischargeServiceFacadeRemote();
    ReeferLoadServiceFacadeRemote reeferLoadServiceFacade = lookupReeferLoadServiceFacadeRemote();
    RegistrationFacadeRemote registrationFacade = lookupRegistrationFacadeRemote();
    private List<Object[]> registrations;
    private List<Object[]> listJobslip;
    private Registration registration;
    private ReeferDischargeService reeferDischargeService;
    private ReeferLoadService reeferLoadService;
    private PluggingReeferService pluggingReeferService;
    private String no_reg;
    private String tipe = "";
    private String jobslip;
    private Boolean panel = true;
    private Boolean panelDisch = false;
    private Boolean panelLoad = false;
    private Boolean panelPlug = false;
    private Boolean enablePrint;
    private Boolean enablePrintAll;
    private LoginSessionBean loginSessionBean;

    /** Creates a new instance of JobSlipReceivingBean */
    public JobSlipReceivingBean() {
        registration = new Registration();
        registration.setMasterService(new MasterService());
        registration.setMasterCustomer(new MasterCustomer());
        registration.setPlanningVessel(new PlanningVessel());
        registration.getPlanningVessel().setPreserviceVessel(new PreserviceVessel());
        registration.getPlanningVessel().getPreserviceVessel().setMasterVessel(new MasterVessel());
        loginSessionBean = LoginSessionBean.getCurrentInstance();
        registrations = lookupRegistrationFacadeRemote().findRegistrationPluggingReefer();
    }

    public void handleSelectRegistration(ActionEvent event) {
        no_reg = (String) event.getComponent().getAttributes().get("noReg");
        registration = registrationFacade.find(no_reg);
        if (registration.getMasterService().getServiceCode().equalsIgnoreCase("SC003")) {
            setListJobslip(pluggingReeferServiceFacade.findPluggingReeferServiceByReg(no_reg));
            tipe = "pluggingAll";
        } else if (registration.getMasterService().getServiceCode().equalsIgnoreCase("SC015")) {
            setListJobslip(reeferDischargeServiceFacade.findDischargeReeferServiceByReg(no_reg));
            tipe = "pluggingDischargeAll";
        } else if (registration.getMasterService().getServiceCode().equalsIgnoreCase("SC016")) {
            setListJobslip(reeferLoadServiceFacade.findLoadReeferServiceByReg(no_reg));
            tipe = "pluggingLoadAll";
        }
        cekPrintAll();
    }

    public void report(ActionEvent event) {
        jobslip = (String) event.getComponent().getAttributes().get("jobSlip");
        if (registration.getMasterService().getServiceCode().equalsIgnoreCase("SC015")) {
            panelDisch = true;
            reeferDischargeService = reeferDischargeServiceFacade.find(jobslip);
            tipe = "discharge";
        } else if (registration.getMasterService().getServiceCode().equalsIgnoreCase("SC016")) {
            panelLoad = true;
            reeferLoadService = reeferLoadServiceFacade.find(jobslip);
            tipe = "load";
        } else if (registration.getMasterService().getServiceCode().equalsIgnoreCase("SC003")) {
            panelPlug = true;
            pluggingReeferService = pluggingReeferServiceFacade.find(jobslip);
            tipe = "plugging";
        }
        panel = false;
        cekPrint();
    }

    public void tutup(ActionEvent event) {
        panel = true;
        panelDisch = false;
        panelLoad = false;
        panelPlug = false;
        if (registration.getMasterService().getServiceCode().equalsIgnoreCase("SC003")) {
            tipe = "pluggingAll";
        } else if (registration.getMasterService().getServiceCode().equalsIgnoreCase("SC015")) {
            tipe = "pluggingDischargeAll";
        } else if (registration.getMasterService().getServiceCode().equalsIgnoreCase("SC016")) {
            tipe = "pluggingLoadAll";
        }
        cekPrintAll();
    }

    public void handleClick() {
        if (registration.getMasterService().getServiceCode().equalsIgnoreCase("SC003")) {
            setListJobslip(lookupPluggingReeferServiceFacadeRemote().findPluggingReeferServiceByReg(no_reg));
            for (Object[] csl : listJobslip) {
                pluggingReeferService = lookupPluggingReeferServiceFacadeRemote().find(csl[0]);
//                if (pluggingReeferService.getCounterPrint() == null) {
//                    pluggingReeferService.setCounterPrint(0);
//                }
                pluggingReeferService.setCounterPrint(pluggingReeferService.getCounterPrint() + 1);
                lookupPluggingReeferServiceFacadeRemote().edit(pluggingReeferService);
            }
        } else if (registration.getMasterService().getServiceCode().equalsIgnoreCase("SC015")) {
            setListJobslip(reeferDischargeServiceFacade.findDischargeReeferServiceByReg(no_reg));
            for (Object[] csl : listJobslip) {
                reeferDischargeService = lookupReeferDischargeServiceFacadeRemote().find(csl[0]);
//                if (reeferDischargeService.getCounterPrint() == null) {
//                    reeferDischargeService.setCounterPrint(0);
//                }
                reeferDischargeService.setCounterPrint(reeferDischargeService.getCounterPrint() + 1);
                lookupReeferDischargeServiceFacadeRemote().edit(reeferDischargeService);
            }
        } else if (registration.getMasterService().getServiceCode().equalsIgnoreCase("SC016")) {
            setListJobslip(reeferLoadServiceFacade.findLoadReeferServiceByReg(no_reg));
            for (Object[] csl : listJobslip) {
                reeferLoadService = lookupReeferLoadServiceFacadeRemote().find(csl[0]);
//                if (reeferLoadService.getCounterPrint() == null) {
//                    reeferLoadService.setCounterPrint(0);
//                }
                reeferLoadService.setCounterPrint(reeferLoadService.getCounterPrint() + 1);
                lookupReeferLoadServiceFacadeRemote().edit(reeferLoadService);
            }
        }
        cekPrintAll();
    }

    public void handleClick2() {
        if (registration.getMasterService().getServiceCode().equalsIgnoreCase("SC003")) {
            pluggingReeferService = lookupPluggingReeferServiceFacadeRemote().find(jobslip);
//            if (pluggingReeferService.getCounterPrint() == null) {
//                pluggingReeferService.setCounterPrint(0);
//            }
            pluggingReeferService.setCounterPrint(pluggingReeferService.getCounterPrint() + 1);
            lookupPluggingReeferServiceFacadeRemote().edit(pluggingReeferService);
        } else if (registration.getMasterService().getServiceCode().equalsIgnoreCase("SC015")) {
            reeferDischargeService = lookupReeferDischargeServiceFacadeRemote().find(jobslip);
//            if (reeferDischargeService.getCounterPrint() == null) {
//                reeferDischargeService.setCounterPrint(0);
//            }
            reeferDischargeService.setCounterPrint(reeferDischargeService.getCounterPrint() + 1);
            lookupReeferDischargeServiceFacadeRemote().edit(reeferDischargeService);
        } else if (registration.getMasterService().getServiceCode().equalsIgnoreCase("SC016")) {
            reeferLoadService = lookupReeferLoadServiceFacadeRemote().find(jobslip);
//            if (reeferLoadService.getCounterPrint() == null) {
//                reeferLoadService.setCounterPrint(0);
//            }
            reeferLoadService.setCounterPrint(reeferLoadService.getCounterPrint() + 1);
            lookupReeferLoadServiceFacadeRemote().edit(reeferLoadService);
        }
        cekPrint();
    }

    public void cekPrint() {
        if (loginSessionBean.isSupervisor() == true) {
            enablePrint = Boolean.FALSE;
            enablePrintAll = Boolean.FALSE;
        } else {
            if (registration.getMasterService().getServiceCode().equalsIgnoreCase("SC003")) {
                pluggingReeferService = lookupPluggingReeferServiceFacadeRemote().find(jobslip);
                if (pluggingReeferService.getCounterPrint() != null && pluggingReeferService.getCounterPrint() >= 1) {
                    enablePrint = Boolean.TRUE;
                } else {
                    enablePrint = Boolean.FALSE;
                }
            } else if (registration.getMasterService().getServiceCode().equalsIgnoreCase("SC015")) {
                reeferDischargeService = lookupReeferDischargeServiceFacadeRemote().find(jobslip);
                if (reeferDischargeService.getCounterPrint() != null && reeferDischargeService.getCounterPrint() >= 1) {
                    enablePrint = Boolean.TRUE;
                } else {
                    enablePrint = Boolean.FALSE;
                }
            } else if (registration.getMasterService().getServiceCode().equalsIgnoreCase("SC016")) {
                reeferLoadService = lookupReeferLoadServiceFacadeRemote().find(jobslip);
                if (reeferLoadService.getCounterPrint() != null && reeferLoadService.getCounterPrint() >= 1) {
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
            if (registration.getMasterService().getServiceCode().equalsIgnoreCase("SC003")) {
                cekList = lookupPluggingReeferServiceFacadeRemote().findByNoregValidasiPrint(no_reg);
                if (cekList.size() >= 1) {
                    enablePrintAll = Boolean.TRUE;
                } else {
                    enablePrintAll = Boolean.FALSE;
                }
//                setListJobslip(lookupPluggingReeferServiceFacadeRemote().findPluggingReeferServiceByReg(no_reg));
//                for (Object[] csl : listJobslip) {
//                    pluggingReeferService = lookupPluggingReeferServiceFacadeRemote().find(csl[0]);
//                    if (pluggingReeferService.getCounterPrint() != null && pluggingReeferService.getCounterPrint() >= 1) {
//                        enablePrintAll = Boolean.TRUE;
//                    } else {
//                        enablePrintAll = Boolean.FALSE;
//                    }
//                }
            } else if (registration.getMasterService().getServiceCode().equalsIgnoreCase("SC015")) {
                cekList = lookupReeferDischargeServiceFacadeRemote().findByNoregValidasiPrint(no_reg);
                if (cekList.size() >= 1) {
                    enablePrintAll = Boolean.TRUE;
                } else {
                    enablePrintAll = Boolean.FALSE;
                }
//                setListJobslip(reeferDischargeServiceFacade.findDischargeReeferServiceByReg(no_reg));
//                for (Object[] csl : listJobslip) {
//                    reeferDischargeService = lookupReeferDischargeServiceFacadeRemote().find(csl[0]);
//                    if (reeferDischargeService.getCounterPrint() != null && reeferDischargeService.getCounterPrint() >= 1) {
//                        enablePrintAll = Boolean.TRUE;
//                    } else {
//                        enablePrintAll = Boolean.FALSE;
//                    }
//                }
            } else if (registration.getMasterService().getServiceCode().equalsIgnoreCase("SC016")) {
                cekList = lookupReeferLoadServiceFacadeRemote().findByNoregValidasiPrint(no_reg);
                if (cekList.size() >= 1) {
                    enablePrintAll = Boolean.TRUE;
                } else {
                    enablePrintAll = Boolean.FALSE;
                }
//                setListJobslip(reeferLoadServiceFacade.findLoadReeferServiceByReg(no_reg));
//                for (Object[] csl : listJobslip) {
//                    reeferLoadService = lookupReeferLoadServiceFacadeRemote().find(csl[0]);
//                    if (reeferLoadService.getCounterPrint() != null && reeferLoadService.getCounterPrint() >= 1) {
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
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        return context.getRequestScheme() + "://" + context.getRequestServerName() + ":" + context.getRequestServerPort() + context.getRequestContextPath() + "/reportJobslip.pdf?"
                + "tipe=" + tipe + "&" + "no_reg=" + no_reg + "";
    }

    public String getUrl() {
        if (listJobslip == null) {
            return "#";
        }
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        return context.getRequestScheme() + "://" + context.getRequestServerName() + ":" + context.getRequestServerPort() + context.getRequestContextPath() + "/reportJobslip.pdf?"
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

    private ReeferDischargeServiceFacadeRemote lookupReeferDischargeServiceFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (ReeferDischargeServiceFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/ReeferDischargeServiceFacade!com.pelindo.ebtos.ejb.facade.remote.ReeferDischargeServiceFacadeRemote");
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

    private PluggingReeferServiceFacadeRemote lookupPluggingReeferServiceFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (PluggingReeferServiceFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/PluggingReeferServiceFacade!com.pelindo.ebtos.ejb.facade.remote.PluggingReeferServiceFacadeRemote");
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
     * @return the panelDisch
     */
    public Boolean getPanelDisch() {
        return panelDisch;
    }

    /**
     * @param panelDisch the panelDisch to set
     */
    public void setPanelDisch(Boolean panelDisch) {
        this.panelDisch = panelDisch;
    }

    /**
     * @return the panelLoad
     */
    public Boolean getPanelLoad() {
        return panelLoad;
    }

    /**
     * @param panelLoad the panelLoad to set
     */
    public void setPanelLoad(Boolean panelLoad) {
        this.panelLoad = panelLoad;
    }

    /**
     * @return the panelPlug
     */
    public Boolean getPanelPlug() {
        return panelPlug;
    }

    /**
     * @param panelPlug the panelPlug to set
     */
    public void setPanelPlug(Boolean panelPlug) {
        this.panelPlug = panelPlug;
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
