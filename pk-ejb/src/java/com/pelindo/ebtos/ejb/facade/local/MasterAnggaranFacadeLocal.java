/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.master.MasterAnggaran;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author AnggiCipta
 */
@Local
public interface MasterAnggaranFacadeLocal {

    void create(MasterAnggaran masterAnggaran);

    void edit(MasterAnggaran masterAnggaran);

    void remove(MasterAnggaran masterAnggaran);

    MasterAnggaran find(Object id);

    List<MasterAnggaran> findAll();

    List<MasterAnggaran> findRange(int[] range);

    int count();

}
