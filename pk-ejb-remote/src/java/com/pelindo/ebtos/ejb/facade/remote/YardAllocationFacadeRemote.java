/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.YardAllocation;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author dycode-java
 */
@Remote
public interface YardAllocationFacadeRemote {

    void create(YardAllocation yardAllocation);

    void edit(YardAllocation yardAllocation);

    void remove(YardAllocation yardAllocation);

    YardAllocation find(Object id);

    List<YardAllocation> findAll();

    List<YardAllocation> findRange(int[] range);

    int count();

    List<Object[]> findAllByYardAllocationFilter(int yaf);

    int truncate();

    int deleteByIdConstraint(String id_constraint);

    List<String> unFinishBayPlanDischargeByPPKB(String no_ppkb);

}
