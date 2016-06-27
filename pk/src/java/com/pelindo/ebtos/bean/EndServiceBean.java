/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.bean;

import com.qtasnim.jsf.FacesHelper;
import com.pelindo.ebtos.ejb.facade.PlanningContLoadFacade;
import com.pelindo.ebtos.ejb.facade.remote.EdifactFactoryFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.IDGeneratorFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.NoPerhitunganBentukTigaFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningContLoadFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceContDischargeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceContLoadFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceContTranshipmentFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceShiftingFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceVesselFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.TempContDischargeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.UcShiftingServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.UncontainerizedServiceFacadeRemote;
import com.pelindo.ebtos.model.db.NoPerhitunganBentukTiga;
import com.pelindo.ebtos.model.db.PlanningContLoad;
import com.pelindo.ebtos.model.db.ServiceContDischarge;
import com.pelindo.ebtos.model.db.ServiceContLoad;
import com.pelindo.ebtos.model.db.ServiceContTranshipment;
import com.pelindo.ebtos.model.db.ServiceShifting;
import com.pelindo.ebtos.model.db.ServiceVessel;
import com.pelindo.ebtos.model.db.UcShiftingService;
import com.pelindo.ebtos.model.db.UncontainerizedService;
import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.util.ArrayList;
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
import org.primefaces.context.RequestContext;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import org.primefaces.event.DateSelectEvent;
import org.apache.commons.lang3.StringEscapeUtils;
import org.primefaces.component.commandbutton.CommandButton;

/**
 *
 * @author dycoder
 */
@ManagedBean(name = "endServiceBean")
@ViewScoped
public class EndServiceBean implements Serializable{
    private static String LIST = "list";
    private static String DETAIL = "detail";
    
    @EJB
    private TempContDischargeFacadeRemote tempContDischargeFacade;
    @EJB
    private ServiceVesselFacadeRemote serviceVesselFacade;
    @EJB
    private ServiceContDischargeFacadeRemote serviceContDischargeFacade;
    @EJB
    private ServiceContLoadFacadeRemote serviceContLoadFacade;
    @EJB
    private ServiceContTranshipmentFacadeRemote serviceContTranshipmentFacade;
    @EJB
    private ServiceShiftingFacadeRemote serviceShiftingFacade;
    @EJB
    private UcShiftingServiceFacadeRemote ucShiftingServiceFacade;
    @EJB
    private UncontainerizedServiceFacadeRemote uncontainerizedServiceFacade;
    @EJB
    private EdifactFactoryFacadeRemote edifactFactoryFacadeRemote;
    @EJB
    private PlanningContLoadFacadeRemote planningContLoadFacadeRemote;
    @EJB
    private NoPerhitunganBentukTigaFacadeRemote noPerhitunganBentukTigaFacadeRemote;
    @EJB
    private IDGeneratorFacadeRemote iDGeneratorFacade;
    
    
    private String pageState;
    private String messageDischarge;
    private String messageLoad;
    private String messageShifting;
    private String messageTranshipment;
    private String messageShiftingUc;
    private String messageUncontainerized;
    private String messageAll;
    private Integer countDischarge;
    private Integer countLoad;
    private Integer countShifting;
    private Integer countTranshipment;
    private Integer countShiftingUc;
    private Integer countUncontainerized;
    private Integer countServiceVesselConfirm;
    private List<Object[]> serviceVessels;
    private ServiceVessel serviceVessel;
    private StreamedContent file;
    private Object[] sv;
    private Date startDate;
    private Date endDate;
    private String username;
    private NoPerhitunganBentukTiga noPerhitunganBentukTiga;

    /** Creates a new instance of EndServiceBean */
    public EndServiceBean() {}
    
    @PostConstruct
    private void construct(){
        serviceVessel = new ServiceVessel();
        pageState = EndServiceBean.LIST;
        serviceVessels = serviceVesselFacade.findServiceVesselStatusService();
        noPerhitunganBentukTiga = new NoPerhitunganBentukTiga();
    }

    public void handleSelectTable(ActionEvent event) {       
        String no_ppkb = (String) event.getComponent().getAttributes().get("noPpkb");
        serviceVessel = serviceVesselFacade.find(no_ppkb);
    }
    
    //penambahan pengecekan service vessel confirm by ade chelsea tanggal 04-07-2014 jam 08:56
    public void handleCekServiceVesselConfirm(ActionEvent event) {
        String no_ppkb = (String) event.getComponent().getAttributes().get("noPpkb");
        countServiceVesselConfirm = serviceVesselFacade.getCountServiceVesselConfirm(no_ppkb);
    }

