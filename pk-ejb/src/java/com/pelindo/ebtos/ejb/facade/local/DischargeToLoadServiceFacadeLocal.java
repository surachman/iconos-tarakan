/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.DischargeToLoadService;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author R. Seno Anggoro A
 */
@Local
public interface DischargeToLoadServiceFacadeLocal {

    void create(DischargeToLoadService dischargeToLoadService);

    void edit(DischargeToLoadService dischargeToLoadService);

    void remove(DischargeToLoadService dischargeToLoadService);

    DischargeToLoadService find(Object id);

    List<DischargeToLoadService> findAll();

    List<DischargeToLoadService> findRange(int[] range);

    int count();

    public int deleteByNoReg(java.lang.String noReg);

    List<DischargeToLoadService> findByNoReg(String noReg);

}
