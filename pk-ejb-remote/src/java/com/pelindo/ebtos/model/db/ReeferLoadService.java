/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.model.db;

import com.qtasnim.persistence.AuditTrailEntityListener;
import com.qtasnim.persistence.EntityAuditable;
import com.pelindo.ebtos.model.db.master.MasterContainerType;
import com.pelindo.ebtos.model.db.master.MasterCommodity;
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
@Table(name = "reefer_load_service")
@NamedQueries({
    @NamedQuery(name = "ReeferLoadService.findAll", query = "SELECT r FROM ReeferLoadService r"),
    @NamedQuery(name = "ReeferLoadService.findByJobSlip", query = "SELECT r FROM ReeferLoadService r WHERE r.jobSlip = :jobSlip"),
    @NamedQuery(name = "ReeferLoadService.findByNoPpkb", query = "SELECT r FROM ReeferLoadService r WHERE r.planningVessel.noPpkb = :noPpkb"),
    @NamedQuery(name = "ReeferLoadService.findByContNo", query = "SELECT r FROM ReeferLoadService r WHERE r.contNo = :contNo"),
    @NamedQuery(name = "ReeferLoadService.findValidReeferByNoPpkbAndContNo", query = "SELECT r FROM ReeferLoadService r WHERE r.contNo = :contNo AND r.planningVessel.noPpkb = :noPpkb AND r.registration.statusService = 'approve' ORDER BY r.registration.invoice.paymentDate DESC"),
    @NamedQuery(name = "ReeferLoadService.findByContSize", query = "SELECT r FROM ReeferLoadService r WHERE r.contSize = :contSize"),
    @NamedQuery(name = "ReeferLoadService.findByContStatus", query = "SELECT r FROM ReeferLoadService r WHERE r.contStatus = :contStatus"),
    @NamedQuery(name = "ReeferLoadService.findByContGross", query = "SELECT r FROM ReeferLoadService r WHERE r.contGross = :contGross"),
    @NamedQuery(name = "ReeferLoadService.findByOverSize", query = "SELECT r FROM ReeferLoadService r WHERE r.overSize = :overSize"),
    @NamedQuery(name = "ReeferLoadService.findByDg", query = "SELECT r FROM ReeferLoadService r WHERE r.dg = :dg"),
    @NamedQuery(name = "ReeferLoadService.findByDgLabel", query = "SELECT r FROM ReeferLoadService r WHERE r.dgLabel = :dgLabel"),
    @NamedQuery(name = "ReeferLoadService.findByShiftPlanning", query = "SELECT r FROM ReeferLoadService r WHERE r.shiftPlanning = :shiftPlanning"),
    @NamedQuery(name = "ReeferLoadService.updatePlugOnOffAndShiftPlanningByNoPpkb", query = "UPDATE ReeferLoadService r SET r.plugOnDate = :plugOnDate, r.plugOffDate = :plugOffDate, r.shiftPlanning = :shiftPlanning WHERE r.planningVessel.noPpkb = :noPpkb")})
