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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
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
@Table(name = "m_tarif_cont")
@NamedQueries({
    @NamedQuery(name = "MasterTarifCont.findAll", query = "SELECT m FROM MasterTarifCont m"),
    @NamedQuery(name = "MasterTarifCont.findById", query = "SELECT m FROM MasterTarifCont m WHERE m.id = :id"),
    @NamedQuery(name = "MasterTarifCont.findByAmount", query = "SELECT m FROM MasterTarifCont m WHERE m.amount = :amount"),
    @NamedQuery(name = "MasterTarifCont.findByTmt", query = "SELECT m FROM MasterTarifCont m WHERE m.tmt = :tmt")})
@NamedNativeQueries({
    @NamedNativeQuery(name="MasterTarifCont.Native.findAllForMaster", query = "SELECT a.description, t.amount, t2.amount, t.id, t.rekening, t2.rekening FROM m_tarif_cont t, m_tarif_cont t2, m_activity a WHERE t.activity_code = t2.activity_code AND t.curr_code != t2.curr_code AND t.activity_code = a.activity_code AND t.curr_code = 'IDR' AND t2.curr_code = 'USD' ORDER BY t.id ASC"),
    @NamedNativeQuery(name="MasterTarifCont.Native.findByIdActivity", query = "SELECT t.amount, t.curr_code FROM m_tarif_cont t WHERE t.activity_code =  ?"),
    @NamedNativeQuery(name="MasterTarifCont.Native.findByActivityAndCurr", query = "SELECT amount FROM m_tarif_cont WHERE curr_code=? AND activity_code=?"),
    
    @NamedNativeQuery(name="MasterTarifCont.Native.findForUsd", query = "SELECT id FROM m_tarif_cont WHERE curr_code=? AND activity_code=?")
})
public class MasterTarifCont implements Serializable, EntityAuditable {
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
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @Column(name = "amount", precision = 19, scale = 2)
    private BigDecimal amount;
    @Column(name = "tmt")
    @Temporal(TemporalType.DATE)
    private Date tmt;
    @Basic(optional = false)
    @Column(name = "rekening", nullable = false, length = 20)
    private String rekening;
    @JoinColumn(name = "curr_code", referencedColumnName = "curr_code")
    @ManyToOne
    private MasterCurrency masterCurrency;
    @JoinColumn(name = "activity_code", referencedColumnName = "activity_code")
    @ManyToOne
    private MasterActivity masterActivity;

    public MasterTarifCont() {
    }

    public MasterTarifCont(Integer id) {
        this.id = id;
    }

    public MasterTarifCont(Integer id, BigDecimal amount) {
        this.id = id;
        this.amount = amount;
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

    public String getRekening() {
        return rekening;
    }

    public void setRekening(String rekening) {
        this.rekening = rekening;
    }

    public MasterCurrency getMasterCurrency() {
        return masterCurrency;
    }

    public void setMasterCurrency(MasterCurrency masterCurrency) {
        this.masterCurrency = masterCurrency;
    }

    public MasterActivity getMasterActivity() {
        return masterActivity;
    }

    public void setMasterActivity(MasterActivity masterActivity) {
        this.masterActivity = masterActivity;
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
        if (!(object instanceof MasterTarifCont)) {
            return false;
        }
        MasterTarifCont other = (MasterTarifCont) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.MasterTarifCont[id=" + id + "]";
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
