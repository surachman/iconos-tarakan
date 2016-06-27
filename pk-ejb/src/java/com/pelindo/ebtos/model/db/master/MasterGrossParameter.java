/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.model.db.master;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author arie
 */
@Entity
@Table(name = "m_gross_parameter")
@NamedQueries({
    @NamedQuery(name = "MasterGrossParameter.findAll", query = "SELECT m FROM MasterGrossParameter m"),
    @NamedQuery(name = "MasterGrossParameter.findByGrossCode", query = "SELECT m FROM MasterGrossParameter m WHERE m.grossCode = :grossCode"),
    @NamedQuery(name = "MasterGrossParameter.findByContSize", query = "SELECT m FROM MasterGrossParameter m WHERE m.contSize = :contSize"),
    @NamedQuery(name = "MasterGrossParameter.findByContSizeAndGross", query = "SELECT m FROM MasterGrossParameter m WHERE m.contSize = :contSize AND :contGross >= m.minGross AND :contGross < m.maxGross"),
    @NamedQuery(name = "MasterGrossParameter.findByGrossType", query = "SELECT m FROM MasterGrossParameter m WHERE m.grossType = :grossType"),
    @NamedQuery(name = "MasterGrossParameter.findByGrossClass", query = "SELECT m FROM MasterGrossParameter m WHERE m.grossClass = :grossClass"),
    @NamedQuery(name = "MasterGrossParameter.findByMinGross", query = "SELECT m FROM MasterGrossParameter m WHERE m.minGross = :minGross"),
    @NamedQuery(name = "MasterGrossParameter.findByMaxGross", query = "SELECT m FROM MasterGrossParameter m WHERE m.maxGross = :maxGross"),
    @NamedQuery(name = "MasterGrossParameter.findByCreatedBy", query = "SELECT m FROM MasterGrossParameter m WHERE m.createdBy = :createdBy"),
    @NamedQuery(name = "MasterGrossParameter.findByCreatedDate", query = "SELECT m FROM MasterGrossParameter m WHERE m.createdDate = :createdDate"),
    @NamedQuery(name = "MasterGrossParameter.findByModifiedBy", query = "SELECT m FROM MasterGrossParameter m WHERE m.modifiedBy = :modifiedBy"),
    @NamedQuery(name = "MasterGrossParameter.findByModifiedDate", query = "SELECT m FROM MasterGrossParameter m WHERE m.modifiedDate = :modifiedDate")})
public class MasterGrossParameter implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "gross_code")
    private String grossCode;
    @Column(name = "cont_size")
    private Short contSize;
    @Column(name = "gross_type")
    private String grossType;
    @Column(name = "gross_class")
    private String grossClass;
    @Column(name = "min_gross")
    private Integer minGross;
    @Column(name = "max_gross")
    private Integer maxGross;
    @Column(name = "created_by")
    private String createdBy;
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Column(name = "modified_by")
    private String modifiedBy;
    @Column(name = "modified_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;

    public MasterGrossParameter() {
    }

    public MasterGrossParameter(String grossCode) {
        this.grossCode = grossCode;
    }

    public String getGrossCode() {
        return grossCode;
    }

    public void setGrossCode(String grossCode) {
        this.grossCode = grossCode;
    }

    public Short getContSize() {
        return contSize;
    }

    public void setContSize(Short contSize) {
        this.contSize = contSize;
    }

    public String getGrossType() {
        return grossType;
    }

    public void setGrossType(String grossType) {
        this.grossType = grossType;
    }

    public String getGrossClass() {
        return grossClass;
    }

    public void setGrossClass(String grossClass) {
        this.grossClass = grossClass;
    }

    public Integer getMinGross() {
        return minGross;
    }

    public void setMinGross(Integer minGross) {
        this.minGross = minGross;
    }

    public Integer getMaxGross() {
        return maxGross;
    }

    public void setMaxGross(Integer maxGross) {
        this.maxGross = maxGross;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (grossCode != null ? grossCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MasterGrossParameter)) {
            return false;
        }
        MasterGrossParameter other = (MasterGrossParameter) object;
        if ((this.grossCode == null && other.grossCode != null) || (this.grossCode != null && !this.grossCode.equals(other.grossCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.master.MasterGrossParameter[grossCode=" + grossCode + "]";
    }

}
