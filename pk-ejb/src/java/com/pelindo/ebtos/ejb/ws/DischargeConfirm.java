/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.ejb.ws;

import com.qtasnim.util.ParseDate;
import com.pelindo.ebtos.ejb.facade.remote.CancelLoadingServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.EquipmentFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterContDamageFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterEquipmentFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterOperatorFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningVesselFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceContDischargeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceContTranshipmentFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceGateDeliveryFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceShiftingFacadeRemote;
import com.pelindo.ebtos.ejb.remote.DischargeConfirmOperationRemote;
import com.pelindo.ebtos.ejb.ws.model.Container;
import com.pelindo.ebtos.ejb.ws.model.DischargeConfirmInitialData;
import com.pelindo.ebtos.ejb.ws.model.DischargeConfirmSubmitData;
import com.pelindo.ebtos.ejb.ws.model.IMessageable;
import com.pelindo.ebtos.ejb.ws.model.Message;
import com.pelindo.ebtos.exception.TimeSelectionNotValidException;
import com.pelindo.ebtos.model.db.CancelLoadingService;
import com.pelindo.ebtos.model.db.Equipment;
import com.pelindo.ebtos.model.db.ServiceContDischarge;
import com.pelindo.ebtos.model.db.ServiceContTranshipment;
import com.pelindo.ebtos.model.db.ServiceGateDelivery;
import com.pelindo.ebtos.model.db.ServiceShifting;
import com.pelindo.ebtos.model.db.master.MasterContDamage;
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
public class DischargeConfirm {
    private static final String DISCHARGE = "Discharge";
    private static final String CANCEL_LOADING = "Cancel Loading";
    private static final String SHIFT_TO_CY = "Shifting To Cy";
    private static final String TRANSHIPMENT_DISCHARGE = "Transhipment";
    private static final String CRANE_SEA = "EQ000";

    @EJB
    private PlanningVesselFacadeRemote planningVesselFacade;
    @EJB
    private MasterEquipmentFacadeRemote masterEquipmentFacadeRemote;
    @EJB
    private MasterOperatorFacadeRemote masterOperatorFacadeRemote;
    @EJB
    private ServiceContDischargeFacadeRemote serviceContDischargeFacadeRemote;
    @EJB
    private ServiceShiftingFacadeRemote serviceShiftingFacadeRemote;
    @EJB
    private ServiceContTranshipmentFacadeRemote serviceContTranshipmentFacadeRemote;
    @EJB
    private EquipmentFacadeRemote equipmentFacadeRemote;
    @EJB
    private MasterContDamageFacadeRemote masterContDamageFacadeRemote;
    @EJB
    private CancelLoadingServiceFacadeRemote cancelLoadingServiceFacadeRemote;
    @EJB
    private ServiceGateDeliveryFacadeRemote serviceGateDeliveryFacadeRemote;
    @EJB
    private DischargeConfirmOperationRemote dischargeConfirmOperationRemote;

    @WebMethod(operationName = "prepareDischargeConfirmUI")
    public DischargeConfirmInitialData prepareDischargeConfirmUI() {
        DischargeConfirmInitialData dischargeConfirm = null;

        try {
            List<Object> ccCodes = masterEquipmentFacadeRemote.findCrane4HHT();
            List<Object> htCodes = masterEquipmentFacadeRemote.findHeadTruck4HHT();
            List<Object[]> operators = masterOperatorFacadeRemote.findMasterOperators4HHT();
            List<Object[]> vessels = planningVesselFacade.findByStatus4HHT("Servicing");
            List<Object[]> masterContDamages = masterContDamageFacadeRemote.findMasterContDamages();

            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            String serverTime = format.format(new Date());

            dischargeConfirm = new DischargeConfirmInitialData(IMessageable.OK, "Data found!");
            dischargeConfirm.setServerTime(serverTime);
            dischargeConfirm.setCcCodes(ccCodes);
            dischargeConfirm.setHtCodes(htCodes);
            dischargeConfirm.setOperators(operators);
            dischargeConfirm.setVessels(vessels);
            dischargeConfirm.setContDamage(masterContDamages);

        } catch (RuntimeException re) {
            dischargeConfirm = new DischargeConfirmInitialData(IMessageable.ERROR, "Internal error!");
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", re);
        }

        return dischargeConfirm;
    }

