/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.master.MasterCustomer;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author dycoder
 */
@Local
public interface MasterCustomerFacadeLocal {

    void create(MasterCustomer masterCustomer);

    void edit(MasterCustomer masterCustomer);

    void remove(MasterCustomer masterCustomer);

    MasterCustomer find(Object id);

    List<MasterCustomer> findAll();

    List<MasterCustomer> findRange(int[] range);

    int count();

    List<Object[]> fetchNewestAgenPelayaran(Date date);

    public java.lang.Integer syncMasterCustomer();

}
