/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.master.MasterPort;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author dycoder
 */
@Remote
public interface MasterPortFacadeRemote {

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

    List<Object[]> findByName(String portName);
    List<Object[]> findLikeName(String portName);
    MasterPort findMasterPortByName(String portName);
//    MasterPort findMasterPortByCode(String portCode);
    Object findNameByCode(String portCode);
}
