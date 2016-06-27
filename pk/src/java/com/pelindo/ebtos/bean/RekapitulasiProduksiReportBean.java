/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.bean;

import com.pelindo.ebtos.ejb.facade.remote.MasterOwnerEquipmentFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterCustomerFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterEquipmentFacadeRemote;
import com.pelindo.ebtos.model.db.master.MasterOwnerEquipment;
import com.pelindo.ebtos.model.db.master.MasterCustomer;
import com.pelindo.ebtos.model.db.master.MasterEquipment;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.event.ActionEvent;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.apache.commons.lang3.StringEscapeUtils;
import org.primefaces.context.RequestContext;

/**
 *
 * @author dycoder
 */
@ManagedBean(name = "rekapitulasiProduksiReportBean")
@ViewScoped
public class RekapitulasiProduksiReportBean implements Serializable {

    /** Creates a new instance of RekapitulasiProduksiReportBean */
    private int bulan;
    private int tahun;
    private List<Integer> listTahun = new ArrayList<Integer>();
    private List<MasterOwnerEquipment> mOwnerEquipmentList = lookupMOwnerEquipmentFacadeRemote().findAll();
    private List<MasterEquipment> masterEquipments = lookupMasterEquipmentFacadeRemote().findAll();
    private List<MasterCustomer> customers = lookupMasterCustomerFacadeRemote().findAll();
    private List<Object[]> eqForCrane = lookupMasterEquipmentFacadeRemote().findCraneExcKapal();
    private List<Object[]> listEquipment;
    private Date tanggal1, tanggal2;
    private String koordinator;
    private String owner_code;
    private String equip_code;
    private String cust_code;
    private DailyOperationReport dailyOperationReport;
    private String pnJawab, supervisor;
    private String tipe;
    private String pelaksanaGate;
    private String no_ppkb;

    public RekapitulasiProduksiReportBean() {
        Calendar cal = new GregorianCalendar();
        this.bulan = cal.get(Calendar.MONTH);
        this.tahun = cal.get(Calendar.YEAR);
        this.listTahun = new ArrayList<Integer>();
        this.owner_code = mOwnerEquipmentList.get(0).getOwnerCode();
        this.cust_code = customers.get(0).getCustCode();
        this.equip_code = masterEquipments.get(0).getEquipCode();
        listEquipment = lookupMasterEquipmentFacadeRemote().findOwnerReport(mOwnerEquipmentList.get(0).getOwnerCode());
        tanggal2 = cal.getTime();
        cal.add(Calendar.DATE, -1);
        tanggal1 = cal.getTime();
        this.tipe = "k";
    }

    public int getBulan() {
        return bulan;
    }

    public void setBulan(int bulan) {
        this.bulan = bulan;
    }

    public int getTahun() {
        return tahun;
    }

    public void setTahun(int tahun) {
        this.tahun = tahun;
    }

    public List<Integer> getListTahun() {
        for (int i = 1990; i < 2060; i++) {
            listTahun.add(i);
        }
        return listTahun;
    }

    public String getOwner_code() {
        return owner_code;
    }

    public void setOwner_code(String owner_code) {
        this.owner_code = owner_code;
    }

    public void setListTahun(List<Integer> listTahun) {

        this.listTahun = listTahun;
    }

    public List<MasterOwnerEquipment> getmOwnerEquipmentList() {
        return mOwnerEquipmentList;
    }

    public void setmOwnerEquipmentList(List<MasterOwnerEquipment> mOwnerEquipmentList) {
        this.mOwnerEquipmentList = mOwnerEquipmentList;
    }

    public List<MasterEquipment> getMasterEquipments() {
        return masterEquipments;
    }

    public void setMasterEquipments(List<MasterEquipment> masterEquipments) {
        this.masterEquipments = masterEquipments;
    }

    public String getCust_code() {
        return cust_code;
    }

    public void setCust_code(String cust_code) {
        this.cust_code = cust_code;
    }

    /**
     * @return the eqForCrane
     */
    public List<Object[]> getEqForCrane() {
        return eqForCrane;
    }

    /**
     * @param eqForCrane the eqForCrane to set
     */
    public void setEqForCrane(List<Object[]> eqForCrane) {
        this.eqForCrane = eqForCrane;
    }

