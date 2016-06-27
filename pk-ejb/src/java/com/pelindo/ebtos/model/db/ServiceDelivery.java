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
@Table(name = "service_delivery")
@NamedQueries({
    @NamedQuery(name = "ServiceDelivery.findAll", query = "SELECT s FROM ServiceDelivery s"),
    @NamedQuery(name = "ServiceDelivery.findById", query = "SELECT s FROM ServiceDelivery s WHERE s.id = :id"),
    @NamedQuery(name = "ServiceDelivery.findByContNo", query = "SELECT s FROM ServiceDelivery s WHERE s.contNo = :contNo"),
    @NamedQuery(name = "ServiceDelivery.findByNoPpkbAndContNo", query = "SELECT s FROM ServiceDelivery s WHERE s.noPpkb = :noPpkb AND s.contNo = :contNo"),
    @NamedQuery(name = "ServiceDelivery.findByContSize", query = "SELECT s FROM ServiceDelivery s WHERE s.contSize = :contSize"),
    @NamedQuery(name = "ServiceDelivery.findByContStatus", query = "SELECT s FROM ServiceDelivery s WHERE s.contStatus = :contStatus"),
    @NamedQuery(name = "ServiceDelivery.findByContGross", query = "SELECT s FROM ServiceDelivery s WHERE s.contGross = :contGross"),
    @NamedQuery(name = "ServiceDelivery.findBySealNo", query = "SELECT s FROM ServiceDelivery s WHERE s.sealNo = :sealNo"),
    @NamedQuery(name = "ServiceDelivery.findByOverSize", query = "SELECT s FROM ServiceDelivery s WHERE s.overSize = :overSize"),
    @NamedQuery(name = "ServiceDelivery.findByDangerous", query = "SELECT s FROM ServiceDelivery s WHERE s.dangerous = :dangerous"),
    @NamedQuery(name = "ServiceDelivery.findByDgLabel", query = "SELECT s FROM ServiceDelivery s WHERE s.dgLabel = :dgLabel"),
    @NamedQuery(name = "ServiceDelivery.findByYSlot", query = "SELECT s FROM ServiceDelivery s WHERE s.ySlot = :ySlot"),
    @NamedQuery(name = "ServiceDelivery.findByYRow", query = "SELECT s FROM ServiceDelivery s WHERE s.yRow = :yRow"),
    @NamedQuery(name = "ServiceDelivery.findByYTier", query = "SELECT s FROM ServiceDelivery s WHERE s.yTier = :yTier"),
    @NamedQuery(name = "ServiceDelivery.findByTransactionDate", query = "SELECT s FROM ServiceDelivery s WHERE s.transactionDate = :transactionDate")})
@NamedNativeQueries({
    @NamedNativeQuery(name = "ServiceDelivery.Native.findIdDeliveryConfirm", query = "SELECT id FROM service_delivery WHERE no_ppkb = ? AND cont_no = ?"),

/*    
    @NamedNativeQuery(name = "ServiceDelivery.Native.findYardDischarge", query = "SELECT e1.equip_code, (SELECT mopt.name FROM m_operator mopt WHERE e1.opt_code::text = mopt.opt_code::text) AS opt_name, "
            + "e1.end_time,  sr.block,  sr.y_slot, sr.y_row,  sr.y_tier, sr.created_by AS confirm_by "
            + "FROM service_delivery sr "
            + "LEFT JOIN equipment e1 ON e1.no_ppkb::text = sr.no_ppkb::text AND e1.cont_no::text = sr.cont_no::text AND "
            + "e1.equipment_act_code::text = 'DELIVERY'::text AND e1.operation::text = 'DISCHARGE'::text "
            + "WHERE sr.no_ppkb =? AND sr.cont_no=?")
*/
    @NamedNativeQuery(name = "ServiceDelivery.Native.findYardDischarge", query = 
"SELECT e1.equip_code, " 
+"  (SELECT mopt.name FROM m_operator mopt WHERE e1.opt_code = mopt.opt_code " 
+"  ) AS opt_name, " 
+"  e1.end_time, " 
+"  sr.block, " 
+"  sr.y_slot, " 
+"  sr.y_row, " 
+"  sr.y_tier, " 
+"  sr.created_by AS confirm_by " 
+"FROM service_delivery sr " 
+"LEFT JOIN equipment e1 " 
+"ON e1.no_ppkb             = sr.no_ppkb " 
+"AND e1.cont_no            = sr.cont_no " 
+"AND e1.equipment_act_code = 'DELIVERY' " 
+"AND e1.operation          = 'DISCHARGE' " 
+"WHERE sr.no_ppkb          = ? " 
+"AND sr.cont_no            = ?")            
})
public class ServiceDelivery implements Serializable, EntityAuditable {
    @Basic(optional = false)
    @Column(name = "twenty_one_feet", nullable = false)
    private String twentyOneFeet = "FALSE";
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    //@GeneratedValue(strategy=GenerationType.IDENTITY)
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
    private String overSize;
    @Column(name = "dangerous")
    private String dangerous;
    @Column(name = "dg_label")
    private String dgLabel;
    @Basic(optional = false)
    @Column(name = "y_slot", nullable = false)
    private short ySlot;
    @Basic(optional = false)
    @Column(name = "y_row", nullable = false)
    private short yRow;
    @Basic(optional = false)
    @Column(name = "y_tier", nullable = false)
    private short yTier;
    @Column(name = "transaction_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date transactionDate;
    @Column(name = "no_ppkb", length = 30)
    private String noPpkb;
    @JoinColumn(name = "block", referencedColumnName = "block")
    @ManyToOne
    private MasterYard masterYard;
    @JoinColumn(name = "cont_type", referencedColumnName = "cont_type", nullable = false)
    @ManyToOne(optional = false)
    private MasterContainerType masterContainerType;
    @JoinColumn(name = "commodity_code", referencedColumnName = "commodity_code", nullable = false)
    @ManyToOne(optional = false)
    private MasterCommodity masterCommodity;
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

    public ServiceDelivery() {
    }

    public ServiceDelivery(Integer id) {
        this.id = id;
    }

    public ServiceDelivery(Integer id, String contNo, short contSize, String contStatus, short ySlot, short yRow, short yTier, MasterCustomer mlo) {
        this.id = id;
        this.contNo = contNo;
        this.mlo = mlo;
        this.contSize = contSize;
        this.contStatus = contStatus;
        this.ySlot = ySlot;
        this.yRow = yRow;
        this.yTier = yTier;
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

    public short getYSlot() {
        return ySlot;
    }

    public void setYSlot(short ySlot) {
        this.ySlot = ySlot;
    }

    public short getYRow() {
        return yRow;
    }

    public void setYRow(short yRow) {
        this.yRow = yRow;
    }

    public short getYTier() {
        return yTier;
    }

    public void setYTier(short yTier) {
        this.yTier = yTier;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getNoPpkb() {
        return noPpkb;
    }

    public void setNoPpkb(String noPpkb) {
        this.noPpkb = noPpkb;
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
        if (!(object instanceof ServiceDelivery)) {
            return false;
        }
        ServiceDelivery other = (ServiceDelivery) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.ServiceDelivery[id=" + id + "]";
    }

    public String getTwentyOneFeet() {
        return twentyOneFeet;
    }

    public void setTwentyOneFeet(String twentyOneFeet) {
        this.twentyOneFeet = twentyOneFeet;
    }

}
