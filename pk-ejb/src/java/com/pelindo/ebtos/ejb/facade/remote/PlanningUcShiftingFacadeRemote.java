/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.PlanningUcShifting;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author dycode-java
 */
@Remote
public interface PlanningUcShiftingFacadeRemote {

    void create(PlanningUcShifting planningUcShifting);

    void edit(PlanningUcShifting planningUcShifting);

    void remove(PlanningUcShifting planningUcShifting);

    PlanningUcShifting find(Object id);

    List<PlanningUcShifting> findAll();

    List<PlanningUcShifting> findRange(int[] range);

    int count();

    List<Object[]> findAllByPPKB(String no_ppkb);

}
