/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.model;

import java.io.Serializable;

/**
 *
 * @author USER 13
 */
public class TallyModel implements Serializable {
    String username;
    String group;    
    Boolean isSupervisor;
    Boolean isAdmin;
    Boolean isAuthenticated;

    public TallyModel(){

    }

    public TallyModel(Boolean isSpv, Boolean isAdm, Boolean isAuth){
        this.isAdmin = isAdm;
        this.isSupervisor = isSpv;
        this.isAuthenticated = isAuth;
    }

    public Boolean getIsAuthenticated() {
        return isAuthenticated;
    }

    public void setIsAuthenticated(Boolean isAuthenticated) {
        this.isAuthenticated = isAuthenticated;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getIsSupervisor() {
        return isSupervisor;
    }

    public void setIsSupervisor(Boolean isSupervisor) {
        this.isSupervisor = isSupervisor;
    }

    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }
}
