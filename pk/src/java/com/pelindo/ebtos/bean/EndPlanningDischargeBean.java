/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.bean;

import com.qtasnim.jsf.FacesHelper;
import com.pelindo.ebtos.ejb.facade.remote.PlanningContDischargeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.YardAllocationTempFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningShiftDischargeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningTransDischargeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningVesselFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.BaplieDischargeFacadeRemote;
import com.pelindo.ebtos.model.db.PlanningVessel;
import com.pelindo.ebtos.model.db.PreserviceVessel;
import com.pelindo.ebtos.model.db.BaplieDischarge;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author dycoder
 */
@Named(value="endPlanningDischargeBean")
@RequestScoped
public class EndPlanningDischargeBean {
    PlanningShiftDischargeFacadeRemote planningShiftDischargeFacade = lookupPlanningShiftDischargeFacadeRemote();
    PlanningTransDischargeFacadeRemote planningTransDischargeFacade = lookupPlanningTransDischargeFacadeRemote();
    PlanningContDischargeFacadeRemote planningContDischargeFacade = lookupPlanningContDischargeFacadeRemote();
    YardAllocationTempFacadeRemote yardAllocationTempFacade = lookupYardAllocationTempFacadeRemote();
    PlanningVesselFacadeRemote planningVesselFacade = lookupPlanningVesselFacadeRemote();
    BaplieDischargeFacadeRemote baplieDischargeFacade = lookupBaplieDischargeFacadeRemote();
    //PreserviceVessel ps = new PreserviceVessel();
    
    private List<Object[]> planningVessels ;

    private PlanningVessel planningVessel;
    private Object[] pv;

    private String pageState;
    private static String LIST = "list";
    private static String DETAIL = "detail";

    /** Creates a new instance of EndPlanningDischargeBean */
    public EndPlanningDischargeBean() {
        planningVessel = new PlanningVessel();
        pageState = EndPlanningDischargeBean.LIST;
        planningVessels = lookupPlanningVesselFacadeRemote().findPlanningVesselApprovalReady();
    }

    public void handleSelectTable(ActionEvent event){
        String no_ppkb = (String) event.getComponent().getAttributes().get("noPpkb");
        planningVessel = lookupPlanningVesselFacadeRemote().find(no_ppkb);
    }

    public void handlePreview (ActionEvent event){
        String no_ppkb = (String) event.getComponent().getAttributes().get("noPpkb");
        pv = planningVesselFacade.findPlanningVesselDetail(no_ppkb);
        pageState = EndPlanningDischargeBean.DETAIL;
    }

