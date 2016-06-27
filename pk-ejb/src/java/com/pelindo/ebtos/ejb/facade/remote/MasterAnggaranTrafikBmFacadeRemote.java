/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.master.MasterAnggaranTrafikBm;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author dycoder
 */
@Remote
public interface MasterAnggaranTrafikBmFacadeRemote {

    void create(MasterAnggaranTrafikBm masterAnggaranTrafikBm);

    void edit(MasterAnggaranTrafikBm masterAnggaranTrafikBm);

    void remove(MasterAnggaranTrafikBm masterAnggaranTrafikBm);

    MasterAnggaranTrafikBm find(Object id);

    List<MasterAnggaranTrafikBm> findAll();

    List<MasterAnggaranTrafikBm> findRange(int[] range);

    int count();

    List<Object[]> findAllNative();

    String findTahunMax();

    List<Object[]> findAnggaranByTahun(String tahun);

    List<String> findTahun();
}
