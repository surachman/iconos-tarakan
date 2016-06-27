/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.bean;

import com.qtasnim.jsf.FacesHelper;
import com.pelindo.ebtos.ejb.facade.remote.MasterCommodityFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ReceivingBarangServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceCfsStuffingFacadeRemote;
import com.pelindo.ebtos.model.db.ReceivingBarangService;
import com.pelindo.ebtos.model.db.ServiceCfsStuffing;
import com.pelindo.ebtos.model.db.master.MasterCommodity;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.primefaces.context.RequestContext;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author dycoder
 */
@ManagedBean(name = "goodsReceivingConfirm")
@ViewScoped
public class GoodsReceivingConfirm implements Serializable {

    @EJB
    ReceivingBarangServiceFacadeRemote receivingBarangServiceFacadeRemote;
    private List<Object[]> listServiceCfs = lookupServiceCfsStuffingFacadeRemote().findServiceCfsStuffingByList();
    private ReceivingBarangService receivingBarangService;
    private ServiceCfsStuffing serviceCfsStuffing;
    private MasterCommodity masterCommodity;
    private String jobSlip;
    private List<Object[]> goodsReceivings;
    private Object[][] receivingBarang;
    private List<Object[]> listRest;
    private String act = "bl";
    private String bl_no;

    /** Creates a new instance of GoodsReceivingConfirm */
    public GoodsReceivingConfirm() {
        receivingBarangService = new ReceivingBarangService();
        serviceCfsStuffing = new ServiceCfsStuffing();
        masterCommodity = new MasterCommodity();
        receivingBarang = new Object[0][0];
        listRest = new ArrayList<Object[]>();
        this.act = "bl";
    }

    public void handleClear() {
        receivingBarangService = new ReceivingBarangService();
        serviceCfsStuffing = new ServiceCfsStuffing();
        masterCommodity = new MasterCommodity();
        goodsReceivings = new ArrayList<Object[]>();
    }

    public void handleAdd(ActionEvent event) {
        this.handleClear();
    }

    public void ambilContNo(ActionEvent event) {
        setJobSlip((String) event.getComponent().getAttributes().get("jobSlip"));
        Object[] temp = lookupReceivingBarangServiceFacadeRemote().findReceivingBarangServiceByStuffing(jobSlip);
        if (temp != null) {
            receivingBarangService = lookupReceivingBarangServiceFacadeRemote().find(temp[0]);
            masterCommodity = lookupMasterCommodityFacadeRemote().find(receivingBarangService.getCommodityCode());
            serviceCfsStuffing.setBlNo(receivingBarangService.getBlNo());
            serviceCfsStuffing.setCommodityCode(receivingBarangService.getCommodityCode());
            serviceCfsStuffing.setContNo(receivingBarangService.getContNo());
            serviceCfsStuffing.setMlo(receivingBarangService.getMlo());
            serviceCfsStuffing.setCountBy(receivingBarangService.getCountBy());
           // serviceCfsStuffing.setJobslip(receivingBarangService.getJobslip());
            serviceCfsStuffing.setNoPpkb(receivingBarangService.getNoPpkb());
            serviceCfsStuffing.setUnit(receivingBarangService.getUnit());
            serviceCfsStuffing.setVolume(receivingBarangService.getVolume());
            serviceCfsStuffing.setWeight(receivingBarangService.getWeight());

            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.search.found");
        } else {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.search.notfound");
            receivingBarangService = new ReceivingBarangService();
            masterCommodity = new MasterCommodity();
        }

    }

    public void handleEditDelete(ActionEvent event) {
        String id = (String) event.getComponent().getAttributes().get("job");
        receivingBarangService = lookupReceivingBarangServiceFacadeRemote().find(id);
        serviceCfsStuffing = lookupServiceCfsStuffingFacadeRemote().find(receivingBarangService.getJobslip());
    }

