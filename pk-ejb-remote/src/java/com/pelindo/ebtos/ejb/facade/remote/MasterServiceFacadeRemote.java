/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.master.MasterService;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author dycoder
 */
@Remote
public interface MasterServiceFacadeRemote {

    void create(MasterService masterService);

    void edit(MasterService masterService);

    void remove(MasterService masterService);

    MasterService find(Object id);

    List<MasterService> findAll();

    List<MasterService> findRange(int[] range);

    List<Object[]> findMasterServices();

    int count();

}
