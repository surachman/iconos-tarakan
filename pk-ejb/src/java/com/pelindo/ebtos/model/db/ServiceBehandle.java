/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.model.db;

import com.qtasnim.persistence.AuditTrailEntityListener;
import com.qtasnim.persistence.EntityAuditable;
import com.pelindo.ebtos.model.db.master.MasterCustomer;
import java.io.Serializable;
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
 * @author senoanggoro
 */
@Entity
@EntityListeners({AuditTrailEntityListener.class})
@Table(name = "service_behandle")
@NamedQueries({
    @NamedQuery(name = "ServiceBehandle.findAll", query = "SELECT s FROM ServiceBehandle s"),
    @NamedQuery(name = "ServiceBehandle.findById", query = "SELECT s FROM ServiceBehandle s WHERE s.id = :id"),
    @NamedQuery(name = "ServiceBehandle.findByNoPpkb", query = "SELECT s FROM ServiceBehandle s WHERE s.noPpkb = :noPpkb"),
    @NamedQuery(name = "ServiceBehandle.findNotFinishedYetByNoPpkbAndContNo", query = "SELECT s FROM ServiceBehandle s WHERE s.noPpkb = :noPpkb AND s.contNo = :contNo AND s.endTime IS NULL"),
    @NamedQuery(name = "ServiceBehandle.findByContNo", query = "SELECT s FROM ServiceBehandle s WHERE s.contNo = :contNo"),
    @NamedQuery(name = "ServiceBehandle.findByContType", query = "SELECT s FROM ServiceBehandle s WHERE s.contType = :contType"),
    @NamedQuery(name = "ServiceBehandle.findByCommodityCode", query = "SELECT s FROM ServiceBehandle s WHERE s.commodityCode = :commodityCode"),
    @NamedQuery(name = "ServiceBehandle.findByContSize", query = "SELECT s FROM ServiceBehandle s WHERE s.contSize = :contSize"),
    @NamedQuery(name = "ServiceBehandle.findByContStatus", query = "SELECT s FROM ServiceBehandle s WHERE s.contStatus = :contStatus"),
    @NamedQuery(name = "ServiceBehandle.findByContGross", query = "SELECT s FROM ServiceBehandle s WHERE s.contGross = :contGross"),
    @NamedQuery(name = "ServiceBehandle.findBySealNo", query = "SELECT s FROM ServiceBehandle s WHERE s.sealNo = :sealNo"),
    @NamedQuery(name = "ServiceBehandle.findByOverSize", query = "SELECT s FROM ServiceBehandle s WHERE s.overSize = :overSize"),
    @NamedQuery(name = "ServiceBehandle.findByDg", query = "SELECT s FROM ServiceBehandle s WHERE s.dg = :dg"),
    @NamedQuery(name = "ServiceBehandle.findByDgLabel", query = "SELECT s FROM ServiceBehandle s WHERE s.dgLabel = :dgLabel"),
    @NamedQuery(name = "ServiceBehandle.findByStartTime", query = "SELECT s FROM ServiceBehandle s WHERE s.startTime = :startTime"),
    @NamedQuery(name = "ServiceBehandle.findByEndTime", query = "SELECT s FROM ServiceBehandle s WHERE s.endTime = :endTime")})
