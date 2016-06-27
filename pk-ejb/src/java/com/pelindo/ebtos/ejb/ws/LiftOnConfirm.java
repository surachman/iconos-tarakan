/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.ejb.ws;

import com.pelindo.ebtos.ejb.facade.remote.MasterEquipmentFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterOperatorFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterYardFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningContLoadFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningShiftDischargeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceShiftingFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceVesselFacadeRemote;
import com.pelindo.ebtos.ejb.remote.LiftOnOperationRemote;
import com.pelindo.ebtos.ejb.ws.model.Container;
import com.pelindo.ebtos.ejb.ws.model.LiftOnConfirmInitialData;
import com.pelindo.ebtos.ejb.ws.model.LiftOnConfirmSubmitData;
import com.pelindo.ebtos.ejb.ws.model.IMessageable;
import com.pelindo.ebtos.ejb.ws.model.Message;
import com.pelindo.ebtos.exception.ContainerNotMovableException;
import com.pelindo.ebtos.exception.TimeSelectionNotValidException;
import com.pelindo.ebtos.model.db.Equipment;
import com.pelindo.ebtos.model.db.PlanningContLoad;
import com.pelindo.ebtos.model.db.PlanningShiftDischarge;
import com.pelindo.ebtos.model.db.ServiceShifting;
import com.pelindo.ebtos.model.db.ServiceVessel;
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
public class LiftOnConfirm {
    private static final String LOAD = "Load";
    private static final String SHIFT_TO_CY = "Shifting To Cy";
    private static final String TRANSHIPMENT_LOAD = "Transhipment";

    @EJB
    private MasterEquipmentFacadeRemote masterEquipmentFacadeRemote;
    @EJB
    private MasterOperatorFacadeRemote masterOperatorFacadeRemote;
    @EJB
    private PlanningContLoadFacadeRemote planningContLoadFacadeRemote;
    @EJB
    private ServiceVesselFacadeRemote serviceVesselFacadeRemote;
    @EJB
    private ServiceShiftingFacadeRemote serviceShiftingFacadeRemote;
    @EJB
    private LiftOnOperationRemote liftOnOperationRemote;
    @EJB
    private PlanningShiftDischargeFacadeRemote planningShiftDischargeFacadeRemote;
    @EJB
    private MasterYardFacadeRemote masterYardFacadeRemote;

    @WebMethod(operationName = "prepareLiftOnConfirmUI")
    public LiftOnConfirmInitialData prepareLiftOnConfirmUI() {
        LiftOnConfirmInitialData liftOnConfirm = null;

        try {
            List<Object> ttCodes = masterEquipmentFacadeRemote.findTango4HHT();
            List<Object> htCodes = masterEquipmentFacadeRemote.findHeadTruck4HHT();
            List<Object[]> operators = masterOperatorFacadeRemote.findMasterOperators4HHT();

            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            String serverTime = format.format(new Date());

            liftOnConfirm = new LiftOnConfirmInitialData(IMessageable.OK, "Data found!");
            liftOnConfirm.setServerTime(serverTime);
            liftOnConfirm.setTtCodes(ttCodes);
            liftOnConfirm.setHtCodes(htCodes);
            liftOnConfirm.setOperators(operators);
        } catch (RuntimeException re) {
            liftOnConfirm = new LiftOnConfirmInitialData(IMessageable.ERROR, "Internal error!");
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", re);
        }

        return liftOnConfirm;
    }

    @WebMethod(operationName = "findLiftableOnContainer")
    public Container findLiftableOnContainer(@WebParam(name = "contNo") String contNo){
        Container container = null;

        try {
            Object[] result = planningContLoadFacadeRemote.findLiftableOnContainer(contNo);

            if (result != null) {
                Boolean isTranshipment = (Boolean) result[11];
                Boolean isShifting = (Boolean) result[12];

                container = new Container(IMessageable.OK, "Data found!");
                container.setLoadVessel((String) result[2]);
                container.setSize(((Integer) result[7]).shortValue());
                container.setStatus((String) result[8]);
                
                if ((Boolean) result[9] == true) {
                    container.setOverSize("TRUE");
                } else {
                    container.setOverSize("FALSE");
                }
                
                if ((Boolean) result[10] == true) {
                    container.setDangerous("TRUE");
                } else {
                    container.setDangerous("FALSE");
                }
                container.setCondition((String) result[13]);
                container.setYardBlock((String) result[3]);
                container.setYardSlot(((Integer) result[4]).shortValue());
                container.setYardRow(((Integer) result[5]).shortValue());
                container.setYardTier(((Integer) result[6]).shortValue());

                if (isTranshipment) {
                    container.setService(TRANSHIPMENT_LOAD);
                } else if (isShifting) {
                    container.setService(SHIFT_TO_CY);
                } else {
                    container.setService(LOAD);
                }
            } else {
                container = new Container(IMessageable.ERROR, "Data not found!");
            }
        } catch (RuntimeException re) {
            container = new Container(IMessageable.ERROR, "Internal error!");
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", re);
        }

        return container;
    }

