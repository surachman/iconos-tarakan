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
 * @author Dyware-Dev01
 */
@Entity
@EntityListeners({AuditTrailEntityListener.class})
@Table(name = "replacement_cont_discharge")
@NamedQueries({
    @NamedQuery(name = "ReplacementContDischarge.findAll", query = "SELECT r FROM ReplacementContDischarge r"),
    @NamedQuery(name = "ReplacementContDischarge.findById", query = "SELECT r FROM ReplacementContDischarge r WHERE r.id = :id"),
    @NamedQuery(name = "ReplacementContDischarge.findByCommodityCode", query = "SELECT r FROM ReplacementContDischarge r WHERE r.commodityCode = :commodityCode"),
    @NamedQuery(name = "ReplacementContDischarge.findByContType", query = "SELECT r FROM ReplacementContDischarge r WHERE r.contType = :contType"),
    @NamedQuery(name = "ReplacementContDischarge.findByBlock", query = "SELECT r FROM ReplacementContDischarge r WHERE r.block = :block"),
    @NamedQuery(name = "ReplacementContDischarge.findByActivityCode", query = "SELECT r FROM ReplacementContDischarge r WHERE r.activityCode = :activityCode"),
    @NamedQuery(name = "ReplacementContDischarge.findByNoPpkb", query = "SELECT r FROM ReplacementContDischarge r WHERE r.noPpkb = :noPpkb"),
    @NamedQuery(name = "ReplacementContDischarge.findByCrane", query = "SELECT r FROM ReplacementContDischarge r WHERE r.crane = :crane"),
    @NamedQuery(name = "ReplacementContDischarge.findByContNo", query = "SELECT r FROM ReplacementContDischarge r WHERE r.contNo = :contNo"),
    @NamedQuery(name = "ReplacementContDischarge.findByContSize", query = "SELECT r FROM ReplacementContDischarge r WHERE r.contSize = :contSize"),
    @NamedQuery(name = "ReplacementContDischarge.findByContStatus", query = "SELECT r FROM ReplacementContDischarge r WHERE r.contStatus = :contStatus"),
    @NamedQuery(name = "ReplacementContDischarge.findByContGross", query = "SELECT r FROM ReplacementContDischarge r WHERE r.contGross = :contGross"),
    @NamedQuery(name = "ReplacementContDischarge.findBySealNo", query = "SELECT r FROM ReplacementContDischarge r WHERE r.sealNo = :sealNo"),
    @NamedQuery(name = "ReplacementContDischarge.findByOverSize", query = "SELECT r FROM ReplacementContDischarge r WHERE r.overSize = :overSize"),
    @NamedQuery(name = "ReplacementContDischarge.findByDangerous", query = "SELECT r FROM ReplacementContDischarge r WHERE r.dangerous = :dangerous"),
    @NamedQuery(name = "ReplacementContDischarge.findByDgLabel", query = "SELECT r FROM ReplacementContDischarge r WHERE r.dgLabel = :dgLabel"),
    @NamedQuery(name = "ReplacementContDischarge.findByVBay", query = "SELECT r FROM ReplacementContDischarge r WHERE r.vBay = :vBay"),
    @NamedQuery(name = "ReplacementContDischarge.findByVRow", query = "SELECT r FROM ReplacementContDischarge r WHERE r.vRow = :vRow"),
    @NamedQuery(name = "ReplacementContDischarge.findByVTier", query = "SELECT r FROM ReplacementContDischarge r WHERE r.vTier = :vTier"),
    @NamedQuery(name = "ReplacementContDischarge.findByYSlot", query = "SELECT r FROM ReplacementContDischarge r WHERE r.ySlot = :ySlot"),
    @NamedQuery(name = "ReplacementContDischarge.findByYRow", query = "SELECT r FROM ReplacementContDischarge r WHERE r.yRow = :yRow"),
    @NamedQuery(name = "ReplacementContDischarge.findByYTier", query = "SELECT r FROM ReplacementContDischarge r WHERE r.yTier = :yTier"),
    @NamedQuery(name = "ReplacementContDischarge.findByStartPlacementDate", query = "SELECT r FROM ReplacementContDischarge r WHERE r.startPlacementDate = :startPlacementDate"),
    @NamedQuery(name = "ReplacementContDischarge.findByPosition", query = "SELECT r FROM ReplacementContDischarge r WHERE r.position = :position"),
    @NamedQuery(name = "ReplacementContDischarge.findByIsDelivery", query = "SELECT r FROM ReplacementContDischarge r WHERE r.isDelivery = :isDelivery"),
    @NamedQuery(name = "ReplacementContDischarge.findByGrossClass", query = "SELECT r FROM ReplacementContDischarge r WHERE r.grossClass = :grossClass"),
    @NamedQuery(name = "ReplacementContDischarge.findByIsBehandle", query = "SELECT r FROM ReplacementContDischarge r WHERE r.isBehandle = :isBehandle"),
    @NamedQuery(name = "ReplacementContDischarge.findByIsCfs", query = "SELECT r FROM ReplacementContDischarge r WHERE r.isCfs = :isCfs"),
    @NamedQuery(name = "ReplacementContDischarge.findByIsInspection", query = "SELECT r FROM ReplacementContDischarge r WHERE r.isInspection = :isInspection"),
    @NamedQuery(name = "ReplacementContDischarge.findByExStripping", query = "SELECT r FROM ReplacementContDischarge r WHERE r.exStripping = :exStripping"),
    @NamedQuery(name = "ReplacementContDischarge.findByPositionBay", query = "SELECT r FROM ReplacementContDischarge r WHERE r.positionBay = :positionBay"),
    @NamedQuery(name = "ReplacementContDischarge.findByBlNo", query = "SELECT r FROM ReplacementContDischarge r WHERE r.blNo = :blNo"),
    @NamedQuery(name = "ReplacementContDischarge.findByCreatedBy", query = "SELECT r FROM ReplacementContDischarge r WHERE r.createdBy = :createdBy"),
    @NamedQuery(name = "ReplacementContDischarge.findByCreatedDate", query = "SELECT r FROM ReplacementContDischarge r WHERE r.createdDate = :createdDate"),
    @NamedQuery(name = "ReplacementContDischarge.findByModifiedBy", query = "SELECT r FROM ReplacementContDischarge r WHERE r.modifiedBy = :modifiedBy"),
    @NamedQuery(name = "ReplacementContDischarge.findByModifiedDate", query = "SELECT r FROM ReplacementContDischarge r WHERE r.modifiedDate = :modifiedDate"),
    @NamedQuery(name = "ReplacementContDischarge.findByCancelLoading", query = "SELECT r FROM ReplacementContDischarge r WHERE r.cancelLoading = :cancelLoading"),
    @NamedQuery(name = "ReplacementContDischarge.findByOldContNo", query = "SELECT r FROM ReplacementContDischarge r WHERE r.oldContNo = :oldContNo"),
    @NamedQuery(name = "ReplacementContDischarge.findByOriginalContNo", query = "SELECT r FROM ReplacementContDischarge r WHERE r.originalContNo = :originalContNo")})