    public void handleDelete(ActionEvent event) {
        try {
            lookupServiceCfsStuffingFacadeRemote().remove(serviceCfsStuffing);
            listServiceCfs = lookupServiceCfsStuffingFacadeRemote().findServiceCfsStuffingByList();
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.delete.succeeded");
        } catch (EJBException ex) {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.delete.succeeded");
            ex.printStackTrace();
        }
    }

    public void handleConfirm(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();

        boolean loggedIn = false;
        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy-HHmmss");
        Date tgl = Calendar.getInstance().getTime();
        if (serviceCfsStuffing.getContNo() == null) {
            loggedIn = false;
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.save.failed");
        }
        if (serviceCfsStuffing.getJobslip() != null) {
            loggedIn = true;
            serviceCfsStuffing.setIsStuffing("FALSE");
            serviceCfsStuffing.setPlacementDate(tgl);
            lookupServiceCfsStuffingFacadeRemote().edit(serviceCfsStuffing);
            listServiceCfs = lookupServiceCfsStuffingFacadeRemote().findServiceCfsStuffingByList();
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
        } else {
            loggedIn = false;
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.save.failed");
        }
        context.addCallbackParam("loggedIn", loggedIn);
        this.handleClear();
    }

    public void cariBlNo(ActionEvent event) {
        setBl_no((String) event.getComponent().getAttributes().get("blNo"));
        goodsReceivings = lookupReceivingBarangServiceFacadeRemote().findReceivingBarangServiceByList(bl_no);
        if (goodsReceivings.size() < 1) {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.search.notfound");
        } else {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.search.found");
        }
    }

    public void cariBlNo2(ActionEvent event) {
        //setBlNo((String) event.getComponent().getAttributes().get("blNo"));
        setBl_no((String) event.getComponent().getAttributes().get("blNo"));
        goodsReceivings = lookupReceivingBarangServiceFacadeRemote().findReceivingBarangServiceByListAll();
        if (goodsReceivings.size() < 1) {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.search.notfound");
        } else {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.search.found");
        }
        this.act = "all";
    }

