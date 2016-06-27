/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.model.db;

import com.qtasnim.persistence.AuditTrailEntityListener;
import com.qtasnim.persistence.EntityAuditable;
import com.pelindo.ebtos.model.db.master.MasterContDamage;
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
 * @author Dyware-Dev01
 */
@Entity
@EntityListeners({AuditTrailEntityListener.class})
@Table(name = "temp_cont_discharge")
@NamedQueries({
    @NamedQuery(name = "TempContDischarge.findAll", query = "SELECT t FROM TempContDischarge t"),
    @NamedQuery(name = "TempContDischarge.findById", query = "SELECT t FROM TempContDischarge t WHERE t.id = :id"),
    @NamedQuery(name = "TempContDischarge.findByCommodityCode", query = "SELECT t FROM TempContDischarge t WHERE t.commodityCode = :commodityCode"),
    @NamedQuery(name = "TempContDischarge.findByContType", query = "SELECT t FROM TempContDischarge t WHERE t.contType = :contType"),
    @NamedQuery(name = "TempContDischarge.findByBlock", query = "SELECT t FROM TempContDischarge t WHERE t.block = :block"),
    @NamedQuery(name = "TempContDischarge.findByActivityCode", query = "SELECT t FROM TempContDischarge t WHERE t.activityCode = :activityCode"),
    @NamedQuery(name = "TempContDischarge.findByNoPpkb", query = "SELECT t FROM TempContDischarge t WHERE t.noPpkb = :noPpkb"),
    @NamedQuery(name = "TempContDischarge.findByCrane", query = "SELECT t FROM TempContDischarge t WHERE t.crane = :crane"),
    @NamedQuery(name = "TempContDischarge.findByContNo", query = "SELECT t FROM TempContDischarge t WHERE t.contNo = :contNo"),
    @NamedQuery(name = "TempContDischarge.findByContSize", query = "SELECT t FROM TempContDischarge t WHERE t.contSize = :contSize"),
    @NamedQuery(name = "TempContDischarge.findByContStatus", query = "SELECT t FROM TempContDischarge t WHERE t.contStatus = :contStatus"),
    @NamedQuery(name = "TempContDischarge.findByContGross", query = "SELECT t FROM TempContDischarge t WHERE t.contGross = :contGross"),
    @NamedQuery(name = "TempContDischarge.findBySealNo", query = "SELECT t FROM TempContDischarge t WHERE t.sealNo = :sealNo"),
    @NamedQuery(name = "TempContDischarge.findByOverSize", query = "SELECT t FROM TempContDischarge t WHERE t.overSize = :overSize"),
    @NamedQuery(name = "TempContDischarge.findByDangerous", query = "SELECT t FROM TempContDischarge t WHERE t.dangerous = :dangerous"),
    @NamedQuery(name = "TempContDischarge.findByDgLabel", query = "SELECT t FROM TempContDischarge t WHERE t.dgLabel = :dgLabel"),
    @NamedQuery(name = "TempContDischarge.findByVBay", query = "SELECT t FROM TempContDischarge t WHERE t.vBay = :vBay"),
    @NamedQuery(name = "TempContDischarge.findByVRow", query = "SELECT t FROM TempContDischarge t WHERE t.vRow = :vRow"),
    @NamedQuery(name = "TempContDischarge.findByVTier", query = "SELECT t FROM TempContDischarge t WHERE t.vTier = :vTier"),
    @NamedQuery(name = "TempContDischarge.findByYSlot", query = "SELECT t FROM TempContDischarge t WHERE t.ySlot = :ySlot"),
    @NamedQuery(name = "TempContDischarge.findByYRow", query = "SELECT t FROM TempContDischarge t WHERE t.yRow = :yRow"),
    @NamedQuery(name = "TempContDischarge.findByYTier", query = "SELECT t FROM TempContDischarge t WHERE t.yTier = :yTier"),
    @NamedQuery(name = "TempContDischarge.findByStartPlacementDate", query = "SELECT t FROM TempContDischarge t WHERE t.startPlacementDate = :startPlacementDate"),
    @NamedQuery(name = "TempContDischarge.findByPosition", query = "SELECT t FROM TempContDischarge t WHERE t.position = :position"),
    @NamedQuery(name = "TempContDischarge.findByIsDelivery", query = "SELECT t FROM TempContDischarge t WHERE t.isDelivery = :isDelivery"),
    @NamedQuery(name = "TempContDischarge.findByGrossClass", query = "SELECT t FROM TempContDischarge t WHERE t.grossClass = :grossClass"),
    @NamedQuery(name = "TempContDischarge.findByIsBehandle", query = "SELECT t FROM TempContDischarge t WHERE t.isBehandle = :isBehandle"),
    @NamedQuery(name = "TempContDischarge.findByIsCfs", query = "SELECT t FROM TempContDischarge t WHERE t.isCfs = :isCfs"),
    @NamedQuery(name = "TempContDischarge.findByIsInspection", query = "SELECT t FROM TempContDischarge t WHERE t.isInspection = :isInspection"),
    @NamedQuery(name = "TempContDischarge.findByExStripping", query = "SELECT t FROM TempContDischarge t WHERE t.exStripping = :exStripping"),
    @NamedQuery(name = "TempContDischarge.findByPositionBay", query = "SELECT t FROM TempContDischarge t WHERE t.positionBay = :positionBay"),
    @NamedQuery(name = "TempContDischarge.findByBlNo", query = "SELECT t FROM TempContDischarge t WHERE t.blNo = :blNo"),
    @NamedQuery(name = "TempContDischarge.findByCreatedBy", query = "SELECT t FROM TempContDischarge t WHERE t.createdBy = :createdBy"),
    @NamedQuery(name = "TempContDischarge.findByCreatedDate", query = "SELECT t FROM TempContDischarge t WHERE t.createdDate = :createdDate"),
    @NamedQuery(name = "TempContDischarge.findByModifiedBy", query = "SELECT t FROM TempContDischarge t WHERE t.modifiedBy = :modifiedBy"),
    @NamedQuery(name = "TempContDischarge.findByModifiedDate", query = "SELECT t FROM TempContDischarge t WHERE t.modifiedDate = :modifiedDate"),
    @NamedQuery(name = "TempContDischarge.findByCancelLoading", query = "SELECT t FROM TempContDischarge t WHERE t.cancelLoading = :cancelLoading"),
    @NamedQuery(name = "TempContDischarge.findByStatusReplacement", query = "SELECT t FROM TempContDischarge t WHERE t.statusReplacement = :statusReplacement")})
