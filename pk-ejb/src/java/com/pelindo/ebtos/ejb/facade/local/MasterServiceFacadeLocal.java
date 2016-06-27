/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.master.MasterService;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author R. Seno Anggoro A
 */
@Local
public interface MasterServiceFacadeLocal {

    void create(MasterService masterService);

    void edit(MasterService masterService);

    void remove(MasterService masterService);

    MasterService find(Object id);

    List<MasterService> findAll();

    List<MasterService> findRange(int[] range);

    List<Object[]> findMasterServices();

}
