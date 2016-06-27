/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.model.db.master;

import com.qtasnim.persistence.AuditTrailEntityListener;
import com.qtasnim.persistence.EntityAuditable;
import java.io.Serializable;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author R. Seno Anggoro A
 */
@Entity
@EntityListeners({AuditTrailEntityListener.class})
@Table(name = "m_user")
@NamedQueries({
    @NamedQuery(name = "MasterUser.findAll", query = "SELECT m FROM MasterUser m"),
    @NamedQuery(name = "MasterUser.findById", query = "SELECT m FROM MasterUser m WHERE m.id = :id"),
    @NamedQuery(name = "MasterUser.findByUsername", query = "SELECT m FROM MasterUser m WHERE m.username = :username")})
@NamedNativeQueries({
    @NamedNativeQuery(name = "MasterUser.Native.findMasterUsers", query = "select min(u.id), u.username, min(u.cn), \n" +
"listagg(case when g.group_name is not null then g.group_name end, ', ') within group (order by g.group_name) as groups\n" +
"from m_user u \n" +
"left join m_user_user_group ug on u.id = ug.id_user\n" +
"left join m_user_group g on  g.id = ug.id_group\n" +
"group by u.username\n" +
"order by u.username asc"),
})
public class MasterUser implements Serializable, EntityAuditable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    //@GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @JoinTable(name="m_user_user_group",
        joinColumns=@JoinColumn(name="id_user", referencedColumnName="id"),
        inverseJoinColumns=@JoinColumn(name="id_group", referencedColumnName="id"))
    @ManyToMany(fetch=FetchType.EAGER)    
    private List<MasterUserGroup> masterUserGroups;  
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;  
    @Column(name = "cn")
    private String cn;
    @Column(name = "ou")
    private String ou;
    @Column(name = "sn")
    private String sn;
    @Column(name = "dn")
    private String dn;
    @Column(name = "dc")
    private String dc;
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Column(name = "created_by", length = 100)
    private String createdBy;
    @Column(name = "modified_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;
    @Column(name = "modified_by", length = 100)
    private String modifiedBy;

    public MasterUser() {
    }

    public MasterUser(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public List<MasterUserGroup> getGroups(){
        return masterUserGroups;
    }
    
    public void setGroups(List<MasterUserGroup> groups){
        this.masterUserGroups = groups;
    }
    
    public List<String> getGroupString(){
        List<String> result = new ArrayList<String>();
        for(MasterUserGroup userGroup: masterUserGroups){
            result.add(userGroup.getGroup());
        }
        return result;
    }
    
    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(password.getBytes());
            byte[] hashBytes = md.digest();
            return new sun.misc.BASE64Encoder().encode(hashBytes);
        }
        catch(Exception e)
        {
            throw new RuntimeException(e);
        }
    }
    
    public String getCn() {
        return cn;
    }

    public void setCn(String cn) {
        this.cn = cn;
    }

    public String getOu() {
        return ou;
    }

    public void setOu(String ou) {
        this.ou = ou;
    }
    
    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }    

    public String getDn() {
        return dn;
    }

    public void setDn(String dn) {
        this.dn = dn;
    }
    
    public String getDc() {
        return dc;
    }

    public void setDc(String dc) {
        this.dc = dc;
    }
    
    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MasterUser)) {
            return false;
        }
        MasterUser other = (MasterUser) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.MasterUser[id=" + id + "]";
    }

}

