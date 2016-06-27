/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.bean;

import com.qtasnim.jsf.FacesHelper;
import com.pelindo.ebtos.ejb.facade.remote.MasterCommodityFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterSettingAppFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterTarifContFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PerhitunganPenumpukanBarangFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningVesselFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceCfsFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceCfsStrippingFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceCfsStuffingFacadeRemote;
import com.pelindo.ebtos.model.db.PerhitunganPenumpukanBarang;
import com.pelindo.ebtos.model.db.PlanningVessel;
import com.pelindo.ebtos.model.db.ServiceCfs;
import com.pelindo.ebtos.model.db.ServiceCfsStripping;
import com.pelindo.ebtos.model.db.ServiceCfsStuffing;
import com.pelindo.ebtos.model.db.master.MasterCommodity;
import com.qtasnim.util.DateCalculator;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.context.RequestContext;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author dycoder
 */
@ManagedBean(name = "stuffingCfsBean")
@ViewScoped
public class StuffingCfsBean implements Serializable {
    @EJB
    private MasterSettingAppFacadeRemote masterSettingAppFacade;
    @EJB
    private PerhitunganPenumpukanBarangFacadeRemote perhitunganPenumpukanBarangFacadeRemote;
    @EJB
    private ServiceCfsStuffingFacadeRemote serviceCfsStuffingFacadeRemote;
    @EJB
    private MasterCommodityFacadeRemote masterCommodityFacadeRemote;
    @EJB
    private PlanningVesselFacadeRemote planningVesselFacadeRemote;
    @EJB
    private MasterTarifContFacadeRemote masterTarifContFacadeRemote;

    private List<Object[]> listServiceCfs;
    private List<Object[]> listPenumpukanObject;
    private List<PerhitunganPenumpukanBarang> listPenumpukan;
    private PerhitunganPenumpukanBarang perhitunganPenumpukanBarang;
    private ServiceCfsStuffing serviceCfsStuffing;
    private MasterCommodity masterCommodity;
    private String jobSlip;
    private PlanningVessel planningVessel;
    private Object[] tmp;
    private String mode;
    private boolean disable = true;
    private List<Object[]> stuffingList;
    private Object[][] stuffingObject;
    private String blno;
    private String act = "bl";
    ServiceCfsFacadeRemote serviceCfsFacade = lookupServiceCfsFacadeRemote();
    private List<Object[]> serviceCfss;
    private Date tanggal;
    private ServiceCfsStripping serviceCfsStripping;
    private List<Object[]> serviceCfsStrippings;
    private List<Object[]> serviceCfsStuffings;
    private List<Object[]> commoditys;
    MasterCommodityFacadeRemote masterCommodityFacade = lookupMasterCommodityFacadeRemote();
    ServiceCfsStrippingFacadeRemote serviceCfsStrippingFacade = lookupServiceCfsStrippingFacadeRemote();
    ServiceCfsStuffingFacadeRemote serviceCfsStuffingFacade = lookupServiceCfsStuffingFacadeRemote();
    /** Creates a new instance of StuffingCfsBean */
    public StuffingCfsBean() {
    serviceCfsStripping = new ServiceCfsStripping();
        serviceCfsStrippings = serviceCfsStrippingFacade.findAllNative();
        serviceCfsStuffings = serviceCfsStuffingFacade.findAllNative();
        commoditys = masterCommodityFacade.findAllNative();
    }

    @PostConstruct
    private void construct() {
        perhitunganPenumpukanBarang = new PerhitunganPenumpukanBarang();
        serviceCfsStuffing = new ServiceCfsStuffing();
        masterCommodity = new MasterCommodity();
        stuffingList = new ArrayList<Object[]>();
        disable = true;
        stuffingObject = new Object[0][0];
        act = "bl";

        perhitunganPenumpukanBarangFacadeRemote.findPerhitunganPenumpukanBarangByList();
        listPenumpukan = perhitunganPenumpukanBarangFacadeRemote.findAll();
    }

    public void clear() {
        perhitunganPenumpukanBarang = new PerhitunganPenumpukanBarang();
        serviceCfsStuffing = new ServiceCfsStuffing();
        masterCommodity = new MasterCommodity();
        this.disable = true;
        stuffingList=new ArrayList<Object[]>();
    }

