/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.ejb.ws;

import com.qtasnim.util.ParseDate;
import com.pelindo.ebtos.ejb.facade.remote.EquipmentFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterEquipmentFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterOperatorFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningVesselFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceShiftingFacadeRemote;
import com.pelindo.ebtos.ejb.ws.model.Container;
import com.pelindo.ebtos.ejb.ws.model.ReshippingConfirmInitialData;
import com.pelindo.ebtos.ejb.ws.model.ReshippingConfirmSubmitData;
import com.pelindo.ebtos.ejb.ws.model.IMessageable;
import com.pelindo.ebtos.ejb.ws.model.Message;
import com.pelindo.ebtos.ejb.ws.model.ReshippableContainer;
import com.pelindo.ebtos.model.db.Equipment;
import com.pelindo.ebtos.model.db.ServiceShifting;
import com.pelindo.ebtos.model.db.ServiceVessel;
import com.pelindo.ebtos.model.db.master.MasterEquipment;
import com.pelindo.ebtos.model.db.master.MasterOperator;
import com.pelindo.ebtos.security.UserUtil;
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
public class ReshippingConfirm {
    public static String RESHIPPING = "Reshipping";
    public static String RESHIPPING_WITH_TRUCK = "Reshipping with truct";
    
    @EJB
    private PlanningVesselFacadeRemote planningVesselFacade;
    @EJB
    private MasterEquipmentFacadeRemote masterEquipmentFacadeRemote;
    @EJB
    private MasterOperatorFacadeRemote masterOperatorFacadeRemote;
    @EJB
    private ServiceShiftingFacadeRemote serviceShiftingFacadeRemote;
    @EJB
    private EquipmentFacadeRemote equipmentFacadeRemote;

    @WebMethod(operationName = "prepareReshippingUI")
    public ReshippingConfirmInitialData prepareReshippingUI() {
        ReshippingConfirmInitialData shiftingInitialData = null;

        try {
            List<Object[]> vessels = planningVesselFacade.findByStatus4HHT("Servicing");
            List<Object[]> operators = masterOperatorFacadeRemote.findMasterOperators4HHT();
            List<Object> ccCodes = masterEquipmentFacadeRemote.findCrane4HHT();
            List<Object> htCodes = masterEquipmentFacadeRemote.findHeadTruck4HHT();

            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            String serverTime = format.format(new Date());

            shiftingInitialData = new ReshippingConfirmInitialData(IMessageable.OK, "Data found!");
            shiftingInitialData.setVessels(vessels);
            shiftingInitialData.setOperators(operators);
            shiftingInitialData.setCcCodes(ccCodes);
            shiftingInitialData.setHtCodes(htCodes);
            shiftingInitialData.setServerTime(serverTime);

        } catch (RuntimeException re) {
            shiftingInitialData = new ReshippingConfirmInitialData(IMessageable.ERROR, "Internal error!");
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", re);
        }

        return shiftingInitialData;
    }

    @WebMethod(operationName = "findReshippableContainer")
    public ReshippableContainer findReshippableContainer(@WebParam(name = "noPpkb") String noPpkb, @WebParam(name = "contNo") String contNo){
        ReshippableContainer reshippableContainer = null;

        try {
            ServiceShifting serviceShifting = serviceShiftingFacadeRemote.findByNoPpkbContNoAndReshippingStatus(noPpkb, contNo, "FALSE");

            if (serviceShifting != null) {
                reshippableContainer = new ReshippableContainer(IMessageable.OK, "Data found!");
                reshippableContainer.setIsAtHeadTruck(false);

                Container container = new Container(IMessageable.OK, "Data found!");
                container.setVesselBay(serviceShifting.getVBay());
                container.setVesselRow(serviceShifting.getVRow());
                container.setVesselTier(serviceShifting.getVTier());
                container.setSize(serviceShifting.getContSize());
                container.setStatus(serviceShifting.getContStatus());
                container.setOverSize(serviceShifting.getOverSize());
                container.setDangerous(serviceShifting.getDg());
                container.setDgLabel(serviceShifting.getDgLabel());

                Equipment haulageTrucking = equipmentFacadeRemote.findByEquipmentActCodeAndOperation(noPpkb, contNo, "H/T", "RESHIPPING");

                if (haulageTrucking != null && haulageTrucking.getEndTime() == null) {
                    reshippableContainer.setIsAtHeadTruck(true);
                }

                reshippableContainer.setContainer(container);
            } else {
                reshippableContainer = new ReshippableContainer(IMessageable.ERROR, "Data not found!");
            }
        } catch (RuntimeException re) {
            reshippableContainer = new ReshippableContainer(IMessageable.ERROR, "Internal error!");
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", re);
        }

        return reshippableContainer;
    }

