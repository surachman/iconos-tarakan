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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Seno
 */
@Entity
@Table(name = "recap_upah_buruh_hatch_move")
@EntityListeners({AuditTrailEntityListener.class})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RecapUpahBuruhHatchMove.findAll", query = "SELECT r FROM RecapUpahBuruhHatchMove r"),
    @NamedQuery(name = "RecapUpahBuruhHatchMove.findByBentuk3Constraint", query = "SELECT r FROM RecapUpahBuruhHatchMove r WHERE r.serviceVessel.noPpkb = :noPpkb AND r.crane = :crane"),
    @NamedQuery(name = "RecapUpahBuruhHatchMove.deleteByNoPpkb", query = "DELETE FROM RecapUpahBuruhHatchMove r WHERE r.serviceVessel.noPpkb = :noPpkb")})
public class RecapUpahBuruhHatchMove implements Serializable, EntityAuditable {
    private static final long serialVersionUID = 1L;
    public static final String OPERATION_LOAD = "LOAD";
    public static final String OPERATION_DISCHARGE = "DISCHARGE";
    
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;
    @Basic(optional = false)
    @Size(min = 1, max = 2)
    @Column(nullable = false, length = 2)
    private String crane;
    @Basic(optional = false)
    @Size(min = 1, max = 10)
    @Column(nullable = true, length = 10)
    private String operation;
    @Basic(optional = false)
    @Column(nullable = false)
    private int amount = 0;
    @Basic(optional = false)
    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal charge = BigDecimal.ZERO;
    @Basic(optional = false)
    @Column(name = "total_charge", nullable = false, precision = 19, scale = 2)
    private BigDecimal totalCharge = BigDecimal.ZERO;
    @Basic(optional = false)
    @Size(min = 1, max = 256)
    @Column(name = "created_by", nullable = false, length = 256)
    private String createdBy;
    @Basic(optional = false)
    @Column(name = "created_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Size(max = 256)
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

    public RecapUpahBuruhHatchMove() {
    }

    public RecapUpahBuruhHatchMove(Integer id) {
        this.id = id;
    }

    public RecapUpahBuruhHatchMove(Integer id, String crane, String operation, int amount, BigDecimal charge, BigDecimal totalCharge, String createdBy, Date createdDate) {
        this.id = id;
        this.crane = crane;
        this.operation = operation;
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

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RecapUpahBuruhHatchMove)) {
            return false;
        }
        RecapUpahBuruhHatchMove other = (RecapUpahBuruhHatchMove) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.RecapUpahBuruhHatchMove[ id=" + id + " ]";
    }
    
}
