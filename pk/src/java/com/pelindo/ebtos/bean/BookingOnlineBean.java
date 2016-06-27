/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.bean;

import com.qtasnim.jsf.FacesHelper;
import com.pelindo.ebtos.ejb.facade.remote.BookingOnlineFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.IDGeneratorFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterCustomerFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterDockFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterPortFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterVesselFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningVesselFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PreserviceVesselFacadeRemote;
import com.pelindo.ebtos.model.db.BookingOnline;
import com.pelindo.ebtos.model.db.PlanningVessel;
import com.pelindo.ebtos.model.db.PreserviceVessel;
import com.pelindo.ebtos.model.db.master.MasterCustomer;
import com.pelindo.ebtos.model.db.master.MasterDock;
import com.pelindo.ebtos.model.db.master.MasterPort;
import com.pelindo.ebtos.model.db.master.MasterVessel;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.context.RequestContext;

/**
 *
 * @author dycoder
 */
@ManagedBean(name="bookingOnlineBean")
@ViewScoped
public class BookingOnlineBean implements Serializable{
    @EJB
    private IDGeneratorFacadeRemote iDGeneratorFacade;
    @EJB 
    private MasterCustomerFacadeRemote masterCustomerFacadeRemote;
    @EJB 
    private BookingOnlineFacadeRemote bookingOnlineFacade;
    @EJB 
    private MasterVesselFacadeRemote masterVesselFacadeRemote;
    @EJB 
    private PreserviceVesselFacadeRemote preserviceVesselFacadeRemote;
    @EJB 
    private PlanningVesselFacadeRemote planningVesselFacade;
    @EJB 
    private MasterDockFacadeRemote masterDockFacade;
    @EJB 
    private MasterPortFacadeRemote masterPortFacadeRemote;

    private List<Object[]> bookingOnlines ;
    private List<Object[]> masterVessels;
    private BookingOnline bookingOnline;
    private PreserviceVessel preserviceVessel;
    private MasterCustomer masterCustomer;
    private MasterVessel vessel;
    private MasterPort nextport;
    private MasterPort lastport;

    /** Creates a new instance of BookingOnlineBean */
    public BookingOnlineBean() {}

    @PostConstruct
    private void construct() {
        bookingOnline = new BookingOnline();
        preserviceVessel = new PreserviceVessel();
        masterCustomer = new MasterCustomer();
        vessel = new MasterVessel();
        nextport = new MasterPort();
        lastport = new MasterPort();
        bookingOnlines = bookingOnlineFacade.findBooking();
    }

    public void handleSelectVesselCode(ActionEvent event) {
        String vesselCode = (String) event.getComponent().getAttributes().get("vessel_code");
        vessel = masterVesselFacadeRemote.find(vesselCode);
        bookingOnline.setVesselName(vessel.getName());
        bookingOnline.setBrt(vessel.getBrt());
        bookingOnline.setCallSign(vessel.getCallSign());
        bookingOnline.setFlag(vessel.getFlag());
        bookingOnline.setNrt(vessel.getNrt());
        bookingOnline.setLoa(vessel.getLoa());
        bookingOnline.setGrt(vessel.getGrt());
        bookingOnline.setDwt(vessel.getDwt());
    }

    public void handleSelect(ActionEvent event) {
        Integer idBooking = (Integer) event.getComponent().getAttributes().get("idBook");
        bookingOnline = bookingOnlineFacade.find(idBooking);
        masterVessels = masterVesselFacadeRemote.findMasterVesselsByVesselName('%'+bookingOnline.getVesselName()+'%');
        masterCustomer = masterCustomerFacadeRemote.find(bookingOnline.getCustCode());
        nextport = masterPortFacadeRemote.find(bookingOnline.getNextPortCode());
        lastport = masterPortFacadeRemote.find(bookingOnline.getLastPortCode());
    }

    public void handleFindVessel(ActionEvent event){
        masterVessels = masterVesselFacadeRemote.findMasterVesselsByVesselName('%'+bookingOnline.getVesselName()+'%');
        if(masterVessels.isEmpty())
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.search.notfound");
    }

    public void handleFindAllVessel(ActionEvent event){
        masterVessels = masterVesselFacadeRemote.findMasterVesselsByVesselName('%'+""+'%');
    }

    public void handleDelete(ActionEvent event) {
        bookingOnline.setStatus("Canceled");
        bookingOnlineFacade.edit(bookingOnline);
        bookingOnlines = bookingOnlineFacade.findBooking();
        FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.cancel.succeeded");
    }

