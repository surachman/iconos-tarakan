/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.bean;

import com.qtasnim.jsf.FacesHelper;
import com.qtasnim.url.UrlHelper;
import com.pelindo.ebtos.bean.security.LoginSessionBean;
import com.pelindo.ebtos.ejb.remote.ChangeVesselOperationRemote;
import com.pelindo.ebtos.ejb.facade.remote.BankingSyncFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ChangeVesselServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.InvoiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterActivityFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterCommodityFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterContainerTypeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterCurrencyFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterCustomerFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterMateraiFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterPortFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterSettingAppFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterTarifContFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningVesselFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ReceivingServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.RegistrationFacadeRemote;
import com.pelindo.ebtos.ejb.remote.DataIntegratorRemote;
import com.pelindo.ebtos.model.db.BatalNota;
import com.pelindo.ebtos.model.db.ChangeVesselService;
import com.pelindo.ebtos.model.db.Invoice;
import com.pelindo.ebtos.model.db.PlanningVessel;
import com.pelindo.ebtos.model.db.Registration;
import com.pelindo.ebtos.model.db.master.MasterActivity;
import com.pelindo.ebtos.model.db.master.MasterCommodity;
import com.pelindo.ebtos.model.db.master.MasterContainerType;
import com.pelindo.ebtos.model.db.master.MasterCurrency;
import com.pelindo.ebtos.model.db.master.MasterCustomer;
import com.pelindo.ebtos.model.db.master.MasterPort;
import com.pelindo.ebtos.model.db.master.MasterSettingApp;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.context.RequestContext;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.apache.commons.lang3.StringEscapeUtils;
import org.primefaces.model.LazyDataModel;

/**
 *
 * @author R. Seno Anggoro A
 */
@ManagedBean(name = "perhitunganBatalKapalBean")
@ViewScoped
public class PerhitunganBatalKapalBean implements Serializable {

    @EJB
    private MasterSettingAppFacadeRemote masterSettingAppFacade;
    @EJB
    private MasterMateraiFacadeRemote masterMateraiFacade;
    @EJB
    private InvoiceFacadeRemote invoiceFacadeRemote;
    @EJB
    private RegistrationFacadeRemote registrationFacadeRemote;
    @EJB
    private BankingSyncFacadeRemote bankingSyncFacadeRemote;
    @EJB
    private ChangeVesselServiceFacadeRemote changeVesselServiceFacadeRemote;
    @EJB
    private MasterCurrencyFacadeRemote masterCurrencyFacadeRemote;
    @EJB
    private ReceivingServiceFacadeRemote receivingServiceFacadeRemote;
    @EJB
    private MasterTarifContFacadeRemote masterTarifContFacadeRemote;
    @EJB
    private MasterActivityFacadeRemote masterActivityFacadeRemote;
    @EJB
    private PlanningVesselFacadeRemote planningVesselFacadeRemote;
    @EJB
    private MasterPortFacadeRemote masterPortFacadeRemote;
    @EJB
    private MasterCommodityFacadeRemote masterCommodityFacadeRemote;
    @EJB
    private MasterCustomerFacadeRemote masterCustomerFacadeRemote;
    @EJB
    private MasterContainerTypeFacadeRemote masterContainerTypeFacadeRemote;
    @EJB
    private ChangeVesselOperationRemote changeVesselOperationRemote;
    @EJB
    private DataIntegratorRemote dataIntegratorRemote;

    private List<ChangeVesselService> changeVesselServices;
    private LazyDataModel<PlanningVessel> availablePlanningVessels;
    private List<Object[]> registrations;
    private ChangeVesselService changeVesselService;
    private Registration registration;
    private MasterCurrency masterCurrency;
    private PlanningVessel newPlanningVessel;
    private Invoice invoice;
    private BatalNota batalNota;
    private BigDecimal ppn = BigDecimal.ZERO;
    private BigDecimal ppnPrint = BigDecimal.ZERO;
    private BigDecimal materaiPrint = BigDecimal.ZERO;
    private BigDecimal total = BigDecimal.ZERO;
    private String userId = null;
    private Boolean disPrint = true;
    private Boolean disKredit = false;
    private Boolean disDetail = false;
    private Boolean disCancelInv = true;
    private Boolean isSimpatReady;

    /** Creates a new instance of PerhitunganCancelLoadingBean */
    public PerhitunganBatalKapalBean() {}

    @PostConstruct
    private void construct() {
        registration = new Registration();
        invoice = new Invoice();
        invoice.setPaymentType("CASH");
        LoginSessionBean loginSessionBean = LoginSessionBean.getCurrentInstance();
        userId = loginSessionBean.getName();
        masterCurrency = masterCurrencyFacadeRemote.getIDRCurrency();
        populateAvailablePlanningVessels();
        isSimpatReady = dataIntegratorRemote.isSimpatAvailable();
    }

