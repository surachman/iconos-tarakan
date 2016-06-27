/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.bean;

import com.qtasnim.jsf.FacesHelper;
import com.pelindo.ebtos.ejb.ContainerMovementOperation;
import com.pelindo.ebtos.ejb.facade.remote.CancelLoadingServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ContainerMovementHistoryFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.EquipmentContainerMovementFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterContainerFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterEquipmentFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterOperatorFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterYardFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningContLoadFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceContDischargeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceReceivingFacadeRemote;
import com.pelindo.ebtos.ejb.remote.ContainerMovementOperationRemote;
import com.pelindo.ebtos.exception.ContainerNotMovableException;
import com.pelindo.ebtos.exception.LocationNotAvailableException;
import com.pelindo.ebtos.exception.TimeSelectionNotValidException;
import com.pelindo.ebtos.model.db.CancelLoadingService;
import com.pelindo.ebtos.model.db.ContainerMovementHistory;
import com.pelindo.ebtos.model.db.EquipmentContainerMovement;
import com.pelindo.ebtos.model.db.PlanningContLoad;
import com.pelindo.ebtos.model.db.ServiceContDischarge;
import com.pelindo.ebtos.model.db.ServiceReceiving;
import com.pelindo.ebtos.model.db.master.MasterEquipment;
import com.pelindo.ebtos.model.db.master.MasterOperator;
import com.pelindo.ebtos.model.db.master.MasterYard;
import java.io.Serializable;
import java.util.ArrayList;
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
import org.primefaces.context.RequestContext;

/**
 *
 * @author dycoder
 */
@ManagedBean(name="contMovementBean")
@ViewScoped
public class ContMovementBean implements Serializable{
    private static final Logger logger = Logger.getLogger(ContMovementBean.class.getName());

    @EJB
    private ServiceReceivingFacadeRemote serviceReceivingFacade;
    @EJB
    private MasterYardFacadeRemote masterYardFacade;
    @EJB
    private MasterOperatorFacadeRemote masterOperatorFacade;
    @EJB
    private MasterEquipmentFacadeRemote masterEquipmentFacade;
    @EJB
    private ServiceContDischargeFacadeRemote serviceContDischargeFacade;
    @EJB
    private PlanningContLoadFacadeRemote planningContLoadFacadeRemote;
    @EJB
    private ContainerMovementHistoryFacadeRemote containerMovementHistoryFacadeRemote;
    @EJB
    private EquipmentContainerMovementFacadeRemote equipmentContainerMovementFacadeRemote;
    @EJB
    private MasterContainerFacadeRemote masterContainerFacadeRemote;
    @EJB
    private CancelLoadingServiceFacadeRemote cancelLoadingServiceFacadeRemote;
    @EJB
    private ContainerMovementOperationRemote containerMovementOperationRemote;

    private List<Object[]> containerMovementHistories;
    private List<Object[]> equipments;
    private List<Object[]> equipmentsHT;
    private List<MasterOperator> operators;
    private List<MasterYard> masterYards;
    private List<Object[]> movements;
    private ServiceContDischarge serviceContDischarge;
    private PlanningContLoad planningContLoad;
    private CancelLoadingService cancelLoadingService;
    private ContainerMovementHistory containerMovementHistory;
    private EquipmentContainerMovement equipmentLift;
    private EquipmentContainerMovement equipmentHT;
    private String check;
    private String movement;
    private String cfsOperation;
    private String cfsOperationStatus;
    private Boolean isHT;
    private Boolean edit;
    private Object[] masterYard;
    private Object[] moveCy;
    private Object[] moveBehandle;
    private Object[] moveInspection;
    private Object[] moveCfs;
    private Map<Integer, Integer> slots;
    private Map<Integer, Integer> rows;
    private Map<Integer, Integer> tiers;
    private Integer counterSlot;
    private Integer counterRow;
    private Integer counterTier;
    private String popupTargetUpdate;
    private String containerNoQuery;

    /** Creates a new instance of ContMovementBean */
    public ContMovementBean() {}

