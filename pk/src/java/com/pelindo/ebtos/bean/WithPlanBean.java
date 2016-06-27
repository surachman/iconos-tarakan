/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.bean;

import com.qtasnim.jsf.FacesHelper;
import com.pelindo.ebtos.ejb.facade.remote.EquipmentFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterEquipmentFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterOperatorFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterProfileDetailFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterVesselProfileFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningVesselFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceShiftingFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceVesselFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.UcShiftingServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.UncontainerizedServiceFacadeRemote;
import com.pelindo.ebtos.model.db.Equipment;
import com.pelindo.ebtos.model.db.PlanningVessel;
import com.pelindo.ebtos.model.db.ServiceShifting;
import com.pelindo.ebtos.model.db.UcShiftingService;
import com.pelindo.ebtos.model.db.UncontainerizedService;
import com.pelindo.ebtos.model.db.master.MasterActivity;
import com.pelindo.ebtos.model.db.master.MasterCommodity;
import com.pelindo.ebtos.model.db.master.MasterContainerType;
import com.pelindo.ebtos.model.db.master.MasterEquipment;
import com.pelindo.ebtos.model.db.master.MasterOperator;
import com.pelindo.ebtos.util.ServiceUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import org.primefaces.context.RequestContext;

/**
 *
 * @author dycode-java
 */
@ManagedBean(name="withPlanBean")
@ViewScoped
public class WithPlanBean implements Serializable{
    @EJB
    private EquipmentFacadeRemote equipmentFacade;
    @EJB
    private ServiceShiftingFacadeRemote serviceShiftingFacade;
    @EJB
    private UcShiftingServiceFacadeRemote ucShiftingServiceFacade;
    @EJB
    private UncontainerizedServiceFacadeRemote uncontainerizedServiceFacadeRemote;
    @EJB
    private ServiceVesselFacadeRemote serviceVesselFacade;
    @EJB
    private MasterOperatorFacadeRemote masterOperatorFacade;
    @EJB
    private MasterEquipmentFacadeRemote masterEquipmentFacade;
    @EJB
    private PlanningVesselFacadeRemote planningVesselFacadeRemote;
    @EJB
    private MasterVesselProfileFacadeRemote masterVesselProfileFacadeRemote;
    @EJB
    private MasterProfileDetailFacadeRemote masterProfileDetailFacadeRemote;

    private List<MasterOperator> operators;
    private List<Object[]> serviceVessels;
    private List<Object[]> equipments;
    private List<Object[]> serviceShiftings;
    private List<Object[]> serviceShiftingsPick;
    private List<Object[]> ucShiftingServices;
    private List<Object[]> uncontainerizedServices;
    private List<Integer> bayChoices;
    private List<Integer> rowChoices;
    private List<Integer> tierChoices;
    private List<Integer> rowUps;
    private List<Integer> rowBottoms;
    private Equipment equipment;
    private Equipment equipmentRe;
    private PlanningVessel planningVessel;
    private ServiceShifting serviceShifting;
    private UcShiftingService ucShiftingService;
    private UncontainerizedService uncontainerizedService;
    private Object[] serviceVessel;
    private String vesselCode = "";
    private String no_ppkb;

    /** Creates a new instance of WithPlanBean */
    public WithPlanBean() {}

    @PostConstruct
    private void construct() {
        init();
        bayChoices = new ArrayList<Integer>();
        rowChoices = new ArrayList<Integer>();
        tierChoices = new ArrayList<Integer>();
        rowUps = new ArrayList<Integer>();
        rowBottoms = new ArrayList<Integer>();

        operators = masterOperatorFacade.findAll();
        serviceVessels = serviceVesselFacade.findServiceVesselServicing();
        equipments = masterEquipmentFacade.findCraneForView();
    }