    public void populateAvailablePlanningVessels() {
        availablePlanningVessels = new LazyDataModel<PlanningVessel>(){
            @Override
            public List<PlanningVessel> load(int first, int pageSize, String sortField, boolean sortOrder, Map<String,String> filters) {
                int count = planningVesselFacadeRemote.findByStatussesAndLoadOnly_Count(filters, "Confirm");
                availablePlanningVessels.setRowCount(count);

                return planningVesselFacadeRemote.findByStatussesAndLoadOnly(first, pageSize, sortField, sortOrder, filters, "Confirm");
            }

            @Override
            public void setRowIndex(final int rowIndex){
                if (rowIndex == -1 || getPageSize() == 0) {
                    super.setRowIndex(-1);
                } else {
                    super.setRowIndex(rowIndex % getPageSize());
                }
            }
        };
    }
    
    public void handleShowRegistrations(ActionEvent event){
        registrations = registrationFacadeRemote.findRegistrationBatalKapal();
    }

    public void handleSelectRegistration(ActionEvent event) {
        String noReg = (String) event.getComponent().getAttributes().get("noReg");
        registration = registrationFacadeRemote.find(noReg);
        viewData();
    }
    
    public void handleAddCancelInvoice(ActionEvent event) {
        batalNota = new BatalNota();
    }

    private void viewData() {
        changeVesselServices = new ArrayList();
        
        if (registration.getInvoice() != null && registration.getInvoice().getNoInvoice() != null) {
            if (registration.getInvoice().isCancelInvoice().equals("FALSE")) {
                changeVesselServices = changeVesselServiceFacadeRemote.findByNoReg(registration.getNoReg());
                newPlanningVessel = changeVesselServices.get(0).getNewPlanningVessel();
            }
        } else {
            populateContainers();
        }
        
        invoice = registration.getInvoice();
        disCancelInv = false;
        disDetail = false;
        total = BigDecimal.ZERO;

        for (ChangeVesselService row : changeVesselServices) {
            total = total.add(row.getCharge());
        }

        MasterSettingApp ppnSetting = masterSettingAppFacade.find("ppn");
        ppnPrint = ppnSetting.getValueFloat();
        ppn = total.multiply(ppnPrint);
        masterCurrency = masterCurrencyFacadeRemote.getIDRCurrency();
        
        List<Object[]> materai = masterMateraiFacade.findByCurr(masterCurrency.getCurrCode());
        
        if (total.compareTo((BigDecimal) materai.get(1)[2]) == -1) {
            materaiPrint = (BigDecimal) materai.get(0)[1];
        } else if (total.compareTo((BigDecimal) materai.get(2)[2]) == -1) {
            materaiPrint = (BigDecimal) materai.get(1)[1];
        } else {
            materaiPrint = (BigDecimal) materai.get(2)[1];
        }

        if (invoice != null) {
            disPrint = invoice.getValidasiPrint().equals("TRUE") ? true : false;

            if (invoice.getPaymentType().equalsIgnoreCase("CASH")) {
                disKredit = false;
            } else {
                disKredit = true;
            }
        } else {
            invoice = new Invoice();
            invoice.setPaymentType("CASH");
            disPrint = true;
            disKredit = true;
        }
    }

