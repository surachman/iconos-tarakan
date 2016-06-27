/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.bean;

import com.qtasnim.jsf.FacesHelper;
import com.pelindo.ebtos.ejb.facade.remote.PlanningContDischargeFacadeRemote;
import java.io.Serializable;
import java.util.ArrayList;
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
import org.apache.commons.lang3.StringEscapeUtils;
import org.primefaces.context.RequestContext;

/**
 *
 * @author dycoder
 */
@ManagedBean(name="monitoringHistoryContainerBean")
@ViewScoped
public class MonitoringHistoryContainerBean implements Serializable{
    PlanningContDischargeFacadeRemote planningContDischargeFacade = lookupPlanningContDischargeFacadeRemote();

    private List<Object[]> containerList;

    private String cont_no;

    /** Creates a new instance of MonitoringHistoryContainerBean */
    public MonitoringHistoryContainerBean() {
        containerList = new ArrayList<Object[]>();
        cont_no = "";
    }

    public void handleSearch(ActionEvent event){
        containerList = planningContDischargeFacade.monitoringContainer(cont_no);
        if(containerList.size() > 0)
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.search.found");
        else
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.search.notfound");
    }

    public void handleClear(ActionEvent event){
        cont_no = "";
        containerList.clear();
    }

    public void handleDownload(ActionEvent event){
        boolean print = false;
        if (containerList != null){
            print = true;
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.addCallbackParam("doPrint", print); //harus ada, buat ngeflag di izinkan ngeprint ato ngga
        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3("../../../../goodsCfs.pdf?"
                + "mode=" + "contHistMonitoring" + "&"
                + "cont_no=" + cont_no));
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

    /**
     * @return the containerList
     */
    public List<Object[]> getContainerList() {
        return containerList;
    }

    /**
     * @param containerList the containerList to set
     */
    public void setContainerList(List<Object[]> containerList) {
        this.containerList = containerList;
    }

    /**
     * @return the cont_no
     */
    public String getCont_no() {
        return cont_no;
    }

    /**
     * @param cont_no the cont_no to set
     */
    public void setCont_no(String cont_no) {
        this.cont_no = cont_no;
    }

}
