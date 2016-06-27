/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.bean;

import com.qtasnim.jsf.FacesHelper;
import com.pelindo.ebtos.util.GrossConverter;
import com.pelindo.ebtos.ejb.facade.remote.ChangeStatusHistoryFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterCommodityFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterContainerTypeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterPortFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterYardFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceContDischargeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceContTranshipmentFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceVesselFacadeRemote;
import com.pelindo.ebtos.model.db.ChangeStatusHistory;
import com.pelindo.ebtos.model.db.ServiceContDischarge;
import com.pelindo.ebtos.model.db.ServiceContTranshipment;
import com.pelindo.ebtos.model.db.ServiceVessel;
import com.pelindo.ebtos.model.db.master.MasterCommodity;
import com.pelindo.ebtos.model.db.master.MasterContainerType;
import com.pelindo.ebtos.model.db.master.MasterYard;
import java.io.Serializable;
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

/**
 *
 * @author dycoder
 */
@ManagedBean(name="transhipmentToDeliveryBean")
@ViewScoped
public class TranshipmentToDeliveryBean implements Serializable{
    MasterPortFacadeRemote masterPortFacadeRemote = lookupMasterPortFacadeRemote();
    MasterYardFacadeRemote masterYardFacade = lookupMasterYardFacadeRemote();
    MasterContainerTypeFacadeRemote masterContainerTypeFacade = lookupMasterContainerTypeFacadeRemote();
    MasterCommodityFacadeRemote masterCommodityFacade = lookupMasterCommodityFacadeRemote();
    ChangeStatusHistoryFacadeRemote changeStatusHistoryFacade = lookupChangeStatusHistoryFacadeRemote();
    ServiceContDischargeFacadeRemote serviceContDischargeFacade = lookupServiceContDischargeFacadeRemote();
    ServiceContTranshipmentFacadeRemote serviceContTranshipmentFacade = lookupServiceContTranshipmentFacadeRemote();
    ServiceVesselFacadeRemote serviceVesselFacade = lookupServiceVesselFacadeRemote();

    private List<Object[]> serviceVessels = lookupServiceVesselFacadeRemote().findServiceVesselsServicing();
    private List<Object[]> changeStatusHistorys;
    private List<Object[]> serviceContTranshipments;
    private List<Object[]> masterPorts = lookupMasterPortFacadeRemote().findNoError();

    private Object[] serviceVessel;
    private ServiceContTranshipment serviceContTranshipment;
    private ChangeStatusHistory changeStatusHistory;
    private String no_ppkb;

    /** Creates a new instance of TranshipmentToDeliveryBean */
    public TranshipmentToDeliveryBean() {
        serviceVessel = new Object[4];
        serviceContTranshipment = new ServiceContTranshipment();
        serviceContTranshipment.setServiceVessel(new ServiceVessel());
        serviceContTranshipment.setMasterYard(new MasterYard());
        serviceContTranshipment.setMasterCommodity(new MasterCommodity());
        serviceContTranshipment.setMasterContainerType(new MasterContainerType());
        changeStatusHistory = new ChangeStatusHistory();
    }

    public void handleSelectNoPpkb(ActionEvent event){
        no_ppkb = (String) event.getComponent().getAttributes().get("noPpkb");
        serviceVessel = serviceVesselFacade.findServiceVesselByPpkb(no_ppkb);
        serviceContTranshipments = serviceContTranshipmentFacade.findServiceContTranshipmentsConfirmServed(no_ppkb);
        changeStatusHistorys = changeStatusHistoryFacade.findListTranshipment(no_ppkb);
    }

    public void handleSelectContTrans(ActionEvent event){
        Integer id_cont = (Integer) event.getComponent().getAttributes().get("idCont");
        serviceContTranshipment = serviceContTranshipmentFacade.find(id_cont);
    }

    public void handleSelectContDisch(ActionEvent event){
        Integer id_cont = (Integer) event.getComponent().getAttributes().get("idCont");
        changeStatusHistory = changeStatusHistoryFacade.find(id_cont);
    }
    
