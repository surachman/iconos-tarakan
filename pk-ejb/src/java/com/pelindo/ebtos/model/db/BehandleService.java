/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.model.db;

import com.pelindo.ebtos.model.db.master.MasterContainerType;
import com.qtasnim.persistence.AuditTrailEntityListener;
import com.qtasnim.persistence.EntityAuditable;
import com.pelindo.ebtos.model.db.master.MasterCustomer;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
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
@Table(name = "behandle_service")
@NamedQueries({
    @NamedQuery(name = "BehandleService.findAll", query = "SELECT b FROM BehandleService b"),
    @NamedQuery(name = "BehandleService.findByJobSlip", query = "SELECT b FROM BehandleService b WHERE b.jobSlip = :jobSlip"),
    @NamedQuery(name = "BehandleService.findByNoReg", query = "SELECT b FROM BehandleService b WHERE b.registration.noReg = :noReg"),
    @NamedQuery(name = "BehandleService.deleteByNoReg", query = "DELETE FROM BehandleService b WHERE b.registration.noReg = :noReg"),
    @NamedQuery(name = "BehandleService.findByContNoAndNoReg", query = "SELECT b FROM BehandleService b WHERE b.registration.noReg = :noReg AND b.contNo = :contNo"),
    @NamedQuery(name = "BehandleService.findByNoPpkb", query = "SELECT b FROM BehandleService b WHERE b.planningVessel.noPpkb = :noPpkb"),
    @NamedQuery(name = "BehandleService.findByCommodity", query = "SELECT b FROM BehandleService b WHERE b.commodity = :commodity"),
    @NamedQuery(name = "BehandleService.findByNoInvoice", query = "SELECT b FROM BehandleService b WHERE b.noInvoice = :noInvoice"),
    @NamedQuery(name = "BehandleService.findByContNo", query = "SELECT b FROM BehandleService b WHERE b.contNo = :contNo"),
    @NamedQuery(name = "BehandleService.findByContSize", query = "SELECT b FROM BehandleService b WHERE b.contSize = :contSize"),
    @NamedQuery(name = "BehandleService.findByContStatus", query = "SELECT b FROM BehandleService b WHERE b.contStatus = :contStatus"),
    @NamedQuery(name = "BehandleService.findByContGross", query = "SELECT b FROM BehandleService b WHERE b.contGross = :contGross"),
    @NamedQuery(name = "BehandleService.findByOverSize", query = "SELECT b FROM BehandleService b WHERE b.overSize = :overSize"),
    @NamedQuery(name = "BehandleService.findByDg", query = "SELECT b FROM BehandleService b WHERE b.dg = :dg"),
    @NamedQuery(name = "BehandleService.findByDgLabel", query = "SELECT b FROM BehandleService b WHERE b.dgLabel = :dgLabel"),
    @NamedQuery(name = "BehandleService.findByValidDate", query = "SELECT b FROM BehandleService b WHERE b.validDate = :validDate")})
