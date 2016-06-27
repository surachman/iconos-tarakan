/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.bean;

import com.qtasnim.jsf.FacesHelper;
import com.pelindo.ebtos.ejb.facade.remote.MasterCustomerFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterProfileDetailFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterVesselProfileFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterYardCoordinatFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterYardFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningContLoadFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningShiftDischargeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningVesselFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceContTranshipmentFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceReceivingFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceShiftingFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.UncontainerizedServiceFacadeRemote;
import com.pelindo.ebtos.model.db.PlanningContLoad;
import com.pelindo.ebtos.model.db.PlanningReceivingAllocation;
import com.pelindo.ebtos.model.db.PlanningShiftDischarge;
import com.pelindo.ebtos.model.db.PlanningVessel;
import com.pelindo.ebtos.model.db.ServiceContTranshipment;
import com.pelindo.ebtos.model.db.ServiceReceiving;
import com.pelindo.ebtos.model.db.ServiceShifting;
import com.pelindo.ebtos.model.db.UncontainerizedService;
import com.pelindo.ebtos.model.db.master.MasterCommodity;
import com.pelindo.ebtos.model.db.master.MasterContainerType;
import com.pelindo.ebtos.model.db.master.MasterCustomer;
import com.pelindo.ebtos.model.db.master.MasterGrossClass;
import com.pelindo.ebtos.model.db.master.MasterYard;
import com.pelindo.ebtos.model.db.master.MasterYardCoordinat;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import org.primefaces.context.RequestContext;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.apache.commons.lang3.StringEscapeUtils;

/**
 *
 * @author dycode-java
 */
@ManagedBean(name = "bayPlanLoadBean")
@ViewScoped
public class BayPlanLoadBean implements Serializable {

    @EJB
    private PlanningVesselFacadeRemote planningVesselFacade;
    @EJB
    private ServiceReceivingFacadeRemote serviceReceivingFacadeRemote;
    @EJB
    private MasterYardCoordinatFacadeRemote masterYardCoordinatFacadeRemote;
    @EJB
    private PlanningContLoadFacadeRemote planningContLoadFacadeRemote;
    @EJB
    private ServiceContTranshipmentFacadeRemote serviceContTranshipmentFacadeRemote;
    @EJB
    private MasterVesselProfileFacadeRemote masterVesselProfileFacadeRemote;
    @EJB
    private MasterProfileDetailFacadeRemote masterProfileDetailFacadeRemote;
    @EJB
    private MasterYardFacadeRemote masterYardFacadeRemote;
    @EJB
    private UncontainerizedServiceFacadeRemote uncontainerizedServiceFacadeRemote;
    @EJB
    private ServiceShiftingFacadeRemote serviceShiftingFacadeRemote;
    @EJB
    private PlanningShiftDischargeFacadeRemote planningShiftDischargeFacadeRemote;
    @EJB
    private MasterCustomerFacadeRemote masterCustomerFacadeRemote;

    private List<PlanningContLoad> notPlannedContainersOnYard = new ArrayList();
    private List<PlanningContLoad> plannedContainersOnBay = new ArrayList();
    private List<Object[]> planningVessels = new ArrayList();
    private List<Object[]> serviceReceivingListObject = new ArrayList();
    private List<Object[]> planningContLoadList = new ArrayList();
    private List<Object[]> planningLoadCekListCompleted = new ArrayList();
    private List<Object[]> planningReceivingSearchId = new ArrayList();
    private List<Object[]> planningReceivingList = new ArrayList();
    private List<Object[]> uncontainerizedList = new ArrayList();
    private List<Object[]> uncontainerizedSelect = new ArrayList();
    private List<Object[]> serviceShftingList = new ArrayList();
    private List<Integer> bayChoices = new ArrayList();
    private List<Integer> rowChoices = new ArrayList();
    private List<Integer> tierChoices = new ArrayList();
    private List<Integer> rowUps = new ArrayList();
    private List<Integer> rowBottoms = new ArrayList();
    private List<Integer> slots = new ArrayList();
    private List<String> blocks = new ArrayList();
    private List<String> bays = new ArrayList();
    private List<String> pods = new ArrayList();
    private ServiceReceiving serviceReceiving2;
    private PlanningContLoad planningContLoad;
    private PlanningVessel planningVessell;
    private MasterCommodity masterCommodity;
    private MasterYard masterYard;
    private MasterContainerType masterContainerType;
    private MasterGrossClass masterGrossClass;
    private ServiceContTranshipment serviceContTranshipment;
    private PlanningReceivingAllocation planningReceivingAllocation;
    private UncontainerizedService uncontainerizedService;
    private ServiceShifting serviceShifting;
    private PlanningShiftDischarge planningShiftDischarge;
    private Object[][] serviceReceivingArray;
    private Object[][] serviceTranshipmentArray;
    private Object[][] bayplanUncontainerized;
    private Object[] planningVessel;
    private Object[] serviceReceiving;
    private Object[] masterYard2;
    private Object[] selectedBayLocation;
    private Boolean disable = false;
    private Boolean enableZoom;
    private Integer contNo;
    private Integer idtrans = null;
    private Integer selectedSlot;
    private String noPpkb;
    private String vesselCode;
    private String tipe = "cont";
    private String selectedBlock;
    private String selectedBay;
    private String selectedPod;

    /** Creates a new instance of BayPlanLoadBean */
    public BayPlanLoadBean() {
    }

    @PostConstruct
    private void construct() {
        planningContLoad = new PlanningContLoad();
        serviceReceiving2 = new ServiceReceiving();
        planningVessell = new PlanningVessel();
        masterCommodity = new MasterCommodity();
        masterYard = new MasterYard();
        masterContainerType = new MasterContainerType();
        masterGrossClass = new MasterGrossClass();
        planningVessel = new Object[1];
        serviceContTranshipment = new ServiceContTranshipment();
        planningReceivingAllocation = new PlanningReceivingAllocation();
        uncontainerizedService = new UncontainerizedService();
        serviceShifting = new ServiceShifting();
        planningShiftDischarge = new PlanningShiftDischarge();

    }

