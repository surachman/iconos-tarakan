/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.bean;

import com.qtasnim.jsf.FacesHelper;
import com.pelindo.ebtos.ejb.facade.remote.MasterActivityFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterTarifContFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterTarifHistoryFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PerhitunganPenumpukanSusulanFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.RegistrationFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.UcDeliveryServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.UcPenumpukanSusulanServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.UncontainerizedServiceFacadeRemote;
import com.pelindo.ebtos.model.db.PerhitunganPenumpukanSusulan;
import com.pelindo.ebtos.model.db.PlanningVessel;
import com.pelindo.ebtos.model.db.PreserviceVessel;
import com.pelindo.ebtos.model.db.Registration;
import com.pelindo.ebtos.model.db.UcDeliveryService;
import com.pelindo.ebtos.model.db.UcPenumpukanSusulanService;
import com.pelindo.ebtos.model.db.UncontainerizedService;
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
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.primefaces.context.RequestContext;

/**
 *
 * @author dycoder
 */
@ManagedBean(name="entryPenumpukanSusulanUcBean")
@ViewScoped
public class EntryPenumpukanSusulanUcBean implements Serializable{
    UncontainerizedServiceFacadeRemote uncontainerizedServiceFacade = lookupUncontainerizedServiceFacadeRemote();
    MasterTarifHistoryFacadeRemote masterTarifHistoryFacade = lookupMasterTarifHistoryFacadeRemote();
    MasterActivityFacadeRemote masterActivityFacade = lookupMasterActivityFacadeRemote();
    UcDeliveryServiceFacadeRemote deliveryServiceFacade = lookupDeliveryServiceFacadeRemote();
    PerhitunganPenumpukanSusulanFacadeRemote perhitunganPenumpukanSusulanFacade = lookupPerhitunganPenumpukanSusulanFacadeRemote();
    UcPenumpukanSusulanServiceFacadeRemote penumpukanSusulanServiceFacade = lookupPenumpukanSusulanServiceFacadeRemote();
    MasterTarifContFacadeRemote masterTarifContFacade = lookupMasterTarifContFacadeRemote();
    RegistrationFacadeRemote registrationFacade = lookupRegistrationFacadeRemote();

    private List<Object[]> registrations = lookupRegistrationFacadeRemote().findRegistrationPenumpukanSusulanUC();
    private List<Object[]> penumpukanSusulanServices;
    private List<Object[]> deliveryServices;

    private Registration registration;
    private UcPenumpukanSusulanService penumpukanSusulanService;
    private PerhitunganPenumpukanSusulan perhitunganPenumpukanSusulan;
    private UncontainerizedService uncontainerizedService;
    private UcDeliveryService ds;
    private String no_reg;
    private String actTumpuk;

    /** Creates a new instance of EntryPenumpukanSusulanUcBean */
    public EntryPenumpukanSusulanUcBean() {
        registration = new Registration();
        registration.setMasterCustomer(new MasterCustomer());
        registration.setPlanningVessel(new PlanningVessel());
        registration.getPlanningVessel().setPreserviceVessel(new PreserviceVessel());
        registration.getPlanningVessel().getPreserviceVessel().setMasterVessel(new MasterVessel());
        perhitunganPenumpukanSusulan = new PerhitunganPenumpukanSusulan();
        uncontainerizedService = new UncontainerizedService();
    }

    public void handleSelectRegistration(ActionEvent event) {
        no_reg = (String) event.getComponent().getAttributes().get("noReg");
        registration = registrationFacade.find(no_reg);
        setPenumpukanSusulanServices((List<Object[]>) penumpukanSusulanServiceFacade.findPenumpukanSusulanServiceByPPKBnReg(registration.getPlanningVessel().getNoPpkb(), no_reg));
        setDeliveryServices(deliveryServiceFacade.findDeliveryServiceByPPKB(registration.getPlanningVessel().getNoPpkb()));
    }

    public void handleAdd(ActionEvent event){
        penumpukanSusulanService = new UcPenumpukanSusulanService();
        uncontainerizedService = new UncontainerizedService();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyMM");
        String tgl = dateFormat.format(Calendar.getInstance().getTime());
        String i;
        String id = penumpukanSusulanServiceFacade.generateId(tgl.substring(2));
        if(id == null)
            i = "00001";
        else
            i = String.valueOf(Integer.valueOf(id) + 1);

        if(i.length() == 1)
            i = tgl+"0000"+i;
        else if(i.length() == 2)
            i = tgl+"000"+i;
        else if(i.length() == 3)
            i = tgl+"00"+i;
        else if(i.length() == 4)
            i = tgl+"0"+i;
        else
            i = tgl + i;
        penumpukanSusulanService.setJobSlip("21"+i);
    }

    public void handleSelectContPick(ActionEvent event){
        String id_cont = (String) event.getComponent().getAttributes().get("idCont");
        ds = deliveryServiceFacade.find(id_cont);
        uncontainerizedService = ds.getUncontainerizedService();
        penumpukanSusulanService.setIdUc(ds.getUncontainerizedService().getIdUc());
    }

