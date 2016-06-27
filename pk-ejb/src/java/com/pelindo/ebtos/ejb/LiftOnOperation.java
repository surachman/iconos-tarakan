/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb;

import com.pelindo.ebtos.ejb.facade.local.CancelLoadingServiceFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.EquipmentFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.PlanningContLoadFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.ReceivingServiceFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.ServiceContLoadFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.ServiceReceivingFacadeLocal;
import com.pelindo.ebtos.ejb.local.YardOperationLocal;
import com.pelindo.ebtos.ejb.facade.local.PlanningShiftDischargeFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.ServiceShiftingFacadeLocal;
import com.pelindo.ebtos.ejb.remote.LiftOnOperationRemote;
import com.pelindo.ebtos.exception.ContainerNotMovableException;
import com.pelindo.ebtos.exception.LocationNotAvailableException;
import com.pelindo.ebtos.exception.TimeSelectionNotValidException;
import com.pelindo.ebtos.model.YardLocation;
import com.pelindo.ebtos.model.db.CancelLoadingService;
import com.pelindo.ebtos.model.db.Equipment;
import com.pelindo.ebtos.model.db.PlanningContLoad;
import com.pelindo.ebtos.model.db.PlanningShiftDischarge;
import com.pelindo.ebtos.model.db.ReceivingService;
import com.pelindo.ebtos.model.db.ServiceContLoad;
import com.pelindo.ebtos.model.db.ServiceReceiving;
import com.pelindo.ebtos.model.db.ServiceShifting;
import com.pelindo.ebtos.model.db.ServiceVessel;
import com.pelindo.ebtos.util.GrossConverter;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author R. Seno Anggoro A
 */
@Stateless
public class LiftOnOperation implements LiftOnOperationRemote {
    @EJB
    private CancelLoadingServiceFacadeLocal cancelLoadingServiceFacadeLocal;
    @EJB
    private ReceivingServiceFacadeLocal receivingServiceFacadeLocal;
    @EJB
    private ServiceReceivingFacadeLocal serviceReceivingFacadeLocal;
    @EJB
    private PlanningContLoadFacadeLocal planningContLoadFacadeLocal;
    @EJB
    private EquipmentFacadeLocal equipmentFacadeLocal;
    @EJB
    private ServiceContLoadFacadeLocal serviceContLoadFacadeLocal;
    @EJB
    private YardOperationLocal yardOperationLocal;
    @EJB
    private ServiceShiftingFacadeLocal serviceShiftingFacadeLocal;
    @EJB
    private PlanningShiftDischargeFacadeLocal planningShiftDischargeFacadeLocal;
    
