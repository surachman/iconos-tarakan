/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.bean;

import com.qtasnim.jsf.FacesHelper;
import com.pelindo.ebtos.ejb.facade.remote.MasterSettingAppFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterTarifContFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.RegistrationFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.UcDeliveryServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.UcPerhitunganLiftFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.UcPerhitunganPenumpukanFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.UcPerhitunganStevedoringFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.UncontainerizedServiceFacadeRemote;
import com.pelindo.ebtos.model.db.PlanningVessel;
import com.pelindo.ebtos.model.db.PreserviceVessel;
import com.pelindo.ebtos.model.db.Registration;
import com.pelindo.ebtos.model.db.UcDeliveryService;
import com.pelindo.ebtos.model.db.UcPerhitunganLift;
import com.pelindo.ebtos.model.db.UcPerhitunganPenumpukan;
import com.pelindo.ebtos.model.db.UcPerhitunganStevedoring;
import com.pelindo.ebtos.model.db.UncontainerizedService;
import com.pelindo.ebtos.model.db.master.MasterCustomer;
import com.pelindo.ebtos.model.db.master.MasterSettingApp;
import com.pelindo.ebtos.model.db.master.MasterVessel;
import com.qtasnim.util.DateCalculator;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;
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
 * @author dycode-java
 */
@ManagedBean(name="entryDeliveryUcServiceBean")
@ViewScoped
public class EntryDeliveryUcServiceBean implements Serializable{
    MasterTarifContFacadeRemote masterTarifContFacade = lookupMasterTarifContFacadeRemote();
    MasterSettingAppFacadeRemote masterSettingAppFacade = lookupMasterSettingAppFacadeRemote();
    RegistrationFacadeRemote registrationFacade = lookupRegistrationFacadeRemote();
    UcDeliveryServiceFacadeRemote ucDeliveryServiceFacade = lookupUcDeliveryServiceFacadeRemote();
    UncontainerizedServiceFacadeRemote uncontainerizedServiceFacade = lookupUncontainerizedServiceFacadeRemote();
    UcPerhitunganPenumpukanFacadeRemote ucPerhitunganPenumpukanFacade = lookupUcPerhitunganPenumpukanFacadeRemote();
    UcPerhitunganLiftFacadeRemote ucPerhitunganLiftFacade = lookupUcPerhitunganLiftFacadeRemote();
    UcPerhitunganStevedoringFacadeRemote ucPerhitunganStevedoringFacade = lookupUcPerhitunganStevedoringFacadeRemote();

    private List<Object[]> registrations = lookupRegistrationFacadeRemote().findRegistrationDeliveryUC();
    private List<Object[]> ucDeliveryServices;
    private List<Object[]> uncontainerizedServices;

    private Registration registration;
    private UcDeliveryService ucDeliveryService;
    private UncontainerizedService uncontainerizedService;
    private UcPerhitunganStevedoring stevedoring;
    private UcPerhitunganLift lift;
    private UcPerhitunganPenumpukan tumpuk;

    private String no_reg;

    /** Creates a new instance of EntryDeliveryUcServiceBean */
    public EntryDeliveryUcServiceBean() {
        registration = new Registration();
        registration.setMasterCustomer(new MasterCustomer());
        registration.setPlanningVessel(new PlanningVessel());
        registration.getPlanningVessel().setPreserviceVessel(new PreserviceVessel());
        registration.getPlanningVessel().getPreserviceVessel().setMasterVessel(new MasterVessel());
        ucDeliveryService = new UcDeliveryService();
        uncontainerizedService = new UncontainerizedService();
        stevedoring = new UcPerhitunganStevedoring();
        tumpuk = new UcPerhitunganPenumpukan();
        lift = new UcPerhitunganLift();
    }

    public void handleSelectRegistration(ActionEvent event) {
        no_reg = (String) event.getComponent().getAttributes().get("noReg");
        registration = registrationFacade.find(no_reg);
        ucDeliveryServices = ucDeliveryServiceFacade.findByReg(no_reg);
        uncontainerizedServices = uncontainerizedServiceFacade.findByEntryDelivery(registration.getPlanningVessel().getNoPpkb());
    }

    public void handleAdd(ActionEvent event){
        ucDeliveryService = new UcDeliveryService();
        uncontainerizedService = new UncontainerizedService();
        stevedoring = new UcPerhitunganStevedoring();
        tumpuk = new UcPerhitunganPenumpukan();
        lift = new UcPerhitunganLift();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyMM");
        String tgl = dateFormat.format(Calendar.getInstance().getTime());
        String i;
        String id = ucDeliveryServiceFacade.generateId(tgl);
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
        ucDeliveryService.setJobslip("14"+i);
    }

