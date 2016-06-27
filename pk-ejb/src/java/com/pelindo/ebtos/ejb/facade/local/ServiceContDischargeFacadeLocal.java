/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.ServiceContDischarge;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Dyware-Dev01
 */
@Local
public interface ServiceContDischargeFacadeLocal {

    void create(ServiceContDischarge serviceContDischarge);

    void edit(ServiceContDischarge serviceContDischarge);

    void remove(ServiceContDischarge serviceContDischarge);

    ServiceContDischarge find(Object id);

    List<ServiceContDischarge> findAll();

    List<ServiceContDischarge> findRange(int[] range);

    int count();

    public java.util.List<com.pelindo.ebtos.model.db.ServiceContDischarge> findContainersThatHaveReachedCY(java.lang.String noPpkb);

    public int removeMasterActivityThatHaveReachedCY(java.lang.String noPpkb);

    List<Object[]> findForRecapDischarge(String no_ppkb);

    public com.pelindo.ebtos.model.db.ServiceContDischarge findMovableContainer(java.lang.String contNo);

    ServiceContDischarge findByNoPpkbAndContNo(String noPpkb, String contNo);
}
