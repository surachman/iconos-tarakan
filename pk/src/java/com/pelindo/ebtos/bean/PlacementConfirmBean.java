/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.bean;

import com.qtasnim.jsf.FacesHelper;
import com.qtasnim.util.CyValidation;
import com.pelindo.ebtos.bean.security.LoginSessionBean;
import com.pelindo.ebtos.ejb.facade.remote.CancelLoadingServiceFacadeRemote;
import com.pelindo.ebtos.exception.ContainerNotMovableException;
import com.pelindo.ebtos.exception.LocationNotAvailableException;
import com.pelindo.ebtos.exception.TimeSelectionNotValidException;
import com.pelindo.ebtos.ejb.facade.remote.EquipmentFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterCommodityFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterContainerTypeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterEquipmentFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterOperatorFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterYardFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningShiftDischargeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceContDischargeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceContTranshipmentFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceShiftingFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceVesselFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.UcShiftingServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.UncontainerizedServiceFacadeRemote;
import com.pelindo.ebtos.ejb.remote.LiftOffOperationRemote;
import com.pelindo.ebtos.model.db.CancelLoadingService;
import com.pelindo.ebtos.model.db.Equipment;
import com.pelindo.ebtos.model.db.PlanningShiftDischarge;
import com.pelindo.ebtos.model.db.PlanningVessel;
import com.pelindo.ebtos.model.db.ServiceContDischarge;
import com.pelindo.ebtos.model.db.ServiceContTranshipment;
import com.pelindo.ebtos.model.db.ServiceShifting;
import com.pelindo.ebtos.model.db.ServiceVessel;
import com.pelindo.ebtos.model.db.UcShiftingService;
import com.pelindo.ebtos.model.db.UncontainerizedService;
import com.pelindo.ebtos.model.db.master.MasterCommodity;
import com.pelindo.ebtos.model.db.master.MasterContainerType;
import com.pelindo.ebtos.model.db.master.MasterEquipment;
import com.pelindo.ebtos.model.db.master.MasterOperator;
import com.pelindo.ebtos.model.db.master.MasterYard;
import java.io.Serializable;
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
@ManagedBean(name = "placementConfirmBean")
@ViewScoped
public class PlacementConfirmBean implements Serializable {
    private static final Logger logger = Logger.getLogger(PlacementConfirmBean.class.getName());

    @EJB
    private ServiceVesselFacadeRemote serviceVesselFacadeRemote;
    @EJB
    private ServiceContDischargeFacadeRemote serviceContDischargeFacadeRemote;
    @EJB
    private MasterContainerTypeFacadeRemote masterContainerTypeFacadeRemote;
    @EJB
    private MasterCommodityFacadeRemote masterCommodityFacadeRemote;
    @EJB
    private MasterYardFacadeRemote masterYardFacadeRemote;
    @EJB
    private EquipmentFacadeRemote equipmentFacadeRemote;
    @EJB
    private MasterEquipmentFacadeRemote masterEquipmentFacadeRemote;
    @EJB
    private MasterOperatorFacadeRemote masterOperatorFacadeRemote;
    @EJB
    private UncontainerizedServiceFacadeRemote uncontainerizedServiceFacadeRemote;
    @EJB
    private ServiceContTranshipmentFacadeRemote serviceContTranshipmentFacadeRemote;
    @EJB
    private PlanningShiftDischargeFacadeRemote planningShiftDischargeFacadeRemote;
    @EJB
    private ServiceShiftingFacadeRemote serviceShiftingFacadeRemote;
    @EJB
    private CancelLoadingServiceFacadeRemote cancelLoadingServiceFacadeRemote;
    @EJB
    private UcShiftingServiceFacadeRemote ucShiftingServiceFacadeRemote;
    @EJB
    private LiftOffOperationRemote liftOffOperationRemote;

    private List<MasterOperator> masterOperators;
    private List<Object[]> serviceVesselsServiced;
    private List<Object[]> masterTangos;
    private List<Object[]> liftableOffContainers;
    private List<Object[]> liftableOffTranshipmentContainers;
    private List<Object[]> liftableOffUcs;
    private List<Object[]> liftableOffShiftingContainers;
    private List<Object[]> liftableOffCancelLoadingsContainers;
    private List<Object[]> liftedOffUcs;
    private List<Object[]> liftedOffShiftingContainers;
    private List<Object[]> liftedOffCancelLoadingContainers;
    private List<Object[]> liftedOffContainers;
    private List<Object[]> liftedOffTranshipmentContainers;
    private Map<Integer, Integer> slots;
    private Map<Integer, Integer> rows;
    private Map<Integer, Integer> tiers;
    private CyValidation cyValidation;
    private Equipment tango;
    private Equipment haulaceTruck;
    private PlanningShiftDischarge planningShiftDischarge;
    private ServiceVessel serviceVessel;
    private ServiceShifting serviceShifting;
    private ServiceContTranshipment serviceContTranshipment;
    private ServiceContDischarge serviceContDischarge;
    private UncontainerizedService uncontainerizedService;
    private CancelLoadingService cancelLoadingService;
    private Object[] serviceVesselServiced;
    private Object[] uncontainerizedDetail;
    private String counterDisch = "isi";
    private String noPPKB;
    private String opsi;
    private String type;
    private String username;
    private String koordinatorLapangan;
    private String supervisiOperasi;
    private Boolean isBottomExist = true;
    private Boolean isTopExist = false;
    private Boolean disableEquipment;
    private Boolean disableOptTango;
    private int counterSlot;
    private int counterRow;
    private int counterTier;

