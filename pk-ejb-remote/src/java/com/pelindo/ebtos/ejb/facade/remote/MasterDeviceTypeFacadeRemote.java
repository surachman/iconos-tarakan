/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.master.MasterDeviceType;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author senoanggoro
 */
@Remote
public interface MasterDeviceTypeFacadeRemote {

    void create(MasterDeviceType masterDeviceType);

    void edit(MasterDeviceType masterDeviceType);

    void remove(MasterDeviceType masterDeviceType);

    MasterDeviceType find(Object id);

    List<MasterDeviceType> findAll();

    List<MasterDeviceType> findRange(int[] range);

    int count();

}
