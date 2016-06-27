/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.bean;

import com.qtasnim.jsf.FacesHelper;
import com.pelindo.ebtos.ejb.facade.remote.MasterAnggaranTrafikBmFacadeRemote;
import com.pelindo.ebtos.model.db.master.MasterAnggaranTrafikBm;
import com.pelindo.ebtos.model.db.master.MasterAnggaranTrafikBmPK;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
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
@ManagedBean(name="masterAnggaranTrafikBean")
@ViewScoped
public class MasterAnggaranTrafikBean implements Serializable{
    MasterAnggaranTrafikBmFacadeRemote masterAnggaranTrafikBmFacade = lookupMasterAnggaranTrafikBmFacadeRemote();

    private List<Object[]> anggarans;
    private List<String> listTahun;

    private MasterAnggaranTrafikBm anggaran;
    private Boolean disEdit, reefer;
    private String tradeType, act, type, size, satuan, thn;

    /** Creates a new instance of MasterAnggaranTrafikBmBean */
    public MasterAnggaranTrafikBean() {
        anggarans = lookupMasterAnggaranTrafikBmFacadeRemote().findAllNative();
        init();
    }

    public void init(){
        anggaran = new MasterAnggaranTrafikBm();
        anggaran.setMasterAnggaranTrafikBmPK(new MasterAnggaranTrafikBmPK());
        disEdit = false;
        reefer = false;
        act = "B";
        type = "F";
        size = "20";
        satuan = "B";
        tradeType = "D";
    }

    public void handleAdd(ActionEvent event) {
        init();
    }

    public void handleGenerateTahun(ActionEvent event) {
        thn = String.valueOf(Integer.valueOf(masterAnggaranTrafikBmFacade.findTahunMax())+1);
    }

    public void handleDeleteTahun(ActionEvent event) {
        listTahun = new ArrayList<String>();
        listTahun = masterAnggaranTrafikBmFacade.findTahun();
    }

    public void handleGenerate(ActionEvent event){
        for(Object[] o : masterAnggaranTrafikBmFacade.findAnggaranByTahun(String.valueOf(Integer.valueOf(thn)-1))){
            MasterAnggaranTrafikBm ang = new MasterAnggaranTrafikBm(); // anggaran lama
            ang.setMasterAnggaranTrafikBmPK(new MasterAnggaranTrafikBmPK());
            MasterAnggaranTrafikBmPK pk = new MasterAnggaranTrafikBmPK();
            MasterAnggaranTrafikBm ang_generate = new MasterAnggaranTrafikBm(); // anggaran baru
            ang_generate.setMasterAnggaranTrafikBmPK(new MasterAnggaranTrafikBmPK());

            pk.setTahun((String) o[0]);
            pk.setActivity((String) o[1]);
            ang = masterAnggaranTrafikBmFacade.find(pk);
            ang_generate.getMasterAnggaranTrafikBmPK().setActivity(ang.getMasterAnggaranTrafikBmPK().getActivity());
            ang_generate.getMasterAnggaranTrafikBmPK().setTahun(thn);
            ang_generate.setProdTahun(ang.getProdTahun());
            ang_generate.setTriwulan1(ang.getTriwulan1());
            ang_generate.setTriwulan2(ang.getTriwulan2());
            ang_generate.setTriwulan3(ang.getTriwulan3());
            ang_generate.setTriwulan4(ang.getTriwulan4());
            ang_generate.setProdTriwulan1(ang.getProdTriwulan1());
            ang_generate.setProdTriwulan2(ang.getProdTriwulan2());
            ang_generate.setProdTriwulan3(ang.getProdTriwulan3());
            ang_generate.setProdTriwulan4(ang.getProdTriwulan4());
            ang_generate.setDescription(ang.getDescription());
            masterAnggaranTrafikBmFacade.create(ang_generate);
        }
        FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
        anggarans = masterAnggaranTrafikBmFacade.findAllNative();
    }

    public void handleDeleteAll(ActionEvent event){
        for(Object[] o : masterAnggaranTrafikBmFacade.findAnggaranByTahun(thn)){
            MasterAnggaranTrafikBm ang = new MasterAnggaranTrafikBm();
            MasterAnggaranTrafikBmPK pk = new MasterAnggaranTrafikBmPK();

            pk.setTahun((String) o[0]);
            pk.setActivity((String) o[1]);
            ang = masterAnggaranTrafikBmFacade.find(pk);
            masterAnggaranTrafikBmFacade.remove(ang);
        }
        FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.delete.succeeded");
        anggarans = masterAnggaranTrafikBmFacade.findAllNative();
    }

