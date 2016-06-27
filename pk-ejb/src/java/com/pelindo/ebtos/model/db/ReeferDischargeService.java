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
@Table(name = "reefer_discharge_service")
@NamedQueries({
    @NamedQuery(name = "ReeferDischargeService.findAll", query = "SELECT r FROM ReeferDischargeService r"),
    @NamedQuery(name = "ReeferDischargeService.findByJobSlip", query = "SELECT r FROM ReeferDischargeService r WHERE r.jobSlip = :jobSlip"),
    @NamedQuery(name = "ReeferDischargeService.findByContNo", query = "SELECT r FROM ReeferDischargeService r WHERE r.contNo = :contNo"),
    @NamedQuery(name = "ReeferDischargeService.findByContNoAndNoReg", query = "SELECT r FROM ReeferDischargeService r WHERE r.contNo = :contNo AND r.registration.noReg = :noReg"),
    @NamedQuery(name = "ReeferDischargeService.findByNoPpkbAndContNo", query = "SELECT r FROM ReeferDischargeService r WHERE r.contNo = :contNo AND r.planningVessel.noPpkb = :noPpkb ORDER BY r.validDate DESC"),
    @NamedQuery(name = "ReeferDischargeService.findValidReeferByNoPpkbAndContNo", query = "SELECT r FROM ReeferDischargeService r WHERE r.contNo = :contNo AND r.planningVessel.noPpkb = :noPpkb AND r.registration.statusService = 'approve' ORDER BY r.validDate DESC"),
    @NamedQuery(name = "ReeferDischargeService.findByNoReg", query = "SELECT r FROM ReeferDischargeService r WHERE r.registration.noReg = :noReg"),
    @NamedQuery(name = "ReeferDischargeService.findByContSize", query = "SELECT r FROM ReeferDischargeService r WHERE r.contSize = :contSize"),
    @NamedQuery(name = "ReeferDischargeService.findByContStatus", query = "SELECT r FROM ReeferDischargeService r WHERE r.contStatus = :contStatus"),
    @NamedQuery(name = "ReeferDischargeService.findByContGross", query = "SELECT r FROM ReeferDischargeService r WHERE r.contGross = :contGross"),
    @NamedQuery(name = "ReeferDischargeService.findByOverSize", query = "SELECT r FROM ReeferDischargeService r WHERE r.overSize = :overSize"),
    @NamedQuery(name = "ReeferDischargeService.findByDg", query = "SELECT r FROM ReeferDischargeService r WHERE r.dg = :dg"),
    @NamedQuery(name = "ReeferDischargeService.findByDgLabel", query = "SELECT r FROM ReeferDischargeService r WHERE r.dgLabel = :dgLabel"),
    @NamedQuery(name = "ReeferDischargeService.findByPlugOnDate", query = "SELECT r FROM ReeferDischargeService r WHERE r.plugOnDate = :plugOnDate"),
    @NamedQuery(name = "ReeferDischargeService.findByShiftPlanning", query = "SELECT r FROM ReeferDischargeService r WHERE r.shiftPlanning = :shiftPlanning")})
