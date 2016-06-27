/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.bean;

import com.qtasnim.jsf.FacesHelper;
import com.pelindo.ebtos.bean.security.LoginSessionBean;
import com.pelindo.ebtos.ejb.facade.remote.CancelLoadingServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.EquipmentFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterCommodityFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterContainerTypeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterEquipmentFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterOperatorFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterProfileDetailFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterVesselProfileFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterYardCoordinatFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterYardFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceContDischargeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceGateDeliveryFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceShiftingFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceVesselFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.UcGatedeliveryServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.UcShiftingServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.UncontainerizedServiceFacadeRemote;
import com.pelindo.ebtos.ejb.remote.DischargeConfirmOperationRemote;
import com.pelindo.ebtos.exception.TimeSelectionNotValidException;
import com.pelindo.ebtos.model.db.CancelLoadingService;
import com.pelindo.ebtos.model.db.Equipment;
import com.pelindo.ebtos.model.db.PlanningVessel;
import com.pelindo.ebtos.model.db.ServiceContDischarge;
import com.pelindo.ebtos.model.db.ServiceGateDelivery;
import com.pelindo.ebtos.model.db.ServiceShifting;
import com.pelindo.ebtos.model.db.ServiceVessel;
import com.pelindo.ebtos.model.db.UcDeliveryService;
import com.pelindo.ebtos.model.db.UcGatedeliveryService;
import com.pelindo.ebtos.model.db.UcShiftingService;
import com.pelindo.ebtos.model.db.UncontainerizedService;
import com.pelindo.ebtos.model.db.master.MasterCommodity;
import com.pelindo.ebtos.model.db.master.MasterContainerType;
import com.pelindo.ebtos.model.db.master.MasterEquipment;
import com.pelindo.ebtos.model.db.master.MasterOperator;
import com.pelindo.ebtos.model.db.master.MasterYard;
import com.pelindo.ebtos.model.db.master.MasterYardCoordinat;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.primefaces.context.RequestContext;

/**
 *
 * @author dycoder
 */
@ManagedBean(name = "dischargeConfirmBean")
@ViewScoped
public class DischargeConfirmBean implements Serializable {
    private static final Logger logger = Logger.getLogger(DischargeConfirmBean.class.getName());

    private static final String TYPE_CANCEL_LOADING = "cancelLoading";
    private static final String TYPE_DISCHARGE = "cont";
    private static final String TYPE_SHIFT = "shift";

    @EJB
    private CancelLoadingServiceFacadeRemote cancelLoadingServiceFacadeRemote;
    @EJB
    private EquipmentFacadeRemote equipmentFacadeRemote;
    @EJB
    private MasterVesselProfileFacadeRemote masterVesselProfileFacadeRemote;
    @EJB
    private MasterProfileDetailFacadeRemote masterProfileDetailFacadeRemote;
    @EJB
    private MasterEquipmentFacadeRemote masterEquipmentFacadeRemote;
    @EJB
    private MasterOperatorFacadeRemote masterOperatorFacadeRemote;
    @EJB
    private MasterYardFacadeRemote masterYardFacadeRemote;
    @EJB
    private ServiceVesselFacadeRemote serviceVesselFacadeRemote;
    @EJB
    private ServiceShiftingFacadeRemote serviceShiftingFacadeRemote;
    @EJB
    private ServiceContDischargeFacadeRemote serviceContDischargeFacadeRemote;
    @EJB
    private UncontainerizedServiceFacadeRemote uncontainerizedServiceFacadeRemote;
    @EJB
    private ServiceGateDeliveryFacadeRemote serviceGateDeliveryFacadeRemote;
    @EJB
    private UcGatedeliveryServiceFacadeRemote ucGatedeliveryServiceFacadeRemote;
    @EJB
    private UcShiftingServiceFacadeRemote ucShiftingServiceFacadeRemote;
    @EJB
    private DischargeConfirmOperationRemote dischargeConfirmOperationRemote;

    private List<MasterOperator> masterOperators;
    private List<Object[]> masterCranes;
    private List<Object[]> masterHTs;
    private List<Object[]> serviceVessels;
    private List<Object[]> masterYards;
    private List<Object[]> serviceContDischarges;
    private List<Object[]> dischargableContainers;
    private List<Object[]> dischargableUcs;
    private List<Object[]> uncontainerizedServices;
    private List<Object[]> cancelLoadings;
    private List<Object[]> dischargableCancelLoadings;
    private List<Object[]> serviceShiftingList;
    private List<Object[]> dischargableShiftContainers;
    private List<Integer> bayChoices;
    private List<Integer> rowChoices;
    private List<Integer> tierChoices;
    private List<Integer> rowUps;
    private List<Integer> rowBottoms;
    private Map<Integer, Integer> slots;
    private Map<Integer, Integer> rows;
    private Map<Integer, Integer> tiers;
    private Object[] vesselDetail;
    private Object[] uncontainerizedDetail;
    private Object[] masterYard;
    private ServiceVessel serviceVessel;
    private ServiceContDischarge serviceContDischarge;
    private UncontainerizedService uncontainerizedService;
    private CancelLoadingService cancelLoadingService;
    private MasterYardCoordinat masterYardCoordinat;
    private ServiceShifting serviceShifting;
    private Equipment crane;
    private Equipment ht;
    private Integer counterSlot, counterRow, counterTier;
    private String ket = TYPE_DISCHARGE;
    private String noPPKB;
    private String vesselCode;
    private String opsi;
    private String username;
    private boolean disableYard = Boolean.FALSE;
    private boolean isHasEnteredGate = Boolean.FALSE;
    private String foreman;
    private String supervisiOperasi;

