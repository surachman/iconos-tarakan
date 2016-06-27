/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.NamedObject;
import javax.ejb.Local;

/**
 *
 * @author dycoder
 */
@Local
public interface MasterUserFacadeLocal {
    boolean authenticate(NamedObject user, String password);
    boolean authenticate(String dn, String password);
    NamedObject findUserByUid(String uid);
    String getMessage();
    void changePassword(NamedObject user, String password);
    void create(NamedObject user, String ou, String userPassword);
    void remove(NamedObject user);
}
