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
@Table(name = "uc_delivery_service")
@NamedQueries({
    @NamedQuery(name = "UcDeliveryService.findAll", query = "SELECT u FROM UcDeliveryService u"),
    @NamedQuery(name = "UcDeliveryService.findByJobslip", query = "SELECT u FROM UcDeliveryService u WHERE u.jobslip = :jobslip"),
    @NamedQuery(name = "UcDeliveryService.findByIdUc", query = "SELECT u FROM UcDeliveryService u WHERE u.uncontainerizedService.idUc = :idUc"),
    @NamedQuery(name = "UcDeliveryService.findByNoReg", query = "SELECT u FROM UcDeliveryService u WHERE u.noReg = :noReg"),
    @NamedQuery(name = "UcDeliveryService.deleteByNoReg", query = "DELETE FROM UcDeliveryService u WHERE u.noReg = :noReg"),
    @NamedQuery(name = "UcDeliveryService.findByValidDate", query = "SELECT u FROM UcDeliveryService u WHERE u.validDate = :validDate"),
    @NamedQuery(name = "UcDeliveryService.findByDeliveryDate", query = "SELECT u FROM UcDeliveryService u WHERE u.deliveryDate = :deliveryDate"),
    @NamedQuery(name = "UcDeliveryService.findByIsDelivery", query = "SELECT u FROM UcDeliveryService u WHERE u.isDelivery = :isDelivery"),
    @NamedQuery(name = "UcDeliveryService.findByMasa1", query = "SELECT u FROM UcDeliveryService u WHERE u.masa1 = :masa1"),
    @NamedQuery(name = "UcDeliveryService.findByMasa2", query = "SELECT u FROM UcDeliveryService u WHERE u.masa2 = :masa2")})
