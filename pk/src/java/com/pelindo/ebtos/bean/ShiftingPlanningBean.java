/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.bean;

import com.qtasnim.jsf.FacesHelper;
import com.pelindo.ebtos.ejb.facade.remote.BaplieDischargeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterCommodityFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterContainerTypeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterCustomerFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterPortFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterProfileDetailFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterSettingAppFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterYardCoordinatFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterVesselProfileFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningShiftDischargeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningVesselFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceShiftingFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.UncontainerizedServiceFacadeRemote;
import com.pelindo.ebtos.exception.ReceivingAllocationCont40ftIsNotEnoughException;
import com.pelindo.ebtos.exception.ReceivingAllocationIsNotEnoughException;
import com.pelindo.ebtos.model.db.BaplieDischarge;
import com.pelindo.ebtos.model.db.PlanningShiftDischarge;
import com.pelindo.ebtos.model.db.PlanningVessel;
import com.pelindo.ebtos.model.db.ServiceShifting;
import com.pelindo.ebtos.model.db.UncontainerizedService;
import com.pelindo.ebtos.model.db.master.MasterActivity;
import com.pelindo.ebtos.model.db.master.MasterCommodity;
import com.pelindo.ebtos.model.db.master.MasterContDamage;
import com.pelindo.ebtos.model.db.master.MasterContainerType;
import com.pelindo.ebtos.model.db.master.MasterCustomer;
import com.pelindo.ebtos.model.db.master.MasterPort;
import com.pelindo.ebtos.util.GrossConverter;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import org.primefaces.context.RequestContext;

/**
 *
 * @author dycode-java
 */
@ManagedBean(name = "shiftingPlanningBean")
@ViewScoped
public class ShiftingPlanningBean implements Serializable {
    @EJB
    private BaplieDischargeFacadeRemote baplieDischargeFacadeRemote;
    @EJB
    private PlanningShiftDischargeFacadeRemote planningShiftDischargeFacadeRemote;
    @EJB
    private PlanningVesselFacadeRemote planningVesselFacadeRemote;
    @EJB
    private MasterContainerTypeFacadeRemote masterContainerTypeFacadeRemote;
    @EJB
    private MasterVesselProfileFacadeRemote masterVesselProfileFacade;
    @EJB
    private MasterProfileDetailFacadeRemote masterProfileDetailFacadeRemote;
    @EJB
    private UncontainerizedServiceFacadeRemote uncontainerizedServiceFacadeRemote;
    @EJB
    private MasterYardCoordinatFacadeRemote masterYardCoordinatFacadeRemote;
    @EJB
    private MasterCustomerFacadeRemote masterCustomerFacadeRemote;
    @EJB
    private ServiceShiftingFacadeRemote serviceShiftingFacade;
    @EJB MasterCommodityFacadeRemote masterCommodityFacadeRemote;
    @EJB MasterPortFacadeRemote masterPortFacadeRemote;

    private List<Object[]> planningVessels;
    //variable
    private List<Object[]> baplieDischarges;
    private List<Object[]> loadPlanningAllocationsInfo;
    private List<Object[]> planningShiftDischarges;
    private Object[] vessel;
    private PlanningVessel planningVessel;
    private BaplieDischarge baplieDischarge;
    private PlanningShiftDischarge planningShiftDischarge;
    private ServiceShifting serviceShifting;
    private Object[] shiftDischarge;
    private String noPPKB;
    private String opsi;
    private Object[][] containers;
    private Object[][] containerRemoves;
    private List<Object[]> uncontaineraizedList;
    private boolean isEdit;
    private UncontainerizedService uncontainerizedService;
    private List<Object[]> ucList;
    private Object[][] uncontainerizedArray;
    private String vesselCode;
    private List<Integer> bayChoices;
    private List<Integer> rowChoices;
    private List<Integer> tierChoices;
    private List<Integer> rowUps;
    private List<Integer> rowBottoms;
    private List<Object[]> masterCustomers;
    String commodityName, port1, port2;

    /** Creates a new instance of ShiftingPlanningBean */
    public ShiftingPlanningBean() {
        bayChoices = new ArrayList<Integer>();
        rowChoices = new ArrayList<Integer>();
        tierChoices = new ArrayList<Integer>();
        rowUps = new ArrayList<Integer>();
        rowBottoms = new ArrayList<Integer>();
    }

    @PostConstruct
    private void construct() {
        
        planningShiftDischarge = new PlanningShiftDischarge();
        noPPKB = null;
        vesselCode = null;
        baplieDischarge = new BaplieDischarge();
        uncontainerizedService = new UncontainerizedService();
        uncontainerizedService.setShiftTo("NOLANDED");
        uncontainerizedArray = new Object[0][0];
    }

