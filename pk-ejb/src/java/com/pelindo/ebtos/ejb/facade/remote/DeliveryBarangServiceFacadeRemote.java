/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.DeliveryBarangService;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author dycoder
 */
@Remote
public interface DeliveryBarangServiceFacadeRemote {

    void create(DeliveryBarangService deliveryBarangService);

    void edit(DeliveryBarangService deliveryBarangService);

    void remove(DeliveryBarangService deliveryBarangService);

    DeliveryBarangService find(Object id);

    List<DeliveryBarangService> findAll();

    List<DeliveryBarangService> findRange(int[] range);

    int count();

    String generateId(String bulan);

    String findInvoice(String cont_no, String no_ppkb, String no_reg);

    List<Object[]> findPerhitungan(String no_reg);

    List<Object[]> findByReg(String no_reg);

    List<String> findJobslipAutoComplete(String jobslip);

    Object[] findJobslip(String jobslip);

    List<Object[]> findDeliveryBarangServiceByList(String Bl);

    List<Object[]> findDeliveryBarangServiceByListAll();

    List<Object[]> findByNoregValidasiPrint(String no_reg);
}
