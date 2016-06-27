/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.model;

import com.pelindo.ebtos.ejb.facade.remote.MasterSettingAppFacadeRemote;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author senoanggoro
 */
public class NamedObject implements Serializable {
    public static final String AGENT_DN = "cn=Agent,ou=Groups,dc=ebtos,dc=pelabuhan4,dc=co,dc=id";
    public static final String AGENT_CN = "Agent";
    public static final String KEUANGAN_CN = "Keuangan";
    public static final String PERENCANA_DAN_OPERASI_CN = "Perencana dan Operasi";
    public static final String SPV_PERENCANAAN_DAN_OPERASI_CN = "Spv Perencanaan dan Operasi";
    
    private String dn;
    private List<String> groups;
    private List<String> groupsDN;
    private String cn;
    private String givenName; //First Name
    private String sn; //Last Name
    private String uid; //unique id, for login
    private String password;

    public NamedObject(){

    }

    public NamedObject(String cn, String givenName, String sn, String uid) {
        this.cn = cn;
        this.givenName = givenName;
        this.sn = sn;
        this.uid = uid;
    }

    public String getDn() {
        return dn;
    }

    public void setDn(String dn) {
        this.dn = dn;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getGroups() {
        return groups;
    }

    public void setGroups(List<String> groups) {
        this.groups = groups;
    }

    public List<String> getGroupsDN() {
        return groupsDN;
    }

    public void setGroupsDN(List<String> groupsDN) {
        this.groupsDN = groupsDN;
    }

    public String getCn() {
        return cn;
    }

    public void setCn(String cn) {
        this.cn = cn;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public boolean isReady(){
        return (groups!=null) && (password!=null) && (dn!=null);
    }

    private MasterSettingAppFacadeRemote lookupMasterSettingAppFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (MasterSettingAppFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/MasterSettingAppFacade!com.pelindo.ebtos.ejb.facade.remote.MasterSettingAppFacadeRemote");
        } catch (NamingException ne) {
            throw new RuntimeException(ne);
        }
    }
    
}