    private void populateContainers() {
        List<Object[]> receivingContainers = receivingServiceFacadeRemote.findContainersWithPosition(registration.getPlanningVessel().getNoPpkb());
        changeVesselServices = new ArrayList<ChangeVesselService>();
        
        boolean bContIsOverSize = false;
        
        for (Object[] row: receivingContainers) {
            MasterCommodity masterCommodity = masterCommodityFacadeRemote.find((String) row[3]);
            MasterCustomer mlo = masterCustomerFacadeRemote.find((String) row[11]);
            MasterContainerType masterContainerType = masterContainerTypeFacadeRemote.find((Integer) row[2]);
            MasterPort masterPort = masterPortFacadeRemote.find((String) row[13]);

            ChangeVesselService container = new ChangeVesselService();
            container.setBlNo((String) row[10]);
            container.setContGross((Integer) row[5]);
            container.setContNo((String) row[0]);
            container.setContSize(((Integer) row[1]).shortValue());
            container.setContStatus((String) row[4]);
            if ((Boolean) row[8] == true) {
                container.setDg("TRUE");
            } else {
                container.setDg("FALSE");
            }
            
            if ((Boolean) row[9] == true) {
                container.setDgLabel("TRUE");
            } else {
                container.setDgLabel("FALSE");
            }
            
            if ((Boolean) row[12] == true) {
                container.setIsExport("TRUE");
            } else {
                container.setIsExport("FALSE");
            }
            
            container.setMasterCommodity(masterCommodity);
            container.setMasterContainerType(masterContainerType);
            container.setMlo(mlo);
            container.setDischargePort(masterPort);
            if ((Boolean) row[7] == true) {
                container.setOverSize("TRUE");
            } else {
                container.setOverSize("FALSE");
            }
            container.setPosition((String) row[14]);
            container.setRegistration(registration);
            container.setSealNo((String) row[6]);
            container.setNewPlanningVessel(newPlanningVessel);

            String changeVesselActivity = masterActivityFacadeRemote.translateCancelDocumentActivityCode();
            MasterActivity changeVesselMasterActivity = masterActivityFacadeRemote.find(changeVesselActivity);
            BigDecimal changeVesselCharge = masterTarifContFacadeRemote.findByCurrCodeAndActivity(masterCurrency.getCurrCode(), changeVesselActivity);
            container.setMasterActivity(changeVesselMasterActivity);
            container.setCharge(changeVesselCharge);
            
            if (container.getOverSize().equals("TRUE")) {
                bContIsOverSize =  true;
            } else {
                bContIsOverSize = false;
            }
            
            if (container.getPosition().equals("CY")) {
                String relocationActivity = masterActivityFacadeRemote.translateExtraMovementActivityCode(
                        container.getContSize(), 
                        bContIsOverSize);
                MasterActivity relocationMasterActivity = masterActivityFacadeRemote.find(relocationActivity);
                BigDecimal relocationCharge = masterTarifContFacadeRemote.findByCurrCodeAndActivity(masterCurrency.getCurrCode(), relocationActivity);
                container.setMasterActivity(relocationMasterActivity);
                container.setCharge(changeVesselCharge.add(relocationCharge));
            }

            changeVesselServices.add(container);
        }
    }

    public void handleSubmit(ActionEvent event) {
        String status;
        Boolean loggedIn = false;

        try {
            invoice.setNoReg(registration.getNoReg());
            invoice.setRegistration(registration);
            invoice.setPaymentStatus("UNPAID");
            invoice.setJumlahTagihan(total);
            invoice.setPpn(ppn);
            invoice.setMaterai(materaiPrint);
            invoice.setAdditionalCharge(BigDecimal.ZERO);
            invoice.setTotalTagihan(invoice.getJumlahTagihan().add(invoice.getMaterai()).add(invoice.getPpn()).add(invoice.getAdditionalCharge()));
            invoice.setMasterCurrency(masterCurrency);
            registration.setInvoice(invoice);

            if (invoice.getPaymentType().equals("KREDIT")) {
                status = "approve";
                disKredit = true;
            } else {
                status = "confirm";
                disKredit = false;
            }

            registration.setStatusService(status);
            registrationFacadeRemote.edit(registration);
            changeVesselOperationRemote.submitChangeVessel(registration, changeVesselServices);

            viewData();
            loggedIn = true;
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
        } catch (RuntimeException re) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", re);
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.failed");
        }

