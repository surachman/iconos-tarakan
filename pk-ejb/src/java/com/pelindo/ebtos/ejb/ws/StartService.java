/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.ejb.ws;

import com.pelindo.ebtos.ejb.facade.remote.BookingOnlineFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterDockFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningContDischargeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningShiftDischargeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningTransDischargeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningVesselFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceContDischargeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceContTranshipmentFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceShiftingFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceVesselFacadeRemote;
import com.pelindo.ebtos.ejb.ws.model.IMessageable;
import com.pelindo.ebtos.ejb.ws.model.Message;
import com.pelindo.ebtos.ejb.ws.model.StartServiceInitialData;
import com.pelindo.ebtos.ejb.ws.model.StartServiceSubmitData;
import com.pelindo.ebtos.ejb.ws.model.Vessel;
import com.pelindo.ebtos.model.db.BookingOnline;
import com.pelindo.ebtos.model.db.PlanningContDischarge;
import com.pelindo.ebtos.model.db.PlanningShiftDischarge;
import com.pelindo.ebtos.model.db.PlanningTransDischarge;
import com.pelindo.ebtos.model.db.PlanningVessel;
import com.pelindo.ebtos.model.db.ServiceContDischarge;
import com.pelindo.ebtos.model.db.ServiceContTranshipment;
import com.pelindo.ebtos.model.db.ServiceShifting;
import com.pelindo.ebtos.model.db.ServiceVessel;
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
public class StartService {
    @EJB
    private PlanningVesselFacadeRemote planningVesselFacade;
    @EJB
    private PlanningVesselFacadeRemote planningVesselFacadeRemote;
    @EJB
    private MasterDockFacadeRemote masterDockFacadeRemote;
    @EJB
    private ServiceVesselFacadeRemote serviceVesselFacadeRemote;
    @EJB
    private ServiceVesselFacadeRemote serviceVesselFacade;
    @EJB
    private PlanningContDischargeFacadeRemote planningContDischargeFacade;
    @EJB
    private ServiceContDischargeFacadeRemote serviceContDischargeFacade;
    @EJB
    private PlanningTransDischargeFacadeRemote planningTransDischargeFacade;
    @EJB
    private ServiceContTranshipmentFacadeRemote serviceContTranshipmentFacade;
    @EJB
    private PlanningShiftDischargeFacadeRemote planningShiftDischargeFacade;
    @EJB
    private ServiceShiftingFacadeRemote serviceShiftingFacade;
    @EJB
    private BookingOnlineFacadeRemote bookingOnlineFacade;

    @WebMethod(operationName = "prepareStartServiceUI")
    public StartServiceInitialData prepareStartServiceUI() {
        StartServiceInitialData startServiceInitialData = null;

        try {
            List<Object[]> vessels = planningVesselFacade.findByStatus4HHT("ReadyService");
            List<String> masterDocks = masterDockFacadeRemote.findDocks();
            Vessel vessel;

            if (!vessels.isEmpty()) {
                startServiceInitialData = new StartServiceInitialData(IMessageable.OK, "Data found!");
                vessel = findServiceVessel((String) vessels.get(0)[0]);
            } else {
                startServiceInitialData = new StartServiceInitialData(IMessageable.ERROR, "Data not found!");
                vessel = new Vessel(IMessageable.ERROR, "Data not found!");
            }

            startServiceInitialData.setVessels(vessels);
            startServiceInitialData.setInititalVesselData(vessel);
            startServiceInitialData.setMasterDocks(masterDocks);
        } catch (RuntimeException re) {
            startServiceInitialData = new StartServiceInitialData(IMessageable.ERROR, "Internal error!");
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", re);
        }

        return startServiceInitialData;
    }