    public List<Object[]> getListEquipment() {
        return listEquipment;
    }

    public void setListEquipment(List<Object[]> listEquipment) {
        this.listEquipment = listEquipment;
    }

    public DailyOperationReport getDailyOperationReport() {
        return dailyOperationReport;
    }

    public void setDailyOperationReport(DailyOperationReport dailyOperationReport) {
        this.dailyOperationReport = dailyOperationReport;
    }

    public void onChangeEventBulan(ValueChangeEvent event) {
        int temp = (Integer) event.getNewValue();
        setBulan(temp);
        System.out.println("dsd" + temp);
    }

    public void onChangeEvent(ValueChangeEvent event) {
        int temp = (Integer) event.getNewValue();
        setTahun(temp);
        System.out.println("dsd" + temp);
    }

    public void onChangeEventCustomer(ValueChangeEvent event) {
        String temp = (String) event.getNewValue();
        setCust_code(temp);
    }

    public String getEquip_code() {
        return equip_code;
    }

    public void setEquip_code(String equip_code) {
        this.equip_code = equip_code;
    }

    public void onChangeEventEquipment(ValueChangeEvent event) {
        this.equip_code = (String) event.getNewValue();
    }

    public void onChangeEventEquipmentExcKapal(ValueChangeEvent event) {
        this.equip_code = (String) event.getNewValue();
    }

    public List<MasterCustomer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<MasterCustomer> customers) {
        this.customers = customers;
    }

    public void onChangeEventOwner(ValueChangeEvent event) {
        this.owner_code = (String) event.getNewValue();
        this.listEquipment = lookupMasterEquipmentFacadeRemote().findOwnerReport(owner_code);
    }

    public String getUrlGlobal() {

        return FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/rekapitulasiRealisasiProduksi.pdf?"
                + "mode=" + "ProduksiGlobal" + "&"
                + "bulan=" + bulan + "&"
                + "tahun=" + tahun + " ";
    }

    public String getUrlProduksiDetail() {

        return FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/rekapitulasiRealisasiProduksi.pdf?"
                + "mode=" + "ProduksiDetail" + "&"
                + "bulan=" + bulan + "&"
                + "tahun=" + tahun + " ";
    }

    public String getUrlProduksiPendapatan() {
        return FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/rekapitulasiRealisasiProduksi.pdf?"
                + "mode=" + "ProduksiPendapatan" + "&"
                + "bulan=" + bulan + "&"
                + "tahun=" + tahun + " ";
    }

    public void handleDownloadReport10(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        context.addCallbackParam("doPrint", true); //harus ada, buat ngeflag di izinkan ngeprint ato ngga
        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3("../../../rekapitulasiRealisasiReport07.pdf?mode=" + "report10"
                + "&bulan=" + bulan
                + "&tahun=" + tahun
                + "&cust_code=" + cust_code));
    }
    public void handleDownloadReport10Excel(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        context.addCallbackParam("doPrint", true); //harus ada, buat ngeflag di izinkan ngeprint ato ngga
        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3("../../../RekapitulasiRealisasiReport07Excel.csv?mode=" + "report10"
                + "&bulan=" + bulan
                + "&tahun=" + tahun
                + "&cust_code=" + cust_code));
    }

//    public String getUrlProduksiDanPendapatanReport10() {
//        return FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/rekapitulasiRealisasiReport07.pdf?"
//                + "mode=" + "report10" + "&"
//                + "bulan=" + bulan + "&"
//                + "cust_code=" + cust_code + "&"
//                + "tahun=" + tahun + " ";
//    }
    public void handleDownloadReport11(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        context.addCallbackParam("doPrint", true); //harus ada, buat ngeflag di izinkan ngeprint ato ngga
        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3("../../../rekapitulasiRealisasiReport07.pdf?mode=" + "report11"
                + "&bulan=" + bulan
                + "&tahun=" + tahun));
    }
    public void handleDownloadReport11Excel(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        context.addCallbackParam("doPrint", true); //harus ada, buat ngeflag di izinkan ngeprint ato ngga
        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3("../../../RekapitulasiRealisasiReport07Excel.csv?mode=" + "report11"
                + "&bulan=" + bulan
                + "&tahun=" + tahun));
    }

