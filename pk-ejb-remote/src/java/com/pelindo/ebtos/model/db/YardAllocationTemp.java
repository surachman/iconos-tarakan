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
@Table(name = "yard_allocation_temp")
@NamedQueries({
    @NamedQuery(name = "YardAllocationTemp.findAll", query = "SELECT y FROM YardAllocationTemp y"),
    @NamedQuery(name = "YardAllocationTemp.findById", query = "SELECT y FROM YardAllocationTemp y WHERE y.id = :id"),
    @NamedQuery(name = "YardAllocationTemp.findByIdCont", query = "SELECT y FROM YardAllocationTemp y WHERE y.idCont = :idCont"),
    @NamedQuery(name = "YardAllocationTemp.findByNoPpkb", query = "SELECT y FROM YardAllocationTemp y WHERE y.noPpkb = :noPpkb"),
    @NamedQuery(name = "YardAllocationTemp.findByContNo", query = "SELECT y FROM YardAllocationTemp y WHERE y.contNo = :contNo"),
    @NamedQuery(name = "YardAllocationTemp.findByContType", query = "SELECT y FROM YardAllocationTemp y WHERE y.contType = :contType"),
    @NamedQuery(name = "YardAllocationTemp.findByCommodityCode", query = "SELECT y FROM YardAllocationTemp y WHERE y.commodityCode = :commodityCode"),
    @NamedQuery(name = "YardAllocationTemp.findByContSize", query = "SELECT y FROM YardAllocationTemp y WHERE y.contSize = :contSize"),
    @NamedQuery(name = "YardAllocationTemp.findByContStatus", query = "SELECT y FROM YardAllocationTemp y WHERE y.contStatus = :contStatus"),
    @NamedQuery(name = "YardAllocationTemp.findByContGross", query = "SELECT y FROM YardAllocationTemp y WHERE y.contGross = :contGross"),
    @NamedQuery(name = "YardAllocationTemp.findByGrossClass", query = "SELECT y FROM YardAllocationTemp y WHERE y.grossClass = :grossClass"),
    @NamedQuery(name = "YardAllocationTemp.findBySealNo", query = "SELECT y FROM YardAllocationTemp y WHERE y.sealNo = :sealNo"),
    @NamedQuery(name = "YardAllocationTemp.findByDg", query = "SELECT y FROM YardAllocationTemp y WHERE y.dg = :dg"),
    @NamedQuery(name = "YardAllocationTemp.findByDgLabel", query = "SELECT y FROM YardAllocationTemp y WHERE y.dgLabel = :dgLabel"),
    @NamedQuery(name = "YardAllocationTemp.findByOverSize", query = "SELECT y FROM YardAllocationTemp y WHERE y.overSize = :overSize"),
    @NamedQuery(name = "YardAllocationTemp.findByTradeType", query = "SELECT y FROM YardAllocationTemp y WHERE y.tradeType = :tradeType"),
    @NamedQuery(name = "YardAllocationTemp.findByLoadPort", query = "SELECT y FROM YardAllocationTemp y WHERE y.loadPort = :loadPort"),
    @NamedQuery(name = "YardAllocationTemp.findByDischPort", query = "SELECT y FROM YardAllocationTemp y WHERE y.dischPort = :dischPort"),
    @NamedQuery(name = "YardAllocationTemp.findByVesselBay", query = "SELECT y FROM YardAllocationTemp y WHERE y.vesselBay = :vesselBay"),
    @NamedQuery(name = "YardAllocationTemp.findByVesselRow", query = "SELECT y FROM YardAllocationTemp y WHERE y.vesselRow = :vesselRow"),
    @NamedQuery(name = "YardAllocationTemp.findByVesselTier", query = "SELECT y FROM YardAllocationTemp y WHERE y.vesselTier = :vesselTier"),
    @NamedQuery(name = "YardAllocationTemp.findByBlock", query = "SELECT y FROM YardAllocationTemp y WHERE y.block = :block"),
    @NamedQuery(name = "YardAllocationTemp.findByYardSlot", query = "SELECT y FROM YardAllocationTemp y WHERE y.yardSlot = :yardSlot"),
    @NamedQuery(name = "YardAllocationTemp.findByYardRow", query = "SELECT y FROM YardAllocationTemp y WHERE y.yardRow = :yardRow"),
    @NamedQuery(name = "YardAllocationTemp.findByYardTier", query = "SELECT y FROM YardAllocationTemp y WHERE y.yardTier = :yardTier"),
    @NamedQuery(name = "YardAllocationTemp.findByIdCoordinat", query = "SELECT y FROM YardAllocationTemp y WHERE y.idCoordinat = :idCoordinat"),
    @NamedQuery(name = "YardAllocationTemp.findByIdYardAllocation", query = "SELECT y FROM YardAllocationTemp y WHERE y.idYardAllocation = :idYardAllocation"),
    @NamedQuery(name = "YardAllocationTemp.findByIdYardAllocationFilter", query = "SELECT y FROM YardAllocationTemp y WHERE y.idYardAllocationFilter = :idYardAllocationFilter")})
