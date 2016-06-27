/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.ejb.ws;

import com.pelindo.ebtos.ejb.facade.remote.CancelLoadingServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterEquipmentFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterOperatorFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterYardFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningVesselFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceContDischargeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceContTranshipmentFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceShiftingFacadeRemote;
import com.pelindo.ebtos.ejb.remote.LiftOffOperationRemote;
import com.pelindo.ebtos.ejb.ws.model.Container;
import com.pelindo.ebtos.ejb.ws.model.LiftOffConfirmInitialData;
import com.pelindo.ebtos.ejb.ws.model.LiftOffConfirmSubmitData;
import com.pelindo.ebtos.ejb.ws.model.IMessageable;
import com.pelindo.ebtos.ejb.ws.model.Message;
import com.pelindo.ebtos.exception.LocationNotAvailableException;
import com.pelindo.ebtos.exception.TimeSelectionNotValidException;
import com.pelindo.ebtos.model.db.CancelLoadingService;
import com.pelindo.ebtos.model.db.Equipment;
import com.pelindo.ebtos.model.db.PlanningShiftDischarge;
import com.pelindo.ebtos.model.db.PlanningVessel;
import com.pelindo.ebtos.model.db.ServiceContDischarge;
import com.pelindo.ebtos.model.db.ServiceContTranshipment;
import com.pelindo.ebtos.model.db.ServiceShifting;
import com.pelindo.ebtos.model.db.master.MasterEquipment;
import com.pelindo.ebtos.model.db.master.MasterOperator;
import com.pelindo.ebtos.model.db.master.MasterYard;
import com.pelindo.ebtos.security.UserUtil;
import com.qtasnim.util.ParseDate;
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
public class LiftOffConfirm {
    private static final String DISCHARGE = "Discharge";
    private static final String SHIFT_TO_CY = "Shifting To Cy";
    private static final String TRANSHIPMENT_DISCHARGE = "Transhipment";
    private static final String CANCEL_LOADING = "Cancel Loading";

    @EJB
    private MasterEquipmentFacadeRemote masterEquipmentFacadeRemote;
    @EJB
    private MasterOperatorFacadeRemote masterOperatorFacadeRemote;
    @EJB
    private ServiceShiftingFacadeRemote serviceShiftingFacadeRemote;
    @EJB
    private ServiceContDischargeFacadeRemote serviceContDischargeFacadeRemote;
    @EJB
    private ServiceContTranshipmentFacadeRemote serviceContTranshipmentFacadeRemote;
    @EJB
    private MasterYardFacadeRemote masterYardFacadeRemote;
    @EJB
    private PlanningVesselFacadeRemote planningVesselFacadeRemote;
    @EJB
    private CancelLoadingServiceFacadeRemote cancelLoadingServiceFacadeRemote;
    @EJB
    private LiftOffOperationRemote liftOffOperationRemote;

    @WebMethod(operationName = "prepareLiftOffConfirmUI")
    public LiftOffConfirmInitialData prepareLiftOffConfirmUI() {
        LiftOffConfirmInitialData liftOffConfirm = null;

        try {
            List<Object> ttCodes = masterEquipmentFacadeRemote.findTango4HHT();
            List<Object[]> operators = masterOperatorFacadeRemote.findMasterOperators4HHT();

            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            String serverTime = format.format(new Date());

            liftOffConfirm = new LiftOffConfirmInitialData(IMessageable.OK, "Data found!");
            liftOffConfirm.setServerTime(serverTime);
            liftOffConfirm.setTtCodes(ttCodes);
            liftOffConfirm.setOperators(operators);
        } catch (RuntimeException re) {
            liftOffConfirm = new LiftOffConfirmInitialData(IMessageable.ERROR, "Internal error!");
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", re);
        }

        return liftOffConfirm;
    }

