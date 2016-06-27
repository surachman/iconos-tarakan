/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.bean;

import com.qtasnim.jsf.FacesHelper;
import com.pelindo.ebtos.ejb.facade.remote.AngsurServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterActivityFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterTarifContFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterTarifHistoryFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PerhitunganAngsurFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.RegistrationFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceContDischargeFacadeRemote;
import com.pelindo.ebtos.model.db.AngsurService;
import com.pelindo.ebtos.model.db.PerhitunganAngsur;
import com.pelindo.ebtos.model.db.Registration;
import com.pelindo.ebtos.model.db.ServiceContDischarge;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.context.RequestContext;

/**
 *
 * @author dycode-java
 */
@ManagedBean(name = "entryAngsurPetikemasBean")
@ViewScoped
public class EntryAngsurPetikemasBean implements Serializable {

    @EJB
    PerhitunganAngsurFacadeRemote perhitunganAngsurFacadeRemote;
    @EJB
    AngsurServiceFacadeRemote angsurServiceFacadeRemote;
    @EJB
    MasterTarifContFacadeRemote masterTarifContFacadeRemote;
    @EJB
    MasterActivityFacadeRemote masterActivityFacadeRemote;
    MasterTarifHistoryFacadeRemote masterTarifHistoryFacade = lookupMasterTarifHistoryFacadeRemote();
    ServiceContDischargeFacadeRemote serviceContDischargeFacade = lookupServiceContDischargeFacadeRemote();
    RegistrationFacadeRemote registrationFacade = lookupRegistrationFacadeRemote();
    private List<Object[]> registrations = lookupRegistrationFacadeRemote().findRegistrationAngsur();
    private Registration registration;
    private AngsurService angsurService;
    private List<Object[]> angsurServices;
    private List<Object[]> serviceContDischarges;
    private String no_reg;
    private String actLift = null;
    private PerhitunganAngsur perhitunganAngsur;
    private String currency;

    /** Creates a new instance of EntryAngsurPetikemasBean */
    public EntryAngsurPetikemasBean() {
        angsurService = new AngsurService();
        registration = new Registration();
        perhitunganAngsur = new PerhitunganAngsur();
    }

    public void handleSelectRegistration(ActionEvent event) {
        setNo_reg((String) event.getComponent().getAttributes().get("noReg"));
        registration = registrationFacade.find(getNo_reg());
        angsurServices = angsurServiceFacadeRemote.findAngsurServiceByPPKBnReg(registration.getPlanningVessel().getNoPpkb(), getNo_reg());
        serviceContDischarges = serviceContDischargeFacade.findServiceContDischargesAngsurService(registration.getPlanningVessel().getNoPpkb());
    }

    public void clear() {
        angsurService = new AngsurService();
    }

