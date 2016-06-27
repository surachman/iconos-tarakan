/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.ReceivingUc;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author dycoder
 */
@Remote
public interface ReceivingUcFacadeRemote {

    void create(ReceivingUc receivingUc);

    void edit(ReceivingUc receivingUc);

    void remove(ReceivingUc receivingUc);

    ReceivingUc find(Object id);

    List<ReceivingUc> findAll();

    List<ReceivingUc> findRange(int[] range);

    int count();

    List<Object[]> findReceivingUcByPPKBnReg(String no_ppkb, String no_reg);

    String generateId(String bulan);
}
