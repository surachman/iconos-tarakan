/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.UcReceivingService;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author postgres
 */
@Remote
public interface UcReceivingServiceFacadeRemote {

    void create(UcReceivingService ucReceivingService);

    void edit(UcReceivingService ucReceivingService);

    void remove(UcReceivingService ucReceivingService);

    UcReceivingService find(Object id);

    List<UcReceivingService> findAll();

    List<UcReceivingService> findRange(int[] range);

    int count();

    public List<Object[]> findByUcReceivingServices(String no_reg);

    public List<Object[]> findByUcReceivingPerhitungan(String no_reg);

    public String generateId(String bulan);

    public List<Object[]> findByUcReceivingJobSlip(String no_reg);

    public List<String> findGateInPassableJobSlips(String jobslip);

    public Object[] findGateInPassableByJobSlip(String job_slip);


    public List<Object[]> findLoadRecapUc(String no_ppkb);

    List<Integer> findJobslipByIdUC(int id_uc);

    Object[] findNoRegJobslipByIdUC(int id_uc);

    List<Object[]> findByPpkb(String no_ppkb);

    List<Object[]> findByPpkbTLfalse(String no_ppkb);

    String findJobslipByIdUCMobile(int id_uc);

    List<Object[]> findCancelInvoice(String no_reg);

    List<Object[]> findByNoregValidasiPrint(String no_reg);

    public int deleteByNoReg(java.lang.String noReg);

}
