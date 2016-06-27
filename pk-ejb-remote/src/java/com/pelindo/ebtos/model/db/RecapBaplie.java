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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author R Seno Anggoro
 */
@Entity
@EntityListeners({AuditTrailEntityListener.class})
@Table(name = "recap_baplie", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"document_number"})})
@NamedQueries({
    @NamedQuery(name = "RecapBaplie.findAll", query = "SELECT r FROM RecapBaplie r"),
    @NamedQuery(name = "RecapBaplie.findById", query = "SELECT r FROM RecapBaplie r WHERE r.id = :id"),
    @NamedQuery(name = "RecapBaplie.findByNoPpkb", query = "SELECT r FROM RecapBaplie r WHERE r.serviceVessel.noPpkb = :noPpkb"),
    @NamedQuery(name = "RecapBaplie.findByMessage", query = "SELECT r FROM RecapBaplie r WHERE r.message = :message"),
    @NamedQuery(name = "RecapBaplie.findByCompilationTime", query = "SELECT r FROM RecapBaplie r WHERE r.compilationTime = :compilationTime"),
    @NamedQuery(name = "RecapBaplie.findByDocumentNumber", query = "SELECT r FROM RecapBaplie r WHERE r.documentNumber = :documentNumber")})
public class RecapBaplie implements Serializable, EntityAuditable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @Column(name = "message", nullable = false, length = 2147483647)
    private String message;
    @Basic(optional = false)
    @Column(name = "compilation_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date compilationTime;
    @Basic(optional = false)
    @Column(name = "document_number", nullable = false, length = 2147483647)
    private String documentNumber;
    @JoinColumn(name = "no_ppkb", referencedColumnName = "no_ppkb", nullable = false)
    @ManyToOne(optional = false)
    private ServiceVessel serviceVessel;
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

    public RecapBaplie() {
    }

    public RecapBaplie(Integer id) {
        this.id = id;
    }

    public RecapBaplie(Integer id, String message, Date compilationTime, String documentNumber) {
        this.id = id;
        this.message = message;
        this.compilationTime = compilationTime;
        this.documentNumber = documentNumber;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getCompilationTime() {
        return compilationTime;
    }

    public void setCompilationTime(Date compilationTime) {
        this.compilationTime = compilationTime;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public ServiceVessel getServiceVessel() {
        return serviceVessel;
    }

    public void setServiceVessel(ServiceVessel serviceVessel) {
        this.serviceVessel = serviceVessel;
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
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RecapBaplie)) {
            return false;
        }
        RecapBaplie other = (RecapBaplie) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.RecapBaplie[id=" + id + "]";
    }
}
