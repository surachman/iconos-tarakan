/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.bean;

import com.qtasnim.jsf.FacesHelper;
import com.qtasnim.url.UrlHelper;
import com.pelindo.ebtos.ejb.facade.remote.*;
import com.pelindo.ebtos.model.db.PlanningVessel;
import com.pelindo.ebtos.model.db.UcGatereceivingService;
import com.pelindo.ebtos.model.db.UcReceivingService;
import com.pelindo.ebtos.model.db.UncontainerizedService;
import com.pelindo.ebtos.model.db.master.MasterCommodity;
import com.pelindo.ebtos.model.db.master.MasterVehicle;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import org.apache.commons.lang3.StringEscapeUtils;
import org.primefaces.context.RequestContext;

/**
 *
 * @author dycoder
 */
@ManagedBean(name = "gateInReceivingUcBean")
@ViewScoped
public class GateInReceivingUcBean implements Serializable {
    @EJB
    private MasterVehicleFacadeRemote masterVehicleFacadeRemote;
    @EJB
    private UcReceivingServiceFacadeRemote ucReceivingServiceFacadeRemote;
    @EJB
    private UcGatereceivingServiceFacadeRemote ucGatereceivingServiceFacadeRemote;
    @EJB
    private PlanningVesselFacadeRemote planningVesselFacadeRemote;
    @EJB
    private UncontainerizedServiceFacadeRemote uncontainerizedServiceFacadeRemote;
    @EJB
    private MasterCommodityFacadeRemote masterCommodityFacadeRemote;

    private List<MasterVehicle> vehicles;
    private UcReceivingService ucReceivingService;
    private UncontainerizedService uncontainerizedService;
    private MasterCommodity masterCommodity;
    private UcGatereceivingService ucGatereceivingService;
    private MasterVehicle masterVehicle;
    private PlanningVessel planningVessel;
    private Object[] ucReceivingObject;
    private Object[] ucGateReceivingObject;
    private String mode;
    private String vesselName;
    private String voyIn;
    private String voyOut;
    private String Block;
    private String blNo;
    private String noPpkb;
    private String jobSlip;
    private boolean visible;
    private String ucRecUseTruck = null;

    /** Creates a new instance of GateInReceivingUcBean */
    public GateInReceivingUcBean() {}

    @PostConstruct
    private void construct(){
        ucReceivingService = new UcReceivingService();
        uncontainerizedService = new UncontainerizedService();
        masterCommodity = new MasterCommodity();
        ucGatereceivingService = new UcGatereceivingService();
        planningVessel = new PlanningVessel();
        
        visible = false;
        vehicles = masterVehicleFacadeRemote.findAll();
        ucRecUseTruck = "1";
    }

    public List<String> setListAutoComplete(String query) {
        List<String> results = ucReceivingServiceFacadeRemote.findGateInPassableJobSlips(query);
        return results;
    }

