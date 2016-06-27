/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.bean;

import com.pelindo.ebtos.ejb.facade.remote.EquipmentFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterEquipmentFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterOperatorFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterYardCoordinatFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterYardFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningContLoadFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningContReceivingFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningVesselFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ReceivingServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceReceivingFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceVesselFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.UcReceivingServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.UncontainerizedServiceFacadeRemote;
import com.pelindo.ebtos.ejb.remote.ReceivingOperationRemote;
import com.pelindo.ebtos.exception.ContainerNotMovableException;
import com.pelindo.ebtos.exception.LocationNotAvailableException;
import com.pelindo.ebtos.exception.TimeSelectionNotValidException;
import com.pelindo.ebtos.model.db.Equipment;
import com.pelindo.ebtos.model.db.PlanningContLoad;
import com.pelindo.ebtos.model.db.PlanningContReceiving;
import com.pelindo.ebtos.model.db.PlanningUcReceiving;
import com.pelindo.ebtos.model.db.PlanningVessel;
import com.pelindo.ebtos.model.db.PreserviceVessel;
import com.pelindo.ebtos.model.db.ReceivingService;
import com.pelindo.ebtos.model.db.ServiceReceiving;
import com.pelindo.ebtos.model.db.ServiceReceivingUc;
import com.pelindo.ebtos.model.db.ServiceVessel;
import com.pelindo.ebtos.model.db.UcReceivingService;
import com.pelindo.ebtos.model.db.UncontainerizedService;
import com.pelindo.ebtos.model.db.master.MasterCommodity;
import com.pelindo.ebtos.model.db.master.MasterContainerType;
import com.pelindo.ebtos.model.db.master.MasterEquipment;
import com.pelindo.ebtos.model.db.master.MasterOperator;
import com.pelindo.ebtos.model.db.master.MasterYard;
import com.pelindo.ebtos.model.db.master.MasterYardCoordinat;
import com.pelindo.ebtos.util.GrossConverter;
import com.qtasnim.jsf.FacesHelper;
import com.qtasnim.util.CyValidation;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
@ManagedBean(name = "receivingConfirm")
@ViewScoped
public class ReceivingConfirm implements Serializable {
    private static final Logger logger = Logger.getLogger(ReceivingConfirm.class.getName());

    @EJB
    private UncontainerizedServiceFacadeRemote uncontainerizedServiceFacadeRemote;
    @EJB
    private UcReceivingServiceFacadeRemote ucReceivingServiceFacadeRemote;
    @EJB
    private PlanningVesselFacadeRemote planningVesselFacade;
    @EJB
    private PlanningContLoadFacadeRemote planningContLoadFacadeRemote;
    @EJB
    private PlanningContReceivingFacadeRemote planningContReceivingFacadeRemote;
    @EJB
    private MasterYardFacadeRemote masterYardFacadeRemote;
    @EJB
    private MasterEquipmentFacadeRemote masterEquipmentFacadeRemote;
    @EJB
    private EquipmentFacadeRemote equipmentFacadeRemote;
    @EJB
    private ServiceReceivingFacadeRemote serviceReceivingFacadeRemote;
    @EJB
    private ServiceVesselFacadeRemote serviceVesselFacadeRemote;
    @EJB
    private MasterOperatorFacadeRemote masterOperatorFacadeRemote;
    @EJB
    private MasterYardCoordinatFacadeRemote masterYardCoordinatFacadeRemote;
    @EJB
    private ReceivingServiceFacadeRemote receivingServiceFacadeRemote;
    @EJB
    private ReceivingOperationRemote receivingOperationRemote;
    
