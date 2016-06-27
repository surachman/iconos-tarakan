/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.bean;

import com.qtasnim.jsf.FacesHelper;
import com.pelindo.ebtos.ejb.facade.remote.CancelLoadingServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterYardCoordinatFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningContLoadFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceReceivingFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceVesselFacadeRemote;
import com.pelindo.ebtos.model.db.CancelLoadingService;
import com.pelindo.ebtos.model.db.PlanningContLoad;
import com.pelindo.ebtos.model.db.PlanningVessel;
import com.pelindo.ebtos.model.db.ServiceReceiving;
import com.pelindo.ebtos.model.db.master.MasterCommodity;
import com.pelindo.ebtos.model.db.master.MasterContainerType;
import com.pelindo.ebtos.model.db.master.MasterYard;
import com.pelindo.ebtos.model.db.master.MasterYardCoordinat;
import com.pelindo.ebtos.util.GrossConverter;
import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

/**
 *
 * @author dycoder
 */
@ManagedBean(name = "changeOfDestinationBean")
@ViewScoped
public class ChangeOfDestinationBean implements Serializable {
    @EJB
    private PlanningContLoadFacadeRemote planningContLoadFacadeRemote;
    @EJB
    private MasterYardCoordinatFacadeRemote masterYardCoordinatFacadeRemote;
    @EJB
    private ServiceVesselFacadeRemote serviceVesselFacadeRemote;
    @EJB
    private CancelLoadingServiceFacadeRemote cancelLoadingServiceFacadeRemote;
    @EJB
    private ServiceReceivingFacadeRemote serviceReceivingFacadeRemote;

    private List<Object[]> serviceVesselsDisch;
    private List<Object[]> serviceVesselsLoad;
    private Object[] serviceVessel;
    private Object[] serviceVesselLoad;
    private String no_ppkb;
    private String new_no_ppkb;
    private List<Object[]> serviceReceivingList;
    private List<Object[]> serviceCancelLoadings;
    private Object[][] cancelLoadingObjcetArray;
    private CancelLoadingService cancelLoadingService;
    private ServiceReceiving serviceReceiving;
    private PlanningContLoad planningContLoad;

    /** Creates a new instance of ChangeOfDestinationBean */
    public ChangeOfDestinationBean() {}

    @PostConstruct
    private void construct() {
        cancelLoadingService = new CancelLoadingService();
        serviceReceiving = new ServiceReceiving();
        serviceReceiving.setMasterCommodity(new MasterCommodity());
        serviceReceiving.setMasterContainerType(new MasterContainerType());
        serviceReceiving.setMasterYard(new MasterYard());
        serviceReceiving.setPlanningVessel(new PlanningVessel());

        planningContLoad = new PlanningContLoad();
        planningContLoad.setMasterCommodity(new MasterCommodity());
        planningContLoad.setMasterContainerType(new MasterContainerType());
        planningContLoad.setMasterYard(new MasterYard());
        planningContLoad.setPlanningVessel(new PlanningVessel());

        serviceVesselsDisch = serviceVesselFacadeRemote.findServiceVesselsFromChange();
        serviceVesselsLoad = serviceVesselFacadeRemote.findServiceVesselsToChange();
    }

    public void handleSelectNoPpkb(ActionEvent event) {
        no_ppkb = (String) event.getComponent().getAttributes().get("noPpkb");
        serviceVessel = serviceVesselFacadeRemote.findServiceVesselByPpkb(no_ppkb);
        serviceCancelLoadings = cancelLoadingServiceFacadeRemote.findByChangeDestination(no_ppkb);
    }

    public void handleSelectNoPpkbLoad(ActionEvent event) {
        new_no_ppkb = (String) event.getComponent().getAttributes().get("noPpkb");
        serviceVesselLoad = serviceVesselFacadeRemote.findServiceVesselByPpkb(new_no_ppkb);
        serviceReceivingList = serviceReceivingFacadeRemote.findServiceReceivingsChangeDestination(new_no_ppkb);
    }

    public void handleSelectDeleteCont(ActionEvent event) {
        Integer id_cont = (Integer) event.getComponent().getAttributes().get("idCon");
        serviceReceiving = serviceReceivingFacadeRemote.find(id_cont);
        Object[] servi = null;
        servi = cancelLoadingServiceFacadeRemote.findCancelLoadingServiceByDestination(serviceReceiving.getOld_ppkb(), serviceReceiving.getContNo());
        cancelLoadingService = cancelLoadingServiceFacadeRemote.find(servi[0]);

        Integer pld = null;
        pld = planningContLoadFacadeRemote.findByContNoAndPpkbByEdit(serviceReceiving.getPlanningVessel().getNoPpkb(), serviceReceiving.getContNo());
        planningContLoad = planningContLoadFacadeRemote.find(pld);
    }

