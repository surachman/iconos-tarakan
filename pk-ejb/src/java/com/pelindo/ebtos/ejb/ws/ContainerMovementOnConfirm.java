/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.ejb.ws;

import com.pelindo.ebtos.ejb.ContainerMovementOperation;
import com.pelindo.ebtos.ejb.facade.remote.CancelLoadingServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterContainerFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterEquipmentFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterOperatorFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterYardFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceReceivingFacadeRemote;
import com.pelindo.ebtos.ejb.remote.ContainerMovementOperationRemote;
import com.pelindo.ebtos.ejb.ws.model.Container;
import com.pelindo.ebtos.ejb.ws.model.ContainerMovementOnConfirmInitialData;
import com.pelindo.ebtos.ejb.ws.model.ContainerMovementOnConfirmSubmitData;
import com.pelindo.ebtos.ejb.ws.model.IMessageable;
import com.pelindo.ebtos.ejb.ws.model.Message;
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
import com.pelindo.ebtos.security.UserUtil;
import com.qtasnim.util.ParseDate;
import java.text.SimpleDateFormat;
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
public class ContainerMovementOnConfirm {
    @EJB
    private MasterEquipmentFacadeRemote masterEquipmentFacadeRemote;
    @EJB
    private MasterOperatorFacadeRemote masterOperatorFacadeRemote;
    @EJB
    private MasterContainerFacadeRemote masterContainerFacadeRemote;
    @EJB
    private MasterYardFacadeRemote masterYardFacadeRemote;
    @EJB
    private ServiceReceivingFacadeRemote serviceReceivingFacadeRemote;
    @EJB
    private CancelLoadingServiceFacadeRemote cancelLoadingServiceFacadeRemote;
    @EJB
    private ContainerMovementOperationRemote containerMovementOperationRemote;

    @WebMethod(operationName = "prepareContainerMovementOnConfirmUI")
    public ContainerMovementOnConfirmInitialData prepareContainerMovementOnConfirmUI() {
        ContainerMovementOnConfirmInitialData initaialData = null;

        try {
            List<Object> ttCodes = masterEquipmentFacadeRemote.findTango4HHT();
            List<Object> htCodes = masterEquipmentFacadeRemote.findHeadTruck4HHT();
            List<Object[]> operators = masterOperatorFacadeRemote.findMasterOperators4HHT();

            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            String serverTime = format.format(new Date());

            initaialData = new ContainerMovementOnConfirmInitialData(IMessageable.OK, "Data found!");
            initaialData.setHtCodes(htCodes);
            initaialData.setTtCodes(ttCodes);
            initaialData.setOperators(operators);
            initaialData.setServerTime(serverTime);

        } catch (RuntimeException re) {
            initaialData = new ContainerMovementOnConfirmInitialData(IMessageable.ERROR, "Internal error!");
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", re);
        }

        return initaialData;
    }