    @WebMethod(operationName = "liftOnConfirm")
    public Message liftOnConfirm(@WebParam(name = "submitData") LiftOnConfirmSubmitData submitData) {
        Message message;

        try {
            UserUtil.setCurrentUser(submitData.getCreatedBy());
            Object[] result = planningContLoadFacadeRemote.findLiftableOnContainer(submitData.getContNo());

            if (result != null) {
                ServiceVessel serviceVessel = serviceVesselFacadeRemote.find(result[1]);
                MasterEquipment tangoMasterEquipment = masterEquipmentFacadeRemote.find(submitData.getTtCode());
                MasterOperator tangoOperator = masterOperatorFacadeRemote.find(submitData.getTtOperatorCode());
                MasterEquipment haulageTruckMasterEquipment = masterEquipmentFacadeRemote.find(submitData.getHtCode());
                MasterOperator haulageTruckOperator = masterOperatorFacadeRemote.find(submitData.getHtOperatorCode());
                Date startTime = ParseDate.changeDate(submitData.getStartTime());
                Date endTime = ParseDate.changeDate(submitData.getEndTime());
                MasterYard block = masterYardFacadeRemote.find(((String) result[3]).toUpperCase());
                int slot = (Integer) result[4];
                int row = (Integer) result[5];
                int tier = (Integer) result[6];
                Boolean isTranshipment = (Boolean) result[11];
                Boolean isShifting = (Boolean) result[12];

                if (((submitData.getService().equals(LOAD) && !isTranshipment) || (submitData.getService().equals(TRANSHIPMENT_LOAD) && isTranshipment) && !isShifting)) {
                    PlanningContLoad planningContLoad = planningContLoadFacadeRemote.findByNoPpkbAndContNoExcludeCancelLoading(serviceVessel.getNoPpkb(), (String) result[0]);
                    planningContLoad.setMasterYard(block);
                    planningContLoad.setYSlot((short) slot);
                    planningContLoad.setYRow((short) row);
                    planningContLoad.setYTier((short) tier);

                    Equipment liftingOn = new Equipment();
                    liftingOn.setMasterEquipment(tangoMasterEquipment);
                    liftingOn.setMasterOperator(tangoOperator);
                    liftingOn.setStartTime(startTime);
                    liftingOn.setEndTime(endTime);

                    Equipment haulageTrucking = new Equipment();
                    haulageTrucking.setMasterEquipment(haulageTruckMasterEquipment);
                    haulageTrucking.setMasterOperator(haulageTruckOperator);

                    liftOnOperationRemote.handleLiftOnConfirmLoadContainer(serviceVessel, planningContLoad, liftingOn, haulageTrucking);
                    message = new Message(IMessageable.OK, "Succeed!");
                } else if (submitData.getService().equals(SHIFT_TO_CY) && isShifting) {
                    ServiceShifting serviceShifting = serviceShiftingFacadeRemote.findByNoPpkbAndContNo(serviceVessel.getNoPpkb(), (String) result[0]);
                    PlanningShiftDischarge planningShiftDischarge = serviceShifting.getPlanningShiftDischarge();
                    planningShiftDischarge.setMasterYard(block);
                    planningShiftDischarge.setYSlot((short) slot);
                    planningShiftDischarge.setYRow((short) row);
                    planningShiftDischarge.setYTier((short) tier);

                    if (planningShiftDischarge == null) {
                        planningShiftDischarge = planningShiftDischargeFacadeRemote.findByPpkbAndContNo(serviceShifting.getServiceVessel().getNoPpkb(), serviceShifting.getContNo());
                    }

                    Equipment liftingOn = new Equipment();
                    liftingOn.setMasterEquipment(tangoMasterEquipment);
                    liftingOn.setMasterOperator(tangoOperator);
                    liftingOn.setStartTime(startTime);
                    liftingOn.setEndTime(endTime);

                    Equipment haulageTrucking = new Equipment();
                    haulageTrucking.setMasterEquipment(haulageTruckMasterEquipment);
                    haulageTrucking.setMasterOperator(haulageTruckOperator);

                    liftOnOperationRemote.handleLiftOnConfirmShiftingContainer(planningShiftDischarge, serviceShifting, liftingOn, haulageTrucking);
                    message = new Message(IMessageable.OK, "Succeed!");
                } else {
                    throw new RuntimeException(String.format("Service type is not valid! [service=%s,isShifting=%s,isTranshipment=%s]", submitData.getService(), isShifting, isTranshipment));
                }
            } else {
                message = new Message(IMessageable.ERROR, "Data not found!");
            }
        } catch (TimeSelectionNotValidException ex) {
            message = new Message(IMessageable.ERROR, "End time must be greater than start time!");
        } catch (ContainerNotMovableException ex) {
            message = new Message(IMessageable.ERROR, "Cant move container with current state!");
        } catch (RuntimeException re) {
            message = new Message(IMessageable.ERROR, "Internal error!");
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", re);
        }
        
        return message;
    }
}
