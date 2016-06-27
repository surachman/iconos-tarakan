/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.model.db;

import com.pelindo.ebtos.model.db.master.MasterActivity;
import com.pelindo.ebtos.model.db.master.MasterCurrency;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
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
 * @author x42jr
 */
@Entity
@Table(name = "recap_jasa_dermaga_uc")
@NamedQueries({
    @NamedQuery(name = "RecapJasaDermagaUc.findAll", query = "SELECT r FROM RecapJasaDermagaUc r"),
    @NamedQuery(name = "RecapJasaDermagaUc.findById", query = "SELECT r FROM RecapJasaDermagaUc r WHERE r.id = :id"),
    @NamedQuery(name = "RecapJasaDermagaUc.findByBlNo", query = "SELECT r FROM RecapJasaDermagaUc r WHERE r.blNo = :blNo"),
    @NamedQuery(name = "RecapJasaDermagaUc.findByOperation", query = "SELECT r FROM RecapJasaDermagaUc r WHERE r.operation = :operation"),
    @NamedQuery(name = "RecapJasaDermagaUc.findByActivity", query = "SELECT r FROM RecapJasaDermagaUc r WHERE r.activity = :activity"),
    @NamedQuery(name = "RecapJasaDermagaUc.findByCharge", query = "SELECT r FROM RecapJasaDermagaUc r WHERE r.charge = :charge"),
    @NamedQuery(name = "RecapJasaDermagaUc.findByTotalCharge", query = "SELECT r FROM RecapJasaDermagaUc r WHERE r.totalCharge = :totalCharge"),
    @NamedQuery(name = "RecapJasaDermagaUc.findByCreatedBy", query = "SELECT r FROM RecapJasaDermagaUc r WHERE r.createdBy = :createdBy"),
    @NamedQuery(name = "RecapJasaDermagaUc.findByCreatedDate", query = "SELECT r FROM RecapJasaDermagaUc r WHERE r.createdDate = :createdDate"),
    @NamedQuery(name = "RecapJasaDermagaUc.findByModifiedBy", query = "SELECT r FROM RecapJasaDermagaUc r WHERE r.modifiedBy = :modifiedBy"),
    @NamedQuery(name = "RecapJasaDermagaUc.findByModifiedDate", query = "SELECT r FROM RecapJasaDermagaUc r WHERE r.modifiedDate = :modifiedDate")})
public class RecapJasaDermagaUc implements Serializable {
    public static final String OPERATION_LOAD = "LOAD";
    public static final String OPERATION_DISCHARGE = "DISCHARGE";
    public static final String OPERATION_TRANSHIPMENT = "TRANSHIPMENT";
    public static final String OPERATION_SHIFTING = "SHIFTING";
    public static final String OPERATION_RESHIPPING = "RESHIPPING";
    
    public static final String ACTIVITY_LOAD = "LOAD";
    public static final String ACTIVITY_DISCHARGE = "DISCHARGE";

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Basic(optional = false)
    @Column(name = "bl_no", nullable = false, length = 100)
    private String blNo;
    @Basic(optional = false)
    @Column(name = "operation", nullable = false, length = 15)
    private String operation;
    @Basic(optional = false)
    @Column(name = "activity", nullable = false, length = 20)
    private String activity;
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

    public RecapJasaDermagaUc() {
    }

    public RecapJasaDermagaUc(Integer id) {
        this.id = id;
    }

    public RecapJasaDermagaUc(Integer id, String blNo, String operation, String activity, BigDecimal charge, BigDecimal totalCharge, String createdBy, Date createdDate) {
        this.id = id;
        this.blNo = blNo;
        this.operation = operation;
        this.activity = activity;
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

    public String getBlNo() {
        return blNo;
    }

    public void setBlNo(String blNo) {
        this.blNo = blNo;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RecapJasaDermagaUc)) {
            return false;
        }
        RecapJasaDermagaUc other = (RecapJasaDermagaUc) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.RecapJasaDermagaUc[id=" + id + "]";
    }

}
