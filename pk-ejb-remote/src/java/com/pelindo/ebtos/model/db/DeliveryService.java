/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.model.db;

import com.qtasnim.persistence.AuditTrailEntityListener;
import com.qtasnim.persistence.EntityAuditable;
import com.pelindo.ebtos.model.db.master.MasterCommodity;
import com.pelindo.ebtos.model.db.master.MasterContainerType;
import com.pelindo.ebtos.model.db.master.MasterCustomer;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Cacheable;
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
@Table(name = "delivery_service")
@NamedQueries({
    @NamedQuery(name = "DeliveryService.findAll", query = "SELECT d FROM DeliveryService d"),
    @NamedQuery(name = "DeliveryService.findByJobSlip", query = "SELECT d FROM DeliveryService d WHERE d.jobSlip = :jobSlip"),
    @NamedQuery(name = "DeliveryService.findByNoInvoice", query = "SELECT d FROM DeliveryService d WHERE d.noInvoice = :noInvoice"),
    @NamedQuery(name = "DeliveryService.findByContNo", query = "SELECT d FROM DeliveryService d WHERE d.contNo = :contNo"),
    @NamedQuery(name = "DeliveryService.findByNoPpkbAndContNo", query = "SELECT d FROM DeliveryService d WHERE d.contNo = :contNo AND d.planningVessel.noPpkb = :noPpkb"),
    @NamedQuery(name = "DeliveryService.findByContSize", query = "SELECT d FROM DeliveryService d WHERE d.contSize = :contSize"),
    @NamedQuery(name = "DeliveryService.findByContStatus", query = "SELECT d FROM DeliveryService d WHERE d.contStatus = :contStatus"),
    @NamedQuery(name = "DeliveryService.findByContGross", query = "SELECT d FROM DeliveryService d WHERE d.contGross = :contGross"),
    @NamedQuery(name = "DeliveryService.findByOverSize", query = "SELECT d FROM DeliveryService d WHERE d.overSize = :overSize"),
    @NamedQuery(name = "DeliveryService.findByDg", query = "SELECT d FROM DeliveryService d WHERE d.dg = :dg"),
    @NamedQuery(name = "DeliveryService.deleteByPpkbAndReg", query = "DELETE FROM DeliveryService d WHERE d.planningVessel.noPpkb = :noPpkb AND d.registration.noReg = :noReg"),
    @NamedQuery(name = "DeliveryService.findByContNoPpkbAndReg", query = "SELECT d FROM DeliveryService d WHERE d.contNo = :contNo AND d.planningVessel.noPpkb = :noPpkb AND d.registration.noReg = :noReg"),
    @NamedQuery(name = "DeliveryService.findByDgLabel", query = "SELECT d FROM DeliveryService d WHERE d.dgLabel = :dgLabel"),
    @NamedQuery(name = "DeliveryService.findByPlacementDate", query = "SELECT d FROM DeliveryService d WHERE d.placementDate = :placementDate"),
    @NamedQuery(name = "DeliveryService.findByMasa1", query = "SELECT d FROM DeliveryService d WHERE d.masa1 = :masa1"),
    @NamedQuery(name = "DeliveryService.findByMasa2", query = "SELECT d FROM DeliveryService d WHERE d.masa2 = :masa2"),
    @NamedQuery(name = "DeliveryService.findByValidDate", query = "SELECT d FROM DeliveryService d WHERE d.validDate = :validDate")})
