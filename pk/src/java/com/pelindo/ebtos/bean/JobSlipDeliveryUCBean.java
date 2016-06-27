/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.bean;

import com.qtasnim.url.UrlHelper;
import com.pelindo.ebtos.bean.security.LoginSessionBean;
import com.pelindo.ebtos.ejb.facade.remote.RegistrationFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.UcDeliveryServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.UcReceivingServiceFacadeRemote;
import com.pelindo.ebtos.model.db.Registration;
import com.pelindo.ebtos.model.db.UcDeliveryService;
import com.pelindo.ebtos.model.db.UcReceivingService;
import com.pelindo.ebtos.model.db.UncontainerizedService;
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
@ManagedBean(name = "jobSlipDeliveryUCBean")
@ViewScoped
public class JobSlipDeliveryUCBean implements Serializable {
    @EJB
    private RegistrationFacadeRemote registrationFacadeRemote;
    @EJB
    private UcDeliveryServiceFacadeRemote ucDeliveryServiceFacadeRemote;
    @EJB
    private UcReceivingServiceFacadeRemote ucReceivingServiceFacadeRemote;

    private List<Object[]> registrations;
    private List<Object[]> listJobslip;
    private Registration registration;
    private UcReceivingService ucReceivingService;
    private UcDeliveryService ucDeliveryService;
    private UncontainerizedService uncontainerizedService;
    private LoginSessionBean loginSessionBean;
    private String no_reg;
    private String tipe = "";
    private String jobslip;
    private Boolean panel = true;
    private Boolean panelDel = false;
    private Boolean panelRec = false;
    private Boolean enablePrint;
    private Boolean enablePrintAll;

    /** Creates a new instance of JobSlipDeliveryUCBean */
    public JobSlipDeliveryUCBean() {}

    @PostConstruct
    private void construct(){
        registration = new Registration();
        ucDeliveryService = new UcDeliveryService();
        ucReceivingService = new UcReceivingService();
        uncontainerizedService = new UncontainerizedService();
        loginSessionBean = LoginSessionBean.getCurrentInstance();

        registrations = registrationFacadeRemote.findRegistrationDeliveryReceivingUC();
    }

    public void handleSelectRegistration(ActionEvent event) {
        no_reg = (String) event.getComponent().getAttributes().get("noReg");
        registration = registrationFacadeRemote.find(no_reg);
        
        if (registration.getMasterService().getServiceCode().equalsIgnoreCase("SC014")) {
            listJobslip = ucDeliveryServiceFacadeRemote.findByUcDeliveryServiceJobSlip(no_reg);
            tipe = "deliveryUCAll";
        } else if (registration.getMasterService().getServiceCode().equalsIgnoreCase("SC013")) {
            listJobslip = ucReceivingServiceFacadeRemote.findByUcReceivingJobSlip(no_reg);
            tipe = "receivingUCAll";
        }

        cekPrintAll();
    }

    public void report(ActionEvent event) {
        jobslip = (String) event.getComponent().getAttributes().get("jobSlip");

        if (registration.getMasterService().getServiceCode().equalsIgnoreCase("SC014")) {
            panelDel = true;
            ucDeliveryService = ucDeliveryServiceFacadeRemote.find(jobslip);
            uncontainerizedService = ucDeliveryService.getUncontainerizedService();
            tipe = "deliveryUC";
        } else if (registration.getMasterService().getServiceCode().equalsIgnoreCase("SC013")) {
            panelRec = true;
            ucReceivingService = ucReceivingServiceFacadeRemote.find(jobslip);
            uncontainerizedService = ucReceivingService.getUncontainerizedService();
            tipe = "receivingUC";
        }
        
        panel = false;
        cekPrint();
    }

    public void tutup(ActionEvent event) {
        panel = true;
        panelDel = false;
        panelRec = false;

        if (registration.getMasterService().getServiceCode().equalsIgnoreCase("SC014")) {
            tipe = "deliveryUCAll";
        } else if (registration.getMasterService().getServiceCode().equalsIgnoreCase("SC013")) {
            tipe = "receivingUCAll";
        }
        
        cekPrintAll();
    }

    public void handleDownload(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesContext fc = FacesContext.getCurrentInstance();
        context.addCallbackParam("doPrint", true); //harus ada, buat ngeflag di izinkan ngeprint ato ngga
        context.addCallbackParam("target", (String) event.getComponent().getAttributes().get("target"));
        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3(UrlHelper.resolveFullAddress(fc, "/reportJobslip.pdf?tipe=" + tipe + "&no_reg=" + no_reg)));

