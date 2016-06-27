/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.PerhitunganHandlingDischarge;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author dycoder
 */
@Local
public interface PerhitunganHandlingDischargeFacadeLocal {

    void create(PerhitunganHandlingDischarge perhitunganHandlingDischarge);

    void edit(PerhitunganHandlingDischarge perhitunganHandlingDischarge);

    void remove(PerhitunganHandlingDischarge perhitunganHandlingDischarge);

    PerhitunganHandlingDischarge find(Object id);

    List<PerhitunganHandlingDischarge> findAll();

    List<PerhitunganHandlingDischarge> findRange(int[] range);

    int count();

    Integer findInvoice(String cont_no, String no_ppkb, String no_reg);

    Object[] findHandlingInvoice(String no_ppkb);

    PerhitunganHandlingDischarge findByContNoAndNoReg(java.lang.String contNo, java.lang.String noReg);

    public int updateNoPpkb(java.lang.String newValue, java.lang.String oldValue);
}
