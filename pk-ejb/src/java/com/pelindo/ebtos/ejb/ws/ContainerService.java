/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.ejb.ws;

import com.qtasnim.util.CyValidation;
import com.pelindo.ebtos.ejb.facade.remote.EquipmentFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterYardCoordinatFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningContLoadFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceContDischargeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceContLoadFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceContTranshipmentFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceVesselFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterPluggingReeferFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningContReceivingFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PluggingReeferServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ReeferMonitoringFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceHatchMovesFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceLoadUcFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceReceivingFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceReeferFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceShiftingFacadeRemote;
import com.pelindo.ebtos.model.db.Equipment;
import com.pelindo.ebtos.model.db.PlanningContLoad;
import com.pelindo.ebtos.model.db.ServiceContDischarge;
import com.pelindo.ebtos.model.db.ServiceContLoad;
import com.pelindo.ebtos.model.db.ServiceContTranshipment;
import com.pelindo.ebtos.model.db.ServiceVessel;
import com.pelindo.ebtos.model.db.PlanningContReceiving;
import com.pelindo.ebtos.model.db.PlanningVessel;
import com.pelindo.ebtos.model.db.ReeferMonitoring;
import com.pelindo.ebtos.model.db.ServiceBehandle;
import com.pelindo.ebtos.model.db.ServiceDischargeUc;
import com.pelindo.ebtos.model.db.ServiceHatchMoves;
import com.pelindo.ebtos.model.db.ServiceLoadUc;
import com.pelindo.ebtos.model.db.ServiceReceiving;
import com.pelindo.ebtos.model.db.ServiceReefer;
import com.pelindo.ebtos.model.db.ServiceShifting;
import com.pelindo.ebtos.model.db.master.MasterOperator;
import com.pelindo.ebtos.model.db.master.MasterPluggingReefer;
import com.pelindo.ebtos.model.db.master.MasterCommodity;
import com.pelindo.ebtos.model.db.master.MasterContainerType;
import com.pelindo.ebtos.model.db.master.MasterEquipment;
import com.pelindo.ebtos.model.db.master.MasterYard;
import com.pelindo.ebtos.model.db.master.MasterYardCoordinat;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import com.qtasnim.util.ParseDate;
import com.pelindo.ebtos.ejb.facade.remote.BookingOnlineFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.CancelLoadingServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterContainerTypeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterEquipmentFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterOperatorFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterYardFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningContDischargeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningShiftDischargeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningTransDischargeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningVesselFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ReceivingServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ReeferDischargeServiceFacadeRemote;
import com.pelindo.ebtos.util.GrossConverter;
import com.pelindo.ebtos.ejb.facade.remote.ServiceDeliveryFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.TempContDischargeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.UcDeliveryServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.UcReceivingServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.UcShiftingServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.UncontainerizedServiceFacadeRemote;
import com.pelindo.ebtos.model.db.BookingOnline;
import com.pelindo.ebtos.model.db.CancelLoadingService;
import com.pelindo.ebtos.model.db.PlanningContDischarge;
import com.pelindo.ebtos.model.db.PlanningShiftDischarge;
import com.pelindo.ebtos.model.db.PlanningTransDischarge;
import com.pelindo.ebtos.model.db.PluggingReeferService;
import com.pelindo.ebtos.model.db.ReceivingService;
import com.pelindo.ebtos.model.db.ReeferDischargeService;
import com.pelindo.ebtos.model.db.ServiceCfs;
import com.pelindo.ebtos.model.db.ServiceDelivery;
import com.pelindo.ebtos.model.db.ServiceInspection;
import com.pelindo.ebtos.model.db.TempContDischarge;
import com.pelindo.ebtos.model.db.UcDeliveryService;
import com.pelindo.ebtos.model.db.UcReceivingService;
import com.pelindo.ebtos.model.db.UcShiftingService;
import com.pelindo.ebtos.model.db.UncontainerizedService;
import com.pelindo.ebtos.security.UserUtil;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 *
 * @author Dyware-Dev01
 */
@WebService()
@Stateless()
public class ContainerService {

    private static final String ALERT_1 = "exist_error";
    private static final String ALERT_2 = "tier_error";
    private static final String CRANE_SEA = "EQ000";
    private static final int CONTAINER_TYPE_20 = 20;
    private static final int CONTAINER_TYPE_40 = 40;
    @EJB
    private ServiceContDischargeFacadeRemote serviceContDischargeFacadeRemote;
    @EJB
    private EquipmentFacadeRemote equipmentFacadeRemote;
    @EJB
    private ServiceContTranshipmentFacadeRemote serviceContTranshipmentFacadeRemote;
    @EJB
    private ServiceContLoadFacadeRemote serviceContLoadFacadeRemote;
    @EJB
    private PlanningContLoadFacadeRemote planningContLoadFacadeRemote;
    @EJB
    private MasterYardCoordinatFacadeRemote masterYardCoordinatFacadeRemote;
    @EJB
    private ServiceVesselFacadeRemote serviceVesselFacadeRemote;
    @EJB
    private PlanningContReceivingFacadeRemote planningContReceivingFacadeRemote;
    @EJB
    private ServiceReceivingFacadeRemote serviceReceivingFacadeRemote;
    @EJB
    private ServiceReeferFacadeRemote serviceReeferFacadeRemote;
    @EJB
    private PluggingReeferServiceFacadeRemote pluggingReeferServiceFacadeRemote;
    @EJB
    private MasterPluggingReeferFacadeRemote masterPluggingReeferFacadeRemote;
    @EJB
    private ReeferMonitoringFacadeRemote reeferMonitoringFacadeRemote;
    @EJB
    private ServiceLoadUcFacadeRemote serviceLoadUcFacadeRemote;
    @EJB
    private ServiceHatchMovesFacadeRemote serviceHatchMovesFacadeRemote;
    @EJB
    private ServiceShiftingFacadeRemote serviceShiftingFacadeRemote;
    @EJB
    private UncontainerizedServiceFacadeRemote uncontainerizedServiceFacadeRemote;
    @EJB
    private UcDeliveryServiceFacadeRemote ucDeliveryServiceFacadeRemote;
    @EJB
    private UcReceivingServiceFacadeRemote ucReceivingServiceFacadeRemote;
    @EJB
    private UcShiftingServiceFacadeRemote ucShiftingServiceFacadeRemote;
    @EJB
    private ReceivingServiceFacadeRemote receivingServiceFacadeRemote;
    @EJB
    private PlanningVesselFacadeRemote planningVesselFacadeRemote;
    @EJB
    private BookingOnlineFacadeRemote bookingOnlineFacadeRemote;
    @EJB
    private PlanningContDischargeFacadeRemote planningContDischargeFacadeRemote;
    @EJB
    private PlanningTransDischargeFacadeRemote planningTransDischargeFacadeRemote;
    @EJB
    private PlanningShiftDischargeFacadeRemote planningShiftDischargeFacadeRemote;
    @EJB
    private TempContDischargeFacadeRemote tempContDischargeFacadeRemote;
    @EJB
    private CancelLoadingServiceFacadeRemote cancelLoadingServiceFacadeRemote;
    @EJB
    private ServiceDeliveryFacadeRemote serviceDeliveryFacadeRemote;
    @EJB
    private MasterContainerTypeFacadeRemote masterContainerTypeFacadeRemote;
    @EJB
    private MasterYardFacadeRemote masterYardFacadeRemote;
    @EJB
    private ReeferDischargeServiceFacadeRemote reeferDischargeServiceFacadeRemote;
    @EJB
    private MasterEquipmentFacadeRemote masterEquipmentFacadeRemote;
    @EJB
    private MasterOperatorFacadeRemote masterOperatorFacadeRemote;

    /** Object Instantiasi **/
    private PlanningVessel planningVessel;
    private PlanningContReceiving planningContReceiving;
    private PlanningContLoad planningContLoad;
    private ServiceContDischarge serviceContDischarge;
    private ServiceContTranshipment serviceContTranshipment;
    private ServiceContLoad serviceContLoad;
    private ServiceReceiving serviceReceiving;
    private ServiceVessel serviceVessel, serviceVessel2;
    private ServiceReefer serviceReefer;
    private ServiceLoadUc serviceLoadUc;
    private ServiceHatchMoves serviceHatchMoves;
    private ServiceShifting serviceShifting;
    private ServiceDelivery serviceDelivery;
    private ReceivingService receivingService;
    private CancelLoadingService cancelLoadingService;
    private MasterEquipment masterEquipment;
    private MasterEquipment masterEquipment2;
    private MasterYard masterYard;
    private MasterCommodity masterCommodity;
    private MasterContainerType masterContainerType;
    private MasterYardCoordinat masterYardCoordinat;
    private MasterYardCoordinat masterYardCoordinat2;
    private MasterPluggingReefer masterPluggingReefer;
    private MasterOperator masterOperator;
    private ReeferMonitoring reeferMonitoring;
    private Equipment equipment, equipment2;
    private UncontainerizedService uncontainerizedService;
    private UcDeliveryService ucDeliveryService;
    private UcReceivingService ucReceivingService;
    private UcShiftingService ucShiftingService;
    private BookingOnline bookingOnline;
    private TempContDischarge tempContDischarge;
    private CyValidation cyVlaidate;
    private PluggingReeferService pluggingReeferService;
    private List<Object[]> masterYardCoordinats;
    Object[] container, contServicing, tempShifting, containerPlug;
    String[] temp;
    String status = "Ok";
    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    public ContainerService() {
    }

    public void starOver() {
        serviceContDischarge = new ServiceContDischarge();
        serviceContTranshipment = new ServiceContTranshipment();
        serviceContLoad = new ServiceContLoad();
        equipment = new Equipment();
        equipment2 = new Equipment();
        masterEquipment = new MasterEquipment();
        masterEquipment2 = new MasterEquipment();
        serviceVessel = new ServiceVessel();
        masterYard = new MasterYard();
        masterCommodity = new MasterCommodity();
        masterContainerType = new MasterContainerType();
        masterYardCoordinat = new MasterYardCoordinat();
        masterYardCoordinat2 = new MasterYardCoordinat();
        serviceVessel2 = new ServiceVessel();
        masterYardCoordinats = new ArrayList<Object[]>();
        planningContReceiving = new PlanningContReceiving();
        serviceReceiving = new ServiceReceiving();
        planningVessel = new PlanningVessel();
        serviceReefer = new ServiceReefer();
        masterPluggingReefer = new MasterPluggingReefer();
        reeferMonitoring = new ReeferMonitoring();
        serviceLoadUc = new ServiceLoadUc();
        serviceHatchMoves = new ServiceHatchMoves();
        serviceShifting = new ServiceShifting();
        masterOperator = new MasterOperator();
        uncontainerizedService = new UncontainerizedService();
        ucDeliveryService = new UcDeliveryService();
        ucReceivingService = new UcReceivingService();
        ucShiftingService = new UcShiftingService();
        bookingOnline = new BookingOnline();
        tempContDischarge = new TempContDischarge();
        cancelLoadingService = new CancelLoadingService();
        status = "Ok";
        cyVlaidate = new CyValidation();
        serviceDelivery = new ServiceDelivery();
        pluggingReeferService = new PluggingReeferService();
    }

    /**
     * Web service operation
     * @param no_ppkb, cont_no, type
     * @return Object Array Container
     */
    @WebMethod(operationName = "getCont")
    public String[] getCont(@WebParam(name = "no_ppkb") String no_ppkb, @WebParam(name = "cont_no") String cont_no, @WebParam(name = "type") String type) {

        //type 1 = serv cont discharge
        //type 2 = serv cont transhipment
        //type 3 = serv disch uc
        //type 4 = serv load uc
        //type 5,6 = serv shifting
        //type 7 = sertvice cont load
        //type 8 = lift off UC
        //type 9 = shifting w/o plan
        //type 10 = shifting w plan
        //type 11 = reshipping UC
        //type 12 = receiving confirm
        //type 13 = cancel loading disch
        //type 14 = cancel loading deliver

        if (type.equals("1")) {
            /**
             * replaced by @DischargeConfirm.findDischargeContainer
             */
            container = serviceContDischargeFacadeRemote.findByNoPpkb(no_ppkb, cont_no, "01");
            if (container == null) {
                container = serviceShiftingFacadeRemote.findByPpkbDischargeConfirmSelectHht(no_ppkb, cont_no);
            }
        } else if (type.equals("2")) {
            /**
             * replaced by @LoadConfirm.findLoadContainer
             */
            container = serviceContLoadFacadeRemote.findByContNoPpkb(no_ppkb, cont_no, "02");
            if (container == null) {
                container = serviceShiftingFacadeRemote.findByPpkbLoadConfirmSelectHht(no_ppkb,cont_no, "04");
            }
        } else if (type.equals("3")) {
            /**
             * replaced by @DischargeConfirmUc.findDischargeUc
             */
            container = uncontainerizedServiceFacadeRemote.findByPpkbAndBlNoMobile(no_ppkb, cont_no, "PLANNING", "DISCHARGE", 0);
        } else if (type.equals("4")) {
            /**
             * replaced by @LoadUcConfirm.findLoadUc
             */
        } else if (type.equals("5")) {
            container = serviceShiftingFacadeRemote.findByContNo(no_ppkb, cont_no);
        } else if (type.equals("6")) {
            container = serviceShiftingFacadeRemote.findByContNoReship(no_ppkb, cont_no, "FALSE");
        } else if (type.equals("7")) {
            container = serviceContLoadFacadeRemote.findByContNoPpkb(no_ppkb, cont_no, "01");
        } else if (type.equals("8")) {
            container = uncontainerizedServiceFacadeRemote.findByPpkbAndBlNoMobile(no_ppkb, cont_no, "H/T", "DISCHARGEUC", 0);
        } else if (type.equals("9")) {
            container = uncontainerizedServiceFacadeRemote.findShiftingWithoutMobile(no_ppkb, cont_no);
        } else if (type.equals("10")) {
            container = uncontainerizedServiceFacadeRemote.findShiftingMobile(no_ppkb, cont_no);
        } else if (type.equals("11")) {
            container = ucShiftingServiceFacadeRemote.findByPpkbBlNo(no_ppkb, cont_no, 0);
        } else if (type.equals("12")) {
            /**
             * replaced by @ReceivingConfirm.findReceivingContainer
             */
            container = planningContReceivingFacadeRemote.findReceivableContainerWithSuggestedLocation(cont_no);
            if (container == null) {
                container = pluggingReeferServiceFacadeRemote.findSelectPluggingReeferServiceReceivingHht(cont_no);
            }
        } else if (type.equals("13")) {
            container = cancelLoadingServiceFacadeRemote.findByPpkbAndContMobile(no_ppkb, cont_no);
        } else if (type.equals("14")) {
            container = cancelLoadingServiceFacadeRemote.findByPpkbAndContMobileDelivery(no_ppkb, cont_no);
        }

        if (container != null) {
            temp = new String[container.length];

            for (int i = 0; i < container.length; i++) {
                if (container[i] != null) {
                    temp[i] = container[i].toString();
                } else {
                    temp[i] = null;
                }
            }
            return temp;
        } else {
            return null;
        }
    }

    /**
     * Web service operation
     * @param cont_no, type
     * @return Object Array Container
     */
    @WebMethod(operationName = "getContainer")
    public String[] getContainer(@WebParam(name = "cont_no") String cont_no, @WebParam(name = "type") String type) {
        //TODO Fungsi utama search container

        //type 1 : cari container dari service_cont_load dgn position "02"
        //type 2 : cari container dari service_cont_trans dgn position "02"
        //tupe 3 : cari container dari service_cont_disch dgn position "02"
        //type 4 : cari container dari planning_cont_load dgn position "03" dan is_transhipment FALSE
        //type 5 : cari container dari service_cont_trans dgn position "01"
        //type 6 : cari container dari service_cont_trans dgn position "02"
        //type 7 : cari container dari service_cont_disch dgn position "03" dan is_delivery FALSE
        //type 8 : cari container dari planning_cont_load dgn positon "03" dan is_transhipment TRUE
        //type 9 : cari container dari planning_cont_receiving dgn position "01"
        //type 10 : cari cont dari service reefer
        //type 16 : cari uc dischrge confirm
        //type 17 : cari uc receiving confirm

        if (type.equals("1")) {
            container = serviceContLoadFacadeRemote.findByContNo(cont_no, "02");            
        } else if (type.equals("2")) {
            container = serviceContTranshipmentFacadeRemote.findByContNo(cont_no, "02");
        } else if (type.equals("3")) {
            /**
             * replaced by @LiftOffConfirm.findLiftableOffContainer
             */
            container = serviceContDischargeFacadeRemote.findByContNo(cont_no, "02");
            if (container == null) {
                container = tempContDischargeFacadeRemote.findByContNo(cont_no, "02");
                if (container == null) {
                    container = serviceShiftingFacadeRemote.findByPpkbLiftOffeConfirmSelectHht(cont_no, "02");
                }
            }
        } else if (type.equals("4")) {
            /**
             * replaced by @LiftOnConfirm.findLiftableOnContainer
             */
            container = planningContLoadFacadeRemote.findByContNo(cont_no, "03", false);
            if (container == null) {
                container = serviceShiftingFacadeRemote.findByPpkbLiftOnConfirmSelectHht(cont_no, "03");
            }
        } else if (type.equals("5")) {
            /**
             * replaced by @DischargeConfirm.findDischargeContainer
             */
            container = serviceContTranshipmentFacadeRemote.findByContNo(cont_no, "01");
        } else if (type.equals("6")) {
            container = serviceContTranshipmentFacadeRemote.findByContNo(cont_no, "02");
        } else if (type.equals("7")) {
            /**
             * replaced by @DeliveryConfirm.findDeliveryContainer
             */
            container = serviceContDischargeFacadeRemote.findDeliverableCont(cont_no);
            if (container == null) {
                container = pluggingReeferServiceFacadeRemote.findPluggingReeferServiceByDeliveryConfirmHht(cont_no);
            }
        } else if (type.equals("8")) {
            container = planningContLoadFacadeRemote.findByContNo(cont_no, "03", true);
        } else if (type.equals("9")) {
            container = planningContReceivingFacadeRemote.findByContNoMobile(cont_no, "01");
        } else if (type.equals("10")) {
            container = serviceReeferFacadeRemote.findByContNo(cont_no);
        } else if (type.equals("11")) {
            container = serviceContDischargeFacadeRemote.findByContNoMov(cont_no, "03", false, false, false, false);
        } else if (type.equals("12")) {
            //container = contDischargeHistoryFacadeRemote.findContIsHt(cont_no, true);
            if (container == null) {
//                container = contReceivingHistoryFacadeRemote.findContIsHt(cont_no, true);
            }
        } else if (type.equals("13")) {
            container = serviceContDischargeFacadeRemote.findContMovementMobile(cont_no, "03", false);
            if (container == null) {
                container = serviceReceivingFacadeRemote.findServiceReceivingByIsLiftOn(cont_no, false);
            }
        } else if (type.equals("14")) {
            container = uncontainerizedServiceFacadeRemote.findByBlNoMobile(cont_no, "STV", "DISCHARGE", 0);
        } else if (type.equals("15")) {
            container = uncontainerizedServiceFacadeRemote.findByBlNoMobile(cont_no, "CY", "LOAD", 0);
        } else if (type.equals("16")) {
            container = uncontainerizedServiceFacadeRemote.findByBlNoMobile(cont_no, "CY", "DISCHARGE", 0);
        } else if (type.equals("17")) {
            container = uncontainerizedServiceFacadeRemote.findByBlNoMobileUc(cont_no);
        } else if (type.equals("18")) {
            /**
             * replaced by @StartService.findServiceVessel
             */
            container = planningVesselFacadeRemote.findReadyServicesMobile(cont_no);
        } else if (type.equals("19")) {
            /**
             * replaced by @VesselService.findVesselSchedules
             */
            container = serviceVesselFacadeRemote.findServiceByStatus(cont_no, "Servicing");
        }

        if (container != null) {
            temp = new String[container.length];

            for (int i = 0; i < container.length; i++) {
                if (container[i] != null) {
                    temp[i] = container[i].toString();
                } else {
                    temp[i] = null;
                }
            }
            return temp;
        } else {
            return null;
        }
    }