//    public String getUrlReport11() {
//        return FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/rekapitulasiRealisasiReport07.pdf?"
//                + "mode=" + "report11" + "&"
//                + "bulan=" + bulan + "&"
//                + "cust_code=" + cust_code + "&"
//                + "tahun=" + tahun + " ";
//    }
    public void handleDownloadReport12(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        context.addCallbackParam("doPrint", true); //harus ada, buat ngeflag di izinkan ngeprint ato ngga
        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3("../../../rekapitulasiRealisasiProduksi.pdf?mode=" + "report12"
                + "&bulan=" + bulan
                + "&tahun=" + tahun
                + "&owner=" + owner_code));
    }

//    public String getUrlReport12() {
//        return FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/rekapitulasiRealisasiProduksi.pdf?"
//                + "mode=" + "report12" + "&"
//                + "bulan=" + bulan + "&"
//                + "owner=" + owner_code + "&"
//                + "tahun=" + tahun + " ";
//    }
    public void handleDownloadReport04(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        context.addCallbackParam("doPrint", true); //harus ada, buat ngeflag di izinkan ngeprint ato ngga
        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3("../../../report04.pdf?"
                + "&bulan=" + bulan
                + "&tahun=" + tahun
                + "&owner=" + owner_code
                + "&equipCode=" + equip_code));
    }

//    public String getUrlReport04() {
//        return FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/report04.pdf?"
//                + "mode=" + "report10" + "&"
//                + "bulan=" + bulan + "&"
//                + "owner=" + owner_code + "&"
//                + "equipCode=" + equip_code + "&"
//                + "tahun=" + tahun + " ";
//    }
    public void handleDownloadReport07(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        context.addCallbackParam("doPrint", true); //harus ada, buat ngeflag di izinkan ngeprint ato ngga
        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3("../../../rekapitulasiRealisasiReport07.pdf?mode=" + "report07"
                + "&bulan=" + bulan
                + "&tahun=" + tahun));
    }
    public void handleDownloadReport07Excel(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        context.addCallbackParam("doPrint", true); //harus ada, buat ngeflag di izinkan ngeprint ato ngga
        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3("../../../RekapitulasiRealisasiReport07Excel.csv?mode=" + "report07"
                + "&bulan=" + bulan
                + "&tahun=" + tahun));
    }

//    public String getUrlProduksiDanPendapatanReport07() {
//        return FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/rekapitulasiRealisasiReport07.pdf?"
//                + "mode=" + "report07" + "&"
//                + "bulan=" + bulan + "&"
//                + "tahun=" + tahun + " ";
//    }
    public void handleDownloadReport18(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        context.addCallbackParam("doPrint", true); //harus ada, buat ngeflag di izinkan ngeprint ato ngga
        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3("../../../rekapitulasiRealisasiProduksi.pdf?mode=" + "report18"
                + "&bulan=" + bulan
                + "&tahun=" + tahun));
    }

    public void handleDownloadReport191(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        context.addCallbackParam("doPrint", true); //harus ada, buat ngeflag di izinkan ngeprint ato ngga
        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3("../../../report19.pdf?mode=" + "report191"
                + "&bulan=" + bulan
                + "&tahun=" + tahun
                + "&owner=" + owner_code
                + "&equip=" + equip_code));
    }

//    public String getUrlReport18() {
//        return FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/rekapitulasiRealisasiProduksi.pdf?"
//                + "mode=" + "report18" + "&"
//                + "bulan=" + bulan + "&"
//                + "tahun=" + tahun + " ";
//    }
    public void handleDownloadReport19(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        context.addCallbackParam("doPrint", true); //harus ada, buat ngeflag di izinkan ngeprint ato ngga
        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3("../../../report19.pdf?mode=" + "report19"
                + "&bulan=" + bulan
                + "&tahun=" + tahun
                + "&owner=" + owner_code
                + "&equip=" + equip_code));
    }

