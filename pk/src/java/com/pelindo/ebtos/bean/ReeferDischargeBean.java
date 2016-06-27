/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.bean;

import com.qtasnim.jsf.FacesHelper;
import com.pelindo.ebtos.ejb.facade.remote.MasterContainerTypeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterCustomerFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterPluggingReeferFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ReeferDischargeServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ReeferMonitoringFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceContDischargeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceReeferFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceVesselFacadeRemote;
import com.pelindo.ebtos.model.db.ReeferDischargeService;
import com.pelindo.ebtos.model.db.ReeferMonitoring;
import com.pelindo.ebtos.model.db.ServiceContDischarge;
import com.pelindo.ebtos.model.db.ServiceReefer;
import com.pelindo.ebtos.model.db.master.MasterContainerType;
import com.pelindo.ebtos.model.db.master.MasterCustomer;
import com.pelindo.ebtos.model.db.master.MasterPluggingReefer;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.context.RequestContext;

/**
 *
 * @author dycode-java
 */
@ManagedBean(name= "reeferDischargeBean")
@ViewScoped
public class ReeferDischargeBean implements Serializable {
    @EJB
    private ReeferMonitoringFacadeRemote reeferMonitoringFacade;
    @EJB
    private MasterContainerTypeFacadeRemote masterContainerTypeFacade;
    @EJB
    private MasterPluggingReeferFacadeRemote masterPluggingReeferFacade;
    @EJB
    private ServiceReeferFacadeRemote serviceReeferFacade;
    @EJB
    private ServiceVesselFacadeRemote serviceVesselFacade;
    @EJB
    private ServiceContDischargeFacadeRemote serviceContDischargeFacade;
    @EJB
    private MasterCustomerFacadeRemote masterCustomerFacadeRemote;
    @EJB
    private ReeferDischargeServiceFacadeRemote reeferDischargeServiceFacadeRemote;
    
    private List<Object[]> serviceVessels;
    private List<Object[]> serviceReefers;
    private List<Object[]> serviceContDischarges;
    private List<Object[]> reeferMonitorings;
    private List<MasterPluggingReefer> pluggingReefers;
    private Object[] serviceVessel;
    private ServiceReefer serviceReefer;
    private ReeferMonitoring reeferMonitoring;
    private String no_ppkb;
    private Date plugOnOff;
    private Boolean kodePlug = false;
    private Boolean editCont = false;
    private Boolean changePlug = true;
    private Boolean editMon = false;

    /** Creates a new instance of ReeferDischargeBean */
    public ReeferDischargeBean() {}

    @PostConstruct
    private void construct() {
        serviceReefer = new ServiceReefer();
        serviceReefer.setMasterContainerType(new MasterContainerType());
        serviceReefer.setMasterPluggingReefer(new MasterPluggingReefer());
        reeferMonitoring = new ReeferMonitoring();
        reeferMonitoring.setServiceReefer(new ServiceReefer());

        serviceVessels = serviceVesselFacade.findServiceVesselStatusService();
    }

    public void handleSelectNoPpkb(ActionEvent event) {
        no_ppkb = (String) event.getComponent().getAttributes().get("noPpkb");
        serviceVessel = serviceVesselFacade.findServiceVesselByPpkb(no_ppkb);
        serviceReefers = serviceReeferFacade.findServiceReeferByPpkbDischarge(no_ppkb);
        serviceContDischarges = serviceContDischargeFacade.findServiceContDischargesReefer(no_ppkb);
    }

    public void handlePlugOn(ActionEvent event) {
        serviceReefer = new ServiceReefer();
        serviceReefer.setMasterContainerType(new MasterContainerType());
        serviceReefer.setMasterPluggingReefer(new MasterPluggingReefer());
        kodePlug = false;
        editCont = false;
        plugOnOff = null;
    }

    public void handleClear(ActionEvent event) {
        reeferMonitoring = new ReeferMonitoring();
        reeferMonitoring.setServiceReefer(new ServiceReefer());
        editMon = false;
    }

