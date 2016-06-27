/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.YardAllocationFilter;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author dycode-java
 */
@Local
public interface YardAllocationFilterFacadeLocal {

    void create(YardAllocationFilter yardAllocationFilter);

    void edit(YardAllocationFilter yardAllocationFilter);

    void remove(YardAllocationFilter yardAllocationFilter);

    YardAllocationFilter find(Object id);

    List<YardAllocationFilter> findAll();

    List<YardAllocationFilter> findRange(int[] range);

    int count();

}
