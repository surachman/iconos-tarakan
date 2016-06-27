/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.bean;

import com.qtasnim.url.UrlHelper;
import com.pelindo.ebtos.bean.security.LoginSessionBean;
import com.pelindo.ebtos.ejb.facade.remote.PluggingReeferServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.RegistrationFacadeRemote;
import com.pelindo.ebtos.model.db.PlanningVessel;
import com.pelindo.ebtos.model.db.PluggingReeferService;
import com.pelindo.ebtos.model.db.Registration;
import com.pelindo.ebtos.model.db.master.MasterCustomer;
import com.pelindo.ebtos.model.db.master.MasterService;
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
 * @author wulan
 */
@ManagedBean(name = "jobslipDeliveryPluggingBean")
@ViewScoped
public class JobslipDeliveryPluggingBean implements Serializable{

    PluggingReeferServiceFacadeRemote pluggingReeferServiceFacade = lookupPluggingReeferServiceFacadeRemote();
    RegistrationFacadeRemote registrationFacade = lookupRegistrationFacadeRemote();

    private List<Object[]> registrations;
    private List<Object[]> listJobslip;
    private Registration registration;
    private PluggingReeferService pluggingReeferService;
    private String no_reg;
    private String tipe = "";
    private String jobslip;
    private Boolean panel = true;
    private Boolean panelPlug = false;
    private Boolean enablePrint;
    private Boolean enablePrintAll;
    private LoginSessionBean loginSessionBean;

    /** Creates a new instance of JobSlipPluggingReceivingBean */
    public JobslipDeliveryPluggingBean() {
        registration = new Registration();
        registration.setMasterService(new MasterService());
        registration.setMasterCustomer(new MasterCustomer());
        loginSessionBean = LoginSessionBean.getCurrentInstance();
        registrations = lookupRegistrationFacadeRemote().findRegistrationPluggingOnly();
    }

     public void handleSelectRegistration(ActionEvent event) {
        no_reg = (String) event.getComponent().getAttributes().get("noReg");
        registration = registrationFacade.find(no_reg);
        if (registration.getMasterService().getServiceCode().equalsIgnoreCase("SC003")) {
            setListJobslip(pluggingReeferServiceFacade.findByRegPluggingDelivery(no_reg));
            tipe = "pluggingAllDel";
        }
        cekPrintAll();
    }

    public void report(ActionEvent event) {
        jobslip = (String) event.getComponent().getAttributes().get("jobSlip");
        if (registration.getMasterService().getServiceCode().equalsIgnoreCase("SC003")) {
            panelPlug = true;
            pluggingReeferService = pluggingReeferServiceFacade.find(jobslip);
            tipe = "pluggingDel";
        }
        panel = false;
        cekPrint();
    }

    public void tutup(ActionEvent event) {
        panel = true;
        panelPlug = false;
        if (registration.getMasterService().getServiceCode().equalsIgnoreCase("SC003")) {
            tipe = "pluggingAllDel";
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

    private PluggingReeferServiceFacadeRemote lookupPluggingReeferServiceFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (PluggingReeferServiceFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/PluggingReeferServiceFacade!com.pelindo.ebtos.ejb.facade.remote.PluggingReeferServiceFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    public Boolean getEnablePrint() {
        return enablePrint;
    }

    public void setEnablePrint(Boolean enablePrint) {
        this.enablePrint = enablePrint;
    }

    public Boolean getEnablePrintAll() {
        return enablePrintAll;
    }

    public void setEnablePrintAll(Boolean enablePrintAll) {
        this.enablePrintAll = enablePrintAll;
    }

    public String getJobslip() {
        return jobslip;
    }

    public void setJobslip(String jobslip) {
        this.jobslip = jobslip;
    }

    public List<Object[]> getListJobslip() {
        return listJobslip;
    }

    public void setListJobslip(List<Object[]> listJobslip) {
        this.listJobslip = listJobslip;
    }

    public String getNo_reg() {
        return no_reg;
    }

    public void setNo_reg(String no_reg) {
        this.no_reg = no_reg;
    }

    public Boolean getPanel() {
        return panel;
    }

    public void setPanel(Boolean panel) {
        this.panel = panel;
    }

    public Boolean getPanelPlug() {
        return panelPlug;
    }

    public void setPanelPlug(Boolean panelPlug) {
        this.panelPlug = panelPlug;
    }

    public PluggingReeferService getPluggingReeferService() {
        return pluggingReeferService;
    }

    public void setPluggingReeferService(PluggingReeferService pluggingReeferService) {
        this.pluggingReeferService = pluggingReeferService;
    }

    public Registration getRegistration() {
        return registration;
    }

    public void setRegistration(Registration registration) {
        this.registration = registration;
    }

    public List<Object[]> getRegistrations() {
        return registrations;
    }

    public void setRegistrations(List<Object[]> registrations) {
        this.registrations = registrations;
    }

    public String getTipe() {
        return tipe;
    }

    public void setTipe(String tipe) {
        this.tipe = tipe;
    }
}
