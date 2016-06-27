/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.model.db.ContainerMovementHistory;
import com.pelindo.ebtos.model.db.EquipmentContainerMovement;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author R. Seno Anggoro A
 */
@Local
public interface ContainerMovementHistoryFacadeLocal {

    void create(ContainerMovementHistory entity);

    void create(ContainerMovementHistory history, EquipmentContainerMovement... equipments);

    /**
     *
     * @return
     * 0. noPpkb
     * 1. cont_no
     * 2. cont_size
     * 3. type_in_general
     * 4. cont_status
     * 5. cont_gross
     * 6. block
     * 7. yard_slot
     * 8. yard_row
     * 9. yard_tier
     * 10. is_ht
     * 11. is_behandle
     * 12. is_cfs
     * 13. is_inspection
     * 14. is_cy
     * 15. is_last
     */
    List<Object[]> findDischargeMovementHistories();

    ContainerMovementHistory findLastRecord(String noPpkb, String contNo);

    ContainerMovementHistory findMovableOffByContNo(String contNo);

    /**
     *
     * @return
     * 0. noPpkb
     * 1. cont_no
     * 2. cont_size
     * 3. type_in_general
     * 4. cont_status
     * 5. cont_gross
     * 6. block
     * 7. yard_slot
     * 8. yard_row
     * 9. yard_tier
     * 10. is_ht
     * 11. is_behandle
     * 12. is_cfs
     * 13. is_inspection
     * 14. is_cy
     * 15. is_last
     */
    List<Object[]> findMovementHistories();

    /**
     *
     * @return
     * 0. noPpkb
     * 1. cont_no
     * 2. cont_size
     * 3. type_in_general
     * 4. cont_status
     * 5. cont_gross
     * 6. block
     * 7. yard_slot
     * 8. yard_row
     * 9. yard_tier
     * 10. is_ht
     * 11. is_behandle
     * 12. is_cfs
     * 13. is_inspection
     * 14. is_cy
     * 15. is_last
     */
    List<Object[]> findReceivingMovementHistories();

    public void edit(ContainerMovementHistory containerMovementHistory);

}
