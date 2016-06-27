/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb;

import com.pelindo.ebtos.ejb.facade.ContainerMovementHistoryFacadeLocal;
import com.pelindo.ebtos.ejb.local.YardOperationLocal;
import com.pelindo.ebtos.ejb.facade.local.CancelLoadingServiceFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.EquipmentContainerMovementFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.PlanningContLoadFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.PlanningShiftDischargeFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.ServiceBehandleFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.ServiceCfsFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.ServiceContDischargeFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.ServiceContTranshipmentFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.ServiceInspectionFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.ServiceReceivingFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.ServiceShiftingFacadeLocal;
import com.pelindo.ebtos.ejb.remote.ContainerMovementOperationRemote;
import com.pelindo.ebtos.exception.ContainerNotMovableException;
import com.pelindo.ebtos.exception.LocationNotAvailableException;
import com.pelindo.ebtos.exception.TimeSelectionNotValidException;
import com.pelindo.ebtos.model.YardLocation;
import com.pelindo.ebtos.model.db.CancelLoadingService;
import com.pelindo.ebtos.model.db.ContainerMovementHistory;
import com.pelindo.ebtos.model.db.EquipmentContainerMovement;
import com.pelindo.ebtos.model.db.PlanningContLoad;
import com.pelindo.ebtos.model.db.PlanningShiftDischarge;
import com.pelindo.ebtos.model.db.ServiceBehandle;
import com.pelindo.ebtos.model.db.ServiceCfs;
import com.pelindo.ebtos.model.db.ServiceContDischarge;
import com.pelindo.ebtos.model.db.ServiceContTranshipment;
import com.pelindo.ebtos.model.db.ServiceInspection;
import com.pelindo.ebtos.model.db.ServiceReceiving;
import com.pelindo.ebtos.model.db.ServiceShifting;
import com.pelindo.ebtos.util.GrossConverter;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author R. Seno Anggoro A
 */
@Stateless
public class ContainerMovementOperation implements ContainerMovementOperationRemote {
    public static final String MOVEMENT_CFS = "CFS";
    public static final String MOVEMENT_BEHANDLE = "BEHANDLE";
    public static final String MOVEMENT_INSPECTION = "INSPECTION";
    public static final String MOVEMENT_CY = "CY";
    
    @EJB
    private CancelLoadingServiceFacadeLocal cancelLoadingServiceFacadeLocal;
    @EJB
    private YardOperationLocal yardOperationLocal;
    @EJB
    private ContainerMovementHistoryFacadeLocal containerMovementHistoryFacadeLocal;
    @EJB
    private EquipmentContainerMovementFacadeLocal equipmentContainerMovementFacadeLocal;
    @EJB
    private ServiceBehandleFacadeLocal serviceBehandleFacadeLocal;
    @EJB
    private ServiceInspectionFacadeLocal serviceInspectionFacadeLocal;
    @EJB
    private ServiceCfsFacadeLocal serviceCfsFacadeLocal;
    @EJB
    private ServiceContDischargeFacadeLocal serviceContDischargeFacadeLocal;
    @EJB
    private ServiceReceivingFacadeLocal serviceReceivingFacadeLocal;
    @EJB
    private ServiceShiftingFacadeLocal serviceShiftingFacadeLocal;
    @EJB
    private PlanningShiftDischargeFacadeLocal planningShiftDischargeFacadeLocal;
    @EJB
    private ServiceContTranshipmentFacadeLocal serviceContTranshipmentFacadeLocal;
    @EJB
    private PlanningContLoadFacadeLocal planningContLoadFacadeLocal;

