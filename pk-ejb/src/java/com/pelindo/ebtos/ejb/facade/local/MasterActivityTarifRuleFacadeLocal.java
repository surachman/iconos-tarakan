/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.master.MasterActivity;
import com.pelindo.ebtos.model.db.master.MasterActivityTarifRule;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author dycoder
 */
@Local
public interface MasterActivityTarifRuleFacadeLocal {

    void create(MasterActivityTarifRule masterActivity);

    void edit(MasterActivityTarifRule masterActivity);

    void remove(MasterActivityTarifRule masterActivity);

    MasterActivityTarifRule find(Object id);

    List<MasterActivityTarifRule> findAll();

    List<MasterActivityTarifRule> findRange(int[] range);

    int count();

    List<Object[]> findAllActivityTarifRule();

}
