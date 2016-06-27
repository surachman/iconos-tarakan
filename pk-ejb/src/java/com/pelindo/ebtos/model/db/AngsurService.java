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
@Table(name = "angsur_service")
@NamedQueries({
    @NamedQuery(name = "AngsurService.findAll", query = "SELECT a FROM AngsurService a"),
    @NamedQuery(name = "AngsurService.findByJobSlip", query = "SELECT a FROM AngsurService a WHERE a.jobSlip = :jobSlip"),
    @NamedQuery(name = "AngsurService.findByContNo", query = "SELECT a FROM AngsurService a WHERE a.contNo = :contNo"),
    @NamedQuery(name = "AngsurService.findByContSize", query = "SELECT a FROM AngsurService a WHERE a.contSize = :contSize"),
    @NamedQuery(name = "AngsurService.findByContStatus", query = "SELECT a FROM AngsurService a WHERE a.contStatus = :contStatus"),
    @NamedQuery(name = "AngsurService.findByContGross", query = "SELECT a FROM AngsurService a WHERE a.contGross = :contGross"),
    @NamedQuery(name = "AngsurService.findByOverSize", query = "SELECT a FROM AngsurService a WHERE a.overSize = :overSize"),
    @NamedQuery(name = "AngsurService.findByDg", query = "SELECT a FROM AngsurService a WHERE a.dg = :dg"),
    @NamedQuery(name = "AngsurService.findByDgLabel", query = "SELECT a FROM AngsurService a WHERE a.dgLabel = :dgLabel"),
    @NamedQuery(name = "AngsurService.findBySpecialEquipment", query = "SELECT a FROM AngsurService a WHERE a.specialEquipment = :specialEquipment")})

    @NamedNativeQueries({
/* 
* Updated by SRACH
* Mon 27 Oct 2014 03:15:15 PM WIT 
* Update Notes: Upgrade to Oracle 11g SQL Compliance
*/    

/*
    @NamedNativeQuery(name = "AngsurService.Native.findByPpkbNReg", query = "SELECT ds.job_slip, c.name, ds.cont_no, ds.cont_size, ct.name, ds.cont_status, ds.cont_gross, CASE WHEN ds.over_size=true THEN 'Yes' WHEN ds.over_size=false THEN 'No' END as over_size, change(ds.special_equipment) FROM angsur_service ds, m_commodity c, m_container_type ct WHERE ds.commodity_code = c.commodity_code AND ds.cont_type = ct.cont_type AND ds.no_ppkb = ? AND ds.no_reg = ?"),
*/
    @NamedNativeQuery(name = "AngsurService.Native.findByPpkbNReg", query = 
"SELECT ds.job_slip, " 
+"  c.name, " 
+"  ds.cont_no, " 
+"  ds.cont_size, " 
+"  ct.name, " 
+"  ds.cont_status, " 
+"  ds.cont_gross, " 
+"  DECODE(ds.over_size, 1, 'Yes', 'No') AS over_size, " 
+"  DECODE(ds.special_equipment, 1, 'Yes', 0, 'No') " 
+"FROM angsur_service ds, " 
+"  m_commodity c, " 
+"  m_container_type ct " 
+"WHERE ds.commodity_code = c.commodity_code " 
+"AND ds.cont_type        = ct.cont_type " 
+"AND ds.no_ppkb          = ? " 
+"AND ds.no_reg           = ?"),        

/* 
* Updated by SRACH
* Mon 27 Oct 2014 03:30:04 PM WIT 
* Update Notes: Upgrade to Oracle 11g SQL Compliance
*/    

/*
    @NamedNativeQuery(name = "AngsurService.Native.generateId", query = "SELECT MAX(substring(job_slip,7,5))FROM angsur_service WHERE substring(job_slip,3,4) = ?"),
*/    
    @NamedNativeQuery(name = "AngsurService.Native.generateId", query = 
"SELECT MAX(SUBSTR(job_slip,7,5)) " 
+"FROM angsur_service " 
+"WHERE SUBSTR(job_slip,3,4) = ?"),

/* 
* Updated by SRACH
* Mon 27 Oct 2014 03:36:50 PM WIT 
* Update Notes: Upgrade to Oracle 11g SQL Compliance
*/    

/*    
    @NamedNativeQuery(name = "AngsurService.Native.perhitungan", query = "SELECT ds.job_slip, ds.cont_no, ds.cont_status, ds.cont_size, m.name, CASE WHEN ds.over_size=true THEN 'Yes' WHEN ds.over_size=false THEN 'No' END as over_size, CASE WHEN ds.dg=true THEN 'Yes' WHEN ds.dg=false THEN 'No' END as dg,CASE WHEN ds.dg_label=true THEN 'Yes' WHEN ds.dg_label=false THEN 'No' END as dg_label,pls.charge,c.symbol,c.language,c.country,mc.name,ds.bl_no FROM angsur_service ds, perhitungan_angsur pls,m_container_type m,m_currency c,m_commodity mc WHERE pls.cont_no = ds.cont_no AND pls.curr_code=c.curr_code AND ds.cont_type=m.cont_type AND pls.no_reg = ds.no_reg AND ds.commodity_code=mc.commodity_code AND ds.no_reg=?"),
*/    
    @NamedNativeQuery(name = "AngsurService.Native.perhitungan", query = 
"SELECT ds.job_slip, " 
+"  ds.cont_no, " 
+"  ds.cont_status, " 
+"  ds.cont_size, " 
+"  m.name, " 
+"  DECODE(ds.over_size, 1, 'Yes', 'No') AS over_size, " 
+"  DECODE(ds.dg, 1, 'Yes', 'No')        AS dg, " 
+"  DECODE(ds.dg_label, 1, 'Yes', 'No')  AS dg_label, " 
+"  pls.charge, " 
+"  c.symbol, " 
+"  c.language, " 
+"  c.country, " 
+"  mc.name, " 
+"  ds.bl_no " 
+"FROM angsur_service ds, " 
+"  perhitungan_angsur pls, " 
+"  m_container_type m, " 
+"  m_currency c, " 
+"  m_commodity mc " 
+"WHERE pls.cont_no    = ds.cont_no " 
+"AND pls.curr_code    =c.curr_code " 
+"AND ds.cont_type     =m.cont_type " 
+"AND pls.no_reg       = ds.no_reg " 
+"AND ds.commodity_code=mc.commodity_code " 
+"AND ds.no_reg        =?"),    
    @NamedNativeQuery(name = "AngsurService.Native.findByListBatalNotaService", query = "select d.job_slip,d.no_reg,d.no_ppkb,d.cont_no from angsur_service d where d.no_ppkb=? AND d.no_reg=?")})

public class AngsurService implements Serializable, EntityAuditable {
    @Column(name = "bl_no")
    private String blNo;
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
    @Column(name = "over_size", length = 5)
    private String overSize;
    @Column(name = "dg", length = 5)
    private String dg;
    @Column(name = "dg_label" , length = 5)
    private String dgLabel;
    @Column(name = "special_equipment", length = 5)
    private String specialEquipment;
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
    @JoinColumn(name = "mlo", referencedColumnName = "cust_code")
    @ManyToOne
    private MasterCustomer mlo;

    public AngsurService() {
    }

    public AngsurService(String jobSlip) {
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

    public String getSpecialEquipment() {
        return specialEquipment;
    }

    public void setSpecialEquipment(String specialEquipment) {
        this.specialEquipment = specialEquipment;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (jobSlip != null ? jobSlip.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AngsurService)) {
            return false;
        }
        AngsurService other = (AngsurService) object;
        if ((this.jobSlip == null && other.jobSlip != null) || (this.jobSlip != null && !this.jobSlip.equals(other.jobSlip))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.AngsurService[jobSlip=" + jobSlip + "]";
    }
}
