/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.bean;

import com.qtasnim.jsf.FacesHelper;
import com.pelindo.ebtos.ejb.facade.remote.MasterAnggaranFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterActivityFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterTarifContFacadeRemote;
import com.pelindo.ebtos.model.db.master.MasterAnggaran;
import com.pelindo.ebtos.model.db.master.MasterActivity;
import com.pelindo.ebtos.model.db.master.MasterAnggaranPK;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import org.primefaces.context.RequestContext;

/**
 *
 * @author dycoder
 */
@ManagedBean(name="masterAnggaranBean")
@ViewScoped
public class MasterAnggaranBean implements Serializable{
    MasterTarifContFacadeRemote masterTarifContFacade = lookupMasterTarifContFacadeRemote();
    MasterAnggaranFacadeRemote masterAnggaranFacade = lookupMasterAnggaranFacadeRemote();
    MasterActivityFacadeRemote masterActivityFacade = lookupMasterActivityFacadeRemote();

    private List<Object[]> anggarans = lookupMasterAnggaranFacadeRemote().findAllNative();
    private List<Object[]> activitys = lookupMasterActivityFacadeRemote().findAllActivity();

    private MasterAnggaran anggaran;
    private String internasional;
    private Boolean disEdit;

    /** Creates a new instance of MasterAnggaranBean */
    public MasterAnggaranBean() {
        anggaran = new MasterAnggaran();
        anggaran.setMasterAnggaranPK(new MasterAnggaranPK());
        anggaran.setMasterActivity(new MasterActivity());
        disEdit = false;
    }

    public void handleAdd(ActionEvent event) {
        anggaran = new MasterAnggaran();
        anggaran.setMasterAnggaranPK(new MasterAnggaranPK());
        anggaran.setMasterActivity(new MasterActivity());
        anggaran.getMasterAnggaranPK().setCurrCode("IDR");
        disEdit = false;
    }

    public void handleSelectTable(ActionEvent event) {
        String curr = (String) event.getComponent().getAttributes().get("curr");
        String thn = (String) event.getComponent().getAttributes().get("thn");
        String act = (String) event.getComponent().getAttributes().get("act");
        MasterAnggaranPK pk = new MasterAnggaranPK();
        pk.setCurrCode(curr);
        pk.setTahun(thn);
        pk.setActivityCode(act);
        anggaran = masterAnggaranFacade.find(pk);
        if(anggaran.getMasterAnggaranPK().getCurrCode().equalsIgnoreCase("USDI")){
            anggaran.getMasterAnggaranPK().setCurrCode("USD");
            internasional = "I";
        }
        
        if(anggaran.getMasterAnggaranPK().getCurrCode().equalsIgnoreCase("USDE")){
            anggaran.getMasterAnggaranPK().setCurrCode("USD");
            internasional = "E";
        }
        disEdit = true;
    }

    public void handleSelectAct(ActionEvent event){
        String idAct = (String) event.getComponent().getAttributes().get("idAct");
        MasterActivity act = new MasterActivity();
        act = masterActivityFacade.find(idAct);
        anggaran.setMasterActivity(act);
        anggaran.setDescription(act.getDescription());
        anggaran.getMasterAnggaranPK().setActivityCode(idAct);
    }

    public void onChangeCurrCode(ValueChangeEvent event){
        anggaran.getMasterAnggaranPK().setCurrCode((String) event.getNewValue());
        if(anggaran.getMasterAnggaranPK().getCurrCode().equalsIgnoreCase("USD"))
            internasional = "I";
        else
            internasional = "";
    }

