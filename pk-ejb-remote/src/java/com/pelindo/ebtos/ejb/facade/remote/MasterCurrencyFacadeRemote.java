/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.master.MasterCurrency;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author dycoder
 */
@Remote
public interface MasterCurrencyFacadeRemote {

    void create(MasterCurrency masterCurrency);

    void edit(MasterCurrency masterCurrency);

    void remove(MasterCurrency masterCurrency);

    MasterCurrency find(Object id);

    List<MasterCurrency> findAll();

    List<MasterCurrency> findRange(int[] range);

    int count();

    String findSymbolByTradetype(String trade_type);

    public com.pelindo.ebtos.model.db.master.MasterCurrency getIDRCurrency();

    public com.pelindo.ebtos.model.db.master.MasterCurrency getUSDCurrency();

    public com.pelindo.ebtos.model.db.master.MasterCurrency findByTradetype(java.lang.String tradeType);

}