    private PreserviceVessel preserviceVessel;
    private ServiceReceiving serviceReceiving;
    private List<PreserviceVessel> preserviceVessels;
    private List<Object[]> serviceReceivings;
    private List<ServiceReceivingUc> serviceReceivingUcs;
    private ServiceReceivingUc serviceReceivingUc;
    private List<MasterYard> masterYards;
    private Equipment equipment;
    private List<Object[]> masterEquipments;
    private PlanningUcReceiving planningUcReceiving;
    private MasterContainerType masterContainerType;
    private String noPpkb;
    private List<MasterOperator> masterOperators;
    private MasterEquipment masterEquipment;
    private MasterOperator masterOperator;
    private PlanningVessel planningVessell;
    /** Creates a new instance of ReceivingConfirm */
    private PlanningContReceiving planningContReceiving;
    private Object[] planningVessel;
    private List<Object[]> planningContReceivings;
    private List<Object[]> planningVessels;
    private List<Object[]> planningContreceivingList;
    private Object[] masterYard2;
    private Object[] masterYardCordinat;
    private Map<Integer, Integer> slots;
    private Map<Integer, Integer> rows;
    private Map<Integer, Integer> tiers;
    private Integer counterSlot, counterRow, counterTier;
    private MasterYard masterYard;
    private boolean disableOptCrane;
    private boolean diasbleEquipment;
    //uncontainerized
    private ServiceVessel vessel;
    private List<Object[]> uncontainerizedServiceSelect;
    private List<Object[]> uncontainerizedServices;
    private UncontainerizedService uncontainerizedService;
    private UcReceivingService ucReceivingService;
    private Object[] uncontainerizedDetail;
    private String opsi;
    private PlanningContLoad planningContLoad;

    public ReceivingConfirm() {}

    @PostConstruct
    private void construct() {
        preserviceVessel = new PreserviceVessel();
        serviceReceiving = new ServiceReceiving();
        equipment = new Equipment();
        equipment.setMasterEquipment(new MasterEquipment());
        masterContainerType = new MasterContainerType();
        planningUcReceiving = new PlanningUcReceiving();
        slots = new LinkedHashMap<Integer, Integer>();
        rows = new LinkedHashMap<Integer, Integer>();
        tiers = new LinkedHashMap<Integer, Integer>();
        counterSlot = 100;
        counterRow = 100;
        counterTier = 5;
        masterEquipment = new MasterEquipment();
        masterYard = new MasterYard();
        masterOperator = new MasterOperator();
        equipment.setMasterOperator(new MasterOperator());
        equipment.setMasterEquipment(new MasterEquipment());
        serviceReceiving.setMasterYard(new MasterYard());
        planningContReceiving = new PlanningContReceiving();
        planningVessel = new Object[1];
        disableOptCrane = true;
        diasbleEquipment = true;
        planningContLoad = new PlanningContLoad();
        masterYards = masterYardFacadeRemote.findAll();
    }

    public List<Object[]> getPlanningVessels() {
        return planningVessels;
    }

    public boolean getVisible() {
        if (getPlanningVessel()[0] == null) {
            return true;
        }
        return false;
    }

    public List<Object[]> getPlanningContreceivingList() {
        return planningContreceivingList;
    }

    public void handleRefreshUC(ActionEvent actionEvent) {
        uncontainerizedServices = uncontainerizedServiceFacadeRemote.findForLiftOFF(noPpkb,"LOAD");
    }

    public void handleRefresh(ActionEvent event) {
        serviceReceivings = serviceReceivingFacadeRemote.findServiceReceivingByPpkbList2(getNoPpkb());
    }

