/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.model.db.master;

import com.qtasnim.persistence.AuditTrailEntityListener;
import com.qtasnim.persistence.EntityAuditable;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author wulan
 */
@Entity
@EntityListeners({AuditTrailEntityListener.class})
@Table(name = "m_yard_type")
@NamedQueries({
    @NamedQuery(name = "MasterYardType.findAll", query = "SELECT m FROM MasterYardType m"),
    @NamedQuery(name = "MasterYardType.findById", query = "SELECT m FROM MasterYardType m WHERE m.id = :id"),
    @NamedQuery(name = "MasterYardType.findByYardType", query = "SELECT m FROM MasterYardType m WHERE m.yardType = :yardType"),
    @NamedQuery(name = "MasterYardType.findByDescription", query = "SELECT m FROM MasterYardType m WHERE m.description = :description"),
    @NamedQuery(name = "MasterYardType.findByCreatedDate", query = "SELECT m FROM MasterYardType m WHERE m.createdDate = :createdDate"),
    @NamedQuery(name = "MasterYardType.findByCreatedBy", query = "SELECT m FROM MasterYardType m WHERE m.createdBy = :createdBy"),
    @NamedQuery(name = "MasterYardType.findByModifiedDate", query = "SELECT m FROM MasterYardType m WHERE m.modifiedDate = :modifiedDate"),
    @NamedQuery(name = "MasterYardType.findByModifiedBy", query = "SELECT m FROM MasterYardType m WHERE m.modifiedBy = :modifiedBy")})
public class MasterYardType implements Serializable, EntityAuditable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    //@GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "yard_type", length = 50)
    private String yardType;
    @Column(name = "description", length = 100)
    private String description;
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

    public MasterYardType() {
    }

    public MasterYardType(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getYardType() {
        return yardType;
    }

    public void setYardType(String yardType) {
        this.yardType = yardType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        if (!(object instanceof MasterYardType)) {
            return false;
        }
        MasterYardType other = (MasterYardType) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.MasterYardType[id=" + id + "]";
    }

}
