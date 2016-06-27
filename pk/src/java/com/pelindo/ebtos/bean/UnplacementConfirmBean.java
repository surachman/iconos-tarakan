/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.bean;

import com.qtasnim.jsf.FacesHelper;
import com.pelindo.ebtos.bean.security.LoginSessionBean;
import com.pelindo.ebtos.ejb.facade.remote.EquipmentFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterEquipmentFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterOperatorFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterYardCoordinatFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningContLoadFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningShiftDischargeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceContLoadFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceVesselFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceShiftingFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.UcShiftingServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.UncontainerizedServiceFacadeRemote;
import com.pelindo.ebtos.ejb.remote.LiftOnOperationRemote;
import com.pelindo.ebtos.exception.ContainerNotMovableException;
import com.pelindo.ebtos.exception.LocationNotAvailableException;
import com.pelindo.ebtos.exception.TimeSelectionNotValidException;
import com.pelindo.ebtos.model.db.Equipment;
import com.pelindo.ebtos.model.db.PlanningContLoad;
import com.pelindo.ebtos.model.db.PlanningVessel;
import com.pelindo.ebtos.model.db.ServiceContLoad;
import com.pelindo.ebtos.model.db.ServiceVessel;
import com.pelindo.ebtos.model.db.master.MasterContainerType;
import com.pelindo.ebtos.model.db.master.MasterEquipment;
import com.pelindo.ebtos.model.db.master.MasterOperator;
import com.pelindo.ebtos.model.db.master.MasterYard;
import com.pelindo.ebtos.model.db.master.MasterYardCoordinat;
import com.pelindo.ebtos.model.db.PlanningShiftDischarge;
import com.pelindo.ebtos.model.db.ReceivingService;
import com.pelindo.ebtos.model.db.ServiceReceiving;
import com.pelindo.ebtos.model.db.ServiceShifting;
import com.pelindo.ebtos.model.db.UcShiftingService;
import com.pelindo.ebtos.model.db.UncontainerizedService;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
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
 * @author dycode-java
 */
@ManagedBean(name = "unplacementConfirmBean")
@ViewScoped
public class UnplacementConfirmBean implements Serializable {
    private static final Logger logger = Logger.getLogger(UnplacementConfirmBean.class.getName());

    @EJB
    private UncontainerizedServiceFacadeRemote uncontainerizedServiceFacadeRemote;
    @EJB
    private PlanningContLoadFacadeRemote planningContLoadFacade;
    @EJB
    private MasterEquipmentFacadeRemote masterEquipmentFacadeRemote;
    @EJB
    private MasterOperatorFacadeRemote masterOperatorFacadeRemote;
    @EJB
    private ServiceVesselFacadeRemote serviceVesselFacadeRemote;
    @EJB
    private ServiceContLoadFacadeRemote serviceContLoadFacadeRemote;
    @EJB
    private EquipmentFacadeRemote equipmentFacadeRemote;
    @EJB
    private MasterYardCoordinatFacadeRemote masterYardCoordinatFacadeRemote;
    @EJB
    private ServiceShiftingFacadeRemote serviceShiftingFacadeRemote;
    @EJB
    private PlanningShiftDischargeFacadeRemote planningShiftDischargeFacadeRemote;
    @EJB
    private UcShiftingServiceFacadeRemote ucShiftingServiceFacadeRemote;
    @EJB
    private LiftOnOperationRemote liftOnOperationRemote;
    
