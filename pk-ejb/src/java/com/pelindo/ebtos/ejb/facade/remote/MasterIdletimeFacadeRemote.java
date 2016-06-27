/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.master.MasterIdletime;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author USER
 */
@Remote
public interface MasterIdletimeFacadeRemote {

    void create(MasterIdletime masterIdletime);

    void edit(MasterIdletime masterIdletime);

    void remove(MasterIdletime masterIdletime);

    MasterIdletime find(Object id);

    List<MasterIdletime> findAll();

    List<MasterIdletime> findRange(int[] range);

    int count();

    List<Object[]> findAllNative();

    List<Object[]> findAllMasterIdletimeByDelete (int id_type);

    List<Object[]> findAllMasterIdletimeById (int id_type);
}
