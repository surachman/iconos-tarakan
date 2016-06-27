/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.master.MasterActivitySharing;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author senoanggoro
 */
@Remote
public interface MasterActivitySharingFacadeRemote {

    void create(MasterActivitySharing masterActivitySharing);

    void edit(MasterActivitySharing masterActivitySharing);

    void remove(MasterActivitySharing masterActivitySharing);

    MasterActivitySharing find(Object id);

    List<MasterActivitySharing> findAll();

    List<MasterActivitySharing> findRange(int[] range);

    int count();

}
