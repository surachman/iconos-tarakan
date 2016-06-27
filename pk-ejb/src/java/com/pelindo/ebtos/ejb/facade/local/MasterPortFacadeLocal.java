/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.master.MasterPort;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author dycoder
 */
@Local
public interface MasterPortFacadeLocal {

    void create(MasterPort masterPort);

    void edit(MasterPort masterPort);

    void remove(MasterPort masterPort);

    MasterPort find(Object id);

    List<MasterPort> findAll();

    List<MasterPort> findRange(int[] range);

    int count();

    List<Object[]> findAllNative();

    List<Object[]> findMasterPortForIdentify(String idPort);

    List<Object[]> findNoError();

}
