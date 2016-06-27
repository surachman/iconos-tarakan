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
import com.pelindo.ebtos.ejb.facade.remote.UcShiftingServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.UncontainerizedServiceFacadeRemote;
import com.pelindo.ebtos.ejb.ws.model.Uncontainerized;
import com.pelindo.ebtos.ejb.ws.model.LoadUcConfirmInitialData;
import com.pelindo.ebtos.ejb.ws.model.LoadUcConfirmSubmitData;
import com.pelindo.ebtos.ejb.ws.model.IMessageable;
import com.pelindo.ebtos.ejb.ws.model.Message;
import com.pelindo.ebtos.model.db.Equipment;
import com.pelindo.ebtos.model.db.PlanningVessel;
import com.pelindo.ebtos.model.db.UcShiftingService;
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
public class LoadUcConfirm {
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
    private UcShiftingServiceFacadeRemote ucShiftingServiceFacadeRemote;

    @WebMethod(operationName = "prepareLoadUcConfirmUI")
    public LoadUcConfirmInitialData prepareLoadUcConfirmUI() {
        LoadUcConfirmInitialData loadUcConfirm = null;

        try {
            List<Object> ccCodes = masterEquipmentFacadeRemote.findCrane4HHT();
            List<Object[]> operators = masterOperatorFacadeRemote.findMasterOperators4HHT();
            List<Object[]> vessels = planningVesselFacade.findByStatus4HHT("Servicing");

            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            String serverTime = format.format(new Date());

            loadUcConfirm = new LoadUcConfirmInitialData(IMessageable.OK, "Data found!");
            loadUcConfirm.setServerTime(serverTime);
            loadUcConfirm.setCcCodes(ccCodes);
            loadUcConfirm.setOperators(operators);
            loadUcConfirm.setVessels(vessels);
        } catch (RuntimeException re) {
            loadUcConfirm = new LoadUcConfirmInitialData(IMessageable.ERROR, "Internal error!");
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", re);
        }

        return loadUcConfirm;
    }

    @WebMethod(operationName = "findLoadUc")
    public Uncontainerized findLoadUc(@WebParam(name = "noPpkb") String noPpkb, @WebParam(name = "blNo") String blNo){
        Uncontainerized uncontainerized = null;

        try {
            Object[] loadableUc = uncontainerizedServiceFacadeRemote.findLoadableByNoPpkbAndNoBl(noPpkb, blNo);
            
            if (loadableUc != null) {
                uncontainerized = new Uncontainerized(IMessageable.OK, "Data found!");
                uncontainerized.setNoPpkb(noPpkb);
                uncontainerized.setBlNo(blNo);
                uncontainerized.setCommodityName((String) loadableUc[3]);
                uncontainerized.setWeight((Integer) loadableUc[4]);
                uncontainerized.setUnit((Integer) loadableUc[5]);
                uncontainerized.setIsHasEnteredGate((Boolean) loadableUc[6]);
            } else {
                uncontainerized = new Uncontainerized(IMessageable.ERROR, "Data not found!");
            }
        } catch (RuntimeException re) {
            uncontainerized = new Uncontainerized(IMessageable.ERROR, "Internal error!");
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", re);
        }

        return uncontainerized;
    }