        RequestContext.getCurrentInstance().addCallbackParam("loggedIn", loggedIn);
    }

    public void handleSelectNewVessel(ActionEvent event) {
        for (int i = 0; i < changeVesselServices.size(); i++) {
            changeVesselServices.get(i).setNewPlanningVessel(newPlanningVessel);
        }
    }

    public void handleSubmitBatalNota(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        boolean loggedIn = false;

        try {
            final String noInvoice = invoice.getNoInvoice();
            changeVesselOperationRemote.cancelInvoice(registration, batalNota, newPlanningVessel);
            disCancelInv = true;
            changeVesselServices.clear();

            if (masterSettingAppFacade.isPaymentBankingEnabled()) {
                try {
                    bankingSyncFacadeRemote.cancelInvoice(noInvoice);
                } catch (RuntimeException e) {
                    Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught when cancelInvoice Sync", e);
                    FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_INFO, "Info", "application.integration.banking.failed");
                }
            }

            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
            loggedIn = true;
        } catch (RuntimeException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught when handleSubmitBatalNota", e);
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_INFO, "Info", "application.save.failed");
        }

        context.addCallbackParam("loggedIn", loggedIn);
    }

    public void handleDownloadNota(ActionEvent event) {
        Boolean doPrint = false;
        RequestContext requestContext = RequestContext.getCurrentInstance();
        FacesContext facesContext = FacesContext.getCurrentInstance();

        try {
            invoice = invoiceFacadeRemote.find(registration.getNoReg());

            if (invoice.getValidasiPrint().equals("FALSE")) {
                invoice = invoiceFacadeRemote.publishInvoice(invoice);
            }

            disPrint = invoice.getValidasiPrint().equals("TRUE") ? true : false;
            viewData();

            String key = changeVesselOperationRemote.prepareInvoiceReport(registration, changeVesselServices);

            if (key != null) {
                doPrint = true;
                requestContext.addCallbackParam("target", (String) event.getComponent().getAttributes().get("target"));
                requestContext.addCallbackParam("url", StringEscapeUtils.escapeHtml3(UrlHelper.resolveFullAddress(facesContext, String.format("/report?key=%s", key))));
            } else {
                throw new RuntimeException("failed to generate report");
            }
        } catch (RuntimeException re) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught when handleDownloadNota", re);
        }

        requestContext.addCallbackParam("doPrint", doPrint);
    }

    public void handleDownloadDetail(ActionEvent event) {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        boolean doPrint = false;

        try {
            String key = changeVesselOperationRemote.preparePerhitunganInvoiceReport(registration, changeVesselServices);

            if (key != null) {
                doPrint = true;
                requestContext.addCallbackParam("target", (String) event.getComponent().getAttributes().get("target"));
                requestContext.addCallbackParam("url", StringEscapeUtils.escapeHtml3(UrlHelper.resolveFullAddress(facesContext, String.format("/report?key=%s", key))));
            } else {
                throw new RuntimeException("failed to generate report");
            }
        } catch (RuntimeException re) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught when handleDownloadDetail", re);
        }

        requestContext.addCallbackParam("doPrint", doPrint);
    }

    public void handleDownloadTransactionRecap(ActionEvent event) {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        boolean doPrint = false;

        try {
            String key = changeVesselOperationRemote.preparePerhitunganReport(registration, masterCurrency, ppn, materaiPrint, changeVesselServices);

            if (key != null) {
                doPrint = true;
                requestContext.addCallbackParam("target", (String) event.getComponent().getAttributes().get("target"));
                requestContext.addCallbackParam("url", StringEscapeUtils.escapeHtml3(UrlHelper.resolveFullAddress(facesContext, String.format("/report?key=%s", key))));
            } else {
                throw new RuntimeException("failed to generate report");
            }
        } catch (RuntimeException re) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught when handleDownloadTransactionRecap", re);
        }

        requestContext.addCallbackParam("doPrint", doPrint);
    }

    /**
     * @return the registrations
     */
    public List<Object[]> getRegistrations() {
        return registrations;
    }

    /**
     * @param registrations the registrations to set
     */
    public void setRegistrations(List<Object[]> registrations) {
        this.registrations = registrations;
    }

    /**
     * @return the total
     */
    public BigDecimal getTotal() {
        return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    /**
     * @return the registration
     */
    public Registration getRegistration() {
        return registration;
    }

    /**
     * @param registration the registration to set
     */
    public void setRegistration(Registration registration) {
        this.registration = registration;
    }

    public List<ChangeVesselService> getChangeVesselServices() {
        return changeVesselServices;
    }

    /**
     * @return the invoice
     */
    public Invoice getInvoice() {
        return invoice;
    }

    /**
     * @param invoice the invoice to set
     */
    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    /**
     * @return the changeVesselService
     */
    public ChangeVesselService getChangeVesselService() {
        return changeVesselService;
    }

    /**
     * @param changeVesselService the changeVesselService to set
     */
    public void setChangeVesselService(ChangeVesselService changeVesselService) {
        this.changeVesselService = changeVesselService;
    }

    public BigDecimal getMateraiPrint() {
        return materaiPrint;
    }

    public void setMateraiPrint(BigDecimal materaiPrint) {
        this.materaiPrint = materaiPrint;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Boolean getDisDetail() {
        return disDetail;
    }

    public void setDisDetail(Boolean disDetail) {
        this.disDetail = disDetail;
    }

    public Boolean getDisKredit() {
        return disKredit;
    }

    public void setDisKredit(Boolean disKredit) {
        this.disKredit = disKredit;
    }

    public Boolean getDisPrint() {
        return disPrint;
    }

    public void setDisPrint(Boolean disPrint) {
        this.disPrint = disPrint;
    }

    public Boolean getDisCancelInv() {
        return disCancelInv;
    }

    public void setDisCancelInv(Boolean disCancelInv) {
        this.disCancelInv = disCancelInv;
    }

    public MasterCurrency getMasterCurrency() {
        return masterCurrency;
    }

    public BigDecimal getPpn() {
        return ppn;
    }

    public LazyDataModel<PlanningVessel> getAvailablePlanningVessels() {
        return availablePlanningVessels;
    }

    public PlanningVessel getNewPlanningVessel() {
        return newPlanningVessel;
    }

    public void setNewPlanningVessel(PlanningVessel newPlanningVessel) {
        this.newPlanningVessel = newPlanningVessel;
    }

    public BatalNota getBatalNota() {
        return batalNota;
    }

    public void setBatalNota(BatalNota batalNota) {
        this.batalNota = batalNota;
    }

    public Boolean getIsSimpatReady() {
        return isSimpatReady;
    }
}
