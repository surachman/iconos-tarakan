/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.master.MasterManualActivityType;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Dyware-Dev01
 */
@Remote
public interface MasterManualActivityTypeFacadeRemote {

    void create(MasterManualActivityType mManualActivityType);

    void edit(MasterManualActivityType mManualActivityType);

    void remove(MasterManualActivityType mManualActivityType);

    MasterManualActivityType find(Object id);

    List<MasterManualActivityType> findAll();

    List<MasterManualActivityType> findRange(int[] range);

    int count();

    List<Object[]> findAllNative();

}
