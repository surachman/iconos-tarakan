/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.ejb.ws;

import com.qtasnim.util.ParseDate;
import com.pelindo.ebtos.ejb.facade.remote.EquipmentFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterEquipmentFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterOperatorFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningContLoadFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningShiftDischargeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningVesselFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceContLoadFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceShiftingFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceVesselFacadeRemote;
import com.pelindo.ebtos.ejb.ws.model.Container;
import com.pelindo.ebtos.ejb.ws.model.LoadConfirmInitialData;
import com.pelindo.ebtos.ejb.ws.model.LoadConfirmSubmitData;
import com.pelindo.ebtos.ejb.ws.model.IMessageable;
import com.pelindo.ebtos.ejb.ws.model.Message;
import com.pelindo.ebtos.model.db.Equipment;
import com.pelindo.ebtos.model.db.PlanningContLoad;
import com.pelindo.ebtos.model.db.PlanningShiftDischarge;
import com.pelindo.ebtos.model.db.ServiceContLoad;
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
public class LoadConfirm {
    private static final String LOAD = "Load";
    private static final String SHIFT_TO_CY = "Shifting To Cy";
    private static final String TRANSHIPMENT_LOAD = "Transhipment";
    private static final String CRANE_SEA = "EQ000";

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
    @EJB
    private ServiceContLoadFacadeRemote serviceContLoadFacadeRemote;
    @EJB
    private ServiceVesselFacadeRemote serviceVesselFacadeRemote;
    @EJB
    private PlanningShiftDischargeFacadeRemote planningShiftDischargeFacadeRemote;
    @EJB
    private PlanningContLoadFacadeRemote planningContLoadFacadeRemote;

    @WebMethod(operationName = "prepareLoadConfirmUI")
    public LoadConfirmInitialData prepareLoadConfirmUI() {
        LoadConfirmInitialData initialData = null;

        try {
            List<Object> ccCodes = masterEquipmentFacadeRemote.findCrane4HHT();
            List<Object[]> operators = masterOperatorFacadeRemote.findMasterOperators4HHT();
            List<Object[]> vessels = planningVesselFacade.findByStatus4HHT("Servicing");

            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            String serverTime = format.format(new Date());

            initialData = new LoadConfirmInitialData(IMessageable.OK, "Data found!");
            initialData.setServerTime(serverTime);
            initialData.setCcCodes(ccCodes);
            initialData.setOperators(operators);
            initialData.setVessels(vessels);

        } catch (RuntimeException re) {
            initialData = new LoadConfirmInitialData(IMessageable.ERROR, "Internal error!");
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", re);
        }

        return initialData;
    }

    @WebMethod(operationName = "findLoadContainer")
    public Container findLoadContainer(@WebParam(name = "noPpkb") String noPpkb, @WebParam(name = "contNo") String contNo){
        Container container = null;

        try {
            ServiceContLoad serviceContLoad = serviceContLoadFacadeRemote.findByContNoAndPosition(noPpkb, contNo, "02");
            
            if (serviceContLoad != null) {
                container = new Container(IMessageable.OK, "Data found!");
                container.setNoPpkb(serviceContLoad.getServiceVessel().getNoPpkb());
                container.setContNo(serviceContLoad.getContNo());
                container.setSize(serviceContLoad.getContSize());
                container.setStatus(serviceContLoad.getContStatus());
                container.setOverSize(serviceContLoad.getOverSize());
                container.setDangerous(serviceContLoad.getDangerous());
                container.setVesselBay(serviceContLoad.getVBay());
                container.setVesselRow(serviceContLoad.getVRow());
                container.setVesselTier(serviceContLoad.getVTier());

                if (serviceContLoad.getIsTranshipment().equals("TRUE")) {
                    container.setService(TRANSHIPMENT_LOAD);
                } else {
                    container.setService(LOAD);
                }
            } else {
                ServiceShifting serviceShifting = serviceShiftingFacadeRemote.findByPositionAndContNo(noPpkb, contNo, "04");

                if (serviceShifting != null) {
                    container = new Container(IMessageable.OK, "Data found!");
                    container.setNoPpkb(serviceShifting.getServiceVessel().getNoPpkb());
                    container.setContNo(serviceShifting.getContNo());
                    container.setSize(serviceShifting.getContSize());
                    container.setStatus(serviceShifting.getContStatus());
                    container.setOverSize(serviceShifting.getOverSize());
                    container.setDangerous(serviceShifting.getDg());
                    container.setVesselBay(serviceShifting.getVBay());
                    container.setVesselRow(serviceShifting.getVRow());
                    container.setVesselTier(serviceShifting.getVTier());
                    container.setService(SHIFT_TO_CY);
                } else {
                    container = new Container(IMessageable.ERROR, "Data not found!");
                }
            }
            
        } catch (RuntimeException re) {
            container = new Container(IMessageable.ERROR, "Internal error!");
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", re);
        }

        return container;
    }

