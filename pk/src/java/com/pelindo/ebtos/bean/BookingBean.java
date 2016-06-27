/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.bean;

import com.qtasnim.jsf.FacesHelper;
import com.pelindo.ebtos.ejb.facade.remote.IDGeneratorFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterCustomerFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterDockFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterPortFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterVesselFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningVesselFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PreserviceVesselFacadeRemote;
import com.pelindo.ebtos.model.db.PlanningVessel;
import com.pelindo.ebtos.model.db.PreserviceVessel;
import com.pelindo.ebtos.model.db.master.MasterCustomer;
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
import javax.faces.event.ValueChangeEvent;
import org.primefaces.context.RequestContext;

/**
 *
 * @author dycoder
 */
@ManagedBean(name="bookingBean")
@ViewScoped
public class BookingBean implements Serializable{
    @EJB
    private IDGeneratorFacadeRemote iDGeneratorFacade;
    @EJB
    private MasterDockFacadeRemote masterDockFacade;
    @EJB
    private PlanningVesselFacadeRemote planningVesselFacade;
    @EJB
    private PreserviceVesselFacadeRemote preserviceVesselFacadeRemote;
    @EJB
    private MasterPortFacadeRemote masterPortFacadeRemote;
    @EJB
    private MasterCustomerFacadeRemote masterCustomerFacadeRemote;
    @EJB
    private MasterVesselFacadeRemote masterVesselFacadeRemote;

    private List<Object[]> preserviceVessels;
    private List<Object[]> canceledPreserviceVessels;
    private List<Object[]> masterVessels;
    private List<Object[]> masterCustomers ;
    private List<MasterPort> masterPorts ;
    private PreserviceVessel preserviceVessel;
    private boolean visibleHiji;
    private boolean visibleDua;
    private boolean bookingActivity;
    private boolean cekAgent;
    private Integer valueRadio = 0;

    /** Creates a new instance of BookingBean */
    public BookingBean() {}

    @PostConstruct
    public void construct(){
        preserviceVessel = new PreserviceVessel();
        preserviceVessel.setMasterCustomer(new MasterCustomer());
        preserviceVessel.setMasterVessel(new MasterVessel());
        preserviceVessel.setMasterPort(new MasterPort());
        preserviceVessel.setMasterPort1(new MasterPort());
        visibleHiji=false;
        visibleDua=false;

        preserviceVessels = preserviceVesselFacadeRemote.findPreserviceVessels();
        masterPorts = masterPortFacadeRemote.findAll();
    }

    // event tombol add
    public void handleAdd(ActionEvent event){
        construct();
        preserviceVessel.setActivity((short) 3);
        cekAgent = Boolean.FALSE;
    }

    public void handleSelectVesselCode(ActionEvent event) {
        String vesselCode = (String) event.getComponent().getAttributes().get("vessel_code");
        MasterVessel vessel = masterVesselFacadeRemote.find(vesselCode);
        MasterCustomer customer = masterCustomerFacadeRemote.find(vessel.getMasterCustomer().getCustCode());
        
        preserviceVessel.setMasterVessel(vessel);
        preserviceVessel.setMasterCustomer(customer);
        cekAgent = Boolean.TRUE;
    }

    public void handleSelectVesselCustCode(ActionEvent event) {
        String custCode = (String) event.getComponent().getAttributes().get("cust_code");
        preserviceVessel.setMasterCustomer(masterCustomerFacadeRemote.find(custCode));
    }

    public void handleShowMasterVessels(ActionEvent event) {
        if (preserviceVessel.getMasterCustomer().getCustCode() != null) {
            masterVessels = masterVesselFacadeRemote.findMasterVesselsByCustCode(preserviceVessel.getMasterCustomer().getCustCode());
        } else {
            masterVessels = masterVesselFacadeRemote.findMasterVessels();
        }
    }

    public void handleShowCanceledPreserviceVessels(ActionEvent event){
        canceledPreserviceVessels = preserviceVesselFacadeRemote.findCanceledPreserviceVessels();
    }

    public void handleShowMasterCustomers(ActionEvent event) {
        masterCustomers = masterCustomerFacadeRemote.findMasterCustomers();
    }

    public void handleEditDeleteButton(ActionEvent event) {
        String bookingCode = (String) event.getComponent().getAttributes().get("booking_code");
        preserviceVessel = preserviceVesselFacadeRemote.find(bookingCode);
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
        preserviceVessel.setStatus("Canceled");
        
        if (preserviceVessel.getPlanningVessel() != null) {
            preserviceVessel.getPlanningVessel().setStatus("Canceled");
            PlanningVessel planningVessel = preserviceVessel.getPlanningVessel();
            planningVesselFacade.edit(planningVessel);
        }

        preserviceVesselFacadeRemote.edit(preserviceVessel);
        preserviceVessels = preserviceVesselFacadeRemote.findPreserviceVessels();
        FacesHelper.addFacesMessage(context, FacesMessage.SEVERITY_INFO, "Info", "application.cancel.succeeded");
    }

