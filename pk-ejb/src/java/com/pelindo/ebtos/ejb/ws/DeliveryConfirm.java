/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.ejb.ws;

import com.pelindo.ebtos.ejb.facade.remote.CancelLoadingServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterEquipmentFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterOperatorFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceContDischargeFacadeRemote;
import com.pelindo.ebtos.ejb.remote.DeliveryOperationRemote;
import com.pelindo.ebtos.ejb.ws.model.Container;
import com.pelindo.ebtos.ejb.ws.model.DeliveryConfirmInitialData;
import com.pelindo.ebtos.ejb.ws.model.DeliveryConfirmSubmitData;
import com.pelindo.ebtos.ejb.ws.model.IMessageable;
import com.pelindo.ebtos.ejb.ws.model.Message;
import com.pelindo.ebtos.exception.ContainerNotMovableException;
import com.pelindo.ebtos.exception.TimeSelectionNotValidException;
import com.pelindo.ebtos.model.db.CancelLoadingService;
import com.pelindo.ebtos.model.db.Equipment;
import com.pelindo.ebtos.model.db.ServiceContDischarge;
import com.pelindo.ebtos.model.db.master.MasterEquipment;
import com.pelindo.ebtos.model.db.master.MasterOperator;
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
public class DeliveryConfirm {
    @EJB
    private MasterEquipmentFacadeRemote masterEquipmentFacadeRemote;
    @EJB
    private MasterOperatorFacadeRemote masterOperatorFacadeRemote;
    @EJB
    private ServiceContDischargeFacadeRemote serviceContDischargeFacadeRemote;
    @EJB
    private CancelLoadingServiceFacadeRemote cancelLoadingServiceFacadeRemote;
    @EJB
    private DeliveryOperationRemote deliveryOperationRemote;

    @WebMethod(operationName = "prepareDeliveryConfirmUI")
    public DeliveryConfirmInitialData prepareDeliveryConfirmUI() {
        DeliveryConfirmInitialData deliveryConfirm = null;

        try {
            List<Object> ttCodes = masterEquipmentFacadeRemote.findTango4HHT();
            List<Object[]> operators = masterOperatorFacadeRemote.findMasterOperators4HHT();

            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            String serverTime = format.format(new Date());

            deliveryConfirm = new DeliveryConfirmInitialData(IMessageable.OK, "Data found!");
            deliveryConfirm.setServerTime(serverTime);
            deliveryConfirm.setTtCodes(ttCodes);
            deliveryConfirm.setOperators(operators);

        } catch (RuntimeException re) {
            deliveryConfirm = new DeliveryConfirmInitialData(IMessageable.ERROR, "Internal error!");
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", re);
        }

        return deliveryConfirm;
    }

    @WebMethod(operationName = "findDeliveryContainer")
    public Container findDeliveryContainer(@WebParam(name = "contNo") String contNo){
        Container container = null;

        try {
            Object[] cont = serviceContDischargeFacadeRemote.findDeliverableCont(contNo);

            if (cont != null) {
                Boolean hasEnteringGate = (Boolean) cont[17];

                if (hasEnteringGate) {
                    container = new Container(IMessageable.OK, "Data found!");
                    container.setDischargeVessel((String) cont[11]);
                    container.setSize(((Integer)cont[12]).shortValue());
                    container.setStatus((String) cont[13]);
                    if ((Boolean) cont[14] == true) {
                        container.setOverSize("TRUE");
                    } else {
                        container.setOverSize("FALSE");
                    }
                    if ((Boolean) cont[15] == true) {
                        container.setDangerous("TRUE");
                    }else {
                                container.setDangerous("FALSE");
                                }
                    container.setCondition((String) cont[16]);
                    container.setYardBlock((String) cont[6]);
                    container.setYardSlot(((Integer)cont[3]).shortValue());
                    container.setYardRow(((Integer)cont[4]).shortValue());
                    container.setYardTier(((Integer)cont[5]).shortValue());
                } else {
                    container = new Container(IMessageable.ERROR, "Container has not passed through the Gate!");
                }
            } else {
                cont = cancelLoadingServiceFacadeRemote.findDeliverableContainerByContNo(contNo);
                
                if (cont != null) {
                    container = new Container(IMessageable.OK, "Data found!");
                    container.setDischargeVessel((String) cont[10]);
                    container.setSize(((Integer)cont[2]).shortValue());
                    container.setStatus((String) cont[4]);
                    if((Boolean) cont[7] == true) {
                        container.setOverSize("TRUE");                        
                    } else {
                        container.setOverSize("FALSE");                        
                    }

                    if ((Boolean) cont[8] == true) {
                        container.setDangerous("TRUE");
                    } else {
                        container.setDangerous("FALSE");
                    }
                    container.setCondition((String) cont[9]);
                    container.setYardBlock((String) cont[11]);
                    container.setYardSlot(((Integer)cont[12]).shortValue());
                    container.setYardRow(((Integer)cont[13]).shortValue());
                    container.setYardTier(((Integer)cont[14]).shortValue());
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

    @WebMethod(operationName = "deliveryConfirm")
    public Message deliveryConfirm(@WebParam(name = "submitData") DeliveryConfirmSubmitData submitData) {
        Message message;
        
        try {
            UserUtil.setCurrentUser(submitData.getCreatedBy());
            MasterEquipment tangoMasterEquipment = masterEquipmentFacadeRemote.find(submitData.getTtCode());
            MasterOperator tangoOperator = masterOperatorFacadeRemote.find(submitData.getTtOperatorCode());
            Date startTime = ParseDate.changeDate(submitData.getStartTime());
            Date endTime = ParseDate.changeDate(submitData.getEndTime());

            Object[] cont = serviceContDischargeFacadeRemote.findDeliverableCont(submitData.getContNo());

            if (cont != null) {
                ServiceContDischarge serviceContDischarge = serviceContDischargeFacadeRemote.find(cont[0]);
                Boolean hasEnteringGate = (Boolean) cont[17];

                if (hasEnteringGate) {
                    Equipment equipment = new Equipment();
                    equipment.setMasterEquipment(tangoMasterEquipment);
                    equipment.setStartTime(startTime);
                    equipment.setEndTime(endTime);
                    equipment.setMasterOperator(tangoOperator);
                    
                    deliveryOperationRemote.handleDeliveryConfirm(serviceContDischarge, equipment);
                    message = new Message(IMessageable.OK, "Success!");
                } else {
                    message = new Message(IMessageable.ERROR, "Container has not passed through the Gate!");
                }
            } else {
                cont = cancelLoadingServiceFacadeRemote.findDeliverableContainerByContNo(submitData.getContNo());

                if (cont != null) {
                    CancelLoadingService cancelLoadingService = cancelLoadingServiceFacadeRemote.find(cont[0]);

                    Equipment equipment = new Equipment();
                    equipment.setMasterEquipment(tangoMasterEquipment);
                    equipment.setStartTime(startTime);
                    equipment.setEndTime(endTime);
                    equipment.setMasterOperator(tangoOperator);

                    deliveryOperationRemote.handleDeliveryConfirmCancelLoading(cancelLoadingService, equipment);
                    message = new Message(IMessageable.OK, "Success!");
                } else {
                    message = new Message(IMessageable.ERROR, "Data not found!");
                }
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