    private void init(){
        serviceShifting = new ServiceShifting();
        serviceShifting.setMasterActivity(new MasterActivity());
        serviceShifting.setMasterContainerType(new MasterContainerType());
        serviceShifting.setMasterCommodity(new MasterCommodity());
        ucShiftingService = new UcShiftingService();
        uncontainerizedService = new UncontainerizedService();
        equipment = new Equipment();
        equipment.setMasterEquipment(new MasterEquipment());
        equipment.setMasterOperator(new MasterOperator());
    }

    public void handleRefresh(ActionEvent event){
        serviceShiftings = serviceShiftingFacade.findServiceShiftingReshippingNotToCY(no_ppkb);
    }

    public void handleRefreshUc(ActionEvent event){
        ucShiftingServices = ucShiftingServiceFacade.findUcShiftingServiceWithPlan(no_ppkb);
    }

    public void handleShifting(ActionEvent event){
        init();
        serviceShifting.setCrane("L");
        ucShiftingService.setCrane("L");
        serviceShiftingsPick = serviceShiftingFacade.findServiceShiftingReshippingFalse(no_ppkb);
        uncontainerizedServices = uncontainerizedServiceFacadeRemote.findShifting(no_ppkb);
        //untuk mengkondisikan pemilihan bay
        serviceShifting.setVBay(Short.parseShort(bayChoices.get(0).toString()));
        changeBay(serviceShifting.getVBay());
        changeRow(Short.parseShort(rowChoices.get(0).toString()));
    }

    public void handleSelectNoPpkb(ActionEvent event){
        no_ppkb = (String) event.getComponent().getAttributes().get("noPpkb");
        serviceVessel = serviceVesselFacade.findServiceVesselByPpkb(no_ppkb);
        planningVessel = planningVesselFacadeRemote.find(no_ppkb);
        serviceShiftings = serviceShiftingFacade.findServiceShiftingReshippingNotToCY(no_ppkb);
        serviceShiftingsPick = serviceShiftingFacade.findServiceShiftingReshippingFalse(no_ppkb);
        ucShiftingServices = ucShiftingServiceFacade.findUcShiftingServiceWithPlan(no_ppkb);
        uncontainerizedServices = uncontainerizedServiceFacadeRemote.findShifting(no_ppkb);
        vesselCode = serviceVessel[4].toString();
        //ngurusin bay
        bayChoices.clear();
        bayChoices = masterVesselProfileFacadeRemote.findBaysByVessel(vesselCode);
    }

    public void handleSelectCont(ActionEvent event){
        Integer id_cont = (Integer) event.getComponent().getAttributes().get("idCont");
        Integer id_eq = (Integer) event.getComponent().getAttributes().get("idEq");
        serviceShifting = serviceShiftingFacade.find(id_cont);
        if(id_eq != null) {
            equipment = equipmentFacade.find(id_eq);
            if (equipment.getMasterOperator()==null)
                equipment.setMasterOperator(new MasterOperator());
        }
        else{
            equipment = new Equipment();
            equipment.setMasterEquipment(new MasterEquipment());
            equipment.setMasterOperator(new MasterOperator());
        }

        baySetting();
    }

    public void handleSelectContUC(ActionEvent event){
        Integer id_cont = (Integer) event.getComponent().getAttributes().get("idCont");
        uncontainerizedService = uncontainerizedServiceFacadeRemote.find(id_cont);
        ucShiftingService.setBlNo(uncontainerizedService.getBlNo());
        ucShiftingService.setOperation(uncontainerizedService.getOperation());
        ucShiftingService.setCrane(uncontainerizedService.getCrane());
        ucShiftingService.setIsPaid("TRUE");
        ucShiftingService.setIsPlanning("TRUE");
        ucShiftingService.setShiftTo(uncontainerizedService.getShiftTo());
    }