    @WebMethod(operationName = "findMovableOnContainer")
    public Container findMovableOnContainer(@WebParam(name = "contNo") String contNo){
        Container container = null;

        try {
            Object object = masterContainerFacadeRemote.findMovableContainer(contNo);

            if (object != null) {
                if (object instanceof PlanningContLoad) {
                    PlanningContLoad planningContLoad = (PlanningContLoad) object;

                    container = new Container(IMessageable.OK, "Data found!");
                    container.setContNo(planningContLoad.getContNo());
                    container.setYardBlock(planningContLoad.getMasterYard().getBlock());
                    container.setYardSlot(planningContLoad.getYSlot());
                    container.setYardRow(planningContLoad.getYRow());
                    container.setYardTier(planningContLoad.getYTier());

                    if (planningContLoad.getIsTranshipment().equals("TRUE")) {
                        container.setService(ContainerMovementHistory.SERVICE_TRANSHIPMENT);
                    } else if (planningContLoad.getIsShifting().equals("TRUE")) {
                        container.setService(ContainerMovementHistory.SERVICE_SHIFTING);
                    } else {
                        if (planningContLoad.getIsChangeVessel().equals("TRUE")) {
                            CancelLoadingService cancelLoadingService = cancelLoadingServiceFacadeRemote.findByNewPpkbAndRelocationStatus(planningContLoad.getPlanningVessel().getNoPpkb(), planningContLoad.getContNo(), false);

                            if (cancelLoadingService != null) {
                                container.setService(ContainerMovementHistory.SERVICE_RELOCATION);
                            } else {
                                container.setService(ContainerMovementHistory.SERVICE_RECEIVING);
                            }
                        } else {
                            ServiceReceiving serviceReceiving = serviceReceivingFacadeRemote.findByNoPpkbAndContNoNotCancelLoading(planningContLoad.getPlanningVessel().getNoPpkb(), planningContLoad.getContNo());

                            if (serviceReceiving.getIsBehandle().equals("TRUE")) {
                                container.setStatus(ContainerMovementOperation.MOVEMENT_BEHANDLE);
                            } else if (serviceReceiving.getIsCfs().equals("TRUE")) {
                                container.setStatus(ContainerMovementOperation.MOVEMENT_CFS);
                            } else if (serviceReceiving.getIsInspection().equals("TRUE")) {
                                container.setStatus(ContainerMovementOperation.MOVEMENT_INSPECTION);
                            } else {
                                container.setStatus(ContainerMovementOperation.MOVEMENT_CY);
                            }

                            container.setService(ContainerMovementHistory.SERVICE_RECEIVING);
                        }
                    }
                } else if (object instanceof ServiceContDischarge){
                    ServiceContDischarge serviceContDischarge = (ServiceContDischarge) object;

                    container = new Container(IMessageable.OK, "Data found!");
                    container.setContNo(serviceContDischarge.getContNo());
                    container.setYardBlock(serviceContDischarge.getMasterYard().getBlock());
                    container.setYardSlot(serviceContDischarge.getYSlot());
                    container.setYardRow(serviceContDischarge.getYRow());
                    container.setYardTier(serviceContDischarge.getYTier());
                    container.setService(ContainerMovementHistory.SERVICE_DISCHARGE);

                    if (serviceContDischarge.getIsBehandle().equals("TRUE")) {
                        container.setStatus(ContainerMovementOperation.MOVEMENT_BEHANDLE);
                    } else if (serviceContDischarge.getIsCfs().equals("TRUE")) {
                        container.setStatus(ContainerMovementOperation.MOVEMENT_CFS);
                    } else if (serviceContDischarge.getIsInspection().equals("TRUE")) {
                        container.setStatus(ContainerMovementOperation.MOVEMENT_INSPECTION);
                    } else {
                        container.setStatus(ContainerMovementOperation.MOVEMENT_CY);
                    }
                } else if (object instanceof CancelLoadingService) {
                    CancelLoadingService cancelLoadingService = (CancelLoadingService) object;

                    container = new Container(IMessageable.OK, "Data found!");
                    container.setContNo(cancelLoadingService.getContNo());
                    container.setYardBlock(cancelLoadingService.getMasterYard().getBlock());
                    container.setYardSlot(cancelLoadingService.getySlot());
                    container.setYardRow(cancelLoadingService.getyRow());
                    container.setYardTier(cancelLoadingService.getyTier());
                    container.setService(ContainerMovementHistory.SERVICE_CANCEL_LOADING);
                    container.setStatus(ContainerMovementOperation.MOVEMENT_CY);
                } else {
                    container = new Container(IMessageable.ERROR, "Data not found!");
                }
            } else {
                container = new Container(IMessageable.ERROR, "Data not found!");
            }
        } catch (RuntimeException re) {
            container = new Container(IMessageable.ERROR, "Internal error!");
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", re);
        }

        return container;
    }