@NamedNativeQueries({

/* 
* Updated by SRACH
* Mon 27 Oct 2014 05:11:57 PM WIT 
* Update Notes: Upgrade to Oracle 11g SQL Compliance
*/

/*
    @NamedNativeQuery(name = "BehandleService.Native.findByPpkbNReg", query = "SELECT bs.job_slip, bs.commodity, bs.cont_no, bs.cont_size, bs.cont_type, bs.cont_status, bs.cont_gross, CASE WHEN bs.over_size=true THEN 'Yes' WHEN bs.over_size=false THEN 'No' END as over_size FROM behandle_service bs WHERE bs.no_ppkb = ? AND bs.no_reg = ?"),
*/    
    @NamedNativeQuery(name = "BehandleService.Native.findByPpkbNReg", query =     
"SELECT bs.job_slip, " 
+"  bs.commodity, " 
+"  bs.cont_no, " 
+"  bs.cont_size, " 
+"  bs.cont_type, " 
+"  bs.cont_status, " 
+"  bs.cont_gross, " 
+"  DECODE(bs.over_size, 1, 'Yes', 'No') AS over_size " 
+"FROM behandle_service bs " 
+"WHERE bs.no_ppkb = ? " 
+"AND bs.no_reg    = ?"),    

/* 
* Updated by SRACH
* Mon 27 Oct 2014 05:14:11 PM WIT 
* Update Notes: Upgrade to Oracle 11g SQL Compliance
*/

/*
    @NamedNativeQuery(name = "BehandleService.Native.generateId", query = "SELECT MAX(substring(job_slip,7,5))FROM behandle_service WHERE substring(job_slip,3,4) = ?"),
*/
    
    @NamedNativeQuery(name = "BehandleService.Native.generateId", query =     
"SELECT MAX(SUBSTR(job_slip,7,5)) " 
+"FROM behandle_service " 
+"WHERE SUBSTR(job_slip,3,4) = ?"),
    
    @NamedNativeQuery(name = "BehandleService.Native.findInvoice", query = "SELECT ds.job_slip FROM behandle_service ds WHERE ds.cont_no = ? AND ds.no_ppkb = ? AND ds.no_reg = ?"),

/* 
* Updated by SRACH
* Mon 27 Oct 2014 05:14:11 PM WIT 
* Update Notes: Upgrade to Oracle 11g SQL Compliance
*/

/*    
    @NamedNativeQuery(name = "BehandleService.Native.findByJobslip", query = "SELECT bs.job_slip, bs.commodity, bs.cont_no, bs.cont_size, ct.type_in_general as cont_type, bs.cont_status, bs.cont_gross, change(bs.over_size) "
                                                                        + "FROM behandle_service bs "
                                                                                + "left join m_container_type ct on bs.cont_type = ct.cont_type ,registration r "
                                                                                + "WHERE bs.no_reg=r.no_reg AND bs.no_reg=?"),
*/                                                                                
    @NamedNativeQuery(name = "BehandleService.Native.findByJobslip", query = 
"SELECT bs.job_slip, " 
+"  bs.commodity, " 
+"  bs.cont_no, " 
+"  bs.cont_size, " 
+"  ct.type_in_general AS cont_type, " 
+"  bs.cont_status, " 
+"  bs.cont_gross, " 
+"  DECODE(bs.over_size, 1, 'Yes', 'No') " 
+"FROM behandle_service bs " 
+"LEFT JOIN m_container_type ct " 
+"ON bs.cont_type = ct.cont_type , " 
+"  registration r " 
+"WHERE bs.no_reg=r.no_reg " 
+"AND bs.no_reg  = ?"),  

/* 
* Updated by SRACH
* Mon 27 Oct 2014 05:14:11 PM WIT 
* Update Notes: Upgrade to Oracle 11g SQL Compliance
TODO:
Add columns on table "perhitungan_behandle" as the following:
- CURR_CODE VARCHAR2(5 BYTE) 
- CONT_NO VARCHAR2(11 BYTE)
- NO_REG VARCHAR2(30 BYTE)

See attached file: PERHITUNGAN_BEHANDLE-add-column.sql

*/                                                                              
/*
    @NamedNativeQuery(name = "BehandleService.Native.perhitungan", query = "SELECT ds.job_slip, ds.cont_no, ds.cont_size, ct.type_in_general as type, ds.cont_status, change(ds.over_size), change(ds.dg), change(ds.dg_label), change(ds.special_equipment), pp.total_charge, c.symbol, c.language, c.country "
    + "FROM behandle_service ds, perhitungan_behandle pp, m_currency c, m_container_type ct "
    + "WHERE ct.name = ds.cont_type and pp.cont_no = ds.cont_no AND pp.no_reg = ds.no_reg AND pp.curr_code = c.curr_code AND ds.no_reg = ?"),
*/    

    @NamedNativeQuery(name = "BehandleService.Native.perhitungan", query = 
"SELECT ds.job_slip, " 
+"  ds.cont_no, " 
+"  ds.cont_size, " 
+"  ct.type_in_general AS type, " 
+"  ds.cont_status, " 
+"  DECODE(ds.over_size, 1, 'Yes', 'No'), " 
+"  DECODE(ds.dg, 1, 'Yes', 'No'), " 
+"  DECODE(ds.dg_label, 1, 'Yes', 'No'), " 
+"  DECODE(ds.special_equipment, 1, 'Yes', 'No'), " 
+"  pp.total_charge, " 
+"  c.symbol, " 
+"  c.language, " 
+"  c.country " 
+"FROM behandle_service ds, " 
+"  perhitungan_behandle pp, " 
+"  m_currency c, " 
+"  m_container_type ct " 
+"WHERE ct.name    = ds.cont_type " 
+"AND pp.cont_no   = ds.cont_no " 
+"AND pp.no_reg    = ds.no_reg " 
+"AND pp.curr_code = c.curr_code " 
+"AND ds.no_reg    = ?"),

@NamedNativeQuery(name = "BehandleService.Native.findByNoregValidasiPrint", query = "select ds.job_slip from behandle_service ds where ds.counter_print >=1 AND ds.no_reg=?")})
public class BehandleService implements Serializable, EntityAuditable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "job_slip", length = 30)
    private String jobSlip;
    @Column(name = "commodity")
    private String commodity;
    @Column(name = "no_invoice", length = 30)
    private String noInvoice;
    @JoinColumn(name = "no_reg", referencedColumnName = "no_reg", nullable = false)
    @ManyToOne(optional = false)
    private Registration registration;
    @JoinColumn(name = "no_ppkb", referencedColumnName = "no_ppkb", nullable = false)
    @ManyToOne(optional = false)
    private PlanningVessel planningVessel;
    @Column(name = "cont_no")
    private String contNo;
    @Column(name = "cont_size")
    private Short contSize;
    @Column(name = "cont_status")
    private String contStatus;
    @Column(name = "cont_gross")
    private Integer contGross;
    @Column(name = "over_size")
    private String overSize = "FALSE";
    @Column(name = "dg")
    private String dg = "FALSE";
    @Column(name = "dg_label")
    private String dgLabel = "FALSE";
    @Column(name = "special_equipment")
    private String specialEquipment = "FALSE";
    @Column(name = "counter_print")
    private Integer counterPrint=0;
    @JoinColumn(name = "mlo", referencedColumnName = "cust_code")
    @ManyToOne
    private MasterCustomer mlo;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "behandleService")
    private PerhitunganBehandle perhitunganBehandle;
    @Column(name =     "valid_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date validDate;
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
    @Column(name = "bl_no")
    private String blNo;
    @JoinColumn(name = "cont_type", referencedColumnName = "cont_type", nullable = false)
    @ManyToOne(optional = false)
    private MasterContainerType masterContainerType;
    @OneToOne
    @JoinColumns(value = {
        @JoinColumn(name = "cont_no", referencedColumnName = "cont_no", insertable = false, updatable = false),
        @JoinColumn(name = "no_ppkb", referencedColumnName = "no_ppkb", insertable = false, updatable = false)
    })
    private ServiceContDischarge serviceContDischarge;

    public BehandleService() {
    }

    public BehandleService(String jobSlip) {
        this.jobSlip = jobSlip;
    }

    public String getJobSlip() {
        return jobSlip;
    }

    public void setJobSlip(String jobSlip) {
        this.jobSlip = jobSlip;
    }

    public String getCommodity() {
        return commodity;
    }

    public void setCommodity(String commodity) {
        this.commodity = commodity;
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

    public Date getValidDate() {
        return validDate;
    }

    public void setValidDate(Date validDate) {
        this.validDate = validDate;
    }

    public String getSpecialEquipment() {
        return specialEquipment;
    }

    public void setSpecialEquipment(String specialEquipment) {
        this.specialEquipment = specialEquipment;
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

    public PerhitunganBehandle getPerhitunganBehandle() {
        return perhitunganBehandle;
    }

    public void setPerhitunganBehandle(PerhitunganBehandle perhitunganBehandle) {
        this.perhitunganBehandle = perhitunganBehandle;
    }

    public MasterContainerType getMasterContainerType() {
        return masterContainerType;
    }

    public void setMasterContainerType(MasterContainerType masterContainerType) {
        this.masterContainerType = masterContainerType;
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

    public ServiceContDischarge getServiceContDischarge() {
        return serviceContDischarge;
    }

    @Deprecated
    public void setServiceContDischarge(ServiceContDischarge serviceContDischarge) {
        this.serviceContDischarge = serviceContDischarge;
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
        if (!(object instanceof BehandleService)) {
            return false;
        }
        BehandleService other = (BehandleService) object;
        if ((this.jobSlip == null && other.jobSlip != null) || (this.jobSlip != null && !this.jobSlip.equals(other.jobSlip))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.BehandleService[jobSlip=" + jobSlip + "]";
    }
}
