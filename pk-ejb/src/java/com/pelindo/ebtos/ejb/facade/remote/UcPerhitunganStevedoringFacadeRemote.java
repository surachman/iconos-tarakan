/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.UcPerhitunganStevedoring;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author postgres
 */
@Remote
public interface UcPerhitunganStevedoringFacadeRemote {

    void create(UcPerhitunganStevedoring ucPerhitunganStevedoring);

    void edit(UcPerhitunganStevedoring ucPerhitunganStevedoring);

    void remove(UcPerhitunganStevedoring ucPerhitunganStevedoring);

    UcPerhitunganStevedoring find(Object id);

    List<UcPerhitunganStevedoring> findAll();

    List<UcPerhitunganStevedoring> findRange(int[] range);

    int count();

    Integer findByJobslip(String jobSlip);

    List<Object> findByReg(String no_reg);

    public int deleteByJobslip(java.lang.String jobslip);

    public int deleteByNoReg(java.lang.String noReg);
}
