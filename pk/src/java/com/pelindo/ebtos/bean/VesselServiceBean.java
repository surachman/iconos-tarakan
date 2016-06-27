/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.bean;

import com.qtasnim.formula.FormulaManager;
import com.qtasnim.jsf.FacesHelper;
import com.pelindo.ebtos.ejb.facade.remote.FormulaManagerFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterDockFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningVesselFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceVesselFacadeRemote;
import com.pelindo.ebtos.model.db.PlanningVessel;
import com.pelindo.ebtos.model.db.PreserviceVessel;
import com.pelindo.ebtos.model.db.ServiceVessel;
import com.pelindo.ebtos.model.db.master.MasterDock;
import com.pelindo.ebtos.model.db.master.MasterPort;
import com.pelindo.ebtos.model.db.master.MasterVessel;
import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.primefaces.context.RequestContext;

/**
 *
 * @author dycoder
 */
@ManagedBean(name="vesselServiceBean")
@ViewScoped
public class VesselServiceBean implements Serializable{
    FormulaManagerFacadeRemote formulaManagerFacade = lookupFormulaManagerFacadeRemote();
    MasterDockFacadeRemote masterDockFacade = lookupMasterDockFacadeRemote();
    PlanningVesselFacadeRemote planningVesselFacade = lookupPlanningVesselFacadeRemote();
    ServiceVesselFacadeRemote serviceVesselFacade = lookupServiceVesselFacadeRemote();

    private PlanningVessel planningVessel;
    private ServiceVessel serviceVessel;
    private List<MasterDock> docks = masterDockFacade.findAll();
    private MasterDock dock;
    private Object[] sv;

    private String pageState;
    private static String LIST = "list";
    private static String DETAIL = "detail";

    private List<Object[]> serviceVessels = lookupServiceVesselFacadeRemote().findServiceVesselServicing();

    /** Creates a new instance of VesselServiceBean */
    public VesselServiceBean() {
        planningVessel = new PlanningVessel();
        planningVessel.setPreserviceVessel(new PreserviceVessel());
        planningVessel.getPreserviceVessel().setMasterVessel(new MasterVessel());
        planningVessel.getPreserviceVessel().setMasterPort(new MasterPort());
        planningVessel.getPreserviceVessel().setMasterPort1(new MasterPort());
        planningVessel.setMasterDock(new MasterDock());
        serviceVessel = new ServiceVessel();
        dock = new MasterDock();
        pageState = VesselServiceBean.LIST;
    }

    public void handleSelectTable(ActionEvent event){
        String no_ppkb = (String) event.getComponent().getAttributes().get("noPpkb");
        serviceVessel = lookupServiceVesselFacadeRemote().find(no_ppkb);
        planningVessel = lookupPlanningVesselFacadeRemote().find(no_ppkb);
        if (serviceVessel.getDockCode() == null)
            dock = planningVessel.getMasterDock();
        else
            dock = masterDockFacade.find(serviceVessel.getDockCode());
        if (serviceVessel.getFrMeter() == null)
            serviceVessel.setFrMeter(planningVessel.getFrMeter());
        if (serviceVessel.getToMeter() == null)
            serviceVessel.setToMeter(planningVessel.getToMeter());
        if (serviceVessel.getArrivalTime() == null)
            serviceVessel.setArrivalTime(planningVessel.getEta());
        if (serviceVessel.getBerhtingTime() == null)
            serviceVessel.setBerhtingTime(planningVessel.getEbt());
        if (serviceVessel.getDepartureTime() == null)
            serviceVessel.setDepartureTime(planningVessel.getEtd());
        if (serviceVessel.getStartWorkTime() == null)
            serviceVessel.setStartWorkTime(planningVessel.getEstStartWork());
        if (serviceVessel.getEndWorkTime() == null)
            serviceVessel.setEndWorkTime(planningVessel.getEstEndWork());
    }

    public void handlePreview (ActionEvent event){
        String no_ppkb = (String) event.getComponent().getAttributes().get("noPpkb");
        sv = serviceVesselFacade.findServiceVesselDetail(no_ppkb);
        pageState = VesselServiceBean.DETAIL;
    }

