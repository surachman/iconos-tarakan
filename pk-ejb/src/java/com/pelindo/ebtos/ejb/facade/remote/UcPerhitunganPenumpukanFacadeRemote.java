/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.UcPerhitunganPenumpukan;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author postgres
 */
@Remote
public interface UcPerhitunganPenumpukanFacadeRemote {

    void create(UcPerhitunganPenumpukan ucPerhitunganPenumpukan);

    void edit(UcPerhitunganPenumpukan ucPerhitunganPenumpukan);

    void remove(UcPerhitunganPenumpukan ucPerhitunganPenumpukan);

    UcPerhitunganPenumpukan find(Object id);

    List<UcPerhitunganPenumpukan> findAll();

    List<UcPerhitunganPenumpukan> findRange(int[] range);

    int count();

    int findByJobslip(String jobSlip);

    List<Object> findByReg(String no_reg);

    public int deleteByJobslip(java.lang.String jobslip);

    public int deleteByNoReg(java.lang.String noReg);
}