    public void handleSelectContPick(ActionEvent event){
        Integer id_cont = (Integer) event.getComponent().getAttributes().get("idCont");
        uncontainerizedService = uncontainerizedServiceFacade.find(id_cont);
        ucDeliveryService.setUncontainerizedService(uncontainerizedService);
    }

    public void handleConfirm(ActionEvent event){
        boolean loggedIn = false;
        if(ucDeliveryService.getUncontainerizedService() == null)
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Warning", "application.transaction.notcompleted");
        else {
            Calendar now = Calendar.getInstance();
            if (uncontainerizedService.getPlacementDate() != null) {
                int min = DateCalculator.countRangeInDays(now.getTime(), uncontainerizedService.getPlacementDate()) + 1;
                if (now.getTime().getHours() > uncontainerizedService.getPlacementDate().getHours()) {
                    now.add(Calendar.DATE, 1);
                    now.set(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DATE), uncontainerizedService.getPlacementDate().getHours(), uncontainerizedService.getPlacementDate().getMinutes(), uncontainerizedService.getPlacementDate().getSeconds());
                    ucDeliveryService.setValidDate(now.getTime());
                } else if (now.getTime().getHours() == uncontainerizedService.getPlacementDate().getHours()) {
                    if (now.getTime().getMinutes() > uncontainerizedService.getPlacementDate().getMinutes()) {
                        now.add(Calendar.DATE, 1);
                        now.set(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DATE), uncontainerizedService.getPlacementDate().getHours(), uncontainerizedService.getPlacementDate().getMinutes(), uncontainerizedService.getPlacementDate().getSeconds());
                        ucDeliveryService.setValidDate(now.getTime());
                    } else if (now.getTime().getMinutes() == uncontainerizedService.getPlacementDate().getMinutes()) {
                        if (now.getTime().getSeconds() > uncontainerizedService.getPlacementDate().getSeconds()) {
                            now.add(Calendar.DATE, 1);
                            now.set(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DATE), uncontainerizedService.getPlacementDate().getHours(), uncontainerizedService.getPlacementDate().getMinutes(), uncontainerizedService.getPlacementDate().getSeconds());
                            ucDeliveryService.setValidDate(now.getTime());
                        } else if (now.getTime().getSeconds() == uncontainerizedService.getPlacementDate().getSeconds()) {
                            now.set(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DATE), uncontainerizedService.getPlacementDate().getHours(), uncontainerizedService.getPlacementDate().getMinutes(), uncontainerizedService.getPlacementDate().getSeconds());
                            ucDeliveryService.setValidDate(now.getTime());
                        }
                    }
                } else {
                    now.set(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DATE), uncontainerizedService.getPlacementDate().getHours(), uncontainerizedService.getPlacementDate().getMinutes(), uncontainerizedService.getPlacementDate().getSeconds());
                    ucDeliveryService.setValidDate(now.getTime());
                }
                MasterSettingApp settingApp = new MasterSettingApp();
                settingApp = masterSettingAppFacade.find("masa");

                int m1 = min - 2;
                if (min < 13) {
                    if (min <= settingApp.getValueInteger()) {
                        ucDeliveryService.setMasa1(1);
                    } else {
                        ucDeliveryService.setMasa1(m1);
                    }
                    ucDeliveryService.setMasa2(0);
                } else {
                    int masa2 = min - 12;
                    int masa1 = m1 - masa2;
                    ucDeliveryService.setMasa1(masa1);
                    ucDeliveryService.setMasa2(masa2);
                }
            } else {
                ucDeliveryService.setValidDate(null);
                ucDeliveryService.setMasa1(0);
                ucDeliveryService.setMasa2(0);
            }
            ucDeliveryService.setNoReg(registration.getNoReg());
            ucDeliveryService.setDeliveryDate(null);
            ucDeliveryService.setIsDelivery("FALSE");
            ucDeliveryServiceFacade.edit(ucDeliveryService);
            loggedIn = true;

            stevedoring.setNoReg(no_reg);
            stevedoring.setJobslip(ucDeliveryService.getJobslip());
            if(uncontainerizedService.getCrane().equalsIgnoreCase("L")){
                if(uncontainerizedService.getWeight() <= 20000)
                    stevedoring.setActivityCode("P001");
                else if(uncontainerizedService.getWeight() <= 35000)
                    stevedoring.setActivityCode("P002");
                else
                    stevedoring.setActivityCode("P003");
            }else{
                if(uncontainerizedService.getWeight() <= 20000)
                    stevedoring.setActivityCode("P004");
                else if(uncontainerizedService.getWeight() <= 35000)
                    stevedoring.setActivityCode("P005");
                else
                    stevedoring.setActivityCode("P006");
            }

            // set amount berdasarkan tipe pelayaran
            BigDecimal cHandD = BigDecimal.ZERO;
            BigDecimal cHandI = BigDecimal.ZERO;
            List<Object[]> listTarif = new ArrayList<Object[]>();
            listTarif = masterTarifContFacade.findAmountByActivity(stevedoring.getActivityCode());
            for(Object[] o1 : listTarif){
                if(o1[1].equals("IDR"))
                    cHandD = (BigDecimal) o1[0];
                else
                    cHandI = (BigDecimal) o1[0];
            }

            lift.setNoReg(registration.getNoReg());
            lift.setJobslip(ucDeliveryService.getJobslip());
            if(uncontainerizedService.getWeight() <= 20000)
                lift.setActivityCode("P010");
            else if(uncontainerizedService.getWeight() <= 35000)
                lift.setActivityCode("P011");
            else
                lift.setActivityCode("P012");

            // set amount berdasarkan tipe pelayaran
            BigDecimal cLiftD = BigDecimal.ZERO;
            BigDecimal cLiftI = BigDecimal.ZERO;
            listTarif = masterTarifContFacade.findAmountByActivity(lift.getActivityCode());
            for(Object[] o1 : listTarif){
                if(o1[1].equals("IDR"))
                    cLiftD = (BigDecimal) o1[0];
                else
                    cLiftI = (BigDecimal) o1[0];
                lift.setCurrCode((String) o1[1]);
            }

            tumpuk.setRegistration(registration);
            tumpuk.setJobslip(ucDeliveryService.getJobslip());
            tumpuk.setOperation("DISCHARGE");
            if(uncontainerizedService.getWeight() <= 20000)
                tumpuk.setActivityCode("P007");
            else if(uncontainerizedService.getWeight() <= 35000)
                tumpuk.setActivityCode("P008");
            else
                tumpuk.setActivityCode("P009");

            // set charge dan jasa dermaga berdasarkan tipe pelayaran
            BigDecimal cTumpukD = BigDecimal.ZERO;
            BigDecimal cTumpukI = BigDecimal.ZERO;
            BigDecimal jasaDerD = BigDecimal.ZERO;
            BigDecimal jasaDerI = BigDecimal.ZERO;

            listTarif = masterTarifContFacade.findAmountByActivity(tumpuk.getActivityCode());
            for(Object[] o1 : listTarif){
                if(o1[1].equals("IDR"))
                    cTumpukD = (BigDecimal) o1[0];
                else
                    cTumpukI = (BigDecimal) o1[0];
                tumpuk.setCurrCode((String) o1[1]);
            }

            listTarif = masterTarifContFacade.findAmountByActivity("J001");
            for(Object[] o1 : listTarif){
                if(o1[1].equals("IDR"))
                    jasaDerD = (BigDecimal) o1[0];
                else
                    jasaDerI = (BigDecimal) o1[0];
            }

            BigDecimal cTumpuk = BigDecimal.ZERO;
            BigDecimal jasaDer = BigDecimal.ZERO;
            BigDecimal cHand = BigDecimal.ZERO;
            BigDecimal cLift = BigDecimal.ZERO;
            if(registration.getPlanningVessel().getTipePelayaran().equals("d")){
                cTumpuk = cTumpukD;
                jasaDer = jasaDerD;
                cHand = cHandD;
                cLift = cLiftD;
                stevedoring.setCurrCode("IDR");
                lift.setCurrCode("IDR");
                tumpuk.setCurrCode("IDR");
            } else {
                cTumpuk = cTumpukI;
                jasaDer = jasaDerI;
                cHand = cHandI;
                cLift = cLiftI;
                stevedoring.setCurrCode("USD");
                lift.setCurrCode("USD");
                tumpuk.setCurrCode("USD");
            }

            if (uncontainerizedService.getTruckLoosing().equals("FALSE")) {
                lift.setCharge(cLift);
                ucPerhitunganLiftFacade.create(lift);
            }

            stevedoring.setCharge(cHand);
            stevedoring.setJasaDermaga(jasaDer);
            ucPerhitunganStevedoringFacade.create(stevedoring);

            tumpuk.setCharge(cTumpuk);

            tumpuk.setChargeMasa1(tumpuk.getCharge().multiply(BigDecimal.valueOf(ucDeliveryService.getMasa1())));
            tumpuk.setChargeMasa2(tumpuk.getCharge().multiply(BigDecimal.valueOf(ucDeliveryService.getMasa2())).multiply(BigDecimal.valueOf(2)));

            tumpuk.setCharge(tumpuk.getChargeMasa1().add(tumpuk.getChargeMasa2()));
            ucPerhitunganPenumpukanFacade.create(tumpuk);

            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
            RequestContext.getCurrentInstance().addCallbackParam("loggedIn", loggedIn);
            ucDeliveryServices = ucDeliveryServiceFacade.findByReg(no_reg);
            uncontainerizedServices = uncontainerizedServiceFacade.findByEntryDelivery(registration.getPlanningVessel().getNoPpkb());
        }
    }

    public void handleEditDelete(ActionEvent event){
        String job_slip = (String) event.getComponent().getAttributes().get("jobSlip");
        ucDeliveryService = ucDeliveryServiceFacade.find(job_slip);
        Integer idHan = ucPerhitunganStevedoringFacade.findByJobslip(job_slip);
        if(idHan != null)
            stevedoring = ucPerhitunganStevedoringFacade.find(idHan);
        Integer idLift = ucPerhitunganLiftFacade.findByUcPerhitunganLift(job_slip);
        if(idLift != null)
            lift = ucPerhitunganLiftFacade.find(idLift);
        Integer idTump = ucPerhitunganPenumpukanFacade.findByJobslip(job_slip);
        if(idTump != null)
            tumpuk = ucPerhitunganPenumpukanFacade.find(idTump);
    }

    public void handleDelete(ActionEvent event){
        ucDeliveryServiceFacade.remove(ucDeliveryService);
        ucPerhitunganLiftFacade.remove(lift);
        ucPerhitunganPenumpukanFacade.remove(tumpuk);
        ucPerhitunganStevedoringFacade.remove(stevedoring);
        FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.delete.succeeded");
        ucDeliveryServices = ucDeliveryServiceFacade.findByReg(no_reg);
        uncontainerizedServices = uncontainerizedServiceFacade.findByEntryDelivery(registration.getPlanningVessel().getNoPpkb());
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

    private UcDeliveryServiceFacadeRemote lookupUcDeliveryServiceFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (UcDeliveryServiceFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/UcDeliveryServiceFacade!com.pelindo.ebtos.ejb.facade.remote.UcDeliveryServiceFacadeRemote");
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

    private MasterSettingAppFacadeRemote lookupMasterSettingAppFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (MasterSettingAppFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/MasterSettingAppFacade!com.pelindo.ebtos.ejb.facade.remote.MasterSettingAppFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private UcPerhitunganStevedoringFacadeRemote lookupUcPerhitunganStevedoringFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (UcPerhitunganStevedoringFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/UcPerhitunganStevedoringFacade!com.pelindo.ebtos.ejb.facade.remote.UcPerhitunganStevedoringFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private UcPerhitunganLiftFacadeRemote lookupUcPerhitunganLiftFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (UcPerhitunganLiftFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/UcPerhitunganLiftFacade!com.pelindo.ebtos.ejb.facade.remote.UcPerhitunganLiftFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private UcPerhitunganPenumpukanFacadeRemote lookupUcPerhitunganPenumpukanFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (UcPerhitunganPenumpukanFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/UcPerhitunganPenumpukanFacade!com.pelindo.ebtos.ejb.facade.remote.UcPerhitunganPenumpukanFacadeRemote");
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
     * @return the deliveryUcServices
     */
    public List<Object[]> getUcDeliveryServices() {
        return ucDeliveryServices;
    }

    /**
     * @param deliveryUcServices the deliveryUcServices to set
     */
    public void setUcDeliveryServices(List<Object[]> ucDeliveryServices) {
        this.ucDeliveryServices = ucDeliveryServices;
    }

    /**
     * @return the uncontainerizedServices
     */
    public List<Object[]> getUncontainerizedServices() {
        return uncontainerizedServices;
    }

    /**
     * @param uncontainerizedServices the uncontainerizedServices to set
     */
    public void setUncontainerizedServices(List<Object[]> uncontainerizedServices) {
        this.uncontainerizedServices = uncontainerizedServices;
    }

    /**
     * @return the ucDeliveryService
     */
    public UcDeliveryService getUcDeliveryService() {
        return ucDeliveryService;
    }

    /**
     * @param ucDeliveryService the ucDeliveryService to set
     */
    public void setUcDeliveryService(UcDeliveryService ucDeliveryService) {
        this.ucDeliveryService = ucDeliveryService;
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
