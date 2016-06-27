/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.UcPerhitunganLift;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author postgres
 */
@Remote
public interface UcPerhitunganLiftFacadeRemote {

    void create(UcPerhitunganLift ucPerhitunganLift);

    void edit(UcPerhitunganLift ucPerhitunganLift);

    void remove(UcPerhitunganLift ucPerhitunganLift);

    UcPerhitunganLift find(Object id);

    List<UcPerhitunganLift> findAll();

    List<UcPerhitunganLift> findRange(int[] range);

    int count();

    public Integer findByUcPerhitunganLift(String jobSlip);

    List<Object> findByReg(String no_reg);

    public int deleteByNoReg(java.lang.String noReg);

    public int deleteByJobslip(java.lang.String jobslip);
}
