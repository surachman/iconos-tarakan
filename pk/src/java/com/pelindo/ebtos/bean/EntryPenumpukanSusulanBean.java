/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.bean;

import com.qtasnim.jsf.FacesHelper;
import com.pelindo.ebtos.ejb.facade.remote.DeliveryServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterActivityFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterTarifContFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterTarifHistoryFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PenumpukanSusulanServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PerhitunganPenumpukanSusulanFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.RegistrationFacadeRemote;
import com.pelindo.ebtos.model.db.DeliveryService;
import com.pelindo.ebtos.model.db.PenumpukanSusulanService;
import com.pelindo.ebtos.model.db.PerhitunganPenumpukanSusulan;
import com.pelindo.ebtos.model.db.PlanningVessel;
import com.pelindo.ebtos.model.db.PreserviceVessel;
import com.pelindo.ebtos.model.db.Registration;
import com.pelindo.ebtos.model.db.master.MasterCustomer;
import com.pelindo.ebtos.model.db.master.MasterVessel;
import com.qtasnim.util.DateCalculator;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJBException;
import javax.enterprise.context.Conversation;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.primefaces.context.RequestContext;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author dycode-java
 */
@ManagedBean(name = "entryPenumpukanSusulanBean")
@ViewScoped
public class EntryPenumpukanSusulanBean implements Serializable {

    MasterTarifHistoryFacadeRemote masterTarifHistoryFacade = lookupMasterTarifHistoryFacadeRemote();
    MasterActivityFacadeRemote masterActivityFacade = lookupMasterActivityFacadeRemote();
    DeliveryServiceFacadeRemote deliveryServiceFacade = lookupDeliveryServiceFacadeRemote();
    PerhitunganPenumpukanSusulanFacadeRemote perhitunganPenumpukanSusulanFacade = lookupPerhitunganPenumpukanSusulanFacadeRemote();
    PenumpukanSusulanServiceFacadeRemote penumpukanSusulanServiceFacade = lookupPenumpukanSusulanServiceFacadeRemote();
    MasterTarifContFacadeRemote masterTarifContFacade = lookupMasterTarifContFacadeRemote();
    RegistrationFacadeRemote registrationFacade = lookupRegistrationFacadeRemote();
    @Inject
    Conversation con;
    private List<Object[]> registrations = lookupRegistrationFacadeRemote().findRegistrationPenumpukanSusulan();
    private List<Object[]> penumpukanSusulanServices;
    private List<Object[]> deliveryServices;
    private Registration registration;
    private PenumpukanSusulanService penumpukanSusulanService;
    private PerhitunganPenumpukanSusulan perhitunganPenumpukanSusulan;
    private DeliveryService ds;
    private String no_reg;
    private String actTumpuk;

    /** Creates a new instance of EntryPenumpukanSusulanBean */
    public EntryPenumpukanSusulanBean() {
        registration = new Registration();
        registration.setMasterCustomer(new MasterCustomer());
        registration.setPlanningVessel(new PlanningVessel());
        registration.getPlanningVessel().setPreserviceVessel(new PreserviceVessel());
        registration.getPlanningVessel().getPreserviceVessel().setMasterVessel(new MasterVessel());
        perhitunganPenumpukanSusulan = new PerhitunganPenumpukanSusulan();
    }

    public void handleSelectRegistration(ActionEvent event) {
        no_reg = (String) event.getComponent().getAttributes().get("noReg");
        registration = registrationFacade.find(no_reg);
        setPenumpukanSusulanServices((List<Object[]>) penumpukanSusulanServiceFacade.findPenumpukanSusulanServiceByPPKBnReg(registration.getPlanningVessel().getNoPpkb(), no_reg));
        setDeliveryServices(deliveryServiceFacade.findDeliveryServiceByPPKB(registration.getPlanningVessel().getNoPpkb()));
    }

