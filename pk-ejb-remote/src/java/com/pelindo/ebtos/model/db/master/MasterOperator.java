/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.model.db.master;

import com.qtasnim.persistence.AuditTrailEntityListener;
import com.qtasnim.persistence.EntityAuditable;
import com.pelindo.ebtos.model.db.Equipment;
import com.pelindo.ebtos.model.db.SewaAlat;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
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
@Table(name = "m_operator")
@NamedQueries({
    @NamedQuery(name = "MasterOperator.findAll", query = "SELECT m FROM MasterOperator m"),
    @NamedQuery(name = "MasterOperator.findByOptCode", query = "SELECT m FROM MasterOperator m WHERE m.optCode = :optCode"),
    @NamedQuery(name = "MasterOperator.findByName", query = "SELECT m FROM MasterOperator m WHERE m.name = :name"),
    @NamedQuery(name = "MasterOperator.findByIsOutsource", query = "SELECT m FROM MasterOperator m WHERE m.isOutsource = :isOutsource")})
@NamedNativeQueries({
    @NamedNativeQuery(name = "MasterOperator.Native.findOperators", query = "SELECT opt_code, name FROM m_operator")
    })
public class MasterOperator implements Serializable, EntityAuditable {
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
    @Column(name = "opt_code", nullable = false, length = 5)
    private String optCode;
    @Basic(optional = false)
    @Column(name = "name", nullable = false, length = 50)
    private String name;
    @Basic(optional = false)
    @Column(name = "is_outsource", nullable = false)
    private boolean isOutsource;
    @OneToMany(mappedBy = "masterOperator")
    private List<Equipment> equipmentList;
    @OneToMany(mappedBy = "masterOperator")
    private List<SewaAlat> sewaAlatList;

    public MasterOperator() {
    }

    public MasterOperator(String optCode) {
        this.optCode = optCode;
    }

    public MasterOperator(String optCode, String name, boolean isOutsource) {
        this.optCode = optCode;
        this.name = name;
        this.isOutsource = isOutsource;
    }

    public String getOptCode() {
        return optCode;
    }

    public void setOptCode(String optCode) {
        this.optCode = optCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getIsOutsource() {
        return isOutsource;
    }

    public void setIsOutsource(boolean isOutsource) {
        this.isOutsource = isOutsource;
    }

    public List<Equipment> getEquipmentList() {
        return equipmentList;
    }

    public void setEquipmentList(List<Equipment> equipmentList) {
        this.equipmentList = equipmentList;
    }

    public List<SewaAlat> getSewaAlatList() {
        return sewaAlatList;
    }

    public void setSewaAlatList(List<SewaAlat> sewaAlatList) {
        this.sewaAlatList = sewaAlatList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (optCode != null ? optCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MasterOperator)) {
            return false;
        }
        MasterOperator other = (MasterOperator) object;
        if ((this.optCode == null && other.optCode != null) || (this.optCode != null && !this.optCode.equals(other.optCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.MasterOperator[optCode=" + optCode + "]";
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
