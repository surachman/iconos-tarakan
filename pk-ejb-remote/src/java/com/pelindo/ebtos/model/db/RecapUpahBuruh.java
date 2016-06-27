/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.model.db;

import com.pelindo.ebtos.model.db.master.MasterActivity;
import com.pelindo.ebtos.model.db.master.MasterContainerTypeInGeneral;
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
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author R Seno Anggoro
 */
@Entity
@EntityListeners({AuditTrailEntityListener.class})
@Table(name = "recap_upah_buruh")
@NamedQueries({
    @NamedQuery(name = "RecapUpahBuruh.findAll", query = "SELECT r FROM RecapUpahBuruh r"),
    @NamedQuery(name = "RecapUpahBuruh.findById", query = "SELECT r FROM RecapUpahBuruh r WHERE r.id = :id"),
    @NamedQuery(name = "RecapUpahBuruh.deleteByNoPpkb", query = "DELETE FROM RecapUpahBuruh r WHERE r.serviceVessel.noPpkb = :noPpkb"),
    @NamedQuery(name = "RecapUpahBuruh.findByAmount", query = "SELECT r FROM RecapUpahBuruh r WHERE r.amount = :amount"),
    @NamedQuery(name = "RecapUpahBuruh.findByCharge", query = "SELECT r FROM RecapUpahBuruh r WHERE r.charge = :charge"),
    @NamedQuery(name = "RecapUpahBuruh.findByTotalCharge", query = "SELECT r FROM RecapUpahBuruh r WHERE r.totalCharge = :totalCharge"),
    @NamedQuery(name = "RecapUpahBuruh.findByCreatedBy", query = "SELECT r FROM RecapUpahBuruh r WHERE r.createdBy = :createdBy"),
    @NamedQuery(name = "RecapUpahBuruh.findByCreatedDate", query = "SELECT r FROM RecapUpahBuruh r WHERE r.createdDate = :createdDate"),
    @NamedQuery(name = "RecapUpahBuruh.findByModifiedBy", query = "SELECT r FROM RecapUpahBuruh r WHERE r.modifiedBy = :modifiedBy"),
    @NamedQuery(name = "RecapUpahBuruh.findByModifiedDate", query = "SELECT r FROM RecapUpahBuruh r WHERE r.modifiedDate = :modifiedDate"),
    @NamedQuery(name = "RecapUpahBuruh.findByActivityCodeAndNoPpkb", query = "SELECT r FROM RecapUpahBuruh r WHERE r.masterActivity.activityCode = :activityCode AND r.serviceVessel.noPpkb = :noPpkb"),
    @NamedQuery(name = "RecapUpahBuruh.findByBentuk3Constraint", query = "SELECT r FROM RecapUpahBuruh r WHERE r.contStatus = :contStatus AND r.crane = :crane AND r.isExportImport = :isExportImport AND r.sling = :sling AND r.containerType.id = :containerType AND r.masterActivity.activityCode = :activityCode AND r.operation = :operation AND r.activity = :activity AND r.serviceVessel.noPpkb = :noPpkb AND r.masterCurrency.currCode = :currCode AND r.openDoor = :openDoor AND r.contSize = :contSize AND r.twentyOneFeet = :twentyOneFeet AND r.charge = :charge"),
    @NamedQuery(name = "RecapUpahBuruh.findByBentuk3ConstraintWithStatus", query = "SELECT r FROM RecapUpahBuruh r WHERE r.contStatus = :contStatus AND r.crane = :crane AND r.isExportImport = :isExportImport AND r.sling = :sling AND r.containerType.id = :containerType AND r.masterActivity.activityCode = :activityCode AND r.operation = :operation AND r.activity = :activity AND r.serviceVessel.noPpkb = :noPpkb AND r.masterCurrency.currCode = :currCode AND r.status = :status AND r.openDoor = :openDoor AND r.contSize = :contSize AND r.twentyOneFeet = :twentyOneFeet AND r.charge = :charge")})
