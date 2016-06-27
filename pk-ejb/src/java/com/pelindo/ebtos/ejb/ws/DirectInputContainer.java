/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.ejb.ws;

import com.qtasnim.util.ParseDate;
import com.pelindo.ebtos.ejb.facade.remote.EquipmentFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterContDamageFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterEquipmentFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterOperatorFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningVesselFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceContDischargeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceVesselFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.TempContDischargeFacadeRemote;
import com.pelindo.ebtos.ejb.ws.model.DirectInputContainerInitialData;
import com.pelindo.ebtos.ejb.ws.model.DirectInputContainerSubmitData;
import com.pelindo.ebtos.ejb.ws.model.IMessageable;
import com.pelindo.ebtos.ejb.ws.model.Message;
import com.pelindo.ebtos.model.db.Equipment;
import com.pelindo.ebtos.model.db.ServiceVessel;
import com.pelindo.ebtos.model.db.TempContDischarge;
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
public class DirectInputContainer {
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
    private EquipmentFacadeRemote equipmentFacadeRemote;
    @EJB
    private MasterContDamageFacadeRemote masterContDamageFacadeRemote;
    @EJB
    private TempContDischargeFacadeRemote tempContDischargeFacadeRemote;
    @EJB
    private ServiceVesselFacadeRemote serviceVesselFacadeRemote;

    @WebMethod(operationName = "prepareDirectInputContainerUI")
    public DirectInputContainerInitialData prepareDirectInputContainerUI() {
        DirectInputContainerInitialData initialData = null;

        try {
            List<Object> ccCodes = masterEquipmentFacadeRemote.findCrane4HHT();
            List<Object> htCodes = masterEquipmentFacadeRemote.findHeadTruck4HHT();
            List<Object[]> operators = masterOperatorFacadeRemote.findMasterOperators4HHT();
            List<Object[]> vessels = planningVesselFacade.findByStatus4HHT("Servicing");
            List<Object[]> masterContDamages = masterContDamageFacadeRemote.findMasterContDamages();

            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            String serverTime = format.format(new Date());

            initialData = new DirectInputContainerInitialData(IMessageable.OK, "Data found!");
            initialData.setServerTime(serverTime);
            initialData.setCcCodes(ccCodes);
            initialData.setHtCodes(htCodes);
            initialData.setOperators(operators);
            initialData.setVessels(vessels);
            initialData.setContDamage(masterContDamages);

        } catch (RuntimeException re) {
            initialData = new DirectInputContainerInitialData(IMessageable.ERROR, "Internal error!");
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", re);
        }

        return initialData;
    }

    @WebMethod(operationName = "directInputConfirm")
    public Message directInputConfirm(@WebParam(name = "submitData") DirectInputContainerSubmitData submitData) {
        Message message = null;

        try {
            UserUtil.setCurrentUser(submitData.getCreatedBy());
            Object[] confirmedContainer = serviceContDischargeFacadeRemote.findConfirmedContainer(submitData.getNoPpkb(), submitData.getContNo());

            if (confirmedContainer != null) {
                message = new Message(IMessageable.ERROR, "This container already confirmed!");
            } else {
                ServiceVessel serviceVessel = serviceVesselFacadeRemote.find(submitData.getNoPpkb());
                MasterEquipment containerCraneMasterEquipment = masterEquipmentFacadeRemote.find(submitData.getCcCode());
                MasterOperator containerCraneOperator = masterOperatorFacadeRemote.find(submitData.getCcOperatorCode());
                MasterEquipment haulageTruckMasterEquipment = masterEquipmentFacadeRemote.find(submitData.getHtCode());
                MasterOperator haulageTruckOperator = masterOperatorFacadeRemote.find(submitData.getHtOperatorCode());
                MasterContDamage masterContDamage = masterContDamageFacadeRemote.find(submitData.getContDamage());
                Date startTime = ParseDate.changeDate(submitData.getStartTime());
                Date endTime = ParseDate.changeDate(submitData.getEndTime());

                TempContDischarge tempContDischarge = new TempContDischarge();
                tempContDischarge.setNoPpkb(submitData.getNoPpkb());
                tempContDischarge.setContNo(submitData.getContNo());
                tempContDischarge.setVBay(Short.parseShort(submitData.getVesselBay()));
                tempContDischarge.setVRow(Short.parseShort(submitData.getVesselRow()));
                tempContDischarge.setVTier(Short.parseShort(submitData.getVesselTier()));
                tempContDischarge.setContSize(Short.parseShort(submitData.getContSize()));
                tempContDischarge.setStatusReplacement(TempContDischarge.UNREPLACED);
                tempContDischarge.setPosition("02");
                if (Boolean.parseBoolean(submitData.getIsDangerous()) == true) {
                    tempContDischarge.setDangerous("TRUE");
                } else {
                    tempContDischarge.setDangerous("FALSE");
                }
                
                if (Boolean.parseBoolean(submitData.getIsOversize()) == true) {
                    tempContDischarge.setOverSize("TRUE");
                } else {
                    tempContDischarge.setOverSize("FALSE");
                }
                
                tempContDischarge.setContStatus(submitData.getContStatus());
                tempContDischarge.setMasterContDamage(masterContDamage);

                if (submitData.getCcCode().equals(CRANE_SEA)) {
                    tempContDischarge.setCrane("S");
                } else {
                    tempContDischarge.setCrane("L");
                }

                Equipment stevedoring = new Equipment();
                stevedoring.setContNo(submitData.getContNo());
                stevedoring.setMasterEquipment(containerCraneMasterEquipment);
                stevedoring.setPlanningVessel(serviceVessel.getPlanningVessel());
                stevedoring.setEquipmentActCode("STEVEDORING");
                stevedoring.setOperation("DISCHARGE");
                stevedoring.setStartTime(startTime);
                stevedoring.setEndTime(endTime);
                stevedoring.setMasterOperator(containerCraneOperator);

                Equipment haulageTrucking = new Equipment();
                haulageTrucking.setContNo(submitData.getContNo());
                haulageTrucking.setEquipmentActCode("H/T");
                haulageTrucking.setOperation("DISCHARGE");
                haulageTrucking.setMasterEquipment(haulageTruckMasterEquipment);
                haulageTrucking.setPlanningVessel(serviceVessel.getPlanningVessel());
                haulageTrucking.setStartTime(endTime);
                haulageTrucking.setMasterOperator(haulageTruckOperator);

                equipmentFacadeRemote.create(stevedoring);
                equipmentFacadeRemote.create(haulageTrucking);
                tempContDischargeFacadeRemote.create(tempContDischarge);

                message = new Message(IMessageable.OK, "Success!");
            }
        } catch (RuntimeException re) {
            message = new Message(IMessageable.ERROR, "Internal error!");
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", re);
        }
        return message;
    }
}
