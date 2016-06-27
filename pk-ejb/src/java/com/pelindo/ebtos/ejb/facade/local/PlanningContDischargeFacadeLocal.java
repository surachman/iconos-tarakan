/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.ejb.facade.remote.*;
import com.pelindo.ebtos.model.db.PlanningContDischarge;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author senoanggoro
 */
@Local
public interface PlanningContDischargeFacadeLocal {

    void create(PlanningContDischarge planningContDischarge);

    void edit(PlanningContDischarge planningContDischarge);

    void remove(PlanningContDischarge planningContDischarge);

    PlanningContDischarge find(Object id);

    List<PlanningContDischarge> findAll();

    List<PlanningContDischarge> findRange(int[] range);

    int count();

    Object[] findContOnBay(String noPpkb, int bay, int row, int tier);

    public int deleteByContNoAndNoPpkb(java.lang.String contNo, java.lang.String noPpkb);
}
