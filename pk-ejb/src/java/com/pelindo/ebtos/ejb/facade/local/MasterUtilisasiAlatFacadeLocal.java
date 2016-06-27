/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.master.MasterUtilisasiAlat;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author wulan
 */
@Local
public interface MasterUtilisasiAlatFacadeLocal {

    void create(MasterUtilisasiAlat mUtilisasiAlat);

    void edit(MasterUtilisasiAlat mUtilisasiAlat);

    void remove(MasterUtilisasiAlat mUtilisasiAlat);

    MasterUtilisasiAlat find(Object id);

    List<MasterUtilisasiAlat> findAll();

    List<MasterUtilisasiAlat> findRange(int[] range);

    int count();

}
