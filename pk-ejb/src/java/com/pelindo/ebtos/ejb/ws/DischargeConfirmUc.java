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
import com.pelindo.ebtos.ejb.facade.remote.ServiceVesselFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.UcGatedeliveryServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.UcShiftingServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.UncontainerizedServiceFacadeRemote;
import com.pelindo.ebtos.ejb.ws.model.Uncontainerized;
import com.pelindo.ebtos.ejb.ws.model.DischargeConfirmUcInitialData;
import com.pelindo.ebtos.ejb.ws.model.DischargeConfirmUcSubmitData;
import com.pelindo.ebtos.ejb.ws.model.IMessageable;
import com.pelindo.ebtos.ejb.ws.model.Message;
import com.pelindo.ebtos.model.db.Equipment;
import com.pelindo.ebtos.model.db.ServiceVessel;
import com.pelindo.ebtos.model.db.UcDeliveryService;
import com.pelindo.ebtos.model.db.UcGatedeliveryService;
import com.pelindo.ebtos.model.db.UcShiftingService;
import com.pelindo.ebtos.model.db.UncontainerizedService;
import com.pelindo.ebtos.model.db.master.MasterCommodity;
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
public class DischargeConfirmUc {
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
    private ServiceVesselFacadeRemote serviceVesselFacadeRemote;
    @EJB
    private MasterCommodityFacadeRemote masterCommodityFacadeRemote;
    @EJB
    private UcGatedeliveryServiceFacadeRemote ucGatedeliveryServiceFacadeRemote;
    @EJB
    private UcShiftingServiceFacadeRemote ucShiftingServiceFacadeRemote;

    @WebMethod(operationName = "prepareDischargeConfirmUcUI")
    public DischargeConfirmUcInitialData prepareDischargeConfirmUcUI() {
        DischargeConfirmUcInitialData dischargeUcConfirm = null;

        try {
            List<Object> ccCodes = masterEquipmentFacadeRemote.findCrane4HHT();
            List<Object> htCodes = masterEquipmentFacadeRemote.findHeadTruck4HHT();
            List<Object[]> operators = masterOperatorFacadeRemote.findMasterOperators4HHT();
            List<Object[]> vessels = planningVesselFacade.findByStatus4HHT("Servicing");

            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            String serverTime = format.format(new Date());

            dischargeUcConfirm = new DischargeConfirmUcInitialData(IMessageable.OK, "Data found!");
            dischargeUcConfirm.setServerTime(serverTime);
            dischargeUcConfirm.setCcCodes(ccCodes);
            dischargeUcConfirm.setHtCodes(htCodes);
            dischargeUcConfirm.setOperators(operators);
            dischargeUcConfirm.setVessels(vessels);
        } catch (RuntimeException re) {
            dischargeUcConfirm = new DischargeConfirmUcInitialData(IMessageable.ERROR, "Internal error!");
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", re);
        }

        return dischargeUcConfirm;
    }

    @WebMethod(operationName = "findDischargeUc")
    public Uncontainerized findDischargeUc(@WebParam(name = "noPpkb") String noPpkb, @WebParam(name = "blNo") String blNo){
        Uncontainerized uncontainerized = null;

        try {
            UncontainerizedService uncontainerizedService = uncontainerizedServiceFacadeRemote.findDischargableUcByNoPpkbAndBlNo(noPpkb, blNo);

            if (uncontainerizedService != null) {
                MasterCommodity masterCommodity = masterCommodityFacadeRemote.find(uncontainerizedService.getCommodity());
                UcDeliveryService ucDeliveryService = uncontainerizedService.getUcDeliveryService();
                uncontainerizedService.setTruckLoosing("FALSE");
                Boolean isHasEnteredGate = Boolean.FALSE;

                if (ucDeliveryService != null 
                        && uncontainerizedService.getIsShifting().equals("FALSE")
                        && uncontainerizedService.getIsTranshipment().equals("FALSE")) {
                    UcGatedeliveryService ucGatedeliveryService = ucGatedeliveryServiceFacadeRemote.findNotDeliveredYetByJobSlip(ucDeliveryService.getJobslip());
                    isHasEnteredGate = ucGatedeliveryService != null;

                    if (isHasEnteredGate) {
                        uncontainerizedService.setTruckLoosing("TRUE");
                    }
                }

                uncontainerized = new Uncontainerized(IMessageable.OK, "Data found!");
                uncontainerized.setNoPpkb(noPpkb);
                uncontainerized.setBlNo(blNo);
                uncontainerized.setCommodityName(masterCommodity.getName());
                uncontainerized.setWeight(uncontainerizedService.getWeight());
                uncontainerized.setUnit(uncontainerizedService.getUnit());
                uncontainerized.setIsHasEnteredGate(isHasEnteredGate);
            } else {
                uncontainerized = new Uncontainerized(IMessageable.ERROR, "Data not found!");
            }
        } catch (RuntimeException re) {
            uncontainerized = new Uncontainerized(IMessageable.ERROR, "Internal error!");
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", re);
        }

        return uncontainerized;
    }

