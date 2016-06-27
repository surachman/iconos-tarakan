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
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
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
 * @author AnggiCipta
 */
@Entity
@EntityListeners({AuditTrailEntityListener.class})
@Table(name = "m_anggaran")
@NamedQueries({
    @NamedQuery(name = "MasterAnggaran.findAll", query = "SELECT m FROM MasterAnggaran m"),
    @NamedQuery(name = "MasterAnggaran.findByTahun", query = "SELECT m FROM MasterAnggaran m WHERE m.masterAnggaranPK.tahun = :tahun"),
    @NamedQuery(name = "MasterAnggaran.findByActivityCode", query = "SELECT m FROM MasterAnggaran m WHERE m.masterAnggaranPK.activityCode = :activityCode"),
    @NamedQuery(name = "MasterAnggaran.findByTarifDasar", query = "SELECT m FROM MasterAnggaran m WHERE m.tarifDasar = :tarifDasar"),
    @NamedQuery(name = "MasterAnggaran.findByProdTahun", query = "SELECT m FROM MasterAnggaran m WHERE m.prodTahun = :prodTahun"),
    @NamedQuery(name = "MasterAnggaran.findByPendTahun", query = "SELECT m FROM MasterAnggaran m WHERE m.pendTahun = :pendTahun"),
    @NamedQuery(name = "MasterAnggaran.findByTriwulan1", query = "SELECT m FROM MasterAnggaran m WHERE m.triwulan1 = :triwulan1"),
    @NamedQuery(name = "MasterAnggaran.findByTriwulan2", query = "SELECT m FROM MasterAnggaran m WHERE m.triwulan2 = :triwulan2"),
    @NamedQuery(name = "MasterAnggaran.findByTriwulan3", query = "SELECT m FROM MasterAnggaran m WHERE m.triwulan3 = :triwulan3"),
    @NamedQuery(name = "MasterAnggaran.findByTriwulan4", query = "SELECT m FROM MasterAnggaran m WHERE m.triwulan4 = :triwulan4"),
    @NamedQuery(name = "MasterAnggaran.findByProdTriwulan1", query = "SELECT m FROM MasterAnggaran m WHERE m.prodTriwulan1 = :prodTriwulan1"),
    @NamedQuery(name = "MasterAnggaran.findByPendTriwulan1", query = "SELECT m FROM MasterAnggaran m WHERE m.pendTriwulan1 = :pendTriwulan1"),
    @NamedQuery(name = "MasterAnggaran.findByProdTriwulan2", query = "SELECT m FROM MasterAnggaran m WHERE m.prodTriwulan2 = :prodTriwulan2"),
    @NamedQuery(name = "MasterAnggaran.findByPendTriwulan2", query = "SELECT m FROM MasterAnggaran m WHERE m.pendTriwulan2 = :pendTriwulan2"),
    @NamedQuery(name = "MasterAnggaran.findByProdTriwulan3", query = "SELECT m FROM MasterAnggaran m WHERE m.prodTriwulan3 = :prodTriwulan3"),
    @NamedQuery(name = "MasterAnggaran.findByPendTriwulan3", query = "SELECT m FROM MasterAnggaran m WHERE m.pendTriwulan3 = :pendTriwulan3"),
    @NamedQuery(name = "MasterAnggaran.findByProdTriwulan4", query = "SELECT m FROM MasterAnggaran m WHERE m.prodTriwulan4 = :prodTriwulan4"),
    @NamedQuery(name = "MasterAnggaran.findByPendTriwulan4", query = "SELECT m FROM MasterAnggaran m WHERE m.pendTriwulan4 = :pendTriwulan4"),
    @NamedQuery(name = "MasterAnggaran.findByCreatedBy", query = "SELECT m FROM MasterAnggaran m WHERE m.createdBy = :createdBy"),
    @NamedQuery(name = "MasterAnggaran.findByCreatedDate", query = "SELECT m FROM MasterAnggaran m WHERE m.createdDate = :createdDate"),
    @NamedQuery(name = "MasterAnggaran.findByModifiedBy", query = "SELECT m FROM MasterAnggaran m WHERE m.modifiedBy = :modifiedBy"),
    @NamedQuery(name = "MasterAnggaran.findByModifiedDate", query = "SELECT m FROM MasterAnggaran m WHERE m.modifiedDate = :modifiedDate"),
    @NamedQuery(name = "MasterAnggaran.findByCurrCode", query = "SELECT m FROM MasterAnggaran m WHERE m.masterAnggaranPK.currCode = :currCode"),
    @NamedQuery(name = "MasterAnggaran.findByDescription", query = "SELECT m FROM MasterAnggaran m WHERE m.description = :description")})