    public void handleSave(ActionEvent event){
        boolean loggedIn = false;
        FacesContext context = FacesContext.getCurrentInstance();
        dock = masterDockFacade.find(dock.getDockCode());
        if (serviceVessel.getFrMeter() == null || serviceVessel.getToMeter() == null
                || serviceVessel.getDischarge() == null || serviceVessel.getLoad() == null) {
            FacesHelper.addFacesMessage(context, FacesMessage.SEVERITY_WARN, "Warning", "application.save.failed");
        } else {
            if (serviceVessel.getFrMeter() < dock.getFrMeter() || serviceVessel.getToMeter() < (dock.getFrMeter() + planningVessel.getPreserviceVessel().getMasterVessel().getLoa())) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Fr Meter and To Meter Minimum Value for vessel <b>" + planningVessel.getVesselCode() + "</b> at <b>" + dock.getName() + "</b> is " + dock.getFrMeter() + " and " + (dock.getFrMeter() + planningVessel.getPreserviceVessel().getMasterVessel().getLoa())));
            } else if (serviceVessel.getFrMeter() > (dock.getToMeter() - planningVessel.getPreserviceVessel().getMasterVessel().getLoa()) || serviceVessel.getToMeter() > dock.getToMeter()) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Fr Meter and To Meter Maximum Value for vessel <b>" + planningVessel.getVesselCode() + "</b> at <b>" + dock.getName() + "</b> is " + (dock.getToMeter() - planningVessel.getPreserviceVessel().getMasterVessel().getLoa()) + " and " + dock.getToMeter()));
            } else {
                List<Object[]> services = conflictVesselService(serviceVessel.getFrMeter(), serviceVessel.getToMeter(), dock.getDockCode(), serviceVessel.getNoPpkb());
                if (!services.isEmpty() && false){
                    String message = "Allocation for <b>" + serviceVessel.getNoPpkb() + "</b> conflict with: <br/><ul>";
                    for (Object[] service: services){
                        message += "<li><b>" + service[0] + "</b>, from <b>" + service[1] + "</b> to <b>" + service[2] + "</b></li>";
                    }
                    message += "</ul>";
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", message));
                } else {
                    loggedIn = true;

                    // ambil rumus menghitung closing time
                    FormulaManager formula = formulaManagerFacade.setFormula("formula.closingtime");
                    formula.setParameter("b", serviceVessel.getDischarge());
                    formula.setParameter("m", serviceVessel.getLoad());

                    Double d = formula.getResult();
                    int jam = (int) Math.floor(d);
                    double menit = (formula.getResult() - jam) * 60;
                    int m = (int) Math.round(menit);

                    Calendar c = Calendar.getInstance();
                    c.setTime(serviceVessel.getArrivalTime());
                    c.add(Calendar.HOUR, jam);
                    c.add(Calendar.MINUTE, m);
                    serviceVessel.setCloseRec(c.getTime());

                    serviceVessel.setDockCode(dock.getDockCode());
                    serviceVessel.setStatus("Servicing");
                    serviceVesselFacade.edit(serviceVessel);
                    serviceVessels = lookupServiceVesselFacadeRemote().findServiceVesselServicing();
                    FacesHelper.addFacesMessage(context, FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
                    RequestContext.getCurrentInstance().addCallbackParam("loggedIn", loggedIn);
                }
            }
        }
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
     * @return the serviceVessel
     */
    public ServiceVessel getServiceVessel() {
        return serviceVessel;
    }

    /**
     * @param serviceVessel the serviceVessel to set
     */
    public void setServiceVessel(ServiceVessel serviceVessel) {
        this.serviceVessel = serviceVessel;
    }

    /**
     * @return the serviceVessels
     */
    public List<Object[]> getServiceVessels() {
        return serviceVessels;
    }

    /**
     * @param serviceVessels the serviceVessels to set
     */
    public void setServiceVessels(List<Object[]> serviceVessels) {
        this.serviceVessels = serviceVessels;
    }

    private List<Object[]> conflictVesselService(Short frMeter, Short toMeter, String dockCode, String noPpkb){
        return serviceVesselFacade.checkSpaceAvailability(frMeter, toMeter, dockCode, noPpkb);
    }

    private ServiceVesselFacadeRemote lookupServiceVesselFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (ServiceVesselFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/ServiceVesselFacade!com.pelindo.ebtos.ejb.facade.remote.ServiceVesselFacadeRemote");
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

    private MasterDockFacadeRemote lookupMasterDockFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (MasterDockFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/MasterDockFacade!com.pelindo.ebtos.ejb.facade.remote.MasterDockFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private FormulaManagerFacadeRemote lookupFormulaManagerFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (FormulaManagerFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/FormulaManagerFacade!com.pelindo.ebtos.ejb.facade.remote.FormulaManagerFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    /**
     * @return the dock
     */
    public MasterDock getDock() {
        return dock;
    }

    /**
     * @param dock the dock to set
     */
    public void setDock(MasterDock dock) {
        this.dock = dock;
    }

    public boolean isListState(){
        return getPageState().equals(VesselServiceBean.LIST);
    }

    public boolean isDetailState(){
        return getPageState().equals(VesselServiceBean.DETAIL);
    }

    public void setToListState(){
        setPageState(VesselServiceBean.LIST);
    }

    public void setToDetailState(){
        setPageState(VesselServiceBean.DETAIL);
    }

    /**
     * @return the sv
     */
    public Object[] getSv() {
        return sv;
    }

    /**
     * @param sv the sv to set
     */
    public void setSv(Object[] sv) {
        this.sv = sv;
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

    public String getBerthUrl(){
        FacesContext context  = FacesContext.getCurrentInstance();
        return context.getApplication().getViewHandler().getActionURL(context, "/modules/Monitoring/BerthMonitoring.xhtml");
    }
}