    public void handleLiftOnCancelLoadingContainer(CancelLoadingService cancelLoadingService, ContainerMovementHistory containerMovementHistory, EquipmentContainerMovement equipmentLift, EquipmentContainerMovement equipmentHT) throws ContainerNotMovableException, LocationNotAvailableException, TimeSelectionNotValidException{
        if (equipmentLift.getStartTime().after(equipmentLift.getEndTime()) || equipmentLift.getStartTime().equals(equipmentLift.getEndTime())) {
            throw new TimeSelectionNotValidException("End time must be after start time!");
        }

        String grossClass = GrossConverter.convert(cancelLoadingService.getContSize(), cancelLoadingService.getContGross());

        if (containerMovementHistory.getIsHt().equals("FALSE")) {
            YardLocation newLocation = new YardLocation(cancelLoadingService.getMasterYard().getBlock(), cancelLoadingService.getySlot(), cancelLoadingService.getyRow(), cancelLoadingService.getyTier());
            yardOperationLocal.liftoff(cancelLoadingService.getPlanningVessel().getNoPpkb(), cancelLoadingService.getContNo(), cancelLoadingService.getMasterContainerType().getContType().toString(), cancelLoadingService.getContSize(), cancelLoadingService.getContGross(), cancelLoadingService.getDischargePort().getPortCode(), grossClass, newLocation);
        }

        YardLocation oldLocation = new YardLocation(containerMovementHistory.getMasterYard().getBlock(), containerMovementHistory.getYardSlot(), containerMovementHistory.getYardRow(), containerMovementHistory.getYardTier());
        yardOperationLocal.lifton(cancelLoadingService.getPlanningVessel().getNoPpkb(), cancelLoadingService.getContNo(), cancelLoadingService.getContSize(), oldLocation);

        equipmentLift.setContNo(containerMovementHistory.getContNo());
        equipmentLift.setPlanningVessel(containerMovementHistory.getPlanningVessel());
        equipmentLift.setActivity(EquipmentContainerMovement.ACTIVITY_LIFTON);
        equipmentLift.setService(EquipmentContainerMovement.SERVICE_CANCEL_LOADING);

        containerMovementHistory.setIsBehandle("FALSE");
        containerMovementHistory.setIsCfs("FALSE");
        containerMovementHistory.setIsInspection("FALSE");

        if (containerMovementHistory.getIsHt().equals("TRUE")) {
            cancelLoadingService.setPosisi(CancelLoadingService.AT_MOVEMENT);
            equipmentHT.setContNo(containerMovementHistory.getContNo());
            equipmentHT.setPlanningVessel(containerMovementHistory.getPlanningVessel());
            equipmentHT.setActivity(EquipmentContainerMovement.ACTIVITY_HT);
            equipmentHT.setService(EquipmentContainerMovement.SERVICE_CANCEL_LOADING);
            equipmentHT.setStartTime(equipmentLift.getEndTime());

            equipmentHT.setContainerMovementHistory(containerMovementHistory);
            equipmentLift.setContainerMovementHistory(containerMovementHistory);
            containerMovementHistoryFacadeLocal.create(containerMovementHistory, equipmentLift, equipmentHT);
        } else {
            equipmentLift.setContainerMovementHistory(containerMovementHistory);
            containerMovementHistoryFacadeLocal.create(containerMovementHistory, equipmentLift);
        }

        cancelLoadingServiceFacadeLocal.edit(cancelLoadingService);
    }

    public void handleLiftOffCancelLoadingContainer(CancelLoadingService cancelLoadingService, ContainerMovementHistory containerMovementHistory, EquipmentContainerMovement equipmentLift, EquipmentContainerMovement equipmentHT, String moveTo) throws LocationNotAvailableException, TimeSelectionNotValidException {
        if (equipmentLift.getStartTime().after(equipmentLift.getEndTime()) || equipmentLift.getStartTime().equals(equipmentLift.getEndTime())) {
            throw new TimeSelectionNotValidException("End time must be after start time!");
        }

        if (moveTo.equalsIgnoreCase(MOVEMENT_CY)) {
            String grossClass = GrossConverter.convert(cancelLoadingService.getContSize(), cancelLoadingService.getContGross());
            YardLocation newLocation = new YardLocation(cancelLoadingService.getMasterYard().getBlock(), cancelLoadingService.getySlot(), cancelLoadingService.getyRow(), cancelLoadingService.getyTier());
            yardOperationLocal.liftoff(cancelLoadingService.getPlanningVessel().getNoPpkb(), cancelLoadingService.getContNo(), cancelLoadingService.getMasterContainerType().getContType().toString(), cancelLoadingService.getContSize(), cancelLoadingService.getContGross(), cancelLoadingService.getDischargePort().getPortCode(), grossClass, newLocation);
        } else {
            throw new LocationNotAvailableException("Cancel Loading Container cant be moved except to CY");
        }

        containerMovementHistory.setIsHt("FALSE");
        containerMovementHistory.setIsCfs("FALSE");
        containerMovementHistory.setIsBehandle("FALSE");
        containerMovementHistory.setIsInspection("FALSE");

        equipmentLift.setContNo(containerMovementHistory.getContNo());
        equipmentLift.setPlanningVessel(containerMovementHistory.getPlanningVessel());
        equipmentLift.setActivity(EquipmentContainerMovement.ACTIVITY_LIFTOFF);
        equipmentLift.setService(EquipmentContainerMovement.SERVICE_CANCEL_LOADING);
        equipmentLift.setContainerMovementHistory(containerMovementHistory);

        cancelLoadingService.setPosisi(CancelLoadingService.AT_CY);
        equipmentHT.setEndTime(equipmentLift.getStartTime());

        containerMovementHistoryFacadeLocal.edit(containerMovementHistory);
        equipmentContainerMovementFacadeLocal.create(equipmentLift);
        equipmentContainerMovementFacadeLocal.edit(equipmentHT);
        cancelLoadingServiceFacadeLocal.edit(cancelLoadingService);
    }

