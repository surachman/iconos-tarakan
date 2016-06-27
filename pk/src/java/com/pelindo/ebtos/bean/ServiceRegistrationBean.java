/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.bean;

import com.qtasnim.jsf.FacesHelper;
import com.pelindo.ebtos.ejb.facade.remote.IDGeneratorFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterCustomerFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningVesselFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.RegistrationFacadeRemote;
import com.pelindo.ebtos.model.db.PlanningVessel;
import com.pelindo.ebtos.model.db.Registration;
import com.pelindo.ebtos.model.db.master.MasterCustomer;
import com.pelindo.ebtos.model.db.master.MasterService;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author dycode-java
 */
@ManagedBean(name = "serviceRegistrationBean")
@ViewScoped
public class ServiceRegistrationBean implements Serializable {
    private static final Logger logger = Logger.getLogger(ServiceRegistrationBean.class.getName());

    @EJB
    private IDGeneratorFacadeRemote iDGeneratorFacadeRemote;
    @EJB
    private RegistrationFacadeRemote registrationFacadeRemote;
    @EJB
    private MasterCustomerFacadeRemote masterCustomerFacadeRemote;
    @EJB
    private MasterServiceFacadeRemote masterServiceFacadeRemote;
    @EJB
    private PlanningVesselFacadeRemote planningVesselFacadeRemote;

    private List<MasterService> masterServices;
    private List<Object[]> masterSelectServices;
    private List<Object[]> masterSelectServicesDelivery;
    private List<Object[]> masterSelectServicesDeliveryUC;
    private List<Object[]> masterSelectServicesUC;
    private List<Registration> registrations;
    private List<Registration> registrationHistories;
    private List<Object[]> planningVessels;
    private List<Object[]> masterCustomers;
    private Registration registration;
    private Date dateFilter;
    
     private String nomorReg;  
    /** Creates a new instance of ServiceRegistrationBean */
    public ServiceRegistrationBean() {}

    @PostConstruct
    private void construct(){
        masterServices = masterServiceFacadeRemote.findAll();
        masterSelectServices = masterServiceFacadeRemote.findSelectService();
        masterSelectServicesUC = masterServiceFacadeRemote.findSelectServiceUC();
        masterSelectServicesDelivery = masterServiceFacadeRemote.findSelectServiceDelivery();
        masterSelectServicesDeliveryUC = masterServiceFacadeRemote.findSelectServiceDeliveryUC();
        registrations = registrationFacadeRemote.findByStatusses("registrasi", "confirm");
        masterCustomers = masterCustomerFacadeRemote.findMasterCustomers();
        resetAttributes();
    }

    public void resetAttributes() {
        dateFilter = new Date();
        registration = new Registration();
        registration.setMasterCustomer(new MasterCustomer());
        registration.setMasterService(new MasterService());
        registration.setPlanningVessel(new PlanningVessel());

        if (!masterServices.isEmpty()) {
            registration.setMasterService(masterServices.get(0));
        }
    }

    public void handleSelectNoPpkb(ActionEvent event) {
        String noPpkb = (String) event.getComponent().getAttributes().get("noPpkb");
        PlanningVessel planningVessel = planningVesselFacadeRemote.find(noPpkb);
        registration.setPlanningVessel(planningVessel);
    }
    
    public void resetForm() {
        registration = new Registration();
        //deliveryBean = new DeliveryBean();
    if (!masterServices.isEmpty()) {
            registration.setMasterService(masterServices.get(0));
        }
    }
    
    public void handleAdd(ActionEvent event) {
        resetAttributes();
        resetForm();
    }

    public void delete(ActionEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();

        try {
            registrationFacadeRemote.remove(registration);
            registrations = registrationFacadeRemote.findByStatusses("registrasi", "confirm");
            FacesHelper.addFacesMessage(context, FacesMessage.SEVERITY_INFO, "Info", "application.delete.succeeded");
            resetAttributes();
        } catch (RuntimeException ex) {
            FacesHelper.addFacesMessage(context, FacesMessage.SEVERITY_WARN, "Error", "application.delete.failed");
            logger.log(Level.SEVERE, "exception caught when call delete", ex);
        }
    }