    public void handleEdDelContUC(ActionEvent event){
        Integer id_cont = (Integer) event.getComponent().getAttributes().get("idCont");
        Integer idU = (Integer) event.getComponent().getAttributes().get("idU");
        Integer id_eq = (Integer) event.getComponent().getAttributes().get("idEq");
        Integer id_eqr = (Integer) event.getComponent().getAttributes().get("idEqRe");
        ucShiftingService = ucShiftingServiceFacade.find(id_cont);
        uncontainerizedService = uncontainerizedServiceFacadeRemote.find(idU);
        if(id_eq != null)
            equipment = equipmentFacade.find(id_eq);
        else{
            equipment = new Equipment();
            equipment.setMasterEquipment(new MasterEquipment());
            equipment.setMasterOperator(new MasterOperator());
        }
        if(id_eqr != null)
            equipmentRe = equipmentFacade.find(id_eqr);
        else{
            equipmentRe = new Equipment();
            equipmentRe.setMasterEquipment(new MasterEquipment());
            equipmentRe.setMasterOperator(new MasterOperator());
        }
        serviceShifting = new ServiceShifting();
        serviceShifting.setMasterActivity(new MasterActivity());
        serviceShifting.setMasterContainerType(new MasterContainerType());
        serviceShifting.setMasterCommodity(new MasterCommodity());
    }

    public void handleSelectContRe(ActionEvent event){
        Integer id_cont = (Integer) event.getComponent().getAttributes().get("idCont");
        Integer id_eqr = (Integer) event.getComponent().getAttributes().get("idEqRe");
        serviceShifting = serviceShiftingFacade.find(id_cont);
        if(id_eqr != null)
            equipment = equipmentFacade.find(id_eqr);
        else{
            equipment = new Equipment();
            equipment.setMasterEquipment(new MasterEquipment());
            equipment.setMasterOperator(new MasterOperator());
        }

        baySetting();
    }

     //handling crane
    public void onChangeCrane(ValueChangeEvent event) {
        String newCrane = (String) event.getNewValue();
        serviceShifting.setCrane(newCrane);
        if (newCrane.equalsIgnoreCase("S")) {
            equipment.setMasterEquipment(masterEquipmentFacade.find("EQ000"));
            equipment.setMasterOperator(masterOperatorFacade.find("0"));
        }else if (newCrane.equalsIgnoreCase("L")) {
            equipment.setMasterEquipment(new MasterEquipment());
            equipment.setMasterOperator(new MasterOperator());
        }
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
            startRow = (Integer) loopRowBottom.get(0)[0];
            totalRow = (Integer) loopRowBottom.get(0)[1];
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
            startRow = (Integer) loopRowUp.get(0)[0];
            totalRow = (Integer) loopRowUp.get(0)[1];
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
    }