    private List<ServiceContLoad> serviceContLoads;
    private List<MasterOperator> masterOperators;
    private List<Equipment> equipments;
    private List<Object[]> tangoList;
    private List<Object[]> headTruckList;
    private List<Object[]> liftableOnContainers;
    private List<Object[]> planningVessels;
    private List<Object[]> liftableOnUcs;
    private List<Object[]> liftedOnUcs;
    private List<Object[]> liftedOnShiftContainers;
    private List<Object[]> liftableOnShiftContainers;
    private List<Object[]> liftedOnContainers;
    private PlanningVessel planningVessel;
    private ServiceVessel vessel;
    private MasterYardCoordinat masterYardCoordinat;
    private UncontainerizedService uncontainerizedService;
    private ServiceShifting serviceShifting;
    private PlanningShiftDischarge planningShiftDischarge;
    private ServiceContLoad serviceContLoad;
    private ServiceReceiving serviceReceiving;
    private MasterEquipment masterEquipment;
    private MasterYard masterYard;
    private Equipment tango;
    private Equipment haulageTruck;
    private PlanningContLoad planningContLoad;
    private ReceivingService receivingService;
    private Object[] planningVessell;
    private Object[] masterYardCordinat;
    private Object[] masterYard2;
    private Object[] tempEdit;
    private Object[] tempEdit2;
    private Object[] uncontainerizedDetail;
    private Integer idDelete;
    private String noPpkb;
    private String username;
    private String koordinatorLapangan;
    private String supervisiOperasi;
    private boolean diasbleHTCrane;
    private boolean disableOptTrane;
    private boolean disableOptHT;
    private boolean disableTTCrane;

    /** Creates a new instance of UnplacementConfirmBean */
    public UnplacementConfirmBean() {}

    @PostConstruct
    public void construct() {
        planningContLoad = new PlanningContLoad();
        planningContLoad.setMasterContainerType(new MasterContainerType());
        masterYard = new MasterYard();
        planningContLoad.setMasterYard(new MasterYard());
        tango = new Equipment();
        haulageTruck = new Equipment();
        masterEquipment = new MasterEquipment();
        haulageTruck.setMasterEquipment(new MasterEquipment());
        haulageTruck.setMasterOperator(new MasterOperator());
        tango.setMasterEquipment(new MasterEquipment());
        tango.setMasterOperator(new MasterOperator());
        serviceContLoad = new ServiceContLoad();
        serviceContLoad.setMasterContainerType(new MasterContainerType());
        serviceContLoad.setMasterYard(new MasterYard());
        masterYardCoordinat = new MasterYardCoordinat();
        serviceReceiving = new ServiceReceiving();
        receivingService = new ReceivingService();
        diasbleHTCrane = true;
        disableOptTrane = true;
        disableOptHT = true;
        disableTTCrane = true;
        planningShiftDischarge = new PlanningShiftDischarge();
        serviceShifting = new ServiceShifting();

        LoginSessionBean session = LoginSessionBean.getCurrentInstance();
        username = session.getUsername();
    }
    public void resetForm() {
        planningContLoad = new PlanningContLoad();
        planningContLoad.setMasterContainerType(new MasterContainerType());
        masterYard = new MasterYard();
        planningContLoad.setMasterYard(new MasterYard());
        //tango = new Equipment();
        //haulageTruck = new Equipment();
        masterEquipment = new MasterEquipment();
        //haulageTruck.setMasterEquipment(new MasterEquipment());
        //haulageTruck.setMasterOperator(new MasterOperator());
        //tango.setMasterEquipment(new MasterEquipment());
        //tango.setMasterOperator(new MasterOperator());
        serviceContLoad = new ServiceContLoad();
        serviceContLoad.setMasterContainerType(new MasterContainerType());
        serviceContLoad.setMasterYard(new MasterYard());
        masterYardCoordinat = new MasterYardCoordinat();
        serviceReceiving = new ServiceReceiving();
        receivingService = new ReceivingService();
        diasbleHTCrane = true;
        disableOptTrane = true;
        disableOptHT = true;
        //disableTTCrane = true;
        planningShiftDischarge = new PlanningShiftDischarge();
        serviceShifting = new ServiceShifting();

        LoginSessionBean session = LoginSessionBean.getCurrentInstance();
        username = session.getUsername();
    }

    public void initialData() {
        this.construct();
    }

