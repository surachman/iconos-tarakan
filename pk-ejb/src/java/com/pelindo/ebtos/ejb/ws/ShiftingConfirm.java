/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.ejb.ws;

import com.qtasnim.util.ParseDate;
import com.pelindo.ebtos.ejb.facade.remote.EquipmentFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterCommodityFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterContainerTypeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterCustomerFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterEquipmentFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterOperatorFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningVesselFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceContLoadFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceShiftingFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceVesselFacadeRemote;
import com.pelindo.ebtos.ejb.ws.model.Container;
import com.pelindo.ebtos.ejb.ws.model.ShiftingConfirmInitialData;
import com.pelindo.ebtos.ejb.ws.model.ShiftingConfirmSubmitData;
import com.pelindo.ebtos.ejb.ws.model.IMessageable;
import com.pelindo.ebtos.ejb.ws.model.Message;
import com.pelindo.ebtos.ejb.ws.model.ShiftableContainer;
import com.pelindo.ebtos.model.db.Equipment;
import com.pelindo.ebtos.model.db.ServiceShifting;
import com.pelindo.ebtos.model.db.ServiceVessel;
import com.pelindo.ebtos.model.db.master.MasterCommodity;
import com.pelindo.ebtos.model.db.master.MasterContainerType;
import com.pelindo.ebtos.model.db.master.MasterCustomer;
import com.pelindo.ebtos.model.db.master.MasterEquipment;
import com.pelindo.ebtos.model.db.master.MasterOperator;
import com.pelindo.ebtos.security.UserUtil;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
public class ShiftingConfirm {
    public static String SHIFT_TO_LANDED = "LANDED";
    public static String SHIFT_TO_NOLANDED = "NOLANDED";

    public static String SERVICE_LOAD = "LOAD";
    public static String SERVICE_DISCHARGE = "DISCHARGE";
    public static String SERVICE_DIRECT_INPUT = "DIRECT_INPUT";

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
    private MasterCustomerFacadeRemote masterCustomerFacadeRemote;
    @EJB
    private MasterContainerTypeFacadeRemote masterContainerTypeFacadeRemote;
    @EJB
    private MasterCommodityFacadeRemote masterCommodityFacadeRemote;

    @WebMethod(operationName = "prepareShiftingConfirmUI")
    public ShiftingConfirmInitialData prepareShiftingConfirmUI() {
        ShiftingConfirmInitialData shiftingInitialData = null;

        try {
            List<Object[]> vessels = planningVesselFacade.findByStatus4HHT("Servicing");
            List<Object[]> operators = masterOperatorFacadeRemote.findMasterOperators4HHT();
            List<Object> ccCodes = masterEquipmentFacadeRemote.findCrane4HHT();
            List<Object> statusses = new ArrayList<Object>();
            List<Object> sizes = new ArrayList<Object>();

            statusses.add("FCL");
            statusses.add("LCL");
            statusses.add("MTY");

            sizes.add("20");
            sizes.add("40");

            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            String serverTime = format.format(new Date());

            shiftingInitialData = new ShiftingConfirmInitialData(IMessageable.OK, "Data found!");
            shiftingInitialData.setVessels(vessels);
            shiftingInitialData.setOperators(operators);
            shiftingInitialData.setCcCodes(ccCodes);
            shiftingInitialData.setStatusses(statusses);
            shiftingInitialData.setSizes(sizes);
            shiftingInitialData.setServerTime(serverTime);
        } catch (RuntimeException re) {
            shiftingInitialData = new ShiftingConfirmInitialData(IMessageable.ERROR, "Internal error!");
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", re);
        }

        return shiftingInitialData;
    }

