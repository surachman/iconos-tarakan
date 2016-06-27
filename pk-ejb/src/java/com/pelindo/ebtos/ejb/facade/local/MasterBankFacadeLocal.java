/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.master.MasterBank;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author R Seno Anggoro
 */
@Local
public interface MasterBankFacadeLocal {

    void create(MasterBank masterBank);

    void edit(MasterBank masterBank);

    void remove(MasterBank masterBank);

    MasterBank find(Object id);

    List<MasterBank> findAll();

    List<MasterBank> findRange(int[] range);

    int count();

    public MasterBank findByGuid(String guid);

}
