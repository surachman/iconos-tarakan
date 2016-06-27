/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.ChangeStatusService;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Lenovo
 */
@Local
public interface ChangeStatusServiceFacadeLocal {

    void create(ChangeStatusService changeStatusService);

    void edit(ChangeStatusService changeStatusService);

    void remove(ChangeStatusService changeStatusService);

    ChangeStatusService find(Object id);

    List<ChangeStatusService> findAll();

    List<ChangeStatusService> findRange(int[] range);

    int count();

    public java.util.List<Object[]> findChangeStatusService(java.lang.String noPPKB);
    
}
