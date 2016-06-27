/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.master.MasterManualActivityType;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Dyware-Dev01
 */
@Local
public interface MasterManualActivityTypeFacadeLocal {

    void create(MasterManualActivityType mManualActivityType);

    void edit(MasterManualActivityType mManualActivityType);

    void remove(MasterManualActivityType mManualActivityType);

    MasterManualActivityType find(Object id);

    List<MasterManualActivityType> findAll();

    List<MasterManualActivityType> findRange(int[] range);

    int count();

}
