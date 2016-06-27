/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.ServiceSewaAlat;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author dycoder
 */
@Local
public interface ServiceSewaAlatFacadeLocal {

    void create(ServiceSewaAlat serviceSewaAlat);

    void edit(ServiceSewaAlat serviceSewaAlat);

    void remove(ServiceSewaAlat serviceSewaAlat);

    ServiceSewaAlat find(Object id);

    List<ServiceSewaAlat> findAll();

    List<ServiceSewaAlat> findRange(int[] range);

    int count();

}