    public void clearSave() {
        planningContLoad = new PlanningContLoad();
        serviceReceiving2 = new ServiceReceiving();
        planningVessell = new PlanningVessel();
        masterCommodity = new MasterCommodity();
        masterYard = new MasterYard();
        masterContainerType = new MasterContainerType();
        masterGrossClass = new MasterGrossClass();
        serviceContTranshipment = new ServiceContTranshipment();
        uncontainerizedService = new UncontainerizedService();
        serviceShifting = new ServiceShifting();
        planningShiftDischarge = new PlanningShiftDischarge();
        this.idtrans = null;
    }

    public void handleViewppkb(ActionEvent event) {
        planningVessels = planningVesselFacade.findPlanningVesselsReceivingConfirm();
    }

    public boolean getVisible() {
        if (getPlanningVessel()[0] == null) {
            return true;
        }
        return false;
    }

    /**
     * @return the planningVessels
     */
    public List<Object[]> getServiceReceivings() {
        return serviceReceivingFacadeRemote.findServiceReceivings();
    }

    public void handleSelectDeleteUc(ActionEvent event) {
        Integer id = (Integer) event.getComponent().getAttributes().get("idCont");
        uncontainerizedService = uncontainerizedServiceFacadeRemote.find(id);
    }

    public void deleteUc(ActionEvent event) {
        try {
            Integer id = uncontainerizedServiceFacadeRemote.findUncontainerizedBayPlanLoadId(uncontainerizedService.getTempPpkb(), uncontainerizedService.getBlNo());
            UncontainerizedService un = uncontainerizedServiceFacadeRemote.find(id);
            un.setBayPlan("FALSE");
            uncontainerizedServiceFacadeRemote.edit(un);
            uncontainerizedServiceFacadeRemote.remove(uncontainerizedService);
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.delete.succeeded");
        } catch (EJBException e) {
            e.printStackTrace();
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.delete.failed");
        }
        //uncontainerized
        uncontainerizedList = uncontainerizedServiceFacadeRemote.findUncontainerizedBayPlanLoadList(noPpkb);
        uncontainerizedSelect = uncontainerizedServiceFacadeRemote.findUncontainerizedBayPlanLoadListSelect(noPpkb);
    }

    public void handleSaveUc(ActionEvent event) {
        try {
            for (int j = 0; j < bayplanUncontainerized.length; j++) {
                //ServiceContDischarge scd = serviceContDischargeFacade.find(deliveryContainer[j][9]);
                uncontainerizedService = uncontainerizedServiceFacadeRemote.find(bayplanUncontainerized[j][0]);
                UncontainerizedService unz = new UncontainerizedService();
                unz.setBlNo(uncontainerizedService.getBlNo());
                unz.setBlock(uncontainerizedService.getBlock());
                unz.setCommodity(uncontainerizedService.getCommodity());
                unz.setCrane(uncontainerizedService.getCrane());
                unz.setDischPort(uncontainerizedService.getDischPort());
                unz.setIsDelivery(uncontainerizedService.getIsDelivery());
                unz.setIsShifting(uncontainerizedService.getIsShifting());
                unz.setLoadPort(uncontainerizedService.getLoadPort());
                unz.setTruckLoosing(uncontainerizedService.getTruckLoosing());
                unz.setUnit(uncontainerizedService.getUnit());
                unz.setWeight(uncontainerizedService.getWeight());
                unz.setShiftTo(uncontainerizedService.getShiftTo());
                unz.setStatus(uncontainerizedService.getStatus());
                unz.setOperation("LOAD");

                unz.setIsTranshipment("FALSE");
                unz.setNoPpkb(noPpkb);
                unz.setPlacementDate(uncontainerizedService.getPlacementDate());
                unz.setTempPpkb(uncontainerizedService.getNoPpkb());
                unz.setBayPlan("TRUE");
                uncontainerizedServiceFacadeRemote.create(unz);
                FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
            }
        } catch (EJBException e) {
            e.printStackTrace();
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.failed");
        }
        //uncontainerized
        uncontainerizedList = uncontainerizedServiceFacadeRemote.findUncontainerizedBayPlanLoadList(noPpkb);
        uncontainerizedSelect = uncontainerizedServiceFacadeRemote.findUncontainerizedBayPlanLoadListSelect(noPpkb);
    }

