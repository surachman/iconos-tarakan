/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.model.db;

import com.qtasnim.persistence.AuditTrailEntityListener;
import com.qtasnim.persistence.EntityAuditable;
import com.pelindo.ebtos.model.db.master.MasterCustomer;
import com.pelindo.ebtos.util.GrossConverter;
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
@Table(name = "change_status_history")
@NamedQueries({
    @NamedQuery(name = "ChangeStatusHistory.findAll", query = "SELECT c FROM ChangeStatusHistory c"),
    @NamedQuery(name = "ChangeStatusHistory.findById", query = "SELECT c FROM ChangeStatusHistory c WHERE c.id = :id"),
    @NamedQuery(name = "ChangeStatusHistory.findByCommodityCode", query = "SELECT c FROM ChangeStatusHistory c WHERE c.commodityCode = :commodityCode"),
    @NamedQuery(name = "ChangeStatusHistory.findByContType", query = "SELECT c FROM ChangeStatusHistory c WHERE c.contType = :contType"),
    @NamedQuery(name = "ChangeStatusHistory.findByBlock", query = "SELECT c FROM ChangeStatusHistory c WHERE c.block = :block"),
    @NamedQuery(name = "ChangeStatusHistory.findByActivityCode", query = "SELECT c FROM ChangeStatusHistory c WHERE c.activityCode = :activityCode"),
    @NamedQuery(name = "ChangeStatusHistory.findByOldPpkb", query = "SELECT c FROM ChangeStatusHistory c WHERE c.oldPpkb = :oldPpkb"),
    @NamedQuery(name = "ChangeStatusHistory.findByNewPpkb", query = "SELECT c FROM ChangeStatusHistory c WHERE c.newPpkb = :newPpkb"),
    @NamedQuery(name = "ChangeStatusHistory.findByCrane", query = "SELECT c FROM ChangeStatusHistory c WHERE c.crane = :crane"),
    @NamedQuery(name = "ChangeStatusHistory.findByContNo", query = "SELECT c FROM ChangeStatusHistory c WHERE c.contNo = :contNo"),
    @NamedQuery(name = "ChangeStatusHistory.findByContSize", query = "SELECT c FROM ChangeStatusHistory c WHERE c.contSize = :contSize"),
    @NamedQuery(name = "ChangeStatusHistory.findByContStatus", query = "SELECT c FROM ChangeStatusHistory c WHERE c.contStatus = :contStatus"),
    @NamedQuery(name = "ChangeStatusHistory.findByContGross", query = "SELECT c FROM ChangeStatusHistory c WHERE c.contGross = :contGross"),
    @NamedQuery(name = "ChangeStatusHistory.findBySealNo", query = "SELECT c FROM ChangeStatusHistory c WHERE c.sealNo = :sealNo"),
    @NamedQuery(name = "ChangeStatusHistory.findByOverSize", query = "SELECT c FROM ChangeStatusHistory c WHERE c.overSize = :overSize"),
    @NamedQuery(name = "ChangeStatusHistory.findByDangerous", query = "SELECT c FROM ChangeStatusHistory c WHERE c.dangerous = :dangerous"),
    @NamedQuery(name = "ChangeStatusHistory.findByDgLabel", query = "SELECT c FROM ChangeStatusHistory c WHERE c.dgLabel = :dgLabel"),
    @NamedQuery(name = "ChangeStatusHistory.findByVBay", query = "SELECT c FROM ChangeStatusHistory c WHERE c.vBay = :vBay"),
    @NamedQuery(name = "ChangeStatusHistory.findByVRow", query = "SELECT c FROM ChangeStatusHistory c WHERE c.vRow = :vRow"),
    @NamedQuery(name = "ChangeStatusHistory.findByVTier", query = "SELECT c FROM ChangeStatusHistory c WHERE c.vTier = :vTier"),
    @NamedQuery(name = "ChangeStatusHistory.findByYSlot", query = "SELECT c FROM ChangeStatusHistory c WHERE c.ySlot = :ySlot"),
    @NamedQuery(name = "ChangeStatusHistory.findByYRow", query = "SELECT c FROM ChangeStatusHistory c WHERE c.yRow = :yRow"),
    @NamedQuery(name = "ChangeStatusHistory.findByYTier", query = "SELECT c FROM ChangeStatusHistory c WHERE c.yTier = :yTier"),
    @NamedQuery(name = "ChangeStatusHistory.findByStartPlacementDate", query = "SELECT c FROM ChangeStatusHistory c WHERE c.startPlacementDate = :startPlacementDate"),
    @NamedQuery(name = "ChangeStatusHistory.findByPosition", query = "SELECT c FROM ChangeStatusHistory c WHERE c.position = :position"),
    @NamedQuery(name = "ChangeStatusHistory.findByIsDelivery", query = "SELECT c FROM ChangeStatusHistory c WHERE c.isDelivery = :isDelivery"),
    @NamedQuery(name = "ChangeStatusHistory.findByGrossClass", query = "SELECT c FROM ChangeStatusHistory c WHERE c.grossClass = :grossClass"),
    @NamedQuery(name = "ChangeStatusHistory.findByIsBehandle", query = "SELECT c FROM ChangeStatusHistory c WHERE c.isBehandle = :isBehandle"),
    @NamedQuery(name = "ChangeStatusHistory.findByIsCfs", query = "SELECT c FROM ChangeStatusHistory c WHERE c.isCfs = :isCfs"),
    @NamedQuery(name = "ChangeStatusHistory.findByIsInspection", query = "SELECT c FROM ChangeStatusHistory c WHERE c.isInspection = :isInspection"),
    @NamedQuery(name = "ChangeStatusHistory.findByIsLifton", query = "SELECT c FROM ChangeStatusHistory c WHERE c.isLifton = :isLifton"),
    @NamedQuery(name = "ChangeStatusHistory.findByExStuffing", query = "SELECT c FROM ChangeStatusHistory c WHERE c.exStuffing = :exStuffing"),
    @NamedQuery(name = "ChangeStatusHistory.findByExStripping", query = "SELECT c FROM ChangeStatusHistory c WHERE c.exStripping = :exStripping"),
    @NamedQuery(name = "ChangeStatusHistory.findByPositionBay", query = "SELECT c FROM ChangeStatusHistory c WHERE c.positionBay = :positionBay"),
    @NamedQuery(name = "ChangeStatusHistory.findByBlNo", query = "SELECT c FROM ChangeStatusHistory c WHERE c.blNo = :blNo"),
    @NamedQuery(name = "ChangeStatusHistory.findByCreatedBy", query = "SELECT c FROM ChangeStatusHistory c WHERE c.createdBy = :createdBy"),
    @NamedQuery(name = "ChangeStatusHistory.findByCreatedDate", query = "SELECT c FROM ChangeStatusHistory c WHERE c.createdDate = :createdDate"),
    @NamedQuery(name = "ChangeStatusHistory.findByModifiedBy", query = "SELECT c FROM ChangeStatusHistory c WHERE c.modifiedBy = :modifiedBy"),
    @NamedQuery(name = "ChangeStatusHistory.findByModifiedDate", query = "SELECT c FROM ChangeStatusHistory c WHERE c.modifiedDate = :modifiedDate"),
    @NamedQuery(name = "ChangeStatusHistory.findByLoadPort", query = "SELECT c FROM ChangeStatusHistory c WHERE c.loadPort = :loadPort"),
    @NamedQuery(name = "ChangeStatusHistory.findByDischPort", query = "SELECT c FROM ChangeStatusHistory c WHERE c.dischPort = :dischPort"),
    @NamedQuery(name = "ChangeStatusHistory.findByChangeStatus", query = "SELECT c FROM ChangeStatusHistory c WHERE c.changeStatus = :changeStatus"),
    @NamedQuery(name = "ChangeStatusHistory.findTranshipmentsByOldPpkb", query = "SELECT c FROM ChangeStatusHistory c WHERE c.changeStatus = 'TRANSHIPMENT' AND c.oldPpkb = :noPpkb")})