    public void handleEditDelete(ActionEvent event){
        String job_slip = (String) event.getComponent().getAttributes().get("jobSlip");
        String no_cont = (String) event.getComponent().getAttributes().get("noCont");
        Integer id_uc = (Integer) event.getComponent().getAttributes().get("idUc");
        penumpukanSusulanService = penumpukanSusulanServiceFacade.find(job_slip);
        uncontainerizedService = uncontainerizedServiceFacade.find(id_uc);
        Integer idTumpuk = perhitunganPenumpukanSusulanFacade.findInvoiceUC(no_cont, registration.getPlanningVessel().getNoPpkb(), no_reg);
        perhitunganPenumpukanSusulan = perhitunganPenumpukanSusulanFacade.find(idTumpuk);
    }

    public void handleDelete(ActionEvent event){
        penumpukanSusulanServiceFacade.remove(penumpukanSusulanService);
        perhitunganPenumpukanSusulanFacade.remove(perhitunganPenumpukanSusulan);
        FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.delete.succeeded");
        setPenumpukanSusulanServices((List<Object[]>) penumpukanSusulanServiceFacade.findPenumpukanSusulanServiceByPPKBnReg(registration.getPlanningVessel().getNoPpkb(), no_reg));
        setDeliveryServices(deliveryServiceFacade.findDeliveryServiceByPPKB(registration.getPlanningVessel().getNoPpkb()));
    }

    public void handleConfirm(ActionEvent event){
        boolean loggedIn = false;
        if(penumpukanSusulanService.getIdUc() == null)
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Warning", "application.transaction.notcompleted");
        else {
            setUncontainerizedService(new UncontainerizedService());
            setUncontainerizedService(ds.getUncontainerizedService());
            int min = DateCalculator.countRangeInDays(new Date(), uncontainerizedService.getPlacementDate()) + 1;
            if(ds.getMasa1() < 11){
                int m = min + ds.getMasa1();
                if (m > 10) {
                    penumpukanSusulanService.setMasa1(Short.parseShort(String.valueOf("10")));
                    penumpukanSusulanService.setMasa2(Short.parseShort(String.valueOf(m - 10)));
                } else {
                    penumpukanSusulanService.setMasa1(Short.parseShort(String.valueOf(m)));
                    penumpukanSusulanService.setMasa2(Short.parseShort(String.valueOf("0")));
                }
            }else{
                penumpukanSusulanService.setMasa1(Short.parseShort(String.valueOf("0")));
                penumpukanSusulanService.setMasa2(Short.parseShort(String.valueOf(min)));
            }

            penumpukanSusulanService.setNoReg(registration.getNoReg());
            penumpukanSusulanService.setNoPpkb(registration.getPlanningVessel().getNoPpkb());
            penumpukanSusulanServiceFacade.edit(penumpukanSusulanService);
            loggedIn = true;

            if(getUncontainerizedService().getWeight() <= 20000)
                actTumpuk = "P007";
            else if(getUncontainerizedService().getWeight() <= 35000)
                actTumpuk = "P008";
            else
                actTumpuk = "P009";
            perhitunganPenumpukanSusulan.setBlNo(getUncontainerizedService().getBlNo());
            perhitunganPenumpukanSusulan.setNoPpkb(registration.getPlanningVessel().getNoPpkb());
            perhitunganPenumpukanSusulan.setRegistration(registration);
            perhitunganPenumpukanSusulan.setMasterActivity(masterActivityFacade.find(actTumpuk));

            // set amount berdasarkan tipe pelayaran
            BigDecimal cHandD = BigDecimal.ZERO;
            BigDecimal cHandI = BigDecimal.ZERO;
            for(Object[] o1 : masterTarifContFacade.findAmountByActivity(actTumpuk)){
                if(o1[1].equals("IDR"))
                    cHandD = (BigDecimal) o1[0];
                else
                    cHandI = (BigDecimal) o1[0];
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

    private UncontainerizedServiceFacadeRemote lookupUncontainerizedServiceFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (UncontainerizedServiceFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/UncontainerizedServiceFacade!com.pelindo.ebtos.ejb.facade.remote.UncontainerizedServiceFacadeRemote");
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

    private UcPenumpukanSusulanServiceFacadeRemote lookupPenumpukanSusulanServiceFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (UcPenumpukanSusulanServiceFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/UcPenumpukanSusulanServiceFacade!com.pelindo.ebtos.ejb.facade.remote.UcPenumpukanSusulanServiceFacadeRemote");
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

    private UcDeliveryServiceFacadeRemote lookupDeliveryServiceFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (UcDeliveryServiceFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/UcDeliveryServiceFacade!com.pelindo.ebtos.ejb.facade.remote.UcDeliveryServiceFacadeRemote");
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
    public UcPenumpukanSusulanService getPenumpukanSusulanService() {
        return penumpukanSusulanService;
    }

    /**
     * @param penumpukanSusulanService the penumpukanSusulanService to set
     */
    public void setPenumpukanSusulanService(UcPenumpukanSusulanService penumpukanSusulanService) {
        this.penumpukanSusulanService = penumpukanSusulanService;
    }

    /**
     * @return the uncontainerizedService
     */
    public UncontainerizedService getUncontainerizedService() {
        return uncontainerizedService;
    }

    /**
     * @param uncontainerizedService the uncontainerizedService to set
     */
    public void setUncontainerizedService(UncontainerizedService uncontainerizedService) {
        this.uncontainerizedService = uncontainerizedService;
    }

}
