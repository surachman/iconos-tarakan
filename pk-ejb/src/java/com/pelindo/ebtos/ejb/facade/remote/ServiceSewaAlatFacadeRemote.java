/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.ServiceSewaAlat;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author dycoder
 */
@Remote
public interface ServiceSewaAlatFacadeRemote {

    void create(ServiceSewaAlat serviceSewaAlat);

    void edit(ServiceSewaAlat serviceSewaAlat);

    void remove(ServiceSewaAlat serviceSewaAlat);

    ServiceSewaAlat find(Object id);

    List<ServiceSewaAlat> findAll();

    List<ServiceSewaAlat> findRange(int[] range);

    int count();

    List<Object[]> findEquipmentSewaAlat();

}
