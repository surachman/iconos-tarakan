/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.ReeferLoadService;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author dycoder
 */
@Remote
public interface ReeferLoadServiceFacadeRemote {

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

    List<Object[]> findByNoregValidasiPrint(String no_reg);

    List<ReeferLoadService> findByNoPpkb(String noPpkb);

    public com.pelindo.ebtos.model.db.ReeferLoadService findValidReeferByNoPpkbAndContNo(java.lang.String noPpkb, java.lang.String contNo);
}
