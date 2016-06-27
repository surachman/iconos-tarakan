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
public interface IChargeDetailWithUpahBuruh extends IChargeDetail {
    public BigDecimal getUpahBuruh();
    public void setUpahBuruh(BigDecimal ppn);
}