    public void handleSaveEdit(ActionEvent event) {
        boolean loggedIn;
        if (anggaran.getTriwulan1().add(anggaran.getTriwulan2()).add(anggaran.getTriwulan3()).add(anggaran.getTriwulan4()).compareTo(BigDecimal.valueOf(100)) == 1) {
            loggedIn = false;
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.validation.percentage");
        } else if (anggaran.getTriwulan1().add(anggaran.getTriwulan2()).add(anggaran.getTriwulan3()).add(anggaran.getTriwulan4()).compareTo(BigDecimal.valueOf(100)) == -1) {
            loggedIn = false;
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.validation.percentage_less");
        } else {
            loggedIn = true;
            if(anggaran.getMasterAnggaranPK().getCurrCode().equalsIgnoreCase("USD")){
                anggaran.getMasterAnggaranPK().setCurrCode(anggaran.getMasterAnggaranPK().getCurrCode()+internasional);
                if(internasional.equalsIgnoreCase("I"))
                    anggaran.setDescription(anggaran.getDescription()+" IMPOR");
                else
                    anggaran.setDescription(anggaran.getDescription()+" EKSPOR");
            }

            // BigDecimal tarifDasar = masterTarifContFacade.findByActivityAndCurr(anggaran.getMasterAnggaranPK().getCurrCode().substring(0, 3), anggaran.getMasterAnggaranPK().getActivityCode());
            
            // tarif internasional tetep ambil dari tarif domestik (curr_code = IDR)
            BigDecimal tarifDasar = masterTarifContFacade.findByCurrCodeAndActivity("IDR", anggaran.getMasterAnggaranPK().getActivityCode());
            anggaran.setTarifDasar(tarifDasar);
            anggaran.setProdTriwulan1(anggaran.getProdTahun().multiply(anggaran.getTriwulan1().divide(BigDecimal.valueOf(100))));
            anggaran.setProdTriwulan2(anggaran.getProdTahun().multiply(anggaran.getTriwulan2().divide(BigDecimal.valueOf(100))));
            anggaran.setProdTriwulan3(anggaran.getProdTahun().multiply(anggaran.getTriwulan3().divide(BigDecimal.valueOf(100))));
            anggaran.setProdTriwulan4(anggaran.getProdTahun().multiply(anggaran.getTriwulan4().divide(BigDecimal.valueOf(100))));
            anggaran.setPendTahun(tarifDasar.multiply(anggaran.getProdTahun()));
            anggaran.setPendTriwulan1(anggaran.getPendTahun().multiply(anggaran.getTriwulan1().divide(BigDecimal.valueOf(100))));
            anggaran.setPendTriwulan2(anggaran.getPendTahun().multiply(anggaran.getTriwulan2().divide(BigDecimal.valueOf(100))));
            anggaran.setPendTriwulan3(anggaran.getPendTahun().multiply(anggaran.getTriwulan3().divide(BigDecimal.valueOf(100))));
            anggaran.setPendTriwulan4(anggaran.getPendTahun().multiply(anggaran.getTriwulan4().divide(BigDecimal.valueOf(100))));
            masterAnggaranFacade.edit(anggaran);
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
            anggarans = masterAnggaranFacade.findAllNative();
        }
        RequestContext.getCurrentInstance().addCallbackParam("loggedIn", loggedIn);
    }

    public void handleDelete(ActionEvent event) {
        try {
            masterAnggaranFacade.remove(anggaran);
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.delete.succeeded");
        } catch (EJBException ex) {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_ERROR, "Error", "application.save.failed");
        }
        anggarans = masterAnggaranFacade.findAllNative();
    }

    private MasterAnggaranFacadeRemote lookupMasterAnggaranFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (MasterAnggaranFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/MasterAnggaranFacade!com.pelindo.ebtos.ejb.facade.remote.MasterAnggaranFacadeRemote");
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
     * @return the anggarans
     */
    public List<Object[]> getAnggarans() {
        return anggarans;
    }

    /**
     * @param anggarans the anggarans to set
     */
    public void setAnggarans(List<Object[]> anggarans) {
        this.anggarans = anggarans;
    }

    /**
     * @return the anggaran
     */
    public MasterAnggaran getAnggaran() {
        return anggaran;
    }

    /**
     * @param anggaran the anggaran to set
     */
    public void setAnggaran(MasterAnggaran anggaran) {
        this.anggaran = anggaran;
    }

    /**
     * @return the activitys
     */
    public List<Object[]> getActivitys() {
        return activitys;
    }

    /**
     * @param activitys the activitys to set
     */
    public void setActivitys(List<Object[]> activitys) {
        this.activitys = activitys;
    }

    /**
     * @return the internasional
     */
    public String getInternasional() {
        return internasional;
    }

    /**
     * @param internasional the internasional to set
     */
    public void setInternasional(String internasional) {
        this.internasional = internasional;
    }

    /**
     * @return the disEdit
     */
    public Boolean getDisEdit() {
        return disEdit;
    }

    /**
     * @param disEdit the disEdit to set
     */
    public void setDisEdit(Boolean disEdit) {
        this.disEdit = disEdit;
    }

}
