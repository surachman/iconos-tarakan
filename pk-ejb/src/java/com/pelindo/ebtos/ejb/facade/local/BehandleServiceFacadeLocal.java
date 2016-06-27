/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.BehandleService;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author dycoder
 */
@Local
public interface BehandleServiceFacadeLocal {

    void create(BehandleService behandleService);

    void edit(BehandleService behandleService);

    void remove(BehandleService behandleService);

    BehandleService find(Object id);

    List<BehandleService> findAll();

    List<BehandleService> findRange(int[] range);

    int count();

    List<Object[]> findBehandleServiceByPPKBnReg(String no_ppkb, String no_reg);

    String generateId(String bulan);

    String findInvoice(String cont_no, String no_ppkb, String no_reg);

    List<Object[]> findPerhitungan(String no_reg);

    List<Object[]> findByJobslip(String no_reg);
}