@NamedNativeQueries({
    @NamedNativeQuery(name = "UcDeliveryService.Native.findByPpkbNReg", query = "SELECT ds.jobslip, us.bl_no, us.weight, c.name, change(us.truck_loosing), p.name, pl.name FROM uc_delivery_service ds, uncontainerized_service us, m_port p, m_port pl, m_commodity c WHERE ds.id_uc = us.id_uc AND p.port_code = us.disch_port AND pl.port_code = us.load_port AND c.commodity_code = us.commodity AND ds.no_reg = ?"),
    @NamedNativeQuery(name = "UcDeliveryService.Native.findByUcDeliveryServiceJobSlip", query = "SELECT i.jobslip,pu.bl_no, m.name, pu.weight, change(pu.truck_loosing),i.valid_date from uncontainerized_service pu,m_commodity m,uc_delivery_service i where pu.commodity=m.commodity_code AND pu.operation = 'DISCHARGE' AND pu.id_uc=i.id_uc AND i.no_reg=?"),
    @NamedNativeQuery(name = "UcDeliveryService.Native.generateId", query = "SELECT MAX(substring(jobslip,7,5))FROM uc_delivery_service WHERE substring(jobslip,3,4) = ?"),
    @NamedNativeQuery(name = "UcDeliveryService.Native.perhitungan", query = "SELECT ds.jobslip, us.bl_no, us.weight, m.name, change(us.truck_loosing), us.placement_date, pp.masa_1, pp.masa_2, nvl(upl.charge), nvl(pp.charge_masa1), nvl(pp.charge_masa2), nvl(ups.charge), nvl(ppg.total_charge), nvl(pp.charge), us.cubication, nvl(pub.charge) "
                                                                            + "FROM ((((((((uc_delivery_service ds "
                                                                                    + "LEFT JOIN uc_perhitungan_stevedoring ups ON (ups.jobslip = ds.jobslip)) "
                                                                                    + "LEFT JOIN uc_perhitungan_penumpukan pp ON (pp.jobslip = ds.jobslip)) "
                                                                                    + "LEFT JOIN uc_perhitungan_lift upl ON (upl.jobslip = ds.jobslip)) "
                                                                                    + "LEFT JOIN perhitungan_pass_gate ppg ON (ppg.job_slip = ds.jobslip)) "
                                                                                    + "LEFT JOIN perhitungan_upah_buruh pub ON (pub.jobslip = ds.jobslip)) "
                                                                                    + "JOIN uncontainerized_service us ON (ds.id_uc = us.id_uc)) "
                                                                                    + "JOIN m_commodity m ON (us.commodity = m.commodity_code)) "
                                                                                    + "JOIN registration r ON (ds.no_reg = r.no_reg)) "
                                                                            + "WHERE ds.no_reg = ?"),
    @NamedNativeQuery(name = "UcDeliveryService.Native.findByUcDeliveryServiceAutoComplete", query = "SELECT d.jobslip "
                                                                                                    + "FROM uc_delivery_service d "
                                                                                                            + "LEFT JOIN uc_gatedelivery_service s ON d.jobslip=s.jobslip "
                                                                                                    + "WHERE s.jobslip IS NULL AND d.jobslip LIKE ('%'|| ? ||'%') AND d.counter_print > 0 "
                                                                                                    + "ORDER BY substring(d.jobslip,7,5) DESC "
                                                                                                    + "LIMIT 10"),
    @NamedNativeQuery(name = "UcDeliveryService.Native.findDischargeRecapUc", query = "SELECT ds.jobslip, us.bl_no, us.weight, m.name, change(us.truck_loosing), us.placement_date, ds.masa1, ds.masa2, pls.charge, pp.charge_masa1, pp.charge_masa2, phd.charge, phd.jasa_dermaga, pp.charge, c.symbol, c.language, c.country FROM (((uc_delivery_service ds LEFT JOIN uc_perhitungan_stevedoring phd ON phd.jobslip = ds.jobslip AND phd.no_reg = ds.no_reg) LEFT JOIN uc_perhitungan_penumpukan pp ON pp.jobslip = ds.jobslip AND pp.no_reg = ds.no_reg) LEFT JOIN uc_perhitungan_lift pls ON pls.jobslip = ds.jobslip AND pls.no_reg = ds.no_reg), uncontainerized_service us, m_currency c, m_commodity m WHERE ds.id_uc = us.id_uc AND us.commodity = m.commodity_code AND phd.curr_code = c.curr_code AND us.no_ppkb = ?"),
    @NamedNativeQuery(name = "UcDeliveryService.Native.findByUcDeliveryServiceGateInDeliveryClosingTime", query = "SELECT i.jobslip,pu.bl_no, m.name, pu.weight, change(pu.truck_loosing),i.valid_date from uncontainerized_service pu,m_commodity m,uc_delivery_service i where pu.commodity=m.commodity_code AND pu.id_uc=i.id_uc AND i.jobslip=? AND i.valid_date>=now() AND i.jobslip NOT IN (select jobslip from uc_gatedelivery_service)"),
    @NamedNativeQuery(name = "UcDeliveryService.Native.findByUcDeliveryServiceGateInDeliveryClosingTimeTruckYes", query = "SELECT i.jobslip,pu.bl_no, m.name, pu.weight, change(pu.truck_loosing),i.valid_date from uncontainerized_service pu,m_commodity m,uc_delivery_service i where pu.commodity=m.commodity_code AND pu.id_uc=i.id_uc AND i.jobslip=? AND i.jobslip NOT IN (select jobslip from uc_gatedelivery_service)"),
    //@NamedNativeQuery(name = "UcDeliveryService.Native.findByUcDeliveryServiceGateInDeliveryClosingTime", query = "SELECT i.jobslip,pu.bl_no, m.name, pu.weight, change(pu.truck_loosing),i.valid_date from uncontainerized_service pu,m_commodity m,uc_delivery_service i where pu.commodity=m.commodity_code AND pu.id_uc=i.id_uc AND i.jobslip=? AND i.valid_date>=now() AND i.jobslip NOT IN (select jobslip from uc_gatedelivery_service)"),
    @NamedNativeQuery(name = "UcDeliveryService.Native.findByPpkbSusulan", query = "SELECT ds.jobslip, us.bl_no, us.unit, us.weight, c.name, us.placement_date FROM uc_delivery_service ds, uncontainerized_service us, m_commodity c WHERE us.no_ppkb = ? AND ds.id_uc = us.id_uc AND us.truck_loosing = false AND c.commodity_code = us.commodity AND us.id_uc NOT IN (SELECT id_uc FROM uc_penumpukan_susulan_service WHERE no_ppkb = us.no_ppkb)"),
    @NamedNativeQuery(name = "UcDeliveryService.Native.findByPpkbNIdUC", query = "SELECT ds.jobslip FROM uc_delivery_service ds, uncontainerized_service u WHERE u.id_uc = ds.id_uc AND ds.id_uc = ? AND u.no_ppkb = ?"),
    @NamedNativeQuery(name = "UcDeliveryService.Native.findByPpkbSusulanByBL", query = "SELECT ds.jobslip, us.bl_no, us.unit, us.weight, c.name, us.placement_date FROM uc_delivery_service ds, uncontainerized_service us, m_commodity c WHERE us.bl_no = ? AND us.no_ppkb = ? AND ds.id_uc = us.id_uc AND us.truck_loosing = false AND c.commodity_code = us.commodity AND us.bl_no NOT IN (SELECT bl_no FROM uc_penumpukan_susulan_service WHERE no_ppkb = us.no_ppkb)"),
    @NamedNativeQuery(name = "UcDeliveryService.Native.findByNoregValidasiPrint", query = "select ds.jobslip from uc_delivery_service ds where ds.counter_print >=1 AND ds.no_reg=?")})
public class UcDeliveryService implements Serializable, EntityAuditable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @Basic(optional = false)
    @Column(name = "jobslip", nullable = false, length = 30)
    private String jobslip;
    @Column(name = "no_reg", length = 30)
    private String noReg;
    @Column(name = "valid_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date validDate;
    @Column(name = "delivery_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deliveryDate;
    @Column(name = "is_delivery")
    private Boolean isDelivery;
    @Column(name = "masa1")
    private Integer masa1;
    @Column(name = "masa2")
    private Integer masa2;
    @Column(name = "counter_print")
    private Integer counterPrint=0;
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

    public UcDeliveryService() {
    }

    public UcDeliveryService(String jobslip) {
        this.jobslip = jobslip;
    }

    public String getJobslip() {
        return jobslip;
    }

    public void setJobslip(String jobslip) {
        this.jobslip = jobslip;
    }

    public String getNoReg() {
        return noReg;
    }

    public void setNoReg(String noReg) {
        this.noReg = noReg;
    }

    public Date getValidDate() {
        return validDate;
    }

    public void setValidDate(Date validDate) {
        this.validDate = validDate;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Boolean getIsDelivery() {
        return isDelivery;
    }

    public void setIsDelivery(Boolean isDelivery) {
        this.isDelivery = isDelivery;
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
        if (!(object instanceof UcDeliveryService)) {
            return false;
        }
        UcDeliveryService other = (UcDeliveryService) object;
        if ((this.jobslip == null && other.jobslip != null) || (this.jobslip != null && !this.jobslip.equals(other.jobslip))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.UcDeliveryService[jobslip=" + jobslip + "]";
    }
}
