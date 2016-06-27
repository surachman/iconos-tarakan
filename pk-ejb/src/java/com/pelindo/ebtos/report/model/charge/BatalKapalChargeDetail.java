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
public class BatalKapalChargeDetail implements IChargeDetail{
    private BigDecimal batalKapalCharge;
    private BigDecimal ppn;
    private BigDecimal materai;
    private BigDecimal jumlahTagihan;
    private BigDecimal totalTagihan;
    private String terbilang;

    public BatalKapalChargeDetail() {
    }

    public BigDecimal getBatalKapalCharge() {
        return batalKapalCharge;
    }

    public void setBatalKapalCharge(BigDecimal batalKapalCharge) {
        this.batalKapalCharge = batalKapalCharge;
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
