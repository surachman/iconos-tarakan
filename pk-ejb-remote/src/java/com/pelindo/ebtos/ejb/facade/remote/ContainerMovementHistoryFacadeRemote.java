/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.ContainerMovementHistory;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author R Seno Anggoro
 */
@Remote
public interface ContainerMovementHistoryFacadeRemote {

    void create(ContainerMovementHistory containerMovementHistory);

    void edit(ContainerMovementHistory containerMovementHistory);

    void remove(ContainerMovementHistory containerMovementHistory);

    ContainerMovementHistory find(Object id);

    List<ContainerMovementHistory> findAll();

    List<ContainerMovementHistory> findRange(int[] range);

    int count();

    public java.util.List<java.lang.Object[]> findDischargeMovementHistories();
    
    public java.util.List<java.lang.Object[]> findReceivingMovementHistories();

    public com.pelindo.ebtos.model.db.ContainerMovementHistory findLastRecord(java.lang.String noPpkb, java.lang.String contNo);

    public void create(com.pelindo.ebtos.model.db.ContainerMovementHistory history, com.pelindo.ebtos.model.db.EquipmentContainerMovement... equipments);

    public java.util.List<java.lang.Object[]> findMovementHistories();

    public ContainerMovementHistory findMovableOffByContNo(String contNo);
}