    public DischargeConfirmBean() {}

    @PostConstruct
    private void construct() {
        initial();
        bayChoices = new ArrayList<Integer>();
        rowChoices = new ArrayList<Integer>();
        tierChoices = new ArrayList<Integer>();
        rowUps = new ArrayList<Integer>();
        rowBottoms = new ArrayList<Integer>();
        cancelLoadingService = new CancelLoadingService();

        slots = new LinkedHashMap<Integer, Integer>();
        rows = new LinkedHashMap<Integer, Integer>();
        tiers = new LinkedHashMap<Integer, Integer>();
        counterSlot = 100;
        counterRow = 100;
        counterTier = 100;
        masterYardCoordinat = new MasterYardCoordinat();

        masterOperators = masterOperatorFacadeRemote.findAll();
        masterCranes = masterEquipmentFacadeRemote.findCraneForView();
        masterHTs = masterEquipmentFacadeRemote.findHtForView();
        masterYards = masterYardFacadeRemote.findMasterYards();

        LoginSessionBean session = LoginSessionBean.getCurrentInstance();
        username = session.getUsername();
    }

    public void initial() {
        //inisialisasi container
        serviceShifting = new ServiceShifting();
        serviceContDischarge = new ServiceContDischarge();
        serviceContDischarge.setMasterCommodity(new MasterCommodity());
        serviceContDischarge.setMasterContainerType(new MasterContainerType());
        serviceContDischarge.setMasterYard(new MasterYard());
        serviceContDischarge.setServiceVessel(new ServiceVessel());
        serviceContDischarge.setCrane("L");
        //inisialisasi crane
        crane = new Equipment();
        crane.setMasterEquipment(new MasterEquipment());
        crane.setMasterOperator(new MasterOperator());
        crane.setPlanningVessel(new PlanningVessel());
        //inisialisasi ht
        ht = new Equipment();
        ht.setMasterEquipment(new MasterEquipment());
        ht.setMasterOperator(new MasterOperator());
        ht.setPlanningVessel(new PlanningVessel());
    }
    
    public void resetForm() {
        //inisialisasi container
        serviceShifting = new ServiceShifting();
        serviceContDischarge = new ServiceContDischarge();
        serviceContDischarge.setMasterCommodity(new MasterCommodity());
        serviceContDischarge.setMasterContainerType(new MasterContainerType());
        serviceContDischarge.setMasterYard(new MasterYard());
        serviceContDischarge.setServiceVessel(new ServiceVessel());
        serviceContDischarge.setCrane("L");
        //inisialisasi crane
//        crane = new Equipment();
//        crane.setMasterEquipment(new MasterEquipment());
//        crane.setMasterOperator(new MasterOperator());
//        crane.setPlanningVessel(new PlanningVessel());
        //inisialisasi ht
//        ht = new Equipment();
//        ht.setMasterEquipment(new MasterEquipment());
//        ht.setMasterOperator(new MasterOperator());
//        ht.setPlanningVessel(new PlanningVessel());
    }

    public void postProcessXLS(Object document) {
        HSSFWorkbook wb = (HSSFWorkbook) document;
        HSSFSheet sheet = wb.getSheetAt(0);
        HSSFRow header = sheet.getRow(0);

        HSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setFillForegroundColor(HSSFColor.YELLOW.index);
        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        for (int i = 0; i < header.getPhysicalNumberOfCells(); i++) {
            HSSFCell cell = header.getCell(i);

            cell.setCellStyle(cellStyle);
        }
    }

    public void handleSelectDischargableShiftContainer(ActionEvent event) {
        Integer id = (Integer) event.getComponent().getAttributes().get("noContainer");
        serviceShifting = serviceShiftingFacadeRemote.find(id);
    }

    public void handleSelectDischargableUncontainerized(ActionEvent event) {
        int idUC = (Integer) event.getComponent().getAttributes().get("idSelect");
        uncontainerizedService = uncontainerizedServiceFacadeRemote.find(idUC);
        UcDeliveryService ucDeliveryService = uncontainerizedService.getUcDeliveryService();
        uncontainerizedService.setTruckLoosing("FALSE");
        isHasEnteredGate = Boolean.FALSE;
        
        if (ucDeliveryService != null 
                && uncontainerizedService.getIsShifting().equals("FALSE")
                && uncontainerizedService.getIsTranshipment().equals("FALSE")) {
            UcGatedeliveryService ucGatedeliveryService = ucGatedeliveryServiceFacadeRemote.findNotDeliveredYetByJobSlip(ucDeliveryService.getJobslip());
            isHasEnteredGate = ucGatedeliveryService != null;

            if (isHasEnteredGate) {
                uncontainerizedService.setTruckLoosing("TRUE");
            }
        }

        uncontainerizedService.setCrane("L");
        uncontainerizedService.setStatus("STV");
        uncontainerizedDetail = uncontainerizedServiceFacadeRemote.findDetailByIdUc(idUC);
    }

