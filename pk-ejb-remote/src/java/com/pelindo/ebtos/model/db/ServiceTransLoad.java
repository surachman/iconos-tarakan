/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.model.db;

import com.qtasnim.persistence.AuditTrailEntityListener;
import com.qtasnim.persistence.EntityAuditable;
import com.pelindo.ebtos.model.db.master.MasterYard;
import com.pelindo.ebtos.model.db.master.MasterContainerType;
import com.pelindo.ebtos.model.db.master.MasterCommodity;
import com.pelindo.ebtos.model.db.master.MasterCustomer;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
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
 * @author senoanggoro
 */
@Entity
@EntityListeners({AuditTrailEntityListener.class})
@Table(name = "service_trans_load")
@NamedQueries({
    @NamedQuery(name = "ServiceTransLoad.findAll", query = "SELECT s FROM ServiceTransLoad s"),
    @NamedQuery(name = "ServiceTransLoad.findById", query = "SELECT s FROM ServiceTransLoad s WHERE s.id = :id"),
    @NamedQuery(name = "ServiceTransLoad.findByContNo", query = "SELECT s FROM ServiceTransLoad s WHERE s.contNo = :contNo"),
    @NamedQuery(name = "ServiceTransLoad.findByContSize", query = "SELECT s FROM ServiceTransLoad s WHERE s.contSize = :contSize"),
    @NamedQuery(name = "ServiceTransLoad.findByContStatus", query = "SELECT s FROM ServiceTransLoad s WHERE s.contStatus = :contStatus"),
    @NamedQuery(name = "ServiceTransLoad.findByContGross", query = "SELECT s FROM ServiceTransLoad s WHERE s.contGross = :contGross"),
    @NamedQuery(name = "ServiceTransLoad.findBySealNo", query = "SELECT s FROM ServiceTransLoad s WHERE s.sealNo = :sealNo"),
    @NamedQuery(name = "ServiceTransLoad.findByOverSize", query = "SELECT s FROM ServiceTransLoad s WHERE s.overSize = :overSize"),
    @NamedQuery(name = "ServiceTransLoad.findByDg", query = "SELECT s FROM ServiceTransLoad s WHERE s.dg = :dg"),
    @NamedQuery(name = "ServiceTransLoad.findByDgLabel", query = "SELECT s FROM ServiceTransLoad s WHERE s.dgLabel = :dgLabel"),
    @NamedQuery(name = "ServiceTransLoad.findByLoadPort", query = "SELECT s FROM ServiceTransLoad s WHERE s.loadPort = :loadPort"),
    @NamedQuery(name = "ServiceTransLoad.findByDischPort", query = "SELECT s FROM ServiceTransLoad s WHERE s.dischPort = :dischPort"),
    @NamedQuery(name = "ServiceTransLoad.findByYSlot", query = "SELECT s FROM ServiceTransLoad s WHERE s.ySlot = :ySlot"),
    @NamedQuery(name = "ServiceTransLoad.findByYRow", query = "SELECT s FROM ServiceTransLoad s WHERE s.yRow = :yRow"),
    @NamedQuery(name = "ServiceTransLoad.findByYTier", query = "SELECT s FROM ServiceTransLoad s WHERE s.yTier = :yTier"),
    @NamedQuery(name = "ServiceTransLoad.findByLastPpkb", query = "SELECT s FROM ServiceTransLoad s WHERE s.lastPpkb = :lastPpkb"),
    @NamedQuery(name = "ServiceTransLoad.findByPlacementDate", query = "SELECT s FROM ServiceTransLoad s WHERE s.placementDate = :placementDate"),
    @NamedQuery(name = "ServiceTransLoad.findByUnplacementDate", query = "SELECT s FROM ServiceTransLoad s WHERE s.unplacementDate = :unplacementDate")})
public class ServiceTransLoad implements Serializable, EntityAuditable {
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
    private Integer id;
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
    @Column(name = "dg")
    private Boolean dg;
    @Column(name = "dg_label")
    private Boolean dgLabel;
    @Column(name = "load_port", length = 10)
    private String loadPort;
    @Column(name = "disch_port", length = 10)
    private String dischPort;
    @Column(name = "y_slot")
    private Short ySlot;
    @Column(name = "y_row")
    private Short yRow;
    @Column(name = "y_tier")
    private Short yTier;
    @Column(name = "last_ppkb", length = 30)
    private String lastPpkb;
    @Column(name = "placement_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date placementDate;
    @Column(name = "unplacement_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date unplacementDate;
    @JoinColumn(name = "block", referencedColumnName = "block")
    @ManyToOne
    private MasterYard masterYard;
    @JoinColumn(name = "cont_type", referencedColumnName = "cont_type")
    @ManyToOne
    private MasterContainerType masterContainerType;
    @JoinColumn(name = "commodity_code", referencedColumnName = "commodity_code")
    @ManyToOne
    private MasterCommodity masterCommodity;
    @JoinColumn(name = "mlo", referencedColumnName = "cust_code")
    @ManyToOne
    private MasterCustomer mlo;

    public ServiceTransLoad() {
    }

    public ServiceTransLoad(Integer id) {
        this.id = id;
    }

    public ServiceTransLoad(Integer id, String contNo, short contSize, String contStatus, MasterCustomer mlo) {
        this.id = id;
        this.contNo = contNo;
        this.mlo = mlo;
        this.contSize = contSize;
        this.contStatus = contStatus;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getLastPpkb() {
        return lastPpkb;
    }

    public void setLastPpkb(String lastPpkb) {
        this.lastPpkb = lastPpkb;
    }

    public Date getPlacementDate() {
        return placementDate;
    }

    public void setPlacementDate(Date placementDate) {
        this.placementDate = placementDate;
    }

    public Date getUnplacementDate() {
        return unplacementDate;
    }

    public void setUnplacementDate(Date unplacementDate) {
        this.unplacementDate = unplacementDate;
    }

    public MasterYard getMasterYard() {
        return masterYard;
    }

    public void setMasterYard(MasterYard masterYard) {
        this.masterYard = masterYard;
    }

    public MasterContainerType getMasterContainerType() {
        return masterContainerType;
    }

    public void setMasterContainerType(MasterContainerType masterContainerType) {
        this.masterContainerType = masterContainerType;
    }

    public MasterCommodity getMasterCommodity() {
        return masterCommodity;
    }

    public void setMasterCommodity(MasterCommodity masterCommodity) {
        this.masterCommodity = masterCommodity;
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
        if (!(object instanceof ServiceTransLoad)) {
            return false;
        }
        ServiceTransLoad other = (ServiceTransLoad) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.ServiceTransLoad[id=" + id + "]";
    }

}
