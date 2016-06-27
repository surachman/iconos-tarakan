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
 * @author dycoder
 */
@Entity
@EntityListeners({AuditTrailEntityListener.class})
@Table(name = "stripping_service")
@NamedQueries({
    @NamedQuery(name = "StrippingService.findAll", query = "SELECT s FROM StrippingService s"),
    @NamedQuery(name = "StrippingService.deleteByNoReg", query = "DELETE FROM StrippingService s WHERE s.registration.noReg = :noReg"),
    @NamedQuery(name = "StrippingService.findByNoReg", query = "SELECT s FROM StrippingService s WHERE s.registration.noReg = :noReg"),
    @NamedQuery(name = "StrippingService.findByJobSlip", query = "SELECT s FROM StrippingService s WHERE s.jobSlip = :jobSlip")})
@NamedNativeQueries({
/*
    @NamedNativeQuery(name = "StrippingService.Native.findByPpkbNReg", query = "SELECT ss.job_slip, ss.commodity, ss.cont_no, ss.cont_size, mct.name, ss.cont_status, ss.cont_gross, CASE WHEN ss.over_size=true THEN 'Yes' WHEN ss.over_size=false THEN 'No' END as over_size "
                                                                            + "FROM stripping_service ss "
                                                                                    + "JOIN registration r ON (ss.no_reg=r.no_reg) "
                                                                                    + "JOIN m_container_type mct ON (ss.cont_type=mct.cont_type) "
                                                                            + "WHERE r.no_ppkb = ? AND ss.no_reg = ?"),
*/
    @NamedNativeQuery(name = "StrippingService.Native.findByPpkbNReg", query = 
"SELECT ss.job_slip, " 
+"  ss.commodity, " 
+"  ss.cont_no, " 
+"  ss.cont_size, " 
+"  mct.name, " 
+"  ss.cont_status, " 
+"  ss.cont_gross, " 
+"  CASE " 
+"    WHEN ss.over_size= 'TRUE' " 
+"    THEN 'Yes' " 
+"    ELSE 'No' " 
+"  END AS over_size " 
+"FROM stripping_service ss " 
+"JOIN registration r " 
+"ON (ss.no_reg=r.no_reg) " 
+"JOIN m_container_type mct " 
+"ON (ss.cont_type=mct.cont_type) " 
+"WHERE r.no_ppkb = ? " 
+"AND ss.no_reg   = ?"),                                                                            
                                                                            
    @NamedNativeQuery(name = "StrippingService.Native.generateId", query = "SELECT MAX(substring(job_slip,7,5))FROM stripping_service WHERE substring(job_slip,3,4) = ?"),
    @NamedNativeQuery(name = "StrippingService.Native.findInvoice", query = "SELECT ds.job_slip "
                                                                            + "FROM stripping_service ds "
                                                                                    + "JOIN registration r ON (ds.no_reg=r.no_reg) "
                                                                            + "WHERE ds.cont_no = ? AND r.no_ppkb = ? AND ds.no_reg = ?"),

/*                                                                            
    @NamedNativeQuery(name = "StrippingService.Native.perhitungan", query = "SELECT ds.job_slip, ds.cont_no, ds.cont_status, ds.cont_size, mct.name, CASE WHEN ds.over_size=true THEN 'Yes' WHEN ds.over_size=false THEN 'No' END as over_size, CASE WHEN ds.dg=true THEN 'Yes' WHEN ds.dg=false THEN 'No' END as dg, CASE WHEN ds.dg_label=true THEN 'Yes' WHEN ds.dg_label=false THEN 'No' END as dg_label, ps.charge, c.symbol, c.language, c.country ,ps.id "
                                                                            + "FROM stripping_service ds "
                                                                                    + "JOIN m_container_type mct ON (ds.cont_type=mct.cont_type) "
                                                                                    + "JOIN perhitungan_stripping ps ON (ps.cont_no = ds.cont_no AND ps.no_reg = ds.no_reg) "
                                                                                    + "JOIN m_currency c ON (ps.curr_code = c.curr_code) "
                                                                            + "WHERE ds.no_reg = ?"),
*/
    @NamedNativeQuery(name = "StrippingService.Native.perhitungan", query = 
"SELECT ds.job_slip, " 
+"  ds.cont_no, " 
+"  ds.cont_status, " 
+"  ds.cont_size, " 
+"  mct.name, " 
+"  CASE " 
+"    WHEN ds.over_size= 'TRUE' " 
+"    THEN 'Yes' " 
+"    ELSE 'No' " 
+"  END AS over_size, " 
+"  CASE " 
+"    WHEN ds.dg = 'TRUE' " 
+"    THEN 'Yes' " 
+"    ELSE 'No' " 
+"  END AS dg, " 
+"  CASE " 
+"    WHEN ds.dg_label = 'TRUE' " 
+"    THEN 'Yes' " 
+"    ELSE 'No' " 
+"  END AS dg_label, " 
+"  ps.charge, " 
+"  c.symbol, " 
+"  c.language, " 
+"  c.country , " 
+"  ps.id " 
+"FROM stripping_service ds " 
+"JOIN m_container_type mct " 
+"ON (ds.cont_type=mct.cont_type) " 
+"JOIN perhitungan_stripping ps " 
+"ON (ps.cont_no = ds.cont_no " 
+"AND ps.no_reg  = ds.no_reg) "  //=> Kolom NO_REG di table PERHITUNGAN_STRIPPING tidak ada, MKR, Wed 12 Nov 2014 11:43:52 AM WIT 
+"JOIN m_currency c " 
+"ON (ps.curr_code = c.curr_code) " 
+"WHERE ds.no_reg  = ?"),

/*
    @NamedNativeQuery(name = "StrippingService.Native.findStrippingServiceByReg", query = "SELECT ds.job_slip, ds.cont_no, ds.cont_size, mct.name, ds.cont_status, ds.cont_gross, CASE WHEN ds.over_size=true THEN 'Yes' WHEN ds.over_size=false THEN 'No' END as over_size, ds.valid_date "
                                                                                        + "FROM stripping_service ds "
                                                                                                + "JOIN registration rs ON (ds.no_reg = rs.no_reg) "
                                                                                                + "JOIN m_container_type mct ON (mct.cont_type=ds.cont_type) "
                                                                                        + "WHERE ds.no_reg = ?"),
*/
    @NamedNativeQuery(name = "StrippingService.Native.findStrippingServiceByReg", query = 
"SELECT ds.job_slip, " 
+"  ds.cont_no, " 
+"  ds.cont_size, " 
+"  mct.name, " 
+"  ds.cont_status, " 
+"  ds.cont_gross, " 
+"  CASE " 
+"    WHEN ds.over_size = 'TRUE' " 
+"    THEN 'Yes' " 
+"    ELSE 'No' " 
+"  END AS over_size, " 
+"  ds.valid_date " 
+"FROM stripping_service ds " 
+"JOIN registration rs " 
+"ON (ds.no_reg = rs.no_reg) " 
+"JOIN m_container_type mct " 
+"ON (mct.cont_type=ds.cont_type) " 
+"WHERE ds.no_reg  = ?"), 

    @NamedNativeQuery(name = "StrippingService.Native.findByNoregValidasiPrint", query = "select ds.job_slip from stripping_service ds where ds.counter_print >=1 AND ds.no_reg=?")})
    
