/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.UcPerhitunganLift;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author postgres
 */
@Local
public interface UcPerhitunganLiftFacadeLocal {

    void create(UcPerhitunganLift ucPerhitunganLift);

    void edit(UcPerhitunganLift ucPerhitunganLift);

    void remove(UcPerhitunganLift ucPerhitunganLift);

    UcPerhitunganLift find(Object id);

    List<UcPerhitunganLift> findAll();

    List<UcPerhitunganLift> findRange(int[] range);

    int count();

}
