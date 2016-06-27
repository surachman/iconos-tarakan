/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.report.model.charge;

import java.math.BigDecimal;

/**
 *
 * @author R. Seno Anggoro A
 */
public interface IChargeDetail {
    public BigDecimal getPpn();
    public void setPpn(BigDecimal ppn);
    public BigDecimal getMaterai();
    public void setMaterai(BigDecimal materai);
    public BigDecimal getJumlahTagihan();
    public void setJumlahTagihan(BigDecimal jumlahTagihan);
    public BigDecimal getTotalTagihan();
    public void setTotalTagihan(BigDecimal totalTagihan);
    public String getTerbilang();
    public void setTerbilang(String terbilang);
}
