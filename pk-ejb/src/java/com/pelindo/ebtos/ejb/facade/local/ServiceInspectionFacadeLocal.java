/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.ServiceInspection;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Dyware-Dev01
 */
@Local
public interface ServiceInspectionFacadeLocal {

    void create(ServiceInspection serviceInspection);

    void edit(ServiceInspection serviceInspection);

    void remove(ServiceInspection serviceInspection);

    ServiceInspection find(Object id);

    List<ServiceInspection> findAll();

    List<ServiceInspection> findRange(int[] range);

    int count();

    public ServiceInspection findNotFinishedYetByNoPpkbAndContNo(String noPpkb, String contNo);

}
