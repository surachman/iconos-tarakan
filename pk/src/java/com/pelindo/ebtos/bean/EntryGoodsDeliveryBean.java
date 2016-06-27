/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.bean;

import com.qtasnim.jsf.FacesHelper;
import com.pelindo.ebtos.ejb.facade.remote.DeliveryBarangServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterActivityFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterTarifContFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterTarifHistoryFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PerhitunganLiftBarangFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PerhitunganPenumpukanBarangFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.RegistrationFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceCfsStrippingFacadeRemote;
import com.pelindo.ebtos.model.db.DeliveryBarangService;
import com.pelindo.ebtos.model.db.PerhitunganLiftBarang;
import com.pelindo.ebtos.model.db.PerhitunganPenumpukanBarang;
import com.pelindo.ebtos.model.db.PlanningVessel;
import com.pelindo.ebtos.model.db.PreserviceVessel;
import com.pelindo.ebtos.model.db.Registration;
import com.pelindo.ebtos.model.db.ServiceCfsStripping;
import com.pelindo.ebtos.model.db.master.MasterCustomer;
import com.pelindo.ebtos.model.db.master.MasterVessel;
import com.qtasnim.util.DateCalculator;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.application.FacesMessage;
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
@ManagedBean(name = "entryGoodsDeliveryBean")
@ViewScoped
public class EntryGoodsDeliveryBean implements Serializable{
    MasterTarifHistoryFacadeRemote masterTarifHistoryFacade = lookupMasterTarifHistoryFacadeRemote();
    MasterTarifContFacadeRemote masterTarifContFacade = lookupMasterTarifContFacadeRemote();
    MasterActivityFacadeRemote masterActivityFacade = lookupMasterActivityFacadeRemote();
    PerhitunganPenumpukanBarangFacadeRemote perhitunganPenumpukanBarangFacade = lookupPerhitunganPenumpukanBarangFacadeRemote();
    PerhitunganLiftBarangFacadeRemote perhitunganLiftBarangFacade = lookupPerhitunganLiftBarangFacadeRemote();
    ServiceCfsStrippingFacadeRemote serviceCfsStrippingFacade = lookupServiceCfsStrippingFacadeRemote();
    DeliveryBarangServiceFacadeRemote deliveryBarangServiceFacade = lookupDeliveryBarangServiceFacadeRemote();
    RegistrationFacadeRemote registrationFacade = lookupRegistrationFacadeRemote();

    private List<Object[]> registrations = lookupRegistrationFacadeRemote().findRegistrationDeliveryBarang();
    private List<Object[]> deliveryBarangServices;
    private List<Object[]> serviceCfsStrippings;
    private List<Object[]> serviceCfsStrippingsBL;

    private Registration registration;
    private DeliveryBarangService deliveryBarangService;
    private String no_reg;
    private PerhitunganLiftBarang lift;
    private PerhitunganPenumpukanBarang tumpuk;

    /** Creates a new instance of EntryGoodsDeliveryBean */
    public EntryGoodsDeliveryBean() {
        registration = new Registration();
        registration.setMasterCustomer(new MasterCustomer());
        registration.setPlanningVessel(new PlanningVessel());
        registration.getPlanningVessel().setPreserviceVessel(new PreserviceVessel());
        registration.getPlanningVessel().getPreserviceVessel().setMasterVessel(new MasterVessel());
        deliveryBarangService = new DeliveryBarangService();
        lift = new PerhitunganLiftBarang();
        tumpuk = new PerhitunganPenumpukanBarang();
    }

    public void handleSelectRegistration(ActionEvent event) {
        no_reg = (String) event.getComponent().getAttributes().get("noReg");
        setRegistration(registrationFacade.find(no_reg));
        deliveryBarangServices = deliveryBarangServiceFacade.findByReg(no_reg);
        serviceCfsStrippings = serviceCfsStrippingFacade.findForDeliveryGoods();
    }

    public void handleAdd(ActionEvent event){
        deliveryBarangService = new DeliveryBarangService();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyMM");
        String tgl = dateFormat.format(Calendar.getInstance().getTime());
        String i;
        String id = deliveryBarangServiceFacade.generateId(tgl.substring(2));
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
        deliveryBarangService.setJobslip("19"+i);
        deliveryBarangService.setCountBy("weight");
    }

