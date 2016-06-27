/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb;

import com.pelindo.ebtos.ejb.facade.local.CancelLoadingServiceFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.EquipmentFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.MasterYardCoordinatFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.ServiceContDischargeFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.ServiceDeliveryFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.ServiceGateDeliveryFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.ServiceShiftingFacadeLocal;
import com.pelindo.ebtos.ejb.remote.DischargeConfirmOperationRemote;
import com.pelindo.ebtos.exception.TimeSelectionNotValidException;
import com.pelindo.ebtos.model.db.CancelLoadingService;
import com.pelindo.ebtos.model.db.Equipment;
import com.pelindo.ebtos.model.db.ServiceContDischarge;
import com.pelindo.ebtos.model.db.ServiceDelivery;
import com.pelindo.ebtos.model.db.ServiceGateDelivery;
import com.pelindo.ebtos.model.db.ServiceShifting;
import com.pelindo.ebtos.model.db.ServiceVessel;
import com.pelindo.ebtos.model.db.master.MasterYardCoordinat;
import com.pelindo.ebtos.util.ServiceUtil;
import java.util.Arrays;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author R. Seno Anggoro A
 */
@Stateless
public class DischargeConfirmOperation implements DischargeConfirmOperationRemote {
    @EJB
    private ServiceContDischargeFacadeLocal serviceContDischargeFacadeLocal;
    @EJB
    private EquipmentFacadeLocal equipmentFacadeLocal;
    @EJB
    private MasterYardCoordinatFacadeLocal masterYardCoordinatFacadeLocal;
    @EJB
    private CancelLoadingServiceFacadeLocal cancelLoadingServiceFacadeLocal;
    @EJB
    private ServiceShiftingFacadeLocal serviceShiftingFacadeLocal;
    @EJB
    private ServiceDeliveryFacadeLocal serviceDeliveryFacadeLocal;
    @EJB
    private ServiceGateDeliveryFacadeLocal serviceGateDeliveryFacadeLocal;
    
