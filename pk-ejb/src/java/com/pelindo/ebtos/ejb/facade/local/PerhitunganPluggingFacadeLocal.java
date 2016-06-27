/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.PerhitunganPlugging;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author dycoder
 */
@Local
public interface PerhitunganPluggingFacadeLocal {

    void create(PerhitunganPlugging perhitunganPlugging);

    void edit(PerhitunganPlugging perhitunganPlugging);

    void remove(PerhitunganPlugging perhitunganPlugging);

    PerhitunganPlugging find(Object id);

    List<PerhitunganPlugging> findAll();

    List<PerhitunganPlugging> findRange(int[] range);

    int count();

    Integer findInvoice(String cont_no, String no_ppkb, String no_reg);

    Integer findInvoicePlugging(String cont_no, String no_reg);

    public com.pelindo.ebtos.model.db.PerhitunganPlugging findByContNoPpkbAndNoReg(java.lang.String contNo, java.lang.String noPpkb, java.lang.String noReg);

    public int deleteByNoPpkbLoadOnly(java.lang.String noPpkb);

    public PerhitunganPlugging findByContNoAndNoReg(String contNo, String noReg);

}
