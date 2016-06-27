/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.report.model.charge;

import java.math.BigDecimal;

/**
 *
 * @author x42jr
 */
public class CancelDocumentChargeDetail implements IChargeDetailWithUpahBuruh{
    private BigDecimal administrationCharge;
    private BigDecimal liftOffReceivingCharge;
    private BigDecimal handlingCharge;
    private BigDecimal penumpukanCharge;
    private BigDecimal passGateCharge;
    private BigDecimal upahBuruh;
    private BigDecimal ppn;
    private BigDecimal materai;
    private BigDecimal jumlahTagihan;
    private BigDecimal totalTagihan;
    private String terbilang;

    public CancelDocumentChargeDetail() {
    }

    public BigDecimal getAdministrationCharge() {
        return administrationCharge;
    }

    public void setAdministrationCharge(BigDecimal administrationCharge) {
        this.administrationCharge = administrationCharge;
    }

    public BigDecimal getHandlingCharge() {
        return handlingCharge;
    }

    public void setHandlingCharge(BigDecimal handlingCharge) {
        this.handlingCharge = handlingCharge;
    }

    public BigDecimal getLiftOffReceivingCharge() {
        return liftOffReceivingCharge;
    }

    public void setLiftOffReceivingCharge(BigDecimal liftOffReceivingCharge) {
        this.liftOffReceivingCharge = liftOffReceivingCharge;
    }

    public BigDecimal getPassGateCharge() {
        return passGateCharge;
    }

    public void setPassGateCharge(BigDecimal passGateCharge) {
        this.passGateCharge = passGateCharge;
    }

    public BigDecimal getPenumpukanCharge() {
        return penumpukanCharge;
    }

    public void setPenumpukanCharge(BigDecimal penumpukanCharge) {
        this.penumpukanCharge = penumpukanCharge;
    }

    public BigDecimal getUpahBuruh() {
        return upahBuruh;
    }

    public void setUpahBuruh(BigDecimal upahBuruh) {
        this.upahBuruh = upahBuruh;
    }

    public BigDecimal getJumlahTagihan() {
        return jumlahTagihan;
    }

    public void setJumlahTagihan(BigDecimal jumlahTagihan) {
        this.jumlahTagihan = jumlahTagihan;
    }

    public BigDecimal getMaterai() {
        return materai;
    }

    public void setMaterai(BigDecimal materai) {
        this.materai = materai;
    }

    public BigDecimal getPpn() {
        return ppn;
    }

    public void setPpn(BigDecimal ppn) {
        this.ppn = ppn;
    }

    public String getTerbilang() {
        return terbilang;
    }

    public void setTerbilang(String terbilang) {
        this.terbilang = terbilang;
    }

    public BigDecimal getTotalTagihan() {
        return totalTagihan;
    }

    public void setTotalTagihan(BigDecimal totalTagihan) {
        this.totalTagihan = totalTagihan;
    }
}
