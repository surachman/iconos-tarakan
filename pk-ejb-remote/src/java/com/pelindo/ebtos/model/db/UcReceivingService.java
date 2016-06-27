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
import javax.persistence.CascadeType;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author postgres
 */
@Entity
@EntityListeners({AuditTrailEntityListener.class})
@Table(name = "uc_receiving_service")
@NamedQueries({
    @NamedQuery(name = "UcReceivingService.findAll", query = "SELECT u FROM UcReceivingService u"),
    @NamedQuery(name = "UcReceivingService.findByJobslip", query = "SELECT u FROM UcReceivingService u WHERE u.jobslip = :jobslip"),
    @NamedQuery(name = "UcReceivingService.findByIdUc", query = "SELECT u FROM UcReceivingService u WHERE u.uncontainerizedService.idUc = :idUc"),
    @NamedQuery(name = "UcReceivingService.findByNoReg", query = "SELECT u FROM UcReceivingService u WHERE u.registration.noReg = :noReg"),
    @NamedQuery(name = "UcReceivingService.deleteByNoReg", query = "DELETE FROM UcReceivingService u WHERE u.registration.noReg = :noReg"),
    @NamedQuery(name = "UcReceivingService.findByValidDate", query = "SELECT u FROM UcReceivingService u WHERE u.validDate = :validDate"),
    @NamedQuery(name = "UcReceivingService.findByReceivingDate", query = "SELECT u FROM UcReceivingService u WHERE u.receivingDate = :receivingDate"),
    @NamedQuery(name = "UcReceivingService.findByNoPpkbAndTruckLoosingStatus", query = "SELECT u FROM UcReceivingService u WHERE u.uncontainerizedService.noPpkb = :noPpkb AND u.uncontainerizedService.truckLoosing = :truckLoosing"),
    @NamedQuery(name = "UcReceivingService.updateMasaByNoPpkbAndTruckLoosingStatus", query = "UPDATE UcReceivingService u SET u.masa1 = :masa1, u.masa2 = :masa2 WHERE u.uncontainerizedService.noPpkb = :noPpkb AND u.uncontainerizedService.truckLoosing = :truckLoosing")})
