package com.qtasnim.edi.model;

import java.io.Serializable;
import java.util.Date;

public class Container implements Serializable
{
    public static String FULL;
    public static String EMPTY;
    public static String LCL;
    public static String FCL;
    private Carrier carrier;
    private Date dateWhenPassingThroughTheGate;
    private String containerId;
    private String commodityCode;
    private String temperatureScale;
    private String loadPort;
    private String portOfDischarge;
    private String portOfDelivery;
    private String containerType;
    private String fullEmptyIndicator;
    private String billOfLadingNumber;
    private Short bay;
    private Short row;
    private Short tier;
    private Integer grossWeight;
    private Integer tareWeight;
    private Integer temperature;
    
    public Carrier getCarrier() {
        return this.carrier;
    }
    
    public void setCarrier(final Carrier carrier) {
        this.carrier = carrier;
    }
    
    public String getContainerId() {
        return this.containerId;
    }
    
    public void setContainerId(final String containerId) {
        this.containerId = containerId;
    }
    
    public String getCommodityCode() {
        return this.commodityCode;
    }
    
    public void setCommodityCode(final String commodityCode) {
        this.commodityCode = commodityCode;
    }
    
    public String getTemperatureScale() {
        return this.temperatureScale;
    }
    
    public void setTemperatureScale(final String temperatureScale) {
        this.temperatureScale = temperatureScale;
    }
    
    public String getLoadPort() {
        return this.loadPort;
    }
    
    public void setLoadPort(final String loadPort) {
        this.loadPort = loadPort;
    }
    
    public String getPortOfDischarge() {
        return this.portOfDischarge;
    }
    
    public void setPortOfDischarge(final String portOfDischarge) {
        this.portOfDischarge = portOfDischarge;
    }
    
    public String getPortOfDelivery() {
        return this.portOfDelivery;
    }
    
    public void setPortOfDelivery(final String portOfDelivery) {
        this.portOfDelivery = portOfDelivery;
    }
    
    public String getContainerType() {
        return this.containerType;
    }
    
    public void setContainerType(final String containerType) {
        this.containerType = containerType;
    }
    
    public String getFullEmptyIndicator() {
        return this.fullEmptyIndicator;
    }
    
    public void setFullEmptyIndicator(final String fullEmptyIndicator) {
        this.fullEmptyIndicator = fullEmptyIndicator;
    }
    
    public Short getBay() {
        return this.bay;
    }
    
    public void setBay(final Short bay) {
        this.bay = bay;
    }
    
    public Short getRow() {
        return this.row;
    }
    
    public void setRow(final Short row) {
        this.row = row;
    }
    
    public Short getTier() {
        return this.tier;
    }
    
    public void setTier(final Short tier) {
        this.tier = tier;
    }
    
    public Integer getGrossWeight() {
        return this.grossWeight;
    }
    
    public void setGrossWeight(final Integer weight) {
        this.grossWeight = weight;
    }
    
    public Integer getTareWeight() {
        return this.tareWeight;
    }
    
    public void setTareWeight(final Integer tareWeight) {
        this.tareWeight = tareWeight;
    }
    
    public Integer getTemperature() {
        return this.temperature;
    }
    
    public void setTemperature(final Integer temperature) {
        this.temperature = temperature;
    }
    
    public String getBillOfLadingNumber() {
        return this.billOfLadingNumber;
    }
    
    public void setBillOfLadingNumber(final String billOfLadingNumber) {
        this.billOfLadingNumber = billOfLadingNumber;
    }
    
    public Date getDateWhenPassingThroughTheGate() {
        return this.dateWhenPassingThroughTheGate;
    }
    
    public void setDateWhenPassingThroughTheGate(final Date dateWhenPassingThroughTheGate) {
        this.dateWhenPassingThroughTheGate = dateWhenPassingThroughTheGate;
    }
    
    static {
        Container.FULL = "5";
        Container.EMPTY = "4";
        Container.LCL = "7";
        Container.FCL = "8";
    }
}
