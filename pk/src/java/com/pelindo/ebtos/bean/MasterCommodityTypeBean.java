/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.bean;

import com.pelindo.ebtos.ejb.facade.remote.MasterCommodityFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterCommodityTypeFacadeRemote;
import com.pelindo.ebtos.model.db.master.MasterCommodityType;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.context.RequestContext;

/**
 *
 * @author dycoder
 */
@Named(value = "masterCommodityTypeBean")
@RequestScoped
public class MasterCommodityTypeBean {
    @EJB
    MasterCommodityTypeFacadeRemote masterCommodityTypeFacadeRemote;
    @EJB MasterCommodityFacadeRemote masterCommodityFacadeRemote;
    private MasterCommodityType masterCommodityType;
    private Boolean cekId;

    public MasterCommodityTypeBean() {
        masterCommodityType = new MasterCommodityType();
    }

    public void handleEditDeleteButton(ActionEvent event) {
        String commodity_type = (String) event.getComponent().getAttributes().get("commodity_type");
        System.out.println("booking code : " + commodity_type);
        masterCommodityType = masterCommodityTypeFacadeRemote.find(commodity_type);
        System.out.println("Edit Finish");
    }

    public void handleAdd(ActionEvent event){
        masterCommodityType = new MasterCommodityType();
        System.out.println("test" + masterCommodityType.getCommodityTypeCode());
    }
     public String getCommodityCodeType(){
        String temp=masterCommodityTypeFacadeRemote.findMasterCommodityByGenerate();
        Integer tempCode= 1 + Integer.parseInt(temp);
        String comCod="";
        temp=String.valueOf(tempCode);
        switch(temp.length()){
            case 1:
                temp="00"+temp;
                break;
            case 2:
                temp="0"+temp;
                break;
        }
        comCod=temp;
        System.out.println("ID Test : " + comCod);
        return comCod;
    }
    public void saveEdit(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage msg = null;
        boolean loggedIn = false;
        if (masterCommodityType.getName() == null) {
            loggedIn = false;
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Data belum Lengkap", "Invalid Data");
        } else {
            loggedIn = true;
            if (masterCommodityType.getCommodityTypeCode() == "") {
               masterCommodityType.setCommodityTypeCode(getCommodityCodeType());
            }
            masterCommodityTypeFacadeRemote.edit(masterCommodityType);
            masterCommodityType = new MasterCommodityType();
            System.out.println("Succes Add");
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Data Sukses Di Simpan", masterCommodityType.getName());
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
        context.addCallbackParam("loggedIn", loggedIn);

    }   
    public void delete(ActionEvent event) {
        //vesselType=new MasterVesselType();
        FacesContext context = FacesContext.getCurrentInstance();
        if(masterCommodityFacadeRemote.findMasterCommodityByCode(masterCommodityType.getCommodityTypeCode()).isEmpty()){
            masterCommodityTypeFacadeRemote.remove(masterCommodityType);
            System.out.println("Success Delete" + masterCommodityType.getCommodityTypeCode());
            masterCommodityType = new MasterCommodityType();
        }else{
             context.addMessage(null, new FacesMessage("Aborted", "delete file id= " + masterCommodityType.getCommodityTypeCode()));
             context.addMessage(null, new FacesMessage("attention", "data is cannot deleted because have relationship with primary tabel"));
        }
       

    }

    public List<Object[]> getMasterCommodityTypes() {
        return masterCommodityTypeFacadeRemote.findMasterCommodityTypes();
    }

    /**
     * @return the masterCommodityType
     */
    public MasterCommodityType getMasterCommodityType() {
        return masterCommodityType;
    }

    /**
     * @param masterCommodityType the masterCommodityType to set
     */
    public void setMasterCommodityType(MasterCommodityType masterCommodityType) {
        this.masterCommodityType = masterCommodityType;
    }

    /**
     * @return the cekId
     */
    public Boolean getCekId() {
        if (masterCommodityType.getCommodityTypeCode() == null) {
            cekId = false;
        } else {
            cekId = true;
        }
        return cekId;
    }

    /**
     * @param cekId the cekId to set
     */
    public void setCekId(Boolean cekId) {
        this.cekId = cekId;
    }
}
