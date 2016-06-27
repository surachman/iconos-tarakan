/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.PerhitunganPenumpukan;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author dycoder
 */
@Local
public interface PerhitunganPenumpukanFacadeLocal {

    void create(PerhitunganPenumpukan perhitunganPenumpukan);

    void edit(PerhitunganPenumpukan perhitunganPenumpukan);

    void remove(PerhitunganPenumpukan perhitunganPenumpukan);

    PerhitunganPenumpukan find(Object id);

    List<PerhitunganPenumpukan> findAll();

    List<PerhitunganPenumpukan> findRange(int[] range);

    int count();

    Integer findInvoice(String cont_no, String no_ppkb, String no_reg);

    public int deleteByNoPpkbReceivingOnly(java.lang.String noPpkb);

    public PerhitunganPenumpukan findByContNoAndNoReg(java.lang.String contNo, java.lang.String noReg);

    public int updateNoPpkb(java.lang.String newValue, java.lang.String oldValue);
}
