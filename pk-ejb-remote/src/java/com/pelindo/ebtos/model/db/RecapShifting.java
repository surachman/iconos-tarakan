/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.model.db;

import com.pelindo.ebtos.model.db.master.MasterContainerTypeInGeneral;
import com.qtasnim.persistence.AuditTrailEntityListener;
import com.qtasnim.persistence.EntityAuditable;
import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "recap_shifting")
@NamedQueries({
    @NamedQuery(name = "RecapShifting.findAll", query = "SELECT r FROM RecapShifting r"),
    @NamedQuery(name = "RecapShifting.findById", query = "SELECT r FROM RecapShifting r WHERE r.id = :id"),
    @NamedQuery(name = "RecapShifting.findByNoPpkb", query = "SELECT r FROM RecapShifting r WHERE r.noPpkb = :noPpkb"),
    @NamedQuery(name = "RecapShifting.deleteByNoPpkb", query = "DELETE FROM RecapShifting r WHERE r.noPpkb = :noPpkb"),
    @NamedQuery(name = "RecapShifting.findByOperation", query = "SELECT r FROM RecapShifting r WHERE r.operation = :operation"),
    @NamedQuery(name = "RecapShifting.findByActivityCode", query = "SELECT r FROM RecapShifting r WHERE r.activityCode = :activityCode"),
    @NamedQuery(name = "RecapShifting.findByAmount", query = "SELECT r FROM RecapShifting r WHERE r.amount = :amount"),
    @NamedQuery(name = "RecapShifting.findByCharge", query = "SELECT r FROM RecapShifting r WHERE r.charge = :charge"),
    @NamedQuery(name = "RecapShifting.findByTotalCharge", query = "SELECT r FROM RecapShifting r WHERE r.totalCharge = :totalCharge"),
    @NamedQuery(name = "RecapShifting.findByActivityCodeNoPpkbAndOperation", query = "SELECT r FROM RecapShifting r WHERE r.activityCode = :activityCode AND r.noPpkb = :noPpkb AND r.operation = :operation"),
    @NamedQuery(name = "RecapShifting.findByBentuk3Constraint", query = "SELECT r FROM RecapShifting r WHERE r.contStatus = :contStatus AND r.crane = :crane AND r.isExportImport = :isExportImport AND r.sling = :sling AND r.containerType.id = :containerType AND r.activityCode = :activityCode AND r.operation = :operation AND r.noPpkb = :noPpkb AND r.currCode = :currCode AND r.shiftTo = :shiftTo AND r.openDoor = :openDoor AND r.contSize = :contSize AND r.twentyOneFeet = :twentyOneFeet AND r.charge = :charge")})
