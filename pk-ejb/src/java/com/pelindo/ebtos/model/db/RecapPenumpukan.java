/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.model.db;

import com.qtasnim.persistence.AuditTrailEntityListener;
import com.qtasnim.persistence.EntityAuditable;
import com.pelindo.ebtos.model.db.master.MasterCustomer;
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
import javax.persistence.JoinColumns;
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
@Table(name = "recap_penumpukan")
@NamedQueries({
    @NamedQuery(name = "RecapPenumpukan.findAll", query = "SELECT r FROM RecapPenumpukan r"),
    @NamedQuery(name = "RecapPenumpukan.findById", query = "SELECT r FROM RecapPenumpukan r WHERE r.id = :id"),
    @NamedQuery(name = "RecapPenumpukan.findByContNo", query = "SELECT r FROM RecapPenumpukan r WHERE r.contNo = :contNo"),
    @NamedQuery(name = "RecapPenumpukan.findByTotalCharge", query = "SELECT r FROM RecapPenumpukan r WHERE r.totalCharge = :totalCharge"),
    @NamedQuery(name = "RecapPenumpukan.deleteThatHaveReachedCY", query = "DELETE FROM RecapPenumpukan r WHERE r.serviceContDischarge.serviceVessel.noPpkb = :noPpkb AND r.serviceContDischarge.position = '03'"),
    @NamedQuery(name = "RecapPenumpukan.deleteByNoPpkb", query = "DELETE FROM RecapPenumpukan r WHERE r.noPpkb = :noPpkb")})
@NamedNativeQueries({
    @NamedNativeQuery(name = "RecapPenumpukan.Native.findByPpkbAndCont", query = "SELECT id FROM recap_penumpukan WHERE cont_no = ? AND no_ppkb = ?")
})
public class RecapPenumpukan implements Serializable, EntityAuditable {
    public static final String LOAD = "LOAD";
    public static final String DISCHARGE = "DISCHARGE";
    public static final String SHIFTING = "SHIFTING";
    
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "no_ppkb", length = 30)
    private String noPpkb;
    @Column(name = "activity_code", length = 10)
    private String activityCode;
    @Basic(optional = false)
    @Column(name = "cont_no", nullable = false, length = 11)
    private String contNo;
    @Column(name = "masa_1")
    private Short masa1;
    @Column(name = "charge_masa_1", precision = 19, scale = 2)
    private BigDecimal chargeMasa1;
    @Column(name = "jasa_dermaga", precision = 19, scale = 2)
    private BigDecimal jasaDermaga;
    @Column(name = "total_charge", precision = 19, scale = 2)
    private BigDecimal totalCharge;
    @Column(name = "curr_code", length = 5)
    private String currCode;
    @Column(name = "masa_2")
    private Short masa2;
    @Column(name = "charge_masa_2", precision = 19, scale = 2)
    private BigDecimal chargeMasa2;
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
    @JoinColumns(value = {
        @JoinColumn(name = "cont_no", referencedColumnName = "cont_no", insertable = false, updatable = false),
        @JoinColumn(name = "no_ppkb", referencedColumnName = "no_ppkb", insertable = false, updatable = false)
    })
    @ManyToOne
    private ServiceContDischarge serviceContDischarge;
    @JoinColumn(name = "mlo", referencedColumnName = "cust_code")
    @ManyToOne
    private MasterCustomer mlo;
    @Column(name = "operation", length = 15)
    private String operation;
    @Basic(optional = false)
    @Column(name = "total_hari", nullable = false)
    private int totalHari = 0;

    public RecapPenumpukan() {
    }

    public RecapPenumpukan(Integer id) {
        this.id = id;
    }

    public RecapPenumpukan(Integer id, String contNo) {
        this.id = id;
        this.contNo = contNo;
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

    public String getActivityCode() {
        return activityCode;
    }

    public void setActivityCode(String activityCode) {
        this.activityCode = activityCode;
    }

    public String getContNo() {
        return contNo;
    }

    public void setContNo(String contNo) {
        this.contNo = contNo;
    }

    public Short getMasa1() {
        return masa1;
    }

    public void setMasa1(Short masa1) {
        this.masa1 = masa1;
    }

    public BigDecimal getChargeMasa1() {
        return chargeMasa1;
    }

    public void setChargeMasa1(BigDecimal chargeMasa1) {
        this.chargeMasa1 = chargeMasa1;
    }

    public BigDecimal getJasaDermaga() {
        return jasaDermaga;
    }

    public void setJasaDermaga(BigDecimal jasaDermaga) {
        this.jasaDermaga = jasaDermaga;
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

    public ServiceContDischarge getServiceContDischarge() {
        return serviceContDischarge;
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

    public Short getMasa2() {
        return masa2;
    }

    public void setMasa2(Short masa2) {
        this.masa2 = masa2;
    }

    public BigDecimal getChargeMasa2() {
        return chargeMasa2;
    }

    public void setChargeMasa2(BigDecimal chargeMasa2) {
        this.chargeMasa2 = chargeMasa2;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
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
        if (!(object instanceof RecapPenumpukan)) {
            return false;
        }
        RecapPenumpukan other = (RecapPenumpukan) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.RecapPenumpukan[id=" + id + "]";
    }

    public int getTotalHari() {
        return totalHari;
    }

    public void setTotalHari(int totalHari) {
        this.totalHari = totalHari;
    }
}
