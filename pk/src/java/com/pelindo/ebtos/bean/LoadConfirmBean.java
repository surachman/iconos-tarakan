/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.bean;

import com.qtasnim.jsf.FacesHelper;
import com.pelindo.ebtos.bean.security.LoginSessionBean;
import com.pelindo.ebtos.ejb.facade.remote.CancelLoadingServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.EquipmentFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterEquipmentFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterOperatorFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterProfileDetailFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterVesselProfileFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningContLoadFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningShiftDischargeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningVesselFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceContLoadFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceShiftingFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceVesselFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.UcShiftingServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.UncontainerizedServiceFacadeRemote;
import com.pelindo.ebtos.model.db.CancelLoadingService;
import com.pelindo.ebtos.model.db.Equipment;
import com.pelindo.ebtos.model.db.PlanningContLoad;
import com.pelindo.ebtos.model.db.PlanningShiftDischarge;
import com.pelindo.ebtos.model.db.PlanningUncontainerized;
import com.pelindo.ebtos.model.db.PlanningVessel;
import com.pelindo.ebtos.model.db.ServiceContLoad;
import com.pelindo.ebtos.model.db.ServiceShifting;
import com.pelindo.ebtos.model.db.ServiceVessel;
import com.pelindo.ebtos.model.db.UcShiftingService;
import com.pelindo.ebtos.model.db.UncontainerizedService;
import com.pelindo.ebtos.model.db.master.MasterEquipment;
import com.pelindo.ebtos.model.db.master.MasterOperator;
import com.pelindo.ebtos.model.db.master.MasterYard;
import com.pelindo.ebtos.model.db.master.MasterYardCoordinat;
import com.pelindo.ebtos.util.ServiceUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

/**
 *
 * @author dycoder
 */
@ManagedBean(name = "loadConfirmBean")
@ViewScoped
public class LoadConfirmBean implements Serializable {
    @EJB
    private UncontainerizedServiceFacadeRemote uncontainerizedServiceFacadeRemote;
    @EJB
    private ServiceContLoadFacadeRemote serviceContLoadFacade;
    @EJB
    private ServiceVesselFacadeRemote serviceVesselFacade;
    @EJB
    private PlanningVesselFacadeRemote planningVesselFacade;
    @EJB
    private MasterOperatorFacadeRemote masterOperatorFacadeRemote;
    @EJB
    private MasterEquipmentFacadeRemote masterEquipmentFacadeRemote;
    @EJB
    private EquipmentFacadeRemote equipmentFacadeRemote;
    @EJB
    private MasterVesselProfileFacadeRemote masterVesselProfileFacadeRemote;
    @EJB
    private MasterProfileDetailFacadeRemote masterProfileDetailFacadeRemote;
    @EJB
    private ServiceShiftingFacadeRemote serviceShiftingFacadeRemote;
    @EJB
    private PlanningShiftDischargeFacadeRemote planningShiftDischargeFacadeRemote;
    @EJB
    private CancelLoadingServiceFacadeRemote cancelLoadingServiceFacadeRemote;
    @EJB
    private PlanningContLoadFacadeRemote planningContLoadFacadeRemote;
    @EJB
    private UcShiftingServiceFacadeRemote ucShiftingServiceFacadeRemote;

    private List<ServiceContLoad> serviceContLoads;
    private List<PlanningContLoad> planningContLoads;
    private List<Equipment> equipments;
    private List<MasterOperator> masterOperators;
    private List<Object[]> serviceVessels;
    private List<Object[]> loadableContainers;
    private List<Object[]> loadableTranshipmentContainers;
    private List<Object[]> loadedShiftContainers;
    private List<Object[]> loadableShiftContainers;
    private List<Object[]> loadableUcs;
    private List<Object[]> loadedUcs;
    private List<Object[]> loadedContainers;
    private List<Object[]> loadedTranshipmentContainers;
    private List<Object[]> masterEquipments;
    private List<Integer> bayChoices;
    private List<Integer> rowChoices;
    private List<Integer> tierChoices;
    private List<Integer> rowUps;
    private List<Integer> rowBottoms;
    private List<String> masterEquipmentss;
    
    private Object[] serviceVessel;
    private Object[] uncontainerizedDetail;
    private Equipment crane;
    private Equipment haulageTruck;
    private ServiceVessel serviceVessell;
    private MasterOperator masterOperator;
    private PlanningVessel planningVessel;
    private PlanningContLoad planningContLoad;
    private ServiceContLoad serviceContLoad;
    private PlanningUncontainerized planningUncontainerized;
    private ServiceVessel vessel;
    private UncontainerizedService uncontainerizedService;
    private ServiceShifting serviceShifting;
    private PlanningShiftDischarge planningShiftDischarge;
    private boolean disableOptCrane;
    private boolean disableEdit = Boolean.FALSE;
    private String opsi = "cont";
    private String counterRealisasi = "isi";
    private String vesselCode;
    private String noPpkb;
    private String username;
    private String foreman;
    private String supervisiOperasi;

    /** Creates a new instance of LoadConfirmBean */
    public LoadConfirmBean() {
    }