    public void handleCopy(ActionEvent event){
        GrossConverter grossConverter = new GrossConverter();
        ServiceContDischarge scd = new ServiceContDischarge();

        scd.setContNo(serviceContTranshipment.getContNo());
        scd.setMlo(serviceContTranshipment.getMlo());
        scd.setServiceVessel(serviceContTranshipment.getServiceVessel());
        scd.setMasterCommodity(serviceContTranshipment.getMasterCommodity());
        scd.setContSize(serviceContTranshipment.getContSize());
        scd.setMasterContainerType(serviceContTranshipment.getMasterContainerType());
        scd.setContStatus(serviceContTranshipment.getContStatus());
        scd.setContGross(serviceContTranshipment.getContGross());
        scd.setSealNo(serviceContTranshipment.getSealNo());
        scd.setMasterYard(serviceContTranshipment.getMasterYard());
        scd.setDangerous(serviceContTranshipment.getDangerous());
        scd.setDgLabel(serviceContTranshipment.getDgLabel());
        scd.setOverSize(serviceContTranshipment.getOverSize());
        scd.setVBay(serviceContTranshipment.getVBay());
        scd.setVRow(serviceContTranshipment.getVRow());
        scd.setVTier(serviceContTranshipment.getVTier());
        scd.setYRow(serviceContTranshipment.getYRow());
        scd.setYSlot(serviceContTranshipment.getYSlot());
        scd.setYTier(serviceContTranshipment.getYTier());
        scd.setStartPlacementDate(serviceContTranshipment.getStartPlacementDate());
        scd.setCrane(serviceContTranshipment.getCrane());
        scd.setPosition(serviceContTranshipment.getPosition());
        scd.setIsDelivery("FALSE");
        scd.setIsBehandle("FALSE");
        scd.setIsCfs("FALSE");
        scd.setIsInspection("FALSE");       
        scd.setGrossClass(grossConverter.convert(serviceContTranshipment.getContSize(), serviceContTranshipment.getContGross()));
        scd.setBlNo(serviceContTranshipment.getBlNo());
        scd.setCancelLoading("FALSE");
        scd.setDischPort(serviceContTranshipment.getDischPort());
        scd.setLoadPort(serviceContTranshipment.getLoadPort());

        changeStatusHistory = new ChangeStatusHistory(serviceContTranshipment);

        serviceContDischargeFacade.create(scd);
        changeStatusHistoryFacade.create(changeStatusHistory);
        serviceContTranshipmentFacade.remove(serviceContTranshipment);
        serviceContTranshipments = serviceContTranshipmentFacade.findServiceContTranshipmentsConfirmServed(no_ppkb);
        changeStatusHistorys = changeStatusHistoryFacade.findListTranshipment(no_ppkb);
        FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.save.succeeded");
    }