    public void handleLiftOnDischargeContainer(ServiceContDischarge serviceContDischarge, ContainerMovementHistory containerMovementHistory, EquipmentContainerMovement equipmentLift, EquipmentContainerMovement equipmentHT, String cfsOperationStatus) throws LocationNotAvailableException, ContainerNotMovableException, TimeSelectionNotValidException {
        if (equipmentLift.getStartTime().after(equipmentLift.getEndTime()) || equipmentLift.getStartTime().equals(equipmentLift.getEndTime())) {
            throw new TimeSelectionNotValidException("End time must be after start time!");
        }

        if (containerMovementHistory.getIsHt().equals("FALSE")) {
            YardLocation newLocation = new YardLocation(serviceContDischarge.getMasterYard().getBlock(), serviceContDischarge.getYSlot(), serviceContDischarge.getYRow(), serviceContDischarge.getYTier());
            yardOperationLocal.liftoff(serviceContDischarge.getServiceVessel().getNoPpkb(), serviceContDischarge.getContNo(), serviceContDischarge.getMasterContainerType().getContType().toString(), serviceContDischarge.getContSize(), serviceContDischarge.getContGross(), serviceContDischarge.getDischPort(), serviceContDischarge.getGrossClass(), newLocation);
        }

        YardLocation oldLocation = new YardLocation(containerMovementHistory.getMasterYard().getBlock(), containerMovementHistory.getYardSlot(), containerMovementHistory.getYardRow(), containerMovementHistory.getYardTier());
        yardOperationLocal.lifton(serviceContDischarge.getServiceVessel().getNoPpkb(), serviceContDischarge.getContNo(), serviceContDischarge.getContSize(), oldLocation);

        if (serviceContDischarge.getIsBehandle().equals("TRUE")) {
            ServiceBehandle serviceBehandle = serviceBehandleFacadeLocal.findNotFinishedYetByNoPpkbAndContNo(serviceContDischarge.getServiceVessel().getNoPpkb(), serviceContDischarge.getContNo());
            serviceBehandle.setEndTime(equipmentLift.getStartTime());
            serviceBehandleFacadeLocal.edit(serviceBehandle);
        } else if (serviceContDischarge.getIsInspection().equals("TRUE")) {
            ServiceInspection serviceInspection = serviceInspectionFacadeLocal.findNotFinishedYetByNoPpkbAndContNo(serviceContDischarge.getServiceVessel().getNoPpkb(), serviceContDischarge.getContNo());
            serviceInspection.setEndTime(equipmentLift.getStartTime());
            serviceInspectionFacadeLocal.edit(serviceInspection);
        } else if (serviceContDischarge.getIsCfs().equals("TRUE")) {
            ServiceCfs serviceCfs = serviceCfsFacadeLocal.findNotFinishedYetByNoPpkbAndContNo(serviceContDischarge.getServiceVessel().getNoPpkb(), serviceContDischarge.getContNo());
            serviceCfs.setOperationStatus(cfsOperationStatus);
            serviceCfs.setEndTime(equipmentLift.getStartTime());
            serviceCfsFacadeLocal.edit(serviceCfs);
        }

        equipmentLift.setContNo(containerMovementHistory.getContNo());
        equipmentLift.setPlanningVessel(containerMovementHistory.getPlanningVessel());
        equipmentLift.setActivity(EquipmentContainerMovement.ACTIVITY_LIFTON);
        equipmentLift.setService(EquipmentContainerMovement.SERVICE_DISCHARGE);

        containerMovementHistory.setIsBehandle("FALSE");
        containerMovementHistory.setIsCfs("FALSE");
        containerMovementHistory.setIsInspection("FALSE");

        if (containerMovementHistory.getIsHt().equals("TRUE")) {
            serviceContDischarge.setPosition(ServiceContDischarge.LOCATION_MOVEMENT);
            equipmentHT.setContNo(containerMovementHistory.getContNo());
            equipmentHT.setPlanningVessel(containerMovementHistory.getPlanningVessel());
            equipmentHT.setActivity(EquipmentContainerMovement.ACTIVITY_HT);
            equipmentHT.setService(EquipmentContainerMovement.SERVICE_DISCHARGE);
            equipmentHT.setStartTime(equipmentLift.getEndTime());

            equipmentHT.setContainerMovementHistory(containerMovementHistory);
            equipmentLift.setContainerMovementHistory(containerMovementHistory);
            containerMovementHistoryFacadeLocal.create(containerMovementHistory, equipmentLift, equipmentHT);
        } else {
            serviceContDischarge.setIsBehandle("FALSE");
            serviceContDischarge.setIsInspection("FALSE");
            serviceContDischarge.setIsCfs("FALSE");
            equipmentLift.setContainerMovementHistory(containerMovementHistory);
            containerMovementHistoryFacadeLocal.create(containerMovementHistory, equipmentLift);
        }

        serviceContDischargeFacadeLocal.edit(serviceContDischarge);
    }