public class RecapUpahBuruh implements Serializable, EntityAuditable {
    public static final String OPERATION_LOAD = "LOAD";
    public static final String OPERATION_DISCHARGE = "DISCHARGE";
    public static final String ACTIVITY_LOAD = "LOAD";
    public static final String ACTIVITY_DISCHARGE = "DISCHARGE";
    public static final String ACTIVITY_SHIFTING = "SHIFTING";
    public static final String ACTIVITY_RESHIPPING = "RESHIPPING";
    public static final String ACTIVITY_TRANSHIPMENT = "TRANSHIPMENT";

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Basic(optional = false)
    @Column(name = "amount", nullable = false)
    private int amount;
    @Basic(optional = false)
    @Column(name = "charge", nullable = false, precision = 19, scale = 2)
    private BigDecimal charge;
    @Basic(optional = false)
    @Column(name = "total_charge", nullable = false, precision = 19, scale = 2)
    private BigDecimal totalCharge = BigDecimal.ZERO;
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
    @Column(name = "operation", length = 10, nullable = false)
    private String operation;
    @Basic(optional = false)
    @Column(name = "crane", length = 2)
    private String crane;
    @Basic(optional = false)
    @Column(name = "is_export_import", nullable = false)
    private boolean isExportImport = false;
    @Basic(optional = false)
    @Column(name = "sling", nullable = false)
    private boolean sling = false;
    @JoinColumn(name = "cont_type", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private MasterContainerTypeInGeneral containerType;
    @Column(name = "cont_status", length = 5)
    private String contStatus;
    @Basic(optional = false)
    @Column(name = "activity", nullable = false, length = 10)
    private String activity;
    @Column(name = "status", length = 20)
    private String status;
    @Basic(optional = false)
    @Column(name = "open_door", nullable = false)
    private boolean openDoor;
    @Basic(optional = false)
    @Column(name = "cont_size", nullable = false)
    private short contSize;
    @Basic(optional = false)
    @Column(name = "twenty_one_feet", nullable = false)
    private boolean twentyOneFeet = false;

    public RecapUpahBuruh() {
    }

    public RecapUpahBuruh(Integer id) {
        this.id = id;
    }

    public RecapUpahBuruh(Integer id, int amount, BigDecimal charge, BigDecimal totalCharge, String createdBy, Date createdDate) {
        this.id = id;
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

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getCrane() {
        return crane;
    }

    public void setCrane(String crane) {
        this.crane = crane;
    }

    public boolean getIsExportImport() {
        return isExportImport;
    }

    public void setIsExportImport(boolean isExportImport) {
        this.isExportImport = isExportImport;
    }

    public boolean getSling() {
        return sling;
    }

    public void setSling(boolean sling) {
        this.sling = sling;
    }

    public MasterContainerTypeInGeneral getContainerType() {
        return containerType;
    }

    public void setContainerType(MasterContainerTypeInGeneral containerType) {
        this.containerType = containerType;
    }

    public String getContStatus() {
        return contStatus;
    }

    public void setContStatus(String contStatus) {
        this.contStatus = contStatus;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean getOpenDoor() {
        return openDoor;
    }

    public void setOpenDoor(boolean openDoor) {
        this.openDoor = openDoor;
    }

    public short getContSize() {
        return contSize;
    }

    public void setContSize(short contSize) {
        this.contSize = contSize;
    }

    public boolean getTwentyOneFeet() {
        return twentyOneFeet;
    }

    public void setTwentyOneFeet(boolean twentyOneFeet) {
        this.twentyOneFeet = twentyOneFeet;
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
        if (!(object instanceof RecapUpahBuruh)) {
            return false;
        }
        RecapUpahBuruh other = (RecapUpahBuruh) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.RecapUpahBuruh[id=" + id + "]";
    }
}