//    public String getUrlReport19() {
//        return FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/rekapitulasiRealisasiProduksi.pdf?"
//                + "mode=" + "report19" + "&"
//                + "bulan=" + bulan + "&"
//                + "tahun=" + tahun + " ";
//    }
    public void handleDownloadReport05(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        context.addCallbackParam("doPrint", true); //harus ada, buat ngeflag di izinkan ngeprint ato ngga
        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3("../../../rekapitulasiRealisasiProduksi.pdf?mode=" + "report05"
                + "&bulan=" + bulan
                + "&tahun=" + tahun
                + "&owner=" + owner_code));
    }

//    public String getUrlReport05() {
//        return FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/rekapitulasiRealisasiProduksi.pdf?"
//                + "mode=" + "report05" + "&"
//                + "bulan=" + bulan + "&"
//                + "owner=" + owner_code + "&"
//                + "tahun=" + tahun + " ";
//    }
    public void handleDownloadReport08(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        context.addCallbackParam("doPrint", true); //harus ada, buat ngeflag di izinkan ngeprint ato ngga
        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3("../../../rekapitulasiRealisasiProduksi.pdf?mode=" + "report8"
                + "&bulan=" + bulan
                + "&tahun=" + tahun));
    }

//    public String getUrlProduksiDanPendapatanReport08() {
//        return FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/rekapitulasiRealisasiProduksi.pdf?"
//                + "mode=" + "report8" + "&"
//                + "bulan=" + bulan + "&"
//                + "tahun=" + tahun + " ";
//    }
    public void handleDownloadReport09(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        context.addCallbackParam("doPrint", true); //harus ada, buat ngeflag di izinkan ngeprint ato ngga
        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3("../../../rekapitulasiRealisasiProduksi.pdf?mode=" + "report9"
                + "&bulan=" + bulan
                + "&tahun=" + tahun));
    }

//    public String getUrlProduksiDanPendapatanReport09() {
//        return FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/rekapitulasiRealisasiProduksi.pdf?"
//                + "mode=" + "report9" + "&"
//                + "bulan=" + bulan + "&"
//                + "tahun=" + tahun + " ";
//    }
    public void handleDownloadReport23(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        context.addCallbackParam("doPrint", true); //harus ada, buat ngeflag di izinkan ngeprint ato ngga
        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3("../../../rekapitulasiRealisasiProduksi.pdf?mode=" + "report23"
                + "&bulan=" + bulan
                + "&tahun=" + tahun));
    }

//    public String getUrlProduksiDanPendapatanReport23() {
//        return FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/rekapitulasiRealisasiProduksi.pdf?"
//                + "mode=" + "report23" + "&"
//                + "bulan=" + bulan + "&"
//                + "tahun=" + tahun + " ";
//    }
    public void handleDownloadReport24(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        context.addCallbackParam("doPrint", true); //harus ada, buat ngeflag di izinkan ngeprint ato ngga
        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3("../../../report24.pdf?mode=" + "report24"
                + "&bulan=" + bulan
                + "&tahun=" + tahun));
    }

//    public String getUrlProduksiDanPendapatanReport24() {
//        return FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/report24.pdf?"
//                + "mode=" + "report24" + "&"
//                + "bulan=" + bulan + "&"
//                + "tahun=" + tahun + " ";
//    }
    public void handleDownloadReport02(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        context.addCallbackParam("doPrint", true); //harus ada, buat ngeflag di izinkan ngeprint ato ngga
        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3("../../../rekapitulasiRealisasiProduksi.pdf?mode=" + "report02"
                + "&bulan=" + bulan
                + "&tahun=" + tahun));
    }

//    public String getUrlReport02(){
//        return FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/rekapitulasiRealisasiProduksi.pdf?"
//                + "mode=" + "report02" + "&"
//                + "bulan=" + bulan + "&"
//                + "tahun=" + tahun + " ";
//    }
    public void handleDownloadReport03(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        context.addCallbackParam("doPrint", true); //harus ada, buat ngeflag di izinkan ngeprint ato ngga
        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3("../../../rekapitulasiReport01.pdf?mode=" + "report03"
                + "&bulan=" + bulan
                + "&tahun=" + tahun));
    }

    public void handleDownloadReport03Excel(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        context.addCallbackParam("doPrint", true); //harus ada, buat ngeflag di izinkan ngeprint ato ngga
        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3("../../../PendapatanRinciReport03Excel.csv?"
                + "bulan=" + bulan
                + "&tahun=" + tahun));
    }

