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
 * @author dycoder
 */
@Entity
@EntityListeners({AuditTrailEntityListener.class})
@Table(name = "uc_penumpukan_susulan_service")
@NamedQueries({
    @NamedQuery(name = "UcPenumpukanSusulanService.findAll", query = "SELECT u FROM UcPenumpukanSusulanService u"),
    @NamedQuery(name = "UcPenumpukanSusulanService.findByJobSlip", query = "SELECT u FROM UcPenumpukanSusulanService u WHERE u.jobSlip = :jobSlip"),
    @NamedQuery(name = "UcPenumpukanSusulanService.findByNoReg", query = "SELECT u FROM UcPenumpukanSusulanService u WHERE u.noReg = :noReg"),
    @NamedQuery(name = "UcPenumpukanSusulanService.findByNoPpkb", query = "SELECT u FROM UcPenumpukanSusulanService u WHERE u.noPpkb = :noPpkb"),
    @NamedQuery(name = "UcPenumpukanSusulanService.findByIdUc", query = "SELECT u FROM UcPenumpukanSusulanService u WHERE u.idUc = :idUc"),
    @NamedQuery(name = "UcPenumpukanSusulanService.findByMasa1", query = "SELECT u FROM UcPenumpukanSusulanService u WHERE u.masa1 = :masa1"),
    @NamedQuery(name = "UcPenumpukanSusulanService.findByMasa2", query = "SELECT u FROM UcPenumpukanSusulanService u WHERE u.masa2 = :masa2")})
@NamedNativeQueries({
    @NamedNativeQuery(name = "UcPenumpukanSusulanService.Native.findByPpkbNReg", query = "SELECT ds.job_slip, us.bl_no, us.unit, us.weight, c.name, us.id_uc FROM uc_penumpukan_susulan_service ds, uncontainerized_service us, m_commodity c WHERE ds.id_uc = us.id_uc AND c.commodity_code = us.commodity AND ds.no_ppkb = ? AND ds.no_reg = ?"),
    @NamedNativeQuery(name = "UcPenumpukanSusulanService.Native.generateId", query = "SELECT MAX(substring(job_slip,7,5))FROM uc_penumpukan_susulan_service WHERE substring(job_slip,3,4) = ?"),
    @NamedNativeQuery(name = "UcPenumpukanSusulanService.Native.findInvoice", query = "SELECT ds.job_slip FROM uc_penumpukan_susulan_service ds WHERE ds.id_uc = ? AND ds.no_ppkb = ? AND ds.no_reg = ?"),
    @NamedNativeQuery(name = "UcPenumpukanSusulanService.Native.perhitungan", query = "SELECT ds.job_slip, us.bl_no, us.weight, us.unit, co.name, ds.placement_date, ds.masa_1, ds.masa_2, pp.charge_m1, pp.charge_m2, pp.total_charge,c.symbol,c.language,c.country, us.id_uc FROM uc_penumpukan_susulan_service ds, perhitungan_penumpukan_susulan pp, m_currency c, uncontainerized_service us, m_commodity co WHERE co.commodity_code = us.commodity AND ds.id_uc = us.id_uc AND pp.bl_no = us.bl_no AND pp.curr_code=c.curr_code AND pp.no_reg = ds.no_reg AND ds.no_reg = ?"),
    @NamedNativeQuery(name = "UcPenumpukanSusulanService.Native.findByReg", query = "SELECT ds.job_slip FROM uc_penumpukan_susulan_service ds WHERE ds.no_reg = ?")
})
public class UcPenumpukanSusulanService implements Serializable, EntityAuditable {
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
    @Column(name = "placement_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date placementDate;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "job_slip", nullable = false, length = 30)
    private String jobSlip;
    @Column(name = "no_reg", length = 30)
    private String noReg;
    @Column(name = "no_ppkb", length = 30)
    private String noPpkb;
    @Column(name = "id_uc")
    private Integer idUc;
    @Column(name = "masa_1")
    private Short masa1;
    @Column(name = "masa_2")
    private Short masa2;

    public UcPenumpukanSusulanService() {
    }

    public UcPenumpukanSusulanService(String jobSlip) {
        this.jobSlip = jobSlip;
    }

    public String getJobSlip() {
        return jobSlip;
    }

    public void setJobSlip(String jobSlip) {
        this.jobSlip = jobSlip;
    }

    public String getNoReg() {
        return noReg;
    }

    public void setNoReg(String noReg) {
        this.noReg = noReg;
    }

    public String getNoPpkb() {
        return noPpkb;
    }

    public void setNoPpkb(String noPpkb) {
        this.noPpkb = noPpkb;
    }

    public Integer getIdUc() {
        return idUc;
    }

    public void setIdUc(Integer idUc) {
        this.idUc = idUc;
    }

    public Short getMasa1() {
        return masa1;
    }

    public void setMasa1(Short masa1) {
        this.masa1 = masa1;
    }

    public Short getMasa2() {
        return masa2;
    }

    public void setMasa2(Short masa2) {
        this.masa2 = masa2;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (jobSlip != null ? jobSlip.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UcPenumpukanSusulanService)) {
            return false;
        }
        UcPenumpukanSusulanService other = (UcPenumpukanSusulanService) object;
        if ((this.jobSlip == null && other.jobSlip != null) || (this.jobSlip != null && !this.jobSlip.equals(other.jobSlip))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.UcPenumpukanSusulanService[jobSlip=" + jobSlip + "]";
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

    public Date getPlacementDate() {
        return placementDate;
    }

    public void setPlacementDate(Date placementDate) {
        this.placementDate = placementDate;
    }

}
