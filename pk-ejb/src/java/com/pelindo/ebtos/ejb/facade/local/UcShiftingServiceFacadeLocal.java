/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.UcShiftingService;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author dycoder
 */
@Local
public interface UcShiftingServiceFacadeLocal {

    void create(UcShiftingService ucShiftingService);

    void edit(UcShiftingService ucShiftingService);

    void remove(UcShiftingService ucShiftingService);

    UcShiftingService find(Object id);

    List<UcShiftingService> findAll();

    List<UcShiftingService> findRange(int[] range);

    int count();

    public java.util.List<com.pelindo.ebtos.model.db.UcShiftingService> findByNoPpkbAndHasPaid(java.lang.String noPpkb);

    public int removeActivityCodeByNoPpkbAndHasPaid(java.lang.String noPpkb);

    List<Integer> findByPPKBPaid(String no_ppkb);

}
