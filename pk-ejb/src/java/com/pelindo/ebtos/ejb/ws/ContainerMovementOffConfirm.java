/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.ejb.ws;

import com.pelindo.ebtos.ejb.ContainerMovementOperation;
import com.pelindo.ebtos.ejb.facade.remote.CancelLoadingServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ContainerMovementHistoryFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.EquipmentContainerMovementFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterEquipmentFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterOperatorFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterYardFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningContLoadFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceContDischargeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceReceivingFacadeRemote;
import com.pelindo.ebtos.ejb.remote.ContainerMovementOperationRemote;
import com.pelindo.ebtos.ejb.ws.model.Container;
import com.pelindo.ebtos.ejb.ws.model.ContainerMovementOffConfirmInitialData;
import com.pelindo.ebtos.ejb.ws.model.ContainerMovementOffConfirmSubmitData;
import com.pelindo.ebtos.ejb.ws.model.IMessageable;
import com.pelindo.ebtos.ejb.ws.model.Message;
import com.pelindo.ebtos.ejb.ws.model.MoveableOffContainer;
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
import com.pelindo.ebtos.security.UserUtil;
import com.qtasnim.util.ParseDate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.ejb.Stateless;
import javax.jws.WebParam;

/**
 *
 * @author x42jr
 */
@WebService()
@Stateless()
public class ContainerMovementOffConfirm {
    private static final String CFS_STUFFING = "STUFFING";

    @EJB
    private MasterEquipmentFacadeRemote masterEquipmentFacadeRemote;
    @EJB
    private MasterOperatorFacadeRemote masterOperatorFacadeRemote;
    @EJB
    private ServiceContDischargeFacadeRemote serviceContDischargeFacadeRemote;
    @EJB
    private MasterYardFacadeRemote masterYardFacadeRemote;
    @EJB
    private ContainerMovementHistoryFacadeRemote containerMovementHistoryFacadeRemote;
    @EJB
    private ServiceReceivingFacadeRemote serviceReceivingFacadeRemote;
    @EJB
    private PlanningContLoadFacadeRemote planningContLoadFacadeRemote;
    @EJB
    private EquipmentContainerMovementFacadeRemote equipmentContainerMovementFacadeRemote;
    @EJB
    private CancelLoadingServiceFacadeRemote cancelLoadingServiceFacadeRemote;
    @EJB
    private ContainerMovementOperationRemote containerMovementOperationRemote;

    @WebMethod(operationName = "prepareContainerMovementOffConfirmUI")
    public ContainerMovementOffConfirmInitialData prepareContainerMovementOffConfirmUI() {
        ContainerMovementOffConfirmInitialData initaialData = null;

        try {
            List<Object> ttCodes = masterEquipmentFacadeRemote.findTango4HHT();
            List<Object[]> operators = masterOperatorFacadeRemote.findMasterOperators4HHT();

            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            String serverTime = format.format(new Date());

            initaialData = new ContainerMovementOffConfirmInitialData(IMessageable.OK, "Data found!");
            initaialData.setTtCodes(ttCodes);
            initaialData.setOperators(operators);
            initaialData.setServerTime(serverTime);

        } catch (RuntimeException re) {
            initaialData = new ContainerMovementOffConfirmInitialData(IMessageable.ERROR, "Internal error!");
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", re);
        }

        return initaialData;
    }