    public void ambilContNo(ActionEvent event) {
        setJobSlip((String) event.getComponent().getAttributes().get("jobSlip"));
        Object[] temp = serviceCfsStuffingFacadeRemote.findServiceCfsStuffingByPenumpukan(jobSlip);
        if (temp != null) {
            serviceCfsStuffing = serviceCfsStuffingFacadeRemote.find(temp[0]);
            masterCommodity = masterCommodityFacadeRemote.find(serviceCfsStuffing.getCommodityCode());
            perhitunganPenumpukanBarang.setBlNo(serviceCfsStuffing.getBlNo());
            perhitunganPenumpukanBarang.setCommodityCode(serviceCfsStuffing.getCommodityCode());
            perhitunganPenumpukanBarang.setContNo(serviceCfsStuffing.getContNo());
            perhitunganPenumpukanBarang.setMlo(serviceCfsStuffing.getMlo());
           // perhitunganPenumpukanBarang.setJobslip(serviceCfsStuffing.getJobslip());
            perhitunganPenumpukanBarang.setNoPpkb(serviceCfsStuffing.getNoPpkb());
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.search.found");
            this.disable = false;
        } else {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.search.notfound");
            this.clear();

        }

    }

    public List<String> setListAutoComplete(String query) {
        List<String> results = new ArrayList<String>();
        results = serviceCfsStuffingFacadeRemote.findServiceCfsStuffingByAutoComplete(query);
        return results;
    }

    public String getUrlReceiving() {
        if (listPenumpukanObject == null) {
            return "#";
        }

        setMode("stuffingGoodsReceiving");
        return FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/goodsCfs.pdf?"
                + "mode=" + getMode() + "";
    }

    public void handleEditDelete(ActionEvent event) {
        String id = (String) event.getComponent().getAttributes().get("job");
        serviceCfsStuffing = serviceCfsStuffingFacadeRemote.find(id);
       // setTmp(perhitunganPenumpukanBarangFacadeRemote.findPerhitunganPenumpukanBarangByEdit(serviceCfsStuffing.getJobslip(), serviceCfsStuffing.getContNo()));

        perhitunganPenumpukanBarang = perhitunganPenumpukanBarangFacadeRemote.find(getTmp()[0]);
    }

    public void handleAdd(ActionEvent actionEvent) {
        serviceCfsStripping = new ServiceCfsStripping();
        serviceCfsStuffing = new ServiceCfsStuffing();
        serviceCfss = serviceCfsFacade.findStuffing();
        this.tanggal=Calendar.getInstance().getTime();
    }
    
        public void handleSelectContPick(ActionEvent event) {
        Integer id_cfs = (Integer) event.getComponent().getAttributes().get("idCfs");
        ServiceCfs cfs = serviceCfsFacade.find(id_cfs);
        serviceCfsStuffing.setContNo(cfs.getContNo());
        serviceCfsStuffing.setMlo(cfs.getMlo());
        serviceCfsStuffing.setNoPpkb(cfs.getNoPpkb());
        tanggal=serviceCfsStuffing.getPlacementDate();
    }

    public void handleDelete(ActionEvent event) {
        try {
            serviceCfsStuffing.setIsStuffing("FALSE");
            serviceCfsStuffing.setStuffingDate(null);
            serviceCfsStuffing.setMasa1(null);
            serviceCfsStuffing.setMasa2(null);
            serviceCfsStuffingFacadeRemote.edit(serviceCfsStuffing);
            perhitunganPenumpukanBarangFacadeRemote.remove(perhitunganPenumpukanBarang);
            listPenumpukanObject = perhitunganPenumpukanBarangFacadeRemote.findPerhitunganPenumpukanBarangByList();
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.delete.succeeded");
            this.clear();
        } catch (EJBException ex) {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.delete.succeeded");
            ex.printStackTrace();
        }

    }