    @PostConstruct
    private void construct() {
        masterOperator = new MasterOperator();
        crane = new Equipment();
        crane.setMasterEquipment(new MasterEquipment());
        crane.setMasterOperator(new MasterOperator());
        serviceContLoad = new ServiceContLoad();
        serviceContLoad.setMasterYard(new MasterYard());
        disableOptCrane = true;
        serviceContLoad.setCrane("L");
        bayChoices = new ArrayList<Integer>();
        rowChoices = new ArrayList<Integer>();
        tierChoices = new ArrayList<Integer>();
        rowUps = new ArrayList<Integer>();
        rowBottoms = new ArrayList<Integer>();
        this.counterRealisasi = "isi";
        this.disableEdit = Boolean.FALSE;
        masterOperators = masterOperatorFacadeRemote.findAll();
        masterEquipments = masterEquipmentFacadeRemote.findCraneForView();
        serviceVessels = serviceVesselFacade.findServiceVesselsServicing();
        planningShiftDischarge = new PlanningShiftDischarge();
        serviceShifting = new ServiceShifting();

        LoginSessionBean session = LoginSessionBean.getCurrentInstance();
        username = session.getUsername();
    }

    public void resetForm() {
        //masterOperator = new MasterOperator();
        //masterYard = new MasterYard();
//        crane = new Equipment();
//        crane.setMasterEquipment(new MasterEquipment());
//        crane.setMasterOperator(new MasterOperator());
        serviceContLoad = new ServiceContLoad();
        serviceContLoad.setMasterYard(new MasterYard());
        this.counterRealisasi = "isi";
        serviceContLoad.setCrane("L");
        planningShiftDischarge = new PlanningShiftDischarge();
        serviceShifting = new ServiceShifting();
        serviceShifting.setCrane("L");
        this.disableEdit = Boolean.FALSE;
    }
public void clearSave() {
        masterOperator = new MasterOperator();
        //masterYard = new MasterYard();
        crane = new Equipment();
        crane.setMasterEquipment(new MasterEquipment());
        crane.setMasterOperator(new MasterOperator());
        serviceContLoad = new ServiceContLoad();
        serviceContLoad.setMasterYard(new MasterYard());
        this.counterRealisasi = "isi";
        serviceContLoad.setCrane("L");
        planningShiftDischarge = new PlanningShiftDischarge();
        serviceShifting = new ServiceShifting();
        serviceShifting.setCrane("L");
        this.disableEdit = Boolean.FALSE;
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

    public void handleAdd(ActionEvent event) {
        this.opsi = "cont";
        this.clearSave();
        serviceContLoad.setVBay(Short.parseShort(bayChoices.get(0).toString()));
        changeBay(serviceContLoad.getVBay());
        changeRow(Short.parseShort(rowChoices.get(0).toString()));

    }

    public void handleAddShift(ActionEvent event) {
        this.opsi = "shift";
        this.clearSave();
        serviceShifting.setVBay(Short.parseShort(bayChoices.get(0).toString()));
        changeBay(serviceShifting.getVBay());
        changeRow(Short.parseShort(rowChoices.get(0).toString()));

    }

    public void handleOnSelect(ActionEvent event) {
        setNoPpkb((String) event.getComponent().getAttributes().get("noPpkb"));
        setServiceVessel(serviceVesselFacade.findServiceVesselByPpkb(getNoPpkb()));
        vesselCode = serviceVessel[4].toString();
        vessel = serviceVesselFacade.find(noPpkb);
        planningVessel = planningVesselFacade.find(noPpkb);

        loadedContainers = serviceContLoadFacade.findServiceContLoadByPpkb2(getNoPpkb());
        loadedTranshipmentContainers = serviceContLoadFacade.findServiceContLoadByPpkb2t(getNoPpkb());
        loadedUcs = uncontainerizedServiceFacadeRemote.findHasLoadedByNoPpkb(noPpkb);
        loadedShiftContainers = serviceShiftingFacadeRemote.findByPpkbLoadConfirmList(noPpkb);

        bayChoices.clear();
        bayChoices = masterVesselProfileFacadeRemote.findBaysByVessel(vesselCode);
    }

    public void handleRefreshLoadedContainers(ActionEvent event) {
        loadedContainers = serviceContLoadFacade.findServiceContLoadByPpkb2(getNoPpkb());
    }

    public void handleRefreshLoadedTranshipmentContainers(ActionEvent event) {
        loadedTranshipmentContainers = serviceContLoadFacade.findServiceContLoadByPpkb2t(getNoPpkb());
    }

    public void handleRefreshLoadedShiftContainers(ActionEvent event) {
        loadedShiftContainers = serviceShiftingFacadeRemote.findByPpkbLoadConfirmList(noPpkb);
    }

    public void handleRefreshLoadedUcs(ActionEvent event) {
        loadedUcs = uncontainerizedServiceFacadeRemote.findHasLoadedByNoPpkb(noPpkb);
    }

    public void handleShowLoadableShiftContainers(ActionEvent event) {
        crane.setStartTime(equipmentFacadeRemote.findByStartTimeData());
        crane.setEndTime(equipmentFacadeRemote.findByEndTimeData());
        loadableShiftContainers = serviceShiftingFacadeRemote.findByPpkbLoadConfirmSelect(noPpkb);
    }

    public void handleShowLoadableContainers(ActionEvent event) {
        crane.setStartTime(equipmentFacadeRemote.findByStartTimeData());
        crane.setEndTime(equipmentFacadeRemote.findByEndTimeData());
        loadableContainers = serviceContLoadFacade.findServiceContLoadConfirm(getNoPpkb());
    }

    public void handleShowLoadableTranshipmentContainers(ActionEvent event) {
        crane.setStartTime(equipmentFacadeRemote.findByStartTimeData());
        crane.setEndTime(equipmentFacadeRemote.findByEndTimeData());
        loadableTranshipmentContainers = serviceContLoadFacade.findServiceContLoadConfirm2(getNoPpkb());
    }

    public void handleShowLoadableUcs(ActionEvent event) {
        crane.setStartTime(equipmentFacadeRemote.findByStartTimeData());
        crane.setEndTime(equipmentFacadeRemote.findByEndTimeData());
        loadableUcs = uncontainerizedServiceFacadeRemote.findLoadableByNoPpkb(getNoPpkb());
    }

    public void handleSelectNoContShifting(ActionEvent event) {
        //this.opsi = "shift";
        int idPlanning = (Integer) event.getComponent().getAttributes().get("idPlanning");
        int idService = (Integer) event.getComponent().getAttributes().get("idService");
        planningShiftDischarge = planningShiftDischargeFacadeRemote.find(idPlanning);
        serviceShifting = serviceShiftingFacadeRemote.find(idService);        
    }

    public void handleSelectNoCont(ActionEvent event) {
        //this.opsi = "cont";
        int cont_no = (Integer) event.getComponent().getAttributes().get("id_no");
        serviceContLoad = serviceContLoadFacade.find(cont_no);        
    }

    public void handleSelectNoContt(ActionEvent event) {
        int cont_no = (Integer) event.getComponent().getAttributes().get("id_no");
        serviceContLoad = serviceContLoadFacade.find(cont_no);
        System.out.println("sdsd" + serviceContLoad.getVBay() + serviceContLoad.getVRow() + serviceContLoad.getVTier());

    }

    public void handleEditDeleteTran(ActionEvent event) {
        this.opsi="cont";
        int idCont = (Integer) event.getComponent().getAttributes().get("id_cont");
        serviceContLoad = serviceContLoadFacade.find(idCont);
        int equip = equipmentFacadeRemote.findByIdContainer(getNoPpkb(), serviceContLoad.getContNo(), "STEVEDORING", "TRANSHIPMENT");
        crane = equipmentFacadeRemote.find(equip);
        //check if master operator is null
        if (crane.getMasterOperator()==null)
            crane.setMasterOperator(new MasterOperator());
        this.counterRealisasi = "no";
        changeBay(serviceContLoad.getVBay());
        changeRow(serviceContLoad.getVRow());
        this.disableEdit = Boolean.TRUE;
    }

    public void handleEditDelete(ActionEvent event) {
        this.opsi="cont";
        int idCont = (Integer) event.getComponent().getAttributes().get("id_cont");
        serviceContLoad = serviceContLoadFacade.find(idCont);
        int equip = equipmentFacadeRemote.findByIdContainer(getNoPpkb(), serviceContLoad.getContNo(), "STEVEDORING", "LOAD");
        crane = equipmentFacadeRemote.find(equip);
        //check if master operator is null
        if (crane.getMasterOperator()==null)
            crane.setMasterOperator(new MasterOperator());
        
        changeBay(serviceContLoad.getVBay());
        changeRow(serviceContLoad.getVRow());
        this.counterRealisasi = "no";
        this.disableEdit = Boolean.TRUE;
    }

    public void handleEditDeleteShifting(ActionEvent event) {
        this.opsi="shift";
        int idPlanning = (Integer) event.getComponent().getAttributes().get("idPlanning");
        int idService = (Integer) event.getComponent().getAttributes().get("idService");
        planningShiftDischarge = planningShiftDischargeFacadeRemote.find(idPlanning);
        serviceShifting = serviceShiftingFacadeRemote.find(idService);

        int equip = equipmentFacadeRemote.findByIdContainer(getNoPpkb(), planningShiftDischarge.getContNo(), "STEVEDORING", "LD-SHIFTING-CY");
        crane = equipmentFacadeRemote.find(equip);
        //check if master operator is null
        if (crane.getMasterOperator()==null)
            crane.setMasterOperator(new MasterOperator());
        
        changeBay(serviceShifting.getVBay());
        changeRow(serviceShifting.getVRow());
        this.counterRealisasi = "no";
        this.disableEdit = Boolean.TRUE;
    }

    public void handleSaveShifting(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        boolean loggedIn = false;
        Date tgl = Calendar.getInstance().getTime();
        setServiceVessell(serviceVesselFacade.find(getNoPpkb()));
        if (planningShiftDischarge.getContNo() == null) {
            loggedIn = false;
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.save.failed");
        } else if (crane.getMasterEquipment().getEquipCode() == null || crane.getMasterOperator().getOptCode() == null) {
            loggedIn = false;
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.save.failed");
        } else if (crane.getEndTime().equals(crane.getStartTime()) || crane.getStartTime().after(crane.getEndTime())) {
            loggedIn = false;
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.validation.time");
        } else {
            if (disableEdit) {
                loggedIn = true;
            }

            serviceVessell = serviceVesselFacade.find(noPpkb);

            if (counterRealisasi.equals("isi")) {
                serviceVessell.setLoad((short) (serviceVessell.getLoad() + 1));
                serviceVesselFacade.edit(serviceVessell);
            }

            planningContLoad = planningContLoadFacadeRemote.findByNoPpkbAndContNoExcludeCancelLoading(noPpkb, planningShiftDischarge.getContNo());
            planningContLoad.setPosition(PlanningContLoad.LOCATION_VESSEL);

            crane.setContNo(planningShiftDischarge.getContNo());
            crane.setMlo(planningShiftDischarge.getMlo());
            crane.setEquipmentActCode("STEVEDORING");
            crane.setOperation("LD-SHIFTING-CY");
            crane.setPlanningVessel(serviceVessell.getPlanningVessel());

            // checking if master operator is null
            if (crane.getMasterOperator().getOptCode()==null)
                crane.setMasterOperator(null);
            
            planningShiftDischarge.setVBay(serviceShifting.getVBay());
            planningShiftDischarge.setVRow(serviceShifting.getVRow());
            planningShiftDischarge.setVTier(serviceShifting.getVTier());

            serviceShifting.setPosition("05");

            planningContLoadFacadeRemote.edit(planningContLoad);
            serviceShiftingFacadeRemote.edit(serviceShifting);
            planningShiftDischargeFacadeRemote.edit(planningShiftDischarge);
            equipmentFacadeRemote.edit(crane);
            equipmentFacadeRemote.updateEndTimeByEquipmentConstraint(crane.getStartTime(), noPpkb, planningShiftDischarge.getContNo(), "H/T", "LD-SHIFTING-CY");

            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");

//            this.clearSave();
            resetForm();
        }
        loadedShiftContainers=serviceShiftingFacadeRemote.findByPpkbLoadConfirmList(noPpkb);
        loadableShiftContainers=serviceShiftingFacadeRemote.findByPpkbLoadConfirmSelect(noPpkb);
        context.addCallbackParam("loggedIn", loggedIn);
    }

    public void handleSave(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        boolean loggedIn = false;
        serviceVessell = serviceVesselFacade.find(getNoPpkb());
        
        if (crane.getEndTime().equals(crane.getStartTime()) || crane.getStartTime().after(crane.getEndTime())) {
            loggedIn = false;
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.validation.time");
        } else {
            if (disableEdit) {
                loggedIn = true;
            }
            if(crane.getMasterEquipment() != null)
            {
                serviceContLoad.setEquipmentGroup(crane.getMasterEquipment().getEquipmentGroup());
            }
            
            planningContLoad = planningContLoadFacadeRemote.findByNoPpkbAndContNoExcludeCancelLoading(noPpkb, serviceContLoad.getContNo());
            planningContLoad.setPosition(PlanningContLoad.LOCATION_VESSEL);

            serviceContLoad.setLoadDate(crane.getEndTime());
            serviceContLoad.setPosition("01");
            serviceContLoad.setStatusCancelLoading("FALSE");

            crane.setContNo(serviceContLoad.getContNo());
            crane.setMlo(serviceContLoad.getMlo());
            crane.setEquipmentActCode("STEVEDORING");
            crane.setOperation("LOAD");
            crane.setPlanningVessel(serviceContLoad.getServiceVessel().getPlanningVessel());
            
            // checking if master operator is null
            if (crane.getMasterOperator().getOptCode()==null)
                crane.setMasterOperator(null);
            
            if (serviceContLoad.getIsChangeVessel().equals("TRUE")) {
                CancelLoadingService cancelLoadingService = cancelLoadingServiceFacadeRemote.findByNewPpkbAndContNo(noPpkb, serviceContLoad.getContNo());
                cancelLoadingService.setPosisi(CancelLoadingService.AT_VESSEL);

                cancelLoadingServiceFacadeRemote.edit(cancelLoadingService);
            }

            if (counterRealisasi.equals("isi")) {
                serviceVessell.setLoad((short) (serviceVessell.getLoad() + 1));
                serviceVesselFacade.edit(serviceVessell);
            }

            Equipment headTruck = equipmentFacadeRemote.findByEquipmentActCodeAndOperation(noPpkb, serviceContLoad.getContNo(), "H/T", "LOAD");

            if (headTruck != null) {
                headTruck.setEndTime(crane.getStartTime());
                equipmentFacadeRemote.edit(headTruck);
            }

            serviceContLoad.setCrane(ServiceUtil.getCraneCode(crane.getMasterEquipment().getEquipCode()));
            equipmentFacadeRemote.edit(crane);
            serviceContLoadFacade.edit(serviceContLoad);
            planningContLoadFacadeRemote.edit(planningContLoad);
            
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
            //clearSave();
            resetForm();
        }

        loadedContainers = serviceContLoadFacade.findServiceContLoadByPpkb2(getNoPpkb());
        context.addCallbackParam("loggedIn", loggedIn);
    }

    public void handleSaveTrans(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        boolean loggedIn = false;
        setServiceVessell(serviceVesselFacade.find(getNoPpkb()));

        if (crane.getEndTime().equals(crane.getStartTime()) || crane.getStartTime().after(crane.getEndTime())) {
            loggedIn = false;
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.validation.time");
        } else {
            if (disableEdit) {
                loggedIn = true;
            }

            planningContLoad = planningContLoadFacadeRemote.findByNoPpkbAndContNoExcludeCancelLoading(noPpkb, serviceContLoad.getContNo());
            planningContLoad.setPosition(PlanningContLoad.LOCATION_VESSEL);

            serviceContLoad.setPosition("01");
            serviceContLoad.setLoadDate(crane.getEndTime());
            serviceContLoad.setStatusCancelLoading("FALSE");

            crane.setContNo(serviceContLoad.getContNo());
            crane.setMlo(serviceContLoad.getMlo());
            crane.setEquipmentActCode("STEVEDORING");
            crane.setOperation("TRANSHIPMENT");
            crane.setPlanningVessel(serviceContLoad.getServiceVessel().getPlanningVessel());
            
            // checking if master operator is null
            if (crane.getMasterOperator().getOptCode()==null)
                crane.setMasterOperator(null);
            
            Equipment headTruck = equipmentFacadeRemote.findByEquipmentActCodeAndOperation(noPpkb, serviceContLoad.getContNo(), "H/T", "TRANSHIPMENT");
            headTruck.setEndTime(crane.getStartTime());

            if (counterRealisasi.equals("isi")) {
                serviceVessell.setLoad((short) (serviceVessell.getLoad() + 1));
                serviceVesselFacade.edit(serviceVessell);
            }

            if (serviceContLoad.getIsChangeVessel().equals("TRUE")) {
                CancelLoadingService cancelLoadingService = cancelLoadingServiceFacadeRemote.findByNewPpkbAndContNo(noPpkb, serviceContLoad.getContNo());
                cancelLoadingService.setPosisi(CancelLoadingService.AT_VESSEL);
                cancelLoadingServiceFacadeRemote.edit(cancelLoadingService);
            }

            planningContLoadFacadeRemote.edit(planningContLoad);
            serviceContLoadFacade.edit(serviceContLoad);
            equipmentFacadeRemote.edit(crane);
            equipmentFacadeRemote.edit(headTruck);

            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
            //this.clearSave();
            resetForm();
        }
        
        loadedTranshipmentContainers = serviceContLoadFacade.findServiceContLoadByPpkb2t(getNoPpkb());
        context.addCallbackParam("loggedIn", loggedIn);
    }

    public void handleDeleteShift(ActionEvent event) {
        try {
            RequestContext context = RequestContext.getCurrentInstance();
            serviceVessell = serviceVesselFacade.find(getNoPpkb());
            short counter = (short) (serviceVessell.getLoad() - 1);
            serviceVessell.setLoad(counter);
            serviceVesselFacade.edit(serviceVessell);
            serviceShifting.setPosition("04");
            serviceShiftingFacadeRemote.edit(serviceShifting);
            equipmentFacadeRemote.remove(crane);

            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.delete.succeeded");
            this.clearSave();
        } catch (EJBException ex) {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.delete.failed");
            ex.printStackTrace();
        }
        loadedShiftContainers=serviceShiftingFacadeRemote.findByPpkbLoadConfirmList(noPpkb);
        loadableShiftContainers=serviceShiftingFacadeRemote.findByPpkbLoadConfirmSelect(noPpkb);
    }

    public void handleDelete(ActionEvent event) {
        try {
            serviceVessell = serviceVesselFacade.find(getNoPpkb());

            planningContLoad = planningContLoadFacadeRemote.findByNoPpkbAndContNoExcludeCancelLoading(noPpkb, serviceContLoad.getContNo());
            planningContLoad.setPosition(PlanningContLoad.LOCATION_HT);

            serviceVessell.setLoad((short) (serviceVessell.getLoad() - 1));
            serviceContLoad.setPosition("02");
            if (crane.getId()==null) {
                crane = equipmentFacadeRemote.findByEquipmentActCodeAndOperation(serviceContLoad.getServiceVessel().getNoPpkb(), 
                        serviceContLoad.getContNo(), "STEVEDORING", "LOAD");
            }
            if (serviceContLoad.getIsChangeVessel().equals("TRUE")) {
                CancelLoadingService cancelLoadingService = cancelLoadingServiceFacadeRemote.findByNewPpkbAndContNo(noPpkb, serviceContLoad.getContNo());
                cancelLoadingService.setPosisi(CancelLoadingService.AT_HAULAGE_TRUCK);

                cancelLoadingServiceFacadeRemote.edit(cancelLoadingService);
            }

            planningContLoadFacadeRemote.edit(planningContLoad);
            serviceContLoadFacade.edit(serviceContLoad);
            serviceVesselFacade.edit(serviceVessell);
            if (crane!=null)
                equipmentFacadeRemote.remove(crane);

            if (serviceContLoad.getIsTranshipment().equals("TRUE")) {
                loadedTranshipmentContainers = serviceContLoadFacade.findServiceContLoadByPpkb2t(getNoPpkb());
            } else {
                loadedContainers = serviceContLoadFacade.findServiceContLoadByPpkb2(getNoPpkb());
            }

            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.delete.succeeded");
            clearSave();
        } catch (EJBException ex) {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.delete.failed");
            ex.printStackTrace();
        }
    }

    public void handleSelectHT(ActionEvent event) {
        String idHT = (String) event.getComponent().getAttributes().get("idHT");
        crane.setMasterEquipment(masterEquipmentFacadeRemote.find(idHT));
        disableOptCrane = false;
    }

    public void handleSelectOperatorCrane(ActionEvent event) {
        String idOperatorCrane = (String) event.getComponent().getAttributes().get("idOperatorCrane");
        crane.setMasterOperator(masterOperatorFacadeRemote.find(idOperatorCrane));
    }

    //uncontainerized logic
    public void handleAddUncontainerized() {
        uncontainerizedService = new UncontainerizedService();
        //inisialisasi crane
        crane = new Equipment();
        crane.setMasterEquipment(new MasterEquipment());
        crane.setMasterOperator(new MasterOperator());
        crane.setPlanningVessel(new PlanningVessel());
        uncontainerizedDetail = null;
        serviceContLoad.setVBay(Short.parseShort(bayChoices.get(0).toString()));
        changeBay(serviceContLoad.getVBay());
    }

    public void handleEditDeleteUncontainerized(ActionEvent event) {
        int idUC = (Integer) event.getComponent().getAttributes().get("idUC");
        uncontainerizedService = uncontainerizedServiceFacadeRemote.find(idUC);
        uncontainerizedDetail = uncontainerizedServiceFacadeRemote.findDetailByIdUc(idUC);

        if (uncontainerizedService.getIsShifting().equals("TRUE")) {
            crane = equipmentFacadeRemote.findByPpkbBLNoEquipmentActCodeAndOperation(noPpkb, uncontainerizedService.getBlNo(), "RESHIPPING", "SHIFTING-TOCY");
        } else if (uncontainerizedService.getIsTranshipment().equals("TRUE")) {
            crane = equipmentFacadeRemote.findByPpkbBLNoEquipmentActCodeAndOperation(noPpkb, uncontainerizedService.getBlNo(), "STEVEDORING", "TRANSLOADUC");
        } else {
            crane = equipmentFacadeRemote.findByPpkbBLNoEquipmentActCodeAndOperation(noPpkb, uncontainerizedService.getBlNo(), "STEVEDORING", "LOADUC");
        }
        //check if master operator is null
        if (crane.getMasterOperator()==null)
            crane.setMasterOperator(new MasterOperator());
    }

    public void handleSelectUncontainerized(ActionEvent event) {
        int idUC = (Integer) event.getComponent().getAttributes().get("idSelect");
        uncontainerizedService = uncontainerizedServiceFacadeRemote.find(idUC);
        uncontainerizedService.setStatus("VESSEL");
        uncontainerizedService.setCrane("L");
        uncontainerizedDetail = uncontainerizedServiceFacadeRemote.findDetailByIdUc(idUC);
    }

    public void saveUncontainerized() {
        boolean loggedIn = false;

        if (uncontainerizedService.getCrane() == null || crane.getMasterEquipment() == null || crane.getMasterOperator() == null || crane.getStartTime() == null || crane.getEndTime() == null) {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.save.failed");
        } else if (crane.getStartTime().after(crane.getEndTime()) || crane.getStartTime().equals(crane.getEndTime())) {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.validation.time");
        } else {
            crane.setPlanningVessel(vessel.getPlanningVessel());
            crane.setBlNo(uncontainerizedService.getBlNo());

            if (uncontainerizedService.getIsShifting().equals("TRUE")) {
                crane.setEquipmentActCode("RESHIPPING");
                crane.setOperation("SHIFTING-TOCY");
                equipmentFacadeRemote.edit(crane);

                UcShiftingService ucShiftingService = ucShiftingServiceFacadeRemote.findByNoPpkbAndBlNo(uncontainerizedService.getNoPpkb(), uncontainerizedService.getBlNo());
                int id = equipmentFacadeRemote.findIdEquipmentByAllBlNo(crane.getMasterEquipment().getEquipCode(), crane.getMasterOperator().getOptCode(), crane.getBlNo(), uncontainerizedService.getNoPpkb(), crane.getStartTime(), crane.getEndTime(), crane.getEquipmentActCode(), crane.getOperation());
                ucShiftingService.setIdEquipmentReshipping(id);
                ucShiftingService.setIsReshipping("TRUE");

                ucShiftingServiceFacadeRemote.edit(ucShiftingService);
            } else if (uncontainerizedService.getIsTranshipment().equals("TRUE")) {
                crane.setEquipmentActCode("STEVEDORING");
                crane.setOperation("TRANSLOADUC");
                equipmentFacadeRemote.edit(crane);
            } else {
                crane.setEquipmentActCode("STEVEDORING");
                crane.setOperation("LOADUC");
                equipmentFacadeRemote.edit(crane);
            }

            if (uncontainerizedService.getTruckLoosing().equals("FALSE")) {
                if (uncontainerizedService.getIsShifting().equals("TRUE")) {
                    haulageTruck = equipmentFacadeRemote.findByPpkbBLNoEquipmentActCodeAndOperation(noPpkb, uncontainerizedService.getBlNo(), "H/T-RESHIPPING", "SHIFTING-TOCY");
                } else if (uncontainerizedService.getIsTranshipment().equals("TRUE")) {
                    haulageTruck = equipmentFacadeRemote.findByPpkbBLNoEquipmentActCodeAndOperation(noPpkb, uncontainerizedService.getBlNo(), "H/T", "TRANSLOADUC");
                } else {
                    haulageTruck = equipmentFacadeRemote.findByPpkbBLNoEquipmentActCodeAndOperation(noPpkb, uncontainerizedService.getBlNo(), "H/T", "LOADUC");
                }

                haulageTruck.setEndTime(crane.getStartTime());

                equipmentFacadeRemote.edit(haulageTruck);
            }

            uncontainerizedServiceFacadeRemote.edit(uncontainerizedService);
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
            loggedIn = true;
            resetForm();
        }

        RequestContext.getCurrentInstance().addCallbackParam("loggedIn", loggedIn);
        loadedUcs = uncontainerizedServiceFacadeRemote.findHasLoadedByNoPpkb(noPpkb);
    }

    public void deleteUC() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            uncontainerizedService.setStatus("STV");

            if (uncontainerizedService.getTruckLoosing().equals("FALSE")) {
                if (uncontainerizedService.getIsShifting().equals("TRUE")) {
                    haulageTruck = equipmentFacadeRemote.findByPpkbBLNoEquipmentActCodeAndOperation(noPpkb, uncontainerizedService.getBlNo(), "H/T-RESHIPPING", "SHIFTING-TOCY");

                    UcShiftingService ucShiftingService = ucShiftingServiceFacadeRemote.findByNoPpkbAndBlNo(uncontainerizedService.getNoPpkb(), uncontainerizedService.getBlNo());
                    ucShiftingService.setIsReshipping("FALSE");
                    ucShiftingService.setIdEquipmentReshipping(null);

                    ucShiftingServiceFacadeRemote.edit(ucShiftingService);
                } else if (uncontainerizedService.getIsTranshipment().equals("TRUE")) {
                    haulageTruck = equipmentFacadeRemote.findByPpkbBLNoEquipmentActCodeAndOperation(noPpkb, uncontainerizedService.getBlNo(), "H/T", "TRANSLOADUC");
                } else {
                    haulageTruck = equipmentFacadeRemote.findByPpkbBLNoEquipmentActCodeAndOperation(noPpkb, uncontainerizedService.getBlNo(), "H/T", "LOADUC");
                }

                haulageTruck.setEndTime(null);
                equipmentFacadeRemote.edit(haulageTruck);
            }

            uncontainerizedServiceFacadeRemote.edit(uncontainerizedService);
            equipmentFacadeRemote.remove(crane);

            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.delete.succeeded");
            loadedUcs = uncontainerizedServiceFacadeRemote.findHasLoadedByNoPpkb(noPpkb);
        } catch (EJBException ex) {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.delete.failed");
            ex.printStackTrace();
        }
    }

    public void onchangeBay(ValueChangeEvent event) {
        Short newBay = (Short) event.getNewValue();
        if (opsi.equalsIgnoreCase("cont")) {
            serviceContLoad.setVBay(newBay);
        } else {
            serviceShifting.setVBay(newBay);
        }
        changeBay(newBay);
    }

    public void onChangeRow(ValueChangeEvent event) {
        Short newRow = (Short) event.getNewValue();
        if (opsi.equalsIgnoreCase("cont")) {
            serviceContLoad.setVRow(newRow);
        } else {
            serviceShifting.setVRow(newRow);
        }
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
        if (opsi.equalsIgnoreCase("cont")) {
            bay = serviceContLoad.getVBay();
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

    public void onChangeCraneS(ValueChangeEvent event) {
        String newCrane = (String) event.getNewValue();
        serviceShifting.setCrane(newCrane);
        if (newCrane.equalsIgnoreCase("S")) {
            crane.setMasterEquipment(masterEquipmentFacadeRemote.find("EQ000"));
            crane.setMasterOperator(masterOperatorFacadeRemote.find("0"));
        } else if (newCrane.equalsIgnoreCase("L")) {
            crane.setMasterEquipment(new MasterEquipment());
            crane.setMasterOperator(new MasterOperator());
        }
    }

    //handling crane
    public void onChangeCrane(ValueChangeEvent event) {
        String newCrane = (String) event.getNewValue();
        serviceContLoad.setCrane(newCrane);
        if (newCrane.equalsIgnoreCase("S")) {
            crane.setMasterEquipment(masterEquipmentFacadeRemote.find("EQ000"));
            crane.setMasterOperator(masterOperatorFacadeRemote.find("0"));
        } else if (newCrane.equalsIgnoreCase("L")) {
            crane.setMasterEquipment(new MasterEquipment());
            crane.setMasterOperator(new MasterOperator());
        }
    }

    public void onChangeCraneUc(ValueChangeEvent event) {
        String newCrane = (String) event.getNewValue();
        uncontainerizedService.setCrane(newCrane);
        if (newCrane.equalsIgnoreCase("S")) {
            crane.setMasterEquipment(masterEquipmentFacadeRemote.find("EQ000"));
            crane.setMasterOperator(masterOperatorFacadeRemote.find("0"));
        } else if (newCrane.equalsIgnoreCase("L")) {
            crane.setMasterEquipment(new MasterEquipment());
            crane.setMasterOperator(new MasterOperator());
        }
    }

    public void handleDownloadReport(ActionEvent event) {
        try {
            RequestContext context = RequestContext.getCurrentInstance();

            context.addCallbackParam("doPrint", true); //harus ada, buat ngeflag di izinkan ngeprint ato ngga
            String url = "../../../LoadReport.pdf?" +
                    "noPpkb=" + vessel.getNoPpkb() + "&" +
                    "foreman=" + foreman + "&" +
                    "supervisiOperasi=" + supervisiOperasi + "&" +
                    "username=" + username;
            context.addCallbackParam("url", StringEscapeUtils.escapeHtml3(url));
        } catch (RuntimeException re) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught when call handleDownloadReport", re);
        }
    }

    /**
     * @return the serviceContLoads
     */
    public List<ServiceContLoad> getServiceContLoads() {
        return serviceContLoads;
    }

    /**
     * @param serviceContLoads the serviceContLoads to set
     */
    public void setServiceContLoads(List<ServiceContLoad> serviceContLoads) {
        this.serviceContLoads = serviceContLoads;
    }

    /**
     * @return the planningVessel
     */
    public PlanningVessel getPlanningVessel() {
        return planningVessel;
    }

    /**
     * @param planningVessel the planningVessel to set
     */
    public void setPlanningVessel(PlanningVessel planningVessel) {
        this.planningVessel = planningVessel;
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
     * @return the planningUncontainerized
     */
    public PlanningUncontainerized getPlanningUncontainerized() {
        return planningUncontainerized;
    }

    /**
     * @param planningUncontainerized the planningUncontainerized to set
     */
    public void setPlanningUncontainerized(PlanningUncontainerized planningUncontainerized) {
        this.planningUncontainerized = planningUncontainerized;
    }

    /**
     * @return the serviceContLoad
     */
    public ServiceContLoad getServiceContLoad() {
        return serviceContLoad;
    }

    /**
     * @param serviceContLoad the serviceContLoad to set
     */
    public void setServiceContLoad(ServiceContLoad serviceContLoad) {
        this.serviceContLoad = serviceContLoad;
    }

    /**
     * @return the equipment
     */
    public Equipment getCrane() {
        return crane;
    }

    /**
     * @return the planningContLoads
     */
    public List<PlanningContLoad> getPlanningContLoads() {
        return planningContLoads;
    }

    /**
     * @param planningContLoads the planningContLoads to set
     */
    public void setPlanningContLoads(List<PlanningContLoad> planningContLoads) {
        this.planningContLoads = planningContLoads;
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
     * @return the ServiceContLoadsListObjek
     */
    public List<Object[]> getLoadableContainers() {
        return loadableContainers;
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
     * @return the equipments
     */
    public List<Equipment> getEquipments() {
        return equipments;
    }

    /**
     * @param equipments the equipments to set
     */
    public void setEquipments(List<Equipment> equipments) {
        this.equipments = equipments;
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
     * @return the masterEquipmentss
     */
    public List<String> getMasterEquipmentss() {
        return masterEquipmentss;
    }

    /**
     * @param masterEquipmentss the masterEquipmentss to set
     */
    public void setMasterEquipmentss(List<String> masterEquipmentss) {
        this.masterEquipmentss = masterEquipmentss;
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
     * @return the serviceVessell
     */
    public ServiceVessel getServiceVessell() {
        return serviceVessell;
    }

    /**
     * @param serviceVessell the serviceVessell to set
     */
    public void setServiceVessell(ServiceVessel serviceVessell) {
        this.serviceVessell = serviceVessell;
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
     * @return the serviceContLoadsList
     */
    public List<Object[]> getLoadedContainers() {
        return loadedContainers;
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
     * @return the serviceContLoadListTran
     */
    public List<Object[]> getLoadableTranshipmentContainers() {
        return loadableTranshipmentContainers;
    }

    /**
     * @return the serviceListTranshipment
     */
    public List<Object[]> getLoadedTranshipmentContainers() {
        return loadedTranshipmentContainers;
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

    /**
     * @return the uncontainerizedServiceSelect
     */
    public List<Object[]> getLoadableUcs() {
        return loadableUcs;
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

    public String getCounterRealisasi() {
        return counterRealisasi;
    }

    public void setCounterRealisasi(String counterRealisasi) {
        this.counterRealisasi = counterRealisasi;
    }

    public boolean isDisableEdit() {
        return disableEdit;
    }

    public void setDisableEdit(boolean disableEdit) {
        this.disableEdit = disableEdit;
    }

    /**
     * @return the uncontainerizedDetail
     */
    public Object[] getUncontainerizedDetail() {
        return uncontainerizedDetail;
    }

    public PlanningShiftDischarge getPlanningShiftDischarge() {
        return planningShiftDischarge;
    }

    public List<Object[]> getLoadedShiftContainers() {
        return loadedShiftContainers;
    }

    public List<Object[]> getLoadableShiftContainers() {
        return loadableShiftContainers;
    }

    public List<Object[]> getLoadedUcs() {
        return loadedUcs;
    }

    public String getOpsi() {
        return opsi;
    }

    public void setOpsi(String opsi) {
        this.opsi = opsi;
    }

    public void setPlanningShiftDischarge(PlanningShiftDischarge planningShiftDischarge) {
        this.planningShiftDischarge = planningShiftDischarge;
    }

    public ServiceShifting getServiceShifting() {
        return serviceShifting;
    }

    public void setServiceShifting(ServiceShifting serviceShifting) {
        this.serviceShifting = serviceShifting;
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
    }
}
