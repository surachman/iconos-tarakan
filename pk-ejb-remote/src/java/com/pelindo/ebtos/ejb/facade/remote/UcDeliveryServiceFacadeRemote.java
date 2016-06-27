/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.UcDeliveryService;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author postgres
 */
@Remote
public interface UcDeliveryServiceFacadeRemote {

    void create(UcDeliveryService ucDeliveryService);

    void edit(UcDeliveryService ucDeliveryService);

    void remove(UcDeliveryService ucDeliveryService);

    UcDeliveryService find(Object id);

    List<UcDeliveryService> findAll();

    List<UcDeliveryService> findRange(int[] range);

    int count();

    public List<Object[]> findByUcDeliveryServiceJobSlip(String no_reg);

    List<Object[]> findByReg(String no_reg);

    String generateId(String bulan);

    public List<String> findByUcDeliveryServiceAutoComplete(String jobslip);

    public Object[] findByUcDeliveryServiceGateInDeliveryClosingTime(String job_slip);

    List<Object[]> findPerhitungan(String no_reg);

    public List<Object[]> findDischargeRecapUc(String no_ppkb);

    List<Object[]> findDeliveryServiceByPPKB(String no_ppkb);

    String findByPpkbNIdUC(Integer id_uc, String no_ppkb);

    List<Object[]> findByPpkbSusulanByBL(String bl, String no_ppkb);

    Object[] findByUcDeliveryServiceGateInDeliveryClosingTimeTruckYes(String job_slip);

    List<Object[]> findByNoregValidasiPrint(String no_reg);

    public int deleteByNoReg(java.lang.String noReg);
}
