/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.model.db;

import com.qtasnim.persistence.AuditTrailEntityListener;
import com.qtasnim.persistence.EntityAuditable;
import com.pelindo.ebtos.model.db.master.MasterCustomer;
import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author R Seno Anggoro
 */
@Entity
@EntityListeners({AuditTrailEntityListener.class})
@Table(name = "rinci_cont_batal_nota")
@NamedQueries({
    @NamedQuery(name = "RinciContBatalNota.findAll", query = "SELECT r FROM RinciContBatalNota r"),
    @NamedQuery(name = "RinciContBatalNota.findByNoInvoice", query = "SELECT r FROM RinciContBatalNota r WHERE r.noInvoice = :noInvoice"),
    @NamedQuery(name = "RinciContBatalNota.findByJobSlip", query = "SELECT r FROM RinciContBatalNota r WHERE r.jobSlip = :jobSlip"),
    @NamedQuery(name = "RinciContBatalNota.findByBlNo", query = "SELECT r FROM RinciContBatalNota r WHERE r.blNo = :blNo"),
    @NamedQuery(name = "RinciContBatalNota.findByContNo", query = "SELECT r FROM RinciContBatalNota r WHERE r.contNo = :contNo"),
    @NamedQuery(name = "RinciContBatalNota.findByContStatus", query = "SELECT r FROM RinciContBatalNota r WHERE r.contStatus = :contStatus"),
    @NamedQuery(name = "RinciContBatalNota.findByContType", query = "SELECT r FROM RinciContBatalNota r WHERE r.contType = :contType"),
    @NamedQuery(name = "RinciContBatalNota.findByContGross", query = "SELECT r FROM RinciContBatalNota r WHERE r.contGross = :contGross"),
    @NamedQuery(name = "RinciContBatalNota.findByCharge", query = "SELECT r FROM RinciContBatalNota r WHERE r.charge = :charge"),
    @NamedQuery(name = "RinciContBatalNota.findByCreatedBy", query = "SELECT r FROM RinciContBatalNota r WHERE r.createdBy = :createdBy"),
    @NamedQuery(name = "RinciContBatalNota.findByCreatedDate", query = "SELECT r FROM RinciContBatalNota r WHERE r.createdDate = :createdDate"),
    @NamedQuery(name = "RinciContBatalNota.findByModifiedBy", query = "SELECT r FROM RinciContBatalNota r WHERE r.modifiedBy = :modifiedBy"),
    @NamedQuery(name = "RinciContBatalNota.findByModifiedDate", query = "SELECT r FROM RinciContBatalNota r WHERE r.modifiedDate = :modifiedDate")})
public class RinciContBatalNota implements Serializable, EntityAuditable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "no_invoice", nullable = false, length = 30)
    private String noInvoice;
    @Column(name = "job_slip", length = 15)
    private String jobSlip;
    @Column(name = "bl_no")
    private String blNo;
    @Basic(optional = false)
    @Column(name = "cont_no", nullable = false, length = 11)
    private String contNo;
    @Column(name = "cont_status", length = 10)
    private String contStatus;
    @Basic(optional = false)
    @Column(name = "cont_type", nullable = false)
    private int contType;
    @Basic(optional = false)
    @Column(name = "cont_gross", nullable = false)
    private int contGross;
    @Column(name = "charge", precision = 19, scale = 2)
    private BigDecimal charge;
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
    @JoinColumn(name = "no_invoice", referencedColumnName = "no_invoice", nullable = false, insertable = false, updatable = false)
    @OneToOne(optional = false)
    private BatalNota batalNota;
    @JoinColumn(name = "mlo", referencedColumnName = "cust_code")
    @ManyToOne
    private MasterCustomer mlo;

    public RinciContBatalNota() {
    }

    public RinciContBatalNota(String noInvoice) {
        this.noInvoice = noInvoice;
    }

    public RinciContBatalNota(String noInvoice, String contNo, int contType, int contGross, String createdBy, Date createdDate, MasterCustomer mlo) {
        this.noInvoice = noInvoice;
        this.contNo = contNo;
        this.contType = contType;
        this.contGross = contGross;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.mlo = mlo;
    }

    public String getNoInvoice() {
        return noInvoice;
    }

    public void setNoInvoice(String noInvoice) {
        this.noInvoice = noInvoice;
    }

    public String getJobSlip() {
        return jobSlip;
    }

    public void setJobSlip(String jobSlip) {
        this.jobSlip = jobSlip;
    }

    public String getBlNo() {
        return blNo;
    }

    public void setBlNo(String blNo) {
        this.blNo = blNo;
    }

    public String getContNo() {
        return contNo;
    }

    public void setContNo(String contNo) {
        this.contNo = contNo;
    }

    public String getContStatus() {
        return contStatus;
    }

    public void setContStatus(String contStatus) {
        this.contStatus = contStatus;
    }

    public int getContType() {
        return contType;
    }

    public void setContType(int contType) {
        this.contType = contType;
    }

    public int getContGross() {
        return contGross;
    }

    public void setContGross(int contGross) {
        this.contGross = contGross;
    }

    public BigDecimal getCharge() {
        return charge;
    }

    public void setCharge(BigDecimal charge) {
        this.charge = charge;
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

    public BatalNota getBatalNota() {
        return batalNota;
    }

    public void setBatalNota(BatalNota batalNota) {
        this.batalNota = batalNota;
    }

    /**
     * @return the mlo
     */
    public MasterCustomer getMlo() {
        return mlo;
    }

    /**
     * @param mlo the mlo to set
     */
    public void setMlo(MasterCustomer mlo) {
        this.mlo = mlo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (noInvoice != null ? noInvoice.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RinciContBatalNota)) {
            return false;
        }
        RinciContBatalNota other = (RinciContBatalNota) object;
        if ((this.noInvoice == null && other.noInvoice != null) || (this.noInvoice != null && !this.noInvoice.equals(other.noInvoice))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.RinciContBatalNota[noInvoice=" + noInvoice + "]";
    }

}