    @PostConstruct
    private void costruct(){
        serviceContDischarge = new ServiceContDischarge();
        serviceContDischarge.setMasterYard(new MasterYard());
        slots = new LinkedHashMap<Integer, Integer>();
        rows = new LinkedHashMap<Integer, Integer>();
        tiers = new LinkedHashMap<Integer, Integer>();
        counterSlot = 100;
        counterRow = 100;
        counterTier = 100;
        movement = ContainerMovementOperation.MOVEMENT_CY;
        movements = new ArrayList<Object[]>();
        moveCy = new Object[2];
        moveCy[0] = "CY Movement";
        moveCy[1] = ContainerMovementOperation.MOVEMENT_CY;
        moveBehandle = new Object[2];
        moveBehandle[0] = "Behandle";
        moveBehandle[1] = ContainerMovementOperation.MOVEMENT_BEHANDLE;
        moveInspection = new Object[2];
        moveInspection[0] = "Inspection";
        moveInspection[1] = ContainerMovementOperation.MOVEMENT_INSPECTION;
        moveCfs = new Object[2];
        moveCfs[0] = "CFS";
        moveCfs[1] = ContainerMovementOperation.MOVEMENT_CFS;

        isHT = true;
        edit = false;
        
        containerMovementHistories = containerMovementHistoryFacadeRemote.findMovementHistories();
        masterYards = masterYardFacade.findAll();
    }

    public void handleShowOperators(ActionEvent event) {
        operators = masterOperatorFacade.findAll();
    }

    public void handleShowTangos(ActionEvent event) {
        equipments = masterEquipmentFacade.findTangoForView();
    }

    public void handleShowHeadTrucks(ActionEvent event) {
        equipmentsHT = masterEquipmentFacade.findHtForView();
    }

    public void handlePopupTriggered(String popupTriggerID) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        String update = facesContext.getExternalContext().getRequestParameterMap().get("update");

        if (popupTriggerID != null) {
            if (popupTriggerID.equals("operator")) {
                handleShowOperators(null);
            } else if (popupTriggerID.equals("tango")) {
                handleShowTangos(null);
            } else if (popupTriggerID.equals("headTruck")) {
                handleShowHeadTrucks(null);
            } else {
                throw new RuntimeException(String.format("unknown popup trigger ID [%s]", popupTriggerID));
            }
        }

