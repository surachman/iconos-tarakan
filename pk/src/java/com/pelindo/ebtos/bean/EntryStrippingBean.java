/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.bean;

import com.qtasnim.jsf.FacesHelper;
import com.pelindo.ebtos.ejb.facade.remote.MasterActivityFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterTarifContFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterTarifHistoryFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PerhitunganHandlingDischargeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PerhitunganPenumpukanFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PerhitunganStrippingFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.RegistrationFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceContDischargeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceReeferFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.StrippingServiceFacadeRemote;
import com.pelindo.ebtos.model.db.PerhitunganHandlingDischarge;
import com.pelindo.ebtos.model.db.PerhitunganPenumpukan;
import com.pelindo.ebtos.model.db.PerhitunganStripping;
import com.pelindo.ebtos.model.db.PlanningVessel;
import com.pelindo.ebtos.model.db.PreserviceVessel;
import com.pelindo.ebtos.model.db.Registration;
import com.pelindo.ebtos.model.db.ServiceContDischarge;
import com.pelindo.ebtos.model.db.StrippingService;
import com.pelindo.ebtos.model.db.master.MasterActivity;
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
import javax.ejb.EJB;
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
@ManagedBean(name="entryStrippingBean")
@ViewScoped
public class EntryStrippingBean implements Serializable{
    @EJB
    private ServiceReeferFacadeRemote serviceReeferFacadeRemote;
    private PerhitunganStrippingFacadeRemote perhitunganStrippingFacade = lookupPerhitunganStrippingFacadeRemote();
    private MasterTarifHistoryFacadeRemote masterTarifHistoryFacade = lookupMasterTarifHistoryFacadeRemote();
    private MasterTarifContFacadeRemote masterTarifContFacade = lookupMasterTarifContFacadeRemote();
    private MasterActivityFacadeRemote masterActivityFacade = lookupMasterActivityFacadeRemote();
    private PerhitunganPenumpukanFacadeRemote perhitunganPenumpukanFacade = lookupPerhitunganPenumpukanFacadeRemote();
    private PerhitunganHandlingDischargeFacadeRemote perhitunganHandlingDischargeFacade = lookupPerhitunganHandlingDischargeFacadeRemote();
    private StrippingServiceFacadeRemote strippingServiceFacade = lookupStrippingServiceFacadeRemote();
    private ServiceContDischargeFacadeRemote serviceContDischargeFacade = lookupServiceContDischargeFacadeRemote();
    private RegistrationFacadeRemote registrationFacade = lookupRegistrationFacadeRemote();

    private List<Object[]> registrations = lookupRegistrationFacadeRemote().findRegistrationStriping();
    private List<Object[]> strippingServices;
    private List<Object[]> serviceContDischarges;

    private Registration registration;
    private StrippingService strippingService;
    private String no_reg;
    private PerhitunganHandlingDischarge stevedoring;
    private PerhitunganHandlingDischarge ht;
    private PerhitunganHandlingDischarge otherHandling;
    private PerhitunganPenumpukan tumpuk;
    private PerhitunganStripping stripping;
    private String crane = null;
    private Integer masa1 = 0;
    private Integer masa2 = 0;

    /** Creates a new instance of EntryStrippingBean */
    public EntryStrippingBean() {
        registration = new Registration();
        registration.setMasterCustomer(new MasterCustomer());
        registration.setPlanningVessel(new PlanningVessel());
        registration.getPlanningVessel().setPreserviceVessel(new PreserviceVessel());
        registration.getPlanningVessel().getPreserviceVessel().setMasterVessel(new MasterVessel());
        stevedoring = new PerhitunganHandlingDischarge();
        ht = new PerhitunganHandlingDischarge();
        otherHandling = new PerhitunganHandlingDischarge();
        tumpuk = new PerhitunganPenumpukan();
        tumpuk.setRegistration(new Registration());
        tumpuk.setMasterActivity(new MasterActivity());
        stripping = new PerhitunganStripping();
    }

    public void handleSelectRegistration(ActionEvent event) {
        no_reg = (String) event.getComponent().getAttributes().get("noReg");
        registration = registrationFacade.find(no_reg);
        strippingServices = strippingServiceFacade.findStrippingServiceByPPKBnReg(registration.getPlanningVessel().getNoPpkb(), no_reg);
        serviceContDischarges = serviceContDischargeFacade.findServiceContDischargesStrippingService(registration.getPlanningVessel().getNoPpkb());
    }

