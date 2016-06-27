/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.model.db.master;

import com.qtasnim.persistence.AuditTrailEntityListener;
import com.qtasnim.persistence.EntityAuditable;
import com.pelindo.ebtos.model.db.ServiceReceivingBarang;
import com.pelindo.ebtos.model.db.ServiceStripping;
import com.pelindo.ebtos.model.db.ServiceStuffing;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
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
@Table(name = "m_uom")
@NamedQueries({
    @NamedQuery(name = "MasterUom.findAll", query = "SELECT m FROM MasterUom m"),
    @NamedQuery(name = "MasterUom.findByUomCode", query = "SELECT m FROM MasterUom m WHERE m.uomCode = :uomCode"),
    @NamedQuery(name = "MasterUom.findByName", query = "SELECT m FROM MasterUom m WHERE m.name = :name")})
public class MasterUom implements Serializable, EntityAuditable {
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
    @Column(name = "uom_code", nullable = false, length = 5)
    private String uomCode;
    @Column(name = "name", length = 10)
    private String name;
    @OneToMany(mappedBy = "masterUom")
    private List<ServiceStripping> serviceStrippingList;
    @OneToMany(mappedBy = "masterUom")
    private List<ServiceStuffing> serviceStuffingList;
    @OneToMany(mappedBy = "masterUom")
    private List<ServiceReceivingBarang> serviceReceivingBarangList;

    public MasterUom() {
    }

    public MasterUom(String uomCode) {
        this.uomCode = uomCode;
    }

    public String getUomCode() {
        return uomCode;
    }

    public void setUomCode(String uomCode) {
        this.uomCode = uomCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ServiceStripping> getServiceStrippingList() {
        return serviceStrippingList;
    }

    public void setServiceStrippingList(List<ServiceStripping> serviceStrippingList) {
        this.serviceStrippingList = serviceStrippingList;
    }

    public List<ServiceStuffing> getServiceStuffingList() {
        return serviceStuffingList;
    }

    public void setServiceStuffingList(List<ServiceStuffing> serviceStuffingList) {
        this.serviceStuffingList = serviceStuffingList;
    }

    public List<ServiceReceivingBarang> getServiceReceivingBarangList() {
        return serviceReceivingBarangList;
    }

    public void setServiceReceivingBarangList(List<ServiceReceivingBarang> serviceReceivingBarangList) {
        this.serviceReceivingBarangList = serviceReceivingBarangList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (uomCode != null ? uomCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MasterUom)) {
            return false;
        }
        MasterUom other = (MasterUom) object;
        if ((this.uomCode == null && other.uomCode != null) || (this.uomCode != null && !this.uomCode.equals(other.uomCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.MasterUom[uomCode=" + uomCode + "]";
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
