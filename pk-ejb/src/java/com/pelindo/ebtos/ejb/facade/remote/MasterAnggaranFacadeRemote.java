/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.master.MasterAnggaran;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author AnggiCipta
 */
@Remote
public interface MasterAnggaranFacadeRemote {

    void create(MasterAnggaran masterAnggaran);

    void edit(MasterAnggaran masterAnggaran);

    void remove(MasterAnggaran masterAnggaran);

    MasterAnggaran find(Object id);

    List<MasterAnggaran> findAll();

    List<MasterAnggaran> findRange(int[] range);

    int count();

    List<Object[]> findAllNative();
}
