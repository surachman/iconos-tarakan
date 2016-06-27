/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.StrippingService;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author dycoder
 */
@Remote
public interface StrippingServiceFacadeRemote {

    void create(StrippingService strippingService);

    void edit(StrippingService strippingService);

    StrippingService editAndFetch(StrippingService strippingService);

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

    List<Object[]> findByNoregValidasiPrint(String no_reg);

    public java.util.List<com.pelindo.ebtos.model.db.StrippingService> findByNoReg(java.lang.String noReg);
}
