/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.ChangeStatusService;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Lenovo
 */
@Remote
public interface ChangeStatusServiceFacadeRemote {

    void create(ChangeStatusService changeStatusService);

    void edit(ChangeStatusService changeStatusService);

    void remove(ChangeStatusService changeStatusService);

    ChangeStatusService find(Object id);

    List<ChangeStatusService> findAll();

    List<ChangeStatusService> findRange(int[] range);

    int count();

    public java.util.List<Object[]> findChangeStatusService(java.lang.String noPPKB);
    
}
