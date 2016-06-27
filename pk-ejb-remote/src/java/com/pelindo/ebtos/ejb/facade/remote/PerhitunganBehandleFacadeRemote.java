/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.PerhitunganBehandle;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author dycoder
 */
@Remote
public interface PerhitunganBehandleFacadeRemote {

    void create(PerhitunganBehandle perhitunganBehandle);

    void edit(PerhitunganBehandle perhitunganBehandle);

    void remove(PerhitunganBehandle perhitunganBehandle);

    PerhitunganBehandle find(Object id);

    PerhitunganBehandle findByContNoAndNoReg(String contNo, String noReg);

    List<PerhitunganBehandle> findAll();

    List<PerhitunganBehandle> findRange(int[] range);

    int count();

    int deleteByNoReg(String noReg);

    Integer findInvoice(String cont_no, String no_ppkb, String no_reg);

    List<Object> findByReg(String no_reg);

    public java.util.List<com.pelindo.ebtos.model.db.PerhitunganBehandle> findByNoReg(java.lang.String noReg);
}