    @WebMethod(operationName = "findShiftableContainer")
    public ShiftableContainer findShiftableContainer(@WebParam(name = "noPpkb") String noPpkb, @WebParam(name = "contNo") String contNo, @WebParam(name = "operation") String operation){
        ShiftableContainer shiftableContainer = null;

        try {
            if (operation.equals(SERVICE_LOAD)) {
                Object[] result = serviceContLoadFacadeRemote.findShiftableContainer(noPpkb, contNo);

                if (result != null) {
                    shiftableContainer = new ShiftableContainer(IMessageable.OK, "Data found!");

                    Container container = new Container(IMessageable.OK, "Data found!");
                    container.setService(SERVICE_LOAD);
                    container.setContNo((String) result[1]);
                    container.setVesselBay(((Integer) result[13]).shortValue());
                    container.setVesselRow(((Integer) result[14]).shortValue());
                    container.setVesselTier(((Integer) result[15]).shortValue());
                    container.setStatus((String) result[5]);
                    container.setSize(((Integer) result[4]).shortValue());
                    
                    if ((Boolean) result[16] == true) {
                        container.setOverSize("TRUE");
                    } else {
                        container.setOverSize("FALSE");
                    }
                    
                    if ((Boolean) result[6] == true) {
                        container.setDangerous("TRUE");
                    } else {
                        container.setDangerous("FALSE");
                    }
                    
                    if ((Boolean) result[7] == true) {
                        container.setDgLabel("TRUE");
                    } else {
                        container.setDgLabel("FALSE");
                        }
                    shiftableContainer.setContainer(container);
                    shiftableContainer.setIsoCode((String) result[17]);
                }
            } else if (operation.equals(SERVICE_DISCHARGE)) {
                Object[] result = serviceShiftingFacadeRemote.findShiftableContainer(noPpkb, contNo);

                if (result != null) {
                    shiftableContainer = new ShiftableContainer(IMessageable.OK, "Data found!");

                    Container container = new Container(IMessageable.OK, "Data found!");
                    container.setService(SERVICE_DISCHARGE);
                    container.setContNo((String) result[1]);
                    container.setVesselBay(((Integer) result[14]).shortValue());
                    container.setVesselRow(((Integer) result[15]).shortValue());
                    container.setVesselTier(((Integer) result[16]).shortValue());
                    container.setStatus((String) result[5]);
                    container.setSize(((Integer) result[4]).shortValue());
                    if((Boolean) result[17] == true) {
                        container.setOverSize("TRUE");
                    } else {
                        container.setOverSize("FALSE");
                    }
                    
                    if ((Boolean) result[6] == true) {
                        container.setDangerous("TRUE");
                    } else {
                        container.setDangerous("FALSE");
                    }
                    
                    if ((Boolean) result[7]) {
                        container.setDgLabel("TRUE");
                    } else {
                        container.setDgLabel("FALSE");
                    }

                    shiftableContainer.setContainer(container);
                    shiftableContainer.setShiftTo((String) result[13]);
                    shiftableContainer.setIsoCode((String) result[18]);
                }
            }

            if (shiftableContainer == null) {
                ServiceShifting serviceShifting = serviceShiftingFacadeRemote.findByNoPpkbAndContNo(noPpkb, contNo);

                if (serviceShifting == null) {
                    shiftableContainer = new ShiftableContainer(IMessageable.OK, "Data found!");
                    
                    Container container = new Container(IMessageable.OK, "Data found!");
                    container.setService(SERVICE_DIRECT_INPUT);

                    shiftableContainer.setContainer(container);
                } else {
                    shiftableContainer = new ShiftableContainer(IMessageable.ERROR, "Data not found!");
                }
            }
        } catch (RuntimeException re) {
            shiftableContainer = new ShiftableContainer(IMessageable.ERROR, "Internal error!");
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", re);
        }

        return shiftableContainer;
    }