    public void changeRow(Short newRow) {
        Integer startTier = 0;
        Integer totalTier = 0;
        Integer row = newRow.intValue();
        tierChoices.clear();
        if (rowUps.contains(row) && rowBottoms.contains(row)) {
            List<Object[]> loopTierBottom = masterProfileDetailFacadeRemote.findTierByRow(vesselCode, "bottom", serviceShifting.getVBay().intValue(), row);
            if (!loopTierBottom.isEmpty()) {
                startTier = (Integer) loopTierBottom.get(0)[0];
                totalTier = (Integer) loopTierBottom.get(0)[1];
            }
            Integer entryTier = startTier;
            for (Integer j = 0; j < totalTier; j++) {
                tierChoices.add(entryTier);
                entryTier += 2;
            }
            List<Object[]> loopTierUp = masterProfileDetailFacadeRemote.findTierByRow(vesselCode, "up", serviceShifting.getVBay().intValue(), row);
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
        } else if (rowBottoms.contains(row)) {
            List<Object[]> loopTier = masterProfileDetailFacadeRemote.findTierByRow(vesselCode, "bottom", serviceShifting.getVBay().intValue(), row);
            if (!loopTier.isEmpty()) {
                startTier = (Integer) loopTier.get(0)[0];
                totalTier = (Integer) loopTier.get(0)[1];
            }
            Integer entryTier = startTier;
            for (Integer j = 0; j < totalTier; j++) {
                tierChoices.add(entryTier);
                entryTier += 2;
            }
        } else if (rowUps.contains(row)) {
            List<Object[]> loopTier = masterProfileDetailFacadeRemote.findTierByRow(vesselCode, "up", serviceShifting.getVBay().intValue(), row);
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
    }

    public void baySetting(){
        setBayChoices(masterVesselProfileFacadeRemote.findBaysByVessel(vesselCode));
        Integer startRow = 0;
        Integer totalRow = 0;
        Integer startTier = 0;
        Integer totalTier = 0;
        
        if (serviceShifting.getPositionBay() != null && serviceShifting.getVBay() != null) {
            List<Object[]> loopRow = masterVesselProfileFacadeRemote.findRowByBay(vesselCode, serviceShifting.getPositionBay(), serviceShifting.getVBay());
            if (!loopRow.isEmpty()) {
                startRow = (Integer) loopRow.get(0)[0];
                totalRow = (Integer) loopRow.get(0)[1];
            }
            List<Object[]> loopTier = masterProfileDetailFacadeRemote.findTierByRow(vesselCode, serviceShifting.getPositionBay(), serviceShifting.getVBay(), startRow);
            if (!loopTier.isEmpty()) {
                startTier = (Integer) loopTier.get(0)[0];
                totalTier = (Integer) loopTier.get(0)[1];
            }
        }
    }

    public void handleSelectOperator(ActionEvent event){
        String id_operator = (String) event.getComponent().getAttributes().get("idOperator");
        equipment.setMasterOperator(masterOperatorFacade.find(id_operator));
    }

    public void handleSelectEquipment(ActionEvent event){
        String id_equipment = (String) event.getComponent().getAttributes().get("idEquipment");
        equipment.setMasterEquipment(masterEquipmentFacade.find(id_equipment));
    }

    public void handleConfirm(ActionEvent event){
        boolean loggedIn = false;
        if(serviceShifting.getContNo() == null || equipment.getMasterEquipment() == null || equipment.getMasterOperator() == null || equipment.getStartTime() == null || equipment.getEndTime() == null)
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Warning", "application.transaction.notcompleted");
        else if(equipment.getStartTime().after(equipment.getEndTime()) || equipment.getStartTime().equals(equipment.getEndTime()))
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Warning", "application.validation.time");
        else {
            loggedIn = true;
            
            if(equipment.getMasterEquipment() != null)
                serviceShifting.setBlNo(equipment.getMasterEquipment().getEquipmentGroup());

            if (serviceShifting.getShiftTo().equals("LANDED")) {
                equipment.setOperation("SHIFTING-LANDED");
                serviceShifting.setIsLanded("TRUE");
                serviceShifting.setIsReshipping("FALSE");
            } else {
                equipment.setOperation("SHIFTING-NOLAND");
                serviceShifting.setShiftingDate(equipment.getEndTime());
                serviceShifting.setIsLanded("FALSE");
                serviceShifting.setIsReshipping("TRUE");
            }
            if (equipment.getMasterOperator().getOptCode()==null)
                equipment.setMasterOperator(null);
            equipment.setPlanningVessel(planningVessel);
            equipment.setContNo(serviceShifting.getContNo());
            equipment.setMlo(serviceShifting.getMlo());
            equipment.setEquipmentActCode("STEVEDORING");
            equipmentFacade.edit(equipment);
            
            String equipmentCode = equipment.getMasterEquipment().getEquipCode();
            String craneCode = ServiceUtil.getCraneCode(equipmentCode);
            serviceShifting.setCrane(craneCode);
            
            
//            int i = equipmentFacade.findIdEquipmentByAll(equipment.getMasterEquipment().getEquipCode(), equipment.getMasterOperator()!=null? equipment.getMasterOperator().getOptCode() : null, equipment.getContNo(), no_ppkb, equipment.getStartTime(), equipment.getEndTime(), equipment.getEquipmentActCode(), equipment.getOperation());
            int i = equipmentFacade.findEquipmentId(serviceShifting.getServiceVessel().getNoPpkb(), equipment.getStartTime(), equipment.getEndTime(), equipment.getEquipmentActCode(), equipment.getOperation());
            
            
            serviceShifting.setIdEquipment(i);
            serviceShifting.setIsPaid("TRUE");
            serviceShiftingFacade.edit(serviceShifting);

            serviceShiftings = serviceShiftingFacade.findServiceShiftingReshippingNotToCY(no_ppkb);
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
            RequestContext.getCurrentInstance().addCallbackParam("loggedIn", loggedIn);
        }
    }

    public void handleConfirmUC(ActionEvent event){
        boolean loggedIn = false;
        if(ucShiftingService.getShiftTo() == null || uncontainerizedService.getBlNo() == null || equipment.getMasterEquipment() == null || equipment.getMasterOperator() == null || equipment.getStartTime() == null || equipment.getEndTime() == null)
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Warning", "application.transaction.notcompleted");
        else if(equipment.getStartTime().after(equipment.getEndTime()) || equipment.getStartTime().equals(equipment.getEndTime()))
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Warning", "application.validation.time");
        else {

            if (ucShiftingService.getShiftTo().equals("LANDED")) {
                Date now = new Date();

                equipment.setOperation("SHIFTING-LANDED");
                
                ucShiftingService.setIsLanded("TRUE");
                ucShiftingService.setIsReshipping("FALSE");

                uncontainerizedService.setStatus(UncontainerizedService.STATUS_STV);
                uncontainerizedService.setPlacementDate(now);
            } else {
                equipment.setOperation("SHIFTING-NOLAND");
                ucShiftingService.setIsLanded("FALSE");
                ucShiftingService.setIsReshipping("TRUE");
                uncontainerizedService.setStatus(UncontainerizedService.STATUS_VESSEL);
            }

            equipment.setPlanningVessel(planningVessel);
            equipment.setBlNo(ucShiftingService.getBlNo());
            equipment.setEquipmentActCode("STEVEDORING");

            equipmentFacade.edit(equipment);

            int i = equipmentFacade.findIdEquipmentByAllBlNo(equipment.getMasterEquipment().getEquipCode(), equipment.getMasterOperator().getOptCode(), equipment.getBlNo(), no_ppkb, equipment.getStartTime(), equipment.getEndTime(), equipment.getEquipmentActCode(), equipment.getOperation());
            ucShiftingService.setIdEquipment(i);
            ucShiftingService.setNoPpkb(no_ppkb);
            ucShiftingService.setIsPaid("TRUE");
            ucShiftingServiceFacade.edit(ucShiftingService);
            uncontainerizedServiceFacadeRemote.edit(uncontainerizedService);

            ucShiftingServices = ucShiftingServiceFacade.findUcShiftingServiceWithPlan(no_ppkb);
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
            loggedIn = true;
        }
        
        RequestContext.getCurrentInstance().addCallbackParam("loggedIn", loggedIn);
    }

    public void handleConfirmReshipping(ActionEvent event){
        boolean loggedIn = false;

        if (equipment.getStartTime().after(equipment.getEndTime()) || equipment.getStartTime().equals(equipment.getEndTime())) {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Warning", "application.validation.time");
        } else {
            loggedIn = true;
            equipment.setPlanningVessel(planningVessel);
            equipment.setContNo(serviceShifting.getContNo());
            equipment.setMlo(serviceShifting.getMlo());
            equipment.setOperation("RESHIPPING");
            equipment.setEquipmentActCode("STEVEDORING");
            equipmentFacade.edit(equipment);

            int i = equipmentFacade.findIdEquipmentByAll(equipment.getMasterEquipment().getEquipCode(), equipment.getMasterOperator().getOptCode(), equipment.getContNo(), no_ppkb, equipment.getStartTime(), equipment.getEndTime(), equipment.getEquipmentActCode(), equipment.getOperation());
            serviceShifting.setIsReshipping("TRUE");
            serviceShifting.setShiftingDate(equipment.getEndTime());
            serviceShifting.setIdEquipmentReshipping(i);
            String equipmentCode = equipment.getMasterEquipment().getEquipCode();
            String craneCode = ServiceUtil.getCraneCode(equipmentCode);
            serviceShifting.setCrane(craneCode);
            serviceShiftingFacade.edit(serviceShifting);

            serviceShiftings = serviceShiftingFacade.findServiceShiftingReshippingNotToCY(no_ppkb);
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
            RequestContext.getCurrentInstance().addCallbackParam("loggedIn", loggedIn);
        }
    }

    public void handleConfirmReshippingUC(ActionEvent event){
        boolean loggedIn = false;

        if(equipment.getStartTime().after(equipment.getEndTime()) || equipment.getStartTime().equals(equipment.getEndTime())) {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Warning", "application.validation.time");
        } else {
            loggedIn = true;
            equipment.setPlanningVessel(planningVessel);
            equipment.setBlNo(ucShiftingService.getBlNo());
            equipment.setOperation("RESHIPPING");
            equipment.setEquipmentActCode("STEVEDORING");

            uncontainerizedService.setStatus(UncontainerizedService.STATUS_VESSEL);

            uncontainerizedServiceFacadeRemote.edit(uncontainerizedService);
            equipmentFacade.create(equipment);

            int i = equipmentFacade.findIdEquipmentByAllBlNo(equipment.getMasterEquipment().getEquipCode(), equipment.getMasterOperator().getOptCode(), equipment.getBlNo(), no_ppkb, equipment.getStartTime(), equipment.getEndTime(), equipment.getEquipmentActCode(), equipment.getOperation());
            ucShiftingService.setIsReshipping("TRUE");
            ucShiftingService.setIdEquipmentReshipping(i);
            
            String equipmentCode = equipment.getMasterEquipment().getEquipCode();
            String craneCode = ServiceUtil.getCraneCode(equipmentCode);
            ucShiftingService.setCrane(craneCode);
            ucShiftingServiceFacade.edit(ucShiftingService);

            ucShiftingServices = ucShiftingServiceFacade.findUcShiftingServiceWithPlan(no_ppkb);
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
            RequestContext.getCurrentInstance().addCallbackParam("loggedIn", loggedIn);
        }
    }

    public void handleSelectDelete(ActionEvent event){
        Integer id_cont = (Integer) event.getComponent().getAttributes().get("idCont");
        Integer id_eq = (Integer) event.getComponent().getAttributes().get("idEq");
        Integer id_eqr = (Integer) event.getComponent().getAttributes().get("idEqRe");
        serviceShifting = serviceShiftingFacade.find(id_cont);
        equipment = equipmentFacade.find(id_eq);
        if(id_eqr != null)
            equipmentRe = equipmentFacade.find(id_eqr);
    }

    public void handleDelete(ActionEvent event){
        if(serviceShifting.getId() != null){
            serviceShifting.setIsPlanning("TRUE");
            serviceShifting.setIsReshipping("FALSE");
            serviceShiftingFacade.edit(serviceShifting);
        } else
            ucShiftingServiceFacade.remove(ucShiftingService);
        if(equipment != null) 
                equipmentFacade.remove(equipment);
        if(equipmentRe != null)
            equipmentFacade.remove(equipmentRe);
        serviceShiftings = serviceShiftingFacade.findServiceShiftingReshippingNotToCY(no_ppkb);
        ucShiftingServices = ucShiftingServiceFacade.findUcShiftingServiceWithPlan(no_ppkb);
        FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.delete.succeeded");
    }

    public void onchangeBay(ValueChangeEvent event) {
        Short newBay = (Short) event.getNewValue();
        serviceShifting.setVBay(newBay);
        changeBay(newBay);
    }

    public void onChangeRow(ValueChangeEvent event) {
        Short newRow = (Short) event.getNewValue();
        serviceShifting.setVRow(newRow);
        changeRow(newRow);
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

    /**
     * @return the serviceShiftings
     */
    public List<Object[]> getServiceShiftings() {
        return serviceShiftings;
    }

    /**
     * @param serviceShiftings the serviceShiftings to set
     */
    public void setServiceShiftings(List<Object[]> serviceShiftings) {
        this.serviceShiftings = serviceShiftings;
    }

    /**
     * @return the serviceVessels
     */
    public List<Object[]> getServiceVessels() {
        return serviceVessels;
    }

    /**
     * @param serviceVessels the serviceVessels to set
     */
    public void setServiceVessels(List<Object[]> serviceVessels) {
        this.serviceVessels = serviceVessels;
    }

    /**
     * @return the serviceVessel
     */
    public Object[] getServiceVessel() {
        return serviceVessel;
    }

    /**
     * @param serviceVessel the serviceVessel to set
     */
    public void setServiceVessel(Object[] serviceVessel) {
        this.serviceVessel = serviceVessel;
    }

    /**
     * @return the serviceShiftingsPick
     */
    public List<Object[]> getServiceShiftingsPick() {
        return serviceShiftingsPick;
    }

    /**
     * @param serviceShiftingsPick the serviceShiftingsPick to set
     */
    public void setServiceShiftingsPick(List<Object[]> serviceShiftingsPick) {
        this.serviceShiftingsPick = serviceShiftingsPick;
    }

    /**
     * @return the equipment
     */
    public Equipment getEquipment() {
        return equipment;
    }

    /**
     * @param equipment the equipment to set
     */
    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    /**
     * @return the equipments
     */
    public List<Object[]> getEquipments() {
        return equipments;
    }

    /**
     * @param equipments the equipments to set
     */
    public void setEquipments(List<Object[]> equipments) {
        this.equipments = equipments;
    }

    /**
     * @return the operators
     */
    public List<MasterOperator> getOperators() {
        return operators;
    }

    /**
     * @param operators the operators to set
     */
    public void setOperators(List<MasterOperator> operators) {
        this.operators = operators;
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
     * @return the ucShiftingServices
     */
    public List<Object[]> getUcShiftingServices() {
        return ucShiftingServices;
    }

    /**
     * @param ucShiftingServices the ucShiftingServices to set
     */
    public void setUcShiftingServices(List<Object[]> ucShiftingServices) {
        this.ucShiftingServices = ucShiftingServices;
    }

    /**
     * @return the uncontainerizedServices
     */
    public List<Object[]> getUncontainerizedServices() {
        return uncontainerizedServices;
    }

    /**
     * @param uncontainerizedServices the uncontainerizedServices to set
     */
    public void setUncontainerizedServices(List<Object[]> uncontainerizedServices) {
        this.uncontainerizedServices = uncontainerizedServices;
    }

    /**
     * @return the ucShiftingService
     */
    public UcShiftingService getUcShiftingService() {
        return ucShiftingService;
    }

    /**
     * @param ucShiftingService the ucShiftingService to set
     */
    public void setUcShiftingService(UcShiftingService ucShiftingService) {
        this.ucShiftingService = ucShiftingService;
    }

    /**
     * @return the uncontainerizedService
     */
    public UncontainerizedService getUncontainerizedService() {
        return uncontainerizedService;
    }

    /**
     * @param uncontainerizedService the uncontainerizedService to set
     */
    public void setUncontainerizedService(UncontainerizedService uncontainerizedService) {
        this.uncontainerizedService = uncontainerizedService;
    }
}