    public void handleEndPlanning(ActionEvent event){
//        String cek = "b";
//
//        List<Object[]> o = planningContDischargeFacade.findPlanningContDischargeListNative(planningVessel.getNoPpkb());
//        List<Object[]> oo = planningTransDischargeFacade.findPlanningTransDischargeListNative(planningVessel.getNoPpkb());
//        List<Object[]> ooo = planningShiftDischargeFacade.findPlanningShiftDischargeListNative(planningVessel.getNoPpkb());
//        if(o.isEmpty() || oo.isEmpty() || ooo.isEmpty())
//            cek = "s";
//
//        //cek planning_cont_discharge
//        for (Object[] o1 : o) {
//            PlanningContDischarge pcd = planningContDischargeFacade.find(o1[9]);
//            if (pcd.getContNo() == null || pcd.getMasterCommodity() == null || pcd.getContSize() == null || pcd.getMasterContainerType() == null
//                    || pcd.getContStatus() == null || pcd.getContGross() == null || pcd.getMasterYard() == null || pcd.getVBay() == null || pcd.getVRow() == null
//                    || pcd.getVTier() == null || pcd.getYRow() == null || pcd.getYSlot() == null || pcd.getYTier() == null || pcd.getGrossClass() == null) {
//                cek = "s";
//            }
//        }
//        //cek planning_trans_discharge
//        for (Object[] o1 : oo) {
//            PlanningTransDischarge ptd = planningTransDischargeFacade.find(o1[9]);
//            if (ptd.getContNo() == null || ptd.getMasterCommodity() == null || ptd.getContSize() == null || ptd.getMasterContainerType() == null
//                    || ptd.getContStatus() == null || ptd.getContGross() == null || ptd.getMasterYard() == null || ptd.getVBay() == null || ptd.getVRow() == null
//                    || ptd.getVTier() == null || ptd.getYRow() == null || ptd.getYSlot() == null || ptd.getYTier() == null) {
//                cek = "s";
//            }
//        }
//        //cek planning_shift_discharge
//        for (Object[] o1 : ooo) {
//            PlanningShiftDischarge psd = planningShiftDischargeFacade.find(o1[8]);
//            if (psd.getContNo() == null || psd.getMasterCommodity() == null || psd.getContSize() == null || psd.getMasterContainerType() == null
//                    || psd.getContStatus() == null || psd.getContGross() == null || psd.getMasterYard() == null || psd.getVBay() == null || psd.getVRow() == null
//                    || psd.getVTier() == null || psd.getYRow() == null || psd.getYSlot() == null || psd.getYTier() == null) {
//                cek = "s";
//            }
//        }
//        System.out.print(cek);       
        
        planningVessel = planningVesselFacade.find(planningVessel.getNoPpkb());
        Short act = planningVessel.getPreserviceVessel().getActivity();
        String msg = "";
        boolean readyToEnd = false;
        if(act == 2){
            readyToEnd = true;
        }else{
            
            String mPort = baplieDischargeFacade.mPort();
            
            //bongkaran
            int baplieCount = baplieDischargeFacade.findBaplieDischargeByPpkbPodDpc(planningVessel.getNoPpkb(), mPort, mPort).size();
            //int planningCount = planningContDischargeFacade.findByNoPpkb(planningVessel.getNoPpkb()).size();
            int planningCount = yardAllocationTempFacade.findByNoPpkb(planningVessel.getNoPpkb()).size();
            if (baplieCount == planningCount){ readyToEnd = true;}else readyToEnd = false;

            //transhipment
            baplieCount = baplieDischargeFacade.findBaplieDischargeByPpkbNotPod(planningVessel.getNoPpkb(), mPort, planningVessel.getPreserviceVessel().getMasterPort().getPortCode()).size();
            planningCount = planningTransDischargeFacade.findNativeByPpkb(planningVessel.getNoPpkb()).size();
            if (baplieCount == planningCount){ readyToEnd &= true;}else readyToEnd &= false;

            //Shifting via CY
            baplieCount = baplieDischargeFacade.findBaplieDischargeByPpkbShiftingViaCY(planningVessel.getNoPpkb(), mPort, planningVessel.getPreserviceVessel().getMasterPort().getPortCode()).size();
            planningCount = planningShiftDischargeFacade.findByNoPpkb(planningVessel.getNoPpkb()).size();
            if (baplieCount == planningCount){ readyToEnd &= true;} else readyToEnd &= false;

        }


        if(readyToEnd == true){
            planningVessel.setStatus("ReadyService");
            lookupPlanningVesselFacadeRemote().edit(planningVessel);
            planningVessels = lookupPlanningVesselFacadeRemote().findPlanningVesselApprovalReady();
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.confirm.succeeded");
        }else
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning", "Data Planning Container belum lengkap"));
    }

    public void handleRollBack(ActionEvent event) {
        planningVessel = planningVesselFacade.find(planningVessel.getNoPpkb());
        planningVessel.setStatus("Approved");
        lookupPlanningVesselFacadeRemote().edit(planningVessel);
        planningVessels = lookupPlanningVesselFacadeRemote().findPlanningVesselApprovalReady();
        FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.rollback.succeeded");
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

    private PlanningContDischargeFacadeRemote lookupPlanningContDischargeFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (PlanningContDischargeFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/PlanningContDischargeFacade!com.pelindo.ebtos.ejb.facade.remote.PlanningContDischargeFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private PlanningTransDischargeFacadeRemote lookupPlanningTransDischargeFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (PlanningTransDischargeFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/PlanningTransDischargeFacade!com.pelindo.ebtos.ejb.facade.remote.PlanningTransDischargeFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private PlanningShiftDischargeFacadeRemote lookupPlanningShiftDischargeFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (PlanningShiftDischargeFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/PlanningShiftDischargeFacade!com.pelindo.ebtos.ejb.facade.remote.PlanningShiftDischargeFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private BaplieDischargeFacadeRemote lookupBaplieDischargeFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (BaplieDischargeFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/BaplieDischargeFacade!com.pelindo.ebtos.ejb.facade.remote.BaplieDischargeFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
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
        return getPageState().equals(EndPlanningDischargeBean.LIST);
    }

    public boolean isDetailState(){
        return getPageState().equals(EndPlanningDischargeBean.DETAIL);
    }

    public void setToListState(){
        setPageState(EndPlanningDischargeBean.LIST);
    }

    public void setToDetailState(){
        setPageState(EndPlanningDischargeBean.DETAIL);
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

    private YardAllocationTempFacadeRemote lookupYardAllocationTempFacadeRemote() {
       try {
            Context c = new InitialContext();
            return (YardAllocationTempFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/YardAllocationTempFacade!com.pelindo.ebtos.ejb.facade.remote.YardAllocationTempFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