    public void ambilContNo(ActionEvent event) {
        //this.clear();
        jobSlip = (String) event.getComponent().getAttributes().get("jobSlip");
        ucReceivingObject = ucReceivingServiceFacadeRemote.findGateInPassableByJobSlip(jobSlip);
        ucGateReceivingObject = ucGatereceivingServiceFacadeRemote.findUcGatereceivingServiceByGatee(jobSlip);

        if (ucReceivingObject != null) {
            ucReceivingService = ucReceivingServiceFacadeRemote.find(ucReceivingObject[0]);
            ucGatereceivingService.setJobslip(ucReceivingService.getJobslip());
            uncontainerizedService = ucReceivingService.getUncontainerizedService();
            
            //MasterCommodity commodity = masterCommodityFacadeRemote.find(uncontainerizedService.getCommodity());
            //uncontainerizedService.setCommodity(commodity.getName());

            masterCommodity = masterCommodityFacadeRemote.find(uncontainerizedService.getCommodity());

            planningVessel = new PlanningVessel();
            visible = false;
            
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.search.found");
        } else if (ucGateReceivingObject != null) {
            ucGatereceivingService = ucGatereceivingServiceFacadeRemote.find(ucGateReceivingObject[0]);
            ucGatereceivingService.setWeight((Integer) ucGateReceivingObject[4]);
            ucReceivingService = ucReceivingServiceFacadeRemote.find(ucGatereceivingService.getJobslip());
            uncontainerizedService = ucReceivingService.getUncontainerizedService();
            
            masterCommodity = masterCommodityFacadeRemote.find(uncontainerizedService.getCommodity());
            
            planningVessel = planningVesselFacadeRemote.find(uncontainerizedService.getNoPpkb());
            vesselName = planningVessel.getPreserviceVessel().getMasterVessel().getName();
            voyIn = planningVessel.getPreserviceVessel().getVoyIn();
            voyOut = planningVessel.getPreserviceVessel().getVoyOut();
            Block = uncontainerizedService.getTruckLoosing().equals("TRUE") ? planningVessel.getMasterDock().getName() : uncontainerizedService.getBlock();
            blNo = uncontainerizedService.getBlNo();
            noPpkb = uncontainerizedService.getNoPpkb();

            visible = true;
            ucReceivingService = new UcReceivingService();
            uncontainerizedService = new UncontainerizedService();
            ucGatereceivingService = new UcGatereceivingService();

            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.search.found");
        } else {
            visible = true;
            ucReceivingService = new UcReceivingService();
            uncontainerizedService = new UncontainerizedService();
            ucGatereceivingService = new UcGatereceivingService();
            planningVessel = new PlanningVessel();

            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.search.notfound");
        }
    }

    public void clear() {
        ucReceivingService = new UcReceivingService();
        uncontainerizedService = new UncontainerizedService();
        ucGatereceivingService = new UcGatereceivingService();
        planningVessel = new PlanningVessel();
    }

    public void saveEdit(ActionEvent event) {
        RequestContext requestContext = RequestContext.getCurrentInstance();

        try {
            Date now = new Date();
            planningVessel = planningVesselFacadeRemote.find(uncontainerizedService.getNoPpkb());

            if (uncontainerizedService.getTruckLoosing().equals("TRUE")) {
                uncontainerizedService.setStatus("STV");
            }

            uncontainerizedService.setWeight(ucGatereceivingService.getUcWeight());
            uncontainerizedService.setPlacementDate(now);
            ucGatereceivingService.setDateIn(now);

            ucGatereceivingServiceFacadeRemote.create(ucGatereceivingService);
            uncontainerizedServiceFacadeRemote.edit(uncontainerizedService);

            vesselName = planningVessel.getPreserviceVessel().getMasterVessel().getName();
            voyIn = planningVessel.getPreserviceVessel().getVoyIn();
            voyOut = planningVessel.getPreserviceVessel().getVoyOut();
            Block = uncontainerizedService.getTruckLoosing().equals("TRUE") ? planningVessel.getMasterDock().getName() : uncontainerizedService.getBlock();
            blNo = uncontainerizedService.getBlNo();
            noPpkb = uncontainerizedService.getNoPpkb();
            
            ucReceivingService = new UcReceivingService();
            uncontainerizedService = new UncontainerizedService();
            ucGatereceivingService = new UcGatereceivingService();
            visible = false;
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
            requestContext.addCallbackParam("loggedIn", true);
        } catch (RuntimeException re) {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Warning", "application.save.failed");
            requestContext.addCallbackParam("loggedIn", false);
        }
    }

