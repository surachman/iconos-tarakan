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
import javax.persistence.Id;
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
@EntityListeners({AuditTrailEntityListener.class})
@Table(name = "m_shift")
@NamedQueries({
    @NamedQuery(name = "MasterShift.findAll", query = "SELECT m FROM MasterShift m"),
    @NamedQuery(name = "MasterShift.findByShiftCode", query = "SELECT m FROM MasterShift m WHERE m.shiftCode = :shiftCode"),
    @NamedQuery(name = "MasterShift.findByName", query = "SELECT m FROM MasterShift m WHERE m.name = :name"),
    @NamedQuery(name = "MasterShift.findByStartShift", query = "SELECT m FROM MasterShift m WHERE m.startShift = :startShift"),
    @NamedQuery(name = "MasterShift.findByEndShift", query = "SELECT m FROM MasterShift m WHERE m.endShift = :endShift")})
public class MasterShift implements Serializable, EntityAuditable {
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
    @Column(name = "shift_code", nullable = false)
    private Integer shiftCode;
    @Basic(optional = false)
    @Column(name = "name", nullable = false, length = 10)
    private String name;
    @Basic(optional = false)
    @Column(name = "start_shift", nullable = false)
    @Temporal(TemporalType.TIME)
    private Date startShift;
    @Basic(optional = false)
    @Column(name = "end_shift", nullable = false)
    @Temporal(TemporalType.TIME)
    private Date endShift;

    public MasterShift() {
    }

    public MasterShift(Integer shiftCode) {
        this.shiftCode = shiftCode;
    }

    public MasterShift(Integer shiftCode, String name, Date startShift, Date endShift) {
        this.shiftCode = shiftCode;
        this.name = name;
        this.startShift = startShift;
        this.endShift = endShift;
    }

    public Integer getShiftCode() {
        return shiftCode;
    }

    public void setShiftCode(Integer shiftCode) {
        this.shiftCode = shiftCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartShift() {
        return startShift;
    }

    public void setStartShift(Date startShift) {
        this.startShift = startShift;
    }

    public Date getEndShift() {
        return endShift;
    }

    public void setEndShift(Date endShift) {
        this.endShift = endShift;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (shiftCode != null ? shiftCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MasterShift)) {
            return false;
        }
        MasterShift other = (MasterShift) object;
        if ((this.shiftCode == null && other.shiftCode != null) || (this.shiftCode != null && !this.shiftCode.equals(other.shiftCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.MasterShift[shiftCode=" + shiftCode + "]";
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
