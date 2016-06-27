/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.PerhitunganPenumpukan;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author dycoder
 */
@Remote
public interface PerhitunganPenumpukanFacadeRemote {

    void create(PerhitunganPenumpukan perhitunganPenumpukan);

    void edit(PerhitunganPenumpukan perhitunganPenumpukan);

    void remove(PerhitunganPenumpukan perhitunganPenumpukan);

    PerhitunganPenumpukan find(Object id);

    List<PerhitunganPenumpukan> findAll();

    List<PerhitunganPenumpukan> findRange(int[] range);

    int count();

    Integer findInvoice(String cont_no, String no_ppkb, String no_reg);

    List<Object> findByReg(String no_reg);

    Integer findByCancelInvoice(String no_reg, String no_ppkb);

    public java.lang.Integer deleteByContNoAndNoReg(java.lang.String contNo, java.lang.String noReg);

    public PerhitunganPenumpukan findByContNoAndNoReg(java.lang.String contNo, java.lang.String noReg);

    public java.lang.Integer deleteByNoReg(java.lang.String noReg);
}