    public void delete(ActionEvent event) {
        try {

            //UPDATE MASTER YARD CORDINAT
            List<Integer> yrdCor = masterYardCoordinatFacadeRemote.findIdByContNoUpdatePlugging(planningContLoad.getContNo(), serviceReceiving.getPlanningVessel().getNoPpkb());
            for (Integer ii : yrdCor) {
                MasterYardCoordinat masterYardCoordinat = new MasterYardCoordinat();
                masterYardCoordinat = masterYardCoordinatFacadeRemote.find(ii);
                masterYardCoordinat.setNoPpkb(serviceReceiving.getOld_ppkb());
                masterYardCoordinatFacadeRemote.edit(masterYardCoordinat);
            }

            serviceReceivingFacadeRemote.remove(serviceReceiving);
            cancelLoadingService.setChangeDestination("FALSE");
            cancelLoadingServiceFacadeRemote.edit(cancelLoadingService);
            planningContLoadFacadeRemote.remove(planningContLoad);
            serviceReceivingList = serviceReceivingFacadeRemote.findServiceReceivingsChangeDestination(new_no_ppkb);
            serviceCancelLoadings = cancelLoadingServiceFacadeRemote.findByChangeDestination(no_ppkb);
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.delete.succeeded");
        } catch (EJBException e) {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.delete.failed");
        }

    }