@NamedNativeQueries({
    @NamedNativeQuery(name = "TempContDischarge.Native.findTempContDischargeByStatus", query = "SELECT tcd.cont_no, tcd.cont_size, tcd.cont_status, tcd.v_bay, tcd.v_row, tcd.v_tier, tcd.id FROM temp_cont_discharge tcd WHERE tcd.position='03' AND tcd.no_ppkb=? AND tcd.status_replacement=?"),
    @NamedNativeQuery(name = "TempContDischarge.Native.findTempContDischargeByPpkb", query = "SELECT id FROM temp_cont_discharge WHERE no_ppkb=?"),
    @NamedNativeQuery(name = "TempContDischarge.Native.findByContNo", query = "SELECT cont_no, start_placement_date, v_bay, v_row, v_tier, id, y_slot, y_row, y_tier, block, no_ppkb, cont_size, 't' as origin FROM temp_cont_discharge WHERE cont_no = ? AND position = ?")
})
public class TempContDischarge implements Serializable, EntityAuditable {
    private static final long serialVersionUID = 1L;
    public static final String UNREPLACED = "UNREPLACED";

    @Id
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Basic(optional = false)
    @Column(name = "commodity_code", length = 5)
    private String commodityCode;
    @Basic(optional = false)
    @Column(name = "cont_type", nullable = false)
    private int contType;
    @Column(name = "block", length = 5)
    private String block;
    @Column(name = "activity_code", length = 10)
    private String activityCode;
    @Column(name = "no_ppkb", length = 30)
    private String noPpkb;
    @Column(name = "crane", length = 2)
    private String crane;
    @Basic(optional = false)
    @Column(name = "cont_no", nullable = false, length = 11)
    private String contNo;
    @Basic(optional = false)
    @Column(name = "cont_size", nullable = false)
    private short contSize;
    @Basic(optional = false)
    @Column(name = "cont_status", nullable = false, length = 5)
    private String contStatus;
    @Column(name = "cont_gross")
    private Integer contGross;
    @Column(name = "seal_no", length = 10)
    private String sealNo;
    @Column(name = "over_size")
    private Boolean overSize;
    @Column(name = "dangerous")
    private Boolean dangerous;
    @Column(name = "dg_label")
    private Boolean dgLabel;
    @Column(name = "v_bay")
    private Short vBay;
    @Column(name = "v_row")
    private Short vRow;
    @Column(name = "v_tier")
    private Short vTier;
    @Column(name = "y_slot")
    private Short ySlot;
    @Column(name = "y_row")
    private Short yRow;
    @Column(name = "y_tier")
    private Short yTier;
    @Column(name = "start_placement_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startPlacementDate;
    @Column(name = "position", length = 2)
    private String position;
    @Column(name = "is_delivery")
    private Boolean isDelivery;
    @Column(name = "gross_class", length = 5)
    private String grossClass;
    @Column(name = "is_behandle")
    private Boolean isBehandle;
    @Column(name = "is_cfs")
    private Boolean isCfs;
    @Column(name = "is_inspection")
    private Boolean isInspection;
    @Column(name = "ex_stripping")
    private Boolean exStripping;
    @Column(name = "position_bay", length = 2147483647)
    private String positionBay;
    @Column(name = "bl_no")
    private String blNo;
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
    @Basic(optional = false)
    @Column(name = "cancel_loading", nullable = false)
    private boolean cancelLoading;
    @Column(name = "status_replacement", length = 10)
    private String statusReplacement;
    @JoinColumn(name = "mlo", referencedColumnName = "cust_code")
    @ManyToOne
    private MasterCustomer mlo;
    @JoinColumn(name = "cont_damage", referencedColumnName = "id")
    @ManyToOne
    private MasterContDamage masterContDamage;

    public TempContDischarge() {
    }

    public TempContDischarge(Integer id) {
        this.id = id;
    }

    public TempContDischarge(Integer id, String commodityCode, int contType, String contNo, short contSize, String contStatus, String createdBy, Date createdDate, boolean cancelLoading, MasterCustomer mlo) {
        this.id = id;
        this.commodityCode = commodityCode;
        this.contType = contType;
        this.contNo = contNo;
        this.mlo = mlo;
        this.contSize = contSize;
        this.contStatus = contStatus;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.cancelLoading = cancelLoading;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCommodityCode() {
        return commodityCode;
    }

    public void setCommodityCode(String commodityCode) {
        this.commodityCode = commodityCode;
    }

    public int getContType() {
        return contType;
    }

    public void setContType(int contType) {
        this.contType = contType;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public String getActivityCode() {
        return activityCode;
    }

    public void setActivityCode(String activityCode) {
        this.activityCode = activityCode;
    }

    public String getNoPpkb() {
        return noPpkb;
    }

    public void setNoPpkb(String noPpkb) {
        this.noPpkb = noPpkb;
    }

    public String getCrane() {
        return crane;
    }

    public void setCrane(String crane) {
        this.crane = crane;
    }

    public String getContNo() {
        return contNo;
    }

    public void setContNo(String contNo) {
        this.contNo = contNo;
    }

    public short getContSize() {
        return contSize;
    }

    public void setContSize(short contSize) {
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

    public Boolean getDangerous() {
        return dangerous;
    }

    public void setDangerous(Boolean dangerous) {
        this.dangerous = dangerous;
    }

    public Boolean getDgLabel() {
        return dgLabel;
    }

    public void setDgLabel(Boolean dgLabel) {
        this.dgLabel = dgLabel;
    }

    public Short getVBay() {
        return vBay;
    }

    public void setVBay(Short vBay) {
        this.vBay = vBay;
    }

    public Short getVRow() {
        return vRow;
    }

    public void setVRow(Short vRow) {
        this.vRow = vRow;
    }

    public Short getVTier() {
        return vTier;
    }

    public void setVTier(Short vTier) {
        this.vTier = vTier;
    }

    public Short getYSlot() {
        return ySlot;
    }

    public void setYSlot(Short ySlot) {
        this.ySlot = ySlot;
    }

    public Short getYRow() {
        return yRow;
    }

    public void setYRow(Short yRow) {
        this.yRow = yRow;
    }

    public Short getYTier() {
        return yTier;
    }

    public void setYTier(Short yTier) {
        this.yTier = yTier;
    }

    public Date getStartPlacementDate() {
        return startPlacementDate;
    }

    public void setStartPlacementDate(Date startPlacementDate) {
        this.startPlacementDate = startPlacementDate;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Boolean getIsDelivery() {
        return isDelivery;
    }

    public void setIsDelivery(Boolean isDelivery) {
        this.isDelivery = isDelivery;
    }

    public String getGrossClass() {
        return grossClass;
    }

    public void setGrossClass(String grossClass) {
        this.grossClass = grossClass;
    }

    public Boolean getIsBehandle() {
        return isBehandle;
    }

    public void setIsBehandle(Boolean isBehandle) {
        this.isBehandle = isBehandle;
    }

    public Boolean getIsCfs() {
        return isCfs;
    }

    public void setIsCfs(Boolean isCfs) {
        this.isCfs = isCfs;
    }

    public Boolean getIsInspection() {
        return isInspection;
    }

    public void setIsInspection(Boolean isInspection) {
        this.isInspection = isInspection;
    }

    public Boolean getExStripping() {
        return exStripping;
    }

    public void setExStripping(Boolean exStripping) {
        this.exStripping = exStripping;
    }

    public String getPositionBay() {
        return positionBay;
    }

    public void setPositionBay(String positionBay) {
        this.positionBay = positionBay;
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

    public boolean getCancelLoading() {
        return cancelLoading;
    }

    public void setCancelLoading(boolean cancelLoading) {
        this.cancelLoading = cancelLoading;
    }

    public String getStatusReplacement() {
        return statusReplacement;
    }

    public void setStatusReplacement(String statusReplacement) {
        this.statusReplacement = statusReplacement;
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

    public MasterContDamage getMasterContDamage() {
        return masterContDamage;
    }

    public void setMasterContDamage(MasterContDamage masterContDamage) {
        this.masterContDamage = masterContDamage;
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
        if (!(object instanceof TempContDischarge)) {
            return false;
        }
        TempContDischarge other = (TempContDischarge) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.TempContDischarge[id=" + id + "]";
    }
}
