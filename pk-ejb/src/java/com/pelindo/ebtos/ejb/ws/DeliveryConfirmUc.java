/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.ejb.ws;

import com.qtasnim.util.ParseDate;
import com.pelindo.ebtos.ejb.facade.remote.EquipmentFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterCommodityFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterEquipmentFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterOperatorFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningVesselFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.UcDeliveryServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.UncontainerizedServiceFacadeRemote;
import com.pelindo.ebtos.ejb.ws.model.Uncontainerized;
import com.pelindo.ebtos.ejb.ws.model.DeliveryConfirmUcInitialData;
import com.pelindo.ebtos.ejb.ws.model.DeliveryConfirmUcLookupData;
import com.pelindo.ebtos.ejb.ws.model.DeliveryConfirmUcSubmitData;
import com.pelindo.ebtos.ejb.ws.model.IMessageable;
import com.pelindo.ebtos.ejb.ws.model.Message;
import com.pelindo.ebtos.model.db.Equipment;
import com.pelindo.ebtos.model.db.PlanningVessel;
import com.pelindo.ebtos.model.db.UcDeliveryService;
import com.pelindo.ebtos.model.db.UncontainerizedService;
import com.pelindo.ebtos.model.db.master.MasterEquipment;
import com.pelindo.ebtos.model.db.master.MasterOperator;
import com.pelindo.ebtos.security.UserUtil;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.WebParam;
import javax.ejb.Stateless;
import javax.ejb.EJB;

/**
 *
 * @author x42jr
 */
@WebService()
@Stateless()
public class DeliveryConfirmUc {
    private static final String CRANE_SEA = "EQ000";

    @EJB
    private PlanningVesselFacadeRemote planningVesselFacade;
    @EJB
    private MasterEquipmentFacadeRemote masterEquipmentFacadeRemote;
    @EJB
    private MasterOperatorFacadeRemote masterOperatorFacadeRemote;
    @EJB
    private EquipmentFacadeRemote equipmentFacadeRemote;
    @EJB
    private UncontainerizedServiceFacadeRemote uncontainerizedServiceFacadeRemote;
    @EJB
    private MasterCommodityFacadeRemote masterCommodityFacadeRemote;
    @EJB
    private UcDeliveryServiceFacadeRemote ucDeliveryServiceFacadeRemote;

    @WebMethod(operationName = "prepareDeliveryConfirmUcUI")
    public DeliveryConfirmUcInitialData prepareDeliveryConfirmUcUI() {
        DeliveryConfirmUcInitialData deliveryUcConfirm = null;

        try {
            List<Object> ttCodes = masterEquipmentFacadeRemote.findTango4HHT();
            List<Object[]> operators = masterOperatorFacadeRemote.findMasterOperators4HHT();

            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            String serverTime = format.format(new Date());

            deliveryUcConfirm = new DeliveryConfirmUcInitialData(IMessageable.OK, "Data found!");
            deliveryUcConfirm.setServerTime(serverTime);
            deliveryUcConfirm.setEquipmentCodes(ttCodes);
            deliveryUcConfirm.setOperators(operators);
        } catch (RuntimeException re) {
            deliveryUcConfirm = new DeliveryConfirmUcInitialData(IMessageable.ERROR, "Internal error!");
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", re);
        }

        return deliveryUcConfirm;
    }

    @WebMethod(operationName = "findDeliveryUc")
    public DeliveryConfirmUcLookupData findDeliveryUc(@WebParam(name = "blNo") String blNo){
        DeliveryConfirmUcLookupData deliveryConfirmUcLookupData = null;

        try {
            Object[] result = uncontainerizedServiceFacadeRemote.findDeliverableUcByBlNo(blNo);

            if (result != null) {
                PlanningVessel planningVessel = planningVesselFacade.find((String) result[1]);
                Boolean isTruckLoosing = (Boolean) result[6];
                List<Object> equipments = null;

                Uncontainerized uncontainerized = new Uncontainerized(IMessageable.OK, "Data found!");
                uncontainerized.setVesselName(planningVessel.getPreserviceVessel().getMasterVessel().getName());
                uncontainerized.setBlNo(blNo);
                uncontainerized.setCommodityName((String) result[3]);
                uncontainerized.setWeight((Integer) result[5]);
                uncontainerized.setUnit((Integer) result[4]);
                uncontainerized.setYardBlock((String) result[7]);

                if (isTruckLoosing) {
                    uncontainerized.setYardBlock(planningVessel.getMasterDock().getName());
                    equipments = masterEquipmentFacadeRemote.findCrane4HHT();
                } else {
                    equipments = masterEquipmentFacadeRemote.findTango4HHT();
                }

                deliveryConfirmUcLookupData = new DeliveryConfirmUcLookupData(IMessageable.OK, "Data found!");
                deliveryConfirmUcLookupData.setUncontainerized(uncontainerized);
                deliveryConfirmUcLookupData.setEquipmentCodes(equipments);
            } else {
                deliveryConfirmUcLookupData = new DeliveryConfirmUcLookupData(IMessageable.ERROR, "Data not found!");
            }
        } catch (RuntimeException re) {
            deliveryConfirmUcLookupData = new DeliveryConfirmUcLookupData(IMessageable.ERROR, "Internal error!");
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", re);
        }

        return deliveryConfirmUcLookupData;
    }

    @WebMethod(operationName = "deliveryUcConfirm")
    public Message deliveryUcConfirm(@WebParam(name = "submitData") DeliveryConfirmUcSubmitData submitData) {
        Message message = null;

        try {
            UserUtil.setCurrentUser(submitData.getCreatedBy());
            Object[] result = uncontainerizedServiceFacadeRemote.findDeliverableUcByBlNo(submitData.getBlNo());

            if (result != null) {
                UncontainerizedService uncontainerizedService = uncontainerizedServiceFacadeRemote.find((Integer) result[0]);
                UcDeliveryService ucDeliveryService = uncontainerizedService.getUcDeliveryService();
                MasterEquipment masterEquipment = masterEquipmentFacadeRemote.find(submitData.getEquipmentCode());
                MasterOperator masterOperator = masterOperatorFacadeRemote.find(submitData.getEquipmentOperatorCode());
                PlanningVessel planningVessel = planningVesselFacade.find(uncontainerizedService.getNoPpkb());
                Date start = ParseDate.changeDate(submitData.getStartTime());
                Date end = ParseDate.changeDate(submitData.getEndTime());

                Equipment equipment = new Equipment();
                equipment.setPlanningVessel(planningVessel);
                equipment.setBlNo(uncontainerizedService.getBlNo());
                equipment.setEquipmentActCode("DELIVERY");
                equipment.setOperation("DISCHARGEUC");
                equipment.setMasterEquipment(masterEquipment);
                equipment.setMasterOperator(masterOperator);
                equipment.setStartTime(start);
                equipment.setEndTime(end);

                uncontainerizedService.setIsDelivery("TRUE");

                ucDeliveryService.setIsDelivery("TRUE");
                ucDeliveryService.setDeliveryDate(equipment.getStartTime());

                ucDeliveryServiceFacadeRemote.edit(ucDeliveryService);
                uncontainerizedServiceFacadeRemote.edit(uncontainerizedService);
                equipmentFacadeRemote.edit(equipment);
                message = new Message(IMessageable.OK, "Success!");
            } else {
                message = new Message(IMessageable.ERROR, "Data not found!");
            }
        } catch (RuntimeException re) {
            message = new Message(IMessageable.ERROR, "Internal error!");
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", re);
        }
        
        return message;
    }
}
