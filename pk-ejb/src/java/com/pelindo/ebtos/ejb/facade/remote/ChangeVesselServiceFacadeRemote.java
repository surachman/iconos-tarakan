/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.ChangeVesselService;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author R. Seno Anggoro A
 */
@Remote
public interface ChangeVesselServiceFacadeRemote {

    void create(ChangeVesselService changeVesselService);

    void edit(ChangeVesselService changeVesselService);

    void remove(ChangeVesselService changeVesselService);

    ChangeVesselService find(Object id);

    List<ChangeVesselService> findAll();

    List<ChangeVesselService> findRange(int[] range);

    int count();

    public java.util.List<com.pelindo.ebtos.model.db.ChangeVesselService> findByNoReg(java.lang.String noReg);

    public com.pelindo.ebtos.model.db.PlanningVessel findNewPlanningVesselByNoReg(java.lang.String noReg);

}