@NamedNativeQueries({
    @NamedNativeQuery(name = "DeliveryService.Native.findByPpkbNReg", query = "SELECT ds.job_slip, c.name, ds.cont_no, ds.cont_size, ct.name, ds.cont_status, ds.cont_gross, CASE WHEN ds.over_size=true THEN 'Yes' WHEN ds.over_size=false THEN 'No' END as over_size, ds.placement_date FROM delivery_service ds, m_commodity c, m_container_type ct WHERE ds.commodity_code = c.commodity_code AND ds.cont_type = ct.cont_type AND ds.no_ppkb = ? AND ds.no_reg = ?"),
    @NamedNativeQuery(name = "DeliveryService.Native.generateId", query = "SELECT MAX(substring(job_slip,7,5))FROM delivery_service WHERE substring(job_slip,3,4) = ?"),
    @NamedNativeQuery(name = "DeliveryService.Native.findDeliveryServiceByValidateDate", query = "SELECT pp.job_slip,pp.no_ppkb,pp.cont_no,mm.iso_code FROM delivery_service pp,m_container_type mm,registration r where pp.cont_type=mm.cont_type AND pp.no_reg=r.no_reg AND r.status_service='approve' AND pp.valid_date>=now() AND pp.job_slip NOT IN (select job_slip from service_gate_delivery) ORDER BY substring(pp.job_slip,7,4) DESC"),
    @NamedNativeQuery(name = "DeliveryService.Native.findDeliveryServiceByClosingTime", query = "SELECT pp.job_slip,pp.no_ppkb,pp.cont_no,pp.seal_no,mm.iso_code, r.no_reg "
                                                                                                + "FROM delivery_service pp "
                                                                                                        + "JOIN m_container_type mm ON (pp.cont_type=mm.cont_type) "
                                                                                                        + "JOIN registration r ON (pp.no_reg=r.no_reg) "
                                                                                                        + "JOIN service_cont_discharge scd ON (scd.cont_no=pp.cont_no AND scd.no_ppkb=pp.no_ppkb) "
                                                                                                        + "JOIN m_commodity mc ON (mc.commodity_code=scd.commodity_code) "
                                                                                                        + "LEFT JOIN service_gate_delivery sgd ON (pp.job_slip=sgd.job_slip) "
                                                                                                + "WHERE pp.counter_print > 0 AND sgd.job_slip IS NULL AND ((scd.position='" + ServiceContDischarge.LOCATION_CY + "' AND (mc.dangerous_class IS NULL OR mc.dangerous_class NOT IN ('1', '7'))) OR (scd.position='" + ServiceContDischarge.LOCATION_VESSEL + "' AND mc.dangerous_class IN ('1', '7'))) AND r.status_service='approve' AND pp.job_slip=? AND pp.valid_date>=now()"),
    @NamedNativeQuery(name = "DeliveryService.Native.findInvoice", query = "SELECT ds.job_slip FROM delivery_service ds WHERE ds.cont_no = ? AND ds.no_ppkb = ? AND ds.no_reg = ?"),
    @NamedNativeQuery(name = "DeliveryService.Native.perhitungan", query = "SELECT ds.job_slip, ds.cont_no, ds.cont_status, ds.cont_size, ct.type_in_general as name, change(ds.over_size) as over_size, change(ds.dg) as dg, change(ds.dg_label) as dg_label, ds.placement_date, nvl(pp.masa_1), nvl(pp.masa_2), pls.charge, nvl(pp.charge_m1), nvl(pp.charge_m2), nvl(phd.charge), nvl(ppg.total_charge), nvl(pp.charge), sgd.cont_no IS NOT NULL, nvl(pub.charge) "
                                                                            + "FROM (((((((delivery_service ds "
                                                                                            + "LEFT JOIN service_gate_delivery sgd ON (ds.cont_no=sgd.cont_no AND ds.no_ppkb=sgd.no_ppkb)) "
                                                                                            + "LEFT JOIN perhitungan_handling_discharge phd ON (phd.cont_no = ds.cont_no AND phd.no_reg = ds.no_reg)) "
                                                                                            + "LEFT JOIN perhitungan_pass_gate ppg ON (ppg.job_slip=ds.job_slip)) "
                                                                                            + "LEFT JOIN perhitungan_upah_buruh pub ON (pub.jobslip=ds.job_slip)) "
                                                                                            + "JOIN perhitungan_lift_service pls ON (pls.cont_no = ds.cont_no AND pls.no_reg = ds.no_reg)) "
                                                                                            + "LEFT JOIN perhitungan_penumpukan pp ON (pp.cont_no = ds.cont_no AND pp.no_reg = ds.no_reg)) "
                                                                                            + "JOIN m_container_type ct ON (ds.cont_type = ct.cont_type)) "
                                                                                            + "JOIN m_currency c ON (pls.curr_code = c.curr_code) "
                                                                            + "WHERE ds.no_reg = ?"),
    @NamedNativeQuery(name = "DeliveryService.Native.findByPpkb", query = "SELECT ds.job_slip, c.name, ds.cont_no, ds.cont_size, ct.name, ds.cont_status, ds.cont_gross, CASE WHEN ds.over_size=true THEN 'Yes' WHEN ds.over_size=false THEN 'No' END as over_size, ds.placement_date,ds.bl_no FROM delivery_service ds, m_commodity c, m_container_type ct WHERE ds.commodity_code = c.commodity_code AND ds.cont_type = ct.cont_type AND ds.no_ppkb = ? AND ds.cont_no NOT IN (SELECT cont_no FROM penumpukan_susulan_service WHERE no_ppkb = ds.no_ppkb)"),
    @NamedNativeQuery(name = "DeliveryService.Native.findByPpkbAndBlNo", query = "SELECT ds.job_slip, c.name, ds.cont_no, ds.cont_size, ct.name, ds.cont_status, ds.cont_gross, CASE WHEN ds.over_size=true THEN 'Yes' WHEN ds.over_size=false THEN 'No' END as over_size, ds.placement_date,ds.bl_no FROM delivery_service ds, m_commodity c, m_container_type ct WHERE ds.commodity_code = c.commodity_code AND ds.cont_type = ct.cont_type AND ds.no_ppkb = ? AND ds.bl_no=? AND ds.cont_no NOT IN (SELECT cont_no FROM penumpukan_susulan_service WHERE no_ppkb = ds.no_ppkb)"),
    @NamedNativeQuery(name = "DeliveryService.Native.findByReg", query = "SELECT ds.job_slip, ds.cont_no, ds.cont_size, ct.type_in_general as name, ds.cont_status, ds.cont_gross, CASE WHEN ds.over_size=true THEN 'Yes' WHEN ds.over_size=false THEN 'No' END as over_size, ds.valid_date FROM delivery_service ds, m_container_type ct WHERE ds.cont_type = ct.cont_type AND ds.no_reg = ?"),
    @NamedNativeQuery(name = "DeliveryService.Native.findDeliveryServiceByAutoComplete", query = "SELECT d.job_slip FROM delivery_service d where d.job_slip LIKE ('%'|| ? ||'%') AND d.job_slip NOT IN (SELECT job_slip FROM service_gate_delivery) ORDER BY d.created_date DESC"),
    @NamedNativeQuery(name = "DeliveryService.Native.findByPpkbNCont", query = "SELECT ds.job_slip FROM delivery_service ds WHERE ds.cont_no = ? AND ds.no_ppkb = ?"),
    @NamedNativeQuery(name = "DeliveryService.Native.findByListBatalNota", query = "select d.bl_no,d.job_slip,d.cont_no,d.cont_status,mt.name,d.cont_gross from delivery_service d,m_container_type mt where d.no_reg=? AND d.cont_type=mt.cont_type"),
    @NamedNativeQuery(name = "DeliveryService.Native.findByPpkbAndBlNoPenumpukanSusulan", query = "SELECT ds.job_slip, c.name, ds.cont_no, ds.cont_size, ct.name, ds.cont_status, ds.cont_gross, CASE WHEN ds.over_size=true THEN 'Yes' WHEN ds.over_size=false THEN 'No' END as over_size, ds.placement_date,ds.bl_no FROM delivery_service ds, m_commodity c, m_container_type ct,service_cont_discharge scd WHERE ds.commodity_code = c.commodity_code AND ds.cont_type = ct.cont_type AND ds.no_ppkb = ? AND ds.bl_no=? AND scd.no_ppkb=ds.no_ppkb AND scd.cont_no=ds.cont_no AND ds.valid_date <= now() AND scd.is_delivery=false "),
    @NamedNativeQuery(name = "DeliveryService.Native.findByPpkbPenumpukanSusulan", query = "SELECT ds.job_slip, c.name, ds.cont_no, ds.cont_size, ct.name, ds.cont_status, ds.cont_gross, CASE WHEN ds.over_size=true THEN 'Yes' WHEN ds.over_size=false THEN 'No' END as over_size, ds.placement_date,ds.bl_no FROM delivery_service ds, m_commodity c, m_container_type ct,service_cont_discharge scd WHERE ds.commodity_code = c.commodity_code AND ds.cont_type = ct.cont_type AND ds.no_ppkb = ? AND scd.no_ppkb=ds.no_ppkb AND scd.cont_no=ds.cont_no AND ds.valid_date <= now() AND scd.is_delivery=false "),
    @NamedNativeQuery(name = "DeliveryService.Native.findByNoregValidasiPrint", query = "select ds.job_slip from delivery_service ds where ds.counter_print >=1 AND ds.no_reg=?"),
    @NamedNativeQuery(name = "DeliveryService.Native.findContainerDetail", query = "select r.no_reg,r.no_ppkb,r.bl_no,r.job_slip,r.cont_no,r.cont_status,r.cont_size,r.created_by,r.modified_by,r.created_date,r.modified_date from delivery_service r where r.no_ppkb= ? "),
    @NamedNativeQuery(name = "DeliveryService.Native.findCashierDischarge", query = "SELECT ds.job_slip, ds.valid_date,inv.no_invoice,inv.payment_date,ds.created_by,m.name as consignee,r.pengurus_do,ds.no_reg  "
            + "FROM delivery_service ds LEFT JOIN invoice inv ON inv.no_reg::TEXT = ds.no_reg::TEXT LEFT JOIN registration r ON r.no_reg = ds.no_reg LEFT JOIN m_customer m ON m.cust_code = r.cust_code WHERE ds.no_ppkb =?  AND ds.cont_no= ?")})

    public class DeliveryService implements Serializable, EntityAuditable {
    @Basic(optional = false)
    @Column(name = "twenty_one_feet", nullable = false)
    private boolean twentyOneFeet = false;

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
    @Column(name = "bl_no")
    private String blNo;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "job_slip", nullable = false, length = 30)
    private String jobSlip;
    @Column(name = "no_invoice", length = 30)
    private String noInvoice;
    @Column(name = "cont_no", length = 11)
    private String contNo;
    @Column(name = "cont_size")
    private Short contSize;
    @Column(name = "cont_status", length = 10)
    private String contStatus;
    @Column(name = "cont_gross")
    private Integer contGross;
    @Column(name = "over_size")
    private Boolean overSize;
    @Column(name = "dg")
    private Boolean dg;
    @Column(name = "dg_label")
    private Boolean dgLabel;
    @Column(name = "placement_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date placementDate;
    @Column(name = "masa_1")
    private Short masa1;
    @Column(name = "masa_2")
    private Short masa2;
    @Column(name = "valid_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date validDate;
    @Column(name = "seal_no")
    private String sealNo;
    @Column(name = "crane", length = 2)
    private String crane;
    @JoinColumn(name = "no_reg", referencedColumnName = "no_reg")
    @ManyToOne
    private Registration registration;
    @JoinColumn(name = "no_ppkb", referencedColumnName = "no_ppkb")
    @ManyToOne
    private PlanningVessel planningVessel;
    @JoinColumn(name = "cont_type", referencedColumnName = "cont_type")
    @ManyToOne
    private MasterContainerType masterContainerType;
    @JoinColumn(name = "commodity_code", referencedColumnName = "commodity_code")
    @ManyToOne
    private MasterCommodity masterCommodity;
    @Column(name = "counter_print")
    private Integer counterPrint=0;
    @Column(name = "real_penumpukan")
    private Integer realPenumpukan;
    @JoinColumn(name = "mlo", referencedColumnName = "cust_code")
    @ManyToOne
    private MasterCustomer mlo;
    @Column(name = "no_ppkb", insertable=false, updatable=false)
    private String noPpkb;

    public DeliveryService() {
    }

    public DeliveryService(String jobSlip) {
        this.jobSlip = jobSlip;
    }

    public String getJobSlip() {
        return jobSlip;
    }

    public void setJobSlip(String jobSlip) {
        this.jobSlip = jobSlip;
    }

    public String getNoInvoice() {
        return noInvoice;
    }

    public void setNoInvoice(String noInvoice) {
        this.noInvoice = noInvoice;
    }

    public String getContNo() {
        return contNo;
    }

    public void setContNo(String contNo) {
        this.contNo = contNo;
    }

    public Short getContSize() {
        return contSize;
    }

    public void setContSize(Short contSize) {
        this.contSize = contSize;
    }

    public String getContStatus() {
        return contStatus;
    }

    public void setContStatus(String contStatus) {
        this.contStatus = contStatus;
    }

    public Integer getContGross() {
        return contGross;
    }

    public void setContGross(Integer contGross) {
        this.contGross = contGross;
    }

    public Boolean getOverSize() {
        return overSize;
    }

    public void setOverSize(Boolean overSize) {
        this.overSize = overSize;
    }

    public Boolean getDg() {
        return dg;
    }

    public void setDg(Boolean dg) {
        this.dg = dg;
    }

    public Boolean getDgLabel() {
        return dgLabel;
    }

    public void setDgLabel(Boolean dgLabel) {
        this.dgLabel = dgLabel;
    }

    public Date getPlacementDate() {
        return placementDate;
    }

    public void setPlacementDate(Date placementDate) {
        this.placementDate = placementDate;
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

    public Date getValidDate() {
        return validDate;
    }

    public void setValidDate(Date validDate) {
        this.validDate = validDate;
    }

    public Registration getRegistration() {
        return registration;
    }

    public void setRegistration(Registration registration) {
        this.registration = registration;
    }

    public PlanningVessel getPlanningVessel() {
        return planningVessel;
    }

    public void setPlanningVessel(PlanningVessel planningVessel) {
        this.planningVessel = planningVessel;
        if (planningVessel != null) {
            noPpkb = planningVessel.getNoPpkb();
        }
    }

    public MasterContainerType getMasterContainerType() {
        return masterContainerType;
    }

    public void setMasterContainerType(MasterContainerType masterContainerType) {
        this.masterContainerType = masterContainerType;
    }

    public MasterCommodity getMasterCommodity() {
        return masterCommodity;
    }

    public void setMasterCommodity(MasterCommodity masterCommodity) {
        this.masterCommodity = masterCommodity;
    }

    /**
     * @return the sealNo
     */
    public String getSealNo() {
        return sealNo;
    }

    /**
     * @param sealNo the sealNo to set
     */
    public void setSealNo(String sealNo) {
        this.sealNo = sealNo;
    }

    public String getCrane() {
        return crane;
    }

    public void setCrane(String crane) {
        this.crane = crane;
    }

    public String getBlNo() {
        return blNo;
    }

    public void setBlNo(String blNo) {
        this.blNo = blNo;
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

    public Integer getRealPenumpukan() {
        return realPenumpukan;
    }

    public void setRealPenumpukan(Integer realPenumpukan) {
        this.realPenumpukan = realPenumpukan;
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

    @Deprecated
    public String getNoPpkb() {
        return noPpkb;
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
        if (!(object instanceof DeliveryService)) {
            return false;
        }
        DeliveryService other = (DeliveryService) object;
        if ((this.jobSlip == null && other.jobSlip != null) || (this.jobSlip != null && !this.jobSlip.equals(other.jobSlip))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.DeliveryService[jobSlip=" + jobSlip + "]";
    }

    public boolean getTwentyOneFeet() {
        return twentyOneFeet;
    }

    public void setTwentyOneFeet(boolean twentyOneFeet) {
        this.twentyOneFeet = twentyOneFeet;
    }
}
