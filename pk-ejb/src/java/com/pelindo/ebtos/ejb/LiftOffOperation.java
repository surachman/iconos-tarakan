/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb;

import com.pelindo.ebtos.ejb.facade.local.CancelLoadingServiceFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.EquipmentFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.MasterYardCoordinatFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.PlanningContLoadFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.PlanningShiftDischargeFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.PlanningVesselFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.ServiceContDischargeFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.ServiceContTranshipmentFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.ServiceShiftingFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.ServiceVesselFacadeLocal;
import com.pelindo.ebtos.ejb.local.YardOperationLocal;
import com.pelindo.ebtos.ejb.remote.LiftOffOperationRemote;
import com.pelindo.ebtos.exception.ContainerNotMovableException;
import com.pelindo.ebtos.exception.LocationNotAvailableException;
import com.pelindo.ebtos.exception.TimeSelectionNotValidException;
import com.pelindo.ebtos.model.YardLocation;
import com.pelindo.ebtos.model.db.CancelLoadingService;
import com.pelindo.ebtos.model.db.Equipment;
import com.pelindo.ebtos.model.db.PlanningContLoad;
import com.pelindo.ebtos.model.db.PlanningShiftDischarge;
import com.pelindo.ebtos.model.db.PlanningVessel;
import com.pelindo.ebtos.model.db.ServiceContDischarge;
import com.pelindo.ebtos.model.db.ServiceContTranshipment;
import com.pelindo.ebtos.model.db.ServiceShifting;
import com.pelindo.ebtos.model.db.ServiceVessel;
import com.pelindo.ebtos.util.GrossConverter;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author R. Seno Anggoro A
 */
@Stateless
public class LiftOffOperation implements LiftOffOperationRemote {
    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    @EJB
    private MasterYardCoordinatFacadeLocal masterYardCoordinatFacade;
    @EJB
    private YardOperationLocal yardOperation;
    @EJB
    private ServiceVesselFacadeLocal serviceVesselFacadeLocal;
    @EJB
    private ServiceContDischargeFacadeLocal serviceContDischargeFacadeLocal;
    @EJB
    private EquipmentFacadeLocal equipmentFacadeLocal;
    @EJB
    private PlanningContLoadFacadeLocal planningContLoadFacadeLocal;
    @EJB
    private PlanningVesselFacadeLocal planningVesselFacadeLocal;
    @EJB
    private CancelLoadingServiceFacadeLocal cancelLoadingServiceFacadeLocal;
    @EJB
    private ServiceShiftingFacadeLocal serviceShiftingFacadeLocal;
    @EJB
    private PlanningShiftDischargeFacadeLocal planningShiftDischargeFacadeLocal;
    @EJB
    private ServiceContTranshipmentFacadeLocal serviceContTranshipmentFacadeLocal;
    
