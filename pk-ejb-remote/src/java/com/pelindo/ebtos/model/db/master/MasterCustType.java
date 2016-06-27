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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "m_cust_type")
@NamedQueries({
    @NamedQuery(name = "MasterCustType.findAll", query = "SELECT m FROM MasterCustType m"),
    @NamedQuery(name = "MasterCustType.findByCustTypeId", query = "SELECT m FROM MasterCustType m WHERE m.custTypeId = :custTypeId"),
    @NamedQuery(name = "MasterCustType.findByName", query = "SELECT m FROM MasterCustType m WHERE m.name = :name")})
public class MasterCustType implements Serializable, EntityAuditable {
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
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "cust_type_id", nullable = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer custTypeId;
    @Column(name = "name", length = 256)
    private String name;
    @OneToMany(mappedBy = "masterCustType")
    private List<MasterCustomer> masterCustomerList;

    public MasterCustType() {
    }

    public MasterCustType(Integer custTypeId) {
        this.custTypeId = custTypeId;
    }

    public Integer getCustTypeId() {
        return custTypeId;
    }

    public void setCustTypeId(Integer custTypeId) {
        this.custTypeId = custTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<MasterCustomer> getMasterCustomerList() {
        return masterCustomerList;
    }

    public void setMasterCustomerList(List<MasterCustomer> masterCustomerList) {
        this.masterCustomerList = masterCustomerList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (custTypeId != null ? custTypeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MasterCustType)) {
            return false;
        }
        MasterCustType other = (MasterCustType) object;
        if ((this.custTypeId == null && other.custTypeId != null) || (this.custTypeId != null && !this.custTypeId.equals(other.custTypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.MasterCustType[custTypeId=" + custTypeId + "]";
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
