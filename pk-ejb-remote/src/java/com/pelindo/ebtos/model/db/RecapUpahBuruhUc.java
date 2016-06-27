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
@Table(name = "recap_upah_buruh_uc")
@EntityListeners({AuditTrailEntityListener.class})
@NamedQueries({
    @NamedQuery(name = "RecapUpahBuruhUc.findAll", query = "SELECT r FROM RecapUpahBuruhUc r"),
    @NamedQuery(name = "RecapUpahBuruhUc.findById", query = "SELECT r FROM RecapUpahBuruhUc r WHERE r.id = :id"),
    @NamedQuery(name = "RecapUpahBuruhUc.deleteByNoPpkb", query = "DELETE FROM RecapUpahBuruhUc r WHERE r.serviceVessel.noPpkb = :noPpkb"),
    @NamedQuery(name = "RecapUpahBuruhUc.findByCrane", query = "SELECT r FROM RecapUpahBuruhUc r WHERE r.crane = :crane"),
    @NamedQuery(name = "RecapUpahBuruhUc.findByWeightGroup", query = "SELECT r FROM RecapUpahBuruhUc r WHERE r.weightGroup = :weightGroup"),
    @NamedQuery(name = "RecapUpahBuruhUc.findByIsExportImport", query = "SELECT r FROM RecapUpahBuruhUc r WHERE r.isExportImport = :isExportImport"),
    @NamedQuery(name = "RecapUpahBuruhUc.findByOperation", query = "SELECT r FROM RecapUpahBuruhUc r WHERE r.operation = :operation"),
    @NamedQuery(name = "RecapUpahBuruhUc.findByActivity", query = "SELECT r FROM RecapUpahBuruhUc r WHERE r.activity = :activity"),
    @NamedQuery(name = "RecapUpahBuruhUc.findByAmount", query = "SELECT r FROM RecapUpahBuruhUc r WHERE r.amount = :amount"),
    @NamedQuery(name = "RecapUpahBuruhUc.findByCharge", query = "SELECT r FROM RecapUpahBuruhUc r WHERE r.charge = :charge"),
    @NamedQuery(name = "RecapUpahBuruhUc.findByTotalCharge", query = "SELECT r FROM RecapUpahBuruhUc r WHERE r.totalCharge = :totalCharge"),
    @NamedQuery(name = "RecapUpahBuruhUc.findByCreatedBy", query = "SELECT r FROM RecapUpahBuruhUc r WHERE r.createdBy = :createdBy"),
    @NamedQuery(name = "RecapUpahBuruhUc.findByCreatedDate", query = "SELECT r FROM RecapUpahBuruhUc r WHERE r.createdDate = :createdDate"),
    @NamedQuery(name = "RecapUpahBuruhUc.findByModifiedBy", query = "SELECT r FROM RecapUpahBuruhUc r WHERE r.modifiedBy = :modifiedBy"),
    @NamedQuery(name = "RecapUpahBuruhUc.findByModifiedDate", query = "SELECT r FROM RecapUpahBuruhUc r WHERE r.modifiedDate = :modifiedDate"),
    @NamedQuery(name = "RecapUpahBuruhUc.findByBentuk3Constraint", query = "SELECT r FROM RecapUpahBuruhUc r WHERE r.weightGroup = :weightGroup AND r.crane = :crane AND r.isExportImport = :isExportImport AND r.masterActivity.activityCode = :activityCode AND r.serviceVessel.noPpkb = :noPpkb AND r.operation = :operation AND r.activity = :activity AND r.masterCurrency.currCode = :currCode AND r.charge = :charge"),
    @NamedQuery(name = "RecapUpahBuruhUc.findByBentuk3ConstraintWithStatus", query = "SELECT r FROM RecapUpahBuruhUc r WHERE r.weightGroup = :weightGroup AND r.crane = :crane AND r.isExportImport = :isExportImport AND r.masterActivity.activityCode = :activityCode AND r.serviceVessel.noPpkb = :noPpkb AND r.operation = :operation AND r.activity = :activity AND r.masterCurrency.currCode = :currCode AND r.status = :status AND r.charge = :charge")})
public class RecapUpahBuruhUc implements Serializable, EntityAuditable {
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
    private int weightGroup = 0;
    @Basic(optional = false)
    @Column(name = "is_export_import", nullable = false)
    private boolean isExportImport = false;
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
    @Column(name = "status", length = 20)
    private String status;
    @JoinColumn(name = "no_ppkb", referencedColumnName = "no_ppkb", nullable = false)
    @ManyToOne(optional = false)
    private ServiceVessel serviceVessel;
    @JoinColumn(name = "curr_code", referencedColumnName = "curr_code", nullable = false)
    @ManyToOne(optional = false)
    private MasterCurrency masterCurrency;
    @JoinColumn(name = "activity_code", referencedColumnName = "activity_code", nullable = false)
    @ManyToOne(optional = false)
    private MasterActivity masterActivity;

    public RecapUpahBuruhUc() {
    }

    public RecapUpahBuruhUc(Integer id) {
        this.id = id;
    }

    public RecapUpahBuruhUc(Integer id, String crane, int weightGroup, boolean isExportImport, String operation, String activity, int amount, BigDecimal charge, BigDecimal totalCharge, String createdBy, Date createdDate) {
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
        if (!(object instanceof RecapUpahBuruhUc)) {
            return false;
        }
        RecapUpahBuruhUc other = (RecapUpahBuruhUc) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.RecapUpahBuruhUc[id=" + id + "]";
    }
}