//    public String getUrlReport03() {
//        return FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/rekapitulasiReport01.pdf?"
//                + "mode=" + "report03" + "&"
//                + "bulan=" + bulan + "&"
//                + "tahun=" + tahun + " ";
//    }
    public void handleDownloadReport01(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        context.addCallbackParam("doPrint", true); //harus ada, buat ngeflag di izinkan ngeprint ato ngga
        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3("../../../rekapitulasiRealisasiProduksi.pdf?mode=" + "report01"
                + "&bulan=" + bulan
                + "&tahun=" + tahun));
    }
//    public String getUrlReport01(){
//        System.out.println("bean");
//        return FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/rekapitulasiRealisasiProduksi.pdf?"
//                + "mode=" + "report01" + "&"
//                + "bulan=" + bulan + "&"
//                + "tahun=" + tahun + " ";
//    }

    public void handleDownloadReport06(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        context.addCallbackParam("doPrint", true); //harus ada, buat ngeflag di izinkan ngeprint ato ngga
        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3("../../../rekapitulasiRealisasiProduksiDanPendapatanSubreport.pdf?mode=" + "report06"
                + "&bulan=" + bulan
                + "&tahun=" + tahun
                + "&ownerr=" + owner_code
                + "&equipCode=" + equip_code));
    }

//    public String getUrlProduksiDanPendapatanReport06() {
//        return FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/rekapitulasiRealisasiProduksiDanPendapatanSubreport.pdf?"
//                + "mode=" + "report06" + "&"
//                + "bulan=" + bulan + "&"
//                + "ownerr=" + owner_code + "&"
//                + "equipCode=" + equip_code + "&"
//                + "tahun=" + tahun + " ";
//    }
    public void handleDownloadReport13(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        context.addCallbackParam("doPrint", true); //harus ada, buat ngeflag di izinkan ngeprint ato ngga
        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3("../../../Report13.pdf?"
                + "&bulan=" + bulan
                + "&tahun=" + tahun));
    }

//    public String getUrlReport13() {
//        return FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/Report13.pdf?"
//                + "bulan=" + bulan + "&"
//                + "tahun=" + tahun;
//    }
    public void handleDownloadReport14(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        context.addCallbackParam("doPrint", true); //harus ada, buat ngeflag di izinkan ngeprint ato ngga
        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3("../../../Report14.pdf?"
                + "&bulan=" + bulan
                + "&tahun=" + tahun));
    }

//    public String getUrlReport14() {
//        return FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/Report14.pdf?"
//                + "bulan=" + bulan + "&"
//                + "tahun=" + tahun;
//    }
    public void handleDownloadReport15(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        context.addCallbackParam("doPrint", true); //harus ada, buat ngeflag di izinkan ngeprint ato ngga
        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3("../../../Report15.pdf?"
                + "&bulan=" + bulan
                + "&tahun=" + tahun));
    }

//    public String getUrlReport15() {
//        return FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/Report15.pdf?"
//                + "bulan=" + bulan + "&"
//                + "tahun=" + tahun;
//    }
    public void handleDownloadReport16(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        context.addCallbackParam("doPrint", true); //harus ada, buat ngeflag di izinkan ngeprint ato ngga
        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3("../../../Report16.pdf?"
                + "&bulan=" + bulan
                + "&tahun=" + tahun));
    }

//    public String getUrlReport16() {
//        return FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/Report16.pdf?"
//                + "bulan=" + bulan + "&"
//                + "tahun=" + tahun;
//    }
    public void handleDownloadReport17(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        context.addCallbackParam("doPrint", true); //harus ada, buat ngeflag di izinkan ngeprint ato ngga
        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3("../../../Report17.pdf?"
                + "&bulan=" + bulan
                + "&tahun=" + tahun));
    }

//    public String getUrlReport17() {
//        return FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/Report17.pdf?"
//                + "bulan=" + bulan + "&"
//                + "tahun=" + tahun;
//    }
    public void handleDownloadReport20(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        context.addCallbackParam("doPrint", true); //harus ada, buat ngeflag di izinkan ngeprint ato ngga
        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3("../../../report24.pdf?mode=" + "report20"
                + "&bulan=" + bulan
                + "&tahun=" + tahun));
    }

