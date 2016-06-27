/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.ReeferLoadService;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author dycoder
 */
@Local
public interface ReeferLoadServiceFacadeLocal {

    void create(ReeferLoadService reeferLoadService);

    void edit(ReeferLoadService reeferLoadService);

    void remove(ReeferLoadService reeferLoadService);

    ReeferLoadService find(Object id);

    List<ReeferLoadService> findAll();

    List<ReeferLoadService> findRange(int[] range);

    int count();

    List<Object[]> findReeferLoadServiceByPPKBnReg(String no_ppkb, String no_reg);

    String generateId(String bulan);

    String findInvoice(String cont_no, String no_ppkb, String no_reg);

    List<Object[]> findPerhitungan(String no_reg);

    List<String> findReeferLoadServiceByPPKB(String no_ppkb);

    List<Object[]> findLoadReeferServiceByReg(String no_reg);

    Object[] findGateReeferByJobSlip(String job_slip);

    List<String> findReeferLoadServiceByAutoComplete(String jobslip);

    public java.util.List<com.pelindo.ebtos.model.db.ReeferLoadService> findByNoPpkb(java.lang.String noPpkb);

    public int removePlugOnOffAndShiftPlanningByNoPpkb(java.lang.String noPpkb);
}