@NamedNativeQueries({
    @NamedNativeQuery(name = "ChangeStatusHistory.Native.findByPpkbAndContNo", query = "SELECT r.id, r.old_ppkb FROM change_status_history r WHERE r.old_ppkb = ? AND r.cont_no = ?"),

/* 
* Updated by SRACH
* Tue 28 Oct 2014 11:14:15 AM WIT 
* Update Notes: Upgrade to Oracle 11g SQL Compliance
*/
/*        
    @NamedNativeQuery(name = "ChangeStatusHistory.Native.findListTranshipment", query = "SELECT ch.id, ch.bl_no, ch.cont_no, ct.name, ch.cont_gross, co.name, ch.seal_no, ch.block, ch.y_slot, ch.y_row, ch.y_tier, ch.load_port, ch.disch_port FROM change_status_history ch, m_commodity co, m_container_type ct WHERE ch.change_status = 'TRANSHIPMENT' AND ch.commodity_code = co.commodity_code AND ch.cont_type = ct.cont_type AND ch.old_ppkb = ?")
*/
    @NamedNativeQuery(name = "ChangeStatusHistory.Native.findListTranshipment", query = 
"SELECT ch.id, " 
+"  ch.bl_no, " 
+"  ch.cont_no, " 
+"  ct.name, " 
+"  ch.cont_gross, " 
+"  co.name, " 
+"  ch.seal_no, " 
+"  ch.block, " 
+"  ch.y_slot, " 
+"  ch.y_row, " 
+"  ch.y_tier, " 
+"  ch.load_port, " 
+"  ch.disch_port " 
+"FROM change_status_history ch, " 
+"  m_commodity co, " 
+"  m_container_type ct " 
+"WHERE dbms_lob.substr(ch.change_status,4000,1) = 'TRANSHIPMENT' " 
+"AND ch.commodity_code                          = co.commodity_code " 
+"AND ch.cont_type                               = ct.cont_type " 
+"AND ch.old_ppkb                                = ?")   
 
})

