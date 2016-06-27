/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.master.MasterDeviceType;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author senoanggoro
 */
@Local
public interface MasterDeviceTypeFacadeLocal {

    void create(MasterDeviceType masterDeviceType);

    void edit(MasterDeviceType masterDeviceType);

    void remove(MasterDeviceType masterDeviceType);

    MasterDeviceType find(Object id);

    List<MasterDeviceType> findAll();

    List<MasterDeviceType> findRange(int[] range);

    int count();

}