    public void handleDischargeConfirmContainer(ServiceContDischarge serviceContDischarge, Equipment crane, Equipment ht) throws TimeSelectionNotValidException {
        if (crane.getStartTime().after(crane.getEndTime()) || crane.getStartTime().equals(crane.getEndTime())) {
            throw new TimeSelectionNotValidException("End time must be after start time!");
        }
        
        if(crane.getMasterEquipment() != null){
            serviceContDischarge.setEquipmentGroup(crane.getMasterEquipment().getEquipmentGroup());
        }

        boolean isDangerous = serviceContDischarge.getMasterCommodity().getMasterDangerousClass() != null && Arrays.asList(new String[] {"1", "7"}).contains(serviceContDischarge.getMasterCommodity().getMasterDangerousClass().getDangerousClass());
            
        ServiceVessel serviceVessel = serviceContDischarge.getServiceVessel();

        if (serviceContDischarge.getPosition().equals(ServiceContDischarge.LOCATION_VESSEL) && serviceContDischarge.getIsDelivery().equals("FALSE")) {
            if (isDangerous) {
                ServiceDelivery serviceDelivery = new ServiceDelivery();
                serviceDelivery.setNoPpkb(serviceVessel.getPlanningVessel().getNoPpkb());
                serviceDelivery.setMasterCommodity(serviceContDischarge.getMasterCommodity());
                serviceDelivery.setMasterContainerType(serviceContDischarge.getMasterContainerType());
                serviceDelivery.setMasterYard(serviceContDischarge.getMasterYard());
                serviceDelivery.setContNo(serviceContDischarge.getContNo());
                serviceDelivery.setMlo(serviceContDischarge.getMlo());
                serviceDelivery.setContSize(serviceContDischarge.getContSize());
                serviceDelivery.setContStatus(serviceContDischarge.getContStatus());
                serviceDelivery.setContGross(serviceContDischarge.getContGross());
                serviceDelivery.setTwentyOneFeet(serviceContDischarge.getTwentyOneFeet());
                serviceDelivery.setSealNo(serviceContDischarge.getSealNo());
                serviceDelivery.setOverSize(serviceContDischarge.getOverSize());
                serviceDelivery.setDangerous(serviceContDischarge.getDangerous());
                serviceDelivery.setDgLabel(serviceContDischarge.getDgLabel());
                serviceDelivery.setYSlot(serviceContDischarge.getYSlot());
                serviceDelivery.setYRow(serviceContDischarge.getYRow());
                serviceDelivery.setYTier(serviceContDischarge.getYTier());
                serviceDelivery.setTransactionDate(crane.getStartTime());

                ServiceGateDelivery serviceGateDelivery = serviceGateDeliveryFacadeLocal.findByContPpkb(serviceContDischarge.getContNo(), serviceVessel.getPlanningVessel().getNoPpkb());
                serviceGateDelivery.setStatus("Y");

//                serviceContDischarge.setIsDelivery(Boolean.TRUE);

                serviceContDischarge.setIsDelivery("TRUE");
                
                serviceGateDeliveryFacadeLocal.edit(serviceGateDelivery);
                serviceDeliveryFacadeLocal.edit(serviceDelivery);
            } else {
                serviceContDischarge.setPosition(ServiceContDischarge.LOCATION_HT);
            }
        }

        crane.setPlanningVessel(serviceVessel.getPlanningVessel());
        crane.setContNo(serviceContDischarge.getContNo());
        crane.setMlo(serviceContDischarge.getMlo());
        crane.setEquipmentActCode("STEVEDORING");
        crane.setOperation("DISCHARGE");
        
        //set master operator to null if optCode is null
        if (crane.getMasterOperator().getOptCode()==null)
            crane.setMasterOperator(null);
        
        if (ht.getMasterOperator().getOptCode()==null)
            ht.setMasterOperator(null);
        
        equipmentFacadeLocal.edit(crane);
        String equipmentCode = crane.getMasterEquipment().getEquipCode();
        String craneCode = ServiceUtil.getCraneCode(equipmentCode);
            
        serviceContDischarge.setCrane(craneCode);
        if (!isDangerous) {
            ht.setPlanningVessel(serviceVessel.getPlanningVessel());
            ht.setContNo(serviceContDischarge.getContNo());
            ht.setMlo(serviceContDischarge.getMlo());
            ht.setEquipmentActCode("H/T");
            ht.setOperation("DISCHARGE");
            ht.setStartTime(crane.getEndTime());
            equipmentFacadeLocal.edit(ht);
        }

        serviceContDischargeFacadeLocal.edit(serviceContDischarge);
    }

