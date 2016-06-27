/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.bean;

import com.qtasnim.url.UrlHelper;
import com.pelindo.ebtos.bean.security.LoginSessionBean;
import com.pelindo.ebtos.ejb.facade.remote.BehandleServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.RegistrationFacadeRemote;
import com.pelindo.ebtos.model.db.BehandleService;
import com.pelindo.ebtos.model.db.PlanningVessel;
import com.pelindo.ebtos.model.db.PreserviceVessel;
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
@ManagedBean(name = "jobSlipBehandleBean")
@ViewScoped
public class JobSlipBehandleBean implements Serializable {

    private List<Object[]> registrations = lookupRegistrationFacadeRemote().findRegistrationBehandleJobslip();
    private List<Object[]> listJobslip;
    private Registration registration;
    private BehandleService behandleService;
    private String no_reg;
    private String tipe = "";
    private String jobslip;
    private Boolean panel = true;
    private Boolean panelDel = false;
    private Boolean enablePrint;
    private Boolean enablePrintAll;
    private LoginSessionBean loginSessionBean;

    /** Creates a new instance of JobSlipBehandleBean */
    public JobSlipBehandleBean() {
        registration = new Registration();
        registration.setMasterService(new MasterService());
        registration.setMasterCustomer(new MasterCustomer());
        registration.setPlanningVessel(new PlanningVessel());
        registration.getPlanningVessel().setPreserviceVessel(new PreserviceVessel());
        registration.getPlanningVessel().getPreserviceVessel().setMasterVessel(new MasterVessel());
        listJobslip = new ArrayList<Object[]>();
        this.panel = true;
        panelDel = false;
        loginSessionBean = LoginSessionBean.getCurrentInstance();
    }

    public void handleSelectRegistration(ActionEvent event) {
        no_reg = (String) event.getComponent().getAttributes().get("noReg");
        System.out.println(no_reg);
        registration = lookupRegistrationFacadeRemote().find(no_reg);
        listJobslip = lookupBehandleServiceFacadeRemote().findByJobslip(no_reg);
        tipe = "behandleAll";
        cekPrintAll();
    }

    public void report(ActionEvent event) {
        jobslip = (String) event.getComponent().getAttributes().get("jobSlip");
        behandleService = lookupBehandleServiceFacadeRemote().find(jobslip);
        tipe = "behandle";
        panel = false;
        panelDel = true;
        cekPrint();
    }

    public void tutup(ActionEvent event) {
        panel = true;
        panelDel = false;
        tipe = "behandleAll";
        cekPrintAll();
    }

    public void handleDownload(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesContext fc = FacesContext.getCurrentInstance();
        context.addCallbackParam("doPrint", true); //harus ada, buat ngeflag di izinkan ngeprint ato ngga
        context.addCallbackParam("target", (String) event.getComponent().getAttributes().get("target"));
        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3(UrlHelper.resolveFullAddress(fc, "/reportJobslip.pdf?tipe=" + tipe + "&no_reg=" + no_reg)));

        //update tabel jobslip counter print 2x
        listJobslip = lookupBehandleServiceFacadeRemote().findByJobslip(no_reg);
        for (Object[] csl : listJobslip) {
            behandleService = lookupBehandleServiceFacadeRemote().find(csl[0]);
//            if (behandleService.getCounterPrint() == null) {
//                behandleService.setCounterPrint(0);
//            }
            behandleService.setCounterPrint(behandleService.getCounterPrint() + 1);
            lookupBehandleServiceFacadeRemote().edit(behandleService);
        }
        cekPrintAll();
    }

    public void handleDownloadOne(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesContext fc = FacesContext.getCurrentInstance();
        context.addCallbackParam("doPrint", true); //harus ada, buat ngeflag di izinkan ngeprint ato ngga
        context.addCallbackParam("target", (String) event.getComponent().getAttributes().get("target"));
        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3(UrlHelper.resolveFullAddress(fc, "/reportJobslip.pdf?tipe=" + tipe + "&job_slip=" + jobslip + "&no_reg=" + no_reg)));

        //update counter jobslip print 2x
        behandleService = lookupBehandleServiceFacadeRemote().find(jobslip);
//        if (behandleService.getCounterPrint() == null) {
//            behandleService.setCounterPrint(0);
//        }
        behandleService.setCounterPrint(behandleService.getCounterPrint() + 1);
        lookupBehandleServiceFacadeRemote().edit(behandleService);
        cekPrint();

    }

    public String getPrintUrl() {
        if (listJobslip == null) {
            return "#";
        }
        return FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/reportJobslip.pdf?"
                + "tipe=" + tipe + "&" + "no_reg=" + no_reg + "";
    }

//    public void handleClick(ActionEvent actionEvent) {
//        listJobslip = lookupBehandleServiceFacadeRemote().findByJobslip(no_reg);
//        for (Object[] csl : listJobslip) {
//            behandleService = lookupBehandleServiceFacadeRemote().find(csl[0]);
//            if (behandleService.getCounterPrint() == null) {
//                behandleService.setCounterPrint(0);
//            }
//            behandleService.setCounterPrint(behandleService.getCounterPrint() + 1);
//            lookupBehandleServiceFacadeRemote().edit(behandleService);
//        }
//        cekPrintAll();
//    }
//    public void handleClick2(ActionEvent actionEvent) {
//        behandleService = lookupBehandleServiceFacadeRemote().find(jobslip);
//        if (behandleService.getCounterPrint() == null) {
//            behandleService.setCounterPrint(0);
//        }
//        behandleService.setCounterPrint(behandleService.getCounterPrint() + 1);
//        lookupBehandleServiceFacadeRemote().edit(behandleService);
//        cekPrint();
//    }
    public void cekPrint() {
        if (loginSessionBean.isSupervisor() == true) {
            enablePrint = Boolean.FALSE;
            enablePrintAll = Boolean.FALSE;
        } else {
            behandleService = lookupBehandleServiceFacadeRemote().find(jobslip);
            if (behandleService.getCounterPrint() != null && behandleService.getCounterPrint() >= 1) {
                enablePrint = Boolean.TRUE;
            } else {
                enablePrint = Boolean.FALSE;
            }
        }
    }

    public void cekPrintAll() {
        List<Object[]> cekList=null;
        if (loginSessionBean.isSupervisor() == true) {
            enablePrint = Boolean.FALSE;
            enablePrintAll = Boolean.FALSE;
        } else {
            cekList=lookupBehandleServiceFacadeRemote().findByNoregValidasiPrint(no_reg);
             if (cekList.size() >= 1) {
                    enablePrintAll = Boolean.TRUE;
                } else {
                    enablePrintAll = Boolean.FALSE;
                }
//            for (Object[] csl : listJobslip) {
//                behandleService = lookupBehandleServiceFacadeRemote().find(csl[0]);
//                if (behandleService.getCounterPrint() != null && behandleService.getCounterPrint() >= 1) {
//                    enablePrintAll = Boolean.TRUE;
//                } else {
//                    enablePrintAll = Boolean.FALSE;
//                }
//            }
        }
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

    private BehandleServiceFacadeRemote lookupBehandleServiceFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (BehandleServiceFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/BehandleServiceFacade!com.pelindo.ebtos.ejb.facade.remote.BehandleServiceFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    public BehandleService getBehandleService() {
        return behandleService;
    }

    public void setBehandleService(BehandleService behandleService) {
        this.behandleService = behandleService;
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

    public Boolean getPanelDel() {
        return panelDel;
    }

    public void setPanelDel(Boolean panelDel) {
        this.panelDel = panelDel;
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