    @WebMethod(operationName = "findServiceVessel")
    public Vessel findServiceVessel(@WebParam(name = "noPpkb") String noPpkb){
        Vessel vessel = null;

        try {
            PlanningVessel planningVessel = planningVesselFacadeRemote.findByPpkbAndStatus(noPpkb, "ReadyService");

            if (planningVessel != null) {
                SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

                vessel = new Vessel(IMessageable.OK, "Data found!");
                vessel.setNoPpkb(planningVessel.getNoPpkb());
                vessel.setBookingCode(planningVessel.getPreserviceVessel().getBookingCode());
                vessel.setEta(dateFormatter.format(planningVessel.getEta()));
                vessel.setDischargeConts(planningVessel.getEstDischarge());
                vessel.setLoadConts(planningVessel.getEstLoad());
                vessel.setDockCode(planningVessel.getMasterDock().getDockCode());
                vessel.setFrMeter(planningVessel.getFrMeter());
                vessel.setToMeter(planningVessel.getToMeter());
                vessel.setLoa(planningVessel.getPreserviceVessel().getMasterVessel().getLoa());
            } else {
                vessel = new Vessel(IMessageable.ERROR, "Data not found!");
            }
        } catch (RuntimeException re) {
            vessel = new Vessel(IMessageable.ERROR, "Internal error!");
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", re);
        }

        return vessel;
    }

    @WebMethod(operationName = "startServiceConfirm")
    public Message startServiceConfirm(@WebParam(name = "submitData") StartServiceSubmitData submitData) {
        Message message = null;

        try {
            PlanningVessel planningVessel = planningVesselFacadeRemote.findByPpkbAndStatus(submitData.getNoPpkb(), "ReadyService");

            if (planningVessel != null) {
                UserUtil.setCurrentUser(submitData.getCreatedBy());
                List<Object[]> services = serviceVesselFacadeRemote.checkSpaceAvailability(Short.decode(submitData.getFrMeter().trim()), Short.decode(submitData.getToMeter().trim()), submitData.getDockCode(), submitData.getNoPpkb());

                if (!services.isEmpty()) {
                    handleStartService(planningVessel, submitData);
                } else {
                    message = new Message(IMessageable.ERROR, "Berth location conflict with another vessel!");
                }
            } else {
                message = new Message(IMessageable.ERROR, "Data not found!");
            }

        } catch (RuntimeException re) {
            message = new Message(IMessageable.ERROR, "Internal error!");
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", re);
        }

        return message;
    }

