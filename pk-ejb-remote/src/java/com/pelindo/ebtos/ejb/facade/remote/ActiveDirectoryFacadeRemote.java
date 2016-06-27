/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.NamedObject;
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
    String getMessage();
    void changePassword(NamedObject user, String password);
    void create(NamedObject user, String ou, String userPassword);
    void remove(NamedObject user);
}
