/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.ejb.facade.remote.MasterSettingAppFacadeRemote;
import com.pelindo.ebtos.model.db.master.MasterSettingApp;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author dycoder
 */
@Remote
public interface MasterSettingAppFacadeRemote {

    void create(MasterSettingApp masterSettingApp);

    void edit(MasterSettingApp masterSettingApp);

    void remove(MasterSettingApp masterSettingApp);

    MasterSettingApp find(Object id);

    List<MasterSettingApp> findAll();

    List<MasterSettingApp> findRange(int[] range);

    int count();

    public java.util.List<com.pelindo.ebtos.model.db.master.MasterSettingApp> findAllSorted();

    public java.lang.String findImplementedPortCode();

    public java.lang.Boolean isPaymentBankingEnabled();

    public java.lang.Boolean isJKMGroupingByCustomerEnabled();

    Boolean changeHandlingInvoiceSetting(Boolean newValue);

    public boolean isCYValidationEnabledWhenReceiving();

    int getMasa1ContainerRange();

    int getMasa1UcRange();

    public int getMasa1FreeRange();

    public int getMasa1GoodsRange();

    public int findTotalCYCapacity();

}
