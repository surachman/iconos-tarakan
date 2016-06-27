/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.PerhitunganPassGate;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author x42jr
 */
@Local
public interface PerhitunganPassGateFacadeLocal {

    void create(PerhitunganPassGate perhitunganPassGate);

    void edit(PerhitunganPassGate perhitunganPassGate);

    void remove(PerhitunganPassGate perhitunganPassGate);

    PerhitunganPassGate find(Object id);

    List<PerhitunganPassGate> findAll();

    List<PerhitunganPassGate> findRange(int[] range);

    int count();

    Integer deleteByJobSlip(String jobSlip);

    Integer deleteByNoReg(String noReg);

    PerhitunganPassGate findByJobSlip(String jobSlip);

    public int updatePlanningVessel(com.pelindo.ebtos.model.db.PlanningVessel newValue, com.pelindo.ebtos.model.db.PlanningVessel oldValue);
}