    public void handleSelectDischargableContainer(ActionEvent event) {
        int noContainer = (Integer) event.getComponent().getAttributes().get("noContainer");
        serviceContDischarge = serviceContDischargeFacadeRemote.find(noContainer);
//        changeBay(serviceContDischarge.getVBay());
//        changeRow(serviceContDischarge.getVRow());
    }

    public void handleSelectDischargableCancelLoadingContainer(ActionEvent event) {
        String jobslip = (String) event.getComponent().getAttributes().get("idcont");
        cancelLoadingService = cancelLoadingServiceFacadeRemote.find(jobslip);
        ServiceGateDelivery serviceGateDelivery = serviceGateDeliveryFacadeRemote.findNotDeliveredYetByJobSlip(cancelLoadingService.getJobSlip());

        if (serviceGateDelivery != null) {
            cancelLoadingService.setTruckLosing("TRUE");
        }
    }

    public void handleShowDischargableCancelLoadings(ActionEvent event) {
        crane.setStartTime(equipmentFacadeRemote.findByStartTimeData());
        crane.setEndTime(equipmentFacadeRemote.findByEndTimeData());
        dischargableCancelLoadings = cancelLoadingServiceFacadeRemote.findDischargableContainerByNoPpkb(noPPKB);
    }

    public void handleShowDischargableContainers(ActionEvent event) {
        crane.setStartTime(equipmentFacadeRemote.findByStartTimeData());
        crane.setEndTime(equipmentFacadeRemote.findByEndTimeData());
        dischargableContainers = serviceContDischargeFacadeRemote.findServiceContDischargesSelect(noPPKB);
    }

    public void handleShowDischargableShiftContainers(ActionEvent event) {
        crane.setStartTime(equipmentFacadeRemote.findByStartTimeData());
        crane.setEndTime(equipmentFacadeRemote.findByEndTimeData());
        dischargableShiftContainers = serviceShiftingFacadeRemote.findByPpkbDischargeConfirmSelect(noPPKB);
    }

    public void handleShowDischargableUcs(ActionEvent event) {
        crane.setStartTime(equipmentFacadeRemote.findByStartTimeData());
        crane.setEndTime(equipmentFacadeRemote.findByEndTimeData());
        dischargableUcs = uncontainerizedServiceFacadeRemote.findDischargableUcsByNoPpkb(noPPKB);
    }

    public void handleShowServiceVessels(ActionEvent event) {
        serviceVessels = serviceVesselFacadeRemote.findServiceVesselsServicing();
    }

    public void handleSelectNoPpkb(ActionEvent event) {
        noPPKB = (String) event.getComponent().getAttributes().get("noPpkb");
        serviceVessel = serviceVesselFacadeRemote.find(noPPKB);
        vesselDetail = serviceVesselFacadeRemote.findServiceVesselByPpkb(noPPKB);
        vesselCode = vesselDetail[4].toString();
        bayChoices.clear();
        bayChoices = masterVesselProfileFacadeRemote.findBaysByVessel(vesselCode);

        serviceContDischarges = serviceContDischargeFacadeRemote.findServiceContDischargesConfirm(noPPKB);
        uncontainerizedServices = uncontainerizedServiceFacadeRemote.findDischargedUcsByNoPpkb(noPPKB);
        cancelLoadings = cancelLoadingServiceFacadeRemote.findHasDischargedContainerByNoPpkb(noPPKB);
        serviceShiftingList = serviceShiftingFacadeRemote.findByPpkbDischargeConfirmList(noPPKB);
    }

    public void handleRefresh(ActionEvent event) {
        serviceContDischarges = serviceContDischargeFacadeRemote.findServiceContDischargesConfirm(noPPKB);
    }

    public void handleRefreshUc(ActionEvent event) {
        uncontainerizedServices = uncontainerizedServiceFacadeRemote.findDischargedUcsByNoPpkb(noPPKB);
    }

    public void handleRefreshCancelLoading(ActionEvent event) {
        cancelLoadings = cancelLoadingServiceFacadeRemote.findHasDischargedContainerByNoPpkb(noPPKB);
    }

    public void handleRefreshShiftinf(ActionEvent event) {
        serviceShiftingList = serviceShiftingFacadeRemote.findByPpkbDischargeConfirmList(noPPKB);
    }
    //handling button tambah

    public void handleAddContainer() {
        initial();
        this.ket = TYPE_DISCHARGE;
        //untuk mengkondisikan pemilihan bay
        serviceContDischarge.setVBay(Short.parseShort(bayChoices.get(0).toString()));
        changeBay(serviceContDischarge.getVBay());
        changeRow(Short.parseShort(rowChoices.get(0).toString()));
        opsi = "add";
    }

    public void handleAddShiftContainer() {
        initial();
        ket = TYPE_SHIFT;
        opsi = "add";

        serviceShifting.setVBay(Short.parseShort(bayChoices.get(0).toString()));
        changeBay(serviceShifting.getVBay());
        changeRow(Short.parseShort(rowChoices.get(0).toString()));
    }