@NamedNativeQueries({
    @NamedNativeQuery(name = "YardAllocationTemp.Native.truncate", query = "TRUNCATE yard_allocation_temp"),
    @NamedNativeQuery(name = "YardAllocationTemp.Native.findId", query = "SELECT id FROM yard_allocation_temp WHERE id_cont = ?"),
    @NamedNativeQuery(name = "YardAllocationTemp.Native.deleteByIdYardAllocation", query = "DELETE FROM yard_allocation_temp WHERE id_yard_allocation = ?"),
    @NamedNativeQuery(name = "YardAllocationTemp.Native.deleteByIdYardAllocationFilter", query = "DELETE FROM yard_allocation_temp WHERE id_yard_allocation_filter = ?"),
    @NamedNativeQuery(name = "YardAllocationTemp.Native.distinctIdCont", query = "SELECT DISTINCT id_cont FROM yard_allocation_temp"),
    @NamedNativeQuery(name = "YardAllocationTemp.Native.coordinatByIdCont", query = "SELECT yard_slot, yard_row, yard_tier, block, id_cont FROM yard_allocation_temp WHERE id_cont = ? ORDER BY yard_slot"),
    @NamedNativeQuery(name = "YardAllocationTemp.Native.findAllTemp", query = "SELECT cont_no, cont_size, cont_type, cont_status, cont_gross, block, yard_slot, yard_row, yard_tier, id, id_yard_allocation_filter FROM yard_allocation_temp WHERE id_yard_allocation_filter IN(SELECT id FROM yard_allocation_filter) ORDER BY cont_no"),
    @NamedNativeQuery(name = "YardAllocationTemp.Native.findCreateGenerate", query = "SELECT id_cont FROM yard_allocation_temp WHERE no_ppkb=? AND id_yard_allocation_filter=?"),
    @NamedNativeQuery(name = "YardAllocationTemp.Native.findIdContNotGenerate", query = "SELECT id_cont, id FROM yard_allocation_temp WHERE id_coordinat IS NULL AND no_ppkb=? AND id_yard_allocation_filter=? ORDER BY id LIMIT 1"),
    @NamedNativeQuery(name = "YardAllocationTemp.Native.updateCoordinat", query = "UPDATE yard_allocation_temp SET id_coordinat=?, id_yard_allocation=? WHERE id=?"),
    @NamedNativeQuery(name = "YardAllocationTemp.Native.removeGenerate", query = "SELECT a.id_cont, a.id_coordinat, b.feet_mark, a.id FROM yard_allocation_temp a, m_container_type b WHERE a.cont_type=b.cont_type AND a.id_yard_allocation=?"),
    @NamedNativeQuery(name = "YardAllocationTemp.Native.updateIdCoordinat", query = "UPDATE yard_allocation_temp SET id_coordinat=? WHERE id_coordinat=?"),
    @NamedNativeQuery(name = "YardAllocationTemp.Native.removeIdCoordinat", query = "UPDATE yard_allocation_temp SET id_coordinat=null, id_yard_allocation=null WHERE id=?"),
    @NamedNativeQuery(name = "YardAllocationTemp.Native.unFinishBayPlanDischargeByPPKB", query = "SELECT DISTINCT id FROM yard_allocation_temp WHERE no_ppkb=?")
})
public class YardAllocationTemp implements Serializable, EntityAuditable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "id_cont")
    private Integer idCont;
    @Column(name = "no_ppkb", length = 30)
    private String noPpkb;
    @Column(name = "cont_no", length = 2147483647)
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
    @Column(name = "gross_class", length = 5)
    private String grossClass;
    @Column(name = "seal_no", length = 10)
    private String sealNo;
    @Column(name = "dg")
    private Boolean dg;
    @Column(name = "dg_label")
    private Boolean dgLabel;
    @Column(name = "over_size")
    private Boolean overSize;
    @Column(name = "trade_type", length = 3)
    private String tradeType;
    @Column(name = "load_port", length = 2147483647)
    private String loadPort;
    @Column(name = "disch_port", length = 2147483647)
    private String dischPort;
    @Column(name = "vessel_bay")
    private Short vesselBay;
    @Column(name = "vessel_row")
    private Short vesselRow;
    @Column(name = "vessel_tier")
    private Short vesselTier;
    @Column(name = "block", length = 5)
    private String block;
    @Column(name = "yard_slot")
    private Short yardSlot;
    @Column(name = "yard_row")
    private Short yardRow;
    @Column(name = "yard_tier")
    private Short yardTier;
    @Column(name = "id_coordinat")
    private Integer idCoordinat;
    @Column(name = "id_yard_allocation")
    private String idYardAllocation;
    @Column(name = "id_yard_allocation_filter")
    private Integer idYardAllocationFilter;
    @Column(name = "is_transhipment", nullable=false)
    private Boolean isTranshipment = false;
    @Basic(optional = false)
    @Column(name = "is_import", nullable = false)
    private boolean isImport;
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
    @Column(name = "is_shifting", nullable = false)
    private Boolean isShifting = false;
    @JoinColumn(name = "mlo", referencedColumnName = "cust_code")
    @ManyToOne
    private MasterCustomer mlo;

    public YardAllocationTemp() {
    }

    public YardAllocationTemp(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdCont() {
        return idCont;
    }

    public void setIdCont(Integer idCont) {
        this.idCont = idCont;
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

    public String getGrossClass() {
        return grossClass;
    }

    public void setGrossClass(String grossClass) {
        this.grossClass = grossClass;
    }

    public String getSealNo() {
        return sealNo;
    }

    public void setSealNo(String sealNo) {
        this.sealNo = sealNo;
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

    public Boolean getOverSize() {
        return overSize;
    }

    public void setOverSize(Boolean overSize) {
        this.overSize = overSize;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
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

    public Short getVesselBay() {
        return vesselBay;
    }

    public void setVesselBay(Short vesselBay) {
        this.vesselBay = vesselBay;
    }

    public Short getVesselRow() {
        return vesselRow;
    }

    public void setVesselRow(Short vesselRow) {
        this.vesselRow = vesselRow;
    }

    public Short getVesselTier() {
        return vesselTier;
    }

    public void setVesselTier(Short vesselTier) {
        this.vesselTier = vesselTier;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public Short getYardSlot() {
        return yardSlot;
    }

    public void setYardSlot(Short yardSlot) {
        this.yardSlot = yardSlot;
    }

    public Short getYardRow() {
        return yardRow;
    }

    public void setYardRow(Short yardRow) {
        this.yardRow = yardRow;
    }

    public Short getYardTier() {
        return yardTier;
    }

    public void setYardTier(Short yardTier) {
        this.yardTier = yardTier;
    }

    public Integer getIdCoordinat() {
        return idCoordinat;
    }

    public void setIdCoordinat(Integer idCoordinat) {
        this.idCoordinat = idCoordinat;
    }

    public String getIdYardAllocation() {
        return idYardAllocation;
    }

    public void setIdYardAllocation(String idYardAllocation) {
        this.idYardAllocation = idYardAllocation;
    }

    public Integer getIdYardAllocationFilter() {
        return idYardAllocationFilter;
    }

    public void setIdYardAllocationFilter(Integer idYardAllocationFilter) {
        this.idYardAllocationFilter = idYardAllocationFilter;
    }

    public Boolean getIsTranshipment() {
        return isTranshipment;
    }

    public void setIsTranshipment(Boolean isTranshipment) {
        this.isTranshipment = isTranshipment;
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
    public Boolean getIsShifting() {
        return isShifting;
    }

    public void setIsShifting(Boolean isShifting) {
        this.isShifting = isShifting;
    }

    public boolean getIsImport() {
        return isImport;
    }

    public void setIsImport(boolean isImport) {
        this.isImport = isImport;
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
        if (!(object instanceof YardAllocationTemp)) {
            return false;
        }
        YardAllocationTemp other = (YardAllocationTemp) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.YardAllocationTemp[id=" + id + "]";
    }
}
