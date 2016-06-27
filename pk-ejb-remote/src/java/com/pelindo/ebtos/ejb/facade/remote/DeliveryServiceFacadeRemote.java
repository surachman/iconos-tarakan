/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.DeliveryService;
import java.util.Date;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author dycoder
 */
@Remote
public interface DeliveryServiceFacadeRemote {

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

    List<Object[]> findByListBatalNota(String no_reg);

    List<Object[]> findByPpkbAndBlNoPenumpukanSusulan(String no_ppkb, String bl_no);

    List<Object[]> findByPpkbPenumpukanSusulan(String no_ppkb);

    List<Object[]> findByNoregValidasiPrint(String no_reg);

    List<Object[]> findContainerDetail(String no_ppkb);

    List<Object[]> findCashierDischarge(String no_ppkb, String contNo);

    public com.pelindo.ebtos.model.db.DeliveryService findByNoPpkbAndContNo(java.lang.String noPpkb, java.lang.String contNo);

    public java.lang.Integer deleteByPpkbAndReg(java.lang.String noPpkb, java.lang.String noReg);

    public com.pelindo.ebtos.model.db.DeliveryService findByContNoPpkbAndReg(java.lang.String contNo, java.lang.String noPpkb, java.lang.String noReg);
}
