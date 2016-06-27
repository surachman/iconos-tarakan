/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.ServiceIdleTime;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author dycoder
 */
@Remote
public interface ServiceIdleTimeFacadeRemote {

    void create(ServiceIdleTime serviceIdleTime);

    void edit(ServiceIdleTime serviceIdleTime);

    void remove(ServiceIdleTime serviceIdleTime);

    ServiceIdleTime find(Object id);

    List<ServiceIdleTime> findAll();

    List<ServiceIdleTime> findRange(int[] range);

    int count();

   List<Object[]> findServiceIdleTimesByPpkb(String no_ppkb);
   
  
}
