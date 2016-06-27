/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.PerhitunganStuffing;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author dycoder
 */
@Local
public interface PerhitunganStuffingFacadeLocal {

    void create(PerhitunganStuffing perhitunganStuffing);

    void edit(PerhitunganStuffing perhitunganStuffing);

    void remove(PerhitunganStuffing perhitunganStuffing);

    PerhitunganStuffing find(Object id);

    List<PerhitunganStuffing> findAll();

    List<PerhitunganStuffing> findRange(int[] range);

    int count();

    public Integer findInvoice(String cont_no, String no_reg);

    public int deleteByNoReg(String noReg);
}
