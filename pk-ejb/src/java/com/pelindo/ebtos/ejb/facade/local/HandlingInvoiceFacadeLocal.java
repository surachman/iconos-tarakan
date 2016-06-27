/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.HandlingInvoice;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author dycode-java
 */
@Local
public interface HandlingInvoiceFacadeLocal {

    void create(HandlingInvoice handlingInvoice);

    void edit(HandlingInvoice handlingInvoice);

    void remove(HandlingInvoice handlingInvoice);

    HandlingInvoice find(Object id);

    List<HandlingInvoice> findAll();

    List<HandlingInvoice> findRange(int[] range);

    int count();

}