@NamedNativeQueries({
    @NamedNativeQuery(name = "UcReceivingService.Native.findByUcReceivingPerhitungan", query = "SELECT urs.jobslip, us.bl_no, us.weight, m.name, change(us.truck_loosing), us.cubication, nvl(pp.masa_1), nvl(pp.masa_2), nvl(upl.charge), nvl(pp.charge_masa1), nvl(pp.charge_masa2), nvl(ups.charge), nvl(ppg.total_charge), nvl(pp.charge), CASE WHEN ugs.jobslip IS NULL THEN FALSE ELSE TRUE END, nvl(pub.charge) "
                                                                                                + "FROM ((((((((uc_receiving_service urs "
                                                                                                    + "LEFT JOIN uc_perhitungan_stevedoring ups ON (ups.jobslip = urs.jobslip)) "
                                                                                                    + "LEFT JOIN uc_perhitungan_penumpukan pp ON (pp.jobslip = urs.jobslip)) "
                                                                                                    + "LEFT JOIN uc_perhitungan_lift upl ON (upl.jobslip = urs.jobslip)) "
                                                                                                    + "LEFT JOIN perhitungan_pass_gate ppg ON (ppg.job_slip = urs.jobslip)) "
                                                                                                    + "LEFT JOIN uc_gatereceiving_service ugs ON (ugs.jobslip = urs.jobslip)) "
                                                                                                    + "LEFT JOIN perhitungan_upah_buruh pub ON (pub.jobslip = urs.jobslip)) "
                                                                                                    + "JOIN uncontainerized_service us ON (urs.id_uc = us.id_uc)) "
                                                                                                    + "JOIN m_commodity m ON (us.commodity = m.commodity_code)) "
                                                                                                    + "JOIN registration r ON (urs.no_reg = r.no_reg) "
                                                                                                + "WHERE urs.no_reg = ?"),
    @NamedNativeQuery(name = "UcReceivingService.Native.findByUcReceivingJobSlip", query = "SELECT i.jobslip,pu.bl_no, m.name, pu.weight, change(pu.truck_loosing),i.valid_date from uncontainerized_service pu,m_commodity m,uc_receiving_service i where pu.commodity=m.commodity_code AND pu.operation = 'LOAD' AND pu.id_uc=i.id_uc AND i.no_reg=?"),
    @NamedNativeQuery(name = "UcReceivingService.Native.findByUcReceivingServices", query = "SELECT u.jobslip, pu.bl_no, m.name, pu.weight, change(pu.truck_loosing), pu.id_uc, pu.unit,pu.weight,pu.status from uc_receiving_service u,uncontainerized_service pu,m_commodity m where pu.commodity=m.commodity_code AND u.id_uc=pu.id_uc AND u.no_reg=?"),
    @NamedNativeQuery(name = "UcReceivingService.Native.generateId", query = "SELECT MAX(substring(jobslip,7,5))FROM uc_receiving_service WHERE substring(jobslip,3,4) = ?"),
    @NamedNativeQuery(name = "UcReceivingService.Native.findGateInPassableJobSlips", query = "SELECT d.jobslip "
                                                                                                        + "FROM planning_vessel p "
                                                                                                                + "JOIN uncontainerized_service pu on (p.no_ppkb=pu.no_ppkb) "
                                                                                                                + "JOIN uc_receiving_service d ON (pu.id_uc=d.id_uc) "
                                                                                                                + "LEFT JOIN uc_gatereceiving_service s ON d.jobslip=s.jobslip "
                                                                                                        + "WHERE d.jobslip LIKE ('%'|| ? ||'%') AND s.jobslip IS NULL AND d.counter_print > 0 AND p.close_uc >= now() "
                                                                                                        + "ORDER BY substring(d.jobslip,7,5) DESC "
                                                                                                        + "LIMIT 10"),
    @NamedNativeQuery(name = "UcReceivingService.Native.findLoadRecapUc", query = "SELECT ds.jobslip, us.bl_no, us.weight, m.name, change(us.truck_loosing), us.placement_date, ds.masa1, ds.masa2, pls.charge, nvl(pp.charge_masa1), nvl(pp.charge_masa2), nvl(phd.charge), nvl(phd.jasa_dermaga), nvl(pp.charge), c.symbol, c.language, c.country FROM (((uc_receiving_service ds LEFT JOIN uc_perhitungan_stevedoring phd ON phd.jobslip = ds.jobslip AND phd.no_reg = ds.no_reg) LEFT JOIN uc_perhitungan_penumpukan pp ON pp.jobslip = ds.jobslip AND pp.no_reg = ds.no_reg) LEFT JOIN uc_perhitungan_lift pls ON pls.jobslip = ds.jobslip AND pls.no_reg = ds.no_reg), uncontainerized_service us, m_currency c, m_commodity m WHERE ds.id_uc = us.id_uc AND us.commodity = m.commodity_code AND phd.curr_code = c.curr_code AND us.no_ppkb = ?"),
    @NamedNativeQuery(name = "UcReceivingService.Native.findGateInPassableByJobSlip", query = "SELECT i.jobslip,pu.bl_no, m.name, pu.weight, change(pu.truck_loosing),i.valid_date "
                                                                                                                    + "FROM planning_vessel p "
                                                                                                                            + "JOIN uncontainerized_service pu on (p.no_ppkb=pu.no_ppkb) "
                                                                                                                                    + "JOIN m_commodity m ON (pu.commodity=m.commodity_code) "
                                                                                                                                    + "JOIN uc_receiving_service i ON (pu.id_uc=i.id_uc) "
                                                                                                                                    + "LEFT JOIN uc_gatereceiving_service ugs ON (ugs.jobslip=i.jobslip) "
                                                                                                                    + "WHERE i.jobslip = ? AND ugs.jobslip IS NULL AND i.counter_print > 0 AND p.close_uc >= now();"),
    @NamedNativeQuery(name = "UcReceivingService.Native.findJobslipByIdUC", query = "SELECT jobslip FROM uc_receiving_service WHERE id_uc=?"),
    @NamedNativeQuery(name = "UcReceivingService.Native.findNoRegJobslipByIdUC", query = "SELECT no_reg, jobslip FROM uc_receiving_service WHERE id_uc=?"),
    @NamedNativeQuery(name = "UcReceivingService.Native.findByPpkb", query = "SELECT u.jobslip, u.no_reg, pu.bl_no from uc_receiving_service u, uncontainerized_service pu where u.id_uc = pu.id_uc AND pu.no_ppkb = ?"),
    @NamedNativeQuery(name = "UcReceivingService.Native.findByPpkbTLfalse", query = "SELECT u.jobslip, u.no_reg, pu.bl_no from uc_receiving_service u, uncontainerized_service pu where u.id_uc = pu.id_uc AND pu.truck_loosing = false AND pu.no_ppkb = ?"),
    @NamedNativeQuery(name = "UcReceivingService.Native.findJobslipByIdUCMobile", query = "SELECT jobslip FROM uc_receiving_service WHERE id_uc=?"),
    @NamedNativeQuery(name = "UcReceivingService.Native.findCancelInvoice", query = "select d.jobslip,d.no_reg from uc_receiving_service d where d.no_reg=?"),
    @NamedNativeQuery(name = "UcReceivingService.Native.findByNoregValidasiPrint", query = "select ds.jobslip from uc_receiving_service ds where ds.counter_print >=1 AND ds.no_reg=?")})
    //
    public class UcReceivingService implements Serializable, EntityAuditable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "jobslip", nullable = false, length = 30)
    private String jobslip;
    @Column(name = "valid_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date validDate;
    @Column(name = "receiving_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date receivingDate;
    @Column(name = "masa1")
    private Integer masa1;
    @Column(name = "masa2")
    private Integer masa2;
    @Column(name = "end_storage_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date endStorageDate;
    @Column(name = "counter_print")
    private Integer counterPrint=0;
    @JoinColumn(name = "no_reg", referencedColumnName = "no_reg")
    @ManyToOne
    private Registration registration;
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
    @JoinColumn(name = "id_uc", referencedColumnName = "id_uc")
    @OneToOne
    private UncontainerizedService uncontainerizedService;

    public UcReceivingService() {
        
    }

    public UcReceivingService(String jobslip) {
        this.jobslip = jobslip;
    }

    public String getJobslip() {
        return jobslip;
    }

    public void setJobslip(String jobslip) {
        this.jobslip = jobslip;
    }

    public Date getValidDate() {
        return validDate;
    }

    public void setValidDate(Date validDate) {
        this.validDate = validDate;
    }

    public Date getReceivingDate() {
        return receivingDate;
    }

    public void setReceivingDate(Date receivingDate) {
        this.receivingDate = receivingDate;
    }

    public Integer getMasa1() {
        return masa1;
    }

    public void setMasa1(Integer masa1) {
        this.masa1 = masa1;
    }

    public Integer getMasa2() {
        return masa2;
    }

    public void setMasa2(Integer masa2) {
        this.masa2 = masa2;
    }

    public Date getEndStorageDate() {
        return endStorageDate;
    }

    public void setEndStorageDate(Date endStorageDate) {
        this.endStorageDate = endStorageDate;
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

    public Integer getCounterPrint() {
        return counterPrint;
    }

    public void setCounterPrint(Integer counterPrint) {
        this.counterPrint = counterPrint;
    }

    public Registration getRegistration() {
        return registration;
    }

    public void setRegistration(Registration registration) {
        this.registration = registration;
    }

    public UncontainerizedService getUncontainerizedService() {
        return uncontainerizedService;
    }

    public void setUncontainerizedService(UncontainerizedService uncontainerizedService) {
        this.uncontainerizedService = uncontainerizedService;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (jobslip != null ? jobslip.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UcReceivingService)) {
            return false;
        }
        UcReceivingService other = (UcReceivingService) object;
        if ((this.jobslip == null && other.jobslip != null) || (this.jobslip != null && !this.jobslip.equals(other.jobslip))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.UcReceivingService[jobslip=" + jobslip + "]";
    }
}