    //uncontainerized logic
    public void handleAddUncontainerized() {
        uncontainerizedService = new UncontainerizedService();
        uncontainerizedService.setCrane("L");

        crane = new Equipment();
        crane.setMasterEquipment(new MasterEquipment());
        crane.setMasterOperator(new MasterOperator());
        crane.setPlanningVessel(new PlanningVessel());
        
        ht = new Equipment();
        ht.setMasterEquipment(new MasterEquipment());
        ht.setMasterOperator(new MasterOperator());
        ht.setPlanningVessel(new PlanningVessel());

        uncontainerizedDetail = null;
        opsi = "addUC";
    }

    public void onchangeBay(ValueChangeEvent event) {
        Short newBay = (Short) event.getNewValue();
        changeBay(newBay);
    }

    public void onChangeRow(ValueChangeEvent event) {
        Short newRow = (Short) event.getNewValue();
        changeRow(newRow);
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
        Short bay;

        if (ket.equalsIgnoreCase(TYPE_DISCHARGE)) {
            bay = serviceContDischarge.getVBay();
        } else if (ket.equalsIgnoreCase(TYPE_CANCEL_LOADING)) {
            bay = cancelLoadingService.getVesselBay();
        } else {
            bay = serviceShifting.getVBay();
        }

        tierChoices.clear();

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
        } else if (rowBottoms.contains(row)) {
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
        } else if (rowUps.contains(row)) {
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
    }

    //handling crane
    public void onChangeCrane(ValueChangeEvent event) {
        String newCrane = (String) event.getNewValue();
        if (newCrane.equalsIgnoreCase("S")) {
            crane.setMasterEquipment(masterEquipmentFacadeRemote.find("EQ000"));
            crane.setMasterOperator(masterOperatorFacadeRemote.find("0"));
        } else if (newCrane.equalsIgnoreCase("L")) {
            crane.setMasterEquipment(new MasterEquipment());
            crane.setMasterOperator(new MasterOperator());
        }
    }

    public void handleSelectCrane(ActionEvent event) {
        String idCrane = (String) event.getComponent().getAttributes().get("idCrane");
        crane.setMasterEquipment(masterEquipmentFacadeRemote.find(idCrane));
    }

    public void handleSelectHT(ActionEvent event) {
        String idHT = (String) event.getComponent().getAttributes().get("idHT");
        ht.setMasterEquipment(masterEquipmentFacadeRemote.find(idHT));
    }

    public void handleSelectOperatorCrane(ActionEvent event) {
        String idOperatorCrane = (String) event.getComponent().getAttributes().get("idOperatorCrane");
        crane.setMasterOperator(masterOperatorFacadeRemote.find(idOperatorCrane));
    }

    public void handleSelectOperatorHT(ActionEvent event) {
        String idOperatorHT = (String) event.getComponent().getAttributes().get("idOperatorHT");
        ht.setMasterOperator(masterOperatorFacadeRemote.find(idOperatorHT));
    }

    public void handleSelectDischargedShiftContainer(ActionEvent event) {
        int idContainer = (Integer) event.getComponent().getAttributes().get("idCont");

        ket = TYPE_SHIFT;
        opsi = "delete";

        serviceShifting = serviceShiftingFacadeRemote.find(idContainer);
        crane = equipmentFacadeRemote.findByEquipmentActCodeAndOperation(noPPKB, serviceShifting.getContNo(), "STEVEDORING", "SHIFTING-CY");
        ht = equipmentFacadeRemote.findByEquipmentActCodeAndOperation(noPPKB, serviceShifting.getContNo(), "H/T", "SHIFTING-CY");
        changeBay(serviceShifting.getVBay());
        changeRow(serviceShifting.getVRow());
    }

    public void handleSelectDischargedCancelLoadingContainer(ActionEvent event) {
        String jobslip = (String) event.getComponent().getAttributes().get("jobslip");
        opsi = "delete";
        ket = TYPE_CANCEL_LOADING;

        cancelLoadingService = cancelLoadingServiceFacadeRemote.find(jobslip);
        crane = equipmentFacadeRemote.findByEquipmentActCodeAndOperation(noPPKB, cancelLoadingService.getContNo(), "STEVEDORING", "DISCHARGE");

        if (cancelLoadingService.getTruckLosing().equals("FALSE")) {
            ht = equipmentFacadeRemote.findByEquipmentActCodeAndOperation(noPPKB, cancelLoadingService.getContNo(), "H/T", "DISCHARGE");
        }

        changeBay(cancelLoadingService.getVesselBay());
        changeRow(cancelLoadingService.getVesselRow());
    }

    public void handleSelectDischargedContainer(ActionEvent event) {
        int idContainer = (Integer) event.getComponent().getAttributes().get("idCont");

        opsi = "delete";
        ket = TYPE_DISCHARGE;

        serviceContDischarge = serviceContDischargeFacadeRemote.find(idContainer);
        crane = equipmentFacadeRemote.findByEquipmentActCodeAndOperation(noPPKB, serviceContDischarge.getContNo(), "STEVEDORING", "DISCHARGE");
        ht = equipmentFacadeRemote.findByEquipmentActCodeAndOperation(noPPKB, serviceContDischarge.getContNo(), "H/T", "DISCHARGE");
        
        // check if master operator is null
        if (crane.getMasterOperator()==null)
            crane.setMasterOperator(new MasterOperator());
        if (ht.getMasterOperator()==null)
            ht.setMasterOperator(new MasterOperator());
        
//        changeBay(serviceContDischarge.getVBay());
//        changeRow(serviceContDischarge.getVRow());
//        
//        if (!bayChoices.contains(serviceContDischarge.getVBay().intValue())) {
//            bayChoices.add(serviceContDischarge.getVBay().intValue());
//        }
//        
//        if (!rowChoices.contains(serviceContDischarge.getVRow().intValue())) {
//            rowChoices.add(serviceContDischarge.getVRow().intValue());
//        }
//        
//        if (!tierChoices.contains(serviceContDischarge.getVTier().intValue())) {
//            tierChoices.add(serviceContDischarge.getVTier().intValue());
//        }
    }

