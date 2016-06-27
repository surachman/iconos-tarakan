/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.bean;

import com.qtasnim.url.UrlHelper;
import com.pelindo.ebtos.bean.security.LoginSessionBean;
import com.pelindo.ebtos.ejb.facade.remote.CancelLoadingServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningContLoadFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningVesselFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.RegistrationFacadeRemote;
import com.pelindo.ebtos.model.db.CancelLoadingService;
import com.pelindo.ebtos.model.db.PlanningContLoad;
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
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
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
@ManagedBean(name = "jobSlipCancelLoadingBean")
@ViewScoped
public class JobSlipCancelLoadingBean implements Serializable {
    @EJB
    private RegistrationFacadeRemote registrationFacadeRemote;
    @EJB
    private CancelLoadingServiceFacadeRemote cancelLoadingServiceFacadeRemote;
    @EJB
    private PlanningContLoadFacadeRemote planningContLoadFacadeRemote;
    @EJB
    private PlanningVesselFacadeRemote planningVesselFacadeRemote;

    private List<Object[]> registrations;
    private List<Object[]> listJobslip;
    private Registration registration;
    private CancelLoadingService cancelLoadingService;
    private String no_reg;
    private String tipe = "";
    private String jobslip;
    private Boolean panel = true;
    private Boolean panelDel = false;
    private Boolean enablePrint;
    private Boolean enablePrintAll;
    private LoginSessionBean loginSessionBean;

    /** Creates a new instance of JobSlipBehandleBean */
    public JobSlipCancelLoadingBean() {}

    @PostConstruct
    private void construct(){
        registration = new Registration();
        registration.setMasterService(new MasterService());
        registration.setMasterCustomer(new MasterCustomer());
        registration.setPlanningVessel(new PlanningVessel());
        registration.getPlanningVessel().setPreserviceVessel(new PreserviceVessel());
        registration.getPlanningVessel().getPreserviceVessel().setMasterVessel(new MasterVessel());
        listJobslip = new ArrayList<Object[]>();
        this.panel = true;
        this.panelDel = false;
        loginSessionBean = LoginSessionBean.getCurrentInstance();
        registrations = registrationFacadeRemote.findRegistrationBatalMuatJobslip();
    }

    public void handleSelectRegistration(ActionEvent event) {
        no_reg = (String) event.getComponent().getAttributes().get("noReg");
        System.out.println(no_reg);
        registration = registrationFacadeRemote.find(no_reg);
        listJobslip = cancelLoadingServiceFacadeRemote.findCancelLoadingServiceByNoreg(no_reg);
        tipe = "cancelAll";
        cekPrintAll();
    }

    public void report(ActionEvent event) {
        jobslip = (String) event.getComponent().getAttributes().get("jobSlip");
        cancelLoadingService = cancelLoadingServiceFacadeRemote.find(jobslip);
        tipe = "cancel";
        panel = false;
        panelDel = true;
        cekPrint();
    }

    public void tutup(ActionEvent event) {
        panel = true;
        panelDel = false;
        tipe = "cancelAll";
        cekPrintAll();
    }

    public void cekPrint() {
        if (loginSessionBean.isSupervisor() == true) {
            enablePrint = Boolean.FALSE;
            enablePrintAll = Boolean.FALSE;
        } else {
            cancelLoadingService = cancelLoadingServiceFacadeRemote.find(jobslip);
            if (cancelLoadingService.getCounterPrint() != null && cancelLoadingService.getCounterPrint() >= 1) {
                enablePrint = Boolean.TRUE;
            } else {
                enablePrint = Boolean.FALSE;
            }
        }
    }

    public void cekPrintAll() {
        List<Object[]> cekList = null;
        if (loginSessionBean.isSupervisor() == true) {
            enablePrint = Boolean.FALSE;
            enablePrintAll = Boolean.FALSE;
        } else {
            cekList = cancelLoadingServiceFacadeRemote.findByNoregValidasiPrint(no_reg);
            if (cekList.size() >= 1) {
                enablePrintAll = Boolean.TRUE;
            } else {
                enablePrintAll = Boolean.FALSE;
            }
        }
    }

    public void handleDownload(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesContext fc = FacesContext.getCurrentInstance();
        context.addCallbackParam("doPrint", true); //harus ada, buat ngeflag di izinkan ngeprint ato ngga
        context.addCallbackParam("target", (String) event.getComponent().getAttributes().get("target"));
        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3(UrlHelper.resolveFullAddress(fc, "/reportJobslip.pdf?tipe=" + tipe + "&no_reg=" + no_reg)));

        listJobslip = cancelLoadingServiceFacadeRemote.findCancelLoadingServiceByNoreg(no_reg);