    public void handleRefreshPreserviceVessels(ActionEvent event) {
        preserviceVessels = preserviceVesselFacadeRemote.findPreserviceVessels();
    }

    public void onChangeEvent(ValueChangeEvent event) {
        Short temp = (Short) event.getNewValue();
        short i = 0;

        if(temp == 1){
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
        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy-HHmmss");
        Date tgl = Calendar.getInstance().getTime();
        String bookingId = "EBTOS" + dateFormat.format(tgl);

        FacesContext context = FacesContext.getCurrentInstance();
        boolean isValid = false;

        if (preserviceVessel.getVoyIn() == null || preserviceVessel.getVoyOut() == null || preserviceVessel.getEta() == null || preserviceVessel.getEtd() == null || preserviceVessel.getMasterCustomer().getCustCode() == null) {
            isValid = false;
            FacesHelper.addFacesMessage(context, FacesMessage.SEVERITY_WARN, "Warning", "application.transaction.notcompleted");
        } else if(preserviceVessel.getEta().after(preserviceVessel.getEtd()) || preserviceVessel.getEta().equals(preserviceVessel.getEtd())){
            FacesHelper.addFacesMessage(context, FacesMessage.SEVERITY_WARN, "Warning", "application.validation.eta");
        } else {
            isValid = true;

            if (preserviceVessel.getBookingCode() == null) {
                preserviceVessel.setBookingCode(bookingId);
            }

            preserviceVessel.setStatus("Booking");
            preserviceVesselFacadeRemote.edit(preserviceVessel);
            preserviceVessels = preserviceVesselFacadeRemote.findPreserviceVessels();
            FacesHelper.addFacesMessage(context, FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");

            RequestContext.getCurrentInstance().addCallbackParam("isValid", isValid);
        }
    }

    public void handleSaveConfirm(ActionEvent event) {
        FacesContext cont = FacesContext.getCurrentInstance();
        boolean isValid = false;
        
        if (preserviceVessel.getActivity() == 3 || preserviceVessel.getActivity() == 2) {
            if(preserviceVessel.getOpenStack() == null){
                isValid = false;
                FacesHelper.addFacesMessage(cont, FacesMessage.SEVERITY_WARN, "Warning", "application.validation.openstack");
            }else if(preserviceVessel.getOpenStack().after(preserviceVessel.getEta()) || preserviceVessel.getOpenStack().after(preserviceVessel.getEtd())){
                isValid = false;
                FacesHelper.addFacesMessage(cont, FacesMessage.SEVERITY_WARN, "Warning", "application.validation.time_openstack");
            } else {
                isValid = true;
                saveConfirm();
            }
        } else {
            isValid = true;
            saveConfirm();
        }
        RequestContext.getCurrentInstance().addCallbackParam("isValid", isValid);
    }

    public void saveConfirm() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        try {
            PlanningVessel planningVessel = new PlanningVessel();

            if (preserviceVessel.getPlanningVessel() != null) {
                planningVessel = preserviceVessel.getPlanningVessel();
            } else {
                planningVessel.setNoPpkb(iDGeneratorFacade.generatePpkbID());
            }

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
            planningVessel.setBaplieDischarge("TRUE");
            planningVessel.setStatus("Confirm");
            planningVesselFacade.edit(planningVessel);

            preserviceVessel.setStatus("Confirm");
            preserviceVesselFacadeRemote.edit(preserviceVessel);

            preserviceVessels = preserviceVesselFacadeRemote.findPreserviceVessels();
            preserviceVessel = new PreserviceVessel();
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_INFO, "Info", "application.confirm.succeeded");
        } catch (RuntimeException e) {
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_ERROR, "Error", "application.confirm.failed");
        }
    }

    public List<Object[]> getPreserviceVessels() {
        return preserviceVessels;
    }

    public List<Object[]> getMasterVessels() {
        return masterVessels;
    }

    public List<Object[]> getMasterCustomers() {
        return masterCustomers;
    }
    
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
        if (preserviceVessel.getActivity() == null){
            bookingActivity = false;
        } else {
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
    /**
     * @return the cekAgent
     */
    public boolean isCekAgent() {
        return cekAgent;
    }

    /**
     * @param cekAgent the cekAgent to set
     */
    public void setCekAgent(boolean cekAgent) {
        this.cekAgent = cekAgent;
    }

    public List<Object[]> getCanceledPreserviceVessels() {
        return canceledPreserviceVessels;
    }
}
