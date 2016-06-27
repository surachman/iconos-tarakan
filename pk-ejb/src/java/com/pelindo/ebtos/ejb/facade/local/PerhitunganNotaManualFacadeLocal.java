/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.PerhitunganNotaManual;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Dyware-Dev01
 */
@Local
public interface PerhitunganNotaManualFacadeLocal {

    void create(PerhitunganNotaManual perhitunganNotaManual);

    void edit(PerhitunganNotaManual perhitunganNotaManual);

    void remove(PerhitunganNotaManual perhitunganNotaManual);

    PerhitunganNotaManual find(Object id);

    List<PerhitunganNotaManual> findAll();

    List<PerhitunganNotaManual> findRange(int[] range);

    int count();

    List<Object[]> findAllNative();

}
