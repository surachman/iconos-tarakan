/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.master.MasterYard;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author USER
 */
@Local
public interface MasterYardFacadeLocal {

    void create(MasterYard masterYard);

    void edit(MasterYard masterYard);

    void remove(MasterYard masterYard);

    MasterYard find(Object id);

    List<MasterYard> findAll();

    List<MasterYard> findRange(int[] range);

    int count();

    Object[] findMasterYardByBlock(String block);

    List<Object[]> findMasterYards();
    
    MasterYard findMasterYardsSimpleByBlock(String block);

}
