/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.PerhitunganLiftService;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author dycoder
 */
@Remote
public interface PerhitunganLiftServiceFacadeRemote {

    void create(PerhitunganLiftService perhitunganLiftService);

    void edit(PerhitunganLiftService perhitunganLiftService);

    void remove(PerhitunganLiftService perhitunganLiftService);

    PerhitunganLiftService find(Object id);

    List<PerhitunganLiftService> findAll();

    List<PerhitunganLiftService> findRange(int[] range);

    int count();

    Integer findInvoice(String cont_no, String no_ppkb, String no_reg);

    List<Object> findByReg(String no_reg);

    public java.lang.Integer deleteByContNoAndNoReg(java.lang.String contNo, java.lang.String noReg);

    public java.lang.Integer deleteByNoReg(java.lang.String noReg);

    PerhitunganLiftService findByContNoAndNoReg(java.lang.String contNo, java.lang.String noReg);
}
