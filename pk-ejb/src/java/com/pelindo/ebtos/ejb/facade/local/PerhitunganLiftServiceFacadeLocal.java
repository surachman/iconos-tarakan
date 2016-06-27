/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.PerhitunganLiftService;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author dycoder
 */
@Local
public interface PerhitunganLiftServiceFacadeLocal {

    void create(PerhitunganLiftService perhitunganLiftService);

    void edit(PerhitunganLiftService perhitunganLiftService);

    void remove(PerhitunganLiftService perhitunganLiftService);

    PerhitunganLiftService find(Object id);

    List<PerhitunganLiftService> findAll();

    List<PerhitunganLiftService> findRange(int[] range);

    int count();

    Integer findInvoice(String cont_no, String no_ppkb, String no_reg);
    
    PerhitunganLiftService findByContNoAndNoReg(java.lang.String contNo, java.lang.String noReg);

    public int updateNoPpkb(java.lang.String newValue, java.lang.String oldValue);
}
