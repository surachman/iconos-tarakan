/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.ReeferDischargeService;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author dycoder
 */
@Local
public interface ReeferDischargeServiceFacadeLocal {

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

    public ReeferDischargeService findByContNoAndNoReg(String contNo, String noReg);

    public ReeferDischargeService findLastValidReeferByNoPpkbAndContNo(String noPpkb, String contNo);

    public java.util.List<com.pelindo.ebtos.model.db.ReeferDischargeService> findByNoReg(java.lang.String noReg);
}