    public void handleAdd(ActionEvent event) {
        initialData();
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

    public void handleOnSelect(ActionEvent event) {
        noPpkb = (String) event.getComponent().getAttributes().get("noPpkb");
        planningVessell = serviceVesselFacadeRemote.findServiceVesselByPpkb(getNoPpkb());
        vessel = serviceVesselFacadeRemote.find(noPpkb);

        liftedOnUcs = uncontainerizedServiceFacadeRemote.findHasLiftedOnByNoPpkb(noPpkb);
        liftedOnShiftContainers = serviceShiftingFacadeRemote.findByPpkbLiftOnList(noPpkb);
        liftedOnContainers = serviceContLoadFacadeRemote.findServiceContLoadConfirm(getNoPpkb());
//        liftedOnContainers = planningContLoadFacade.findPlanningBayplanLoadsByPpkb(getNoPpkb());
    }

    public void handleRefreshLiftedOnContainers(ActionEvent event) {
        liftedOnContainers = serviceContLoadFacadeRemote.findServiceContLoadConfirm(getNoPpkb());
    }

    public void handleRefreshLiftedOnShiftContainers(ActionEvent event) {
        liftedOnShiftContainers = serviceShiftingFacadeRemote.findByPpkbLiftOnList(noPpkb);
    }

    public void handleRefreshLiftedOnUcs(ActionEvent event) {
        liftedOnUcs = uncontainerizedServiceFacadeRemote.findHasLiftedOnByNoPpkb(noPpkb);
    }

    public void handleShowLiftableOnUcs(ActionEvent event) {
        
        tango.setStartTime(equipmentFacadeRemote.findByStartTimeData());
        tango.setEndTime(equipmentFacadeRemote.findByEndTimeData());
        liftableOnUcs = uncontainerizedServiceFacadeRemote.findLiftableOnByNoPpkb(noPpkb);
    }

    public void handleShowLiftableOnContainers(ActionEvent event) {
        tango.setStartTime(equipmentFacadeRemote.findByStartTimeData());
        tango.setEndTime(equipmentFacadeRemote.findByEndTimeData());
        liftableOnContainers = planningContLoadFacade.findPlanningContLoads3(getNoPpkb());
    }

    public void handleShowLiftableOnShiftContainers(ActionEvent event) {
        
        
        tango.setStartTime(equipmentFacadeRemote.findByStartTimeData());
        tango.setEndTime(equipmentFacadeRemote.findByEndTimeData());
        liftableOnShiftContainers = serviceShiftingFacadeRemote.findByPpkbLiftOnListSelect(noPpkb);
    }

    public void handleShowPlanningVessels(ActionEvent event) {
        planningVessels = serviceVesselFacadeRemote.findServiceVesselsServicing();
    }

    public void handleShowMasterOperatorsCrane(ActionEvent event) {
        masterOperators = masterOperatorFacadeRemote.findAll();
    }

    public void handleShowMasterOperatorsHeadTruck(ActionEvent event) {
        masterOperators = masterOperatorFacadeRemote.findAll();
    }

    public void handleShowTangos(ActionEvent event) {
        tangoList = masterEquipmentFacadeRemote.findTangoForView();
    }

    public void handleShowHeadTrucks(ActionEvent event) {
        headTruckList = masterEquipmentFacadeRemote.findHtForView();
    }

    public void handleSelectNoContShift(ActionEvent event) {
        Integer idService = (Integer) event.getComponent().getAttributes().get("idService");
        Integer idPlanning = (Integer) event.getComponent().getAttributes().get("idPlanning");
        serviceShifting = serviceShiftingFacadeRemote.find(idService);
        planningShiftDischarge = planningShiftDischargeFacadeRemote.find(idPlanning);
    }

    public void handleSelectNoCont(ActionEvent event) {
        int contNo = (Integer) event.getComponent().getAttributes().get("cont_no");
        planningContLoad = planningContLoadFacade.find(contNo);
        
        
        
        disableTTCrane = false;
    }

    public void handleSelectOperator(ActionEvent event) {
        String idOperatorCrane = (String) event.getComponent().getAttributes().get("idOperatorCran");
        haulageTruck.setMasterOperator(masterOperatorFacadeRemote.find(idOperatorCrane));
    }

    public void handleSelectOperatorCrane(ActionEvent event) {
        String idOperatorCrane = (String) event.getComponent().getAttributes().get("idOperatorCrane");
        tango.setMasterOperator(masterOperatorFacadeRemote.find(idOperatorCrane));
        diasbleHTCrane = false;
    }

    public void handleSelectHT(ActionEvent event) {
        String idHT = (String) event.getComponent().getAttributes().get("idHT");
        tango.setMasterEquipment(masterEquipmentFacadeRemote.find(idHT));
        disableOptTrane = false;
        diasbleHTCrane = false;
    }

    public void handleSelectOperatorHT(ActionEvent event) {
        String idOperatorHT = (String) event.getComponent().getAttributes().get("idOperatorHT");
        haulageTruck.setMasterEquipment(masterEquipmentFacadeRemote.find(idOperatorHT));
        disableOptHT = false;
    }

    public void handleEditDeleteButton(ActionEvent event) {
        setIdDelete((Integer) event.getComponent().getAttributes().get("idCont"));
        serviceContLoad = serviceContLoadFacadeRemote.find(getIdDelete());
        planningContLoad = planningContLoadFacade.findByNoPpkbAndContNoExcludeCancelLoading(serviceContLoad.getServiceVessel().getNoPpkb(), serviceContLoad.getContNo());

        if (serviceContLoad.getIsTranshipment().equals("TRUE")) {
            tango = equipmentFacadeRemote.findByEquipmentActCodeAndOperation(getNoPpkb(), serviceContLoad.getContNo(), "LIFTON", "TRANSHIPMENT");
            haulageTruck = equipmentFacadeRemote.findByEquipmentActCodeAndOperation(getNoPpkb(), serviceContLoad.getContNo(), "H/T", "TRANSHIPMENT");
        } else {
            tango = equipmentFacadeRemote.findByEquipmentActCodeAndOperation(getNoPpkb(), serviceContLoad.getContNo(), "LIFTON", "LOAD");
            haulageTruck = equipmentFacadeRemote.findByEquipmentActCodeAndOperation(getNoPpkb(), serviceContLoad.getContNo(), "H/T", "LOAD");
        }
        if (tango==null)
            tango = new Equipment();
        if (haulageTruck == null)
            haulageTruck = new Equipment();
        if (tango.getMasterOperator()==null)
            tango.setMasterOperator(new MasterOperator());
        if (haulageTruck.getMasterOperator()==null)
            haulageTruck.setMasterOperator(new MasterOperator());
    }

    public void handleEditDeleteButtonShifting(ActionEvent event) {
        int idService = (Integer) event.getComponent().getAttributes().get("idService");
        this.idDelete = (Integer) event.getComponent().getAttributes().get("idPlanning");
        serviceShifting = serviceShiftingFacadeRemote.find(idService);
        planningShiftDischarge = planningShiftDischargeFacadeRemote.find(idDelete);

        tango = equipmentFacadeRemote.findByEquipmentActCodeAndOperation(getNoPpkb(), planningShiftDischarge.getContNo(), "LIFTON", "LD-SHIFTING-CY");
        haulageTruck = equipmentFacadeRemote.findByEquipmentActCodeAndOperation(getNoPpkb(), planningShiftDischarge.getContNo(), "H/T", "LD-SHIFTING-CY");
    }

    public void handleLiftOnConfirmLoadContainer(ActionEvent event) {
        boolean isValid = false;
        RequestContext context = RequestContext.getCurrentInstance();
        FacesContext facesContext = FacesContext.getCurrentInstance();

        try {
            liftOnOperationRemote.handleLiftOnConfirmLoadContainer(vessel, planningContLoad, tango, haulageTruck);

            if (!planningContLoad.getPosition().equals(PlanningContLoad.LOCATION_CY)) {
                isValid = true;
            }

            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
            //handleAdd(event);
            resetForm();
        } catch (TimeSelectionNotValidException ex) {
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_ERROR, "Error", "application.validation.time");
        } catch (ContainerNotMovableException ex) {
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_ERROR, "Error", "application.validation.cant_move_container");
        } catch (Exception ex) {
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_ERROR, "Error", "application.loading.error");
            logger.log(Level.SEVERE, null, ex);
        }

        liftedOnContainers = serviceContLoadFacadeRemote.findServiceContLoadConfirm(getNoPpkb());
        context.addCallbackParam("loggedIn", isValid);
    }

    public void handleLiftOnConfirmShiftingContainer(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        boolean isValid = false;

        try {
            liftOnOperationRemote.handleLiftOnConfirmShiftingContainer(planningShiftDischarge, serviceShifting, tango, haulageTruck);

            if (!serviceShifting.getPosition().equals(ServiceShifting.LOCATION_CY)) {
                isValid = true;
            }

            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
            //construct();
            resetForm();
        } catch (TimeSelectionNotValidException ex) {
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_ERROR, "Error", "application.validation.time");
        } catch (ContainerNotMovableException ex) {
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_ERROR, "Error", "application.validation.cant_move_container");
        } catch (Exception ex) {
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_ERROR, "Error", "application.loading.error");
            logger.log(Level.SEVERE, null, ex);
        }

        liftedOnShiftContainers = serviceShiftingFacadeRemote.findByPpkbLiftOnList(noPpkb);
        context.addCallbackParam("loggedIn", isValid);
    }

    public void handleDeleteLoadContainer(ActionEvent actionEvent) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        
        try {
            liftOnOperationRemote.handleCancelLiftOnConfirmLoadContainer(planningContLoad, tango, haulageTruck);
            construct();
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_INFO, "Info", "application.delete.succeeded");
        } catch (LocationNotAvailableException ex) {
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_ERROR, "Error", "application.validation.location_not_available");
        } catch (Exception ex) {
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_ERROR, "Error", "application.delete.failed");
            logger.log(Level.SEVERE, null, ex);
        }

        liftedOnContainers = serviceContLoadFacadeRemote.findServiceContLoadConfirm(getNoPpkb());
    }

    public void handleDeleteShiftContainer(ActionEvent actionEvent) {
        FacesContext facesContext = FacesContext.getCurrentInstance();

        try {
            liftOnOperationRemote.handleCancelLiftOnConfirmShiftingContainer(planningShiftDischarge, serviceShifting, tango, haulageTruck);
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_INFO, "Info", "application.delete.succeeded");
        } catch (LocationNotAvailableException ex) {
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_ERROR, "Error", "application.validation.location_not_available");
        } catch (Exception ex) {
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_ERROR, "Error", "application.delete.failed");
            logger.log(Level.SEVERE, null, ex);
        }

        liftedOnShiftContainers = serviceShiftingFacadeRemote.findByPpkbLiftOnList(noPpkb);
    }

    public void handleSaveEditShifting(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        boolean loggedIn = false;

        if (tango.getStartTime().after(tango.getEndTime()) || tango.getStartTime().equals(tango.getEndTime())) {
            loggedIn = false;
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.validation.time");
        } else {
            loggedIn = true;

            tango.setContNo(planningShiftDischarge.getContNo());
            tango.setMlo(planningShiftDischarge.getMlo());
            tango.setEquipmentActCode("LIFTON");
            tango.setOperation("LD-SHIFTING-CY");
            tango.setPlanningVessel(vessel.getPlanningVessel());
            equipmentFacadeRemote.edit(tango);

            haulageTruck.setContNo(planningShiftDischarge.getContNo());
            haulageTruck.setMlo(planningShiftDischarge.getMlo());
            haulageTruck.setEquipmentActCode("H/T");
            haulageTruck.setOperation("LD-SHIFTING-CY");
            haulageTruck.setStartTime(tango.getEndTime());
            haulageTruck.setPlanningVessel(vessel.getPlanningVessel());
            equipmentFacadeRemote.edit(haulageTruck);

            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");

            this.construct();
        }
        liftedOnShiftContainers = serviceShiftingFacadeRemote.findByPpkbLiftOnList(noPpkb);
        liftableOnShiftContainers = serviceShiftingFacadeRemote.findByPpkbLiftOnListSelect(noPpkb);
        context.addCallbackParam("loggedIn", loggedIn);
    }

    public void handleDownloadReport(ActionEvent event) {
        try {
            RequestContext context = RequestContext.getCurrentInstance();

            context.addCallbackParam("doPrint", true); //harus ada, buat ngeflag di izinkan ngeprint ato ngga
            String url = "../../../LoadYardActivityReport.pdf?" +
                    "noPpkb=" + vessel.getNoPpkb() + "&" +
                    "koordinatorLapangan=" + koordinatorLapangan + "&" +
                    "supervisiOperasi=" + supervisiOperasi + "&" +
                    "username=" + username;
            context.addCallbackParam("url", StringEscapeUtils.escapeHtml3(url));
        } catch (RuntimeException re) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught when call handleDownloadReport", re);
        }
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
     * @return the equipment
     */
    public Equipment getTango() {
        return tango;
    }

    /**
     * @param equipment the equipment to set
     */
    public void setTango(Equipment equipment) {
        this.tango = equipment;
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
    public Object[] getPlanningVessell() {
        return planningVessell;
    }

    /**
     * @param planningVessell the planningVessell to set
     */
    public void setPlanningVessell(Object[] planningVessell) {
        this.planningVessell = planningVessell;
    }

    /**
     * @return the masterEquipments
     */
    public List<Object[]> getTangoList() {
        return tangoList;
    }

    /**
     * @param masterEquipments the masterEquipments to set
     */
    public void setTangoList(List<Object[]> masterEquipments) {
        this.tangoList = masterEquipments;
    }

    /**
     * @return the masterEquipments
     */
    public List<Object[]> getHeadTruckList() {
        return headTruckList;
    }

    /**
     * @param masterEquipments the masterEquipments to set
     */
    public void setHeadTruckList(List<Object[]> masterEquipmentss) {
        this.headTruckList = masterEquipmentss;
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
     * @return the equipment2
     */
    public Equipment getHaulageTruck() {
        return haulageTruck;
    }

    /**
     * @param equipment2 the equipment2 to set
     */
    public void setHaulageTruck(Equipment equipment2) {
        this.haulageTruck = equipment2;
    }

    /**
     * @return the ServiceContLoadsList
     */
    public List<Object[]> getLiftedOnContainers() {
        return liftedOnContainers;
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
     * @return the diasbleEquipment
     */
    public boolean isDiasbleHTCrane() {
        return diasbleHTCrane;
    }

    /**
     * @param diasbleEquipment the diasbleEquipment to set
     */
    public void setDiasbleHTCrane(boolean diasbleHTCrane) {
        this.diasbleHTCrane = diasbleHTCrane;
    }

    /**
     * @return the disableOptCrane
     */
    public boolean isDisableOptTrane() {
        return disableOptTrane;
    }

    /**
     * @param disableOptCrane the disableOptCrane to set
     */
    public void setDisableOptTrane(boolean disableOptTrane) {
        this.disableOptTrane = disableOptTrane;
    }

    /**
     * @return the disableOptHT
     */
    public boolean isDisableOptHT() {
        return disableOptHT;
    }

    /**
     * @param disableOptHT the disableOptHT to set
     */
    public void setDisableOptHT(boolean disableOptHT) {
        this.disableOptHT = disableOptHT;
    }

    /**
     * @return the disableTTCrane
     */
    public boolean isDisableTTCrane() {
        return disableTTCrane;
    }

    /**
     * @param disableTTCrane the disableTTCrane to set
     */
    public void setDisableTTCrane(boolean disableTTCrane) {
        this.disableTTCrane = disableTTCrane;
    }

    /**
     * @return the id
     */
    public Integer getIdDelete() {
        return idDelete;
    }

    /**
     * @param id the id to set
     */
    public void setIdDelete(Integer idDelete) {
        this.idDelete = idDelete;
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
     * @return the masterYardCoordinat
     */
    public MasterYardCoordinat getMasterYardCoordinat() {
        return masterYardCoordinat;
    }

    /**
     * @param masterYardCoordinat the masterYardCoordinat to set
     */
    public void setMasterYardCoordinat(MasterYardCoordinat masterYardCoordinat) {
        this.masterYardCoordinat = masterYardCoordinat;
    }

    /**
     * @return the tempEdit
     */
    public Object[] getTempEdit() {
        return tempEdit;
    }

    /**
     * @param tempEdit the tempEdit to set
     */
    public void setTempEdit(Object[] tempEdit) {
        this.tempEdit = tempEdit;
    }

    /**
     * @return the tempEdit2
     */
    public Object[] getTempEdit2() {
        return tempEdit2;
    }

    /**
     * @param tempEdit2 the tempEdit2 to set
     */
    public void setTempEdit2(Object[] tempEdit2) {
        this.tempEdit2 = tempEdit2;
    }

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
     * @return the uncontainerizedServiceSelect
     */
    public List<Object[]> getLiftableOnUcs() {
        return liftableOnUcs;
    }

    /**
     * @return the uncontainerizedServices
     */
    public List<Object[]> getLiftedOnUcs() {
        return liftedOnUcs;
    }

    /**
     * @return the uncontainerizedDetail
     */
    public Object[] getUncontainerizedDetail() {
        return uncontainerizedDetail;
    }

    public ReceivingService getReceivingService() {
        return receivingService;
    }

    public void setReceivingService(ReceivingService receivingService) {
        this.receivingService = receivingService;
    }

    public PlanningShiftDischarge getPlanningShiftDischarge() {
        return planningShiftDischarge;
    }

    public List<Object[]> getLiftedOnShiftContainers() {
        return liftedOnShiftContainers;
    }

    public List<Object[]> getLiftableOnShiftContainers() {
        return liftableOnShiftContainers;
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

    //uncontainerized logic
    public void handleAddUncontainerized() {
        uncontainerizedService = new UncontainerizedService();
        //inisialisasi tango
        tango = new Equipment();
        tango.setMasterEquipment(new MasterEquipment());
        tango.setMasterOperator(new MasterOperator());
        tango.setPlanningVessel(new PlanningVessel());
        uncontainerizedDetail = null;
        //inisialisasi haulaceTruck
        haulageTruck = new Equipment();
        haulageTruck.setMasterEquipment(new MasterEquipment());
        haulageTruck.setMasterOperator(new MasterOperator());
        haulageTruck.setPlanningVessel(new PlanningVessel());
    }

    public void handleEditDeleteUncontainerized(ActionEvent event) {
        int idUC = (Integer) event.getComponent().getAttributes().get("idUC");
        uncontainerizedService = uncontainerizedServiceFacadeRemote.find(idUC);
        uncontainerizedDetail = uncontainerizedServiceFacadeRemote.findDetailByIdUc(idUC);

        if (uncontainerizedService.getIsShifting().equals("TRUE")) {
            tango = equipmentFacadeRemote.findByPpkbBLNoEquipmentActCodeAndOperation(noPpkb, uncontainerizedService.getBlNo(), "LIFTON", "SHIFTING-TOCY");
            haulageTruck = equipmentFacadeRemote.findByPpkbBLNoEquipmentActCodeAndOperation(noPpkb, uncontainerizedService.getBlNo(), "H/T-RESHIPPING", "SHIFTING-TOCY");
        } else if (uncontainerizedService.getIsTranshipment().equals("TRUE")) {
            tango = equipmentFacadeRemote.findByPpkbBLNoEquipmentActCodeAndOperation(noPpkb, uncontainerizedService.getBlNo(), "LIFTON", "TRANSLOADUC");
            haulageTruck = equipmentFacadeRemote.findByPpkbBLNoEquipmentActCodeAndOperation(noPpkb, uncontainerizedService.getBlNo(), "H/T", "TRANSLOADUC");
        } else {
            tango = equipmentFacadeRemote.findByPpkbBLNoEquipmentActCodeAndOperation(noPpkb, uncontainerizedService.getBlNo(), "LIFTON", "LOADUC");
            haulageTruck = equipmentFacadeRemote.findByPpkbBLNoEquipmentActCodeAndOperation(noPpkb, uncontainerizedService.getBlNo(), "H/T", "LOADUC");
        }
    }

    public void handleSelectUncontainerized(ActionEvent event) {
        int idUC = (Integer) event.getComponent().getAttributes().get("idSelect");
        uncontainerizedService = uncontainerizedServiceFacadeRemote.find(idUC);
        uncontainerizedService.setStatus("STV");
        uncontainerizedDetail = uncontainerizedServiceFacadeRemote.findDetailByIdUc(idUC);
    }

    public UncontainerizedService getUncontainerizedService() {
        return uncontainerizedService;
    }

    public void saveUncontainerized() {
        boolean loggedIn = false;
        if (uncontainerizedService.getBlNo() == null || tango.getMasterEquipment() == null || tango.getMasterOperator() == null || tango.getStartTime() == null || tango.getEndTime() == null || haulageTruck.getMasterEquipment() == null || haulageTruck.getMasterOperator() == null) {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_FATAL, "application.save.failed");
        } else if (tango.getStartTime().after(tango.getEndTime()) || tango.getStartTime().equals(tango.getEndTime())) {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_FATAL, "application.save.failed");
        } else {
            tango.setPlanningVessel(vessel.getPlanningVessel());
            tango.setBlNo(uncontainerizedService.getBlNo());
            tango.setEquipmentActCode("LIFTON");

            haulageTruck.setPlanningVessel(vessel.getPlanningVessel());
            haulageTruck.setBlNo(uncontainerizedService.getBlNo());
            haulageTruck.setStartTime(tango.getEndTime());

            if (uncontainerizedService.getIsShifting().equals("TRUE")) {
                tango.setOperation("SHIFTING-TOCY");

                haulageTruck.setOperation("SHIFTING-TOCY");
                haulageTruck.setEquipmentActCode("H/T-RESHIPPING");

                UcShiftingService ucShiftingService = ucShiftingServiceFacadeRemote.findByNoPpkbAndBlNo(uncontainerizedService.getNoPpkb(), uncontainerizedService.getBlNo());
                ucShiftingService.setIsLanded("TRUE");
                ucShiftingServiceFacadeRemote.edit(ucShiftingService);
            } else if (uncontainerizedService.getIsTranshipment().equals("TRUE")) {
                tango.setOperation("TRANSLOADUC");

                haulageTruck.setOperation("TRANSLOADUC");
                haulageTruck.setEquipmentActCode("H/T");
            } else {
                tango.setOperation("LOADUC");
                
                haulageTruck.setOperation("LOADUC");
                haulageTruck.setEquipmentActCode("H/T");
            }

            equipmentFacadeRemote.edit(tango);
            equipmentFacadeRemote.edit(haulageTruck);
            uncontainerizedServiceFacadeRemote.edit(uncontainerizedService);
            resetForm();
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "application.save.succeeded");
            loggedIn = true;
        }
        RequestContext.getCurrentInstance().addCallbackParam("loggedIn", loggedIn);
        liftedOnUcs = uncontainerizedServiceFacadeRemote.findHasLiftedOnByNoPpkb(noPpkb);
    }

    public void deleteUC() {
        uncontainerizedService.setStatus("CY");

        if (uncontainerizedService.getIsShifting().equals("TRUE")) {
            UcShiftingService ucShiftingService = ucShiftingServiceFacadeRemote.findByNoPpkbAndBlNo(uncontainerizedService.getNoPpkb(), uncontainerizedService.getBlNo());
            ucShiftingService.setIsLanded("FALSE");
            ucShiftingServiceFacadeRemote.edit(ucShiftingService);
        }

        equipmentFacadeRemote.remove(tango);
        equipmentFacadeRemote.remove(haulageTruck);
        uncontainerizedServiceFacadeRemote.edit(uncontainerizedService);
        FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "application.delete.succeeded");
        liftedOnUcs = uncontainerizedServiceFacadeRemote.findHasLiftedOnByNoPpkb(noPpkb);
    }

    public List<Object[]> getLiftableOnContainers() {
        return liftableOnContainers;
    }

    public List<Object[]> getPlanningVessels() {
        return planningVessels;
    }

    public String getKoordinatorLapangan() {
        return koordinatorLapangan;
    }

    public void setKoordinatorLapangan(String koordinatorLapangan) {
        this.koordinatorLapangan = koordinatorLapangan;
    }

    public String getSupervisiOperasi() {
        return supervisiOperasi;
    }

    public void setSupervisiOperasi(String supervisiOperasi) {
        this.supervisiOperasi = supervisiOperasi;
    }
}
