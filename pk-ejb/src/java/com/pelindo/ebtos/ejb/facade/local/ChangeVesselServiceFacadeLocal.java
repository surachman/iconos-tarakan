/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.ChangeVesselService;
import com.pelindo.ebtos.model.db.PlanningVessel;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author R. Seno Anggoro A
 */
@Local
public interface ChangeVesselServiceFacadeLocal {

    void create(ChangeVesselService changeVesselService);

    void edit(ChangeVesselService changeVesselService);

    void remove(ChangeVesselService changeVesselService);

    ChangeVesselService find(Object id);

    List<ChangeVesselService> findAll();

    List<ChangeVesselService> findRange(int[] range);

    int count();

    public java.util.List<com.pelindo.ebtos.model.db.ChangeVesselService> findByNoReg(java.lang.String noReg);

    public int deleteByNoPpkb(java.lang.String noPpkb);

    public PlanningVessel findNewPlanningVesselByNoReg(String noReg);

}