    public void handleAdd(ActionEvent event) {
        penumpukanSusulanService = new PenumpukanSusulanService();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyMM");
        String tgl = dateFormat.format(Calendar.getInstance().getTime());
        String i;
        String id = penumpukanSusulanServiceFacade.generateId(tgl.substring(2));
        if (id == null) {
            i = "00001";
        } else {
            i = String.valueOf(Integer.valueOf(id) + 1);
        }

        if (i.length() == 1) {
            i = tgl + "0000" + i;
        } else if (i.length() == 2) {
            i = tgl + "000" + i;
        } else if (i.length() == 3) {
            i = tgl + "00" + i;
        } else if (i.length() == 4) {
            i = tgl + "0" + i;
        } else {
            i = tgl + i;
        }
        penumpukanSusulanService.setJobSlip("09" + i);
    }

    public void handleSelectContPick(ActionEvent event) {
        String id_cont = (String) event.getComponent().getAttributes().get("idCont");
        ds = deliveryServiceFacade.find(id_cont);
        penumpukanSusulanService.setContNo(ds.getContNo());
        penumpukanSusulanService.setMlo(ds.getMlo());
        penumpukanSusulanService.setContSize(ds.getContSize());
        penumpukanSusulanService.setContGross(ds.getContGross());
        penumpukanSusulanService.setContStatus(ds.getContStatus());
        penumpukanSusulanService.setOverSize(ds.getOverSize());
        penumpukanSusulanService.setDg(ds.getDg());
        penumpukanSusulanService.setDgLabel(ds.getDgLabel());
        penumpukanSusulanService.setMasterCommodity(ds.getMasterCommodity());
        penumpukanSusulanService.setMasterContainerType(ds.getMasterContainerType());
        penumpukanSusulanService.setPlacementDate(ds.getValidDate());
        penumpukanSusulanService.setBlNo(ds.getBlNo());
    }

    public void handleEditDelete(ActionEvent event) {
        String job_slip = (String) event.getComponent().getAttributes().get("jobSlip");
        String no_cont = (String) event.getComponent().getAttributes().get("noCont");
        penumpukanSusulanService = penumpukanSusulanServiceFacade.find(job_slip);
        Integer idTumpuk = perhitunganPenumpukanSusulanFacade.findInvoice(no_cont, registration.getPlanningVessel().getNoPpkb(), no_reg);
        perhitunganPenumpukanSusulan = perhitunganPenumpukanSusulanFacade.find(idTumpuk);
    }