public class ChangeStatusHistory implements Serializable, EntityAuditable  {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Basic(optional = false)
    @Column(name = "commodity_code", nullable = false, length = 5)
    private String commodityCode;
    @Basic(optional = false)
    @Column(name = "cont_type", nullable = false)
    private int contType;
    @Column(name = "block", length = 5)
    private String block;
    @Column(name = "activity_code", length = 10)
    private String activityCode;
    @Column(name = "old_ppkb", length = 30)
    private String oldPpkb;
    @Column(name = "new_ppkb", length = 30)
    private String newPpkb;
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
    private String overSize;
    @Column(name = "dangerous")
    private String dangerous;
    @Column(name = "dg_label")
    private String dgLabel;
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
    private String isDelivery;
    @Column(name = "gross_class", length = 5)
    private String grossClass;
    @Column(name = "is_behandle")
    private String isBehandle;
    @Column(name = "is_cfs")
    private String isCfs;
    @Column(name = "is_inspection")
    private String isInspection;
    @Column(name = "is_lifton")
    private String isLifton;
    @Column(name = "ex_stuffing")
    private String exStuffing;
    @Column(name = "ex_stripping")
    private String exStripping;
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
    @Column(name = "load_port", length = 2147483647)
    private String loadPort;
    @Column(name = "disch_port", length = 2147483647)
    private String dischPort;
    @Column(name = "change_status", nullable = false)
    private String changeStatus;
    @JoinColumn(name = "mlo", referencedColumnName = "cust_code")
    @ManyToOne
    private MasterCustomer mlo;

    public ChangeStatusHistory() {
    }

    public ChangeStatusHistory(Integer id) {
        this.id = id;
    }

    public ChangeStatusHistory(Integer id, String commodityCode, int contType, String contNo, short contSize, String contStatus, String createdBy, Date createdDate, MasterCustomer mlo) {
        this.id = id;
        this.commodityCode = commodityCode;
        this.contType = contType;
        this.contNo = contNo;
        this.contSize = contSize;
        this.contStatus = contStatus;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.mlo = mlo;
    }

