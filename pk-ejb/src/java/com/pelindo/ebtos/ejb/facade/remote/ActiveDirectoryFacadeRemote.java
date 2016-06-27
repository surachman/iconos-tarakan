/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.NamedObject;
import com.pelindo.ebtos.model.db.master.MasterUser;
import java.util.List;
import java.util.Map;
import javax.ejb.Remote;
import javax.naming.directory.Attributes;

/**
 *
 * @author dycoder
 */
@Remote
public interface ActiveDirectoryFacadeRemote {
    boolean authenticate(NamedObject user, String password);
    boolean authenticate(String dn, String password);
    
    NamedObject findUserByUid(String uid);
    MasterUser findByUsername(String uid);
    MasterUser find(Object uid);
    
    String getMessage();
    void changePassword(NamedObject user, String password);
    
    
    
    void create(MasterUser user);
    void create(NamedObject user, String ou, String userPassword);
    
    void remove(NamedObject user);
    void remove(MasterUser user);
    
    void edit(MasterUser user);
    void update(NamedObject user, Map<String, String> attrs);
    
    List<Object[]> findMasterUsers();
}
