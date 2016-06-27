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
import com.pelindo.ebtos.ejb.facade.remote.ServiceHatchMovesFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceVesselFacadeRemote;
import com.pelindo.ebtos.ejb.ws.model.HatchMoveInitialData;
import com.pelindo.ebtos.ejb.ws.model.HatchMoveSubmitData;
import com.pelindo.ebtos.ejb.ws.model.IMessageable;
import com.pelindo.ebtos.ejb.ws.model.Message;
import com.pelindo.ebtos.model.db.Equipment;
import com.pelindo.ebtos.model.db.ServiceHatchMoves;
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
public class HatchMove {
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
    private ServiceVesselFacadeRemote serviceVesselFacadeRemote;
    @EJB
    private ServiceHatchMovesFacadeRemote serviceHatchMovesFacadeRemote;

    @WebMethod(operationName = "prepareHatchMoveContainerUI")
    public HatchMoveInitialData prepareHatchMoveContainerUI() {
        HatchMoveInitialData initialData = null;

        try {
            List<Object> ccCodes = masterEquipmentFacadeRemote.findCrane4HHT();
            List<Object[]> operators = masterOperatorFacadeRemote.findMasterOperators4HHT();
            List<Object[]> vessels = planningVesselFacade.findByStatus4HHT("Servicing");

            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            String serverTime = format.format(new Date());

            initialData = new HatchMoveInitialData(IMessageable.OK, "Data found!");
            initialData.setServerTime(serverTime);
            initialData.setCcCodes(ccCodes);
            initialData.setOperators(operators);
            initialData.setVessels(vessels);

        } catch (RuntimeException re) {
            initialData = new HatchMoveInitialData(IMessageable.ERROR, "Internal error!");
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", re);
        }

        return initialData;
    }

    @WebMethod(operationName = "hatchMoveConfirm")
    public Message hatchMoveConfirm(@WebParam(name = "hatchMoveSubmitData") HatchMoveSubmitData submitData) {
        Message message = null;

        try {
            UserUtil.setCurrentUser(submitData.getCreatedBy());
            ServiceVessel serviceVessel = serviceVesselFacadeRemote.findByPpkbAndStatus(submitData.getNoPpkb(), "Servicing");
            
            if (serviceVessel != null) {
                MasterEquipment containerCraneMasterEquipment = masterEquipmentFacadeRemote.find(submitData.getCcCode());
                MasterOperator containerCraneOperator = masterOperatorFacadeRemote.find(submitData.getCcOperatorCode());
                Date startTime = ParseDate.changeDate(submitData.getStartTime());
                Date endTime = ParseDate.changeDate(submitData.getEndTime());
                short hatchNumber = Short.parseShort(submitData.getHatchNumber());
                Equipment hatchMoving = null;

                ServiceHatchMoves serviceHatchMoves = serviceHatchMovesFacadeRemote.findByOperationBayAndHatchMoves(submitData.getNoPpkb(), hatchNumber, submitData.getOperation(), submitData.getActivity());
                
                if (serviceHatchMoves == null) {
                    hatchMoving = new Equipment();
                    serviceHatchMoves = new ServiceHatchMoves();
                } else {
                    hatchMoving = equipmentFacadeRemote.find(serviceHatchMoves.getIdEquipment());
                    serviceHatchMoves.setIdEquipment(hatchMoving.getId());
                }

                serviceHatchMoves.setNoPpkb(serviceVessel.getNoPpkb());
                serviceHatchMoves.setBay(hatchNumber);
                serviceHatchMoves.setHatchMoves(submitData.getActivity());
                serviceHatchMoves.setOperation(submitData.getOperation());
                serviceHatchMoves.setHatchMoveDate(endTime);

                if (submitData.getCcCode().equals(CRANE_SEA)) {
                    serviceHatchMoves.setCrane("S");
                    serviceHatchMoves.setIsPaid("FALSE");
                } else {
                    serviceHatchMoves.setCrane("L");
                    serviceHatchMoves.setIsPaid("TRUE");
                }

                hatchMoving.setMasterEquipment(containerCraneMasterEquipment);
                hatchMoving.setPlanningVessel(serviceVessel.getPlanningVessel());
                hatchMoving.setStartTime(startTime);
                hatchMoving.setEndTime(endTime);
                hatchMoving.setEquipmentActCode(submitData.getActivity());
                hatchMoving.setOperation(submitData.getOperation());
                hatchMoving.setMasterOperator(containerCraneOperator);

                equipmentFacadeRemote.edit(hatchMoving);

                if (serviceHatchMoves.getIdEquipment() == null) {
                    int id = equipmentFacadeRemote.findEquipmentId(submitData.getNoPpkb(), startTime, endTime, submitData.getActivity(), submitData.getOperation());
                    serviceHatchMoves.setIdEquipment(id);
                }

                serviceHatchMovesFacadeRemote.edit(serviceHatchMoves);
                
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
