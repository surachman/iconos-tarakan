/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.ChangeStatusHistory;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author dycoder
 */
@Remote
public interface ChangeStatusHistoryFacadeRemote {

    void create(ChangeStatusHistory changeStatusHistory);

    void edit(ChangeStatusHistory changeStatusHistory);

    void remove(ChangeStatusHistory changeStatusHistory);

    ChangeStatusHistory find(Object id);

    List<ChangeStatusHistory> findAll();

    List<ChangeStatusHistory> findRange(int[] range);

    int count();

    Object[] findByPpkbAndContNo(String ppkb, String cont_no);

    List<Object[]> findListTranshipment(String ppkb);
}