    public void handleLiftOffDischargeContainer(ServiceContDischarge serviceContDischarge, ContainerMovementHistory containerMovementHistory, EquipmentContainerMovement equipmentLift, EquipmentContainerMovement equipmentHT, String moveTo) throws TimeSelectionNotValidException, LocationNotAvailableException {
        if (equipmentLift.getStartTime().after(equipmentLift.getEndTime()) || equipmentLift.getStartTime().equals(equipmentLift.getEndTime())) {
            throw new TimeSelectionNotValidException("End time must be after start time!");
        }

        if (moveTo.equalsIgnoreCase(MOVEMENT_CY)) {
            YardLocation newLocation = new YardLocation(serviceContDischarge.getMasterYard().getBlock(), serviceContDischarge.getYSlot(), serviceContDischarge.getYRow(), serviceContDischarge.getYTier());
            yardOperationLocal.liftoff(serviceContDischarge.getServiceVessel().getNoPpkb(), serviceContDischarge.getContNo(), serviceContDischarge.getMasterContainerType().getContType().toString(), serviceContDischarge.getContSize(), serviceContDischarge.getContGross(), serviceContDischarge.getDischPort(), serviceContDischarge.getGrossClass(), newLocation);

            if (serviceContDischarge.getIsCfs().equals("TRUE")) {
                ServiceCfs serviceCfs = serviceCfsFacadeLocal.findLastCfsByPpkbAndContNo(serviceContDischarge.getServiceVessel().getNoPpkb(), serviceContDischarge.getContNo());

                if (serviceCfs.getOperationStatus().equalsIgnoreCase("COMPLETED")) {
                    if (serviceContDischarge.getContStatus().equalsIgnoreCase("MTY")) {
                        serviceContDischarge.setIsDelivery("TRUE");
                        serviceCfs.setOperation("STUFFING");

                        ServiceReceiving serviceReceiving = new ServiceReceiving(serviceContDischarge);
                        serviceReceiving.setTransactionDate(equipmentLift.getStartTime());
                        serviceReceiving.setPlanningVessel(serviceContDischarge.getServiceVessel().getPlanningVessel());
                        serviceReceivingFacadeLocal.edit(serviceReceiving);
                    } else {
                        serviceCfs.setOperation("STRIPPING");
                        serviceContDischarge.setIsCfs("FALSE");
                        serviceContDischarge.setContStatus("MTY");
                        serviceContDischarge.setGrossClass("E");
                        serviceContDischarge.setContGross(0);
                        serviceContDischarge.setExStripping("TRUE");
                    }

                    serviceCfsFacadeLocal.edit(serviceCfs);
                } else {
                    serviceContDischarge.setIsCfs("FALSE");
                }
            }

            serviceContDischarge.setIsBehandle("FALSE");
            serviceContDischarge.setIsInspection("FALSE");
        } else if (moveTo.equals(MOVEMENT_BEHANDLE)) {
            serviceContDischarge.setIsBehandle("TRUE");

            ServiceBehandle serviceBehandle = new ServiceBehandle(serviceContDischarge);
            serviceBehandle.setStartTime(equipmentLift.getEndTime());

            serviceBehandleFacadeLocal.edit(serviceBehandle);
        } else if (moveTo.equals(MOVEMENT_INSPECTION)) {
            serviceContDischarge.setIsInspection("TRUE");

            ServiceInspection serviceInspection = new ServiceInspection(serviceContDischarge);
            serviceInspection.setStartTime(equipmentLift.getEndTime());

            serviceInspectionFacadeLocal.edit(serviceInspection);
        } else if (moveTo.equals(MOVEMENT_CFS)) {
            ServiceCfs lastRecord = serviceCfsFacadeLocal.findLastCfsByPpkbAndContNo(serviceContDischarge.getServiceVessel().getNoPpkb(), serviceContDischarge.getContNo());

            if (lastRecord != null) {
                lastRecord.setIsLast("FALSE");
                serviceCfsFacadeLocal.edit(lastRecord);
            }

            ServiceCfs serviceCfs = new ServiceCfs(serviceContDischarge);
            serviceCfsFacadeLocal.edit(serviceCfs);
            serviceContDischarge.setIsCfs("TRUE");
        }

        containerMovementHistory.setIsHt("FALSE");
        containerMovementHistory.setIsCfs(serviceContDischarge.getIsCfs());
        containerMovementHistory.setIsBehandle(serviceContDischarge.getIsBehandle());
        containerMovementHistory.setIsInspection(serviceContDischarge.getIsInspection());

        equipmentLift.setContNo(containerMovementHistory.getContNo());
        equipmentLift.setPlanningVessel(containerMovementHistory.getPlanningVessel());
        equipmentLift.setActivity(EquipmentContainerMovement.ACTIVITY_LIFTOFF);
        equipmentLift.setService(EquipmentContainerMovement.SERVICE_DISCHARGE);
        equipmentLift.setContainerMovementHistory(containerMovementHistory);

        equipmentHT.setEndTime(equipmentLift.getStartTime());

        serviceContDischarge.setPosition(ServiceContDischarge.LOCATION_CY);

        containerMovementHistoryFacadeLocal.edit(containerMovementHistory);
        equipmentContainerMovementFacadeLocal.create(equipmentLift);
        equipmentContainerMovementFacadeLocal.edit(equipmentHT);
        serviceContDischargeFacadeLocal.edit(serviceContDischarge);
    }