    public void handleLiftOffConfirmDischargeContainer(ServiceContDischarge serviceContDischarge, Equipment tango) throws LocationNotAvailableException, TimeSelectionNotValidException {
        if (tango.getStartTime().after(tango.getEndTime()) || tango.getStartTime().equals(tango.getEndTime())) {
            throw new TimeSelectionNotValidException("End time must be after start time!");
        }

        ServiceVessel serviceVessel = serviceContDischarge.getServiceVessel();
        boolean isAtHeadTruck = serviceContDischarge.getPosition().equals(ServiceContDischarge.LOCATION_HT);

        if (isAtHeadTruck) {
//            YardLocation newLocation = new YardLocation(serviceContDischarge.getMasterYard().getBlock(), serviceContDischarge.getYSlot(), serviceContDischarge.getYRow(), serviceContDischarge.getYTier());
//              yardOperation.liftoff(serviceContDischarge.getServiceVessel().getNoPpkb(), serviceContDischarge.getContNo(), serviceContDischarge.getMasterContainerType().getContType().toString(), serviceContDischarge.getContSize(), serviceContDischarge.getContGross(), serviceContDischarge.getDischPort(), serviceContDischarge.getGrossClass(), newLocation);

            serviceVessel.setDischarge((short) (serviceVessel.getDischarge() + 1));
            serviceVesselFacadeLocal.edit(serviceVessel);

            serviceContDischarge.setPosition(ServiceContDischarge.LOCATION_CY);
        }

        serviceContDischarge.setServiceVessel(serviceVessel);

        tango.setPlanningVessel(serviceVessel.getPlanningVessel());
        tango.setContNo(serviceContDischarge.getContNo());
        tango.setMlo(serviceContDischarge.getMlo());
        tango.setEquipmentActCode("LIFTOFF");
        tango.setOperation("DISCHARGE");

        equipmentFacadeLocal.edit(tango);
        serviceContDischargeFacadeLocal.edit(serviceContDischarge);
        equipmentFacadeLocal.updateEndTimeByEquipmentConstraint(tango.getStartTime(), serviceContDischarge.getServiceVessel().getNoPpkb(), serviceContDischarge.getContNo(), "H/T", "DISCHARGE");

        if (isAtHeadTruck) {
            em.flush();
            masterYardCoordinatFacade.clearYardsByNoPpkbAndContNo(serviceContDischarge.getServiceVessel().getNoPpkb(), serviceContDischarge.getContNo(), "planning");
        }
    }

