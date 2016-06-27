/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.PerhitunganPlugging;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author dycoder
 */
@Remote
public interface PerhitunganPluggingFacadeRemote {

    void create(PerhitunganPlugging perhitunganPlugging);

    void edit(PerhitunganPlugging perhitunganPlugging);

    void remove(PerhitunganPlugging perhitunganPlugging);

    PerhitunganPlugging find(Object id);

    List<PerhitunganPlugging> findAll();

    List<PerhitunganPlugging> findRange(int[] range);

    int count();

    Integer findInvoice(String cont_no, String no_ppkb, String no_reg);

    Integer findInvoicePlugging(String cont_no, String no_reg);

    List<Object> findByReg(String no_reg);

    public PerhitunganPlugging findByContNoAndNoReg(String contNo, String noReg);
}
