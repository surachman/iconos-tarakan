/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.UnderpaymentHistory;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author x42jr
 */
@Remote
public interface UnderpaymentHistoryFacadeRemote {

    void create(UnderpaymentHistory underpaymentHistory);

    void edit(UnderpaymentHistory underpaymentHistory);

    void remove(UnderpaymentHistory underpaymentHistory);

    UnderpaymentHistory find(Object id);

    List<UnderpaymentHistory> findAll();

    List<UnderpaymentHistory> findRange(int[] range);

    int count();

}
