/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.bean;

import com.qtasnim.jsf.FacesHelper;
import com.pelindo.ebtos.ejb.facade.remote.MasterCustomerFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterTarifContFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterTarifHistoryFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PerhitunganStuffingFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.RegistrationFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceContDischargeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceReceivingFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.StuffingServiceFacadeRemote;
import com.pelindo.ebtos.model.db.PerhitunganStuffing;
import com.pelindo.ebtos.model.db.PlanningVessel;
import com.pelindo.ebtos.model.db.PreserviceVessel;
import com.pelindo.ebtos.model.db.Registration;
import com.pelindo.ebtos.model.db.StuffingService;
import com.pelindo.ebtos.model.db.master.MasterCustomer;
import com.pelindo.ebtos.model.db.master.MasterVessel;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
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
@ManagedBean(name="entryStuffingBean")
@ViewScoped
public class EntryStuffingBean implements Serializable{
//    @EJB
//    private MasterTarifContFacadeRemote masterTarifContFacade;
//    @EJB
//    private PerhitunganStuffingFacadeRemote perhitunganStuffingFacade;
//    @EJB
//    private ServiceReceivingFacadeRemote serviceReceivingFacadeRemote;
//    @EJB
//    private StuffingServiceFacadeRemote stuffingServiceFacade;
//    @EJB
//    private ServiceContDischargeFacadeRemote serviceContDischargeFacade;
//    @EJB
//    private RegistrationFacadeRemote registrationFacade;
//    @EJB
//    private MasterCustomerFacadeRemote masterCustomerFacadeRemote;
//
//    private List<Object[]> registrations;
//    private List<Object[]> stuffingServices;
//    private List<Object[]> serviceContDischarges;
//
//    private Registration registration;
//    private StuffingService stuffingService;
//    private PerhitunganStuffing stuffing;
//    private String no_reg;

