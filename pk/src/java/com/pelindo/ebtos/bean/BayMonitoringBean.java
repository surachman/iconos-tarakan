/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.bean;

import com.pelindo.ebtos.ejb.facade.remote.MasterVesselProfileFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceContLoadFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceShiftingFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceVesselFacadeRemote;
import com.pelindo.ebtos.model.db.ServiceContLoad;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import org.apache.commons.lang3.StringEscapeUtils;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Lenovo
 */
@ManagedBean(name="bayMonitoringBean")
@ViewScoped
public class BayMonitoringBean {
    @EJB
    private ServiceVesselFacadeRemote serviceVesselFacade;
    @EJB
    private MasterVesselProfileFacadeRemote masterVesselProfileFacadeRemote;
    @EJB
    private ServiceContLoadFacadeRemote serviceContLoadFacadeRemote;
    @EJB
    private ServiceShiftingFacadeRemote serviceShiftingFacadeRemote;

    private List<Object[]> serviceVessels;
    private List<String> bays = new ArrayList();
    private List<ServiceContLoad> services;
    private Object[] serviceVessel;
    private String noPPKB;
    private String selectedBay;
    
    @PostConstruct
    private void construct(){
        serviceVessels = serviceVesselFacade.findServiceVesselStatusService();
    }

    public void handleSelectNoPPKB(ActionEvent event){
        noPPKB = (String) event.getComponent().getAttributes().get("noPPKB");
        serviceVessel = serviceVesselFacade.findServiceVesselByPpkb(noPPKB);
        bays = masterVesselProfileFacadeRemote.findBaysByNoPpkb(noPPKB);
        selectedBay = bays.get(0);
    }

    public void handleChangeBay(ValueChangeEvent event){
        selectedBay = event.getNewValue().toString();
    }
    
    public void handleClickNextBayButton(ActionEvent event) {
        if (!selectedBay.equals(bays.get(bays.size() - 1))) {
            int index = bays.indexOf(selectedBay);
            if (index < bays.size() - 1) {
                selectedBay = bays.get(index + 1);
            }
        }
    }

    public void handleClickPrevBayButton(ActionEvent event) {
        if (!selectedBay.equals(bays.get(0))) {
            int index = bays.indexOf(selectedBay);
            if (index > 0) {
                selectedBay = bays.get(index - 1);
            }
        }
    }
    
    public String getVesselProfileUrl() {
        if (serviceVessel == null || bays == null) {
            return "#";
        }
        return FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/../bayMonitoring.png?"
                + "isLoad=t&"
                + "vessel=" + serviceVessel[4] + "&"
                + "bay=" + selectedBay + "&"
                + "ppkb=" + noPPKB;
    }
    
    public void handlePrint(){
        RequestContext context = RequestContext.getCurrentInstance();
        context.addCallbackParam("doPrint", true);
        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3("../../../bayMonitoringReport.pdf?"
                + "isLoad=t&"
                + "vessel=" + serviceVessel[4] + "&"
                + "bay=" + selectedBay + "&"
                + "ppkb=" + noPPKB));
    }

    public List<ServiceContLoad> getServices() {
        return services;
    }

    public void setServices(List<ServiceContLoad> services) {
        this.services = services;
    }

    public List<String> getBays() {
        return bays;
    }

    public void setBays(List<String> bays) {
        this.bays = bays;
    }

    public String getSelectedBay() {
        return selectedBay;
    }

    public void setSelectedBay(String selectedBay) {
        this.selectedBay = selectedBay;
    }

    public String getNoPPKB() {
        return noPPKB;
    }

    public void setNoPPKB(String noPPKB) {
        this.noPPKB = noPPKB;
    }

    public Object[] getServiceVessel() {
        return serviceVessel;
    }

    public void setServiceVessel(Object[] serviceVessel) {
        this.serviceVessel = serviceVessel;
    }
    
    

    public List<Object[]> getServiceVessels() {
        return serviceVessels;
    }

    public void setServiceVessels(List<Object[]> serviceVessels) {
        this.serviceVessels = serviceVessels;
    }
}
