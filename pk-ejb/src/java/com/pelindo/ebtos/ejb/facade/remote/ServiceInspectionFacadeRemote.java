/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.ServiceInspection;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Dyware-Dev01
 */
@Remote
public interface ServiceInspectionFacadeRemote {

    void create(ServiceInspection serviceInspection);

    void edit(ServiceInspection serviceInspection);

    void remove(ServiceInspection serviceInspection);

    ServiceInspection find(Object id);

    List<ServiceInspection> findAll();

    List<ServiceInspection> findRange(int[] range);

    int count();

    Object[] findByContNo(String no_ppkb, String cont_no);

    public ServiceInspection findNotFinishedYetByNoPpkbAndContNo(String noPpkb, String contNo);
}
