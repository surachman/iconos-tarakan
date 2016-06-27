/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.master.MasterSettingApp;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author dycoder
 */
@Local
public interface MasterSettingAppFacadeLocal {

    void create(MasterSettingApp masterSettingApp);

    void edit(MasterSettingApp masterSettingApp);

    void remove(MasterSettingApp masterSettingApp);

    MasterSettingApp find(Object id);

    List<MasterSettingApp> findAll();

    List<MasterSettingApp> findRange(int[] range);

    int count();

    public Object[] findCustomerSyncParameter();

    String findImplementedPortCode();

    public java.lang.Boolean isJKMGroupingByCustomerEnabled();

    public java.lang.Boolean isPaymentBankingEnabled();
}