    @WebMethod(operationName = "findDischargeContainer")
    public Container findDischargeContainer(@WebParam(name = "noPpkb") String noPpkb, @WebParam(name = "contNo") String contNo){
        Container container = null;
        
        try {
            ServiceContDischarge serviceContDischarge = serviceContDischargeFacadeRemote.findDischargableContainer(noPpkb, contNo);
            
            if (serviceContDischarge != null) {
                container = new Container(IMessageable.OK, "Data found!");
                container.setNoPpkb(serviceContDischarge.getServiceVessel().getNoPpkb());
                container.setContNo(serviceContDischarge.getContNo());
                container.setVesselBay(serviceContDischarge.getVBay());
                container.setVesselRow(serviceContDischarge.getVRow());
                container.setVesselTier(serviceContDischarge.getVTier());
                container.setSize(serviceContDischarge.getContSize());
                container.setStatus(serviceContDischarge.getContStatus());
                container.setOverSize(serviceContDischarge.getOverSize());
                container.setDangerous(serviceContDischarge.getDangerous());
                container.setService(DISCHARGE);
            } else {
                ServiceShifting serviceShifting = serviceShiftingFacadeRemote.findContainerShiftingByPositionAndShiftDestination(noPpkb, contNo, "01", "TOCY");

                if (serviceShifting != null) {
                    container = new Container(IMessageable.OK, "Data found!");
                    container.setNoPpkb(serviceShifting.getServiceVessel().getNoPpkb());
                    container.setContNo(serviceShifting.getContNo());
                    container.setVesselBay(serviceShifting.getVBay());
                    container.setVesselRow(serviceShifting.getVRow());
                    container.setVesselTier(serviceShifting.getVTier());
                    container.setSize(serviceShifting.getContSize());
                    container.setStatus(serviceShifting.getContStatus());
                    container.setOverSize(serviceShifting.getOverSize());
                    container.setDangerous(serviceShifting.getDg());
                    container.setService(SHIFT_TO_CY);
                } else {
                    ServiceContTranshipment serviceContTranshipment = serviceContTranshipmentFacadeRemote.findByTranshipmentContainerByPosition(noPpkb, contNo, "01");

                    if (serviceContTranshipment != null) {
                        container = new Container(IMessageable.OK, "Data found!");
                        container.setNoPpkb(serviceContTranshipment.getServiceVessel().getNoPpkb());
                        container.setContNo(serviceContTranshipment.getContNo());
                        container.setVesselBay(serviceContTranshipment.getVBay());
                        container.setVesselRow(serviceContTranshipment.getVRow());
                        container.setVesselTier(serviceContTranshipment.getVTier());
                        container.setSize(serviceContTranshipment.getContSize());
                        container.setStatus(serviceContTranshipment.getContStatus());
                        container.setOverSize(serviceContTranshipment.getOverSize());
                        container.setDangerous(serviceContTranshipment.getDangerous());
                        container.setService(TRANSHIPMENT_DISCHARGE);
                    } else {
                        CancelLoadingService cancelLoadingService = cancelLoadingServiceFacadeRemote.findDischargableContainerByContNoAndPpkb(noPpkb, contNo);

                        if (cancelLoadingService != null) {
                            container = new Container(IMessageable.OK, "Data found!");
                            container.setNoPpkb(cancelLoadingService.getPlanningVessel().getNoPpkb());
                            container.setContNo(cancelLoadingService.getContNo());
                            container.setVesselBay(cancelLoadingService.getVesselBay());
                            container.setVesselRow(cancelLoadingService.getVesselRow());
                            container.setVesselTier(cancelLoadingService.getVesselTier());
                            container.setSize(cancelLoadingService.getContSize());
                            container.setStatus(cancelLoadingService.getContStatus());
                            container.setOverSize(cancelLoadingService.getOverSize());
                            container.setDangerous(cancelLoadingService.getDg());
                            container.setService(CANCEL_LOADING);
                        } else {
                            container = new Container(IMessageable.ERROR, "Data not found!");
                        }
                    }
                }
            }
            
        } catch (RuntimeException re) {
            container = new Container(IMessageable.ERROR, "Internal error!");
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", re);
        }

        return container;
    }