    @WebMethod(operationName = "loadUcStevedoringConfirm")
    public Message loadUcStevedoringConfirm(@WebParam(name = "submitData") LoadUcConfirmSubmitData submitData) {
        Message message = null;

        try {
            UserUtil.setCurrentUser(submitData.getCreatedBy());
            Object[] loadableUc = uncontainerizedServiceFacadeRemote.findLoadableByNoPpkbAndNoBl(submitData.getNoPpkb(), submitData.getBlNo());

            if (loadableUc != null) {
                Date start = ParseDate.changeDate(submitData.getStartTime());
                Date end = ParseDate.changeDate(submitData.getEndTime());
                PlanningVessel planningVessel = planningVesselFacade.find(submitData.getNoPpkb());
                MasterEquipment crane = masterEquipmentFacadeRemote.find(submitData.getCcCode());
                MasterOperator craneOperator = masterOperatorFacadeRemote.find(submitData.getCcOperatorCode());

                UncontainerizedService uncontainerizedService = uncontainerizedServiceFacadeRemote.find(loadableUc[0]);
                uncontainerizedService.setStatus("VESSEL");
                uncontainerizedService.setPlacementDate(start);

                if (submitData.getCcCode().equals(CRANE_SEA)) {
                    uncontainerizedService.setCrane("S");
                } else {
                    uncontainerizedService.setCrane("L");
                }

                Equipment stevedoring = new Equipment();
                stevedoring.setPlanningVessel(planningVessel);
                stevedoring.setBlNo(uncontainerizedService.getBlNo());
                stevedoring.setMasterEquipment(crane);
                stevedoring.setStartTime(start);
                stevedoring.setEndTime(end);
                stevedoring.setMasterOperator(craneOperator);

                if (uncontainerizedService.getIsShifting().equals("TRUE")) {
                    stevedoring.setEquipmentActCode("RESHIPPING");
                    stevedoring.setOperation("SHIFTING-TOCY");
                    equipmentFacadeRemote.edit(stevedoring);

                    UcShiftingService ucShiftingService = ucShiftingServiceFacadeRemote.findByNoPpkbAndBlNo(uncontainerizedService.getNoPpkb(), uncontainerizedService.getBlNo());
                    int id = equipmentFacadeRemote.findIdEquipmentByAllBlNo(stevedoring.getMasterEquipment().getEquipCode(), stevedoring.getMasterOperator().getOptCode(), stevedoring.getBlNo(), uncontainerizedService.getNoPpkb(), stevedoring.getStartTime(), stevedoring.getEndTime(), stevedoring.getEquipmentActCode(), stevedoring.getOperation());
                    ucShiftingService.setIdEquipmentReshipping(id);
                    ucShiftingService.setIsReshipping("TRUE");

                    ucShiftingServiceFacadeRemote.edit(ucShiftingService);
                } else if (uncontainerizedService.getIsTranshipment().equals("TRUE")) {
                    stevedoring.setEquipmentActCode("STEVEDORING");
                    stevedoring.setOperation("TRANSLOADUC");
                    equipmentFacadeRemote.edit(stevedoring);
                } else {
                    stevedoring.setEquipmentActCode("STEVEDORING");
                    stevedoring.setOperation("LOADUC");
                    equipmentFacadeRemote.edit(stevedoring);
                }

                if (uncontainerizedService.getTruckLoosing().equals("FALSE")) {
                    Equipment haulageTruck;

                    if (uncontainerizedService.getIsShifting().equals("TRUE")) {
                        haulageTruck = equipmentFacadeRemote.findByPpkbBLNoEquipmentActCodeAndOperation(planningVessel.getNoPpkb(), uncontainerizedService.getBlNo(), "H/T-RESHIPPING", "SHIFTING-TOCY");
                    } else if (uncontainerizedService.getIsTranshipment().equals("TRUE")) {
                        haulageTruck = equipmentFacadeRemote.findByPpkbBLNoEquipmentActCodeAndOperation(planningVessel.getNoPpkb(), uncontainerizedService.getBlNo(), "H/T", "TRANSLOADUC");
                    } else {
                        haulageTruck = equipmentFacadeRemote.findByPpkbBLNoEquipmentActCodeAndOperation(planningVessel.getNoPpkb(), uncontainerizedService.getBlNo(), "H/T", "LOADUC");
                    }

                    haulageTruck.setEndTime(stevedoring.getStartTime());

                    equipmentFacadeRemote.edit(haulageTruck);
                }

                uncontainerizedServiceFacadeRemote.edit(uncontainerizedService);
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
