/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.bean;

import com.qtasnim.jsf.FacesHelper;
import com.pelindo.ebtos.ejb.facade.remote.DeliveryServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterActivityFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterSettingAppFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterTarifContFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterTarifHistoryFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PerhitunganHandlingDischargeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PerhitunganLiftServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PerhitunganPenumpukanFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.RegistrationFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceContDischargeFacadeRemote;
import com.pelindo.ebtos.model.db.DeliveryService;
import com.pelindo.ebtos.model.db.PerhitunganHandlingDischarge;
import com.pelindo.ebtos.model.db.PerhitunganLiftService;
import com.pelindo.ebtos.model.db.PerhitunganPenumpukan;
import com.pelindo.ebtos.model.db.PlanningVessel;
import com.pelindo.ebtos.model.db.PreserviceVessel;
import com.pelindo.ebtos.model.db.Registration;
import com.pelindo.ebtos.model.db.ServiceContDischarge;
import com.pelindo.ebtos.model.db.master.MasterActivity;
import com.pelindo.ebtos.model.db.master.MasterCommodity;
import com.pelindo.ebtos.model.db.master.MasterContainerType;
import com.pelindo.ebtos.model.db.master.MasterCustomer;
import com.pelindo.ebtos.model.db.master.MasterSettingApp;
import com.pelindo.ebtos.model.db.master.MasterVessel;
import com.qtasnim.util.DateCalculator;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
 * @author dycode-java
 */
@ManagedBean(name="entryDeliveryServiceBean")
@ViewScoped
public class EntryDeliveryServiceBean implements Serializable{
    MasterSettingAppFacadeRemote masterSettingAppFacade = lookupMasterSettingAppFacadeRemote();
    MasterTarifHistoryFacadeRemote masterTarifHistoryFacade = lookupMasterTarifHistoryFacadeRemote();
    MasterTarifContFacadeRemote masterTarifContFacade = lookupMasterTarifContFacadeRemote();
    MasterActivityFacadeRemote masterActivityFacade = lookupMasterActivityFacadeRemote();
    PerhitunganPenumpukanFacadeRemote perhitunganPenumpukanFacade = lookupPerhitunganPenumpukanFacadeRemote();
    PerhitunganLiftServiceFacadeRemote perhitunganLiftServiceFacade = lookupPerhitunganLiftServiceFacadeRemote();
    PerhitunganHandlingDischargeFacadeRemote perhitunganHandlingDischargeFacade = lookupPerhitunganHandlingDischargeFacadeRemote();
    ServiceContDischargeFacadeRemote serviceContDischargeFacade = lookupServiceContDischargeFacadeRemote();
    DeliveryServiceFacadeRemote deliveryServiceFacade = lookupDeliveryServiceFacadeRemote();
    RegistrationFacadeRemote registrationFacade = lookupRegistrationFacadeRemote();

    private List<Object[]> registrations = lookupRegistrationFacadeRemote().findRegistrationDelivery();
    private List<Object[]> deliveryServices;
    private List<Object[]> serviceContDischarges;

    private Registration registration;
    private DeliveryService deliveryService;
    private PerhitunganHandlingDischarge handling;
    private PerhitunganLiftService lift;
    private PerhitunganPenumpukan tumpuk;
    private String no_reg;
    private String actHandling = null;
    private String actLift = null;
    private String actTumpuk = null;

    /** Creates a new instance of EntryDeliveryServiceBean */
    public EntryDeliveryServiceBean() {
        registration = new Registration();
        registration.setMasterCustomer(new MasterCustomer());
        registration.setPlanningVessel(new PlanningVessel());
        registration.getPlanningVessel().setPreserviceVessel(new PreserviceVessel());
        registration.getPlanningVessel().getPreserviceVessel().setMasterVessel(new MasterVessel());
        deliveryService = new DeliveryService();
        deliveryService.setMasterContainerType(new MasterContainerType());
        deliveryService.setMasterCommodity(new MasterCommodity());
        handling = new PerhitunganHandlingDischarge();
        lift = new PerhitunganLiftService();
        lift.setRegistration(new Registration());
        lift.setMasterActivity(new MasterActivity());
        tumpuk = new PerhitunganPenumpukan();
        tumpuk.setRegistration(new Registration());
        tumpuk.setMasterActivity(new MasterActivity());
    }

