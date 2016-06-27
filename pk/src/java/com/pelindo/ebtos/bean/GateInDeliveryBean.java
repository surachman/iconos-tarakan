package com.pelindo.ebtos.bean;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import com.qtasnim.jsf.FacesHelper;
import com.qtasnim.url.UrlHelper;
import com.pelindo.ebtos.ejb.facade.remote.CancelLoadingServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.DeliveryServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterContainerTypeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterCustomerFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterVehicleFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningVesselFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PluggingReeferServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceContDischargeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceGateDeliveryFacadeRemote;
import com.pelindo.ebtos.model.db.CancelLoadingService;
import com.pelindo.ebtos.model.db.DeliveryService;
import com.pelindo.ebtos.model.db.PluggingReeferService;
import com.pelindo.ebtos.model.db.ServiceGateDelivery;
import com.pelindo.ebtos.model.db.master.MasterContainerType;
import com.pelindo.ebtos.model.db.master.MasterCustomer;
import com.pelindo.ebtos.model.db.master.MasterVehicle;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.apache.commons.lang3.StringEscapeUtils;
import org.primefaces.context.RequestContext;

/**
 *
 * @author dycoder
 */
@ManagedBean(name = "gateInDeliveryBean")
@ViewScoped
public class GateInDeliveryBean implements Serializable {
    
    @EJB
    private MasterVehicleFacadeRemote masterVehicleFacadeRemote;
    @EJB
    private DeliveryServiceFacadeRemote deliveryServiceFacadeRemote;
    @EJB
    private ServiceContDischargeFacadeRemote serviceContDischargeFacadeRemote;
    @EJB
    private MasterContainerTypeFacadeRemote masterContainerTypeFacadeRemote;
    @EJB
    private ServiceGateDeliveryFacadeRemote serviceGateDeliveryFacadeRemote;
    @EJB
    private PlanningVesselFacadeRemote planningVesselFacadeRemote;
    @EJB
    private MasterCustomerFacadeRemote masterCustomerFacadeRemote;
    @EJB
    private PluggingReeferServiceFacadeRemote pluggingReeferServiceFacadeRemote;
    @EJB
    private CancelLoadingServiceFacadeRemote cancelLoadingServiceFacadeRemote;

    private List<Object[]> deliveryGateIntList;
    private List<MasterVehicle> vehicles;
    private DeliveryService deliveryService;
    private ServiceGateDelivery serviceGateDelivery;
    private String contNo;
    private Object[] serviceContDischargeObject;
    private Object[] deliveryServiceObject;
    private Object[] cancelObject;
    private MasterContainerType masterContainerType;
    private CancelLoadingService cancelLoadingService;
    private Object[] serviceGateDeliveryObject;
    private String contType;
    private String blok = null;
    private Short slot;
    private Short row;
    private Short tier;
    private Object[] planningVessel;
    private String vesselName;
    private String voyyage;
    private String mode;
    private boolean visible;
    private String noPpkb;
    private boolean disableGrowl=Boolean.FALSE;
    private String contStatus;
    private PluggingReeferService pluggingReeferService;
    private String status="DELIVERY";

    /** Creates a new instance of GateInDeliveryBean */
    public GateInDeliveryBean() {}

    @PostConstruct
    private void construct() {
        serviceGateDelivery = new ServiceGateDelivery();
        serviceGateDelivery.setMasterVehicle(new MasterVehicle());
        serviceGateDelivery.getMasterVehicle();
        deliveryService = new DeliveryService();
        this.visible = Boolean.FALSE;
        deliveryService.setMasterContainerType(new MasterContainerType());
        //reeferDischargeService = new ReeferDischargeService();
        cancelLoadingService = new CancelLoadingService();
        this.noPpkb = null;
        pluggingReeferService=new PluggingReeferService();

        deliveryGateIntList = deliveryServiceFacadeRemote.findDeliveryServiceByValidateDate(Calendar.getInstance().getTime());
        vehicles = masterVehicleFacadeRemote.findAll();
    }

    public void handleLookupMasterVehicle(ActionEvent event) {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        FacesContext facesContext = FacesContext.getCurrentInstance();

        if (serviceGateDelivery.getMasterVehicle().getVehicleCode() != null) {
            MasterVehicle masterVehicle = masterVehicleFacadeRemote.find(serviceGateDelivery.getMasterVehicle().getVehicleCode());

            if (serviceGateDelivery.getMasterVehicle() != null) {
                serviceGateDelivery.setMasterVehicle(masterVehicle);
                requestContext.addCallbackParam("loggedIn", true);
                return;
            }

            requestContext.addCallbackParam("loggedIn", false);
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_WARN, "Warning", "application.validation.license_plate.not_registered");
            return;
        }