    public void handleSelectDischargedUncontainerized(ActionEvent event) {
        int idUC = (Integer) event.getComponent().getAttributes().get("idUC");
        uncontainerizedService = uncontainerizedServiceFacadeRemote.find(idUC);
        uncontainerizedDetail = uncontainerizedServiceFacadeRemote.findDetailByIdUc(idUC);
        
        if (uncontainerizedService.getIsShifting().equals("TRUE")) {
            crane = equipmentFacadeRemote.findByPpkbBLNoEquipmentActCodeAndOperation(noPPKB, uncontainerizedService.getBlNo(), "STEVEDORING", "SHIFTING-TOCY");
            ht = equipmentFacadeRemote.findByPpkbBLNoEquipmentActCodeAndOperation(noPPKB, uncontainerizedService.getBlNo(), "H/T", "SHIFTING-TOCY");
        } else if (uncontainerizedService.getIsTranshipment().equals("TRUE")) {
            crane = equipmentFacadeRemote.findByPpkbBLNoEquipmentActCodeAndOperation(noPPKB, uncontainerizedService.getBlNo(), "STEVEDORING", "TRANSDISCUC");
            ht = equipmentFacadeRemote.findByPpkbBLNoEquipmentActCodeAndOperation(noPPKB, uncontainerizedService.getBlNo(), "H/T", "TRANSDISCUC");
        } else {
            UcDeliveryService ucDeliveryService = uncontainerizedService.getUcDeliveryService();

            if (ucDeliveryService != null) {
                UcGatedeliveryService ucGatedeliveryService = ucGatedeliveryServiceFacadeRemote.findNotDeliveredYetByJobSlip(ucDeliveryService.getJobslip());
                isHasEnteredGate = ucGatedeliveryService != null;
            }

            if (uncontainerizedService.getTruckLoosing().equals("TRUE")) {
                ht = new Equipment();
            } else {
                ht = equipmentFacadeRemote.findByPpkbBLNoEquipmentActCodeAndOperation(noPPKB, uncontainerizedService.getBlNo(), "H/T", "DISCHARGEUC");
            }
            
            crane = equipmentFacadeRemote.findByPpkbBLNoEquipmentActCodeAndOperation(noPPKB, uncontainerizedService.getBlNo(), "STEVEDORING", "DISCHARGEUC");
        }

        opsi = "deleteUC";
    }

    public void handleDischargeConfirmShiftContainer(ActionEvent event) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        RequestContext requestContext = RequestContext.getCurrentInstance();
        boolean loggedIn = Boolean.FALSE;