@NamedNativeQueries({
/*
    @NamedNativeQuery(name = "ReeferDischargeService.Native.findByPpkbNReg", query = "SELECT rds.job_slip, c.name, rds.cont_no, rds.cont_size, ct.name, rds.cont_status, rds.cont_gross, CASE WHEN rds.over_size=true THEN 'Yes' WHEN rds.over_size=false THEN 'No' END as over_size, rds.plug_on_date, rds.shift_planning FROM reefer_discharge_service rds, m_commodity c, m_container_type ct WHERE rds.commodity_code = c.commodity_code AND rds.cont_type = ct.cont_type AND rds.no_ppkb = ? AND rds.no_reg = ?"),
*/
    @NamedNativeQuery(name = "ReeferDischargeService.Native.findByPpkbNReg", query = 
"SELECT rds.job_slip, " 
+"  c.name, " 
+"  rds.cont_no, " 
+"  rds.cont_size, " 
+"  ct.name, " 
+"  rds.cont_status, " 
+"  rds.cont_gross, " 
+"  CASE " 
+"    WHEN rds.over_size = 'TRUE' " 
+"    THEN 'Yes' " 
+"    ELSE 'No' " 
+"  END AS over_size, " 
+"  rds.plug_on_date, " 
+"  rds.shift_planning " 
+"FROM reefer_discharge_service rds, " 
+"  m_commodity c, " 
+"  m_container_type ct " 
+"WHERE rds.commodity_code = c.commodity_code " 
+"AND rds.cont_type        = ct.cont_type " 
+"AND rds.no_ppkb          = ? " 
+"AND rds.no_reg           = ?"),    

//    @NamedNativeQuery(name = "ReeferDischargeService.Native.generateId", query = "SELECT MAX(substring(job_slip,7,5))FROM reefer_discharge_service WHERE substring(job_slip,3,4) = ?"),
    @NamedNativeQuery(name = "ReeferDischargeService.Native.generateId", query = 
"SELECT MAX(SUBSTR(job_slip,7,5)) " 
+"FROM reefer_discharge_service " 
+"WHERE SUBSTR(job_slip,3,4) = ?"),
    @NamedNativeQuery(name = "ReeferDischargeService.Native.findInvoice", query = "SELECT job_slip FROM reefer_discharge_service WHERE cont_no = ? AND no_ppkb = ? AND no_reg = ?"),

/*
    @NamedNativeQuery(name = "ReeferDischargeService.Native.perhitungan", query = "SELECT ds.job_slip, ds.cont_no, ds.cont_status, ds.cont_size, ct.type_in_general as cont_type, ds.plug_on_date, "
                                                                                        + "ds.shift_planning, pp.charge, pp.total_charge, pm.charge, pm.total_charge, sgd.cont_no IS NOT NULL "
                                                                                + "FROM reefer_discharge_service ds "
                                                                                        + "JOIN perhitungan_plugging pp ON (pp.cont_no = ds.cont_no AND pp.no_reg = ds.no_reg) "
                                                                                        + "JOIN perhitungan_monitoring pm ON (pm.cont_no = ds.cont_no AND pm.no_reg = ds.no_reg) "
                                                                                        + "JOIN m_currency c ON (pp.curr_code = c.curr_code) "
                                                                                        + "JOIN m_container_type ct ON (ct.cont_type = ds.cont_type) "
                                                                                        + "LEFT JOIN service_gate_delivery sgd ON (ds.cont_no=sgd.cont_no AND ds.no_ppkb=sgd.no_ppkb) "
                                                                                + "WHERE ds.no_reg = ?;"),
*/
    @NamedNativeQuery(name = "ReeferDischargeService.Native.perhitungan", query = 
"SELECT ds.job_slip, " 
+"  ds.cont_no, " 
+"  ds.cont_status, " 
+"  ds.cont_size, " 
+"  ct.type_in_general AS cont_type, " 
+"  ds.plug_on_date, " 
+"  ds.shift_planning, " 
+"  pp.charge, " 
+"  pp.total_charge, " 
+"  pm.charge, " 
+"  pm.total_charge, " 
+"  sgd.cont_no " 
+"FROM reefer_discharge_service ds " 
+"JOIN perhitungan_plugging pp " 
+"ON (pp.cont_no = ds.cont_no " 
+"AND pp.no_reg  = ds.no_reg) " 
+"JOIN perhitungan_monitoring pm " 
+"ON (pm.cont_no = ds.cont_no " 
+"AND pm.no_reg  = ds.no_reg) " 
+"JOIN m_currency c " 
+"ON (pp.curr_code = c.curr_code) " 
+"JOIN m_container_type ct " 
+"ON (ct.cont_type = ds.cont_type) " 
+"LEFT JOIN service_gate_delivery sgd " 
+"ON (ds.cont_no  =sgd.cont_no " 
+"AND ds.no_ppkb  =sgd.no_ppkb) " 
+"WHERE ds.no_reg = ?"),                                                                                
/*
    @NamedNativeQuery(name = "ReeferDischargeService.Native.findByReg", query = "SELECT rds.job_slip, rds.cont_no, rds.cont_size, ct.type_in_general as name, rds.cont_status, rds.cont_gross, CASE WHEN rds.over_size=true THEN 'Yes' WHEN rds.over_size=false THEN 'No' END as over_size FROM reefer_discharge_service rds, m_container_type ct WHERE rds.cont_type = ct.cont_type AND rds.no_reg = ?"),
*/
    @NamedNativeQuery(name = "ReeferDischargeService.Native.findByReg", query = 
"SELECT rds.job_slip, " 
+"  rds.cont_no, " 
+"  rds.cont_size, " 
+"  ct.type_in_general AS name, " 
+"  rds.cont_status, " 
+"  rds.cont_gross, " 
+"  CASE " 
+"    WHEN rds.over_size = 'TRUE' " 
+"    THEN 'Yes' " 
+"    ELSE 'No' " 
+"  END AS over_size " 
+"FROM reefer_discharge_service rds, " 
+"  m_container_type ct " 
+"WHERE rds.cont_type = ct.cont_type " 
+"AND rds.no_reg      = ?"),    

/*
    @NamedNativeQuery(name = "ReeferDischargeService.Native.findReeferByReg", query = "SELECT pp.job_slip,pp.no_ppkb,pp.cont_no,mm.iso_code, r.no_reg FROM reefer_discharge_service pp,m_container_type mm,registration r where pp.cont_type=mm.cont_type AND pp.no_reg=r.no_reg AND r.status_service='approve' AND pp.job_slip=? AND pp.job_slip NOT IN (select job_slip from service_gate_delivery) ORDER BY substring(pp.job_slip,7,4) DESC"),
*/
    @NamedNativeQuery(name = "ReeferDischargeService.Native.findReeferByReg", query =   
"SELECT pp.job_slip, " 
+"  pp.no_ppkb, " 
+"  pp.cont_no, " 
+"  mm.iso_code, " 
+"  r.no_reg " 
+"FROM reefer_discharge_service pp, " 
+"  m_container_type mm, " 
+"  registration r " 
+"WHERE pp.cont_type   =mm.cont_type " 
+"AND pp.no_reg        =r.no_reg " 
+"AND r.status_service ='approve' " 
+"AND pp.job_slip      =? " 
+"AND pp.job_slip NOT IN " 
+"  (SELECT job_slip FROM service_gate_delivery " 
+"  ) " 
+"ORDER BY SUBSTR(pp.job_slip,7,4) DESC"),    
/*
    @NamedNativeQuery(name = "ReeferDischargeService.Native.findReeferDischargeServiceByAutoComplete", query = "SELECT d.job_slip FROM reefer_discharge_service d LEFT JOIN service_gate_delivery s ON d.job_slip=s.job_slip where d.job_slip LIKE ('%'|| ? ||'%') ORDER BY substring(d.job_slip,7,5) DESC"),
*/
    @NamedNativeQuery(name = "ReeferDischargeService.Native.findReeferDischargeServiceByAutoComplete", query = 
"SELECT d.job_slip " 
+"FROM reefer_discharge_service d " 
+"LEFT JOIN service_gate_delivery s " 
+"ON d.job_slip=s.job_slip " 
+"WHERE d.job_slip LIKE ('%' " 
+"  || ? " 
+"  ||'%') " 
+"ORDER BY SUBSTR(d.job_slip,7,5) DESC"),

    @NamedNativeQuery(name = "ReeferDischargeService.Native.findByNoregValidasiPrint", query = "select ds.job_slip from reefer_discharge_service ds where ds.counter_print >=1 AND ds.no_reg=?")})
