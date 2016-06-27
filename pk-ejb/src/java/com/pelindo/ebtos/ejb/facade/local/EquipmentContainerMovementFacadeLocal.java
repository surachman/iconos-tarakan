/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.EquipmentContainerMovement;
import com.pelindo.ebtos.model.db.PlanningVessel;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author R. Seno Anggoro A
 */
@Local
public interface EquipmentContainerMovementFacadeLocal {

    EquipmentContainerMovement findNotFinishedYetByActivityAndService(String noPpkb, String contNo, String activity, String service);

    int updatePlanningVesselByContNo(PlanningVessel newValue, PlanningVessel oldValue, List<String> contNo);

    public void create(EquipmentContainerMovement equipmentLift);

    public void edit(EquipmentContainerMovement equipmentHT);

}
