/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.model.db.master;

import com.qtasnim.persistence.AuditTrailEntityListener;
import com.qtasnim.persistence.EntityAuditable;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author senoanggoro
 */
@Entity
@Table(name = "m_tarif_sharing")
@EntityListeners({AuditTrailEntityListener.class})
@NamedQueries({
    @NamedQuery(name = "MasterTarifSharing.findAll", query = "SELECT m FROM MasterTarifSharing m"),
    @NamedQuery(name = "MasterTarifSharing.findById", query = "SELECT m FROM MasterTarifSharing m WHERE m.id = :id"),
    @NamedQuery(name = "MasterTarifSharing.findByAmount", query = "SELECT m FROM MasterTarifSharing m WHERE m.amount = :amount"),
    @NamedQuery(name = "MasterTarifSharing.findByTmt", query = "SELECT m FROM MasterTarifSharing m WHERE m.tmt = :tmt"),
    @NamedQuery(name = "MasterTarifSharing.findByCreatedBy", query = "SELECT m FROM MasterTarifSharing m WHERE m.createdBy = :createdBy"),
    @NamedQuery(name = "MasterTarifSharing.findByCreatedDate", query = "SELECT m FROM MasterTarifSharing m WHERE m.createdDate = :createdDate"),
    @NamedQuery(name = "MasterTarifSharing.findByModifiedBy", query = "SELECT m FROM MasterTarifSharing m WHERE m.modifiedBy = :modifiedBy"),
    @NamedQuery(name = "MasterTarifSharing.findByModifiedDate", query = "SELECT m FROM MasterTarifSharing m WHERE m.modifiedDate = :moifiedDate")})
public class MasterTarifSharing implements Serializable, EntityAuditable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    //@GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "amount", precision = 19, scale = 2)
    private BigDecimal amount;
    @Column(name = "tmt")
    @Temporal(TemporalType.DATE)
    private Date tmt;
    @Basic(optional = false)
    @Column(name = "created_by", nullable = false, length = 30)
    private String createdBy;
    @Basic(optional = false)
    @Column(name = "created_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Column(name = "modified_by", length = 30)
    private String modifiedBy;
    @Column(name = "modified_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;
    @JoinColumn(name = "owner_code", referencedColumnName = "owner_code")
    @ManyToOne
    private MasterOwnerEquipment masterOwnerEquipment;
    @JoinColumn(name = "activity_code", referencedColumnName = "activity_code")
    @ManyToOne
    private MasterActivitySharing masterActivitySharing;

    public MasterTarifSharing() {
    }

    public MasterTarifSharing(Integer id) {
        this.id = id;
    }

    public MasterTarifSharing(Integer id, String createdBy, Date createdDate) {
        this.id = id;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getTmt() {
        return tmt;
    }

    public void setTmt(Date tmt) {
        this.tmt = tmt;
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

    public MasterActivitySharing getMasterActivitySharing() {
        return masterActivitySharing;
    }

    public void setMasterActivitySharing(MasterActivitySharing masterActivitySharing) {
        this.masterActivitySharing = masterActivitySharing;
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
        if (!(object instanceof MasterTarifSharing)) {
            return false;
        }
        MasterTarifSharing other = (MasterTarifSharing) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.master.MasterTarifSharing[id=" + id + "]";
    }

    public MasterOwnerEquipment getMasterOwnerEquipment() {
        return masterOwnerEquipment;
    }

    public void setMasterOwnerEquipment(MasterOwnerEquipment masterOwnerEquipment) {
        this.masterOwnerEquipment = masterOwnerEquipment;
    }

}
