/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.TempContDischarge;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Dyware-Dev01
 */
@Remote
public interface TempContDischargeFacadeRemote {

    void create(TempContDischarge tempContDischarge);

    void edit(TempContDischarge tempContDischarge);

    void remove(TempContDischarge tempContDischarge);

    TempContDischarge find(Object id);

    List<TempContDischarge> findAll();

    List<TempContDischarge> findRange(int[] range);

    int count();

    List<Object[]> findTempContDischargeByStatus(String no_ppkb, String status);

    List<Object[]> findTempContDischargeByPpkb(String no_ppkb);

    Object[] findByContNo(String cont_no, String pos);
}