    public void handleLiftOnLoadContainer(PlanningContLoad planningContLoad, ContainerMovementHistory containerMovementHistory, EquipmentContainerMovement equipmentLift, EquipmentContainerMovement equipmentHT, String cfsOperationStatus) throws TimeSelectionNotValidException, LocationNotAvailableException, ContainerNotMovableException {
        if (equipmentLift.getStartTime().after(equipmentLift.getEndTime()) || equipmentLift.getStartTime().equals(equipmentLift.getEndTime())) {
            throw new TimeSelectionNotValidException("End time must be after start time!");
        }
        
        String grossClass = GrossConverter.convert(planningContLoad.getContSize(), planningContLoad.getContGross());

        if (containerMovementHistory.getIsHt().equals("FALSE")) {
            YardLocation newLocation = new YardLocation(planningContLoad.getMasterYard().getBlock(), planningContLoad.getYSlot(), planningContLoad.getYRow(), planningContLoad.getYTier());
            yardOperationLocal.liftoff(planningContLoad.getPlanningVessel().getNoPpkb(), planningContLoad.getContNo(), planningContLoad.getMasterContainerType().getContType().toString(), planningContLoad.getContSize(), planningContLoad.getContGross(), planningContLoad.getDischPort(), grossClass, newLocation);
        }

        YardLocation oldLocation = new YardLocation(containerMovementHistory.getMasterYard().getBlock(), containerMovementHistory.getYardSlot(), containerMovementHistory.getYardRow(), containerMovementHistory.getYardTier());
        yardOperationLocal.lifton(planningContLoad.getPlanningVessel().getNoPpkb(), planningContLoad.getContNo(), planningContLoad.getContSize(), oldLocation);

        equipmentLift.setContNo(containerMovementHistory.getContNo());
        equipmentLift.setActivity(EquipmentContainerMovement.ACTIVITY_LIFTON);
        equipmentLift.setPlanningVessel(planningContLoad.getPlanningVessel());

        if (containerMovementHistory.getIsHt().equals("TRUE")) {
            planningContLoad.setPosition(PlanningContLoad.LOCATION_MOVEMENT);

            equipmentHT.setContNo(containerMovementHistory.getContNo());
            equipmentHT.setActivity(EquipmentContainerMovement.ACTIVITY_HT);
            equipmentHT.setPlanningVessel(planningContLoad.getPlanningVessel());
            equipmentHT.setStartTime(equipmentLift.getEndTime());

            containerMovementHistory.setIsBehandle("FALSE");
            containerMovementHistory.setIsCfs("FALSE");
            containerMovementHistory.setIsInspection("FALSE");

            if (planningContLoad.getIsShifting().equals("TRUE")) {
                equipmentLift.setService(EquipmentContainerMovement.SERVICE_SHIFTING);
                equipmentHT.setService(EquipmentContainerMovement.SERVICE_SHIFTING);

                ServiceShifting serviceShifting = serviceShiftingFacadeLocal.findByNoPpkbAndContNo(planningContLoad.getPlanningVessel().getNoPpkb(), planningContLoad.getContNo());
                serviceShifting.setPosition(ServiceShifting.LOCATION_MOVEMENT);

                serviceShiftingFacadeLocal.edit(serviceShifting);
            } else if (planningContLoad.getIsTranshipment().equals("TRUE")) {
                equipmentLift.setService(EquipmentContainerMovement.SERVICE_TRANSHIPMENT);
                equipmentHT.setService(EquipmentContainerMovement.SERVICE_TRANSHIPMENT);
            } else {
                if (planningContLoad.getIsChangeVessel().equals("TRUE")) {
                    CancelLoadingService changeVesselContainer = cancelLoadingServiceFacadeLocal.findByNewPpkbAndRelocationStatus(planningContLoad.getPlanningVessel().getNoPpkb(), planningContLoad.getContNo(), false);

                    if (changeVesselContainer != null) {
                        equipmentLift.setService(EquipmentContainerMovement.SERVICE_RELOCATION);
                        equipmentHT.setService(EquipmentContainerMovement.SERVICE_RELOCATION);

                        changeVesselContainer.setPosisi(CancelLoadingService.AT_MOVEMENT);
                        cancelLoadingServiceFacadeLocal.edit(changeVesselContainer);
                    } else {
                        equipmentLift.setService(EquipmentContainerMovement.SERVICE_RECEIVING);
                        equipmentHT.setService(EquipmentContainerMovement.SERVICE_RECEIVING);
                    }
                } else {
                    equipmentLift.setService(EquipmentContainerMovement.SERVICE_RECEIVING);
                    equipmentHT.setService(EquipmentContainerMovement.SERVICE_RECEIVING);

                    ServiceReceiving serviceReceiving = serviceReceivingFacadeLocal.findByNoPpkbAndContNoNotCancelLoading(planningContLoad.getPlanningVessel().getNoPpkb(), planningContLoad.getContNo());

                    if (serviceReceiving.getIsBehandle().equals("TRUE")) {
                        ServiceBehandle serviceBehandle = serviceBehandleFacadeLocal.findNotFinishedYetByNoPpkbAndContNo(serviceReceiving.getPlanningVessel().getNoPpkb(), serviceReceiving.getContNo());
                        serviceBehandle.setEndTime(equipmentHT.getStartTime());

                        serviceBehandleFacadeLocal.edit(serviceBehandle);
                    } else if (serviceReceiving.getIsInspection().equals("TRUE")) {
                        ServiceInspection serviceInspection = serviceInspectionFacadeLocal.findNotFinishedYetByNoPpkbAndContNo(serviceReceiving.getPlanningVessel().getNoPpkb(), serviceReceiving.getContNo());
                        serviceInspection.setEndTime(equipmentHT.getStartTime());

                        serviceInspectionFacadeLocal.edit(serviceInspection);
                    } else if (serviceReceiving.getIsCfs().equals("TRUE")) {
                        ServiceCfs serviceCfs = serviceCfsFacadeLocal.findNotFinishedYetByNoPpkbAndContNo(planningContLoad.getPlanningVessel().getNoPpkb(), planningContLoad.getContNo());
                        serviceCfs.setOperationStatus(cfsOperationStatus);
                        serviceCfs.setEndTime(equipmentHT.getStartTime());
                        serviceCfsFacadeLocal.edit(serviceCfs);
                    }

                    serviceReceiving.setIsLifton("TRUE");
                    serviceReceivingFacadeLocal.edit(serviceReceiving);
                }
            }

            equipmentHT.setContainerMovementHistory(containerMovementHistory);
            equipmentLift.setContainerMovementHistory(containerMovementHistory);
            containerMovementHistoryFacadeLocal.create(containerMovementHistory, equipmentLift, equipmentHT);
        } else {
            if (planningContLoad.getIsShifting().equals("TRUE")) {
                equipmentLift.setService(EquipmentContainerMovement.SERVICE_SHIFTING);

                PlanningShiftDischarge planningShiftDischarge = planningShiftDischargeFacadeLocal.findByPpkbAndContNo(planningContLoad.getPlanningVessel().getNoPpkb(), planningContLoad.getContNo());
                planningShiftDischarge.setMasterYard(planningContLoad.getMasterYard());
                planningShiftDischarge.setYRow(planningContLoad.getYRow());
                planningShiftDischarge.setYSlot(planningContLoad.getYSlot());
                planningShiftDischarge.setYTier(planningContLoad.getYTier());

                planningShiftDischargeFacadeLocal.edit(planningShiftDischarge);
            } else if (planningContLoad.getIsTranshipment().equals("TRUE")) {
                equipmentLift.setService(EquipmentContainerMovement.SERVICE_TRANSHIPMENT);

                ServiceContTranshipment serviceContTranshipment = serviceContTranshipmentFacadeLocal.findByNewPpkbAndContNo(planningContLoad.getPlanningVessel().getNoPpkb(), planningContLoad.getContNo());
                serviceContTranshipment.setMasterYard(planningContLoad.getMasterYard());
                serviceContTranshipment.setYRow(planningContLoad.getYRow());
                serviceContTranshipment.setYSlot(planningContLoad.getYSlot());
                serviceContTranshipment.setYTier(planningContLoad.getYTier());

                serviceContTranshipmentFacadeLocal.edit(serviceContTranshipment);
            } else {
                if (planningContLoad.getIsChangeVessel().equals("TRUE")) {
                    CancelLoadingService changeVesselContainer = cancelLoadingServiceFacadeLocal.findByNewPpkbAndRelocationStatus(planningContLoad.getPlanningVessel().getNoPpkb(), planningContLoad.getContNo(), false);

                    if (changeVesselContainer != null) {
                        equipmentLift.setService(EquipmentContainerMovement.SERVICE_RELOCATION);
                        changeVesselContainer.setHasRelocated("TRUE");
                        cancelLoadingServiceFacadeLocal.edit(changeVesselContainer);
                    } else {
                        equipmentLift.setService(EquipmentContainerMovement.SERVICE_RECEIVING);
                    }
                } else {
                    equipmentLift.setService(EquipmentContainerMovement.SERVICE_RECEIVING);

                    ServiceReceiving serviceReceiving = serviceReceivingFacadeLocal.findByNoPpkbAndContNoNotCancelLoading(planningContLoad.getPlanningVessel().getNoPpkb(), planningContLoad.getContNo());

                    serviceReceiving.setMasterYard(planningContLoad.getMasterYard());
                    serviceReceiving.setYRow(planningContLoad.getYRow());
                    serviceReceiving.setYSlot(planningContLoad.getYSlot());
                    serviceReceiving.setYTier(planningContLoad.getYTier());

                    if (serviceReceiving.getIsBehandle().equals("TRUE")) {
                        ServiceBehandle serviceBehandle = serviceBehandleFacadeLocal.findNotFinishedYetByNoPpkbAndContNo(serviceReceiving.getPlanningVessel().getNoPpkb(), serviceReceiving.getContNo());
                        serviceBehandle.setEndTime(equipmentHT.getStartTime());

                        serviceBehandleFacadeLocal.edit(serviceBehandle);
                    } else if (serviceReceiving.getIsInspection().equals("TRUE")) {
                        ServiceInspection serviceInspection = serviceInspectionFacadeLocal.findNotFinishedYetByNoPpkbAndContNo(serviceReceiving.getPlanningVessel().getNoPpkb(), serviceReceiving.getContNo());
                        serviceInspection.setEndTime(equipmentHT.getStartTime());

                        serviceInspectionFacadeLocal.edit(serviceInspection);
                    } else if (serviceReceiving.getIsCfs().equals("TRUE")) {
                        ServiceCfs serviceCfs = serviceCfsFacadeLocal.findNotFinishedYetByNoPpkbAndContNo(planningContLoad.getPlanningVessel().getNoPpkb(), planningContLoad.getContNo());
                        serviceCfs.setOperationStatus(cfsOperationStatus);
                        serviceCfs.setEndTime(equipmentHT.getStartTime());
                        serviceCfsFacadeLocal.edit(serviceCfs);
                    }

                    serviceReceiving.setIsLifton("FALSE");
                    serviceReceiving.setIsBehandle("FALSE");
                    serviceReceiving.setIsInspection("FALSE");
                    serviceReceiving.setIsCfs("FALSE");
                    serviceReceivingFacadeLocal.edit(serviceReceiving);
                }
            }

            equipmentLift.setContainerMovementHistory(containerMovementHistory);
            containerMovementHistoryFacadeLocal.create(containerMovementHistory, equipmentLift);
        }

        planningContLoadFacadeLocal.edit(planningContLoad);
    }

