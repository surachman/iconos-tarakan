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
public class Vessel implements Serializable, IMessageable {
    private String noPpkb;
    private String bookingCode;
    private String eta;
    private String arrivalTime;
    private String berthingTime;
    private String startWorkTime;
    private String endWorkTime;
    private String departureTime;
    private String estArrivalTime;
    private String estBerthingTime;
    private String estStartWorkTime;
    private String estEndWorkTime;
    private String estDepartureTime;
    private String dockCode;
    private Short dischargeConts;
    private Short loadConts;
    private Short frMeter;
    private Short toMeter;
    private Integer loa;
    private String messageCode;
    private String message;

    public Vessel() {}

    public Vessel(String code, String message) {
        this.messageCode = code;
        this.message = message;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getBerthingTime() {
        return berthingTime;
    }

    public void setBerthingTime(String berthingTime) {
        this.berthingTime = berthingTime;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getEndWorkTime() {
        return endWorkTime;
    }

    public void setEndWorkTime(String endWorkTime) {
        this.endWorkTime = endWorkTime;
    }

    public String getStartWorkTime() {
        return startWorkTime;
    }

    public void setStartWorkTime(String startWorkTime) {
        this.startWorkTime = startWorkTime;
    }

    public String getEstArrivalTime() {
        return estArrivalTime;
    }

    public void setEstArrivalTime(String estArrivalTime) {
        this.estArrivalTime = estArrivalTime;
    }

    public String getEstBerthingTime() {
        return estBerthingTime;
    }

    public void setEstBerthingTime(String estBerthingTime) {
        this.estBerthingTime = estBerthingTime;
    }

    public String getEstDepartureTime() {
        return estDepartureTime;
    }

    public void setEstDepartureTime(String estDepartureTime) {
        this.estDepartureTime = estDepartureTime;
    }

    public String getEstEndWorkTime() {
        return estEndWorkTime;
    }

    public void setEstEndWorkTime(String estEndWorkTime) {
        this.estEndWorkTime = estEndWorkTime;
    }

    public String getEstStartWorkTime() {
        return estStartWorkTime;
    }

    public void setEstStartWorkTime(String estStartWorkTime) {
        this.estStartWorkTime = estStartWorkTime;
    }

    public String getBookingCode() {
        return bookingCode;
    }

    public void setBookingCode(String bookingCode) {
        this.bookingCode = bookingCode;
    }

    public Short getDischargeConts() {
        return dischargeConts;
    }

    public void setDischargeConts(Short dischargeConts) {
        this.dischargeConts = dischargeConts;
    }

    public String getDockCode() {
        return dockCode;
    }

    public void setDockCode(String dockCode) {
        this.dockCode = dockCode;
    }

    public String getEta() {
        return eta;
    }

    public void setEta(String eta) {
        this.eta = eta;
    }

    public Short getFrMeter() {
        return frMeter;
    }

    public void setFrMeter(Short frMeter) {
        this.frMeter = frMeter;
    }

    public Short getLoadConts() {
        return loadConts;
    }

    public void setLoadConts(Short loadConts) {
        this.loadConts = loadConts;
    }

    public String getNoPpkb() {
        return noPpkb;
    }

    public void setNoPpkb(String noPpkb) {
        this.noPpkb = noPpkb;
    }

    public Short getToMeter() {
        return toMeter;
    }

    public void setToMeter(Short toMeter) {
        this.toMeter = toMeter;
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

    public Integer getLoa() {
        return loa;
    }

    public void setLoa(Integer loa) {
        this.loa = loa;
    }
}