        try {
            dischargeConfirmOperationRemote.handleDischargeConfirmShiftingContainer(serviceShifting, crane, ht);

            if (serviceShifting.getPosition().equals(ServiceShifting.LOCATION_HT_SHIFTING)) {
                loggedIn = Boolean.TRUE;
            }

            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_INFO, "application.save.succeeded");
            //initial();
            resetForm();
            ket = TYPE_SHIFT;
        } catch (TimeSelectionNotValidException ex) {
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_ERROR, "Error", "application.validation.time");
        } catch (Exception ex) {
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_ERROR, "Error", "application.save.failed");
            logger.log(Level.SEVERE, null, ex);
        }

        serviceShiftingList = serviceShiftingFacadeRemote.findByPpkbDischargeConfirmList(noPPKB);
        requestContext.addCallbackParam("loggedIn", loggedIn);
    }

    public void handleDischargeConfirmContainer(ActionEvent event) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        boolean loggedIn = Boolean.FALSE;

        try {
            dischargeConfirmOperationRemote.handleDischargeConfirmContainer(serviceContDischarge, crane, ht);

            if (serviceContDischarge.getPosition().equals(ServiceContDischarge.LOCATION_HT) 
                    || serviceContDischarge.getIsDelivery().equals("TRUE")) {
                loggedIn = true;
            }

            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_INFO, "application.save.succeeded");
            //initial();
            resetForm();
            this.ket = TYPE_DISCHARGE;
        } catch (TimeSelectionNotValidException ex) {
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_ERROR, "Error", "application.validation.time");
        } catch (Exception ex) {
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_ERROR, "Error", "application.save.failed");
            logger.log(Level.SEVERE, null, ex);
        }

        serviceContDischarges = serviceContDischargeFacadeRemote.findServiceContDischargesConfirm(noPPKB);
        RequestContext.getCurrentInstance().addCallbackParam("loggedIn", loggedIn);
    }

    public void handleDischargeConfirmCancelLoadingContainer(ActionEvent event) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        boolean loggedIn = false;

        try {
            dischargeConfirmOperationRemote.handleDischargeConfirmCancelLoadingContainer(cancelLoadingService, crane, ht);

            if (cancelLoadingService.getPosition().equals(CancelLoadingService.AT_HAULAGE_TRUCK)) {
                loggedIn = true;
            }

            handleAddCancelLoadingContainer(event);
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_INFO, "application.save.succeeded");
        } catch (TimeSelectionNotValidException ex) {
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_ERROR, "Error", "application.validation.time");
        } catch (Exception ex) {
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_ERROR, "Error", "application.save.failed");
            logger.log(Level.SEVERE, null, ex);
        }

        cancelLoadings = cancelLoadingServiceFacadeRemote.findHasDischargedContainerByNoPpkb(noPPKB);
        RequestContext.getCurrentInstance().addCallbackParam("loggedIn", loggedIn);
    }

    public void handleDeleteShiftContainer(ActionEvent event) {
        try {
            serviceShifting.setPosition("01");
            serviceShiftingFacadeRemote.edit(serviceShifting);
            equipmentFacadeRemote.remove(crane);
            equipmentFacadeRemote.remove(ht);
            serviceShiftingList = serviceShiftingFacadeRemote.findByPpkbDischargeConfirmList(noPPKB);
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "application.delete.succeeded");
        } catch (EJBException e) {
            e.printStackTrace();
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "application.delete.failed");
        }
    }

    public void handleDeleteContainer(ActionEvent event) {
         if (ket.equalsIgnoreCase(TYPE_CANCEL_LOADING)) {
            cancelLoadingService.setPosisi(CancelLoadingService.AT_VESSEL);
            cancelLoadingService.setIsDischarge("FALSE");
            cancelLoadingService.setIsDelivery("FALSE");
            cancelLoadingService.setTruckLosing("FALSE");

            cancelLoadingServiceFacadeRemote.edit(cancelLoadingService);
            equipmentFacadeRemote.remove(crane);

            if (cancelLoadingService.getTruckLosing().equals("FALSE")) {
                equipmentFacadeRemote.remove(ht);
            }
            cancelLoadings = cancelLoadingServiceFacadeRemote.findHasDischargedContainerByNoPpkb(noPPKB);
        } else if (opsi.equalsIgnoreCase("delete")) {
            serviceContDischarge.setPosition("01");
            serviceContDischargeFacadeRemote.edit(serviceContDischarge);
            equipmentFacadeRemote.remove(crane);
            equipmentFacadeRemote.remove(ht);

            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "application.delete.succeeded");
            serviceContDischarges = serviceContDischargeFacadeRemote.findServiceContDischargesConfirm(noPPKB);
        } else if (opsi.equalsIgnoreCase("deleteUC")) {
            uncontainerizedService.setStatus("PLANNING");
            uncontainerizedService.setCrane("L");
            uncontainerizedService.setIsDelivery("FALSE");
            uncontainerizedServiceFacadeRemote.edit(uncontainerizedService);

            if (uncontainerizedService.getTruckLoosing().equals("FALSE")) {
                equipmentFacadeRemote.remove(ht);
            }

            if (uncontainerizedService.getIsShifting().equals("TRUE")) {
                ucShiftingServiceFacadeRemote.deleteByNoPpkbAndBlNo(uncontainerizedService.getNoPpkb(), uncontainerizedService.getBlNo());
            }
            
            equipmentFacadeRemote.remove(crane);

            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "application.delete.succeeded");
            uncontainerizedServices = uncontainerizedServiceFacadeRemote.findDischargedUcsByNoPpkb(noPPKB);
        }
    }
    
    public void handleDischargeConfirmUncontainerized(ActionEvent event) {
        boolean loggedIn = Boolean.FALSE;
        FacesContext facesContext = FacesContext.getCurrentInstance();
        RequestContext requestContext = RequestContext.getCurrentInstance();

        if (uncontainerizedService.getCrane() == null || crane.getMasterEquipment() == null || crane.getMasterOperator() == null || crane.getStartTime() == null || crane.getEndTime() == null) {
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_WARN, "application.save.failed");
        } else if (crane.getStartTime().after(crane.getEndTime()) || crane.getStartTime().equals(crane.getEndTime())) {
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_WARN, "application.save.failed");
        } else if ((uncontainerizedService.getTruckLoosing().equals("FALSE")
                && ht.getMasterEquipment() == null) 
                || (uncontainerizedService.getTruckLoosing().equals("FALSE") && ht.getMasterOperator() == null)) {
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_WARN, "application.save.failed");
        } else {
            crane.setPlanningVessel(serviceVessel.getPlanningVessel());
            crane.setBlNo(uncontainerizedService.getBlNo());
            crane.setEquipmentActCode("STEVEDORING");

            if (uncontainerizedService.getIsShifting().equals("TRUE")) {
                crane.setOperation("SHIFTING-TOCY");
            } else if (uncontainerizedService.getIsTranshipment().equals("TRUE")) {
                crane.setOperation("TRANSDISCUC");
            } else {
                crane.setOperation("DISCHARGEUC");
            }

            if (uncontainerizedService.getTruckLoosing().equals("TRUE")) {
                Date now = new Date();
                uncontainerizedService.setPlacementDate(now);

                if (isHasEnteredGate) {
                    uncontainerizedService.setIsDelivery("TRUE");
                } else {
                    uncontainerizedService.setStatus("CY");
                }
            } else {
                uncontainerizedService.setIsDelivery("FALSE");

                ht.setPlanningVessel(serviceVessel.getPlanningVessel());
                ht.setBlNo(uncontainerizedService.getBlNo());
                ht.setStartTime(crane.getEndTime());
                ht.setEquipmentActCode("H/T");

                if (uncontainerizedService.getIsShifting().equals("TRUE")) {
                    ht.setOperation("SHIFTING-TOCY");
                } else if (uncontainerizedService.getIsTranshipment().equals("TRUE")) {
                    ht.setOperation("TRANSDISCUC");
                } else {
                    ht.setOperation("DISCHARGEUC");
                }

                equipmentFacadeRemote.edit(ht);
            }

            equipmentFacadeRemote.edit(crane);
            uncontainerizedServiceFacadeRemote.edit(uncontainerizedService);

            if (uncontainerizedService.getIsShifting().equals("TRUE")) {
                UcShiftingService ucShiftingService = ucShiftingServiceFacadeRemote.findByNoPpkbAndBlNo(uncontainerizedService.getNoPpkb(), uncontainerizedService.getBlNo());

                if (ucShiftingService == null) {
                    ucShiftingService = new UcShiftingService();
                }

                ucShiftingService.setNoPpkb(uncontainerizedService.getNoPpkb());
                ucShiftingService.setBlNo(uncontainerizedService.getBlNo());
                ucShiftingService.setOperation(uncontainerizedService.getOperation());
                ucShiftingService.setCrane(uncontainerizedService.getCrane());
                ucShiftingService.setIsPaid("TRUE");
                ucShiftingService.setIsPlanning("TRUE");
                ucShiftingService.setIsLanded("FALSE");
                ucShiftingService.setIsReshipping("FALSE");
                ucShiftingService.setShiftTo(uncontainerizedService.getShiftTo());

                int id = equipmentFacadeRemote.findIdEquipmentByAllBlNo(crane.getMasterEquipment().getEquipCode(), crane.getMasterOperator().getOptCode(), crane.getBlNo(), uncontainerizedService.getNoPpkb(), crane.getStartTime(), crane.getEndTime(), crane.getEquipmentActCode(), crane.getOperation());
                ucShiftingService.setIdEquipment(id);
                ucShiftingServiceFacadeRemote.edit(ucShiftingService);
            }
            
            uncontainerizedServices = uncontainerizedServiceFacadeRemote.findDischargedUcsByNoPpkb(noPPKB);
            
            if (opsi.equalsIgnoreCase("deleteUC")) {
                loggedIn = true;
            }

            handleAddUncontainerized();
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_INFO, "application.save.succeeded");
        }
        
        requestContext.addCallbackParam("loggedIn", loggedIn);
    }

    public void handleAddCancelLoadingContainer(ActionEvent event) {
        //initial();
        resetForm();
        ket = TYPE_CANCEL_LOADING;
        opsi = "add";

        cancelLoadingService = new CancelLoadingService();
        masterYardCoordinat = new MasterYardCoordinat();
        cancelLoadingService.setVesselBay(Short.parseShort(bayChoices.get(0).toString()));

        changeBay(cancelLoadingService.getVesselBay());
        changeRow(Short.parseShort(rowChoices.get(0).toString()));
    }

    public void onChangeEventTruck(ValueChangeEvent event) {
        boolean truck = (Boolean) event.getNewValue();
        if (truck == Boolean.FALSE) {
            this.disableYard = Boolean.FALSE;
        } else {
            this.disableYard = Boolean.TRUE;
        }
    }

    public void onChangeEvent(ValueChangeEvent event) {
        String newItem = (String) event.getNewValue();
        setMasterYard(masterYardFacadeRemote.findMasterYardByBlock(newItem));
        Integer slot = (Integer) getMasterYard()[0];
        Integer row = (Integer) getMasterYard()[1];
        Integer tier = (Integer) getMasterYard()[2];
        getSlots().clear();
        getRows().clear();
        getTiers().clear();
        this.setCounterSlot(slot);
        this.setCounterRow(row);
        this.setCounterTier(tier);
    }

    public void handleDownloadReport(ActionEvent event) {
        try {
            RequestContext context = RequestContext.getCurrentInstance();

            context.addCallbackParam("doPrint", true); //harus ada, buat ngeflag di izinkan ngeprint ato ngga
            String url = "../../../DischargeReport.pdf?" +
                    "noPpkb=" + serviceVessel.getNoPpkb() + "&" +
                    "foreman=" + foreman + "&" +
                    "supervisiOperasi=" + supervisiOperasi + "&" +
                    "username=" + username;
            context.addCallbackParam("url", StringEscapeUtils.escapeHtml3(url));
        } catch (RuntimeException re) {
            logger.log(Level.SEVERE, "exception caught when call handleDownloadReport", re);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="properties">

    /**
     * @return the bayChoices
     */
    public List<Integer> getBayChoices() {
        return bayChoices;
    }

    /**
     * @return the rowChoices
     */
    public List<Integer> getRowChoices() {
        return rowChoices;
    }

    /**
     * @return the tierChoices
     */
    public List<Integer> getTierChoices() {
        return tierChoices;
    }

    /**
     * @return the masterOperators
     */
    public List<MasterOperator> getMasterOperators() {
        return masterOperators;
    }

    /**
     * @return the masterCranes
     */
    public List<Object[]> getMasterCranes() {
        return masterCranes;
    }

    /**
     * @return the masterHTs
     */
    public List<Object[]> getMasterHTs() {
        return masterHTs;
    }

    /**
     * @return the crane
     */
    public Equipment getCrane() {
        return crane;
    }

    /**
     * @return the ht
     */
    public Equipment getHt() {
        return ht;
    }

    //untuk menangani edit
    /**
     * @return the opsi
     */
    public String getOpsi() {
        return opsi;
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
     * @return the uncontainerizedServiceSelect
     */
    public List<Object[]> getDischargableUcs() {
        return dischargableUcs;
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

    /**
     * @return the uncontainerizedDetail
     */
    public Object[] getUncontainerizedDetail() {
        return uncontainerizedDetail;
    }

    public List<Object[]> getCancelLoadings() {
        return cancelLoadings;
    }

    public void setCancelLoadings(List<Object[]> cancelLoadings) {
        this.cancelLoadings = cancelLoadings;
    }

    public List<Object[]> getDischargableCancelLoadings() {
        return dischargableCancelLoadings;
    }

    public CancelLoadingService getCancelLoadingService() {
        return cancelLoadingService;
    }

    public void setCancelLoadingService(CancelLoadingService cancelLoadingService) {
        this.cancelLoadingService = cancelLoadingService;
    }

    public List<Object[]> getMasterYards() {
        return masterYards;
    }

    public void setMasterYards(List<Object[]> masterYards) {
        this.masterYards = masterYards;
    }

    public Integer getCounterRow() {
        return counterRow;
    }

    public void setCounterRow(Integer counterRow) {
        this.counterRow = counterRow;
    }

    public Integer getCounterSlot() {
        return counterSlot;
    }

    public void setCounterSlot(Integer counterSlot) {
        this.counterSlot = counterSlot;
    }

    public Integer getCounterTier() {
        return counterTier;
    }

    public void setCounterTier(Integer counterTier) {
        this.counterTier = counterTier;
    }

    public Object[] getMasterYard() {
        return masterYard;
    }

    public ServiceShifting getServiceShifting() {
        return serviceShifting;
    }

    public void setServiceShifting(ServiceShifting serviceShifting) {
        this.serviceShifting = serviceShifting;
    }

    public void setMasterYard(Object[] masterYard) {
        this.masterYard = masterYard;
    }

    public Map<Integer, Integer> getRows() {
        for (int i = 1; i <= counterRow; i++) {
            rows.put(i, i);
        }
        return rows;
    }

    public void setRows(Map<Integer, Integer> rows) {
        this.rows = rows;
    }

    public Map<Integer, Integer> getSlots() {
        for (int i = 1; i <= counterSlot; i++) {
            slots.put(i, i);
        }
        return slots;
    }

    public void setSlots(Map<Integer, Integer> slots) {
        this.slots = slots;
    }

    public Map<Integer, Integer> getTiers() {
        for (int i = 1; i <= counterTier; i++) {
            tiers.put(i, i);
        }
        return tiers;
    }

    public void setTiers(Map<Integer, Integer> tiers) {
        this.tiers = tiers;
    }

    public boolean isDisableYard() {
        return disableYard;
    }

    public void setDisableYard(boolean disableYard) {
        this.disableYard = disableYard;
    }

    public MasterYardCoordinat getMasterYardCoordinat() {
        return masterYardCoordinat;
    }

    public void setMasterYardCoordinat(MasterYardCoordinat masterYardCoordinat) {
        this.masterYardCoordinat = masterYardCoordinat;
    }

    public List<Object[]> getServiceShiftingList() {
        return serviceShiftingList;
    }

    public void setServiceShiftingList(List<Object[]> serviceShiftingList) {
        this.serviceShiftingList = serviceShiftingList;
    }

    public List<Object[]> getDischargableShiftContainers() {
        return dischargableShiftContainers;
    }

    public String getKet() {
        return ket;
    }

    public void setKet(String ket) {
        this.ket = ket;
    }

    /**
     * @return the serviceVessels
     */
    public List<Object[]> getServiceVessels() {
        return serviceVessels;
    }

    /**
     * @return the vesselDetail
     */
    public Object[] getVesselDetail() {
        return vesselDetail;
    }

    /**
     * @return the ServiceContDischarge
     */
    public ServiceContDischarge getServiceContDischarge() {
        return serviceContDischarge;
    }

    /**
     * @param crane the ServiceContDischarge to set
     */
    public void setServiceContDischarge(ServiceContDischarge serviceContDischarge) {
        this.serviceContDischarge = serviceContDischarge;
    }

    /**
     * @return the serviceContDischarges
     */
    public List<Object[]> getServiceContDischarges() {
        return serviceContDischarges;
    }

    public List<Object[]> getDischargableContainers() {
        return dischargableContainers;
    }

    public boolean isIsHasEnteredGate() {
        return isHasEnteredGate;
    }

    public String getForeman() {
        return foreman;
    }

    public void setForeman(String foreman) {
        this.foreman = foreman;
    }

    public String getSupervisiOperasi() {
        return supervisiOperasi;
    }

    public void setSupervisiOperasi(String supervisiOperasi) {
        this.supervisiOperasi = supervisiOperasi;
    }// </editor-fold>
}