    public void handleDischargeConfirmCancelLoadingContainer(CancelLoadingService cancelLoadingService, Equipment crane, Equipment ht) throws TimeSelectionNotValidException {
        if (crane.getStartTime().after(crane.getEndTime()) || crane.getStartTime().equals(crane.getEndTime())) {
            throw new TimeSelectionNotValidException("End time must be after start time!");
        }

        if (cancelLoadingService.getPosition().equals(CancelLoadingService.AT_VESSEL)) {
            cancelLoadingService.setPosisi(CancelLoadingService.AT_HAULAGE_TRUCK);
//            cancelLoadingService.setIsDischarge(Boolean.TRUE);
            cancelLoadingService.setIsDischarge("TRUE");            
        }

        crane.setPlanningVessel(cancelLoadingService.getPlanningVessel());
        crane.setContNo(cancelLoadingService.getContNo());
        crane.setMlo(cancelLoadingService.getMlo());
        crane.setEquipmentActCode("STEVEDORING");
        crane.setOperation("DISCHARGE");

        if (cancelLoadingService.getTruckLosing().equals("TRUE")) {
            if ( cancelLoadingService.getIsDelivery().equals("FALSE")) {
//                cancelLoadingService.setIsDelivery(Boolean.TRUE);
                cancelLoadingService.setIsDelivery("TRUE");

                MasterYardCoordinat targetLocation = masterYardCoordinatFacadeLocal.findByCoordinat(cancelLoadingService.getMasterYard().getBlock(), cancelLoadingService.getySlot(), cancelLoadingService.getyRow(), cancelLoadingService.getyTier());
                MasterYardCoordinat targetLocation40ft = masterYardCoordinatFacadeLocal.findByCoordinat(cancelLoadingService.getMasterYard().getBlock(), cancelLoadingService.getySlot() + 1, cancelLoadingService.getyRow(), cancelLoadingService.getyTier());
                MasterYardCoordinat targetLocation45ft = masterYardCoordinatFacadeLocal.findByCoordinat(cancelLoadingService.getMasterYard().getBlock(), cancelLoadingService.getySlot() + 2, cancelLoadingService.getyRow(), cancelLoadingService.getyTier());

                if (targetLocation != null && targetLocation.getStatus().equals("planning") && targetLocation.getContNo().equals(cancelLoadingService.getContNo())) {
                    targetLocation.setContNo(null);
                    targetLocation.setMlo(null);
                    targetLocation.setContSize(null);
                    targetLocation.setContType(null);
                    targetLocation.setPod(null);
                    targetLocation.setNoPpkb(null);
                    targetLocation.setGrossClass(null);
                    targetLocation.setStatus("empty");
                    masterYardCoordinatFacadeLocal.edit(targetLocation);
                }

                if (cancelLoadingService.getContSize() == 40 && targetLocation40ft != null && targetLocation40ft.getStatus().equals("planning") && targetLocation40ft.getContNo().equals(cancelLoadingService.getContNo())) {
                    targetLocation40ft.setContNo(null);
                    targetLocation40ft.setMlo(null);
                    targetLocation40ft.setContSize(null);
                    targetLocation40ft.setContType(null);
                    targetLocation40ft.setPod(null);
                    targetLocation40ft.setNoPpkb(null);
                    targetLocation40ft.setGrossClass(null);
                    targetLocation40ft.setStatus("empty");
                    masterYardCoordinatFacadeLocal.edit(targetLocation40ft);
                }

                if (cancelLoadingService.getContSize() == 45 && targetLocation45ft != null && targetLocation45ft.getStatus().equals("planning") && targetLocation45ft.getContNo().equals(cancelLoadingService.getContNo())) {
                    targetLocation45ft.setContNo(null);
                    targetLocation45ft.setMlo(null);
                    targetLocation45ft.setContSize(null);
                    targetLocation45ft.setContType(null);
                    targetLocation45ft.setPod(null);
                    targetLocation45ft.setNoPpkb(null);
                    targetLocation45ft.setGrossClass(null);
                    targetLocation45ft.setStatus("empty");
                    masterYardCoordinatFacadeLocal.edit(targetLocation45ft);
                }
            }
        } else {
            ht.setPlanningVessel(cancelLoadingService.getPlanningVessel());
            ht.setContNo(cancelLoadingService.getContNo());
            ht.setMlo(cancelLoadingService.getMlo());
            ht.setEquipmentActCode("H/T");
            ht.setOperation("DISCHARGE");
            ht.setStartTime(crane.getEndTime());
            equipmentFacadeLocal.edit(ht);
        }

        equipmentFacadeLocal.edit(crane);
        cancelLoadingServiceFacadeLocal.edit(cancelLoadingService);
    }

    public void handleDischargeConfirmShiftingContainer(ServiceShifting serviceShifting, Equipment crane, Equipment ht) throws TimeSelectionNotValidException {
        if (crane.getStartTime().after(crane.getEndTime()) || crane.getStartTime().equals(crane.getEndTime())) {
            throw new TimeSelectionNotValidException("End time must be after start time!");
        }

        ServiceVessel serviceVessel = serviceShifting.getServiceVessel();

        crane.setPlanningVessel(serviceVessel.getPlanningVessel());
        crane.setContNo(serviceShifting.getContNo());
        crane.setEquipmentActCode("STEVEDORING");
        crane.setOperation("SHIFTING-CY");

        ht.setPlanningVessel(serviceVessel.getPlanningVessel());
        ht.setContNo(serviceShifting.getContNo());
        ht.setEquipmentActCode("H/T");
        ht.setOperation("SHIFTING-CY");
        ht.setStartTime(crane.getEndTime());

        if (serviceShifting.getPosition().equals(ServiceShifting.LOCATION_SHIFTING)) {
            serviceShifting.setIsPaid("TRUE");
            serviceShifting.setPosition(ServiceShifting.LOCATION_HT_SHIFTING);
            serviceShiftingFacadeLocal.edit(serviceShifting);
        }

        equipmentFacadeLocal.edit(crane);
        equipmentFacadeLocal.edit(ht);
    }

    public void handleDischargeConfirmTanshipmentContainer() {
        
    }
}