@NamedNativeQueries({
    @NamedNativeQuery(name = "RecapShifting.Native.findByActCode", query = "SELECT id FROM recap_shifting WHERE activity_code = ? AND no_ppkb = ? AND operation = ?"),
    @NamedNativeQuery(name = "RecapShifting.Native.findHandlingInvoice", query = "SELECT a.no_ppkb, ((SELECT nvl(SUM(s.total_charge)) FROM recap_shifting s WHERE a.no_ppkb=s.no_ppkb AND s.operation ='DISCHARGE')+"
                + "(SELECT nvl(SUM(m.total_charge)) FROM recap_hatch_move m WHERE a.no_ppkb = m.no_ppkb AND m.operation='DISCHARGE')+"
                + "(SELECT nvl(SUM(t.total_charge))FROM recap_transhipment t WHERE a.no_ppkb = t.no_ppkb AND t.operation='DISCHARGE')) as paket_handling, "
                + "(SELECT nvl(SUM(p.total_charge)) FROM recap_penumpukan p WHERE a.no_ppkb = p.no_ppkb) as penumpukan,"
                + "((SELECT nvl(SUM(s.total_charge)) FROM recap_shifting s WHERE a.no_ppkb=s.no_ppkb AND s.operation ='DISCHARGE')+"
                + "(SELECT nvl(SUM(m.total_charge)) FROM recap_hatch_move m WHERE a.no_ppkb = m.no_ppkb AND m.operation='DISCHARGE')+"
                + "(SELECT nvl(SUM(t.total_charge))FROM recap_transhipment t WHERE a.no_ppkb = t.no_ppkb AND t.operation='DISCHARGE')+"
                + "(SELECT nvl(SUM(p.total_charge)) FROM recap_penumpukan p WHERE a.no_ppkb = p.no_ppkb)) as jumlah1 "
                + "FROM service_vessel a "
                + "WHERE a.no_ppkb = ? "
                + "GROUP BY a.no_ppkb"),
    @NamedNativeQuery(name = "RecapShifting.Native.findByPpkb", query = "SELECT id FROM recap_shifting WHERE no_ppkb = ?")
})
public class RecapShifting implements Serializable, EntityAuditable {
    public static final String OPERATION_LOAD = "LOAD";
    public static final String OPERATION_DISCHARGE = "DISCHARGE";
    
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "no_ppkb", length = 30)
    private String noPpkb;
    @Column(name = "operation", length = 10)
    private String operation;
    @Column(name = "activity_code", length = 10)
    private String activityCode;
    @Column(name = "amount")
    private Integer amount;
    @Column(name = "charge", precision = 19, scale = 2)
    private BigDecimal charge;
    @Column(name = "total_charge", precision = 19, scale = 2)
    private BigDecimal totalCharge;
    @Column(name = "curr_code", length = 5)
    private String currCode;
    @Basic(optional = false)
    @Column(name = "crane", nullable = false, length = 2)
    private String crane;
    @Basic(optional = false)
    @Column(name = "is_export_import", nullable = false)
    private boolean isExportImport = false;
    @Basic(optional = false)
    @Column(name = "sling", nullable = false)
    private boolean sling = false;
    @Column(name = "open_door", nullable = false)
    private boolean openDoor = false;
    @Basic(optional = false)
    @Column(name = "cont_size", nullable = false)
    private short contSize;
    @JoinColumn(name = "cont_type", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private MasterContainerTypeInGeneral containerType;
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
    @Column(name = "cont_status", length = 5)
    private String contStatus;
    @Column(name = "shift_to", length = 10, nullable = false)
    private String shiftTo;
    @Basic(optional = false)
    @Column(name = "twenty_one_feet", nullable = false)
    private boolean twentyOneFeet = false;

    public RecapShifting() {
    }

    public RecapShifting(Integer id) {
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

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getActivityCode() {
        return activityCode;
    }

    public void setActivityCode(String activityCode) {
        this.activityCode = activityCode;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public BigDecimal getCharge() {
        return charge;
    }

    public void setCharge(BigDecimal charge) {
        this.charge = charge;
    }

    public BigDecimal getTotalCharge() {
        return totalCharge;
    }

    public void setTotalCharge(BigDecimal totalCharge) {
        this.totalCharge = totalCharge;
    }

    public String getCurrCode() {
        return currCode;
    }

    public void setCurrCode(String currCode) {
        this.currCode = currCode;
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

    public String getCrane() {
        return crane;
    }

    public void setCrane(String crane) {
        this.crane = crane;
    }

    public boolean getIsExportImport() {
        return isExportImport;
    }

    public void setIsExportImport(boolean isExportImport) {
        this.isExportImport = isExportImport;
    }

    public boolean getSling() {
        return sling;
    }

    public void setSling(boolean sling) {
        this.sling = sling;
    }

    public MasterContainerTypeInGeneral getContainerType() {
        return containerType;
    }

    public void setContainerType(MasterContainerTypeInGeneral containerType) {
        this.containerType = containerType;
    }

    public String getContStatus() {
        return contStatus;
    }

    public void setContStatus(String contStatus) {
        this.contStatus = contStatus;
    }

    public String getShiftTo() {
        return shiftTo;
    }

    public void setShiftTo(String shiftTo) {
        this.shiftTo = shiftTo;
    }

    public boolean isOpenDoor() {
        return openDoor;
    }

    public void setOpenDoor(boolean openDoor) {
        this.openDoor = openDoor;
    }

    public short getContSize() {
        return contSize;
    }

    public void setContSize(short contSize) {
        this.contSize = contSize;
    }

    public boolean getTwentyOneFeet() {
        return twentyOneFeet;
    }

    public void setTwentyOneFeet(boolean twentyOneFeet) {
        this.twentyOneFeet = twentyOneFeet;
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
        if (!(object instanceof RecapShifting)) {
            return false;
        }
        RecapShifting other = (RecapShifting) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.RecapShifting[id=" + id + "]";
    }
}
