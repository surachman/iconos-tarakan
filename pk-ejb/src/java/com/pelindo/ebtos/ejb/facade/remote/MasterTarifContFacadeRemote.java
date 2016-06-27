/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.master.MasterTarifCont;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author dycoder
 */
@Remote
public interface MasterTarifContFacadeRemote {

    void create(MasterTarifCont masterTarifCont);

    void edit(MasterTarifCont masterTarifCont);

    void remove(MasterTarifCont masterTarifCont);

    MasterTarifCont find(Object id);

    List<MasterTarifCont> findAll();

    List<MasterTarifCont> findRange(int[] range);

    int count();

    List<Object[]> findAllForMaster();

    List<Object[]> findAmountByActivity(String act);

    BigDecimal findByCurrCodeAndActivity(java.lang.String curr_code, String activity_code);

    Integer findForUsd(String curr_code, String activity_code);

    public java.math.BigDecimal findChargeAfterDiscount(java.lang.String currCode, java.lang.String activityCode, java.lang.String customerCode);

    public java.math.BigDecimal findChangeStatusChargeByActivityAndCurr(java.lang.String curr_code, java.lang.String fclCode, java.lang.String lclCode);
}
 