    public void handleShowPlanningVessels(ActionEvent event) {
        planningVessels = planningVesselFacadeRemote.findPlanningVesselsByFinishedNotServed();
    }

    public void handleSelectNoPpkb(ActionEvent event) {
        noPPKB = (String) event.getComponent().getAttributes().get("noPpkb");
        vessel = planningVesselFacadeRemote.findPlanningVesselByPpkb(noPPKB);
        planningVessel = planningVesselFacadeRemote.find(noPPKB);

        vesselCode = planningVessel.getVesselCode();
        baplieDischarges = baplieDischargeFacadeRemote.findBaplieDischargesByPpkbShifting(noPPKB);
        planningShiftDischarges = planningShiftDischargeFacadeRemote.findPlanningShiftDischargesByPPKB(noPPKB);

        uncontaineraizedList = uncontainerizedServiceFacadeRemote.findByPpkbAndBl(noPPKB);
        ucList = uncontainerizedServiceFacadeRemote.findByPpkbAndBlList(noPPKB);
        //ngurusin bay
        bayChoices.clear();
        bayChoices = masterVesselProfileFacade.findBaysByVessel(vesselCode);
    }

    public void handleButtonAdd(ActionEvent event) {
        MasterPort nextPort = planningVessel.getPreserviceVessel().getMasterPort();
        MasterPort lastPort = planningVessel.getPreserviceVessel().getMasterPort1();

        baplieDischarge.setContNo(null);
        baplieDischarge.setId(null);
        baplieDischarge.setMasterPort(lastPort);
        baplieDischarge.setMasterPort1(nextPort);
        baplieDischarge.setPortOfDelivery(nextPort);
        baplieDischarge.setDataComplete("FALSE");

        //untuk mengkondisikan pemilihan bay
        opsi = "add";

        if (getBayChoices().isEmpty()) {
            getBayChoices().add(0);
        }
        if (getTierChoices().isEmpty()) {
            getTierChoices().add(0);
        }
        if (getRowChoices().isEmpty()) {
            getRowChoices().add(0);
        }

        baplieDischarge.setVBay(Short.parseShort(getBayChoices().get(0).toString()));
        changeBay(baplieDischarge.getVBay());

        if (baplieDischarge.getContSize() == null) {
            MasterContainerType mct = masterContainerTypeFacadeRemote.find(1);
            baplieDischarge.setMasterContainerType(mct);
            baplieDischarge.setContSize(mct.getFeetMark());
            baplieDischarge.setContStatus("FCL");
        }
    }

    public void submit() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        RequestContext requestContext = RequestContext.getCurrentInstance();
        List<Object[]> notSavedObjects = new ArrayList();

