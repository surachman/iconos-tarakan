/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.bean;

import com.qtasnim.jsf.FacesHelper;
import com.pelindo.ebtos.ejb.facade.remote.MasterDockFacadeRemote;
import com.pelindo.ebtos.model.db.master.MasterDock;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.event.DateSelectEvent;

/**
 *
 * @author senoanggoro
 */
@ManagedBean(name="vesselScheduleMonitoring")
@ViewScoped
public class VesselScheduleMonitoring implements Serializable{
    @EJB
    private MasterDockFacadeRemote masterDockFacade;
    
    private List<MasterDock> docks;
    private MasterDock dock;
    private Date startDate;
    private Date endDate;
    private int berthWidth;

    public VesselScheduleMonitoring() {}

    @PostConstruct
    private void construct() {
        String dockCode = FacesHelper.getParam("dockCode", FacesContext.getCurrentInstance());
        berthWidth = 1000;
        docks = masterDockFacade.findAll();

        if (docks.size() > 0) {
            dock = docks.get(0);
            if (dockCode != null){
                MasterDock obj = masterDockFacade.find(dockCode);
                if (obj != null)
                    dock = obj;
            }
        }

        Calendar calendar = Calendar.getInstance();
        startDate = calendar.getTime();
        
        calendar.add(Calendar.DAY_OF_YEAR, 10);
        endDate = calendar.getTime();
    }

    public void handleSelectBerth (ActionEvent event){
        String dockCode = (String) event.getComponent().getAttributes().get("dockCode");
        dock = masterDockFacade.find(dockCode);
    }

    public void handleResetTransactionRecapParameter(DateSelectEvent event) {
        org.primefaces.component.calendar.Calendar sender = (org.primefaces.component.calendar.Calendar) event.getSource();
        Date date = (Date) sender.getValue();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        
        if (sender.getId().equals("startDate")) {
            if (date.compareTo(endDate) >= 0) {
                cal.add(Calendar.DAY_OF_MONTH, 1);
                endDate = cal.getTime();
            }
        } else if (sender.getId().equals("endDate")) {
            if (date.compareTo(startDate) <= 0) {
                cal.setTime(startDate);
                cal.add(Calendar.DAY_OF_MONTH, 1);
                endDate = cal.getTime();
            }
        }
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

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getVesselScheduleMonitoringUrl(){
        return FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/../vesselScheduleVisual.png?"
                + "dockCode=" + dock.getDockCode() + "&"
                + "width=" + berthWidth + "&"
                + "from=" + startDate.getTime() + "&"
                + "to=" + endDate.getTime() + "";
    }
}
