/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.master.MasterDeviceRegistration;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author x42jr
 */
@Remote
public interface MasterDeviceRegistrationFacadeRemote {

    void create(MasterDeviceRegistration masterDeviceRegistration);

    void edit(MasterDeviceRegistration masterDeviceRegistration);

    void remove(MasterDeviceRegistration masterDeviceRegistration);

    MasterDeviceRegistration find(Object id);

    List<MasterDeviceRegistration> findAll();

    List<MasterDeviceRegistration> findRange(int[] range);

    int count();

}
