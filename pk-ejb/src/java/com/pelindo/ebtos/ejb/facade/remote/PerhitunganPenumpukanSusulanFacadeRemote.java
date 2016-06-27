/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.PerhitunganPenumpukanSusulan;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author dycoder
 */
@Remote
public interface PerhitunganPenumpukanSusulanFacadeRemote {

    void create(PerhitunganPenumpukanSusulan perhitunganPenumpukanSusulan);

    void edit(PerhitunganPenumpukanSusulan perhitunganPenumpukanSusulan);

    void remove(PerhitunganPenumpukanSusulan perhitunganPenumpukanSusulan);

    PerhitunganPenumpukanSusulan find(Object id);

    List<PerhitunganPenumpukanSusulan> findAll();

    List<PerhitunganPenumpukanSusulan> findRange(int[] range);

    int count();

    Integer findInvoice(String cont_no, String no_ppkb, String no_reg);

    Integer findInvoiceUC(String bl_no, String no_ppkb, String no_reg);

    List<Object> findByReg(String no_reg);
}