    public void handleLiftOffConfirmCancelLoadingContainer(CancelLoadingService cancelLoadingService, Equipment tango) throws LocationNotAvailableException, TimeSelectionNotValidException {
        if (tango.getStartTime().after(tango.getEndTime()) || tango.getStartTime().equals(tango.getEndTime())) {
            throw new TimeSelectionNotValidException("End time must be after start time!");
        }
        boolean isAtHeadTruck = cancelLoadingService.getPosisi().equals(CancelLoadingService.AT_HAULAGE_TRUCK);

        if (isAtHeadTruck) {
            String grossClass = GrossConverter.convert(cancelLoadingService.getContSize(), cancelLoadingService.getContGross());
            YardLocation newLocation = new YardLocation(cancelLoadingService.getMasterYard().getBlock(), cancelLoadingService.getySlot(), cancelLoadingService.getyRow(), cancelLoadingService.getyTier());

            if (cancelLoadingService.getPullOut().equals("TRUE")) {
                yardOperation.liftoff(cancelLoadingService.getPlanningVessel().getNoPpkb(), cancelLoadingService.getContNo(), cancelLoadingService.getMasterContainerType().getContType().toString(), cancelLoadingService.getContSize(), cancelLoadingService.getContGross(), cancelLoadingService.getDischargePort().getPortCode(), grossClass, newLocation);
            } else {
                yardOperation.liftoff(cancelLoadingService.getNewPlanningVessel().getNoPpkb(), cancelLoadingService.getContNo(), cancelLoadingService.getMasterContainerType().getContType().toString(), cancelLoadingService.getContSize(), cancelLoadingService.getContGross(), cancelLoadingService.getDischargePort().getPortCode(), grossClass, newLocation);
            }
            
            cancelLoadingService.setPosisi(CancelLoadingService.AT_CY);
        }

        tango.setPlanningVessel(cancelLoadingService.getPlanningVessel());
        tango.setContNo(cancelLoadingService.getContNo());
        tango.setMlo(cancelLoadingService.getMlo());
        tango.setEquipmentActCode("LIFTOFF");
        tango.setOperation("DISCHARGE");

        if (cancelLoadingService.getCategory() == 4 && isAtHeadTruck) {
            PlanningContLoad planningContLoad = planningContLoadFacadeLocal.findByNoPpkbContNoAndStatusCancelLoading(cancelLoadingService.getPlanningVessel().getNoPpkb(), cancelLoadingService.getContNo(), true);
            PlanningVessel planningVessel = cancelLoadingService.getNewPlanningVessel();
            
            PlanningContLoad loadingContainer = new PlanningContLoad();
            loadingContainer.setMasterCommodity(planningContLoad.getMasterCommodity());
            loadingContainer.setMasterYard(cancelLoadingService.getMasterYard());
            loadingContainer.setMasterContainerType(planningContLoad.getMasterContainerType());
            loadingContainer.setPlanningVessel(planningVessel);
            loadingContainer.setContNo(planningContLoad.getContNo());
            loadingContainer.setContSize(planningContLoad.getContSize());
            loadingContainer.setContStatus(planningContLoad.getContStatus());
            loadingContainer.setIsTranshipment(planningContLoad.getIsTranshipment());
            loadingContainer.setIsShifting("FALSE");
            loadingContainer.setIsChangeVessel("TRUE");
            loadingContainer.setDischPort(cancelLoadingService.getDischargePort().getPortCode());
            loadingContainer.setLoadPort(planningContLoad.getLoadPort());
            loadingContainer.setTwentyOneFeet(planningContLoad.getTwentyOneFeet());
            loadingContainer.setPosition(PlanningContLoad.LOCATION_CY);
            loadingContainer.setMlo(planningContLoad.getMlo());
            loadingContainer.setContGross(planningContLoad.getContGross());
            loadingContainer.setSealNo(planningContLoad.getSealNo());
            loadingContainer.setOverSize(planningContLoad.getOverSize());
            loadingContainer.setDgLabel(planningContLoad.getDgLabel());
            loadingContainer.setDg(planningContLoad.getDg());
            loadingContainer.setYSlot(cancelLoadingService.getySlot());
            loadingContainer.setYRow(cancelLoadingService.getyRow());
            loadingContainer.setYTier(cancelLoadingService.getyTier());
            loadingContainer.setCompleted("FALSE");
            loadingContainer.setUnknown("FALSE");
            loadingContainer.setBlNo(planningContLoad.getBlNo());
            loadingContainer.setPortOfDelivery(planningContLoad.getPortOfDelivery());

            cancelLoadingService.setHasRelocated("TRUE");
            planningContLoadFacadeLocal.edit(loadingContainer);
        }

        cancelLoadingServiceFacadeLocal.edit(cancelLoadingService);
        equipmentFacadeLocal.edit(tango);
        equipmentFacadeLocal.updateEndTimeByEquipmentConstraint(tango.getStartTime(), cancelLoadingService.getPlanningVessel().getNoPpkb(), cancelLoadingService.getContNo(), "H/T", "DISCHARGE");

        if (isAtHeadTruck && cancelLoadingService.getPullOut().equals("TRUE")) {
            em.flush();
            masterYardCoordinatFacade.clearYardsByNoPpkbAndContNo(cancelLoadingService.getPlanningVessel().getNoPpkb(), cancelLoadingService.getContNo(), "planning");
        }
    }

