/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.bean;

import com.pelindo.ebtos.ejb.facade.remote.VwContainerInformationFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceDeliveryFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceReceivingFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ReceivingServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.DeliveryServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.CancelLoadingServiceFacadeRemote;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
/**
 *
 * @author arie
 */
@ManagedBean(name = "containerInformationBean")
@ViewScoped
public class ContainerInformationBean implements Serializable {

    @EJB
    private VwContainerInformationFacadeRemote vwContainerInformationFacade;
    @EJB
    private ServiceDeliveryFacadeRemote serviceDeliveryFacade;
    @EJB
    private ServiceReceivingFacadeRemote serviceReceivingFacade;
    @EJB
    private ReceivingServiceFacadeRemote receivingServiceFacade;
    @EJB
    private DeliveryServiceFacadeRemote deliveryServiceFacade;
    @EJB
    private CancelLoadingServiceFacadeRemote cancelLoadingServiceFacade;

    private List<Object[]> historyContainerList;
    private Object[] contInfo;
    private List<Object[]> vesselDetail;
    private List<Object[]> containerDetail;
    private List<Object[]> handlingDischarges;
    private List<Object[]> handlingLoads;
    private List<Object[]> yardDischarges;
    private List<Object[]> yardLoads;
    private List<Object[]> cashierDischarges;
    private List<Object[]> cashierLoadCancels;
    private List<Object[]> cashierCancelLoadings;
    private List<Object[]> cashierLoads;
    private List<Object[]> gateDischarges;
    private List<Object[]> gateLoads;
    
    private String contNo;
    private String contInfos;

    public ContainerInformationBean() {
        historyContainerList = new ArrayList<Object[]>();
        handlingDischarges = new ArrayList<Object[]>();
        handlingLoads = new ArrayList<Object[]>();
        contInfo = new Object[4];
        vesselDetail = new ArrayList<Object[]>();
        containerDetail = new ArrayList<Object[]>();
        yardDischarges = new ArrayList<Object[]>();
        yardLoads = new ArrayList<Object[]>();
        cashierDischarges = new ArrayList<Object[]>();
        cashierLoadCancels = new ArrayList<Object[]>();
        cashierCancelLoadings = new ArrayList<Object[]>();
        cashierLoads = new ArrayList<Object[]>();
        gateDischarges = new ArrayList<Object[]>();
        gateLoads = new ArrayList<Object[]>();

    }

    @PostConstruct
    private void construct(){
    }

    public void handleFindContainerData(ActionEvent event) {
        setHistoryContainerList(vwContainerInformationFacade.findHistoryContList(getContNo()));
        contInfo = vwContainerInformationFacade.findContInfo(getContNo());
        contInfos = contInfo[0] + " " + contInfo[1] + " " + contInfo[2] + " " + contInfo[3];
        handlingDischarges = new ArrayList<Object[]>();
        handlingLoads = new ArrayList<Object[]>();
        vesselDetail = new ArrayList<Object[]>();
        containerDetail = new ArrayList<Object[]>();
        yardDischarges = new ArrayList<Object[]>();
        yardLoads = new ArrayList<Object[]>();
        cashierDischarges = new ArrayList<Object[]>();
        cashierLoadCancels = new ArrayList<Object[]>();
        cashierCancelLoadings = new ArrayList<Object[]>();
        cashierLoads = new ArrayList<Object[]>();
        gateDischarges = new ArrayList<Object[]>();
        gateLoads = new ArrayList<Object[]>();
    }

    public void handleClearContainerData(ActionEvent event) {
        contNo = "";
        contInfos = "";
        historyContainerList = new ArrayList<Object[]>();
        handlingDischarges = new ArrayList<Object[]>();
        handlingLoads = new ArrayList<Object[]>();
        contInfo = new Object[4];
        vesselDetail = new ArrayList<Object[]>();
        containerDetail = new ArrayList<Object[]>();
        yardDischarges = new ArrayList<Object[]>();
        yardLoads = new ArrayList<Object[]>();
        cashierDischarges = new ArrayList<Object[]>();
        cashierLoadCancels = new ArrayList<Object[]>();
        cashierCancelLoadings = new ArrayList<Object[]>();
        cashierLoads = new ArrayList<Object[]>();
        gateDischarges = new ArrayList<Object[]>();
        gateLoads = new ArrayList<Object[]>();
    }

    public void handleDetailInfo(ActionEvent event) {
        String noppkb = (String) event.getComponent().getAttributes().get("noppkb");
        vesselDetail = vwContainerInformationFacade.findVesselDetail(getContNo(),noppkb);
        containerDetail = vwContainerInformationFacade.findContainerDetail(getContNo(),noppkb);
        //setHandlingDischarges(vwContainerInformationFacade.findHandlingDischarge(contNo, noppkb));

        handlingDischarges = vwContainerInformationFacade.findHandlingDischarge(contNo, noppkb);
        handlingLoads = vwContainerInformationFacade.findHandlingLoad(contNo, noppkb);

        yardDischarges = serviceDeliveryFacade.findYardDischarge(noppkb, contNo);
        yardLoads = serviceReceivingFacade.findYardLoad(noppkb, contNo);

        cashierDischarges = deliveryServiceFacade.findCashierDischarge(noppkb, contNo);
        cashierLoads = receivingServiceFacade.findCashierLoad(noppkb, contNo);

        cashierLoadCancels = receivingServiceFacade.findCashierLoadCancel(noppkb.substring(0,10), contNo);

        cashierCancelLoadings = cancelLoadingServiceFacade.findCashierCancelLoading(noppkb.substring(0,10), contNo);

        gateDischarges = vwContainerInformationFacade.findGateDischarge(contNo, noppkb);
        gateLoads = vwContainerInformationFacade.findGateLoad(contNo, noppkb);
    }

