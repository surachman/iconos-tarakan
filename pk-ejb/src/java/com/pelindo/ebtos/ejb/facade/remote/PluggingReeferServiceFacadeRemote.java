/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.PluggingReeferService;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author dycoder
 */
@Remote
public interface PluggingReeferServiceFacadeRemote {

    void create(PluggingReeferService pluggingReeferService);

    void edit(PluggingReeferService pluggingReeferService);

    void remove(PluggingReeferService pluggingReeferService);

    PluggingReeferService find(Object id);

    List<PluggingReeferService> findAll();

    List<PluggingReeferService> findRange(int[] range);

    List<Object[]> findByNoRegistration(String no_reg);

    String generateId(String bulan);

    int count();

    Object[] findContainerReefer(String cont_no);

    String findInvoice(String cont_no, String no_reg);

    List<Object[]> findPerhitungan(String no_reg);

    List<Object[]> findPluggingReeferServiceByReg(String no_reg);

    List<String> findJobSlipByPPKBnCONT(String no_ppkb, String cont_no);

    List<Object[]> findByNoregValidasiPrint(String no_reg);

    List<Object[]> findByNoregPlugingReefer(String no_reg);

    Object[] findPluggingReeferServiceByJobSlip(String cont_no) ;

    List<Object[]> findSelectPluggingReeferService();

    List<Object[]> findSelectDeliveryPluggingReeferService(String no_reg);

    List<Object[]> findByRegPluggingDelivery(String no_reg);

    Object[] findPluggingReeferServiceByGatePlugging(String job_slip);

    List<Object[]> findPluggingReeferServiceByDeliveryConfirm();

    List<Object[]> findPluggingReeferServiceByDeliveryConfirmList();

    List<Object[]> findPerhitunganDeliveryPluggingService(String no_reg);

    List<Object[]> findChangeStatusPluggingToLoad();

    Object[] findUpdateServiceReceiving(String cont_no,String no_ppkb);

    List<Object[]> findPreviewServiceReceivingPlugging();

    String findPluggingReeferValidasiInput(String cont_no, String no_ppkb, String no_reg);

    Object[] findSelectPluggingReeferServiceReceivingHht(String cont_no);

    Object[] findPluggingReeferServiceByDeliveryConfirmHht(String cont_no);

    List<Object[]> findByRegCancelInvoice(String no_reg);
}