        //update tabel jobslip counter print 2x
        if (registration.getMasterService().getServiceCode().equalsIgnoreCase("SC014")) {
            listJobslip = ucDeliveryServiceFacadeRemote.findByUcDeliveryServiceJobSlip(no_reg);
            for (Object[] csl : listJobslip) {
                ucDeliveryService = ucDeliveryServiceFacadeRemote.find(csl[0]);
                ucDeliveryService.setCounterPrint(ucDeliveryService.getCounterPrint() + 1);
                ucDeliveryServiceFacadeRemote.edit(ucDeliveryService);
            }
        } else if (registration.getMasterService().getServiceCode().equalsIgnoreCase("SC013")) {
            listJobslip = ucReceivingServiceFacadeRemote.findByUcReceivingJobSlip(no_reg);
            for (Object[] csl : listJobslip) {
                ucReceivingService = ucReceivingServiceFacadeRemote.find(csl[0]);
                ucReceivingService.setCounterPrint(ucReceivingService.getCounterPrint() + 1);
                ucReceivingServiceFacadeRemote.edit(ucReceivingService);
            }
        }
        cekPrintAll();
    }

    public void handleDownloadOne(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesContext fc = FacesContext.getCurrentInstance();
        context.addCallbackParam("doPrint", true); //harus ada, buat ngeflag di izinkan ngeprint ato ngga
        context.addCallbackParam("target", (String) event.getComponent().getAttributes().get("target"));
        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3(UrlHelper.resolveFullAddress(fc, "/reportJobslip.pdf?tipe=" + tipe + "&job_slip=" + jobslip + "&no_reg=" + no_reg)));

        if (registration.getMasterService().getServiceCode().equalsIgnoreCase("SC014")) {
            ucDeliveryService = ucDeliveryServiceFacadeRemote.find(jobslip);
            ucDeliveryService.setCounterPrint(ucDeliveryService.getCounterPrint() + 1);
            ucDeliveryServiceFacadeRemote.edit(ucDeliveryService);
        } else if (registration.getMasterService().getServiceCode().equalsIgnoreCase("SC013")) {
            ucReceivingService = ucReceivingServiceFacadeRemote.find(jobslip);
            ucReceivingService.setCounterPrint(ucReceivingService.getCounterPrint() + 1);
            ucReceivingServiceFacadeRemote.edit(ucReceivingService);
        }

        cekPrint();

    }

    public String getPrintUrl() {
        if (listJobslip == null) {
            return "#";
        }
        return FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/reportJobslip.pdf?"
                + "tipe=" + tipe + "&" + "no_reg=" + no_reg + "";
    }

    public String getUrl() {
        return FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/reportJobslip.pdf?"
                + "no_reg=" + no_reg + "&"
                + "job_slip=" + jobslip + "&"
                + "tipe=" + tipe + "";
    }

    public void cekPrint() {
        if (loginSessionBean.isSupervisor() == true) {
            enablePrint = Boolean.FALSE;
            enablePrintAll = Boolean.FALSE;
        } else {
            if (registration.getMasterService().getServiceCode().equalsIgnoreCase("SC014")) {
                ucDeliveryService = ucDeliveryServiceFacadeRemote.find(jobslip);
                if (ucDeliveryService.getCounterPrint() != null && ucDeliveryService.getCounterPrint() >= 1) {
                    enablePrint = Boolean.TRUE;
                } else {
                    enablePrint = Boolean.FALSE;
                }
            } else if (registration.getMasterService().getServiceCode().equalsIgnoreCase("SC013")) {
                ucReceivingService = ucReceivingServiceFacadeRemote.find(jobslip);
                if (ucReceivingService.getCounterPrint() != null && ucReceivingService.getCounterPrint() >= 1) {
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
            if (registration.getMasterService().getServiceCode().equalsIgnoreCase("SC014")) {
                cekList = ucDeliveryServiceFacadeRemote.findByNoregValidasiPrint(no_reg);

                if (cekList.size() >= 1) {
                    enablePrintAll = Boolean.TRUE;
                } else {
                    enablePrintAll = Boolean.FALSE;
                }
            } else if (registration.getMasterService().getServiceCode().equalsIgnoreCase("SC013")) {
                cekList = ucReceivingServiceFacadeRemote.findByNoregValidasiPrint(no_reg);
                
                if (cekList.size() >= 1) {
                    enablePrintAll = Boolean.TRUE;
                } else {
                    enablePrintAll = Boolean.FALSE;
                }
            }
        }
    }

    public UncontainerizedService getUncontainerizedService() {
        return uncontainerizedService;
    }

    public void setUncontainerizedService(UncontainerizedService uncontainerizedService) {
        this.uncontainerizedService = uncontainerizedService;
    }

    public UcDeliveryService getUcDeliveryService() {
        return ucDeliveryService;
    }

    public void setUcDeliveryService(UcDeliveryService ucDeliveryService) {
        this.ucDeliveryService = ucDeliveryService;
    }

    public UcReceivingService getUcReceivingService() {
        return ucReceivingService;
    }

    public void setUcReceivingService(UcReceivingService ucReceivingService) {
        this.ucReceivingService = ucReceivingService;
    }

    public List<Object[]> getRegistrations() {
        return registrations;
    }

    public void setRegistrations(List<Object[]> registrations) {
        this.registrations = registrations;
    }

    public List<Object[]> getListJobslip() {
        return listJobslip;
    }

    public void setListJobslip(List<Object[]> listJobslip) {
        this.listJobslip = listJobslip;
    }

    public String getJobslip() {
        return jobslip;
    }

    public void setJobslip(String jobslip) {
        this.jobslip = jobslip;
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

    public Boolean getPanelDel() {
        return panelDel;
    }

    public void setPanelDel(Boolean panelDel) {
        this.panelDel = panelDel;
    }

    public Boolean getPanelRec() {
        return panelRec;
    }

    public void setPanelRec(Boolean panelRec) {
        this.panelRec = panelRec;
    }

    public String getTipe() {
        return tipe;
    }

    public void setTipe(String tipe) {
        this.tipe = tipe;
    }

    public Registration getRegistration() {
        return registration;
    }

    public void setRegistration(Registration registration) {
        this.registration = registration;
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
