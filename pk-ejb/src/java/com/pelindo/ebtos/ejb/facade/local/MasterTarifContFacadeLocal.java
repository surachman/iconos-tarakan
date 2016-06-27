/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.master.MasterTarifCont;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author dycoder
 */
@Local
public interface MasterTarifContFacadeLocal {

    void create(MasterTarifCont masterTarifCont);

    void edit(MasterTarifCont masterTarifCont);

    void remove(MasterTarifCont masterTarifCont);

    MasterTarifCont find(Object id);

    List<MasterTarifCont> findAll();

    List<MasterTarifCont> findRange(int[] range);

    int count();
    
    BigDecimal findByCurrCodeAndActivity(String fclCode, String lclCode);

    public java.math.BigDecimal findChargeAfterDiscount(java.lang.String currCode, java.lang.String activityCode, java.lang.String customerCode);
}
