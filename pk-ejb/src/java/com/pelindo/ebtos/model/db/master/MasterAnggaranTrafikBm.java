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
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author dycoder
 */
@Entity
@EntityListeners({AuditTrailEntityListener.class})
@Table(name = "m_anggaran_trafik_bm")
@NamedQueries({
    @NamedQuery(name = "MasterAnggaranTrafikBm.findAll", query = "SELECT m FROM MasterAnggaranTrafikBm m"),
    @NamedQuery(name = "MasterAnggaranTrafikBm.findByTahun", query = "SELECT m FROM MasterAnggaranTrafikBm m WHERE m.masterAnggaranTrafikBmPK.tahun = :tahun"),
    @NamedQuery(name = "MasterAnggaranTrafikBm.findByProdTahun", query = "SELECT m FROM MasterAnggaranTrafikBm m WHERE m.prodTahun = :prodTahun"),
    @NamedQuery(name = "MasterAnggaranTrafikBm.findByActivity", query = "SELECT m FROM MasterAnggaranTrafikBm m WHERE m.masterAnggaranTrafikBmPK.activity = :activity"),
    @NamedQuery(name = "MasterAnggaranTrafikBm.findByTriwulan1", query = "SELECT m FROM MasterAnggaranTrafikBm m WHERE m.triwulan1 = :triwulan1"),
    @NamedQuery(name = "MasterAnggaranTrafikBm.findByTriwulan2", query = "SELECT m FROM MasterAnggaranTrafikBm m WHERE m.triwulan2 = :triwulan2"),
    @NamedQuery(name = "MasterAnggaranTrafikBm.findByTriwulan3", query = "SELECT m FROM MasterAnggaranTrafikBm m WHERE m.triwulan3 = :triwulan3"),
    @NamedQuery(name = "MasterAnggaranTrafikBm.findByTriwulan4", query = "SELECT m FROM MasterAnggaranTrafikBm m WHERE m.triwulan4 = :triwulan4"),
    @NamedQuery(name = "MasterAnggaranTrafikBm.findByProdTriwulan1", query = "SELECT m FROM MasterAnggaranTrafikBm m WHERE m.prodTriwulan1 = :prodTriwulan1"),
    @NamedQuery(name = "MasterAnggaranTrafikBm.findByProdTriwulan2", query = "SELECT m FROM MasterAnggaranTrafikBm m WHERE m.prodTriwulan2 = :prodTriwulan2"),
    @NamedQuery(name = "MasterAnggaranTrafikBm.findByProdTriwulan3", query = "SELECT m FROM MasterAnggaranTrafikBm m WHERE m.prodTriwulan3 = :prodTriwulan3"),
    @NamedQuery(name = "MasterAnggaranTrafikBm.findByProdTriwulan4", query = "SELECT m FROM MasterAnggaranTrafikBm m WHERE m.prodTriwulan4 = :prodTriwulan4"),
    @NamedQuery(name = "MasterAnggaranTrafikBm.findByCreatedBy", query = "SELECT m FROM MasterAnggaranTrafikBm m WHERE m.createdBy = :createdBy"),
    @NamedQuery(name = "MasterAnggaranTrafikBm.findByCreatedDate", query = "SELECT m FROM MasterAnggaranTrafikBm m WHERE m.createdDate = :createdDate"),
    @NamedQuery(name = "MasterAnggaranTrafikBm.findByModifiedBy", query = "SELECT m FROM MasterAnggaranTrafikBm m WHERE m.modifiedBy = :modifiedBy"),
    @NamedQuery(name = "MasterAnggaranTrafikBm.findByModifiedDate", query = "SELECT m FROM MasterAnggaranTrafikBm m WHERE m.modifiedDate = :modifiedDate")})
