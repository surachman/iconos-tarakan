/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.model.db.master;

import com.qtasnim.persistence.AuditTrailEntityListener;
import com.qtasnim.persistence.EntityAuditable;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import javax.annotation.Generated;
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
 * @author senoanggoro
 */
@Entity
@EntityListeners({AuditTrailEntityListener.class})
@Table(name = "m_discount")
@NamedQueries({
    @NamedQuery(name = "MasterDiscount.findAll", query = "SELECT m FROM MasterDiscount m"),
    @NamedQuery(name = "MasterDiscount.findById", query = "SELECT m FROM MasterDiscount m WHERE m.id = :id"),
    @NamedQuery(name = "MasterDiscount.findByDiscountAmount", query = "SELECT m FROM MasterDiscount m WHERE m.discountAmount = :discountAmount")})
@NamedNativeQueries({
    @NamedNativeQuery(name = "MasterDiscount.Native.getValidDiscount", query = "SELECT nvl(discount_amount), discount_amount_as_money "
                                                                                + "FROM m_discount "
                                                                                + "WHERE cust_code = ? AND activity_code = ? AND valid_date <= now()::date"),
   @NamedNativeQuery(name = "MasterDiscount.Native.findAllDiscount", query = "select m.activity_code,ma.description,r.name,m.discount_amount,m.valid_date,m.id from m_discount m ,m_activity ma,m_customer r WHERE m.activity_code=ma.activity_code AND m.cust_code=r.cust_code order by id DESC")
})
public class MasterDiscount implements Serializable, EntityAuditable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @Column(name = "discount_amount")
    private BigDecimal discountAmount;
    @JoinColumn(name = "cust_code", referencedColumnName = "cust_code")
    @ManyToOne
    private MasterCustomer masterCustomer;
    @JoinColumn(name = "activity_code", referencedColumnName = "activity_code")
    @ManyToOne
    private MasterActivity masterActivity;
    @Column(name = "discount_amount_as_money", precision = 12, scale = 2)
    private BigDecimal discountAmountAsMoney;
    @Basic(optional = false)
    @Column(name = "valid_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date validDate;
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

    public MasterDiscount() {
    }

    public MasterDiscount(Integer id) {
        this.id = id;
    }

    public MasterDiscount(Integer id, BigDecimal discountAmount) {
        this.id = id;
        this.discountAmount = discountAmount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    public MasterCustomer getMasterCustomer() {
        return masterCustomer;
    }

    public void setMasterCustomer(MasterCustomer masterCustomer) {
        this.masterCustomer = masterCustomer;
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
        if (!(object instanceof MasterDiscount)) {
            return false;
        }
        MasterDiscount other = (MasterDiscount) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.MasterDiscount[id=" + id + "]";
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

    public Date getValidDate() {
        return validDate;
    }

    public void setValidDate(Date validDate) {
        this.validDate = validDate;
    }

    public BigDecimal getDiscountAmountAsMoney() {
        return discountAmountAsMoney;
    }

    public void setDiscountAmountAsMoney(BigDecimal discountAmountAsMoney) {
        this.discountAmountAsMoney = discountAmountAsMoney;
    }

}