    /** Creates a new instance of EntryStuffingBean */
    public EntryStuffingBean() { }
//
//    @PostConstruct
//    private void construct() {
//        registration = new Registration();
//        registration.setMasterCustomer(new MasterCustomer());
//        registration.setPlanningVessel(new PlanningVessel());
//        registration.getPlanningVessel().setPreserviceVessel(new PreserviceVessel());
//        registration.getPlanningVessel().getPreserviceVessel().setMasterVessel(new MasterVessel());
//        serviceContDischarges = new ArrayList<Object[]>();
//        stuffing = new PerhitunganStuffing();
//        registrations = registrationFacade.findRegistrationStuffing();
//    }
//
//    public void handleSelectRegistration(ActionEvent event) {
//        no_reg = (String) event.getComponent().getAttributes().get("noReg");
//        registration = registrationFacade.find(no_reg);
//        stuffingServices = stuffingServiceFacade.findStuffingServiceByPPKBnReg(no_reg);
//
//        serviceContDischarges = new ArrayList<Object[]>();
//        List<Object[]> listdisc = serviceContDischargeFacade.findServiceContDischargesStuffingService(registration.getPlanningVessel().getNoPpkb());
//        List<Object[]> listrec = serviceReceivingFacadeRemote.findServiceReceivingsStuffingService(registration.getPlanningVessel().getNoPpkb());
//        if (listdisc != null) {
//            for (Object[] o : listdisc) {
//                serviceContDischarges.add(o);
//            }
//        }
//        if (listrec != null) {
//            for (Object[] o1 : listrec) {
//                serviceContDischarges.add(o1);
//            }
//        }
//    }
//
//    public void handleAdd(ActionEvent event){
//        stuffingService = new StuffingService();
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyMM");
//        String tgl = dateFormat.format(Calendar.getInstance().getTime());
//        String i;
//        String id = stuffingServiceFacade.generateId(tgl.substring(2));
//        if(id == null)
//            i = "00001";
//        else
//            i = String.valueOf(Integer.valueOf(id) + 1);
//
//        if(i.length() == 1)
//            i = tgl+"0000"+i;
//        else if(i.length() == 2)
//            i = tgl+"000"+i;
//        else if(i.length() == 3)
//            i = tgl+"00"+i;
//        else if(i.length() == 4)
//            i = tgl+"0"+i;
//        else
//            i = tgl + i;
//        stuffingService.setJobSlip("06"+i);
//    }
//
//    public void handleSelectContPick(ActionEvent event){
//        String no_cont = (String) event.getComponent().getAttributes().get("noCont");
//        Integer size_cont = (Integer) event.getComponent().getAttributes().get("size");
//        String type_cont = (String) event.getComponent().getAttributes().get("type");
//        String status_cont = (String) event.getComponent().getAttributes().get("status");
//        Integer gross_cont = (Integer) event.getComponent().getAttributes().get("gross");
//        String commodity = (String) event.getComponent().getAttributes().get("com");
//        Boolean oversize = (Boolean) event.getComponent().getAttributes().get("ov");
//        Boolean dg = (Boolean) event.getComponent().getAttributes().get("dg");
//        Boolean dg_label = (Boolean) event.getComponent().getAttributes().get("dgL");
//        String blNo = (String) event.getComponent().getAttributes().get("blNo");
//        String _mlo = (String) event.getComponent().getAttributes().get("mlo");
//        MasterCustomer mlo = null;
//        if (_mlo != null) {
//            mlo = masterCustomerFacadeRemote.find(_mlo);
//        }
//
//        stuffingService.setContNo(no_cont);
//        stuffingService.setContSize(Short.parseShort(String.valueOf(size_cont)));
//        stuffingService.setContGross(gross_cont);
//        stuffingService.setContStatus(status_cont);
//        stuffingService.setOverSize(oversize);
//        stuffingService.setDg(dg);
//        stuffingService.setBlNo(blNo);
//        stuffingService.setMlo(mlo);
//        stuffingService.setDgLabel(dg_label);
//    }
//
//    public void handleEditDelete(ActionEvent event){
//        String job_slip = (String) event.getComponent().getAttributes().get("jobSlip");
//        String no_cont = (String) event.getComponent().getAttributes().get("noCont");
//        stuffingService = stuffingServiceFacade.find(job_slip);
//        Integer idStuf = perhitunganStuffingFacade.findInvoice(no_cont, registration.getPlanningVessel().getNoPpkb(), no_reg);
//        stuffing = perhitunganStuffingFacade.find(idStuf);
//    }
//
//    public void handleDelete(ActionEvent event){
//        stuffingServiceFacade.remove(stuffingService);
//        perhitunganStuffingFacade.remove(stuffing);
//        FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.delete.succeeded");
//        stuffingServices = stuffingServiceFacade.findStuffingServiceByPPKBnReg(registration.getPlanningVessel().getNoPpkb(), no_reg);
//
//        serviceContDischarges = new ArrayList<Object[]>();
//        List<Object[]> listdisc = serviceContDischargeFacade.findServiceContDischargesStuffingService(registration.getPlanningVessel().getNoPpkb());
//        List<Object[]> listrec = serviceReceivingFacadeRemote.findServiceReceivingsStuffingService(registration.getPlanningVessel().getNoPpkb());
//        if (listdisc != null) {
//            for (Object[] o : listdisc) {
//                serviceContDischarges.add(o);
//            }
//        }
//        if (listrec != null) {
//            for (Object[] o1 : listrec) {
//                serviceContDischarges.add(o1);
//            }
//        }
//    }
//
//    public void handleConfirm(ActionEvent event){
//        boolean loggedIn = false;
//        if(stuffingService.getContNo() == null)
//            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Warning", "application.transaction.notcompleted");
//        else {
//            stuffingService.setNoReg(no_reg);
//            stuffingService.setNoPpkb(registration.getPlanningVessel().getNoPpkb());
//            stuffingServiceFacade.edit(stuffingService);
//            loggedIn = true;
//
//            // input perhitungan stuffing
//            stuffing.setNoReg(no_reg);
//            stuffing.setNoPpkb(registration.getPlanningVessel().getNoPpkb());
//            stuffing.setContNo(stuffingService.getContNo());
//            stuffing.setMlo(stuffingService.getMlo());
//            if(stuffingService.getContSize() == 20)
//                stuffing.setActivityCode("K001");
//            else if(stuffingService.getContSize() == 40)
//                stuffing.setActivityCode("K002");
//
//            // set amount berdasarkan tipe pelayaran
//            BigDecimal cStrD = BigDecimal.ZERO;
//            BigDecimal cStrI = BigDecimal.ZERO;
//            for(Object[] o1 : masterTarifContFacade.findAmountByActivity(stuffing.getActivityCode())){
//                if(o1[1].equals("IDR"))
//                    cStrD = (BigDecimal) o1[0];
//                else
//                    cStrI = (BigDecimal) o1[0];
//            }
//            BigDecimal cStr = BigDecimal.ZERO;
//            if (registration.getPlanningVessel().getTipePelayaran().equals("d")) {
//                cStr = cStrD;
//                stuffing.setCurrCode("IDR");
//            } else {
//                cStr = cStrI;
//                stuffing.setCurrCode("USD");
//            }
//
//            stuffing.setCharge(cStr);
//            perhitunganStuffingFacade.create(stuffing);
//            stuffingServices = stuffingServiceFacade.findStuffingServiceByPPKBnReg(registration.getPlanningVessel().getNoPpkb(), no_reg);
//
//            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
//            RequestContext.getCurrentInstance().addCallbackParam("loggedIn", loggedIn);
//
//            serviceContDischarges = new ArrayList<Object[]>();
//            List<Object[]> listdisc = serviceContDischargeFacade.findServiceContDischargesStuffingService(registration.getPlanningVessel().getNoPpkb());
//            List<Object[]> listrec = serviceReceivingFacadeRemote.findServiceReceivingsStuffingService(registration.getPlanningVessel().getNoPpkb());
//            if (listdisc != null) {
//                for (Object[] o : listdisc) {
//                    serviceContDischarges.add(o);
//                }
//            }
//            if (listrec != null) {
//                for (Object[] o1 : listrec) {
//                    serviceContDischarges.add(o1);
//                }
//            }
//        }
//    }
//
//    /**
//     * @return the registrations
//     */
//    public List<Object[]> getRegistrations() {
//        return registrations;
//    }
//
//    /**
//     * @param registrations the registrations to set
//     */
//    public void setRegistrations(List<Object[]> registrations) {
//        this.registrations = registrations;
//    }
//
//    /**
//     * @return the stuffingServices
//     */
//    public List<Object[]> getStuffingServices() {
//        return stuffingServices;
//    }
//
//    /**
//     * @param stuffingServices the stuffingServices to set
//     */
//    public void setStuffingServices(List<Object[]> stuffingServices) {
//        this.stuffingServices = stuffingServices;
//    }
//
//    /**
//     * @return the serviceContDischarges
//     */
//    public List<Object[]> getServiceContDischarges() {
//        return serviceContDischarges;
//    }
//
//    /**
//     * @param serviceContDischarges the serviceContDischarges to set
//     */
//    public void setServiceContDischarges(List<Object[]> serviceContDischarges) {
//        this.serviceContDischarges = serviceContDischarges;
//    }
//
//    /**
//     * @return the registration
//     */
//    public Registration getRegistration() {
//        return registration;
//    }
//
//    /**
//     * @param registration the registration to set
//     */
//    public void setRegistration(Registration registration) {
//        this.registration = registration;
//    }
//
//    /**
//     * @return the stuffingService
//     */
//    public StuffingService getStuffingService() {
//        return stuffingService;
//    }
//
//    /**
//     * @param stuffingService the stuffingService to set
//     */
//    public void setStuffingService(StuffingService stuffingService) {
//        this.stuffingService = stuffingService;
//    }

}