@NamedNativeQueries({
    @NamedNativeQuery(name = "ReeferLoadService.Native.findByPpkbNReg", query = "SELECT rds.job_slip, c.name, rds.cont_no, rds.cont_size, ct.name, rds.cont_status, rds.cont_gross, CASE WHEN rds.over_size=true THEN 'Yes' WHEN rds.over_size=false THEN 'No' END as over_size, rds.plug_on_date, rds.shift_planning FROM reefer_load_service rds, m_commodity c, m_container_type ct WHERE rds.commodity_code = c.commodity_code AND rds.cont_type = ct.cont_type AND rds.no_ppkb = ? AND rds.no_reg = ?"),
    @NamedNativeQuery(name = "ReeferLoadService.Native.generateId", query = "SELECT MAX(substring(job_slip,7,5))FROM reefer_load_service WHERE substring(job_slip,3,4) = ?"),
    @NamedNativeQuery(name = "ReeferLoadService.Native.findInvoice", query = "SELECT job_slip FROM reefer_load_service WHERE cont_no = ? AND no_ppkb = ? AND no_reg = ?"),
    @NamedNativeQuery(name = "ReeferLoadService.Native.perhitungan", query = "SELECT ds.job_slip, ds.cont_no, ds.cont_status, ds.cont_size, ds.cont_type, ds.plug_on_date, nvl(ds.shift_planning), nvl(pp.charge), nvl(pp.total_charge), nvl(pm.charge), nvl(pm.total_charge), c.symbol, c.language, c.country, mt.type_in_general as name "
                                                                            + "FROM "
                                                                                    + "m_container_type mt, "
                                                                                        + "((perhitungan_plugging pp "
                                                                                            + "JOIN m_currency c ON pp.curr_code = c.curr_code) "
                                                                                            + "JOIN reefer_load_service ds ON pp.no_reg = ds.no_reg AND pp.cont_no = ds.cont_no) "
                                                                                                + "LEFT JOIN perhitungan_monitoring pm ON pm.no_reg = ds.no_reg AND pm.cont_no = ds.cont_no "
                                                                            + "WHERE ds.no_reg = ? AND ds.cont_type=mt.cont_type"),
    @NamedNativeQuery(name = "ReeferLoadService.Native.findByPpkb", query = "SELECT job_slip FROM reefer_load_service WHERE no_ppkb = ?"),
    @NamedNativeQuery(name = "ReeferLoadService.Native.findByReg", query = "SELECT prs.job_slip, prs.cont_no, prs.cont_size, ct.type_in_general as name, prs.cont_status, prs.cont_gross, CASE WHEN prs.over_size=true THEN 'Yes' WHEN prs.over_size=false THEN 'No' END as over_size FROM reefer_load_service prs, m_container_type ct WHERE prs.cont_type = ct.cont_type AND prs.no_reg = ?"),
    @NamedNativeQuery(name = "ReeferLoadService.Native.findGateReeferByJobSlip", query = "SELECT pp.job_slip,pp.no_ppkb,pp.cont_no,mm.iso_code, r.no_reg FROM planning_vessel p, reefer_load_service pp,m_container_type mm,registration r where p.no_ppkb=pp.no_ppkb AND pp.no_reg=r.no_reg AND pp.job_slip=? AND r.status_service='approve' AND pp.cont_type=mm.cont_type AND p.close_rec>=now() AND pp.job_slip NOT IN (select job_slip from service_gate_receiving) ORDER BY substring(pp.job_slip,7,4) DESC"),
    @NamedNativeQuery(name = "ReeferLoadService.Native.findReeferLoadServiceByAutoComplete", query = "SELECT d.job_slip FROM reefer_load_service d LEFT JOIN service_gate_delivery s ON d.job_slip=s.job_slip where d.job_slip LIKE ('%'|| ? ||'%') ORDER BY substring(d.job_slip,7,5) DESC"),
    @NamedNativeQuery(name = "ReeferLoadService.Native.findByNoregValidasiPrint", query = "select ds.job_slip from reefer_load_service ds where ds.counter_print >=1 AND ds.no_reg=?")})
public class ReeferLoadService implements Serializable, EntityAuditable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "job_slip", nullable = false, length = 30)
    private String jobSlip;
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
    @Column(name = "shift_planning")
    private Short shiftPlanning;
    @Column(name = "plug_on_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date plugOnDate;
    @Column(name = "plug_off_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date plugOffDate;
    @Column(name = "temperature", precision = 5, scale = 2)
    private BigDecimal temperature;
    @JoinColumn(name = "no_reg", referencedColumnName = "no_reg")
    @ManyToOne
    private Registration registration;
    @Column(name = "no_reg", insertable=false, updatable=false)
    private String noReg;
    @Column(name = "no_ppkb", insertable=false, updatable=false)
    private String noPpkb;
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
    @Column(name = "real")
    private BigDecimal real;
    @JoinColumn(name = "mlo", referencedColumnName = "cust_code")
    @ManyToOne
    private MasterCustomer mlo;
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

    public ReeferLoadService() {
    }

    public ReeferLoadService(String jobSlip) {
        this.jobSlip = jobSlip;
    }

    public String getJobSlip() {
        return jobSlip;
    }

    public void setJobSlip(String jobSlip) {
        this.jobSlip = jobSlip;
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

    public Short getShiftPlanning() {
        return shiftPlanning;
    }

    public void setShiftPlanning(Short shiftPlanning) {
        this.shiftPlanning = shiftPlanning;
    }

    public Date getPlugOnDate() {
        return plugOnDate;
    }

    public void setPlugOnDate(Date plugOnDate) {
        this.plugOnDate = plugOnDate;
    }

    public Date getPlugOffDate() {
        return plugOffDate;
    }

    public void setPlugOffDate(Date plugOffDate) {
        this.plugOffDate = plugOffDate;
    }

    public Registration getRegistration() {
        return registration;
    }

    public void setRegistration(Registration registration) {
        this.registration = registration;

        if (registration != null) {
            noReg = registration.getNoReg();
        }
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

    public BigDecimal getReal() {
        return real;
    }

    public void setReal(BigDecimal real) {
        this.real = real;
    }
    public String getNoReg() {
        return noReg;
    }

    public String getNoPpkb() {
        return noPpkb;
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
        hash += (jobSlip != null ? jobSlip.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ReeferLoadService)) {
            return false;
        }
        ReeferLoadService other = (ReeferLoadService) object;
        if ((this.jobSlip == null && other.jobSlip != null) || (this.jobSlip != null && !this.jobSlip.equals(other.jobSlip))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.ReeferLoadService[jobSlip=" + jobSlip + "]";
    }

    /**
     * @return the temperature
     */
    public BigDecimal getTemperature() {
        return temperature;
    }

    /**
     * @param temperature the temperature to set
     */
    public void setTemperature(BigDecimal temperature) {
        this.temperature = temperature;
    }
}