    public String getGenerateCode() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyMM");
        String tgl = dateFormat.format(Calendar.getInstance().getTime());
        String tempCode;
        String temp = angsurServiceFacadeRemote.generateId(tgl.substring(4));
        if (temp == null) {
            tempCode = "00001";
        } else {
            tempCode = String.valueOf(Integer.valueOf(temp) + 1);
        }
        String comCod = "";
        switch (tempCode.length()) {
            case 1:
                tempCode = "0000" + tempCode;
                break;
            case 2:
                tempCode = "000" + tempCode;
                break;
            case 3:
                tempCode = "00" + tempCode;
                break;
            case 4:
                tempCode = "0" + tempCode;
                break;
        }
        comCod = "17" + tgl + tempCode;
        return comCod;
    }

    public void handleSelectContPick(ActionEvent event) {
        Integer id_cont = (Integer) event.getComponent().getAttributes().get("idCont");
        ServiceContDischarge scd = serviceContDischargeFacade.find(id_cont);
        angsurService.setContNo(scd.getContNo());
        angsurService.setMlo(scd.getMlo());
        angsurService.setContSize(scd.getContSize());
        angsurService.setContGross(scd.getContGross());
        angsurService.setContStatus(scd.getContStatus());
        angsurService.setOverSize(scd.getOverSize());
        angsurService.setDg(scd.getDangerous());
        angsurService.setDgLabel(scd.getDgLabel());
        angsurService.setMasterCommodity(scd.getMasterCommodity());
        angsurService.setMasterContainerType(scd.getMasterContainerType());
        angsurService.setRegistration(registration);
        angsurService.setPlanningVessel(registration.getPlanningVessel());
        angsurService.setBlNo(scd.getBlNo());
    }

    public void handleActLift() {
        if (angsurService.getSpecialEquipment().equals("FALSE")) {
            if (angsurService.getContSize() == 20) {
                setActLift("E001");
            } else if (angsurService.getContSize() == 40) {
                setActLift("E002");
            }
        } else {
            if (angsurService.getContSize() == 20) {
                setActLift("E003");
            } else if (angsurService.getContSize() == 40) {
                setActLift("E004");
            }
        }
    }

    public void handleConfirm(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();

        boolean loggedIn = false;
        if (angsurService.getContNo() == null) {
            loggedIn = false;
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.failed");
        } else {
            handleActLift();
            loggedIn = true;
            BigDecimal cLiftD = BigDecimal.ZERO;
            BigDecimal cLiftI = BigDecimal.ZERO;

            for (Object[] o1 : masterTarifContFacadeRemote.findAmountByActivity(actLift)) {
                if (o1[1].equals("IDR")) {
                    //cLiftD = (Integer) o1[0];
                    cLiftD = ((BigDecimal) (o1[0]));
                } else if (o1[1].equals("USD")) {
                    cLiftI = ((BigDecimal) (o1[0]));
                }
            }

            BigDecimal cLift = BigDecimal.ZERO;
            if (angsurService.getPlanningVessel().getTipePelayaran().equals("d")) {
                cLift = cLiftD;
                setCurrency("IDR");
            } else if (angsurService.getPlanningVessel().getTipePelayaran().equals("i")) {
                cLift = cLiftI;
                setCurrency("USD");
            }

            if (angsurService.getDg().equals("TRUE") && angsurService.getDgLabel().equals("TRUE")) {
                //perhitunganAngsur.setCharge(cLift * 2);
                perhitunganAngsur.setCharge(cLift.multiply(BigDecimal.valueOf(2)));
            } else if (angsurService.getDg().equals("TRUE") && angsurService.getDgLabel().equals("FALSE")) {
                perhitunganAngsur.setCharge(cLift.multiply(BigDecimal.valueOf(3)));
                //perhitunganAngsur.setCharge(cLift * 3);
            } else {
                perhitunganAngsur.setCharge(cLift);
            }


            perhitunganAngsur.setContNo(angsurService.getContNo());
            perhitunganAngsur.setMlo(angsurService.getMlo());
            perhitunganAngsur.setNoPpkb(angsurService.getPlanningVessel().getNoPpkb());
            perhitunganAngsur.setRegistration(angsurService.getRegistration());
            perhitunganAngsur.setMasterActivity(masterActivityFacadeRemote.find(getActLift()));

            angsurService.setJobSlip(getGenerateCode());
            angsurService.setRegistration(registration);
            angsurService.setPlanningVessel(registration.getPlanningVessel());
            angsurServiceFacadeRemote.create(angsurService);
            perhitunganAngsur.setCurrCode(currency);
            perhitunganAngsurFacadeRemote.create(perhitunganAngsur);
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
            angsurServices = angsurServiceFacadeRemote.findAngsurServiceByPPKBnReg(registration.getPlanningVessel().getNoPpkb(), getNo_reg());
            serviceContDischarges = serviceContDischargeFacade.findServiceContDischargesAngsurService(registration.getPlanningVessel().getNoPpkb());
        }

        context.addCallbackParam("loggedIn", loggedIn);
        this.clear();
    }

    public void handleEditDelete(ActionEvent event) {

        String job_slip = (String) event.getComponent().getAttributes().get("jobSlip");
        angsurService = angsurServiceFacadeRemote.find(job_slip);
        int id = perhitunganAngsurFacadeRemote.findPerhitunganAngsurId(angsurService.getPlanningVessel().getNoPpkb(), angsurService.getRegistration().getNoReg(), angsurService.getContNo());
        perhitunganAngsur = perhitunganAngsurFacadeRemote.find(id);
    }

    public void handleAdd(ActionEvent actionEvent) {
        this.clear();
    }

    public void handleDelete(ActionEvent event) {
        try {
            angsurServiceFacadeRemote.remove(angsurService);
            perhitunganAngsurFacadeRemote.remove(perhitunganAngsur);
            angsurServices = angsurServiceFacadeRemote.findAngsurServiceByPPKBnReg(registration.getPlanningVessel().getNoPpkb(), getNo_reg());
            serviceContDischarges = serviceContDischargeFacade.findServiceContDischargesAngsurService(registration.getPlanningVessel().getNoPpkb());
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.delete.succeeded");
            this.clear();
        } catch (EJBException ex) {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.delete.failed");
            ex.printStackTrace();
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
     * @return the angsurService
     */
    public AngsurService getAngsurService() {
        return angsurService;
    }

    /**
     * @param angsurService the angsurService to set
     */
    public void setAngsurService(AngsurService angsurService) {
        this.angsurService = angsurService;
    }

    /**
     * @return the angsurServices
     */
    public List<Object[]> getAngsurServices() {
        return angsurServices;
    }

    /**
     * @param angsurServices the angsurServices to set
     */
    public void setAngsurServices(List<Object[]> angsurServices) {
        this.angsurServices = angsurServices;
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

    private ServiceContDischargeFacadeRemote lookupServiceContDischargeFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (ServiceContDischargeFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/ServiceContDischargeFacade!com.pelindo.ebtos.ejb.facade.remote.ServiceContDischargeFacadeRemote");
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
     * @return the serviceContDischarges
     */
    public List<Object[]> getServiceContDischarges() {
        return serviceContDischarges;
    }

    /**
     * @param serviceContDischarges the serviceContDischarges to set
     */
    public void setServiceContDischarges(List<Object[]> serviceContDischarges) {
        this.setServiceContDischarges(serviceContDischarges);
    }

    /**
     * @return the no_reg
     */
    public String getNo_reg() {
        return no_reg;
    }

    /**
     * @param no_reg the no_reg to set
     */
    public void setNo_reg(String no_reg) {
        this.no_reg = no_reg;
    }

    /**
     * @return the actLift
     */
    public String getActLift() {
        return actLift;
    }

    /**
     * @param actLift the actLift to set
     */
    public void setActLift(String actLift) {
        this.actLift = actLift;
    }

    /**
     * @return the perhitunganAngsur
     */
    public PerhitunganAngsur getPerhitunganAngsur() {
        return perhitunganAngsur;
    }

    /**
     * @param perhitunganAngsur the perhitunganAngsur to set
     */
    public void setPerhitunganAngsur(PerhitunganAngsur perhitunganAngsur) {
        this.perhitunganAngsur = perhitunganAngsur;
    }

    /**
     * @return the currency
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * @param currency the currency to set
     */
    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