    public void handleSelectVesselCustCode(ActionEvent event) {
        String custCode = (String) event.getComponent().getAttributes().get("cust_code");
        MasterCustomer masterCustomer = masterCustomerFacadeRemote.find(custCode);
        registration.setMasterCustomer(masterCustomer);
    }

    public void saveEdit(ActionEvent event) {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        boolean isValid = false;
        
        try {
            if (registration.getNoReg() == null) {
                registration.setNoReg(iDGeneratorFacadeRemote.generateRegistrationID());
                //penambahan send data nomor registrasi ke receiving.xhtml untuk view data yg di input berdasarkan nomor registrasi by ade chelsea tanggal 19 maret 2014 20:26
                setNomorReg(registration.getNoReg());
                if(registration.getMasterService().getServiceCode().equals("SC003") && registration.getNo_ppkb_plug()==null){
                    registration.setNo_ppkb_plug(iDGeneratorFacadeRemote.generatePpkbPlugOnlyID());
                }
            }

            registration.setStatusService("registrasi");
            registrationFacadeRemote.edit(registration);
            registrations = registrationFacadeRemote.findByStatusses("registrasi", "confirm");
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
            resetAttributes();
            isValid = true;
            
        } catch (RuntimeException ex) {
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_WARN, "Error", "application.delete.failed");
            logger.log(Level.SEVERE, "exception caught when call saveEdit", ex);
        }
        
        
        
        requestContext.addCallbackParam("isValid", isValid);
    }

    public void handleShowVessels(ActionEvent event) {
        if (registration.getMasterService().getServiceCode().equals("SC001")) {
            planningVessels = planningVesselFacadeRemote.findPlanningVesselsSg(new Date());
        } else if (registration.getMasterService().getServiceCode().equals("SC023")) {
            planningVessels = planningVesselFacadeRemote.findVesselsCanBeCanceled();
        } else if (registration.getMasterService().getServiceCode().equals("SC024") || registration.getMasterService().getServiceCode().equals("SC025")) {
            planningVessels = planningVesselFacadeRemote.findCancelStatusAbleVessels();
        } else {
            planningVessels = planningVesselFacadeRemote.findPlanningVesselsSgOther();
        }
    }

    public void handleFilterRegistrationHistories(ActionEvent event) {
        registrationHistories = registrationFacadeRemote.findByStatussesAndCreatedDate(dateFilter, "approve");
    }

    public List<Object[]> getMasterCustomers() {
        return masterCustomers;
    }

    public List<Object[]> getPlanningVessels() {
        return planningVessels;
    }

    public List<Registration> getRegistrations() {
        return registrations;
    }
    
    public Registration getRegistration() {
        return registration;
    }

    public void setRegistration(Registration registration) {
        this.registration = registration;
    }

    public List<MasterService> getMasterServices() {
        return masterServices;
    }

    public List<Object[]> getMasterSelectServices() {
        return masterSelectServices;
    }

    public void setMasterSelectServices(List<Object[]> masterSelectServices) {
        this.masterSelectServices = masterSelectServices;
    }

 
    
    
    
    public List<Registration> getRegistrationHistories() {
        return registrationHistories;
    }

    public Date getDateFilter() {
        return dateFilter;
    }

    public void setDateFilter(Date dateFilter) {
        this.dateFilter = dateFilter;
    }

    public String getNomorReg() {
        return nomorReg;
    }

    public void setNomorReg(String nomorReg) {
        this.nomorReg = nomorReg;
    }

    public List<Object[]> getMasterSelectServicesUC() {
        return masterSelectServicesUC;
    }

    public void setMasterSelectServicesUC(List<Object[]> masterSelectServicesUC) {
        this.masterSelectServicesUC = masterSelectServicesUC;
    }

    public List<Object[]> getMasterSelectServicesDelivery() {
        return masterSelectServicesDelivery;
    }

    public void setMasterSelectServicesDelivery(List<Object[]> masterSelectServicesDelivery) {
        this.masterSelectServicesDelivery = masterSelectServicesDelivery;
    }

    public List<Object[]> getMasterSelectServicesDeliveryUC() {
        return masterSelectServicesDeliveryUC;
    }

    public void setMasterSelectServicesDeliveryUC(List<Object[]> masterSelectServicesDeliveryUC) {
        this.masterSelectServicesDeliveryUC = masterSelectServicesDeliveryUC;
    }
    
    
}