@NamedNativeQueries({
    @NamedNativeQuery(name = "MasterAnggaranTrafikBm.Native.findAll", query = "SELECT tahun, activity, prod_tahun, triwulan1, triwulan2, triwulan3, triwulan4, description FROM m_anggaran_trafik_bm ORDER BY created_date DESC"),
    @NamedNativeQuery(name = "MasterAnggaranTrafikBm.Native.findTahunMax", query = "SELECT MAX(tahun) FROM m_anggaran_trafik_bm"),
    @NamedNativeQuery(name = "MasterAnggaranTrafikBm.Native.findAnggaranByTahun", query = "SELECT tahun, activity, prod_tahun, triwulan1, triwulan2, triwulan3, triwulan4, description FROM m_anggaran_trafik_bm WHERE tahun = ?"),
    @NamedNativeQuery(name = "MasterAnggaranTrafikBm.Native.findTahun", query = "SELECT DISTINCT(tahun) FROM m_anggaran_trafik_bm")
})
public class MasterAnggaranTrafikBm implements Serializable, EntityAuditable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected MasterAnggaranTrafikBmPK masterAnggaranTrafikBmPK;
    @Column(name = "prod_tahun", precision = 19, scale = 2)
    private BigDecimal prodTahun;
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
    @Column(name = "prod_triwulan2", precision = 19, scale = 2)
    private BigDecimal prodTriwulan2;
    @Column(name = "prod_triwulan3", precision = 19, scale = 2)
    private BigDecimal prodTriwulan3;
    @Column(name = "prod_triwulan4", precision = 19, scale = 2)
    private BigDecimal prodTriwulan4;
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
    @Column(name = "description", length = 256)
    private String description;

    public MasterAnggaranTrafikBm() {
    }

    public MasterAnggaranTrafikBm(MasterAnggaranTrafikBmPK masterAnggaranTrafikBmPK) {
        this.masterAnggaranTrafikBmPK = masterAnggaranTrafikBmPK;
    }

    public MasterAnggaranTrafikBm(MasterAnggaranTrafikBmPK masterAnggaranTrafikBmPK, String createdBy, Date createdDate) {
        this.masterAnggaranTrafikBmPK = masterAnggaranTrafikBmPK;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
    }

    public MasterAnggaranTrafikBm(String tahun, String activity) {
        this.masterAnggaranTrafikBmPK = new MasterAnggaranTrafikBmPK(tahun, activity);
    }

    public MasterAnggaranTrafikBmPK getMasterAnggaranTrafikBmPK() {
        return masterAnggaranTrafikBmPK;
    }

    public void setMasterAnggaranTrafikBmPK(MasterAnggaranTrafikBmPK masterAnggaranTrafikBmPK) {
        this.masterAnggaranTrafikBmPK = masterAnggaranTrafikBmPK;
    }

    public BigDecimal getProdTahun() {
        return prodTahun;
    }

    public void setProdTahun(BigDecimal prodTahun) {
        this.prodTahun = prodTahun;
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

    public BigDecimal getProdTriwulan2() {
        return prodTriwulan2;
    }

    public void setProdTriwulan2(BigDecimal prodTriwulan2) {
        this.prodTriwulan2 = prodTriwulan2;
    }

    public BigDecimal getProdTriwulan3() {
        return prodTriwulan3;
    }

    public void setProdTriwulan3(BigDecimal prodTriwulan3) {
        this.prodTriwulan3 = prodTriwulan3;
    }

    public BigDecimal getProdTriwulan4() {
        return prodTriwulan4;
    }

    public void setProdTriwulan4(BigDecimal prodTriwulan4) {
        this.prodTriwulan4 = prodTriwulan4;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (masterAnggaranTrafikBmPK != null ? masterAnggaranTrafikBmPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MasterAnggaranTrafikBm)) {
            return false;
        }
        MasterAnggaranTrafikBm other = (MasterAnggaranTrafikBm) object;
        if ((this.masterAnggaranTrafikBmPK == null && other.masterAnggaranTrafikBmPK != null) || (this.masterAnggaranTrafikBmPK != null && !this.masterAnggaranTrafikBmPK.equals(other.masterAnggaranTrafikBmPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.master.MasterAnggaranTrafikBm[masterAnggaranTrafikBmPK=" + masterAnggaranTrafikBmPK + "]";
    }

}
