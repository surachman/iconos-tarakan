/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.bean;

import com.pelindo.ebtos.ejb.facade.remote.MasterCustomerFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterDockFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterPortFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterVesselFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningVesselFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PreserviceVesselFacadeRemote;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.primefaces.context.RequestContext;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author dycoder
 */
//@Named(value = "shipArrivalPlanning")
//@ConversationScoped
@ManagedBean(name = "shipArrivalPlanning")
@ViewScoped
public class ShipArrivalPlanning implements Serializable {

  

    MasterDockFacadeRemote masterDockFacade = lookupMasterDockFacadeRemote();
    PlanningVesselFacadeRemote planningVesselFacade = lookupPlanningVesselFacadeRemote();
    PreserviceVesselFacadeRemote preserviceVesselFacadeRemote = lookupPreserviceVesselFacadeRemote();
    MasterPortFacadeRemote masterPortFacadeRemote = lookupMasterPortFacadeRemote();
    MasterCustomerFacadeRemote masterCustomerFacadeRemote = lookupMasterCustomerFacadeRemote();
    MasterVesselFacadeRemote masterVesselFacadeRemote = lookupMasterVesselFacadeRemote();

    private List<Object[]> preserviceVesselss = lookupPreserviceVesselFacadeRemote().findPreserviceVessels();
    private List<Object[]> masterVessels = lookupMasterVesselFacadeRemote().findMasterVessels();
    private List<Object[]> masterCustomers = lookupMasterCustomerFacadeRemote().findMasterCustomers();
    private List<MasterPort> masterPorts = lookupMasterPortFacadeRemote().findAll();
    private PreserviceVessel preserviceVessel;
    private boolean visibleHiji;
    private boolean visibleDua;
    private Integer valueRadio = 0;
    private boolean bookingActivity;

    /** Creates a new instance of BookingBean */
    public ShipArrivalPlanning() {
        preserviceVessel=new PreserviceVessel();
        //initial();
    }

    public void initial(){
        preserviceVessel = new PreserviceVessel();
        preserviceVessel.setMasterCustomer(new MasterCustomer());
        preserviceVessel.setMasterVessel(new MasterVessel(""));
        preserviceVessel.setMasterPort(new MasterPort());
        preserviceVessel.setMasterPort1(new MasterPort());
        visibleHiji=false;
        visibleDua=false;
    }

    public void handleAdd(ActionEvent event){
        System.out.println("add");
        initial();
        preserviceVessel.setActivity((short) 3);
    }

    public void handleSelectVesselCode(ActionEvent event) {
        String vessel_code = (String) event.getComponent().getAttributes().get("vessel_code");
        MasterVessel vessel = masterVesselFacadeRemote.find(vessel_code);
        preserviceVessel.setMasterVessel(vessel);
    }

    public void handleSelectVesselCustCode(ActionEvent event) {
        String cust_code = (String) event.getComponent().getAttributes().get("cust_code");
        preserviceVessel.setMasterCustomer(masterCustomerFacadeRemote.find(cust_code));
    }

    public void handleEditDeleteButton(ActionEvent event) {
        System.out.println("select");
        String booking_code = (String) event.getComponent().getAttributes().get("booking_code");
        preserviceVessel = preserviceVesselFacadeRemote.find(booking_code);
        preserviceVessel.getActivity();
        preserviceVessel.setOpenStack(null);
        if(preserviceVessel.getActivity()==1){
            this.setVisibleHiji(false);
            this.setVisibleDua(true);
        } else if(preserviceVessel.getActivity()==2){
            this.setVisibleHiji(true);
            this.setVisibleDua(false);
        } else {
            this.setVisibleHiji(false);
            this.setVisibleDua(false);
        }
    }