    /**
     * Web service operation
     * @param position, type
     * @return status operation
     */
    @WebMethod(operationName = "updateContDisch")
    public String updateContDisch(@WebParam(name = "position") String[] position, @WebParam(name = "type") String type) {
        UserUtil.setCurrentUser((String) position[11]);
        //type 1 = discharge_confirm
        //type 2 = transhipment_transhipment
        //type 3 = direct input
        starOver();
        Date start = ParseDate.changeDate(position[7]);
        Date end = ParseDate.changeDate(position[8]);

        if (type.equals("1")) {
            /**
             * replaced by @DischargeConfirm.stevedoringConfirm
             */
            container = serviceContDischargeFacadeRemote.findByNoPpkb(position[0], position[1], "01");
            containerPlug = serviceShiftingFacadeRemote.findByPpkbDischargeConfirmSelectHht(position[0], position[1]);
            if (container != null) {
                serviceContDischarge = serviceContDischargeFacadeRemote.find(container[5]);
                serviceContDischarge.setVBay(Short.parseShort(position[2]));
                serviceContDischarge.setVRow(Short.parseShort(position[3]));
                serviceContDischarge.setVTier(Short.parseShort(position[4]));
                serviceContDischarge.setIsBehandle("FALSE");
                serviceContDischarge.setIsCfs("FALSE");
                serviceContDischarge.setIsInspection("FALSE");
                serviceContDischarge.setIsDelivery("FALSE");
                serviceContDischarge.setPosition("02");
                if (position[5].equals(CRANE_SEA)) {
                    serviceContDischarge.setCrane("S");
                } else {
                    serviceContDischarge.setCrane("L");
                }

                masterEquipment.setEquipCode(position[5]);
                masterEquipment2.setEquipCode(position[6]);

                serviceVessel.setNoPpkb(position[0]);

                equipment.setContNo(position[1]);
                equipment.setEquipmentActCode("STEVEDORING");
                equipment.setOperation("DISCHARGE");
                equipment.setMasterEquipment(masterEquipment);
                equipment.setPlanningVessel(serviceVessel.getPlanningVessel());
                equipment.setStartTime(start);
                equipment.setEndTime(end);
                equipment.setMasterOperator(new MasterOperator(position[9]));

                equipment2.setContNo(position[1]);
                equipment2.setEquipmentActCode("H/T");
                equipment2.setOperation("DISCHARGE");
                equipment2.setMasterEquipment(masterEquipment2);
                equipment2.setPlanningVessel(serviceVessel.getPlanningVessel());
                equipment2.setStartTime(end);
                equipment2.setMasterOperator(new MasterOperator(position[10]));

                //update
                try {
                    serviceContDischargeFacadeRemote.edit(serviceContDischarge);
                    equipmentFacadeRemote.edit(equipment);
                    equipmentFacadeRemote.edit(equipment2);
                } catch (EJBException e) {
                    status = e.toString();
                }
            } else if (containerPlug != null) {
                ServiceShifting sc = new ServiceShifting();
                sc = serviceShiftingFacadeRemote.find(containerPlug[5]);
                sc.setVBay(Short.parseShort(position[2]));
                sc.setVRow(Short.parseShort(position[3]));
                sc.setVTier(Short.parseShort(position[4]));
                sc.setPosition("02");
                sc.setServiceVessel(serviceVessel);

                if (position[5].equals(CRANE_SEA)) {
                    sc.setCrane("S");
                } else {
                    sc.setCrane("L");
                }

                masterEquipment.setEquipCode(position[5]);
                masterEquipment2.setEquipCode(position[6]);

                serviceVessel.setNoPpkb(position[0]);

                equipment.setContNo(position[1]);
                equipment.setEquipmentActCode("STEVEDORING");
                equipment.setOperation("SHIFTING-CY");
                equipment.setMasterEquipment(masterEquipment);
                equipment.setPlanningVessel(serviceVessel.getPlanningVessel());
                equipment.setStartTime(start);
                equipment.setEndTime(end);
                equipment.setMasterOperator(new MasterOperator(position[9]));


                equipment2.setContNo(position[1]);
                equipment2.setEquipmentActCode("H/T");
                equipment2.setOperation("SHIFTING-CY");
                equipment2.setMasterEquipment(masterEquipment2);
                equipment2.setPlanningVessel(serviceVessel.getPlanningVessel());
                equipment2.setStartTime(end);
                equipment2.setMasterOperator(new MasterOperator(position[10]));


                //update
                try {
                    serviceShiftingFacadeRemote.edit(sc);
                    equipmentFacadeRemote.edit(equipment);
                    equipmentFacadeRemote.edit(equipment2);
                } catch (EJBException e) {
                    status = e.toString();
                }
                //sc.edit(serviceShifting);
            }
        } else if (type.equals("2")) {
            /**
             * replaced by @DischargeConfirm.stevedoringConfirm
             */
            container = serviceContTranshipmentFacadeRemote.findByContNo(position[1], "01");
            serviceContTranshipment = serviceContTranshipmentFacadeRemote.find(container[0]);
            serviceContTranshipment.setVBay(Short.parseShort(position[2]));
            serviceContTranshipment.setVRow(Short.parseShort(position[3]));
            serviceContTranshipment.setVTier(Short.parseShort(position[4]));
            serviceContTranshipment.setPosition("02");
            if (position[5].equals(CRANE_SEA)) {
                serviceContTranshipment.setCrane("S");
            } else {
                serviceContTranshipment.setCrane("L");
            }

            masterEquipment.setEquipCode(position[5]);
            masterEquipment2.setEquipCode(position[6]);

            serviceVessel.setNoPpkb((String) container[2]);

            equipment.setContNo(position[1]);
            equipment.setPlanningVessel(serviceVessel.getPlanningVessel());
            equipment.setMasterEquipment(new MasterEquipment(position[5]));
            equipment.setEquipmentActCode("STEVEDORING");
            equipment.setOperation("TRANSDISCHARGE");
            equipment.setStartTime(start);
            equipment.setEndTime(end);
            equipment.setMasterOperator(new MasterOperator(position[9]));


            equipment2.setContNo(position[1]);
            equipment2.setPlanningVessel(serviceVessel.getPlanningVessel());
            equipment2.setMasterEquipment(new MasterEquipment(position[6]));
            equipment2.setEquipmentActCode("H/T");
            equipment2.setOperation("TRANSDISCHARGE");
            equipment2.setStartTime(end);
            equipment2.setMasterOperator(new MasterOperator(position[10]));

            //update
            try {
                serviceContTranshipmentFacadeRemote.edit(serviceContTranshipment);
                equipmentFacadeRemote.edit(equipment);
                equipmentFacadeRemote.edit(equipment2);
            } catch (EJBException e) {
                status = e.toString();
            }
        } else if (type.equals("3")) {
            /**
             * replaced by @DirectInputContainer.directInputConfirm
             */

            Object[] confirmedContainer = serviceContDischargeFacadeRemote.findConfirmedContainer(position[0], position[1]);
            if (confirmedContainer != null) {
                return "Container " + position[1] + " sudah pernah di confirm";
            }

            tempContDischarge.setNoPpkb(position[0]);
            tempContDischarge.setContNo(position[1]);
            tempContDischarge.setVBay(Short.parseShort(position[2]));
            tempContDischarge.setVRow(Short.parseShort(position[3]));
            tempContDischarge.setVTier(Short.parseShort(position[4]));
            tempContDischarge.setContSize(Short.parseShort(position[12]));
            tempContDischarge.setStatusReplacement("UNREPLACED");
            tempContDischarge.setPosition("02");
            tempContDischarge.setContStatus(position[13]);

            if (position[5].equals(CRANE_SEA)) {
                tempContDischarge.setCrane("S");
            } else {
                tempContDischarge.setCrane("L");
            }

            equipment.setContNo(position[1]);
            equipment.setMasterEquipment(new MasterEquipment(position[5]));
            equipment.setPlanningVessel(new PlanningVessel(position[0]));
            equipment.setEquipmentActCode("STEVEDORING");
            equipment.setOperation("DISCHARGE");
            equipment.setStartTime(start);
            equipment.setEndTime(end);
            equipment.setMasterOperator(new MasterOperator(position[9]));

            equipment2.setContNo(position[1]);
            equipment2.setEquipmentActCode("H/T");
            equipment2.setOperation("DISCHARGE");
            equipment2.setMasterEquipment(new MasterEquipment(position[6]));
            equipment2.setPlanningVessel(new PlanningVessel(position[0]));
            equipment2.setStartTime(end);
            equipment2.setMasterOperator(new MasterOperator(position[10]));

            //update
            try {
                tempContDischargeFacadeRemote.edit(tempContDischarge);
                equipmentFacadeRemote.edit(equipment);
                equipmentFacadeRemote.edit(equipment2);
            } catch (EJBException e) {
                status = e.toString();
            }
        }
        return status;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "updatePosCont")
    public String updatePosCont(@WebParam(name = "parameter") String[] parameter, @WebParam(name = "type") String type) {
        UserUtil.setCurrentUser((String) parameter[11]);
        //type 1 = cy_lift_off -> confirm service_cont_disc | update pos 02 -> 03 | update end time H/T DISCHARGE | input equipment
        //type 2 = trans_lift_off -> confirm service_cont_trans | update pos 02 -> 03 | update end time H/T TRANSHIPMENT | input equipment
        //type 3 = cy_lift_on -> confirm service_cont_load | update pos 03 -> 02 | input equipment 2x | copy dari plan_cont_load -> serv_cont_load
        //type 4 = load_confirm -> confirm service_cont_load | update serv_cont_load pos 02 -> 01 | update end time H/T | input equipment
        //type 5 = delivery_confirm -> update is delivery from serv_cont_disch | input equipment
        //type 6 = trans_lift_on -> confirm service_cont_load | update pos 03 -> 02 | input equipment 2x | copy dari plan_cont_load -> serv_cont_load (trans)
        //type 7 = receiving_confirm
        //type 8 = discharge confirm UC
        //type 9 = Load Conf UC
        //type 10 = Hatch Move
        //type 11 = shifting with plan
        //type 12 = reshipping with plan
        //type 13 = shifting discharge
        //type 14 = lift off UC
        //type 15 = lift on UC
        //type 16 = delivery UC
        //type 17 = receiving UC
        //type 18,19 = Shiting UC without / with plan
        //type 20 = reshipping UC
        //type 22 = cancel loading
        //type 23 = cancel loading deliver

        Date start, end;
        starOver();

        if (type.equals("1")) {
            /**
             * replaced by @LiftOffConfirm.liftOffConfirm
             */
            start = ParseDate.changeDate(parameter[7]);
            end = ParseDate.changeDate(parameter[8]);

            container = serviceContDischargeFacadeRemote.findByContNo(parameter[1], "02");
            containerPlug = serviceShiftingFacadeRemote.findByPpkbLiftOffeConfirmSelectHht(parameter[1], "02");
            if (container != null) {
                masterYard.setBlock(parameter[2]);

                serviceContDischarge = serviceContDischargeFacadeRemote.find(container[5]);
                serviceContDischarge.setMasterYard(masterYard);
                serviceContDischarge.setYRow(Short.parseShort(parameter[3]));
                serviceContDischarge.setYSlot(Short.parseShort(parameter[4]));
                serviceContDischarge.setYTier(Short.parseShort(parameter[5]));
                serviceContDischarge.setPosition("03");

                serviceVessel.setNoPpkb(serviceContDischarge.getServiceVessel().getNoPpkb());

                masterEquipment.setEquipCode(parameter[0]);

                equipment.setPlanningVessel(serviceVessel.getPlanningVessel());
                equipment.setMasterEquipment(masterEquipment);
                equipment.setContNo(parameter[1]);
                equipment.setMasterOperator(new MasterOperator(parameter[9]));
                equipment.setEquipmentActCode("LIFTOFF");
                equipment.setOperation("DISCHARGE");
                equipment.setStartTime(start);
                equipment.setEndTime(end);

                Integer PK2 = equipmentFacadeRemote.findByIdContainer(serviceContDischarge.getServiceVessel().getNoPpkb(), parameter[1], "H/T", "DISCHARGE");

                System.out.println(PK2);

                equipment2 = equipmentFacadeRemote.find(PK2);
                equipment2.setEndTime(start);

                serviceVessel2 = serviceVesselFacadeRemote.find(serviceContDischarge.getServiceVessel().getNoPpkb());
                short counter = (short) (serviceVessel2.getDischarge() + 1);
                serviceVessel2.setDischarge(counter);

                //do the business
                //TODO mekanisme mencari kontainer ukuran 40 dan harus di alokasikan 2 di master yard coor
                Object[] coorStatus = masterYardCoordinatFacadeRemote.findIdStatusByCoordinat(parameter[2], Short.parseShort(parameter[4]), Short.parseShort(parameter[3]), Short.parseShort(parameter[5]));
                Object[] coorStatus2 = masterYardCoordinatFacadeRemote.findIdStatusByCoordinat(parameter[2], (short) (Short.parseShort(parameter[4]) + 1), Short.parseShort(parameter[3]), Short.parseShort(parameter[5]));

                Boolean isBottomExist = cyVlaidate.isBottomExist(parameter[2], Short.parseShort(parameter[4]), Short.parseShort(parameter[3]), Short.parseShort(parameter[5]), serviceContDischarge.getContSize());

                if (isBottomExist) {
                    masterYardCoordinats = masterYardCoordinatFacadeRemote.findByContNo(parameter[1]);
                    System.out.println(masterYardCoordinats.size());
                    int count = masterYardCoordinats.size();
                    if (count > 0) {
                        for (Object[] tempObj : masterYardCoordinats) {
                            int cont_no = (Integer) tempObj[0];
                            masterYardCoordinat = masterYardCoordinatFacadeRemote.find(cont_no);
                            masterYardCoordinat.setContNo(null);
                            masterYardCoordinat.setContSize(null);
                            masterYardCoordinat.setContType(null);
                            masterYardCoordinat.setContWeight(null);
                            masterYardCoordinat.setGrossClass(null);
                            masterYardCoordinat.setStatus("empty");
                            masterYardCoordinat.setNoPpkb(null);
                            masterYardCoordinatFacadeRemote.edit(masterYardCoordinat);
                        }
                    }

                    switch (serviceContDischarge.getContSize()) {
                        case CONTAINER_TYPE_40:
                            if (coorStatus2 == null) {
                                status = ALERT_1;
                            } else {
                                if ((coorStatus[1].equals("empty") || coorStatus[1].equals("planning")) && (coorStatus2[1].equals("empty") || coorStatus2[1].equals("planning"))) {
                                    masterYardCoordinat = masterYardCoordinatFacadeRemote.find(coorStatus[0]);
                                    masterYardCoordinat.setStatus("exist");
                                    masterYardCoordinat.setContNo(serviceContDischarge.getContNo());
                                    masterYardCoordinat.setContSize(serviceContDischarge.getContSize());
                                    masterYardCoordinat.setContType(serviceContDischarge.getMasterContainerType().getContType().toString());
                                    masterYardCoordinat.setContWeight(Integer.parseInt(serviceContDischarge.getContGross().toString()));
                                    masterYardCoordinat.setGrossClass(serviceContDischarge.getGrossClass());
                                    masterYardCoordinat.setNoPpkb(serviceContDischarge.getServiceVessel().getNoPpkb());

                                    masterYardCoordinat2 = masterYardCoordinatFacadeRemote.find(coorStatus2[0]);
                                    masterYardCoordinat2.setStatus("exist");
                                    masterYardCoordinat2.setContNo(serviceContDischarge.getContNo());
                                    masterYardCoordinat2.setContSize(serviceContDischarge.getContSize());
                                    masterYardCoordinat2.setContType(serviceContDischarge.getMasterContainerType().getContType().toString());
                                    masterYardCoordinat2.setContWeight(Integer.parseInt(serviceContDischarge.getContGross().toString()));
                                    masterYardCoordinat2.setGrossClass(serviceContDischarge.getGrossClass());
                                    masterYardCoordinat2.setNoPpkb(serviceContDischarge.getServiceVessel().getNoPpkb());
                                    //TODO rubah audit trail equipment
                                    try {
                                        serviceContDischargeFacadeRemote.edit(serviceContDischarge);
                                        serviceVesselFacadeRemote.edit(serviceVessel2);
                                        equipmentFacadeRemote.edit(equipment);
                                        equipmentFacadeRemote.edit(equipment2);
                                        masterYardCoordinatFacadeRemote.edit(masterYardCoordinat);
                                        masterYardCoordinatFacadeRemote.edit(masterYardCoordinat2);
                                    } catch (EJBException e) {
                                        status = e.toString();
                                    }

                                } else {
                                    status = ALERT_1;
                                }
                            }
                            break;
                        case CONTAINER_TYPE_20:
                            if (coorStatus[1].equals("empty") || coorStatus[1].equals("planning")) {
                                masterYardCoordinat = masterYardCoordinatFacadeRemote.find(coorStatus[0]);
                                masterYardCoordinat.setStatus("exist");
                                masterYardCoordinat.setContNo(serviceContDischarge.getContNo());
                                masterYardCoordinat.setContSize(serviceContDischarge.getContSize());
                                masterYardCoordinat.setContType(serviceContDischarge.getMasterContainerType().getContType().toString());
                                masterYardCoordinat.setContWeight(Integer.parseInt(serviceContDischarge.getContGross().toString()));
                                masterYardCoordinat.setGrossClass(serviceContDischarge.getGrossClass());
                                masterYardCoordinat.setNoPpkb(serviceContDischarge.getServiceVessel().getNoPpkb());
                                try {
                                    serviceContDischargeFacadeRemote.edit(serviceContDischarge);
                                    serviceVesselFacadeRemote.edit(serviceVessel2);
                                    equipmentFacadeRemote.edit(equipment);
                                    equipmentFacadeRemote.edit(equipment2);
                                    masterYardCoordinatFacadeRemote.edit(masterYardCoordinat);
                                } catch (EJBException e) {
                                    status = e.toString();
                                }
                            } else {
                                status = ALERT_1;
                            }
                            break;
                    }
                } else {
                    status = ALERT_2;
                }
            } else if (containerPlug != null) {

                ServiceShifting sc = new ServiceShifting();
                PlanningShiftDischarge ps = new PlanningShiftDischarge();

                masterYard.setBlock(parameter[2]);
                sc = serviceShiftingFacadeRemote.find(containerPlug[13]);
                ps = planningShiftDischargeFacadeRemote.find(containerPlug[5]);
                ps.setMasterYard(masterYard);
                ps.setYRow(Short.parseShort(parameter[3]));
                ps.setYSlot(Short.parseShort(parameter[4]));
                ps.setYTier(Short.parseShort(parameter[5]));
                sc.setPlacementDate(start);
                sc.setPosition("03");

                serviceVessel.setNoPpkb(sc.getServiceVessel().getNoPpkb());

                masterEquipment.setEquipCode(parameter[0]);

                equipment.setPlanningVessel(serviceVessel.getPlanningVessel());
                equipment.setMasterEquipment(masterEquipment);
                equipment.setContNo(parameter[1]);
                equipment.setMasterOperator(new MasterOperator(parameter[9]));
                equipment.setEquipmentActCode("LIFTOFF");
                equipment.setOperation("SHIFTING-CY");
                equipment.setStartTime(start);
                equipment.setEndTime(end);

                Integer PK2 = equipmentFacadeRemote.findByIdContainer(sc.getServiceVessel().getNoPpkb(), parameter[1], "H/T", "SHIFTING-CY");

                System.out.println(PK2);

                equipment2 = equipmentFacadeRemote.find(PK2);
                equipment2.setEndTime(start);

                serviceVessel2 = serviceVesselFacadeRemote.find(sc.getServiceVessel().getNoPpkb());
                short counter = (short) (serviceVessel2.getDischarge() + 1);
                serviceVessel2.setDischarge(counter);

                //do the business
                //TODO mekanisme mencari kontainer ukuran 40 dan harus di alokasikan 2 di master yard coor
                Object[] coorStatus = masterYardCoordinatFacadeRemote.findIdStatusByCoordinat(parameter[2], Short.parseShort(parameter[4]), Short.parseShort(parameter[3]), Short.parseShort(parameter[5]));
                Object[] coorStatus2 = masterYardCoordinatFacadeRemote.findIdStatusByCoordinat(parameter[2], (short) (Short.parseShort(parameter[4]) + 1), Short.parseShort(parameter[3]), Short.parseShort(parameter[5]));

                Boolean isBottomExist = cyVlaidate.isBottomExist(parameter[2], Short.parseShort(parameter[4]), Short.parseShort(parameter[3]), Short.parseShort(parameter[5]), sc.getContSize());

                if (isBottomExist) {
                    masterYardCoordinats = masterYardCoordinatFacadeRemote.findByContNo(parameter[1]);
                    System.out.println(masterYardCoordinats.size());
                    int count = masterYardCoordinats.size();
                    if (count > 0) {
                        for (Object[] tempObj : masterYardCoordinats) {
                            int cont_no = (Integer) tempObj[0];
                            masterYardCoordinat = masterYardCoordinatFacadeRemote.find(cont_no);
                            masterYardCoordinat.setContNo(null);
                            masterYardCoordinat.setContSize(null);
                            masterYardCoordinat.setContType(null);
                            masterYardCoordinat.setContWeight(null);
                            masterYardCoordinat.setGrossClass(null);
                            masterYardCoordinat.setStatus("empty");
                            masterYardCoordinat.setNoPpkb(null);
                            masterYardCoordinatFacadeRemote.edit(masterYardCoordinat);
                        }
                    }

                    switch (sc.getContSize()) {
                        case CONTAINER_TYPE_40:
                            if (coorStatus2 == null) {
                                status = ALERT_1;
                            } else {
                                if ((coorStatus[1].equals("empty") || coorStatus[1].equals("planning")) && (coorStatus2[1].equals("empty") || coorStatus2[1].equals("planning"))) {
                                    masterYardCoordinat = masterYardCoordinatFacadeRemote.find(coorStatus[0]);
                                    masterYardCoordinat.setStatus("exist");
                                    masterYardCoordinat.setContNo(sc.getContNo());
                                    masterYardCoordinat.setContSize(sc.getContSize());
                                    masterYardCoordinat.setContType(sc.getMasterContainerType().getContType().toString());
                                    masterYardCoordinat.setContWeight(Integer.parseInt(sc.getContGross().toString()));
                                    masterYardCoordinat.setGrossClass(ps.getGrossClass());
                                    masterYardCoordinat.setNoPpkb(sc.getServiceVessel().getNoPpkb());

                                    masterYardCoordinat2 = masterYardCoordinatFacadeRemote.find(coorStatus2[0]);
                                    masterYardCoordinat2.setStatus("exist");
                                    masterYardCoordinat2.setContNo(sc.getContNo());
                                    masterYardCoordinat2.setContSize(sc.getContSize());
                                    masterYardCoordinat2.setContType(sc.getMasterContainerType().getContType().toString());
                                    masterYardCoordinat2.setContWeight(Integer.parseInt(sc.getContGross().toString()));
                                    masterYardCoordinat2.setGrossClass(ps.getGrossClass());
                                    masterYardCoordinat2.setNoPpkb(sc.getServiceVessel().getNoPpkb());
                                    //TODO rubah audit trail equipment
                                    try {
                                        serviceShiftingFacadeRemote.edit(sc);
                                        planningShiftDischargeFacadeRemote.edit(ps);
                                        serviceVesselFacadeRemote.edit(serviceVessel2);
                                        equipmentFacadeRemote.edit(equipment);
                                        equipmentFacadeRemote.edit(equipment2);
                                        masterYardCoordinatFacadeRemote.edit(masterYardCoordinat);
                                        masterYardCoordinatFacadeRemote.edit(masterYardCoordinat2);
                                    } catch (EJBException e) {
                                        status = e.toString();
                                    }

                                } else {
                                    status = ALERT_1;
                                }
                            }
                            break;
                        case CONTAINER_TYPE_20:
                            if (coorStatus[1].equals("empty") || coorStatus[1].equals("planning")) {
                                masterYardCoordinat = masterYardCoordinatFacadeRemote.find(coorStatus[0]);
                                masterYardCoordinat.setStatus("exist");
                                masterYardCoordinat.setContNo(sc.getContNo());
                                masterYardCoordinat.setContSize(sc.getContSize());
                                masterYardCoordinat.setContType(sc.getMasterContainerType().getContType().toString());
                                masterYardCoordinat.setContWeight(Integer.parseInt(sc.getContGross().toString()));
                                masterYardCoordinat.setGrossClass(ps.getGrossClass());
                                masterYardCoordinat.setNoPpkb(sc.getServiceVessel().getNoPpkb());
                                try {
                                    serviceShiftingFacadeRemote.edit(sc);
                                    planningShiftDischargeFacadeRemote.edit(ps);
                                    serviceVesselFacadeRemote.edit(serviceVessel2);
                                    equipmentFacadeRemote.edit(equipment);
                                    equipmentFacadeRemote.edit(equipment2);
                                    masterYardCoordinatFacadeRemote.edit(masterYardCoordinat);
                                } catch (EJBException e) {
                                    status = e.toString();
                                }
                            } else {
                                status = ALERT_1;
                            }
                            break;
                    }
                } else {
                    status = ALERT_2;
                }
            }
        } else if (type.equals("2")) {
            start = ParseDate.changeDate(parameter[7]);
            end = ParseDate.changeDate(parameter[8]);

            contServicing = serviceContTranshipmentFacadeRemote.findByContNo(parameter[1], "02");

            masterYard.setBlock(parameter[2]);

            serviceContTranshipment = serviceContTranshipmentFacadeRemote.find(contServicing[0]);
            serviceContTranshipment.setMasterYard(masterYard);
            serviceContTranshipment.setYRow(Short.parseShort(parameter[3]));
            serviceContTranshipment.setYSlot(Short.parseShort(parameter[4]));
            serviceContTranshipment.setYTier(Short.parseShort(parameter[5]));
            serviceContTranshipment.setStartPlacementDate(start);
            serviceContTranshipment.setPosition("03");

            serviceVessel.setNoPpkb(serviceContTranshipment.getServiceVessel().getNoPpkb());

            masterEquipment.setEquipCode(parameter[6]);

            equipment.setPlanningVessel(serviceVessel.getPlanningVessel());
            equipment.setMasterEquipment(masterEquipment);
            equipment.setContNo(parameter[1]);
            equipment.setEquipmentActCode("LIFTOFF");
            equipment.setOperation("TRANSDISCHARGE");
            equipment.setStartTime(start);
            equipment.setEndTime(end);
            equipment.setMasterOperator(new MasterOperator(parameter[9]));

            Integer PK = equipmentFacadeRemote.findByIdContainer(serviceContTranshipment.getServiceVessel().getNoPpkb(), parameter[1], "H/T", "TRANSDISCHARGE");

            equipment2 = equipmentFacadeRemote.find(PK);
            equipment2.setEndTime(start);

            String grs = GrossConverter.convert(serviceContTranshipment.getContSize(), serviceContTranshipment.getContGross());

            //do the business
            //TODO mekanisme mencari kontainer ukuran 40 dan harus di alokasikan 2 di master yard coor
            Object[] coorStatus = masterYardCoordinatFacadeRemote.findIdStatusByCoordinat(parameter[2], Short.parseShort(parameter[4]), Short.parseShort(parameter[3]), Short.parseShort(parameter[5]));
            Object[] coorStatus2 = masterYardCoordinatFacadeRemote.findIdStatusByCoordinat(parameter[2], (short) (Short.parseShort(parameter[4]) + 1), Short.parseShort(parameter[3]), Short.parseShort(parameter[5]));

            Boolean isBottomExist = cyVlaidate.isBottomExist(parameter[2], Short.parseShort(parameter[4]), Short.parseShort(parameter[3]), Short.parseShort(parameter[5]), serviceContTranshipment.getContSize());

            if (isBottomExist) {
                masterYardCoordinats = masterYardCoordinatFacadeRemote.findByContNo(parameter[1]);
                int count = masterYardCoordinats.size();
                if (count > 0) {
                    for (Object[] tempObj : masterYardCoordinats) {
                        int cont_no = (Integer) tempObj[0];
                        masterYardCoordinat = masterYardCoordinatFacadeRemote.find(cont_no);
                        masterYardCoordinat.setContNo(null);
                        masterYardCoordinat.setContSize(null);
                        masterYardCoordinat.setContType(null);
                        masterYardCoordinat.setContWeight(null);
                        masterYardCoordinat.setGrossClass(null);
                        masterYardCoordinat.setStatus("empty");
                        masterYardCoordinat.setNoPpkb(null);
                        masterYardCoordinatFacadeRemote.edit(masterYardCoordinat);
                    }
                }

                switch (serviceContTranshipment.getContSize()) {
                    case CONTAINER_TYPE_40:
                        if (coorStatus2 == null) {
                            status = ALERT_1;
                        } else {
                            if ((coorStatus[1].equals("empty") || coorStatus[1].equals("planning")) && (coorStatus2[1].equals("empty") || coorStatus2[1].equals("planning"))) {
                                masterYardCoordinat = masterYardCoordinatFacadeRemote.find(coorStatus[0]);
                                masterYardCoordinat.setStatus("exist");
                                masterYardCoordinat.setContNo(serviceContTranshipment.getContNo());
                                masterYardCoordinat.setContSize(serviceContTranshipment.getContSize());
                                masterYardCoordinat.setContType(serviceContTranshipment.getMasterContainerType().getContType().toString());
                                masterYardCoordinat.setContWeight(Integer.parseInt(serviceContTranshipment.getContGross().toString()));
                                masterYardCoordinat.setGrossClass(grs);
                                masterYardCoordinat.setNoPpkb(serviceContTranshipment.getServiceVessel().getNoPpkb());

                                masterYardCoordinat2 = masterYardCoordinatFacadeRemote.find(coorStatus2[0]);
                                masterYardCoordinat2.setStatus("exist");
                                masterYardCoordinat2.setContNo(serviceContTranshipment.getContNo());
                                masterYardCoordinat2.setContSize(serviceContTranshipment.getContSize());
                                masterYardCoordinat2.setContType(serviceContTranshipment.getMasterContainerType().getContType().toString());
                                masterYardCoordinat2.setContWeight(Integer.parseInt(serviceContTranshipment.getContGross().toString()));
                                masterYardCoordinat2.setGrossClass(grs);
                                masterYardCoordinat2.setNoPpkb(serviceContTranshipment.getServiceVessel().getNoPpkb());

                                try {
                                    serviceContTranshipmentFacadeRemote.edit(serviceContTranshipment);
                                    equipmentFacadeRemote.edit(equipment);
                                    equipmentFacadeRemote.edit(equipment2);
                                    masterYardCoordinatFacadeRemote.edit(masterYardCoordinat);
                                    masterYardCoordinatFacadeRemote.edit(masterYardCoordinat2);
                                } catch (EJBException e) {
                                    status = e.toString();
                                }

                            } else {
                                status = ALERT_1;
                            }
                        }
                        break;
                    case CONTAINER_TYPE_20:
                        if (coorStatus[1].equals("empty") || coorStatus[1].equals("planning")) {
                            masterYardCoordinat = masterYardCoordinatFacadeRemote.find(coorStatus[0]);
                            masterYardCoordinat.setStatus("exist");
                            masterYardCoordinat.setContNo(serviceContTranshipment.getContNo());
                            masterYardCoordinat.setContSize(serviceContTranshipment.getContSize());
                            masterYardCoordinat.setContType(serviceContTranshipment.getMasterContainerType().getContType().toString());
                            masterYardCoordinat.setContWeight(Integer.parseInt(serviceContTranshipment.getContGross().toString()));
                            masterYardCoordinat.setGrossClass(grs);
                            masterYardCoordinat.setNoPpkb(serviceContTranshipment.getServiceVessel().getNoPpkb());
                            try {
                                serviceContTranshipmentFacadeRemote.edit(serviceContTranshipment);
                                equipmentFacadeRemote.edit(equipment);
                                equipmentFacadeRemote.edit(equipment2);
                                masterYardCoordinatFacadeRemote.edit(masterYardCoordinat);
                            } catch (EJBException e) {
                                status = e.toString();
                            }
                        } else {
                            status = ALERT_1;
                        }
                        break;
                }
            } else {
                status = ALERT_2;
            }
        } else if (type.equals("3")) {
            /**
             * replaced by @LiftOnConfirm.liftOnConfirm
             */
            start = ParseDate.changeDate(parameter[8]);
            end = ParseDate.changeDate(parameter[9]);

            Date now = Calendar.getInstance().getTime();

            container = planningContLoadFacadeRemote.findByContNo(parameter[1], "03", false);
            containerPlug = serviceShiftingFacadeRemote.findByPpkbLiftOnConfirmSelectHht(parameter[1], "03");

            //update is lift on true
            if (container != null) {
                Object[] dump = serviceReceivingFacadeRemote.findByPpkbAndContNo(parameter[0], parameter[1]);
                serviceReceiving = serviceReceivingFacadeRemote.find(dump[0]);
                serviceReceiving.setIsLifton("TRUE");
                serviceReceivingFacadeRemote.edit(serviceReceiving);

                //update planning
                planningContLoad = planningContLoadFacadeRemote.find(container[0]);
                planningContLoad.setPosition("02");


                serviceVessel.setNoPpkb(parameter[0]);
                masterYard.setBlock(planningContLoad.getMasterYard().getBlock());
                masterCommodity.setCommodityCode(planningContLoad.getMasterCommodity().getCommodityCode());
                masterContainerType.setContType(planningContLoad.getMasterContainerType().getContType());

                //copy ke serv cont load
                serviceContLoad.setContNo(planningContLoad.getContNo());
                serviceContLoad.setServiceVessel(serviceVessel);
                serviceContLoad.setContSize(planningContLoad.getContSize());
                serviceContLoad.setContGross(planningContLoad.getContGross());
                serviceContLoad.setContStatus(planningContLoad.getContStatus());
                serviceContLoad.setDangerous(planningContLoad.getDg());
                serviceContLoad.setDgLabel(planningContLoad.getDgLabel());
                serviceContLoad.setIsTranshipment(planningContLoad.getIsTranshipment());
                serviceContLoad.setMasterCommodity(masterCommodity);
                serviceContLoad.setMasterContainerType(masterContainerType);
                serviceContLoad.setOverSize(planningContLoad.getOverSize());
                serviceContLoad.setSealNo(planningContLoad.getSealNo());
                serviceContLoad.setYRow(planningContLoad.getYRow());
                serviceContLoad.setYSlot(planningContLoad.getYSlot());
                serviceContLoad.setYTier(planningContLoad.getYTier());
                serviceContLoad.setVBay(planningContLoad.getVBay());
                serviceContLoad.setVRow(planningContLoad.getVRow());
                serviceContLoad.setVTier(planningContLoad.getVTier());
                serviceContLoad.setTransactionDate(now);
                serviceContLoad.setMasterYard(masterYard);
                serviceContLoad.setPosition("02");
                serviceContLoad.setBlNo(planningContLoad.getBlNo());
                serviceContLoad.setStatusCancelLoading("FALSE");
                masterEquipment.setEquipCode(parameter[7]);
                masterEquipment2.setEquipCode(parameter[6]);

                equipment.setContNo(parameter[1]);
                equipment.setMasterEquipment(masterEquipment);
                equipment.setStartTime(start);
                equipment.setEndTime(end);
                equipment.setPlanningVessel(serviceVessel.getPlanningVessel());
                equipment.setEquipmentActCode("LIFTON");
                equipment.setOperation("LOAD");
                equipment.setMasterOperator(new MasterOperator(parameter[11]));

                equipment2.setContNo(parameter[1]);
                equipment2.setMasterEquipment(masterEquipment2);
                equipment2.setStartTime(end);
                equipment2.setPlanningVessel(serviceVessel.getPlanningVessel());
                equipment2.setEquipmentActCode("H/T");
                equipment2.setOperation("LOAD");
                equipment2.setMasterOperator(new MasterOperator(parameter[10]));

                //update date setEndStorageDate di receiving service

                String jobslip = receivingServiceFacadeRemote.findReceivingServiceByUpdateDate(parameter[0], planningContLoad.getContNo());
                receivingService = receivingServiceFacadeRemote.find(jobslip);
                receivingService.setEndStorageDate(start);

                Object[] coorYard, coorYard2;

                Boolean isTopExist = cyVlaidate.isTopExist(serviceContLoad.getMasterYard().getBlock(), serviceContLoad.getYSlot(), serviceContLoad.getYRow(), serviceContLoad.getYTier(), serviceContLoad.getContSize());

                if (!isTopExist) {
                    switch (serviceContLoad.getContSize()) {
                        case CONTAINER_TYPE_40:
                            coorYard = masterYardCoordinatFacadeRemote.findIdStatusByCoordinat(serviceContLoad.getMasterYard().getBlock(), serviceContLoad.getYSlot(), serviceContLoad.getYRow(), serviceContLoad.getYTier());
                            masterYardCoordinat = masterYardCoordinatFacadeRemote.find(coorYard[0]);
                            masterYardCoordinat.setStatus("empty");
                            masterYardCoordinat.setContNo(null);
                            masterYardCoordinat.setContSize(null);
                            masterYardCoordinat.setContType(null);
                            masterYardCoordinat.setGrossClass(null);
                            masterYardCoordinat.setContWeight(null);
                            masterYardCoordinat.setNoPpkb(null);

                            coorYard2 = masterYardCoordinatFacadeRemote.findIdStatusByCoordinat(serviceContLoad.getMasterYard().getBlock(), (short) ((short) serviceContLoad.getYSlot() + 1), serviceContLoad.getYRow(), serviceContLoad.getYTier());
                            masterYardCoordinat2 = masterYardCoordinatFacadeRemote.find(coorYard2[0]);
                            masterYardCoordinat2.setStatus("empty");
                            masterYardCoordinat2.setContNo(null);
                            masterYardCoordinat2.setContSize(null);
                            masterYardCoordinat2.setContType(null);
                            masterYardCoordinat2.setGrossClass(null);
                            masterYardCoordinat2.setContWeight(null);
                            masterYardCoordinat2.setNoPpkb(null);

                            //do the business!
                            try {
                                planningContLoadFacadeRemote.edit(planningContLoad);
                                serviceContLoadFacadeRemote.edit(serviceContLoad);
                                equipmentFacadeRemote.edit(equipment);
                                equipmentFacadeRemote.edit(equipment2);
                                receivingServiceFacadeRemote.edit(receivingService);
                                masterYardCoordinatFacadeRemote.edit(masterYardCoordinat);
                                masterYardCoordinatFacadeRemote.edit(masterYardCoordinat2);
                            } catch (EJBException e) {
                                status = e.toString();
                            }
                            break;
                        case CONTAINER_TYPE_20:
                            coorYard = masterYardCoordinatFacadeRemote.findIdStatusByCoordinat(serviceContLoad.getMasterYard().getBlock(), serviceContLoad.getYSlot(), serviceContLoad.getYRow(), serviceContLoad.getYTier());
                            masterYardCoordinat = masterYardCoordinatFacadeRemote.find(coorYard[0]);
                            masterYardCoordinat.setStatus("empty");
                            masterYardCoordinat.setContNo(null);
                            masterYardCoordinat.setContSize(null);
                            masterYardCoordinat.setContType(null);
                            masterYardCoordinat.setGrossClass(null);
                            masterYardCoordinat.setContWeight(null);
                            masterYardCoordinat.setNoPpkb(null);

                            //do the business!
                            try {
                                planningContLoadFacadeRemote.edit(planningContLoad);
                                serviceContLoadFacadeRemote.edit(serviceContLoad);
                                receivingServiceFacadeRemote.edit(receivingService);
                                equipmentFacadeRemote.edit(equipment);
                                equipmentFacadeRemote.edit(equipment2);
                                masterYardCoordinatFacadeRemote.edit(masterYardCoordinat);

                            } catch (EJBException e) {
                                status = e.toString();
                            }
                            break;
                    }
                } else {
                    status = ALERT_2;
                }
            } else if (containerPlug != null) {
                PlanningShiftDischarge ps = new PlanningShiftDischarge();
                ServiceShifting sc = new ServiceShifting();
                ps = planningShiftDischargeFacadeRemote.find(containerPlug[0]);
                sc = serviceShiftingFacadeRemote.find(containerPlug[7]);

                sc.setPosition("04");
                //update planning


                serviceVessel.setNoPpkb(parameter[0]);
                masterYard.setBlock(ps.getMasterYard().getBlock());
                masterCommodity.setCommodityCode(ps.getMasterCommodity().getCommodityCode());
                masterContainerType.setContType(ps.getMasterContainerType().getContType());


                masterEquipment.setEquipCode(parameter[7]);
                masterEquipment2.setEquipCode(parameter[6]);

                equipment.setContNo(parameter[1]);
                equipment.setMasterEquipment(masterEquipment);
                equipment.setStartTime(start);
                equipment.setEndTime(end);
                equipment.setPlanningVessel(serviceVessel.getPlanningVessel());
                equipment.setEquipmentActCode("LIFTON");
                equipment.setOperation("LD-SHIFTING-CY");
                equipment.setMasterOperator(new MasterOperator(parameter[11]));

                equipment2.setContNo(parameter[1]);
                equipment2.setMasterEquipment(masterEquipment2);
                equipment2.setStartTime(end);
                equipment2.setPlanningVessel(serviceVessel.getPlanningVessel());
                equipment2.setEquipmentActCode("H/T");
                equipment2.setOperation("LD-SHIFTING-CY");
                equipment2.setMasterOperator(new MasterOperator(parameter[10]));


                Object[] coorYard, coorYard2;

                Boolean isTopExist = cyVlaidate.isTopExist(ps.getMasterYard().getBlock(), ps.getYSlot(), ps.getYRow(), ps.getYTier(), ps.getContSize());

                if (!isTopExist) {
                    switch (ps.getContSize()) {
                        case CONTAINER_TYPE_40:
                            coorYard = masterYardCoordinatFacadeRemote.findIdStatusByCoordinat(ps.getMasterYard().getBlock(), ps.getYSlot(), ps.getYRow(), ps.getYTier());
                            masterYardCoordinat = masterYardCoordinatFacadeRemote.find(coorYard[0]);
                            masterYardCoordinat.setStatus("empty");
                            masterYardCoordinat.setContNo(null);
                            masterYardCoordinat.setContSize(null);
                            masterYardCoordinat.setContType(null);
                            masterYardCoordinat.setGrossClass(null);
                            masterYardCoordinat.setContWeight(null);
                            masterYardCoordinat.setNoPpkb(null);

                            coorYard2 = masterYardCoordinatFacadeRemote.findIdStatusByCoordinat(ps.getMasterYard().getBlock(), (short) ((short) ps.getYSlot() + 1), ps.getYRow(), ps.getYTier());
                            masterYardCoordinat2 = masterYardCoordinatFacadeRemote.find(coorYard2[0]);
                            masterYardCoordinat2.setStatus("empty");
                            masterYardCoordinat2.setContNo(null);
                            masterYardCoordinat2.setContSize(null);
                            masterYardCoordinat2.setContType(null);
                            masterYardCoordinat2.setGrossClass(null);
                            masterYardCoordinat2.setContWeight(null);
                            masterYardCoordinat2.setNoPpkb(null);

                            //do the business!
                            try {
                                serviceShiftingFacadeRemote.edit(sc);
                                planningShiftDischargeFacadeRemote.edit(ps);
                                equipmentFacadeRemote.edit(equipment);
                                equipmentFacadeRemote.edit(equipment2);
                                masterYardCoordinatFacadeRemote.edit(masterYardCoordinat);
                                masterYardCoordinatFacadeRemote.edit(masterYardCoordinat2);
                            } catch (EJBException e) {
                                status = e.toString();
                            }
                            break;
                        case CONTAINER_TYPE_20:
                            coorYard = masterYardCoordinatFacadeRemote.findIdStatusByCoordinat(ps.getMasterYard().getBlock(), ps.getYSlot(), ps.getYRow(), ps.getYTier());
                            masterYardCoordinat = masterYardCoordinatFacadeRemote.find(coorYard[0]);
                            masterYardCoordinat.setStatus("empty");
                            masterYardCoordinat.setContNo(null);
                            masterYardCoordinat.setContSize(null);
                            masterYardCoordinat.setContType(null);
                            masterYardCoordinat.setGrossClass(null);
                            masterYardCoordinat.setContWeight(null);
                            masterYardCoordinat.setNoPpkb(null);

                            //do the business!
                            try {
                                serviceShiftingFacadeRemote.edit(sc);
                                planningShiftDischargeFacadeRemote.edit(ps);
                                equipmentFacadeRemote.edit(equipment);
                                equipmentFacadeRemote.edit(equipment2);
                                masterYardCoordinatFacadeRemote.edit(masterYardCoordinat);

                            } catch (EJBException e) {
                                status = e.toString();
                            }
                            break;
                    }
                } else {
                    status = ALERT_2;
                }
            }

        } else if (type.equals("4")) {
            /**
             * replaced by @LoadConfirm.stevedoringConfirm
             */
            start = ParseDate.changeDate(parameter[6]);
            end = ParseDate.changeDate(parameter[7]);

            Date now = Calendar.getInstance().getTime();

            container = serviceContLoadFacadeRemote.findByContNo(parameter[0], "02");
            containerPlug = serviceShiftingFacadeRemote.findByPpkbLoadConfirmSelectHht2(parameter[0], "04");
            if (container != null) {
                serviceContLoad = serviceContLoadFacadeRemote.find(container[0]);
                serviceContLoad.setVBay(Short.parseShort(parameter[2]));
                serviceContLoad.setVRow(Short.parseShort(parameter[3]));
                serviceContLoad.setVTier(Short.parseShort(parameter[4]));
                serviceContLoad.setTransactionDate(now);
                serviceContLoad.setPosition("01");
                if (parameter[5].equals("EQ005")) {
                    serviceContLoad.setCrane("S");
                } else {
                    serviceContLoad.setCrane("L");
                }

                //masterEquipment.setEquipCode(parameter[5]);

                //serviceVessel.setNoPpkb(parameter[1]);

                equipment.setMasterEquipment(new MasterEquipment(parameter[5]));
                equipment.setContNo(parameter[0]);
                equipment.setPlanningVessel(new PlanningVessel(parameter[1]));
                equipment.setEquipmentActCode("STEVEDORING");
                equipment.setOperation("LOAD");
                equipment.setStartTime(start);
                equipment.setEndTime(end);
                equipment.setMasterOperator(new MasterOperator(parameter[8]));

                Boolean is_transh = false;
                if(serviceContLoad.getIsTranshipment().equals("TRUE")) {
                    is_transh = true;
                }
                Integer PK;

                if (is_transh) {
                    PK = equipmentFacadeRemote.findByIdContainer(parameter[1], parameter[0], "H/T", "TRANSHIPMENT");
                } else {
                    PK = equipmentFacadeRemote.findByIdContainer(parameter[1], parameter[0], "H/T", "LOAD");
                }

                serviceVessel2 = serviceVesselFacadeRemote.find(parameter[1]);

                short counter = (short) (serviceVessel2.getLoad() + 1);
                serviceVessel2.setLoad(counter);
                //System.out.println(counter);

                equipment2 = equipmentFacadeRemote.find(PK);
                equipment2.setEndTime(start);

                //do the business shit!
                try {
                    serviceContLoadFacadeRemote.edit(serviceContLoad);
                    serviceVesselFacadeRemote.edit(serviceVessel2);
                    equipmentFacadeRemote.edit(equipment);
                    equipmentFacadeRemote.edit(equipment2);
                } catch (EJBException e) {
                    status = e.toString();
                }
            } else if(containerPlug!=null){
                ServiceShifting sc = new ServiceShifting();
                sc = serviceShiftingFacadeRemote.find(containerPlug[0]);
                sc.setVBay(Short.parseShort(parameter[2]));
                sc.setVRow(Short.parseShort(parameter[3]));
                sc.setVTier(Short.parseShort(parameter[4]));
                sc.setPosition("05");
                if (parameter[5].equals("EQ005")) {
                    sc.setCrane("S");
                } else {
                    sc.setCrane("L");
                }

                //masterEquipment.setEquipCode(parameter[5]);

                //serviceVessel.setNoPpkb(parameter[1]);

                equipment.setMasterEquipment(new MasterEquipment(parameter[5]));
                equipment.setContNo(parameter[0]);
                equipment.setPlanningVessel(new PlanningVessel(parameter[1]));
                equipment.setEquipmentActCode("STEVEDORING");
                equipment.setOperation("LD-SHIFTING-CY");
                equipment.setStartTime(start);
                equipment.setEndTime(end);
                equipment.setMasterOperator(new MasterOperator(parameter[8]));

                Integer PK;

                PK = equipmentFacadeRemote.findByIdContainer(parameter[1], parameter[0], "H/T", "LD-SHIFTING-CY");

                serviceVessel2 = serviceVesselFacadeRemote.find(parameter[1]);

                short counter = (short) (serviceVessel2.getLoad() + 1);
                serviceVessel2.setLoad(counter);
                //System.out.println(counter);

                equipment2 = equipmentFacadeRemote.find(PK);
                equipment2.setEndTime(start);

                //do the business shit!
                try {
                    serviceShiftingFacadeRemote.edit(sc);
                    equipmentFacadeRemote.edit(equipment);
                    equipmentFacadeRemote.edit(equipment2);
                } catch (EJBException e) {
                    status = e.toString();
                }
            }
        } else if (type.equals("5")) {
            /**
             * replaced by @DeliveryConfirm.deliveryConfirm
             */
            start = ParseDate.changeDate(parameter[3]);
            end = ParseDate.changeDate(parameter[4]);

            container = serviceContDischargeFacadeRemote.findDeliverableCont(parameter[0]);
            containerPlug = pluggingReeferServiceFacadeRemote.findPluggingReeferServiceByDeliveryConfirmHht(parameter[0]);

            if (container != null) {
                serviceContDischarge = serviceContDischargeFacadeRemote.find(container[0]);
                serviceContDischarge.setIsDelivery("TRUE");
                serviceContDischarge.setPosition("04");

                masterEquipment.setEquipCode(parameter[2]);

                serviceVessel.setNoPpkb(parameter[1]);

                equipment.setMasterEquipment(masterEquipment);
                equipment.setPlanningVessel(serviceVessel.getPlanningVessel());
                equipment.setContNo(parameter[0]);
                equipment.setEquipmentActCode("DELIVERY");
                equipment.setOperation("DISCHARGE");
                equipment.setStartTime(start);
                equipment.setEndTime(end);
                equipment.setMasterOperator(new MasterOperator(parameter[5]));

                Object[] coorYard, coorYard2;

                Boolean isTopExist = cyVlaidate.isTopExist(serviceContDischarge.getMasterYard().getBlock(), serviceContDischarge.getYSlot(), serviceContDischarge.getYRow(), serviceContDischarge.getYTier(), serviceContDischarge.getContSize());

                if (!isTopExist) {
                    serviceDelivery.setNoPpkb(parameter[1]);
                    serviceDelivery.setMasterCommodity(serviceContDischarge.getMasterCommodity());
                    serviceDelivery.setMasterContainerType(serviceContDischarge.getMasterContainerType());
                    serviceDelivery.setMasterYard(serviceContDischarge.getMasterYard());
                    serviceDelivery.setContNo(serviceContDischarge.getContNo());
                    serviceDelivery.setContSize(serviceContDischarge.getContSize());
                    serviceDelivery.setContStatus(serviceContDischarge.getContStatus());
                    serviceDelivery.setContGross(serviceContDischarge.getContGross());
                    serviceDelivery.setSealNo(serviceContDischarge.getSealNo());
                    serviceDelivery.setOverSize(serviceContDischarge.getOverSize());
                    serviceDelivery.setDangerous(serviceContDischarge.getDangerous());
                    serviceDelivery.setDgLabel(serviceContDischarge.getDgLabel());
                    serviceDelivery.setYSlot(serviceContDischarge.getYSlot());
                    serviceDelivery.setYRow(serviceContDischarge.getYRow());
                    serviceDelivery.setYTier(serviceContDischarge.getYTier());
                    serviceDelivery.setTransactionDate(Calendar.getInstance().getTime());
                    serviceDeliveryFacadeRemote.edit(serviceDelivery);

                    switch (serviceContDischarge.getContSize()) {
                        case CONTAINER_TYPE_40:
                            coorYard = masterYardCoordinatFacadeRemote.findIdStatusByCoordinat(serviceContDischarge.getMasterYard().getBlock(), serviceContDischarge.getYSlot(), serviceContDischarge.getYRow(), serviceContDischarge.getYTier());
                            masterYardCoordinat = masterYardCoordinatFacadeRemote.find(coorYard[0]);
                            masterYardCoordinat.setStatus("empty");
                            masterYardCoordinat.setContNo(null);
                            masterYardCoordinat.setContSize(null);
                            masterYardCoordinat.setContType(null);
                            masterYardCoordinat.setGrossClass(null);
                            masterYardCoordinat.setContWeight(null);
                            masterYardCoordinat.setNoPpkb(null);

                            coorYard2 = masterYardCoordinatFacadeRemote.findIdStatusByCoordinat(serviceContDischarge.getMasterYard().getBlock(), (short) ((short) serviceContDischarge.getYSlot() + 1), serviceContDischarge.getYRow(), serviceContDischarge.getYTier());
                            masterYardCoordinat2 = masterYardCoordinatFacadeRemote.find(coorYard2[0]);
                            masterYardCoordinat2.setStatus("empty");
                            masterYardCoordinat2.setContNo(null);
                            masterYardCoordinat2.setContSize(null);
                            masterYardCoordinat2.setContType(null);
                            masterYardCoordinat2.setGrossClass(null);
                            masterYardCoordinat2.setContWeight(null);
                            masterYardCoordinat2.setNoPpkb(null);

                            //do the business!
                            try {
                                serviceContDischargeFacadeRemote.edit(serviceContDischarge);
                                equipmentFacadeRemote.edit(equipment);
                                masterYardCoordinatFacadeRemote.edit(masterYardCoordinat);
                                masterYardCoordinatFacadeRemote.edit(masterYardCoordinat2);
                            } catch (EJBException e) {
                                status = e.toString();
                            }
                            break;
                        case CONTAINER_TYPE_20:
                            coorYard = masterYardCoordinatFacadeRemote.findIdStatusByCoordinat(serviceContDischarge.getMasterYard().getBlock(), serviceContDischarge.getYSlot(), serviceContDischarge.getYRow(), serviceContDischarge.getYTier());
                            masterYardCoordinat = masterYardCoordinatFacadeRemote.find(coorYard[0]);
                            masterYardCoordinat.setStatus("empty");
                            masterYardCoordinat.setContNo(null);
                            masterYardCoordinat.setContSize(null);
                            masterYardCoordinat.setContType(null);
                            masterYardCoordinat.setGrossClass(null);
                            masterYardCoordinat.setContWeight(null);
                            masterYardCoordinat.setNoPpkb(null);
                            //do the business!
                            try {
                                serviceContDischargeFacadeRemote.edit(serviceContDischarge);
                                equipmentFacadeRemote.edit(equipment);
                                masterYardCoordinatFacadeRemote.edit(masterYardCoordinat);
                            } catch (EJBException e) {
                                status = e.toString();
                            }
                            break;
                    }
                } else {
                    status = ALERT_2;
                }
            } else if (containerPlug != null) {
                serviceReceiving = new ServiceReceiving();
                pluggingReeferService = new PluggingReeferService();

                pluggingReeferService = pluggingReeferServiceFacadeRemote.find(containerPlug[0]);
                serviceReceiving = serviceReceivingFacadeRemote.find(containerPlug[10]);

                masterEquipment.setEquipCode(parameter[2]);

//                serviceVessel.setNoPpkb(parameter[1]);

                equipment.setMasterEquipment(masterEquipment);
                equipment.setNoPpkbPlug(pluggingReeferService.getNoPpkb());
                equipment.setContNo(parameter[0]);
                equipment.setEquipmentActCode("DELIVERY");
                equipment.setOperation("DISCHARGE");
                equipment.setStartTime(start);
                equipment.setEndTime(end);
                equipment.setMasterOperator(new MasterOperator(parameter[5]));

                Object[] coorYard, coorYard2;

                Boolean isTopExist = cyVlaidate.isTopExist(serviceReceiving.getMasterYard().getBlock(), serviceReceiving.getYSlot(), serviceReceiving.getYRow(), serviceReceiving.getYTier(), serviceReceiving.getContSize());

                if (!isTopExist) {
                    serviceDelivery.setNoPpkb(parameter[1]);
                    serviceDelivery.setMasterCommodity(serviceReceiving.getMasterCommodity());
                    serviceDelivery.setMasterContainerType(serviceReceiving.getMasterContainerType());
                    serviceDelivery.setMasterYard(serviceReceiving.getMasterYard());
                    serviceDelivery.setContNo(serviceReceiving.getContNo());
                    serviceDelivery.setContSize(serviceReceiving.getContSize());
                    serviceDelivery.setContStatus(serviceReceiving.getContStatus());
                    serviceDelivery.setContGross(serviceReceiving.getContGross());
                    serviceDelivery.setSealNo(serviceReceiving.getSealNo());
                    serviceDelivery.setOverSize(serviceReceiving.getOverSize());
                    serviceDelivery.setDangerous(serviceReceiving.getDangerous());
                    serviceDelivery.setDgLabel(serviceReceiving.getDgLabel());
                    serviceDelivery.setYSlot(serviceReceiving.getYSlot());
                    serviceDelivery.setYRow(serviceReceiving.getYRow());
                    serviceDelivery.setYTier(serviceReceiving.getYTier());
                    serviceDelivery.setTransactionDate(Calendar.getInstance().getTime());
                    serviceDeliveryFacadeRemote.edit(serviceDelivery);

                    pluggingReeferService.setStatus("04");
                    pluggingReeferServiceFacadeRemote.edit(pluggingReeferService);

                    switch (serviceReceiving.getContSize()) {
                        case CONTAINER_TYPE_40:
                            coorYard = masterYardCoordinatFacadeRemote.findIdStatusByCoordinat(serviceReceiving.getMasterYard().getBlock(), serviceReceiving.getYSlot(), serviceReceiving.getYRow(), serviceReceiving.getYTier());
                            masterYardCoordinat = masterYardCoordinatFacadeRemote.find(coorYard[0]);
                            masterYardCoordinat.setStatus("empty");
                            masterYardCoordinat.setContNo(null);
                            masterYardCoordinat.setContSize(null);
                            masterYardCoordinat.setContType(null);
                            masterYardCoordinat.setGrossClass(null);
                            masterYardCoordinat.setContWeight(null);
                            masterYardCoordinat.setNoPpkb(null);

                            coorYard2 = masterYardCoordinatFacadeRemote.findIdStatusByCoordinat(serviceReceiving.getMasterYard().getBlock(), (short) ((short) serviceReceiving.getYSlot() + 1), serviceReceiving.getYRow(), serviceReceiving.getYTier());
                            masterYardCoordinat2 = masterYardCoordinatFacadeRemote.find(coorYard2[0]);
                            masterYardCoordinat2.setStatus("empty");
                            masterYardCoordinat2.setContNo(null);
                            masterYardCoordinat2.setContSize(null);
                            masterYardCoordinat2.setContType(null);
                            masterYardCoordinat2.setGrossClass(null);
                            masterYardCoordinat2.setContWeight(null);
                            masterYardCoordinat2.setNoPpkb(null);

                            //do the business!
                            try {
                                equipmentFacadeRemote.edit(equipment);
                                masterYardCoordinatFacadeRemote.edit(masterYardCoordinat);
                                masterYardCoordinatFacadeRemote.edit(masterYardCoordinat2);
                            } catch (EJBException e) {
                                status = e.toString();
                            }
                            break;
                        case CONTAINER_TYPE_20:
                            coorYard = masterYardCoordinatFacadeRemote.findIdStatusByCoordinat(serviceReceiving.getMasterYard().getBlock(), serviceReceiving.getYSlot(), serviceReceiving.getYRow(), serviceReceiving.getYTier());
                            masterYardCoordinat = masterYardCoordinatFacadeRemote.find(coorYard[0]);
                            masterYardCoordinat.setStatus("empty");
                            masterYardCoordinat.setContNo(null);
                            masterYardCoordinat.setContSize(null);
                            masterYardCoordinat.setContType(null);
                            masterYardCoordinat.setGrossClass(null);
                            masterYardCoordinat.setContWeight(null);
                            masterYardCoordinat.setNoPpkb(null);
                            //do the business!
                            try {
                                equipmentFacadeRemote.edit(equipment);
                                masterYardCoordinatFacadeRemote.edit(masterYardCoordinat);
                            } catch (EJBException e) {
                                status = e.toString();
                            }
                            break;
                    }
                } else {
                    status = ALERT_2;
                }
            }

        } else if (type.equals("6")) {
            start = ParseDate.changeDate(parameter[8]);
            end = ParseDate.changeDate(parameter[9]);

            Date now = Calendar.getInstance().getTime();

            container = planningContLoadFacadeRemote.findByContNo(parameter[0], "03", true);

            //update planning
            planningContLoad = planningContLoadFacadeRemote.find(container[0]);
            planningContLoad.setPosition("02");

            serviceVessel.setNoPpkb(parameter[1]);
            masterYard.setBlock(parameter[2]);
            masterCommodity.setCommodityCode(planningContLoad.getMasterCommodity().getCommodityCode());
            masterContainerType.setContType(planningContLoad.getMasterContainerType().getContType());

            //copy ke serv cont load
            serviceContLoad.setContNo(planningContLoad.getContNo());
            serviceContLoad.setServiceVessel(serviceVessel);
            serviceContLoad.setContSize(planningContLoad.getContSize());
            serviceContLoad.setContGross(planningContLoad.getContGross());
            serviceContLoad.setContStatus(planningContLoad.getContStatus());
            serviceContLoad.setDangerous(planningContLoad.getDg());
            serviceContLoad.setDgLabel(planningContLoad.getDgLabel());
            serviceContLoad.setIsTranshipment(planningContLoad.getIsTranshipment());
            serviceContLoad.setMasterCommodity(masterCommodity);
            serviceContLoad.setMasterContainerType(masterContainerType);
            serviceContLoad.setOverSize(planningContLoad.getOverSize());
            serviceContLoad.setSealNo(planningContLoad.getSealNo());
            serviceContLoad.setYRow(Short.parseShort(parameter[3]));
            serviceContLoad.setYSlot(Short.parseShort(parameter[4]));
            serviceContLoad.setYTier(Short.parseShort(parameter[5]));
            serviceContLoad.setVBay(planningContLoad.getVBay());
            serviceContLoad.setVRow(planningContLoad.getVRow());
            serviceContLoad.setVTier(planningContLoad.getVTier());
            serviceContLoad.setTransactionDate(now);
            serviceContLoad.setMasterYard(masterYard);
            serviceContLoad.setPosition("02");
            serviceContLoad.setBlNo(planningContLoad.getBlNo());

            masterEquipment.setEquipCode(parameter[6]);
            masterEquipment2.setEquipCode(parameter[7]);

            equipment.setContNo(parameter[0]);
            equipment.setMasterEquipment(masterEquipment);
            equipment.setStartTime(end);
            equipment.setPlanningVessel(serviceVessel.getPlanningVessel());
            equipment.setEquipmentActCode("H/T");
            equipment.setOperation("TRANSHIPMENT");
            equipment.setMasterOperator(new MasterOperator(parameter[10]));

            equipment2.setContNo(parameter[0]);
            equipment2.setMasterEquipment(masterEquipment2);
            equipment2.setStartTime(start);
            equipment2.setEndTime(end);
            equipment2.setPlanningVessel(serviceVessel.getPlanningVessel());
            equipment2.setEquipmentActCode("LIFTON");
            equipment2.setOperation("TRANSHIPMENT");
            equipment2.setMasterOperator(new MasterOperator(parameter[11]));

            Object[] coorYard, coorYard2;

            Boolean isTopExist = cyVlaidate.isTopExist(planningContLoad.getMasterYard().getBlock(), planningContLoad.getYSlot(), planningContLoad.getYRow(), planningContLoad.getYTier(), planningContLoad.getContSize());

            if (!isTopExist) {
                switch (planningContLoad.getContSize()) {
                    case CONTAINER_TYPE_40:
                        coorYard = masterYardCoordinatFacadeRemote.findIdStatusByCoordinat(planningContLoad.getMasterYard().getBlock(), planningContLoad.getYSlot(), planningContLoad.getYRow(), planningContLoad.getYTier());
                        masterYardCoordinat = masterYardCoordinatFacadeRemote.find(coorYard[0]);
                        masterYardCoordinat.setStatus("empty");
                        masterYardCoordinat.setContNo(null);
                        masterYardCoordinat.setContSize(null);
                        masterYardCoordinat.setContType(null);
                        masterYardCoordinat.setGrossClass(null);
                        masterYardCoordinat.setContWeight(null);
                        masterYardCoordinat.setNoPpkb(null);

                        coorYard2 = masterYardCoordinatFacadeRemote.findIdStatusByCoordinat(planningContLoad.getMasterYard().getBlock(), (short) ((short) planningContLoad.getYSlot() + 1), planningContLoad.getYRow(), planningContLoad.getYTier());
                        masterYardCoordinat2 = masterYardCoordinatFacadeRemote.find(coorYard2[0]);
                        masterYardCoordinat2.setStatus("empty");
                        masterYardCoordinat2.setContNo(null);
                        masterYardCoordinat2.setContSize(null);
                        masterYardCoordinat2.setContType(null);
                        masterYardCoordinat2.setGrossClass(null);
                        masterYardCoordinat2.setContWeight(null);
                        masterYardCoordinat2.setNoPpkb(null);

                        //do the business!
                        try {
                            planningContLoadFacadeRemote.edit(planningContLoad);
                            serviceContLoadFacadeRemote.edit(serviceContLoad);
                            equipmentFacadeRemote.edit(equipment);
                            equipmentFacadeRemote.edit(equipment2);
                            masterYardCoordinatFacadeRemote.edit(masterYardCoordinat);
                            masterYardCoordinatFacadeRemote.edit(masterYardCoordinat2);
                        } catch (EJBException e) {
                            status = e.toString();
                        }
                        break;
                    case CONTAINER_TYPE_20:
                        coorYard = masterYardCoordinatFacadeRemote.findIdStatusByCoordinat(planningContLoad.getMasterYard().getBlock(), planningContLoad.getYSlot(), planningContLoad.getYRow(), planningContLoad.getYTier());
                        masterYardCoordinat = masterYardCoordinatFacadeRemote.find(coorYard[0]);
                        masterYardCoordinat.setStatus("empty");
                        masterYardCoordinat.setContNo(null);
                        masterYardCoordinat.setContSize(null);
                        masterYardCoordinat.setContType(null);
                        masterYardCoordinat.setGrossClass(null);
                        masterYardCoordinat.setContWeight(null);
                        masterYardCoordinat.setNoPpkb(null);

                        //do the business!
                        try {
                            planningContLoadFacadeRemote.edit(planningContLoad);
                            serviceContLoadFacadeRemote.edit(serviceContLoad);
                            equipmentFacadeRemote.edit(equipment);
                            equipmentFacadeRemote.edit(equipment2);
                            masterYardCoordinatFacadeRemote.edit(masterYardCoordinat);
                        } catch (EJBException e) {
                            status = e.toString();
                        }
                        break;
                }
            } else {
                status = ALERT_2;
            }
        } else if (type.equals("7")) {
            /**
             * replaced by @ReceivingConfirm.receivingConfirm
             */
            start = ParseDate.changeDate(parameter[7]);
            end = ParseDate.changeDate(parameter[8]);

            container = planningContReceivingFacadeRemote.findByContNo(parameter[1], parameter[0], "01");
            containerPlug = pluggingReeferServiceFacadeRemote.findSelectPluggingReeferServiceReceivingHht(parameter[1]);

            if (container != null) {
                planningContReceiving = planningContReceivingFacadeRemote.find(container[0]);
                planningContReceiving.setPosition("02");

                planningVessel.setNoPpkb(parameter[1]);
                masterYard.setBlock(parameter[2]);
                masterCommodity.setCommodityCode(planningContReceiving.getMasterCommodity().getCommodityCode());
                masterContainerType.setContType(planningContReceiving.getMasterContainerType().getContType());

                serviceReceiving.setContNo(planningContReceiving.getContNo());
                serviceReceiving.setContGross(planningContReceiving.getContGross());
                serviceReceiving.setContSize(planningContReceiving.getContSize());
                serviceReceiving.setContStatus(planningContReceiving.getContStatus());
                serviceReceiving.setDangerous(planningContReceiving.getDg());
                serviceReceiving.setDgLabel(planningContReceiving.getDgLabel());
                serviceReceiving.setMasterCommodity(masterCommodity);
                serviceReceiving.setMasterContainerType(masterContainerType);
                serviceReceiving.setPlanningVessel(planningVessel);
                serviceReceiving.setOverSize(planningContReceiving.getOverSize());
                serviceReceiving.setSealNo(planningContReceiving.getSealNo());
                serviceReceiving.setMasterYard(masterYard);
                serviceReceiving.setYSlot(Short.parseShort(parameter[3]));
                serviceReceiving.setYRow(Short.parseShort(parameter[4]));
                serviceReceiving.setYTier(Short.parseShort(parameter[5]));
                serviceReceiving.setTransactionDate(start);
                serviceReceiving.setIsLifton("FALSE");
                serviceReceiving.setIsBehandle("FALSE");
                serviceReceiving.setIsCfs("FALSE");
                serviceReceiving.setIsInspection("FALSE");
                serviceReceiving.setBlNo(planningContReceiving.getBlNo());
                serviceReceiving.setChangeStatus("FALSE");
                serviceReceiving.setIsChangeDestination("FALSE");
                serviceReceiving.setStatusCancelLoading("FALSE");

                String grosss = GrossConverter.convert(serviceReceiving.getContSize(), serviceReceiving.getContGross());
                serviceReceiving.setGrossClass(grosss);

//TODO uncomment untuk langsung copy ke Planning cont load
                planningContLoad = new PlanningContLoad();
                planningContLoad.setMasterCommodity(masterCommodity);
                planningContLoad.setMasterYard(masterYard);
                planningContLoad.setMasterContainerType(masterContainerType);
                planningContLoad.setPlanningVessel(planningVessel);
                planningContLoad.setContNo(serviceReceiving.getContNo());
                planningContLoad.setContSize(serviceReceiving.getContSize());
                planningContLoad.setContStatus(serviceReceiving.getContStatus());
                planningContLoad.setIsTranshipment("FALSE");
                planningContLoad.setPosition("03");
                planningContLoad.setContGross(serviceReceiving.getContGross());
                planningContLoad.setSealNo(serviceReceiving.getSealNo());
                planningContLoad.setOverSize(serviceReceiving.getOverSize());
                planningContLoad.setDgLabel(serviceReceiving.getDgLabel());
                planningContLoad.setDg(serviceReceiving.getDangerous());
                planningContLoad.setYSlot(serviceReceiving.getYSlot());
                planningContLoad.setYRow(serviceReceiving.getYRow());
                planningContLoad.setYTier(serviceReceiving.getYTier());
                planningContLoad.setCompleted("FALSE");
                planningContLoad.setUnknown("FALSE");
                planningContLoad.setBlNo(serviceReceiving.getBlNo());

                masterEquipment.setEquipCode(parameter[6]);

                equipment.setContNo(parameter[0]);
                equipment.setNoPpkbRecv(parameter[1]);
                equipment.setStartTime(start);
                equipment.setEndTime(end);
                equipment.setMasterEquipment(masterEquipment);
                equipment.setEquipmentActCode("RECEIVING");
                equipment.setOperation("LOAD");
                equipment.setMasterOperator(new MasterOperator(parameter[9]));

                Object[] coorStatus = masterYardCoordinatFacadeRemote.findIdStatusByCoordinat(parameter[2], Short.parseShort(parameter[3]), Short.parseShort(parameter[4]), Short.parseShort(parameter[5]));
                Object[] coorStatus2 = masterYardCoordinatFacadeRemote.findIdStatusByCoordinat(parameter[2], (short) (Short.parseShort(parameter[3]) + 1), Short.parseShort(parameter[4]), Short.parseShort(parameter[5]));

                Boolean isBottomExist = cyVlaidate.isBottomExist(parameter[2], Short.parseShort(parameter[3]), Short.parseShort(parameter[4]), Short.parseShort(parameter[5]), serviceReceiving.getContSize());

                if (isBottomExist) {
                    switch (serviceReceiving.getContSize()) {
                        case CONTAINER_TYPE_40:
                            if (coorStatus2 == null) {
                                status = ALERT_1;
                            } else {
                                if ((coorStatus[1].equals("empty") || coorStatus[1].equals("planning")) && (coorStatus2[1].equals("empty") || coorStatus2[1].equals("planning"))) {
                                    masterYardCoordinat = masterYardCoordinatFacadeRemote.find(coorStatus[0]);
                                    masterYardCoordinat.setStatus("exist");
                                    masterYardCoordinat.setContNo(serviceReceiving.getContNo());
                                    masterYardCoordinat.setContSize(serviceReceiving.getContSize());
                                    masterYardCoordinat.setContType(serviceReceiving.getMasterContainerType().getContType().toString());
                                    masterYardCoordinat.setContWeight(Integer.parseInt(serviceReceiving.getContGross().toString()));
                                    masterYardCoordinat.setGrossClass(grosss);
                                    masterYardCoordinat.setNoPpkb(serviceReceiving.getPlanningVessel().getNoPpkb());

                                    masterYardCoordinat2 = masterYardCoordinatFacadeRemote.find(coorStatus2[0]);
                                    masterYardCoordinat2.setStatus("exist");
                                    masterYardCoordinat2.setContNo(serviceReceiving.getContNo());
                                    masterYardCoordinat2.setContSize(serviceReceiving.getContSize());
                                    masterYardCoordinat2.setContType(serviceReceiving.getMasterContainerType().getContType().toString());
                                    masterYardCoordinat2.setContWeight(Integer.parseInt(serviceReceiving.getContGross().toString()));
                                    masterYardCoordinat2.setGrossClass(grosss);
                                    masterYardCoordinat2.setNoPpkb(serviceReceiving.getPlanningVessel().getNoPpkb());

                                    try {
                                        planningContReceivingFacadeRemote.edit(planningContReceiving);
                                        equipmentFacadeRemote.edit(equipment);
                                        serviceReceivingFacadeRemote.edit(serviceReceiving);
                                        planningContLoadFacadeRemote.edit(planningContLoad);
                                        masterYardCoordinatFacadeRemote.edit(masterYardCoordinat);
                                        masterYardCoordinatFacadeRemote.edit(masterYardCoordinat2);
                                    } catch (EJBException e) {
                                        status = e.toString();
                                    }

                                } else {
                                    status = ALERT_1;
                                }
                            }
                            break;
                        case CONTAINER_TYPE_20:
                            if (coorStatus[1].equals("empty") || coorStatus[1].equals("planning")) {
                                masterYardCoordinat = masterYardCoordinatFacadeRemote.find(coorStatus[0]);
                                masterYardCoordinat.setStatus("exist");
                                masterYardCoordinat.setContNo(serviceReceiving.getContNo());
                                masterYardCoordinat.setContSize(serviceReceiving.getContSize());
                                masterYardCoordinat.setContType(serviceReceiving.getMasterContainerType().getContType().toString());
                                masterYardCoordinat.setContWeight(Integer.parseInt(serviceReceiving.getContGross().toString()));
                                masterYardCoordinat.setGrossClass(grosss);
                                masterYardCoordinat.setNoPpkb(serviceReceiving.getPlanningVessel().getNoPpkb());
                                try {
                                    planningContReceivingFacadeRemote.edit(planningContReceiving);
                                    equipmentFacadeRemote.edit(equipment);
                                    planningContLoadFacadeRemote.edit(planningContLoad);
                                    serviceReceivingFacadeRemote.edit(serviceReceiving);
                                    masterYardCoordinatFacadeRemote.edit(masterYardCoordinat);
                                } catch (EJBException e) {
                                    status = e.toString();
                                }
                            } else {
                                status = ALERT_1;
                            }
                            break;
                    }
                } else {
                    status = ALERT_2;
                }
            } else if (containerPlug != null) {
                pluggingReeferService = new PluggingReeferService();
                pluggingReeferService = pluggingReeferServiceFacadeRemote.find(containerPlug[0]);

                masterYard.setBlock(parameter[2]);
                masterCommodity.setCommodityCode(pluggingReeferService.getMasterCommodity().getCommodityCode());
                masterContainerType.setContType(pluggingReeferService.getMasterContainerType().getContType());
                serviceReceiving.setIdPluggingReefer(pluggingReeferService.getJobSlip());
                serviceReceiving.setContNo(pluggingReeferService.getContNo());
                serviceReceiving.setContGross(pluggingReeferService.getContGross());
                serviceReceiving.setContSize(pluggingReeferService.getContSize());
                serviceReceiving.setContStatus(pluggingReeferService.getContStatus());
                serviceReceiving.setDangerous(pluggingReeferService.getDg());
                serviceReceiving.setDgLabel(pluggingReeferService.getDgLabel());
                serviceReceiving.setMasterCommodity(masterCommodity);
                serviceReceiving.setMasterContainerType(masterContainerType);
                //serviceReceiving.setPlanningVessel(planningVessel);
                serviceReceiving.setOverSize(pluggingReeferService.getOverSize());
                //serviceReceiving.setSealNo(pluggingReeferService.getSealNo());
                serviceReceiving.setMasterYard(masterYard);
                serviceReceiving.setYSlot(Short.parseShort(parameter[3]));
                serviceReceiving.setYRow(Short.parseShort(parameter[4]));
                serviceReceiving.setYTier(Short.parseShort(parameter[5]));
                serviceReceiving.setTransactionDate(start);
                serviceReceiving.setIsLifton("FALSE");
                serviceReceiving.setIsBehandle("FALSE");
                serviceReceiving.setIsCfs("FALSE");
                serviceReceiving.setIsInspection("FALSE");
                serviceReceiving.setBlNo(pluggingReeferService.getBlNo());
                serviceReceiving.setChangeStatus("FALSE");
                serviceReceiving.setIsChangeDestination("FALSE");
                serviceReceiving.setStatusCancelLoading("FALSE");

                String grosss = GrossConverter.convert(serviceReceiving.getContSize(), serviceReceiving.getContGross());
                serviceReceiving.setGrossClass(grosss);

//TODO uncomment untuk langsung copy ke Planning cont load

                masterEquipment.setEquipCode(parameter[6]);

                equipment.setContNo(parameter[0]);
                equipment.setNoPpkbPlug(parameter[1]);
                equipment.setStartTime(start);
                equipment.setEndTime(end);
                equipment.setMasterEquipment(masterEquipment);
                equipment.setEquipmentActCode("RECEIVING");
                equipment.setOperation("LOAD");
                equipment.setMasterOperator(new MasterOperator(parameter[9]));

                //update plgging service
                pluggingReeferService.setPlacementDate(Calendar.getInstance().getTime());
                pluggingReeferService.setStatus("02");


                Object[] coorStatus = masterYardCoordinatFacadeRemote.findIdStatusByCoordinat(parameter[2], Short.parseShort(parameter[3]), Short.parseShort(parameter[4]), Short.parseShort(parameter[5]));
                Object[] coorStatus2 = masterYardCoordinatFacadeRemote.findIdStatusByCoordinat(parameter[2], (short) (Short.parseShort(parameter[3]) + 1), Short.parseShort(parameter[4]), Short.parseShort(parameter[5]));

                Boolean isBottomExist = cyVlaidate.isBottomExist(parameter[2], Short.parseShort(parameter[3]), Short.parseShort(parameter[4]), Short.parseShort(parameter[5]), serviceReceiving.getContSize());

                if (isBottomExist) {
                    switch (serviceReceiving.getContSize()) {
                        case CONTAINER_TYPE_40:
                            if (coorStatus2 == null) {
                                status = ALERT_1;
                            } else {
                                if ((coorStatus[1].equals("empty") || coorStatus[1].equals("planning")) && (coorStatus2[1].equals("empty") || coorStatus2[1].equals("planning"))) {
                                    masterYardCoordinat = masterYardCoordinatFacadeRemote.find(coorStatus[0]);
                                    masterYardCoordinat.setStatus("exist");
                                    masterYardCoordinat.setContNo(serviceReceiving.getContNo());
                                    masterYardCoordinat.setContSize(serviceReceiving.getContSize());
                                    masterYardCoordinat.setContType(serviceReceiving.getMasterContainerType().getContType().toString());
                                    masterYardCoordinat.setContWeight(Integer.parseInt(serviceReceiving.getContGross().toString()));
                                    masterYardCoordinat.setGrossClass(grosss);
                                    masterYardCoordinat.setNoPpkb(pluggingReeferService.getNoPpkb());

                                    masterYardCoordinat2 = masterYardCoordinatFacadeRemote.find(coorStatus2[0]);
                                    masterYardCoordinat2.setStatus("exist");
                                    masterYardCoordinat2.setContNo(serviceReceiving.getContNo());
                                    masterYardCoordinat2.setContSize(serviceReceiving.getContSize());
                                    masterYardCoordinat2.setContType(serviceReceiving.getMasterContainerType().getContType().toString());
                                    masterYardCoordinat2.setContWeight(Integer.parseInt(serviceReceiving.getContGross().toString()));
                                    masterYardCoordinat2.setGrossClass(grosss);
                                    masterYardCoordinat2.setNoPpkb(pluggingReeferService.getNoPpkb());

                                    try {
                                        equipmentFacadeRemote.edit(equipment);
                                        serviceReceivingFacadeRemote.edit(serviceReceiving);
                                        masterYardCoordinatFacadeRemote.edit(masterYardCoordinat);
                                        masterYardCoordinatFacadeRemote.edit(masterYardCoordinat2);
                                        pluggingReeferServiceFacadeRemote.edit(pluggingReeferService);
                                    } catch (EJBException e) {
                                        status = e.toString();
                                    }

                                } else {
                                    status = ALERT_1;
                                }
                            }
                            break;
                        case CONTAINER_TYPE_20:
                            if (coorStatus[1].equals("empty") || coorStatus[1].equals("planning")) {
                                masterYardCoordinat = masterYardCoordinatFacadeRemote.find(coorStatus[0]);
                                masterYardCoordinat.setStatus("exist");
                                masterYardCoordinat.setContNo(serviceReceiving.getContNo());
                                masterYardCoordinat.setContSize(serviceReceiving.getContSize());
                                masterYardCoordinat.setContType(serviceReceiving.getMasterContainerType().getContType().toString());
                                masterYardCoordinat.setContWeight(Integer.parseInt(serviceReceiving.getContGross().toString()));
                                masterYardCoordinat.setGrossClass(grosss);
                                masterYardCoordinat.setNoPpkb(pluggingReeferService.getNoPpkb());
                                try {
                                    equipmentFacadeRemote.edit(equipment);
                                    serviceReceivingFacadeRemote.edit(serviceReceiving);
                                    masterYardCoordinatFacadeRemote.edit(masterYardCoordinat);
                                    pluggingReeferServiceFacadeRemote.edit(pluggingReeferService);
                                } catch (EJBException e) {
                                    status = e.toString();
                                }
                            } else {
                                status = ALERT_1;
                            }
                            break;
                    }
                } else {
                    status = ALERT_2;
                }
            }

        } else if (type.equals("8")) {
            /**
             * replaced by @DischargeConfirmUc.stevedoringConfirm
             */
            start = ParseDate.changeDate(parameter[4]);
            end = ParseDate.changeDate(parameter[5]);

            container = uncontainerizedServiceFacadeRemote.findByPpkbAndBlNoMobile(parameter[0], parameter[1], "PLANNING", "DISCHARGE", 0);
            uncontainerizedService = uncontainerizedServiceFacadeRemote.find(container[0]);
            uncontainerizedService.setStatus("STV");
            uncontainerizedService.setPlacementDate(start);

            if (parameter[2].equals(CRANE_SEA)) {
                uncontainerizedService.setCrane("S");
            } else {
                uncontainerizedService.setCrane("L");
            }

            if (uncontainerizedService.getTruckLoosing().equals("TRUE")) {
                uncontainerizedService.setIsDelivery("TRUE");

                String id = ucDeliveryServiceFacadeRemote.findByPpkbNIdUC(uncontainerizedService.getIdUc(), parameter[0]);
                ucDeliveryService = ucDeliveryServiceFacadeRemote.find(id);
                ucDeliveryService.setDeliveryDate(end);
                ucDeliveryService.setIsDelivery("TRUE");

                ucDeliveryServiceFacadeRemote.edit(ucDeliveryService);
            } else {
                uncontainerizedService.setIsDelivery("FALSE");
                equipment2.setPlanningVessel(new PlanningVessel(parameter[0]));
                equipment2.setMasterEquipment(new MasterEquipment(parameter[3]));
                equipment2.setStartTime(start);
                equipment2.setOperation("DISCHARGEUC");
                equipment2.setEquipmentActCode("H/T");
                equipment2.setBlNo(parameter[1]);
                equipment2.setMasterOperator(new MasterOperator(parameter[7]));

                equipmentFacadeRemote.edit(equipment2);
            }

            equipment.setPlanningVessel(new PlanningVessel(parameter[0]));
            equipment.setMasterEquipment(new MasterEquipment(parameter[2]));
            equipment.setStartTime(start);
            equipment.setEndTime(end);
            equipment.setOperation("DISCHARGEUC");
            equipment.setEquipmentActCode("STEVEDORING");
            equipment.setBlNo(parameter[1]);
            equipment.setMasterOperator(new MasterOperator(parameter[6]));

            try {
                uncontainerizedServiceFacadeRemote.edit(uncontainerizedService);
                equipmentFacadeRemote.edit(equipment);
            } catch (EJBException ex) {
                status = ex.toString();
            }

        } else if (type.equals("9")) {
            /**
             * replaced by @LoadUcConfirm.loadUcStevedoringConfirm
             */
        } else if (type.equals("10")) {
            /**
             * replaced by @HatchMove.hatchMoveConfirm
             */
            start = ParseDate.changeDate(parameter[5]);
            end = ParseDate.changeDate(parameter[6]);

            equipment.setMasterEquipment(new MasterEquipment(parameter[4]));
            equipment.setPlanningVessel(new PlanningVessel(parameter[0]));
            equipment.setStartTime(start);
            equipment.setEndTime(end);
            equipment.setEquipmentActCode(parameter[3]);
            equipment.setOperation(parameter[2]);
            equipment.setMasterOperator(new MasterOperator(parameter[7]));
            equipmentFacadeRemote.create(equipment);

            serviceHatchMoves.setNoPpkb(parameter[0]);
            serviceHatchMoves.setBay(Short.parseShort(parameter[1]));
            serviceHatchMoves.setHatchMoves(parameter[3]);
            serviceHatchMoves.setOperation(parameter[2]);
            serviceHatchMoves.setHatchMoveDate(end);
            if (parameter[4].equals(CRANE_SEA)) {
                serviceHatchMoves.setCrane("S");
                serviceHatchMoves.setIsPaid("FALSE");
            } else {
                serviceHatchMoves.setCrane("L");
                serviceHatchMoves.setIsPaid("TRUE");
            }

            try {
                int id = equipmentFacadeRemote.findEquipmentId(parameter[0], start, end, parameter[3], parameter[2]);
                serviceHatchMoves.setIdEquipment(id);
                serviceHatchMovesFacadeRemote.edit(serviceHatchMoves);
            } catch (EJBException ex) {
                status = ex.toString();
            }
        } else if (type.equals("11")) {
            start = ParseDate.changeDate(parameter[6]);
            end = ParseDate.changeDate(parameter[7]);

            //System.out.println(serviceShiftingFacadeRemote.findByContNo("2011120001", "MRTU9609718"));
            container = serviceShiftingFacadeRemote.findByContNo(parameter[0], parameter[1]);

            equipment.setMasterEquipment(new MasterEquipment(parameter[5]));
            equipment.setPlanningVessel(new PlanningVessel(parameter[0]));
            equipment.setContNo(parameter[1]);
            equipment.setStartTime(start);
            equipment.setEndTime(end);
            equipment.setEquipmentActCode("SHIFTING");
            equipment.setOperation("DISCHARGE");
            equipment.setMasterOperator(new MasterOperator(parameter[8]));
            try {
                equipmentFacadeRemote.edit(equipment);
            } catch (Exception e) {
                e.printStackTrace();
            }

            //System.out.println(parameter[0] + " " + parameter[1]);
            //tempShifting = new Object[10];
            System.out.println(container);
            serviceShifting = serviceShiftingFacadeRemote.find((Integer) container[0]);
            serviceShifting.setVBay(Short.parseShort(parameter[2]));
            serviceShifting.setVRow(Short.parseShort(parameter[3]));
            serviceShifting.setVTier(Short.parseShort(parameter[4]));
            if (parameter[5].equals(CRANE_SEA)) {
                serviceShifting.setCrane("S");
            } else {
                serviceShifting.setCrane("L");
            }
            if (serviceShifting.getShiftTo().equals("LANDED")) {
                serviceShifting.setIsLanded("TRUE");
                serviceShifting.setIsReshipping("FALSE");
            } else {
                serviceShifting.setIsLanded("FALSE");
                serviceShifting.setIsReshipping("TRUE");
            }
            int i = equipmentFacadeRemote.findIdEquipmentByAll(parameter[5], parameter[8], parameter[1], parameter[0], start, end, "SHIFTING", "DISCHARGE");
            serviceShifting.setIdEquipment(i);
            serviceShifting.setIsPaid("TRUE");

            try {
                serviceShiftingFacadeRemote.edit(serviceShifting);
            } catch (EJBException ex) {
                status = ex.toString();
            }
        } else if (type.equals("12")) {
            start = ParseDate.changeDate(parameter[6]);
            end = ParseDate.changeDate(parameter[7]);

            container = serviceShiftingFacadeRemote.findByContNoReship(parameter[0], parameter[1], "FALSE");
            serviceShifting = serviceShiftingFacadeRemote.find((Integer) container[0]);

            equipment.setMasterEquipment(new MasterEquipment(parameter[5]));
            equipment.setPlanningVessel(new PlanningVessel(parameter[0]));
            equipment.setContNo(parameter[1]);
            equipment.setStartTime(start);
            equipment.setEndTime(end);
            equipment.setEquipmentActCode("RESHIPPING");
            equipment.setOperation(serviceShifting.getOperation());
            equipment.setMasterOperator(new MasterOperator(parameter[8]));
            equipmentFacadeRemote.edit(equipment);

            int i = equipmentFacadeRemote.findIdEquipmentByAll(parameter[5], parameter[8], parameter[1], parameter[0], start, end, "RESHIPPING", serviceShifting.getOperation());

            serviceShifting.setVBay(Short.parseShort(parameter[2]));
            serviceShifting.setVRow(Short.parseShort(parameter[3]));
            serviceShifting.setVTier(Short.parseShort(parameter[4]));
            serviceShifting.setIsReshipping("TRUE");
            serviceShifting.setIdEquipmentReshipping(i);

            try {
                serviceShiftingFacadeRemote.edit(serviceShifting);
            } catch (EJBException ex) {
                status = ex.toString();
            }
        } else if (type.equals("13")) {
            start = ParseDate.changeDate(parameter[6]);
            end = ParseDate.changeDate(parameter[7]);
            boolean isLanded = false;
            boolean isReshipping = false;

            if (parameter[9].equals("LANDED")) {
                isLanded = true;
            } else {
                isReshipping = true;
            }

            if (parameter[8].equals("LOAD")) {
                equipment.setMasterEquipment(new MasterEquipment(parameter[5]));
                equipment.setContNo(parameter[1]);
                equipment.setEquipmentActCode("SHIFTING");
                equipment.setOperation("LOAD");
                equipment.setPlanningVessel(new PlanningVessel(parameter[0]));
                equipment.setStartTime(start);
                equipment.setEndTime(end);
                equipment.setMasterOperator(new MasterOperator(parameter[10]));
                equipmentFacadeRemote.edit(equipment);

                int id = equipmentFacadeRemote.findIdEquipmentByAll(parameter[5], parameter[10], parameter[1], parameter[0], start, end, "SHIFTING", "LOAD");

                container = serviceContLoadFacadeRemote.findByContNo(parameter[1], "01");

                serviceContLoad = serviceContLoadFacadeRemote.find(container[0]);
                serviceShifting.setContNo(serviceContLoad.getContNo());
                serviceShifting.setContGross(serviceContLoad.getContGross());
                serviceShifting.setContSize(serviceContLoad.getContSize());
                serviceShifting.setContStatus(serviceContLoad.getContStatus());
                serviceShifting.setDg(serviceContLoad.getDangerous());
                serviceShifting.setDgLabel(serviceContLoad.getDgLabel());
                serviceShifting.setMasterContainerType(serviceContLoad.getMasterContainerType());
                serviceShifting.setMasterCommodity(serviceContLoad.getMasterCommodity());
                serviceShifting.setServiceVessel(new ServiceVessel(parameter[0]));
                
                if (isLanded) {
                    serviceShifting.setIsLanded("TRUE");
                } else {
                    serviceShifting.setIsLanded("FALSE");
                }
                
                if (isReshipping) {
                    serviceShifting.setIsReshipping("TRUE");
                } else {
                    serviceShifting.setIsReshipping("FALSE");
                }
                
                serviceShifting.setIsPlanning("FALSE");
                serviceShifting.setSealNo(serviceContLoad.getSealNo());
                serviceShifting.setIdEquipment(id);
                serviceShifting.setVBay(Short.parseShort(parameter[2]));
                serviceShifting.setVRow(Short.parseShort(parameter[3]));
                serviceShifting.setVTier(Short.parseShort(parameter[4]));
                serviceShifting.setShiftTo(parameter[9]);
                serviceShifting.setCrane(serviceContLoad.getCrane());
                serviceShifting.setOperation("DISCHARGE");
                serviceShifting.setBlNo(serviceContLoad.getBlNo());
            } else {
                equipment.setMasterEquipment(new MasterEquipment(parameter[5]));
                equipment.setContNo(parameter[1]);
                equipment.setEquipmentActCode("SHIFTING");
                equipment.setOperation("DISCHARGE");
                equipment.setPlanningVessel(new PlanningVessel(parameter[0]));
                equipment.setStartTime(start);
                equipment.setEndTime(end);
                equipment.setMasterOperator(new MasterOperator(parameter[10]));
                equipmentFacadeRemote.edit(equipment);

                int id = equipmentFacadeRemote.findIdEquipmentByAll(parameter[5], parameter[10], parameter[1], parameter[0], start, end, "SHIFTING", "DISCHARGE");

                container = serviceContDischargeFacadeRemote.findByContNo(parameter[1], "01");
                serviceContDischarge = serviceContDischargeFacadeRemote.find(container[5]);
                serviceShifting.setContNo(serviceContDischarge.getContNo());
                serviceShifting.setContSize(serviceContDischarge.getContSize());
                serviceShifting.setContStatus(serviceContDischarge.getContStatus());
                serviceShifting.setContGross(serviceContDischarge.getContGross());
                serviceShifting.setDg(serviceContDischarge.getDangerous());
                serviceShifting.setDgLabel(serviceContDischarge.getDgLabel());
                serviceShifting.setMasterContainerType(serviceContDischarge.getMasterContainerType());
                serviceShifting.setMasterCommodity(serviceContDischarge.getMasterCommodity());
                serviceShifting.setServiceVessel(new ServiceVessel(parameter[0]));
                
                if (isLanded) {
                    serviceShifting.setIsLanded("TRUE");
                } else {
                    serviceShifting.setIsLanded("FALSE");
                }
                
                if (isReshipping) {
                    serviceShifting.setIsReshipping("TRUE");
                } else {
                    serviceShifting.setIsReshipping("FALSE");
                }
                
                serviceShifting.setIsPlanning("FALSE");
                serviceShifting.setSealNo(serviceContDischarge.getSealNo());
                serviceShifting.setIdEquipment(id);
                serviceShifting.setVBay(Short.parseShort(parameter[2]));
                serviceShifting.setVRow(Short.parseShort(parameter[3]));
                serviceShifting.setVTier(Short.parseShort(parameter[4]));
                serviceShifting.setShiftTo(parameter[9]);
                serviceShifting.setCrane(serviceContDischarge.getCrane());
                serviceShifting.setOperation("DISCHARGE");
                serviceShifting.setBlNo(serviceContDischarge.getBlNo());
            }

            try {
                serviceShiftingFacadeRemote.edit(serviceShifting);
            } catch (EJBException ex) {
                status = ex.toString();
            }

        } else if (type.equals("14")) {
            start = ParseDate.changeDate(parameter[3]);
            end = ParseDate.changeDate(parameter[4]);

            container = uncontainerizedServiceFacadeRemote.findByPpkbAndBlNoMobile(parameter[1], parameter[0], "STV", "DISCHARGE", 0);

            uncontainerizedService = uncontainerizedServiceFacadeRemote.find(container[0]);
            uncontainerizedService.setIsDelivery("TRUE");
            uncontainerizedService.setStatus("CY");
            uncontainerizedService.setPlacementDate(end);
            uncontainerizedService.setBlock(parameter[6]);

            equipment.setPlanningVessel(new PlanningVessel(parameter[1]));
            equipment.setMasterEquipment(new MasterEquipment(parameter[2]));
            equipment.setStartTime(start);
            equipment.setEndTime(end);
            equipment.setOperation("DISCHARGEUC");
            equipment.setEquipmentActCode("LIFTOFF");
            equipment.setBlNo(parameter[0]);
            equipment.setMasterOperator(new MasterOperator(parameter[5]));

            int pk = equipmentFacadeRemote.findIdByBLno(parameter[1], parameter[0], "H/T", "DISCHARGEUC");
            equipment2 = equipmentFacadeRemote.find(pk);
            equipment2.setEndTime(start);

            try {
                uncontainerizedServiceFacadeRemote.edit(uncontainerizedService);
                equipmentFacadeRemote.edit(equipment);
                equipmentFacadeRemote.edit(equipment2);
            } catch (EJBException ex) {
                status = ex.toString();
            }
        } else if (type.equals("15")) {
            start = ParseDate.changeDate(parameter[3]);
            end = ParseDate.changeDate(parameter[4]);

            container = uncontainerizedServiceFacadeRemote.findByPpkbAndBlNoMobile(parameter[1], parameter[0], "CY", "LOAD", 0);

            uncontainerizedService = uncontainerizedServiceFacadeRemote.find(container[0]);
            uncontainerizedService.setIsDelivery("TRUE");
            uncontainerizedService.setStatus("STV");

            equipment.setPlanningVessel(new PlanningVessel(parameter[1]));
            equipment.setMasterEquipment(new MasterEquipment(parameter[2]));
            equipment.setStartTime(start);
            equipment.setEndTime(end);
            equipment.setOperation("LOADUC");
            equipment.setEquipmentActCode("LIFTON");
            equipment.setBlNo(parameter[0]);
            equipment.setMasterOperator(new MasterOperator(parameter[5]));

            equipment2.setPlanningVessel(new PlanningVessel(parameter[1]));
            equipment2.setMasterEquipment(new MasterEquipment(parameter[7]));
            equipment2.setMasterOperator(new MasterOperator(parameter[8]));
            equipment2.setStartTime(start);
            equipment2.setOperation("LOADUC");
            equipment2.setEquipmentActCode("H/T");
            equipment2.setBlNo(parameter[0]);

            try {
                uncontainerizedServiceFacadeRemote.edit(uncontainerizedService);
                equipmentFacadeRemote.edit(equipment);
                equipmentFacadeRemote.edit(equipment2);
            } catch (EJBException ex) {
                status = ex.toString();
            }
        } else if (type.equals("16")) {
            start = ParseDate.changeDate(parameter[3]);
            end = ParseDate.changeDate(parameter[4]);

            container = uncontainerizedServiceFacadeRemote.findByPpkbAndBlNoMobile(parameter[1], parameter[0], "CY", "DISCHARGE", 0);
            uncontainerizedService = uncontainerizedServiceFacadeRemote.find(container[0]);
            uncontainerizedService.setIsDelivery("TRUE");
            uncontainerizedService.setPlacementDate(end);

            String id = ucDeliveryServiceFacadeRemote.findByPpkbNIdUC(uncontainerizedService.getIdUc(), parameter[1]);
            ucDeliveryService = ucDeliveryServiceFacadeRemote.find(id);
            ucDeliveryService.setDeliveryDate(end);
            ucDeliveryService.setIsDelivery("TRUE");

            equipment.setPlanningVessel(new PlanningVessel(parameter[1]));
            equipment.setMasterEquipment(new MasterEquipment(parameter[2]));
            equipment.setStartTime(start);
            equipment.setEndTime(end);
            equipment.setOperation("DISCHARGEUC");
            equipment.setEquipmentActCode("DELIVERY");
            equipment.setBlNo(parameter[0]);
            equipment.setMasterOperator(new MasterOperator(parameter[5]));

            try {
                uncontainerizedServiceFacadeRemote.edit(uncontainerizedService);
                ucDeliveryServiceFacadeRemote.edit(ucDeliveryService);
                equipmentFacadeRemote.edit(equipment);
            } catch (EJBException ex) {
                status = ex.toString();
            }

        } else if (type.equals("17")) {
            start = ParseDate.changeDate(parameter[3]);
            end = ParseDate.changeDate(parameter[4]);

            container = uncontainerizedServiceFacadeRemote.findByBlNoMobileUc(parameter[0]);
            uncontainerizedService = uncontainerizedServiceFacadeRemote.find(container[0]);
            uncontainerizedService.setStatus("CY");

            String id = ucReceivingServiceFacadeRemote.findJobslipByIdUCMobile(uncontainerizedService.getIdUc());
            ucReceivingService = ucReceivingServiceFacadeRemote.find(id);
            ucReceivingService.setReceivingDate(end);

            equipment.setPlanningVessel(new PlanningVessel(parameter[1]));
            equipment.setMasterEquipment(new MasterEquipment(parameter[2]));
            equipment.setStartTime(start);
            equipment.setEndTime(end);
            equipment.setOperation("LOADUC");
            equipment.setEquipmentActCode("RECEIVING");
            equipment.setBlNo(parameter[0]);
            equipment.setMasterOperator(new MasterOperator(parameter[5]));

            try {
                uncontainerizedServiceFacadeRemote.edit(uncontainerizedService);
                ucReceivingServiceFacadeRemote.edit(ucReceivingService);
                equipmentFacadeRemote.edit(equipment);
            } catch (EJBException ex) {
                status = ex.toString();
            }
        } else if (type.equals("18")) {
            start = ParseDate.changeDate(parameter[3]);
            end = ParseDate.changeDate(parameter[4]);

            String oper = null;
            boolean tem = false;
            boolean tem2 = false;
            if (parameter[6].equalsIgnoreCase("LANDED")) {
                tem = true;
            } else if (parameter[6].equalsIgnoreCase("NOLANDED")) {
                tem2 = true;
            }

            equipment.setBlNo(parameter[0]);
            equipment.setPlanningVessel(new PlanningVessel(parameter[1]));
            equipment.setStartTime(start);
            equipment.setEndTime(end);
            equipment.setEquipmentActCode("SHIFTING");
            if (parameter[7].equalsIgnoreCase("LOAD")) {
                equipment.setOperation("LOADUC");
                oper = "LOADUC";
            } else {
                equipment.setOperation("DISCHARGEUC");
                oper = "DISCHARGEUC";
            }
            equipment.setMasterEquipment(new MasterEquipment(parameter[2]));
            equipment.setMasterOperator(new MasterOperator(parameter[5]));

            try {
                equipmentFacadeRemote.edit(equipment);
            } catch (EJBException ex) {
                status = ex.toString();
            }

            int id = equipmentFacadeRemote.findIdEquipmentByAllBlNo(parameter[2], parameter[5], parameter[0], parameter[1], start, end, "SHIFTING", oper);

            if (parameter[2].equals(CRANE_SEA)) {
                ucShiftingService.setCrane("S");
                ucShiftingService.setIsPaid("FALSE");
            } else {
                ucShiftingService.setCrane("L");
                ucShiftingService.setIsPaid("TRUE");
            }
            ucShiftingService.setBlNo(parameter[0]);
            ucShiftingService.setNoPpkb(parameter[1]);
            ucShiftingService.setIsPlanning("FALSE");
            ucShiftingService.setOperation(parameter[7]);
            
            if (tem) {
                ucShiftingService.setIsLanded("TRUE");
            } else {
                ucShiftingService.setIsLanded("FALSE");
            }
            
            if (tem2) {
                ucShiftingService.setIsReshipping("TRUE");
            } else {
                ucShiftingService.setIsReshipping("FALSE");
            }
            
            ucShiftingService.setIdEquipment(id);
            ucShiftingService.setShiftTo(parameter[6]);

            try {
                ucShiftingServiceFacadeRemote.edit(ucShiftingService);
            } catch (EJBException ex) {
                status = ex.toString();
            }
        } else if (type.equals("19")) {
            start = ParseDate.changeDate(parameter[3]);
            end = ParseDate.changeDate(parameter[4]);

            boolean tem = false;
            boolean tem2 = false;
            if (parameter[6].equalsIgnoreCase("LANDED")) {
                tem = true;
            } else if (parameter[6].equalsIgnoreCase("NOLANDED")) {
                tem2 = true;
            }

            equipment.setBlNo(parameter[0]);
            equipment.setPlanningVessel(new PlanningVessel(parameter[1]));
            equipment.setStartTime(start);
            equipment.setEndTime(end);
            equipment.setEquipmentActCode("SHIFTING");
            equipment.setOperation("DISCHARGEUC");
            equipment.setMasterEquipment(new MasterEquipment(parameter[2]));
            equipment.setMasterOperator(new MasterOperator(parameter[5]));

            try {
                equipmentFacadeRemote.edit(equipment);
            } catch (EJBException ex) {
                status = ex.toString();
            }

            int id = equipmentFacadeRemote.findIdEquipmentByAllBlNo(parameter[2], parameter[5], parameter[0], parameter[1], start, end, "SHIFTING", "DISCHRAGEUC");

            if (parameter[2].equals(CRANE_SEA)) {
                ucShiftingService.setCrane("S");
                ucShiftingService.setIsPaid("FALSE");
            } else {
                ucShiftingService.setCrane("L");
                ucShiftingService.setIsPaid("TRUE");
            }
            ucShiftingService.setBlNo(parameter[0]);
            ucShiftingService.setNoPpkb(parameter[1]);
            ucShiftingService.setIsPlanning("FALSE");
            ucShiftingService.setOperation(parameter[7]);
            
            if (tem) {
                ucShiftingService.setIsLanded("TRUE");
            } else {
                ucShiftingService.setIsLanded("FALSE");
            }
            if (tem2) {
                ucShiftingService.setIsReshipping("TRUE");
            } else {
                ucShiftingService.setIsReshipping("FALSE");
            }
            
            ucShiftingService.setIdEquipment(id);
            ucShiftingService.setShiftTo(parameter[6]);

            try {
                ucShiftingServiceFacadeRemote.edit(ucShiftingService);
            } catch (EJBException ex) {
                status = ex.toString();
            }
        } else if (type.equals("20")) {
            start = ParseDate.changeDate(parameter[4]);
            end = ParseDate.changeDate(parameter[5]);
            String oper = null;
            equipment.setMasterEquipment(new MasterEquipment(parameter[2]));
            equipment.setMasterOperator(new MasterOperator(parameter[3]));
            equipment.setEquipmentActCode("RESHIPPING");
            if (parameter[6].equalsIgnoreCase("LOAD")) {
                equipment.setOperation("LOADUC");
                oper = "LOADUC";
            } else {
                equipment.setOperation("DISCHARGEUC");
                oper = "DISCHARGEUC";
            }
            equipment.setStartTime(start);
            equipment.setEndTime(end);
            equipment.setPlanningVessel(new PlanningVessel(parameter[0]));
            equipment.setBlNo(parameter[1]);

            try {
                equipmentFacadeRemote.edit(equipment);
            } catch (EJBException ex) {
                status = ex.toString();
            }

            int i = equipmentFacadeRemote.findIdEquipmentByAllBlNo(parameter[2], parameter[3], parameter[1], parameter[0], start, end, "RESHIPPING", oper);

            container = ucShiftingServiceFacadeRemote.findByPpkbBlNo(parameter[0], parameter[1], 0);
            ucShiftingService = ucShiftingServiceFacadeRemote.find(container[0]);
            ucShiftingService.setIsReshipping("TRUE");
            ucShiftingService.setIdEquipmentReshipping(i);

            try {
                ucShiftingServiceFacadeRemote.edit(ucShiftingService);
            } catch (EJBException ex) {
                status = ex.toString();
            }
        } else if (type.equals("21")) {
            start = ParseDate.changeDate(parameter[7]);
            end = ParseDate.changeDate(parameter[8]);

            container = tempContDischargeFacadeRemote.findByContNo(parameter[1], "02");

            masterYard.setBlock(parameter[2]);

            tempContDischarge = tempContDischargeFacadeRemote.find(container[5]);
            tempContDischarge.setBlock(parameter[2]);
            tempContDischarge.setYRow(Short.parseShort(parameter[3]));
            tempContDischarge.setYSlot(Short.parseShort(parameter[4]));
            tempContDischarge.setYTier(Short.parseShort(parameter[5]));
            tempContDischarge.setStartPlacementDate(start);
            tempContDischarge.setPosition("03");

            //serviceVessel.setNoPpkb(serviceContDischarge.getServiceVessel().getNoPpkb());

            masterEquipment.setEquipCode(parameter[0]);

            equipment.setPlanningVessel(new PlanningVessel(parameter[6]));
            equipment.setMasterEquipment(new MasterEquipment(parameter[0]));
            equipment.setContNo(parameter[1]);
            equipment.setMasterOperator(new MasterOperator(parameter[9]));
            equipment.setEquipmentActCode("LIFTOFF");
            equipment.setOperation("DISCHARGE");
            equipment.setStartTime(start);
            equipment.setEndTime(end);

            Integer PK2 = equipmentFacadeRemote.findByIdContainer(parameter[6], parameter[1], "H/T", "DISCHARGE");
            //System.out.println(PK2);
            equipment2 = equipmentFacadeRemote.find(PK2);
            equipment2.setEndTime(start);

            serviceVessel2 = serviceVesselFacadeRemote.find(parameter[6]);
            short counter = (short) (serviceVessel2.getDischarge() + 1);
            serviceVessel2.setDischarge(counter);

            masterYardCoordinats = masterYardCoordinatFacadeRemote.findByContNo(parameter[1]);
            System.out.println(masterYardCoordinats.size());
            int count = masterYardCoordinats.size();
            if (count > 0) {
                for (Object[] tempObj : masterYardCoordinats) {
                    int cont_no = (Integer) tempObj[0];
                    masterYardCoordinat = masterYardCoordinatFacadeRemote.find(cont_no);
                    masterYardCoordinat.setContNo(null);
                    masterYardCoordinat.setContSize(null);
                    masterYardCoordinat.setContType(null);
                    masterYardCoordinat.setContWeight(null);
                    masterYardCoordinat.setGrossClass(null);
                    masterYardCoordinat.setStatus("empty");
                    masterYardCoordinat.setNoPpkb(null);
                    masterYardCoordinatFacadeRemote.edit(masterYardCoordinat);
                }
            }

            //do the business
            //TODO mekanisme mencari kontainer ukuran 40 dan harus di alokasikan 2 di master yard coor
            Object[] coorStatus = masterYardCoordinatFacadeRemote.findIdStatusByCoordinat(parameter[2], Short.parseShort(parameter[4]), Short.parseShort(parameter[3]), Short.parseShort(parameter[5]));
            Object[] coorStatus2 = masterYardCoordinatFacadeRemote.findIdStatusByCoordinat(parameter[2], (short) (Short.parseShort(parameter[4]) + 1), Short.parseShort(parameter[3]), Short.parseShort(parameter[5]));

            switch (tempContDischarge.getContSize()) {
                case CONTAINER_TYPE_40:
                    if (coorStatus2 == null) {
                        status = ALERT_1;
                    } else {
                        if ((coorStatus[1].equals("empty") || coorStatus[1].equals("planning")) && (coorStatus2[1].equals("empty") || coorStatus2[1].equals("planning"))) {
                            masterYardCoordinat = masterYardCoordinatFacadeRemote.find(coorStatus[0]);
                            masterYardCoordinat.setStatus("exist");
                            masterYardCoordinat.setContNo(tempContDischarge.getContNo());
                            masterYardCoordinat.setContSize(tempContDischarge.getContSize());
                            masterYardCoordinat.setContType("3");
                            masterYardCoordinat.setContWeight(0);
                            masterYardCoordinat.setGrossClass("D");
                            masterYardCoordinat.setNoPpkb(tempContDischarge.getNoPpkb());

                            masterYardCoordinat2 = masterYardCoordinatFacadeRemote.find(coorStatus2[0]);
                            masterYardCoordinat2.setStatus("exist");
                            masterYardCoordinat2.setContNo(tempContDischarge.getContNo());
                            masterYardCoordinat2.setContSize(tempContDischarge.getContSize());
                            masterYardCoordinat2.setContType("3");
                            masterYardCoordinat2.setContWeight(0);
                            masterYardCoordinat2.setGrossClass("D");
                            masterYardCoordinat2.setNoPpkb(tempContDischarge.getNoPpkb());

                            try {
                                tempContDischargeFacadeRemote.edit(tempContDischarge);
                                serviceVesselFacadeRemote.edit(serviceVessel2);
                                equipmentFacadeRemote.edit(equipment);
                                equipmentFacadeRemote.edit(equipment2);
                                masterYardCoordinatFacadeRemote.edit(masterYardCoordinat);
                                masterYardCoordinatFacadeRemote.edit(masterYardCoordinat2);
                            } catch (EJBException e) {
                                status = e.toString();
                            }

                        } else {
                            status = ALERT_1;
                        }
                    }
                    break;
                case CONTAINER_TYPE_20:
                    if (coorStatus[1].equals("empty") || coorStatus[1].equals("planning")) {
                        masterYardCoordinat = masterYardCoordinatFacadeRemote.find(coorStatus[0]);
                        masterYardCoordinat.setStatus("exist");
                        masterYardCoordinat.setContNo(tempContDischarge.getContNo());
                        masterYardCoordinat.setContSize(tempContDischarge.getContSize());
                        masterYardCoordinat.setContType("3");
                        masterYardCoordinat.setContWeight(0);
                        masterYardCoordinat.setGrossClass("D");
                        masterYardCoordinat.setNoPpkb(tempContDischarge.getNoPpkb());
                        try {
                            tempContDischargeFacadeRemote.edit(tempContDischarge);
                            serviceVesselFacadeRemote.edit(serviceVessel2);
                            equipmentFacadeRemote.edit(equipment);
                            equipmentFacadeRemote.edit(equipment2);
                            masterYardCoordinatFacadeRemote.edit(masterYardCoordinat);
                        } catch (EJBException e) {
                            status = e.toString();
                        }
                    } else {
                        status = ALERT_1;
                    }
                    break;
            }
        } else if (type.equals("22")) {
            cancelLoadingService = cancelLoadingServiceFacadeRemote.find(parameter[7]);
            cancelLoadingService.setIsDischarge("TRUE");

            if (parameter[6].equalsIgnoreCase("FALSE")) {
                masterYard = masterYardFacadeRemote.find((String)parameter[2]);
                cancelLoadingService.setPosisi("02");
                cancelLoadingService.setMasterYard(masterYard);
                cancelLoadingService.setySlot(Short.parseShort(parameter[3]));
                cancelLoadingService.setyRow(Short.parseShort(parameter[4]));
                cancelLoadingService.setyTier(Short.parseShort(parameter[5]));

                /**  ke CY  */
                String gross = GrossConverter.convert(cancelLoadingService.getContSize(), cancelLoadingService.getContGross());

                Object[] coorStatus = masterYardCoordinatFacadeRemote.findIdStatusByCoordinat(parameter[2], Short.parseShort(parameter[3]), Short.parseShort(parameter[4]), Short.parseShort(parameter[5]));
                Object[] coorStatus2 = masterYardCoordinatFacadeRemote.findIdStatusByCoordinat(parameter[2], (short) (Short.parseShort(parameter[3]) + 1), Short.parseShort(parameter[4]), Short.parseShort(parameter[5]));

                switch (cancelLoadingService.getContSize()) {
                    case CONTAINER_TYPE_40:
                        if (coorStatus2 == null) {
                            status = ALERT_1;
                        } else {
                            if ((coorStatus[1].equals("empty") || coorStatus[1].equals("planning")) && (coorStatus2[1].equals("empty") || coorStatus2[1].equals("planning"))) {
                                masterYardCoordinat = masterYardCoordinatFacadeRemote.find(coorStatus[0]);
                                masterYardCoordinat.setStatus("exist");
                                masterYardCoordinat.setContNo(cancelLoadingService.getContNo());
                                masterYardCoordinat.setContSize(cancelLoadingService.getContSize());
                                masterYardCoordinat.setContType(cancelLoadingService.getMasterContainerType().getContType().toString());
                                masterYardCoordinat.setContWeight(cancelLoadingService.getContGross());
                                masterYardCoordinat.setGrossClass(gross);
                                masterYardCoordinat.setNoPpkb(cancelLoadingService.getPlanningVessel().getNoPpkb());

                                masterYardCoordinat2 = masterYardCoordinatFacadeRemote.find(coorStatus2[0]);
                                masterYardCoordinat2.setStatus("exist");
                                masterYardCoordinat2.setContNo(cancelLoadingService.getContNo());
                                masterYardCoordinat2.setContSize(cancelLoadingService.getContSize());
                                masterYardCoordinat2.setContType(cancelLoadingService.getMasterContainerType().getContType().toString());
                                masterYardCoordinat2.setContWeight(cancelLoadingService.getContGross());
                                masterYardCoordinat2.setGrossClass(gross);
                                masterYardCoordinat2.setNoPpkb(cancelLoadingService.getPlanningVessel().getNoPpkb());

                                try {
                                    masterYardCoordinatFacadeRemote.edit(masterYardCoordinat);
                                    masterYardCoordinatFacadeRemote.edit(masterYardCoordinat2);
                                } catch (EJBException e) {
                                    status = e.toString();
                                }
                            } else {
                                status = ALERT_1;
                            }
                        }
                        break;
                    case CONTAINER_TYPE_20:
                        if (coorStatus[1].equals("empty") || coorStatus[1].equals("planning")) {
                            masterYardCoordinat = masterYardCoordinatFacadeRemote.find(coorStatus[0]);
                            masterYardCoordinat.setStatus("exist");
                            masterYardCoordinat.setContNo(cancelLoadingService.getContNo());
                            masterYardCoordinat.setContSize(cancelLoadingService.getContSize());
                            masterYardCoordinat.setContType(cancelLoadingService.getMasterContainerType().getContType().toString());
                            masterYardCoordinat.setContWeight(cancelLoadingService.getContGross());
                            masterYardCoordinat.setGrossClass(gross);
                            masterYardCoordinat.setNoPpkb(cancelLoadingService.getPlanningVessel().getNoPpkb());
                            try {
                                masterYardCoordinatFacadeRemote.edit(masterYardCoordinat);
                            } catch (EJBException e) {
                                status = e.toString();
                            }
                        } else {
                            status = ALERT_1;
                        }
                        break;
                }
            } else {
                cancelLoadingService.setPosisi("01");
            }
            cancelLoadingServiceFacadeRemote.edit(cancelLoadingService);
        } else if (type.equals("23")) {
            cancelLoadingService = cancelLoadingServiceFacadeRemote.find(parameter[2]);
            cancelLoadingService.setPosisi("01");

            Object[] coorYard, coorYard2;
            Boolean isTopExist = cyVlaidate.isTopExist(cancelLoadingService.getMasterYard().getBlock(), cancelLoadingService.getySlot(), cancelLoadingService.getyRow(), cancelLoadingService.getyTier(), cancelLoadingService.getContSize());

            if (!isTopExist) {
                switch (cancelLoadingService.getContSize()) {
                    case CONTAINER_TYPE_40:
                        coorYard = masterYardCoordinatFacadeRemote.findIdStatusByCoordinat(cancelLoadingService.getMasterYard().getBlock(), cancelLoadingService.getySlot(), cancelLoadingService.getyRow(), cancelLoadingService.getyTier());
                        masterYardCoordinat = masterYardCoordinatFacadeRemote.find(coorYard[0]);
                        masterYardCoordinat.setStatus("empty");
                        masterYardCoordinat.setContNo(null);
                        masterYardCoordinat.setContSize(null);
                        masterYardCoordinat.setContType(null);
                        masterYardCoordinat.setGrossClass(null);
                        masterYardCoordinat.setContWeight(null);
                        masterYardCoordinat.setNoPpkb(null);

                        coorYard2 = masterYardCoordinatFacadeRemote.findIdStatusByCoordinat(cancelLoadingService.getMasterYard().getBlock(), (short) ((short) cancelLoadingService.getySlot() + 1), cancelLoadingService.getyRow(), cancelLoadingService.getyTier());
                        masterYardCoordinat2 = masterYardCoordinatFacadeRemote.find(coorYard2[0]);
                        masterYardCoordinat2.setStatus("empty");
                        masterYardCoordinat2.setContNo(null);
                        masterYardCoordinat2.setContSize(null);
                        masterYardCoordinat2.setContType(null);
                        masterYardCoordinat2.setGrossClass(null);
                        masterYardCoordinat2.setContWeight(null);
                        masterYardCoordinat2.setNoPpkb(null);

                        //do the business!
                        try {
                            cancelLoadingServiceFacadeRemote.edit(cancelLoadingService);
                            masterYardCoordinatFacadeRemote.edit(masterYardCoordinat);
                            masterYardCoordinatFacadeRemote.edit(masterYardCoordinat2);
                        } catch (EJBException e) {
                            status = e.toString();
                        }
                        break;
                    case CONTAINER_TYPE_20:
                        coorYard = masterYardCoordinatFacadeRemote.findIdStatusByCoordinat(cancelLoadingService.getMasterYard().getBlock(), cancelLoadingService.getySlot(), cancelLoadingService.getyRow(), cancelLoadingService.getyTier());
                        masterYardCoordinat = masterYardCoordinatFacadeRemote.find(coorYard[0]);
                        masterYardCoordinat.setStatus("empty");
                        masterYardCoordinat.setContNo(null);
                        masterYardCoordinat.setContSize(null);
                        masterYardCoordinat.setContType(null);
                        masterYardCoordinat.setGrossClass(null);
                        masterYardCoordinat.setContWeight(null);
                        masterYardCoordinat.setNoPpkb(null);
                        //do the business!
                        try {
                            cancelLoadingServiceFacadeRemote.edit(cancelLoadingService);
                            masterYardCoordinatFacadeRemote.edit(masterYardCoordinat);
                        } catch (EJBException e) {
                            status = e.toString();
                        }
                        break;
                }
            } else {
                status = ALERT_2;
            }
        }
        System.out.println(status);
        return status;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "reeferService")
    public String reeferService(@WebParam(name = "parameter") String parameter[], @WebParam(name = "type") int type) {
        UserUtil.setCurrentUser((String) parameter[3]);
        starOver();
        Date start = ParseDate.changeDate(parameter[2]);

        switch (type) {
            case 1:

                Object[] tempContDish = serviceContDischargeFacadeRemote.findContainerReefer(parameter[0]);
                Object[] tempContRec = planningContLoadFacadeRemote.findPluggableContainer(parameter[0]);
                Object[] tempContPlug = pluggingReeferServiceFacadeRemote.findContainerReefer(parameter[0]);

                if (tempContDish != null && !parameter[1].equals("NULL")) {
                    masterContainerType = masterContainerTypeFacadeRemote.find((Integer) tempContDish[0]);
                    masterPluggingReefer = masterPluggingReeferFacadeRemote.find(parameter[1]);

                    serviceReefer.setNoPpkb(tempContDish[4].toString());
                    serviceReefer.setContNo(parameter[0]);
                    serviceReefer.setContSize(Short.parseShort(tempContDish[2].toString()));
                    serviceReefer.setContStatus(tempContDish[3].toString());
                    serviceReefer.setMasterContainerType(masterContainerType);
                    serviceReefer.setMasterPluggingReefer(masterPluggingReefer);
                    serviceReefer.setOperation("DISCHARGE");
                    serviceReefer.setPlugOn(start);
                    serviceReefer.setFirstPlugOn(parameter[1]);
                    serviceReefer.setLastPluggingCode(parameter[1]);
                    serviceReefer.setChangePlugging((short) 0);

                    try {
                        serviceReeferFacadeRemote.edit(serviceReefer);
                        masterPluggingReefer = masterPluggingReeferFacadeRemote.find(parameter[1]);
                        masterPluggingReefer.setAvailability("FALSE");
                        masterPluggingReeferFacadeRemote.edit(masterPluggingReefer);
                        status = "Ok";
                    } catch (EJBException ex) {
                        status = ex.toString();
                    }
                } else if (tempContRec != null && !parameter[1].equals("NULL")) {
                    masterContainerType = masterContainerTypeFacadeRemote.find((Integer) tempContRec[2]);
                    masterPluggingReefer = masterPluggingReeferFacadeRemote.find(parameter[1]);
                    Boolean isTranshipment = (Boolean) tempContRec[7];
                    Boolean isShifting = (Boolean) tempContRec[8];

                    serviceReefer.setNoPpkb((String) tempContRec[6]);
                    serviceReefer.setContNo(parameter[0]);
                    serviceReefer.setContSize(((Integer) tempContRec[1]).shortValue());
                    serviceReefer.setContStatus((String) tempContRec[3]);
                    serviceReefer.setMasterContainerType(masterContainerType);
                    serviceReefer.setMasterPluggingReefer(masterPluggingReefer);
                    serviceReefer.setPlugOn(start);
                    serviceReefer.setFirstPlugOn(parameter[1]);
                    serviceReefer.setLastPluggingCode(parameter[1]);
                    serviceReefer.setChangePlugging((short) 0);

                    if (isTranshipment) {
                        serviceReefer.setOperation(ServiceReefer.OPERATION_TRANSHIPMENT);
                    } else if (isShifting) {
                        serviceReefer.setOperation(ServiceReefer.OPERATION_SHIFTING);
                    } else {
                        serviceReefer.setOperation(ServiceReefer.OPERATION_LOAD);
                    }

                    try {
                        serviceReeferFacadeRemote.edit(serviceReefer);
                        masterPluggingReefer = masterPluggingReeferFacadeRemote.find(parameter[1]);
                        masterPluggingReefer.setAvailability("FALSE");
                        masterPluggingReeferFacadeRemote.edit(masterPluggingReefer);
                        status = "Ok";
                    } catch (EJBException ex) {
                        status = ex.toString();
                    }
                } else if (tempContPlug != null && !parameter[1].equals("NULL")) {

                    serviceReefer.setNoReg(tempContPlug[4].toString());
                    serviceReefer.setContNo(parameter[0]);
                    serviceReefer.setContSize(Short.parseShort(tempContPlug[2].toString()));
                    serviceReefer.setContStatus(tempContPlug[3].toString());
                    masterContainerType.setContType(Integer.parseInt(tempContPlug[0].toString()));
                    serviceReefer.setMasterContainerType(masterContainerType);
                    masterPluggingReefer.setPluggingCode(parameter[1]);
                    serviceReefer.setMasterPluggingReefer(masterPluggingReefer);
                    serviceReefer.setOperation("PLUGGING");
                    serviceReefer.setPlugOn(start);
                    serviceReefer.setFirstPlugOn(parameter[1]);
                    serviceReefer.setLastPluggingCode(parameter[1]);
                    serviceReefer.setChangePlugging((short) 0);

                    try {
                        serviceReeferFacadeRemote.edit(serviceReefer);
                        masterPluggingReefer = masterPluggingReeferFacadeRemote.find(parameter[1]);
                        masterPluggingReefer.setAvailability("FALSE");
                        masterPluggingReeferFacadeRemote.edit(masterPluggingReefer);
                        status = "Ok";
                    } catch (EJBException ex) {
                        status = ex.toString();
                    }
                } else {
                    status = "No Container has been plugged on.";
                }
                System.out.println(status);
                break;
            case 2:
                container = serviceReeferFacadeRemote.findByContNo(parameter[0]);

                if (container != null) {
                    serviceReefer = serviceReeferFacadeRemote.find(container[0]);
                    serviceReefer.setPlugOff(start);
                    masterPluggingReefer.setPluggingCode(parameter[1]);
                    serviceReefer.setMasterPluggingReefer(masterPluggingReefer);

                    ReeferDischargeService reeferDischargeService = reeferDischargeServiceFacadeRemote.findLastValidReeferByNoPpkbAndContNo(serviceReefer.getNoPpkb(), serviceReefer.getContNo());

                    if (reeferDischargeService == null || serviceReefer.getPlugOff().after(reeferDischargeService.getValidDate())) {
                        return "Valid data has expired!";
                    }

                    try {
                        serviceReeferFacadeRemote.edit(serviceReefer);
                        masterPluggingReefer = masterPluggingReeferFacadeRemote.find(parameter[1]);
                        masterPluggingReefer.setAvailability("TRUE");
                        masterPluggingReeferFacadeRemote.edit(masterPluggingReefer);
                        status = "Ok";
                    } catch (EJBException ex) {
                        status = ex.toString();
                    }
                } else {
                    status = "No Container has been plugged off.";
                }
                break;
            case 3:
                container = serviceReeferFacadeRemote.findByContNo(parameter[0]);
                //System.out.println(parameter[1]);

                if (container != null) {
                    serviceReefer = serviceReeferFacadeRemote.find(container[0]);
                    String oldPlugCode = serviceReefer.getMasterPluggingReefer().getPluggingCode();
                    String lastPlugCode = serviceReefer.getLastPluggingCode();
                    String newPlugCode = parameter[1];
                    short countChange = (short) (serviceReefer.getChangePlugging() + 1);

                    if (oldPlugCode.equals("NULL") && newPlugCode.equals("NULL")) {
                        status = "No Plugging Allowed";
                        break;
                    } else if (newPlugCode.equals("NULL")) {
                        serviceReefer.setLastPluggingCode(oldPlugCode);
                        masterPluggingReefer = masterPluggingReeferFacadeRemote.find(oldPlugCode);
                        masterPluggingReefer.setAvailability("TRUE");
                        masterPluggingReeferFacadeRemote.edit(masterPluggingReefer);
                    } else if (oldPlugCode.equals("NULL")) {
                        if (!newPlugCode.equals(lastPlugCode)) {
                            serviceReefer.setChangePlugging(countChange);

                            masterPluggingReefer = masterPluggingReeferFacadeRemote.find(newPlugCode);
                            masterPluggingReefer.setAvailability("FALSE");
                            masterPluggingReeferFacadeRemote.edit(masterPluggingReefer);
                        }
                    } else {
                        if (!newPlugCode.equals(oldPlugCode)) {
                            serviceReefer.setChangePlugging(countChange);

                            masterPluggingReefer = masterPluggingReeferFacadeRemote.find(oldPlugCode);
                            masterPluggingReefer.setAvailability("TRUE");
                            masterPluggingReeferFacadeRemote.edit(masterPluggingReefer);
                        }
                        serviceReefer.setLastPluggingCode(oldPlugCode);
                        masterPluggingReefer = masterPluggingReeferFacadeRemote.find(newPlugCode);
                        masterPluggingReefer.setAvailability("FALSE");
                        masterPluggingReeferFacadeRemote.edit(masterPluggingReefer);
                    }
                    serviceReefer.setMasterPluggingReefer(new MasterPluggingReefer(newPlugCode));

                    reeferMonitoring.setContNo(parameter[0]);
                    reeferMonitoring.setServiceReefer(serviceReefer);
                    reeferMonitoring.setDateMontoring(start);
                    reeferMonitoring.setTemperature(new BigDecimal(parameter[3]));

                    try {
                        reeferMonitoringFacadeRemote.edit(reeferMonitoring);
                        serviceReeferFacadeRemote.edit(serviceReefer);
                        status = "Ok";
                    } catch (EJBException ex) {
                        status = ex.toString();
                    }
                } else {
                    status = "No Container has been monitored.";
                }
                break;
        }
        System.out.println("Status Method Cont Disch :" + status);
        return status;
    }

