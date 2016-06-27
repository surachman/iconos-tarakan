/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.TempContDischarge;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Dyware-Dev01
 */
@Local
public interface TempContDischargeFacadeLocal {

    void create(TempContDischarge tempContDischarge);

    void edit(TempContDischarge tempContDischarge);

    void remove(TempContDischarge tempContDischarge);

    TempContDischarge find(Object id);

    List<TempContDischarge> findAll();

    List<TempContDischarge> findRange(int[] range);

    int count();

}
