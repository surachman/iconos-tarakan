/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.PerhitunganNotaManual;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Dyware-Dev01
 */
@Remote
public interface PerhitunganNotaManualFacadeRemote {

    void create(PerhitunganNotaManual perhitunganNotaManual);

    void edit(PerhitunganNotaManual perhitunganNotaManual);

    void remove(PerhitunganNotaManual perhitunganNotaManual);

    PerhitunganNotaManual find(Object id);

    List<PerhitunganNotaManual> findAll();

    List<PerhitunganNotaManual> findRange(int[] range);

    int count();

    List<Object[]> findAllNative();

}