    public void handleSaveConfirm(ActionEvent event) {
        FacesContext cont = FacesContext.getCurrentInstance();
        boolean loggedIn = false;
        if (vessel.getVesselCode() == null) {
            loggedIn = false;
            FacesHelper.addFacesMessage(cont, FacesMessage.SEVERITY_WARN, "Warning", "application.verify.vessel");
        } else if ((bookingOnline.getActivity() == 3 || bookingOnline.getActivity() == 2) && preserviceVessel.getOpenStack() == null) {
            loggedIn = false;
            FacesHelper.addFacesMessage(cont, FacesMessage.SEVERITY_WARN, "Warning", "application.validation.openstack");
        } else {
            loggedIn = true;

            SimpleDateFormat dateFormatBooking = new SimpleDateFormat("ddMMyyyy-HHmmss");
            Date tglBooking = Calendar.getInstance().getTime();
            String bookingId = "EBTOS" + dateFormatBooking.format(tglBooking);

            bookingOnline.setBookingCode(bookingId);
            bookingOnline.setStatus("Confirmed");
            bookingOnlineFacade.edit(bookingOnline);

            //copy ke preservice
            preserviceVessel.setBookingCode(bookingId);
            preserviceVessel.setActivity(bookingOnline.getActivity());
            preserviceVessel.setEstDischarge(bookingOnline.getEstDischarge());
            preserviceVessel.setEstLoad(bookingOnline.getEstLoad());
            preserviceVessel.setEta(bookingOnline.getEta());
            preserviceVessel.setEtd(bookingOnline.getEtd());
            preserviceVessel.setMasterCustomer(masterCustomer);
            preserviceVessel.setMasterPort(nextport);
            preserviceVessel.setMasterPort1(lastport);
            preserviceVessel.setMasterVessel(vessel);
            preserviceVessel.setVoyIn(bookingOnline.getVoyIn());
            preserviceVessel.setVoyOut(bookingOnline.getVoyOut());
            preserviceVessel.setStatus("Confirm");

            preserviceVesselFacadeRemote.create(preserviceVessel);

            // copy to planning vessel
            PlanningVessel planningVessel = new PlanningVessel();
            planningVessel.setPreserviceVessel(new PreserviceVessel());
            planningVessel.setMasterDock(new MasterDock());

            planningVessel.setNoPpkb(iDGeneratorFacade.generatePpkbID());
            planningVessel.setPreserviceVessel(preserviceVessel);
            planningVessel.setEta(preserviceVessel.getEta());
            planningVessel.setEtd(preserviceVessel.getEtd());
            planningVessel.setVesselCode(preserviceVessel.getMasterVessel().getVesselCode());
            planningVessel.setEstDischarge(preserviceVessel.getEstDischarge());
            planningVessel.setEstLoad(preserviceVessel.getEstLoad());
            planningVessel.setStatus(preserviceVessel.getStatus());
            planningVessel.setMasterDock(masterDockFacade.find("1"));
            planningVessel.setTipePelayaran("d");
            planningVessel.setCheckBaplie("TRUE");

            planningVesselFacade.create(planningVessel);

            planningVessel = new PlanningVessel();
            preserviceVessel = new PreserviceVessel();
            FacesHelper.addFacesMessage(cont, FacesMessage.SEVERITY_INFO, "Info", "application.confirm.succeeded");
        }
        bookingOnlines = bookingOnlineFacade.findBooking();
        RequestContext.getCurrentInstance().addCallbackParam("loggedIn", loggedIn);
    }

    /**
     * @return the bookingOnlines
     */
    public List<Object[]> getBookingOnlines() {
        return bookingOnlines;
    }

    /**
     * @param bookingOnlines the bookingOnlines to set
     */
    public void setBookingOnlines(List<Object[]> bookingOnlines) {
        this.bookingOnlines = bookingOnlines;
    }

    /**
     * @return the masterVessels
     */
    public List<Object[]> getMasterVessels() {
        return masterVessels;
    }

    /**
     * @param masterVessels the masterVessels to set
     */
    public void setMasterVessels(List<Object[]> masterVessels) {
        this.masterVessels = masterVessels;
    }

    /**
     * @return the bookingOnline
     */
    public BookingOnline getBookingOnline() {
        return bookingOnline;
    }

    /**
     * @param bookingOnline the bookingOnline to set
     */
    public void setBookingOnline(BookingOnline bookingOnline) {
        this.bookingOnline = bookingOnline;
    }

    /**
     * @return the preserviceVessel
     */
    public PreserviceVessel getPreserviceVessel() {
        return preserviceVessel;
    }

    /**
     * @param preserviceVessel the preserviceVessel to set
     */
    public void setPreserviceVessel(PreserviceVessel preserviceVessel) {
        this.preserviceVessel = preserviceVessel;
    }

    /**
     * @return the masterCustomer
     */
    public MasterCustomer getMasterCustomer() {
        return masterCustomer;
    }

    /**
     * @param masterCustomer the masterCustomer to set
     */
    public void setMasterCustomer(MasterCustomer masterCustomer) {
        this.masterCustomer = masterCustomer;
    }

    /**
     * @return the nextport
     */
    public MasterPort getNextport() {
        return nextport;
    }

    /**
     * @param Nextport the nextport to set
     */
    public void setNextport(MasterPort nextport) {
        this.nextport = nextport;
    }

    /**
     * @return the lastport
     */
    public MasterPort getLastport() {
        return lastport;
    }

    /**
     * @param Lastport the lastport to set
     */
    public void setLastport(MasterPort lastport) {
        this.lastport = lastport;
    }

}
