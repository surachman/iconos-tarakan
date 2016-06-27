/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.PerhitunganStripping;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author dycoder
 */
@Remote
public interface PerhitunganStrippingFacadeRemote {

    void create(PerhitunganStripping perhitunganStripping);

    void edit(PerhitunganStripping perhitunganStripping);

    void remove(PerhitunganStripping perhitunganStripping);

    PerhitunganStripping find(Object id);

    List<PerhitunganStripping> findAll();

    List<PerhitunganStripping> findRange(int[] range);

    int count();

    public Integer findInvoice(String cont_no, String no_ppkb, String no_reg);

    Integer findByCancelInvoice(String no_reg, String no_ppkb);
    
    PerhitunganStripping findByContNoAndNoReg(String noReg, String contNo);

    public java.util.List<com.pelindo.ebtos.model.db.PerhitunganStripping> findByNoReg(java.lang.String noReg);
}
