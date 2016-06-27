/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.model.db.master;

import com.qtasnim.persistence.AuditTrailEntityListener;
import com.qtasnim.persistence.EntityAuditable;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author senoanggoro
 */
@Entity
@EntityListeners({AuditTrailEntityListener.class})
@Table(name = "m_view")
@NamedQueries({
    @NamedQuery(name = "MasterView.findAll", query = "SELECT m FROM MasterView m WHERE (SELECT COUNT(mv) FROM MasterView mv WHERE mv.parent = m.id)=0 ORDER BY m.id"),
    @NamedQuery(name = "MasterView.findById", query = "SELECT m FROM MasterView m WHERE m.id = :id"),
    @NamedQuery(name = "MasterView.findByView", query = "SELECT m FROM MasterView m WHERE m.view = :view"),
    @NamedQuery(name = "MasterView.findMessageByView", query = "SELECT m.message FROM MasterView m WHERE m.view = :view"),
    @NamedQuery(name = "MasterView.findByMessage", query = "SELECT m FROM MasterView m WHERE m.message = :message"),
    @NamedQuery(name = "MasterView.findByParent", query = "SELECT m FROM MasterView m WHERE m.parent = :parent")})
@NamedNativeQueries({
    @NamedNativeQuery(name = "MasterView.findChildsByParent", query = "SELECT mv.id, mv.view, mv.message, mv.parent, (SELECT COUNT(*)::integer FROM m_view WHERE parent = mv.id) AS children FROM m_view mv WHERE mv.parent=? ORDER BY mv.id")})
public class MasterView implements Serializable, EntityAuditable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @Column(name = "view", nullable = false, length = 2147483647)
    private String view;
    @Basic(optional = false)
    @Column(name = "message", nullable = false, length = 2147483647)
    private String message;
    @Basic(optional = false)
    @Column(name = "parent", nullable = false)
    private int parent;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "masterView", fetch=FetchType.LAZY, orphanRemoval=true)
    private List<MasterRole> masterRoleList;
    @Basic(optional = false)
    @Column(name = "created_by", nullable = false, length = 256)
    private String createdBy;
    @Basic(optional = false)
    @Column(name = "created_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Column(name = "modified_by", length = 256)
    private String modifiedBy;
    @Column(name = "modified_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;

    public MasterView() {
    }

    public MasterView(Integer id) {
        this.id = id;
    }

    public MasterView(Integer id, String view, String message, int parent) {
        this.id = id;
        this.view = view;
        this.message = message;
        this.parent = parent;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getParent() {
        return parent;
    }

    public void setParent(int parent) {
        this.parent = parent;
    }

    public List<MasterRole> getMasterRoleList() {
        return masterRoleList;
    }

    public void setMasterRoleList(List<MasterRole> masterRoleList) {
        this.masterRoleList = masterRoleList;
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
        if (!(object instanceof MasterView)) {
            return false;
        }
        MasterView other = (MasterView) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.master.MasterView[id=" + id + "]";
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

}
