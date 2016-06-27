/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.ChangeStatusHistory;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author dycoder
 */
@Local
public interface ChangeStatusHistoryFacadeLocal {

    void create(ChangeStatusHistory changeStatusHistory);

    void edit(ChangeStatusHistory changeStatusHistory);

    void remove(ChangeStatusHistory changeStatusHistory);

    ChangeStatusHistory find(Object id);

    List<ChangeStatusHistory> findAll();

    List<ChangeStatusHistory> findRange(int[] range);

    int count();

    public java.util.List<com.pelindo.ebtos.model.db.ChangeStatusHistory> findTranshipmentsByOldPpkb(java.lang.String noPpkb);

}
