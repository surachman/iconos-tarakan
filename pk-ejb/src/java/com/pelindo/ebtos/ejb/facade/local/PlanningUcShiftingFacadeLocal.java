/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.PlanningUcShifting;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author dycode-java
 */
@Local
public interface PlanningUcShiftingFacadeLocal {

    void create(PlanningUcShifting planningUcShifting);

    void edit(PlanningUcShifting planningUcShifting);

    void remove(PlanningUcShifting planningUcShifting);

    PlanningUcShifting find(Object id);

    List<PlanningUcShifting> findAll();

    List<PlanningUcShifting> findRange(int[] range);

    int count();

}
