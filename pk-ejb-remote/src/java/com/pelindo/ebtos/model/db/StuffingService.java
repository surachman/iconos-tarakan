/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.model.db;

import com.pelindo.ebtos.model.db.master.MasterCommodity;
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
@Table(name = "stuffing_service")
@NamedQueries({
    @NamedQuery(name = "StuffingService.findAll", query = "SELECT s FROM StuffingService s"),
    @NamedQuery(name = "StuffingService.findByNoReg", query = "SELECT s FROM StuffingService s WHERE s.registration.noReg = :noReg"),
    @NamedQuery(name = "StuffingService.deleteByNoReg", query = "DELETE FROM StuffingService s WHERE s.registration.noReg = :noReg")})
@NamedNativeQueries({
    @NamedNativeQuery(name = "StuffingService.Native.findByPpkbNReg", query = "SELECT ss.job_slip, ss.commodity_code, ss.cont_no, ss.cont_size, ss.cont_type, ss.cont_status, ss.cont_gross, CASE WHEN ss.over_size=true THEN 'Yes' WHEN ss.over_size=false THEN 'No' END as over_size FROM stuffing_service ss WHERE ss.no_reg = ?"),
    @NamedNativeQuery(name = "StuffingService.Native.generateId", query = "SELECT MAX(substring(job_slip,7,5))FROM stuffing_service WHERE substring(job_slip,3,4) = ?"),
    @NamedNativeQuery(name = "StuffingService.Native.findInvoice", query = "SELECT ds.job_slip FROM stuffing_service ds WHERE ds.cont_no = ? AND ds.no_reg = ?"),
    @NamedNativeQuery(name = "StuffingService.Native.perhitungan", query = "SELECT ds.job_slip, ds.cont_no, ds.cont_status, ds.cont_size, ds.cont_type, CASE WHEN ds.over_size=true THEN 'Yes' WHEN ds.over_size=false THEN 'No' END as over_size, CASE WHEN ds.dg=true THEN 'Yes' WHEN ds.dg=false THEN 'No' END as dg, CASE WHEN ds.dg_label=true THEN 'Yes' WHEN ds.dg_label=false THEN 'No' END as dg_label, ps.charge, ps.id "
                                                                            + "FROM stuffing_service ds "
                                                                                    + "JOIN perhitungan_stuffing ps ON (ps.id=ds.perhitungan_stuffing_id) "
                                                                            + "WHERE ds.no_reg = ?"),
    @NamedNativeQuery(name = "StuffingService.Native.findStuffingServiceByReg", query = "SELECT ds.job_slip, ds.cont_no, ds.cont_size, ds.cont_type, ds.cont_status, ds.cont_gross, CASE WHEN ds.over_size=true THEN 'Yes' WHEN ds.over_size=false THEN 'No' END as over_size, ds.valid_date "
                                                                                        + "FROM stuffing_service ds "
                                                                                                + "JOIN registration rs ON (ds.no_reg = rs.no_reg) "
                                                                                        + "WHERE ds.no_reg = ?"),
    @NamedNativeQuery(name = "StuffingService.Native.findByNoregValidasiPrint", query = "SELECT ds.job_slip FROM stuffing_service ds WHERE ds.counter_print >=1 AND ds.no_reg=?")})
public class StuffingService implements Serializable, EntityAuditable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "job_slip", length = 30)
    private String jobSlip;
    @Column(name = "no_invoice", length = 30)
    private String noInvoice;
    @Basic(optional = false)
    @Column(name = "cont_no", nullable = false, length = 11)
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
    private boolean overSize = false;
    @Basic(optional = false)
    @Column(name = "dg", nullable = false)
    private boolean dg = false;
    @Basic(optional = false)
    @Column(name = "dg_label", nullable = false)
    private boolean dgLabel = false;
    @Column(name = "valid_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date validDate;
    @Column(name = "bl_no", length = 100)
    private String blNo;
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
    @Column(name = "counter_print")
    private Integer counterPrint = 0;
    @JoinColumn(name = "no_reg", referencedColumnName = "no_reg", nullable = false)
    @ManyToOne(optional = false)
    private Registration registration;
    @JoinColumn(name = "perhitungan_stuffing_id", referencedColumnName = "id")
    @OneToOne(cascade=CascadeType.ALL, orphanRemoval=true)
    private PerhitunganStuffing perhitunganStuffing;
    @JoinColumn(name = "mlo", referencedColumnName = "cust_code")
    @ManyToOne
    private MasterCustomer mlo;
    @JoinColumn(name = "cont_type", referencedColumnName = "cont_type", nullable = false)
    @ManyToOne(optional = false)
    private MasterContainerType masterContainerType;
    @JoinColumn(name = "commodity_code", referencedColumnName = "commodity_code", nullable = false)
    @ManyToOne(optional = false)
    private MasterCommodity masterCommodity;
    @OneToOne(cascade=CascadeType.ALL, orphanRemoval=true)
    @JoinColumn(name = "job_slip", referencedColumnName = "job_slip", insertable = false, updatable = false)
    private ReceivingService exStuffingLoadContainer;

    public StuffingService() {
    }

    public StuffingService(String jobSlip) {
        this.jobSlip = jobSlip;
    }

    public StuffingService(String jobSlip, String contNo, short contSize, String contStatus, int contGross, boolean overSize, boolean dg, boolean dgLabel, String createdBy, Date createdDate) {
        this.jobSlip = jobSlip;
        this.contNo = contNo;
        this.contSize = contSize;
        this.contStatus = contStatus;
        this.contGross = contGross;
        this.overSize = overSize;
        this.dg = dg;
        this.dgLabel = dgLabel;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
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

    public boolean getOverSize() {
        return overSize;
    }

    public void setOverSize(boolean overSize) {
        this.overSize = overSize;
    }

    public boolean getDg() {
        return dg;
    }

    public void setDg(boolean dg) {
        this.dg = dg;
    }

    public boolean getDgLabel() {
        return dgLabel;
    }

    public void setDgLabel(boolean dgLabel) {
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

    public PerhitunganStuffing getPerhitunganStuffing() {
        return perhitunganStuffing;
    }

    public void setPerhitunganStuffing(PerhitunganStuffing perhitunganStuffing) {
        this.perhitunganStuffing = perhitunganStuffing;
    }

    public MasterCustomer getMlo() {
        return mlo;
    }

    public void setMlo(MasterCustomer masterCustomer) {
        this.mlo = masterCustomer;
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

    public ReceivingService getExStuffingLoadContainer() {
        return exStuffingLoadContainer;
    }

    @Deprecated
    public void setExStuffingLoadContainer(ReceivingService exStuffingLoadContainer) {
        this.exStuffingLoadContainer = exStuffingLoadContainer;
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
        if (!(object instanceof StuffingService)) {
            return false;
        }
        StuffingService other = (StuffingService) object;
        if ((this.jobSlip == null && other.jobSlip != null) || (this.jobSlip != null && !this.jobSlip.equals(other.jobSlip))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.StuffingService[jobSlip=" + jobSlip + "]";
    }
}