    public void handleSelectContPick(ActionEvent event){
        String id_cont = (String) event.getComponent().getAttributes().get("idCont");
        deliveryBarangService.setContNo(id_cont);
        serviceCfsStrippingsBL = serviceCfsStrippingFacade.findBL(id_cont);
    }

    public void handleSelectBL(ActionEvent event){
        Integer id_bl = (Integer) event.getComponent().getAttributes().get("idBl");
        ServiceCfsStripping scd = serviceCfsStrippingFacade.find(id_bl);
        deliveryBarangService.setNoPpkb(scd.getNoPpkb());
        deliveryBarangService.setBlNo(scd.getBlNo());
        deliveryBarangService.setCommodityCode(scd.getCommodityCode());
        deliveryBarangService.setUnit(scd.getUnit());
        deliveryBarangService.setWeight(scd.getWeight());
        deliveryBarangService.setVolume(scd.getVolume());
        deliveryBarangService.setPlacementDate(scd.getPlacementDate());
    }

    public void handleConfirm(ActionEvent event){
        boolean loggedIn = false;
        if(deliveryBarangService.getContNo() == null)
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Warning", "application.transaction.notcompleted");
        else {
            Calendar now = Calendar.getInstance();
            int min = DateCalculator.countRangeInDays(now.getTime(), deliveryBarangService.getPlacementDate()) + 1;
            if (now.getTime().getHours() > deliveryBarangService.getPlacementDate().getHours()) {
                now.add(Calendar.DATE, 1);
                now.set(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DATE), deliveryBarangService.getPlacementDate().getHours(), deliveryBarangService.getPlacementDate().getMinutes(), deliveryBarangService.getPlacementDate().getSeconds());
                deliveryBarangService.setValidDate(now.getTime());
            } else if (now.getTime().getHours() == deliveryBarangService.getPlacementDate().getHours()) {
                if (now.getTime().getMinutes() > deliveryBarangService.getPlacementDate().getMinutes()) {
                    now.add(Calendar.DATE, 1);
                    now.set(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DATE), deliveryBarangService.getPlacementDate().getHours(), deliveryBarangService.getPlacementDate().getMinutes(), deliveryBarangService.getPlacementDate().getSeconds());
                    deliveryBarangService.setValidDate(now.getTime());
                } else if (now.getTime().getMinutes() == deliveryBarangService.getPlacementDate().getMinutes()) {
                    if (now.getTime().getSeconds() > deliveryBarangService.getPlacementDate().getSeconds()) {
                        now.add(Calendar.DATE, 1);
                        now.set(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DATE), deliveryBarangService.getPlacementDate().getHours(), deliveryBarangService.getPlacementDate().getMinutes(), deliveryBarangService.getPlacementDate().getSeconds());
                        deliveryBarangService.setValidDate(now.getTime());
                    } else if (now.getTime().getSeconds() == deliveryBarangService.getPlacementDate().getSeconds()) {
                        now.set(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DATE), deliveryBarangService.getPlacementDate().getHours(), deliveryBarangService.getPlacementDate().getMinutes(), deliveryBarangService.getPlacementDate().getSeconds());
                        deliveryBarangService.setValidDate(now.getTime());
                    }
                }
            } else {
                now.set(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DATE), deliveryBarangService.getPlacementDate().getHours(), deliveryBarangService.getPlacementDate().getMinutes(), deliveryBarangService.getPlacementDate().getSeconds());
                deliveryBarangService.setValidDate(now.getTime());
            }

            int m1 = min - 2;
            if(min < 13){
                if(min < 4)
                    deliveryBarangService.setMasa1(1);
                else
                    deliveryBarangService.setMasa1(m1);
                deliveryBarangService.setMasa2(0);
            } else {
                int masa2 = min - 12;
                int masa1 = m1 - masa2;
                deliveryBarangService.setMasa1(masa1);
                deliveryBarangService.setMasa2(masa2);
            }
            deliveryBarangService.setNoReg(no_reg);
            deliveryBarangServiceFacade.edit(deliveryBarangService);
            loggedIn = true;

            String actLift = "";
            String actTumpuk = "";
            if(deliveryBarangService.getCountBy().equalsIgnoreCase("weight")){
                actLift = "N001";
                actTumpuk = "M001";
            }else{
                actLift = "N002";
                actTumpuk = "M002";
            }

            lift.setNoReg(no_reg);
            lift.setNoPpkb(getRegistration().getPlanningVessel().getNoPpkb());
            lift.setContNo(deliveryBarangService.getContNo());
            lift.setMlo(deliveryBarangService.getMlo());
            lift.setJobslip(deliveryBarangService.getJobslip());
            lift.setCommodityCode(deliveryBarangService.getCommodityCode());
            lift.setBlNo(deliveryBarangService.getBlNo());
            lift.setOperation("DELIVERY");
            lift.setActivityCode(actLift);

            // set amount berdasarkan tipe pelayaran
            BigDecimal cLiftD = BigDecimal.ZERO;
            BigDecimal cLiftI = BigDecimal.ZERO;
            for(Object[] o1 : masterTarifContFacade.findAmountByActivity(actLift)){
                if(o1[1].equals("IDR"))
                    cLiftD = (BigDecimal) o1[0];
                else
                    cLiftI = (BigDecimal) o1[0];
                lift.setCurrCode((String) o1[1]);
            }

            tumpuk.setNoReg(getRegistration().getNoReg());
            tumpuk.setNoPpkb(getRegistration().getPlanningVessel().getNoPpkb());
            tumpuk.setContNo(deliveryBarangService.getContNo());
            tumpuk.setMlo(deliveryBarangService.getMlo());
            tumpuk.setJobslip(deliveryBarangService.getJobslip());
            tumpuk.setCommodityCode(deliveryBarangService.getCommodityCode());
            tumpuk.setBlNo(deliveryBarangService.getBlNo());
            tumpuk.setOperation("DELIVERY");
            tumpuk.setActivityCode(actTumpuk);

            // set charge dan jasa dermaga berdasarkan tipe pelayaran
            BigDecimal cTumpukD = BigDecimal.ZERO;
            BigDecimal cTumpukI = BigDecimal.ZERO;
            BigDecimal jasaDerD = BigDecimal.ZERO;
            BigDecimal jasaDerI = BigDecimal.ZERO;

            for(Object[] o1 : masterTarifContFacade.findAmountByActivity(actTumpuk)){
                if(o1[1].equals("IDR"))
                    cTumpukD = (BigDecimal) o1[0];
                else
                    cTumpukI = (BigDecimal) o1[0];
                tumpuk.setCurrCode((String) o1[1]);
            }

            for(Object[] o1 : masterTarifContFacade.findAmountByActivity("J001")){
                if(o1[1].equals("IDR"))
                    jasaDerD = (BigDecimal) o1[0];
                else
                    jasaDerI = (BigDecimal) o1[0];
            }

            BigDecimal cTumpuk = BigDecimal.ZERO;
            BigDecimal jasaDer = BigDecimal.ZERO;
            BigDecimal cLift = BigDecimal.ZERO;
            if(getRegistration().getPlanningVessel().getTipePelayaran().equals("d")){
                cTumpuk = cTumpukD;
                jasaDer = jasaDerD;
                cLift = cLiftD;
                lift.setCurrCode("IDR");
                tumpuk.setCurrCode("IDR");
            } else {
                cTumpuk = cTumpukI;
                jasaDer = jasaDerI;
                cLift = cLiftI;
                lift.setCurrCode("USD");
                tumpuk.setCurrCode("USD");
            }

            lift.setChargeEquipmet(cLift);
            perhitunganLiftBarangFacade.edit(lift);

            tumpuk.setChargePenumpukan(cTumpuk);
            tumpuk.setChargeMasa1(tumpuk.getChargePenumpukan().multiply(BigDecimal.valueOf(deliveryBarangService.getMasa1())));
            tumpuk.setChargeMasa2(tumpuk.getChargePenumpukan().multiply(BigDecimal.valueOf(deliveryBarangService.getMasa2())).multiply(BigDecimal.valueOf(2)));
            tumpuk.setJasaDermaga(jasaDer);
            tumpuk.setTotalCharge(tumpuk.getChargeMasa1().add(tumpuk.getChargeMasa2()).add(tumpuk.getJasaDermaga()));
            perhitunganPenumpukanBarangFacade.edit(tumpuk);

            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
            RequestContext.getCurrentInstance().addCallbackParam("loggedIn", loggedIn);
            deliveryBarangServices = deliveryBarangServiceFacade.findByReg(no_reg);
            serviceCfsStrippings = serviceCfsStrippingFacade.findForDeliveryGoods();
        }
    }

    public void handleEditDelete(ActionEvent event){
        String job_slip = (String) event.getComponent().getAttributes().get("jobSlip");
        String no_cont = (String) event.getComponent().getAttributes().get("noCont");
        deliveryBarangService = deliveryBarangServiceFacade.find(job_slip);
        Object[] idLift = perhitunganLiftBarangFacade.findPerhitunganLiftBarangByEdit(job_slip, no_cont);
        lift = perhitunganLiftBarangFacade.find(idLift[0]);
        Object[] idTump = perhitunganPenumpukanBarangFacade.findPerhitunganPenumpukanBarangByEdit(job_slip, no_cont);
        tumpuk = perhitunganPenumpukanBarangFacade.find(idTump[0]);
    }

    public void handleDelete(ActionEvent event){
        deliveryBarangServiceFacade.remove(deliveryBarangService);
        perhitunganLiftBarangFacade.remove(lift);
        perhitunganPenumpukanBarangFacade.remove(tumpuk);
        FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.delete.succeeded");
        deliveryBarangServices = deliveryBarangServiceFacade.findByReg(no_reg);
        serviceCfsStrippings = serviceCfsStrippingFacade.findForDeliveryGoods();
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

    private DeliveryBarangServiceFacadeRemote lookupDeliveryBarangServiceFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (DeliveryBarangServiceFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/DeliveryBarangServiceFacade!com.pelindo.ebtos.ejb.facade.remote.DeliveryBarangServiceFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private ServiceCfsStrippingFacadeRemote lookupServiceCfsStrippingFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (ServiceCfsStrippingFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/ServiceCfsStrippingFacade!com.pelindo.ebtos.ejb.facade.remote.ServiceCfsStrippingFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private PerhitunganLiftBarangFacadeRemote lookupPerhitunganLiftBarangFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (PerhitunganLiftBarangFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/PerhitunganLiftBarangFacade!com.pelindo.ebtos.ejb.facade.remote.PerhitunganLiftBarangFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private PerhitunganPenumpukanBarangFacadeRemote lookupPerhitunganPenumpukanBarangFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (PerhitunganPenumpukanBarangFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/PerhitunganPenumpukanBarangFacade!com.pelindo.ebtos.ejb.facade.remote.PerhitunganPenumpukanBarangFacadeRemote");
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

    private MasterTarifContFacadeRemote lookupMasterTarifContFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (MasterTarifContFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/MasterTarifContFacade!com.pelindo.ebtos.ejb.facade.remote.MasterTarifContFacadeRemote");
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
     * @return the deliveryBarangServices
     */
    public List<Object[]> getDeliveryBarangServices() {
        return deliveryBarangServices;
    }

    /**
     * @param deliveryBarangServices the deliveryBarangServices to set
     */
    public void setDeliveryBarangServices(List<Object[]> deliveryBarangServices) {
        this.deliveryBarangServices = deliveryBarangServices;
    }

    /**
     * @return the serviceCfsStrippings
     */
    public List<Object[]> getServiceCfsStrippings() {
        return serviceCfsStrippings;
    }

    /**
     * @param serviceCfsStrippings the serviceCfsStrippings to set
     */
    public void setServiceCfsStrippings(List<Object[]> serviceCfsStrippings) {
        this.serviceCfsStrippings = serviceCfsStrippings;
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
     * @return the deliveryBarangService
     */
    public DeliveryBarangService getDeliveryBarangService() {
        return deliveryBarangService;
    }

    /**
     * @param deliveryBarangService the deliveryBarangService to set
     */
    public void setDeliveryBarangService(DeliveryBarangService deliveryBarangService) {
        this.deliveryBarangService = deliveryBarangService;
    }

    /**
     * @return the serviceCfsStrippingsBL
     */
    public List<Object[]> getServiceCfsStrippingsBL() {
        return serviceCfsStrippingsBL;
    }

    /**
     * @param serviceCfsStrippingsBL the serviceCfsStrippingsBL to set
     */
    public void setServiceCfsStrippingsBL(List<Object[]> serviceCfsStrippingsBL) {
        this.serviceCfsStrippingsBL = serviceCfsStrippingsBL;
    }

}
