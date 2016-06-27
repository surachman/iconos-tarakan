/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.master.MasterDeviceRegistration;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author x42jr
 */
@Local
public interface MasterDeviceRegistrationFacadeLocal {

    void create(MasterDeviceRegistration masterDeviceRegistration);

    void edit(MasterDeviceRegistration masterDeviceRegistration);

    void remove(MasterDeviceRegistration masterDeviceRegistration);

    MasterDeviceRegistration find(Object id);

    List<MasterDeviceRegistration> findAll();

    List<MasterDeviceRegistration> findRange(int[] range);

    int count();

}