    public void handleCancelButton(ActionEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        preserviceVesselFacadeRemote.find(preserviceVessel.getBookingCode());
        //preserviceVessel.setStatus("Cancel");
        preserviceVesselFacadeRemote.remove(preserviceVessel);
        preserviceVesselss = lookupPreserviceVesselFacadeRemote().findPreserviceVessels();
        context.addMessage(null, new FacesMessage("Successfull", "Data has been canceled"));
        System.out.println("Cancelling : " + preserviceVessel.getBookingCode());
    }

    public void onChangeEvent(ValueChangeEvent event) {
        Short temp = (Short) event.getNewValue();
        short i=0;
        if(temp==1){
            this.setVisibleHiji(false);
            this.setVisibleDua(true);
            preserviceVessel.setEstLoad(i);
        } else if(temp==2){
            this.setVisibleHiji(true);
            this.setVisibleDua(false);
            preserviceVessel.setEstDischarge(i);
        } else {
            this.setVisibleHiji(false);
            this.setVisibleDua(false);
            preserviceVessel.setEstLoad(i);
            preserviceVessel.setEstDischarge(i);
        }
        System.out.println("Visiblehiji : " + isVisibleHiji());
        System.out.println("Visibledua : " + isVisibleDua());
    }

    public boolean isVisibleDua() {
        return visibleDua;
    }

    public void setVisibleDua(boolean visibleDua) {
        this.visibleDua = visibleDua;
    }

    public boolean isVisibleHiji() {
        return visibleHiji;
    }

    public void setVisibleHiji(boolean visibleHiji) {
        this.visibleHiji = visibleHiji;
    }

    public Integer getValueRadio() {
        return valueRadio;
    }

    public void setValueRadio(Integer valueRadio) {
        this.valueRadio = valueRadio;
    }

    public void handleSaveEdit(ActionEvent event) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy-hhmmss");
        Date tgl = Calendar.getInstance().getTime();
        String bookingId = "EBTOS" + dateFormat.format(tgl);

        FacesMessage msg = null;
        boolean loggedIn = false;

