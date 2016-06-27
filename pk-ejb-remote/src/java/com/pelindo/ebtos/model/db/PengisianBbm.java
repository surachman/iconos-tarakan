/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.model.db;

import com.qtasnim.persistence.AuditTrailEntityListener;
import com.qtasnim.persistence.EntityAuditable;
import com.pelindo.ebtos.model.db.master.MasterEquipment;
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

/**
 *
 * @author senoanggoro
 */
@Entity
@EntityListeners({AuditTrailEntityListener.class})
@Table(name = "pengisian_bbm")
@NamedQueries({
    @NamedQuery(name = "PengisianBbm.findAll", query = "SELECT p FROM PengisianBbm p"),
    @NamedQuery(name = "PengisianBbm.findById", query = "SELECT p FROM PengisianBbm p WHERE p.id = :id"),
    @NamedQuery(name = "PengisianBbm.findByMeterAwal", query = "SELECT p FROM PengisianBbm p WHERE p.meterAwal = :meterAwal"),
    @NamedQuery(name = "PengisianBbm.findByMeterAkhir", query = "SELECT p FROM PengisianBbm p WHERE p.meterAkhir = :meterAkhir"),
    @NamedQuery(name = "PengisianBbm.findByJenisBbm", query = "SELECT p FROM PengisianBbm p WHERE p.jenisBbm = :jenisBbm"),
    @NamedQuery(name = "PengisianBbm.findByTanggalPengisian", query = "SELECT p FROM PengisianBbm p WHERE p.tanggalPengisian = :tanggalPengisian")})
public class PengisianBbm implements Serializable, EntityAuditable {
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
    @Column(name = "meter_awal")
    private Short meterAwal;
    @Column(name = "meter_akhir")
    private Short meterAkhir;
    @Column(name = "jenis_bbm", length = 50)
    private String jenisBbm;
    @Column(name = "tanggal_pengisian")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tanggalPengisian;
    @JoinColumn(name = "equip_code", referencedColumnName = "equip_code")
    @ManyToOne
    private MasterEquipment masterEquipment;

    public PengisianBbm() {
    }

    public PengisianBbm(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Short getMeterAwal() {
        return meterAwal;
    }

    public void setMeterAwal(Short meterAwal) {
        this.meterAwal = meterAwal;
    }

    public Short getMeterAkhir() {
        return meterAkhir;
    }

    public void setMeterAkhir(Short meterAkhir) {
        this.meterAkhir = meterAkhir;
    }

    public String getJenisBbm() {
        return jenisBbm;
    }

    public void setJenisBbm(String jenisBbm) {
        this.jenisBbm = jenisBbm;
    }

    public Date getTanggalPengisian() {
        return tanggalPengisian;
    }

    public void setTanggalPengisian(Date tanggalPengisian) {
        this.tanggalPengisian = tanggalPengisian;
    }

    public MasterEquipment getMasterEquipment() {
        return masterEquipment;
    }

    public void setMasterEquipment(MasterEquipment masterEquipment) {
        this.masterEquipment = masterEquipment;
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
        if (!(object instanceof PengisianBbm)) {
            return false;
        }
        PengisianBbm other = (PengisianBbm) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.PengisianBbm[id=" + id + "]";
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
