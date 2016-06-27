/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.DeliveryService;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author dycoder
 */
@Local
public interface DeliveryServiceFacadeLocal {

    void create(DeliveryService deliveryService);

    void edit(DeliveryService deliveryService);

    void remove(DeliveryService deliveryService);

    DeliveryService find(Object id);

    List<DeliveryService> findAll();

    List<DeliveryService> findRange(int[] range);

    int count();

    List<Object[]> findDeliveryServiceByPPKBnReg(String no_ppkb, String no_reg);

    String generateId(String bulan);

    List<Object[]> findDeliveryServiceByValidateDate(Date validate);

    String findInvoice(String cont_no, String no_ppkb, String no_reg);

    String findByPpkbNCont(String cont_no, String no_ppkb);

    List<Object[]> findPerhitungan(String no_reg);

    List<Object[]> findDeliveryServiceByPPKB(String no_ppkb);

    List<Object[]> findDeliveryServiceByReg(String no_reg);

    Object[] findDeliveryServiceByClosingTime(String job_slip);

    List<String> findDeliveryServiceByAutoComplete(String jobslip);

    List<Object[]> findByPpkbAndBlNo(String no_ppkb, String bl_no);
}