    @WebMethod(operationName = "reshippingConfirm")
    public Message reshippingConfirm(@WebParam(name = "submitData") ReshippingConfirmSubmitData submitData) {
        Message message = null;

        try {
            UserUtil.setCurrentUser(submitData.getCreatedBy());
            ServiceShifting serviceShifting = serviceShiftingFacadeRemote.findByNoPpkbContNoAndReshippingStatus(submitData.getNoPpkb(), submitData.getContNo(), "FALSE");
            
            if (serviceShifting != null) {
                MasterEquipment craneMasterEquipment = masterEquipmentFacadeRemote.find(submitData.getCcCode());
                MasterOperator craneOperator = masterOperatorFacadeRemote.find(submitData.getCcOperatorCode());
                Date start = ParseDate.changeDate(submitData.getStartTime());
                Date end = ParseDate.changeDate(submitData.getEndTime());
                Boolean isUseHT = submitData.getIsUseHt() != null && Boolean.parseBoolean(submitData.getIsUseHt());

                ServiceVessel serviceVessel = serviceShifting.getServiceVessel();

                Equipment reshipping = new Equipment();
                reshipping.setMasterEquipment(craneMasterEquipment);
                reshipping.setPlanningVessel(serviceVessel.getPlanningVessel());
                reshipping.setContNo(submitData.getContNo());
                reshipping.setStartTime(start);
                reshipping.setEndTime(end);
                reshipping.setOperation("RESHIPPING");
                reshipping.setMasterOperator(craneOperator);
                reshipping.setEquipmentActCode("STEVEDORING");
                equipmentFacadeRemote.create(reshipping);

                if (isUseHT) {
                    MasterEquipment haulageTruckingMasterEquipment = masterEquipmentFacadeRemote.find(submitData.getHtCode());
                    MasterOperator haulageTruckingOperator = masterOperatorFacadeRemote.find(submitData.getHtOperatorCode());

                    Equipment haulageTrucking = new Equipment();
                    haulageTrucking.setMasterEquipment(haulageTruckingMasterEquipment);
                    haulageTrucking.setPlanningVessel(serviceVessel.getPlanningVessel());
                    haulageTrucking.setContNo(submitData.getContNo());
                    haulageTrucking.setStartTime(start);
                    haulageTrucking.setEquipmentActCode("H/T");
                    haulageTrucking.setOperation("RESHIPPING");
                    haulageTrucking.setMasterOperator(haulageTruckingOperator);

                    equipmentFacadeRemote.create(haulageTrucking);
                } else {
                    int i = equipmentFacadeRemote.findIdEquipmentByAll(reshipping.getMasterEquipment().getEquipCode(), reshipping.getMasterOperator().getOptCode(), reshipping.getContNo(), submitData.getNoPpkb(), reshipping.getStartTime(), reshipping.getEndTime(), reshipping.getEquipmentActCode(), reshipping.getOperation());
                    serviceShifting.setIdEquipmentReshipping(i);
                    serviceShifting.setShiftingDate(reshipping.getEndTime());
                    serviceShifting.setVBay(Short.parseShort(submitData.getVesselBay()));
                    serviceShifting.setVRow(Short.parseShort(submitData.getVesselRow()));
                    serviceShifting.setVTier(Short.parseShort(submitData.getVesselTier()));
                    serviceShifting.setIsReshipping("TRUE");
                    serviceShifting.setModifiedBy(submitData.getModifiedBy());

                    Equipment haulageTrucking = equipmentFacadeRemote.findByEquipmentActCodeAndOperation(submitData.getNoPpkb(), submitData.getContNo(), "H/T", "RESHIPPING");

                    if (haulageTrucking != null && haulageTrucking.getEndTime() == null) {
                        haulageTrucking.setEndTime(reshipping.getStartTime());
                        equipmentFacadeRemote.edit(haulageTrucking);
                    }

                    serviceShiftingFacadeRemote.edit(serviceShifting);
                }

                message = new Message(IMessageable.OK, "Succeed!");
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