        serviceGateDelivery.setMasterVehicle(new MasterVehicle());
    }

    public void handleResetMasterVehicle(ActionEvent event) {
        serviceGateDelivery.setMasterVehicle(new MasterVehicle());
    }

    public List<String> setListAutoComplete(String query) {
        List<String> results = serviceGateDeliveryFacadeRemote.findGateInPassableJobSlips(query);
        return results;
    }

    public void ambilContNo(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        boolean loggedIn = false;
        disableGrowl = Boolean.FALSE;
        contNo = (String) event.getComponent().getAttributes().get("contNo");
        deliveryServiceObject = deliveryServiceFacadeRemote.findDeliveryServiceByClosingTime(contNo);
        serviceGateDeliveryObject = serviceGateDeliveryFacadeRemote.findServiceGateDeliveryDateOutByJobSlip(contNo);
        cancelObject = cancelLoadingServiceFacadeRemote.findGateInPassableByJobSlip(contNo);
        Object[] pluggg = pluggingReeferServiceFacadeRemote.findPluggingReeferServiceByGatePlugging(contNo);

        if (getDeliveryServiceObject() != null) {
            loggedIn = true;
            deliveryService = deliveryServiceFacadeRemote.findDataDelivery(getContNo());
            serviceGateDelivery.setContNo(deliveryService.getContNo());
            serviceGateDelivery.setMlo(deliveryService.getMlo());
            serviceGateDelivery.setJobSlip(deliveryService.getJobSlip());
            serviceGateDelivery.setCont_type(deliveryService.getMasterContainerType().getContType());
            masterContainerType = masterContainerTypeFacadeRemote.find(serviceGateDelivery.getCont_type());
            contType = masterContainerType.getIsoCode();
            serviceGateDelivery.setNoPpkb(deliveryService.getPlanningVessel().getNoPpkb());
            serviceGateDelivery.setContGross(deliveryService.getContGross());
            serviceGateDelivery.setSealNo(deliveryService.getSealNo());
            serviceGateDelivery.setBlNo(deliveryService.getBlNo());
            contStatus=deliveryService.getContStatus();
            this.visible = Boolean.FALSE;
            this.status="DELIVERY";
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.search.found");
        } else if (cancelObject != null) {
            loggedIn = true;
            MasterCustomer mlo = masterCustomerFacadeRemote.find(cancelObject[17]);

            serviceGateDelivery.setContNo((String) cancelObject[2]);
            serviceGateDelivery.setMlo(mlo);
            serviceGateDelivery.setJobSlip((String) cancelObject[0]);
            serviceGateDelivery.setCont_type((Integer) cancelObject[15]);
            masterContainerType = masterContainerTypeFacadeRemote.find((Integer) cancelObject[15]);
            contType = masterContainerType.getIsoCode();
            serviceGateDelivery.setNoPpkb((String) cancelObject[16]);
            serviceGateDelivery.setContGross((Integer) cancelObject[4]);
            serviceGateDelivery.setSealNo((String) cancelObject[6]);
            serviceGateDelivery.setBlNo((String) cancelObject[1]);
            contStatus = (String) cancelObject[8];
            visible = Boolean.FALSE;
            status="DELIVERY";
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.search.found");
        } else  if (pluggg != null) {
            loggedIn = true;
            pluggingReeferService=pluggingReeferServiceFacadeRemote.find(contNo);
            serviceGateDelivery.setContNo(pluggingReeferService.getContNo());
            serviceGateDelivery.setMlo(pluggingReeferService.getMlo());
            serviceGateDelivery.setJobSlip(pluggingReeferService.getJobSlip());
            serviceGateDelivery.setCont_type(pluggingReeferService.getMasterContainerType().getContType());
            masterContainerType = masterContainerTypeFacadeRemote.find(serviceGateDelivery.getCont_type());
            setCont_type(masterContainerType.getIsoCode());
            serviceGateDelivery.setNoPpkb(pluggingReeferService.getNoPpkb());
            serviceGateDelivery.setContGross(pluggingReeferService.getContGross());
            //serviceGateDelivery.setSealNo(pluggingReeferService.getSealNo());
            serviceGateDelivery.setBlNo(pluggingReeferService.getBlNo());
            contStatus=pluggingReeferService.getContStatus();
            this.visible = Boolean.FALSE;
            this.status="PLUGG";
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.search.found");
        } else if (serviceGateDeliveryObject != null) {
            loggedIn = true;
            serviceGateDelivery = serviceGateDeliveryFacadeRemote.find(serviceGateDeliveryObject[0]);
            masterContainerType = masterContainerTypeFacadeRemote.find(serviceGateDelivery.getCont_type());
            contType = masterContainerType.getIsoCode();
            contNo = serviceGateDelivery.getContNo();
            noPpkb = serviceGateDelivery.getNoPpkb();
            serviceContDischargeObject = serviceContDischargeFacadeRemote.findServiceContDischargesByContAndPpkb(serviceGateDelivery.getNoPpkb(), serviceGateDelivery.getContNo());
            cancelLoadingService = cancelLoadingServiceFacadeRemote.find(serviceGateDelivery.getJobSlip());
            
            if (serviceContDischargeObject != null) {
                setBlok((String) serviceContDischargeObject[2]);
                setSlot((Short.parseShort(serviceContDischargeObject[3].toString())));
                setRow((Short.parseShort(serviceContDischargeObject[4].toString())));
                setTier((Short.parseShort(serviceContDischargeObject[5].toString())));
                setPlanningVessel(planningVesselFacadeRemote.findPlanningVesselByPpkb(serviceGateDelivery.getNoPpkb()));
                setVesselName((String) getPlanningVessel()[1]);
                setVoyyage((String) getPlanningVessel()[2]);

                this.visible = Boolean.TRUE;
            } else if (cancelLoadingService != null) {
                setBlok(cancelLoadingService.getMasterYard().getBlock());
                setSlot(cancelLoadingService.getySlot());
                setRow(cancelLoadingService.getyRow());
                setTier(cancelLoadingService.getyTier());
                setPlanningVessel(planningVesselFacadeRemote.findPlanningVesselByPpkb(serviceGateDelivery.getNoPpkb()));
                setVesselName((String) getPlanningVessel()[1]);
                setVoyyage((String) getPlanningVessel()[2]);

                this.visible = Boolean.TRUE;
            }
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.search.found");
            this.Clear2();
        } else {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Warning", "application.search.notfound_jobslip");
            serviceGateDelivery = new ServiceGateDelivery();
            serviceGateDelivery.setMasterVehicle(new MasterVehicle());
            this.contType = null;
            this.visible = Boolean.TRUE;
            this.disableGrowl=Boolean.TRUE;
            this.Clear2();
        }
        context.addCallbackParam("loggedIn", loggedIn);
        context.addCallbackParam("tipe", "deliver");
    }

    public void handleSelectNoCont(ActionEvent event) {
        contNo = (String) event.getComponent().getAttributes().get("cont_no");
        deliveryService = deliveryServiceFacadeRemote.find(getContNo());
        serviceGateDelivery.setContNo(deliveryService.getContNo());
        serviceGateDelivery.setMlo(deliveryService.getMlo());
        serviceGateDelivery.setJobSlip(deliveryService.getJobSlip());
        serviceGateDelivery.setCont_type(deliveryService.getMasterContainerType().getContType());
        masterContainerType = masterContainerTypeFacadeRemote.find(serviceGateDelivery.getCont_type());
        contType = masterContainerType.getIsoCode();
        serviceGateDelivery.setNoPpkb(deliveryService.getPlanningVessel().getNoPpkb());
        serviceGateDelivery.setContGross(deliveryService.getContGross());
        serviceGateDelivery.setSealNo(deliveryService.getSealNo());
    }

    public void saveEdit(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        Boolean loggedIn = false;
        Date tgl = Calendar.getInstance().getTime();

        if (serviceGateDelivery.getContNo() != null) {
            cancelLoadingService = cancelLoadingServiceFacadeRemote.findByNoPpkbContNoAndStatusDelivery(serviceGateDelivery.getNoPpkb(), serviceGateDelivery.getContNo(), false);
            serviceContDischargeObject = serviceContDischargeFacadeRemote.findServiceContDischargesByContAndPpkb(serviceGateDelivery.getNoPpkb(), serviceGateDelivery.getContNo());

            serviceGateDelivery.setDateIn(tgl);
            serviceGateDelivery.setStatus("N");
            serviceGateDeliveryFacadeRemote.create(serviceGateDelivery);

            contNo = serviceGateDelivery.getContNo();
            noPpkb = serviceGateDelivery.getNoPpkb();

            if (serviceContDischargeObject != null) {
                blok = (String) serviceContDischargeObject[2];
                slot = (Short.parseShort(serviceContDischargeObject[3].toString()));
                row = (Short.parseShort(serviceContDischargeObject[4].toString()));
                tier = (Short.parseShort(serviceContDischargeObject[5].toString()));
                planningVessel = planningVesselFacadeRemote.findPlanningVesselByPpkb(serviceGateDelivery.getNoPpkb());
                vesselName = (String) getPlanningVessel()[1];
                voyyage = (String) getPlanningVessel()[2];
                noPpkb = serviceGateDelivery.getNoPpkb();
                status = "DELIVERY";
            } else if (cancelLoadingService != null) {
                planningVessel = planningVesselFacadeRemote.findPlanningVesselByPpkb(serviceGateDelivery.getNoPpkb());
                blok = cancelLoadingService.getMasterYard().getBlock();
                slot = cancelLoadingService.getySlot();
                row = cancelLoadingService.getyRow();
                tier = cancelLoadingService.getyTier();
                vesselName = (String) planningVessel[1];
                voyyage = (String) planningVessel[2];
                visible = Boolean.TRUE;
                status = "DELIVERY";
            } else{
                blok = "-";
                slot = 0;
                row = 0;
                tier = 0;
                vesselName = "-";
                voyyage = "-";
                visible = Boolean.TRUE;
                status = "PLUGG";
            }

            deliveryGateIntList = deliveryServiceFacadeRemote.findDeliveryServiceByValidateDate(Calendar.getInstance().getTime());
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
            loggedIn = true;
        }
//        FacesContext.getCurrentInstance().addMessage(null, msg);
        context.addCallbackParam("loggedIn", loggedIn);
        this.Clear2();
    }

    public void Clear2() {
        serviceGateDelivery = new ServiceGateDelivery();
        serviceGateDelivery.setMasterVehicle(new MasterVehicle());
        cancelLoadingService = new CancelLoadingService();
        this.contType = null;
        this.contStatus=null;

    }

    public void clear() {
        serviceGateDelivery = new ServiceGateDelivery();
        serviceGateDelivery.setMasterVehicle(new MasterVehicle());
        //reeferDischargeService = new ReeferDischargeService();
        cancelLoadingService = new CancelLoadingService();
        pluggingReeferService=new PluggingReeferService();
        deliveryService = new DeliveryService();
        this.visible = Boolean.FALSE;
        deliveryService.setMasterContainerType(new MasterContainerType());
        this.vesselName = null;
        this.voyyage = null;
        this.contType = null;
        this.blok = null;
        this.row = null;
        this.slot = null;
        this.tier = null;
        this.disableGrowl=Boolean.FALSE;
        this.contStatus=null;
    }

    public void handleClearr(ActionEvent event) {
        // this.clear();
        serviceGateDelivery = new ServiceGateDelivery();
        serviceGateDelivery.setMasterVehicle(new MasterVehicle());
        this.contType = null;

    }

    public void handleDownload(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesContext fc = FacesContext.getCurrentInstance();
        context.addCallbackParam("doPrint", true); //harus ada, buat ngeflag di izinkan ngeprint ato ngga
        context.addCallbackParam("target", (String) event.getComponent().getAttributes().get("target"));
        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3(UrlHelper.resolveFullAddress(fc,"/gateInDeliveryReport.pdf?mode=" + "delivery" + "&no_ppkb=" + noPpkb + "&block=" + getBlok() + "&slot=" + getSlot() + "&row=" + getRow() + "&tier=" + getTier() + "&cont_no=" + contNo + "&status=" + status)));
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the deliveryGateIntList
     */
    public List<Object[]> getDeliveryGateIntList() {
        return deliveryGateIntList;
    }

    /**
     * @param deliveryGateIntList the deliveryGateIntList to set
     */
    public void setDeliveryGateIntList(List<Object[]> deliveryGateIntList) {
        this.deliveryGateIntList = deliveryGateIntList;
    }

    /**
     * @return the cont_no
     */
    public String getContNo() {
        return contNo;
    }

    /**
     * @param cont_no the cont_no to set
     */
    public void setContNo(String contNo) {
        this.contNo = contNo;
    }

    /**
     * @return the vehicles
     */
    public List<MasterVehicle> getVehicles() {
        return vehicles;
    }

    /**
     * @param vehicles the vehicles to set
     */
    public void setVehicles(List<MasterVehicle> vehicles) {
        this.vehicles = vehicles;
    }

    /**
     * @return the deliveryService
     */
    public DeliveryService getDeliveryService() {
        return deliveryService;
    }

    /**
     * @param deliveryService the deliveryService to set
     */
    public void setDeliveryService(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    /**
     * @return the serviceGateDelivery
     */
    public ServiceGateDelivery getServiceGateDelivery() {
        return serviceGateDelivery;
    }

    /**
     * @param serviceGateDelivery the serviceGateDelivery to set
     */
    public void setServiceGateDelivery(ServiceGateDelivery serviceGateDelivery) {
        this.serviceGateDelivery = serviceGateDelivery;
    }

    /**
     * @return the serviceContDischargeObject
     */
    public Object[] getServiceContDischargeObject() {
        return serviceContDischargeObject;
    }

    /**
     * @param serviceContDischargeObject the serviceContDischargeObject to set
     */
    public void setServiceContDischargeObject(Object[] serviceContDischargeObject) {
        this.serviceContDischargeObject = serviceContDischargeObject;
    }

    /**
     * @return the masterContainerType
     */
    public MasterContainerType getMasterContainerType() {
        return masterContainerType;
    }

    /**
     * @param masterContainerType the masterContainerType to set
     */
    public void setMasterContainerType(MasterContainerType masterContainerType) {
        this.masterContainerType = masterContainerType;
    }

    /**
     * @return the cont_type
     */
    public String getContType() {
        return contType;
    }

    /**
     * @param cont_type the cont_type to set
     */
    public void setCont_type(String contType) {
        this.contType = contType;
    }

    /**
     * @return the blok
     */
    public String getBlok() {
        return blok;
    }

    /**
     * @param blok the blok to set
     */
    public void setBlok(String blok) {
        this.blok = blok;
    }

    /**
     * @return the slot
     */
    public Short getSlot() {
        return slot;
    }

    /**
     * @param slot the slot to set
     */
    public void setSlot(Short slot) {
        this.slot = slot;
    }

    /**
     * @return the row
     */
    public Short getRow() {
        return row;
    }

    /**
     * @param row the row to set
     */
    public void setRow(Short row) {
        this.row = row;
    }

    /**
     * @return the tier
     */
    public Short getTier() {
        return tier;
    }

    /**
     * @param tier the tier to set
     */
    public void setTier(Short tier) {
        this.tier = tier;
    }

    /**
     * @return the planningVessel
     */
    public Object[] getPlanningVessel() {
        return planningVessel;
    }

    /**
     * @param planningVessel the planningVessel to set
     */
    public void setPlanningVessel(Object[] planningVessel) {
        this.planningVessel = planningVessel;
    }

    /**
     * @return the vesselName
     */
    public String getVesselName() {
        return vesselName;
    }

    /**
     * @param vesselName the vesselName to set
     */
    public void setVesselName(String vesselName) {
        this.vesselName = vesselName;
    }

    /**
     * @return the voyyage
     */
    public String getVoyyage() {
        return voyyage;
    }

    /**
     * @param voyyage the voyyage to set
     */
    public void setVoyyage(String voyyage) {
        this.voyyage = voyyage;
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

    /**
     * @return the deliveryServiceObject
     */
    public Object[] getDeliveryServiceObject() {
        return deliveryServiceObject;
    }

    /**
     * @param deliveryServiceObject the deliveryServiceObject to set
     */
    public void setDeliveryServiceObject(Object[] deliveryServiceObject) {
        this.deliveryServiceObject = deliveryServiceObject;
    }

    /**
     * @return the serviceGatedelivery
     */
    public Object[] getServiceGateDeliveryObject() {
        return serviceGateDeliveryObject;
    }

    /**
     * @param serviceGatedelivery the serviceGatedelivery to set
     */
    public void setServiceGateDeliveryObject(Object[] serviceGateDeliveryObject) {
        this.serviceGateDeliveryObject = serviceGateDeliveryObject;
    }

    /**
     * @return the visible
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * @param visible the visible to set
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public String getNoPpkb() {
        return noPpkb;
    }

    public void setNoPpkb(String noPpkb) {
        this.noPpkb = noPpkb;
    }

    public CancelLoadingService getCancelLoadingService() {
        return cancelLoadingService;
    }

    public void setCancelLoadingService(CancelLoadingService cancelLoadingService) {
        this.cancelLoadingService = cancelLoadingService;
    }

    public Object[] getCancelObject() {
        return cancelObject;
    }

    public void setCancelObject(Object[] cancelObject) {
        this.cancelObject = cancelObject;
    }

    public boolean isDisableGrowl() {
        return disableGrowl;
    }

    public void setDisableGrowl(boolean disableGrowl) {
        this.disableGrowl = disableGrowl;
    }

    public String getContStatus() {
        return contStatus;
    }

    public void setContStatus(String contStatus) {
        this.contStatus = contStatus;
    }

    public PluggingReeferService getPluggingReeferService() {
        return pluggingReeferService;
    }

    public void setPluggingReeferService(PluggingReeferService pluggingReeferService) {
        this.pluggingReeferService = pluggingReeferService;
    }

}
