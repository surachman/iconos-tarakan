/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.bean;

import com.pelindo.ebtos.ejb.facade.remote.ServiceHatchMovesFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceVesselFacadeRemote;
import com.pelindo.ebtos.model.db.ServiceHatchMoves;
import com.pelindo.ebtos.model.db.ServiceVessel;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.apache.commons.lang3.StringEscapeUtils;
import org.primefaces.context.RequestContext;

/**
 *
 * @author dycoder
 */
@ManagedBean(name = "calculatingHatchMoves")
@ViewScoped
public class CalculatingHatchMoves implements Serializable {

    @EJB
    private ServiceVesselFacadeRemote serviceVesselFacadeFacadeRemote;
    @EJB
    private ServiceHatchMovesFacadeRemote serviceHatchMovesFacadeFacadeRemote;
    private ServiceVessel serviceVessel;
    private Object[] serviceVesselDetail;
    private String no_ppkb;
    private String operation;
    private ServiceHatchMoves serviceHatchMoves;
    private List<Object[]> serviceHatchMovesList = lookupServiceHatchMovesFacadeRemote().findServiceHatchMovesByPpkbOperation(null, null);
//    private List<Object[]> serviceVesselServeds = lookupServiceVesselFacadeRemote().findServiceVesselsServiced();
    private List<Object[]> serviceVesselServeds = lookupServiceVesselFacadeRemote().findServiceVesselsServicedServicingDischarge();
    private String mode;

    /** Creates a new instance of CalculatingHatchMoves */
    public CalculatingHatchMoves() {
        serviceVessel = new ServiceVessel();
        setOperation("DISCHARGELOAD");
        serviceVesselDetail=new Object[4];

    }

    public void handleSelectNoPpkb(ActionEvent event) {
        no_ppkb = (String) event.getComponent().getAttributes().get("noPpkb");
        serviceVesselDetail = serviceVesselFacadeFacadeRemote.findServiceVesselByPpkb(no_ppkb);
        
        if (getOperation().equals("DISCHARGE")){
        serviceHatchMovesList = serviceHatchMovesFacadeFacadeRemote.findServiceHatchMovesByPpkbOperationDischarge(no_ppkb, getOperation());
        }
        else if (getOperation().equals("LOAD")){
        serviceHatchMovesList = serviceHatchMovesFacadeFacadeRemote.findServiceHatchMovesByPpkbOperationLoad(no_ppkb, getOperation());
        }
        else{
        serviceHatchMovesList = serviceHatchMovesFacadeFacadeRemote.findServiceHatchMovesByPpkbOperation(no_ppkb, getOperation());
        }
    }
    
    public void handleDownload(ActionEvent event){
        boolean print = false;
        if (serviceHatchMovesList != null){
            print = true;
        }
        setMode("hatchMoves");
        RequestContext context = RequestContext.getCurrentInstance();
        context.addCallbackParam("doPrint", print); //harus ada, buat ngeflag di izinkan ngeprint ato ngga
        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3("../../../../reportHatchMoves.pdf?"
                + "mode=" + getMode() + "&" + "noRegistrasi=" + no_ppkb + "" + "&" + "operation=" + getOperation()));
    }

    public String getUrlReceiving() {
        if (serviceHatchMovesList == null) {
            return "#";
        }

        setMode("hatchMoves");
        return FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/reportHatchMoves.pdf?"
                + "mode=" + getMode() + "&" + "noRegistrasi=" + no_ppkb + "" + "&" + "operation=" + getOperation() + "";
    }
    public void onChangeEvent(ValueChangeEvent event) {
        operation = (String) event.getNewValue();
    }

    /**
     * @return the serviceHatchMoves
     */
    public ServiceHatchMoves getServiceHatchMoves() {
        return serviceHatchMoves;
    }

    /**
     * @param serviceHatchMoves the serviceHatchMoves to set
     */
    public void setServiceHatchMoves(ServiceHatchMoves serviceHatchMoves) {
        this.serviceHatchMoves = serviceHatchMoves;
    }

    private ServiceHatchMovesFacadeRemote lookupServiceHatchMovesFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (ServiceHatchMovesFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/ServiceHatchMovesFacade!com.pelindo.ebtos.ejb.facade.remote.ServiceHatchMovesFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
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

    /**
     * @return the serviceHatchMovesList
     */
    public List<Object[]> getServiceHatchMovesList() {
        return serviceHatchMovesList;
    }

    /**
     * @param serviceHatchMovesList the serviceHatchMovesList to set
     */
    public void setServiceHatchMovesList(List<Object[]> serviceHatchMovesList) {
        this.serviceHatchMovesList = serviceHatchMovesList;
    }

    /**
     * @return the serviceVesselServeds
     */
    public List<Object[]> getServiceVesselServeds() {
        return serviceVesselServeds;
    }

    /**
     * @param serviceVesselServeds the serviceVesselServeds to set
     */
    public void setServiceVesselServeds(List<Object[]> serviceVesselServeds) {
        this.serviceVesselServeds = serviceVesselServeds;
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
     * @return the serviceVesselDetail
     */
    public Object[] getServiceVesselDetail() {
        return serviceVesselDetail;
    }

    /**
     * @param serviceVesselDetail the serviceVesselDetail to set
     */
    public void setServiceVesselDetail(Object[] serviceVesselDetail) {
        this.serviceVesselDetail = serviceVesselDetail;
    }

    /**
     * @return the no_ppkb
     */
    public String getNo_ppkb() {
        return no_ppkb;
    }

    /**
     * @param no_ppkb the no_ppkb to set
     */
    public void setNo_ppkb(String no_ppkb) {
        this.no_ppkb = no_ppkb;
    }

    /**
     * @return the operation
     */
    public String getOperation() {
        return operation;
    }

    /**
     * @param operation the operation to set
     */
    public void setOperation(String operation) {
        this.operation = operation;
    }

    /**
     * @return the mode
     */
    public String getMode() {
        return mode;
    }

    /**
     * @param mode the mode to set
     */
    public void setMode(String mode) {
        this.mode = mode;
    }
}
