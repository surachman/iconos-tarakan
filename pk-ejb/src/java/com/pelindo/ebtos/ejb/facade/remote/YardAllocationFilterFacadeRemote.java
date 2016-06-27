/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.YardAllocationFilter;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author dycode-java
 */
@Remote
public interface YardAllocationFilterFacadeRemote {

    void create(YardAllocationFilter yardAllocationFilter);

    void edit(YardAllocationFilter yardAllocationFilter);

    void remove(YardAllocationFilter yardAllocationFilter);

    YardAllocationFilter find(Object id);

    List<YardAllocationFilter> findAll();

    List<YardAllocationFilter> findRange(int[] range);

    int count();

    List<Object[]> yardAllocationFilterfindByNoPpkb(String no_ppkb, String is_trans, String isShift);

    int truncate();

    int deleteById(String id);

    long sumCountByPPKB(String no_ppkb);

    Object[] findById(int id);

    List<Integer> unFinishBayPlanDischargeByPPKB(String no_ppkb);
}