    public void handleLiftOffConfirmShiftingContainer(ServiceShifting serviceShifting, PlanningShiftDischarge planningShiftDischarge, Equipment tango) throws LocationNotAvailableException, TimeSelectionNotValidException {
        if (tango.getStartTime().after(tango.getEndTime()) || tango.getStartTime().equals(tango.getEndTime())) {
            throw new TimeSelectionNotValidException("End time must be after start time!");
        }

        ServiceVessel serviceVessel = serviceShifting.getServiceVessel();
        boolean isAtHeadTruck = serviceShifting.getPosition().equals(ServiceShifting.LOCATION_HT_SHIFTING);

        if (isAtHeadTruck) {
            YardLocation newLocation = new YardLocation(planningShiftDischarge.getMasterYard().getBlock(), planningShiftDischarge.getYSlot(), planningShiftDischarge.getYRow(), planningShiftDischarge.getYTier());
            yardOperation.liftoff(serviceVessel.getNoPpkb(), serviceShifting.getContNo(), serviceShifting.getMasterContainerType().getContType().toString(), serviceShifting.getContSize(), serviceShifting.getContGross(), serviceShifting.getDischPort(), planningShiftDischarge.getGrossClass(), newLocation);

            serviceVessel.setDischarge((short) (serviceVessel.getDischarge() + 1));
            serviceVesselFacadeLocal.edit(serviceVessel);

            serviceShifting.setIsLanded("TRUE");
            serviceShifting.setPosition(ServiceShifting.LOCATION_CY);
        }

        serviceShifting.setPlacementDate(tango.getEndTime());

        if (serviceVessel != null) {
            PlanningContLoad planningContLoad;
            PlanningVessel planningVessel = planningVesselFacadeLocal.find(serviceVessel.getNoPpkb());

            if (serviceShifting.getPlanningContLoad() == null) {
                planningContLoad = new PlanningContLoad();
            } else {
                planningContLoad = serviceShifting.getPlanningContLoad();
            }

            planningContLoad.setMasterCommodity(serviceShifting.getMasterCommodity());
            planningContLoad.setMasterYard(serviceShifting.getPlanningShiftDischarge().getMasterYard());
            planningContLoad.setMasterContainerType(serviceShifting.getMasterContainerType());
            planningContLoad.setPlanningVessel(planningVessel);
            planningContLoad.setContNo(serviceShifting.getContNo());
            planningContLoad.setContSize(serviceShifting.getContSize());
            planningContLoad.setContStatus(serviceShifting.getContStatus());
            planningContLoad.setIsTranshipment("FALSE");
            planningContLoad.setIsShifting("TRUE");
            planningContLoad.setPosition("03");
            planningContLoad.setMlo(serviceShifting.getMlo());
            planningContLoad.setDischPort(serviceShifting.getDischPort());
            planningContLoad.setLoadPort(serviceShifting.getLoadPort());
            planningContLoad.setTwentyOneFeet(serviceShifting.getTwentyOneFeet());
            planningContLoad.setContGross(serviceShifting.getContGross());
            planningContLoad.setSealNo(serviceShifting.getSealNo());
            planningContLoad.setOverSize(serviceShifting.getOverSize());
            planningContLoad.setDgLabel(serviceShifting.getDgLabel());
            planningContLoad.setDg(serviceShifting.getDg());
            planningContLoad.setIsSling(serviceShifting.getIsSling());
            planningContLoad.setIsExportImport(serviceShifting.getIsExportImport());
            planningContLoad.setYSlot(planningShiftDischarge.getYSlot());
            planningContLoad.setYRow(planningShiftDischarge.getYRow());
            planningContLoad.setYTier(planningShiftDischarge.getYTier());
            planningContLoad.setCompleted("FALSE");
            planningContLoad.setUnknown("FALSE");
            planningContLoad.setBlNo(serviceShifting.getBlNo());
            planningContLoadFacadeLocal.edit(planningContLoad);
        }

        tango.setPlanningVessel(serviceVessel.getPlanningVessel());
        tango.setContNo(serviceShifting.getContNo());
        tango.setEquipmentActCode("LIFTOFF");
        tango.setOperation("SHIFTING-CY");
        
        serviceShifting.setServiceVessel(serviceVessel);

        equipmentFacadeLocal.edit(tango);
        serviceShiftingFacadeLocal.edit(serviceShifting);
        planningShiftDischargeFacadeLocal.edit(planningShiftDischarge);
        equipmentFacadeLocal.updateEndTimeByEquipmentConstraint(tango.getStartTime(), serviceShifting.getServiceVessel().getNoPpkb(), serviceShifting.getContNo(), "H/T", "SHIFTING-CY");

        if (isAtHeadTruck) {
            em.flush();
            masterYardCoordinatFacade.clearYardsByNoPpkbAndContNo(serviceVessel.getNoPpkb(), serviceShifting.getContNo(), "planning");
        }
    }