    private void handleStartService(PlanningVessel planningVessel, StartServiceSubmitData submitData) {
        Date tgl = new Date();
        ServiceVessel serviceVessel = new ServiceVessel();
        serviceVessel.setNoPpkb(planningVessel.getNoPpkb());
        serviceVessel.setDockCode(planningVessel.getMasterDock().getDockCode());
        serviceVessel.setBookingCode(planningVessel.getPreserviceVessel().getBookingCode());
        serviceVessel.setTipePelayaran(planningVessel.getTipePelayaran());
        serviceVessel.setEstDischarge(planningVessel.getEstDischarge());
        serviceVessel.setEstLoad(planningVessel.getEstLoad());
        serviceVessel.setDischarge(Short.parseShort("0"));
        serviceVessel.setLoad(Short.parseShort("0"));
        serviceVessel.setArrivalTime(planningVessel.getEta());
        serviceVessel.setEta(planningVessel.getEta());
        serviceVessel.setEtd(planningVessel.getEtd());
        serviceVessel.setEbt(planningVessel.getEbt());
        serviceVessel.setEstEndWork(planningVessel.getEstEndWork());
        serviceVessel.setEstStartWork(planningVessel.getEstStartWork());
        serviceVessel.setBerhtingTime(planningVessel.getEbt());
        serviceVessel.setStartWorkTime(tgl);
        serviceVessel.setCloseRec(planningVessel.getCloseRec());
        serviceVessel.setCloseDocRec(planningVessel.getCloseDocRec());
        serviceVessel.setCloseEmpty(planningVessel.getCloseEmpty());
        serviceVessel.setCloseDocMtyref(planningVessel.getCloseDocMtyref());
        serviceVessel.setCloseReefer(planningVessel.getCloseReefer());
        serviceVessel.setPaymentType(planningVessel.getPaymentType());
        serviceVessel.setStatus("Servicing");
        serviceVessel.setFrMeter(planningVessel.getFrMeter());
        serviceVessel.setToMeter(planningVessel.getToMeter());
        serviceVessel.setLoadServiceType(planningVessel.getLoadServiceType());
        serviceVessel.setDischargeServiceType(planningVessel.getDischargeServiceType());
        serviceVesselFacade.create(serviceVessel);

        List<PlanningContDischarge> planningContDischarges = planningContDischargeFacade.findByNoPpkb(planningVessel.getNoPpkb());

        for (PlanningContDischarge planningContDischarge : planningContDischarges) {
            ServiceContDischarge serviceContDischarge = new ServiceContDischarge();
            serviceContDischarge.setContNo(planningContDischarge.getContNo());
            serviceContDischarge.setMlo(planningContDischarge.getMlo());
            serviceContDischarge.setServiceVessel(serviceVessel);
            serviceContDischarge.setMasterCommodity(planningContDischarge.getMasterCommodity());
            serviceContDischarge.setContSize(planningContDischarge.getContSize());
            serviceContDischarge.setMasterContainerType(planningContDischarge.getMasterContainerType());
            serviceContDischarge.setContStatus(planningContDischarge.getContStatus());
            serviceContDischarge.setContGross(planningContDischarge.getContGross());
            serviceContDischarge.setSealNo(planningContDischarge.getSealNo());
            serviceContDischarge.setMasterYard(planningContDischarge.getMasterYard());
            serviceContDischarge.setDangerous(planningContDischarge.getDg());
            serviceContDischarge.setDgLabel(planningContDischarge.getDgLabel());
            serviceContDischarge.setOverSize(planningContDischarge.getOverSize());
            serviceContDischarge.setVBay(planningContDischarge.getVBay());
            serviceContDischarge.setVRow(planningContDischarge.getVRow());
            serviceContDischarge.setVTier(planningContDischarge.getVTier());
            serviceContDischarge.setYRow(planningContDischarge.getYRow());
            serviceContDischarge.setYSlot(planningContDischarge.getYSlot());
            serviceContDischarge.setYTier(planningContDischarge.getYTier());
            serviceContDischarge.setIsImport(planningContDischarge.getIsImport());
            serviceContDischarge.setStartPlacementDate(tgl);
            serviceContDischarge.setCrane("L");
            serviceContDischarge.setPosition("01");
            serviceContDischarge.setIsDelivery("FALSE");
            serviceContDischarge.setIsBehandle("FALSE");
            serviceContDischarge.setIsCfs("FALSE");
            serviceContDischarge.setIsInspection("FALSE");
            serviceContDischarge.setGrossClass(planningContDischarge.getGrossClass());
            serviceContDischarge.setBlNo(planningContDischarge.getBlNo());
            serviceContDischarge.setCancelLoading("FALSE");
            serviceContDischarge.setDischPort(planningContDischarge.getDischPort());
            serviceContDischarge.setLoadPort(planningContDischarge.getLoadPort());
            serviceContDischargeFacade.create(serviceContDischarge);
        }
        
        List<PlanningTransDischarge> planningTransDischarges = planningTransDischargeFacade.findByNoPpkb(planningVessel.getNoPpkb());

        for (PlanningTransDischarge planningTransDischarge : planningTransDischarges) {
            ServiceContTranshipment serviceContTranshipment = new ServiceContTranshipment();

            if(planningTransDischarge.getPlanningVessel() != null) {
                serviceContTranshipment.setNewPpkb(planningTransDischarge.getPlanningVessel().getNoPpkb());
            }

            serviceContTranshipment.setContNo(planningTransDischarge.getContNo());
            serviceContTranshipment.setMlo(planningTransDischarge.getMlo());
            serviceContTranshipment.setServiceVessel(serviceVessel);
            serviceContTranshipment.setMasterCommodity(planningTransDischarge.getMasterCommodity());
            serviceContTranshipment.setPortOfDelivery(planningTransDischarge.getPortOfDelivery());
            serviceContTranshipment.setContSize(planningTransDischarge.getContSize());
            serviceContTranshipment.setMasterContainerType(planningTransDischarge.getMasterContainerType());
            serviceContTranshipment.setContStatus(planningTransDischarge.getContStatus());
            serviceContTranshipment.setContGross(planningTransDischarge.getContGross());
            serviceContTranshipment.setSealNo(planningTransDischarge.getSealNo());
            serviceContTranshipment.setMasterYard(planningTransDischarge.getMasterYard());
            serviceContTranshipment.setDangerous(planningTransDischarge.getDg());
            serviceContTranshipment.setDgLabel(planningTransDischarge.getDgLabel());
            serviceContTranshipment.setOverSize(planningTransDischarge.getOverSize());
            serviceContTranshipment.setVBay(planningTransDischarge.getVBay());
            serviceContTranshipment.setVRow(planningTransDischarge.getVRow());
            serviceContTranshipment.setVTier(planningTransDischarge.getVTier());
            serviceContTranshipment.setYRow(planningTransDischarge.getYRow());
            serviceContTranshipment.setYSlot(planningTransDischarge.getYSlot());
            serviceContTranshipment.setYTier(planningTransDischarge.getYTier());
            serviceContTranshipment.setStartPlacementDate(tgl);
            serviceContTranshipment.setIsExportImport(planningTransDischarge.getIsExportImport());
            serviceContTranshipment.setPosition("01");
            serviceContTranshipment.setCrane("L");
            serviceContTranshipment.setBlNo(planningTransDischarge.getBlNo());
            serviceContTranshipment.setDischPort(planningTransDischarge.getDischPort());
            serviceContTranshipment.setLoadPort(planningTransDischarge.getLoadPort());
            serviceContTranshipmentFacade.create(serviceContTranshipment);
        }

        List<PlanningShiftDischarge> planningShiftDischarges = planningShiftDischargeFacade.findByNoPpkb(planningVessel.getNoPpkb());

        for (PlanningShiftDischarge planningShiftDischarge : planningShiftDischarges) {
            ServiceShifting serviceShifting = new ServiceShifting();
            serviceShifting.setContNo(planningShiftDischarge.getContNo());
            serviceShifting.setMlo(planningShiftDischarge.getMlo());
            serviceShifting.setServiceVessel(serviceVessel);
            serviceShifting.setMasterCommodity(planningShiftDischarge.getMasterCommodity());
            serviceShifting.setContSize(planningShiftDischarge.getContSize());
            serviceShifting.setMasterContainerType(planningShiftDischarge.getMasterContainerType());
            serviceShifting.setContStatus(planningShiftDischarge.getContStatus());
            serviceShifting.setContGross(planningShiftDischarge.getContGross());
            serviceShifting.setSealNo(planningShiftDischarge.getSealNo());
            serviceShifting.setOverSize(planningShiftDischarge.getOverSize());
            serviceShifting.setDg(planningShiftDischarge.getDg());
            serviceShifting.setDgLabel(planningShiftDischarge.getDgLabel());
            serviceShifting.setDischPort(planningShiftDischarge.getDischPort());
            serviceShifting.setLoadPort(planningShiftDischarge.getLoadPort());
            serviceShifting.setVBay(planningShiftDischarge.getVBay());
            serviceShifting.setIsExportImport(planningShiftDischarge.getIsExportImport());
            serviceShifting.setVRow(planningShiftDischarge.getVRow());
            serviceShifting.setVTier(planningShiftDischarge.getVTier());
            serviceShifting.setShiftTo(planningShiftDischarge.getShiftTo());
            serviceShifting.setCrane("L");
            serviceShifting.setOperation("DISCHARGE");
            serviceShifting.setIsLanded("FALSE");
            serviceShifting.setIsPlanning("TRUE");
            serviceShifting.setIsReshipping("FALSE");
            serviceShifting.setBlNo(planningShiftDischarge.getBlNo());
            serviceShifting.setPosition("01");
            serviceShiftingFacade.create(serviceShifting);
        }

        planningVessel.setStatus("Servicing");
        planningVessel.setEnableOpenStack("FALSE");
        planningVesselFacade.edit(planningVessel);

        BookingOnline bookingOnline = bookingOnlineFacade.findByBooking(planningVessel.getPreserviceVessel().getBookingCode());
        
        if (bookingOnline != null) {
            bookingOnline.setStatus("Servicing");
            bookingOnlineFacade.edit(bookingOnline);
        }
    }
}