    public void handleDelete(ActionEvent event){
        serviceContTranshipment.setContNo(changeStatusHistory.getContNo());
        serviceContTranshipment.setMlo(changeStatusHistory.getMlo());
        serviceContTranshipment.setServiceVessel(serviceVesselFacade.find(changeStatusHistory.getOldPpkb()));
        serviceContTranshipment.setMasterCommodity(masterCommodityFacade.find(changeStatusHistory.getCommodityCode()));
        serviceContTranshipment.setContSize(changeStatusHistory.getContSize());
        serviceContTranshipment.setMasterContainerType(masterContainerTypeFacade.find(changeStatusHistory.getContType()));
        serviceContTranshipment.setContStatus(changeStatusHistory.getContStatus());
        serviceContTranshipment.setContGross(changeStatusHistory.getContGross());
        serviceContTranshipment.setSealNo(changeStatusHistory.getSealNo());
        serviceContTranshipment.setMasterYard(masterYardFacade.find(changeStatusHistory.getBlock()));
        serviceContTranshipment.setDangerous(changeStatusHistory.getDangerous());
        serviceContTranshipment.setDgLabel(changeStatusHistory.getDgLabel());
        serviceContTranshipment.setOverSize(changeStatusHistory.getOverSize());
        serviceContTranshipment.setVBay(changeStatusHistory.getVBay());
        serviceContTranshipment.setVRow(changeStatusHistory.getVRow());
        serviceContTranshipment.setVTier(changeStatusHistory.getVTier());
        serviceContTranshipment.setYRow(changeStatusHistory.getYRow());
        serviceContTranshipment.setYSlot(changeStatusHistory.getYSlot());
        serviceContTranshipment.setYTier(changeStatusHistory.getYTier());
        serviceContTranshipment.setStartPlacementDate(changeStatusHistory.getStartPlacementDate());
        serviceContTranshipment.setCrane(changeStatusHistory.getCrane());
        serviceContTranshipment.setPosition(changeStatusHistory.getPosition());
        serviceContTranshipment.setBlNo(changeStatusHistory.getBlNo());
        serviceContTranshipment.setDischPort(changeStatusHistory.getDischPort());
        serviceContTranshipment.setLoadPort(changeStatusHistory.getLoadPort());

        Object[] scd = serviceContDischargeFacade.findByPpkbAndContNo(no_ppkb, changeStatusHistory.getContNo());
        ServiceContDischarge servContDisch = new ServiceContDischarge();
        servContDisch = serviceContDischargeFacade.find(scd[0]);

        serviceContTranshipmentFacade.create(serviceContTranshipment);
        serviceContDischargeFacade.remove(servContDisch);
        changeStatusHistoryFacade.remove(changeStatusHistory);
        serviceContTranshipments = serviceContTranshipmentFacade.findServiceContTranshipmentsConfirmServed(no_ppkb);
        changeStatusHistorys = changeStatusHistoryFacade.findListTranshipment(no_ppkb);
        FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.delete.succeeded");
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

    private ServiceContDischargeFacadeRemote lookupServiceContDischargeFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (ServiceContDischargeFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/ServiceContDischargeFacade!com.pelindo.ebtos.ejb.facade.remote.ServiceContDischargeFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private ChangeStatusHistoryFacadeRemote lookupChangeStatusHistoryFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (ChangeStatusHistoryFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/ChangeStatusHistoryFacade!com.pelindo.ebtos.ejb.facade.remote.ChangeStatusHistoryFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private MasterPortFacadeRemote lookupMasterPortFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (MasterPortFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/MasterPortFacade!com.pelindo.ebtos.ejb.facade.remote.MasterPortFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private MasterYardFacadeRemote lookupMasterYardFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (MasterYardFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/MasterYardFacade!com.pelindo.ebtos.ejb.facade.remote.MasterYardFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private MasterContainerTypeFacadeRemote lookupMasterContainerTypeFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (MasterContainerTypeFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/MasterContainerTypeFacade!com.pelindo.ebtos.ejb.facade.remote.MasterContainerTypeFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private MasterCommodityFacadeRemote lookupMasterCommodityFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (MasterCommodityFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/MasterCommodityFacade!com.pelindo.ebtos.ejb.facade.remote.MasterCommodityFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private ServiceContTranshipmentFacadeRemote lookupServiceContTranshipmentFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (ServiceContTranshipmentFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/ServiceContTranshipmentFacade!com.pelindo.ebtos.ejb.facade.remote.ServiceContTranshipmentFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
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

    /**
     * @return the serviceVessel
     */
    public Object[] getServiceVessel() {
        return serviceVessel;
    }

    /**
     * @param serviceVessel the serviceVessel to set
     */
    public void setServiceVessel(Object[] serviceVessel) {
        this.serviceVessel = serviceVessel;
    }

    /**
     * @return the serviceContDischarge
     */
    public List<Object[]> getChangeStatusHistorys() {
        return changeStatusHistorys;
    }

    /**
     * @param serviceContDischarge the serviceContDischarge to set
     */
    public void setChangeStatusHistorys(List<Object[]> changeStatusHistorys) {
        this.changeStatusHistorys = changeStatusHistorys;
    }

    /**
     * @return the serviceContDischargeList
     */
    public List<Object[]> getServiceContTranshipments() {
        return serviceContTranshipments;
    }

    /**
     * @param serviceContDischargeList the serviceContDischargeList to set
     */
    public void setServiceContTranshipments(List<Object[]> serviceContTranshipments) {
        this.serviceContTranshipments = serviceContTranshipments;
    }

    /**
     * @return the serviceContDischargeList
     */
    public ServiceContTranshipment getServiceContTranshipment() {
        return serviceContTranshipment;
    }

    /**
     * @param serviceContDischargeList the serviceContDischargeList to set
     */
    public void setServiceContTranshipment(ServiceContTranshipment serviceContTranshipment) {
        this.serviceContTranshipment = serviceContTranshipment;
    }

    /**
     * @return the masterPorts
     */
    public List<Object[]> getMasterPorts() {
        return masterPorts;
    }

    /**
     * @param masterPorts the masterPorts to set
     */
    public void setMasterPorts(List<Object[]> masterPorts) {
        this.masterPorts = masterPorts;
    }

}
