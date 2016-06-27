/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.ServiceBehandle;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Dyware-Dev01
 */
@Remote
public interface ServiceBehandleFacadeRemote {

    void create(ServiceBehandle serviceBehandle);

    void edit(ServiceBehandle serviceBehandle);

    void remove(ServiceBehandle serviceBehandle);

    ServiceBehandle find(Object id);

    List<ServiceBehandle> findAll();

    List<ServiceBehandle> findRange(int[] range);

    int count();

    Object[] findByContNo(String no_ppkb, String cont_no);

    public ServiceBehandle findNotFinishedYetByNoPpkbAndContNo(String noPpkb, String contNo);

}
