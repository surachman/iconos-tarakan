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
public class BehandleCalculationChargeDetail implements IChargeDetail{
    private BigDecimal behandleCharge;
    private BigDecimal sewaAlat;
    private BigDecimal upahBuruh;
    private BigDecimal ppn;
    private BigDecimal materai;
    private BigDecimal jumlahTagihan;
    private BigDecimal totalTagihan;
    private String terbilang;

    public BehandleCalculationChargeDetail() {
    }

    public BigDecimal getBehandleCharge() {
        return behandleCharge;
    }

    public void setBehandleCharge(BigDecimal behandleCharge) {
        this.behandleCharge = behandleCharge;
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

    public BigDecimal getSewaAlat() {
        return sewaAlat;
    }

    public void setSewaAlat(BigDecimal sewaAlat) {
        this.sewaAlat = sewaAlat;
    }

    public BigDecimal getUpahBuruh() {
        return upahBuruh;
    }

    public void setUpahBuruh(BigDecimal upahBuruh) {
        this.upahBuruh = upahBuruh;
    }
}