    public void handleLiftOffConfirmTranshipmentContainer(ServiceContTranshipment serviceContTranshipment, Equipment tango) throws LocationNotAvailableException, TimeSelectionNotValidException {
        if (tango.getStartTime().after(tango.getEndTime()) || tango.getStartTime().equals(tango.getEndTime())) {
            throw new TimeSelectionNotValidException("End time must be after start time!");
        }
        ServiceVessel serviceVessel = serviceContTranshipment.getServiceVessel();
        boolean isAtHeadTruck = serviceContTranshipment.getPosition().equals("02");

        if (isAtHeadTruck) {
            String grossClass = GrossConverter.convert(serviceContTranshipment.getContSize(), serviceContTranshipment.getContGross());
            YardLocation newLocation = new YardLocation(serviceContTranshipment.getMasterYard().getBlock(), serviceContTranshipment.getYSlot(), serviceContTranshipment.getYRow(), serviceContTranshipment.getYTier());
            yardOperation.liftoff(serviceContTranshipment.getNewPpkb(), serviceContTranshipment.getContNo(), serviceContTranshipment.getMasterContainerType().getContType().toString(), serviceContTranshipment.getContSize(), serviceContTranshipment.getContGross(), serviceContTranshipment.getDischPort(), grossClass, newLocation);

            serviceContTranshipment.setPosition("03");
            serviceContTranshipment.setStartPlacementDate(tango.getEndTime());
        }

        if (serviceContTranshipment.getNewPpkb() != null) {
            PlanningContLoad planningContLoad = planningContLoadFacadeLocal.findByNoPpkbAndContNoExcludeCancelLoading(serviceContTranshipment.getNewPpkb(), serviceContTranshipment.getContNo());
            PlanningVessel planningVessel = planningVesselFacadeLocal.find(serviceContTranshipment.getNewPpkb());

            if (planningContLoad == null) {
                planningContLoad = new PlanningContLoad();
            }

            planningContLoad.setMasterCommodity(serviceContTranshipment.getMasterCommodity());
            planningContLoad.setMasterYard(serviceContTranshipment.getMasterYard());
            planningContLoad.setMasterContainerType(serviceContTranshipment.getMasterContainerType());
            planningContLoad.setPlanningVessel(planningVessel);
            planningContLoad.setContNo(serviceContTranshipment.getContNo());
            planningContLoad.setContSize(serviceContTranshipment.getContSize());
            planningContLoad.setContStatus(serviceContTranshipment.getContStatus());
            planningContLoad.setIsTranshipment("TRUE");
            planningContLoad.setIsShifting("FALSE");
            planningContLoad.setDischPort(serviceContTranshipment.getDischPort());
            planningContLoad.setLoadPort(serviceContTranshipment.getLoadPort());
            planningContLoad.setPosition("03");
            planningContLoad.setMlo(serviceContTranshipment.getMlo());
            planningContLoad.setContGross(serviceContTranshipment.getContGross());
            planningContLoad.setTwentyOneFeet(serviceContTranshipment.getTwentyOneFeet());
            planningContLoad.setSealNo(serviceContTranshipment.getSealNo());
            planningContLoad.setOverSize(serviceContTranshipment.getOverSize());
            planningContLoad.setDgLabel(serviceContTranshipment.getDgLabel());
            planningContLoad.setDg(serviceContTranshipment.getDangerous());
            planningContLoad.setIsSling(serviceContTranshipment.getIsSling());
            planningContLoad.setIsExportImport(serviceContTranshipment.getIsExportImport());
            planningContLoad.setYSlot(serviceContTranshipment.getYSlot());
            planningContLoad.setYRow(serviceContTranshipment.getYRow());
            planningContLoad.setYTier(serviceContTranshipment.getYTier());
            planningContLoad.setCompleted("FALSE");
            planningContLoad.setUnknown("FALSE");
            planningContLoad.setBlNo(serviceContTranshipment.getBlNo());
            planningContLoad.setPortOfDelivery(serviceContTranshipment.getPortOfDelivery());
            planningContLoadFacadeLocal.edit(planningContLoad);
        }

        tango.setPlanningVessel(serviceVessel.getPlanningVessel());
        tango.setContNo(serviceContTranshipment.getContNo());
        tango.setMlo(serviceContTranshipment.getMlo());
        tango.setEquipmentActCode("LIFTOFF");
        tango.setOperation("TRANSDISCHARGE");

        serviceContTranshipmentFacadeLocal.edit(serviceContTranshipment);
        equipmentFacadeLocal.edit(tango);
        equipmentFacadeLocal.updateEndTimeByEquipmentConstraint(tango.getStartTime(), serviceVessel.getNoPpkb(), serviceContTranshipment.getContNo(), "H/T", "TRANSDISCHARGE");

        if (isAtHeadTruck) {
            em.flush();
            masterYardCoordinatFacade.clearYardsByNoPpkbAndContNo(serviceContTranshipment.getNewPpkb(), serviceContTranshipment.getContNo(), "planning");
        }
    }