@NamedNativeQueries({
    @NamedNativeQuery(name = "ReplacementContDischarge.Native.findReplacementContDischargeByPPKB", query = "SELECT rcd.bl_no, rcd.cont_no, rcd.old_cont_no, rcd.cont_size, mct.type_in_general as name, rcd.cont_status, rcd.cont_gross, mc.name, rcd.seal_no, rcd.v_bay, rcd.v_row, rcd.v_tier, rcd.y_slot, rcd.y_row, rcd.y_tier, rcd.id, rcd.is_replacement FROM replacement_cont_discharge rcd, m_container_type mct, m_commodity mc WHERE rcd.cont_type=mct.cont_type AND rcd.commodity_code = mc.commodity_code AND rcd.no_ppkb=?")
})
public class ReplacementContDischarge implements Serializable, EntityAuditable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Basic(optional = false)
    @Column(name = "commodity_code", nullable = false, length = 5)
    private String commodityCode;
    @Basic(optional = false)
    @Column(name = "cont_type", nullable = false)
    private Integer contType;
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
    @Column(name = "old_cont_no", length = 11)
    private String oldContNo;
    @Column(name = "original_cont_no", length = 11)
    private String originalContNo;
    @Column(name = "id_service")
    private Integer idService;
    @Column(name = "id_temp")
    private Integer idTemp;
    @Column(name = "is_replacement")
    private Boolean isReplacement;
    @Column(name = "is_new")
    private Boolean isNew;
    @JoinColumn(name = "mlo", referencedColumnName = "cust_code")
    @ManyToOne
    private MasterCustomer mlo;

    public ReplacementContDischarge() {
    }

    public ReplacementContDischarge(Integer id) {
        this.id = id;
    }

    public ReplacementContDischarge(Integer id, String commodityCode, int contType, String contNo, short contSize, String contStatus, String createdBy, Date createdDate, boolean cancelLoading, MasterCustomer mlo) {
        this.id = id;
        this.commodityCode = commodityCode;
        this.contType = contType;
        this.contNo = contNo;
        this.contSize = contSize;
        this.contStatus = contStatus;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.cancelLoading = cancelLoading;
        this.mlo = mlo;
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

    public Integer getContType() {
        return contType;
    }

    public void setContType(Integer contType) {
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

    public String getOldContNo() {
        return oldContNo;
    }

    public void setOldContNo(String oldContNo) {
        this.oldContNo = oldContNo;
    }

    public String getOriginalContNo() {
        return originalContNo;
    }

    public void setOriginalContNo(String originalContNo) {
        this.originalContNo = originalContNo;
    }

    /**
     * @return the idService
     */
    public Integer getIdService() {
        return idService;
    }

    /**
     * @param idService the idService to set
     */
    public void setIdService(Integer idService) {
        this.idService = idService;
    }

    /**
     * @return the idTemp
     */
    public Integer getIdTemp() {
        return idTemp;
    }

    /**
     * @param idTemp the idTemp to set
     */
    public void setIdTemp(Integer idTemp) {
        this.idTemp = idTemp;
    }

    /**
     * @return the isReplacement
     */
    public Boolean getIsReplacement() {
        return isReplacement;
    }

    /**
     * @param isReplacement the isReplacement to set
     */
    public void setIsReplacement(Boolean isReplacement) {
        this.isReplacement = isReplacement;
    }

    public Boolean getIsNew() {
        return isNew;
    }

    public void setIsNew(Boolean isNew) {
        this.isNew = isNew;
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
        if (!(object instanceof ReplacementContDischarge)) {
            return false;
        }
        ReplacementContDischarge other = (ReplacementContDischarge) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.ReplacementContDischarge[id=" + id + "]";
    }
}
