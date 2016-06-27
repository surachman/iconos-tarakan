/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.PerhitunganNotaManualDetail;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Dyware-Dev01
 */
@Local
public interface PerhitunganNotaManualDetailFacadeLocal {

    void create(PerhitunganNotaManualDetail perhitunganNotaManualDetail);

    void edit(PerhitunganNotaManualDetail perhitunganNotaManualDetail);

    void remove(PerhitunganNotaManualDetail perhitunganNotaManualDetail);

    PerhitunganNotaManualDetail find(Object id);

    List<PerhitunganNotaManualDetail> findAll();

    List<PerhitunganNotaManualDetail> findRange(int[] range);

    int count();

}
