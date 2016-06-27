/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.PlanningVesselHistory;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author x42jr
 */
@Remote
public interface PlanningVesselHistoryFacadeRemote {

    void create(PlanningVesselHistory planningVesselHistory);

    void edit(PlanningVesselHistory planningVesselHistory);

    void remove(PlanningVesselHistory planningVesselHistory);

    PlanningVesselHistory find(Object id);

    List<PlanningVesselHistory> findAll();

    List<PlanningVesselHistory> findRange(int[] range);

    int count();

    public java.util.List<java.lang.Object[]> getHistoriesByField(String noPpkb, java.lang.String field);

}
