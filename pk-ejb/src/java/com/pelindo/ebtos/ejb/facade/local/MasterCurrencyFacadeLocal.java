/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.master.MasterCurrency;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author dycoder
 */
@Local
public interface MasterCurrencyFacadeLocal {

    void create(MasterCurrency masterCurrency);

    void edit(MasterCurrency masterCurrency);

    void remove(MasterCurrency masterCurrency);

    MasterCurrency find(Object id);

    List<MasterCurrency> findAll();

    List<MasterCurrency> findRange(int[] range);

    int count();

    public com.pelindo.ebtos.model.db.master.MasterCurrency getIDRCurrency();

    public com.pelindo.ebtos.model.db.master.MasterCurrency getUSDCurrency();

    public com.pelindo.ebtos.model.db.master.MasterCurrency findByTradetype(java.lang.String tradeType);

    public com.pelindo.ebtos.model.db.master.MasterCurrency findByMataUangId(java.lang.String mataUangId);

}