    @WebMethod(operationName = "shiftingConfirm")
    public Message shiftingConfirm(@WebParam(name = "submitData") ShiftingConfirmSubmitData submitData) {
        Message message = null;

        try {
            UserUtil.setCurrentUser(submitData.getCreatedBy());
            if (submitData.getService().equals(SERVICE_LOAD)) {
                Object[] shiftableContainer = serviceContLoadFacadeRemote.findShiftableContainer(submitData.getNoPpkb(), submitData.getContNo());

                if (shiftableContainer != null) {
                    ServiceVessel serviceVessel = serviceVesselFacadeRemote.find(submitData.getNoPpkb());
                    MasterEquipment craneMasterEquipment = masterEquipmentFacadeRemote.find(submitData.getCcCode());
                    MasterOperator craneOperator = masterOperatorFacadeRemote.find(submitData.getCcOperatorCode());
                    Date startTime = ParseDate.changeDate(submitData.getStartTime());
                    Date endTime = ParseDate.changeDate(submitData.getEndTime());
                    MasterContainerType masterContainerType = masterContainerTypeFacadeRemote.find((Integer) shiftableContainer[8]);
                    MasterCommodity masterCommodity = masterCommodityFacadeRemote.find(shiftableContainer[9]);
                    MasterCustomer mlo = null;

                    if (shiftableContainer[10] != null) {
                        mlo = masterCustomerFacadeRemote.find(shiftableContainer[10]);
                    }

                    Equipment stevedoring = new Equipment();
                    stevedoring.setContNo((String) shiftableContainer[1]);
                    stevedoring.setMlo(mlo);
                    stevedoring.setEquipmentActCode("STEVEDORING");
                    stevedoring.setPlanningVessel(serviceVessel.getPlanningVessel());
                    stevedoring.setMasterEquipment(craneMasterEquipment);
                    stevedoring.setMasterOperator(craneOperator);
                    stevedoring.setStartTime(startTime);
                    stevedoring.setEndTime(endTime);

                    ServiceShifting serviceShifting = new ServiceShifting();
                    serviceShifting.setContNo((String) shiftableContainer[1]);
                    serviceShifting.setContGross((Integer) shiftableContainer[3]);
                    serviceShifting.setContSize(((Integer) shiftableContainer[4]).shortValue());
                    serviceShifting.setContStatus((String) shiftableContainer[5]);
                    if((Boolean) shiftableContainer[6] == true) {
                        serviceShifting.setDg("TRUE");
                    } else {
                        serviceShifting.setDg("FALSE");
                    }
                    
                    if((Boolean) shiftableContainer[16]) {
                        serviceShifting.setOverSize("TRUE");
                    } else {
                        serviceShifting.setOverSize("FALSE");
                    }
                    
                    if((Boolean) shiftableContainer[7]) {
                        
                        serviceShifting.setDgLabel("TRUE");
                    } else {
                        serviceShifting.setDgLabel("FALSE");
                    }
                    
                    serviceShifting.setMasterContainerType(masterContainerType);
                    serviceShifting.setMasterCommodity(masterCommodity);
                    serviceShifting.setBlNo((String) shiftableContainer[2]);
                    serviceShifting.setMlo(mlo);
                    serviceShifting.setServiceVessel(serviceVessel);
                    serviceShifting.setShiftTo(submitData.getShiftTo());
                    if(submitData.getShiftTo().equals(SHIFT_TO_LANDED) == true) {
                        serviceShifting.setIsLanded("TRUE");
                    } else {
                        serviceShifting.setIsLanded("FALSE");
                    }
                    
                    serviceShifting.setOperation(submitData.getOperation());
                    serviceShifting.setPosition(ServiceShifting.LOCATION_SHIFTING);
                    serviceShifting.setIsPlanning("FALSE");
                    serviceShifting.setSealNo((String) shiftableContainer[11]);
                    
                    if(Boolean.parseBoolean(submitData.getIsUseSling()) == true) {
                        serviceShifting.setIsSling("TRUE");    
                    } else {
                        serviceShifting.setIsSling("FALSE");
                    }
                    
                    if ((Boolean) shiftableContainer[12] == true) {
                        serviceShifting.setIsExportImport("TRUE");    
                    } else {
                        serviceShifting.setIsExportImport("FALSE");
                    }
                            
                    
                    serviceShifting.setIsPaid("TRUE");

                    if (serviceShifting.getIsLanded().equals("TRUE")) {
                        stevedoring.setOperation("SHIFTING-LANDED");
                        serviceShifting.setVBay(((Integer) shiftableContainer[13]).shortValue());
                        serviceShifting.setVRow(((Integer) shiftableContainer[14]).shortValue());
                        serviceShifting.setVTier(((Integer) shiftableContainer[15]).shortValue());
                        serviceShifting.setIsReshipping("FALSE");
                    } else {
                        stevedoring.setOperation("SHIFTING-NOLAND");
                        serviceShifting.setShiftingDate(stevedoring.getEndTime());
                        serviceShifting.setVBay(Short.parseShort(submitData.getToBay()));
                        serviceShifting.setVRow(Short.parseShort(submitData.getToRow()));
                        serviceShifting.setVTier(Short.parseShort(submitData.getToTier()));
                        serviceShifting.setIsReshipping("TRUE");
                    }

                    equipmentFacadeRemote.create(stevedoring);

                    int id = equipmentFacadeRemote.findIdEquipmentByAll(stevedoring.getMasterEquipment().getEquipCode(), stevedoring.getMasterOperator().getOptCode(), stevedoring.getContNo(), serviceVessel.getNoPpkb(), stevedoring.getStartTime(), stevedoring.getEndTime(), stevedoring.getEquipmentActCode(), stevedoring.getOperation());
                    serviceShifting.setIdEquipment(id);

                    serviceShiftingFacadeRemote.create(serviceShifting);

                    message = new Message(IMessageable.OK, "Succeed!");
                } else {
                    message = new Message(IMessageable.ERROR, "Data not found!");
                }
            } else if (submitData.getService().equals(SERVICE_DISCHARGE)) {
                Object[] shiftableContainer = serviceShiftingFacadeRemote.findShiftableContainer(submitData.getNoPpkb(), submitData.getContNo());

                if (shiftableContainer != null) {
                    ServiceVessel serviceVessel = serviceVesselFacadeRemote.find(submitData.getNoPpkb());
                    ServiceShifting serviceShifting = serviceShiftingFacadeRemote.find((Integer) shiftableContainer[0]);
                    MasterEquipment craneMasterEquipment = masterEquipmentFacadeRemote.find(submitData.getCcCode());
                    MasterOperator craneOperator = masterOperatorFacadeRemote.find(submitData.getCcOperatorCode());
                    Date startTime = ParseDate.changeDate(submitData.getStartTime());
                    Date endTime = ParseDate.changeDate(submitData.getEndTime());

                    Equipment stevedoring = new Equipment();
                    stevedoring.setContNo(serviceShifting.getContNo());
                    stevedoring.setMlo(serviceShifting.getMlo());
                    stevedoring.setEquipmentActCode("STEVEDORING");
                    stevedoring.setPlanningVessel(serviceVessel.getPlanningVessel());
                    stevedoring.setMasterEquipment(craneMasterEquipment);
                    stevedoring.setMasterOperator(craneOperator);
                    stevedoring.setStartTime(startTime);
                    stevedoring.setEndTime(endTime);

                    serviceShifting.setShiftTo(submitData.getShiftTo());
                    if (submitData.getShiftTo().equals(SHIFT_TO_LANDED)) {
                        serviceShifting.setIsLanded("TRUE");
                    } else {
                        serviceShifting.setIsLanded("FALSE");
                    }
                    
                    serviceShifting.setOperation(submitData.getOperation());
                    serviceShifting.setPosition(ServiceShifting.LOCATION_SHIFTING);
                    
                    if (Boolean.parseBoolean(submitData.getIsUseSling()) == true) {
                        serviceShifting.setIsSling("TRUE");
                    } else {
                        serviceShifting.setIsSling("FALSE");
                    }
                    
                    serviceShifting.setIsPaid("TRUE");

                    if (serviceShifting.getIsLanded().equals("TRUE")) {
                        stevedoring.setOperation("SHIFTING-LANDED");
                        serviceShifting.setIsReshipping("FALSE");
                    } else {
                        stevedoring.setOperation("SHIFTING-NOLAND");
                        serviceShifting.setVBay(Short.parseShort(submitData.getToBay()));
                        serviceShifting.setVRow(Short.parseShort(submitData.getToRow()));
                        serviceShifting.setVTier(Short.parseShort(submitData.getToTier()));
                        serviceShifting.setShiftingDate(stevedoring.getEndTime());
                        serviceShifting.setIsReshipping("TRUE");
                    }

                    equipmentFacadeRemote.create(stevedoring);

                    int id = equipmentFacadeRemote.findIdEquipmentByAll(stevedoring.getMasterEquipment().getEquipCode(), stevedoring.getMasterOperator().getOptCode(), stevedoring.getContNo(), serviceVessel.getNoPpkb(), stevedoring.getStartTime(), stevedoring.getEndTime(), stevedoring.getEquipmentActCode(), stevedoring.getOperation());
                    serviceShifting.setIdEquipment(id);

                    serviceShiftingFacadeRemote.edit(serviceShifting);

                    message = new Message(IMessageable.OK, "Succeed!");
                } else {
                    message = new Message(IMessageable.ERROR, "Data not found!");
                }
            } else if (submitData.getService().equals(SERVICE_DIRECT_INPUT)) {
                
                if (true) {
                    return new Message(IMessageable.ERROR, "Shifting direct input not implemented yet!");
                }

                ServiceShifting serviceShifting = serviceShiftingFacadeRemote.findByNoPpkbAndContNo(submitData.getNoPpkb(), submitData.getContNo());

                if (serviceShifting == null) {
                    ServiceVessel serviceVessel = serviceVesselFacadeRemote.find(submitData.getNoPpkb());
                    MasterEquipment craneMasterEquipment = masterEquipmentFacadeRemote.find(submitData.getCcCode());
                    MasterOperator craneOperator = masterOperatorFacadeRemote.find(submitData.getCcOperatorCode());
                    Date startTime = ParseDate.changeDate(submitData.getStartTime());
                    Date endTime = ParseDate.changeDate(submitData.getEndTime());

                    Equipment stevedoring = new Equipment();
                    stevedoring.setContNo(submitData.getContNo());
                    stevedoring.setEquipmentActCode("STEVEDORING");
                    stevedoring.setPlanningVessel(serviceVessel.getPlanningVessel());
                    stevedoring.setMasterEquipment(craneMasterEquipment);
                    stevedoring.setMasterOperator(craneOperator);
                    stevedoring.setStartTime(startTime);
                    stevedoring.setEndTime(endTime);

                    serviceShifting = new ServiceShifting();
                    serviceShifting.setContNo(submitData.getContNo());
                    serviceShifting.setContGross(0);
                    serviceShifting.setContSize(Short.parseShort(submitData.getSize()));
                    serviceShifting.setContStatus(submitData.getStatus());
                    
                    if(Boolean.parseBoolean(submitData.getDg()) == true) {
                        serviceShifting.setDg("TRUE");
                    } else {
                        serviceShifting.setDg("FALSE");
                    }
                    
                    if (Boolean.parseBoolean(submitData.getDgLabel()) == true) {
                        serviceShifting.setDgLabel("TRUE");
                    } else {
                        serviceShifting.setDgLabel("FALSE");
                    }
                    
                    if (Boolean.parseBoolean(submitData.getOverSize()) == true) {
                        serviceShifting.setOverSize("TRUE");
                    } else {
                        serviceShifting.setOverSize("FALSE");
                    }
                    
                    serviceShifting.setServiceVessel(serviceVessel);
                    serviceShifting.setShiftTo(submitData.getShiftTo());
                    
                    if (submitData.getShiftTo().equals(SHIFT_TO_LANDED)) {
                        
                        serviceShifting.setIsLanded("TRUE");
                    } else {
                        serviceShifting.setIsLanded("FALSE");
                    }
                    
                    serviceShifting.setOperation(submitData.getOperation());
                    serviceShifting.setPosition(ServiceShifting.LOCATION_SHIFTING);
                    serviceShifting.setIsPlanning("FALSE");
                    
                    if(Boolean.parseBoolean(submitData.getIsUseSling()) == true) {
                        serviceShifting.setIsSling("TRUE");
                    } else {
                        serviceShifting.setIsSling("FALSE");
                    }
                    
                    serviceShifting.setIsExportImport("FALSE");
                    serviceShifting.setIsPaid("TRUE");

                    if (serviceShifting.getIsLanded().equals("TRUE")) {
                        stevedoring.setOperation("SHIFTING-LANDED");
                        serviceShifting.setIsReshipping("FALSE");

                        serviceShifting.setVBay(Short.parseShort(submitData.getFromBay()));
                        serviceShifting.setVRow(Short.parseShort(submitData.getFromRow()));
                        serviceShifting.setVTier(Short.parseShort(submitData.getFromTier()));
                    } else {
                        stevedoring.setOperation("SHIFTING-NOLAND");
                        serviceShifting.setVBay(Short.parseShort(submitData.getToBay()));
                        serviceShifting.setVRow(Short.parseShort(submitData.getToRow()));
                        serviceShifting.setVTier(Short.parseShort(submitData.getToTier()));
                        serviceShifting.setShiftingDate(stevedoring.getEndTime());
                        serviceShifting.setIsReshipping("TRUE");
                    }

                    equipmentFacadeRemote.create(stevedoring);

                    int id = equipmentFacadeRemote.findIdEquipmentByAll(stevedoring.getMasterEquipment().getEquipCode(), stevedoring.getMasterOperator().getOptCode(), stevedoring.getContNo(), serviceVessel.getNoPpkb(), stevedoring.getStartTime(), stevedoring.getEndTime(), stevedoring.getEquipmentActCode(), stevedoring.getOperation());
                    serviceShifting.setIdEquipment(id);

                    serviceShiftingFacadeRemote.create(serviceShifting);
                    
                    message = new Message(IMessageable.OK, "Succeed!");
                } else {
                    message = new Message(IMessageable.ERROR, "Data not found!");
                }
            } else {
                throw new RuntimeException(String.format("Invalid service name [Service=%s]", submitData.getService()));
            }
        } catch (RuntimeException re) {
            message = new Message(IMessageable.ERROR, "Internal error!");
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", re);
        }
        return message;
    }
}