    //button finih buat mengclearkn CY + set completed planing cont_load jadi true
    public void clearYardCordinat(ActionEvent actionEvent) {
        try {
            int result = masterYardCoordinatFacadeRemote.clearYardByPpkbStatusPlanning(noPpkb);
            result = planningContLoadFacadeRemote.setCompletedByNoPpkb(noPpkb);
            disable = Boolean.TRUE;
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.transaction.succeeded");
        } catch (RuntimeException re) {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_ERROR, "Info", "application.transaction.failed");
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "clearYardCordinat has failed", re);
        }

    }

    //create generate bayplan planning
    public void createPlanList(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        boolean loggedIn = false;
        serviceReceivingListObject = serviceReceivingFacadeRemote.findServiceReceivingByPpkbList(getNoPpkb());

        if (serviceReceivingListObject.size() < 1) {
            loggedIn = false;
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.transaction.failed");
        } else {
            loggedIn = true;

            for (Object[] src : serviceReceivingListObject) {
                String b = (String) src[1];
                masterCommodity.setCommodityCode(b);
                planningContLoad.setMasterCommodity(masterCommodity);
                String y = (String) src[2];
                masterYard.setBlock(y);
                planningContLoad.setMasterYard(masterYard);
                int c = (Integer) src[3];
                masterContainerType.setContType(c);
                planningContLoad.setMasterContainerType(masterContainerType);
                String p = (String) src[4];
                MasterCustomer mlo = masterCustomerFacadeRemote.find(src[17]);
                planningVessell.setNoPpkb(p);
                planningContLoad.setPlanningVessel(planningVessell);
                planningContLoad.setContNo((String) src[5]);
                planningContLoad.setMlo(mlo);
                planningContLoad.setContSize(Short.valueOf(src[6].toString()));
                planningContLoad.setContStatus((String) src[7]);
                planningContLoad.setIsTranshipment("FALSE");

                planningContLoad.setPosition("03");
                planningContLoad.setContGross((Integer) src[8]);
                planningContLoad.setSealNo((String) src[9]);
                
                if (Boolean.parseBoolean((String) src[11].toString()) == true) {
                    planningContLoad.setOverSize("TRUE");
                } else {
                    planningContLoad.setOverSize("FALSE");
                }
                if (Boolean.parseBoolean((String) src[11].toString()) == true) {
                    planningContLoad.setDgLabel("TRUE");
                } else {
                    planningContLoad.setDgLabel("FALSE");
                }
                if(Boolean.parseBoolean((String) src[12].toString()) == true) {
                    planningContLoad.setDg("TRUE");
                } else {
                    planningContLoad.setDg("FALSE");
                }
                planningContLoad.setYSlot(Short.valueOf(src[13].toString()));
                planningContLoad.setYRow(Short.valueOf((String) src[14].toString()));
                planningContLoad.setYTier(Short.valueOf((String) src[15].toString()));
                planningContLoad.setCompleted("FALSE");
                planningContLoad.setUnknown("FALSE");
                planningContLoad.setBlNo((String) src[16]);
                planningContLoadFacadeRemote.create(planningContLoad);

            }
//            planningContLoadList = planningContLoadFacadeRemote.findPlanningBayplanLoadsByPpkb(getNoPpkb());
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.transaction.succeeded");
        }
        planningContLoadList = planningContLoadFacadeRemote.findPlanningBayplanLoadsByPpkb(getNoPpkb());
//        serviceReceivingListObject = serviceReceivingFacadeRemote.findServiceReceivingByPpkbList(getNoPpkb());
        planningContLoad = new PlanningContLoad();
        //serviceTranshipmentListObject = serviceContTranshipmentFacadeRemote.findServiceContTranshipmentByPpkbList(getNo_ppkb());       
        context.addCallbackParam("loggedIn", loggedIn);
    }

    public void handleEditDeleteButton(ActionEvent event) {
        Integer idPlan = (Integer) event.getComponent().getAttributes().get("idPlan");
        planningContLoad = planningContLoadFacadeRemote.find(idPlan);
        this.tipe = "cont";
        if (planningContLoad.getVBay() == null) {
            planningContLoad.setVBay(Short.parseShort(bayChoices.get(0).toString()));
            changeBay(planningContLoad.getVBay());
            changeRow(Short.parseShort(rowChoices.get(0).toString()));
        } else {
            changeBay(planningContLoad.getVBay());
            changeRow(planningContLoad.getVRow());
        }
    }

    public void handleEditDeleteButtonShift(ActionEvent event) {
        Integer idPlan = (Integer) event.getComponent().getAttributes().get("idPlan");
        Integer idService = (Integer) event.getComponent().getAttributes().get("idService");
        planningShiftDischarge = planningShiftDischargeFacadeRemote.find(idService);
        serviceShifting = serviceShiftingFacadeRemote.find(idPlan);
        this.tipe = "shift";
        changeBay(serviceShifting.getVBay());
        changeRow(serviceShifting.getVRow());
    }

    public void handleEditDelete(ActionEvent event) {
        Integer idPlan = (Integer) event.getComponent().getAttributes().get("idPlan");
        planningContLoad = planningContLoadFacadeRemote.find(idPlan);
        this.idtrans = serviceContTranshipmentFacadeRemote.findByIdTranshipment(planningContLoad.getPlanningVessel().getNoPpkb(), planningContLoad.getContNo());
    }

    public void handleSelectNoPpkb(ActionEvent event) {
        noPpkb = (String) event.getComponent().getAttributes().get("noPpkb");
        planningVessel = planningVesselFacade.findPlanningVesselByPpkb(noPpkb);

        planningContLoadList = planningContLoadFacadeRemote.findPlanningBayplanLoadsByPpkb(getNoPpkb());
        serviceReceivingListObject = serviceReceivingFacadeRemote.findServiceReceivingByPpkbList(getNoPpkb());
        vesselCode = planningVessel[10].toString();

        disable = false;
        for (Object[] pln : planningContLoadList) {
            if (pln[19].equals(Boolean.TRUE)) {
                disable = true;
                break;
            }
        }

        reloadBayPlanParameter();

        bayChoices.clear();
        bayChoices = masterVesselProfileFacadeRemote.findBaysByVessel(vesselCode);
        uncontainerizedList = uncontainerizedServiceFacadeRemote.findUncontainerizedBayPlanLoadList(noPpkb);
        uncontainerizedSelect = uncontainerizedServiceFacadeRemote.findUncontainerizedBayPlanLoadListSelect(noPpkb);
        serviceShftingList = serviceShiftingFacadeRemote.findByPpkbBayPlanLoadList(noPpkb);
    }

    public void handlePrintBayPlanLoad(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        context.addCallbackParam("doPrint", true);
        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3("../../../../bayPlanLoadReport.pdf?noPpkb=" + noPpkb));
    }

    public void handlePrintBayPlanLoadCY(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        context.addCallbackParam("doPrint", true);
        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3("../../../../bayPlanLoadCYReport.pdf?noPpkb=" + noPpkb + "&block=" + selectedBlock + "&slot=" + selectedSlot + "&pod=" + selectedPod));
    }

    private void loadNotPlannedContainers() {
        pods = planningContLoadFacadeRemote.findPodByNoPpkbAndYardLocation(noPpkb, selectedBlock, selectedSlot.shortValue());

        if (selectedPod == null) {
            notPlannedContainersOnYard = planningContLoadFacadeRemote.findNotPlannedContainerByNoPpkbAndYardLocation(noPpkb, selectedBlock, selectedSlot.shortValue());
        } else {
            notPlannedContainersOnYard = planningContLoadFacadeRemote.findNotPlannedContainerByPodNoPpkbAndYardLocation(noPpkb, selectedBlock, selectedSlot.shortValue(), selectedPod);
        }
    }

    private void reloadBayPlanParameter() {
        blocks = masterYardFacadeRemote.findNotPlannedBlockLocationByNoPpkb(noPpkb);
        slots.clear();
        notPlannedContainersOnYard.clear();

        if (!blocks.isEmpty() && !disable) {
            if (selectedBlock == null || !blocks.contains(selectedBlock))
                selectedBlock = blocks.get(0);
            slots = masterYardFacadeRemote.findNotPlannedSlotLocationByNoPpkbAndBlock(noPpkb, selectedBlock);
        }

        if (!slots.isEmpty() && !disable) {
            if (selectedSlot == null || !slots.contains(selectedSlot))
                selectedSlot = slots.get(0);
            loadNotPlannedContainers();
            pods = planningContLoadFacadeRemote.findPodByNoPpkbAndYardLocation(noPpkb, selectedBlock, selectedSlot.shortValue());
        }

        selectedPod = null;
        bays = masterVesselProfileFacadeRemote.findBaysByNoPpkb(noPpkb);
        
        if (!bays.isEmpty()) {
            if (selectedBay == null || !bays.contains(selectedBay))
                selectedBay = bays.get(0);
            Integer bay = Integer.parseInt(selectedBay);
            planningContLoad.setVBay(bay.shortValue());
            changeBay(bay.shortValue());
            plannedContainersOnBay = planningContLoadFacadeRemote.findPlannedContainerByBayLocation(noPpkb, bay.shortValue());
        }
    }

    public void delete(ActionEvent event) {
        try {
            RequestContext context = RequestContext.getCurrentInstance();
            planningContLoadFacadeRemote.remove(planningContLoad);

            //cek service transhipment
            if (getIdtrans() != null) {
                serviceContTranshipment = serviceContTranshipmentFacadeRemote.find(idtrans);
                serviceContTranshipment.setNewPpkb(null);
                serviceContTranshipmentFacadeRemote.edit(serviceContTranshipment);
            }

            planningContLoadList = planningContLoadFacadeRemote.findPlanningBayplanLoadsByPpkb(getNoPpkb());
            serviceReceivingListObject = serviceReceivingFacadeRemote.findServiceReceivingByPpkbList(getNoPpkb());
            planningContLoad = new PlanningContLoad();
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.delete.succeeded");
        } catch (EJBException ex) {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_ERROR, "Info", "application.delete.failed");
            ex.printStackTrace();
        }
    }

    public void handleSubmit(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        boolean loggedIn = false;
        Object[] plan = null;
        //di komen dulu gan dulu buat validasi bay nya...
        //plan = planningContLoadFacadeRemote.findPlanningBayplanLoadCekBayExist(planningContLoad.getVBay(), planningContLoad.getVRow(), planningContLoad.getVTier(), getNo_ppkb());

        if (plan != null) {
            loggedIn = false;
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.transaction.failed");

        } else {
            loggedIn = true;
            planningContLoadFacadeRemote.edit(planningContLoad);
            planningContLoadList = planningContLoadFacadeRemote.findPlanningBayplanLoadsByPpkb(getNoPpkb());
            planningContLoad = new PlanningContLoad();
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.transaction.succeeded");
        }
        context.addCallbackParam("loggedIn", loggedIn);
    }

    public void handleSubmitShift(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        boolean loggedIn = false;
        Object[] plan = null;
        //di komen dulu gan dulu buat validasi bay nya...
        //plan = planningContLoadFacadeRemote.findPlanningBayplanLoadCekBayExist(planningContLoad.getVBay(), planningContLoad.getVRow(), planningContLoad.getVTier(), getNo_ppkb());

        if (plan != null) {
            loggedIn = false;
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.transaction.failed");

        } else {
            loggedIn = true;
            planningShiftDischarge.setVBay(serviceShifting.getVBay());
            planningShiftDischarge.setVRow(serviceShifting.getVRow());
            planningShiftDischarge.setVTier(serviceShifting.getVTier());

            serviceShiftingFacadeRemote.edit(serviceShifting);
            planningShiftDischargeFacadeRemote.edit(planningShiftDischarge);
            serviceShftingList = serviceShiftingFacadeRemote.findByPpkbBayPlanLoadList(noPpkb);
            serviceShifting = new ServiceShifting();
            planningShiftDischarge = new PlanningShiftDischarge();
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.transaction.succeeded");
        }
        context.addCallbackParam("loggedIn", loggedIn);
    }

    public void handleAdd(ActionEvent event) {
        this.clearSave();
    }

    public void submitNewPpkb(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        boolean loggedIn = false;
        if (serviceContTranshipment.getContNo() == null) {
            loggedIn = false;
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.save.failed");
        } else {
            loggedIn = true;
            planningContLoad.setContNo(serviceContTranshipment.getContNo());
            planningContLoad.setMlo(serviceContTranshipment.getMlo());
            planningContLoad.setContSize(serviceContTranshipment.getContSize());
            planningContLoad.setContGross(serviceContTranshipment.getContGross());
            planningContLoad.setContStatus(serviceContTranshipment.getContStatus());
            planningContLoad.setDg(serviceContTranshipment.getDangerous());
            planningContLoad.setDgLabel(serviceContTranshipment.getDgLabel());
            planningContLoad.setOverSize(serviceContTranshipment.getOverSize());
            planningContLoad.setMasterYard(serviceContTranshipment.getMasterYard());
            planningContLoad.setYSlot(serviceContTranshipment.getYSlot());
            planningContLoad.setYRow(serviceContTranshipment.getYRow());
            planningContLoad.setYTier(serviceContTranshipment.getYTier());
            planningContLoad.setSealNo(serviceContTranshipment.getSealNo());
            planningContLoad.setMasterContainerType(serviceContTranshipment.getMasterContainerType());
            planningContLoad.setMasterCommodity(serviceContTranshipment.getMasterCommodity());
            planningContLoad.setPosition("03");
            planningContLoad.setIsTranshipment("TRUE");
            planningVessell.setNoPpkb(getNoPpkb());
            planningContLoad.setPlanningVessel(planningVessell);
            planningContLoad.setCompleted("FALSE");
            planningContLoad.setUnknown("TRUE");
            planningContLoad.setBlNo(serviceContTranshipment.getBlNo());
            planningContLoadFacadeRemote.edit(planningContLoad);
            planningContLoadList = planningContLoadFacadeRemote.findPlanningBayplanLoadsByPpkb(getNoPpkb());

            serviceContTranshipmentFacadeRemote.find(getContNo());
            serviceContTranshipment.setNewPpkb(getNoPpkb());
            serviceContTranshipmentFacadeRemote.edit(serviceContTranshipment);
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
            this.clearSave();
        }
        context.addCallbackParam("loggedIn", loggedIn);
    }

    public void changeBay(Short newBay) {
        Integer startRow = 0;
        Integer totalRow = 0;
        //nyiapin data row
        rowChoices.clear();
        rowBottoms.clear();
        rowUps.clear();

        List<Object[]> loopRowBottom = masterVesselProfileFacadeRemote.findRowByBay(vesselCode, "bottom", newBay.intValue());
        if (!loopRowBottom.isEmpty()) {
            startRow = loopRowBottom.get(0)[0] == null ? 0 : (Integer) loopRowBottom.get(0)[0];
            totalRow = loopRowBottom.get(0)[1] == null ? 0 : (Integer) loopRowBottom.get(0)[1];
        }

        Integer entryRow = startRow;
        if(totalRow != null)
        for (Integer i = 0; i < totalRow; i++) {
            rowChoices.add(entryRow);
            rowBottoms.add(entryRow);
            entryRow += 1;
        }

        List<Object[]> loopRowUp = masterVesselProfileFacadeRemote.findRowByBay(vesselCode, "up", newBay.intValue());
        if (!loopRowUp.isEmpty()) {
            startRow = loopRowUp.get(0)[0] == null ? 0 : (Integer) loopRowUp.get(0)[0];
            totalRow = loopRowUp.get(0)[1] == null ? 0 : (Integer) loopRowUp.get(0)[1];
        }

        entryRow = startRow;
        if(totalRow != null)
        for (Integer i = 0; i < totalRow; i++) {
            if (!rowChoices.contains(entryRow)) {
                rowChoices.add(entryRow);
            }
            rowUps.add(entryRow);
            entryRow += 1;
        }

        planningContLoad.setVRow(rowChoices.get(0).shortValue());
        changeRow(rowChoices.get(0).shortValue());
    }

    public void changeRow(Short newRow) {
        Integer startTier = 0;
        Integer totalTier = 0;
        Integer row = newRow.intValue();
        tierChoices.clear();
        Short bay;

        if (tipe.equalsIgnoreCase("cont")) {
            bay = planningContLoad.getVBay();
        } else {
            bay = serviceShifting.getVBay();
        }
        if (rowUps.contains(row) && rowBottoms.contains(row)) {
            List<Object[]> loopTierBottom = masterProfileDetailFacadeRemote.findTierByRow(vesselCode, "bottom", bay.intValue(), row);
            if (!loopTierBottom.isEmpty()) {
                startTier = (Integer) loopTierBottom.get(0)[0];
                totalTier = (Integer) loopTierBottom.get(0)[1];
            }
            Integer entryTier = startTier;
            for (Integer j = 0; j < totalTier; j++) {
                tierChoices.add(entryTier);
                entryTier += 2;
            }
            List<Object[]> loopTierUp = masterProfileDetailFacadeRemote.findTierByRow(vesselCode, "up", bay.intValue(), row);
            if (!loopTierUp.isEmpty()) {
                startTier = (Integer) loopTierUp.get(0)[0];
                totalTier = (Integer) loopTierUp.get(0)[1];
            }
            entryTier = startTier;
            for (Integer j = 0; j < totalTier; j++) {
                if (!tierChoices.contains(entryTier)) {
                    tierChoices.add(entryTier);
                }
                entryTier += 2;
            }
        } else if (!rowUps.contains(row) && rowBottoms.contains(row)) {
            List<Object[]> loopTier = masterProfileDetailFacadeRemote.findTierByRow(vesselCode, "bottom", bay.intValue(), row);
            if (!loopTier.isEmpty()) {
                startTier = (Integer) loopTier.get(0)[0];
                totalTier = (Integer) loopTier.get(0)[1];
            }
            Integer entryTier = startTier;
            for (Integer j = 0; j < totalTier; j++) {
                tierChoices.add(entryTier);
                entryTier += 2;
            }
        } else if (rowUps.contains(row)&& !rowBottoms.contains(row)) {
            List<Object[]> loopTier = masterProfileDetailFacadeRemote.findTierByRow(vesselCode, "up", bay.intValue(), row);
            if (!loopTier.isEmpty()) {
                startTier = (Integer) loopTier.get(0)[0];
                totalTier = (Integer) loopTier.get(0)[1];
            }
            Integer entryTier = startTier;
            for (Integer j = 0; j < totalTier; j++) {
                tierChoices.add(entryTier);
                entryTier += 2;
            }
        }
//        if (tipe.equalsIgnoreCase("cont")) {
        planningContLoad.setVTier(tierChoices.get(0).shortValue());
//        } else {
//            serviceShifting.setVTier(tierChoices.get(0).shortValue());
//        }

    }

    public void onChangeBay(ValueChangeEvent event) {
        Short newBay = (Short) event.getNewValue();
        if (tipe.equalsIgnoreCase("cont")) {
            planningContLoad.setVBay(newBay);
        } else {
            serviceShifting.setVBay(newBay);
        }
        changeBay(newBay);
    }

    public void onChangeRow(ValueChangeEvent event) {
        Short newRow = (Short) event.getNewValue();
        Integer bay = Integer.parseInt(selectedBay);
        planningContLoad.setVBay(bay.shortValue());
        if (tipe.equalsIgnoreCase("cont")) {
            planningContLoad.setVRow(newRow);
        } else {
            serviceShifting.setVRow(newRow);
        }
        changeRow(newRow);
    }

    public String getVesselProfileUrl() {
        if (planningVessel == null || bays == null) {
            return "#";
        }
        return FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/../bayPlan.png?"
                + "isLoad=t&"
                + "bay=" + selectedBay + "&"
                + "ppkb=" + planningVessel[0];
    }

    public String getCyFrontViewUrl() {
        if (planningVessel == null || blocks == null) {
            return "#";
        }
        
        return FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/../cYFrontViewBayPlanMonitoring.png?"
                + "w=850&"
                + "h=400&"
                + "slot=" + selectedSlot + "&"
                + "block=" + selectedBlock + "&"
                + (selectedPod != null ? "pod=" + selectedPod + "&" : "")
                + "ppkb=" + planningVessel[0];
    }

    public void handleChangeBayLocation(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        boolean isBayLocationValid = false;

        try {
            Integer bay = Integer.parseInt(selectedBay);
            Object[] _planningContLoad = planningContLoadFacadeRemote.findPlanningBayplanLoadCekBayExist(bay.shortValue(), planningContLoad.getVRow(), planningContLoad.getVTier(), noPpkb);
            
            if (_planningContLoad == null) {
                planningContLoad.setVBay(bay.shortValue());
                planningContLoadFacadeRemote.edit(planningContLoad);
                isBayLocationValid = true;
                reloadBayPlanParameter();
            } else {
                FacesContext facesContext = FacesContext.getCurrentInstance();
                FacesHelper.addFacesTextMessage(facesContext, FacesMessage.SEVERITY_ERROR, "Error", String.format(FacesHelper.getLocaleMessage("application.validation.bay_location.failed", facesContext), bay +" "+ planningContLoad.getVRow() +" "+ planningContLoad.getVTier(), (String) _planningContLoad[4]), "bayLocationSelectionForm:tierChoices");
            }
        } catch (RuntimeException re) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception on handleChangeBayLocation", re);
        }

        context.addCallbackParam("isBayLocationValid", isBayLocationValid);
    }

    public void handleDeleteContainerOnBay(ActionEvent event) {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            Integer id = Integer.parseInt(context.getExternalContext().getRequestParameterMap().get("index"));
            planningContLoad = plannedContainersOnBay.get(id);

            planningContLoad.setVBay(null);
            planningContLoad.setVRow(null);
            planningContLoad.setVTier(null);

            planningContLoadFacadeRemote.edit(planningContLoad);

            reloadBayPlanParameter();
        } catch (RuntimeException re) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception on handleDeleteContainerOnBay", re);
        }
    }

    public void handleSelectContLoad(ActionEvent event) {
        tipe = "cont";
        Integer bay = Integer.parseInt(selectedBay);
        planningContLoad.setVBay(bay.shortValue());
        changeBay(bay.shortValue());
    }

    public void handleChangeBlock(ValueChangeEvent event){
        selectedBlock = event.getNewValue().toString();
        slots = masterYardFacadeRemote.findNotPlannedSlotLocationByNoPpkbAndBlock(noPpkb, selectedBlock);
        notPlannedContainersOnYard.clear();
        selectedPod = null;

        if (!slots.isEmpty()) {
            selectedSlot = slots.get(0);
            loadNotPlannedContainers();
        }
    }

    public void handleChangeBay(ValueChangeEvent event){
        selectedBay = event.getNewValue().toString();
        Integer bay = Integer.parseInt(selectedBay);
        plannedContainersOnBay = planningContLoadFacadeRemote.findPlannedContainerByBayLocation(noPpkb, bay.shortValue());
    }

    public void handleChangeSlot(ValueChangeEvent event){
        selectedSlot = (Integer) event.getNewValue();
        selectedPod = null;
        loadNotPlannedContainers();
    }

    public void handleChangePod(ValueChangeEvent event){
        selectedPod = (String) event.getNewValue();
        loadNotPlannedContainers();
    }

    public void handleClickNextSlotButton(ActionEvent event){
        if (selectedSlot != slots.get(slots.size() - 1)){
            int index = slots.indexOf(selectedSlot);
            if (index < slots.size() - 1) {
                selectedSlot = slots.get(index + 1);
                loadNotPlannedContainers();
            }
        }
    }

    public void handleClickPrevSlotButton(ActionEvent event){
        if (selectedSlot != slots.get(0)){
            int index = slots.indexOf(selectedSlot);
            if (index > 0) {
                selectedSlot = slots.get(index - 1);
                loadNotPlannedContainers();
            }
        }
    }

    public void handleClickNextBayButton(ActionEvent event){
        if (!selectedBay.equals(bays.get(bays.size() - 1))){
            int index = bays.indexOf(selectedBay);
            if (index < bays.size() - 1) {
                selectedBay = bays.get(index + 1);
                Integer bay = Integer.parseInt(selectedBay);
                plannedContainersOnBay = planningContLoadFacadeRemote.findPlannedContainerByBayLocation(noPpkb, bay.shortValue());
            }
        }
    }

    public void handleClickPrevBayButton(ActionEvent event){
        if (!selectedBay.equals(bays.get(0))) {
            int index = bays.indexOf(selectedBay);
            if (index > 0) {
                selectedBay = bays.get(index - 1);
                Integer bay = Integer.parseInt(selectedBay);
                plannedContainersOnBay = planningContLoadFacadeRemote.findPlannedContainerByBayLocation(noPpkb, bay.shortValue());
            }
        }
    }

    private List<Object[]> createAvailableBayLocationList() {
        List<Object[]> result = new ArrayList();

        try {
            boolean isUpActive = false, isBottomActive = false;
            Integer bay = Integer.parseInt(selectedBay);

            List<Object[]> notAvailableBayLocation = masterVesselProfileFacadeRemote.findNotAvailableBaysLocationByNoPpkbAndBay(noPpkb, bay.shortValue());
            List<Object[]> bayDetail = masterProfileDetailFacadeRemote.findBayDetailByPpkb(noPpkb, bay);
            List<Object[]> bayIdentity = masterVesselProfileFacadeRemote.findBayIdentityByPpkb(noPpkb, bay);

            if (!bayIdentity.isEmpty() && !bayDetail.isEmpty()){
                for (Object[] data: bayIdentity){
                    if (data[0].toString().equals("up")){
                        isUpActive = true;
                    } else if (data[0].toString().equals("bottom")){
                        isBottomActive = true;
                    }
                }

                for (Object[] d: bayDetail){
                    if ((d[3].toString().equals("up") && isUpActive) || (d[3].toString().equals("bottom") && isBottomActive))
                        for (int i = (Integer)d[2];i <= ((((Integer)d[1] - 1) * 2) + (Integer)d[2]); i+=2) {
                            Object[] row = new Object[]{((Integer) d[0]).shortValue(), ((Integer) i).shortValue()};
                            if (!notAvailableBayLocation.contains(row))
                                result.add(row);
                        }
                }
            }
        } catch (Exception re) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception on createAvailableBayLocationList", re);
        }
        
        return result;
    }

    public void handleChangeZoomSetting(ValueChangeEvent event) {
        enableZoom = (Boolean) event.getNewValue();
    }

    /**
     * @return the planningVessel
     */
    public Object[] getPlanningVessel() {
        return planningVessel;
    }

    /**
     * @param planningVessel the planningVessel to set
     */
    public void setPlanningVessel(Object[] planningVessel) {
        this.planningVessel = planningVessel;
    }

    /**
     * @return the serviceReceiving
     */
    public Object[] getServiceReceiving() {
        return serviceReceiving;
    }

    public List<MasterYard> getMasterYards() {
        return masterYardFacadeRemote.findAll();
    }

    /**
     * @param serviceReceiving the serviceReceiving to set
     */
    public void setServiceReceiving(Object[] serviceReceiving) {
        this.serviceReceiving = serviceReceiving;
    }

    /**
     * @return the serviceReceivingListObject
     */
    public List<Object[]> getServiceReceivingListObject() {
        return serviceReceivingListObject;
    }

    /**
     * @param serviceReceivingListObject the serviceReceivingListObject to set
     */
    public void setServiceReceivingListObject(List<Object[]> serviceReceivingListObject) {
        this.serviceReceivingListObject = serviceReceivingListObject;
    }

    /**
     * @return the serviceReceiving2
     */
    public ServiceReceiving getServiceReceiving2() {
        return serviceReceiving2;
    }

    /**
     * @param serviceReceiving2 the serviceReceiving2 to set
     */
    public void setServiceReceiving2(ServiceReceiving serviceReceiving2) {
        this.serviceReceiving2 = serviceReceiving2;
    }

    /**
     * @return the planningContLoad
     */
    public PlanningContLoad getPlanningContLoad() {
        return planningContLoad;
    }

    /**
     * @param planningContLoad the planningContLoad to set
     */
    public void setPlanningContLoad(PlanningContLoad planningContLoad) {
        this.planningContLoad = planningContLoad;
    }

    /**
     * @return the planningVessell
     */
    public PlanningVessel getPlanningVessell() {
        return planningVessell;
    }

    /**
     * @param planningVessell the planningVessell to set
     */
    public void setPlanningVessell(PlanningVessel planningVessell) {
        this.planningVessell = planningVessell;
    }

    /**
     * @return the no_ppkb
     */
    public String getNoPpkb() {
        return noPpkb;
    }

    /**
     * @param no_ppkb the no_ppkb to set
     */
    public void setNoPpkb(String no_ppkb) {
        this.noPpkb = no_ppkb;
    }

    /**
     * @return the masterCommodity
     */
    public MasterCommodity getMasterCommodity() {
        return masterCommodity;
    }

    /**
     * @param masterCommodity the masterCommodity to set
     */
    public void setMasterCommodity(MasterCommodity masterCommodity) {
        this.masterCommodity = masterCommodity;
    }

    /**
     * @return the masterYard
     */
    public MasterYard getMasterYard() {
        return masterYard;
    }

    /**
     * @param masterYard the masterYard to set
     */
    public void setMasterYard(MasterYard masterYard) {
        this.masterYard = masterYard;
    }

    /**
     * @return the masterContainerType
     */
    public MasterContainerType getMasterContainerType() {
        return masterContainerType;
    }

    /**
     * @param masterContainerType the masterContainerType to set
     */
    public void setMasterContainerType(MasterContainerType masterContainerType) {
        this.masterContainerType = masterContainerType;
    }

    /**
     * @return the planningContLoadList
     */
    public List<Object[]> getPlanningContLoadList() {
        return planningContLoadList;
    }

    /**
     * @param planningContLoadList the planningContLoadList to set
     */
    public void setPlanningContLoadList(List<Object[]> planningContLoadList) {
        this.planningContLoadList = planningContLoadList;
    }

    /**
     * @return the planningVessels
     */
    public List<Object[]> getPlanningVessels() {
        return planningVessels;
    }

    /**
     * @param planningVessels the planningVessels to set
     */
    public void setPlanningVessels(List<Object[]> planningVessels) {
        this.planningVessels = planningVessels;
    }

    /**
     * @return the masterYard2
     */
    public Object[] getMasterYard2() {
        return masterYard2;
    }

    /**
     * @param masterYard2 the masterYard2 to set
     */
    public void setMasterYard2(Object[] masterYard2) {
        this.masterYard2 = masterYard2;
    }

    /**
     * @return the serviceContTranshipment
     */
    public ServiceContTranshipment getServiceContTranshipment() {
        return serviceContTranshipment;
    }

    /**
     * @param serviceContTranshipment the serviceContTranshipment to set
     */
    public void setServiceContTranshipment(ServiceContTranshipment serviceContTranshipment) {
        this.serviceContTranshipment = serviceContTranshipment;
    }

    /**
     * @return the cont_no
     */
    public Integer getContNo() {
        return contNo;
    }

    /**
     * @param cont_no the cont_no to set
     */
    public void setContNo(Integer cont_no) {
        this.contNo = cont_no;
    }

    /**
     * @return the planningReceivingSearchId
     */
    public List<Object[]> getPlanningReceivingSearchId() {
        return planningReceivingSearchId;
    }

    /**
     * @param planningReceivingSearchId the planningReceivingSearchId to set
     */
    public void setPlanningReceivingSearchId(List<Object[]> planningReceivingSearchId) {
        this.planningReceivingSearchId = planningReceivingSearchId;
    }

    /**
     * @return the PlanningReceivingList
     */
    public List<Object[]> getPlanningReceivingList() {
        return planningReceivingList;
    }

    /**
     * @param PlanningReceivingList the PlanningReceivingList to set
     */
    public void setPlanningReceivingList(List<Object[]> planningReceivingList) {
        this.setPlanningReceivingList(planningReceivingList);
    }

    /**
     * @return the disable
     */
    public Boolean getDisable() {
        return disable;
    }

    /**
     * @param disable the disable to set
     */
    public void setDisable(Boolean disable) {
        this.disable = disable;
    }

    /**
     * @return the planningLoadCekListCompleted
     */
    public List<Object[]> getPlanningLoadCekListCompleted() {
        return planningLoadCekListCompleted;
    }

    /**
     * @param planningLoadCekListCompleted the planningLoadCekListCompleted to set
     */
    public void setPlanningLoadCekListCompleted(List<Object[]> planningLoadCekListCompleted) {
        this.planningLoadCekListCompleted = planningLoadCekListCompleted;
    }

    public List<String> getBays() {
        return bays;
    }

    /**
     * @return the bayChoices
     */
    public List<Integer> getBayChoices() {
        return bayChoices;
    }

    /**
     * @param bayChoices the bayChoices to set
     */
    public void setBayChoices(List<Integer> bayChoices) {
        this.bayChoices = bayChoices;
    }

    /**
     * @return the rowChoices
     */
    public List<Integer> getRowChoices() {
        return rowChoices;
    }

    /**
     * @param rowChoices the rowChoices to set
     */
    public void setRowChoices(List<Integer> rowChoices) {
        this.rowChoices = rowChoices;
    }

    /**
     * @return the tierChoices
     */
    public List<Integer> getTierChoices() {
        return tierChoices;
    }

    /**
     * @param tierChoices the tierChoices to set
     */
    public void setTierChoices(List<Integer> tierChoices) {
        this.tierChoices = tierChoices;
    }

    /**
     * @return the vesselCode
     */
    public String getVesselCode() {
        return vesselCode;
    }

    /**
     * @param vesselCode the vesselCode to set
     */
    public void setVesselCode(String vesselCode) {
        this.vesselCode = vesselCode;
    }

    public Integer getIdtrans() {
        return idtrans;
    }

    public void setIdtrans(Integer idtrans) {
        this.idtrans = idtrans;
    }

    public List<Integer> getRowBottoms() {
        return rowBottoms;
    }

    public void setRowBottoms(List<Integer> rowBottoms) {
        this.rowBottoms = rowBottoms;
    }

    public List<Integer> getRowUps() {
        return rowUps;
    }

    public void setRowUps(List<Integer> rowUps) {
        this.rowUps = rowUps;
    }

    public Object[][] getServiceReceivingArray() {
        return serviceReceivingArray;
    }

    public void setServiceReceivingArray(Object[][] serviceReceivingArray) {
        this.serviceReceivingArray = serviceReceivingArray;
    }

    public Object[][] getServiceTranshipmentArray() {
        return serviceTranshipmentArray;
    }

    public void setServiceTranshipmentArray(Object[][] serviceTranshipmentArray) {
        this.serviceTranshipmentArray = serviceTranshipmentArray;
    }

    public MasterGrossClass getMasterGrossClass() {
        return masterGrossClass;
    }

    public void setMasterGrossClass(MasterGrossClass masterGrossClass) {
        this.masterGrossClass = masterGrossClass;
    }

    public PlanningReceivingAllocation getPlanningReceivingAllocation() {
        return planningReceivingAllocation;
    }

    public void setPlanningReceivingAllocation(PlanningReceivingAllocation planningReceivingAllocation) {
        this.planningReceivingAllocation = planningReceivingAllocation;
    }

    public List<Object[]> getUncontainerizedList() {
        return uncontainerizedList;
    }

    public void setUncontainerizedList(List<Object[]> uncontainerizedList) {
        this.uncontainerizedList = uncontainerizedList;
    }

    public List<Object[]> getUncontainerizedSelect() {
        return uncontainerizedSelect;
    }

    public void setUncontainerizedSelect(List<Object[]> uncontainerizedSelect) {
        this.uncontainerizedSelect = uncontainerizedSelect;
    }

    public UncontainerizedService getUncontainerizedService() {
        return uncontainerizedService;
    }

    public void setUncontainerizedService(UncontainerizedService uncontainerizedService) {
        this.uncontainerizedService = uncontainerizedService;
    }

    public Object[][] getBayplanUncontainerized() {
        return bayplanUncontainerized;
    }

    public void setBayplanUncontainerized(Object[][] bayplanUncontainerized) {
        this.bayplanUncontainerized = bayplanUncontainerized;
    }

    public ServiceShifting getServiceShifting() {
        return serviceShifting;
    }

    public void setServiceShifting(ServiceShifting serviceShifting) {
        this.serviceShifting = serviceShifting;
    }

    public List<Object[]> getServiceShftingList() {
        return serviceShftingList;
    }

    public void setServiceShftingList(List<Object[]> serviceShftingList) {
        this.serviceShftingList = serviceShftingList;
    }

    public String getTipe() {
        return tipe;
    }

    public void setTipe(String tipe) {
        this.tipe = tipe;
    }

    public List<PlanningContLoad> getNotPlannedContainersOnYard() {
        return notPlannedContainersOnYard;
    }

    public List<PlanningContLoad> getPlannedContainersOnBay() {
        return plannedContainersOnBay;
    }
    
    public List<String> getBlocks() {
        return blocks;
    }

    public List<Integer> getSlots() {
        return slots;
    }

    public String getSelectedBay() {
        return selectedBay;
    }

    public void setSelectedBay(String selectedBay) {
        this.selectedBay = selectedBay;
    }

    public String getSelectedBlock() {
        return selectedBlock;
    }

    public void setSelectedBlock(String selectedBlock) {
        this.selectedBlock = selectedBlock;
    }

    public Integer getSelectedSlot() {
        return selectedSlot;
    }

    public void setSelectedSlot(Integer selectedSlot) {
        this.selectedSlot = selectedSlot;
    }

    public Object[] getSelectedBayLocation() {
        return selectedBayLocation;
    }

    public void setSelectedBayLocation(Object[] selectedBayLocation) {
        this.selectedBayLocation = selectedBayLocation;
    }

    public Boolean getEnableZoom() {
        return enableZoom;
    }

    public void setEnableZoom(Boolean enableZoom) {
        this.enableZoom = enableZoom;
    }

    public String getSelectedPod() {
        return selectedPod;
    }

    public void setSelectedPod(String selectedPod) {
        this.selectedPod = selectedPod;
    }

    public List<String> getPods() {
        return pods;
    }
}
