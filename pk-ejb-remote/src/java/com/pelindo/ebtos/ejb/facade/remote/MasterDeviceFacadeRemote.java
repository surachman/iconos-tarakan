/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.master.MasterDevice;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author senoanggoro
 */
@Remote
public interface MasterDeviceFacadeRemote {

    void create(MasterDevice masterDevice);

    void edit(MasterDevice masterDevice);

    void remove(MasterDevice masterDevice);

    MasterDevice find(Object id);

    List<MasterDevice> findAll();

    List<MasterDevice> findRange(int[] range);

    int count();

    public Object[] idKeyValid(java.lang.String key);

}
