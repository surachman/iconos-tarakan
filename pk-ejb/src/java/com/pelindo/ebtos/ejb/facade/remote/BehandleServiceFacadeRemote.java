/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.BehandleService;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author dycoder
 */
@Remote
public interface BehandleServiceFacadeRemote {

    void create(BehandleService behandleService);

    void edit(BehandleService behandleService);

    void remove(BehandleService behandleService);

    BehandleService find(Object id);

    List<BehandleService> findAll();

    List<BehandleService> findRange(int[] range);
    
    int count();

    int deleteByNoReg(String noReg);

    List<Object[]> findBehandleServiceByPPKBnReg(String no_ppkb, String no_reg);

    String generateId(String bulan);

    String findInvoice(String cont_no, String no_ppkb, String no_reg);

    List<Object[]> findPerhitungan(String no_reg);

    List<Object[]> findByJobslip(String no_reg);

    List<Object[]> findByNoregValidasiPrint(String no_reg);

    BehandleService findByContNoAndNoReg(String contNo, String noReg);
}
