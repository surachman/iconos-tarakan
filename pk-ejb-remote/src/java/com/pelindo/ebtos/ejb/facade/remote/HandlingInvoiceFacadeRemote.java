/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.HandlingInvoice;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author dycode-java
 */
@Remote
public interface HandlingInvoiceFacadeRemote {

    void create(HandlingInvoice handlingInvoice);

    void edit(HandlingInvoice handlingInvoice);

    void remove(HandlingInvoice handlingInvoice);

    HandlingInvoice find(Object id);

    List<HandlingInvoice> findAll();

    List<HandlingInvoice> findRange(int[] range);

    int count();

    String generateId(String bulan);

    String findInvoice(String no_ppkb, String operation);

    Object[] handlingInvoiceDischarge(String no_ppkb, boolean include);

    Object[] handlingInvoiceLoad(String no_ppkb);

    List<Object[]> findContainerServicesChargeDetailByNoPpkb(String noPpkb);

    public java.lang.Object[] handlingInvoiceLoadCBTG(java.lang.String noPpkb);

    public java.lang.Object[] handlingInvoiceLoadPLND(java.lang.String noPpkb);

    List<Object[]> findContainerServicesChargeDetailDischargeByNoPpkb(String noPpkb);

    List<Object[]> findContainerServicesChargeDetailLoadByNoPpkb(String noPpkb);

}
