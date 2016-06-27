/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.master.MasterBank;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author R Seno Anggoro
 */
@Remote
public interface MasterBankFacadeRemote {

    void create(MasterBank masterBank);

    void edit(MasterBank masterBank);

    void remove(MasterBank masterBank);

    MasterBank find(Object id);

    List<MasterBank> findAll();

    List<MasterBank> findRange(int[] range);

    int count();

    public com.pelindo.ebtos.model.db.master.MasterBank findByGuid(java.lang.String guid);

}