    public void handleCancelLiftOffConfirmShiftingContainer(ServiceShifting serviceShifting, PlanningShiftDischarge planningShiftDischarge, Equipment tango) throws ContainerNotMovableException {
        YardLocation oldLocation = new YardLocation(planningShiftDischarge.getMasterYard().getBlock(), planningShiftDischarge.getYSlot(), planningShiftDischarge.getYRow(), planningShiftDischarge.getYTier());
        yardOperation.lifton(serviceShifting.getServiceVessel().getNoPpkb(), serviceShifting.getContNo(), serviceShifting.getMasterContainerType().getContType().toString(), serviceShifting.getContSize(), serviceShifting.getDischPort(), planningShiftDischarge.getGrossClass(), oldLocation);

        ServiceVessel serviceVessel = serviceShifting.getServiceVessel();
        serviceVessel.setDischarge((short) (serviceVessel.getDischarge() - 1));

        serviceShifting.setIsLanded("FALSE");
        serviceShifting.setPosition(ServiceShifting.LOCATION_HT_SHIFTING);
        serviceShifting.setPlacementDate(null);

        PlanningContLoad planningContLoad = serviceShifting.getPlanningContLoad();

        Equipment haulageTrucking = equipmentFacadeLocal.findByEquipmentActCodeAndOperation(serviceShifting.getServiceVessel().getNoPpkb(), serviceShifting.getContNo(), "H/T", "SHIFTING-CY");
        haulageTrucking.setEndTime(null);

        serviceVesselFacadeLocal.edit(serviceVessel);

        serviceShifting.setServiceVessel(serviceVessel);

        serviceShiftingFacadeLocal.edit(serviceShifting);
        planningShiftDischargeFacadeLocal.edit(planningShiftDischarge);
        equipmentFacadeLocal.remove(tango);
        equipmentFacadeLocal.edit(haulageTrucking);
        planningContLoadFacadeLocal.remove(planningContLoad);
    }

    public void handleCancelLiftOffConfirmCancelLoadingContainer(CancelLoadingService cancelLoadingService, Equipment tango) throws ContainerNotMovableException {
        String grossClass = GrossConverter.convert(cancelLoadingService.getContSize(), cancelLoadingService.getContGross());

        YardLocation oldLocation = new YardLocation(cancelLoadingService.getMasterYard().getBlock(), cancelLoadingService.getySlot(), cancelLoadingService.getyRow(), cancelLoadingService.getyTier());

        if (cancelLoadingService.getPullOut().equals("TRUE")) {
            yardOperation.lifton(cancelLoadingService.getPlanningVessel().getNoPpkb(), cancelLoadingService.getContNo(), cancelLoadingService.getMasterContainerType().getContType().toString(), cancelLoadingService.getContSize(), cancelLoadingService.getDischargePort().getPortCode(), grossClass, oldLocation);
        } else {
            yardOperation.lifton(cancelLoadingService.getNewPlanningVessel().getNoPpkb(), cancelLoadingService.getContNo(), cancelLoadingService.getContSize(), oldLocation);
        }
        
        cancelLoadingService.setHasRelocated("FALSE");
        cancelLoadingService.setPosisi(CancelLoadingService.AT_HAULAGE_TRUCK);

        cancelLoadingServiceFacadeLocal.edit(cancelLoadingService);
        equipmentFacadeLocal.remove(tango);
        equipmentFacadeLocal.updateEndTimeByEquipmentConstraint(null, cancelLoadingService.getPlanningVessel().getNoPpkb(), cancelLoadingService.getContNo(), "H/T", "DISCHARGE");

        if (cancelLoadingService.getCategory() == 5) {
            planningContLoadFacadeLocal.deleteByNoPpkbAndContNo(cancelLoadingService.getNewPlanningVessel().getNoPpkb(), cancelLoadingService.getContNo());
        }
    }

