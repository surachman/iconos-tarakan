/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.bean;

import com.qtasnim.jsf.FacesHelper;
import com.pelindo.ebtos.ejb.facade.remote.CancelLoadingServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.DeliveryServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterContDamageFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterContainerTypeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterVehicleFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceGateDeliveryFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceContDischargeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterYardCoordinatFacadeRemote;
import com.pelindo.ebtos.model.db.CancelLoadingService;
import com.pelindo.ebtos.model.db.DeliveryService;
import com.pelindo.ebtos.model.db.ServiceGateDelivery;
import com.pelindo.ebtos.model.db.ServiceContDischarge;
import com.pelindo.ebtos.model.db.master.MasterContDamage;
import com.pelindo.ebtos.model.db.master.MasterContainerType;
import com.pelindo.ebtos.model.db.master.MasterCustomer;
import com.pelindo.ebtos.model.db.master.MasterVehicle;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import org.primefaces.context.RequestContext;

/**
 *
 * @author dycoder
 */
@ManagedBean(name = "gateOutDeliveryBean")
@ViewScoped
public class GateOutDeliveryBean implements Serializable {
    private static final String SERVICE_TYPE_CANCEL_LOADING = "CANCEL_LOADING";
    private static final String SERVICE_TYPE_DISCHARGE = "DISCHARGE";
    
    @EJB
    private ServiceGateDeliveryFacadeRemote serviceGateDeliveryFacadeRemote;
    @EJB
    private ServiceContDischargeFacadeRemote serviceContDischargeFacadeRemote;
    @EJB
    private MasterVehicleFacadeRemote masterVehicleFacadeRemote;
    @EJB
    private MasterContainerTypeFacadeRemote masterContainerTypeFacadeRemote;
    @EJB
    private DeliveryServiceFacadeRemote deliveryServiceFacade;
    @EJB
    private CancelLoadingServiceFacadeRemote cancelLoadingServiceFacadeRemote;
    @EJB
    private MasterContDamageFacadeRemote masterContDamageFacadeRemote;
    @EJB
    private MasterYardCoordinatFacadeRemote masterYardCoordinatFacadeRemote;

    private List<MasterContDamage> masterContDamages;
    private List<MasterVehicle> vehicles;
    private List<Object[]> serviceGateDeliveryList;
    private CancelLoadingService cancelLoadingService;
    private DeliveryService deliveryService;
    private MasterVehicle masterVehicle;
    private MasterContainerType masterContainerType;
    private MasterContDamage masterContDamage;
    private ServiceGateDelivery serviceGateDelivery;
    private ServiceContDischarge serviceContDischarge;
    private Object[] serviceGateOutDelivery;
    private Integer cont_no;
    private String contStatus;
    private String contType;
    private String jobSlip;
    private String contNo;
    private String serviceType;
    private Short tonage;

    String contNumber = "1";
    boolean isSecond = false;

    Integer weightTemp;

    /** Creates a new instance of GateOutDeliveryBean */
    public GateOutDeliveryBean() {}

    @PostConstruct
    private void construct(){
        serviceGateDelivery = new ServiceGateDelivery();
        masterContDamage = new MasterContDamage();
        serviceGateDelivery.setMasterVehicle(new MasterVehicle());
        serviceGateDelivery.setMasterContDamage(new MasterContDamage());
        cancelLoadingService=new CancelLoadingService();
        deliveryService=new DeliveryService();
        serviceContDischarge = new ServiceContDischarge();

        serviceGateDeliveryList = serviceGateDeliveryFacadeRemote.findServiceGateDeliveryDateOutNull();
        masterContDamages = masterContDamageFacadeRemote.findAll();
        contNumber = "1";
        isSecond = false;
    }