    @WebMethod(operationName = "findMovableOffContainer")
    public MoveableOffContainer findMovableOffContainer(@WebParam(name = "contNo") String contNo){
        MoveableOffContainer moveableOffContainer = null;

        try {
            ContainerMovementHistory history = containerMovementHistoryFacadeRemote.findMovableOffByContNo(contNo);

            if (history != null) {
                if (history.getService().equals(ContainerMovementHistory.SERVICE_DISCHARGE)) {
                    ServiceContDischarge serviceContDischarge = serviceContDischargeFacadeRemote.findMovableOffContainer(history.getContNo());

                    if (serviceContDischarge != null) {
                        List<String> destinations = new ArrayList<String>();
                        List<String> operations = new ArrayList<String>();
                        moveableOffContainer = new MoveableOffContainer(IMessageable.OK, "Data found!");

                        Container container = new Container(IMessageable.OK, "Data found!");
                        container.setNoPpkb(serviceContDischarge.getServiceVessel().getNoPpkb());
                        container.setContNo(serviceContDischarge.getContNo());
                        container.setService(history.getService());
                        container.setYardBlock(serviceContDischarge.getMasterYard().getBlock());
                        container.setYardSlot(serviceContDischarge.getYSlot());
                        container.setYardRow(serviceContDischarge.getYRow());
                        container.setYardTier(serviceContDischarge.getYTier());

                        destinations.add(ContainerMovementOperation.MOVEMENT_CY);
                        operations.add(CFS_STUFFING);

                        if (serviceContDischarge.getIsBehandle().equals("TRUE")|| serviceContDischarge.getIsInspection().equals("TRUE")){
                            destinations.add(ContainerMovementOperation.MOVEMENT_CFS);
                        } else if (serviceContDischarge.getIsCfs().equals("TRUE")) {

                        } else {
                            destinations.add(ContainerMovementOperation.MOVEMENT_CFS);
                            destinations.add(ContainerMovementOperation.MOVEMENT_BEHANDLE);
                            destinations.add(ContainerMovementOperation.MOVEMENT_INSPECTION);
                        }

                        moveableOffContainer.setContainer(container);
                        moveableOffContainer.setDestinations(destinations);
                        moveableOffContainer.setOperations(operations);
                    } else {
                        moveableOffContainer = new MoveableOffContainer(IMessageable.ERROR, "Data not found!");
                    }
                } else if (history.getService().equals(ContainerMovementHistory.SERVICE_CANCEL_LOADING)) {
                    CancelLoadingService cancelLoadingService = cancelLoadingServiceFacadeRemote.findMovableOffContainer(history.getContNo());

                    if (cancelLoadingService != null) {
                        List<String> destinations = new ArrayList<String>();
                        List<String> operations = new ArrayList<String>();
                        moveableOffContainer = new MoveableOffContainer(IMessageable.OK, "Data found!");

                        Container container = new Container(IMessageable.OK, "Data found!");
                        container.setNoPpkb(cancelLoadingService.getPlanningVessel().getNoPpkb());
                        container.setContNo(cancelLoadingService.getContNo());
                        container.setService(history.getService());
                        container.setYardBlock(cancelLoadingService.getMasterYard().getBlock());
                        container.setYardSlot(cancelLoadingService.getySlot());
                        container.setYardRow(cancelLoadingService.getyRow());
                        container.setYardTier(cancelLoadingService.getyTier());

                        destinations.add(ContainerMovementOperation.MOVEMENT_CY);

                        moveableOffContainer.setContainer(container);
                        moveableOffContainer.setDestinations(destinations);
                        moveableOffContainer.setOperations(operations);
                    } else {
                        moveableOffContainer = new MoveableOffContainer(IMessageable.ERROR, "Data not found!");
                    }
                } else {
                    PlanningContLoad planningContLoad = planningContLoadFacadeRemote.findMovableOffContainer(contNo);

                    if (planningContLoad != null) {
                        List<String> destinations = new ArrayList<String>();
                        List<String> operations = new ArrayList<String>();
                        moveableOffContainer = new MoveableOffContainer(IMessageable.OK, "Data found!");

                        Container container = new Container(IMessageable.OK, "Data found!");
                        container.setNoPpkb(planningContLoad.getPlanningVessel().getNoPpkb());
                        container.setContNo(planningContLoad.getContNo());
                        container.setService(history.getService());
                        container.setYardBlock(planningContLoad.getMasterYard().getBlock());
                        container.setYardSlot(planningContLoad.getYSlot());
                        container.setYardRow(planningContLoad.getYRow());
                        container.setYardTier(planningContLoad.getYTier());

                        destinations.add(ContainerMovementOperation.MOVEMENT_CY);
                        operations.add(CFS_STUFFING);

                        if (history.getService().equals(ContainerMovementHistory.SERVICE_RECEIVING) && planningContLoad.getIsChangeVessel().equals("FALSE")) {
                            ServiceReceiving serviceReceiving = serviceReceivingFacadeRemote.findByNoPpkbAndContNoNotCancelLoading(planningContLoad.getPlanningVessel().getNoPpkb(), planningContLoad.getContNo());

                            if (serviceReceiving.getIsBehandle().equals("TRUE") || serviceReceiving.getIsInspection().equals("TRUE")){
                                destinations.add(ContainerMovementOperation.MOVEMENT_CFS);
                            } else if (serviceReceiving.getIsCfs().equals("TRUE")) {

                            } else {
                                destinations.add(ContainerMovementOperation.MOVEMENT_CFS);
                                destinations.add(ContainerMovementOperation.MOVEMENT_BEHANDLE);
                                destinations.add(ContainerMovementOperation.MOVEMENT_INSPECTION);
                            }
                        }

                        moveableOffContainer.setContainer(container);
                        moveableOffContainer.setDestinations(destinations);
                        moveableOffContainer.setOperations(operations);
                    } else {
                        moveableOffContainer = new MoveableOffContainer(IMessageable.ERROR, "Data not found!");
                    }
                }
            } else {
                moveableOffContainer = new MoveableOffContainer(IMessageable.ERROR, "Data not found!");
            }
        } catch (RuntimeException re) {
            moveableOffContainer = new MoveableOffContainer(IMessageable.ERROR, "Internal error!");
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", re);
        }

        return moveableOffContainer;
    }
    