    private ReceivingBarangServiceFacadeRemote lookupReceivingBarangServiceFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (ReceivingBarangServiceFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/ReceivingBarangServiceFacade!com.pelindo.ebtos.ejb.facade.remote.ReceivingBarangServiceFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private MasterCommodityFacadeRemote lookupMasterCommodityFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (MasterCommodityFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/MasterCommodityFacade!com.pelindo.ebtos.ejb.facade.remote.MasterCommodityFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private ServiceCfsStuffingFacadeRemote lookupServiceCfsStuffingFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (ServiceCfsStuffingFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/ServiceCfsStuffingFacade!com.pelindo.ebtos.ejb.facade.remote.ServiceCfsStuffingFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    /**
     * @return the receivingBarangService
     */
    public ReceivingBarangService getReceivingBarangService() {
        return receivingBarangService;
    }

    /**
     * @param receivingBarangService the receivingBarangService to set
     */
    public void setReceivingBarangService(ReceivingBarangService receivingBarangService) {
        this.receivingBarangService = receivingBarangService;
    }

    /**
     * @return the serviceCfsStuffing
     */
    public ServiceCfsStuffing getServiceCfsStuffing() {
        return serviceCfsStuffing;
    }

    /**
     * @param serviceCfsStuffing the serviceCfsStuffing to set
     */
    public void setServiceCfsStuffing(ServiceCfsStuffing serviceCfsStuffing) {
        this.serviceCfsStuffing = serviceCfsStuffing;
    }

    /**
     * @return the masterCommodity
     */
    public MasterCommodity getMasterCommodity() {
        return masterCommodity;
    }

    /**
     * @param masterCommodity the masterCommodity to set
     */
    public void setMasterCommodity(MasterCommodity masterCommodity) {
        this.masterCommodity = masterCommodity;
    }

    /**
     * @return the jobSlip
     */
    public String getJobSlip() {
        return jobSlip;
    }

    /**
     * @param jobSlip the jobSlip to set
     */
    public void setJobSlip(String jobSlip) {
        this.jobSlip = jobSlip;
    }

    /**
     * @return the listServiceCfs
     */
    public List<Object[]> getListServiceCfs() {
        return listServiceCfs;
    }

    /**
     * @param listServiceCfs the listServiceCfs to set
     */
    public void setListServiceCfs(List<Object[]> listServiceCfs) {
        this.listServiceCfs = listServiceCfs;
    }

    /**
     * @return the listAutoComplete
     */
    public List<String> setListAutoComplete(String query) {
        List<String> results = new ArrayList<String>();
        results = receivingBarangServiceFacadeRemote.findReceivingBarangServiceByAutoComplete(query);
        return results;
    }

    public List<Object[]> getGoodsReceivings() {
        return goodsReceivings;
    }

    public void setGoodsReceivings(List<Object[]> goodsReceivings) {
        this.goodsReceivings = goodsReceivings;
    }

    public Object[][] getReceivingBarang() {
        //if (receivingBarang != null) {
        // listRest.add(receivingBarang);
        //}
        return receivingBarang;
    }

    public void setReceivingBarang(Object[][] receivingBarang) {
        this.receivingBarang = receivingBarang;
    }

    public List<Object[]> getListRest() {
        return listRest;
    }

    public void setListRest(List<Object[]> listRest) {
        this.listRest = listRest;
    }

    public void handleSelectClik(ActionEvent event) {
        //lookupServiceCfsStuffingFacadeRemote().create(serviceCfsStuffing);
//        listRest.add(receivingBarang);
//        System.out.println("sdkjfddffffllld" + listRest.size() + "sdfdggggggs" +listRest.set(0, receivingBarang));
//        System.out.println("sdkjfddffffllld" + serviceCfsStuffing.getJobslip());
//        for (Object obj : receivingBarang) {
//
////            serviceCfsStuffing.setJobslip(obj);
////            serviceCfsStuffing.setNoPpkb(obj.toString());
//            System.out.println("sdkjfdffff3625fddd" + obj);
//            //receivingBarangService=lookupReceivingBarangServiceFacadeRemote().find(obj);
//            System.out.println("sdkjfdfffffddd" + receivingBarangService.getJobslip());
//        }
//
        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy-HHmmss");
        Date tgl = Calendar.getInstance().getTime();
        for (int i = 0; i < receivingBarang.length; i++) {
            receivingBarangService = lookupReceivingBarangServiceFacadeRemote().find(receivingBarang[i][0].toString());
            serviceCfsStuffing.setBlNo(receivingBarangService.getBlNo());
            serviceCfsStuffing.setCommodityCode(receivingBarangService.getCommodityCode());
            serviceCfsStuffing.setContNo(receivingBarangService.getContNo());
            serviceCfsStuffing.setMlo(receivingBarangService.getMlo());
            serviceCfsStuffing.setCountBy(receivingBarangService.getCountBy());
           // serviceCfsStuffing.setJobslip(receivingBarangService.getJobslip());
            serviceCfsStuffing.setNoPpkb(receivingBarangService.getNoPpkb());
            serviceCfsStuffing.setUnit(receivingBarangService.getUnit());
            serviceCfsStuffing.setVolume(receivingBarangService.getVolume());
            serviceCfsStuffing.setWeight(receivingBarangService.getWeight());
            serviceCfsStuffing.setIsStuffing("FALSE");
            serviceCfsStuffing.setPlacementDate(tgl);
            lookupServiceCfsStuffingFacadeRemote().edit(serviceCfsStuffing);
            if (getAct().equals("all")) {
                goodsReceivings = lookupReceivingBarangServiceFacadeRemote().findReceivingBarangServiceByListAll();
            } else {
                goodsReceivings = lookupReceivingBarangServiceFacadeRemote().findReceivingBarangServiceByList(bl_no);
            }
            listServiceCfs = lookupServiceCfsStuffingFacadeRemote().findServiceCfsStuffingByList();
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
        }
    }

    public String getAct() {
        return act;
    }

    public void setAct(String act) {
        this.act = act;
    }

    public String getBl_no() {
        return bl_no;
    }

    public void setBl_no(String bl_no) {
        this.bl_no = bl_no;
    }
}
