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
public class Uncontainerized implements Serializable, IMessageable {
    private String noPpkb;
    private String vesselName;
    private String blNo;
    private String yardBlock;
    private String status;
    private String commodityName;
    private String service;
    private String messageCode;
    private String message;
    private Short vesselBay;
    private Short vesselRow;
    private Short vesselTier;
    private Short yardSlot;
    private Short yardRow;
    private Short yardTier;
    private Integer unit;
    private Integer weight;
    private Boolean isHasEnteredGate;
    private Boolean isTruckLoosing;

    public Uncontainerized() {}

    public Uncontainerized(String code, String message) {
        this.messageCode = code;
        this.message = message;
    }

    public Uncontainerized(String service) {
        this.service = service;
    }

    public String getBlNo() {
        return blNo;
    }

    public void setBlNo(String blNo) {
        this.blNo = blNo;
    }

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public String getNoPpkb() {
        return noPpkb;
    }

    public void setNoPpkb(String noPpkb) {
        this.noPpkb = noPpkb;
    }

    public String getVesselName() {
        return vesselName;
    }

    public void setVesselName(String vesselName) {
        this.vesselName = vesselName;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getUnit() {
        return unit;
    }

    public void setUnit(Integer unit) {
        this.unit = unit;
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

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getYardBlock() {
        return yardBlock;
    }

    public void setYardBlock(String yardBlock) {
        this.yardBlock = yardBlock;
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

    public Boolean getIsHasEnteredGate() {
        return isHasEnteredGate;
    }

    public void setIsHasEnteredGate(Boolean isHasEnteredGate) {
        this.isHasEnteredGate = isHasEnteredGate;
    }

    public Boolean getIsTruckLoosing() {
        return isTruckLoosing;
    }

    public void setIsTruckLoosing(Boolean isTruckLoosing) {
        this.isTruckLoosing = isTruckLoosing;
    }

    public String getMessageCode() {
        return messageCode;
    }

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