    public void handleConfirm(ActionEvent event) {
        /**RequestContext context = RequestContext.getCurrentInstance();
        boolean loggedIn = false;
        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy-HHmmss");
        Date tgl = Calendar.getInstance().getTime();
        if (perhitunganPenumpukanBarang.getJobslip() != null) {
            loggedIn = true;
            serviceCfsStuffing = serviceCfsStuffingFacadeRemote.find(jobSlip);
            serviceCfsStuffing.setIsStuffing("TRUE");
            serviceCfsStuffing.setStuffingDate(tgl);

            int min = DateCalculator.countRangeInDays(serviceCfsStuffing.getStuffingDate(), serviceCfsStuffing.getPlacementDate()) + 1;
            int masaFree = masterSettingAppFacade.getMasa1FreeRange();
            int masa1Range = masterSettingAppFacade.getMasa1ContainerRange();
            int m1 = min - (masaFree - 1);

            if (min < (masa1Range + 1)) {
                if (min <= masaFree) {
                    serviceCfsStuffing.setMasa1(1);
                } else {
                    serviceCfsStuffing.setMasa1(m1);
                }
                serviceCfsStuffing.setMasa2(0);
            } else {
                Integer masa2 = min - masa1Range;
                Integer masa1 = m1 - masa2;
                serviceCfsStuffing.setMasa1(masa1);
                serviceCfsStuffing.setMasa2(masa2);
            }

            serviceCfsStuffingFacadeRemote.edit(serviceCfsStuffing);

            String actMov = null;
            BigDecimal tarif = BigDecimal.ZERO;
            BigDecimal tumpuk = BigDecimal.ZERO;
            BigDecimal jasaDermaga = BigDecimal.ZERO;
            planningVessel = planningVesselFacadeRemote.find(serviceCfsStuffing.getNoPpkb());
            if (getPlanningVessel().getTipePelayaran().equalsIgnoreCase("d")) {
                //tarif = masterTarifContFacadeRemote.findByActivityAndCurr("IDR", serviceCfsStuffing.getCountBy());
                jasaDermaga = masterTarifContFacadeRemote.findByCurrCodeAndActivity("IDR", "J001");
                perhitunganPenumpukanBarang.setCurrCode("IDR");

                if (serviceCfsStuffing.getCountBy().equalsIgnoreCase("WEIGHT")) {
                    perhitunganPenumpukanBarang.setActivityCode("M001");
                    tumpuk = masterTarifContFacadeRemote.findByCurrCodeAndActivity("IDR", perhitunganPenumpukanBarang.getActivityCode());
                } else if (serviceCfsStuffing.getCountBy().equalsIgnoreCase("VOLUME")) {
                    perhitunganPenumpukanBarang.setActivityCode("M002");

                    tumpuk = masterTarifContFacadeRemote.findByCurrCodeAndActivity("IDR", perhitunganPenumpukanBarang.getActivityCode());
                }

            } else if (getPlanningVessel().getTipePelayaran().equalsIgnoreCase("i")) {
                //tarif = masterTarifContFacadeRemote.findByActivityAndCurr("USD", serviceCfsStuffing.getCountBy());
                jasaDermaga = masterTarifContFacadeRemote.findByCurrCodeAndActivity("USD", "J001");
                perhitunganPenumpukanBarang.setCurrCode("USD");

                if (serviceCfsStuffing.getCountBy().equalsIgnoreCase("WEIGHT")) {
                    perhitunganPenumpukanBarang.setActivityCode("M001");
                    tumpuk = masterTarifContFacadeRemote.findByCurrCodeAndActivity("USD", perhitunganPenumpukanBarang.getActivityCode());
                } else if (serviceCfsStuffing.getCountBy().equalsIgnoreCase("VOLUME")) {
                    perhitunganPenumpukanBarang.setActivityCode("M002");
                    tumpuk = masterTarifContFacadeRemote.findByCurrCodeAndActivity("USD", perhitunganPenumpukanBarang.getActivityCode());
                }

            }

            perhitunganPenumpukanBarang.setOperation("RECEIVING");
            perhitunganPenumpukanBarang.setChargePenumpukan(tumpuk);
            perhitunganPenumpukanBarang.setJasaDermaga(jasaDermaga);
            Double l = Double.parseDouble(serviceCfsStuffing.getMasa1().toString());
            Double k = Double.parseDouble(serviceCfsStuffing.getMasa2().toString());
            perhitunganPenumpukanBarang.setChargeMasa1(tumpuk.multiply(BigDecimal.valueOf(l)));
            perhitunganPenumpukanBarang.setChargeMasa2(tumpuk.multiply(BigDecimal.valueOf(k)));
            perhitunganPenumpukanBarang.setTotalCharge(tumpuk.add(perhitunganPenumpukanBarang.getJasaDermaga().add(perhitunganPenumpukanBarang.getChargeMasa1().add(perhitunganPenumpukanBarang.getChargeMasa2()))));
            perhitunganPenumpukanBarangFacadeRemote.edit(perhitunganPenumpukanBarang);
            listPenumpukanObject = perhitunganPenumpukanBarangFacadeRemote.findPerhitunganPenumpukanBarangByList();
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
        } else {
            loggedIn = false;
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.save.failed");
        }
        context.addCallbackParam("loggedIn", loggedIn);
        this.clear(); **/
        boolean loggedIn = false;
        if (serviceCfsStuffing.getContNo() == null || serviceCfsStuffing.getCommodityCode() == null || serviceCfsStuffing.getUnit() == null
                || serviceCfsStuffing.getVolume() == null || serviceCfsStuffing.getWeight() == null) {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.save.failed");
        } else {
            loggedIn = true;
            Date now = Calendar.getInstance().getTime();
            serviceCfsStuffing.setPlacementDate(tanggal);
            serviceCfsStuffingFacade.edit(serviceCfsStuffing);

            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
            RequestContext.getCurrentInstance().addCallbackParam("loggedIn", loggedIn);
            serviceCfsStuffings = serviceCfsStuffingFacade.findAllNative();
            serviceCfss = serviceCfsFacade.findStuffing();
            //setCommoditys(masterCommodityFacade.findAll());
          }
    }

