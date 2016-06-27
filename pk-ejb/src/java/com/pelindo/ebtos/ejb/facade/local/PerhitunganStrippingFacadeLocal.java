/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.PerhitunganStripping;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author dycoder
 */
@Local
public interface PerhitunganStrippingFacadeLocal {

    void create(PerhitunganStripping perhitunganStripping);

    void edit(PerhitunganStripping perhitunganStripping);

    void remove(PerhitunganStripping perhitunganStripping);

    PerhitunganStripping find(Object id);

    List<PerhitunganStripping> findAll();

    List<PerhitunganStripping> findRange(int[] range);

    int count();

    public Integer findInvoice(String cont_no, String no_ppkb, String no_reg);

    public int deleteByNoReg(java.lang.String noReg);
}