    public void handleLiftOffLoadContainer(PlanningContLoad planningContLoad, ContainerMovementHistory containerMovementHistory, EquipmentContainerMovement equipmentLift, EquipmentContainerMovement equipmentHT, String moveTo) throws TimeSelectionNotValidException, LocationNotAvailableException{
        if (equipmentLift.getStartTime().after(equipmentLift.getEndTime()) || equipmentLift.getStartTime().equals(equipmentLift.getEndTime())) {
            throw new TimeSelectionNotValidException("End time must be after start time!");
        }

        String grossClass = GrossConverter.convert(planningContLoad.getContSize(), planningContLoad.getContGross());

        if (moveTo.equalsIgnoreCase(MOVEMENT_CY)) {
            YardLocation newLocation = new YardLocation(planningContLoad.getMasterYard().getBlock(), planningContLoad.getYSlot(), planningContLoad.getYRow(), planningContLoad.getYTier());
            yardOperationLocal.liftoff(planningContLoad.getPlanningVessel().getNoPpkb(), planningContLoad.getContNo(), planningContLoad.getMasterContainerType().getContType().toString(), planningContLoad.getContSize(), planningContLoad.getContGross(), planningContLoad.getDischPort(), grossClass, newLocation);
        }

        equipmentLift.setContNo(containerMovementHistory.getContNo());
        equipmentLift.setActivity(EquipmentContainerMovement.ACTIVITY_LIFTOFF);
        equipmentLift.setPlanningVessel(planningContLoad.getPlanningVessel());

        planningContLoad.setPosition(PlanningContLoad.LOCATION_CY);

        containerMovementHistory.setIsHt("FALSE");

        equipmentHT.setEndTime(equipmentLift.getStartTime());

        if (planningContLoad.getIsShifting().equals("TRUE")) {
            equipmentLift.setService(EquipmentContainerMovement.SERVICE_SHIFTING);

            PlanningShiftDischarge planningShiftDischarge = planningShiftDischargeFacadeLocal.findByPpkbAndContNo(planningContLoad.getPlanningVessel().getNoPpkb(), planningContLoad.getContNo());
            planningShiftDischarge.setMasterYard(planningContLoad.getMasterYard());
            planningShiftDischarge.setYRow(planningContLoad.getYRow());
            planningShiftDischarge.setYSlot(planningContLoad.getYSlot());
            planningShiftDischarge.setYTier(planningContLoad.getYTier());
            planningShiftDischargeFacadeLocal.edit(planningShiftDischarge);

            ServiceShifting serviceShifting = serviceShiftingFacadeLocal.findByNoPpkbAndContNo(planningContLoad.getPlanningVessel().getNoPpkb(), planningContLoad.getContNo());
            serviceShifting.setPosition(ServiceShifting.LOCATION_CY);

            serviceShiftingFacadeLocal.edit(serviceShifting);
        } else if (planningContLoad.getIsTranshipment().equals("TRUE")) {
            equipmentLift.setService(EquipmentContainerMovement.SERVICE_TRANSHIPMENT);

            ServiceContTranshipment serviceContTranshipment = serviceContTranshipmentFacadeLocal.findByNewPpkbAndContNo(planningContLoad.getPlanningVessel().getNoPpkb(), planningContLoad.getContNo());
            serviceContTranshipment.setMasterYard(planningContLoad.getMasterYard());
            serviceContTranshipment.setYRow(planningContLoad.getYRow());
            serviceContTranshipment.setYSlot(planningContLoad.getYSlot());
            serviceContTranshipment.setYTier(planningContLoad.getYTier());

            serviceContTranshipmentFacadeLocal.edit(serviceContTranshipment);
        } else {
            if (planningContLoad.getIsChangeVessel().equals("TRUE")) {
                CancelLoadingService changeVesselContainer = cancelLoadingServiceFacadeLocal.findByNewPpkbAndRelocationStatus(planningContLoad.getPlanningVessel().getNoPpkb(), planningContLoad.getContNo(), false);

                if (changeVesselContainer != null) {
                    equipmentLift.setService(EquipmentContainerMovement.SERVICE_RELOCATION);
                    changeVesselContainer.setPosisi(CancelLoadingService.AT_CY);
                    changeVesselContainer.setHasRelocated("TRUE");
                    cancelLoadingServiceFacadeLocal.edit(changeVesselContainer);
                } else {
                    equipmentLift.setService(EquipmentContainerMovement.SERVICE_RECEIVING);
                }
            } else {
                equipmentLift.setService(EquipmentContainerMovement.SERVICE_RECEIVING);

                ServiceReceiving serviceReceiving = serviceReceivingFacadeLocal.findByNoPpkbAndContNoNotCancelLoading(planningContLoad.getPlanningVessel().getNoPpkb(), planningContLoad.getContNo());
                serviceReceiving.setIsLifton("FALSE");
                serviceReceiving.setIsBehandle("FALSE");
                serviceReceiving.setIsInspection("FALSE");
                serviceReceiving.setIsCfs("FALSE");

                if (moveTo.equals(MOVEMENT_CY)) {
                    serviceReceiving.setMasterYard(planningContLoad.getMasterYard());
                    serviceReceiving.setYRow(planningContLoad.getYRow());
                    serviceReceiving.setYSlot(planningContLoad.getYSlot());
                    serviceReceiving.setYTier(planningContLoad.getYTier());
                } else if (moveTo.equals(MOVEMENT_BEHANDLE)) {
                    serviceReceiving.setIsBehandle("TRUE");

                    ServiceBehandle serviceBehandle = new ServiceBehandle(serviceReceiving);
                    serviceBehandle.setStartTime(equipmentLift.getEndTime());

                    serviceBehandleFacadeLocal.edit(serviceBehandle);
                } else if (moveTo.equals(MOVEMENT_INSPECTION)) {
                    serviceReceiving.setIsInspection("TRUE");

                    ServiceInspection serviceInspection = new ServiceInspection(serviceReceiving);
                    serviceInspection.setStartTime(equipmentLift.getEndTime());

                    serviceInspectionFacadeLocal.edit(serviceInspection);
                } else if (moveTo.equals(MOVEMENT_CFS)) {
                    serviceReceiving.setIsCfs("TRUE");

                    ServiceCfs lastRecord = serviceCfsFacadeLocal.findLastCfsByPpkbAndContNo(serviceReceiving.getNoPpkb(), serviceReceiving.getContNo());

                    if (lastRecord != null) {
                        lastRecord.setIsLast("FALSE");
                        serviceCfsFacadeLocal.edit(lastRecord);
                    }

                    ServiceCfs serviceCfs = new ServiceCfs(serviceReceiving);
                    serviceCfsFacadeLocal.edit(serviceCfs);
                }

                containerMovementHistory.setIsCfs(serviceReceiving.getIsCfs());
                containerMovementHistory.setIsBehandle(serviceReceiving.getIsBehandle());
                containerMovementHistory.setIsInspection(serviceReceiving.getIsInspection());

                serviceReceivingFacadeLocal.edit(serviceReceiving);
            }
        }

        equipmentLift.setContainerMovementHistory(containerMovementHistory);

        containerMovementHistoryFacadeLocal.edit(containerMovementHistory);
        equipmentContainerMovementFacadeLocal.create(equipmentLift);
        planningContLoadFacadeLocal.edit(planningContLoad);
        equipmentContainerMovementFacadeLocal.edit(equipmentHT);
    }
}
