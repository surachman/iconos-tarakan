/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.bean;

import com.qtasnim.formula.FormulaManager;
import com.qtasnim.jsf.FacesHelper;
import com.pelindo.ebtos.bean.security.LoginSessionBean;
import com.pelindo.ebtos.ejb.facade.remote.ActiveDirectoryFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.BookingOnlineFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.FormulaManagerFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.IDGeneratorFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterCustomerFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterDockFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterPortFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterVesselFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningVesselFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningVesselHistoryFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PreserviceVesselFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PreserviceVesselHistoryFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceReceivingFacadeRemote;
import com.pelindo.ebtos.model.NamedObject;
import com.pelindo.ebtos.model.db.BookingOnline;
import com.pelindo.ebtos.model.db.PlanningVessel;
import com.pelindo.ebtos.model.db.PreserviceVessel;
import com.pelindo.ebtos.model.db.master.MasterCustomer;
import com.pelindo.ebtos.model.db.master.MasterDock;
import com.pelindo.ebtos.model.db.master.MasterPort;
import com.pelindo.ebtos.model.db.master.MasterVessel;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
@ManagedBean(name = "vesselListBean")
@ViewScoped
public class VesselListBean implements Serializable {
    private static final String LIST = "list";
    private static final String FORM = "form";
    private static final String DETAIL = "detail";

    @EJB
    private MasterVesselFacadeRemote masterVesselFacadeRemote;
    @EJB
    private FormulaManagerFacadeRemote formulaManagerFacade;
    @EJB
    private BookingOnlineFacadeRemote bookingOnlineFacadeRemote;
    @EJB
    private PlanningVesselFacadeRemote planningVesselFacadeRemote;
    @EJB
    private PreserviceVesselFacadeRemote preserviceVesselFacadeRemote;
    @EJB
    private MasterDockFacadeRemote dockFacadeRemote;
    @EJB
    private MasterCustomerFacadeRemote masterCustomerFacadeRemote;
    @EJB
    private MasterPortFacadeRemote masterPortFacadeRemote;
    @EJB
    private IDGeneratorFacadeRemote iDGeneratorFacadeRemote;
    @EJB
    private PlanningVesselHistoryFacadeRemote planningVesselHistoryFacadeRemote;
    @EJB
    private PreserviceVesselHistoryFacadeRemote preserviceVesselHistoryFacadeRemote;
    @EJB
    private ServiceReceivingFacadeRemote serviceReceivingFacadeRemote;
    @EJB
    private ActiveDirectoryFacadeRemote activeDirectoryFacadeRemote;

    private List<MasterDock> docks;
    private List<MasterPort> masterPorts;
    private List<Object[]> planningVessels;
    private List<Object[]> masterVessels;
    private List<Object[]> masterCustomers;
    private List<Object[]> canceledPreserviceVessels;
    private List<Object[]> valueHistories;
    private Object[] pv;
    private Boolean isEdit = false;
    private String pageState;
    private String changeVesselNotification;
    private String vesselStatus;
    private String username;
    private String password;
    private Integer receivingAllocationCount;
    private PlanningVessel planningVessel;
    private PlanningVessel oldPlanningVessel;
    
    private String selectedPort1, selectedPort2;

    /** Creates a new instance of VesselListBean */
    public VesselListBean() {
        vesselStatus = "Approved";
    }

    @PostConstruct
    private void construct() {
        pageState = VesselListBean.LIST;
        handleChangeVesselStatus();
//        masterPorts = masterPortFacadeRemote.findAll();
//        selectedPort = new Object();

    }

    public void handleSelectVesselCode(ActionEvent event) {
        String vesselCode = (String) event.getComponent().getAttributes().get("vessel_code");
        MasterVessel vessel = masterVesselFacadeRemote.find(vesselCode);
        planningVessel.getPreserviceVessel().setMasterVessel(vessel);
        planningVessel.setVesselCode(vesselCode);
    }
    
    public void handleShowCanceledPreserviceVessels(ActionEvent event){
        canceledPreserviceVessels = preserviceVesselFacadeRemote.findCanceledPreserviceVessels();
    }

