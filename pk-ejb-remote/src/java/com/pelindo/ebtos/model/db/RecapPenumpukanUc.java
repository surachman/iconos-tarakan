/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.model.db;

import com.qtasnim.persistence.AuditTrailEntityListener;
import com.pelindo.ebtos.model.db.master.MasterActivity;
import com.pelindo.ebtos.model.db.master.MasterCurrency;
import com.qtasnim.persistence.EntityAuditable;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author R Seno Anggoro
 */
@Entity
@EntityListeners({AuditTrailEntityListener.class})
@Table(name = "recap_penumpukan_uc")
@NamedQueries({
    @NamedQuery(name = "RecapPenumpukanUc.findAll", query = "SELECT r FROM RecapPenumpukanUc r"),
    @NamedQuery(name = "RecapPenumpukanUc.findById", query = "SELECT r FROM RecapPenumpukanUc r WHERE r.id = :id"),
    @NamedQuery(name = "RecapPenumpukanUc.findByMasa1", query = "SELECT r FROM RecapPenumpukanUc r WHERE r.masa1 = :masa1"),
    @NamedQuery(name = "RecapPenumpukanUc.findByMasa2", query = "SELECT r FROM RecapPenumpukanUc r WHERE r.masa2 = :masa2"),
    @NamedQuery(name = "RecapPenumpukanUc.findByChargeMasa1", query = "SELECT r FROM RecapPenumpukanUc r WHERE r.chargeMasa1 = :chargeMasa1"),
    @NamedQuery(name = "RecapPenumpukanUc.findByChargeMasa2", query = "SELECT r FROM RecapPenumpukanUc r WHERE r.chargeMasa2 = :chargeMasa2"),
    @NamedQuery(name = "RecapPenumpukanUc.findByJasaDermaga", query = "SELECT r FROM RecapPenumpukanUc r WHERE r.jasaDermaga = :jasaDermaga"),
    @NamedQuery(name = "RecapPenumpukanUc.findByTotalCharge", query = "SELECT r FROM RecapPenumpukanUc r WHERE r.totalCharge = :totalCharge"),
    @NamedQuery(name = "RecapPenumpukanUc.findByCreatedBy", query = "SELECT r FROM RecapPenumpukanUc r WHERE r.createdBy = :createdBy"),
    @NamedQuery(name = "RecapPenumpukanUc.findByCreatedDate", query = "SELECT r FROM RecapPenumpukanUc r WHERE r.createdDate = :createdDate"),
    @NamedQuery(name = "RecapPenumpukanUc.findByModifiedBy", query = "SELECT r FROM RecapPenumpukanUc r WHERE r.modifiedBy = :modifiedBy"),
    @NamedQuery(name = "RecapPenumpukanUc.findByModifiedDate", query = "SELECT r FROM RecapPenumpukanUc r WHERE r.modifiedDate = :modifiedDate"),
    @NamedQuery(name = "RecapPenumpukanUc.findByOperation", query = "SELECT r FROM RecapPenumpukanUc r WHERE r.operation = :operation"),
    @NamedQuery(name = "RecapPenumpukanUc.deleteByNoPpkb", query = "DELETE FROM RecapPenumpukanUc r WHERE r.serviceVessel.noPpkb = :noPpkb")})
public class RecapPenumpukanUc implements Serializable, EntityAuditable {
    public static final String OPERATION_LOAD = "LOAD";
    public static final String OPERATION_DISCHARGE = "DISCHARGE";