        if (preserviceVessel.getVoyIn() == null || preserviceVessel.getVoyOut() == null || preserviceVessel.getEta() == null || preserviceVessel.getEtd() == null) {
            loggedIn = false;
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Validation Error", "Data belum lengkap");
        } else if(preserviceVessel.getEta().after(preserviceVessel.getEtd()) || preserviceVessel.getEta().equals(preserviceVessel.getEtd())){
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Validation Error", "eta cannot same or after etd");
        } else {
            loggedIn = true;
            if (preserviceVessel.getBookingCode().equals("")) {
                preserviceVessel.setBookingCode(bookingId);
            }
            preserviceVessel.setStatus("Booking");
            preserviceVesselFacadeRemote.edit(preserviceVessel);
            preserviceVesselss = lookupPreserviceVesselFacadeRemote().findPreserviceVessels();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Successfull", "Data has been saved"));

            System.out.println("Success Add " + preserviceVessel);
            RequestContext.getCurrentInstance().addCallbackParam("loggedIn", loggedIn);
        }
    }

    public void handleSaveConfirm(ActionEvent event) {
        FacesContext cont = FacesContext.getCurrentInstance();
        boolean loggedIn = false;
        if ((preserviceVessel.getActivity() == 3 || preserviceVessel.getActivity() == 2) && preserviceVessel.getOpenStack() == null) {
            loggedIn = false;
            cont.addMessage(null, new FacesMessage("Aborted", "Open Stack Field is Required"));
            RequestContext.getCurrentInstance().addCallbackParam("loggedIn", loggedIn);
        } else {
            loggedIn = true;
            preserviceVessel.setStatus("Confirm");
            preserviceVesselFacadeRemote.edit(preserviceVessel);
            System.out.println("Success Confirm " + preserviceVessel);

            // copy to planning vessel
            SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMhhmmss");
            Date tgl = Calendar.getInstance().getTime();
            String ppkb = dateFormat.format(tgl);

            PlanningVessel planningVessel = new PlanningVessel();
            planningVessel.setPreserviceVessel(new PreserviceVessel());
            planningVessel.setMasterDock(new MasterDock());

            planningVessel.setNoPpkb(ppkb);
            planningVessel.setPreserviceVessel(preserviceVessel);
            planningVessel.setEta(preserviceVessel.getEta());
            planningVessel.setEtd(preserviceVessel.getEtd());
            planningVessel.setVesselCode(preserviceVessel.getMasterVessel().getVesselCode());
            planningVessel.setEstDischarge(preserviceVessel.getEstDischarge());
            planningVessel.setEstLoad(preserviceVessel.getEstLoad());
            planningVessel.setStatus(preserviceVessel.getStatus());
            planningVessel.setMasterDock(masterDockFacade.find("1"));
            planningVessel.setTipePelayaran("d");

            System.out.println("status: " + planningVessel.getStatus());
            planningVesselFacade.create(planningVessel);
            preserviceVesselss = lookupPreserviceVesselFacadeRemote().findPreserviceVessels();
            System.out.println("Success Copy " + planningVessel);

            planningVessel = new PlanningVessel();
            preserviceVessel = new PreserviceVessel();
            cont.addMessage(null, new FacesMessage("Successfull", "Data has been confirmed"));
            RequestContext.getCurrentInstance().addCallbackParam("loggedIn", loggedIn);
        }
    }

    public List<Object[]> getPreserviceVesselss() {
        return preserviceVesselss;
    }

    public List<Object[]> getMasterVessels() {
        return masterVessels;
    }

    public List<Object[]> getMasterCustomers() {
        return masterCustomers;
    }

    /**
     * @param preserviceVessel the preserviceVessel to set
     */
    public void setPreserviceVessel(PreserviceVessel preserviceVessel) {
        this.preserviceVessel = preserviceVessel;
    }

    /**
     * @return the preserviceVessel
     */
    public PreserviceVessel getPreserviceVessel() {
        return preserviceVessel;
    }

    /**
     * @return the masterPorts
     */
    public List<MasterPort> getMasterPorts() {
        return masterPorts;
    }

    /**
     * @param masterPorts the masterPorts to set
     */
    public void setMasterPorts(List<MasterPort> masterPorts) {
        this.masterPorts = masterPorts;
    }

    /**
     * @return the bookingActivity
     */
    public boolean isBookingActivity() {
        if(preserviceVessel.getActivity() == null)
            bookingActivity = false;
        else {
            if (preserviceVessel.getActivity() == 1) {
                bookingActivity = true;
            } else {
                bookingActivity = false;
            }
        }
        return bookingActivity;
    }

    /**
     * @param bookingActivity the bookingActivity to set
     */
    public void setBookingActivity(boolean bookingActivity) {
        this.bookingActivity = bookingActivity;
    }

    private PreserviceVesselFacadeRemote lookupPreserviceVesselFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (PreserviceVesselFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/PreserviceVesselFacade!com.pelindo.ebtos.ejb.facade.remote.PreserviceVesselFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private MasterPortFacadeRemote lookupMasterPortFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (MasterPortFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/MasterPortFacade!com.pelindo.ebtos.ejb.facade.remote.MasterPortFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private MasterVesselFacadeRemote lookupMasterVesselFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (MasterVesselFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/MasterVesselFacade!com.pelindo.ebtos.ejb.facade.remote.MasterVesselFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private MasterCustomerFacadeRemote lookupMasterCustomerFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (MasterCustomerFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/MasterCustomerFacade!com.pelindo.ebtos.ejb.facade.remote.MasterCustomerFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private PlanningVesselFacadeRemote lookupPlanningVesselFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (PlanningVesselFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/PlanningVesselFacade!com.pelindo.ebtos.ejb.facade.remote.PlanningVesselFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private MasterDockFacadeRemote lookupMasterDockFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (MasterDockFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/MasterDockFacade!com.pelindo.ebtos.ejb.facade.remote.MasterDockFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