    public void handleLiftOnConfirmLoadContainer(ServiceVessel serviceVessel, PlanningContLoad planningContLoad, Equipment tango, Equipment haulageTruck) throws TimeSelectionNotValidException, ContainerNotMovableException {
        if (tango.getStartTime().after(tango.getEndTime()) || tango.getStartTime().equals(tango.getEndTime())) {
            throw new TimeSelectionNotValidException("End time must be after start time!");
        }

        ServiceContLoad serviceContLoad = serviceContLoadFacadeLocal.findByNoPpkbContNoAndStatusCancelLoading(serviceVessel.getNoPpkb(), planningContLoad.getContNo(), "FALSE");

        if (serviceContLoad == null) {
            serviceContLoad = new ServiceContLoad();
        }

        serviceContLoad.setContNo(planningContLoad.getContNo());
        serviceContLoad.setMlo(planningContLoad.getMlo());
        serviceContLoad.setContGross(planningContLoad.getContGross());
        serviceContLoad.setContSize(planningContLoad.getContSize());
        serviceContLoad.setContStatus(planningContLoad.getContStatus());
        serviceContLoad.setDangerous(planningContLoad.getDg());
        serviceContLoad.setIsExportImport(planningContLoad.getIsExportImport());
        serviceContLoad.setIsSeling(planningContLoad.getIsSling());
        serviceContLoad.setDgLabel(planningContLoad.getDgLabel());
        serviceContLoad.setMasterCommodity(planningContLoad.getMasterCommodity());
        serviceContLoad.setMasterContainerType(planningContLoad.getMasterContainerType());
        serviceContLoad.setMasterYard(planningContLoad.getMasterYard());
        serviceContLoad.setServiceVessel(serviceVessel);
        serviceContLoad.setOverSize(planningContLoad.getOverSize());
        serviceContLoad.setSealNo(planningContLoad.getSealNo());
        serviceContLoad.setVBay(planningContLoad.getVBay());
        serviceContLoad.setVRow(planningContLoad.getVRow());
        serviceContLoad.setVTier(planningContLoad.getVTier());
        serviceContLoad.setYSlot(planningContLoad.getYSlot());
        serviceContLoad.setYRow(planningContLoad.getYRow());
        serviceContLoad.setYTier(planningContLoad.getYTier());
        serviceContLoad.setTransactionDate(tango.getStartTime());
        serviceContLoad.setTwentyOneFeet(planningContLoad.getTwentyOneFeet());
        serviceContLoad.setServiceVessel(serviceVessel);
        serviceContLoad.setIsTranshipment(planningContLoad.getIsTranshipment());
        serviceContLoad.setIsChangeVessel(planningContLoad.getIsChangeVessel());
        serviceContLoad.setBlNo(planningContLoad.getBlNo());
        serviceContLoad.setStatusCancelLoading("FALSE");
        serviceContLoad.setPortOfDelivery(planningContLoad.getPortOfDelivery());

        tango.setContNo(serviceContLoad.getContNo());
        tango.setEquipmentActCode("LIFTON");
        tango.setPlanningVessel(serviceVessel.getPlanningVessel());

        haulageTruck.setContNo(serviceContLoad.getContNo());
        haulageTruck.setEquipmentActCode("H/T");
        haulageTruck.setStartTime(tango.getEndTime());
        haulageTruck.setPlanningVessel(serviceVessel.getPlanningVessel());

        if (planningContLoad.getIsTranshipment().equals("TRUE")) {
            tango.setOperation("TRANSHIPMENT");
            haulageTruck.setOperation("TRANSHIPMENT");
        } else {
            haulageTruck.setOperation("LOAD");
            tango.setOperation("LOAD");
        }

        planningContLoad.setPlanningVessel(planningContLoad.getPlanningVessel());

        if (planningContLoad.getPosition().equals(PlanningContLoad.LOCATION_CY)) {
//            YardLocation location = new YardLocation(planningContLoad.getMasterYard().getBlock(), planningContLoad.getYSlot(), planningContLoad.getYRow(), planningContLoad.getYTier());
//            yardOperationLocal.forceLifton(serviceVessel.getNoPpkb(), planningContLoad.getContNo(), planningContLoad.getContSize(), location);

            serviceContLoad.setPosition("02");
            planningContLoad.setPosition(PlanningContLoad.LOCATION_HT);

            if (planningContLoad.getIsChangeVessel().equals("TRUE")) {
                CancelLoadingService cancelLoadingService = cancelLoadingServiceFacadeLocal.findByNewPpkbAndContNo(serviceVessel.getNoPpkb(), serviceContLoad.getContNo());
                cancelLoadingService.setPosisi(CancelLoadingService.AT_HAULAGE_TRUCK);

                cancelLoadingServiceFacadeLocal.edit(cancelLoadingService);
            } else if (planningContLoad.getIsTranshipment().equals("TRUE")) {

            } else {
//                ReceivingService receivingService = receivingServiceFacadeLocal.findByNoPpkbAndContNoNotCancelLoading(serviceVessel.getNoPpkb(), serviceContLoad.getContNo());
//                receivingService.setEndStorageDate(tango.getStartTime());
//                receivingServiceFacadeLocal.edit(receivingService);
//
//                ServiceReceiving serviceReceiving = serviceReceivingFacadeLocal.findByNoPpkbAndContNoNotCancelLoading(serviceVessel.getNoPpkb(), serviceContLoad.getContNo());
//                serviceReceiving.setIsLifton("TRUE");
//                serviceReceivingFacadeLocal.edit(serviceReceiving);
            }
        }

        if (tango.getMasterOperator().getOptCode()==null)
            tango.setMasterOperator(null);
        if (haulageTruck.getMasterOperator().getOptCode()==null)
            haulageTruck.setMasterOperator(null);
        planningContLoadFacadeLocal.edit(planningContLoad);
        equipmentFacadeLocal.edit(haulageTruck);
        equipmentFacadeLocal.edit(tango);
        serviceContLoadFacadeLocal.edit(serviceContLoad);
    }