    public ChangeStatusHistory(ServiceContTranshipment serviceContTranshipment){
        GrossConverter grossConverter = new GrossConverter();
        
        this.contNo = serviceContTranshipment.getContNo();
        this.mlo = serviceContTranshipment.getMlo();
        this.oldPpkb = serviceContTranshipment.getServiceVessel().getNoPpkb();
        this.commodityCode = serviceContTranshipment.getMasterCommodity().getCommodityCode();
        this.contSize = serviceContTranshipment.getContSize();
        this.contType = serviceContTranshipment.getMasterContainerType().getContType();
        this.contStatus = serviceContTranshipment.getContStatus();
        this.contGross = serviceContTranshipment.getContGross();
        this.sealNo = serviceContTranshipment.getSealNo();
        this.block = serviceContTranshipment.getMasterYard().getBlock();
        this.dangerous = serviceContTranshipment.getDangerous();
        this.dgLabel = serviceContTranshipment.getDgLabel();
        this.overSize = serviceContTranshipment.getOverSize();
        this.vBay = serviceContTranshipment.getVBay();
        this.vRow = serviceContTranshipment.getVRow();
        this.vTier = serviceContTranshipment.getVTier();
        this.yRow = serviceContTranshipment.getYRow();
        this.ySlot = serviceContTranshipment.getYSlot();
        this.yTier = serviceContTranshipment.getYTier();
        this.startPlacementDate = serviceContTranshipment.getStartPlacementDate();
        this.crane = serviceContTranshipment.getCrane();
        this.position = serviceContTranshipment.getPosition();
        this.isDelivery = "FALSE"; 
        this.isBehandle = "FALSE"; 
        this.isCfs = "FALSE"; 
        this.isInspection = "FALSE"; 
        this.grossClass = grossConverter.convert(serviceContTranshipment.getContSize(), serviceContTranshipment.getContGross());
        this.blNo = serviceContTranshipment.getBlNo();
        this.dischPort = serviceContTranshipment.getDischPort();
        this.loadPort = serviceContTranshipment.getLoadPort();
        this.changeStatus = "TRANSHIPMENT";
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

    public String getOldPpkb() {
        return oldPpkb;
    }

    public void setOldPpkb(String oldPpkb) {
        this.oldPpkb = oldPpkb;
    }

    public String getNewPpkb() {
        return newPpkb;
    }

    public void setNewPpkb(String newPpkb) {
        this.newPpkb = newPpkb;
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

    public String getOverSize() {
        return overSize;
    }

    public void setOverSize(String overSize) {
        this.overSize = overSize;
    }

    public String getDangerous() {
        return dangerous;
    }

    public void setDangerous(String dangerous) {
        this.dangerous = dangerous;
    }

    public String getDgLabel() {
        return dgLabel;
    }

    public void setDgLabel(String dgLabel) {
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

    public String getIsDelivery() {
        return isDelivery;
    }

    public void setIsDelivery(String isDelivery) {
        this.isDelivery = isDelivery;
    }

    public String getGrossClass() {
        return grossClass;
    }

    public void setGrossClass(String grossClass) {
        this.grossClass = grossClass;
    }

    public String getIsBehandle() {
        return isBehandle;
    }

    public void setIsBehandle(String isBehandle) {
        this.isBehandle = isBehandle;
    }

    public String getIsCfs() {
        return isCfs;
    }

    public void setIsCfs(String isCfs) {
        this.isCfs = isCfs;
    }

    public String getIsInspection() {
        return isInspection;
    }

    public void setIsInspection(String isInspection) {
        this.isInspection = isInspection;
    }

    public String getIsLifton() {
        return isLifton;
    }

    public void setIsLifton(String isLifton) {
        this.isLifton = isLifton;
    }

    public String getExStuffing() {
        return exStuffing;
    }

    public void setExStuffing(String exStuffing) {
        this.exStuffing = exStuffing;
    }

    public String getExStripping() {
        return exStripping;
    }

    public void setExStripping(String exStripping) {
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

    public String getLoadPort() {
        return loadPort;
    }

    public void setLoadPort(String loadPort) {
        this.loadPort = loadPort;
    }

    public String getDischPort() {
        return dischPort;
    }

    public void setDischPort(String dischPort) {
        this.dischPort = dischPort;
    }

    public String getChangeStatus() {
        return changeStatus;
    }

    public void setChangeStatus(String changeStatus) {
        this.changeStatus = changeStatus;
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
        if (!(object instanceof ChangeStatusHistory)) {
            return false;
        }
        ChangeStatusHistory other = (ChangeStatusHistory) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.ChangeStatusHistory[id=" + id + "]";
    }

}
