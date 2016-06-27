/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.remote.ContainerMovementHistoryFacadeRemote;
import com.pelindo.ebtos.model.db.ContainerMovementHistory;
import com.pelindo.ebtos.model.db.EquipmentContainerMovement;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author R Seno Anggoro
 */
@Stateless
public class ContainerMovementHistoryFacade extends AbstractFacade<ContainerMovementHistory> implements ContainerMovementHistoryFacadeRemote, ContainerMovementHistoryFacadeLocal {
    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public ContainerMovementHistoryFacade() {
        super(ContainerMovementHistory.class);
    }

    public void create(ContainerMovementHistory entity) {
        ContainerMovementHistory lastRecord = findLastRecord(entity.getPlanningVessel().getNoPpkb(), entity.getContNo());

        if (lastRecord != null) {
            lastRecord.setIsLast("FALSE");
            edit(lastRecord);
        }
        
        entity.setIsLast("TRUE");
        getEntityManager().persist(entity);
    }

    public void create(ContainerMovementHistory history, EquipmentContainerMovement... equipments) {
        ContainerMovementHistory lastRecord = findLastRecord(history.getPlanningVessel().getNoPpkb(), history.getContNo());

        if (lastRecord != null) {
            lastRecord.setIsLast("FALSE");
            edit(lastRecord);
        }

        history.setIsLast("TRUE");
        getEntityManager().persist(history);

        for (EquipmentContainerMovement equipmentContainerMovement: equipments) {
            equipmentContainerMovement.setContainerMovementHistory(history);
            getEntityManager().persist(equipmentContainerMovement);
        }
    }

    public ContainerMovementHistory findLastRecord(String noPpkb, String contNo) {
        try {
            return (ContainerMovementHistory) getEntityManager().createNamedQuery("ContainerMovementHistory.findLastRecord")
                    .setParameter("noPpkb", noPpkb)
                    .setParameter("contNo", contNo)
                    .getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    /**
     *
     * @return
     *      0. noPpkb
     *      1. cont_no
     *      2. cont_size
     *      3. type_in_general
     *      4. cont_status
     *      5. cont_gross
     *      6. block
     *      7. yard_slot
     *      8. yard_row
     *      9. yard_tier
     *      10. is_ht
     *      11. is_behandle
     *      12. is_cfs
     *      13. is_inspection
     *      14. is_cy
     *      15. is_last
     */
    public List<Object[]> findDischargeMovementHistories() {
        return getEntityManager().createNamedQuery("ContainerMovementHistory.Native.findDischargeMovementHistories").getResultList();
    }

    /**
     *
     * @return
     *      0. noPpkb
     *      1. cont_no
     *      2. cont_size
     *      3. type_in_general
     *      4. cont_status
     *      5. cont_gross
     *      6. block
     *      7. yard_slot
     *      8. yard_row
     *      9. yard_tier
     *      10. is_ht
     *      11. is_behandle
     *      12. is_cfs
     *      13. is_inspection
     *      14. is_cy
     *      15. is_last
     */
    public List<Object[]> findReceivingMovementHistories() {
        return getEntityManager().createNamedQuery("ContainerMovementHistory.Native.findReceivingMovementHistories").getResultList();
    }

    /**
     *
     * @return
     *      0. noPpkb
     *      1. cont_no
     *      2. cont_size
     *      3. type_in_general
     *      4. cont_status
     *      5. cont_gross
     *      6. block
     *      7. yard_slot
     *      8. yard_row
     *      9. yard_tier
     *      10. is_ht
     *      11. is_behandle
     *      12. is_cfs
     *      13. is_inspection
     *      14. is_cy
     *      15. is_last
     */
    public List<Object[]> findMovementHistories() {
        return getEntityManager().createNamedQuery("ContainerMovementHistory.Native.findMovementHistories").getResultList();
    }

    @Override
    public ContainerMovementHistory findMovableOffByContNo(String contNo) {
        try {
            return (ContainerMovementHistory) getEntityManager().createNamedQuery("ContainerMovementHistory.findMovableOffByContNo")
                    .setParameter("contNo", contNo)
                    .getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }
}
