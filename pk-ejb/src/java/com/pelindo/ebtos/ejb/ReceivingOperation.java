/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb;

import com.pelindo.ebtos.ejb.facade.local.EquipmentFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.PlanningContLoadFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.PlanningContReceivingFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.ServiceReceivingFacadeLocal;
import com.pelindo.ebtos.ejb.local.YardOperationLocal;
import com.pelindo.ebtos.ejb.remote.ReceivingOperationRemote;
import com.pelindo.ebtos.exception.ContainerNotMovableException;
import com.pelindo.ebtos.exception.LocationNotAvailableException;
import com.pelindo.ebtos.exception.TimeSelectionNotValidException;
import com.pelindo.ebtos.model.YardLocation;
import com.pelindo.ebtos.model.db.Equipment;
import com.pelindo.ebtos.model.db.PlanningContLoad;
import com.pelindo.ebtos.model.db.PlanningContReceiving;
import com.pelindo.ebtos.model.db.PlanningVessel;
import com.pelindo.ebtos.model.db.ServiceReceiving;
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
public class ReceivingOperation implements ReceivingOperationRemote {
    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    @EJB
    private EquipmentFacadeLocal equipmentFacadeLocal;
    @EJB
    private ServiceReceivingFacadeLocal serviceReceivingFacadeLocal;
    @EJB
    private PlanningContLoadFacadeLocal planningContLoadFacadeLocal;
    @EJB
    private PlanningContReceivingFacadeLocal planningContReceivingFacadeLocal;
    @EJB
    private YardOperationLocal yardOperationLocal;
    
