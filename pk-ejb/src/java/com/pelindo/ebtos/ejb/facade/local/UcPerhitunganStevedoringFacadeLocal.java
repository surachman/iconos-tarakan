/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.UcPerhitunganStevedoring;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author postgres
 */
@Local
public interface UcPerhitunganStevedoringFacadeLocal {

    void create(UcPerhitunganStevedoring ucPerhitunganStevedoring);

    void edit(UcPerhitunganStevedoring ucPerhitunganStevedoring);

    void remove(UcPerhitunganStevedoring ucPerhitunganStevedoring);

    UcPerhitunganStevedoring find(Object id);

    List<UcPerhitunganStevedoring> findAll();

    List<UcPerhitunganStevedoring> findRange(int[] range);

    int count();

}
