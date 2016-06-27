/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.model.db;

import com.pelindo.ebtos.model.db.master.MasterActivity;
import com.qtasnim.persistence.AuditTrailEntityListener;
import com.qtasnim.persistence.EntityAuditable;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 *
 * @author dycoder
 */
@Entity
@EntityListeners({AuditTrailEntityListener.class})
@Table(name = "perhitungan_behandle")
@NamedQueries({
    @NamedQuery(name = "PerhitunganBehandle.findAll", query = "SELECT p FROM PerhitunganBehandle p"),
    @NamedQuery(name = "PerhitunganBehandle.findById", query = "SELECT p FROM PerhitunganBehandle p WHERE p.id = :id"),
    @NamedQuery(name = "PerhitunganBehandle.findByNoReg", query = "SELECT p FROM PerhitunganBehandle p WHERE p.behandleService.registration.noReg = :noReg"),
    @NamedQuery(name = "PerhitunganBehandle.deleteByNoReg", query = "DELETE FROM PerhitunganBehandle p WHERE p.behandleService.registration.noReg = :noReg")})
@NamedNativeQueries({
    @NamedNativeQuery(name = "PerhitunganBehandle.Native.findInvoice", query = "SELECT id FROM perhitungan_behandle WHERE cont_no = ? AND no_ppkb = ? AND no_reg = ?"),
    @NamedNativeQuery(name = "PerhitunganBehandle.Native.findByReg", query = "SELECT id FROM perhitungan_behandle WHERE no_reg = ?")})
public class PerhitunganBehandle implements Serializable, EntityAuditable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    @Basic(optional = false)
    @Column(name = "charge", nullable = false, precision = 19, scale = 2)
    private BigDecimal charge = BigDecimal.ZERO;
    @Basic(optional = false)
    @Column(name = "total_charge", nullable = false, precision = 19, scale = 2)
    private BigDecimal totalCharge = BigDecimal.ZERO;
    @JoinColumn(name = "activity_code", referencedColumnName = "activity_code", nullable = false)
    @ManyToOne(optional = false)
    private MasterActivity masterActivity;
    @JoinColumn(name = "job_slip", referencedColumnName = "job_slip", nullable = false)
    @OneToOne(optional = false, cascade=CascadeType.ALL, orphanRemoval=true)
    private BehandleService behandleService;
    @JoinColumn(name = "perhitungan_supervisi_id", referencedColumnName = "id")
    @OneToOne(cascade=CascadeType.ALL, orphanRemoval=true)
    private PerhitunganSupervisi perhitunganSupervisi;
    @JoinColumn(name = "perhitungan_upah_buruh_id", referencedColumnName = "id")
    @OneToOne(cascade=CascadeType.ALL, orphanRemoval=true)
    private PerhitunganUpahBuruh perhitunganUpahBuruh;
    @JoinColumn(name = "perhitungan_sewa_alat_id", referencedColumnName = "id")
    @OneToOne(cascade=CascadeType.ALL, orphanRemoval=true)
    private PerhitunganSewaAlat perhitunganSewaAlat;
    @Basic(optional = false)
    @NotNull
    @Column(name = "created_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Column(name = "modified_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;
    @Basic(optional = false)
    @Column(name = "created_by", nullable = false, length = 256)
    private String createdBy;
    @Column(name = "modified_by", length = 256)
    private String modifiedBy;

    public PerhitunganBehandle() {
    }

    public PerhitunganBehandle(BehandleService behandleService) {
        this.behandleService = behandleService;
    }

    public PerhitunganBehandle(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getCharge() {
        return charge;
    }

    public void setCharge(BigDecimal charge) {
        this.charge = charge;
    }

    public BigDecimal getTotalCharge() {
        return totalCharge;
    }

    public void setTotalCharge(BigDecimal totalCharge) {
        this.totalCharge = totalCharge;
    }

    public MasterActivity getMasterActivity() {
        return masterActivity;
    }

    public void setMasterActivity(MasterActivity masterActivity) {
        this.masterActivity = masterActivity;
    }

    public BehandleService getBehandleService() {
        return behandleService;
    }

    public void setBehandleService(BehandleService behandleService) {
        this.behandleService = behandleService;
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

    public PerhitunganSupervisi getPerhitunganSupervisi() {
        return perhitunganSupervisi;
    }

    public void setPerhitunganSupervisi(PerhitunganSupervisi perhitunganSupervisi) {
        this.perhitunganSupervisi = perhitunganSupervisi;
    }

    public PerhitunganUpahBuruh getPerhitunganUpahBuruh() {
        return perhitunganUpahBuruh;
    }

    public void setPerhitunganUpahBuruh(PerhitunganUpahBuruh perhitunganUpahBuruh) {
        this.perhitunganUpahBuruh = perhitunganUpahBuruh;
    }

    public PerhitunganSewaAlat getPerhitunganSewaAlat() {
        return perhitunganSewaAlat;
    }

    public void setPerhitunganSewaAlat(PerhitunganSewaAlat perhitunganSewaAlat) {
        this.perhitunganSewaAlat = perhitunganSewaAlat;
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
        if (!(object instanceof PerhitunganBehandle)) {
            return false;
        }
        PerhitunganBehandle other = (PerhitunganBehandle) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.PerhitunganBehandle[id=" + id + "]";
    }
}
