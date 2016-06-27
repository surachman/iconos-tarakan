/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.ejb.ws;

import com.qtasnim.util.ParseDate;
import com.pelindo.ebtos.ejb.facade.remote.MasterEquipmentFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterOperatorFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterYardFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningContReceivingFacadeRemote;
import com.pelindo.ebtos.ejb.remote.ReceivingOperationRemote;
import com.pelindo.ebtos.ejb.ws.model.Container;
import com.pelindo.ebtos.ejb.ws.model.ReceivingConfirmInitialData;
import com.pelindo.ebtos.ejb.ws.model.ReceivingConfirmSubmitData;
import com.pelindo.ebtos.ejb.ws.model.IMessageable;
import com.pelindo.ebtos.ejb.ws.model.Message;
import com.pelindo.ebtos.exception.LocationNotAvailableException;
import com.pelindo.ebtos.exception.TimeSelectionNotValidException;
import com.pelindo.ebtos.model.db.Equipment;
import com.pelindo.ebtos.model.db.PlanningContReceiving;
import com.pelindo.ebtos.model.db.ServiceReceiving;
import com.pelindo.ebtos.model.db.master.MasterEquipment;
import com.pelindo.ebtos.model.db.master.MasterOperator;
import com.pelindo.ebtos.model.db.master.MasterYard;
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
public class ReceivingConfirm {
    @EJB
    private MasterEquipmentFacadeRemote masterEquipmentFacadeRemote;
    @EJB
    private MasterOperatorFacadeRemote masterOperatorFacadeRemote;
    @EJB
    private PlanningContReceivingFacadeRemote planningContReceivingFacadeRemote;
    @EJB
    private MasterYardFacadeRemote masterYardFacadeRemote;
    @EJB
    private ReceivingOperationRemote receivingOperationRemote;

    @WebMethod(operationName = "prepareReceivingConfirmUI")
    public ReceivingConfirmInitialData prepareReceivingConfirmUI() {
        ReceivingConfirmInitialData deliveryConfirm = null;

        try {
            List<Object> ttCodes = masterEquipmentFacadeRemote.findTango4HHT();
            List<Object[]> operators = masterOperatorFacadeRemote.findMasterOperators4HHT();

            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            String serverTime = format.format(new Date());

            deliveryConfirm = new ReceivingConfirmInitialData(IMessageable.OK, "Data found!");
            deliveryConfirm.setServerTime(serverTime);
            deliveryConfirm.setTtCodes(ttCodes);
            deliveryConfirm.setOperators(operators);

        } catch (RuntimeException re) {
            deliveryConfirm = new ReceivingConfirmInitialData(IMessageable.ERROR, "Internal error!");
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", re);
        }

        return deliveryConfirm;
    }

    @WebMethod(operationName = "findReceivingContainer")
    public Container findReceivingContainer(@WebParam(name = "contNo") String contNo){
        Container container = null;

        try {
            Object[] receivingContainer = planningContReceivingFacadeRemote.findReceivableContainerWithSuggestedLocation(contNo);

            if (receivingContainer != null) {
                Boolean hasEnteringGate = (Boolean) receivingContainer[16];

                if (hasEnteringGate) {
                    container = new Container(IMessageable.OK, "Data found!");
                    container.setLoadVessel((String) receivingContainer[15]);
                    container.setSize(((Integer) receivingContainer[8]).shortValue());
                    container.setStatus((String) receivingContainer[9]);
                    if((Boolean) receivingContainer[10] == true) {
                        container.setOverSize("TRUE"); 
                    } else {
                        container.setOverSize("FALSE"); 
                    }
                    
                    if ((Boolean) receivingContainer[11]) {
                        container.setDangerous("TRUE");
                    } else {
                        container.setDangerous("FALSE");
                    }
                        
                    
                    container.setDischargePort((String) receivingContainer[12]);
                    container.setWeight((Integer) receivingContainer[13]);
                    container.setCondition((String) receivingContainer[14]);

                    if (receivingContainer[3] != null) {
                        container.setYardBlock((String) receivingContainer[3]);
                        container.setYardSlot(((Integer) receivingContainer[4]).shortValue());
                        container.setYardRow(((Integer) receivingContainer[5]).shortValue());
                        container.setYardTier(((Integer) receivingContainer[6]).shortValue());
                    }
                } else {
                    container = new Container(IMessageable.ERROR, "Container has not passed through the Gate!");
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

    @WebMethod(operationName = "receivingConfirm")
    public Message receivingConfirm(@WebParam(name = "submitData") ReceivingConfirmSubmitData submitData) {
        Message message;

        try {
            UserUtil.setCurrentUser(submitData.getCreatedBy());
            Object[] receivingContainer = planningContReceivingFacadeRemote.findReceivableContainerWithSuggestedLocation(submitData.getContNo());

            if (receivingContainer != null) {
                Boolean hasEnteringGate = (Boolean) receivingContainer[16];

                if (hasEnteringGate) {
                    PlanningContReceiving planningContReceiving = planningContReceivingFacadeRemote.find(receivingContainer[0]);
                    Date startTime = ParseDate.changeDate(submitData.getStartTime());
                    Date endTime = ParseDate.changeDate(submitData.getEndTime());
                    MasterEquipment tangoMasterEquipment = masterEquipmentFacadeRemote.find(submitData.getTtCode());
                    MasterOperator tangoOperator = masterOperatorFacadeRemote.find(submitData.getTtOperatorCode());
                    short slot = Short.parseShort(submitData.getYardSlot());
                    short row = Short.parseShort(submitData.getYardRow());
                    short tier = Short.parseShort(submitData.getYardTier());
                    MasterYard masterYard = masterYardFacadeRemote.find(submitData.getYardBlock().toUpperCase());

                    ServiceReceiving serviceReceiving = new ServiceReceiving();
                    serviceReceiving.setMasterYard(masterYard);
                    serviceReceiving.setYSlot(slot);
                    serviceReceiving.setYRow(row);
                    serviceReceiving.setYTier(tier);

                    Equipment equipment = new Equipment();
                    equipment.setStartTime(startTime);
                    equipment.setEndTime(endTime);
                    equipment.setMasterEquipment(tangoMasterEquipment);
                    equipment.setMasterOperator(tangoOperator);

                    receivingOperationRemote.handleReceivingConfirm(serviceReceiving, planningContReceiving, equipment);
                    message = new Message(IMessageable.OK, "Success!");
                } else {
                    message = new Message(IMessageable.ERROR, "Container has not passed through the Gate!");
                }
            } else {
                message = new Message(IMessageable.ERROR, "Data not found!");
            }
        } catch (TimeSelectionNotValidException ex) {
            message = new Message(IMessageable.ERROR, "End time must be greater than start time!");
        } catch (LocationNotAvailableException ex) {
            message = new Message(IMessageable.ERROR, "Destination location is not available!");
        } catch (RuntimeException re) {
            message = new Message(IMessageable.ERROR, "Internal error!");
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", re);
        }
        return message;
    }
}
