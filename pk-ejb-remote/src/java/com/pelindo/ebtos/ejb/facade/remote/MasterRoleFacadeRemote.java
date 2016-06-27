/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.master.MasterRole;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author senoanggoro
 */
@Remote
public interface MasterRoleFacadeRemote {

    void create(MasterRole masterRole);

    void edit(MasterRole masterRole);

    void remove(MasterRole masterRole);

    MasterRole find(Object id);

    List<MasterRole> findAll();

    List<MasterRole> findRange(int[] range);

    int count();

    public java.util.List<String> findGroupsByView(java.lang.Integer view);

    public java.util.List<com.pelindo.ebtos.model.db.master.MasterRole> findRolesByView(Integer masterView);

}
