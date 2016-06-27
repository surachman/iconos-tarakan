/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.model.db;

import com.qtasnim.persistence.AuditTrailEntityListener;
import com.qtasnim.persistence.EntityAuditable;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author dycode-java
 */
@Entity
@EntityListeners({AuditTrailEntityListener.class})
@Table(name = "log_excel")
@NamedQueries({
    @NamedQuery(name = "LogExcel.findAll", query = "SELECT l FROM LogExcel l"),
    @NamedQuery(name = "LogExcel.findByChangeName", query = "SELECT l FROM LogExcel l WHERE l.changeName = :changeName"),
    @NamedQuery(name = "LogExcel.findByOriginalName", query = "SELECT l FROM LogExcel l WHERE l.originalName = :originalName"),
    @NamedQuery(name = "LogExcel.findByNoPpkb", query = "SELECT l FROM LogExcel l WHERE l.noPpkb = :noPpkb"),
    @NamedQuery(name = "LogExcel.findByUploadTime", query = "SELECT l FROM LogExcel l WHERE l.uploadTime = :uploadTime"),
    @NamedQuery(name = "LogExcel.findByUrl", query = "SELECT l FROM LogExcel l WHERE l.url = :url")})
@NamedNativeQueries({
    @NamedNativeQuery(name ="LogExcel.Native.findAllExcel",query="SELECT * FROM log_excel")
    })
public class LogExcel implements Serializable, EntityAuditable {
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
    @Column(name = "change_name", nullable = false, length = 2147483647)
    private String changeName;
    @Column(name = "original_name", length = 2147483647)
    private String originalName;
    @Column(name = "no_ppkb", length = 30)
    private String noPpkb;
    @Column(name = "upload_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date uploadTime;
    @Column(name = "url", length = 2147483647)
    private String url;
    @Column(name = "type", length = 2147483647)
    private String type;
    @Column(name = "vessel_code", length = 2147483647)
    private String vesselCode;
    @Column(name = "vessel_name", length = 2147483647)
    private String vesselName;

    public LogExcel() {
    }

    public LogExcel(String changeName) {
        this.changeName = changeName;
    }

    public String getChangeName() {
        return changeName;
    }

    public void setChangeName(String changeName) {
        this.changeName = changeName;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getNoPpkb() {
        return noPpkb;
    }

    public void setNoPpkb(String noPpkb) {
        this.noPpkb = noPpkb;
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVesselCode() {
        return vesselCode;
    }

    public void setVesselCode(String vesselCode) {
        this.vesselCode = vesselCode;
    }

    public String getVesselName() {
        return vesselName;
    }

    public void setVesselName(String vesselName) {
        this.vesselName = vesselName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (changeName != null ? changeName.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LogExcel)) {
            return false;
        }
        LogExcel other = (LogExcel) object;
        if ((this.changeName == null && other.changeName != null) || (this.changeName != null && !this.changeName.equals(other.changeName))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.LogExcel[changeName=" + changeName + "]";
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
