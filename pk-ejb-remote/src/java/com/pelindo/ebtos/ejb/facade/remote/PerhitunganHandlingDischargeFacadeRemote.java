/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.PerhitunganHandlingDischarge;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author dycoder
 */
@Remote
public interface PerhitunganHandlingDischargeFacadeRemote {

    void create(PerhitunganHandlingDischarge perhitunganHandlingDischarge);

    void edit(PerhitunganHandlingDischarge perhitunganHandlingDischarge);

    void remove(PerhitunganHandlingDischarge perhitunganHandlingDischarge);

    PerhitunganHandlingDischarge find(Object id);

    List<PerhitunganHandlingDischarge> findAll();

    List<PerhitunganHandlingDischarge> findRange(int[] range);

    int count();

    Integer findInvoice(String cont_no, String no_ppkb, String no_reg);

    Object[] findHandlingInvoice(String no_ppkb);

    List<Object> findByReg(String no_reg);

    Integer findByCancelInvoice(String no_reg, String no_ppkb);

    public java.lang.Integer deleteByContNoAndNoReg(java.lang.String contNo, java.lang.String noReg);

    public java.lang.Integer deleteByNoReg(java.lang.String noReg);

    PerhitunganHandlingDischarge findByContNoAndNoReg(java.lang.String contNo, java.lang.String noReg);
}