    public void handleSelectCont(ActionEvent event) {
        Integer id_cont = (Integer) event.getComponent().getAttributes().get("idCont");
        ServiceContDischarge scd = new ServiceContDischarge();
        scd = serviceContDischargeFacade.find(id_cont);
        serviceReefer.setContNo(scd.getContNo());
        serviceReefer.setMlo(scd.getMlo());
        serviceReefer.setContSize(scd.getContSize());
        serviceReefer.setMasterContainerType(scd.getMasterContainerType());
        serviceReefer.setContStatus(scd.getContStatus());
        serviceReefer.setBlNo(scd.getBlNo());
    }

    public void handleConfirm(ActionEvent event) {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        
        boolean loggedIn = true;
        serviceReefer.setNoPpkb(no_ppkb);
        serviceReefer.setOperation("DISCHARGE");
        serviceReefer.setChangePlugging((short) 0);
        serviceReefer.setLastPluggingCode(serviceReefer.getMasterPluggingReefer().getPluggingCode());

        if (serviceReefer.getFirstPlugOn() == null) {
            serviceReefer.setFirstPlugOn(serviceReefer.getMasterPluggingReefer().getPluggingCode());
        }

        MasterPluggingReefer plug = masterPluggingReeferFacade.find(serviceReefer.getMasterPluggingReefer().getPluggingCode());
        MasterPluggingReefer firstPlugOn = masterPluggingReeferFacade.find(serviceReefer.getFirstPlugOn());
        MasterPluggingReefer lastPluggingCode = masterPluggingReeferFacade.find(serviceReefer.getLastPluggingCode());

        if (kodePlug == false) {
            serviceReefer.setPlugOn(plugOnOff);
            plug.setAvailability("FALSE");
            firstPlugOn.setAvailability("FALSE");
            lastPluggingCode.setAvailability("FALSE");
        } else {
            ReeferDischargeService reeferDischargeService = reeferDischargeServiceFacadeRemote.findLastValidReeferByNoPpkbAndContNo(no_ppkb, serviceReefer.getContNo());

            if (reeferDischargeService == null || plugOnOff.after(reeferDischargeService.getValidDate())) {
                FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_ERROR, "Error", "application.validation.plug.invalid_valid_date");
                return;
            }

            serviceReefer.setPlugOff(plugOnOff);
            
            if (!plug.getPluggingCode().equals("NULL")) {
                plug.setAvailability("TRUE");
            }

            firstPlugOn.setAvailability("TRUE");
            lastPluggingCode.setAvailability("TRUE");
        }

        serviceReeferFacade.edit(serviceReefer);
        masterPluggingReeferFacade.edit(plug);
        masterPluggingReeferFacade.edit(firstPlugOn);
        masterPluggingReeferFacade.edit(lastPluggingCode);