    public void handleCancelLiftOffConfirmDischargeContainer(ServiceContDischarge serviceContDischarge, Equipment tango) throws ContainerNotMovableException {
        try {
            YardLocation oldLocation = new YardLocation(serviceContDischarge.getMasterYard().getBlock(), serviceContDischarge.getYSlot(), serviceContDischarge.getYRow(), serviceContDischarge.getYTier());
            yardOperation.lifton(serviceContDischarge.getServiceVessel().getNoPpkb(), serviceContDischarge.getContNo(), serviceContDischarge.getMasterContainerType().getContType().toString(), serviceContDischarge.getContSize(), serviceContDischarge.getDischPort(), serviceContDischarge.getGrossClass(), oldLocation);
        } catch (Exception e) {}
        ServiceVessel serviceVessel = serviceContDischarge.getServiceVessel();
        serviceVessel.setDischarge((short)(serviceVessel.getDischarge() - 1));

        serviceContDischarge.setPosition("02");

        serviceVesselFacadeLocal.edit(serviceVessel);

        serviceContDischarge.setServiceVessel(serviceVessel);

        equipmentFacadeLocal.updateEndTimeByEquipmentConstraint(null, serviceContDischarge.getServiceVessel().getNoPpkb(), serviceContDischarge.getContNo(), "H/T", "DISCHARGE");
        serviceContDischargeFacadeLocal.edit(serviceContDischarge);
        equipmentFacadeLocal.remove(tango);
    }

    public void handleCancelLiftOffConfirmTranshipmentContainer(ServiceContTranshipment serviceContTranshipment, Equipment tango) throws ContainerNotMovableException {
        String grossClass = GrossConverter.convert(serviceContTranshipment.getContSize(), serviceContTranshipment.getContGross());
        YardLocation oldLocation = new YardLocation(serviceContTranshipment.getMasterYard().getBlock(), serviceContTranshipment.getYSlot(), serviceContTranshipment.getYRow(), serviceContTranshipment.getYTier());
        yardOperation.lifton(serviceContTranshipment.getServiceVessel().getNoPpkb(), serviceContTranshipment.getContNo(), serviceContTranshipment.getMasterContainerType().getContType().toString(), serviceContTranshipment.getContSize(), serviceContTranshipment.getDischPort(), grossClass, oldLocation);

        ServiceVessel serviceVessel = serviceContTranshipment.getServiceVessel();
        serviceVessel.setDischarge((short) (serviceVessel.getDischarge() - 1));

        equipmentFacadeLocal.updateEndTimeByEquipmentConstraint(null, serviceContTranshipment.getServiceVessel().getNoPpkb(), serviceContTranshipment.getContNo(), "H/T", "TRANSDISCHARGE");
        planningContLoadFacadeLocal.deleteByNoPpkbAndContNo(serviceContTranshipment.getNewPpkb(), serviceContTranshipment.getContNo());
        serviceVesselFacadeLocal.edit(serviceVessel);
        equipmentFacadeLocal.remove(tango);

        serviceContTranshipment.setPosition("02");
        serviceContTranshipment.setServiceVessel(serviceVessel);

        serviceContTranshipmentFacadeLocal.edit(serviceContTranshipment);
    }
}