    public void handleDelete(ActionEvent event) {
        try {
            penumpukanSusulanServiceFacade.remove(penumpukanSusulanService);
            perhitunganPenumpukanSusulanFacade.remove(perhitunganPenumpukanSusulan);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("data has been deleted"));
            setPenumpukanSusulanServices((List<Object[]>) penumpukanSusulanServiceFacade.findPenumpukanSusulanServiceByPPKBnReg(registration.getPlanningVessel().getNoPpkb(), no_reg));
            setDeliveryServices(deliveryServiceFacade.findDeliveryServiceByPPKB(registration.getPlanningVessel().getNoPpkb()));
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.delete.succeeded");
            penumpukanSusulanService = new PenumpukanSusulanService();
            perhitunganPenumpukanSusulan = new PerhitunganPenumpukanSusulan();
        } catch (EJBException ex) {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.delete.failed");
            ex.printStackTrace();
        }

    }

    public void handleConfirm(ActionEvent event) {
        boolean loggedIn = false;
        if (penumpukanSusulanService.getContNo() == null) {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.save.failed");
        } else {
            int min = DateCalculator.countRangeInDays(new Date(), penumpukanSusulanService.getPlacementDate()) + 1;
            if (ds.getMasa1() < 11) {
                int m = min + ds.getMasa1();
                if (m > 10) {
                    penumpukanSusulanService.setMasa1(Short.parseShort(String.valueOf("10")));
                    penumpukanSusulanService.setMasa2(Short.parseShort(String.valueOf(m - 10)));
                } else {
                    penumpukanSusulanService.setMasa1(Short.parseShort(String.valueOf(m)));
                    penumpukanSusulanService.setMasa2(Short.parseShort(String.valueOf("0")));
                }
            } else {
                penumpukanSusulanService.setMasa1(Short.parseShort(String.valueOf("0")));
                penumpukanSusulanService.setMasa2(Short.parseShort(String.valueOf(min)));
            }

            penumpukanSusulanService.setRegistration(registration);
            penumpukanSusulanService.setPlanningVessel(registration.getPlanningVessel());
            penumpukanSusulanServiceFacade.edit(penumpukanSusulanService);
            loggedIn = true;

            handleActTumpuk();
            perhitunganPenumpukanSusulan.setContNo(penumpukanSusulanService.getContNo());
            perhitunganPenumpukanSusulan.setMlo(penumpukanSusulanService.getMlo());
            perhitunganPenumpukanSusulan.setNoPpkb(registration.getPlanningVessel().getNoPpkb());
            perhitunganPenumpukanSusulan.setRegistration(registration);
            perhitunganPenumpukanSusulan.setMasterActivity(masterActivityFacade.find(actTumpuk));

            // set amount berdasarkan tipe pelayaran
            BigDecimal cHandD = BigDecimal.ZERO;
            BigDecimal cHandI = BigDecimal.ZERO;
            for (Object[] o1 : masterTarifContFacade.findAmountByActivity(actTumpuk)) {
                if (o1[1].equals("IDR")) {
                    cHandD = (BigDecimal) o1[0];
                } else {
                    cHandI = (BigDecimal) o1[0];
                }
            }
            BigDecimal cHand = BigDecimal.ZERO;
            if (registration.getPlanningVessel().getTipePelayaran().equals("d")) {
                cHand = cHandD;
                perhitunganPenumpukanSusulan.setCurrCode("IDR");
            } else {
                cHand = cHandI;
                perhitunganPenumpukanSusulan.setCurrCode("USD");
            }
            perhitunganPenumpukanSusulan.setCharge(cHand);
            perhitunganPenumpukanSusulan.setChargeM1(cHand.multiply(BigDecimal.valueOf(penumpukanSusulanService.getMasa1())));
            perhitunganPenumpukanSusulan.setChargeM2(cHand.multiply(BigDecimal.valueOf(penumpukanSusulanService.getMasa2())));
            perhitunganPenumpukanSusulan.setTotalCharge(perhitunganPenumpukanSusulan.getChargeM1().add(perhitunganPenumpukanSusulan.getChargeM2()));
            perhitunganPenumpukanSusulanFacade.create(perhitunganPenumpukanSusulan);

            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
            RequestContext.getCurrentInstance().addCallbackParam("loggedIn", loggedIn);
            setPenumpukanSusulanServices((List<Object[]>) penumpukanSusulanServiceFacade.findPenumpukanSusulanServiceByPPKBnReg(registration.getPlanningVessel().getNoPpkb(), no_reg));
            setDeliveryServices(deliveryServiceFacade.findDeliveryServiceByPPKB(registration.getPlanningVessel().getNoPpkb()));
            penumpukanSusulanService = new PenumpukanSusulanService();
            perhitunganPenumpukanSusulan = new PerhitunganPenumpukanSusulan();
        }
    }

    public void handleActTumpuk() {
        if (penumpukanSusulanService.getMasterContainerType().getIsoCode().equals("2230") || penumpukanSusulanService.getMasterContainerType().getIsoCode().equals("2232")
                || penumpukanSusulanService.getMasterContainerType().getIsoCode().equals("2242") || penumpukanSusulanService.getMasterContainerType().getIsoCode().equals("4330")) {
            if (penumpukanSusulanService.getContSize() == 20) {
                actTumpuk = "C007";
            } else if (penumpukanSusulanService.getContSize() == 40) {
                actTumpuk = "C008";
            }
        } else {
            if (penumpukanSusulanService.getOverSize().equals("FALSE")) {
                if (penumpukanSusulanService.getContStatus().equals("MTY")) {
                    if (penumpukanSusulanService.getContSize() == 20) {
                        actTumpuk = "C001";
                    } else if (penumpukanSusulanService.getContSize() == 40) {
                        actTumpuk = "C002";
                    }
                } else if (penumpukanSusulanService.getContStatus().equals("FCL") || penumpukanSusulanService.getContStatus().equals("LCL")) {
                    if (penumpukanSusulanService.getContSize() == 20) {
                        actTumpuk = "C003";
                    } else if (penumpukanSusulanService.getContSize() == 40) {
                        actTumpuk = "C004";
                    }
                }
            } else {
                if (penumpukanSusulanService.getContSize() == 20) {
                    actTumpuk = "C005";
                } else if (penumpukanSusulanService.getContSize() == 40) {
                    actTumpuk = "C006";
                }
            }
        }
    }

    private MasterTarifHistoryFacadeRemote lookupMasterTarifHistoryFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (MasterTarifHistoryFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/MasterTarifHistoryFacade!com.pelindo.ebtos.ejb.facade.remote.MasterTarifHistoryFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private RegistrationFacadeRemote lookupRegistrationFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (RegistrationFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/RegistrationFacade!com.pelindo.ebtos.ejb.facade.remote.RegistrationFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private MasterTarifContFacadeRemote lookupMasterTarifContFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (MasterTarifContFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/MasterTarifContFacade!com.pelindo.ebtos.ejb.facade.remote.MasterTarifContFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private PenumpukanSusulanServiceFacadeRemote lookupPenumpukanSusulanServiceFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (PenumpukanSusulanServiceFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/PenumpukanSusulanServiceFacade!com.pelindo.ebtos.ejb.facade.remote.PenumpukanSusulanServiceFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private PerhitunganPenumpukanSusulanFacadeRemote lookupPerhitunganPenumpukanSusulanFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (PerhitunganPenumpukanSusulanFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/PerhitunganPenumpukanSusulanFacade!com.pelindo.ebtos.ejb.facade.remote.PerhitunganPenumpukanSusulanFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private DeliveryServiceFacadeRemote lookupDeliveryServiceFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (DeliveryServiceFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/DeliveryServiceFacade!com.pelindo.ebtos.ejb.facade.remote.DeliveryServiceFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private MasterActivityFacadeRemote lookupMasterActivityFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (MasterActivityFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/MasterActivityFacade!com.pelindo.ebtos.ejb.facade.remote.MasterActivityFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
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

    /**
     * @return the penumpukanSusulanService
     */
    public PenumpukanSusulanService getPenumpukanSusulanService() {
        return penumpukanSusulanService;
    }

    /**
     * @param penumpukanSusulanService the penumpukanSusulanService to set
     */
    public void setPenumpukanSusulanService(PenumpukanSusulanService penumpukanSusulanService) {
        this.penumpukanSusulanService = penumpukanSusulanService;
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
     * @return the penumpukanSusulanServices
     */
    public List<Object[]> getPenumpukanSusulanServices() {
        return penumpukanSusulanServices;
    }

    /**
     * @param penumpukanSusulanServices the penumpukanSusulanServices to set
     */
    public void setPenumpukanSusulanServices(List<Object[]> penumpukanSusulanServices) {
        this.penumpukanSusulanServices = penumpukanSusulanServices;
    }

    /**
     * @return the deliveryServices
     */
    public List<Object[]> getDeliveryServices() {
        return deliveryServices;
    }

    /**
     * @param deliveryServices the deliveryServices to set
     */
    public void setDeliveryServices(List<Object[]> deliveryServices) {
        this.deliveryServices = deliveryServices;
    }
}
