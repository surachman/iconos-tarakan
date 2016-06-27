/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.YardAllocationTemp;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author dycode-java
 */
@Remote
public interface YardAllocationTempFacadeRemote {

    void create(YardAllocationTemp yardAllocationTemp);

    void edit(YardAllocationTemp yardAllocationTemp);

    void remove(YardAllocationTemp yardAllocationTemp);

    YardAllocationTemp find(Object id);

    List<YardAllocationTemp> findAll();

    List<YardAllocationTemp> findRange(int[] range);

    int count();

    int truncate();

    List<Integer> findId(int id_cont);

    int deleteByIdYardAllocation(String id_yard_allocation);

    int deleteByIdYardAllocationFilter(String id_yard_allocation_filter);

    List<Integer> distinctIdCont();

    List<Object[]> coordinatByIdCont(int id_cont);

    List<Object[]> findAllTemp();

    List<Integer> findCreateGenerate(String no_ppkb, int id_constraint);

    List<Object[]> findIdContNotGenerate(String no_ppkb, int id_constraint);

    int updateCoordinat(int idCoordinat, String idYA, int id);

    List<Object[]> removeGenerate(String idYA);

    int updateIdCoordinat(int new_id, int old_id);

    int removeIdCoordinat(int id);

    List<Integer> unFinishBayPlanDischargeByPPKB(String no_ppkb);

    List<YardAllocationTemp> findByNoPpkb(String noPpkb);
}