    public void handleSelectRegistration(ActionEvent event) {
        no_reg = (String) event.getComponent().getAttributes().get("noReg");
        registration = registrationFacade.find(no_reg);
        deliveryServices = deliveryServiceFacade.findDeliveryServiceByPPKBnReg(registration.getPlanningVessel().getNoPpkb(), no_reg);
        serviceContDischarges = serviceContDischargeFacade.findServiceContDischargesDeliveryService(registration.getPlanningVessel().getNoPpkb());
    }

    public void handleAdd(ActionEvent event){
        deliveryService = new DeliveryService();
        deliveryService.setMasterContainerType(new MasterContainerType());
        deliveryService.setMasterCommodity(new MasterCommodity());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyMM");
        String tgl = dateFormat.format(Calendar.getInstance().getTime());
        String i;
        String id = deliveryServiceFacade.generateId(tgl.substring(2));
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
        deliveryService.setJobSlip("02"+i);
    }

    public void handleSelectContPick(ActionEvent event){
        Integer id_cont = (Integer) event.getComponent().getAttributes().get("idCont");
        ServiceContDischarge scd = serviceContDischargeFacade.find(id_cont);
        deliveryService.setContNo(scd.getContNo());
        deliveryService.setMlo(scd.getMlo());
        deliveryService.setContSize(scd.getContSize());
        deliveryService.setContGross(scd.getContGross());
        deliveryService.setContStatus(scd.getContStatus());
        deliveryService.setOverSize(scd.getOverSize());
        deliveryService.setDg(scd.getDangerous());
        deliveryService.setDgLabel(scd.getDgLabel());
        deliveryService.setCrane(scd.getCrane());
        deliveryService.setPlacementDate(scd.getStartPlacementDate());
        deliveryService.setMasterCommodity(scd.getMasterCommodity());
        deliveryService.setMasterContainerType(scd.getMasterContainerType());
        deliveryService.setSealNo(scd.getSealNo());
    }

