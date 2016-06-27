/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.exception.ReceivingAllocationIsNotEnoughException;
import com.pelindo.ebtos.exception.NotAllocatedReceivingException;
import com.pelindo.ebtos.model.db.StuffingService;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author dycoder
 */
@Remote
public interface StuffingServiceFacadeRemote {

    void create(StuffingService stuffingService);

    void edit(StuffingService stuffingService);

    void remove(StuffingService stuffingService);

    StuffingService find(Object id);

    List<StuffingService> findAll();

    List<StuffingService> findRange(int[] range);

    int count();

    List<Object[]> findStuffingServiceByPPKBnReg(String no_reg);

    String generateId(String bulan);

    String findInvoice(String cont_no, String no_reg);

    List<Object[]> findPerhitungan(String no_reg);

    List<Object[]> findStuffingServiceByReg(String no_reg);

    List<Object[]> findByNoregValidasiPrint(String no_reg);

    public List<StuffingService> findByNoReg(String noReg);
}