    public void handleLiftOnConfirmShiftingContainer(PlanningShiftDischarge planningShiftDischarge, ServiceShifting serviceShifting, Equipment tango, Equipment haulageTruck) throws TimeSelectionNotValidException, ContainerNotMovableException {
        if (tango.getStartTime().after(tango.getEndTime()) || tango.getStartTime().equals(tango.getEndTime())) {
            throw new TimeSelectionNotValidException("End time must be after start time!");
        }

        tango.setContNo(planningShiftDischarge.getContNo());
        tango.setMlo(planningShiftDischarge.getMlo());
        tango.setEquipmentActCode("LIFTON");
        tango.setOperation("LD-SHIFTING-CY");
        tango.setPlanningVessel(planningShiftDischarge.getPlanningVessel());

        haulageTruck.setContNo(planningShiftDischarge.getContNo());
        haulageTruck.setMlo(planningShiftDischarge.getMlo());
        haulageTruck.setEquipmentActCode("H/T");
        haulageTruck.setOperation("LD-SHIFTING-CY");
        haulageTruck.setStartTime(tango.getEndTime());
        haulageTruck.setPlanningVessel(planningShiftDischarge.getPlanningVessel());

        if (serviceShifting.getPosition().equals(ServiceShifting.LOCATION_CY)) {
            PlanningContLoad planningContLoad = planningContLoadFacadeLocal.findByNoPpkbAndContNoExcludeCancelLoading(planningShiftDischarge.getPlanningVessel().getNoPpkb(), planningShiftDischarge.getContNo());
            YardLocation location = new YardLocation(planningShiftDischarge.getMasterYard().getBlock(), planningShiftDischarge.getYSlot(), planningShiftDischarge.getYRow(), planningShiftDischarge.getYTier());
            yardOperationLocal.forceLifton(planningShiftDischarge.getPlanningVessel().getNoPpkb(), planningShiftDischarge.getContNo(), planningShiftDischarge.getContSize(), location);

            planningContLoad.setPosition(PlanningContLoad.LOCATION_HT);
            serviceShifting.setPosition(ServiceShifting.LOCATION_HT_RESHIPPING);
            planningContLoadFacadeLocal.edit(planningContLoad);
        }

        serviceShiftingFacadeLocal.edit(serviceShifting);
        equipmentFacadeLocal.edit(tango);
        equipmentFacadeLocal.edit(haulageTruck);
        planningShiftDischargeFacadeLocal.edit(planningShiftDischarge);
    }

