/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.bean;

import com.qtasnim.jsf.FacesHelper;
import com.qtasnim.url.UrlHelper;
import com.pelindo.ebtos.ejb.facade.remote.*;
import com.pelindo.ebtos.ejb.facade.remote.MasterVehicleFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningVesselFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.UcDeliveryServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.UcGatedeliveryServiceFacadeRemote;
import com.pelindo.ebtos.model.db.PlanningVessel;
import com.pelindo.ebtos.model.db.UcDeliveryService;
import com.pelindo.ebtos.model.db.UcGatedeliveryService;
import com.pelindo.ebtos.model.db.UncontainerizedService;
import com.pelindo.ebtos.model.db.master.MasterVehicle;
import com.pelindo.ebtos.model.db.master.MasterCommodity;
import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import org.apache.commons.lang3.StringEscapeUtils;
import org.primefaces.context.RequestContext;

/**
 *
 * @author dycoder
 */
@ManagedBean(name = "gateInDeliveryUcBean")
@ViewScoped
public class GateInDeliveryUcBean implements Serializable {
    @EJB
    private UcGatedeliveryServiceFacadeRemote ucGatedeliveryServiceFacadeRemote;
    @EJB
    private MasterVehicleFacadeRemote masterVehicleFacadeRemote;
    @EJB
    private UcDeliveryServiceFacadeRemote ucDeliveryServiceFacadeRemote;
    @EJB
    private PlanningVesselFacadeRemote planningVesselFacadeRemote;
    @EJB
    private MasterCommodityFacadeRemote masterCommodityFacadeRemote;

    private List<MasterVehicle> vehicles;
    private UcDeliveryService ucDeliveryService;
    private UncontainerizedService uncontainerizedService;
    private MasterCommodity masterCommodity;
    private UcGatedeliveryService ucGatedeliveryService;
    private MasterVehicle masterVehicle;
    private PlanningVessel planningVessel;
    private UncontainerizedService uncontainerized;
    private Object[] ucDeliveryObject;
    private Object[] ucGateDeliveryObject;
    private boolean visible;
    private String jobSlip;
    private String mode;
    private String vesselName;
    private String voyIn;
    private String voyOut;
    private String Block;
    private String blNo;
    private String noPpkb;
    private String ucDelUseTruck = null;

    /** Creates a new instance of GateInDeliveryUcBean */
    public GateInDeliveryUcBean() {}

    @PostConstruct
    private void construct() {
        ucDeliveryService = new UcDeliveryService();
        uncontainerizedService = new UncontainerizedService();
        ucGatedeliveryService = new UcGatedeliveryService();
        masterVehicle = new MasterVehicle();
        masterCommodity = new MasterCommodity();

        visible = Boolean.FALSE;
        planningVessel = new PlanningVessel();
        uncontainerized = new UncontainerizedService();
        vehicles = masterVehicleFacadeRemote.findAll();
        ucDelUseTruck = "1";
    }

    public List<String> setListAutoComplete(String query) {
        List<String> results = ucDeliveryServiceFacadeRemote.findByUcDeliveryServiceAutoComplete(query);
        return results;
    }