    public void handleShowMasterCustomers(ActionEvent event) {
        masterCustomers = masterCustomerFacadeRemote.findMasterCustomers();
    }

    public void handleAddNewSchedule(ActionEvent event) {
        setToFormState();
        isEdit = false;
        //Default to Surabaya
        MasterPort defaultMasterPort = masterPortFacadeRemote.find("IDSUB");
        setSelectedPort1(defaultMasterPort.getName());
        setSelectedPort2(defaultMasterPort.getName());
        
        planningVessel = new PlanningVessel();
        planningVessel.setPreserviceVessel(new PreserviceVessel());
        planningVessel.getPreserviceVessel().setMasterVessel(new MasterVessel());
        planningVessel.getPreserviceVessel().setMasterCustomer(new MasterCustomer());
        planningVessel.getPreserviceVessel().setMasterPort(defaultMasterPort);
        planningVessel.getPreserviceVessel().setMasterPort1(defaultMasterPort);
        planningVessel.setMasterDock(new MasterDock());
    }

    public void handleEditSchedule(ActionEvent event) {
        String noPpkb = (String) event.getComponent().getAttributes().get("noPpkb");
        planningVessel = planningVesselFacadeRemote.find(noPpkb);
        receivingAllocationCount = planningVesselFacadeRemote.findReceivingAllocationCountByNoPpkb(noPpkb);

        if (planningVessel.getEstDischarge() == null) {
            planningVessel.setEstDischarge((short) 0);
        }

        if (planningVessel.getEstLoad() == null) {
            planningVessel.setEstLoad((short) 0);
        }

        isEdit = true;
        selectedPort1 = planningVessel.getPreserviceVessel().getMasterPort1().getName();
        selectedPort2 = planningVessel.getPreserviceVessel().getMasterPort().getName();

        if (planningVessel.getPreserviceVessel().isLoad() && receivingAllocationCount > 0) {
            if (planningVessel.getCloseRec() == null) {
                FormulaManager formula = formulaManagerFacade.setFormula("formula.closingtime");
                formula.setParameter("b", planningVessel.getEstDischarge());
                formula.setParameter("m", planningVessel.getEstLoad());

                Double d = formula.getResult();
                int jam = (int) Math.floor(d);
                double menit = (d - jam) * 60;
                int m = (int) Math.round(menit);

                Calendar c = Calendar.getInstance();
                c.setTime(planningVessel.getEta());
                c.add(Calendar.HOUR, jam);
                c.add(Calendar.MINUTE, m);
                planningVessel.setCloseRec(c.getTime());
            }
            
            if (planningVessel.getCloseReefer() == null) {
                planningVessel.setCloseReefer(planningVessel.getCloseRec());
            }

            if (planningVessel.getCloseEmpty() == null) {
                planningVessel.setCloseEmpty(planningVessel.getCloseRec());
            }

            if (planningVessel.getCloseDocMtyref() == null) {
                planningVessel.setCloseDocMtyref(planningVessel.getCloseRec());
            }

            if (planningVessel.getCloseDocRec() == null) {
                planningVessel.setCloseDocRec(planningVessel.getCloseRec());
            }

            if (planningVessel.getCloseUc()== null) {
                planningVessel.setCloseUc(planningVessel.getCloseRec());
            }

            if (planningVessel.getCloseDg()== null) {
                planningVessel.setCloseDg(planningVessel.getCloseRec());
            }

            oldPlanningVessel = new PlanningVessel();
            oldPlanningVessel.setCloseDocMtyref(planningVessel.getCloseDocMtyref());
            oldPlanningVessel.setCloseDocRec(planningVessel.getCloseDocRec());
            oldPlanningVessel.setCloseEmpty(planningVessel.getCloseEmpty());
            oldPlanningVessel.setCloseRec(planningVessel.getCloseRec());
            oldPlanningVessel.setCloseReefer(planningVessel.getCloseReefer());
            oldPlanningVessel.setCloseUc(planningVessel.getCloseUc());
            oldPlanningVessel.setCloseDg(planningVessel.getCloseDg());
        }
        
        pageState = VesselListBean.FORM;
    }

