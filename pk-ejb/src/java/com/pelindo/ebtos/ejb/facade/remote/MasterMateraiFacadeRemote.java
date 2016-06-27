/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.master.MasterMaterai;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Dyware-Dev01
 */
@Remote
public interface MasterMateraiFacadeRemote {

    void create(MasterMaterai masterMaterai);

    void edit(MasterMaterai masterMaterai);

    void remove(MasterMaterai masterMaterai);

    MasterMaterai find(Object id);

    List<MasterMaterai> findAll();

    List<MasterMaterai> findRange(int[] range);

    int count();

    List<Object[]> findByCurr(String currency);
}