    public void clearSave() {
        if (contNumber.trim().equals("2")) {
//            serviceGateDelivery = new ServiceGateDelivery();
//            masterContDamage = new MasterContDamage();
//            serviceGateDelivery.setMasterVehicle(new MasterVehicle());
//            serviceGateDelivery.setMasterContDamage(new MasterContDamage());
            cancelLoadingService = new CancelLoadingService();
//            deliveryService = new DeliveryService();
            serviceGateDelivery.setJobSlip(null);
            serviceGateDelivery.setContNo(null);
            serviceGateDelivery.setMlo(new MasterCustomer());
            setContType(null);
            setCont_status(null);
            serviceGateDelivery.setSealNo(null);
//            serviceGateDelivery.setMasterVehicle(new MasterVehicle());
//            contNumber = "2";
            isSecond = true;
            serviceGateDelivery.setWeight(weightTemp);
        } else {
            serviceGateDelivery = new ServiceGateDelivery();
            masterContDamage = new MasterContDamage();
            serviceGateDelivery.setMasterVehicle(new MasterVehicle());
            serviceGateDelivery.setMasterContDamage(new MasterContDamage());
            cancelLoadingService = new CancelLoadingService();
            deliveryService = new DeliveryService();
//            contNumber = "1";
            isSecond = false;
        }
    }

    public void onChangeContCount(ValueChangeEvent event) {
        String ct = (String) event.getNewValue();
        System.out.println("ct : " + ct);
        contNumber = ct;
        if(contNumber.trim().equals("2")){
            isSecond = true;
        }else {
            isSecond = false;
        }
        System.out.println("contNumber : " + contNumber);
    }

    public List<String> setListAutoComplete(String query) {
        List<String> results = serviceGateDeliveryFacadeRemote.findGateOutPassableJobslips(query);
        return results;
    }
    
    public List<String> setListAutoComplete2(String query) {
        List<String> results = serviceGateDeliveryFacadeRemote.findGateOutPassableJobslips2(query);
        return results;
    }

