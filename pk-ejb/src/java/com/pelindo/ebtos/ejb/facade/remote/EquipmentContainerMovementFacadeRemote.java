/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.EquipmentContainerMovement;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author R Seno Anggoro
 */
@Remote
public interface EquipmentContainerMovementFacadeRemote {

    void create(EquipmentContainerMovement equipmentContainerMovement);

    void edit(EquipmentContainerMovement equipmentContainerMovement);

    void remove(EquipmentContainerMovement equipmentContainerMovement);

    EquipmentContainerMovement find(Object id);

    List<EquipmentContainerMovement> findAll();

    List<EquipmentContainerMovement> findRange(int[] range);

    int count();

    public com.pelindo.ebtos.model.db.EquipmentContainerMovement findNotFinishedYetByActivityAndService(java.lang.String noPpkb, java.lang.String contNo, java.lang.String activity, java.lang.String service);

    public int updatePlanningVesselByContNo(com.pelindo.ebtos.model.db.PlanningVessel newValue, com.pelindo.ebtos.model.db.PlanningVessel oldValue, List<java.lang.String> contNo);

}