    public void handleReceivingConfirm(ServiceReceiving serviceReceiving, PlanningContReceiving planningContReceiving, Equipment equipment) throws TimeSelectionNotValidException, LocationNotAvailableException {
        if (equipment.getStartTime().after(equipment.getEndTime()) || equipment.getStartTime().equals(equipment.getEndTime())) {
            throw new TimeSelectionNotValidException("End time must be after start time!");
        }

        PlanningVessel planningVessel = planningContReceiving.getPlanningVessel();

        serviceReceiving.setContNo(planningContReceiving.getContNo());
        serviceReceiving.setMlo(planningContReceiving.getMlo());
        serviceReceiving.setContGross(planningContReceiving.getContGross());
        serviceReceiving.setContSize(planningContReceiving.getContSize());
        serviceReceiving.setContStatus(planningContReceiving.getContStatus());
        serviceReceiving.setDangerous(planningContReceiving.getDg());
        serviceReceiving.setDgLabel(planningContReceiving.getDgLabel());
        serviceReceiving.setMasterCommodity(planningContReceiving.getMasterCommodity());
        serviceReceiving.setMasterContainerType(planningContReceiving.getMasterContainerType());
        serviceReceiving.setPlanningVessel(planningVessel);
        serviceReceiving.setOverSize(planningContReceiving.getOverSize());
        serviceReceiving.setSealNo(planningContReceiving.getSealNo());
        serviceReceiving.setGrossClass(planningContReceiving.getGrossClass());
        serviceReceiving.setTwentyOneFeet(planningContReceiving.getTwentyOneFeet());
        serviceReceiving.setTransactionDate(equipment.getEndTime());
        serviceReceiving.setIsBehandle("FALSE");
        serviceReceiving.setIsCfs("FALSE");
        serviceReceiving.setIsInspection("FALSE");
        serviceReceiving.setBlNo(planningContReceiving.getBlNo());
        serviceReceiving.setChangeStatus("FALSE");
        serviceReceiving.setIsChangeDestination("FALSE");
        serviceReceiving.setStatusCancelLoading("FALSE");
        serviceReceiving.setPortOfDelivery(planningContReceiving.getPortOfDelivery());

        equipment.setNoPpkbRecv(planningVessel.getNoPpkb());
        equipment.setEquipmentActCode("RECEIVING");
        equipment.setOperation("LOAD");
        equipment.setContNo(serviceReceiving.getContNo());
        equipment.setMlo(serviceReceiving.getMlo());

        PlanningContLoad planningContLoad = planningContLoadFacadeLocal.findByNoPpkbAndContNoExcludeCancelLoading(planningVessel.getNoPpkb(), planningContReceiving.getContNo());

        if (planningContLoad == null) {
            planningContLoad = new PlanningContLoad();
        }

        planningContLoad.setMasterCommodity(serviceReceiving.getMasterCommodity());
        planningContLoad.setMasterYard(serviceReceiving.getMasterYard());
        planningContLoad.setMasterContainerType(serviceReceiving.getMasterContainerType());
        planningContLoad.setPlanningVessel(serviceReceiving.getPlanningVessel());
        planningContLoad.setDischPort(planningContReceiving.getDischPort());
        planningContLoad.setLoadPort(planningContReceiving.getLoadPort());
        planningContLoad.setContNo(serviceReceiving.getContNo());
        planningContLoad.setMlo(serviceReceiving.getMlo());
        planningContLoad.setContSize(serviceReceiving.getContSize());
        planningContLoad.setContStatus(serviceReceiving.getContStatus());
        planningContLoad.setIsTranshipment("FALSE");
        planningContLoad.setContGross(serviceReceiving.getContGross());
        planningContLoad.setSealNo(serviceReceiving.getSealNo());
        planningContLoad.setOverSize(serviceReceiving.getOverSize());
        planningContLoad.setDgLabel(serviceReceiving.getDgLabel());
        planningContLoad.setDg(serviceReceiving.getDangerous());
        planningContLoad.setTwentyOneFeet(serviceReceiving.getTwentyOneFeet());
        planningContLoad.setIsExportImport(planningContReceiving.getIsExport());
        planningContLoad.setYSlot(serviceReceiving.getYSlot());
        planningContLoad.setYRow(serviceReceiving.getYRow());
        planningContLoad.setYTier(serviceReceiving.getYTier());
        planningContLoad.setCompleted("FALSE");
        planningContLoad.setUnknown("FALSE");
        planningContLoad.setBlNo(serviceReceiving.getBlNo());
        planningContLoad.setPortOfDelivery(serviceReceiving.getPortOfDelivery());

        if (planningContReceiving.getPosition().equals("01")) {
            String grossClass = GrossConverter.convert(planningContReceiving.getContSize(), planningContReceiving.getContGross());
            YardLocation newLocation = new YardLocation(serviceReceiving.getMasterYard().getBlock(), serviceReceiving.getYSlot(), serviceReceiving.getYRow(), serviceReceiving.getYTier());
            yardOperationLocal.liftoff(planningContReceiving.getPlanningVessel().getNoPpkb(), planningContReceiving.getContNo(), planningContReceiving.getMasterContainerType().getContType().toString(), planningContReceiving.getContSize(), planningContReceiving.getContGross(), planningContReceiving.getDischPort(), grossClass, newLocation);

            serviceReceiving.setIsLifton("FALSE");
            planningContReceiving.setPosition("02");
            planningContLoad.setPosition(PlanningContLoad.LOCATION_CY);
        }

        planningContLoadFacadeLocal.edit(planningContLoad);
        equipmentFacadeLocal.edit(equipment);
        serviceReceivingFacadeLocal.edit(serviceReceiving);
        planningContReceivingFacadeLocal.edit(planningContReceiving);
    }

    public void handleCancelReceivingConfirm(ServiceReceiving serviceReceiving, PlanningContReceiving planningContReceiving, Equipment equipment) throws ContainerNotMovableException {
        YardLocation oldLocation = new YardLocation(serviceReceiving.getMasterYard().getBlock(), serviceReceiving.getYSlot(), serviceReceiving.getYRow(), serviceReceiving.getYTier());
        yardOperationLocal.lifton(planningContReceiving.getPlanningVessel().getNoPpkb(), serviceReceiving.getContNo(), serviceReceiving.getContSize(), oldLocation);

        planningContReceiving.setPosition("01");

        equipmentFacadeLocal.remove(equipment);
        serviceReceivingFacadeLocal.remove(serviceReceiving);
        planningContReceivingFacadeLocal.edit(planningContReceiving);
        em.flush();
        planningContLoadFacadeLocal.deleteByNoPpkbAndContNo(planningContReceiving.getPlanningVessel().getNoPpkb(), planningContReceiving.getContNo());
    }
}
