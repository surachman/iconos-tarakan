/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.local.ActiveDirectoryFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.MasterUserFacadeLocal;
import com.pelindo.ebtos.ejb.facade.remote.ActiveDirectoryFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterUserFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterUserGroupFacadeRemote;
import com.pelindo.ebtos.model.NamedObject;
import com.pelindo.ebtos.model.db.master.MasterUser;
import com.pelindo.ebtos.model.db.master.MasterUserGroup;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author senoanggoro
 */
@Stateless
public class ActiveDirectoryFacade extends AbstractFacade<MasterUser> implements ActiveDirectoryFacadeRemote, ActiveDirectoryFacadeLocal{ 
    
    private MasterUserGroupFacadeRemote masterUserGroupFacadeRemote = lookupMasterUserGroupFacadeRemote();
    private String message;
    
    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;
    
    protected EntityManager getEntityManager() {
        return em;
    }

    public ActiveDirectoryFacade() {
        super(MasterUser.class);
    }

    public NamedObject findUserByUid(String uid){
        NamedObject object = new NamedObject();        
        try {
            MasterUser user = (MasterUser) getEntityManager().createNamedQuery("MasterUser.findByUsername")
                .setParameter("username", uid).setMaxResults(1).getSingleResult();
            object.setUid(uid);
            object.setDn(user.getDn());
            object.setCn(uid);
            object.setGroups(user.getGroupString());
            return object;
        } catch (Exception e) {
            return null;
        }
    }
   
    @Override
    public boolean authenticate(NamedObject user, String password){
        if (user != null){
            return authenticate(user.getUid(), password);
        } else {
            message = "message.access.login_error_credential";
            return false;
        }
    }

    @Override
    public boolean authenticate(String uid, String password){
        try {
            MasterUser user = (MasterUser) getEntityManager().createNamedQuery("MasterUser.findByUsername")
                    .setParameter("username", uid).setMaxResults(1).getSingleResult();
            if(user.getPassword().equals(MasterUser.hashPassword(password)))
                return true;
            else
                throw new NamingException();
        } catch (AuthenticationException ex) {
            message = "message.access.login_error_credential";
        } catch (NamingException ex) { //password not match
            message = "message.access.login_error_credential";
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return false;
    }

    @Override
    public void create(MasterUser entity) {
        entity.setPassword(MasterUser.hashPassword(entity.getPassword()));
        super.create(entity); 
    }
    
    public void create(NamedObject user, String groupDN, String userPassword) {
        try {
            List<MasterUser> userList = getEntityManager().createNamedQuery("MasterUser.findByUsername")
                .setParameter("username", user.getUid()).setMaxResults(1).getResultList();
            if (!userList.isEmpty()) {
                message = "message.user.user_already_exist";
            } else {
                MasterUser newUser = new MasterUser();
                newUser.setCn(user.getCn());
                newUser.setDn(user.getDn());
                newUser.setUsername(user.getUid());
                newUser.setSn(user.getSn());
                newUser.setPassword(MasterUser.hashPassword(userPassword));
                super.create(newUser);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void update(NamedObject user, Map<String, String> attrs){               
        try {
            List<MasterUser> userList = getEntityManager().createNamedQuery("MasterUser.findByUsername")
                .setParameter("username", user.getUid()).setMaxResults(1).getResultList();
            if (userList.isEmpty()) {
                message = "message.user.user_doesnt_exist";
            } else {
                MasterUser existingUser = userList.get(0);
                existingUser.setCn(attrs.get("cn"));
                existingUser.setDn(attrs.get("dn"));
                existingUser.setSn(attrs.get("sn"));
                existingUser.setPassword(MasterUser.hashPassword(attrs.get("userPassword")));
                super.edit(existingUser);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void changePassword(NamedObject user, String password){
        Map<String, String> attrs = new HashMap<String, String>();
        attrs.put("userPassword", password);
        update(user, attrs);
    }

    public void remove(NamedObject user){
        List<MasterUser> userList = getEntityManager().createNamedQuery("MasterUser.findByUsername")
            .setParameter("username", user.getUid()).setMaxResults(1).getResultList();
        if (userList.isEmpty()) {
            message = "message.user.user_doesnt_exist";
        } else {
            MasterUser existingUser = userList.get(0);
            super.remove(existingUser);
        }
    }

    public void assignUserToGroup(NamedObject user, String groupName){
        MasterUserGroup userGroup = masterUserGroupFacadeRemote.findByGroupName(groupName);
        if(userGroup != null) {
            List<MasterUser> existingUsers = getEntityManager().createNamedQuery("MasterUser.findByUsername")
                    .setParameter("username", user.getUid()).setMaxResults(1).getResultList();
            if(existingUsers.isEmpty()) { 
                message = "message.user.user_doesnt_exist";
            }
            else {
                MasterUser existingUser = existingUsers.get(0);
                List<MasterUserGroup> groups = existingUser.getGroups();
                boolean found = false;
                for(MasterUserGroup group: groups) {
                    if(group.getGroup().equals(groupName))
                        found = true;
                }
                if(!found){
                    groups.add(userGroup);
                    super.edit(existingUser);
                }                    
            } 
        }
        else {
            message = "message.group.group_doesnt_exist";
        }
    }
        
    public void removeUserFromGroup(NamedObject user, String groupName) {
        MasterUserGroup userGroup = masterUserGroupFacadeRemote.findByGroupName(groupName);
        if(userGroup != null) {
            List<MasterUser> existingUsers = getEntityManager().createNamedQuery("MasterUser.findByUsername")
                    .setParameter("username", user.getUid()).setMaxResults(1).getResultList();
            if(existingUsers.isEmpty()) { 
                message = "message.user.user_doesnt_exist";
            }
            else {
                MasterUser existingUser = existingUsers.get(0);
                List<MasterUserGroup> groups = existingUser.getGroups();
                MasterUserGroup foundGroup = null;
                for(MasterUserGroup group: groups) {
                    if(group.getGroup().equals(groupName))
                        foundGroup = group;
                }
                if(foundGroup != null){
                    groups.remove(foundGroup);
                    super.edit(existingUser);
                }                    
            } 
        }
        else {
            message = "message.group.group_doesnt_exist";
        }
    }

    public String getMessage(){
        return message;
    }
    
    private MasterUserGroupFacadeRemote lookupMasterUserGroupFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (MasterUserGroupFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/MasterUserGroupFacade!com.pelindo.ebtos.ejb.facade.remote.MasterUserGroupFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
    
    public List<Object[]> findMasterUsers(){    
        return getEntityManager().createNamedQuery("MasterUser.Native.findMasterUsers").getResultList();
    }

    @Override
    public MasterUser findByUsername(String uid) {
        try {
            MasterUser user = (MasterUser) getEntityManager().createNamedQuery("MasterUser.findByUsername")
                .setParameter("username", uid).setMaxResults(1).getSingleResult();
            return user;
        } catch (Exception e) {
            return null;
        }
    }
  
}
