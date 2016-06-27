/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.master.MasterUtilisasiAlat;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author wulan
 */
@Remote
public interface MasterUtilisasiAlatFacadeRemote {

    void create(MasterUtilisasiAlat mUtilisasiAlat);

    void edit(MasterUtilisasiAlat mUtilisasiAlat);

    void remove(MasterUtilisasiAlat mUtilisasiAlat);

    MasterUtilisasiAlat find(Object id);

    List<MasterUtilisasiAlat> findAll();

    List<MasterUtilisasiAlat> findRange(int[] range);

    List<Object[]> findAllUtilisasiAlat();

    int count();

}