        popupTargetUpdate = update == null || update.equals("") ? "@none" : update;
    }

    public void handleFindMovableContainer (ActionEvent event) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        
        try {
            Object container = masterContainerFacadeRemote.findMovableContainer(containerNoQuery);
            
            if (container == null) {
                handleAdd(event);
                FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_WARN, "Warning", "application.validation.not_found");
            } else if (container instanceof PlanningContLoad) {
                planningContLoad = (PlanningContLoad) container;
                serviceContDischarge = null;
                cancelLoadingService = null;

                containerMovementHistory.setContNo(planningContLoad.getContNo());
                containerMovementHistory.setPlanningVessel(planningContLoad.getPlanningVessel());
                containerMovementHistory.setMasterYard(planningContLoad.getMasterYard());
                containerMovementHistory.setYardRow(planningContLoad.getYRow());
                containerMovementHistory.setYardSlot(planningContLoad.getYSlot());
                containerMovementHistory.setYardTier(planningContLoad.getYTier());

                isHT = true;

                if (planningContLoad.getIsShifting().equals("TRUE")) {
                    containerMovementHistory.setService(ContainerMovementHistory.SERVICE_SHIFTING);
                    equipmentLift.setService(EquipmentContainerMovement.SERVICE_SHIFTING);
                } else if (planningContLoad.getIsTranshipment().equals("TRUE")) {
                    containerMovementHistory.setService(ContainerMovementHistory.SERVICE_TRANSHIPMENT);
                    equipmentLift.setService(EquipmentContainerMovement.SERVICE_TRANSHIPMENT);
                } else {
                    if (planningContLoad.getIsChangeVessel().equals("TRUE")) {
                        CancelLoadingService changeVesselContainer = cancelLoadingServiceFacadeRemote.findByNewPpkbAndRelocationStatus(planningContLoad.getPlanningVessel().getNoPpkb(), planningContLoad.getContNo(), false);
                        
                        if (changeVesselContainer != null) {
                            containerMovementHistory.setService(ContainerMovementHistory.SERVICE_RELOCATION);
                            equipmentLift.setService(EquipmentContainerMovement.SERVICE_RELOCATION);
                        } else {
                            containerMovementHistory.setService(ContainerMovementHistory.SERVICE_RECEIVING);
                            equipmentLift.setService(EquipmentContainerMovement.SERVICE_RECEIVING);
                        }
                    } else {
                        containerMovementHistory.setService(ContainerMovementHistory.SERVICE_RECEIVING);
                        equipmentLift.setService(EquipmentContainerMovement.SERVICE_RECEIVING);

                        ServiceReceiving serviceReceiving = serviceReceivingFacade.findByNoPpkbAndContNoNotCancelLoading(planningContLoad.getPlanningVessel().getNoPpkb(), planningContLoad.getContNo());

                        if (serviceReceiving.getIsBehandle().equals("TRUE") || serviceReceiving.getIsCfs().equals("TRUE") || serviceReceiving.getIsInspection().equals("TRUE")) {
                            containerMovementHistory.setIsHt("TRUE");
                            isHT = false;
                        }

                        containerMovementHistory.setIsBehandle(serviceReceiving.getIsBehandle());
                        containerMovementHistory.setIsCfs(serviceReceiving.getIsCfs());
                        containerMovementHistory.setIsInspection(serviceReceiving.getIsInspection());
                    }
                }
            } else if (container instanceof ServiceContDischarge){
                serviceContDischarge = (ServiceContDischarge) container;
                planningContLoad = null;
                cancelLoadingService = null;

                containerMovementHistory.setContNo(serviceContDischarge.getContNo());
                containerMovementHistory.setPlanningVessel(serviceContDischarge.getServiceVessel().getPlanningVessel());
                containerMovementHistory.setService(ContainerMovementHistory.SERVICE_DISCHARGE);
                containerMovementHistory.setMasterYard(serviceContDischarge.getMasterYard());
                containerMovementHistory.setYardRow(serviceContDischarge.getYRow());
                containerMovementHistory.setYardSlot(serviceContDischarge.getYSlot());
                containerMovementHistory.setYardTier(serviceContDischarge.getYTier());

                equipmentLift.setService(EquipmentContainerMovement.SERVICE_DISCHARGE);

                isHT = true;

                if (serviceContDischarge.getIsBehandle().equals("TRUE") 
                        || serviceContDischarge.getIsCfs().equals("TRUE")
                        || serviceContDischarge.getIsInspection().equals("TRUE")) {
                    containerMovementHistory.setIsHt("TRUE");
                    isHT = false;
                }

                containerMovementHistory.setIsBehandle(serviceContDischarge.getIsBehandle());
                containerMovementHistory.setIsCfs(serviceContDischarge.getIsCfs());
                containerMovementHistory.setIsInspection(serviceContDischarge.getIsInspection());
            } else if (container instanceof CancelLoadingService) {
                cancelLoadingService = (CancelLoadingService) container;
                planningContLoad = null;
                serviceContDischarge = null;

                containerMovementHistory.setContNo(cancelLoadingService.getContNo());
                containerMovementHistory.setPlanningVessel(cancelLoadingService.getPlanningVessel());
                containerMovementHistory.setService(ContainerMovementHistory.SERVICE_CANCEL_LOADING);
                containerMovementHistory.setMasterYard(cancelLoadingService.getMasterYard());
                containerMovementHistory.setYardRow(cancelLoadingService.getyRow());
                containerMovementHistory.setYardSlot(cancelLoadingService.getySlot());
                containerMovementHistory.setYardTier(cancelLoadingService.getyTier());

                equipmentLift.setService(EquipmentContainerMovement.SERVICE_CANCEL_LOADING);

                isHT = true;
            } else {
                handleAdd(event);
                FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_WARN, "Warning", "application.validation.not_found");
            }
        } catch (RuntimeException re) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", re);
        }
    }

    public void handleAdd(ActionEvent event){
        equipmentLift = new EquipmentContainerMovement();
        equipmentLift.setActivity(EquipmentContainerMovement.ACTIVITY_LIFTON);
        equipmentLift.setMasterEquipment(new MasterEquipment());
        equipmentLift.setMasterOperator(new MasterOperator());

        equipmentHT = new EquipmentContainerMovement();
        equipmentHT.setActivity(EquipmentContainerMovement.ACTIVITY_HT);

        serviceContDischarge = new ServiceContDischarge();
        serviceContDischarge.setMasterYard(new MasterYard());
        containerMovementHistory = new ContainerMovementHistory();
        planningContLoad = null;
        cancelLoadingService = null;
        containerNoQuery = null;
        isHT = true;
        edit = false;
    }

    public void handleSelectOperator(ActionEvent event){
        if (popupTargetUpdate != null) {
            String operator = (String) event.getComponent().getAttributes().get("idOperator");
            MasterOperator masterOperator = masterOperatorFacade.find(operator);
        
            if (popupTargetUpdate.contains("tango")) {
                equipmentLift.setMasterOperator(masterOperator);
            } else if (popupTargetUpdate.contains("headTruck")) {
                equipmentHT.setMasterOperator(masterOperator);
            }
        }
    }

    public void handleSelectTangoEquipment(ActionEvent event){
        String equipment = (String) event.getComponent().getAttributes().get("idEquipment");
        MasterEquipment masterEquipment = masterEquipmentFacade.find(equipment);
        equipmentLift.setMasterEquipment(masterEquipment);
    }

    public void handleSelectHTEquipment(ActionEvent event){
        String equipment = (String) event.getComponent().getAttributes().get("idEquipment");
        MasterEquipment masterEquipment = masterEquipmentFacade.find(equipment);
        equipmentHT.setMasterEquipment(masterEquipment);
    }

    public void handleRefreshMovementHistories(ActionEvent event) {
        containerMovementHistories = containerMovementHistoryFacadeRemote.findMovementHistories();
    }

    public void onChangeMovement(ValueChangeEvent event){
        String newItem = (String) event.getNewValue();
        movement = newItem;
    }

    public void onChangeEventHT(ValueChangeEvent event) {
        Boolean cek = (Boolean) event.getNewValue();

        if(cek == true){
            isHT = false;
            containerMovementHistory.setIsHt("TRUE");
        } else{
            isHT = true;
            containerMovementHistory.setIsHt("FALSE");
            equipmentHT = new EquipmentContainerMovement();
        }
    }

    public void onChangeEventYard(ValueChangeEvent event) {
        String newItem = (String) event.getNewValue();
        masterYard = masterYardFacade.findMasterYardByBlock(newItem);
        Integer slot = (Integer) masterYard[0];
        Integer row = (Integer) masterYard[1];
        Integer tier = (Integer) masterYard[2];
        getSlots().clear();
        getRows().clear();
        getTiers().clear();
        counterSlot = slot;
        counterRow = row;
        counterTier = tier;
    }

    public void handleLiftOnConfirmCancelLoadingContainer(ActionEvent event) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        RequestContext requestContext = RequestContext.getCurrentInstance();
        Object container = masterContainerFacadeRemote.findMovableContainer(containerNoQuery);
        Boolean isValid = false;

        if (container == null) {
            handleAdd(event);
            requestContext.addCallbackParam("loggedIn", isValid);
            return;
        } else if (container instanceof CancelLoadingService && !cancelLoadingService.equals((CancelLoadingService) container)) {
            handleFindMovableContainer (event);
            requestContext.addCallbackParam("loggedIn", isValid);
            return;
        }

        try {
            containerMovementOperationRemote.handleLiftOnCancelLoadingContainer(cancelLoadingService, containerMovementHistory, equipmentLift, equipmentHT);
            containerMovementHistories = containerMovementHistoryFacadeRemote.findMovementHistories();
            isValid = true;
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
        } catch (TimeSelectionNotValidException ex) {
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_ERROR, "Error", "application.validation.time");
        } catch (LocationNotAvailableException ex) {
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_ERROR, "Error", "application.validation.location_not_available");
        } catch (ContainerNotMovableException ex) {
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_ERROR, "Error", "application.validation.cant_move_container");
        } catch (Exception ex) {
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_ERROR, "Error", "application.loading.error");
            logger.log(Level.SEVERE, null, ex);
        }

        requestContext.addCallbackParam("loggedIn", isValid);
    }

    public void handleLiftOffConfirmCancelLoadingContainer(ActionEvent event) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        RequestContext requestContext = RequestContext.getCurrentInstance();
        Boolean loggedIn = false;

        try {
            containerMovementOperationRemote.handleLiftOffCancelLoadingContainer(cancelLoadingService, containerMovementHistory, equipmentLift, equipmentHT, movement);
            containerMovementHistories = containerMovementHistoryFacadeRemote.findMovementHistories();

            loggedIn = true;
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
        } catch (LocationNotAvailableException ex) {
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_ERROR, "Error", "application.validation.location_not_available");
        } catch (TimeSelectionNotValidException ex) {
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_ERROR, "Error", "application.validation.time");
        } catch (Exception ex) {
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_ERROR, "Error", "application.loading.error");
            logger.log(Level.SEVERE, null, ex);
        }
        
        requestContext.addCallbackParam("loggedIn", loggedIn);
    }

    public void handleLiftOnConfirmDischargeContainer(ActionEvent event) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        RequestContext requestContext = RequestContext.getCurrentInstance();
        Object container = masterContainerFacadeRemote.findMovableContainer(containerNoQuery);
        Boolean isValid = false;

        if (container == null) {
            handleAdd(event);
            requestContext.addCallbackParam("loggedIn", isValid);
            return;
        } else if (container instanceof ServiceContDischarge && !serviceContDischarge.equals((ServiceContDischarge) container)) {
            handleFindMovableContainer (event);
            requestContext.addCallbackParam("loggedIn", isValid);
            return;
        }

        try {
            containerMovementOperationRemote.handleLiftOnDischargeContainer(serviceContDischarge, containerMovementHistory, equipmentLift, equipmentHT, cfsOperationStatus);
            containerMovementHistories = containerMovementHistoryFacadeRemote.findMovementHistories();

            isValid = true;
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
        } catch (TimeSelectionNotValidException ex) {
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_ERROR, "Error", "application.validation.time");
        } catch (LocationNotAvailableException ex) {
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_ERROR, "Error", "application.validation.location_not_available");
        } catch (ContainerNotMovableException ex) {
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_ERROR, "Error", "application.validation.cant_move_container");
        } catch (Exception ex) {
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_ERROR, "Error", "application.loading.error");
            logger.log(Level.SEVERE, null, ex);
        }

        requestContext.addCallbackParam("loggedIn", isValid);
    }

    public void handleLiftOffConfirmDischargeContainer(ActionEvent event) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        RequestContext requestContext = RequestContext.getCurrentInstance();
        Boolean loggedIn = false;
        try {
            containerMovementOperationRemote.handleLiftOffDischargeContainer(serviceContDischarge, containerMovementHistory, equipmentLift, equipmentHT, movement);
            containerMovementHistories = containerMovementHistoryFacadeRemote.findMovementHistories();

            loggedIn = true;
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
        } catch (LocationNotAvailableException ex) {
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_ERROR, "Error", "application.validation.location_not_available");
        } catch (TimeSelectionNotValidException ex) {
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_ERROR, "Error", "application.validation.time");
        } catch (Exception ex) {
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_ERROR, "Error", "application.loading.error");
            logger.log(Level.SEVERE, null, ex);
        }

        requestContext.addCallbackParam("loggedIn", loggedIn);
    }

    public void handleLiftOnConfirmLoadContainer(ActionEvent event) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        RequestContext requestContext = RequestContext.getCurrentInstance();
        Object container = masterContainerFacadeRemote.findMovableContainer(containerNoQuery);
        Boolean isValid = false;

        if (container == null) {
            handleAdd(event);
            requestContext.addCallbackParam("loggedIn", isValid);
            return;
        } else if (container instanceof PlanningContLoad && !planningContLoad.equals((PlanningContLoad) container)) {
            handleFindMovableContainer (event);
            requestContext.addCallbackParam("loggedIn", isValid);
            return;
        }
        try {
            containerMovementOperationRemote.handleLiftOnLoadContainer(planningContLoad, containerMovementHistory, equipmentLift, equipmentHT, cfsOperationStatus);
            containerMovementHistories = containerMovementHistoryFacadeRemote.findMovementHistories();
            isValid = true;
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
        } catch (TimeSelectionNotValidException ex) {
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_ERROR, "Error", "application.validation.time");
        } catch (LocationNotAvailableException ex) {
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_ERROR, "Error", "application.validation.location_not_available");
        } catch (ContainerNotMovableException ex) {
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_ERROR, "Error", "application.validation.cant_move_container");
        } catch (Exception ex) {
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_ERROR, "Error", "application.loading.error");
            logger.log(Level.SEVERE, null, ex);
        }

        requestContext.addCallbackParam("loggedIn", isValid);
    }

    public void handleLiftOffConfirmLoadContainer(ActionEvent event) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        RequestContext requestContext = RequestContext.getCurrentInstance();
        Boolean loggedIn = false;
        try {
            containerMovementOperationRemote.handleLiftOffLoadContainer(planningContLoad, containerMovementHistory, equipmentLift, equipmentHT, movement);
            containerMovementHistories = containerMovementHistoryFacadeRemote.findMovementHistories();

            loggedIn = true;
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
        } catch (LocationNotAvailableException ex) {
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_ERROR, "Error", "application.validation.location_not_available");
        } catch (TimeSelectionNotValidException ex) {
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_ERROR, "Error", "application.validation.time");
        } catch (Exception ex) {
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_ERROR, "Error", "application.loading.error");
            logger.log(Level.SEVERE, null, ex);
        }

        requestContext.addCallbackParam("loggedIn", loggedIn);
    }

    public void handleEditLiftOff(ActionEvent event){
        String noPpkb = (String) event.getComponent().getAttributes().get("noPpkb");
        String contNo = (String) event.getComponent().getAttributes().get("contNo");

        containerMovementHistory = containerMovementHistoryFacadeRemote.findLastRecord(noPpkb, contNo);
        serviceContDischarge = serviceContDischargeFacade.findByNoPpkbAndContNo(noPpkb, contNo);

        if(serviceContDischarge.getMasterYard() == null) {
            serviceContDischarge.setMasterYard(new MasterYard());
        }

        if(serviceContDischarge.getIsBehandle().equals("TRUE") || serviceContDischarge.getIsInspection().equals("TRUE")){
            movements.clear();
            movements.add(moveCy);
            movements.add(moveCfs);
        } else if(serviceContDischarge.getIsCfs().equals("TRUE")){
            movements.clear();
            movements.add(moveCy);
        }

        if (!movements.isEmpty()) {
            movement = (String) movements.get(0)[1];
        }
        
        equipmentLift = new EquipmentContainerMovement();
        equipmentLift.setMasterEquipment(new MasterEquipment());
        equipmentLift.setMasterOperator(new MasterOperator());
        equipmentHT = equipmentContainerMovementFacadeRemote.findNotFinishedYetByActivityAndService(serviceContDischarge.getServiceVessel().getNoPpkb(), serviceContDischarge.getContNo(), EquipmentContainerMovement.ACTIVITY_HT, EquipmentContainerMovement.SERVICE_DISCHARGE);
        edit = true;
    }

    public void handleEditLiftOffCancelLoading(ActionEvent event){
        String noPpkb = (String) event.getComponent().getAttributes().get("noPpkb");
        String contNo = (String) event.getComponent().getAttributes().get("contNo");

        containerMovementHistory = containerMovementHistoryFacadeRemote.findLastRecord(noPpkb, contNo);
        cancelLoadingService = cancelLoadingServiceFacadeRemote.findByNoPpkbAndContNo(noPpkb, contNo);

        if(cancelLoadingService.getMasterYard() == null) {
            cancelLoadingService.setMasterYard(new MasterYard());
        }

        movements.clear();
        movements.add(moveCy);
        
        if (!movements.isEmpty()) {
            movement = (String) movements.get(0)[1];
        }

        equipmentLift = new EquipmentContainerMovement();
        equipmentLift.setMasterEquipment(new MasterEquipment());
        equipmentLift.setMasterOperator(new MasterOperator());
        equipmentHT = equipmentContainerMovementFacadeRemote.findNotFinishedYetByActivityAndService(cancelLoadingService.getPlanningVessel().getNoPpkb(), cancelLoadingService.getContNo(), EquipmentContainerMovement.ACTIVITY_HT, EquipmentContainerMovement.SERVICE_CANCEL_LOADING);
        edit = true;
    }

    public void handleEditLiftOffLoad(ActionEvent event){
        String noPpkb = (String) event.getComponent().getAttributes().get("noPpkb");
        String contNo = (String) event.getComponent().getAttributes().get("contNo");

        planningContLoad = planningContLoadFacadeRemote.findByNoPpkbAndContNoExcludeCancelLoading(noPpkb, contNo);
        containerMovementHistory = containerMovementHistoryFacadeRemote.findLastRecord(noPpkb, contNo);
        movements.clear();
        
        if (planningContLoad.getIsShifting().equals("TRUE")) {
            movements.add(moveCy);
            equipmentHT = equipmentContainerMovementFacadeRemote.findNotFinishedYetByActivityAndService(planningContLoad.getPlanningVessel().getNoPpkb(), planningContLoad.getContNo(), EquipmentContainerMovement.ACTIVITY_HT, EquipmentContainerMovement.SERVICE_SHIFTING);
        } else if (planningContLoad.getIsTranshipment().equals("TRUE")) {movements.clear();
            movements.add(moveCy);
            equipmentHT = equipmentContainerMovementFacadeRemote.findNotFinishedYetByActivityAndService(planningContLoad.getPlanningVessel().getNoPpkb(), planningContLoad.getContNo(), EquipmentContainerMovement.ACTIVITY_HT, EquipmentContainerMovement.SERVICE_TRANSHIPMENT);
        } else {
            if (planningContLoad.getIsChangeVessel().equals("TRUE")) {
                CancelLoadingService changeVesselContainer = cancelLoadingServiceFacadeRemote.findByNewPpkbAndRelocationStatus(planningContLoad.getPlanningVessel().getNoPpkb(), planningContLoad.getContNo(), false);

                if (changeVesselContainer != null) {
                    equipmentHT = equipmentContainerMovementFacadeRemote.findNotFinishedYetByActivityAndService(planningContLoad.getPlanningVessel().getNoPpkb(), planningContLoad.getContNo(), EquipmentContainerMovement.ACTIVITY_HT, EquipmentContainerMovement.SERVICE_RELOCATION);
                } else {
                    equipmentHT = equipmentContainerMovementFacadeRemote.findNotFinishedYetByActivityAndService(planningContLoad.getPlanningVessel().getNoPpkb(), planningContLoad.getContNo(), EquipmentContainerMovement.ACTIVITY_HT, EquipmentContainerMovement.SERVICE_RECEIVING);
                }

                movements.add(moveCy);
            } else {
                ServiceReceiving serviceReceiving = serviceReceivingFacade.findByNoPpkbAndContNoNotCancelLoading(containerMovementHistory.getPlanningVessel().getNoPpkb(), containerMovementHistory.getContNo());

                if (serviceReceiving.getIsBehandle().equals("TRUE") || serviceReceiving.getIsInspection().equals("TRUE")){
                    movements.clear();
                    movements.add(moveCy);
                    movements.add(moveCfs);
                } else if(serviceReceiving.getIsCfs().equals("TRUE")){
                    movements.clear();
                    movements.add(moveCy);
                }

                equipmentHT = equipmentContainerMovementFacadeRemote.findNotFinishedYetByActivityAndService(planningContLoad.getPlanningVessel().getNoPpkb(), planningContLoad.getContNo(), EquipmentContainerMovement.ACTIVITY_HT, EquipmentContainerMovement.SERVICE_RECEIVING);
            }
        }

        if (!movements.isEmpty()) {
            movement = (String) movements.get(0)[1];
        }

        equipmentLift = new EquipmentContainerMovement();
        equipmentLift.setMasterEquipment(new MasterEquipment());
        equipmentLift.setMasterOperator(new MasterOperator());
        edit = true;
    }
    
