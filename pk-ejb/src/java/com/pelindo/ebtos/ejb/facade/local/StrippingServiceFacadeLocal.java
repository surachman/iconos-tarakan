/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.StrippingService;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author dycoder
 */
@Local
public interface StrippingServiceFacadeLocal {

    void create(StrippingService strippingService);

    void edit(StrippingService strippingService);

    void remove(StrippingService strippingService);

    StrippingService find(Object id);

    List<StrippingService> findAll();

    List<StrippingService> findRange(int[] range);

    int count();

    List<Object[]> findStrippingServiceByPPKBnReg(String no_ppkb, String no_reg);

    String generateId(String bulan);

    String findInvoice(String cont_no, String no_ppkb, String no_reg);

    List<Object[]> findPerhitungan(String no_reg);

    List<Object[]> findStrippingServiceByReg(String no_reg);

    public int deleteByNoReg(java.lang.String noReg);

    public List<StrippingService> findByNoReg(String noReg);
}
