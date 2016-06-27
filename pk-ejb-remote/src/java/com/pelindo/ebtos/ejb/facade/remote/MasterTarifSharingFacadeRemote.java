/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.master.MasterTarifSharing;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author senoanggoro
 */
@Remote
public interface MasterTarifSharingFacadeRemote {

    void create(MasterTarifSharing masterTarifSharing);

    void edit(MasterTarifSharing masterTarifSharing);

    void remove(MasterTarifSharing masterTarifSharing);

    MasterTarifSharing find(Object id);

    List<MasterTarifSharing> findAll();

    List<MasterTarifSharing> findRange(int[] range);

    int count();

}
