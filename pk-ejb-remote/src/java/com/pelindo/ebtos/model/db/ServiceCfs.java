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
 * @author dycoder
 */
@Entity
@EntityListeners({AuditTrailEntityListener.class})
@Table(name = "service_cfs")
@NamedQueries({
    @NamedQuery(name = "ServiceCfs.findAll", query = "SELECT s FROM ServiceCfs s"),
    @NamedQuery(name = "ServiceCfs.findById", query = "SELECT s FROM ServiceCfs s WHERE s.id = :id"),
    @NamedQuery(name = "ServiceCfs.findByNoPpkb", query = "SELECT s FROM ServiceCfs s WHERE s.noPpkb = :noPpkb"),
    @NamedQuery(name = "ServiceCfs.findLastCfsByPpkbAndContNo", query = "SELECT s FROM ServiceCfs s WHERE s.noPpkb = :noPpkb AND s.contNo = :contNo AND s.isLast = TRUE"),
    @NamedQuery(name = "ServiceCfs.findNotFinishedYetByNoPpkbAndContNo", query = "SELECT s FROM ServiceCfs s WHERE s.noPpkb = :noPpkb AND s.contNo = :contNo AND s.endTime IS NULL")})
@NamedNativeQueries({
    @NamedNativeQuery(name = "ServiceCfs.Native.findStripping", query = "SELECT sc.id, sc.no_ppkb, sc.cont_no, sc.commodity_code, scd.is_cfs FROM service_cfs sc JOIN service_cont_discharge scd ON (sc.cont_no = scd.cont_no AND sc.no_ppkb = scd.no_ppkb) WHERE sc.end_time IS NULL AND scd.is_cfs = true"),
    @NamedNativeQuery(name = "ServiceCfs.Native.findByContNo", query = "SELECT id, no_ppkb, cont_no FROM service_cfs WHERE no_ppkb = ? AND cont_no = ? AND end_time IS NULL"),
    @NamedNativeQuery(name = "ServiceCfs.Native.findByPpkb", query = "SELECT cont_no, cont_type, cont_size, cont_status, cont_gross FROM service_cfs WHERE no_ppkb = ?"),
    @NamedNativeQuery(name = "ServiceCfs.Native.findIsLast", query = "SELECT id FROM service_cfs WHERE no_ppkb = ? AND cont_no = ? AND is_last = true")
})
public class ServiceCfs implements Serializable, EntityAuditable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "no_ppkb", length = 30)
    private String noPpkb;
    @Column(name = "cont_no")
    private String contNo;
    @Column(name = "cont_type")
    private Integer contType;
    @Column(name = "commodity_code")
    private String commodityCode;
    @Column(name = "cont_size")
    private Short contSize;
    @Column(name = "cont_status")
    private String contStatus;
    @Column(name = "cont_gross")
    private Integer contGross;
    @Column(name = "seal_no")
    private String sealNo;
    @Column(name = "over_size")
    private Boolean overSize;
    @Column(name = "dg")
    private Boolean dg;
    @Column(name = "dg_label")
    private Boolean dgLabel;
    @Column(name = "start_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;
    @Column(name = "end_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endTime;
    @Column(name = "operation")
    private String operation;
    @Column(name = "is_last")
    private Boolean isLast;
    @Column(name = "operation_status")
    private String operationStatus;
    @JoinColumn(name = "mlo", referencedColumnName = "cust_code")
    @ManyToOne
    private MasterCustomer mlo;
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


    public ServiceCfs() {
    }

    public ServiceCfs(ServiceContDischarge serviceContDischarge){
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
        this.blNo = serviceContDischarge.getBlNo();
        this.isLast = true;
    }

    public ServiceCfs(ServiceReceiving serviceReceiving){
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
        this.blNo = serviceReceiving.getBlNo();
        this.isLast = true;
    }

    public ServiceCfs(Integer id) {
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

    public Boolean getOverSize() {
        return overSize;
    }

    public void setOverSize(Boolean overSize) {
        this.overSize = overSize;
    }

    public Boolean getDg() {
        return dg;
    }

    public void setDg(Boolean dg) {
        this.dg = dg;
    }

    public Boolean getDgLabel() {
        return dgLabel;
    }

    public void setDgLabel(Boolean dgLabel) {
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

    public Boolean getIsLast() {
        return isLast;
    }

    public void setIsLast(Boolean isLast) {
        this.isLast = isLast;
    }

    public String getOperationStatus() {
        return operationStatus;
    }

    public void setOperationStatus(String operationStatus) {
        this.operationStatus = operationStatus;
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
        if (!(object instanceof ServiceCfs)) {
            return false;
        }
        ServiceCfs other = (ServiceCfs) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.ServiceCfs[id=" + id + "]";
    }
}