public class StrippingService implements Serializable, EntityAuditable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @Basic(optional = false)
    @Column(name = "job_slip", length = 30)
    private String jobSlip;
    @Basic(optional = false)
    @Column(name = "commodity", nullable = false, length = 256)
    private String commodity;
    @Column(name = "cont_no", length = 11)
    private String contNo;
    @Basic(optional = false)
    @Column(name = "cont_size", nullable = false)
    private short contSize;
    @Basic(optional = false)
    @Column(name = "cont_status", nullable = false, length = 10)
    private String contStatus;
    @Basic(optional = false)
    @Column(name = "cont_gross", nullable = false)
    private int contGross;
    @Basic(optional = false)
    @Column(name = "over_size", nullable = false)
    private String overSize = "FALSE";
    @Basic(optional = false)
    @Column(name = "dg", nullable = false)
    private String dg = "FALSE";
    @Basic(optional = false)
    @Column(name = "dg_label", nullable = false)
    private String dgLabel = "FALSE";
    @Column(name = "valid_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date validDate;
    @Column(name = "bl_no", length = 100)
    private String blNo;
    @Basic(optional = false)
    @Column(name = "is_load", nullable = false)
    private String isLoad = "TRUE";
    @Column(name = "counter_print")
    private Integer counterPrint = 0;
    @JoinColumn(name = "no_reg", referencedColumnName = "no_reg", nullable = false)
    @ManyToOne(optional = false)
    private Registration registration;
    @JoinColumn(name = "mlo", referencedColumnName = "cust_code")
    @ManyToOne
    private MasterCustomer mlo;
    @JoinColumn(name = "cont_type", referencedColumnName = "cont_type", nullable = false)
    @ManyToOne(optional = false)
    private MasterContainerType masterContainerType;
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
    @JoinColumn(name = "perhitungan_stripping_id", referencedColumnName = "id")
    @OneToOne(cascade=CascadeType.ALL, orphanRemoval=true)
    private PerhitunganStripping perhitunganStripping;
    @OneToOne(cascade=CascadeType.ALL, orphanRemoval=true)
    @JoinColumn(name = "job_slip", referencedColumnName = "job_slip", insertable = false, updatable = false)
    private ReceivingService exStrippingLoadContainer;

    public StrippingService() {
    }

    public StrippingService(String jobSlip) {
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

    public String getContNo() {
        return contNo;
    }

    public void setContNo(String contNo) {
        this.contNo = contNo;
    }

    public short getContSize() {
        return contSize;
    }

    public void setContSize(short contSize) {
        this.contSize = contSize;
    }

    public String getContStatus() {
        return contStatus;
    }

    public void setContStatus(String contStatus) {
        this.contStatus = contStatus;
    }

    public int getContGross() {
        return contGross;
    }

    public void setContGross(int contGross) {
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

    public String getBlNo() {
        return blNo;
    }

    public void setBlNo(String blNo) {
        this.blNo = blNo;
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

    public MasterCustomer getMlo() {
        return mlo;
    }

    public void setMlo(MasterCustomer mlo) {
        this.mlo = mlo;
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

    public MasterContainerType getMasterContainerType() {
        return masterContainerType;
    }

    public void setMasterContainerType(MasterContainerType masterContainerType) {
        this.masterContainerType = masterContainerType;
    }

    public PerhitunganStripping getPerhitunganStripping() {
        return perhitunganStripping;
    }

    public void setPerhitunganStripping(PerhitunganStripping perhitunganStripping) {
        this.perhitunganStripping = perhitunganStripping;
    }

    public String getIsLoad() {
        return isLoad;
    }

    public void setIsLoad(String isLoad) {
        this.isLoad = isLoad;
    }

    public ReceivingService getExStrippingLoadContainer() {
        return exStrippingLoadContainer;
    }

    @Deprecated
    public void setExStrippingLoadContainer(ReceivingService exStrippingLoadContainer) {
        this.exStrippingLoadContainer = exStrippingLoadContainer;
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
        if (!(object instanceof StrippingService)) {
            return false;
        }
        StrippingService other = (StrippingService) object;
        if ((this.jobSlip == null && other.jobSlip != null) || (this.jobSlip != null && !this.jobSlip.equals(other.jobSlip))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.StrippingService[jobSlip=" + jobSlip + "]";
    }
}
