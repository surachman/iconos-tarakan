/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.master.MasterUserGroup;
import com.pelindo.ebtos.model.db.master.MasterView;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author senoanggoro
 */
@Remote
public interface MasterUserGroupFacadeRemote {

    void create(MasterUserGroup masterUserGroup);

    void edit(MasterUserGroup masterUserGroup);

    void remove(MasterUserGroup masterUserGroup);

    MasterUserGroup find(Object id);

    List<MasterUserGroup> findAll();

    List<MasterUserGroup> findRange(int[] range);

    int count();

    public java.util.List<com.pelindo.ebtos.model.db.master.MasterUserGroup> findAllForView(Integer id);

    public java.util.List<com.pelindo.ebtos.model.db.master.MasterUserGroup> findGroupsByView(Integer id);

}