    public void handleAdd(ActionEvent event){
        strippingService = new StrippingService();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyMM");
        String tgl = dateFormat.format(Calendar.getInstance().getTime());
        String i;
        String id = strippingServiceFacade.generateId(tgl.substring(2));
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
        strippingService.setJobSlip("06"+i);
    }

    public void handleSelectContPick(ActionEvent event){
        Integer id_cont = (Integer) event.getComponent().getAttributes().get("idCont");
        ServiceContDischarge scd = serviceContDischargeFacade.find(id_cont);
        strippingService.setContNo(scd.getContNo());
        strippingService.setMlo(scd.getMlo());
        strippingService.setContSize(scd.getContSize());
        strippingService.setContGross(scd.getContGross());
        strippingService.setContStatus(scd.getContStatus());
        strippingService.setOverSize(scd.getOverSize());
        strippingService.setDg(scd.getDangerous());
        strippingService.setDgLabel(scd.getDgLabel());
        strippingService.setCommodity(scd.getMasterCommodity().getName());
        strippingService.setMasterContainerType(scd.getMasterContainerType());
        crane = scd.getCrane();

        // set masa 1 masa 2
        int min = DateCalculator.countRangeInDays(new Date(), scd.getStartPlacementDate()) + 1;
        int m1 = min - 2;
        if (min < 13) {
            if (min < 4)
                masa1 = 1;
            else
                masa1 = m1;
        } else {
            masa2 = min - 12;
            masa1 = m1 - masa2;
        }
    }

    public void handleEditDelete(ActionEvent event){
        String job_slip = (String) event.getComponent().getAttributes().get("jobSlip");
        String no_cont = (String) event.getComponent().getAttributes().get("noCont");
        strippingService = strippingServiceFacade.find(job_slip);
        Integer idHan = perhitunganHandlingDischargeFacade.findInvoice(no_cont, registration.getPlanningVessel().getNoPpkb(), no_reg);
        stevedoring = perhitunganHandlingDischargeFacade.find(idHan);
        Integer idTump = perhitunganPenumpukanFacade.findInvoice(no_cont, registration.getPlanningVessel().getNoPpkb(), no_reg);
        tumpuk = perhitunganPenumpukanFacade.find(idTump);
        Integer idStrip = perhitunganStrippingFacade.findInvoice(no_cont, registration.getPlanningVessel().getNoPpkb(), no_reg);
        stripping = perhitunganStrippingFacade.find(idStrip);
    }

    public void handleDelete(ActionEvent event){
        strippingServiceFacade.remove(strippingService);
        perhitunganHandlingDischargeFacade.remove(stevedoring);
        perhitunganPenumpukanFacade.remove(tumpuk);
        perhitunganStrippingFacade.remove(stripping);
        FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.delete.succeeded");
        strippingServices = strippingServiceFacade.findStrippingServiceByPPKBnReg(registration.getPlanningVessel().getNoPpkb(), no_reg);
        serviceContDischarges = serviceContDischargeFacade.findServiceContDischargesStrippingService(registration.getPlanningVessel().getNoPpkb());
    }