    public void ambilContNo(ActionEvent event) {
        try {
            jobSlip = (String) event.getComponent().getAttributes().get("jobSlip");
            ucDeliveryService = ucDeliveryServiceFacadeRemote.find(jobSlip);
            uncontainerized = ucDeliveryService.getUncontainerizedService();


            if (uncontainerized.getTruckLoosing().equals("TRUE")) {
                ucDeliveryObject = ucDeliveryServiceFacadeRemote.findByUcDeliveryServiceGateInDeliveryClosingTimeTruckYes(jobSlip);
            } else {
                ucDeliveryObject = ucDeliveryServiceFacadeRemote.findByUcDeliveryServiceGateInDeliveryClosingTime(jobSlip);
            }

            ucGateDeliveryObject = ucGatedeliveryServiceFacadeRemote.findUcGatedeliveryServiceByGate(jobSlip);

            if (ucDeliveryObject != null) {
                ucDeliveryService = ucDeliveryServiceFacadeRemote.find(ucDeliveryObject[0]);
                ucGatedeliveryService.setJobslip(ucDeliveryService.getJobslip());
                uncontainerizedService = ucDeliveryService.getUncontainerizedService();
                masterCommodity = masterCommodityFacadeRemote.find(uncontainerizedService.getCommodity());
                uncontainerized = new UncontainerizedService();
                planningVessel = new PlanningVessel();
                visible = Boolean.FALSE;
                FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.search.found");
            } else if (ucGateDeliveryObject != null) {
                ucGatedeliveryService = ucGatedeliveryServiceFacadeRemote.find(ucGateDeliveryObject[0]);
                ucGatedeliveryService.setJobslip(ucGatedeliveryService.getJobslip());
                ucGatedeliveryService.setWeight((Integer) ucGateDeliveryObject[4]);
                ucDeliveryService = ucDeliveryServiceFacadeRemote.find(ucGatedeliveryService.getJobslip());
                uncontainerizedService = ucDeliveryService.getUncontainerizedService();
                uncontainerized = ucDeliveryService.getUncontainerizedService();
                masterCommodity = masterCommodityFacadeRemote.find(uncontainerizedService.getCommodity());
                planningVessel = planningVesselFacadeRemote.find(uncontainerizedService.getNoPpkb());
                vesselName = planningVessel.getPreserviceVessel().getMasterVessel().getName();
                voyIn = planningVessel.getPreserviceVessel().getVoyIn();
                voyOut = planningVessel.getPreserviceVessel().getVoyOut();
                Block = (uncontainerized.getTruckLoosing().equals("TRUE")) ? planningVessel.getMasterDock().getName() : uncontainerized.getBlock();
                blNo = uncontainerized.getBlNo();
                noPpkb = uncontainerized.getNoPpkb();
                visible = Boolean.TRUE;
                FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.search.found");
                ucDeliveryService = new UcDeliveryService();
                ucGatedeliveryService = new UcGatedeliveryService();
                uncontainerizedService = new UncontainerizedService();
            } else {
                FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.search.notfound");
                visible = Boolean.TRUE;
                ucDeliveryService = new UcDeliveryService();
                ucGatedeliveryService = new UcGatedeliveryService();
                uncontainerizedService = new UncontainerizedService();
            }
        } catch (RuntimeException re) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", re);
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.loading.error");
        }
    }

    public void handleDownload(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesContext fc = FacesContext.getCurrentInstance();
        context.addCallbackParam("doPrint", true); //harus ada, buat ngeflag di izinkan ngeprint ato ngga
        context.addCallbackParam("target", (String) event.getComponent().getAttributes().get("target"));
        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3(UrlHelper.resolveFullAddress(fc, "/gateInReportTest.pdf?mode=deliveryUc" + "&no_ppkb=" + noPpkb + "&blNo=" + blNo)));

    }

    public void saveEdit(ActionEvent event) {
        RequestContext requestContext = RequestContext.getCurrentInstance();

        try {
            ucGatedeliveryService.setUcWeight(uncontainerizedService.getWeight());
            ucGatedeliveryService.setDateIn(Calendar.getInstance().getTime());
            ucGatedeliveryServiceFacadeRemote.create(ucGatedeliveryService);
            planningVessel = planningVesselFacadeRemote.find(uncontainerizedService.getNoPpkb());
            uncontainerized = ucDeliveryService.getUncontainerizedService();
            vesselName = planningVessel.getPreserviceVessel().getMasterVessel().getName();
            voyIn = planningVessel.getPreserviceVessel().getVoyIn();
            voyOut = planningVessel.getPreserviceVessel().getVoyOut();
            Block = (uncontainerized.getTruckLoosing().equals("TRUE")) ? planningVessel.getMasterDock().getName() : uncontainerized.getBlock();
            blNo = uncontainerized.getBlNo();
            noPpkb = uncontainerized.getNoPpkb();
            ucDeliveryService = new UcDeliveryService();
            ucGatedeliveryService = new UcGatedeliveryService();
            masterVehicle = new MasterVehicle();
            uncontainerizedService = new UncontainerizedService();
            visible = Boolean.FALSE;
            requestContext.addCallbackParam("loggedIn", true);
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
        } catch (RuntimeException re) {
            requestContext.addCallbackParam("loggedIn", false);
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Warning", "application.save.failed");
        }
    }

    public void onChangeConstraintType(ValueChangeEvent event) {
        String newItem = (String) event.getNewValue();

        masterVehicle = masterVehicleFacadeRemote.find(newItem);
        int b = (int) masterVehicle.getTonage();
        ucGatedeliveryService.setVehicleWeight(b);
    }

    public void onChangeUcDelUseTruck(ValueChangeEvent event) {
        String newItem = (String) event.getNewValue();

        if(newItem.equals("0")){
            System.out.println("test1");
            ucGatedeliveryService.setVehicleCode(null);
            ucGatedeliveryService.setVehicleWeight(0);
            masterVehicle = null;
        }
    }

    public void handleLookupMasterVehicle(ActionEvent event) {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        FacesContext facesContext = FacesContext.getCurrentInstance();

        if (ucGatedeliveryService.getVehicleCode() != null) {
            masterVehicle = masterVehicleFacadeRemote.find(ucGatedeliveryService.getVehicleCode());
            
            if (masterVehicle != null) {
                ucGatedeliveryService.setVehicleCode(masterVehicle.getVehicleCode());
                ucGatedeliveryService.setVehicleWeight((int) masterVehicle.getTonage());
                requestContext.addCallbackParam("loggedIn", true);
                return;
            }

            requestContext.addCallbackParam("loggedIn", false);
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_WARN, "Warning", "application.validation.license_plate.not_registered");
            return;
        }

        ucGatedeliveryService.setVehicleCode(null);
        ucGatedeliveryService.setVehicleWeight(null);
    }

    public void handleResetMasterVehicle(ActionEvent event) {
        ucGatedeliveryService.setVehicleCode(null);
        ucGatedeliveryService.setVehicleWeight(null);
    }

    public String getBlock() {
        return Block;
    }

    public void setBlock(String Block) {
        this.Block = Block;
    }

    public String getVesselName() {
        return vesselName;
    }

    public void setVesselName(String vesselName) {
        this.vesselName = vesselName;
    }

    public String getVoyIn() {
        return voyIn;
    }

    public void setVoyIn(String voyIn) {
        this.voyIn = voyIn;
    }

    public String getVoyOut() {
        return voyOut;
    }

    public void setVoyOut(String voyOut) {
        this.voyOut = voyOut;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public UncontainerizedService getUncontainerized() {
        return uncontainerized;
    }

    public void setUncontainerized(UncontainerizedService uncontainerized) {
        this.uncontainerized = uncontainerized;
    }

    public PlanningVessel getPlanningVessel() {
        return planningVessel;
    }

    public void setPlanningVessel(PlanningVessel planningVessel) {
        this.planningVessel = planningVessel;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public MasterVehicle getMasterVehicle() {
        return masterVehicle;
    }

    public void setMasterVehicle(MasterVehicle masterVehicle) {
        this.masterVehicle = masterVehicle;
    }

    public List<MasterVehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<MasterVehicle> vehicles) {
        this.vehicles = vehicles;
    }

    public Object[] getUcGateDeliveryObject() {
        return ucGateDeliveryObject;
    }

    public void setUcGateDeliveryObject(Object[] ucGateDeliveryObject) {
        this.ucGateDeliveryObject = ucGateDeliveryObject;
    }

    public Object[] getUcDeliveryObject() {
        return ucDeliveryObject;
    }

    public void setUcDeliveryObject(Object[] ucDeliveryObject) {
        this.ucDeliveryObject = ucDeliveryObject;
    }

    public UcGatedeliveryService getUcGatedeliveryService() {
        return ucGatedeliveryService;
    }

    public void setUcGatedeliveryService(UcGatedeliveryService ucGatedeliveryService) {
        this.ucGatedeliveryService = ucGatedeliveryService;
    }

    public String getJobSlip() {
        return jobSlip;
    }

    public void setJobSlip(String jobSlip) {
        this.jobSlip = jobSlip;
    }

    public UcDeliveryService getUcDeliveryService() {
        return ucDeliveryService;
    }

    public void setUcDeliveryService(UcDeliveryService ucDeliveryService) {
        this.ucDeliveryService = ucDeliveryService;
    }

    public UncontainerizedService getUncontainerizedService() {
        return uncontainerizedService;
    }

    public void setUncontainerizedService(UncontainerizedService uncontainerizedService) {
        this.uncontainerizedService = uncontainerizedService;
    }

    public String getBlNo() {
        return blNo;
    }

    public void setBlNo(String blNo) {
        this.blNo = blNo;
    }

    public String getNoPpkb() {
        return noPpkb;
    }

    public void setNoPpkb(String noPpkb) {
        this.noPpkb = noPpkb;
    }

    /**
     * @return the ucDelUseTruck
     */
    public String getUcDelUseTruck() {
        return ucDelUseTruck;
    }

    /**
     * @param ucDelUseTruck the ucDelUseTruck to set
     */
    public void setUcDelUseTruck(String ucDelUseTruck) {
        this.ucDelUseTruck = ucDelUseTruck;
    }

      public MasterCommodity getMasterCommodity() {
        return masterCommodity;
    }

    public void setMasterCommodity(MasterCommodity uncontainerizedService) {
        this.masterCommodity = masterCommodity;
    }
}