    @WebMethod(operationName = "findLiftableOffContainer")
    public Container findLiftableOffContainer(@WebParam(name = "contNo") String contNo){
        Container container = null;

        try {
            ServiceContDischarge serviceContDischarge = serviceContDischargeFacadeRemote.findByContNoAndPosition(contNo, "02");

            if (serviceContDischarge != null) {
                container = new Container(IMessageable.OK, "Data found!");
                container.setContNo(serviceContDischarge.getContNo());
                container.setDischargeVessel(serviceContDischarge.getServiceVessel().getPlanningVessel().getPreserviceVessel().getMasterVessel().getName());
                container.setSize(serviceContDischarge.getContSize());
                container.setStatus(serviceContDischarge.getContStatus());
                container.setOverSize(serviceContDischarge.getOverSize());
                container.setDangerous(serviceContDischarge.getDangerous());
                container.setService(DISCHARGE);
                container.setPlanningYardBlock(serviceContDischarge.getMasterYard().getBlock());
                container.setPlanningYardSlot(serviceContDischarge.getYSlot());
                container.setPlanningYardRow(serviceContDischarge.getYRow());
                container.setPlanningYardTier(serviceContDischarge.getYTier());

                if (serviceContDischarge.getMasterContDamage() != null) {
                    container.setCondition(serviceContDischarge.getMasterContDamage().getName());
                }
            } else {
                ServiceContTranshipment serviceContTranshipment = serviceContTranshipmentFacadeRemote.findByTranshipmentByContNoAndPosition(contNo, "02");

                if (serviceContTranshipment != null) {
                    container = new Container(IMessageable.OK, "Data found!");
                    container.setContNo(serviceContTranshipment.getContNo());
                    container.setDischargeVessel(serviceContTranshipment.getServiceVessel().getPlanningVessel().getPreserviceVessel().getMasterVessel().getName());
                    container.setSize(serviceContTranshipment.getContSize());
                    container.setStatus(serviceContTranshipment.getContStatus());
                    container.setOverSize(serviceContTranshipment.getOverSize());
                    container.setDangerous(serviceContTranshipment.getDangerous());
                    container.setService(TRANSHIPMENT_DISCHARGE);
                    container.setPlanningYardBlock(serviceContTranshipment.getMasterYard().getBlock());
                    container.setPlanningYardSlot(serviceContTranshipment.getYSlot());
                    container.setPlanningYardRow(serviceContTranshipment.getYRow());
                    container.setPlanningYardTier(serviceContTranshipment.getYTier());

                    if (serviceContTranshipment.getMasterContDamage() != null) {
                        container.setCondition(serviceContTranshipment.getMasterContDamage().getName());
                    }
                } else {
                    ServiceShifting serviceShifting = serviceShiftingFacadeRemote.findContainerShiftingByContNoPositionAndShiftDestination(contNo, "02", "TOCY");

                    if (serviceShifting != null) {
                        container = new Container(IMessageable.OK, "Data found!");
                        container.setContNo(serviceShifting.getContNo());
                        container.setDischargeVessel(serviceShifting.getServiceVessel().getPlanningVessel().getPreserviceVessel().getMasterVessel().getName());
                        container.setSize(serviceShifting.getContSize());
                        container.setStatus(serviceShifting.getContStatus());
                        container.setOverSize(serviceShifting.getOverSize());
                        container.setDangerous(serviceShifting.getDg());
                        container.setService(SHIFT_TO_CY);
                        container.setPlanningYardBlock(serviceShifting.getPlanningShiftDischarge().getMasterYard().getBlock());
                        container.setPlanningYardSlot(serviceShifting.getPlanningShiftDischarge().getYSlot());
                        container.setPlanningYardRow(serviceShifting.getPlanningShiftDischarge().getYRow());
                        container.setPlanningYardTier(serviceShifting.getPlanningShiftDischarge().getYTier());
                        
                        if (serviceShifting.getMasterContDamage() != null) {
                            container.setCondition(serviceShifting.getMasterContDamage().getName());
                        }
                    } else {
                        CancelLoadingService cancelLoadingService = cancelLoadingServiceFacadeRemote.findLiftableOffByContNo(contNo);

                        if (cancelLoadingService != null) {
                            PlanningVessel planningVessel = planningVesselFacadeRemote.find(cancelLoadingService.getPlanningVessel().getNoPpkb());

                            container = new Container(IMessageable.OK, "Data found!");
                            container.setContNo(cancelLoadingService.getContNo());
                            container.setDischargeVessel(planningVessel.getPreserviceVessel().getMasterVessel().getName());
                            container.setSize(cancelLoadingService.getContSize());
                            container.setStatus(cancelLoadingService.getContStatus());
                            container.setOverSize(cancelLoadingService.getOverSize());
                            container.setDangerous(cancelLoadingService.getDg());
                            container.setService(CANCEL_LOADING);
                            container.setPlanningYardBlock(cancelLoadingService.getMasterYard().getBlock());
                            container.setPlanningYardSlot(cancelLoadingService.getySlot());
                            container.setPlanningYardRow(cancelLoadingService.getyRow());
                            container.setPlanningYardTier(cancelLoadingService.getyTier());

                            if (serviceShifting.getMasterContDamage() != null) {
                                container.setCondition(cancelLoadingService.getMasterContDamage().getName());
                            }
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

    @WebMethod(operationName = "liftOffConfirm")
    public Message liftOffConfirm(@WebParam(name = "submitData") LiftOffConfirmSubmitData submitData) {
        Message message;
        
        try {
            UserUtil.setCurrentUser(submitData.getCreatedBy());
            ServiceContDischarge serviceContDischarge = serviceContDischargeFacadeRemote.findByContNoAndPosition(submitData.getContNo(), "02");

            short slot = Short.parseShort(submitData.getYardSlot());
            short row = Short.parseShort(submitData.getYardRow());
            short tier = Short.parseShort(submitData.getYardTier());
            MasterYard yard = masterYardFacadeRemote.find(submitData.getYardBlock());
            MasterEquipment tangoMasterEquipment = masterEquipmentFacadeRemote.find(submitData.getTtCode());
            MasterOperator tangoOperator = masterOperatorFacadeRemote.find(submitData.getTtOperatorCode());
            Date startTime = ParseDate.changeDate(submitData.getStartTime());
            Date endTime = ParseDate.changeDate(submitData.getEndTime());

            if (serviceContDischarge != null && submitData.getService().equals(DISCHARGE)) {
                serviceContDischarge.setMasterYard(yard);
                serviceContDischarge.setYSlot(slot);
                serviceContDischarge.setYRow(row);
                serviceContDischarge.setYTier(tier);

                Equipment liftingOff = new Equipment();
                liftingOff.setMasterEquipment(tangoMasterEquipment);
                liftingOff.setMasterOperator(tangoOperator);
                liftingOff.setStartTime(startTime);
                liftingOff.setEndTime(endTime);

                liftOffOperationRemote.handleLiftOffConfirmDischargeContainer(serviceContDischarge, liftingOff);
                message = new Message(IMessageable.OK, "Success!");
            } else {
                ServiceContTranshipment serviceContTranshipment = serviceContTranshipmentFacadeRemote.findByTranshipmentByContNoAndPosition(submitData.getContNo(), "02");

                if (serviceContTranshipment != null && submitData.getService().equals(TRANSHIPMENT_DISCHARGE)) {
                    serviceContTranshipment.setMasterYard(yard);
                    serviceContTranshipment.setYSlot(slot);
                    serviceContTranshipment.setYRow(row);
                    serviceContTranshipment.setYTier(tier);

                    Equipment liftingOff = new Equipment();
                    liftingOff.setMasterEquipment(tangoMasterEquipment);
                    liftingOff.setMasterOperator(tangoOperator);
                    liftingOff.setStartTime(startTime);
                    liftingOff.setEndTime(endTime);

                    liftOffOperationRemote.handleLiftOffConfirmTranshipmentContainer(serviceContTranshipment, liftingOff);
                    message = new Message(IMessageable.OK, "Success!");
                } else {
                    ServiceShifting serviceShifting = serviceShiftingFacadeRemote.findContainerShiftingByContNoPositionAndShiftDestination(submitData.getContNo(), "02", "TOCY");

                    if (serviceShifting != null && submitData.getService().equals(SHIFT_TO_CY)) {
                        PlanningShiftDischarge planningShiftDischarge = serviceShifting.getPlanningShiftDischarge();
                        planningShiftDischarge.setMasterYard(yard);
                        planningShiftDischarge.setYSlot(slot);
                        planningShiftDischarge.setYRow(row);
                        planningShiftDischarge.setYTier(tier);

                        Equipment liftingOff = new Equipment();
                        liftingOff.setMasterEquipment(tangoMasterEquipment);
                        liftingOff.setMasterOperator(tangoOperator);
                        liftingOff.setStartTime(startTime);
                        liftingOff.setEndTime(endTime);

                        liftOffOperationRemote.handleLiftOffConfirmShiftingContainer(serviceShifting, planningShiftDischarge, liftingOff);
                        message = new Message(IMessageable.OK, "Success!");
                    } else {
                        CancelLoadingService cancelLoadingService = cancelLoadingServiceFacadeRemote.findLiftableOffByContNo(submitData.getContNo());

                        if (cancelLoadingService != null && submitData.getService().equals(CANCEL_LOADING)) {
                            cancelLoadingService.setMasterYard(yard);
                            cancelLoadingService.setySlot(slot);
                            cancelLoadingService.setyRow(row);
                            cancelLoadingService.setyTier(tier);

                            Equipment liftingOff = new Equipment();
                            liftingOff.setMasterEquipment(tangoMasterEquipment);
                            liftingOff.setMasterOperator(tangoOperator);
                            liftingOff.setStartTime(startTime);
                            liftingOff.setEndTime(endTime);

                            liftOffOperationRemote.handleLiftOffConfirmCancelLoadingContainer(cancelLoadingService, liftingOff);
                            message = new Message(IMessageable.OK, "Success!");
                        } else {
                            message = new Message(IMessageable.ERROR, "Data not found!");
                        }
                    }
                }
            }
        } catch (LocationNotAvailableException ex) {
            message = new Message(IMessageable.ERROR, "Destination location is not available!");
        } catch (TimeSelectionNotValidException ex) {
            message = new Message(IMessageable.ERROR, "End Time value must be greater than Start Time!");
        } catch (RuntimeException re) {
            message = new Message(IMessageable.ERROR, "Internal error!");
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", re);
        }
        
        return message;
    }
}