    @WebMethod(operationName = "dischargeStevedoringConfirm")
    public Message dischargeStevedoringConfirm(@WebParam(name = "submitData") DischargeConfirmSubmitData submitData) {
        Message message;

        try {
            UserUtil.setCurrentUser(submitData.getCreatedBy());
            MasterEquipment containerCraneMasterEquipment = masterEquipmentFacadeRemote.find(submitData.getCcCode());
            MasterOperator containerCraneOperator = masterOperatorFacadeRemote.find(submitData.getCcOperatorCode());
            MasterEquipment haulageTruckMasterEquipment = masterEquipmentFacadeRemote.find(submitData.getHtCode());
            MasterOperator haulageTruckOperator = masterOperatorFacadeRemote.find(submitData.getHtOperatorCode());
            MasterContDamage masterContDamage = masterContDamageFacadeRemote.find(submitData.getContDamage());
            Date startTime = ParseDate.changeDate(submitData.getStartTime());
            Date endTime = ParseDate.changeDate(submitData.getEndTime());
            
            if (submitData.getService().equals(DISCHARGE)) {
                ServiceContDischarge serviceContDischarge = serviceContDischargeFacadeRemote.findDischargableContainer(submitData.getNoPpkb(), submitData.getContNo());

                if (serviceContDischarge != null) {
                    if (Boolean.parseBoolean(submitData.getUseSling()) == true) {
                        serviceContDischarge.setIsSeling("TRUE");
                    } else {
                        serviceContDischarge.setIsSeling("FALSE");
                    }
                        
                    
                    serviceContDischarge.setMasterContDamage(masterContDamage);

                    if (submitData.getCcCode().equals(CRANE_SEA)) {
                        serviceContDischarge.setCrane("S");
                    } else {
                        serviceContDischarge.setCrane("L");
                    }
                    if(containerCraneMasterEquipment != null){
                        serviceContDischarge.setEquipmentGroup(containerCraneMasterEquipment.getEquipmentGroup());
                    }
                    Equipment stevedoring = new Equipment();
                    stevedoring.setMasterEquipment(containerCraneMasterEquipment);
                    stevedoring.setStartTime(startTime);
                    stevedoring.setEndTime(endTime);
                    stevedoring.setMasterOperator(containerCraneOperator);

                    Equipment haulageTruck = new Equipment();
                    haulageTruck.setMasterEquipment(haulageTruckMasterEquipment);
                    haulageTruck.setMasterOperator(haulageTruckOperator);

                    dischargeConfirmOperationRemote.handleDischargeConfirmContainer(serviceContDischarge, stevedoring, haulageTruck);
                    message = new Message(IMessageable.OK, "Success! (" + serviceContDischarge.getMasterYard().getBlock() + " " + serviceContDischarge.getYSlot() + ")");
                } else {
                    message = new Message(IMessageable.ERROR, "Data not found!");
                }
            } else if (submitData.getService().equals(SHIFT_TO_CY)) {
                ServiceShifting serviceShifting = serviceShiftingFacadeRemote.findContainerShiftingByPositionAndShiftDestination(submitData.getNoPpkb(), submitData.getContNo(), "01", "TOCY");

                if (serviceShifting != null) {
                    serviceShifting.setMasterContDamage(masterContDamage);
                    if (Boolean.parseBoolean(submitData.getUseSling()) == true) {
                        serviceShifting.setIsSling("TRUE");
                    } else {
                        serviceShifting.setIsSling("FALSE");
                    }

                    if (submitData.getCcCode().equals(CRANE_SEA)) {
                        serviceShifting.setCrane("S");
                    } else {
                        serviceShifting.setCrane("L");
                    }

                    Equipment stevedoring = new Equipment();
                    stevedoring.setMasterEquipment(containerCraneMasterEquipment);
                    stevedoring.setStartTime(startTime);
                    stevedoring.setEndTime(endTime);
                    stevedoring.setMasterOperator(containerCraneOperator);

                    Equipment haulageTruck = new Equipment();
                    haulageTruck.setMasterEquipment(haulageTruckMasterEquipment);
                    haulageTruck.setMasterOperator(haulageTruckOperator);

                    dischargeConfirmOperationRemote.handleDischargeConfirmShiftingContainer(serviceShifting, stevedoring, haulageTruck);
                    message = new Message(IMessageable.OK, "Success! (" + serviceShifting.getPlanningShiftDischarge().getMasterYard().getBlock() + " " + serviceShifting.getPlanningShiftDischarge().getYSlot() + ")");
                } else {
                    message = new Message(IMessageable.ERROR, "Data not found!");
                }
            } else if (submitData.getService().equals(TRANSHIPMENT_DISCHARGE)) {
                ServiceContTranshipment serviceContTranshipment = serviceContTranshipmentFacadeRemote.findByTranshipmentContainerByPosition(submitData.getNoPpkb(), submitData.getContNo(), "01");
                
                if (serviceContTranshipment != null) {
                    serviceContTranshipment.setPosition("02");
                    if (Boolean.parseBoolean(submitData.getUseSling()) == true) {
                        serviceContTranshipment.setIsSling("TRUE");
                    }else {
                        serviceContTranshipment.setIsSling("FALSE");
                    }
                    serviceContTranshipment.setMasterContDamage(masterContDamage);

                    if (submitData.getCcCode().equals(CRANE_SEA)) {
                        serviceContTranshipment.setCrane("S");
                    } else {
                        serviceContTranshipment.setCrane("L");
                    }

                    Equipment stevedoring = new Equipment();
                    stevedoring.setContNo(submitData.getContNo());
                    stevedoring.setEquipmentActCode("STEVEDORING");
                    stevedoring.setOperation("TRANSDISCHARGE");
                    stevedoring.setMasterEquipment(containerCraneMasterEquipment);
                    stevedoring.setPlanningVessel(serviceContTranshipment.getServiceVessel().getPlanningVessel());
                    stevedoring.setStartTime(startTime);
                    stevedoring.setEndTime(endTime);
                    stevedoring.setMasterOperator(containerCraneOperator);

                    Equipment haulageTruck = new Equipment();
                    haulageTruck.setContNo(submitData.getContNo());
                    haulageTruck.setEquipmentActCode("H/T");
                    haulageTruck.setOperation("TRANSDISCHARGE");
                    haulageTruck.setMasterEquipment(haulageTruckMasterEquipment);
                    haulageTruck.setPlanningVessel(serviceContTranshipment.getServiceVessel().getPlanningVessel());
                    haulageTruck.setStartTime(endTime);
                    haulageTruck.setMasterOperator(haulageTruckOperator);

                    serviceContTranshipmentFacadeRemote.edit(serviceContTranshipment);
                    equipmentFacadeRemote.create(stevedoring);
                    equipmentFacadeRemote.create(haulageTruck);

                    message = new Message(IMessageable.OK, "Success! (" + serviceContTranshipment.getMasterYard().getBlock() + " " + serviceContTranshipment.getYSlot() + ")");
                } else {
                    message = new Message(IMessageable.ERROR, "Data not found!");
                }
            } else if (submitData.getService().equals(CANCEL_LOADING)) {
                CancelLoadingService cancelLoadingService = cancelLoadingServiceFacadeRemote.findDischargableContainerByContNoAndPpkb(submitData.getNoPpkb(), submitData.getContNo());

                if (cancelLoadingService != null) {
                    ServiceGateDelivery serviceGateDelivery = serviceGateDeliveryFacadeRemote.findNotDeliveredYetByJobSlip(cancelLoadingService.getJobSlip());
                    
                    if (serviceGateDelivery != null) {
                        cancelLoadingService.setTruckLosing("TRUE");
                    }

                    if (submitData.getCcCode().equals(CRANE_SEA)) {
                        cancelLoadingService.setCrane("S");
                    } else {
                        cancelLoadingService.setCrane("L");
                    }

                    cancelLoadingService.setMasterContDamage(masterContDamage);

                    Equipment stevedoring = new Equipment();
                    stevedoring.setMasterEquipment(containerCraneMasterEquipment);
                    stevedoring.setStartTime(startTime);
                    stevedoring.setEndTime(endTime);
                    stevedoring.setMasterOperator(containerCraneOperator);

                    Equipment haulageTruck = new Equipment();
                    haulageTruck.setMasterEquipment(haulageTruckMasterEquipment);
                    haulageTruck.setMasterOperator(haulageTruckOperator);

                    dischargeConfirmOperationRemote.handleDischargeConfirmCancelLoadingContainer(cancelLoadingService, stevedoring, haulageTruck);
                    message = new Message(IMessageable.OK, "Success! (" + cancelLoadingService.getMasterYard().getBlock() + " " + cancelLoadingService.getySlot() + ")");
                } else {
                    message = new Message(IMessageable.ERROR, "Data not found!");
                }
            } else {
                throw new RuntimeException(String.format("Service type is not valid! [service=%s]", submitData.getService()));
            }
        } catch (TimeSelectionNotValidException ex) {
            message = new Message(IMessageable.ERROR, "End time must be greater than start time!");
        } catch (RuntimeException re) {
            message = new Message(IMessageable.ERROR, "Internal error!");
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", re);
        }
        return message;
    }
}
