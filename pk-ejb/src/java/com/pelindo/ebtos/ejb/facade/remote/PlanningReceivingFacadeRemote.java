/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.PlanningReceiving;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author dycoder
 */
@Remote
public interface PlanningReceivingFacadeRemote {

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

    public java.lang.Object[] findRecommendedLocation(java.lang.String noPpkb, java.lang.String portCode, java.lang.Integer contType, java.lang.String grossClass, java.lang.Short contSize, java.lang.String contStatus, java.lang.String overSize, java.lang.String dg, java.lang.String isExport);

}
