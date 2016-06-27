/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.ServiceBehandle;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Dyware-Dev01
 */
@Local
public interface ServiceBehandleFacadeLocal {

    void create(ServiceBehandle serviceBehandle);

    void edit(ServiceBehandle serviceBehandle);

    void remove(ServiceBehandle serviceBehandle);

    ServiceBehandle find(Object id);

    List<ServiceBehandle> findAll();

    List<ServiceBehandle> findRange(int[] range);

    int count();

    public ServiceBehandle findNotFinishedYetByNoPpkbAndContNo(String noPpkb, String contNo);

}
