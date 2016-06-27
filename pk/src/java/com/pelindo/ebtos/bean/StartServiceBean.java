/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.bean;

import com.qtasnim.jsf.FacesHelper;
import com.pelindo.ebtos.ejb.facade.remote.BookingOnlineFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.EquipmentFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningContDischargeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningShiftDischargeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningTransDischargeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningVesselFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceContDischargeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceContTranshipmentFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceShiftingFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceVesselFacadeRemote;
import com.pelindo.ebtos.model.db.BookingOnline;
import com.pelindo.ebtos.model.db.Equipment;
import com.pelindo.ebtos.model.db.PlanningVessel;
import com.pelindo.ebtos.model.db.ServiceContDischarge;
import com.pelindo.ebtos.model.db.ServiceContTranshipment;
import com.pelindo.ebtos.model.db.ServiceShifting;
import com.pelindo.ebtos.model.db.ServiceVessel;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

/**
 *
 * @author dycoder
 */
@ManagedBean(name="startServiceBean")
@ViewScoped
public class StartServiceBean {
    private static final String LIST = "list";
    private static final String DETAIL = "detail";

    @EJB
    private EquipmentFacadeRemote equipmentFacade;
    @EJB
    private BookingOnlineFacadeRemote bookingOnlineFacadeRemote;
    @EJB
    private PlanningShiftDischargeFacadeRemote planningShiftDischargeFacade;
    @EJB
    private PlanningTransDischargeFacadeRemote planningTransDischargeFacade;
    @EJB
    private PlanningContDischargeFacadeRemote planningContDischargeFacade;
    @EJB
    private ServiceVesselFacadeRemote serviceVesselFacade;
    @EJB
    private ServiceShiftingFacadeRemote serviceShiftingFacade;
    @EJB
    private ServiceContTranshipmentFacadeRemote serviceContTranshipmentFacade;
    @EJB
    private ServiceContDischargeFacadeRemote serviceContDischargeFacade;
    @EJB
    private PlanningVesselFacadeRemote planningVesselFacade;

    private List<Object[]> planningVessels;
    private List<Object[]> listSemua;
    private PlanningVessel planningVessel;
    private Object[] pv;

    private String pageState, no_ppkb;

    /** Creates a new instance of StartServiceBean */
    public StartServiceBean() {
    }

    @PostConstruct
    private void construct() {
        planningVessels = planningVesselFacade.findPlanningVesselReadyService();
        planningVessel = new PlanningVessel();
        pageState = StartServiceBean.LIST;
    }

    public void handleSelectTable(ActionEvent event){
        no_ppkb = (String) event.getComponent().getAttributes().get("noPpkb");
        planningVessel = planningVesselFacade.find(no_ppkb);
    }

    public void handlePreview (ActionEvent event){
        no_ppkb = (String) event.getComponent().getAttributes().get("noPpkb");
        pv = planningVesselFacade.findPlanningVesselDetail(no_ppkb);
        pageState = StartServiceBean.DETAIL;
    }

    public void handleStartService(ActionEvent event){
        FacesContext context = FacesContext.getCurrentInstance();
        planningVesselFacade.handleStartService(planningVessel);
        planningVessels = planningVesselFacade.findPlanningVesselReadyService();
        FacesHelper.addFacesMessage(context, FacesMessage.SEVERITY_INFO, "Info", "application.confirm.succeeded");
    }

    public void handleRollBack(ActionEvent event){
        FacesContext context = FacesContext.getCurrentInstance();

        planningVessel.setStatus("ReadyService");
        planningVesselFacade.edit(planningVessel);

        //hapus dari booking online
        Integer idBO = bookingOnlineFacadeRemote.findByBookingCode(planningVessel.getPreserviceVessel().getBookingCode());
        if (idBO != null) {
            BookingOnline bookingOnline = new BookingOnline();
            bookingOnline = bookingOnlineFacadeRemote.find(idBO);
            bookingOnline.setStatus("ReadyService");
            bookingOnlineFacadeRemote.edit(bookingOnline);
        }

        //hapus equipment
        listSemua = equipmentFacade.findByPpkb(planningVessel.getNoPpkb());
        if (listSemua != null) {
            for (Object o : listSemua) {
                Equipment e = equipmentFacade.find(o);
                equipmentFacade.remove(e);
            }
        }

        //hapus service_cont_discharge
        listSemua = serviceContDischargeFacade.findServiceContDischargesByPpkb(planningVessel.getNoPpkb());
        if (listSemua != null) {
            for (Object[] o : listSemua) {
                ServiceContDischarge scd = serviceContDischargeFacade.find(o[9]);
                serviceContDischargeFacade.remove(scd);
            }
        }

        //hapus service_cont_transhipment
        listSemua = serviceContTranshipmentFacade.findByPpkb(planningVessel.getNoPpkb());
        if (listSemua != null) {
            for (Object o : listSemua) {
                ServiceContTranshipment sct = serviceContTranshipmentFacade.find(o);
                serviceContTranshipmentFacade.remove(sct);
            }
        }

        //copy planning_shift_discharge ke service shifting
        listSemua = serviceShiftingFacade.findByPPKB(planningVessel.getNoPpkb());
        if (listSemua != null) {
            for (Object o : listSemua) {
                ServiceShifting ss = serviceShiftingFacade.find(o);
                serviceShiftingFacade.remove(ss);
            }
        }

        //hapus service vessel
        ServiceVessel serviceVessel = new ServiceVessel();
        serviceVessel = serviceVesselFacade.find(planningVessel.getNoPpkb());
        serviceVesselFacade.remove(serviceVessel);

        planningVessels = planningVesselFacade.findPlanningVesselReadyService();
        FacesHelper.addFacesMessage(context, FacesMessage.SEVERITY_INFO, "Info", "application.rollback.succeeded");
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

    public boolean isListState(){
        return getPageState().equals(StartServiceBean.LIST);
    }

    public boolean isDetailState(){
        return getPageState().equals(StartServiceBean.DETAIL);
    }

    public void setToListState(){
        setPageState(StartServiceBean.LIST);
    }

    public void setToDetailState(){
        setPageState(StartServiceBean.DETAIL);
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
}