    @WebMethod(operationName = "dischargeUcStevedoringConfirm")
    public Message dischargeUcStevedoringConfirm(@WebParam(name = "submitData") DischargeConfirmUcSubmitData submitData) {
        Message message = null;

        try {
            UserUtil.setCurrentUser(submitData.getCreatedBy());
            UncontainerizedService uncontainerizedService = uncontainerizedServiceFacadeRemote.findDischargableUcByNoPpkbAndBlNo(submitData.getNoPpkb(), submitData.getBlNo());
            Date start = ParseDate.changeDate(submitData.getStartTime());
            Date end = ParseDate.changeDate(submitData.getEndTime());

            if (uncontainerizedService != null) {
                ServiceVessel serviceVessel = serviceVesselFacadeRemote.find(submitData.getNoPpkb());
                UcDeliveryService ucDeliveryService = uncontainerizedService.getUcDeliveryService();
                Boolean isHasEnteredGate = Boolean.FALSE;

                if (ucDeliveryService != null 
                        && uncontainerizedService.getIsShifting().equals("FALSE")
                        && uncontainerizedService.getIsTranshipment().equals("FALSE")) {
                    UcGatedeliveryService ucGatedeliveryService = ucGatedeliveryServiceFacadeRemote.findNotDeliveredYetByJobSlip(ucDeliveryService.getJobslip());
                    isHasEnteredGate = ucGatedeliveryService != null;
                }
                if (submitData.getIsTruckLoosing() != null 
                        && Boolean.parseBoolean(submitData.getIsTruckLoosing()) == true ) {
                    uncontainerizedService.setTruckLoosing("TRUE");
                } else {
                    uncontainerizedService.setTruckLoosing("FALSE");
                }
                
                uncontainerizedService.setStatus("STV");
                uncontainerizedService.setPlacementDate(start);

                if (submitData.getCcCode().equals(CRANE_SEA)) {
                    uncontainerizedService.setCrane("S");
                } else {
                    uncontainerizedService.setCrane("L");
                }

                if (uncontainerizedService.getTruckLoosing().equals("TRUE")) {
                    Date now = new Date();
                    uncontainerizedService.setPlacementDate(now);

                    if (isHasEnteredGate) {
                        uncontainerizedService.setIsDelivery("TRUE");
                    } else {
                        uncontainerizedService.setStatus("CY");
                    }
                } else {
                    uncontainerizedService.setIsDelivery("FALSE");

                    MasterEquipment haulageTruckMasterEquipment = masterEquipmentFacadeRemote.find(submitData.getHtCode());
                    MasterOperator haulageTruckMasterOperator = masterOperatorFacadeRemote.find(submitData.getHtOperatorCode());

                    Equipment haulageTrucking = new Equipment();
                    haulageTrucking.setPlanningVessel(serviceVessel.getPlanningVessel());
                    haulageTrucking.setMasterEquipment(haulageTruckMasterEquipment);
                    haulageTrucking.setStartTime(start);
                    haulageTrucking.setEquipmentActCode("H/T");
                    haulageTrucking.setBlNo(submitData.getBlNo());
                    haulageTrucking.setMasterOperator(haulageTruckMasterOperator);

                    if (uncontainerizedService.getIsShifting().equals("TRUE")) {
                        haulageTrucking.setOperation("SHIFTING-TOCY");
                    } else if (uncontainerizedService.getIsTranshipment().equals("TRUE")) {
                        haulageTrucking.setOperation("TRANSDISCUC");
                    } else {
                        haulageTrucking.setOperation("DISCHARGEUC");
                    }

                    equipmentFacadeRemote.create(haulageTrucking);
                }
                
                MasterEquipment craneMasterEquipment = masterEquipmentFacadeRemote.find(submitData.getCcCode());
                MasterOperator craneMasterOperator = masterOperatorFacadeRemote.find(submitData.getCcOperatorCode());

                Equipment stevedoring = new Equipment();
                stevedoring.setPlanningVessel(serviceVessel.getPlanningVessel());
                stevedoring.setMasterEquipment(craneMasterEquipment);
                stevedoring.setStartTime(start);
                stevedoring.setEndTime(end);
                stevedoring.setEquipmentActCode("STEVEDORING");
                stevedoring.setBlNo(submitData.getBlNo());
                stevedoring.setMasterOperator(craneMasterOperator);

                if (uncontainerizedService.getIsShifting().equals("TRUE")) {
                    stevedoring.setOperation("SHIFTING-TOCY");
                } else if (uncontainerizedService.getIsTranshipment().equals("TRUE")) {
                    stevedoring.setOperation("TRANSDISCUC");
                } else {
                    stevedoring.setOperation("DISCHARGEUC");
                }

                equipmentFacadeRemote.create(stevedoring);
                uncontainerizedServiceFacadeRemote.edit(uncontainerizedService);

                if (uncontainerizedService.getIsShifting().equals("TRUE")) {
                    UcShiftingService ucShiftingService = ucShiftingServiceFacadeRemote.findByNoPpkbAndBlNo(uncontainerizedService.getNoPpkb(), uncontainerizedService.getBlNo());

                    if (ucShiftingService == null) {
                        ucShiftingService = new UcShiftingService();
                    }

                    ucShiftingService.setNoPpkb(uncontainerizedService.getNoPpkb());
                    ucShiftingService.setBlNo(uncontainerizedService.getBlNo());
                    ucShiftingService.setOperation(uncontainerizedService.getOperation());
                    ucShiftingService.setCrane(uncontainerizedService.getCrane());
                    ucShiftingService.setIsPaid("TRUE");
                    ucShiftingService.setIsPlanning("TRUE");
                    ucShiftingService.setIsLanded("FALSE");
                    ucShiftingService.setIsReshipping("FALSE");
                    ucShiftingService.setShiftTo(uncontainerizedService.getShiftTo());

                    int id = equipmentFacadeRemote.findIdEquipmentByAllBlNo(stevedoring.getMasterEquipment().getEquipCode(), stevedoring.getMasterOperator().getOptCode(), stevedoring.getBlNo(), uncontainerizedService.getNoPpkb(), stevedoring.getStartTime(), stevedoring.getEndTime(), stevedoring.getEquipmentActCode(), stevedoring.getOperation());
                    ucShiftingService.setIdEquipment(id);
                    ucShiftingServiceFacadeRemote.edit(ucShiftingService);
                }

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