    public PlacementConfirmBean() {
    }

    @PostConstruct
    private void construct() {
        cyValidation = new CyValidation();

        serviceContDischarge = new ServiceContDischarge();
        serviceContDischarge.setMasterCommodity(new MasterCommodity());
        serviceContDischarge.setMasterContainerType(new MasterContainerType());
        serviceContDischarge.setMasterYard(new MasterYard());
        serviceContDischarge.setServiceVessel(new ServiceVessel());

        serviceContTranshipment = new ServiceContTranshipment();
        serviceContTranshipment.setMasterCommodity(new MasterCommodity());
        serviceContTranshipment.setMasterContainerType(new MasterContainerType());
        serviceContTranshipment.setMasterYard(new MasterYard());
        serviceContTranshipment.setServiceVessel(new ServiceVessel());
        serviceShifting = new ServiceShifting();
        planningShiftDischarge = new PlanningShiftDischarge();
        
        tango = new Equipment();
        tango.setMasterEquipment(new MasterEquipment());
        tango.setMasterOperator(new MasterOperator());
        tango.setPlanningVessel(new PlanningVessel());

        disableEquipment = true;
        disableOptTango = true;

        Object[] mYard = masterYardFacadeRemote.findMasterYardByBlock("A");
        counterSlot = ((Number) mYard[0]).intValue();
        counterRow = ((Number) mYard[1]).intValue();
        counterTier = ((Number) mYard[2]).intValue();
        slots = new LinkedHashMap<Integer, Integer>();
        rows = new LinkedHashMap<Integer, Integer>();
        tiers = new LinkedHashMap<Integer, Integer>();
        this.counterDisch = "isi";

        LoginSessionBean session = LoginSessionBean.getCurrentInstance();
        username = session.getUsername();
    }

    public void handleShowVessels(ActionEvent event) {
        serviceVesselsServiced = serviceVesselFacadeRemote.findServiceVesselServicing();
    }

    public void handleShowTangoOperators(ActionEvent event) {
        masterOperators = masterOperatorFacadeRemote.findAll();
    }

    public void handleShowTangos(ActionEvent event) {
        masterTangos = masterEquipmentFacadeRemote.findTangoForView();
    }

    public void handleRefreshLiftedOffContainers(ActionEvent event) {
        liftedOffContainers = serviceContDischargeFacadeRemote.findServiceContDischargesConfirmServiced(noPPKB);
        refreshTable();
    }

    public void handleRefreshLiftedOffShiftContainers(ActionEvent event) {
        liftedOffShiftingContainers = serviceShiftingFacadeRemote.findByPpkbLiftOffList(noPPKB);
        refreshTable();
    }

    public void handleRefreshLiftedOffTranshipmentContainers(ActionEvent event) {
        liftedOffTranshipmentContainers = serviceContTranshipmentFacadeRemote.findServiceContTranshipmentsConfirmServed(noPPKB);
        refreshTable();
    }

    public void handleRefreshLiftedOffCancelLoadingContainers(ActionEvent event) {
        liftedOffCancelLoadingContainers = cancelLoadingServiceFacadeRemote.findHasLiftedOffByNoPpkb(noPPKB);
        refreshTable();
    }

    public void handleRefreshLiftedOffUcs(ActionEvent event) {
        liftedOffUcs = uncontainerizedServiceFacadeRemote.findHasLiftedOffByNoPpkb(noPPKB);
    }

    public void handleShowLiftableOffContainers(ActionEvent event) {
        tango.setStartTime(equipmentFacadeRemote.findByStartTimeData());
        tango.setEndTime(equipmentFacadeRemote.findByEndTimeData());
        
        liftableOffContainers = serviceContDischargeFacadeRemote.findServiceContDischargesSelectServiced(noPPKB);
    }

    public void handleShowLiftableOffCancelLoadingContainers(ActionEvent event) {
        tango.setStartTime(equipmentFacadeRemote.findByStartTimeData());
        tango.setEndTime(equipmentFacadeRemote.findByEndTimeData());
        liftableOffCancelLoadingsContainers = cancelLoadingServiceFacadeRemote.findLiftableOffByNoPpkb(noPPKB);
    }

    public void handleShowLiftableOffShiftingContainers(ActionEvent event) {
        
        tango.setStartTime(equipmentFacadeRemote.findByStartTimeData());
        tango.setEndTime(equipmentFacadeRemote.findByEndTimeData());
        liftableOffShiftingContainers = serviceShiftingFacadeRemote.findByPpkbLiftOffSelect(noPPKB);
    }

    public void handleShowLiftableOffTranshipmentContainers(ActionEvent event) {
        tango.setStartTime(equipmentFacadeRemote.findByStartTimeData());
        tango.setEndTime(equipmentFacadeRemote.findByEndTimeData());
        liftableOffTranshipmentContainers = serviceContTranshipmentFacadeRemote.findServiceContTranshipmentsSelectServed(noPPKB);
    }

