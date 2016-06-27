/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.PerhitunganBehandle;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author dycoder
 */
@Local
public interface PerhitunganBehandleFacadeLocal {

    void create(PerhitunganBehandle perhitunganBehandle);

    void edit(PerhitunganBehandle perhitunganBehandle);

    void remove(PerhitunganBehandle perhitunganBehandle);

    PerhitunganBehandle find(Object id);

    List<PerhitunganBehandle> findAll();

    List<PerhitunganBehandle> findRange(int[] range);

    int count();

    int deleteByNoReg(String noReg);

    Integer findInvoice(String cont_no, String no_ppkb, String no_reg);

}
