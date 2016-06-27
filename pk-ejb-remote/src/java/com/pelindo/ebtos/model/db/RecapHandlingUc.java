/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.model.db;

import com.pelindo.ebtos.model.db.master.MasterActivity;
import com.pelindo.ebtos.model.db.master.MasterCurrency;
import com.qtasnim.persistence.AuditTrailEntityListener;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author R. Seno Anggoro A
 */
@Entity
@Table(name = "recap_handling_uc")
@EntityListeners({AuditTrailEntityListener.class})
@NamedQueries({
    @NamedQuery(name = "RecapHandlingUc.findAll", query = "SELECT r FROM RecapHandlingUc r"),
    @NamedQuery(name = "RecapHandlingUc.findById", query = "SELECT r FROM RecapHandlingUc r WHERE r.id = :id"),
    @NamedQuery(name = "RecapHandlingUc.deleteByNoPpkb", query = "DELETE FROM RecapHandlingUc r WHERE r.serviceVessel.noPpkb = :noPpkb"),
    @NamedQuery(name = "RecapHandlingUc.findByCrane", query = "SELECT r FROM RecapHandlingUc r WHERE r.crane = :crane"),
    @NamedQuery(name = "RecapHandlingUc.findByWeightGroup", query = "SELECT r FROM RecapHandlingUc r WHERE r.weightGroup = :weightGroup"),
    @NamedQuery(name = "RecapHandlingUc.findByIsExportImport", query = "SELECT r FROM RecapHandlingUc r WHERE r.isExportImport = :isExportImport"),
    @NamedQuery(name = "RecapHandlingUc.findByOperation", query = "SELECT r FROM RecapHandlingUc r WHERE r.operation = :operation"),
    @NamedQuery(name = "RecapHandlingUc.findByActivity", query = "SELECT r FROM RecapHandlingUc r WHERE r.activity = :activity"),
    @NamedQuery(name = "RecapHandlingUc.findByAmount", query = "SELECT r FROM RecapHandlingUc r WHERE r.amount = :amount"),
    @NamedQuery(name = "RecapHandlingUc.findByCharge", query = "SELECT r FROM RecapHandlingUc r WHERE r.charge = :charge"),
    @NamedQuery(name = "RecapHandlingUc.findByTotalCharge", query = "SELECT r FROM RecapHandlingUc r WHERE r.totalCharge = :totalCharge"),
    @NamedQuery(name = "RecapHandlingUc.findByCreatedBy", query = "SELECT r FROM RecapHandlingUc r WHERE r.createdBy = :createdBy"),
    @NamedQuery(name = "RecapHandlingUc.findByCreatedDate", query = "SELECT r FROM RecapHandlingUc r WHERE r.createdDate = :createdDate"),
    @NamedQuery(name = "RecapHandlingUc.findByModifiedBy", query = "SELECT r FROM RecapHandlingUc r WHERE r.modifiedBy = :modifiedBy"),
    @NamedQuery(name = "RecapHandlingUc.findByModifiedDate", query = "SELECT r FROM RecapHandlingUc r WHERE r.modifiedDate = :modifiedDate"),
    @NamedQuery(name = "RecapHandlingUc.findByBentuk3Constraint", query = "SELECT r FROM RecapHandlingUc r WHERE r.weightGroup = :weightGroup AND r.crane = :crane AND r.isExportImport = :isExportImport AND r.masterActivity.activityCode = :activityCode AND r.serviceVessel.noPpkb = :noPpkb AND r.operation = :operation AND r.activity = :activity AND r.masterCurrency.currCode = :currCode AND r.charge = :charge"),
    @NamedQuery(name = "RecapHandlingUc.findByBentuk3ConstraintWithStatus", query = "SELECT r FROM RecapHandlingUc r WHERE r.weightGroup = :weightGroup AND r.crane = :crane AND r.isExportImport = :isExportImport AND r.masterActivity.activityCode = :activityCode AND r.serviceVessel.noPpkb = :noPpkb AND r.operation = :operation AND r.activity = :activity AND r.masterCurrency.currCode = :currCode AND r.status = :status AND r.charge = :charge")})
public class RecapHandlingUc implements Serializable, EntityAuditable {
    private static final long serialVersionUID = 1L;
    
    public static final String OPERATION_LOAD = "LOAD";
    public static final String OPERATION_DISCHARGE = "DISCHARGE";
    public static final String ACTIVITY_LOAD = "LOAD";
    public static final String ACTIVITY_DISCHARGE = "DISCHARGE";
    public static final String ACTIVITY_SHIFTING = "SHIFTING";
    public static final String ACTIVITY_TRANSHIPMENT = "TRANSHIPMENT";
    public static final String ACTIVITY_RESHIPPING = "RESHIPPING";
    
    @Id
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Basic(optional = false)
    @Column(name = "crane", nullable = false, length = 2)
    private String crane;
    @Basic(optional = false)
    @Column(name = "weight_group", nullable = false)
    private int weightGroup;
    @Basic(optional = false)
    @Column(name = "is_export_import", nullable = false)
    private boolean isExportImport;
    @Basic(optional = false)
    @Column(name = "operation", nullable = false, length = 20)
    private String operation;
    @Basic(optional = false)
    @Column(name = "activity", nullable = false, length = 20)
    private String activity;
    @Basic(optional = false)
    @Column(name = "amount", nullable = false)
    private int amount;
    @Basic(optional = false)
    @Column(name = "charge", nullable = false, precision = 19, scale = 2)
    private BigDecimal charge;
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
    @JoinColumn(name = "no_ppkb", referencedColumnName = "no_ppkb", nullable = false)
    @ManyToOne(optional = false)
    private ServiceVessel serviceVessel;
    @JoinColumn(name = "curr_code", referencedColumnName = "curr_code", nullable = false)
    @ManyToOne(optional = false)
    private MasterCurrency masterCurrency;
    @JoinColumn(name = "activity_code", referencedColumnName = "activity_code", nullable = false)
    @ManyToOne(optional = false)
    private MasterActivity masterActivity;
    @Column(name = "status", length = 20)
    private String status;

    public RecapHandlingUc() {
    }

    public RecapHandlingUc(Integer id) {
        this.id = id;
    }

    public RecapHandlingUc(Integer id, String crane, int weightGroup, boolean isExportImport, String operation, String activity, int amount, BigDecimal charge, BigDecimal totalCharge, String createdBy, Date createdDate) {
        this.id = id;
        this.crane = crane;
        this.weightGroup = weightGroup;
        this.isExportImport = isExportImport;
        this.operation = operation;
        this.activity = activity;
        this.amount = amount;
        this.charge = charge;
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

    public String getCrane() {
        return crane;
    }

    public void setCrane(String crane) {
        this.crane = crane;
    }

    public int getWeightGroup() {
        return weightGroup;
    }

    public void setWeightGroup(int weightGroup) {
        this.weightGroup = weightGroup;
    }

    public boolean getIsExportImport() {
        return isExportImport;
    }

    public void setIsExportImport(boolean isExportImport) {
        this.isExportImport = isExportImport;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public BigDecimal getCharge() {
        return charge;
    }

    public void setCharge(BigDecimal charge) {
        this.charge = charge;
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

    public ServiceVessel getServiceVessel() {
        return serviceVessel;
    }

    public void setServiceVessel(ServiceVessel serviceVessel) {
        this.serviceVessel = serviceVessel;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
        if (!(object instanceof RecapHandlingUc)) {
            return false;
        }
        RecapHandlingUc other = (RecapHandlingUc) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.RecapHandlingUc[id=" + id + "]";
    }
}
