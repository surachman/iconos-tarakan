/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.master.MasterLdapConf;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author senoanggoro
 */
@Remote
public interface MasterLdapConfFacadeRemote {

    void create(MasterLdapConf masterLdapConf);

    void edit(MasterLdapConf masterLdapConf);

    void remove(MasterLdapConf masterLdapConf);

    MasterLdapConf find(Object id);

    List<MasterLdapConf> findAll();

    List<MasterLdapConf> findRange(int[] range);

    int count();

}