// <editor-fold defaultstate="collapsed" desc="Properties">
    public ServiceContDischarge getServiceContDischarge() {
        return serviceContDischarge;
    }

    public void setServiceContDischarge(ServiceContDischarge serviceContDischarge) {
        this.serviceContDischarge = serviceContDischarge;
    }

    public List<Object[]> getEquipments() {
        return equipments;
    }

    public void setEquipments(List<Object[]> equipments) {
        this.equipments = equipments;
    }

    public List<Object[]> getEquipmentsHT() {
        return equipmentsHT;
    }

    public void setEquipmentsHT(List<Object[]> equipmentsHT) {
        this.equipmentsHT = equipmentsHT;
    }

    public List<MasterOperator> getOperators() {
        return operators;
    }

    public void setOperators(List<MasterOperator> operators) {
        this.operators = operators;
    }

    public Boolean getIsHT() {
        return isHT;
    }

    public void setIsHT(Boolean isHT) {
        this.isHT = isHT;
    }

    public List<MasterYard> getMasterYards() {
        return masterYards;
    }

    public void setMasterYards(List<MasterYard> masterYards) {
        this.masterYards = masterYards;
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

    public Map<Integer, Integer> getRows() {
        for (int i = 1; i <= counterRow; i++) {
            rows.put(i, i);
        }
        return rows;
    }

    public void setRows(Map<Integer, Integer> rows) {
        this.rows = rows;
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

    public Boolean getEdit() {
        return edit;
    }

    public void setEdit(Boolean edit) {
        this.edit = edit;
    }

    public String getCheck() {
        check = String.valueOf(!isHT);
        return check;
    }

    public void setCheck(String check) {
        if (Boolean.valueOf(check)) {
            containerMovementHistory.setIsHt("TRUE");
//            containerMovementHistory.setIsHt(Boolean.valueOf(check));
        } else {
            containerMovementHistory.setIsHt("FALSE");
        }
        
        this.check = check;
    }

    public String getMovement() {
        return movement;
    }

    public void setMovement(String movement) {
        this.movement = movement;
    }

    public List<Object[]> getMovements() {
        if (movements.isEmpty()) {
            movements.add(moveCy);
            movements.add(moveBehandle);
            movements.add(moveInspection);
            movements.add(moveCfs);
        }
        return movements;
    }

    public void setMovements(List<Object[]> movements) {
        this.movements = movements;
    }

    public ContainerMovementHistory getContainerMovementHistory() {
        return containerMovementHistory;
    }

    public void setContainerMovementHistory(ContainerMovementHistory containerMovementHistory) {
        this.containerMovementHistory = containerMovementHistory;
    }

    public EquipmentContainerMovement getEquipmentHT() {
        return equipmentHT;
    }

    public void setEquipmentHT(EquipmentContainerMovement equipmentHT) {
        this.equipmentHT = equipmentHT;
    }

    public EquipmentContainerMovement getEquipmentLift() {
        return equipmentLift;
    }

    public void setEquipmentLift(EquipmentContainerMovement equipmentLift) {
        this.equipmentLift = equipmentLift;
    }

    public String getPopupTargetUpdate() {
        return popupTargetUpdate;
    }

    public String getContainerNoQuery() {
        return containerNoQuery;
    }

    public void setContainerNoQuery(String containerNoQuery) {
        this.containerNoQuery = containerNoQuery;
    }

    public List<Object[]> getContainerMovementHistories() {
        return containerMovementHistories;
    }

    public CancelLoadingService getCancelLoadingService() {
        return cancelLoadingService;
    }

    public void setCancelLoadingService(CancelLoadingService cancelLoadingService) {
        this.cancelLoadingService = cancelLoadingService;
    }

    public PlanningContLoad getPlanningContLoad() {
        return planningContLoad;
    }

    public void setPlanningContLoad(PlanningContLoad planningContLoad) {
        this.planningContLoad = planningContLoad;
    }

    public String getCfsOperation() {
        return cfsOperation;
    }

    public void setCfsOperation(String cfsOperation) {
        this.cfsOperation = cfsOperation;
    }

    public String getCfsOperationStatus() {
        return cfsOperationStatus;
    }

    public void setCfsOperationStatus(String cfsOperationStatus) {
        this.cfsOperationStatus = cfsOperationStatus;
    }

    // </editor-fold>
}