    @WebMethod(operationName = "containerMovementOffConfirm")
    public Message containerMovementOffConfirm(@WebParam(name = "submitData") ContainerMovementOffConfirmSubmitData submitData) {
        Message message = null;
        
        try {
            UserUtil.setCurrentUser(submitData.getCreatedBy());

            ContainerMovementHistory containerMovementHistory = containerMovementHistoryFacadeRemote.findMovableOffByContNo(submitData.getContNo());

            if (containerMovementHistory != null) {
                MasterYard blockDestination = masterYardFacadeRemote.find(submitData.getYardBlock());
                MasterEquipment tango = masterEquipmentFacadeRemote.find(submitData.getTtCode());
                MasterOperator tangoOperator = masterOperatorFacadeRemote.find(submitData.getTtOperatorCode());
                Date startTime = ParseDate.changeDate(submitData.getStartTime());
                Date endTime = ParseDate.changeDate(submitData.getEndTime());
                short destinationSlot = Short.parseShort(submitData.getYardSlot());
                short destinationRow = Short.parseShort(submitData.getYardRow());
                short destinationTier = Short.parseShort(submitData.getYardTier());

                if (containerMovementHistory.getService().equals(ContainerMovementHistory.SERVICE_DISCHARGE)) {
                    ServiceContDischarge serviceContDischarge = serviceContDischargeFacadeRemote.findMovableOffContainer(containerMovementHistory.getContNo());

                    serviceContDischarge.setMasterYard(blockDestination);
                    serviceContDischarge.setYSlot(destinationSlot);
                    serviceContDischarge.setYRow(destinationRow);
                    serviceContDischarge.setYTier(destinationTier);

                    EquipmentContainerMovement equipmentLift = new EquipmentContainerMovement();
                    equipmentLift.setStartTime(startTime);
                    equipmentLift.setEndTime(endTime);
                    equipmentLift.setMasterEquipment(tango);
                    equipmentLift.setMasterOperator(tangoOperator);

                    EquipmentContainerMovement equipmentHT = equipmentContainerMovementFacadeRemote.findNotFinishedYetByActivityAndService(serviceContDischarge.getServiceVessel().getNoPpkb(), serviceContDischarge.getContNo(), EquipmentContainerMovement.ACTIVITY_HT, EquipmentContainerMovement.SERVICE_DISCHARGE);

                    containerMovementOperationRemote.handleLiftOffDischargeContainer(serviceContDischarge, containerMovementHistory, equipmentLift, equipmentHT, submitData.getDestination());
                    message = new Message(IMessageable.OK, "Success!");
                } else if (containerMovementHistory.getService().equals(ContainerMovementHistory.SERVICE_CANCEL_LOADING)) {
                    CancelLoadingService cancelLoadingService = cancelLoadingServiceFacadeRemote.findMovableOffContainer(containerMovementHistory.getContNo());

                    cancelLoadingService.setMasterYard(blockDestination);
                    cancelLoadingService.setySlot(destinationSlot);
                    cancelLoadingService.setyRow(destinationRow);
                    cancelLoadingService.setyTier(destinationTier);

                    EquipmentContainerMovement equipmentLift = new EquipmentContainerMovement();
                    equipmentLift.setStartTime(startTime);
                    equipmentLift.setEndTime(endTime);
                    equipmentLift.setMasterEquipment(tango);
                    equipmentLift.setMasterOperator(tangoOperator);

                    EquipmentContainerMovement equipmentHT = equipmentContainerMovementFacadeRemote.findNotFinishedYetByActivityAndService(cancelLoadingService.getPlanningVessel().getNoPpkb(), cancelLoadingService.getContNo(), EquipmentContainerMovement.ACTIVITY_HT, EquipmentContainerMovement.SERVICE_CANCEL_LOADING);

                    containerMovementOperationRemote.handleLiftOffCancelLoadingContainer(cancelLoadingService, containerMovementHistory, equipmentLift, equipmentHT, submitData.getDestination());
                    message = new Message(IMessageable.OK, "Success!");
                } else {
                    PlanningContLoad planningContLoad = planningContLoadFacadeRemote.findMovableOffContainer(containerMovementHistory.getContNo());

                    planningContLoad.setMasterYard(blockDestination);
                    planningContLoad.setYSlot(destinationSlot);
                    planningContLoad.setYRow(destinationRow);
                    planningContLoad.setYTier(destinationTier);

                    EquipmentContainerMovement equipmentLift = new EquipmentContainerMovement();
                    equipmentLift.setStartTime(startTime);
                    equipmentLift.setEndTime(endTime);
                    equipmentLift.setMasterEquipment(tango);
                    equipmentLift.setMasterOperator(tangoOperator);
                    
                    EquipmentContainerMovement equipmentHT;

                    if (planningContLoad.getIsShifting().equals("TRUE")) {
                        equipmentHT = equipmentContainerMovementFacadeRemote.findNotFinishedYetByActivityAndService(planningContLoad.getPlanningVessel().getNoPpkb(), planningContLoad.getContNo(), EquipmentContainerMovement.ACTIVITY_HT, EquipmentContainerMovement.SERVICE_SHIFTING);
                    } else if (planningContLoad.getIsTranshipment().equals("TRUE")) {
                        equipmentHT = equipmentContainerMovementFacadeRemote.findNotFinishedYetByActivityAndService(planningContLoad.getPlanningVessel().getNoPpkb(), planningContLoad.getContNo(), EquipmentContainerMovement.ACTIVITY_HT, EquipmentContainerMovement.SERVICE_TRANSHIPMENT);
                    } else {
                        if (planningContLoad.getIsChangeVessel().equals("TRUE")) {
                            CancelLoadingService changeVesselContainer = cancelLoadingServiceFacadeRemote.findByNewPpkbAndRelocationStatus(planningContLoad.getPlanningVessel().getNoPpkb(), planningContLoad.getContNo(), false);

                            if (changeVesselContainer != null) {
                                equipmentHT = equipmentContainerMovementFacadeRemote.findNotFinishedYetByActivityAndService(planningContLoad.getPlanningVessel().getNoPpkb(), planningContLoad.getContNo(), EquipmentContainerMovement.ACTIVITY_HT, EquipmentContainerMovement.SERVICE_RELOCATION);
                            } else {
                                equipmentHT = equipmentContainerMovementFacadeRemote.findNotFinishedYetByActivityAndService(planningContLoad.getPlanningVessel().getNoPpkb(), planningContLoad.getContNo(), EquipmentContainerMovement.ACTIVITY_HT, EquipmentContainerMovement.SERVICE_RECEIVING);
                            }
                        } else {
                            equipmentHT = equipmentContainerMovementFacadeRemote.findNotFinishedYetByActivityAndService(planningContLoad.getPlanningVessel().getNoPpkb(), planningContLoad.getContNo(), EquipmentContainerMovement.ACTIVITY_HT, EquipmentContainerMovement.SERVICE_RECEIVING);
                        }
                    }

                    containerMovementOperationRemote.handleLiftOffLoadContainer(planningContLoad, containerMovementHistory, equipmentLift, equipmentHT, submitData.getDestination());
                    message = new Message(IMessageable.OK, "Success!");
                }
            } else {
                message = new Message(IMessageable.ERROR, "Data not found!");
            }
        } catch (TimeSelectionNotValidException ex) {
            message = new Message(IMessageable.ERROR, "End time must be greater than start time!");
        } catch (LocationNotAvailableException ex) {
            message = new Message(IMessageable.ERROR, "Destination location is not available!");
        } catch (RuntimeException re) {
            message = new Message(IMessageable.ERROR, "Internal error!");
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", re);
        }
        return message;
    }
}
