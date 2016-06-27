/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.master.MasterContDamage;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author dycoder
 */
@Remote
public interface MasterContDamageFacadeRemote {

    void create(MasterContDamage masterContDamage);

    void edit(MasterContDamage masterContDamage);

    void remove(MasterContDamage masterContDamage);

    MasterContDamage find(Object id);

    List<MasterContDamage> findAll();

    List<MasterContDamage> findRange(int[] range);

    int count();

    List<Object[]> findMasterContDamages();

}
