/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.PerhitunganStuffing;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author dycoder
 */
@Remote
public interface PerhitunganStuffingFacadeRemote {

    void create(PerhitunganStuffing perhitunganStuffing);

    void edit(PerhitunganStuffing perhitunganStuffing);

    void remove(PerhitunganStuffing perhitunganStuffing);

    PerhitunganStuffing find(Object id);

    PerhitunganStuffing findByContNoAndNoReg(String contNo, String noReg);

    List<PerhitunganStuffing> findAll();

    List<PerhitunganStuffing> findRange(int[] range);

    int count();

    public Integer findInvoice(String cont_no, String no_reg);

    Integer findByCancelInvoice(String no_reg);
}
