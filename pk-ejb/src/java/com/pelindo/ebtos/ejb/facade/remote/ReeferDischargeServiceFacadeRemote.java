/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.ReeferDischargeService;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author dycoder
 */
@Remote
public interface ReeferDischargeServiceFacadeRemote {

    void create(ReeferDischargeService reeferDischargeService);

    void edit(ReeferDischargeService reeferDischargeService);

    void remove(ReeferDischargeService reeferDischargeService);

    ReeferDischargeService find(Object id);

    List<ReeferDischargeService> findAll();

    List<ReeferDischargeService> findRange(int[] range);

    int count();

    List<Object[]> findReeferDischargeServiceByPPKBnReg(String no_ppkb, String no_reg);

    String generateId(String bulan);

    String findInvoice(String cont_no, String no_ppkb, String no_reg);

    List<Object[]> findPerhitungan(String no_reg);

    List<Object[]> findDischargeReeferServiceByReg(String no_reg);

    Object[] findReeferByReg(String job_slip);

    List<String> findReeferDischargeServiceByAutoComplete(String jobslip);

    List<Object[]> findByNoregValidasiPrint(String no_reg);

    public com.pelindo.ebtos.model.db.ReeferDischargeService findByContNoAndNoReg(java.lang.String contNo, java.lang.String noReg);

    public com.pelindo.ebtos.model.db.ReeferDischargeService findLastValidReeferByNoPpkbAndContNo(java.lang.String noPpkb, java.lang.String contNo);
}
