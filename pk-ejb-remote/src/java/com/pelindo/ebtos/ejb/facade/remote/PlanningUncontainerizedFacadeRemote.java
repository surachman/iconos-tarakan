/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.PlanningUncontainerized;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author senoanggoro
 */
@Remote
public interface PlanningUncontainerizedFacadeRemote {

    void create(PlanningUncontainerized planningUncontainerized);

    void edit(PlanningUncontainerized planningUncontainerized);

    void remove(PlanningUncontainerized planningUncontainerized);

    PlanningUncontainerized find(Object id);

    List<PlanningUncontainerized> findAll();

    List<PlanningUncontainerized> findRange(int[] range);

    int count();
    
    List<Object[]>findPlanningUncontainerizedByPpkb (String no_ppkb);

    List<Object[]> findPlanningUncontainerizedByPpkbDischarge(String no_ppkb);

    List<Object[]> findPlanningUncontainerizedByPpkbLoad(String no_ppkb);
}