    public void handleDownloadBaplie(ActionEvent event) {
        String noPpkb = (String) event.getComponent().getAttributes().get("noPpkb");

        try {
            String[] message = edifactFactoryFacadeRemote.getBaplieByNoPpkb(noPpkb);
            file = new DefaultStreamedContent(new ByteArrayInputStream(message[1].toString().getBytes()), "application/edi", message[0] + ".edi");
        } catch (RuntimeException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void handlePreview(ActionEvent event) {
        String no_ppkb = (String) event.getComponent().getAttributes().get("noPpkb");
        sv = serviceVesselFacade.findServiceVesselDetail(no_ppkb);
        pageState = EndServiceBean.DETAIL;
    }

    public Boolean isNotValid(){
        Boolean isNotValid = false;
        countDischarge = 0;
        countLoad = 0;
        countShifting = 0;
        countShiftingUc = 0;
        countTranshipment = 0;
        countUncontainerized = 0;
        messageDischarge = "";
        messageLoad = "";
        messageShifting = "";
        messageShiftingUc = "";
        messageTranshipment = "";
        messageUncontainerized = "";

        // Cek container discharge yang belum sampai CY
        List<ServiceContDischarge> serviceContDischarges = serviceContDischargeFacade.findContainersThatHaveNotReachedCY(serviceVessel.getNoPpkb());
        for (ServiceContDischarge serviceContDischarge : serviceContDischarges) {
            isNotValid = isNotValid || true;
            String location = "VESSEL";

            if (serviceContDischarge.getPosition().equals(ServiceContDischarge.LOCATION_HT)) {
                location = "HT";
            }

            messageDischarge = messageDischarge + "- " + serviceContDischarge.getContNo() + " (" + location + ")" + "<br/>";
            countDischarge++;
        }


        // cek kontainer yang belum di load
        List<PlanningContLoad> planningContLoads = planningContLoadFacadeRemote.findContainersThatHaveNotBeenLoaded(serviceVessel.getNoPpkb());
        for (PlanningContLoad planningContLoad: planningContLoads) {
            isNotValid = isNotValid || true;
            String location = "CY";

            if (planningContLoad.getPosition().equals(PlanningContLoad.LOCATION_HT)) {
                location = "HT";
            } else if (planningContLoad.getPosition().equals(PlanningContLoad.LOCATION_MOVEMENT)) {
                location = "MV";
            }

            messageLoad = messageLoad + "- " + planningContLoad.getContNo() + " (" + location + ")" + "<br/>";
            countLoad++;
        }

        //cek service cont transhipment
        List<ServiceContTranshipment> serviceContTranshipments = serviceContTranshipmentFacade.findContainersThatHaveNotReachedCY(serviceVessel.getNoPpkb());
        for (ServiceContTranshipment serviceContTranshipment: serviceContTranshipments) {
            isNotValid = isNotValid || true;

            String location = "VESSEL";

            if (serviceContTranshipment.getPosition().equals("02")) {
                location = "HT";
            }

            messageTranshipment = messageTranshipment + "- " + serviceContTranshipment.getContNo() + " (" + location + ")" + "<br/>";
            countTranshipment++;
        }

        //cek service shifting
        List<ServiceShifting> serviceShiftings = serviceShiftingFacade.findByNotReshippedShiftingByNoPpkb(serviceVessel.getNoPpkb());
        for (ServiceShifting serviceShifting: serviceShiftings) {
            isNotValid = isNotValid || true;
            messageShifting = messageShifting + "- " + serviceShifting.getContNo() + "<br/>";
            countShifting++;
        }

        //cek service shifting uc
        List<UcShiftingService> ucShiftingServices = ucShiftingServiceFacade.findByNoPpkbAndReshippingStatus(serviceVessel.getNoPpkb(), false);
        for (UcShiftingService ucShiftingService: ucShiftingServices) {
            isNotValid = isNotValid || true;
            messageShiftingUc = messageShiftingUc + "- " + ucShiftingService.getBlNo() + "<br/>";
            countShiftingUc++;
        }
        
        //cek UC load yang belum di load
        List<UncontainerizedService> notLoadedUcServices = uncontainerizedServiceFacade.findUCThatHaveNotBeenLoaded(serviceVessel.getNoPpkb());
        for (UncontainerizedService uncontainerizedService: notLoadedUcServices) {
            if (isNotValid)
                isNotValid = false;
            messageUncontainerized = messageUncontainerized + "- " + uncontainerizedService.getBlNo() + "<br/>";
            countUncontainerized++;
        }

        //cek UC discharge yang belum ada di CY
        List<UncontainerizedService> notReacheCyUcServices = uncontainerizedServiceFacade.findUCThatHaveNotReachedCY(serviceVessel.getNoPpkb());
        for (UncontainerizedService uncontainerizedService: notReacheCyUcServices) {
            isNotValid = isNotValid || true;
            messageUncontainerized = messageUncontainerized + "- " + uncontainerizedService.getBlNo() + "<br/>";
            countUncontainerized++;
        }

        return isNotValid;
    }

    public void handleEndService(ActionEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            boolean loggedIn = false;
            messageAll = "";
            if (isNotValid()) {
                /* message untuk tiap container yg akan di end service*/
                if(countDischarge > 0)
                    messageAll = messageAll + countDischarge.toString() + " Container Discharge belum selesai Servicing.<br/>Detail No Container: <br/>" + messageDischarge + "<br/>";
                if(countLoad > 0)
                    messageAll = messageAll + countLoad.toString() + " Container Load belum selesai Servicing.<br/>Detail No Container: <br/>" + messageLoad + "<br/>";
                if(countShifting > 0)
                    messageAll = messageAll + countShifting.toString() + " Container Shifting belum selesai Servicing.<br/>Detail No Container: <br/>" + messageShifting + "<br/>";
                if(countShiftingUc > 0)
                    messageAll = messageAll + countShiftingUc.toString() + " Uncontainerized Shifting belum selesai Servicing.<br/>Detail BL No: <br/>" + messageShiftingUc + "<br/>";
                if(countTranshipment > 0)
                    messageAll = messageAll + countTranshipment.toString() + " Container Transhipment belum selesai Servicing.<br/>Detail No Container: <br/>" + messageTranshipment + "<br/>";
                if(countUncontainerized > 0)
                    messageAll = messageAll + countUncontainerized.toString() + " Uncontainerized belum selesai Servicing.<br/>Detail BL No: <br/>" + messageUncontainerized + "<br/>";
            } else {
                loggedIn = true;
                List<Object[]> tempContDisc = tempContDischargeFacade.findTempContDischargeByPpkb(serviceVessel.getNoPpkb());
                
        //penambahan pengecekan service vessel confirm by ade chelsea tanggal 04-07-2014 jam 08:56        
//        countServiceVesselConfirm = serviceVesselFacade.getCountServiceVesselConfirm(serviceVessel.getNoPpkb());
//        if(countServiceVesselConfirm.equals(0)){
//            FacesHelper.addFacesMessage(context, FacesMessage.SEVERITY_INFO, "Info", "Service Vessel Confirm Belum Dilakukan");
//        }else {
            
                if(tempContDisc.isEmpty()){
                    serviceVesselFacade.handleEndService(serviceVessel);
                    serviceVessels = serviceVesselFacade.findServiceVesselStatusService();
                    FacesHelper.addFacesMessage(context, FacesMessage.SEVERITY_INFO, "Info", "application.confirm.succeeded");
                }else {
                    FacesHelper.addFacesMessage(context, FacesMessage.SEVERITY_WARN, "Info", "application.confirm.failed");
                }
        
//        } 
                /*Author
                    @Add
                    @Srach
                */

                if(noPerhitunganBentukTigaFacadeRemote.find(serviceVessel.getNoPpkb())==null){
                    Date date = new Date();
                    noPerhitunganBentukTiga.setNoPpkb(serviceVessel.getNoPpkb());
                    noPerhitunganBentukTiga.setNoRegistrasi(iDGeneratorFacade.generateBentukTigaID());
                    noPerhitunganBentukTiga.setCreateDate(date);
                    noPerhitunganBentukTigaFacadeRemote.edit(noPerhitunganBentukTiga);
                }
            }
            RequestContext.getCurrentInstance().addCallbackParam("loggedIn", loggedIn);
        } catch (RuntimeException re) {
            messageAll = re.getMessage();
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", re);
        }
        
        
    }

    public void handleRollBack(ActionEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        serviceVesselFacade.handleRollback(serviceVessel);
        serviceVessels = serviceVesselFacade.findServiceVesselStatusService();
        FacesHelper.addFacesMessage(context, FacesMessage.SEVERITY_INFO, "Info", "application.rollback.succeeded");
    }


    public void handleOpenVesselActivityReportDialog(ActionEvent event) {
        Calendar now = Calendar.getInstance();
        startDate = now.getTime();
        now.add(Calendar.DAY_OF_MONTH, 1);
        endDate = now.getTime();
    }

    public void handleResetTransactionRecapParameter(DateSelectEvent event) {
        org.primefaces.component.calendar.Calendar sender = (org.primefaces.component.calendar.Calendar) event.getSource();
        Date date = (Date) sender.getValue();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        if (sender.getId().equals("startDate")) {
            if (date.compareTo(endDate) >= 0) {
                cal.add(Calendar.DAY_OF_MONTH, 1);
                endDate = cal.getTime();
            }
        } else if (sender.getId().equals("endDate")) {
            if (date.compareTo(startDate) <= 0) {
                cal.add(Calendar.DAY_OF_MONTH, -1);
                startDate = cal.getTime();
            }
        }
    }


    public void handleDownloadTransactionRecap(ActionEvent event) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        RequestContext context = RequestContext.getCurrentInstance();
        CommandButton sender = (CommandButton) event.getSource();

        context.addCallbackParam("doPrint", true); //harus ada, buat ngeflag di izinkan ngeprint ato ngga
        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3("../../../../VesselActivityRecap.pdf?"
                + "from=" + formatter.format(startDate) + "&"
                + "to=" + formatter.format(endDate) + "&"));
    }

     public void handleDownloadRevenuesRecap(ActionEvent event) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        RequestContext context = RequestContext.getCurrentInstance();
        CommandButton sender = (CommandButton) event.getSource();

        String reportType = "1";
        if (sender.getId().equals("Detail")) {
            reportType = "2";
        }

        context.addCallbackParam("doPrint", true); //harus ada, buat ngeflag di izinkan ngeprint ato ngga
        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3("../../../../VesselRevenuesRecap.pdf?"
                + "from=" + formatter.format(startDate) + "&"
                + "to=" + formatter.format(endDate) + "&"
                + "tipe=" + reportType));
    }


    public void handleDownloadTranshipmentRecap(ActionEvent event) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        RequestContext context = RequestContext.getCurrentInstance();
        CommandButton sender = (CommandButton) event.getSource();

        String reportType = "1";
        if (sender.getId().equals("Detail")) {
            reportType = "2";
        }

        context.addCallbackParam("doPrint", true); //harus ada, buat ngeflag di izinkan ngeprint ato ngga
        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3("../../../../TranshipmentRecap.pdf?"
                + "from=" + formatter.format(startDate) + "&"
                + "to=" + formatter.format(endDate) + "&"
                + "tipe=" + reportType));
    }

    /**
     * @return the serviceVessels
     */
    public List<Object[]> getServiceVessels() {
        return serviceVessels;
    }

    /**
     * @param serviceVessels the serviceVessels to set
     */
    public void setServiceVessels(List<Object[]> planningVessels) {
        this.serviceVessels = planningVessels;
    }

    /**
     * @return the serviceVessel
     */
    public ServiceVessel getServiceVessel() {
        return serviceVessel;
    }

    /**
     * @param serviceVessel the serviceVessel to set
     */
    public void setServiceVessel(ServiceVessel serviceVessel) {
        this.serviceVessel = serviceVessel;
    }

    public boolean isListState() {
        return getPageState().equals(EndServiceBean.LIST);
    }

    public boolean isDetailState() {
        return getPageState().equals(EndServiceBean.DETAIL);
    }

    public void setToListState() {
        setPageState(EndServiceBean.LIST);
    }

    public void setToDetailState() {
        setPageState(EndServiceBean.DETAIL);
    }

    /**
     * @return the sv
     */
    public Object[] getSv() {
        return sv;
    }

    /**
     * @param sv the sv to set
     */
    public void setSv(Object[] sv) {
        this.sv = sv;
    }

    /**
     * @return the pageState
     */
    public String getPageState() {
        return pageState;
    }

    /**
     * @param pageState the pageState to set
     */
    public void setPageState(String pageState) {
        this.pageState = pageState;
    }

    public String getMessageAll() {
        return messageAll;
    }

    public void setMessageAll(String messageAll) {
        this.messageAll = messageAll;
    }

    public StreamedContent getFile() {
        return file;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getCountServiceVesselConfirm() {
        return countServiceVesselConfirm;
    }

    public void setCountServiceVesselConfirm(Integer countServiceVesselConfirm) {
        this.countServiceVesselConfirm = countServiceVesselConfirm;
    }
    
    
}