    public void onChangeType(ValueChangeEvent event){
        type = (String) event.getNewValue();
        reefer = false;
        satuan = "B";
    }

    public void handleSelectTable(ActionEvent event) {
        String taun = (String) event.getComponent().getAttributes().get("thn");
        String actv = (String) event.getComponent().getAttributes().get("act");
        MasterAnggaranTrafikBmPK pk = new MasterAnggaranTrafikBmPK();
        pk.setTahun(taun);
        pk.setActivity(actv);
        anggaran = masterAnggaranTrafikBmFacade.find(pk);
        disEdit = true;
        tradeType = actv.substring(0, 1);
        act = actv.substring(1, 2);
        size = actv.substring(3, 5);
        satuan = actv.substring(5, 6);
        type = actv.substring(2, 3);
        reefer = false;
        if (actv.substring(2, 3).equals("R")) {
            type = "F";
            reefer = true;
        }
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
            if(reefer == true && tradeType.equalsIgnoreCase("D"))
                type = "R";
            anggaran.getMasterAnggaranTrafikBmPK().setActivity(tradeType + act + type + size + satuan);
            String des;
            if (tradeType.equalsIgnoreCase("I")) {
                if(act.equalsIgnoreCase("B"))
                    des = "Internasional Impor ";
                else
                    des = "Internasional Ekspor ";
            } else {
                if(act.equalsIgnoreCase("B"))
                    des = "Domestik Bongkar ";
                else
                    des = "Domestik Muat ";
            }

            if(type.equalsIgnoreCase("F"))
                des = des + "Full ";
            else if(type.equalsIgnoreCase("R"))
                des = des + "Reefer ";
            else
                des = des + "Empty ";

            des = des + size;

            if(satuan.equalsIgnoreCase("b"))
                des = des + " Box";
            else
                des = des + " Ton";

            anggaran.setDescription(des);
            anggaran.setProdTriwulan1(anggaran.getProdTahun().multiply(anggaran.getTriwulan1().divide(BigDecimal.valueOf(100))));
            anggaran.setProdTriwulan2(anggaran.getProdTahun().multiply(anggaran.getTriwulan2().divide(BigDecimal.valueOf(100))));
            anggaran.setProdTriwulan3(anggaran.getProdTahun().multiply(anggaran.getTriwulan3().divide(BigDecimal.valueOf(100))));
            anggaran.setProdTriwulan4(anggaran.getProdTahun().multiply(anggaran.getTriwulan4().divide(BigDecimal.valueOf(100))));
            masterAnggaranTrafikBmFacade.edit(anggaran);
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
            anggarans = masterAnggaranTrafikBmFacade.findAllNative();
        }
        RequestContext.getCurrentInstance().addCallbackParam("loggedIn", loggedIn);
    }

    public void handleDelete(ActionEvent event) {
        try {
            masterAnggaranTrafikBmFacade.remove(anggaran);
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.delete.succeeded");
        } catch (EJBException ex) {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_ERROR, "Error", "application.save.failed");
        }
        anggarans = masterAnggaranTrafikBmFacade.findAllNative();
    }

    private MasterAnggaranTrafikBmFacadeRemote lookupMasterAnggaranTrafikBmFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (MasterAnggaranTrafikBmFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/MasterAnggaranTrafikBmFacade!com.pelindo.ebtos.ejb.facade.remote.MasterAnggaranTrafikBmFacadeRemote");
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
    public MasterAnggaranTrafikBm getAnggaran() {
        return anggaran;
    }

    /**
     * @param anggaran the anggaran to set
     */
    public void setAnggaran(MasterAnggaranTrafikBm anggaran) {
        this.anggaran = anggaran;
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

    public String getAct() {
        return act;
    }

    public void setAct(String act) {
        this.act = act;
    }

    public String getSatuan() {
        return satuan;
    }

    public void setSatuan(String satuan) {
        this.satuan = satuan;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getReefer() {
        return reefer;
    }

    public void setReefer(Boolean reefer) {
        this.reefer = reefer;
    }

    public String getThn() {
        return thn;
    }

    public void setThn(String thn) {
        this.thn = thn;
    }

    public List<String> getListTahun() {
        return listTahun;
    }

    public void setListTahun(List<String> listTahun) {
        this.listTahun = listTahun;
    }

}
