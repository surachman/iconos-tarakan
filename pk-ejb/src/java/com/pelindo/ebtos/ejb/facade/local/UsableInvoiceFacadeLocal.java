/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.UsableInvoice;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author R. Seno Anggoro A
 */
@Local
public interface UsableInvoiceFacadeLocal {

    void create(UsableInvoice usableInvoice);

    void edit(UsableInvoice usableInvoice);

    void remove(UsableInvoice usableInvoice);

    UsableInvoice find(Object id);

    List<UsableInvoice> findAll();

    List<UsableInvoice> findRange(int[] range);

    int count();

    public java.lang.String getAvailableNoInvoice();

}
