/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.PerhitunganNotaManualDetail;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Dyware-Dev01
 */
@Remote
public interface PerhitunganNotaManualDetailFacadeRemote {

    void create(PerhitunganNotaManualDetail perhitunganNotaManualDetail);

    void edit(PerhitunganNotaManualDetail perhitunganNotaManualDetail);

    void remove(PerhitunganNotaManualDetail perhitunganNotaManualDetail);

    PerhitunganNotaManualDetail find(Object id);

    List<PerhitunganNotaManualDetail> findAll();

    List<PerhitunganNotaManualDetail> findRange(int[] range);

    int count();

    List<Object[]> findByNoReg(String no_reg);

}