    public void handleShowLiftableOffUcs(ActionEvent event) {
        tango.setStartTime(equipmentFacadeRemote.findByStartTimeData());
        tango.setEndTime(equipmentFacadeRemote.findByEndTimeData());
        liftableOffUcs = uncontainerizedServiceFacadeRemote.findLiftableOffByNoPpkb(noPPKB);
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

    public void handleSelectNoPpkb(ActionEvent event) {
        noPPKB = (String) event.getComponent().getAttributes().get("noPpkb");
        serviceVessel = serviceVesselFacadeRemote.find(noPPKB);
        serviceVesselServiced = serviceVesselFacadeRemote.findServiceVesselByPpkb(noPPKB);
        liftedOffContainers = serviceContDischargeFacadeRemote.findServiceContDischargesConfirmServiced(noPPKB);
        liftedOffTranshipmentContainers = serviceContTranshipmentFacadeRemote.findServiceContTranshipmentsConfirmServed(noPPKB);
        liftedOffUcs = uncontainerizedServiceFacadeRemote.findHasLiftedOffByNoPpkb(noPPKB);
        liftedOffShiftingContainers = serviceShiftingFacadeRemote.findByPpkbLiftOffList(noPPKB);
        liftedOffCancelLoadingContainers = cancelLoadingServiceFacadeRemote.findHasLiftedOffByNoPpkb(noPPKB);
        refreshTable();
    }

    public void handleSelectLiftableOffCancelLoading(ActionEvent event) {
        String jobslip = (String) event.getComponent().getAttributes().get("jobslip");
        cancelLoadingService = cancelLoadingServiceFacadeRemote.find(jobslip);
    }

    public void refreshTable() {
        for (Object[] oDischarge : liftedOffContainers) {
            isTopExist = cyValidation.isTopExist((String) oDischarge[6], Short.valueOf(((Integer) oDischarge[7]).toString()), Short.valueOf(((Integer) oDischarge[8]).toString()), Short.valueOf(((Integer) oDischarge[9]).toString()), Short.valueOf(((Integer) oDischarge[1]).toString()));
            oDischarge[11] = isTopExist;
        }

        for (Object[] oTrans : liftedOffTranshipmentContainers) {
            isTopExist = cyValidation.isTopExist((String) oTrans[6], Short.valueOf(((Integer) oTrans[7]).toString()), Short.valueOf(((Integer) oTrans[8]).toString()), Short.valueOf(((Integer) oTrans[9]).toString()), Short.valueOf(((Integer) oTrans[1]).toString()));
            oTrans[15] = isTopExist;
        }

        for (Object[] oShift : liftedOffShiftingContainers) {
            isTopExist = cyValidation.isTopExist((String) oShift[6], Short.valueOf(((Integer) oShift[7]).toString()), Short.valueOf(((Integer) oShift[8]).toString()), Short.valueOf(((Integer) oShift[9]).toString()), Short.valueOf(((Integer) oShift[1]).toString()));
            oShift[11] = isTopExist;
        }

        for (Object[] cancelLoading : liftedOffCancelLoadingContainers) {
            isTopExist = cyValidation.isTopExist((String) cancelLoading[9], Short.valueOf(((Integer) cancelLoading[10]).toString()), Short.valueOf(((Integer) cancelLoading[11]).toString()), Short.valueOf(((Integer) cancelLoading[12]).toString()), Short.valueOf(((Integer) cancelLoading[7]).toString()));
            cancelLoading[14] = isTopExist;
        }
    }

    public void handleSelectNoContainer(ActionEvent event) {
        int noContainer = (Integer) event.getComponent().getAttributes().get("noContainer");
        serviceContDischarge = serviceContDischargeFacadeRemote.find(noContainer);
        disableEquipment = false;
    }

    public void handleSelectNoContainerShifting(ActionEvent event) {
        int idPlanning = (Integer) event.getComponent().getAttributes().get("idPlanning");
        int idService = (Integer) event.getComponent().getAttributes().get("idService");
        planningShiftDischarge = planningShiftDischargeFacadeRemote.find(idPlanning);
        serviceShifting = serviceShiftingFacadeRemote.find(idService);
        System.out.println("test if " + planningShiftDischarge.getContNo());
        disableEquipment = false;
    }

    public void handleSelectTango(ActionEvent event) {
        String idTango = (String) event.getComponent().getAttributes().get("idTango");
        tango.setMasterEquipment(masterEquipmentFacadeRemote.find(idTango));
        disableOptTango = false;
    }

    public void handleSelectOperatorTango(ActionEvent event) {
        String idOperatorTango = (String) event.getComponent().getAttributes().get("idOperatorTango");
        tango.setMasterOperator(masterOperatorFacadeRemote.find(idOperatorTango));
    }
    
    public void resetForm() {
        
        serviceContDischarge= new ServiceContDischarge();
        serviceContTranshipment= new ServiceContTranshipment();
        uncontainerizedService= new UncontainerizedService();
        planningShiftDischarge= new PlanningShiftDischarge();
        cancelLoadingService= new CancelLoadingService();
        uncontainerizedDetail= null;
        
    }
    
    public void saveEdit(ActionEvent event) {
        boolean isValid = false;
        RequestContext requestContext = RequestContext.getCurrentInstance();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        
        try {
            liftOffOperationRemote.handleLiftOffConfirmDischargeContainer(serviceContDischarge, tango);
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
            isValid = true;
            resetForm();
        } catch (LocationNotAvailableException ex) {
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_ERROR, "Error", "application.validation.location_not_available");
        } catch (TimeSelectionNotValidException ex) {
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_ERROR, "Error", "application.validation.time");
        } catch (Exception ex) {
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_ERROR, "Error", "application.save.failed");
            logger.log(Level.SEVERE, null, ex);
        }

        liftedOffContainers = serviceContDischargeFacadeRemote.findServiceContDischargesConfirmServiced(noPPKB);
        refreshTable();
        
        requestContext.addCallbackParam("loggedIn", isValid);
    }

    public void handleCancelLoadingLiftOffConfirm(ActionEvent event) {
        final RequestContext requestContext = RequestContext.getCurrentInstance();
        final FacesContext facesContext = FacesContext.getCurrentInstance();
        boolean isValid = false;

        try {
            liftOffOperationRemote.handleLiftOffConfirmCancelLoadingContainer(cancelLoadingService, tango);
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
            isValid = true;
        } catch (LocationNotAvailableException ex) {
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_ERROR, "Error", "application.validation.location_not_available");
        } catch (TimeSelectionNotValidException ex) {
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_ERROR, "Error", "application.validation.time");
        } catch (Exception ex) {
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_ERROR, "Error", "application.save.failed");
            logger.log(Level.SEVERE, null, ex);
        }

        liftedOffCancelLoadingContainers = cancelLoadingServiceFacadeRemote.findHasLiftedOffByNoPpkb(noPPKB);
        refreshTable();
        requestContext.addCallbackParam("loggedIn", isValid);
    }

    public void saveEditShifting(ActionEvent event) {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        boolean isValid = false;

        try {
            liftOffOperationRemote.handleLiftOffConfirmShiftingContainer(serviceShifting, planningShiftDischarge, tango);
            planningShiftDischarge = new PlanningShiftDischarge();
            serviceShifting = new ServiceShifting();
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
            isValid = true;
        } catch (LocationNotAvailableException ex) {
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_ERROR, "Error", "application.validation.location_not_available");
        } catch (TimeSelectionNotValidException ex) {
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_ERROR, "Error", "application.validation.time");
        } catch (Exception ex) {
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_ERROR, "Error", "application.save.failed");
            logger.log(Level.SEVERE, null, ex);
        }

        liftedOffShiftingContainers = serviceShiftingFacadeRemote.findByPpkbLiftOffList(noPPKB);
        refreshTable();

        requestContext.addCallbackParam("loggedIn", isValid);
    }

    public void deleteShift(ActionEvent event) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        
        try {
            liftOffOperationRemote.handleCancelLiftOffConfirmShiftingContainer(serviceShifting, planningShiftDischarge, tango);
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_INFO, "Info", "application.delete.succeeded");
        } catch (ContainerNotMovableException ex) {
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_ERROR, "Error", "application.validation.cant_move_container");
        } catch (Exception ex) {
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_ERROR, "Error", "application.delete.failed");
            logger.log(Level.SEVERE, null, ex);
        }

        liftedOffShiftingContainers = serviceShiftingFacadeRemote.findByPpkbLiftOffList(noPPKB);
        refreshTable();
    }

    public void deleteTranshipment(ActionEvent event) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        
        try {
            liftOffOperationRemote.handleCancelLiftOffConfirmTranshipmentContainer(serviceContTranshipment, tango);
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_INFO, "Info", "application.delete.succeeded");
        } catch (ContainerNotMovableException ex) {
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_ERROR, "Error", "application.validation.cant_move_container");
        } catch (Exception ex) {
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_ERROR, "Error", "application.delete.failed");
            logger.log(Level.SEVERE, null, ex);
        }

        liftedOffTranshipmentContainers = serviceContTranshipmentFacadeRemote.findServiceContTranshipmentsConfirmServed(noPPKB);
        refreshTable();
    }

    public void handleDeleteLiftedOffCancelLoading(ActionEvent event) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        
        try {
            liftOffOperationRemote.handleCancelLiftOffConfirmCancelLoadingContainer(cancelLoadingService, tango);
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_INFO, "Info", "application.delete.succeeded");
        } catch (ContainerNotMovableException ex) {
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_ERROR, "Error", "application.validation.cant_move_container");
        } catch (Exception ex) {
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_ERROR, "Error", "application.delete.failed");
            logger.log(Level.SEVERE, null, ex);
        }
        
        liftedOffCancelLoadingContainers = cancelLoadingServiceFacadeRemote.findHasLiftedOffByNoPpkb(noPPKB);
        refreshTable();
    }

    public void delete(ActionEvent event) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        
        try {
            liftOffOperationRemote.handleCancelLiftOffConfirmDischargeContainer(serviceContDischarge, tango);
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_INFO, "Info", "application.delete.succeeded");
        } catch (ContainerNotMovableException ex) {
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_ERROR, "Error", "application.validation.cant_move_container");
        } catch (Exception ex) {
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_ERROR, "Error", "application.delete.failed");
            logger.log(Level.SEVERE, null, ex);
        }

        liftedOffContainers = serviceContDischargeFacadeRemote.findServiceContDischargesConfirmServiced(noPPKB);
        refreshTable();
    }

    public void handleEditDeleteButton(ActionEvent event) {
        int idContainer = (Integer) event.getComponent().getAttributes().get("idCont");
        serviceContDischarge = serviceContDischargeFacadeRemote.find(idContainer);
        int idTango = equipmentFacadeRemote.findByIdContainer(noPPKB, serviceContDischarge.getContNo(), "LIFTOFF", "DISCHARGE");
        tango = equipmentFacadeRemote.find(idTango);
        disableEquipment = false;
        disableOptTango = false;
        opsi = "edit";
        //mengkondisikan cy
        Object[] mYard = masterYardFacadeRemote.findMasterYardByBlock(serviceContDischarge.getMasterYard().getBlock());
        counterSlot = (Integer) mYard[0];
        counterRow = (Integer) mYard[1];
        counterTier = (Integer) mYard[2];
        slots = new LinkedHashMap<Integer, Integer>();
        rows = new LinkedHashMap<Integer, Integer>();
        tiers = new LinkedHashMap<Integer, Integer>();
        counterDisch = "no";
        type = "container";
    }

    public void handleEditDeleteButtonCancel(ActionEvent event) {
        String idContainer = (String) event.getComponent().getAttributes().get("jobslip");
        cancelLoadingService = cancelLoadingServiceFacadeRemote.find(idContainer);
        tango = equipmentFacadeRemote.findByEquipmentActCodeAndOperation(noPPKB, cancelLoadingService.getContNo(), "LIFTOFF", "DISCHARGE");
        disableEquipment = false;
        disableOptTango = false;
        opsi = "edit";
        Object[] mYard = masterYardFacadeRemote.findMasterYardByBlock(cancelLoadingService.getMasterYard().getBlock());
        counterSlot = (Integer) mYard[0];
        counterRow = (Integer) mYard[1];
        counterTier = (Integer) mYard[2];
        slots = new LinkedHashMap<Integer, Integer>();
        rows = new LinkedHashMap<Integer, Integer>();
        tiers = new LinkedHashMap<Integer, Integer>();
        counterDisch = "no";
        type = "cancelLoadingContainer";
    }

    private void initDisch() {
        serviceContDischarge = new ServiceContDischarge();
        serviceContDischarge.setMasterCommodity(new MasterCommodity());
        serviceContDischarge.setMasterContainerType(new MasterContainerType());
        serviceContDischarge.setMasterYard(new MasterYard());
        serviceContDischarge.setServiceVessel(new ServiceVessel());
        //inisialisasi tango
        tango = new Equipment();
        tango.setMasterEquipment(new MasterEquipment());
        tango.setMasterOperator(new MasterOperator());
        tango.setPlanningVessel(new PlanningVessel());
        //inisialisasi ht
        disableEquipment = true;
        disableOptTango = true;
        this.counterDisch = "isi";

        //shifting
        planningShiftDischarge = new PlanningShiftDischarge();
        serviceShifting = new ServiceShifting();
    }

    private void initCancelLoadingService() {
        cancelLoadingService = new CancelLoadingService();
        
        tango = new Equipment();
        tango.setMasterEquipment(new MasterEquipment());
        tango.setMasterOperator(new MasterOperator());
        tango.setPlanningVessel(new PlanningVessel());

        disableEquipment = true;
        disableOptTango = true;
        counterDisch = "no";
    }

    public void handleEditDeleteButtonShifting(ActionEvent event) {
        int idPlanning = (Integer) event.getComponent().getAttributes().get("idPlanning");
        int idService = (Integer) event.getComponent().getAttributes().get("idService");
        planningShiftDischarge = planningShiftDischargeFacadeRemote.find(idPlanning);
        serviceShifting = serviceShiftingFacadeRemote.find(idService);
        int idTango = equipmentFacadeRemote.findByIdContainer(noPPKB, planningShiftDischarge.getContNo(), "LIFTOFF", "SHIFTING-CY");
        tango = equipmentFacadeRemote.find(idTango);
        disableEquipment = false;
        disableOptTango = false;
        opsi = "edit";
        //mengkondisikan cy
        Object[] mYard = masterYardFacadeRemote.findMasterYardByBlock(planningShiftDischarge.getMasterYard().getBlock());
        counterSlot = (Integer) mYard[0];
        counterRow = (Integer) mYard[1];
        counterTier = (Integer) mYard[2];
        slots = new LinkedHashMap<Integer, Integer>();
        rows = new LinkedHashMap<Integer, Integer>();
        tiers = new LinkedHashMap<Integer, Integer>();
        this.counterDisch = "no";
        type = "shiftContainer";
    }

    public void handleButtonAdd(ActionEvent event) {
        initDisch();
        opsi = "add";
    }

    public void handleAddLiftableOffCancelLoading(ActionEvent event) {
        initCancelLoadingService();
        opsi = "add";
    }

    public void handleDeleteAll(ActionEvent event) {
        opsi = "deleteall";
    }

    /**
     * @return the serviceContTranshipmentServed
     */
    public List<Object[]> getLiftedOffTranshipmentContainers() {
        return liftedOffTranshipmentContainers;
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

    private void initTransh() {
        serviceContTranshipment = new ServiceContTranshipment();
        serviceContTranshipment.setMasterCommodity(new MasterCommodity());
        serviceContTranshipment.setMasterContainerType(new MasterContainerType());
        serviceContTranshipment.setMasterYard(new MasterYard());
        serviceContTranshipment.setServiceVessel(new ServiceVessel());
        //inisialisasi tango
        tango = new Equipment();
        tango.setMasterEquipment(new MasterEquipment());
        tango.setMasterOperator(new MasterOperator());
        tango.setPlanningVessel(new PlanningVessel());
        //disabled button
        disableEquipment = true;
        disableOptTango = true;
    }

    public void handleButtonAddTranshipment(ActionEvent event) {
        initTransh();
        opsi = "addTrans";
    }

    public void saveEditTranshipment(ActionEvent event) {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        boolean isValid = false;

        try {
            liftOffOperationRemote.handleLiftOffConfirmTranshipmentContainer(serviceContTranshipment, tango);
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
            isValid = true;
            resetForm();
        } catch (LocationNotAvailableException ex) {
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_ERROR, "Error", "application.validation.location_not_available");
        } catch (TimeSelectionNotValidException ex) {
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_ERROR, "Error", "application.validation.time");
        } catch (Exception ex) {
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_ERROR, "Error", "application.save.failed");
            logger.log(Level.SEVERE, null, ex);
        }

        liftedOffTranshipmentContainers = serviceContTranshipmentFacadeRemote.findServiceContTranshipmentsConfirmServed(noPPKB);
        refreshTable();

        serviceContTranshipment = new ServiceContTranshipment();
        serviceContTranshipment.setMasterCommodity(new MasterCommodity());
        serviceContTranshipment.setMasterContainerType(new MasterContainerType());
        serviceContTranshipment.setMasterYard(new MasterYard());
        serviceContTranshipment.setServiceVessel(new ServiceVessel());
        serviceShifting = new ServiceShifting();
        planningShiftDischarge = new PlanningShiftDischarge();

        tango = new Equipment();
        tango.setMasterEquipment(new MasterEquipment());
        tango.setMasterOperator(new MasterOperator());
        tango.setPlanningVessel(new PlanningVessel());
        
        requestContext.addCallbackParam("loggedIn", isValid);
    }

    public void handleEditDeleteButtonTranshipment(ActionEvent event) {
        int idContTranshipment = (Integer) event.getComponent().getAttributes().get("idContTranshipment");
        serviceContTranshipment = serviceContTranshipmentFacadeRemote.find(idContTranshipment);
        int idTango = equipmentFacadeRemote.findByIdContainer(noPPKB, serviceContTranshipment.getContNo(), "LIFTOFF", "TRANSDISCHARGE");
        tango = equipmentFacadeRemote.find(idTango);
        disableEquipment = false;
        disableOptTango = false;
        opsi = "editTrans";
        //mengkondisikan yard opsi
        Object[] mYard = masterYardFacadeRemote.findMasterYardByBlock(serviceContTranshipment.getMasterYard().getBlock());
        counterSlot = (Integer) mYard[0];
        counterRow = (Integer) mYard[1];
        counterTier = (Integer) mYard[2];
        slots = new LinkedHashMap<Integer, Integer>();
        rows = new LinkedHashMap<Integer, Integer>();
        tiers = new LinkedHashMap<Integer, Integer>();
        type = "transhipmentContainer";
    }

    public void handleSelectNoContainerTranshipment(ActionEvent event) {
        int noContTranshipment = (Integer) event.getComponent().getAttributes().get("noContTranshipment");
        serviceContTranshipment = serviceContTranshipmentFacadeRemote.find(noContTranshipment);
        if (serviceContTranshipment.getMasterYard() == null) {
            serviceContTranshipment.setMasterYard(new MasterYard());
        }
        disableEquipment = false;
    }

    public void handleDownloadReport(ActionEvent event) {
        try {
            RequestContext context = RequestContext.getCurrentInstance();

            context.addCallbackParam("doPrint", true); //harus ada, buat ngeflag di izinkan ngeprint ato ngga
            String url = "../../../DischargeYardActivityReport.pdf?" +
                    "noPpkb=" + serviceVessel.getNoPpkb() + "&" +
                    "koordinatorLapangan=" + koordinatorLapangan + "&" +
                    "supervisiOperasi=" + supervisiOperasi + "&" +
                    "username=" + username;
            context.addCallbackParam("url", StringEscapeUtils.escapeHtml3(url));
        } catch (RuntimeException re) {
            logger.log(Level.SEVERE, "exception caught when call handleDownloadReport", re);
        }
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

    /**
     * @return the uncontainerizedServiceSelect
     */
    public List<Object[]> getLiftableOffUcs() {
        return liftableOffUcs;
    }

    /**
     * @return the uncontainerizedServices
     */
    public List<Object[]> getLiftedOffUcs() {
        return liftedOffUcs;
    }

    /**
     * @return the uncontainerizedDetail
     */
    public Object[] getUncontainerizedDetail() {
        return uncontainerizedDetail;
    }

    //uncontainerized logic
    public void handleAddUncontainerized() {
        setUncontainerizedService(new UncontainerizedService());
        //inisialisasi tango
        tango = new Equipment();
        tango.setMasterEquipment(new MasterEquipment());
        tango.setMasterOperator(new MasterOperator());
        tango.setPlanningVessel(new PlanningVessel());
        haulaceTruck = new Equipment();
        disableEquipment = true;
        disableOptTango = true;
        uncontainerizedDetail = null;
        opsi = "addUC";
    }

    public void handleEditDeleteUncontainerized(ActionEvent event) {
        int idUC = (Integer) event.getComponent().getAttributes().get("idUC");
        
        uncontainerizedService = uncontainerizedServiceFacadeRemote.find(idUC);
        uncontainerizedDetail = uncontainerizedServiceFacadeRemote.findDetailByIdUc(idUC);

        if (uncontainerizedService.getIsShifting().equals("TRUE")) {
            tango = equipmentFacadeRemote.findByPpkbBLNoEquipmentActCodeAndOperation(noPPKB, uncontainerizedService.getBlNo(), "LIFTOFF", "SHIFTING-TOCY");
            haulaceTruck = equipmentFacadeRemote.findByPpkbBLNoEquipmentActCodeAndOperation(noPPKB, uncontainerizedService.getBlNo(), "H/T", "SHIFTING-TOCY");
        } else if (uncontainerizedService.getIsTranshipment().equals("TRUE")) {
            tango = equipmentFacadeRemote.findByPpkbBLNoEquipmentActCodeAndOperation(noPPKB, uncontainerizedService.getBlNo(), "LIFTOFF", "TRANSDISCUC");
            haulaceTruck = equipmentFacadeRemote.findByPpkbBLNoEquipmentActCodeAndOperation(noPPKB, uncontainerizedService.getBlNo(), "H/T", "TRANSDISCUC");
        } else {
            tango = equipmentFacadeRemote.findByPpkbBLNoEquipmentActCodeAndOperation(noPPKB, uncontainerizedService.getBlNo(), "LIFTOFF", "DISCHARGEUC");
            haulaceTruck = equipmentFacadeRemote.findByPpkbBLNoEquipmentActCodeAndOperation(noPPKB, uncontainerizedService.getBlNo(), "H/T", "DISCHARGEUC");
        }

        disableEquipment = false;
        disableOptTango = false;
        opsi = "editUC";
    }

    public void handleSelectUncontainerized(ActionEvent event) {
        int idUC = (Integer) event.getComponent().getAttributes().get("idSelect");
        
        uncontainerizedService = uncontainerizedServiceFacadeRemote.find(idUC);
        uncontainerizedService.setStatus("CY");
        uncontainerizedService.setPlacementDate(new Date());
        uncontainerizedDetail = uncontainerizedServiceFacadeRemote.findDetailByIdUc(idUC);
        disableEquipment = false;
    }

    public void saveUncontainerized(ActionEvent event) {
        boolean loggedIn = false;

        if (uncontainerizedService.getBlNo() == null || tango.getMasterEquipment() == null || tango.getMasterOperator() == null || tango.getStartTime() == null || tango.getEndTime() == null) {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_FATAL, "application.save.failed");
        } else if (tango.getStartTime().after(tango.getEndTime()) || tango.getStartTime().equals(tango.getEndTime())) {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_FATAL, "application.save.failed");
        } else {
            tango.setPlanningVessel(serviceVessel.getPlanningVessel());
            tango.setBlNo(uncontainerizedService.getBlNo());
            tango.setEquipmentActCode("LIFTOFF");

            if (uncontainerizedService.getIsShifting().equals("TRUE")) {
                haulaceTruck = equipmentFacadeRemote.findByPpkbBLNoEquipmentActCodeAndOperation(noPPKB, uncontainerizedService.getBlNo(), "H/T", "SHIFTING-TOCY");
                tango.setOperation("SHIFTING-TOCY");

                UcShiftingService ucShiftingService = ucShiftingServiceFacadeRemote.findByNoPpkbAndBlNo(uncontainerizedService.getNoPpkb(), uncontainerizedService.getBlNo());

                ucShiftingService.setIsLanded("TRUE");
                ucShiftingServiceFacadeRemote.edit(ucShiftingService);
            } else if (uncontainerizedService.getIsTranshipment().equals("TRUE")) {
                haulaceTruck = equipmentFacadeRemote.findByPpkbBLNoEquipmentActCodeAndOperation(noPPKB, uncontainerizedService.getBlNo(), "H/T", "TRANSDISCUC");
                tango.setOperation("TRANSDISCUC");

                UncontainerizedService transLoad = uncontainerizedServiceFacadeRemote.findByNoPpkbAndBlNo(uncontainerizedService.getNewPpkb(), uncontainerizedService.getBlNo());

                if (transLoad == null) {
                    transLoad = new UncontainerizedService();
                }

                transLoad.setBayPlan(uncontainerizedService.getBayPlan());
                transLoad.setBlNo(uncontainerizedService.getBlNo());
                transLoad.setBlock(uncontainerizedService.getBlock());
                transLoad.setCommodity(uncontainerizedService.getCommodity());
                transLoad.setCrane(uncontainerizedService.getCrane());
                transLoad.setCubication(uncontainerizedService.getCubication());
                transLoad.setDischPort(uncontainerizedService.getDischPort());
                transLoad.setIsDelivery(uncontainerizedService.getIsDelivery());
                transLoad.setIsExportImport(uncontainerizedService.getIsExportImport());
                transLoad.setIsShifting(uncontainerizedService.getIsShifting());
                transLoad.setIsTranshipment(uncontainerizedService.getIsTranshipment());
                transLoad.setLoadPort(uncontainerizedService.getLoadPort());
                transLoad.setNoPpkb(uncontainerizedService.getNewPpkb());
                transLoad.setOperation("LOAD");
                transLoad.setPlacementDate(uncontainerizedService.getPlacementDate());
                transLoad.setStatus(UncontainerizedService.STATUS_CY);
                transLoad.setTruckLoosing("FALSE");
                transLoad.setUnit(uncontainerizedService.getUnit());
                transLoad.setWeight(uncontainerizedService.getWeight());

                uncontainerizedServiceFacadeRemote.edit(transLoad);
            } else {
                haulaceTruck = equipmentFacadeRemote.findByPpkbBLNoEquipmentActCodeAndOperation(noPPKB, uncontainerizedService.getBlNo(), "H/T", "DISCHARGEUC");
                tango.setOperation("DISCHARGEUC");
            }

            haulaceTruck.setEndTime(tango.getStartTime());
            
            equipmentFacadeRemote.edit(haulaceTruck);
            equipmentFacadeRemote.edit(tango);
            uncontainerizedServiceFacadeRemote.edit(uncontainerizedService);
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "application.save.succeeded");
            loggedIn = true;
        }
        
        RequestContext.getCurrentInstance().addCallbackParam("loggedIn", loggedIn);
        liftedOffUcs = uncontainerizedServiceFacadeRemote.findHasLiftedOffByNoPpkb(noPPKB);
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

    public String getCounterDisch() {
        return counterDisch;
    }

    public void setCounterDisch(String counterDisch) {
        this.counterDisch = counterDisch;
    }

    public void deleteUC() {
        if (opsi.equalsIgnoreCase("editUC")) {
            uncontainerizedService.setStatus("STV");
            uncontainerizedService.setPlacementDate(null);
            haulaceTruck.setEndTime(null);

            if (uncontainerizedService.getIsTranshipment().equals("TRUE")) {
                UncontainerizedService transLoad = uncontainerizedServiceFacadeRemote.findByNoPpkbAndBlNo(uncontainerizedService.getNewPpkb(), uncontainerizedService.getBlNo());
                uncontainerizedServiceFacadeRemote.remove(transLoad);
            }

            uncontainerizedServiceFacadeRemote.edit(uncontainerizedService);
            equipmentFacadeRemote.remove(tango);
            equipmentFacadeRemote.edit(haulaceTruck);
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "application.delete.succeeded");
        }
        
        liftedOffUcs = uncontainerizedServiceFacadeRemote.findHasLiftedOffByNoPpkb(noPPKB);
    }

    //render CY
    public void onChangeEvent(ValueChangeEvent event) {
        MasterYard newItem = (MasterYard) event.getNewValue();
        Short slot = newItem.getSlot();
        Short row = newItem.getRow();
        Short tier = newItem.getTier();
        
        getSlots().clear();
        getRows().clear();
        getTiers().clear();
        
        counterSlot = slot;
        counterRow = row;
        counterTier = tier;
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
     * @return the rows
     */
    public Map<Integer, Integer> getRows() {
        for (int i = 1; i <= counterRow; i++) {
            rows.put(i, i);
        }
        return rows;
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

    public String getOpsi() {
        return opsi;
    }

    /**
     * @return the isTopExist
     */
    public Boolean getIsTopExist() {
        return isTopExist;
    }

    /**
     * @param isTopExist the isTopExist to set
     */
    public void setIsTopExist(Boolean isTopExist) {
        this.isTopExist = isTopExist;
    }

    public PlanningShiftDischarge getPlanningShiftDischarge() {
        return planningShiftDischarge;
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

    public List<Object[]> getLiftableOffShiftingContainers() {
        return liftableOffShiftingContainers;
    }

    public List<Object[]> getLiftedOffShiftingContainers() {
        return liftedOffShiftingContainers;
    }

    /**
     * @return the serviceContTranshipmentSelectTranshipment
     */
    public List<Object[]> getLiftableOffTranshipmentContainers() {
        return liftableOffTranshipmentContainers;
    }

    /**
     * @return the serviceVesselsServiced
     */
    public List<Object[]> getServiceVesselsServiced() {
        return serviceVesselsServiced;
    }

    /**
     * @return the serviceVesselServiced
     */
    public Object[] getServiceVesselServiced() {
        return serviceVesselServiced;
    }

    /**
     * @return the serviceContDischargeServiced
     */
    public List<Object[]> getLiftedOffContainers() {
        return liftedOffContainers;
    }

    /**
     * @return the serviceContDischarge
     */
    public ServiceContDischarge getServiceContDischarge() {
        return serviceContDischarge;
    }

    /**
     * @param serviceContDischarge the serviceContDischarge to set
     */
    public void setServiceContDischarge(ServiceContDischarge serviceContDischarge) {
        this.serviceContDischarge = serviceContDischarge;
    }

    /**
     * @return the masterContainerTypes
     */
    public List<MasterContainerType> getMasterContainerTypes() {
        return masterContainerTypeFacadeRemote.findAll();
    }

    /**
     * @return the masterCommoditys
     */
    public List<MasterCommodity> getMasterCommoditys() {
        return masterCommodityFacadeRemote.findAll();
    }

    /**
     * @return the masterOperators
     */
    public List<MasterOperator> getMasterOperators() {
        return masterOperators;
    }

    /**
     * @return the serviceContDischargeSelect
     */
    public List<Object[]> getLiftableOffContainers() {
        return liftableOffContainers;
    }

    /**
     * @return the tango
     */
    public Equipment getTango() {
        return tango;
    }

    /**
     * @param tango the tango to set
     */
    public void setTango(Equipment tango) {
        this.tango = tango;
    }

    /**
     * @return the masterTangos
     */
    public List<Object[]> getMasterTangos() {
        return masterTangos;
    }

    /**
     * @return the disableOptTango
     */
    public boolean isDisableOptTango() {
        return disableOptTango;
    }

    /**
     * @return the disableEquipment
     */
    public boolean isDisableEquipment() {
        return disableEquipment;
    }

    public List<Object[]> getLiftedOffCancelLoadingContainers() {
        return liftedOffCancelLoadingContainers;
    }

    public List<Object[]> getLiftableOffCancelLoadingsContainers() {
        return liftableOffCancelLoadingsContainers;
    }

    public String getType() {
        return type;
    }

    public CancelLoadingService getCancelLoadingService() {
        return cancelLoadingService;
    }

    public void setCancelLoadingService(CancelLoadingService cancelLoadingService) {
        this.cancelLoadingService = cancelLoadingService;
    }
}