        try {
            String contListAsString = "";
            List<Object[]> toCyShiftings = new ArrayList<Object[]>();
            List<Object[]> nonToCyShiftings = new ArrayList<Object[]>();
            loadPlanningAllocationsInfo = null;

            if (containers.length == 0) {
                FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "application.validation.selection_is_empty");
                requestContext.addCallbackParam("isConstraintNotEnough", false);
                return;
            } else {
                for (int i = 0; i < containers.length; i++) {
                    if (!((String) containers[i][13]).equals("TOCY")) {
                        nonToCyShiftings.add(containers[i]);
                    } else {
                        toCyShiftings.add(containers[i]);
                        contListAsString = contListAsString + "'" + (String) containers[i][1] + "',";
                    }
                }

                if (!toCyShiftings.isEmpty()) {
                    contListAsString = contListAsString.substring(0, contListAsString.length() - 1);
                    loadPlanningAllocationsInfo = masterYardCoordinatFacadeRemote.findLoadPlanningAllocations(planningVessel.getNoPpkb(), planningVessel.getNoPpkb(), contListAsString);
                }
                String stat = planningVessel.getStatus();
                planningShiftDischargeFacadeRemote.allocateShiftingToReceivingAllocation(planningVessel, contListAsString, nonToCyShiftings, toCyShiftings, loadPlanningAllocationsInfo, notSavedObjects);
                System.out.println("Status : " + stat);
                if (stat.equalsIgnoreCase("servicing")) {
                    for (int i = 0; i < containers.length; i++) {
                        baplieDischarge = baplieDischargeFacadeRemote.find(containers[i][14]);
                        serviceShifting = new ServiceShifting();
                        serviceShifting.setMasterActivity(null);
                        serviceShifting.setMasterCommodity(baplieDischarge.getMasterCommodity());
                        serviceShifting.setMasterContainerType(baplieDischarge.getMasterContainerType());
                        serviceShifting.setMlo(baplieDischarge.getMlo());
                        serviceShifting.setServiceVessel(baplieDischarge.getPlanningVessel().getServiceVessel());
                        serviceShifting.setMasterContDamage(null);
                        serviceShifting.setCrane("L");
                        serviceShifting.setContNo(baplieDischarge.getContNo());
                        serviceShifting.setContSize(baplieDischarge.getContSize());
                        serviceShifting.setContStatus(baplieDischarge.getContStatus());
                        serviceShifting.setContGross(baplieDischarge.getContGross());
                        serviceShifting.setTwentyOneFeet(baplieDischarge.getTwentyOneFeet());
                        serviceShifting.setSealNo(baplieDischarge.getSealNo());
                        serviceShifting.setOverSize(baplieDischarge.getOverSize());
                        serviceShifting.setDgLabel(baplieDischarge.getDgLabel());
                        serviceShifting.setLoadPort(baplieDischarge.getMasterPort().getPortCode());
                        serviceShifting.setDischPort(baplieDischarge.getMasterPort1().getPortCode());
                        serviceShifting.setVBay(baplieDischarge.getVBay());
                        serviceShifting.setVRow(baplieDischarge.getVRow());
                        serviceShifting.setVTier(baplieDischarge.getVTier());
                        serviceShifting.setShiftTo((containers[i][13]).toString());
                        serviceShifting.setIsReshipping("FALSE");
                        serviceShifting.setOperation("DISCHARGE");
                        serviceShifting.setPosition(ServiceShifting.LOCATION_SHIFTING);
                        serviceShifting.setIsPlanning("TRUE");
                        serviceShifting.setBlNo(baplieDischarge.getBlNo());
                        serviceShifting.setIsLanded("FALSE");
                        serviceShiftingFacade.edit(serviceShifting);
                    }
                }
                baplieDischarges = baplieDischargeFacadeRemote.findBaplieDischargesByPpkbShifting(noPPKB);
                planningShiftDischarges = planningShiftDischargeFacadeRemote.findPlanningShiftDischargesByPPKB(noPPKB);
            }
        } catch (ReceivingAllocationIsNotEnoughException ex) {
            Logger.getLogger(ShiftingPlanningBean.class.getName()).log(Level.SEVERE, null, ex);
            requestContext.addCallbackParam("isConstraintNotEnough", true);
            return;
        } catch (ReceivingAllocationCont40ftIsNotEnoughException ex) {
            Logger.getLogger(ShiftingPlanningBean.class.getName()).log(Level.SEVERE, null, ex);
            FacesHelper.addFacesTextMessage(facesContext, FacesMessage.SEVERITY_INFO, "Info", String.format(FacesHelper.getLocaleMessage("application.validation.some_data_fail_to_saved", facesContext), notSavedObjects.size(), containers.length), null);
        } catch (RuntimeException re) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, re);
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_ERROR, "application.save.failed");
        }

        System.out.println("not saved: " + notSavedObjects.size());
        requestContext.addCallbackParam("isConstraintNotEnough", false);
    }

    public void handleEditDeleteButton(ActionEvent event) {
        int id = ((Number) event.getComponent().getAttributes().get("idCont")).intValue();
        planningShiftDischarge = planningShiftDischargeFacadeRemote.find(id);
        shiftDischarge = planningShiftDischargeFacadeRemote.findPlanningShiftDischargesById(id);
        opsi = "delete";
    }

    public void saveEdit() {
        boolean loggedIn = false;
        if (planningShiftDischarge.getShiftTo() == null) {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.save.failed");
        } else {
            loggedIn = true;
            planningShiftDischargeFacadeRemote.edit(planningShiftDischarge);
            if (planningVessel.getStatus().equalsIgnoreCase("servicing")) {
                serviceShifting = serviceShiftingFacade.findByNoPpkbAndContNo(planningShiftDischarge.getPlanningVessel().getNoPpkb(), planningShiftDischarge.getContNo());

                serviceShifting.setShiftTo(planningShiftDischarge.getShiftTo());
                serviceShiftingFacade.edit(serviceShifting);
            }
            planningShiftDischarge = new PlanningShiftDischarge();
            planningShiftDischarge.setPlanningVessel(new PlanningVessel());
            planningShiftDischarge.setMasterCommodity(new MasterCommodity());
            planningShiftDischarge.setMasterContainerType(new MasterContainerType());
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
        }
        
        baplieDischarges = baplieDischargeFacadeRemote.findBaplieDischargesByPpkbShifting(noPPKB);
        planningShiftDischarges = planningShiftDischargeFacadeRemote.findPlanningShiftDischargesByPPKB(noPPKB);
        RequestContext.getCurrentInstance().addCallbackParam("loggedIn", loggedIn);
    }

    public void delete(ActionEvent event) {
        if (opsi.equalsIgnoreCase("delete")) {
            if (planningShiftDischarge.getShiftTo().equals("TOCY")) {
                masterYardCoordinatFacadeRemote.revertContNoToBookingByPpkb(planningShiftDischarge.getPlanningVessel().getNoPpkb(), planningShiftDischarge.getContNo());
            }
            
            planningShiftDischargeFacadeRemote.remove(planningShiftDischarge);
        } else {
            for (int i = 0; i < containerRemoves.length; i++) {
                planningShiftDischarge = planningShiftDischargeFacadeRemote.find(((Number) containerRemoves[i][14]).intValue());

                if (planningShiftDischarge.getShiftTo().equals("TOCY")) {
                    masterYardCoordinatFacadeRemote.revertContNoToBookingByPpkb(planningShiftDischarge.getPlanningVessel().getNoPpkb(), planningShiftDischarge.getContNo());
                }
                
                planningShiftDischargeFacadeRemote.remove(planningShiftDischarge);
            }
        }
        serviceShifting = serviceShiftingFacade.findByNoPpkbAndContNo(planningShiftDischarge.getPlanningVessel().getNoPpkb(), planningShiftDischarge.getContNo());
        if(serviceShifting != null)
            serviceShiftingFacade.remove(serviceShifting);
        
        FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.delete.succeeded");
        baplieDischarges = baplieDischargeFacadeRemote.findBaplieDischargesByPpkbShifting(noPPKB);
        planningShiftDischarges = planningShiftDischargeFacadeRemote.findPlanningShiftDischargesByPPKB(noPPKB);
    }

    public void saveEditBaplie(ActionEvent event) {
        boolean loggedIn = false;
        List<Object[]> sameContainers = baplieDischargeFacadeRemote.findTheSameContainer(baplieDischarge.getContNo());

        int gross = baplieDischarge.getContGross();
        int size = baplieDischarge.getContSize();
        String grs = GrossConverter.convert((short) size, gross);
        if (baplieDischarge.getContNo() == null || baplieDischarge.getContGross() == null) {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "info", "application.transaction.failed");
        } else if (baplieDischarge.getMasterContainerType().getContType() == 3) {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "info", "application.transaction.failed");
        } else if (opsi.equals("add") && !sameContainers.isEmpty()) {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "info", "application.transaction.failed");
        } else if (opsi.equals("add") && masterYardCoordinatFacadeRemote.findDuplicate(baplieDischarge.getContNo())) {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "info", "application.transaction.failed");
        } else if (grs.equalsIgnoreCase("XX")) {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "info", "application.transaction.failed");
        } else {
            if (baplieDischarge.getPlanningVessel() == null) {
                baplieDischarge.setPlanningVessel(planningVessel);
            }

            boolean dataComplete = false;
            baplieDischarge.setGrossClass(grs);

//            if (baplieDischarge.getGrossClass().equalsIgnoreCase("XX")) {
//                dataComplete = false;
//            }
            System.out.println("getTipePelayaran : " + planningVessel.getTipePelayaran());
            baplieDischarge.setTradeType(planningVessel.getTipePelayaran());
            if (dataComplete) {
                baplieDischarge.setDataComplete("TRUE");
            } else {
                baplieDischarge.setDataComplete("FALSE");
            }
            baplieDischarge.setIsNewShift("TRUE");
            
            //Tarakan
            if ("MTY".equals(baplieDischarge.getContStatus())) {
                MasterCommodity emptyCommodity = masterCommodityFacadeRemote.find("000");
                baplieDischarge.setMasterCommodity(emptyCommodity);
            }
            baplieDischargeFacadeRemote.edit(baplieDischarge);

            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "info", "application.save.succeeded");
            baplieDischarges = baplieDischargeFacadeRemote.findBaplieDischargesByPpkbShifting(noPPKB);

            if (opsi.equals("add")) {
                baplieDischarge = new BaplieDischarge();
                baplieDischarge.setMasterCommodity(new MasterCommodity());
                baplieDischarge.setMasterContainerType(new MasterContainerType());
                baplieDischarge.setMasterPort(new MasterPort());
                baplieDischarge.setMasterPort1(new MasterPort());
                baplieDischarge.setPortOfDelivery(new MasterPort());
                handleButtonAdd(event);
            }
        }
        RequestContext.getCurrentInstance().addCallbackParam("loggedIn", loggedIn);
    }

    public void handleEditDeleteBaplieButton(ActionEvent event) {
        int id = ((Number) event.getComponent().getAttributes().get("idBaplie")).intValue();
        baplieDischarge = baplieDischargeFacadeRemote.find(id);
        opsi = "delete";
    }

    public void deleteBaplie(ActionEvent event) {
//        if (opsi.equalsIgnoreCase("delete")) {
//            if (planningShiftDischarge.getShiftTo().equals("TOCY")) {
//                masterYardCoordinatFacadeRemote.revertContNoToBookingByPpkb(planningShiftDischarge.getPlanningVessel().getNoPpkb(), planningShiftDischarge.getContNo());
//            }
//
//            planningShiftDischargeFacadeRemote.remove(planningShiftDischarge);
//        } else {
//            for (int i = 0; i < containers.length; i++) {
//                baplieDischarge = baplieDischargeFacadeRemote.find((Integer) containers[i][14]);
//
////                if (planningShiftDischarge.getShiftTo().equals("TOCY")) {
////                    masterYardCoordinatFacadeRemote.revertContNoToBookingByPpkb(planningShiftDischarge.getPlanningVessel().getNoPpkb(), planningShiftDischarge.getContNo());
////                }
//
//                baplieDischargeFacadeRemote.remove(baplieDischarge);
//            }
//        }
        baplieDischargeFacadeRemote.remove(baplieDischarge);

        FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.delete.succeeded");
        baplieDischarges = baplieDischargeFacadeRemote.findBaplieDischargesByPpkbShifting(noPPKB);
        planningShiftDischarges = planningShiftDischargeFacadeRemote.findPlanningShiftDischargesByPPKB(noPPKB);
    }

    public void changeBay(Short newBay) {
        Integer startRow = 0;
        Integer totalRow = 0;
        //nyiapin data row
        rowChoices.clear();
        getRowBottoms().clear();
        getRowUps().clear();
        List<Object[]> loopRowBottom = masterVesselProfileFacade.findRowByBay(vesselCode, "bottom", newBay.intValue());
        if (!loopRowBottom.isEmpty()) {
            startRow = (Integer) loopRowBottom.get(0)[0];
            totalRow = (Integer) loopRowBottom.get(0)[1];
        }
        Integer entryRow = startRow;
        if(totalRow != null)
        for (Integer i = 0; i < totalRow; i++) {
            rowChoices.add(entryRow);
            getRowBottoms().add(entryRow);
            entryRow += 1;
        }
        List<Object[]> loopRowUp = masterVesselProfileFacade.findRowByBay(vesselCode, "up", newBay.intValue());
        if (!loopRowUp.isEmpty()) {
            startRow = (Integer) loopRowUp.get(0)[0];
            totalRow = (Integer) loopRowUp.get(0)[1];
        }
        entryRow = startRow;
        if(totalRow != null)
        for (Integer i = 0; i < totalRow; i++) {
            if (!rowChoices.contains(entryRow)) {
                rowChoices.add(entryRow);
            }
            getRowUps().add(entryRow);
            entryRow += 1;
        }

        if (baplieDischarge.getVRow() == null) {
            baplieDischarge.setVRow(rowChoices.get(0).shortValue());
        }

        changeRow(baplieDischarge.getVRow());
        fixEmptyRowChoices();
    }

    public void changeRow(Short newRow) {
        Integer startTier = 0;
        Integer totalTier = 0;
        Integer row = newRow.intValue();
        tierChoices.clear();
        if (getRowUps().contains(row) && getRowBottoms().contains(row)) {
            List<Object[]> loopTierBottom = masterProfileDetailFacadeRemote.findTierByRow(vesselCode, "bottom", baplieDischarge.getVBay().intValue(), row);
            if (!loopTierBottom.isEmpty()) {
                startTier = (Integer) loopTierBottom.get(0)[0];
                totalTier = (Integer) loopTierBottom.get(0)[1];
            }
            Integer entryTier = startTier;
            for (Integer j = 0; j < totalTier; j++) {
                tierChoices.add(entryTier);
                entryTier += 2;
            }
            List<Object[]> loopTierUp = masterProfileDetailFacadeRemote.findTierByRow(vesselCode, "up", baplieDischarge.getVBay().intValue(), row);
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
        } else if (getRowBottoms().contains(row)) {
            List<Object[]> loopTier = masterProfileDetailFacadeRemote.findTierByRow(vesselCode, "bottom", baplieDischarge.getVBay().intValue(), row);
            if (!loopTier.isEmpty()) {
                startTier = (Integer) loopTier.get(0)[0];
                totalTier = (Integer) loopTier.get(0)[1];
            }
            Integer entryTier = startTier;
            for (Integer j = 0; j < totalTier; j++) {
                tierChoices.add(entryTier);
                entryTier += 2;
            }
        } else if (getRowUps().contains(row)) {
            List<Object[]> loopTier = masterProfileDetailFacadeRemote.findTierByRow(vesselCode, "up", baplieDischarge.getVBay().intValue(), row);
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

        if (baplieDischarge.getVTier() == null) {
            baplieDischarge.setVTier(tierChoices.get(0).shortValue());
        }

        fixEmptyTierChoices();
    }

    private void fixEmptyCyChoices(){
        fixEmptyBayChoices();
        fixEmptyRowChoices();
        fixEmptyTierChoices();
    }

    private void fixEmptyBayChoices() {
        if ((bayChoices.isEmpty() || !bayChoices.contains(baplieDischarge.getVBay().intValue())) && opsi.equals("delete")) {
            bayChoices.add(baplieDischarge.getVBay().intValue());
        }
    }

    private void fixEmptyRowChoices() {
        if ((rowChoices.isEmpty() || !rowChoices.contains(baplieDischarge.getVRow().intValue())) && opsi.equals("delete")) {
            rowChoices.add(baplieDischarge.getVRow().intValue());
        }
    }

    private void fixEmptyTierChoices() {
        if ((tierChoices.isEmpty() || !tierChoices.contains(baplieDischarge.getVTier().intValue())) && opsi.equals("delete")) {
            tierChoices.add(baplieDischarge.getVTier().intValue());
        }
    }

    public void onChangeConstraintType(ValueChangeEvent event) {
        int newItem = (Integer) event.getNewValue();
        MasterContainerType mct = masterContainerTypeFacadeRemote.find(newItem);
        baplieDischarge.setMasterContainerType(mct);
        baplieDischarge.setContSize(mct.getFeetMark());
    }

    public void handleShowMasterCustomers(ActionEvent event) {
        masterCustomers = masterCustomerFacadeRemote.findMasterCustomers();
    }

    public void handleSelectCommodity() {
        baplieDischarge.setDg("FALSE");
        commodityName = baplieDischarge.getMasterCommodity().getName();
        uncontainerizedService.setCommodity(baplieDischarge.getMasterCommodity().getCommodityCode());

        if (baplieDischarge.getMasterCommodity() != null && baplieDischarge.getMasterCommodity().getMasterDangerousClass() != null) {
            baplieDischarge.setDg("TRUE");
        }
    }

    public void handleSelectMasterCustomers(ActionEvent event) {
        String custCode = (String) event.getComponent().getAttributes().get("custCode");
        MasterCustomer mlo = masterCustomerFacadeRemote.find(custCode);
        baplieDischarge.setMlo(mlo);
    }

    public void handleDeleteAll() {
        opsi = "deleteall";
    }

    public Boolean SetNewShiftButton(Object obj){

        return (Boolean) obj;
    }

    /**
     * @return the shiftDischarge
     */
    public Object[] getShiftDischarge() {
        return shiftDischarge;
    }

    /**
     * @return the containers
     */
    public Object[][] getContainers() {
        return containers;
    }

    /**
     * @param containers the containers to set
     */
    public void setContainers(Object[][] containers) {
        this.containers = containers;
    }

    /**
     * @return the containerRemoves
     */
    public Object[][] getContainerRemoves() {
        return containerRemoves;
    }

    /**
     * @param containerRemoves the containerRemoves to set
     */
    public void setContainerRemoves(Object[][] containerRemoves) {
        this.containerRemoves = containerRemoves;
    }

    /**
     * @return opsi
     */
    public String getOpsi() {
        return opsi;
    }

    /**
     * @return the noPPKB
     */
    public String getNoPPKB() {
        return noPPKB;
    }

    /**
     * @param noPPKB to set
     */
    public void SetNoPPKB(String noPPKB) {
        this.noPPKB = noPPKB;
    }

    /**
     * @return the baplieDischarge
     */
    public BaplieDischarge getBaplieDischarge() {
        return baplieDischarge;
    }

    /**
     * @return the planningShiftDischarge
     */
    public PlanningShiftDischarge getPlanningShiftDischarge() {
        return planningShiftDischarge;
    }

    /**
     * @param planningShiftDischarge the planningShiftDischarge to set
     */
    public void setPlanningShiftDischarge(PlanningShiftDischarge planningShiftDischarge) {
        this.planningShiftDischarge = planningShiftDischarge;
    }

    /**
     * @param baplieDischarge the baplieDischarge to set
     */
    public void setBaplieDischarge(BaplieDischarge baplieDischarge) {
        this.baplieDischarge = baplieDischarge;
    }

    /**
     * @return the planningVessels
     */
    public List<Object[]> getPlanningVessels() {
        return planningVessels;
    }

    /**
     * @return the vessel
     */
    public Object[] getVessel() {
        return vessel;
    }

    /**
     * @return the planningVessel
     */
    public PlanningVessel getPlanningVessel() {
        return planningVessel;
    }

    /**
     * @return the baplieDischarges
     */
    public List<Object[]> getBaplieDischarges() {
        return baplieDischarges;
    }

    /**
     * @return the planningShiftDischarges
     */
    public List<Object[]> getPlanningShiftDischarges() {
        return planningShiftDischarges;
    }

    public List<Object[]> getUcList() {
        return ucList;
    }

    public void setUcList(List<Object[]> UcList) {
        this.ucList = UcList;
    }

    public UncontainerizedService getUncontainerizedService() {
        return uncontainerizedService;
    }

    public void setUncontainerizedService(UncontainerizedService uncontainerizedService) {
        this.uncontainerizedService = uncontainerizedService;
    }

    public List<Object[]> getUncontaineraizedList() {
        return uncontaineraizedList;
    }

    public void setUncontaineraizedList(List<Object[]> uncontaineraizedList) {
        this.uncontaineraizedList = uncontaineraizedList;
    }

    public void handleSelectNoContainer(ActionEvent event) {
        int noContainer = (Integer) event.getComponent().getAttributes().get("noContainer");
        baplieDischarge = baplieDischargeFacadeRemote.find(noContainer);
    }

    public void handleAdd(ActionEvent event) {
        planningShiftDischarge = new PlanningShiftDischarge();
//        planningShiftDischarge.setPlanningVessel(new PlanningVessel());
//        planningShiftDischarge.setMasterCommodity(new MasterCommodity());
//        planningShiftDischarge.setMasterContainerType(new MasterContainerType());
        planningShiftDischarge.setShiftTo("LANDED");
//        baplieDischarge = new BaplieDischarge();
//        baplieDischarge.setMasterContainerType(new MasterContainerType());
//        baplieDischarge.setMasterCommodity(new MasterCommodity());
//        baplieDischarge.setMasterPort1(new MasterPort());
//        baplieDischarge.setMasterPort(new MasterPort());
        baplieDischarges = baplieDischargeFacadeRemote.findBaplieDischargesByPpkbShifting(noPPKB);
    }

    public void save(ActionEvent event) {
        boolean loggedIn = false;
        if (baplieDischarge.getId() == null) {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.save.failed");
        } else {
            loggedIn = true;
            planningShiftDischarge.setId(baplieDischarge.getId());
            planningShiftDischarge.setMasterContainerType(baplieDischarge.getMasterContainerType());
            planningShiftDischarge.setMasterCommodity(baplieDischarge.getMasterCommodity());
            planningShiftDischarge.setPlanningVessel(planningVessel);
            planningShiftDischarge.setContNo(baplieDischarge.getContNo());
            planningShiftDischarge.setMlo(baplieDischarge.getMlo());
            planningShiftDischarge.setContSize(baplieDischarge.getContSize());
            planningShiftDischarge.setContStatus(baplieDischarge.getContStatus());
            planningShiftDischarge.setContGross(baplieDischarge.getContGross());
            planningShiftDischarge.setSealNo(baplieDischarge.getSealNo());
            planningShiftDischarge.setDg(baplieDischarge.getDg());
            planningShiftDischarge.setDgLabel(baplieDischarge.getDgLabel());
            planningShiftDischarge.setOverSize(baplieDischarge.getOverSize());
            planningShiftDischarge.setTradeType(baplieDischarge.getTradeType());
            planningShiftDischarge.setLoadPort(baplieDischarge.getMasterPort().getPortCode());
            planningShiftDischarge.setDischPort(baplieDischarge.getMasterPort1().getPortCode());
            planningShiftDischarge.setVBay(baplieDischarge.getVBay());
            planningShiftDischarge.setVRow(baplieDischarge.getVRow());
            planningShiftDischarge.setVTier(baplieDischarge.getVTier());
            //save database
            planningShiftDischargeFacadeRemote.edit(planningShiftDischarge);

            planningShiftDischarges = planningShiftDischargeFacadeRemote.findPlanningShiftDischargesByPPKB(noPPKB);

            //inisialisasi ulang data Baplie untuk add selanjutnya
            baplieDischarge = new BaplieDischarge();
            baplieDischarge.setMasterCommodity(new MasterCommodity());
            baplieDischarge.setMasterContainerType(new MasterContainerType());
            baplieDischarge.setMasterPort(new MasterPort());
            baplieDischarge.setMasterPort1(new MasterPort());
            //inisialisasi data planningShiftDischarge
            planningShiftDischarge = new PlanningShiftDischarge();
            planningShiftDischarge.setPlanningVessel(new PlanningVessel());
            planningShiftDischarge.setMasterCommodity(new MasterCommodity());
            planningShiftDischarge.setMasterContainerType(new MasterContainerType());
            planningShiftDischarge.setShiftTo("LANDED");

            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
            RequestContext.getCurrentInstance().addCallbackParam("loggedIn", loggedIn);
        }
    }

    public void saveEditUC() {
        boolean loggedIn = false;
        if (uncontainerizedService.getWeight() == null) {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.save.failed");
            //FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.transaction.notcompleted");
        } else {
            loggedIn = true;
//            String commName = masterCommodityFacadeRemote.find(baplieDischarge.getMasterCommodity().getCommodityCode()).getCommodityCode();
//            uncontainerizedService.setCommodity(commName);
            System.out.println("Commodity : " + uncontainerizedService.getCommodity());
            uncontainerizedService.setIsShifting("TRUE");
            uncontainerizedServiceFacadeRemote.edit(uncontainerizedService);
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
        }
        RequestContext.getCurrentInstance().addCallbackParam("loggedIn", loggedIn);
        ucList = uncontainerizedServiceFacadeRemote.findByPpkbAndBlList(noPPKB);
        uncontaineraizedList = uncontainerizedServiceFacadeRemote.findByPpkbAndBl(noPPKB);
        uncontainerizedService = new UncontainerizedService();
    }

    public void careateEntryGenerate(ActionEvent event) {
        try {
            for (int i = 0; i < uncontainerizedArray.length; i++) {
                uncontainerizedService = uncontainerizedServiceFacadeRemote.find(uncontainerizedArray[i][4]);
                uncontainerizedService.setIsShifting("TRUE");
                uncontainerizedService.setShiftTo((String) uncontainerizedArray[i][8]);
                uncontainerizedServiceFacadeRemote.edit(uncontainerizedService);
                ucList = uncontainerizedServiceFacadeRemote.findByPpkbAndBlList(noPPKB);
                uncontaineraizedList = uncontainerizedServiceFacadeRemote.findByPpkbAndBl(noPPKB);
                FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
            }
        } catch (EJBException e) {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.failed");
        }
        uncontainerizedService = new UncontainerizedService();
    }

    public void handleEditDeleteUC(ActionEvent event) {
        int idUC = (Integer) event.getComponent().getAttributes().get("idUC");
        uncontainerizedService = uncontainerizedServiceFacadeRemote.find(idUC);
        isEdit = true;

            commodityName = masterCommodityFacadeRemote.find(uncontainerizedService.getCommodity()).getName();
            port1 = (String)masterPortFacadeRemote.findNameByCode(uncontainerizedService.getLoadPort());
            port2 = (String)masterPortFacadeRemote.findNameByCode(uncontainerizedService.getDischPort());
            System.out.println("port1 : " + port1);
            System.out.println("port2 : " + port2);
    }

    public void deleteUC() {
        //planningUcShiftingFacadeRemote.remove(planningUcShifting);
        try {
            uncontainerizedService.setIsShifting("FALSE");
            uncontainerizedService.setShiftTo(null);
            uncontainerizedServiceFacadeRemote.edit(uncontainerizedService);
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.delete.succeeded");
            ucList = uncontainerizedServiceFacadeRemote.findByPpkbAndBlList(noPPKB);
            uncontaineraizedList = uncontainerizedServiceFacadeRemote.findByPpkbAndBl(noPPKB);
            uncontainerizedService = new UncontainerizedService();
        } catch (EJBException ex) {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.delete.failed");
            ex.printStackTrace();
        }
    }

    /**
     * @return the isEdit
     */
    public boolean isIsEdit() {
        return isEdit;
    }

    public Object[][] getUncontainerizedArray() {
        return uncontainerizedArray;
    }

    public void setUncontainerizedArray(Object[][] uncontainerizedArray) {
        this.uncontainerizedArray = uncontainerizedArray;
    }

    public List<Object[]> getLoadPlanningAllocationsInfo() {
        return loadPlanningAllocationsInfo;
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
     * @return the rowBottoms
     */
    public List<Integer> getRowBottoms() {
        return rowBottoms;
    }

    /**
     * @param rowBottoms the rowBottoms to set
     */
    public void setRowBottoms(List<Integer> rowBottoms) {
        this.rowBottoms = rowBottoms;
    }

    /**
     * @return the rowUps
     */
    public List<Integer> getRowUps() {
        return rowUps;
    }

    /**
     * @param rowUps the rowUps to set
     */
    public void setRowUps(List<Integer> rowUps) {
        this.rowUps = rowUps;
    }

    /**
     * @return the masterCustomers
     */
    public List<Object[]> getMasterCustomers() {
        return masterCustomers;
    }

    /**
     * @param masterCustomers the masterCustomers to set
     */
    public void setMasterCustomers(List<Object[]> masterCustomers) {
        this.masterCustomers = masterCustomers;
    }

    /**
     * @return the serviceShifting
     */
    public ServiceShifting getServiceShifting() {
        return serviceShifting;
    }

    /**
     * @param serviceShifting the serviceShifting to set
     */
    public void setServiceShifting(ServiceShifting serviceShifting) {
        this.serviceShifting = serviceShifting;
    }

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public String getPort1() {
        return port1;
    }

    public void setPort1(String port1) {
        this.port1 = port1;
    }

    public String getPort2() {
        return port2;
    }

    public void setPort2(String port2) {
        this.port2 = port2;
    }
}
