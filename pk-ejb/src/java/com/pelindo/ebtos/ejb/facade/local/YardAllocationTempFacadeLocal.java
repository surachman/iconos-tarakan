/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.YardAllocationTemp;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author dycode-java
 */
@Local
public interface YardAllocationTempFacadeLocal {

    void create(YardAllocationTemp yardAllocationTemp);

    void edit(YardAllocationTemp yardAllocationTemp);

    void remove(YardAllocationTemp yardAllocationTemp);

    YardAllocationTemp find(Object id);

    List<YardAllocationTemp> findAll();

    List<YardAllocationTemp> findRange(int[] range);

    int count();

}