//    public String getUrlReport20() {
//        return FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/report21.pdf?"
//                + "mode=" + "report20" + "&"
//                + "bulan=" + bulan + "&"
//                + "tahun=" + tahun;
//    }
    public void handleDownloadReport21(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        context.addCallbackParam("doPrint", true); //harus ada, buat ngeflag di izinkan ngeprint ato ngga
        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3("../../../report21.pdf?mode=" + "report21"
                + "&bulan=" + bulan
                + "&tahun=" + tahun
                + "&equipCode=" + equip_code));
    }

//    public String getUrlReport21() {
//        return FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/report21.pdf?"
//                + "mode=" + "report21" + "&"
//                + "bulan=" + bulan + "&"
//                + "equipCode=" + equip_code + "&"
//                + "tahun=" + tahun;
//    }
    public void handleDownloadReport22(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        context.addCallbackParam("doPrint", true); //harus ada, buat ngeflag di izinkan ngeprint ato ngga
        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3("../../../rekapitulasiRealisasiProduksi.pdf?mode=" + "report22"
                + "&bulan=" + bulan
                + "&tahun=" + tahun));
    }

//    public String getUrlReport22() {
//        return FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/rekapitulasiRealisasiProduksi.pdf?"
//                + "mode=" + "report22" + "&"
//                + "bulan=" + bulan + "&"
//                + "tahun=" + tahun + " ";
//    }
    public void handleDownloadDailyOperationReport(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        if (dailyOperationReport != null && dailyOperationReport.isValid()) {
            String foremanNameParam = dailyOperationReport.getForemanName().equals("") ? "" : "&foremanName=" + dailyOperationReport.getForemanName();
            String foremanNippParam = dailyOperationReport.getForemanNipp().equals("") ? "" : "&foremanNipp=" + dailyOperationReport.getForemanNipp();
            Long fromEpoch = dailyOperationReport.getFrom().getTime();
            Long toEpoch = dailyOperationReport.getTo().getTime();
            context.addCallbackParam("url", StringEscapeUtils.escapeHtml3("../../../LaporanOperasionalKapal.pdf?"
                    + "noPpkb=" + dailyOperationReport.getNoPpkb()
                    + "&from=" + fromEpoch
                    + "&to=" + toEpoch
                    + foremanNameParam
                    + foremanNippParam));

            context.addCallbackParam("doPrint", true);
            return;
        }

        context.addCallbackParam("doPrint", false);
    }

    public void handleDownloadDailyOperationReportCy(ActionEvent event) {
        DateFormat formatter;
        formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String s1 = formatter.format(tanggal1);
        String s2 = formatter.format(tanggal2);
        System.out.println("s1" + s1 + "s2 : " + s2);
        RequestContext context = RequestContext.getCurrentInstance();
        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3("../../../daftarPetikemasMasuk.pdf?"
                + "tipe=r"
                + "&tanggal1=" + s1 + "&tanggal2=" + s2 + "&supervisor=" + koordinator + "&pnJawab=saya" + "&owner=" + owner_code));

        context.addCallbackParam("doPrint", true);
    }

    public void handleDownloadDailyOperationReportReefer(ActionEvent event) {
        DateFormat formatter;
        formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String s1 = formatter.format(tanggal1);
        String s2 = formatter.format(tanggal2);

        RequestContext context = RequestContext.getCurrentInstance();
        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3("../../../daftarPetikemasMasuk.pdf?"
                + "tipe=d"
                + "&tanggal1=" + s1 + "&tanggal2=" + s2 + "&supervisor=" + koordinator + "&pnJawab=saya" + "&owner=saya"));

        context.addCallbackParam("doPrint", true);
    }

    public void handlePrintGate(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        Long fromEpoch = tanggal1.getTime();
        Long toEpoch = tanggal2.getTime();

        if (tipe.equals("m")) {
            context.addCallbackParam("url", StringEscapeUtils.escapeHtml3("../../../GateInReport.pdf?"
                    + "from=" + fromEpoch + "&"
                    + "to=" + toEpoch + "&"
                    + "pelaksana_gate=" + pelaksanaGate + "&"
                    + "supervisi=" + supervisor + "&"
                    + "koordinator=" + koordinator + "&"
                    + "no_ppkb=" + no_ppkb));
            context.addCallbackParam("doPrint", true);
        } else if (tipe.equals("k")) {
            context.addCallbackParam("url", StringEscapeUtils.escapeHtml3("../../../GateOutReport.pdf?"
                    + "from=" + fromEpoch + "&"
                    + "to=" + toEpoch + "&"
                    + "pelaksana_gate=" + pelaksanaGate + "&"
                    + "supervisi=" + supervisor + "&"
                    + "koordinator=" + koordinator + "&"
                    + "no_ppkb=" + no_ppkb));
            context.addCallbackParam("doPrint", true);
        } else {
            throw new RuntimeException(String.format("Invalid report type [type=%s]", tipe));
        }
    }

    public void handleShowDailyOperationReport(ActionEvent event) {
        dailyOperationReport = new DailyOperationReport();
    }

    private MasterOwnerEquipmentFacadeRemote lookupMOwnerEquipmentFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (MasterOwnerEquipmentFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/MasterOwnerEquipmentFacade!com.pelindo.ebtos.ejb.facade.remote.MasterOwnerEquipmentFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private MasterEquipmentFacadeRemote lookupMasterEquipmentFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (MasterEquipmentFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/MasterEquipmentFacade!com.pelindo.ebtos.ejb.facade.remote.MasterEquipmentFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private MasterCustomerFacadeRemote lookupMasterCustomerFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (MasterCustomerFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/MasterCustomerFacade!com.pelindo.ebtos.ejb.facade.remote.MasterCustomerFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    public class DailyOperationReport {

        private String noPpkb;
        private Date from;
        private Date to;
        private String foremanName;
        private String foremanNipp;

        public DailyOperationReport() {
            Calendar cal = Calendar.getInstance();
            from = cal.getTime();
            cal.add(Calendar.DATE, 1);
            to = cal.getTime();
        }

        public String getForemanName() {
            return foremanName;
        }

        public void setForemanName(String foremanName) {
            this.foremanName = foremanName;
        }

        public String getForemanNipp() {
            return foremanNipp;
        }

        public void setForemanNipp(String foremanNipp) {
            this.foremanNipp = foremanNipp;
        }

        public Date getFrom() {
            return from;
        }

        public void setFrom(Date from) {
            this.from = from;
        }

        public String getNoPpkb() {
            return noPpkb;
        }

        public void setNoPpkb(String noPpkb) {
            this.noPpkb = noPpkb;
        }

        public Date getTo() {
            return to;
        }

        public void setTo(Date to) {
            this.to = to;
        }

        public boolean isValid() {
            return getNoPpkb() != null && getFrom() != null && getTo() != null;
        }
    }

    
    public String getNo_ppkb(){
        return no_ppkb;
    }
    
    public void setNo_ppkb(String no_ppkb){
        this.no_ppkb = no_ppkb;
    }
    
    public String getKoordinator() {
        return koordinator;
    }

    public void setKoordinator(String koordinator) {
        this.koordinator = koordinator;
    }

    public Date getTanggal1() {
        return tanggal1;
    }

    public void setTanggal1(Date tanggal1) {
        this.tanggal1 = tanggal1;
    }

    public Date getTanggal2() {
        return tanggal2;
    }

    public void setTanggal2(Date tanggal2) {
        this.tanggal2 = tanggal2;
    }

    public String getPnJawab() {
        return pnJawab;
    }

    public void setPnJawab(String pnJawab) {
        this.pnJawab = pnJawab;
    }

    public String getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(String supervisor) {
        this.supervisor = supervisor;
    }

    public String getTipe() {
        return tipe;
    }

    public void setTipe(String tipe) {
        this.tipe = tipe;
    }

    public String getPelaksanaGate() {
        return pelaksanaGate;
    }

    public void setPelaksanaGate(String pelaksanaGate) {
        this.pelaksanaGate = pelaksanaGate;
    }
}