    public List<String> setContListAutoComplete(String query) {
        List<String> results = new ArrayList<String>();
        results = vwContainerInformationFacade.findContList(query);
        results.addAll(results);
        return results;
    }

    /**
     * @return the historyContainerList
     */
    public List<Object[]> getHistoryContainerList() {
        return historyContainerList;
    }

    /**
     * @param historyContainerList the historyContainerList to set
     */
    public void setHistoryContainerList(List<Object[]> historyContainerList) {
        this.historyContainerList = historyContainerList;
    }

    /**
     * @return the contInfo
     */
    public Object[] getContInfo() {
        return contInfo;
    }

    /**
     * @param contInfo the contInfo to set
     */
    public void setContInfo(Object[] contInfo) {
        this.contInfo = contInfo;
    }

    /**
     * @return the vesselDetail
     */
    public List<Object[]> getVesselDetail() {
        return vesselDetail;
    }

    /**
     * @param vesselDetail the vesselDetail to set
     */
    public void setVesselDetail(List<Object[]> vesselDetail) {
        this.vesselDetail = vesselDetail;
    }

    /**
     * @return the containerDetail
     */
    public List<Object[]> getContainerDetail() {
        return containerDetail;
    }

    /**
     * @param containerDetail the containerDetail to set
     */
    public void setContainerDetail(List<Object[]> containerDetail) {
        this.containerDetail = containerDetail;
    }

    /**
     * @return the contNo
     */
    public String getContNo() {
        return contNo;
    }

    /**
     * @param contNo the contNo to set
     */
    public void setContNo(String contNo) {
        this.contNo = contNo;
    }

    /**
     * @return the handlingDischarges
     */
    public List<Object[]> getHandlingDischarges() {
        return handlingDischarges;
    }

    /**
     * @param handlingDischarges the handlingDischarges to set
     */
    public void setHandlingDischarges(List<Object[]> handlingDischarges) {
        this.handlingDischarges = handlingDischarges;
    }

    /**
     * @return the handlingLoads
     */
    public List<Object[]> getHandlingLoads() {
        return handlingLoads;
    }

    /**
     * @param handlingLoads the handlingLoads to set
     */
    public void setHandlingLoads(List<Object[]> handlingLoads) {
        this.handlingLoads = handlingLoads;
    }

    /**
     * @return the yardDischarges
     */
    public List<Object[]> getYardDischarges() {
        return yardDischarges;
    }

    /**
     * @param yardDischarges the yardDischarges to set
     */
    public void setYardDischarges(List<Object[]> yardDischarges) {
        this.yardDischarges = yardDischarges;
    }

    /**
     * @return the yardLoads
     */
    public List<Object[]> getYardLoads() {
        return yardLoads;
    }

    /**
     * @param yardLoads the yardLoads to set
     */
    public void setYardLoads(List<Object[]> yardLoads) {
        this.yardLoads = yardLoads;
    }

    /**
     * @return the cashierDischarges
     */
    public List<Object[]> getCashierDischarges() {
        return cashierDischarges;
    }

    /**
     * @param cashierDischarges the cashierDischarges to set
     */
    public void setCashierDischarges(List<Object[]> cashierDischarges) {
        this.cashierDischarges = cashierDischarges;
    }

    /**
     * @return the cashierLoads
     */
    public List<Object[]> getCashierLoads() {
        return cashierLoads;
    }

    /**
     * @param cashierLoads the cashierLoads to set
     */
    public void setCashierLoads(List<Object[]> cashierLoads) {
        this.cashierLoads = cashierLoads;
    }

     /**
     * @return the cashierCancelLoadings
     */
    public List<Object[]> getCashierCancelLoadings() {
        return cashierCancelLoadings;
    }

    /**
     * @param cashierLoads the cashierCancelLoadings to set
     */

    public void setCashierCancelLoadings(List<Object[]> cashierCancelLoadings) {
        this.cashierCancelLoadings = cashierCancelLoadings;
    }

    /**
     * @return the cashierLoadCancels
     */
    public List<Object[]> getCashierLoadCancels() {
        return cashierLoadCancels;
    }

    /**
     * @param cashierLoadCancels the cashierLoadCancels to set
     */
    public void setCashierLoadCancels(List<Object[]> cashierLoadCancels) {
        this.cashierLoadCancels = cashierLoadCancels;
    }

    /**
     * @return the gateDischarges
     */
    public List<Object[]> getGateDischarges() {
        return gateDischarges;
    }

    /**
     * @param gateDischarges the gateDischarges to set
     */
    public void setGateDischarges(List<Object[]> gateDischarges) {
        this.gateDischarges = gateDischarges;
    }

    /**
     * @return the gateLoads
     */
    public List<Object[]> getGateLoads() {
        return gateLoads;
    }

    /**
     * @param gateLoads the gateLoads to set
     */
    public void setGateLoads(List<Object[]> gateLoads) {
        this.gateLoads = gateLoads;
    }

    /**
     * @return the contInfos
     */
    public String getContInfos() {
        return contInfos;
    }

    /**
     * @param contInfos the contInfos to set
     */
    public void setContInfos(String contInfos) {
        this.contInfos = contInfos;
    }

}