    public static final String ACTIVITY_LOAD = "LOAD";
    public static final String ACTIVITY_DISCHARGE = "DISCHARGE";
    public static final String ACTIVITY_TRANSHIPMENT = "TRANSHIPMENT";
    public static final String ACTIVITY_SHIFTING = "SHIFTING";
    
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Basic(optional = false)
    @Column(name = "total_hari", nullable = false)
    private int totalHari = 0;
    @Basic(optional = false)
    @Column(name = "masa_1", nullable = false)
    private short masa1 = 0;
    @Basic(optional = false)
    @Column(name = "masa_2", nullable = false)
    private short masa2 = 0;
    @Basic(optional = false)
    @Column(name = "charge_masa_1", nullable = false, precision = 19, scale = 2)
    private BigDecimal chargeMasa1;
    @Basic(optional = false)
    @Column(name = "charge_masa_2", nullable = false, precision = 19, scale = 2)
    private BigDecimal chargeMasa2;
    @Basic(optional = false)
    @Column(name = "jasa_dermaga", nullable = false, precision = 19, scale = 2)
    private BigDecimal jasaDermaga;
    @Basic(optional = false)
    @Column(name = "total_charge", nullable = false, precision = 19, scale = 2)
    private BigDecimal totalCharge;
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
    @Column(name = "operation", length = 15)
    private String operation;
    @JoinColumn(name = "curr_code", referencedColumnName = "curr_code", nullable = false)
    @ManyToOne(optional = false)
    private MasterCurrency masterCurrency;
    @JoinColumn(name = "activity_code", referencedColumnName = "activity_code", nullable = false)
    @ManyToOne(optional = false)
    private MasterActivity masterActivity;
    @JoinColumn(name = "no_ppkb", referencedColumnName = "no_ppkb", nullable = false)
    @ManyToOne(optional = false)
    private ServiceVessel serviceVessel;
    @Basic(optional = false)
    @Column(name = "bl_no")
    private String blNo;
    @Basic(optional = false)
    @Column(name = "activity", nullable = false, length = 20)
    private String activity;

    public RecapPenumpukanUc() {
    }

    public RecapPenumpukanUc(Integer id) {
        this.id = id;
    }

    public RecapPenumpukanUc(Integer id, short masa1, short masa2, BigDecimal chargeMasa1, BigDecimal chargeMasa2, BigDecimal jasaDermaga, BigDecimal totalCharge, String createdBy, Date createdDate) {
        this.id = id;
        this.masa1 = masa1;
        this.masa2 = masa2;
        this.chargeMasa1 = chargeMasa1;
        this.chargeMasa2 = chargeMasa2;
        this.jasaDermaga = jasaDermaga;
        this.totalCharge = totalCharge;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public short getMasa1() {
        return masa1;
    }

    public void setMasa1(short masa1) {
        this.masa1 = masa1;
    }

    public short getMasa2() {
        return masa2;
    }

    public void setMasa2(short masa2) {
        this.masa2 = masa2;
    }

    public BigDecimal getChargeMasa1() {
        return chargeMasa1;
    }

    public void setChargeMasa1(BigDecimal chargeMasa1) {
        this.chargeMasa1 = chargeMasa1;
    }

    public BigDecimal getChargeMasa2() {
        return chargeMasa2;
    }

    public void setChargeMasa2(BigDecimal chargeMasa2) {
        this.chargeMasa2 = chargeMasa2;
    }

    public BigDecimal getJasaDermaga() {
        return jasaDermaga;
    }

    public void setJasaDermaga(BigDecimal jasaDermaga) {
        this.jasaDermaga = jasaDermaga;
    }

    public BigDecimal getTotalCharge() {
        return totalCharge;
    }

    public void setTotalCharge(BigDecimal totalCharge) {
        this.totalCharge = totalCharge;
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

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public MasterCurrency getMasterCurrency() {
        return masterCurrency;
    }

    public void setMasterCurrency(MasterCurrency masterCurrency) {
        this.masterCurrency = masterCurrency;
    }

    public MasterActivity getMasterActivity() {
        return masterActivity;
    }

    public void setMasterActivity(MasterActivity masterActivity) {
        this.masterActivity = masterActivity;
    }

    public String getBlNo() {
        return blNo;
    }

    public void setBlNo(String blNo) {
        this.blNo = blNo;
    }

    public ServiceVessel getServiceVessel() {
        return serviceVessel;
    }

    public void setServiceVessel(ServiceVessel serviceVessel) {
        this.serviceVessel = serviceVessel;
    }

    public int getTotalHari() {
        return totalHari;
    }

    public void setTotalHari(int totalHari) {
        this.totalHari = totalHari;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RecapPenumpukanUc)) {
            return false;
        }
        RecapPenumpukanUc other = (RecapPenumpukanUc) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.RecapPenumpukanUc[id=" + id + "]";
    }
}
