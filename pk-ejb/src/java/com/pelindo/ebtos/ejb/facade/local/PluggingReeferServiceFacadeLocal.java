/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.PluggingReeferService;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author dycoder
 */
@Local
public interface PluggingReeferServiceFacadeLocal {

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
}