    @WebMethod(operationName = "containerMovementOnConfirm")
    public Message containerMovementOnConfirm(@WebParam(name = "submitData") ContainerMovementOnConfirmSubmitData submitData) {
        Message message = null;
        
        try {
            UserUtil.setCurrentUser(submitData.getCreatedBy());
            Object object = masterContainerFacadeRemote.findMovableContainer(submitData.getContNo());

            if (object != null) {
                Date startTime = ParseDate.changeDate(submitData.getStartTime());
                Date endTime = ParseDate.changeDate(submitData.getEndTime());
                MasterEquipment tango = masterEquipmentFacadeRemote.find(submitData.getTtCode());
                MasterOperator tangoOperator = masterOperatorFacadeRemote.find(submitData.getTtOperatorCode());
                MasterEquipment ht = masterEquipmentFacadeRemote.find(submitData.getTtCode());
                MasterOperator htOperator = masterOperatorFacadeRemote.find(submitData.getTtOperatorCode());
                Boolean isUseHT = submitData.getIsUseHt() != null && Boolean.parseBoolean(submitData.getIsUseHt());
                MasterYard blockDestination = masterYardFacadeRemote.find(submitData.getYardBlock());
                short destinationSlot = Short.parseShort(submitData.getYardSlot());
                short destinationRow = Short.parseShort(submitData.getYardRow());
                short destinationTier = Short.parseShort(submitData.getYardTier());

                if (object instanceof PlanningContLoad && submitData.getContNo().equals(((PlanningContLoad) object).getContNo())) {
                    PlanningContLoad planningContLoad = (PlanningContLoad) object;

                    ContainerMovementHistory containerMovementHistory = new ContainerMovementHistory();
                    containerMovementHistory.setContNo(planningContLoad.getContNo());
                    containerMovementHistory.setPlanningVessel(planningContLoad.getPlanningVessel());
                    containerMovementHistory.setMasterYard(planningContLoad.getMasterYard());
                    containerMovementHistory.setYardRow(planningContLoad.getYRow());
                    containerMovementHistory.setYardSlot(planningContLoad.getYSlot());
                    containerMovementHistory.setYardTier(planningContLoad.getYTier());
                    if(isUseHT) {
                        containerMovementHistory.setIsHt("TRUE");
                    } else {
                        containerMovementHistory.setIsHt("FALSE");
                    }
                        
                    containerMovementHistory.setService(submitData.getService());
                    containerMovementHistory.setIsBehandle("FALSE");
                    containerMovementHistory.setIsCfs("FALSE");
                    containerMovementHistory.setIsInspection("FALSE");

                    EquipmentContainerMovement equipmentLift = new EquipmentContainerMovement();
                    equipmentLift.setStartTime(startTime);
                    equipmentLift.setEndTime(endTime);
                    equipmentLift.setMasterEquipment(tango);
                    equipmentLift.setMasterOperator(tangoOperator);

                    EquipmentContainerMovement equipmentHT = new EquipmentContainerMovement();
                    equipmentHT.setMasterEquipment(ht);
                    equipmentHT.setMasterOperator(htOperator);

                    if (planningContLoad.getIsShifting().equals("FALSE") && planningContLoad.getIsTranshipment().equals("FALSE")
                            && planningContLoad.getIsChangeVessel().equals("FALSE")) {
                        ServiceReceiving serviceReceiving = serviceReceivingFacadeRemote.findByNoPpkbAndContNoNotCancelLoading(planningContLoad.getPlanningVessel().getNoPpkb(), planningContLoad.getContNo());
                        containerMovementHistory.setIsBehandle(serviceReceiving.getIsBehandle());
                        containerMovementHistory.setIsCfs(serviceReceiving.getIsCfs());
                        containerMovementHistory.setIsInspection(serviceReceiving.getIsInspection());
                    }

                    if (!isUseHT) {
                        planningContLoad.setMasterYard(blockDestination);
                        planningContLoad.setYSlot(destinationSlot);
                        planningContLoad.setYRow(destinationRow);
                        planningContLoad.setYTier(destinationTier);
                    }

                    containerMovementOperationRemote.handleLiftOnLoadContainer(planningContLoad, containerMovementHistory, equipmentLift, equipmentHT, submitData.getStatus());
                    message = new Message(IMessageable.OK, "Success!");
                } else if (object instanceof ServiceContDischarge && submitData.getContNo().equals(((ServiceContDischarge) object).getContNo())){
                    ServiceContDischarge serviceContDischarge = (ServiceContDischarge) object;

                    ContainerMovementHistory containerMovementHistory = new ContainerMovementHistory();
                    containerMovementHistory.setContNo(serviceContDischarge.getContNo());
                    containerMovementHistory.setPlanningVessel(serviceContDischarge.getServiceVessel().getPlanningVessel());
                    containerMovementHistory.setService(submitData.getService());
                    containerMovementHistory.setMasterYard(serviceContDischarge.getMasterYard());
                    containerMovementHistory.setYardRow(serviceContDischarge.getYRow());
                    containerMovementHistory.setYardSlot(serviceContDischarge.getYSlot());
                    containerMovementHistory.setYardTier(serviceContDischarge.getYTier());
                    if (isUseHT) {
                        containerMovementHistory.setIsHt("TRUE");
                    } else {
                        containerMovementHistory.setIsHt("FALSE");
                    }
                    containerMovementHistory.setIsBehandle(serviceContDischarge.getIsBehandle());
                    containerMovementHistory.setIsCfs(serviceContDischarge.getIsCfs());
                    containerMovementHistory.setIsInspection(serviceContDischarge.getIsInspection());

                    EquipmentContainerMovement equipmentLift = new EquipmentContainerMovement();
                    equipmentLift.setStartTime(startTime);
                    equipmentLift.setEndTime(endTime);
                    equipmentLift.setMasterEquipment(tango);
                    equipmentLift.setMasterOperator(tangoOperator);

                    EquipmentContainerMovement equipmentHT = new EquipmentContainerMovement();
                    equipmentHT.setMasterEquipment(ht);
                    equipmentHT.setMasterOperator(htOperator);

                    if (!isUseHT) {
                        serviceContDischarge.setMasterYard(blockDestination);
                        serviceContDischarge.setYSlot(destinationSlot);
                        serviceContDischarge.setYRow(destinationRow);
                        serviceContDischarge.setYTier(destinationTier);
                    }

                    containerMovementOperationRemote.handleLiftOnDischargeContainer(serviceContDischarge, containerMovementHistory, equipmentLift, equipmentHT, submitData.getStatus());
                    message = new Message(IMessageable.OK, "Success!");
                } else if (object instanceof CancelLoadingService && submitData.getContNo().equals(((CancelLoadingService) object).getContNo())){
                    CancelLoadingService cancelLoadingService = (CancelLoadingService) object;

                    ContainerMovementHistory containerMovementHistory = new ContainerMovementHistory();
                    containerMovementHistory.setContNo(cancelLoadingService.getContNo());
                    containerMovementHistory.setPlanningVessel(cancelLoadingService.getPlanningVessel());
                    containerMovementHistory.setService(submitData.getService());
                    containerMovementHistory.setMasterYard(cancelLoadingService.getMasterYard());
                    containerMovementHistory.setYardRow(cancelLoadingService.getyRow());
                    containerMovementHistory.setYardSlot(cancelLoadingService.getySlot());
                    containerMovementHistory.setYardTier(cancelLoadingService.getyTier());
                    
                    if (isUseHT) {
                        containerMovementHistory.setIsHt("TRUE"); 
                    } else {
                        containerMovementHistory.setIsHt("FALSE"); 
                    }

                    EquipmentContainerMovement equipmentLift = new EquipmentContainerMovement();
                    equipmentLift.setStartTime(startTime);
                    equipmentLift.setEndTime(endTime);
                    equipmentLift.setMasterEquipment(tango);
                    equipmentLift.setMasterOperator(tangoOperator);

                    EquipmentContainerMovement equipmentHT = new EquipmentContainerMovement();
                    equipmentHT.setMasterEquipment(ht);
                    equipmentHT.setMasterOperator(htOperator);

                    if (!isUseHT) {
                        cancelLoadingService.setMasterYard(blockDestination);
                        cancelLoadingService.setySlot(destinationSlot);
                        cancelLoadingService.setyRow(destinationRow);
                        cancelLoadingService.setyTier(destinationTier);
                    }

                    containerMovementOperationRemote.handleLiftOnCancelLoadingContainer(cancelLoadingService, containerMovementHistory, equipmentLift, equipmentHT);
                    message = new Message(IMessageable.OK, "Success!");
                } else {
                    message = new Message(IMessageable.ERROR, "Data not found!");
                }
            } else {
                message = new Message(IMessageable.ERROR, "Data not found!");
            }
        } catch (TimeSelectionNotValidException ex) {
            message = new Message(IMessageable.ERROR, "End time must be greater than start time!");
        } catch (LocationNotAvailableException ex) {
            message = new Message(IMessageable.ERROR, "Destination location is not available!");
        } catch (ContainerNotMovableException ex) {
            message = new Message(IMessageable.ERROR, "Cant move container with current state!");
        } catch (RuntimeException re) {
            message = new Message(IMessageable.ERROR, "Internal error!");
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", re);
        }
        return message;
    }
}