    public void delete(ActionEvent event) {
        final FacesContext facesContext = FacesContext.getCurrentInstance();
        
        try {
            receivingOperationRemote.handleCancelReceivingConfirm(serviceReceiving, planningContReceiving, equipment);
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.delete.succeeded");
        } catch (ContainerNotMovableException ex) {
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_ERROR, "Error", "application.validation.cant_move_container");
        } catch (Exception ex) {
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_ERROR, "Error", "application.delete.failed");
            logger.log(Level.SEVERE, null, ex);
        }

        serviceReceivings = serviceReceivingFacadeRemote.findServiceReceivingByPpkbList2(getNoPpkb());
    }

    public void handleSelectHT(ActionEvent event) {
        String idHT = (String) event.getComponent().getAttributes().get("idHT");
        equipment.setMasterEquipment(masterEquipmentFacadeRemote.find(idHT));
        disableOptCrane = false;
    }

    public void handleSelectOperatorCrane(ActionEvent event) {
        String idOperatorCrane = (String) event.getComponent().getAttributes().get("idOperatorCrane");
        equipment.setMasterOperator(masterOperatorFacadeRemote.find(idOperatorCrane));
    }

    public void handleAdd(ActionEvent event) {
        if (!masterYards.isEmpty()) {
            changeBlock(masterYards.get(0).getBlock());
        }

        this.clearSave();
    }

    public void handleShowPlanningContReceivingList(ActionEvent event) {
        planningContreceivingList = planningContReceivingFacadeRemote.findReceivingConfirm(getNoPpkb());
    }
    
    //penambahan pencarian list no container by ade chelsea tanggal 24 maret 2014
    public void handleShowPlanningContReceivingList2(ActionEvent event) {
        
        equipment.setStartTime(equipmentFacadeRemote.findByStartTimeData());       
        equipment.setEndTime(equipmentFacadeRemote.findByEndTimeData());
        planningContreceivingList = planningContReceivingFacadeRemote.findReceivingConfirm2();
    }

    public void handleShowMasterEquipments(ActionEvent event) {
        
        //SimpleDateFormat ft = new SimpleDateFormat("dd/MM/yyyy hh:mm");
       
//    String DATE_FORMAT_NOW = "dd/MM/yyyy hh:mm";
//    Date date = new Date();
//    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
//    String stringDate = sdf.format(date );
//    try{
//        //equipment = new Equipment();
//        Date date2 = sdf.parse(stringDate);
//        equipment.setStartTime(date2);
//    }catch(ParseException e){
//     //Exception handling
//    }catch(Exception e){
//     //handle exception
//    }
       //equipment.setStartTime(date);
        

        masterEquipments = masterEquipmentFacadeRemote.findTangoForView();
        
        
    }

    public void handleShowMasterOperators(ActionEvent event) {
        masterOperators = masterOperatorFacadeRemote.findAll();
    }

    public void handleShowPlanningVessels(ActionEvent event) {
        planningVessels = planningVesselFacade.findPlanningVesselsReceivingConfirm();
    }

    public void handleShowUncontainerizedServices(ActionEvent event) {
        uncontainerizedServiceSelect = uncontainerizedServiceFacadeRemote.findReceivableUcsByNoPpkb(noPpkb);
    }

    public void clearSave() {
        preserviceVessel = new PreserviceVessel();
        serviceReceiving = new ServiceReceiving();
        equipment = new Equipment();
        equipment.setMasterEquipment(new MasterEquipment());
        masterContainerType = new MasterContainerType();
        planningUcReceiving = new PlanningUcReceiving();
        masterEquipment = new MasterEquipment();
        masterYard = new MasterYard();
        masterOperator = new MasterOperator();
        equipment.setMasterOperator(new MasterOperator());
        equipment.setMasterEquipment(new MasterEquipment());
        serviceReceiving.setMasterYard(new MasterYard());
        planningContReceiving = new PlanningContReceiving();

        planningContLoad = new PlanningContLoad();
        planningContLoad.setMasterCommodity(new MasterCommodity());
        planningContLoad.setMasterContainerType(new MasterContainerType());
        planningContLoad.setMasterYard(new MasterYard());
        planningContLoad.setPlanningVessel(new PlanningVessel());
    }
    
    public void clearResetSave() {
        preserviceVessel = new PreserviceVessel();
        serviceReceiving = new ServiceReceiving();
        //equipment = new Equipment();
        //equipment.setMasterEquipment(new MasterEquipment());
        masterContainerType = new MasterContainerType();
        planningUcReceiving = new PlanningUcReceiving();
        //masterEquipment = new MasterEquipment();
        masterYard = new MasterYard();
        //masterOperator = new MasterOperator();
        //equipment.setMasterOperator(new MasterOperator());
        //equipment.setMasterEquipment(new MasterEquipment());
        serviceReceiving.setMasterYard(new MasterYard());
        planningContReceiving = new PlanningContReceiving();

        planningContLoad = new PlanningContLoad();
        planningContLoad.setMasterCommodity(new MasterCommodity());
        planningContLoad.setMasterContainerType(new MasterContainerType());
        planningContLoad.setMasterYard(new MasterYard());
        planningContLoad.setPlanningVessel(new PlanningVessel());
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

    public void handleEditDeleteButton(ActionEvent event) {
        Integer id = (Integer) event.getComponent().getAttributes().get("idCont");
        serviceReceiving = serviceReceivingFacadeRemote.find(id);
        planningContReceiving = planningContReceivingFacadeRemote.findByNoPpkbAndContNo(getNoPpkb(), serviceReceiving.getContNo());
        equipment = equipmentFacadeRemote.findByEquipmentActCodeAndOperationReceiving(getNoPpkb(), serviceReceiving.getContNo(), "RECEIVING", "LOAD");
        
        if (!masterYards.isEmpty() && serviceReceiving.getMasterYard().getBlock() != null) {
            changeBlock(serviceReceiving.getMasterYard().getBlock());
        }
    }

    public void handleSubmit(ActionEvent event) {
        final RequestContext requestContext = RequestContext.getCurrentInstance();
        final FacesContext facesContext = FacesContext.getCurrentInstance();
        boolean isValid = false;
        
        try {
            receivingOperationRemote.handleReceivingConfirm(serviceReceiving, planningContReceiving, equipment);

            if (!planningContReceiving.getPosition().equals("01")) {
                isValid = true;
            }
            
            clearResetSave();
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
        } catch (TimeSelectionNotValidException ex) {
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_ERROR, "Error", "application.validation.time");
        } catch (LocationNotAvailableException ex) {
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_ERROR, "Error", "application.validation.location_not_available");
        } catch (Exception ex) {
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_ERROR, "Error", "application.loading.error");
            logger.log(Level.SEVERE, null, ex);
        }

        serviceReceivings = serviceReceivingFacadeRemote.findServiceReceivingByPpkbList2(getNoPpkb());
        requestContext.addCallbackParam("loggedIn", isValid);
    }

    public void handleSelectNoCont(ActionEvent event) {
        int contNo = (Integer) event.getComponent().getAttributes().get("cont_no");
        planningContReceiving = planningContReceivingFacadeRemote.find(contNo);
//        equipment = equipmentFacadeRemote.findByEquipmentActCodeAndOperationReceiving(getNoPpkb(), serviceReceiving.getContNo(), "RECEIVING", "LOAD");
        Object[] recommendedLocation = planningContReceivingFacadeRemote.findReceivableContainerWithSuggestedLocation(planningContReceiving.getContNo());

        if (recommendedLocation != null && !masterYards.isEmpty() && recommendedLocation[3] != null) {
            String block = (String) recommendedLocation[3];
            Short slot = ((Integer) recommendedLocation[4]).shortValue();
            Short row = ((Integer) recommendedLocation[5]).shortValue();
            Short tier = ((Integer) recommendedLocation[6]).shortValue();

            masterYard = masterYardFacadeRemote.find(block);
            serviceReceiving = new ServiceReceiving();
            serviceReceiving.setMasterYard(masterYard);
            serviceReceiving.setYSlot(slot);
            serviceReceiving.setYRow(row);
            serviceReceiving.setYTier(tier);

            changeBlock(block);
        }
    }

    public void handleOnSelect(ActionEvent event) {
        setNoPpkb((String) event.getComponent().getAttributes().get("noPpkb"));
        setPlanningVessel(planningVesselFacade.findPlanningVesselByPpkb(noPpkb));
        serviceReceivings = serviceReceivingFacadeRemote.findServiceReceivingByPpkbList2(getNoPpkb());
        //uncontainerized
        vessel = serviceVesselFacadeRemote.find(noPpkb);
        uncontainerizedServices = uncontainerizedServiceFacadeRemote.findForLiftOFF(noPpkb, "LOAD");
    }

    /**
     * @return the preserviceVessel
     */
    public PreserviceVessel getPreserviceVessel() {
        return preserviceVessel;
    }

    /**
     * @param preserviceVessel the preserviceVessel to set
     */
    public void setPreserviceVessel(PreserviceVessel preserviceVessel) {
        this.preserviceVessel = preserviceVessel;
    }

    /**
     * @return the serviceReceiving
     */
    public ServiceReceiving getServiceReceiving() {
        return serviceReceiving;
    }

    /**
     * @param serviceReceiving the serviceReceiving to set
     */
    public void setServiceReceiving(ServiceReceiving serviceReceiving) {
        this.serviceReceiving = serviceReceiving;
    }

    /**
     * @return the preserviceVessels
     */
    public List<PreserviceVessel> getPreserviceVessels() {
        return preserviceVessels;
    }

    /**
     * @param preserviceVessels the preserviceVessels to set
     */
    public void setPreserviceVessels(List<PreserviceVessel> preserviceVessels) {
        this.preserviceVessels = preserviceVessels;
    }

    /**
     * @return the serviceReceivings
     */
    public List<Object[]> getServiceReceivings() {
        return serviceReceivings;
    }

    /**
     * @param serviceReceivings the serviceReceivings to set
     */
    public void setServiceReceivings(List<Object[]> serviceReceivings) {
        this.serviceReceivings = serviceReceivings;
    }

    /**
     * @return the serviceReceivingUcs
     */
    public List<ServiceReceivingUc> getServiceReceivingUcs() {
        return serviceReceivingUcs;
    }

    /**
     * @param serviceReceivingUcs the serviceReceivingUcs to set
     */
    public void setServiceReceivingUcs(List<ServiceReceivingUc> serviceReceivingUcs) {
        this.serviceReceivingUcs = serviceReceivingUcs;
    }

    /**
     * @return the serviceReceivingUc
     */
    public ServiceReceivingUc getServiceReceivingUc() {
        return serviceReceivingUc;
    }

    /**
     * @param serviceReceivingUc the serviceReceivingUc to set
     */
    public void setServiceReceivingUc(ServiceReceivingUc serviceReceivingUc) {
        this.serviceReceivingUc = serviceReceivingUc;
    }

    /**
     * @return the masterYards
     */
    public List<MasterYard> getMasterYards() {
        return masterYards;
    }

    /**
     * @param masterYards the masterYards to set
     */
    public void setMasterYards(List<MasterYard> masterYards) {
        this.masterYards = masterYards;
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
     * @return the masterEquipments
     */
    public List<Object[]> getMasterEquipments() {
        return masterEquipments;
    }

    /**
     * @param masterEquipments the masterEquipments to set
     */
    public void setMasterEquipments(List<Object[]> masterEquipments) {
        this.masterEquipments = masterEquipments;
    }

    /**
     * @return the planningUcReceiving
     */
    public PlanningUcReceiving getPlanningUcReceiving() {
        return planningUcReceiving;
    }

    /**
     * @param planningUcReceiving the planningUcReceiving to set
     */
    public void setPlanningUcReceiving(PlanningUcReceiving planningUcReceiving) {
        this.planningUcReceiving = planningUcReceiving;
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
     * @return the planningContReceivings
     */
    public List<Object[]> getPlanningContReceivings() {
        return planningContReceivings;
    }

    /**
     * @param planningContReceivings the planningContReceivings to set
     */
    public void setPlanningContReceivings(List<Object[]> planningContReceivings) {
        this.planningContReceivings = planningContReceivings;
    }

    /**
     * @return the planningContReceiving
     */
    public PlanningContReceiving getPlanningContReceiving() {
        return planningContReceiving;
    }

    /**
     * @param planningContReceiving the planningContReceiving to set
     */
    public void setPlanningContReceiving(PlanningContReceiving planningContReceiving) {
        this.planningContReceiving = planningContReceiving;
    }

    /**
     * @return the masterYard2
     */
    public MasterYard getMasterYard() {
        return masterYard;
    }

    /**
     * @param masterYard2 the masterYard2 to set
     */
    public void setMasterYard(MasterYard masterYard2) {
        this.masterYard = masterYard2;
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
     * @return the no_ppkb
     */
    public String getNoPpkb() {
        return noPpkb;
    }

    /**
     * @param noPpkb the no_ppkb to set
     */
    public void setNoPpkb(String noPpkb) {
        this.noPpkb = noPpkb;
    }

    /**
     * @return the masterOperators
     */
    public List<MasterOperator> getMasterOperators() {
        return masterOperators;
    }

    /**
     * @param masterOperators the masterOperators to set
     */
    public void setMasterOperators(List<MasterOperator> masterOperators) {
        this.masterOperators = masterOperators;
    }

    /**
     * @return the masterEquipment
     */
    public MasterEquipment getMasterEquipment() {
        return masterEquipment;
    }

    /**
     * @param masterEquipment the masterEquipment to set
     */
    public void setMasterEquipment(MasterEquipment masterEquipment) {
        this.masterEquipment = masterEquipment;
    }

    /**
     * @return the masterOperator
     */
    public MasterOperator getMasterOperator() {
        return masterOperator;
    }

    /**
     * @param masterOperator the masterOperator to set
     */
    public void setMasterOperator(MasterOperator masterOperator) {
        this.masterOperator = masterOperator;
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

    public void onChangeEvent(ValueChangeEvent event) {
        String newItem = (String) event.getNewValue();
        changeBlock(newItem);
    }

    private void changeBlock(String block) {
        setMasterYard2(masterYardFacadeRemote.findMasterYardByBlock(block));
        Integer slot = (Integer) getMasterYard2()[0];
        Integer row = (Integer) getMasterYard2()[1];
        Integer tier = (Integer) getMasterYard2()[2];
        getSlots().clear();
        getRows().clear();
        getTiers().clear();
        this.setCounterSlot(slot);
        this.setCounterRow(row);
        this.setCounterTier(tier);
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
     * @return the slots
     */
    public Map<Integer, Integer> getSlots() {
        for (int i = 1; i <= counterSlot; i++) {
            slots.put(i, i);
        }
        return slots;
    }

    /**
     * @param slots the slots to set
     */
    public void setSlots(Map<Integer, Integer> slots) {
        this.slots = slots;
    }

    /**
     * @return the rows
     */
    public Map<Integer, Integer> getRows() {
        for (int i = 1; i <= counterRow; i++) {
            rows.put(i, i);
        }
        return rows;
    }

    /**
     * @param rows the rows to set
     */
    public void setRows(Map<Integer, Integer> rows) {
        this.rows = rows;
    }

    /**
     * @return the tiers
     */
    public Map<Integer, Integer> getTiers() {
        for (int i = 1; i <= counterTier; i++) {
            tiers.put(i, i);
        }
        return tiers;
    }

    /**
     * @param tiers the tiers to set
     */
    public void setTiers(Map<Integer, Integer> tiers) {
        this.tiers = tiers;
    }

    /**
     * @return the counterSlot
     */
    public Integer getCounterSlot() {
        return counterSlot;
    }

    /**
     * @param counterSlot the counterSlot to set
     */
    public void setCounterSlot(Integer counterSlot) {
        this.counterSlot = counterSlot;
    }

    /**
     * @return the counterRow
     */
    public Integer getCounterRow() {
        return counterRow;
    }

    /**
     * @param counterRow the counterRow to set
     */
    public void setCounterRow(Integer counterRow) {
        this.counterRow = counterRow;
    }

    /**
     * @return the counterTier
     */
    public Integer getCounterTier() {
        return counterTier;
    }

    /**
     * @param counterTier the counterTier to set
     */
    public void setCounterTier(Integer counterTier) {
        this.counterTier = counterTier;
    }

    /**
     * @return the disableOptCrane
     */
    public boolean isDisableOptCrane() {
        return disableOptCrane;
    }

    /**
     * @param disableOptCrane the disableOptCrane to set
     */
    public void setDisableOptCrane(boolean disableOptCrane) {
        this.disableOptCrane = disableOptCrane;
    }

    /**
     * @return the diasbleEquipment
     */
    public boolean isDiasbleEquipment() {
        return diasbleEquipment;
    }

    /**
     * @param diasbleEquipment the diasbleEquipment to set
     */
    public void setDiasbleEquipment(boolean diasbleEquipment) {
        this.diasbleEquipment = diasbleEquipment;
    }

    /**
     * @return the masterYardCordinat
     */
    public Object[] getMasterYardCordinat() {
        return masterYardCordinat;
    }

    /**
     * @param masterYardCordinat the masterYardCordinat to set
     */
    public void setMasterYardCordinat(Object[] masterYardCordinat) {
        this.masterYardCordinat = masterYardCordinat;
    }

    /**
     * @return the uncontainerizedServiceSelect
     */
    public List<Object[]> getUncontainerizedServiceSelect() {
        return uncontainerizedServiceSelect;
    }

    /**
     * @return the uncontainerizedServices
     */
    public List<Object[]> getUncontainerizedServices() {
        return uncontainerizedServices;
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

    //uncontainerized logic
    public void handleAddUncontainerized() {
        uncontainerizedService = new UncontainerizedService();
        //inisialisasi tango
        equipment = new Equipment();
        equipment.setMasterEquipment(new MasterEquipment());
        equipment.setMasterOperator(new MasterOperator());
        equipment.setPlanningVessel(new PlanningVessel());
        uncontainerizedDetail = null;
        opsi = "add";
    }

    public void handleEditDeleteUncontainerized(ActionEvent event) {
        int idUC = (Integer) event.getComponent().getAttributes().get("idUC");
        setUncontainerizedService(uncontainerizedServiceFacadeRemote.find(idUC));
        uncontainerizedDetail = uncontainerizedServiceFacadeRemote.findDetailByIdUc(idUC);
        int idTango = equipmentFacadeRemote.findIdByBLno(noPpkb, getUncontainerizedService().getBlNo(), "RECEIVING", "LOADUC");
        equipment = equipmentFacadeRemote.find(idTango);
        opsi = "edit";
    }

    public void handleSelectUncontainerized(ActionEvent event) {
        int idUC = (Integer) event.getComponent().getAttributes().get("idSelect");
        setUncontainerizedService(uncontainerizedServiceFacadeRemote.find(idUC));
        getUncontainerizedService().setStatus("CY");
        uncontainerizedDetail = uncontainerizedServiceFacadeRemote.findDetailByIdUc(idUC);
        diasbleEquipment = false;
    }

    public void saveUncontainerized() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        RequestContext requestContext = RequestContext.getCurrentInstance();
        boolean loggedIn = false;

        if (getUncontainerizedService().getBlNo() == null || equipment.getMasterEquipment() == null || equipment.getMasterOperator() == null || equipment.getStartTime() == null || equipment.getEndTime() == null) {
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_WARN, "Info", "application.save.failed");
        } else if (equipment.getStartTime().after(equipment.getEndTime()) || equipment.getStartTime().equals(equipment.getEndTime())) {
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_WARN, "Info", "application.validation.time");
        } else {
            uncontainerizedServiceFacadeRemote.edit(getUncontainerizedService());

            equipment.setPlanningVessel(vessel.getPlanningVessel());
            equipment.setBlNo(getUncontainerizedService().getBlNo());
            equipment.setEquipmentActCode("RECEIVING");
            equipment.setOperation("LOADUC");
            equipmentFacadeRemote.edit(equipment);

            if (uncontainerizedService.getUcReceivingService() != null) {
                ucReceivingService = uncontainerizedService.getUcReceivingService();
                ucReceivingService.setReceivingDate(equipment.getEndTime());
                ucReceivingServiceFacadeRemote.edit(ucReceivingService);
            }
            
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
            handleAddUncontainerized();
            loggedIn = true;
        }
        
        requestContext.addCallbackParam("loggedIn", loggedIn);
        uncontainerizedServices = uncontainerizedServiceFacadeRemote.findForLiftOFF(noPpkb,"LOAD");
    }

    public void deleteUC() {
        uncontainerizedService.setStatus("PLANNING");
        uncontainerizedServiceFacadeRemote.edit(uncontainerizedService);
        equipmentFacadeRemote.remove(equipment);
        List<Integer> idUCs = ucReceivingServiceFacadeRemote.findJobslipByIdUC(uncontainerizedService.getIdUc());
        if (!idUCs.isEmpty()) {
            ucReceivingService = ucReceivingServiceFacadeRemote.find(idUCs.get(0));
            ucReceivingService.setReceivingDate(null);
            ucReceivingServiceFacadeRemote.edit(ucReceivingService);
        }
        FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.delete.succeeded");

        uncontainerizedServices = uncontainerizedServiceFacadeRemote.findForLiftOFF(noPpkb, "LOAD");
    }

    public String getOpsi() {
        return opsi;
    }

    public PlanningContLoad getPlanningContLoad() {
        return planningContLoad;
    }

    public void setPlanningContLoad(PlanningContLoad planningContLoad) {
        this.planningContLoad = planningContLoad;
    }
}