    public void ambilContNo(ActionEvent event) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        
        try {
            jobSlip = (String) event.getComponent().getAttributes().get("jobSlip");
            serviceGateOutDelivery = serviceGateDeliveryFacadeRemote.findGateOutPassableByJobSlip(jobSlip);

            if (serviceGateOutDelivery != null) {
                serviceGateDelivery = serviceGateDeliveryFacadeRemote.find(serviceGateOutDelivery[0]);
                serviceType = (String) serviceGateOutDelivery[6];

                if (serviceType.equals(SERVICE_TYPE_DISCHARGE)) {
                    deliveryService = deliveryServiceFacade.find(serviceGateDelivery.getJobSlip());
                    contStatus = deliveryService.getContStatus();

                    if (deliveryService.getValidDate().compareTo(new Date()) < 0) {
                        FacesHelper.addFacesTextMessage(facesContext, FacesMessage.SEVERITY_WARN, "Warning", String.format(FacesHelper.getLocaleMessage("application.validation.time_expired", facesContext), "Job Slip"), null);
                        clearSave();
                        contType = null;
                        tonage = null;
                        contStatus=null;
                        return;
                    }
                } else if (serviceType.equals(SERVICE_TYPE_CANCEL_LOADING)) {
                    cancelLoadingService = cancelLoadingServiceFacadeRemote.find(serviceGateOutDelivery[1]);
                    contStatus = cancelLoadingService.getContStatus();
                } else {
                    throw new RuntimeException(String.format("Unknown serviceType [serviceType=%s]", serviceType));
                }
                    serviceGateDelivery.setWeight(weightTemp);
                    System.out.println("getWeight : " + serviceGateDelivery.getWeight());
                masterVehicle = serviceGateDelivery.getMasterVehicle();
                tonage = masterVehicle.getTonage();
                masterContainerType = masterContainerTypeFacadeRemote.find(serviceGateDelivery.getCont_type());
                contType = masterContainerType.getIsoCode();
                serviceGateDelivery.setMasterContDamage(new MasterContDamage());
                FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_INFO, "Info", "application.search.found");
            } else {
                FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_WARN, "Warning", "application.search.notfound");
                clearSave();
                contType = null;
                tonage = null;
                contStatus=null;
            }
        } catch (RuntimeException re) {
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_ERROR, "Error", "application.loading.error");
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", re);
        }
    }
    
    //penambahan function untuk perubahan pencarian dari jobslip ke nomor container by ade chelsea tanggal 21-03-2014
    public void ambilContNo2(ActionEvent event) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        
        try {
            contNo = (String) event.getComponent().getAttributes().get("contNo");
            serviceGateOutDelivery = serviceGateDeliveryFacadeRemote.findGateOutPassableByJobSlip2(contNo);

            if (serviceGateOutDelivery != null) {
                serviceGateDelivery = serviceGateDeliveryFacadeRemote.find(serviceGateOutDelivery[0]);
                serviceType = (String) serviceGateOutDelivery[6];

                if (serviceType.equals(SERVICE_TYPE_DISCHARGE)) {
                    deliveryService = deliveryServiceFacade.findDataDelivery(serviceGateDelivery.getContNo());
                    contStatus = deliveryService.getContStatus();

                    if (deliveryService.getValidDate().compareTo(new Date()) < 0) {
                        FacesHelper.addFacesTextMessage(facesContext, FacesMessage.SEVERITY_WARN, "Warning", String.format(FacesHelper.getLocaleMessage("application.validation.time_expired", facesContext), "Container No"), null);
                        clearSave();
                        contType = null;
                        tonage = null;
                        contStatus=null;
                        return;
                    }
                } else if (serviceType.equals(SERVICE_TYPE_CANCEL_LOADING)) {
                    cancelLoadingService = cancelLoadingServiceFacadeRemote.find(serviceGateOutDelivery[1]);
                    contStatus = cancelLoadingService.getContStatus();
                } else {
                    throw new RuntimeException(String.format("Unknown serviceType [serviceType=%s]", serviceType));
                }
                    serviceGateDelivery.setWeight(weightTemp);
                    System.out.println("getWeight : " + serviceGateDelivery.getWeight());
                masterVehicle = serviceGateDelivery.getMasterVehicle();
                tonage = masterVehicle.getTonage();
                masterContainerType = masterContainerTypeFacadeRemote.find(serviceGateDelivery.getCont_type());
                contType = masterContainerType.getIsoCode();
                serviceGateDelivery.setMasterContDamage(new MasterContDamage());
                FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_INFO, "Info", "application.search.found");
            } else {
                FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_WARN, "Warning", "application.search.notfound");
                clearSave();
                contType = null;
                tonage = null;
                contStatus=null;
            }
        } catch (RuntimeException re) {
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_ERROR, "Error", "application.loading.error");
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", re);
        }
    }

    public void handleSelectNoCont(ActionEvent event) {
        cont_no = (Integer) event.getComponent().getAttributes().get("cont_no");
        serviceGateDelivery = serviceGateDeliveryFacadeRemote.find(getCont_no());
        masterVehicle = masterVehicleFacadeRemote.find(serviceGateDelivery.getMasterVehicle().getVehicleCode());
        tonage = masterVehicle.getTonage();
        masterContainerType = masterContainerTypeFacadeRemote.find(serviceGateDelivery.getCont_type());
        contType = masterContainerType.getIsoCode();
        serviceGateDelivery.setMasterContDamage(new MasterContDamage());
    }

    public void saveEdit(ActionEvent actionEvent) {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        boolean isValid = false;

        try {
            //menutup case contWeight pada gate out karena tdk digunakan by ade chelsea tanggal 7 mei 2014 09:00 jayapura
//            if (serviceGateDelivery.getWeight() == null || serviceGateDelivery.getWeight() < 2000) {
//                isValid = false;
//                FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.validation.gate_gross");
//            } else if (serviceGateDelivery.getContWeight() == null || serviceGateDelivery.getContWeight() < 1000) {
//                isValid = false;
//                FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.validation.cont_weight");
//            } else {
                if (serviceType.equals(SERVICE_TYPE_DISCHARGE)) {
                    serviceContDischarge = serviceContDischargeFacadeRemote.findByNoPpkbAndContNo(serviceGateDelivery.getNoPpkb(), serviceGateDelivery.getContNo());
                    serviceContDischarge.setPosition(ServiceContDischarge.LOCATION_OUTSIDE);
                    serviceContDischargeFacadeRemote.edit(serviceContDischarge);
                    
                    //penambahan update master yard container ketika gate out by ade chelsea 11 april 2014
                    masterYardCoordinatFacadeRemote.updateMasterYardContainerGateOut(serviceGateDelivery.getNoPpkb(), serviceGateDelivery.getContNo());
                    
                } else if (serviceType.equals(SERVICE_TYPE_CANCEL_LOADING)) {
                    cancelLoadingService = cancelLoadingServiceFacadeRemote.find(serviceGateDelivery.getJobSlip());
                    cancelLoadingService.setPosisi(CancelLoadingService.AT_OUTSIDE);
                    cancelLoadingServiceFacadeRemote.edit(cancelLoadingService);
                } else {
                    throw new RuntimeException(String.format("Unknown serviceType [serviceType=%s]", serviceType));
                }

                serviceGateDelivery.setDateOut(new Date());
                serviceGateDeliveryFacadeRemote.edit(serviceGateDelivery);

                contType = null;
                System.out.println("isSecond : " + isSecond);
                if (isSecond) {
                    tonage = null;
                    contNumber = "1";
                }
                System.out.println("contNumber : " + contNumber);
                if(contNumber.equals("2")){
                    weightTemp = serviceGateDelivery.getWeight();
                }
                System.out.println("weightTemp : " + weightTemp);
                contStatus = null;
                clearSave();
                isValid = true;
                FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
            //}
        } catch (RuntimeException re) {
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_ERROR, "Error", "application.save.failed");
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", re);
        }

        requestContext.addCallbackParam("loggedIn", isValid);
    }

    public ServiceGateDelivery getServiceGateDelivery() {
        return serviceGateDelivery;
    }

    public void setServiceGateDelivery(ServiceGateDelivery serviceGateDelivery) {
        this.serviceGateDelivery = serviceGateDelivery;
    }

    public List<MasterVehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<MasterVehicle> vehicles) {
        this.vehicles = vehicles;
    }

    public List<Object[]> getServiceGateDeliveryList() {
        return serviceGateDeliveryList;
    }

    public void setServiceGateDeliveryList(List<Object[]> serviceGateDeliveryList) {
        this.serviceGateDeliveryList = serviceGateDeliveryList;
    }

    public Integer getCont_no() {
        return cont_no;
    }

    public void setCont_no(Integer cont_no) {
        this.cont_no = cont_no;
    }

    public MasterVehicle getMasterVehicle() {
        return masterVehicle;
    }

    public void setMasterVehicle(MasterVehicle masterVehicle) {
        this.masterVehicle = masterVehicle;
    }

    public Short getTonage() {
        return tonage;
    }

    public void setTonage(Short tonage) {
        this.tonage = tonage;
    }

    public String getContType() {
        return contType;
    }

    public void setContType(String contType) {
        this.contType = contType;
    }

    public MasterContainerType getMasterContainerType() {
        return masterContainerType;
    }

    public void setMasterContainerType(MasterContainerType masterContainerType) {
        this.masterContainerType = masterContainerType;
    }

    public List<MasterContDamage> getMasterContDamages() {
        return masterContDamages;
    }

    public void setMasterContDamages(List<MasterContDamage> masterContDamages) {
        this.masterContDamages = masterContDamages;
    }

    public MasterContDamage getMasterContDamage() {
        return masterContDamage;
    }

    public void setMasterContDamage(MasterContDamage masterContDamage) {
        this.masterContDamage = masterContDamage;
    }

    public Object[] getServiceGateOutDelivery() {
        return serviceGateOutDelivery;
    }

    public void setServiceGateOutDelivery(Object[] serviceGateOutDelivery) {
        this.serviceGateOutDelivery = serviceGateOutDelivery;
    }

    public String getJobSlip() {
        return jobSlip;
    }

    public void setJobSlip(String jobSlip) {
        this.jobSlip = jobSlip;
    }

    public CancelLoadingService getCancelLoadingService() {
        return cancelLoadingService;
    }

    public void setCancelLoadingService(CancelLoadingService cancelLoadingService) {
        this.cancelLoadingService = cancelLoadingService;
    }

    public DeliveryService getDeliveryService() {
        return deliveryService;
    }

    public void setDeliveryService(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    public String getContStatus() {
        return contStatus;
    }

    public void setCont_status(String contStatus) {
        this.contStatus = contStatus;
    }

    public ServiceContDischarge getServiceContDischarge() {
        return serviceContDischarge;
    }
    
    public void setServiceContDischarge(ServiceContDischarge serviceContDischarge) {
        this.serviceContDischarge = serviceContDischarge;
    }

    public String getContNumber() {
        return contNumber;
    }

    public void setContNumber(String contNumber) {
        this.contNumber = contNumber;
    }
    public boolean isIsSecond() {
        return isSecond;
    }

    public void setIsSecond(boolean isSecond) {
        this.isSecond = isSecond;
    }

    public String getContNo() {
        return contNo;
    }

    public void setContNo(String contNo) {
        this.contNo = contNo;
    }
    
    


}
