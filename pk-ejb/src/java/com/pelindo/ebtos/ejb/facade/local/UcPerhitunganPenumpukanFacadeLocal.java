/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.UcPerhitunganPenumpukan;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author postgres
 */
@Local
public interface UcPerhitunganPenumpukanFacadeLocal {

    void create(UcPerhitunganPenumpukan ucPerhitunganPenumpukan);

    void edit(UcPerhitunganPenumpukan ucPerhitunganPenumpukan);

    void remove(UcPerhitunganPenumpukan ucPerhitunganPenumpukan);

    UcPerhitunganPenumpukan find(Object id);

    List<UcPerhitunganPenumpukan> findAll();

    List<UcPerhitunganPenumpukan> findRange(int[] range);

    int count();

    public int deleteByNoPpkbReceivingOnly(java.lang.String noPpkb);

    int findByJobslip(String jobSlip);

}