    public void handleDownload(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesContext fc = FacesContext.getCurrentInstance();
        context.addCallbackParam("doPrint", true); //harus ada, buat ngeflag di izinkan ngeprint ato ngga
        context.addCallbackParam("target", (String) event.getComponent().getAttributes().get("target"));
        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3(UrlHelper.resolveFullAddress(fc, "/gateInReportTest.pdf?mode=" + "receivingUc" + "&no_ppkb=" + noPpkb + "&blNo=" + blNo)));

    }

    public void onChangeConstraintType(ValueChangeEvent event) {
        String newItem = (String) event.getNewValue();

        masterVehicle = masterVehicleFacadeRemote.find(newItem);
        ucGatereceivingService.setVehicleWeight((int) masterVehicle.getTonage());
    }

    public void onChangeUcRecUseTruck(ValueChangeEvent event) {
        String newItem = (String) event.getNewValue();

        if(newItem.equals("0")){
            ucGatereceivingService.setVehicleCode(null);
            ucGatereceivingService.setVehicleWeight(0);
            masterVehicle = null;
        }
    }

    public void handleLookupMasterVehicle(ActionEvent event) {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        FacesContext facesContext = FacesContext.getCurrentInstance();

        if (ucGatereceivingService.getVehicleCode() != null) {
            masterVehicle = masterVehicleFacadeRemote.find(ucGatereceivingService.getVehicleCode());
            
            if (masterVehicle != null) {
                ucGatereceivingService.setVehicleCode(masterVehicle.getVehicleCode());
                ucGatereceivingService.setVehicleWeight((int) masterVehicle.getTonage());
                requestContext.addCallbackParam("loggedIn", true);
                return;
            }

            requestContext.addCallbackParam("loggedIn", false);
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_WARN, "Warning", "application.validation.license_plate.not_registered");
            return;
        }

        ucGatereceivingService.setVehicleCode(null);
        ucGatereceivingService.setVehicleWeight(null);
    }

    public void handleResetMasterVehicle(ActionEvent event) {
        ucGatereceivingService.setVehicleCode(null);
        ucGatereceivingService.setVehicleWeight(null);
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

    public PlanningVessel getPlanningVessel() {
        return planningVessel;
    }

    public void setPlanningVessel(PlanningVessel planningVessel) {
        this.planningVessel = planningVessel;
    }

    public String getJobSlip() {
        return jobSlip;
    }

    public void setJobSlip(String jobSlip) {
        this.jobSlip = jobSlip;
    }

    public MasterVehicle getMasterVehicle() {
        return masterVehicle;
    }

    public void setMasterVehicle(MasterVehicle masterVehicle) {
        this.masterVehicle = masterVehicle;
    }

    public Object[] getUcGateReceivingObject() {
        return ucGateReceivingObject;
    }

    public void setUcGateReceivingObject(Object[] ucGateReceivingObject) {
        this.ucGateReceivingObject = ucGateReceivingObject;
    }

    public UcGatereceivingService getUcGatereceivingService() {
        return ucGatereceivingService;
    }

    public void setUcGatereceivingService(UcGatereceivingService ucGatereceivingService) {
        this.ucGatereceivingService = ucGatereceivingService;
    }

    public Object[] getUcReceivingObject() {
        return ucReceivingObject;
    }

    public void setUcReceivingObject(Object[] ucReceivingObject) {
        this.ucReceivingObject = ucReceivingObject;
    }

    public UcReceivingService getUcReceivingService() {
        return ucReceivingService;
    }

    public void setUcReceivingService(UcReceivingService ucReceivingService) {
        this.ucReceivingService = ucReceivingService;
    }

    public UncontainerizedService getUncontainerizedService() {
        return uncontainerizedService;
    }

    public void setUncontainerizedService(UncontainerizedService uncontainerizedService) {
        this.uncontainerizedService = uncontainerizedService;
    }

    public MasterCommodity getMasterCommodity() {
        return masterCommodity;
    }

    public void setMasterCommodity(MasterCommodity uncontainerizedService) {
        this.masterCommodity = masterCommodity;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public List<MasterVehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<MasterVehicle> vehicles) {
        this.vehicles = vehicles;
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
     * @return the ucRecUseTruck
     */
    public String getUcRecUseTruck() {
        return ucRecUseTruck;
    }

    /**
     * @param ucRecUseTruck the ucRecUseTruck to set
     */
    public void setUcRecUseTruck(String ucRecUseTruck) {
        this.ucRecUseTruck = ucRecUseTruck;
    }
}