    public void handleSubmit(ActionEvent event) {
        try {
            if (no_ppkb.equals(new_no_ppkb)) {
                FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "application.save.failed");
            } else if (no_ppkb.isEmpty() || new_no_ppkb.isEmpty()) {
                FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "application.save.failed");
            } else {
                for (int i = 0; i < cancelLoadingObjcetArray.length; i++) {
                    cancelLoadingService = cancelLoadingServiceFacadeRemote.find(cancelLoadingObjcetArray[i][0]);

                    String grossCla = null;
                    grossCla = GrossConverter.convert(cancelLoadingService.getContSize(), cancelLoadingService.getContGross());

                    serviceReceiving.setContNo(cancelLoadingService.getContNo());
                    serviceReceiving.setMlo(cancelLoadingService.getMlo());
                    serviceReceiving.setContGross(cancelLoadingService.getContGross());
                    serviceReceiving.setContSize(cancelLoadingService.getContSize());
                    serviceReceiving.setContStatus(cancelLoadingService.getContStatus());
                    serviceReceiving.setDangerous(cancelLoadingService.getDg());
                    serviceReceiving.setDgLabel(cancelLoadingService.getDgLabel());
                    serviceReceiving.setMasterCommodity(cancelLoadingService.getMasterCommodity());
                    serviceReceiving.setMasterContainerType(cancelLoadingService.getMasterContainerType());
                    serviceReceiving.getPlanningVessel().setNoPpkb(new_no_ppkb);
                    serviceReceiving.setOverSize(cancelLoadingService.getOverSize());
                    serviceReceiving.setSealNo(cancelLoadingService.getSealNo());
                    serviceReceiving.setGrossClass(grossCla);
                    serviceReceiving.setTransactionDate(Calendar.getInstance().getTime());
                    serviceReceiving.setIsLifton("FALSE");
                    serviceReceiving.setIsBehandle("FALSE");
                    serviceReceiving.setIsCfs("FALSE");
                    serviceReceiving.setIsInspection("FALSE");
                    serviceReceiving.setBlNo(cancelLoadingService.getBlNo());
                    serviceReceiving.setChangeStatus("FALSE");
                    serviceReceiving.setIsChangeDestination("TRUE");
                    serviceReceiving.getMasterYard().setBlock(cancelLoadingService.getMasterYard().getBlock());
                    serviceReceiving.setYSlot(cancelLoadingService.getySlot());
                    serviceReceiving.setYRow(cancelLoadingService.getyRow());
                    serviceReceiving.setYTier(cancelLoadingService.getyTier());
                    serviceReceiving.setOld_ppkb(no_ppkb);

                    //UPDATE INPUT DATA K TABEL PLANNING CONT LOAD
                    planningContLoad.getMasterCommodity().setCommodityCode(serviceReceiving.getMasterCommodity().getCommodityCode());
                    planningContLoad.getMasterYard().setBlock(serviceReceiving.getMasterYard().getBlock());
                    planningContLoad.getMasterContainerType().setContType(serviceReceiving.getMasterContainerType().getContType());
                    planningContLoad.getPlanningVessel().setNoPpkb(serviceReceiving.getPlanningVessel().getNoPpkb());
                    planningContLoad.setContNo(serviceReceiving.getContNo());
                    planningContLoad.setMlo(serviceReceiving.getMlo());
                    planningContLoad.setContSize(serviceReceiving.getContSize());
                    planningContLoad.setContStatus(serviceReceiving.getContStatus());
                    planningContLoad.setIsTranshipment("FALSE");
                    planningContLoad.setPosition("03");
                    planningContLoad.setContGross(serviceReceiving.getContGross());
                    planningContLoad.setSealNo(serviceReceiving.getSealNo());
                    planningContLoad.setOverSize(serviceReceiving.getOverSize());
                    planningContLoad.setDgLabel(serviceReceiving.getDgLabel());
                    planningContLoad.setDg(serviceReceiving.getDangerous());
                    planningContLoad.setYSlot(serviceReceiving.getYSlot());
                    planningContLoad.setYRow(serviceReceiving.getYRow());
                    planningContLoad.setYTier(serviceReceiving.getYTier());
                    planningContLoad.setCompleted("FALSE");
                    planningContLoad.setUnknown("FALSE");
                    planningContLoad.setBlNo(serviceReceiving.getBlNo());
                    //lookupPlanningContLoadFacadeRemote().create(planningContLoad);

                    //UPDATE CANCEL LOADING
                    cancelLoadingService.setChangeDestination("TRUE");

                    //UPDATE MASTER YARD CORDINAT
                    List<Integer> yrdCor = masterYardCoordinatFacadeRemote.findIdByContNoUpdatePlugging(planningContLoad.getContNo(), no_ppkb);
                    for (Integer ii : yrdCor) {
                        MasterYardCoordinat masterYardCoordinat = new MasterYardCoordinat();
                        masterYardCoordinat = masterYardCoordinatFacadeRemote.find(ii);
                        masterYardCoordinat.setNoPpkb(new_no_ppkb);
                        masterYardCoordinatFacadeRemote.edit(masterYardCoordinat);
                    }

                    planningContLoadFacadeRemote.create(planningContLoad);
                    serviceReceivingFacadeRemote.create(serviceReceiving);
                    cancelLoadingServiceFacadeRemote.edit(cancelLoadingService);
                }
                serviceReceivingList = serviceReceivingFacadeRemote.findServiceReceivingsChangeDestination(new_no_ppkb);
                serviceCancelLoadings = cancelLoadingServiceFacadeRemote.findByChangeDestination(no_ppkb);
                FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "application.save.succeeded");
            }
        } catch (EJBException e) {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "application.save.failed");
        }
    }

    public String getNew_no_ppkb() {
        return new_no_ppkb;
    }

    public void setNew_no_ppkb(String new_no_ppkb) {
        this.new_no_ppkb = new_no_ppkb;
    }

    public String getNo_ppkb() {
        return no_ppkb;
    }

    public void setNo_ppkb(String no_ppkb) {
        this.no_ppkb = no_ppkb;
    }

    public Object[] getServiceVessel() {
        return serviceVessel;
    }

    public void setServiceVessel(Object[] serviceVessel) {
        this.serviceVessel = serviceVessel;
    }

    public Object[] getServiceVesselLoad() {
        return serviceVesselLoad;
    }

    public void setServiceVesselLoad(Object[] serviceVesselLoad) {
        this.serviceVesselLoad = serviceVesselLoad;
    }

    public List<Object[]> getServiceVesselsDisch() {
        return serviceVesselsDisch;
    }

    public void setServiceVesselsDisch(List<Object[]> serviceVesselsDisch) {
        this.serviceVesselsDisch = serviceVesselsDisch;
    }

    public List<Object[]> getServiceVesselsLoad() {
        return serviceVesselsLoad;
    }

    public void setServiceVesselsLoad(List<Object[]> serviceVesselsLoad) {
        this.serviceVesselsLoad = serviceVesselsLoad;
    }

    public List<Object[]> getServiceCancelLoadings() {
        return serviceCancelLoadings;
    }

    public void setServiceCancelLoadings(List<Object[]> serviceCancelLoadings) {
        this.serviceCancelLoadings = serviceCancelLoadings;
    }

    public List<Object[]> getServiceReceivingList() {
        return serviceReceivingList;
    }

    public void setServiceReceivingList(List<Object[]> serviceReceivingList) {
        this.serviceReceivingList = serviceReceivingList;
    }

    public Object[][] getCancelLoadingObjcetArray() {
        return cancelLoadingObjcetArray;
    }

    public void setCancelLoadingObjcetArray(Object[][] cancelLoadingObjcetArray) {
        this.cancelLoadingObjcetArray = cancelLoadingObjcetArray;
    }

    public CancelLoadingService getCancelLoadingService() {
        return cancelLoadingService;
    }

    public void setCancelLoadingService(CancelLoadingService cancelLoadingService) {
        this.cancelLoadingService = cancelLoadingService;
    }

    public ServiceReceiving getServiceReceiving() {
        return serviceReceiving;
    }

    public void setServiceReceiving(ServiceReceiving serviceReceiving) {
        this.serviceReceiving = serviceReceiving;
    }

    public PlanningContLoad getPlanningContLoad() {
        return planningContLoad;
    }

    public void setPlanningContLoad(PlanningContLoad planningContLoad) {
        this.planningContLoad = planningContLoad;
    }
}