public class ReeferDischargeService implements Serializable, EntityAuditable {
    @Column(name = "valid_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date validDate;

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
    @Column(name = "temperature", precision = 5, scale = 2)
    private BigDecimal temperature;
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
    private String overSize;
    @Column(name = "dg")
    private String dg;
    @Column(name = "dg_label")
    private String dgLabel;
    @Column(name = "plug_on_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date plugOnDate;
    @Column(name = "shift_planning")
    private Short shiftPlanning;
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
    @JoinColumn(name = "mlo", referencedColumnName = "cust_code")
    @ManyToOne
    private MasterCustomer mlo;

    public ReeferDischargeService() {
    }

    public ReeferDischargeService(String jobSlip) {
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

    public String getOverSize() {
        return overSize;
    }

    public void setOverSize(String overSize) {
        this.overSize = overSize;
    }

    public String getDg() {
        return dg;
    }

    public void setDg(String dg) {
        this.dg = dg;
    }

    public String getDgLabel() {
        return dgLabel;
    }

    public void setDgLabel(String dgLabel) {
        this.dgLabel = dgLabel;
    }

    public Date getPlugOnDate() {
        return plugOnDate;
    }

    public void setPlugOnDate(Date plugOnDate) {
        this.plugOnDate = plugOnDate;
    }

    public Short getShiftPlanning() {
        return shiftPlanning;
    }

    public void setShiftPlanning(Short shiftPlanning) {
        this.shiftPlanning = shiftPlanning;
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
        if (!(object instanceof ReeferDischargeService)) {
            return false;
        }
        ReeferDischargeService other = (ReeferDischargeService) object;
        if ((this.jobSlip == null && other.jobSlip != null) || (this.jobSlip != null && !this.jobSlip.equals(other.jobSlip))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.ReeferDischargeService[jobSlip=" + jobSlip + "]";
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

    public Date getValidDate() {
        return validDate;
    }

    public void setValidDate(Date validDate) {
        this.validDate = validDate;
    }
}
