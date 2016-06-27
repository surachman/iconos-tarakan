/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.master.MasterYard;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author USER
 */
@Remote
public interface MasterYardFacadeRemote {

    void create(MasterYard masterYard);

    void edit(MasterYard masterYard);

    void remove(MasterYard masterYard);

    MasterYard find(Object id);

    List<MasterYard> findAll();

    List<MasterYard> findRange(int[] range);

    int count();

    Object[] findMasterYardByBlock(String block);

    List<Object[]> findMasterYards();

    Object[] findAllMax(String block);

    List<String> findAllBlocks();

    public java.util.List<String> findNotPlannedBlockLocationByNoPpkb(java.lang.String noPpkb);

    public java.util.List<java.lang.Integer> findNotPlannedSlotLocationByNoPpkbAndBlock(java.lang.String noPpkb, java.lang.String block);
    
    public Long findYardCapacity();
}
