/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.master.MasterLdapConf;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author senoanggoro
 */
@Local
public interface MasterLdapConfFacadeLocal {

    void create(MasterLdapConf masterLdapConf);

    void edit(MasterLdapConf masterLdapConf);

    void remove(MasterLdapConf masterLdapConf);

    MasterLdapConf find(Object id);

    List<MasterLdapConf> findAll();

    List<MasterLdapConf> findRange(int[] range);

    int count();

}