    public void handleCancel(ActionEvent event) {
        String noPpkb = (String) event.getComponent().getAttributes().get("noPpkb");
        planningVessel = planningVesselFacadeRemote.find(noPpkb);
    }

    public void handleChangeShippingType() {
        System.out.println(planningVessel.getTipePelayaran());
        if (planningVessel.getTipePelayaran() != null && planningVessel.getTipePelayaran().equals("i")) {
            planningVessel.setLoadServiceType("CTC");
            planningVessel.setDischargeServiceType("CTC");
        }
    }

    public void handleCancelVesselSchedule(ActionEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        RequestContext requestContext = RequestContext.getCurrentInstance();
        changeVesselNotification = null;
        
        try {
            Long receivedContainers = serviceReceivingFacadeRemote.countByNoPpkb(planningVessel.getNoPpkb());
            
            if (receivedContainers > 0) {
                changeVesselNotification = String.format(FacesHelper.getLocaleMessage("application.notification.change_vessel_after_cancel", context), receivedContainers);
                requestContext.addCallbackParam("showChangeVesselNotification", true);
            }

            planningVesselFacadeRemote.handleCancelVesselSchedule(planningVessel);
            FacesHelper.addFacesMessage(context, FacesMessage.SEVERITY_INFO, "Info", "application.cancel.succeeded");
        } catch (RuntimeException re) {
            FacesHelper.addFacesMessage(context, FacesMessage.SEVERITY_INFO, "Info", "application.cancel.failed");
        }

        handleChangeVesselStatus();
    }

