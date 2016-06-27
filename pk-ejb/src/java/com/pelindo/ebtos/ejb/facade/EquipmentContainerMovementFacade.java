/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.local.EquipmentContainerMovementFacadeLocal;
import com.pelindo.ebtos.ejb.facade.remote.EquipmentContainerMovementFacadeRemote;
import com.pelindo.ebtos.model.db.EquipmentContainerMovement;
import com.pelindo.ebtos.model.db.PlanningVessel;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;

/**
 *
 * @author R Seno Anggoro
 */
@Stateless
public class EquipmentContainerMovementFacade extends AbstractFacade<EquipmentContainerMovement> implements EquipmentContainerMovementFacadeRemote, EquipmentContainerMovementFacadeLocal {
    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    @PersistenceUnit(unitName="pk-ejbPU")
    private EntityManagerFactory emf;

    protected EntityManager getEntityManager() {
        return em;
    }

    public EquipmentContainerMovementFacade() {
        super(EquipmentContainerMovement.class);
    }

    public EquipmentContainerMovement findNotFinishedYetByActivityAndService(String noPpkb, String contNo, String activity, String service) {
        try {
            return (EquipmentContainerMovement) getEntityManager().createNamedQuery("EquipmentContainerMovement.findNotFinishedYetByActivityAndService")
                    .setParameter("noPpkb", noPpkb)
                    .setParameter("contNo", contNo)
                    .setParameter("activity", activity)
                    .setParameter("service", service)
                    .getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    public int updatePlanningVesselByContNo(PlanningVessel newValue, PlanningVessel oldValue, List<String> containers) {
        EntityManager entityManager = emf.createEntityManager();
        return entityManager.createNamedQuery("EquipmentContainerMovement.updatePlanningVesselByContNo")
                .setParameter("newValue", newValue)
                .setParameter("oldValue", oldValue)
                .setParameter("containers", containers)
                .executeUpdate();
    }

}