    public void handleCancelLiftOnConfirmLoadContainer(PlanningContLoad planningContLoad, Equipment tango, Equipment haulageTruck) throws LocationNotAvailableException {
        ServiceContLoad serviceContLoad = serviceContLoadFacadeLocal.findByNoPpkbContNoAndStatusCancelLoading(planningContLoad.getPlanningVessel().getNoPpkb(), planningContLoad.getContNo(), "FALSE");

//        String grossClass = GrossConverter.convert(serviceContLoad.getContSize(), serviceContLoad.getContGross());
//        YardLocation newLocation = new YardLocation(planningContLoad.getMasterYard().getBlock(), planningContLoad.getYSlot(), planningContLoad.getYRow(), planningContLoad.getYTier());
//        yardOperationLocal.liftoff(planningContLoad.getPlanningVessel().getNoPpkb(), planningContLoad.getContNo(), planningContLoad.getMasterContainerType().getContType().toString(), planningContLoad.getContSize(), planningContLoad.getContGross(), planningContLoad.getDischPort(), grossClass, newLocation);

        if (planningContLoad.getIsChangeVessel().equals("TRUE")) {
            CancelLoadingService cancelLoadingService = cancelLoadingServiceFacadeLocal.findByNewPpkbAndContNo(planningContLoad.getPlanningVessel().getNoPpkb(), serviceContLoad.getContNo());
            cancelLoadingService.setPosisi(CancelLoadingService.AT_CY);
            cancelLoadingServiceFacadeLocal.edit(cancelLoadingService);
        } else if (planningContLoad.getIsTranshipment().equals("TRUE")) {

        } else {
            ServiceReceiving serviceReceiving = serviceReceivingFacadeLocal.findByNoPpkbAndContNoNotCancelLoading(planningContLoad.getPlanningVessel().getNoPpkb(), serviceContLoad.getContNo());
            if (serviceReceiving!=null) {
                serviceReceiving.setIsLifton("FALSE");

                ReceivingService receivingService = receivingServiceFacadeLocal.findByNoPpkbAndContNoNotCancelLoading(planningContLoad.getPlanningVessel().getNoPpkb(), serviceContLoad.getContNo());
                receivingService.setEndStorageDate(null);

                receivingServiceFacadeLocal.edit(receivingService);
                serviceReceivingFacadeLocal.edit(serviceReceiving);
            }
        }

        planningContLoad.setPosition(PlanningContLoad.LOCATION_CY);

        serviceContLoadFacadeLocal.remove(serviceContLoad);
        if (tango.getId()!=null) {
            if (tango.getMasterOperator()!=null && tango.getMasterOperator().getOptCode()==null)
                tango.setMasterOperator(null);
            equipmentFacadeLocal.remove(tango);
        }
        if (haulageTruck.getId()!=null) {
            if (haulageTruck.getMasterOperator()!=null && haulageTruck.getMasterOperator().getOptCode()==null)
                haulageTruck.setMasterOperator(null);
            equipmentFacadeLocal.remove(haulageTruck);
        }
        planningContLoadFacadeLocal.edit(planningContLoad);
    }

    public void handleCancelLiftOnConfirmShiftingContainer(PlanningShiftDischarge planningShiftDischarge, ServiceShifting serviceShifting, Equipment tango, Equipment haulageTruck) throws LocationNotAvailableException {
        String grossClass = GrossConverter.convert(planningShiftDischarge.getContSize(), planningShiftDischarge.getContGross());
        YardLocation newLocation = new YardLocation(planningShiftDischarge.getMasterYard().getBlock(), planningShiftDischarge.getYSlot(), planningShiftDischarge.getYRow(), planningShiftDischarge.getYTier());
        yardOperationLocal.liftoff(planningShiftDischarge.getPlanningVessel().getNoPpkb(), planningShiftDischarge.getContNo(), planningShiftDischarge.getMasterContainerType().getContType().toString(), planningShiftDischarge.getContSize(), planningShiftDischarge.getContGross(), planningShiftDischarge.getDischPort(), grossClass, newLocation);

        PlanningContLoad planningContLoad = planningContLoadFacadeLocal.findByNoPpkbAndContNoExcludeCancelLoading(planningShiftDischarge.getPlanningVessel().getNoPpkb(), planningShiftDischarge.getContNo());

        serviceShifting.setPosition(ServiceShifting.LOCATION_CY);
        planningContLoad.setPosition(PlanningContLoad.LOCATION_CY);

        serviceShiftingFacadeLocal.edit(serviceShifting);
        planningContLoadFacadeLocal.edit(planningContLoad);
        equipmentFacadeLocal.remove(tango);
        equipmentFacadeLocal.remove(haulageTruck);
    }
}
