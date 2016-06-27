/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.model.db.master;

import com.qtasnim.persistence.AuditTrailEntityListener;
import com.qtasnim.persistence.EntityAuditable;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author senoanggoro
 */
@Entity
@EntityListeners({AuditTrailEntityListener.class})
@Table(name = "m_commodity_type")
@NamedQueries({
    @NamedQuery(name = "MasterCommodityType.findAll", query = "SELECT m FROM MasterCommodityType m"),
    @NamedQuery(name = "MasterCommodityType.findByCommodityTypeCode", query = "SELECT m FROM MasterCommodityType m WHERE m.commodityTypeCode = :commodityTypeCode"),
    @NamedQuery(name = "MasterCommodityType.findByName", query = "SELECT m FROM MasterCommodityType m WHERE m.name = :name")})

    @NamedNativeQueries({
    @NamedNativeQuery(name ="MasterCommodityType.Native.findMasterCommodityTypes",query="SELECT m.commodity_type_code,m.name FROM m_commodity_type m"),
    @NamedNativeQuery(name="MasterCommodityType.Native.findMasterCommodityTypeCode",query="SELECT m.commodity_type_code,m.name FROM m_commodity_type m WHERE m.commodity_type_code = ?"),
    @NamedNativeQuery(name="MasterCommodityType.Native.findMasterCommodityByGenerate",query="SELECT MAX(substring(commodity_type_code,1,3))FROM m_commodity_type")})
public class MasterCommodityType implements Serializable, EntityAuditable {
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

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "commodity_type_code", nullable = false, length = 5)
    private String commodityTypeCode;
    @Basic(optional = false)
    @Column(name = "name", nullable = false, length = 50)
    private String name;
    @OneToMany(mappedBy = "masterCommodityType")
    private List<MasterCommodity> masterCommodityList;

    public MasterCommodityType() {
    }

    public MasterCommodityType(String commodityTypeCode) {
        this.commodityTypeCode = commodityTypeCode;
    }

    public MasterCommodityType(String commodityTypeCode, String name) {
        this.commodityTypeCode = commodityTypeCode;
        this.name = name;
    }

    public String getCommodityTypeCode() {
        return commodityTypeCode;
    }

    public void setCommodityTypeCode(String commodityTypeCode) {
        this.commodityTypeCode = commodityTypeCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<MasterCommodity> getMasterCommodityList() {
        return masterCommodityList;
    }

    public void setMasterCommodityList(List<MasterCommodity> masterCommodityList) {
        this.masterCommodityList = masterCommodityList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (commodityTypeCode != null ? commodityTypeCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MasterCommodityType)) {
            return false;
        }
        MasterCommodityType other = (MasterCommodityType) object;
        if ((this.commodityTypeCode == null && other.commodityTypeCode != null) || (this.commodityTypeCode != null && !this.commodityTypeCode.equals(other.commodityTypeCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.MasterCommodityType[commodityTypeCode=" + commodityTypeCode + "]";
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

}
