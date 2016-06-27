/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.bean;

import com.qtasnim.jsf.FacesHelper;
import com.pelindo.ebtos.ejb.facade.remote.UcGatereceivingServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.UcReceivingServiceFacadeRemote;
import com.pelindo.ebtos.model.db.UcGatereceivingService;
import com.pelindo.ebtos.model.db.UcReceivingService;
import com.pelindo.ebtos.model.db.UncontainerizedService;
import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

/**
 *
 * @author dycoder
 */
@ManagedBean(name = "gateOutReceivingUcBean")
@ViewScoped
public class GateOutReceivingUcBean implements Serializable {
    @EJB
    private UcGatereceivingServiceFacadeRemote ucGatereceivingServiceFacadeRemote;
    @EJB
    private UcReceivingServiceFacadeRemote ucReceivingServiceFacadeRemote;

    private UcReceivingService ucReceivingService;
    private UncontainerizedService uncontainerizedService;
    private UcGatereceivingService ucGatereceivingService;
    private String jobSlip = null;
    private Object[] gateOutUc;

    /** Creates a new instance of GateOutReceivingUcBean */
    public GateOutReceivingUcBean() {
        ucGatereceivingService = new UcGatereceivingService();
        uncontainerizedService = new UncontainerizedService();
        ucReceivingService = new UcReceivingService();
    }

    public List<String> setListAutoComplete(String query) {       
        List<String> results = ucGatereceivingServiceFacadeRemote.findUcGatereceivingServiceByAutoComplete(query);
        return results;
    }

    public void ambilContNo(ActionEvent event) {
        setJobSlip((String) event.getComponent().getAttributes().get("jobSlip"));
        gateOutUc = ucGatereceivingServiceFacadeRemote.findUcGatereceivingServiceByDateOutNull(jobSlip);
        if (gateOutUc != null) {
            ucGatereceivingService = ucGatereceivingServiceFacadeRemote.find(gateOutUc[1]);
            ucReceivingService = ucReceivingServiceFacadeRemote.find(ucGatereceivingService.getJobslip());
            uncontainerizedService = ucReceivingService.getUncontainerizedService();
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.search.found");
        } else {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.search.notfound");
            this.clear();
        }
    }

    public void saveEdit(ActionEvent event) {
        ucGatereceivingService.setDateOut(Calendar.getInstance().getTime());
        ucGatereceivingServiceFacadeRemote.edit(ucGatereceivingService);
        FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.transaction.succeeded");
        this.clear();
    }

    public void clear2(ActionEvent event) {
       this.clear();
    }

    public void clear() {
        ucGatereceivingService = new UcGatereceivingService();
        uncontainerizedService = new UncontainerizedService();
        ucReceivingService = new UcReceivingService();
    }

    public Object[] getGateOutUc() {
        return gateOutUc;
    }

    public void setGateOutUc(Object[] gateOutUc) {
        this.gateOutUc = gateOutUc;
    }

    public String getJobSlip() {
        return jobSlip;
    }

    public void setJobSlip(String jobSlip) {
        this.jobSlip = jobSlip;
    }

    public UcGatereceivingService getUcGatereceivingService() {
        return ucGatereceivingService;
    }

    public void setUcGatereceivingService(UcGatereceivingService ucGatereceivingService) {
        this.ucGatereceivingService = ucGatereceivingService;
    }

    public UcReceivingService getUcReceivingService() {
        return ucReceivingService;
    }

    public void setUcReceivingService(UcReceivingService ucReceivingService) {
        this.ucReceivingService = ucReceivingService;
    }

    public UncontainerizedService getUncontainerizedService() {
        return uncontainerizedService;
    }

    public void setUncontainerizedService(UncontainerizedService uncontainerizedService) {
        this.uncontainerizedService = uncontainerizedService;
    }
}
