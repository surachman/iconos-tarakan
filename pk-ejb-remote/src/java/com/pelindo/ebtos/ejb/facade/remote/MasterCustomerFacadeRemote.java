/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.master.MasterCustomer;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author dycoder
 */
@Remote
public interface MasterCustomerFacadeRemote {

    void create(MasterCustomer masterCustomer);

    void edit(MasterCustomer masterCustomer);

    void remove(MasterCustomer masterCustomer);

    MasterCustomer find(Object id);

    List<MasterCustomer> findAll();

    List<MasterCustomer> findRange(int[] range);

    int count();

    List<Object[]> findMasterCustomers();

    Object[] findMasterCustomerByCustCode(String cust_code);

    List<Object[]> findAllmasterCustomerByDelete (int id_type);

    Object[] findDetailAgen(String cust_code);

    public java.util.List<Object[]> findLikeName(java.lang.String customerName);

}
