/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb;

import com.pelindo.ebtos.ejb.facade.local.CancelLoadingServiceFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.EquipmentFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.ServiceContDischargeFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.ServiceDeliveryFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.ServiceGateDeliveryFacadeLocal;
import com.pelindo.ebtos.ejb.local.YardOperationLocal;
import com.pelindo.ebtos.ejb.remote.DeliveryOperationRemote;
import com.pelindo.ebtos.exception.ContainerNotMovableException;
import com.pelindo.ebtos.exception.LocationNotAvailableException;
import com.pelindo.ebtos.exception.TimeSelectionNotValidException;
import com.pelindo.ebtos.model.YardLocation;
import com.pelindo.ebtos.model.db.CancelLoadingService;
import com.pelindo.ebtos.model.db.Equipment;
import com.pelindo.ebtos.model.db.ServiceContDischarge;
import com.pelindo.ebtos.model.db.ServiceDelivery;
import com.pelindo.ebtos.model.db.ServiceGateDelivery;
import com.pelindo.ebtos.model.db.ServiceVessel;
import com.pelindo.ebtos.util.GrossConverter;
import java.util.Arrays;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author R. Seno Anggoro A
 */
@Stateless
public class DeliveryOperation implements DeliveryOperationRemote {
    @EJB
    private ServiceContDischargeFacadeLocal serviceContDischargeFacadeLocal;
    @EJB
    private ServiceDeliveryFacadeLocal serviceDeliveryFacadeLocal;
    @EJB
    private EquipmentFacadeLocal equipmentFacadeLocal;
    @EJB
    private ServiceGateDeliveryFacadeLocal serviceGateDeliveryFacadeLocal;
    @EJB
    private YardOperationLocal yardOperationLocal;
    @EJB
    private CancelLoadingServiceFacadeLocal cancelLoadingServiceFacadeLocal;

    public void handleDeliveryConfirm(ServiceContDischarge serviceContDischarge, Equipment equipment) throws TimeSelectionNotValidException, ContainerNotMovableException {
        if (equipment.getStartTime().after(equipment.getEndTime()) || equipment.getStartTime().equals(equipment.getEndTime())) {
            throw new TimeSelectionNotValidException("End time must be after start time!");
        }
        
        ServiceVessel serviceVessel = serviceContDischarge.getServiceVessel();
        ServiceDelivery serviceDelivery = serviceDeliveryFacadeLocal.findByNoPpkbAndContNo(serviceVessel.getNoPpkb(), serviceContDischarge.getContNo());

        if (serviceDelivery == null) {
            serviceDelivery = new ServiceDelivery();
        }

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
        serviceDelivery.setTransactionDate(equipment.getStartTime());

        if (serviceContDischarge.getIsDelivery().equals("FALSE")) {
            YardLocation oldLocation = new YardLocation(serviceContDischarge.getMasterYard().getBlock(), serviceContDischarge.getYSlot(), serviceContDischarge.getYRow(), serviceContDischarge.getYTier());
            yardOperationLocal.lifton(serviceVessel.getPlanningVessel().getNoPpkb(), serviceContDischarge.getContNo(), serviceContDischarge.getContSize(), oldLocation);

            ServiceGateDelivery serviceGateDelivery = serviceGateDeliveryFacadeLocal.findByContPpkb(serviceContDischarge.getContNo(), serviceVessel.getPlanningVessel().getNoPpkb());
            serviceGateDelivery.setStatus("Y");
            
//            serviceContDischarge.setIsDelivery(Boolean.TRUE);
            serviceContDischarge.setIsDelivery("TRUE");
            serviceGateDeliveryFacadeLocal.edit(serviceGateDelivery);
        }

        equipment.setContNo(serviceContDischarge.getContNo());
        equipment.setEquipmentActCode("DELIVERY");
        equipment.setOperation("DISCHARGE");
        equipment.setPlanningVessel(serviceVessel.getPlanningVessel());

        serviceContDischargeFacadeLocal.edit(serviceContDischarge);
        serviceDeliveryFacadeLocal.edit(serviceDelivery);
        equipmentFacadeLocal.edit(equipment);
    }