@NamedNativeQueries({
    @NamedNativeQuery(name = "ServiceBehandle.Native.findByContNo", query = "SELECT id, no_ppkb FROM service_behandle WHERE no_ppkb = ? AND cont_no = ? AND end_time IS NULL")
})
public class ServiceBehandle implements Serializable, EntityAuditable {
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
    @Column(name = "bl_no")
    private String blNo;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "no_ppkb", length = 30)
    private String noPpkb;
    @Column(name = "cont_no", length = 11)
    private String contNo;
    @Column(name = "cont_type")
    private Integer contType;
    @Column(name = "commodity_code", length = 5)
    private String commodityCode;
    @Column(name = "cont_size")
    private Short contSize;
    @Column(name = "cont_status", length = 5)
    private String contStatus;
    @Column(name = "cont_gross")
    private Integer contGross;
    @Column(name = "seal_no", length = 10)
    private String sealNo;
    @Column(name = "over_size")
    private String overSize;
    @Column(name = "dg")
    private String dg;
    @Column(name = "dg_label")
    private String dgLabel;
    @Column(name = "start_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;
    @Column(name = "end_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endTime;
    @Column(name = "operation", length =  10)
    private String operation;
    @JoinColumn(name = "mlo", referencedColumnName = "cust_code")
    @ManyToOne
    private MasterCustomer mlo;

    public ServiceBehandle() {
    }

    public ServiceBehandle(ServiceContDischarge serviceContDischarge){
        this.contNo = serviceContDischarge.getContNo();
        this.mlo = serviceContDischarge.getMlo();
        this.contSize = serviceContDischarge.getContSize();
        this.contStatus = serviceContDischarge.getContStatus();
        this.contType = serviceContDischarge.getMasterContainerType().getContType();
        this.contGross = serviceContDischarge.getContGross();
        this.commodityCode = serviceContDischarge.getMasterCommodity().getCommodityCode();
        this.overSize = serviceContDischarge.getOverSize();
        this.dg = serviceContDischarge.getDangerous();
        this.dgLabel = serviceContDischarge.getDgLabel();
        this.noPpkb = serviceContDischarge.getServiceVessel().getNoPpkb();
        this.sealNo = serviceContDischarge.getSealNo();
        this.operation = "DISCHARGE";
        this.blNo = serviceContDischarge.getBlNo();
    }

    public ServiceBehandle(ServiceReceiving serviceReceiving){
        this.contNo = serviceReceiving.getContNo();
        this.mlo = serviceReceiving.getMlo();
        this.contSize = serviceReceiving.getContSize();
        this.contStatus = serviceReceiving.getContStatus();
        this.contType = serviceReceiving.getMasterContainerType().getContType();
        this.contGross = serviceReceiving.getContGross();
        this.commodityCode = serviceReceiving.getMasterCommodity().getCommodityCode();
        this.overSize = serviceReceiving.getOverSize();        
        this.dg = serviceReceiving.getDangerous();
        this.dgLabel = serviceReceiving.getDgLabel();
        this.noPpkb = serviceReceiving.getPlanningVessel().getNoPpkb();
        this.sealNo = serviceReceiving.getSealNo();
        this.operation = "LOAD";
        this.blNo = serviceReceiving.getBlNo();
    }

    public ServiceBehandle(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNoPpkb() {
        return noPpkb;
    }

    public void setNoPpkb(String noPpkb) {
        this.noPpkb = noPpkb;
    }

    public String getContNo() {
        return contNo;
    }

    public void setContNo(String contNo) {
        this.contNo = contNo;
    }

    public Integer getContType() {
        return contType;
    }

    public void setContType(Integer contType) {
        this.contType = contType;
    }

    public String getCommodityCode() {
        return commodityCode;
    }

    public void setCommodityCode(String commodityCode) {
        this.commodityCode = commodityCode;
    }

    public Short getContSize() {
        return contSize;
    }

    public void setContSize(Short contSize) {
        this.contSize = contSize;
    }

    public String getContStatus() {
        return contStatus;
    }

    public void setContStatus(String contStatus) {
        this.contStatus = contStatus;
    }

    public Integer getContGross() {
        return contGross;
    }

    public void setContGross(Integer contGross) {
        this.contGross = contGross;
    }

    public String getSealNo() {
        return sealNo;
    }

    public void setSealNo(String sealNo) {
        this.sealNo = sealNo;
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

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
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

    /**
     * @return the mlo
     */
    public MasterCustomer getMlo() {
        return mlo;
    }

    /**
     * @param mlo the mlo to set
     */
    public void setMlo(MasterCustomer mlo) {
        this.mlo = mlo;
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
        if (!(object instanceof ServiceBehandle)) {
            return false;
        }
        ServiceBehandle other = (ServiceBehandle) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.ServiceBehandle[id=" + id + "]";
    }
}
