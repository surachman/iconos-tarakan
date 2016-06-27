/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.bean;

import com.qtasnim.jsf.FacesHelper;
import com.pelindo.ebtos.ejb.facade.remote.MasterDockFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningVesselFacadeRemote;
import com.pelindo.ebtos.ejb.remote.BerthScheduleRemote;
import com.pelindo.ebtos.model.db.master.MasterDock;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author senoanggoro
 */
@ManagedBean(name="berthMonitoring")
@ViewScoped
public class BerthMonitoring implements Serializable{
    BerthScheduleRemote berthSchedule = lookupBerthScheduleRemote();
    PlanningVesselFacadeRemote planningVesselFacadeRemote = lookupPlanningVesselFacadeRemote();
    MasterDockFacadeRemote masterDockFacade = lookupMasterDockFacadeRemote();
    
    private List<MasterDock> docks = masterDockFacade.findAll();
    private MasterDock dock;
    private int berthWidth;
    private int berthHeight;

    public BerthMonitoring() {}

    @PostConstruct
    private void construct() {
        String dockCode = FacesHelper.getParam("dockCode", FacesContext.getCurrentInstance());
        berthWidth = 1000;
        berthHeight = 450;
        if (docks.size() > 0) {
            dock = docks.get(0);
            if (dockCode != null){
                MasterDock obj = masterDockFacade.find(dockCode);
                if (obj != null)
                    dock = obj;
            }
        }
        berthSchedule.init(dock.getDockCode(), berthWidth, berthHeight);
    }

    public MasterDock getDock() {
        return dock;
    }

    public void setDock(MasterDock dock) {
        this.dock = dock;
    }

    public List<MasterDock> getDocks() {
        return docks;
    }


    public String getBirdViewUrl(){
        return FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/../berthSchedule.png?mode=m&"
                + "dockCode=" + dock.getDockCode() + "&"
                + "width=" + berthWidth + "&"
                + "height=" + berthHeight + "";
    }

    public void handleSelectBerth (ActionEvent event){
        String dock_code = (String) event.getComponent().getAttributes().get("dockCode");
        System.out.println(dock_code);
        dock = masterDockFacade.find(dock_code);
        berthSchedule.init(dock.getDockCode(), berthWidth, berthHeight);
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

    private PlanningVesselFacadeRemote lookupPlanningVesselFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (PlanningVesselFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/PlanningVesselFacade!com.pelindo.ebtos.ejb.facade.remote.PlanningVesselFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private BerthScheduleRemote lookupBerthScheduleRemote() {
        try {
            Context c = new InitialContext();
            return (BerthScheduleRemote) c.lookup("java:global/pkproject/pk-ejb/BerthSchedule!com.pelindo.ebtos.ejb.remote.BerthScheduleRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