        for (Object[] csl : listJobslip) {
            cancelLoadingService = cancelLoadingServiceFacadeRemote.find(csl[9]);
            System.out.println("cancelLoadingService : " + cancelLoadingService.getPlanningVessel().getNoPpkb() + "; TYPE : " + tipe);

            if (cancelLoadingService.getCategory() == 2) {
                PlanningContLoad loadingContainer = planningContLoadFacadeRemote.findByNoPpkbAndContNoExcludeCancelLoading(cancelLoadingService.getNewPlanningVessel().getNoPpkb(), cancelLoadingService.getContNo());

                if (loadingContainer == null) {
                    PlanningContLoad planningContLoad = planningContLoadFacadeRemote.findByNoPpkbContNoAndStatusCancelLoading(cancelLoadingService.getPlanningVessel().getNoPpkb(), cancelLoadingService.getContNo(), true);
                    PlanningVessel planningVessel = planningVesselFacadeRemote.find(cancelLoadingService.getNewPlanningVessel().getNoPpkb());

                    loadingContainer = new PlanningContLoad();
                    loadingContainer.setMasterCommodity(planningContLoad.getMasterCommodity());
                    loadingContainer.setMasterYard(planningContLoad.getMasterYard());
                    loadingContainer.setMasterContainerType(planningContLoad.getMasterContainerType());
                    loadingContainer.setPlanningVessel(planningVessel);
                    loadingContainer.setContNo(planningContLoad.getContNo());
                    loadingContainer.setContSize(planningContLoad.getContSize());
                    loadingContainer.setContStatus(planningContLoad.getContStatus());
                    loadingContainer.setIsTranshipment(planningContLoad.getIsTranshipment());
                    loadingContainer.setIsShifting("FALSE");
                    loadingContainer.setIsChangeVessel("TRUE");
                    loadingContainer.setDischPort(cancelLoadingService.getDischargePort().getPortCode());
                    loadingContainer.setLoadPort(planningContLoad.getLoadPort());
                    loadingContainer.setPosition(PlanningContLoad.LOCATION_CY);
                    loadingContainer.setMlo(planningContLoad.getMlo());
                    loadingContainer.setContGross(planningContLoad.getContGross());
                    loadingContainer.setSealNo(planningContLoad.getSealNo());
                    loadingContainer.setOverSize(planningContLoad.getOverSize());
                    loadingContainer.setDgLabel(planningContLoad.getDgLabel());
                    loadingContainer.setDg(planningContLoad.getDg());
                    loadingContainer.setIsExportImport(planningContLoad.getIsExportImport());
                    loadingContainer.setYSlot(planningContLoad.getYSlot());
                    loadingContainer.setYRow(planningContLoad.getYRow());
                    loadingContainer.setYTier(planningContLoad.getYTier());
                    loadingContainer.setCompleted("FALSE");
                    loadingContainer.setUnknown("FALSE");
                    loadingContainer.setBlNo(planningContLoad.getBlNo());
                    loadingContainer.setPortOfDelivery(planningContLoad.getPortOfDelivery());

                    planningContLoadFacadeRemote.edit(loadingContainer);
                }
            }

            cancelLoadingService.setCounterPrint(cancelLoadingService.getCounterPrint() + 1);
            cancelLoadingServiceFacadeRemote.edit(cancelLoadingService);
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
        cancelLoadingService = cancelLoadingServiceFacadeRemote.find(jobslip);

        if (cancelLoadingService.getCounterPrint() == 0 && cancelLoadingService.getCategory() == 2) {
            PlanningContLoad planningContLoad = planningContLoadFacadeRemote.findByNoPpkbContNoAndStatusCancelLoading(cancelLoadingService.getPlanningVessel().getNoPpkb(), cancelLoadingService.getContNo(), true);
            PlanningVessel planningVessel = planningVesselFacadeRemote.find(cancelLoadingService.getNewPlanningVessel().getNoPpkb());

            PlanningContLoad loadingContainer = new PlanningContLoad();
            loadingContainer.setMasterCommodity(planningContLoad.getMasterCommodity());
            loadingContainer.setMasterYard(planningContLoad.getMasterYard());
            loadingContainer.setMasterContainerType(planningContLoad.getMasterContainerType());
            loadingContainer.setPlanningVessel(planningVessel);
            loadingContainer.setContNo(planningContLoad.getContNo());
            loadingContainer.setContSize(planningContLoad.getContSize());
            loadingContainer.setContStatus(planningContLoad.getContStatus());
            loadingContainer.setIsTranshipment(planningContLoad.getIsTranshipment());
            loadingContainer.setIsShifting("FALSE");
            loadingContainer.setIsChangeVessel("TRUE");
            loadingContainer.setDischPort(cancelLoadingService.getDischargePort().getPortCode());
            loadingContainer.setLoadPort(planningContLoad.getLoadPort());
            loadingContainer.setPosition(PlanningContLoad.LOCATION_CY);
            loadingContainer.setMlo(planningContLoad.getMlo());
            loadingContainer.setContGross(planningContLoad.getContGross());
            loadingContainer.setSealNo(planningContLoad.getSealNo());
            loadingContainer.setOverSize(planningContLoad.getOverSize());
            loadingContainer.setDgLabel(planningContLoad.getDgLabel());
            loadingContainer.setDg(planningContLoad.getDg());
            loadingContainer.setYSlot(planningContLoad.getYSlot());
            loadingContainer.setYRow(planningContLoad.getYRow());
            loadingContainer.setYTier(planningContLoad.getYTier());
            loadingContainer.setCompleted("FALSE");
            loadingContainer.setUnknown("FALSE");
            loadingContainer.setBlNo(planningContLoad.getBlNo());
            loadingContainer.setPortOfDelivery(planningContLoad.getPortOfDelivery());

            planningContLoadFacadeRemote.edit(loadingContainer);
        }

        cancelLoadingService.setCounterPrint(cancelLoadingService.getCounterPrint() + 1);
        cancelLoadingServiceFacadeRemote.edit(cancelLoadingService);
        cekPrint();
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

    public CancelLoadingService getCancelLoadingService() {
        return cancelLoadingService;
    }

    public void setCancelLoadingService(CancelLoadingService cancelLoadingService) {
        this.cancelLoadingService = cancelLoadingService;
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