@NamedNativeQueries({
    @NamedNativeQuery(name = "MasterAnggaran.Native.findAll", query = "SELECT curr_code, tahun, description, tarif_dasar, prod_tahun, triwulan1, triwulan2, triwulan3, triwulan4, activity_code, CASE WHEN curr_code = 'IDR' THEN 'Domestik' ELSE 'Internasional' END AS tipe_pelayaran FROM m_anggaran")
})
public class MasterAnggaran implements Serializable, EntityAuditable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected MasterAnggaranPK masterAnggaranPK;
    @Column(name = "tarif_dasar", precision = 19, scale = 2)
    private BigDecimal tarifDasar;
    @Column(name = "prod_tahun", precision = 19, scale = 2)
    private BigDecimal prodTahun;
    @Column(name = "pend_tahun", precision = 19, scale = 2)
    private BigDecimal pendTahun;
    @Column(name = "triwulan1", precision = 10, scale = 2)
    private BigDecimal triwulan1;
    @Column(name = "triwulan2", precision = 10, scale = 2)
    private BigDecimal triwulan2;
    @Column(name = "triwulan3", precision = 10, scale = 2)
    private BigDecimal triwulan3;
    @Column(name = "triwulan4", precision = 10, scale = 2)
    private BigDecimal triwulan4;
    @Column(name = "prod_triwulan1", precision = 19, scale = 2)
    private BigDecimal prodTriwulan1;
    @Column(name = "pend_triwulan1", precision = 19, scale = 2)
    private BigDecimal pendTriwulan1;
    @Column(name = "prod_triwulan2", precision = 19, scale = 2)
    private BigDecimal prodTriwulan2;
    @Column(name = "pend_triwulan2", precision = 19, scale = 2)
    private BigDecimal pendTriwulan2;
    @Column(name = "prod_triwulan3", precision = 19, scale = 2)
    private BigDecimal prodTriwulan3;
    @Column(name = "pend_triwulan3", precision = 19, scale = 2)
    private BigDecimal pendTriwulan3;
    @Column(name = "prod_triwulan4", precision = 19, scale = 2)
    private BigDecimal prodTriwulan4;
    @Column(name = "pend_triwulan4", precision = 19, scale = 2)
    private BigDecimal pendTriwulan4;
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
    @Basic(optional = false)
    @Column(name = "description", nullable = false, length = 256)
    private String description;
    @JoinColumn(name = "activity_code", referencedColumnName = "activity_code", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private MasterActivity masterActivity;

    public MasterAnggaran() {
    }

    public MasterAnggaran(MasterAnggaranPK masterAnggaranPK) {
        this.masterAnggaranPK = masterAnggaranPK;
    }

    public MasterAnggaran(MasterAnggaranPK masterAnggaranPK, String createdBy, Date createdDate, String description) {
        this.masterAnggaranPK = masterAnggaranPK;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.description = description;
    }

    public MasterAnggaran(String tahun, String activityCode, String currCode) {
        this.masterAnggaranPK = new MasterAnggaranPK(tahun, activityCode, currCode);
    }

    public MasterAnggaranPK getMasterAnggaranPK() {
        return masterAnggaranPK;
    }

    public void setMasterAnggaranPK(MasterAnggaranPK masterAnggaranPK) {
        this.masterAnggaranPK = masterAnggaranPK;
    }

    public BigDecimal getTarifDasar() {
        return tarifDasar;
    }

    public void setTarifDasar(BigDecimal tarifDasar) {
        this.tarifDasar = tarifDasar;
    }

    public BigDecimal getProdTahun() {
        return prodTahun;
    }

    public void setProdTahun(BigDecimal prodTahun) {
        this.prodTahun = prodTahun;
    }

    public BigDecimal getPendTahun() {
        return pendTahun;
    }

    public void setPendTahun(BigDecimal pendTahun) {
        this.pendTahun = pendTahun;
    }

    public BigDecimal getTriwulan1() {
        return triwulan1;
    }

    public void setTriwulan1(BigDecimal triwulan1) {
        this.triwulan1 = triwulan1;
    }

    public BigDecimal getTriwulan2() {
        return triwulan2;
    }

    public void setTriwulan2(BigDecimal triwulan2) {
        this.triwulan2 = triwulan2;
    }

    public BigDecimal getTriwulan3() {
        return triwulan3;
    }

    public void setTriwulan3(BigDecimal triwulan3) {
        this.triwulan3 = triwulan3;
    }

    public BigDecimal getTriwulan4() {
        return triwulan4;
    }

    public void setTriwulan4(BigDecimal triwulan4) {
        this.triwulan4 = triwulan4;
    }

    public BigDecimal getProdTriwulan1() {
        return prodTriwulan1;
    }

    public void setProdTriwulan1(BigDecimal prodTriwulan1) {
        this.prodTriwulan1 = prodTriwulan1;
    }

    public BigDecimal getPendTriwulan1() {
        return pendTriwulan1;
    }

    public void setPendTriwulan1(BigDecimal pendTriwulan1) {
        this.pendTriwulan1 = pendTriwulan1;
    }

    public BigDecimal getProdTriwulan2() {
        return prodTriwulan2;
    }

    public void setProdTriwulan2(BigDecimal prodTriwulan2) {
        this.prodTriwulan2 = prodTriwulan2;
    }

    public BigDecimal getPendTriwulan2() {
        return pendTriwulan2;
    }

    public void setPendTriwulan2(BigDecimal pendTriwulan2) {
        this.pendTriwulan2 = pendTriwulan2;
    }

    public BigDecimal getProdTriwulan3() {
        return prodTriwulan3;
    }

    public void setProdTriwulan3(BigDecimal prodTriwulan3) {
        this.prodTriwulan3 = prodTriwulan3;
    }

    public BigDecimal getPendTriwulan3() {
        return pendTriwulan3;
    }

    public void setPendTriwulan3(BigDecimal pendTriwulan3) {
        this.pendTriwulan3 = pendTriwulan3;
    }

    public BigDecimal getProdTriwulan4() {
        return prodTriwulan4;
    }

    public void setProdTriwulan4(BigDecimal prodTriwulan4) {
        this.prodTriwulan4 = prodTriwulan4;
    }

    public BigDecimal getPendTriwulan4() {
        return pendTriwulan4;
    }

    public void setPendTriwulan4(BigDecimal pendTriwulan4) {
        this.pendTriwulan4 = pendTriwulan4;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        hash += (masterAnggaranPK != null ? masterAnggaranPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MasterAnggaran)) {
            return false;
        }
        MasterAnggaran other = (MasterAnggaran) object;
        if ((this.masterAnggaranPK == null && other.masterAnggaranPK != null) || (this.masterAnggaranPK != null && !this.masterAnggaranPK.equals(other.masterAnggaranPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.master.MasterAnggaran[masterAnggaranPK=" + masterAnggaranPK + "]";
    }

}