    /**
     * Web service operation
     * replaced by @ContainerMovementOnConfirm and @ContainerMovementOffConfirm
     */
    @WebMethod(operationName = "containerMovement")
    @Deprecated
    public String containerMovement(@WebParam(name = "type") int type, @WebParam(name = "parameter") String[] parameter) {
        return null;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "startService")
    public String startService(@WebParam(name = "type") int type, @WebParam(name = "noPpkb") String[] parameter) {
        UserUtil.setCurrentUser((String) parameter[4]);
        //TODO write your implementation code here:
        starOver();
        Date tgl = Calendar.getInstance().getTime();

        switch (type) {
            case 1:
                /**
                 * replaced by @StartService.startServiceConfirm
                 */
                List<Object[]> services = serviceVesselFacadeRemote.checkSpaceAvailability(Short.valueOf(parameter[2]), Short.valueOf(parameter[3]), parameter[1], parameter[0]);

                if (!services.isEmpty()) {
                    status = "Location Conflict!";
                } else {
                    List<Object[]> listSemua;

                    planningVessel = planningVesselFacadeRemote.find(parameter[0]);
                    planningVessel.setStatus("Servicing");
                    planningVesselFacadeRemote.edit(planningVessel);

                    //hapus dari booking online
                    Integer idBO = bookingOnlineFacadeRemote.findByBookingCode(planningVessel.getPreserviceVessel().getBookingCode());
                    if (idBO != null) {
                        bookingOnline = bookingOnlineFacadeRemote.find(idBO);
                        bookingOnline.setStatus("Servicing");
                        bookingOnlineFacadeRemote.edit(bookingOnline);
                    }

                    //copy ke service vessel
                    serviceVessel.setNoPpkb(planningVessel.getNoPpkb());
                    serviceVessel.setDockCode(parameter[1]);
                    serviceVessel.setBookingCode(planningVessel.getPreserviceVessel().getBookingCode());
                    serviceVessel.setTipePelayaran(planningVessel.getTipePelayaran());
                    serviceVessel.setEstDischarge(planningVessel.getEstDischarge());
                    serviceVessel.setEstLoad(planningVessel.getEstLoad());
                    serviceVessel.setDischarge(Short.parseShort("0"));
                    serviceVessel.setLoad(Short.parseShort("0"));
                    serviceVessel.setArrivalTime(tgl);
                    serviceVessel.setEta(planningVessel.getEta());
                    serviceVessel.setEtd(planningVessel.getEtd());
                    serviceVessel.setEbt(planningVessel.getEbt());
                    serviceVessel.setEstEndWork(planningVessel.getEstEndWork());
                    serviceVessel.setEstStartWork(planningVessel.getEstStartWork());
                    serviceVessel.setBerhtingTime(planningVessel.getEbt());
                    serviceVessel.setEndWorkTime(planningVessel.getEstEndWork());
                    serviceVessel.setStartWorkTime(planningVessel.getEstStartWork());
                    serviceVessel.setCloseRec(planningVessel.getCloseRec());
                    serviceVessel.setCloseDocRec(planningVessel.getCloseDocRec());
                    serviceVessel.setCloseEmpty(planningVessel.getCloseEmpty());
                    serviceVessel.setCloseDocMtyref(planningVessel.getCloseDocMtyref());
                    serviceVessel.setCloseReefer(planningVessel.getCloseReefer());
                    serviceVessel.setPaymentType(planningVessel.getPaymentType());
                    serviceVessel.setStatus(planningVessel.getStatus());
                    serviceVessel.setFrMeter(Short.valueOf(parameter[2]));
                    serviceVessel.setToMeter(Short.valueOf(parameter[3]));

                    serviceVesselFacadeRemote.create(serviceVessel);

                    //copy planning_cont_discharge ke service_cont_discharge
                    listSemua = planningContDischargeFacadeRemote.findPlanningContDischargeListNative(planningVessel.getNoPpkb());
                    if (listSemua != null) {
                        for (Object[] o : listSemua) {
                            PlanningContDischarge pcd = planningContDischargeFacadeRemote.find(o[9]);
                            ServiceContDischarge scd = new ServiceContDischarge();

                            scd.setContNo(pcd.getContNo());
                            scd.setServiceVessel(serviceVessel);
                            scd.setMasterCommodity(pcd.getMasterCommodity());
                            scd.setContSize(pcd.getContSize());
                            scd.setMasterContainerType(pcd.getMasterContainerType());
                            scd.setContStatus(pcd.getContStatus());
                            scd.setContGross(pcd.getContGross());
                            scd.setSealNo(pcd.getSealNo());
                            scd.setMasterYard(pcd.getMasterYard());
                            scd.setDangerous(pcd.getDg());
                            scd.setDgLabel(pcd.getDgLabel());
                            scd.setOverSize(pcd.getOverSize());
                            scd.setVBay(pcd.getVBay());
                            scd.setVRow(pcd.getVRow());
                            scd.setVTier(pcd.getVTier());
                            scd.setYRow(pcd.getYRow());
                            scd.setYSlot(pcd.getYSlot());
                            scd.setYTier(pcd.getYTier());
                            scd.setStartPlacementDate(tgl);
                            scd.setCrane("L");
                            scd.setPosition("01");
                            scd.setIsDelivery("FALSE");
                            scd.setIsBehandle("FALSE");
                            scd.setIsCfs("FALSE");
                            scd.setIsInspection("FALSE");
                            scd.setGrossClass(pcd.getGrossClass());
                            scd.setBlNo(pcd.getBlNo());
                            scd.setCancelLoading("FALSE");
                            scd.setDischPort(pcd.getDischPort());
                            scd.setLoadPort(pcd.getLoadPort());


                            serviceContDischargeFacadeRemote.create(scd);
                        }
                    }

                    //copy planning_trans_discharge ke service_cont_transhipment
                    listSemua = planningTransDischargeFacadeRemote.findPlanningTransDischargeListNative(planningVessel.getNoPpkb());
                    if (listSemua != null) {
                        for (Object[] o : listSemua) {
                            PlanningTransDischarge ptd = planningTransDischargeFacadeRemote.find(o[9]);
                            ServiceContTranshipment sct = new ServiceContTranshipment();

                            sct.setContNo(ptd.getContNo());
                            sct.setServiceVessel(serviceVessel);
                            sct.setMasterCommodity(ptd.getMasterCommodity());
                            sct.setContSize(ptd.getContSize());
                            sct.setMasterContainerType(ptd.getMasterContainerType());
                            sct.setContStatus(ptd.getContStatus());
                            sct.setContGross(ptd.getContGross());
                            sct.setSealNo(ptd.getSealNo());
                            sct.setMasterYard(ptd.getMasterYard());
                            sct.setDangerous(ptd.getDg());
                            sct.setDgLabel(ptd.getDgLabel());
                            sct.setOverSize(ptd.getOverSize());
                            sct.setVBay(ptd.getVBay());
                            sct.setVRow(ptd.getVRow());
                            sct.setVTier(ptd.getVTier());
                            sct.setYRow(ptd.getYRow());
                            sct.setYSlot(ptd.getYSlot());
                            sct.setYTier(ptd.getYTier());
                            sct.setStartPlacementDate(tgl);
                            sct.setPosition("01");
                            if (ptd.getPlanningVessel() != null) {
                                sct.setNewPpkb(ptd.getPlanningVessel().getNoPpkb());
                            }
                            sct.setCrane("L");
                            sct.setBlNo(ptd.getBlNo());
                            sct.setDischPort(ptd.getDischPort());
                            sct.setLoadPort(ptd.getLoadPort());


                            serviceContTranshipmentFacadeRemote.create(sct);
                        }
                    }

                    //copy planning_shift_discharge ke service shifting
                    listSemua = planningShiftDischargeFacadeRemote.findPlanningShiftDischargeListNative(planningVessel.getNoPpkb());
                    if (listSemua != null) {
                        for (Object[] o : listSemua) {
                            PlanningShiftDischarge psd = planningShiftDischargeFacadeRemote.find(o[8]);
                            ServiceShifting ss = new ServiceShifting();

                            ss.setContNo(psd.getContNo());
                            ss.setServiceVessel(serviceVessel);
                            ss.setMasterCommodity(psd.getMasterCommodity());
                            ss.setContSize(psd.getContSize());
                            ss.setMasterContainerType(psd.getMasterContainerType());
                            ss.setContStatus(psd.getContStatus());
                            ss.setContGross(psd.getContGross());
                            ss.setSealNo(psd.getSealNo());
                            ss.setOverSize(psd.getOverSize());
                            ss.setDg(psd.getDg());
                            ss.setDgLabel(psd.getDgLabel());
                            ss.setDischPort(psd.getDischPort());
                            ss.setLoadPort(psd.getLoadPort());
                            ss.setVBay(psd.getVBay());
                            ss.setVRow(psd.getVRow());
                            ss.setVTier(psd.getVTier());
                            ss.setShiftTo(psd.getShiftTo());
                            ss.setCrane("L");
                            ss.setOperation("DISCHARGE");
                            ss.setIsLanded("FALSE");
                            ss.setIsPlanning("TRUE");
                            ss.setIsReshipping("FALSE");
                            ss.setBlNo(psd.getBlNo());

                            serviceShiftingFacadeRemote.create(ss);
                        }
                    }
                }
                break;
            case 2:
                /**
                 * replaced by @StartService.updateVesselSchedule
                 */
                serviceVessel = serviceVesselFacadeRemote.find(parameter[0]);
                serviceVessel.setArrivalTime(ParseDate.changeDate2(parameter[1]));
                serviceVessel.setBerhtingTime(ParseDate.changeDate2(parameter[2]));
                serviceVessel.setStartWorkTime(ParseDate.changeDate2(parameter[3]));
                serviceVessel.setEndWorkTime(ParseDate.changeDate2(parameter[4]));
                serviceVessel.setDepartureTime(ParseDate.changeDate2(parameter[5]));

                try {
                    serviceVesselFacadeRemote.edit(serviceVessel);
                } catch (EJBException ex) {
                    status = ex.toString();
                }

                //TODO endservice rieut
                break;
            default:
                //do nothing
                break;

        }
        return status;
    }
}
