/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.bean;

import com.qtasnim.jsf.FacesHelper;
import com.pelindo.ebtos.ejb.facade.remote.ServiceGateReceivingFacadeRemote;
import com.pelindo.ebtos.model.db.ServiceGateReceiving;
import java.io.Serializable;
import java.util.Calendar;
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
@ManagedBean(name = "gateOutReceivingBean")
@ViewScoped
public class GateOutReceivingBean implements Serializable {
    @EJB
    private ServiceGateReceivingFacadeRemote serviceGateReceivingFacade;

    private List<Object[]> serviceGateOutList;
    private ServiceGateReceiving serviceGateReceiving;
    private Object[] serviceGateReceivingObject;
    private String jobSlip;
    private Integer cont_no;

    /** Creates a new instance of GateOutReceivingBean */
    public GateOutReceivingBean() {}

    @PostConstruct
    private void construct(){
        serviceGateReceiving = new ServiceGateReceiving();

        serviceGateOutList = serviceGateReceivingFacade.findServiceGateReceivingDateOutNull();
    }

    public void clear(ActionEvent event) {
        serviceGateReceiving = new ServiceGateReceiving();
    }

    public List<String> setListAutoComplete(String query) {
        List<String> results = results = serviceGateReceivingFacade.findGateOutPassableJobslips(query);
        return results;
    }

    public void ambilContNo(ActionEvent event) {
        setJobSlip((String) event.getComponent().getAttributes().get("jobSlip"));
        serviceGateReceivingObject = serviceGateReceivingFacade.findGateOutPassableByJobSlip(jobSlip);
        
        if (serviceGateReceivingObject != null) {
            serviceGateReceiving = serviceGateReceivingFacade.find(serviceGateReceivingObject[0]);
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.search.found");
        } else {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.search.notfound");
            serviceGateReceiving = new ServiceGateReceiving();
        }
    }

    public void handleSelectNoCont(ActionEvent event) {
        setCont_no((Integer) event.getComponent().getAttributes().get("cont_no"));
        serviceGateReceiving = serviceGateReceivingFacade.find(getCont_no());
        //serviceGateOutList=serviceGateReceivingFacade.findServiceGateReceivingDateOutNull();
    }

    public void submit(ActionEvent event) {
        serviceGateReceiving = serviceGateReceivingFacade.find(serviceGateReceivingObject[0]);
        serviceGateReceiving.setDateOut(Calendar.getInstance().getTime());
        serviceGateReceivingFacade.edit(serviceGateReceiving);
        serviceGateOutList = serviceGateReceivingFacade.findServiceGateReceivingDateOutNull();

        FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.transaction.succeeded");
        clear(event);
    }

    public ServiceGateReceiving getServiceGateReceiving() {
        return serviceGateReceiving;
    }

    /**
     * @param serviceGateReceiving the serviceGateReceiving to set
     */
    public void setServiceGateReceiving(ServiceGateReceiving serviceGateReceiving) {
        this.serviceGateReceiving = serviceGateReceiving;
    }

    /**
     * @return the serviceGateOutList
     */
    public List<Object[]> getServiceGateOutList() {
        return serviceGateOutList;
    }

    /**
     * @param serviceGateOutList the serviceGateOutList to set
     */
    public void setServiceGateOutList(List<Object[]> serviceGateOutList) {
        this.serviceGateOutList = serviceGateOutList;
    }

    /**
     * @return the cont_no
     */
    public Integer getCont_no() {
        return cont_no;
    }

    /**
     * @param cont_no the cont_no to set
     */
    public void setCont_no(Integer cont_no) {
        this.cont_no = cont_no;
    }

    /**
     * @return the serviceGateReceivingObject
     */
    public Object[] getServiceGateReceivingObject() {
        return serviceGateReceivingObject;
    }

    /**
     * @param serviceGateReceivingObject the serviceGateReceivingObject to set
     */
    public void setServiceGateReceivingObject(Object[] serviceGateReceivingObject) {
        this.serviceGateReceivingObject = serviceGateReceivingObject;
    }

    /**
     * @return the jobSlip
     */
    public String getJobSlip() {
        return jobSlip;
    }

    /**
     * @param jobSlip the jobSlip to set
     */
    public void setJobSlip(String jobSlip) {
        this.jobSlip = jobSlip;
    }
}