        serviceReefers = serviceReeferFacade.findServiceReeferByPpkbDischarge(no_ppkb);
        serviceContDischarges = serviceContDischargeFacade.findServiceContDischargesReefer(no_ppkb);
        FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
        requestContext.addCallbackParam("loggedIn", loggedIn);
    }

    public void handleSelectServiceReefer(ActionEvent event) {
        Integer id_serReef = (Integer) event.getComponent().getAttributes().get("idSerReef");
        Object[] sr = serviceReeferFacade.findServiceReeferById(id_serReef);
        MasterCustomer mlo = masterCustomerFacadeRemote.find(sr[13]);
        MasterContainerType masterContainerType = masterContainerTypeFacade.find(Integer.valueOf(String.valueOf(sr[3])));
        MasterPluggingReefer masterPluggingReefer = masterPluggingReeferFacade.find((String) sr[7]);

        serviceReefer.setIdReefer((Integer) sr[0]);
        serviceReefer.setContNo((String) sr[1]);
        serviceReefer.setContSize(Short.parseShort(String.valueOf(sr[2])));
        serviceReefer.setMasterContainerType(masterContainerType);
        serviceReefer.setContStatus((String) sr[4]);
        serviceReefer.setFirstPlugOn((String) sr[5]);
        serviceReefer.setPlugOn((Date) sr[6]);
        serviceReefer.setMasterPluggingReefer(masterPluggingReefer);
        serviceReefer.setMlo(mlo);
        serviceReefer.setPlugOff((Date) sr[8]);
        serviceReefer.setOperation((String) sr[9]);
        serviceReefer.setNoPpkb((String) sr[10]);
        serviceReefer.setChangePlugging(Short.parseShort(String.valueOf(sr[11])));
        serviceReefer.setLastPluggingCode((String) sr[12]);

        editCont = true;
        plugOnOff = serviceReefer.getPlugOn();

        reeferMonitorings = reeferMonitoringFacade.findByIdReefer(serviceReefer.getIdReefer());
        reeferMonitoring = new ReeferMonitoring();
        reeferMonitoring.setServiceReefer(new ServiceReefer());
    }

    public void handlePlugOff(ActionEvent event) {
        Integer id_serReef = (Integer) event.getComponent().getAttributes().get("idSerReef");
        Object[] sr = serviceReeferFacade.findServiceReeferById(id_serReef);
        MasterContainerType masterContainerType = masterContainerTypeFacade.find(Integer.valueOf(String.valueOf(sr[3])));
        MasterPluggingReefer masterPluggingReefer = masterPluggingReeferFacade.find((String) sr[7]);
        MasterCustomer mlo = masterCustomerFacadeRemote.find(sr[13]);

        serviceReefer.setIdReefer((Integer) sr[0]);
        serviceReefer.setContNo((String) sr[1]);
        serviceReefer.setContSize(Short.parseShort(String.valueOf(sr[2])));
        serviceReefer.setMasterContainerType(masterContainerType);
        serviceReefer.setContStatus((String) sr[4]);
        serviceReefer.setFirstPlugOn((String) sr[5]);
        serviceReefer.setPlugOn((Date) sr[6]);
        serviceReefer.setMasterPluggingReefer(masterPluggingReefer);
        serviceReefer.setMlo(mlo);
        serviceReefer.setPlugOff((Date) sr[8]);
        serviceReefer.setOperation((String) sr[9]);
        serviceReefer.setNoPpkb((String) sr[10]);
        serviceReefer.setChangePlugging(Short.parseShort(String.valueOf(sr[11])));
        serviceReefer.setLastPluggingCode((String) sr[12]);

        reeferMonitorings = reeferMonitoringFacade.findByIdReefer(serviceReefer.getIdReefer());
        reeferMonitoring = new ReeferMonitoring();
        reeferMonitoring.setServiceReefer(new ServiceReefer());

        plugOnOff = serviceReefer.getPlugOff();
        kodePlug = true;
        editCont = true;
    }

    public void enableChangePlug() {
        changePlug = false;
    }

    public void handleAddReeferMonitoring(ActionEvent event) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        String dateMon = "beda";
        reeferMonitorings = reeferMonitoringFacade.findByIdReefer(serviceReefer.getIdReefer());
        for (Object[] o : reeferMonitorings) {
            if (format.format((Date) o[3]).equals(format.format(reeferMonitoring.getDateMontoring()))) {
                dateMon = "sama";
            } else {
                if (dateMon.equals("sama")) {
                    dateMon = "sama";
                } else {
                    dateMon = "beda";
                }
            }
        }
        if (editMon == true) {
            dateMon = "beda";
        }

        if (dateMon.equals("beda")) {
            ServiceReefer sr = new ServiceReefer();
            sr = serviceReeferFacade.find(serviceReefer.getIdReefer());
            if (serviceReefer.getMasterPluggingReefer().getPluggingCode().equals("NULL")) {
                serviceReeferFacade.edit(serviceReefer);

                MasterPluggingReefer mpr = new MasterPluggingReefer();
                mpr = masterPluggingReeferFacade.find(sr.getMasterPluggingReefer().getPluggingCode());
                mpr.setAvailability("TRUE");
                masterPluggingReeferFacade.edit(mpr);
            } else if (!sr.getLastPluggingCode().equals(serviceReefer.getMasterPluggingReefer().getPluggingCode())) {
                int i = serviceReefer.getChangePlugging() + 1;
                serviceReefer.setChangePlugging(Short.parseShort(String.valueOf(i)));
                serviceReefer.setLastPluggingCode(serviceReefer.getMasterPluggingReefer().getPluggingCode());
                serviceReeferFacade.edit(serviceReefer);

                MasterPluggingReefer mpr = new MasterPluggingReefer();
                mpr = masterPluggingReeferFacade.find(serviceReefer.getMasterPluggingReefer().getPluggingCode());
                mpr.setAvailability("FALSE");
                masterPluggingReeferFacade.edit(mpr);
            }
            reeferMonitoring.setServiceReefer(serviceReefer);
            reeferMonitoring.setContNo(serviceReefer.getContNo());
            reeferMonitoring.setMlo(serviceReefer.getMlo());

            reeferMonitoringFacade.edit(reeferMonitoring);
            changePlug = true;
            reeferMonitoring = new ReeferMonitoring();
            reeferMonitoring.setServiceReefer(new ServiceReefer());
            editMon = false;
            reeferMonitorings = reeferMonitoringFacade.findByIdReefer(serviceReefer.getIdReefer());
        } else {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Warning", "application.validation.monitoring");
        }
    }

    public void handleSelectMonitoring(ActionEvent event) {
        Integer id_rm = (Integer) event.getComponent().getAttributes().get("idRM");
        reeferMonitoring = reeferMonitoringFacade.find(id_rm);
        editMon = true;
    }

    public void handleExit(ActionEvent event) {
        reeferMonitoring = new ReeferMonitoring();
        reeferMonitoring.setServiceReefer(new ServiceReefer());
        serviceReefers = serviceReeferFacade.findServiceReeferByPpkbDischarge(no_ppkb);
    }

    public void handleDelete(ActionEvent event) {
        if (reeferMonitoring.getId() == null) {
            for (Object[] o : reeferMonitorings) {
                reeferMonitoring = reeferMonitoringFacade.find(Integer.valueOf(String.valueOf(o[0])));
                reeferMonitoringFacade.remove(reeferMonitoring);
            }
            MasterPluggingReefer mpr = new MasterPluggingReefer();
            MasterPluggingReefer mpre = new MasterPluggingReefer();
            MasterPluggingReefer mpree = new MasterPluggingReefer();
            mpr = masterPluggingReeferFacade.find(serviceReefer.getMasterPluggingReefer().getPluggingCode());
            mpre = masterPluggingReeferFacade.find(serviceReefer.getFirstPlugOn());
            mpree = masterPluggingReeferFacade.find(serviceReefer.getLastPluggingCode());

            if (!mpr.getPluggingCode().equals("NULL")) {
                mpr.setAvailability("TRUE");
            }
            mpre.setAvailability("TRUE");
            mpree.setAvailability("TRUE");
            masterPluggingReeferFacade.edit(mpr);
            masterPluggingReeferFacade.edit(mpre);
            masterPluggingReeferFacade.edit(mpree);

            serviceReeferFacade.remove(serviceReefer);
            serviceReefers = serviceReeferFacade.findServiceReeferByPpkbDischarge(no_ppkb);
        } else {
            reeferMonitoringFacade.remove(reeferMonitoring);
            reeferMonitorings = reeferMonitoringFacade.findByIdReefer(serviceReefer.getIdReefer());
            reeferMonitoring = new ReeferMonitoring();
            reeferMonitoring.setServiceReefer(new ServiceReefer());
            editMon = false;
        }
        serviceContDischarges = serviceContDischargeFacade.findServiceContDischargesReefer(no_ppkb);
        FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.delete.succeeded");
    }

    /**
     * @return the serviceReefer
     */
    public ServiceReefer getServiceReefer() {
        return serviceReefer;
    }

    /**
     * @param serviceReefer the serviceReefer to set
     */
    public void setServiceReefer(ServiceReefer serviceReefer) {
        this.serviceReefer = serviceReefer;
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
    public void setServiceVessels(List<Object[]> serviceVessels) {
        this.serviceVessels = serviceVessels;
    }

    /**
     * @return the serviceVessel
     */
    public Object[] getServiceVessel() {
        return serviceVessel;
    }

    /**
     * @param serviceVessel the serviceVessel to set
     */
    public void setServiceVessel(Object[] serviceVessel) {
        this.serviceVessel = serviceVessel;
    }

    /**
     * @return the serviceReefers
     */
    public List<Object[]> getServiceReefers() {
        return serviceReefers;
    }

    /**
     * @param serviceReefers the serviceReefers to set
     */
    public void setServiceReefers(List<Object[]> serviceReefers) {
        this.serviceReefers = serviceReefers;
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
     * @return the pluggingReefers
     */
    public List<MasterPluggingReefer> getPluggingReefers() {
        List<MasterPluggingReefer> pl = new ArrayList<MasterPluggingReefer>();
        for (String o : masterPluggingReeferFacade.findNotInServiceReefer()) {
            MasterPluggingReefer plug = new MasterPluggingReefer();
            plug = masterPluggingReeferFacade.find(o);
            pl.add(plug);
        }
        pluggingReefers = pl;
        MasterPluggingReefer mpr = new MasterPluggingReefer();
        mpr = masterPluggingReeferFacade.find("NULL");
        if (serviceReefer.getMasterPluggingReefer().getPluggingCode() != null) {
            pluggingReefers.add(serviceReefer.getMasterPluggingReefer());
            pluggingReefers.add(mpr);
        }
        return pluggingReefers;
    }

    /**
     * @param pluggingReefers the pluggingReefers to set
     */
    public void setPluggingReefers(List<MasterPluggingReefer> pluggingReefers) {
        this.pluggingReefers = pluggingReefers;
    }

    /**
     * @return the plugOnOff
     */
    public Date getPlugOnOff() {
        return plugOnOff;
    }

    /**
     * @param plugOnOff the plugOnOff to set
     */
    public void setPlugOnOff(Date plugOnOff) {
        this.plugOnOff = plugOnOff;
    }

    /**
     * @return the changePlug
     */
    public Boolean getChangePlug() {
        return changePlug;
    }

    /**
     * @param changePlug the changePlug to set
     */
    public void setChangePlug(Boolean changePlug) {
        this.changePlug = changePlug;
    }

    /**
     * @return the reeferMonitorings
     */
    public List<Object[]> getReeferMonitorings() {
        return reeferMonitorings;
    }

    /**
     * @param reeferMonitorings the reeferMonitorings to set
     */
    public void setReeferMonitorings(List<Object[]> reeferMonitorings) {
        this.reeferMonitorings = reeferMonitorings;
    }

    /**
     * @return the reeferMonitoring
     */
    public ReeferMonitoring getReeferMonitoring() {
        return reeferMonitoring;
    }

    /**
     * @param reeferMonitoring the reeferMonitoring to set
     */
    public void setReeferMonitoring(ReeferMonitoring reeferMonitoring) {
        this.reeferMonitoring = reeferMonitoring;
    }

    /**
     * @return the kodePlug
     */
    public Boolean getKodePlug() {
        return kodePlug;
    }

    /**
     * @param kodePlug the kodePlug to set
     */
    public void setKodePlug(Boolean kodePlug) {
        this.kodePlug = kodePlug;
    }

    /**
     * @return the editCont
     */
    public Boolean getEditCont() {
        return editCont;
    }

    /**
     * @param editCont the editCont to set
     */
    public void setEditCont(Boolean editCont) {
        this.editCont = editCont;
    }
}