    public void handleConfirm(ActionEvent event){
        boolean loggedIn = false;
        boolean bIsOverSize = false;
        if(strippingService.getContNo() == null)
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Warning", "application.transaction.notcompleted");
        else {
            strippingService.setRegistration(registration);
            strippingServiceFacade.edit(strippingService);
            loggedIn = true;
            
            // input perhitungan handling
            if (strippingService.getOverSize().equals("TRUE")) {
                bIsOverSize = true;
            }
            
            String dangerousClass = null;
            
            String actStevedoring = masterActivityFacade.translateStevedoringActivityCode(
                    strippingService.getContStatus(), 
                    strippingService.getContSize(), 
                    crane, 
                    bIsOverSize,
                    null,
                    strippingService.getDg().equals("TRUE"),
                    strippingService.getDgLabel().equals("TRUE"),
                    dangerousClass,
                    bIsOverSize,
                    false);
            
            stevedoring.setNoReg(no_reg);
            stevedoring.setNoPpkb(registration.getPlanningVessel().getNoPpkb());
            stevedoring.setContNo(strippingService.getContNo());
            stevedoring.setMlo(strippingService.getMlo());
            stevedoring.setActivityCode(actStevedoring);
            
            String actHt = masterActivityFacade.translateHaulageTruckingActivityCode(
                    strippingService.getContStatus(), 
                    strippingService.getContSize(), 
                    crane, 
                    bIsOverSize,
                    null,
                    strippingService.getDg().equals("TRUE"),
                    strippingService.getDgLabel().equals("TRUE"),
                    dangerousClass,
                    bIsOverSize,
                    false);
            
            ht.setNoReg(no_reg);
            ht.setNoPpkb(registration.getPlanningVessel().getNoPpkb());
            ht.setContNo(strippingService.getContNo());
            ht.setMlo(strippingService.getMlo());
            ht.setActivityCode(actHt);
            
            String actOtherHandling = masterActivityFacade.translateOtherHandlingChargesActivityCode(
                    strippingService.getContStatus(), 
                    strippingService.getContSize(), 
                    crane, 
                    bIsOverSize,
                    null,
                    strippingService.getDg().equals("TRUE"),
                    strippingService.getDgLabel().equals("TRUE"),
                    dangerousClass,
                    bIsOverSize,
                    false);
            
            otherHandling.setNoReg(no_reg);
            otherHandling.setNoPpkb(registration.getPlanningVessel().getNoPpkb());
            otherHandling.setContNo(strippingService.getContNo());
            otherHandling.setMlo(strippingService.getMlo());
            otherHandling.setActivityCode(actOtherHandling);

            // set amount berdasarkan tipe pelayaran
            BigDecimal cHandD = BigDecimal.ZERO;
            BigDecimal cHandI = BigDecimal.ZERO;
            for(Object[] o1 : masterTarifContFacade.findAmountByActivity(actStevedoring)){
                if(o1[1].equals("IDR"))
                    cHandD = (BigDecimal) o1[0];
                else
                    cHandI = (BigDecimal) o1[0];
            }

            //input perhitungan penumpukan
            Integer idReefer = serviceReeferFacadeRemote.findValidasiPenumpukan(strippingService.getRegistration().getPlanningVessel().getNoPpkb(), strippingService.getContNo(), "DISCHARGE");
            String actTumpuk = masterActivityFacade.translatePenumpukanActivityCode(
                    strippingService.getContStatus(), 
                    strippingService.getMasterContainerType().getIsoCode(), 
                    bIsOverSize, 
                    strippingService.getContSize(), true);
            tumpuk.setRegistration(registration);
            tumpuk.setNoPpkb(registration.getPlanningVessel().getNoPpkb());
            tumpuk.setContNo(strippingService.getContNo());
            tumpuk.setMlo(strippingService.getMlo());
            tumpuk.setMasterActivity(masterActivityFacade.find(actTumpuk));

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
            }

            // input perhitungan stripping
            if(strippingService.getContSize() == 20)
                stripping.setMasterActivity(masterActivityFacade.find("K001"));
            else if(strippingService.getContSize() == 40)
                stripping.setMasterActivity(masterActivityFacade.find("K002"));

            // set amount berdasarkan tipe pelayaran
            BigDecimal cStrD = BigDecimal.ZERO;
            BigDecimal cStrI = BigDecimal.ZERO;
            for(Object[] o1 : masterTarifContFacade.findAmountByActivity(stripping.getMasterActivity().getActivityCode())){
                if(o1[1].equals("IDR"))
                    cStrD = (BigDecimal) o1[0];
                else
                    cStrI = (BigDecimal) o1[0];
            }

            BigDecimal cStr = BigDecimal.ZERO;
            BigDecimal cTumpuk = BigDecimal.ZERO;
            BigDecimal cHand = BigDecimal.ZERO;
            if(registration.getPlanningVessel().getTipePelayaran().equals("d")){
                cTumpuk = cTumpukD;
                cHand = cHandD;
                cStr = cStrD;
                stevedoring.setCurrCode("IDR");
                ht.setCurrCode("IDR");
                tumpuk.setCurrCode("IDR");
            } else {
                cTumpuk = cTumpukI;
                cHand = cHandI;
                cStr = cStrI;
                stevedoring.setCurrCode("USD");
                ht.setCurrCode("USD");
                tumpuk.setCurrCode("USD");
            }

            
            stevedoring.setCharge(cHand);
            perhitunganHandlingDischargeFacade.create(stevedoring);
            perhitunganHandlingDischargeFacade.create(ht);
            perhitunganHandlingDischargeFacade.create(otherHandling);

            if(strippingService.getDg().equals("TRUE")&& strippingService.getDgLabel().equals("TRUE"))
                tumpuk.setCharge(cTumpuk.multiply(BigDecimal.valueOf(2)));
            else if(strippingService.getDg().equals("TRUE") && strippingService.getDgLabel().equals("FALSE"))
                tumpuk.setCharge(cTumpuk.multiply(BigDecimal.valueOf(3)));
            else
                tumpuk.setCharge(cTumpuk);
            tumpuk.setChargeM1(tumpuk.getCharge().multiply(BigDecimal.valueOf(masa1)));
            tumpuk.setChargeM2(tumpuk.getCharge().multiply(BigDecimal.valueOf(masa2)).multiply(BigDecimal.valueOf(2)));
            tumpuk.setJasaDermaga(BigDecimal.ZERO);
            tumpuk.setTotalCharge(tumpuk.getChargeM1().add(tumpuk.getChargeM2()).add(tumpuk.getJasaDermaga()));
            perhitunganPenumpukanFacade.create(tumpuk);

            stripping.setCharge(cStr);
            perhitunganStrippingFacade.create(stripping);

            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
            RequestContext.getCurrentInstance().addCallbackParam("loggedIn", loggedIn);
            strippingServices = strippingServiceFacade.findStrippingServiceByPPKBnReg(registration.getPlanningVessel().getNoPpkb(), no_reg);
            serviceContDischarges = serviceContDischargeFacade.findServiceContDischargesStrippingService(registration.getPlanningVessel().getNoPpkb());
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

    private MasterTarifHistoryFacadeRemote lookupMasterTarifHistoryFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (MasterTarifHistoryFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/MasterTarifHistoryFacade!com.pelindo.ebtos.ejb.facade.remote.MasterTarifHistoryFacadeRemote");
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

    private StrippingServiceFacadeRemote lookupStrippingServiceFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (StrippingServiceFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/StrippingServiceFacade!com.pelindo.ebtos.ejb.facade.remote.StrippingServiceFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private PerhitunganHandlingDischargeFacadeRemote lookupPerhitunganHandlingDischargeFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (PerhitunganHandlingDischargeFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/PerhitunganHandlingDischargeFacade!com.pelindo.ebtos.ejb.facade.remote.PerhitunganHandlingDischargeFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private PerhitunganPenumpukanFacadeRemote lookupPerhitunganPenumpukanFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (PerhitunganPenumpukanFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/PerhitunganPenumpukanFacade!com.pelindo.ebtos.ejb.facade.remote.PerhitunganPenumpukanFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private PerhitunganStrippingFacadeRemote lookupPerhitunganStrippingFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (PerhitunganStrippingFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/PerhitunganStrippingFacade!com.pelindo.ebtos.ejb.facade.remote.PerhitunganStrippingFacadeRemote");
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
     * @return the strippingServices
     */
    public List<Object[]> getStrippingServices() {
        return strippingServices;
    }

    /**
     * @param strippingServices the strippingServices to set
     */
    public void setStrippingServices(List<Object[]> strippingServices) {
        this.strippingServices = strippingServices;
    }

    /**
     * @return the serviceContDischarges
     */
    public List<Object[]> getServiceContDischarges() {
        return serviceContDischarges;
    }

    /**
     * @param serviceContDischarges the serviceContDischarges to set
     */
    public void setServiceContDischarges(List<Object[]> serviceContDischarges) {
        this.serviceContDischarges = serviceContDischarges;
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
     * @return the strippingService
     */
    public StrippingService getStrippingService() {
        return strippingService;
    }

    /**
     * @param strippingService the strippingService to set
     */
    public void setStrippingService(StrippingService strippingService) {
        this.strippingService = strippingService;
    }

}
