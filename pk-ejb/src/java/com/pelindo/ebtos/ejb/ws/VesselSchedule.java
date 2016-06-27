/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.ejb.ws;

import com.pelindo.ebtos.ejb.facade.remote.PlanningVesselFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceVesselFacadeRemote;
import com.pelindo.ebtos.ejb.ws.model.VesselScheduleInitialData;
import com.pelindo.ebtos.ejb.ws.model.VesselScheduleSubmitData;
import com.pelindo.ebtos.ejb.ws.model.IMessageable;
import com.pelindo.ebtos.ejb.ws.model.Message;
import com.pelindo.ebtos.ejb.ws.model.Vessel;
import com.pelindo.ebtos.model.db.ServiceVessel;
import com.pelindo.ebtos.security.UserUtil;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
public class VesselSchedule {
    @EJB
    private PlanningVesselFacadeRemote planningVesselFacade;
    @EJB
    private ServiceVesselFacadeRemote serviceVesselFacadeRemote;

    @WebMethod(operationName = "prepareVesselScheduleUI")
    public VesselScheduleInitialData prepareVesselScheduleUI() {
        VesselScheduleInitialData initialData = null;

        try {
            List<Object[]> vessels = planningVesselFacade.findByStatus4HHT("Servicing");
            Vessel vessel;

            if (!vessels.isEmpty()) {
                initialData = new VesselScheduleInitialData(IMessageable.OK, "Data found!");
                vessel = findVesselSchedules((String) vessels.get(0)[0]);
            } else {
                initialData = new VesselScheduleInitialData(IMessageable.ERROR, "Data not found!");
                vessel = new Vessel(IMessageable.ERROR, "Data not found!");
            }
            
            initialData = new VesselScheduleInitialData(IMessageable.OK, "Data found!");
            initialData.setVessels(vessels);
            initialData.setVessel(vessel);
        } catch (RuntimeException re) {
            initialData = new VesselScheduleInitialData(IMessageable.ERROR, "Internal error!");
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", re);
        }

        return initialData;
    }

    @WebMethod(operationName = "findVesselSchedules")
    public Vessel findVesselSchedules(@WebParam(name = "noPpkb") String noPpkb){
        Vessel vessel = null;

        try {
            ServiceVessel serviceVessel = serviceVesselFacadeRemote.findByPpkbAndStatus(noPpkb, "Servicing");

            if (serviceVessel != null) {
                SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");

                vessel = new Vessel(IMessageable.OK, "Data found!");
                vessel.setNoPpkb(serviceVessel.getNoPpkb());

                vessel.setEstArrivalTime(dateFormatter.format(serviceVessel.getEta()));
                vessel.setEstBerthingTime(dateFormatter.format(serviceVessel.getEbt()));
                vessel.setEstStartWorkTime(dateFormatter.format(serviceVessel.getEstStartWork()));
                vessel.setEstEndWorkTime(dateFormatter.format(serviceVessel.getEstEndWork()));
                vessel.setEstDepartureTime(dateFormatter.format(serviceVessel.getEtd()));

                if (serviceVessel.getArrivalTime() != null) {
                    vessel.setArrivalTime(dateFormatter.format(serviceVessel.getArrivalTime()));
                }

                if (serviceVessel.getBerhtingTime() != null){
                    vessel.setBerthingTime(dateFormatter.format(serviceVessel.getBerhtingTime()));
                }

                if (serviceVessel.getStartWorkTime() != null){
                    vessel.setStartWorkTime(dateFormatter.format(serviceVessel.getStartWorkTime()));
                }

                if (serviceVessel.getEndWorkTime() != null){
                    vessel.setEndWorkTime(dateFormatter.format(serviceVessel.getEndWorkTime()));
                }

                if (serviceVessel.getDepartureTime() != null){
                    vessel.setDepartureTime(dateFormatter.format(serviceVessel.getDepartureTime()));
                }
            } else {
                vessel = new Vessel(IMessageable.ERROR, "Data not found!");
            }
        } catch (RuntimeException re) {
            vessel = new Vessel(IMessageable.ERROR, "Internal error!");
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", re);
        }

        return vessel;
    }

    @WebMethod(operationName = "updateVesselSchedule")
    public Message updateVesselSchedule(@WebParam(name = "submitData") VesselScheduleSubmitData submitData) {
        Message message = null;

        try {
            ServiceVessel serviceVessel = serviceVesselFacadeRemote.findByPpkbAndStatus(submitData.getNoPpkb(), "Servicing");

            if (serviceVessel != null) {
                UserUtil.setCurrentUser(submitData.getCreatedBy());
                SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");

                serviceVessel.setArrivalTime(dateFormatter.parse(submitData.getArrivalTime()));
                serviceVessel.setBerhtingTime(dateFormatter.parse(submitData.getBerthingTime()));
                serviceVessel.setStartWorkTime(dateFormatter.parse(submitData.getStartWorkTime()));
                serviceVessel.setEndWorkTime(dateFormatter.parse(submitData.getEndWorkTime()));
                serviceVessel.setDepartureTime(dateFormatter.parse(submitData.getDepartureTime()));
                serviceVesselFacadeRemote.edit(serviceVessel);

                message = new Message(IMessageable.ERROR, "Success!");
            } else {
                message = new Message(IMessageable.ERROR, "Data not found!");
            }
        } catch (ParseException ex) {
            message = new Message(IMessageable.ERROR, "Internal error!");
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught when date parsing", ex);
        } catch (RuntimeException re) {
            message = new Message(IMessageable.ERROR, "Internal error!");
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", re);
        }
        return message;
    }
}
