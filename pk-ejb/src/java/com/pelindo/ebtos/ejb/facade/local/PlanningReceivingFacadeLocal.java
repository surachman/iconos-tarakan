/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.PlanningReceiving;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author dycoder
 */
@Local
public interface PlanningReceivingFacadeLocal {

    void create(PlanningReceiving planningReceiving);

    void edit(PlanningReceiving planningReceiving);

    void remove(PlanningReceiving planningReceiving);

    PlanningReceiving find(Object id);

    List<PlanningReceiving> findAll();

    List<PlanningReceiving> findRange(int[] range);

    int count();

   List<Object[]> findPlanningReceivingList(Integer idCont);

   List<Integer> findAllIdByIdReceivingAllocation(int id);

   List<Object[]> findAllByIdReceivingAllocation(int id);

   Integer findLastOfId();

}
