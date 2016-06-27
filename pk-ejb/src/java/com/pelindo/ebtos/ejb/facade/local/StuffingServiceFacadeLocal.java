/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.StuffingService;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author dycoder
 */
@Local
public interface StuffingServiceFacadeLocal {

    void create(StuffingService stuffingService);

    void edit(StuffingService stuffingService);

    void remove(StuffingService stuffingService);

    StuffingService find(Object id);

    List<StuffingService> findAll();

    List<StuffingService> findRange(int[] range);

    int count();

    List<Object[]> findStuffingServiceByPPKBnReg(String no_reg);

    String generateId(String bulan);

    String findInvoice(String cont_no, String no_reg);

    List<Object[]> findPerhitungan(String no_reg);

    List<Object[]> findStuffingServiceByReg(String no_reg);

    public int deleteByNoReg(java.lang.String noReg);

    List<StuffingService> findByNoReg(String noReg);
}
