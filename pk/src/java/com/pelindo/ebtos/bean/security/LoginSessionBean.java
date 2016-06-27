package com.pelindo.ebtos.bean.security;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



import com.qtasnim.jsf.FacesHelper;
import com.pelindo.ebtos.ejb.facade.remote.MasterSettingAppFacadeRemote;
import com.pelindo.ebtos.model.db.master.MasterSettingApp;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author senoanggoro
 */
@SessionScoped
@ManagedBean(name = LoginSessionBean.NAME, eager=true)
public class LoginSessionBean implements Serializable{
    public static final String NAME = "loginSessionBean";
    private static final String SUPERVISOR_GROUP_NAME_PARAM = "ebtos.group.supervisor_group_name";
    private static final String SUPERVISOR_KEUANGAN_GROUP_NAME_PARAM = "ebtos.group.supervisor_keuangan_group_name";

    private MasterSettingAppFacadeRemote masterSettingAppFacadeRemote = lookupMasterSettingAppFacadeRemote();
    private String username;
    private String name;
    private String judul;
    private String cabang;
    private List<String> groups;

     
    public LoginSessionBean() {
        
    }
    @PostConstruct 
    public void construct() {
        MasterSettingApp mct = masterSettingAppFacadeRemote.find("ebtos.judul.app");
        judul=mct.getDescription();
        
        MasterSettingApp mct2 = masterSettingAppFacadeRemote.find("ebtos.nama.cabang");
        cabang=mct2.getDescription();
        
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getGroups() {
        return groups;
    }

    public void setGroups(List<String> group) {
        this.groups = group;
    }

    public boolean isActive(){
        return (username != null) && (groups != null);
    }

    public long getServerTime(){
        return Calendar.getInstance().getTimeInMillis();
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getCabang() {
        return cabang;
    }

    public void setCabang(String cabang) {
        this.cabang = cabang;
    }
    
    
    
    public boolean isSupervisor(){
        if (getGroups() != null){
            MasterSettingApp supervisorGroupNameSettingApp = masterSettingAppFacadeRemote.find(SUPERVISOR_GROUP_NAME_PARAM);
            if (supervisorGroupNameSettingApp != null){
                if (getGroups().contains(supervisorGroupNameSettingApp.getValueString())){
                    return true;
                }
                return false;
            }
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, SUPERVISOR_GROUP_NAME_PARAM + " is not found");
            return false;
        }
        return false;
    }

    public boolean isSupervisorKeuangan() {
        if (getGroups() != null){
            MasterSettingApp supervisorKeuanganGroupNameSettingApp = masterSettingAppFacadeRemote.find(SUPERVISOR_KEUANGAN_GROUP_NAME_PARAM);
            if (supervisorKeuanganGroupNameSettingApp != null){
                if (getGroups().contains(supervisorKeuanganGroupNameSettingApp.getValueString())){
                    return true;
                }
                return false;
            }
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, SUPERVISOR_KEUANGAN_GROUP_NAME_PARAM + " is not found");
            return false;
        }
        return false;
    }

    public static LoginSessionBean getCurrentInstance(){
        return FacesHelper.translateElExpression(String.format("#{%s}", LoginSessionBean.NAME), FacesContext.getCurrentInstance(), LoginSessionBean.class);
    }

    private MasterSettingAppFacadeRemote lookupMasterSettingAppFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (MasterSettingAppFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/MasterSettingAppFacade!com.pelindo.ebtos.ejb.facade.remote.MasterSettingAppFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