    public void handleConfirm(ActionEvent event){
        boolean loggedIn = false;
        if(deliveryService.getContNo() == null)
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Warning", "application.transaction.notcompleted");
        else {
            Calendar now = Calendar.getInstance();
            int min = DateCalculator.countRangeInDays(now.getTime(), deliveryService.getPlacementDate()) + 1;
            if (now.getTime().getHours() > deliveryService.getPlacementDate().getHours()) {
                now.add(Calendar.DATE, 1);
                now.set(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DATE), deliveryService.getPlacementDate().getHours(), deliveryService.getPlacementDate().getMinutes(), deliveryService.getPlacementDate().getSeconds());
                deliveryService.setValidDate(now.getTime());
            } else if (now.getTime().getHours() == deliveryService.getPlacementDate().getHours()) {
                if (now.getTime().getMinutes() > deliveryService.getPlacementDate().getMinutes()) {
                    now.add(Calendar.DATE, 1);
                    now.set(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DATE), deliveryService.getPlacementDate().getHours(), deliveryService.getPlacementDate().getMinutes(), deliveryService.getPlacementDate().getSeconds());
                    deliveryService.setValidDate(now.getTime());
                } else if (now.getTime().getMinutes() == deliveryService.getPlacementDate().getMinutes()) {
                    if (now.getTime().getSeconds() > deliveryService.getPlacementDate().getSeconds()) {
                        now.add(Calendar.DATE, 1);
                        now.set(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DATE), deliveryService.getPlacementDate().getHours(), deliveryService.getPlacementDate().getMinutes(), deliveryService.getPlacementDate().getSeconds());
                        deliveryService.setValidDate(now.getTime());
                    } else if (now.getTime().getSeconds() == deliveryService.getPlacementDate().getSeconds()) {
                        now.set(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DATE), deliveryService.getPlacementDate().getHours(), deliveryService.getPlacementDate().getMinutes(), deliveryService.getPlacementDate().getSeconds());
                        deliveryService.setValidDate(now.getTime());
                    }
                }
            } else {
                now.set(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DATE), deliveryService.getPlacementDate().getHours(), deliveryService.getPlacementDate().getMinutes(), deliveryService.getPlacementDate().getSeconds());
                deliveryService.setValidDate(now.getTime());
            }

            MasterSettingApp settingApp = new MasterSettingApp();
            settingApp = masterSettingAppFacade.find("masa");

            int m1 = min - 2;
            if(min < 13){
                if(min <= settingApp.getValueInteger())
                    deliveryService.setMasa1(Short.parseShort("1"));
                else
                    deliveryService.setMasa1(Short.parseShort(String.valueOf(m1)));
                deliveryService.setMasa2(Short.parseShort("0"));
            } else {
                int masa2 = min - 12;
                int masa1 = m1 - masa2;
                deliveryService.setMasa1(Short.parseShort(String.valueOf(masa1)));
                deliveryService.setMasa2(Short.parseShort(String.valueOf(masa2)));
            }
            deliveryService.setRegistration(registration);
            deliveryService.setPlanningVessel(registration.getPlanningVessel());
            deliveryServiceFacade.edit(deliveryService);
            loggedIn = true;

            handleActHandling();
            handling.setNoReg(no_reg);
            handling.setNoPpkb(registration.getPlanningVessel().getNoPpkb());
            handling.setContNo(deliveryService.getContNo());
            handling.setMlo(deliveryService.getMlo());
            handling.setActivityCode(actHandling);

            // set amount berdasarkan tipe pelayaran
            BigDecimal cHandD = BigDecimal.ZERO;
            BigDecimal cHandI = BigDecimal.ZERO;
            List<Object[]> listTarif = new ArrayList<Object[]>();
            listTarif = masterTarifContFacade.findAmountByActivity(actHandling);
            for(Object[] o1 : listTarif){
                if(o1[1].equals("IDR"))
                    cHandD = (BigDecimal) o1[0];
                else
                    cHandI = (BigDecimal) o1[0];
            }

            handleActLift();
            lift.setRegistration(registration);
            lift.setNoPpkb(registration.getPlanningVessel().getNoPpkb());
            lift.setContNo(deliveryService.getContNo());
            lift.setMlo(deliveryService.getMlo());
            lift.setMasterActivity(masterActivityFacade.find(actLift));

            // set amount berdasarkan tipe pelayaran
            BigDecimal cLiftD = BigDecimal.ZERO;
            BigDecimal cLiftI = BigDecimal.ZERO;
            listTarif = masterTarifContFacade.findAmountByActivity(actLift);
            for(Object[] o1 : listTarif){
                if(o1[1].equals("IDR"))
                    cLiftD = (BigDecimal) o1[0];
                else
                    cLiftI = (BigDecimal) o1[0];
                lift.setCurrCode((String) o1[1]);
            }

            handleActTumpuk();
            tumpuk.setRegistration(registration);
            tumpuk.setNoPpkb(registration.getPlanningVessel().getNoPpkb());
            tumpuk.setContNo(deliveryService.getContNo());
            tumpuk.setMlo(deliveryService.getMlo());
            tumpuk.setMasterActivity(masterActivityFacade.find(actTumpuk));

            // set charge dan jasa dermaga berdasarkan tipe pelayaran
            BigDecimal cTumpukD = BigDecimal.ZERO;
            BigDecimal cTumpukI = BigDecimal.ZERO;
            BigDecimal jasaDerD = BigDecimal.ZERO;
            BigDecimal jasaDerI = BigDecimal.ZERO;

            listTarif = masterTarifContFacade.findAmountByActivity(actTumpuk);
            for(Object[] o1 : listTarif){
                if(o1[1].equals("IDR"))
                    cTumpukD = (BigDecimal) o1[0];
                else
                    cTumpukI = (BigDecimal) o1[0];
                tumpuk.setCurrCode((String) o1[1]);
            }

            BigDecimal cTumpuk = BigDecimal.ZERO;
            BigDecimal cHand = BigDecimal.ZERO;
            BigDecimal cLift = BigDecimal.ZERO;

            if(registration.getPlanningVessel().getTipePelayaran().equals("d")){
                cTumpuk = cTumpukD;
                cHand = cHandD;
                cLift = cLiftD;
                handling.setCurrCode("IDR");
                lift.setCurrCode("IDR");
                tumpuk.setCurrCode("IDR");
            } else {
                cTumpuk = cTumpukI;
                cHand = cHandI;
                cLift = cLiftI;
                handling.setCurrCode("USD");
                lift.setCurrCode("USD");
                tumpuk.setCurrCode("USD");
            }

            if(deliveryService.getDg().equals("TRUE") && deliveryService.getDgLabel().equals("TRUE"))
                lift.setCharge(cLift.multiply(BigDecimal.valueOf(2)));
            else if(deliveryService.getDg().equals("TRUE") && deliveryService.getDgLabel().equals("FALSE"))
                lift.setCharge(cLift.multiply(BigDecimal.valueOf(3)));
            else
                lift.setCharge(cLift);
            perhitunganLiftServiceFacade.create(lift);

            if(deliveryService.getDg().equals("TRUE") && deliveryService.getDgLabel().equals("TRUE"))
                handling.setCharge(cHand.multiply(BigDecimal.valueOf(2)));
            else if(deliveryService.getDg().equals("TRUE") && deliveryService.getDgLabel().equals("FALSE"))
                handling.setCharge(cHand.multiply(BigDecimal.valueOf(3)));
            else
                handling.setCharge(cHand);
            perhitunganHandlingDischargeFacade.create(handling);

            if(deliveryService.getDg().equals("TRUE") && deliveryService.getDgLabel().equals("TRUE"))
                tumpuk.setCharge(cTumpuk.multiply(BigDecimal.valueOf(2)));
            else if(deliveryService.getDg().equals("TRUE") && deliveryService.getDgLabel().equals("FALSE"))
                tumpuk.setCharge(cTumpuk.multiply(BigDecimal.valueOf(3)));
            else
                tumpuk.setCharge(cTumpuk);

            tumpuk.setChargeM1(tumpuk.getCharge().multiply(BigDecimal.valueOf(deliveryService.getMasa1())));
            tumpuk.setChargeM2(tumpuk.getCharge().multiply(BigDecimal.valueOf(deliveryService.getMasa2())).multiply(BigDecimal.valueOf(2)));
            tumpuk.setJasaDermaga(BigDecimal.ZERO);

            tumpuk.setTotalCharge(tumpuk.getChargeM1().add(tumpuk.getChargeM2()).add(tumpuk.getJasaDermaga()));
            perhitunganPenumpukanFacade.create(tumpuk);

            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
            RequestContext.getCurrentInstance().addCallbackParam("loggedIn", loggedIn);
            deliveryServices = deliveryServiceFacade.findDeliveryServiceByPPKBnReg(registration.getPlanningVessel().getNoPpkb(), no_reg);
            serviceContDischarges = serviceContDischargeFacade.findServiceContDischargesDeliveryService(registration.getPlanningVessel().getNoPpkb());
        }
    }

    public void handleActHandling(){
        if(deliveryService.getContStatus().equalsIgnoreCase("LCL")){
            if(deliveryService.getContSize() == 20){
                if(deliveryService.getCrane().equalsIgnoreCase("L")){
                    if(deliveryService.getOverSize().equals("FALSE"))
                        actHandling = "A013";
                    else
                        actHandling = "A014";
                }else if(deliveryService.getCrane().equalsIgnoreCase("S")){
                    if(deliveryService.getOverSize().equals("FALSE"))
                        actHandling = "A015";
                    else
                        actHandling = "A016";
                }
            }else if(deliveryService.getContSize() == 40){
                if(deliveryService.getCrane().equalsIgnoreCase("L")){
                    if(deliveryService.getOverSize().equals("FALSE"))
                        actHandling = "A017";
                    else
                        actHandling = "A018";
                }else if(deliveryService.getCrane().equalsIgnoreCase("S")){
                    if(deliveryService.getOverSize().equals("FALSE"))
                        actHandling = "A019";
                    else
                        actHandling = "A020";
                }
            }
        }else{
            if(deliveryService.getOverSize().equals("FALSE")){
                if(deliveryService.getContStatus().equalsIgnoreCase("FCL")){
                    if(deliveryService.getContSize() == 20){
                        if(deliveryService.getCrane().equalsIgnoreCase("L"))
                            actHandling = "A001";
                        if(deliveryService.getCrane().equalsIgnoreCase("S"))
                            actHandling = "A003";
                    }else if(deliveryService.getContSize() == 40){
                        if(deliveryService.getCrane().equalsIgnoreCase("L"))
                            actHandling = "A005";
                        if(deliveryService.getCrane().equalsIgnoreCase("S"))
                            actHandling = "A007";
                    }
                }else if(deliveryService.getContStatus().equalsIgnoreCase("MTY")){
                    if(deliveryService.getContSize() == 20){
                        if(deliveryService.getCrane().equalsIgnoreCase("L"))
                            actHandling = "A009";
                        if(deliveryService.getCrane().equalsIgnoreCase("S"))
                            actHandling = "A010";
                    }else if(deliveryService.getContSize() == 40){
                        if(deliveryService.getCrane().equalsIgnoreCase("L"))
                            actHandling = "A011";
                        if(deliveryService.getCrane().equalsIgnoreCase("S"))
                            actHandling = "A012";
                    }
                }
            }else{
                if (deliveryService.getContSize() == 20) {
                    if (deliveryService.getCrane().equalsIgnoreCase("L"))
                        actHandling = "A002";
                    if (deliveryService.getCrane().equalsIgnoreCase("S"))
                        actHandling = "A004";
                } else if (deliveryService.getContSize() == 40) {
                    if (deliveryService.getCrane().equalsIgnoreCase("L"))
                        actHandling = "A006";
                    if (deliveryService.getCrane().equalsIgnoreCase("S"))
                        actHandling = "A008";
                }
            }
        }
    }

    public void handleActLift(){
        if(deliveryService.getOverSize().equals("FALSE")){
            if(deliveryService.getContSize() == 20){
                if(deliveryService.getContStatus().equalsIgnoreCase("FCL") || deliveryService.getContStatus().equalsIgnoreCase("LCL"))
                    actLift = "B001";
                else if(deliveryService.getContStatus().equalsIgnoreCase("MTY"))
                    actLift = "B002";
            }else if(deliveryService.getContSize() == 40){
                if(deliveryService.getContStatus().equalsIgnoreCase("FCL") || deliveryService.getContStatus().equalsIgnoreCase("LCL"))
                    actLift = "B003";
                else if(deliveryService.getContStatus().equalsIgnoreCase("MTY"))
                    actLift = "B004";
            }
        }else{
            if (deliveryService.getContSize() == 20)
                actLift = "B005";
            else if (deliveryService.getContSize() == 40)
                actLift = "B006";
        }
    }

    public void handleActTumpuk(){
        if(deliveryService.getMasterContainerType().getIsoCode().equals("2230") || deliveryService.getMasterContainerType().getIsoCode().equals("2232") ||
           deliveryService.getMasterContainerType().getIsoCode().equals("2242") || deliveryService.getMasterContainerType().getIsoCode().equals("4330")){
            if (deliveryService.getContSize() == 20)
                actTumpuk = "C007";
            else if (deliveryService.getContSize() == 40)
                actTumpuk = "C008";
        }else{
            if (deliveryService.getOverSize().equals("FALSE")) {
                if(deliveryService.getContStatus().equalsIgnoreCase("MTY")){
                    if (deliveryService.getContSize() == 20)
                        actTumpuk = "C001";
                    else if (deliveryService.getContSize() == 40)
                        actTumpuk = "C002";
                }else if(deliveryService.getContStatus().equalsIgnoreCase("FCL") || deliveryService.getContStatus().equalsIgnoreCase("LCL")){
                    if (deliveryService.getContSize() == 20)
                        actTumpuk = "C003";
                    else if (deliveryService.getContSize() == 40)
                        actTumpuk = "C004";
                }
            } else {
                if (deliveryService.getContSize() == 20)
                    actTumpuk = "C005";
                else if (deliveryService.getContSize() == 40)
                    actTumpuk = "C006";
            }
        }
    }

    public void handleEditDelete(ActionEvent event){
        String job_slip = (String) event.getComponent().getAttributes().get("jobSlip");
        String no_cont = (String) event.getComponent().getAttributes().get("noCont");
        deliveryService = deliveryServiceFacade.find(job_slip);
        Integer idHan = perhitunganHandlingDischargeFacade.findInvoice(no_cont, registration.getPlanningVessel().getNoPpkb(), no_reg);
        handling = perhitunganHandlingDischargeFacade.find(idHan);
        Integer idLift = perhitunganLiftServiceFacade.findInvoice(no_cont, registration.getPlanningVessel().getNoPpkb(), no_reg);
        lift = perhitunganLiftServiceFacade.find(idLift);
        Integer idTump = perhitunganPenumpukanFacade.findInvoice(no_cont, registration.getPlanningVessel().getNoPpkb(), no_reg);
        tumpuk = perhitunganPenumpukanFacade.find(idTump);
    }

    public void handleDelete(ActionEvent event){
        deliveryServiceFacade.remove(deliveryService);
        perhitunganHandlingDischargeFacade.remove(handling);
        perhitunganLiftServiceFacade.remove(lift);
        perhitunganPenumpukanFacade.remove(tumpuk);
        FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.delete.succeeded");
        deliveryServices = deliveryServiceFacade.findDeliveryServiceByPPKBnReg(registration.getPlanningVessel().getNoPpkb(), no_reg);
        serviceContDischarges = serviceContDischargeFacade.findServiceContDischargesDeliveryService(registration.getPlanningVessel().getNoPpkb());
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

    private DeliveryServiceFacadeRemote lookupDeliveryServiceFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (DeliveryServiceFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/DeliveryServiceFacade!com.pelindo.ebtos.ejb.facade.remote.DeliveryServiceFacadeRemote");
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

    private PerhitunganHandlingDischargeFacadeRemote lookupPerhitunganHandlingDischargeFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (PerhitunganHandlingDischargeFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/PerhitunganHandlingDischargeFacade!com.pelindo.ebtos.ejb.facade.remote.PerhitunganHandlingDischargeFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private PerhitunganLiftServiceFacadeRemote lookupPerhitunganLiftServiceFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (PerhitunganLiftServiceFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/PerhitunganLiftServiceFacade!com.pelindo.ebtos.ejb.facade.remote.PerhitunganLiftServiceFacadeRemote");
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

    private MasterSettingAppFacadeRemote lookupMasterSettingAppFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (MasterSettingAppFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/MasterSettingAppFacade!com.pelindo.ebtos.ejb.facade.remote.MasterSettingAppFacadeRemote");
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

}