    public void cariBlNo(ActionEvent event) {
        setBlno((String) event.getComponent().getAttributes().get("blNo"));
        stuffingList = serviceCfsStuffingFacadeRemote.findServiceCfsStuffingAllAndBl(blno);
        if (stuffingList.size() < 1) {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.search.notfound");
        } else {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.search.found");
        }
    }

    public void cariBlNo2(ActionEvent event) {
        //setBlNo((String) event.getComponent().getAttributes().get("blNo"));
        setBlno((String) event.getComponent().getAttributes().get("blNo"));
        stuffingList = serviceCfsStuffingFacadeRemote.findServiceCfsStuffingAll();
        if (stuffingList.size() < 1) {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.search.notfound");
        } else {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.search.found");
        }
        this.act = "all";
    }

    public void handleSelectClik(ActionEvent event) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy-HHmmss");
        Date tgl = Calendar.getInstance().getTime();
        for (int i = 0; i < stuffingObject.length; i++) {
            serviceCfsStuffing = serviceCfsStuffingFacadeRemote.find(stuffingObject[i][0].toString());
            perhitunganPenumpukanBarang.setBlNo(serviceCfsStuffing.getBlNo());
            perhitunganPenumpukanBarang.setCommodityCode(serviceCfsStuffing.getCommodityCode());
            perhitunganPenumpukanBarang.setContNo(serviceCfsStuffing.getContNo());
            perhitunganPenumpukanBarang.setMlo(serviceCfsStuffing.getMlo());
           // perhitunganPenumpukanBarang.setJobslip(serviceCfsStuffing.getJobslip());
            perhitunganPenumpukanBarang.setNoPpkb(serviceCfsStuffing.getNoPpkb());

            //serviceCfsStuffing = serviceCfsStuffingFacadeRemote.find(jobSlip);
            serviceCfsStuffing.setIsStuffing("TRUE");
            serviceCfsStuffing.setStuffingDate(tgl);

            int min = DateCalculator.countRangeInDays(serviceCfsStuffing.getStuffingDate(), serviceCfsStuffing.getPlacementDate()) + 1;

            int m1 = min - 2;
            if (min < 13) {

                if (min < 4) {
                    serviceCfsStuffing.setMasa1(1);
                } else {
                    serviceCfsStuffing.setMasa1(m1);
                }
                serviceCfsStuffing.setMasa2(0);
            } else {

                int masa2 = min - 12;
                int masa1 = m1 - masa2;
                serviceCfsStuffing.setMasa1(masa1);
                serviceCfsStuffing.setMasa2(masa2);
            }
            serviceCfsStuffingFacadeRemote.edit(serviceCfsStuffing);

            String actMov = null;
            BigDecimal tarif = BigDecimal.ZERO;
            BigDecimal tumpuk = BigDecimal.ZERO;
            BigDecimal jasaDermaga = BigDecimal.ZERO;

//            BigDecimal disct=BigDecimal.ZERO;
//            BigDecimal totalDisct=BigDecimal.ZERO;
//            disct=lookupMasterDiscountFacadeRemote().getValidDiscount(serviceCfsStuffing., actTumpuk);

            planningVessel = planningVesselFacadeRemote.find(serviceCfsStuffing.getNoPpkb());
            if (getPlanningVessel().getTipePelayaran().equalsIgnoreCase("d")) {
                //tarif = masterTarifContFacadeRemote.findByActivityAndCurr("IDR", serviceCfsStuffing.getCountBy());
                jasaDermaga = masterTarifContFacadeRemote.findByCurrCodeAndActivity("IDR", "J001");
                perhitunganPenumpukanBarang.setCurrCode("IDR");

                if (serviceCfsStuffing.getCountBy().equalsIgnoreCase("WEIGHT")) {
                    perhitunganPenumpukanBarang.setActivityCode("M001");
                    tumpuk = masterTarifContFacadeRemote.findByCurrCodeAndActivity("IDR", perhitunganPenumpukanBarang.getActivityCode());
                } else if (serviceCfsStuffing.getCountBy().equalsIgnoreCase("VOLUME")) {
                    perhitunganPenumpukanBarang.setActivityCode("M002");

                    tumpuk = masterTarifContFacadeRemote.findByCurrCodeAndActivity("IDR", perhitunganPenumpukanBarang.getActivityCode());
                }

            } else if (getPlanningVessel().getTipePelayaran().equalsIgnoreCase("i")) {
                //tarif = masterTarifContFacadeRemote.findByActivityAndCurr("USD", serviceCfsStuffing.getCountBy());
                jasaDermaga = masterTarifContFacadeRemote.findByCurrCodeAndActivity("USD", "J001");
                perhitunganPenumpukanBarang.setCurrCode("USD");

                if (serviceCfsStuffing.getCountBy().equalsIgnoreCase("WEIGHT")) {
                    perhitunganPenumpukanBarang.setActivityCode("M001");
                    tumpuk = masterTarifContFacadeRemote.findByCurrCodeAndActivity("USD", perhitunganPenumpukanBarang.getActivityCode());
                } else if (serviceCfsStuffing.getCountBy().equalsIgnoreCase("VOLUME")) {
                    perhitunganPenumpukanBarang.setActivityCode("M002");
                    tumpuk = masterTarifContFacadeRemote.findByCurrCodeAndActivity("USD", perhitunganPenumpukanBarang.getActivityCode());
                }

            }

            perhitunganPenumpukanBarang.setOperation("RECEIVING");
            perhitunganPenumpukanBarang.setChargePenumpukan(tumpuk);
            perhitunganPenumpukanBarang.setJasaDermaga(jasaDermaga);
            Double l = Double.parseDouble(serviceCfsStuffing.getMasa1().toString());
            Double k = Double.parseDouble(serviceCfsStuffing.getMasa2().toString());
            perhitunganPenumpukanBarang.setChargeMasa1(tumpuk.multiply(BigDecimal.valueOf(l)));
            perhitunganPenumpukanBarang.setChargeMasa2(tumpuk.multiply(BigDecimal.valueOf(k)));
            perhitunganPenumpukanBarang.setTotalCharge(tumpuk.add(perhitunganPenumpukanBarang.getJasaDermaga().add(perhitunganPenumpukanBarang.getChargeMasa1().add(perhitunganPenumpukanBarang.getChargeMasa2()))));
            perhitunganPenumpukanBarangFacadeRemote.edit(perhitunganPenumpukanBarang);
            listPenumpukanObject = perhitunganPenumpukanBarangFacadeRemote.findPerhitunganPenumpukanBarangByList();

            if (getAct().equals("all")) {
                stuffingList = serviceCfsStuffingFacadeRemote.findServiceCfsStuffingAll();
            } else {
                stuffingList = serviceCfsStuffingFacadeRemote.findServiceCfsStuffingAllAndBl(blno);
            }
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
        }
    }

    /**
     * @return the receivingBarangService
     */
    public PerhitunganPenumpukanBarang getPerhitunganPenumpukanBarang() {
        return perhitunganPenumpukanBarang;
    }

    /**
     * @param receivingBarangService the receivingBarangService to set
     */
    public void setPerhitunganPenumpukanBarang(PerhitunganPenumpukanBarang perhitunganPenumpukanBarang) {
        this.perhitunganPenumpukanBarang = perhitunganPenumpukanBarang;
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
     * @return the planningVessel
     */
    public PlanningVessel getPlanningVessel() {
        return planningVessel;
    }

    /**
     * @param planningVessel the planningVessel to set
     */
    public void setPlanningVessel(PlanningVessel planningVessel) {
        this.planningVessel = planningVessel;
    }

    /**
     * @return the listPenumpukan
     */
    public List<PerhitunganPenumpukanBarang> getListPenumpukan() {
        return listPenumpukan;
    }

    /**
     * @param listPenumpukan the listPenumpukan to set
     */
    public void setListPenumpukan(List<PerhitunganPenumpukanBarang> listPenumpukan) {
        this.listPenumpukan = listPenumpukan;
    }

     private ServiceCfsFacadeRemote lookupServiceCfsFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (ServiceCfsFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/ServiceCfsFacade!com.pelindo.ebtos.ejb.facade.remote.ServiceCfsFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
     
    /**
     * @return the tmp
     */
    public Object[] getTmp() {
        return tmp;
    }

    /**
     * @param tmp the tmp to set
     */
    public void setTmp(Object[] tmp) {
        this.tmp = tmp;
    }

    /**
     * @return the listPenumpukanObject
     */
    public List<Object[]> getListPenumpukanObject() {
        return listPenumpukanObject;
    }

    /**
     * @param listPenumpukanObject the listPenumpukanObject to set
     */
    public void setListPenumpukanObject(List<Object[]> listPenumpukanObject) {
        this.listPenumpukanObject = listPenumpukanObject;
    }

    /**
     * @return the mode
     */
    public String getMode() {
        return mode;
    }

    /**
     * @param mode the mode to set
     */
    public void setMode(String mode) {
        this.mode = mode;
    }

    public boolean isDisable() {
        return disable;
    }

    public void setDisable(boolean disable) {
        this.disable = disable;
    }

    public List<Object[]> getStuffingList() {
        return stuffingList;
    }

    public void setStuffingList(List<Object[]> stuffingList) {
        this.stuffingList = stuffingList;
    }

    public Object[][] getStuffingObject() {
        return stuffingObject;
    }

    public void setStuffingObject(Object[][] stuffingObject) {
        this.stuffingObject = stuffingObject;
    }

    public String getAct() {
        return act;
    }

    public void setAct(String act) {
        this.act = act;
    }

    public String getBlno() {
        return blno;
    }

    public void setBlno(String blno) {
        this.blno = blno;
    }
    
    /**
     * @return the serviceCfss
     */
    public List<Object[]> getServiceCfss() {
        return serviceCfss;
    }

    /**
     * @param serviceCfss the serviceCfss to set
     */
    public void setServiceCfss(List<Object[]> serviceCfss) {
        this.serviceCfss = serviceCfss;
    }
    
        /**
     * @return the serviceCfsStrippings
     */
    public List<Object[]> getServiceCfsStrippings() {
        return serviceCfsStrippings;
    }

    /**
     * @param serviceCfsStrippings the serviceCfsStrippings to set
     */
    public void setServiceCfsStrippings(List<Object[]> serviceCfsStrippings) {
        this.serviceCfsStrippings = serviceCfsStrippings;
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
   private ServiceCfsStrippingFacadeRemote lookupServiceCfsStrippingFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (ServiceCfsStrippingFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/ServiceCfsStrippingFacade!com.pelindo.ebtos.ejb.facade.remote.ServiceCfsStrippingFacadeRemote");
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
   
       public List<Object[]> getCommoditys() {
        return commoditys;
    }

    /**
     * @param commoditys the commoditys to set
     */
    public void setCommoditys(List<Object[]> commoditys) {
        this.commoditys = commoditys;
    }
    
        public Date getTanggal() {
        return tanggal;
    }

    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }
    
/**
     * @return the serviceCfsStuffings
     */
    public List<Object[]> getServiceCfsStuffings() {
        return serviceCfsStuffings;
    }

    /**
     * @param serviceCfsStuffings the serviceCfsStuffings to set
     */
    public void setServiceCfsStuffings(List<Object[]> serviceCfsStuffings) {
        this.serviceCfsStuffings = serviceCfsStuffings;
    }
}