    @WebMethod(operationName = "loadStevedoringConfirm")
    public Message loadStevedoringConfirm(@WebParam(name = "submitData") LoadConfirmSubmitData submitData) {
        Message message = null;

        try {
            UserUtil.setCurrentUser(submitData.getCreatedBy());
            if (submitData.getService().equals(SHIFT_TO_CY)) {
                ServiceShifting serviceShifting = serviceShiftingFacadeRemote.findByPositionAndContNo(submitData.getNoPpkb(), submitData.getContNo(), "04");
                PlanningShiftDischarge planningShiftDischarge = planningShiftDischargeFacadeRemote.findByPpkbAndContNo(submitData.getNoPpkb(), submitData.getContNo());

                if (serviceShifting != null && planningShiftDischarge != null) {
                    MasterEquipment containerCraneMasterEquipment = masterEquipmentFacadeRemote.find(submitData.getCcCode());
                    MasterOperator containerCraneOperator = masterOperatorFacadeRemote.find(submitData.getCcOperatorCode());
                    ServiceVessel serviceVessel = serviceVesselFacadeRemote.find(submitData.getNoPpkb());
                    Date startTime = ParseDate.changeDate(submitData.getStartTime());
                    Date endTime = ParseDate.changeDate(submitData.getEndTime());

                    planningShiftDischarge.setVBay(Short.parseShort(submitData.getBay()));
                    planningShiftDischarge.setVRow(Short.parseShort(submitData.getRow()));
                    planningShiftDischarge.setVTier(Short.parseShort(submitData.getTier()));

                    serviceShifting.setVBay(Short.parseShort(submitData.getBay()));
                    serviceShifting.setVRow(Short.parseShort(submitData.getRow()));
                    serviceShifting.setVTier(Short.parseShort(submitData.getTier()));
                    serviceShifting.setPosition("05");

                    if (submitData.getCcCode().equals(CRANE_SEA)) {
                        serviceShifting.setCrane("S");
                    } else {
                        serviceShifting.setCrane("L");
                    }

                    Equipment haulageTrucking = equipmentFacadeRemote.findByEquipmentActCodeAndOperation(submitData.getNoPpkb(), submitData.getContNo(), "H/T", "LD-SHIFTING-CY");
                    haulageTrucking.setEndTime(startTime);

                    Equipment stevedoring = new Equipment();
                    stevedoring.setMasterEquipment(containerCraneMasterEquipment);
                    stevedoring.setContNo(submitData.getContNo());
                    stevedoring.setPlanningVessel(serviceVessel.getPlanningVessel());
                    stevedoring.setEquipmentActCode("STEVEDORING");
                    stevedoring.setOperation("LD-SHIFTING-CY");
                    stevedoring.setStartTime(startTime);
                    stevedoring.setEndTime(endTime);
                    stevedoring.setMasterOperator(containerCraneOperator);

                    serviceVessel.setLoad((short) (serviceVessel.getLoad() + 1));

                    PlanningContLoad planningContLoad = planningContLoadFacadeRemote.findByNoPpkbAndContNoExcludeCancelLoading(serviceVessel.getNoPpkb(), serviceShifting.getContNo());
                    planningContLoad.setPosition(PlanningContLoad.LOCATION_VESSEL);
                    
                    serviceShiftingFacadeRemote.edit(serviceShifting);
                    planningShiftDischargeFacadeRemote.edit(planningShiftDischarge);
                    equipmentFacadeRemote.create(stevedoring);
                    equipmentFacadeRemote.edit(haulageTrucking);
                    planningContLoadFacadeRemote.edit(planningContLoad);

                    message = new Message(IMessageable.OK, "Success!");
                } else {
                    message = new Message(IMessageable.ERROR, "Data not found!");
                }
            } else if (submitData.getService().equals(TRANSHIPMENT_LOAD) || submitData.getService().equals(LOAD)) {
                ServiceContLoad serviceContLoad = serviceContLoadFacadeRemote.findByContNoAndPosition(submitData.getNoPpkb(), submitData.getContNo(), "02");

                if (serviceContLoad != null) {
                    MasterEquipment containerCraneMasterEquipment = masterEquipmentFacadeRemote.find(submitData.getCcCode());
                    MasterOperator containerCraneOperator = masterOperatorFacadeRemote.find(submitData.getCcOperatorCode());
                    ServiceVessel serviceVessel = serviceVesselFacadeRemote.find(submitData.getNoPpkb());
                    Date startTime = ParseDate.changeDate(submitData.getStartTime());
                    Date endTime = ParseDate.changeDate(submitData.getEndTime());
                    Date transactionDate = new Date();

                    serviceContLoad.setVBay(Short.parseShort(submitData.getBay()));
                    serviceContLoad.setVRow(Short.parseShort(submitData.getRow()));
                    serviceContLoad.setVTier(Short.parseShort(submitData.getTier()));
                    serviceContLoad.setTransactionDate(transactionDate);
                    serviceContLoad.setPosition("01");
                    if(containerCraneMasterEquipment != null)
                        serviceContLoad.setEquipmentGroup(containerCraneMasterEquipment.getEquipmentGroup());

                    if (submitData.getCcCode().equals(CRANE_SEA)) {
                        serviceContLoad.setCrane("S");
                    } else {
                        serviceContLoad.setCrane("L");
                    }

                    Equipment haulageTrucking;

                    if (serviceContLoad.getIsTranshipment().equals("TRUE")) {
                        haulageTrucking = equipmentFacadeRemote.findByEquipmentActCodeAndOperation(submitData.getNoPpkb(), submitData.getContNo(), "H/T", "TRANSHIPMENT");
                    } else {
                        haulageTrucking = equipmentFacadeRemote.findByEquipmentActCodeAndOperation(submitData.getNoPpkb(), submitData.getContNo(), "H/T", "LOAD");
                    }


                    Equipment stevedoring = new Equipment();
                    stevedoring.setMasterEquipment(containerCraneMasterEquipment);
                    stevedoring.setContNo(submitData.getContNo());
                    stevedoring.setPlanningVessel(serviceVessel.getPlanningVessel());
                    stevedoring.setEquipmentActCode("STEVEDORING");
                    stevedoring.setStartTime(startTime);
                    stevedoring.setEndTime(endTime);
                    stevedoring.setMasterOperator(containerCraneOperator);

                    if (serviceContLoad.getIsTranshipment().equals("TRUE")) {
                        stevedoring.setOperation("TRANSHIPMENT");
                    } else {
                        stevedoring.setOperation("LOAD");
                    }

                    serviceVessel.setLoad((short) (serviceVessel.getLoad() + 1));

                    PlanningContLoad planningContLoad = planningContLoadFacadeRemote.findByNoPpkbAndContNoExcludeCancelLoading(serviceVessel.getNoPpkb(), serviceContLoad.getContNo());
                    planningContLoad.setPosition(PlanningContLoad.LOCATION_VESSEL);

                    if (haulageTrucking != null) {
                        haulageTrucking.setEndTime(startTime);
                        equipmentFacadeRemote.edit(haulageTrucking);
                    }

                    serviceContLoadFacadeRemote.edit(serviceContLoad);
                    serviceVesselFacadeRemote.edit(serviceVessel);
                    equipmentFacadeRemote.create(stevedoring);
                    planningContLoadFacadeRemote.edit(planningContLoad);

                    message = new Message(IMessageable.OK, "Success!");
                } else {
                    message = new Message(IMessageable.ERROR, "Data not found!");
                }
            } else {
                throw new RuntimeException(String.format("Service type is not valid! [service=%s]", submitData.getService()));
            }
        } catch (RuntimeException re) {
            message = new Message(IMessageable.ERROR, "Internal error!");
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", re);
        }
        
        return message;
    }
}