    public void handleDeliveryConfirmCancelLoading(CancelLoadingService cancelLoadingService, Equipment equipment) throws ContainerNotMovableException, TimeSelectionNotValidException {
        if (equipment.getStartTime().after(equipment.getEndTime()) || equipment.getStartTime().equals(equipment.getEndTime())) {
            throw new TimeSelectionNotValidException("End time must be after start time!");
        }

        if (cancelLoadingService.getIsDelivery().equals("FALSE")) {
            YardLocation oldLocation = new YardLocation(cancelLoadingService.getMasterYard().getBlock(), cancelLoadingService.getySlot(), cancelLoadingService.getyRow(), cancelLoadingService.getyTier());
            yardOperationLocal.lifton(cancelLoadingService.getPlanningVessel().getNoPpkb(), cancelLoadingService.getContNo(), cancelLoadingService.getContSize(), oldLocation);

            ServiceGateDelivery serviceGateDelivery = serviceGateDeliveryFacadeLocal.findByContPpkb(cancelLoadingService.getContNo(), cancelLoadingService.getPlanningVessel().getNoPpkb());
            serviceGateDelivery.setStatus("Y");

            cancelLoadingService.setIsDelivery("TRUE");

            serviceGateDeliveryFacadeLocal.edit(serviceGateDelivery);
        }

        equipment.setContNo(cancelLoadingService.getContNo());
        equipment.setMlo(cancelLoadingService.getMlo());
        equipment.setEquipmentActCode("DELIVERY");
        equipment.setOperation("DISCHARGE");
        equipment.setPlanningVessel(cancelLoadingService.getPlanningVessel());

        cancelLoadingServiceFacadeLocal.edit(cancelLoadingService);
        equipmentFacadeLocal.edit(equipment);
    }

    public void handleCancelDeliveryConfirm(ServiceContDischarge serviceContDischarge, Equipment equipment) throws LocationNotAvailableException {
        boolean isDangerous = serviceContDischarge.getMasterCommodity().getMasterDangerousClass() != null && Arrays.asList(new String[] {"1", "7"}).contains(serviceContDischarge.getMasterCommodity().getMasterDangerousClass().getDangerousClass());

        if (!isDangerous) {
            YardLocation newLocation = new YardLocation(serviceContDischarge.getMasterYard().getBlock(), serviceContDischarge.getYSlot(), serviceContDischarge.getYRow(), serviceContDischarge.getYTier());
            yardOperationLocal.liftoff(serviceContDischarge.getServiceVessel().getPlanningVessel().getNoPpkb(), serviceContDischarge.getContNo(), serviceContDischarge.getMasterContainerType().getContType().toString(), serviceContDischarge.getContSize(), serviceContDischarge.getContGross(), serviceContDischarge.getDischPort(), serviceContDischarge.getGrossClass(), newLocation);
        }

//        serviceContDischarge.setIsDelivery(Boolean.FALSE);
        serviceContDischarge.setIsDelivery("FALSE");
        ServiceGateDelivery serviceGateDelivery = serviceGateDeliveryFacadeLocal.findByContPpkb(serviceContDischarge.getContNo(), serviceContDischarge.getServiceVessel().getPlanningVessel().getNoPpkb());
        serviceGateDelivery.setStatus("N");

        ServiceDelivery serviceDelivery = serviceDeliveryFacadeLocal.findByNoPpkbAndContNo(serviceContDischarge.getServiceVessel().getNoPpkb(), serviceContDischarge.getContNo());
        serviceGateDeliveryFacadeLocal.edit(serviceGateDelivery);
        serviceContDischargeFacadeLocal.edit(serviceContDischarge);
        equipmentFacadeLocal.remove(equipment);
        serviceDeliveryFacadeLocal.remove(serviceDelivery);
    }

    public void handleCancelDeliveryConfirmCancelLoading(CancelLoadingService cancelLoadingService, Equipment equipment) throws LocationNotAvailableException {
        String grossClass = GrossConverter.convert(cancelLoadingService.getContSize(), cancelLoadingService.getContGross());
        YardLocation newLocation = new YardLocation(cancelLoadingService.getMasterYard().getBlock(), cancelLoadingService.getySlot(), cancelLoadingService.getyRow(), cancelLoadingService.getyTier());
        yardOperationLocal.liftoff(cancelLoadingService.getPlanningVessel().getNoPpkb(), cancelLoadingService.getContNo(), cancelLoadingService.getMasterContainerType().getContType().toString(), cancelLoadingService.getContSize(), cancelLoadingService.getContGross(), cancelLoadingService.getDischargePort().getPortCode(), grossClass, newLocation);

        ServiceGateDelivery serviceGateDelivery = serviceGateDeliveryFacadeLocal.findByContPpkb(cancelLoadingService.getContNo(), cancelLoadingService.getPlanningVessel().getNoPpkb());
        serviceGateDelivery.setStatus("N");

//        cancelLoadingService.setIsDelivery(Boolean.FALSE);
        cancelLoadingService.setIsDelivery("FALSE");
        
        serviceGateDeliveryFacadeLocal.edit(serviceGateDelivery);
        cancelLoadingServiceFacadeLocal.edit(cancelLoadingService);
        equipmentFacadeLocal.remove(equipment);
    }
}
