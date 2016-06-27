/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.YardAllocation;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author dycode-java
 */
@Local
public interface YardAllocationFacadeLocal {

    void create(YardAllocation yardAllocation);

    void edit(YardAllocation yardAllocation);

    void remove(YardAllocation yardAllocation);

    YardAllocation find(Object id);

    List<YardAllocation> findAll();

    List<YardAllocation> findRange(int[] range);

    int count();

}