    public void handleSaveNewSchedule(ActionEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            if (planningVessel.getFrMeter() < planningVessel.getMasterDock().getFrMeter() || planningVessel.getToMeter() < (planningVessel.getMasterDock().getFrMeter() + planningVessel.getPreserviceVessel().getMasterVessel().getLoa())) {
                context.addMessage(null, new FacesMessage("Error", "Fr Meter and To Meter Minimum Value for vessel <b>" + planningVessel.getVesselCode() + "</b> is " + planningVessel.getMasterDock().getFrMeter() + " and " + (planningVessel.getMasterDock().getFrMeter() + planningVessel.getPreserviceVessel().getMasterVessel().getLoa())));
                pageState = VesselListBean.FORM;
            } else if (planningVessel.getFrMeter() > (planningVessel.getMasterDock().getToMeter() - planningVessel.getPreserviceVessel().getMasterVessel().getLoa()) || planningVessel.getToMeter() > planningVessel.getMasterDock().getToMeter()) {
                context.addMessage(null, new FacesMessage("Error", "Fr Meter and To Meter Maximum Value for vessel <b>" + planningVessel.getVesselCode() + "</b> is " + (planningVessel.getMasterDock().getToMeter() - planningVessel.getPreserviceVessel().getMasterVessel().getLoa()) + " and " + planningVessel.getMasterDock().getToMeter()));
                pageState = VesselListBean.FORM;
            } else if ((planningVessel.getEstDischarge() != null && planningVessel.getEstDischarge() >= planningVessel.getPreserviceVessel().getMasterVessel().getContCapacity()) || (planningVessel.getEstLoad() != null && planningVessel.getEstLoad() >= planningVessel.getPreserviceVessel().getMasterVessel().getContCapacity())) {
                if (planningVessel.getEstDischarge() != null &&planningVessel.getEstDischarge() >= planningVessel.getPreserviceVessel().getMasterVessel().getContCapacity()) {
                    FacesHelper.addFacesTextMessage(context, FacesMessage.SEVERITY_ERROR, "Error", String.format(FacesHelper.getLocaleMessage("application.validation.discharge_teus", context), " (max " + planningVessel.getPreserviceVessel().getMasterVessel().getContCapacity() + " teus)"), null);
                }
                if (planningVessel.getEstLoad() != null && planningVessel.getEstLoad() >= planningVessel.getPreserviceVessel().getMasterVessel().getContCapacity()) {
                    FacesHelper.addFacesTextMessage(context, FacesMessage.SEVERITY_ERROR, "Error", String.format(FacesHelper.getLocaleMessage("application.validation.load_teus", context), " (max " + planningVessel.getPreserviceVessel().getMasterVessel().getContCapacity() + " teus)"), null);
                }
                pageState = VesselListBean.FORM;
            } else if (planningVessel.getEta().after(planningVessel.getEtd()) || planningVessel.getEta().equals(planningVessel.getEtd())) {
                FacesHelper.addFacesMessage(context, FacesMessage.SEVERITY_WARN, "Warning", "application.validation.eta");
                pageState = VesselListBean.FORM;
            } else {
                SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy-HHmmss");
                String bookingCode = "EBTOS" + dateFormat.format(new Date());

                PreserviceVessel preserviceVessel = planningVessel.getPreserviceVessel();
                preserviceVessel.setBookingCode(bookingCode);
                preserviceVessel.setClosingTime(planningVessel.getCloseRec());
                if (planningVessel.getEstDischarge() != null)
                    preserviceVessel.setEstDischarge(planningVessel.getEstDischarge());
                if (planningVessel.getEstLoad() != null)
                    preserviceVessel.setEstLoad(planningVessel.getEstLoad());
                preserviceVessel.setEta(planningVessel.getEta());
                preserviceVessel.setEtd(planningVessel.getEtd());
                preserviceVessel.setStatus("Confirm");

                planningVessel.setNoPpkb(iDGeneratorFacadeRemote.generatePpkbID());
                planningVessel.setCheckBaplie("TRUE");
                planningVessel.setBaplieDischarge("FALSE");
                planningVessel.setStatus("Confirm");

                if (planningVessel.getEstDischarge() == null) {
                    planningVessel.setEstDischarge((short) 0);
                }

                if (planningVessel.getEstLoad() == null) {
                    planningVessel.setEstLoad((short) 0);
                }

                MasterPort port1 = masterPortFacadeRemote.findMasterPortByName(selectedPort1);
                planningVessel.getPreserviceVessel().setMasterPort1(port1);
                MasterPort port2 = masterPortFacadeRemote.findMasterPortByName(selectedPort2);
                planningVessel.getPreserviceVessel().setMasterPort(port2);

                preserviceVesselFacadeRemote.create(preserviceVessel);
                planningVessel.setPreserviceVessel(preserviceVessel);
                planningVesselFacadeRemote.create(planningVessel);
                masterVesselFacadeRemote.edit(preserviceVessel.getMasterVessel());

                FacesHelper.addFacesMessage(context, FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
                pageState = VesselListBean.LIST;
                handleChangeVesselStatus();
            }
        } catch (RuntimeException re) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", re);
            FacesHelper.addFacesMessage(context, FacesMessage.SEVERITY_INFO, "Info", "application.save.failed");
        }
    }

    public void handleSaveEdit(ActionEvent event) throws ParseException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        RequestContext requestContext = RequestContext.getCurrentInstance();
        LoginSessionBean loginSessionBean = LoginSessionBean.getCurrentInstance();
        
        if (planningVessel.getFrMeter() < planningVessel.getMasterDock().getFrMeter() || planningVessel.getToMeter() < (planningVessel.getMasterDock().getFrMeter() + planningVessel.getPreserviceVessel().getMasterVessel().getLoa())) {
            planningVessel.getMasterDock();
            facesContext.addMessage(null, new FacesMessage("Error", "Fr Meter and To Meter Minimum Value for vessel <b>" + planningVessel.getVesselCode() + "</b> is " + planningVessel.getMasterDock().getFrMeter() + " and " + (planningVessel.getMasterDock().getFrMeter() + planningVessel.getPreserviceVessel().getMasterVessel().getLoa())));
            pageState = VesselListBean.FORM;
        } else if (planningVessel.getFrMeter() > (planningVessel.getMasterDock().getToMeter() - planningVessel.getPreserviceVessel().getMasterVessel().getLoa()) || planningVessel.getToMeter() > planningVessel.getMasterDock().getToMeter()) {
            planningVessel.getMasterDock();
            facesContext.addMessage(null, new FacesMessage("Error", "Fr Meter and To Meter Maximum Value for vessel <b>" + planningVessel.getVesselCode() + "</b> is " + (planningVessel.getMasterDock().getToMeter() - planningVessel.getPreserviceVessel().getMasterVessel().getLoa()) + " and " + planningVessel.getMasterDock().getToMeter()));
            pageState = VesselListBean.FORM;
        } else if ((planningVessel.getEstDischarge() != null && planningVessel.getEstDischarge() >= planningVessel.getPreserviceVessel().getMasterVessel().getContCapacity()) || (planningVessel.getEstLoad() != null && planningVessel.getEstLoad() >= planningVessel.getPreserviceVessel().getMasterVessel().getContCapacity())) {
            if (planningVessel.getEstDischarge() != null && planningVessel.getEstDischarge() >= planningVessel.getPreserviceVessel().getMasterVessel().getContCapacity()) {
                FacesHelper.addFacesTextMessage(facesContext, FacesMessage.SEVERITY_ERROR, "Error", String.format(FacesHelper.getLocaleMessage("application.validation.discharge_teus", facesContext), " (max " + planningVessel.getPreserviceVessel().getMasterVessel().getContCapacity() + " teus)"), null);
            }
            if (planningVessel.getEstLoad() != null && planningVessel.getEstLoad() >= planningVessel.getPreserviceVessel().getMasterVessel().getContCapacity()) {
                FacesHelper.addFacesTextMessage(facesContext, FacesMessage.SEVERITY_ERROR, "Error", String.format(FacesHelper.getLocaleMessage("application.validation.load_teus", facesContext), " (max " + planningVessel.getPreserviceVessel().getMasterVessel().getContCapacity() + " teus)"), null);
            }
            pageState = VesselListBean.FORM;
        } else if (planningVessel.getEta().after(planningVessel.getEtd()) || planningVessel.getEta().equals(planningVessel.getEtd())) {
            planningVessel.getMasterDock();
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_WARN, "Warning", "application.validation.eta");
            pageState = VesselListBean.FORM;
        } else {
            Boolean validateClosingTime = (Boolean) event.getComponent().getAttributes().get("validateClosingTime");
            validateClosingTime = validateClosingTime != null && validateClosingTime;

            if ((planningVessel.getPreserviceVessel().getActivity() == 1 || planningVessel.getPreserviceVessel().getActivity() == 3) && planningVessel.getStatus().equals("Servicing") && validateClosingTime && loginSessionBean != null && !loginSessionBean.isSupervisor() && oldPlanningVessel.isClosingTimeChanged(planningVessel)) {
                requestContext.addCallbackParam("authenticationRequired", true);
                return;
            }

            requestContext.addCallbackParam("authenticationRequired", false);
            BookingOnline bookingOnline = bookingOnlineFacadeRemote.findByBooking(planningVessel.getPreserviceVessel().getBookingCode());

            Boolean isApprove = (Boolean) event.getComponent().getAttributes().get("approve");
            isApprove = isApprove != null && isApprove;
            if (!planningVessel.getStatus().equals("Servicing") && isApprove) {
                if (bookingOnline != null) {
                    bookingOnline.setStatus("Approved");
                    bookingOnlineFacadeRemote.edit(bookingOnline);
                }

                planningVessel.setStatus("Approved");
            }

            if (planningVessel.getEstDischarge() == null) {
                planningVessel.setEstDischarge((short) 0);
            }

            if (planningVessel.getEstLoad() == null) {
                planningVessel.setEstLoad((short) 0);
            }
                MasterPort port1 = masterPortFacadeRemote.findMasterPortByName(selectedPort1);
            planningVessel.getPreserviceVessel().setMasterPort1(port1);
                MasterPort port2 = masterPortFacadeRemote.findMasterPortByName(selectedPort2);
            planningVessel.getPreserviceVessel().setMasterPort(port2);

            planningVesselFacadeRemote.edit(planningVessel);

            if (planningVessel.getPreserviceVessel() != null) {
                PreserviceVessel preserviceVessel = planningVessel.getPreserviceVessel();
                preserviceVessel.setClosingTime(planningVessel.getOpenStack());
                if (planningVessel.getEstDischarge() != null)
                    preserviceVessel.setEstDischarge(planningVessel.getEstDischarge());
                if (planningVessel.getEstLoad() != null)
                    preserviceVessel.setEstLoad(planningVessel.getEstLoad());
                preserviceVessel.setEta(planningVessel.getEta());
                preserviceVessel.setEtd(planningVessel.getEtd());
                preserviceVessel.setOpenStack(planningVessel.getOpenStack());
                
                preserviceVesselFacadeRemote.edit(preserviceVessel);
                masterVesselFacadeRemote.edit(preserviceVessel.getMasterVessel());
            }

            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
            pageState = VesselListBean.LIST;
            handleChangeVesselStatus();
        }
    }

    public void handleAuthentication(ActionEvent event) throws ParseException {
        NamedObject user = activeDirectoryFacadeRemote.findUserByUid(username);
        FacesContext facesContext = FacesContext.getCurrentInstance();
        RequestContext requestContext = RequestContext.getCurrentInstance();
        boolean isLoggedIn = activeDirectoryFacadeRemote.authenticate(user, password);

        if (isLoggedIn && user.getGroups().contains("Admin")) {
            handleSaveEdit(event);
            return;
        }

        requestContext.addCallbackParam("authenticationRequired", true);
        FacesHelper.addErrorFacesMessage(facesContext, "message.access.login_error_credential");
    }

    public void handleEnableOpenStack(ActionEvent event) {
        planningVessel.setEnableOpenStack("TRUE");
    }

    public void handleDisableOpenStack(ActionEvent event) {
        planningVessel.setEnableOpenStack("FALSE");
    }

    public void handleShowDocks(ActionEvent event) {
        docks = dockFacadeRemote.findAll();
    }

    public void handleShowMasterVessels(ActionEvent event) {
        masterVessels = masterVesselFacadeRemote.findMasterVessels();
    }

    public void handlePreview(ActionEvent event) {
        String noPpkb = (String) event.getComponent().getAttributes().get("noPpkb");
        pv = planningVesselFacadeRemote.findPlanningVesselDetail(noPpkb);
        pageState = VesselListBean.DETAIL;
    }

    public void handleAutoFillClosingTime(ActionEvent event) {
        FormulaManager formula = formulaManagerFacade.setFormula("formula.closingtime");
        
        if (planningVessel.getEstDischarge() == null)
            formula.setParameter("b", 0);
        else
            formula.setParameter("b", planningVessel.getEstDischarge());

        formula.setParameter("m", planningVessel.getEstLoad());

        Double d = formula.getResult();
        int jam = (int) Math.floor(d);
        double menit = (d - jam) * 60;
        int m = (int) Math.round(menit);

        Calendar c = Calendar.getInstance();
        c.setTime(planningVessel.getEta());
        c.add(Calendar.HOUR, jam);
        c.add(Calendar.MINUTE, m);

        planningVessel.setCloseRec(c.getTime());
        planningVessel.setCloseReefer(planningVessel.getCloseRec());
        planningVessel.setCloseEmpty(planningVessel.getCloseRec());
        planningVessel.setCloseDocMtyref(planningVessel.getCloseRec());
        planningVessel.setCloseDocRec(planningVessel.getCloseRec());
        planningVessel.setCloseUc(planningVessel.getCloseRec());
        planningVessel.setCloseDg(planningVessel.getCloseRec());
    }

    public void handleSelectBerth(ActionEvent event) {
        String dockCode = (String) event.getComponent().getAttributes().get("dockCode");
        planningVessel.setMasterDock(dockFacadeRemote.find(dockCode));
        pageState = VesselListBean.FORM;
    }
    
    public void handleSelectMasterCustomer(ActionEvent event) {
        String custCode = (String) event.getComponent().getAttributes().get("cust_code");
        planningVessel.getPreserviceVessel().setMasterCustomer(masterCustomerFacadeRemote.find(custCode));
    }

    public void handleViewPlanningVesselHistories(ActionEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        String fieldName = context.getExternalContext().getRequestParameterMap().get("fieldName");
        String noPpkb = context.getExternalContext().getRequestParameterMap().get("param");
        valueHistories = planningVesselHistoryFacadeRemote.getHistoriesByField(noPpkb, fieldName);
    }

    public void handleViewPreserviceVesselHistories(ActionEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        String fieldName = context.getExternalContext().getRequestParameterMap().get("fieldName");
        String bookingCode = context.getExternalContext().getRequestParameterMap().get("param");
        valueHistories = preserviceVesselHistoryFacadeRemote.getHistoriesByField(bookingCode, fieldName);
    }

    public void handleChangeVesselStatus() {
        if (vesselStatus.equals("Approved")) {
            planningVessels = planningVesselFacadeRemote.findPlanningVesselList();
        } else if (vesselStatus.equals("Servicing")) {
            planningVessels = planningVesselFacadeRemote.findPlanningVesselsServicing();
        }
    }

    public String getSelectedPort1() {
        return selectedPort1;
    }

    public void setSelectedPort1(String selectedPort1) {
        this.selectedPort1 = selectedPort1;
    }

    public String getSelectedPort2() {
        return selectedPort2;
    }

    public void setSelectedPort2(String selectedPort2) {
        this.selectedPort2 = selectedPort2;
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

    public List<Object[]> getCanceledPreserviceVessels() {
        return canceledPreserviceVessels;
    }

    public List<Object[]> getMasterCustomers() {
        return masterCustomers;
    }

    public List<MasterPort> getMasterPorts() {
        return masterPorts;
    }

    /**
     * @return the planningVessels
     */
    public List<Object[]> getPlanningVessels() {
        return planningVessels;
    }

    /**
     * @param planningVessels the planningVessels to set
     */
    public void setPlanningVessels(List<Object[]> planningVessels) {
        this.planningVessels = planningVessels;
    }

    /**
     * @return the pageState
     */
    public String getPageState() {
        return pageState;
    }

    /**
     * @param pageState the pageState to set
     */
    public void setPageState(String pageState) {
        this.pageState = pageState;
    }

    public boolean isListState() {
        return pageState.equals(VesselListBean.LIST);
    }

    public boolean isFormState() {
        return pageState.equals(VesselListBean.FORM);
    }

    public boolean isDetailState() {
        return pageState.equals(VesselListBean.DETAIL);
    }

    public void setToListState() {
        planningVessel = null;
        pageState = VesselListBean.LIST;
    }

    public void setToFormState() {
        pageState = VesselListBean.FORM;
    }

    public void setToDetailState() {
        pageState = VesselListBean.DETAIL;
    }

    /**
     * @return the pv
     */
    public Object[] getPv() {
        return pv;
    }

    /**
     * @param pv the pv to set
     */
    public void setPv(Object[] pv) {
        this.pv = pv;
    }

    /**
     * @return the planningVessel
     */
    public PlanningVessel getPlanningVessel() {
        return planningVessel;
    }

    /**
     * @param planningVessel the planningVessel to set
     */
    public void setPlanningVessel(PlanningVessel planningVessel) {
        this.planningVessel = planningVessel;
    }

    /**
     * @return the docks
     */
    public List<MasterDock> getDocks() {
        return docks;
    }

    /**
     * @param docks the docks to set
     */
    public void setDocks(List<MasterDock> docks) {
        this.docks = docks;
    }

    public Boolean getIsEdit() {
        return isEdit;
    }

    public List<Object[]> getValueHistories() {
        return valueHistories;
    }

    public String getChangeVesselNotification() {
        return changeVesselNotification;
    }

    public String getVesselStatus() {
        return vesselStatus;
    }

    public void setVesselStatus(String vesselStatus) {
        this.vesselStatus = vesselStatus;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getReceivingAllocationCount() {
        if (planningVessel.getNoPpkb() == null) {
            return 0;
        }

        return receivingAllocationCount;
    }
}
