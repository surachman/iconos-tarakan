/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.ws.model;

import java.io.Serializable;

/**
 *
 * @author x42jr
 */
public class Container implements Serializable, IMessageable {
    private String noPpkb;
    private String contNo;
    private String loadVessel;
    private String dischargeVessel;
    private String yardBlock;
    private String status;
    private String dischargePort;
    private String loadPort;
    private String service;
    private String condition;
    private String messageCode;
    private String message;
    private String planningYardBlock;
    private String overSize;
    private String dangerous;
    private String dgLabel;
    private Integer weight;
    private Short planningYardSlot;
    private Short planningYardRow;
    private Short planningYardTier;
    private Short vesselBay;
    private Short vesselRow;
    private Short vesselTier;
    private Short yardSlot;
    private Short yardRow;
    private Short yardTier;
    private Short size;

    public Container() {}

    public Container(String code, String message) {
        this.messageCode = code;
        this.message = message;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getLoadVessel() {
        return loadVessel;
    }

    public void setLoadVessel(String loadVessel) {
        this.loadVessel = loadVessel;
    }

    public String getDischargeVessel() {
        return dischargeVessel;
    }

    public void setDischargeVessel(String dischargeVessel) {
        this.dischargeVessel = dischargeVessel;
    }

    public Container(String service) {
        this.service = service;
    }

    public String getContNo() {
        return contNo;
    }

    public void setContNo(String contNo) {
        this.contNo = contNo;
    }

    public String getDangerous() {
        return dangerous;
    }

    public void setDangerous(String dangerous) {
        this.dangerous = dangerous;
    }

    public String getDgLabel() {
        return dgLabel;
    }

    public void setDgLabel(String dgLabel) {
        this.dgLabel = dgLabel;
    }

    public String getNoPpkb() {
        return noPpkb;
    }

    public void setNoPpkb(String noPpkb) {
        this.noPpkb = noPpkb;
    }

    public String getOverSize() {
        return overSize;
    }

    public void setOverSize(String overSize) {
        this.overSize = overSize;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public Short getSize() {
        return size;
    }

    public void setSize(Short size) {
        this.size = size;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Short getVesselBay() {
        return vesselBay;
    }

    public void setVesselBay(Short vesselBay) {
        this.vesselBay = vesselBay;
    }

    public Short getVesselRow() {
        return vesselRow;
    }

    public void setVesselRow(Short vesselRow) {
        this.vesselRow = vesselRow;
    }

    public Short getVesselTier() {
        return vesselTier;
    }

    public void setVesselTier(Short vesselTier) {
        this.vesselTier = vesselTier;
    }

    public String getYardBlock() {
        return yardBlock;
    }

    public void setYardBlock(String yardBlock) {
        this.yardBlock = yardBlock;
    }

    public String getDischargePort() {
        return dischargePort;
    }

    public void setDischargePort(String dischargePort) {
        this.dischargePort = dischargePort;
    }

    public String getLoadPort() {
        return loadPort;
    }

    public void setLoadPort(String loadPort) {
        this.loadPort = loadPort;
    }

    public String getPlanningYardBlock() {
        return planningYardBlock;
    }

    public void setPlanningYardBlock(String planningYardBlock) {
        this.planningYardBlock = planningYardBlock;
    }

    public Short getPlanningYardRow() {
        return planningYardRow;
    }

    public void setPlanningYardRow(Short planningYardRow) {
        this.planningYardRow = planningYardRow;
    }

    public Short getPlanningYardSlot() {
        return planningYardSlot;
    }

    public void setPlanningYardSlot(Short planningYardSlot) {
        this.planningYardSlot = planningYardSlot;
    }

    public Short getPlanningYardTier() {
        return planningYardTier;
    }

    public void setPlanningYardTier(Short planningYardTier) {
        this.planningYardTier = planningYardTier;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Short getYardRow() {
        return yardRow;
    }

    public void setYardRow(Short yardRow) {
        this.yardRow = yardRow;
    }

    public Short getYardSlot() {
        return yardSlot;
    }

    public void setYardSlot(Short yardSlot) {
        this.yardSlot = yardSlot;
    }

    public Short getYardTier() {
        return yardTier;
    }

    public void setYardTier(Short yardTier) {
        this.yardTier = yardTier;
    }

    @Override
    public String getMessageCode() {
        return messageCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setMessageCode(String messageCode) {
        this.messageCode = messageCode;
    }
}
