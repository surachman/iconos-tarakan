/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.master.MasterDiscount;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author senoanggoro
 */
@Local
public interface MasterDiscountFacadeLocal {

    void create(MasterDiscount masterDiscount);

    void edit(MasterDiscount masterDiscount);

    void remove(MasterDiscount masterDiscount);

    MasterDiscount find(Object id);

    List<MasterDiscount> findAll();

    List<MasterDiscount> findRange(int[] range);

    int count();

    public java.math.BigDecimal[] getValidDiscount(java.lang.String custCode, java.lang.String actCode);

}
