/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.model.db;

import com.qtasnim.persistence.AuditTrailEntityListener;
import com.qtasnim.persistence.EntityAuditable;
import com.pelindo.ebtos.model.db.master.MasterActivity;
import com.pelindo.ebtos.model.db.master.MasterCustomer;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author senoanggoro
 */
@Entity
@EntityListeners({AuditTrailEntityListener.class})
@Table(name = "perhitungan_lift_service")
@NamedQueries({
    @NamedQuery(name = "PerhitunganLiftService.findAll", query = "SELECT p FROM PerhitunganLiftService p"),
    @NamedQuery(name = "PerhitunganLiftService.findById", query = "SELECT p FROM PerhitunganLiftService p WHERE p.id = :id"),
    @NamedQuery(name = "PerhitunganLiftService.findByContNo", query = "SELECT p FROM PerhitunganLiftService p WHERE p.contNo = :contNo"),
    @NamedQuery(name = "PerhitunganLiftService.deleteByContNoAndNoReg", query = "DELETE FROM PerhitunganLiftService p WHERE p.contNo = :contNo AND p.registration.noReg = :noReg"),
    @NamedQuery(name = "PerhitunganLiftService.findByContNoAndNoReg", query = "SELECT p FROM PerhitunganLiftService p WHERE p.contNo = :contNo AND p.registration.noReg = :noReg"),
    @NamedQuery(name = "PerhitunganLiftService.deleteByNoReg", query = "DELETE FROM PerhitunganLiftService p WHERE p.registration.noReg = :noReg"),
    @NamedQuery(name = "PerhitunganLiftService.updateNoPpkb", query = "UPDATE PerhitunganLiftService p SET p.noPpkb = :newValue WHERE p.noPpkb = :oldValue"),
    @NamedQuery(name = "PerhitunganLiftService.findByBlNo", query = "SELECT p FROM PerhitunganLiftService p WHERE p.blNo = :blNo"),
    @NamedQuery(name = "PerhitunganLiftService.findByCharge", query = "SELECT p FROM PerhitunganLiftService p WHERE p.charge = :charge"),
    @NamedQuery(name = "PerhitunganLiftService.updatePerhitunganLiftService", query = "UPDATE PerhitunganLiftService p SET p.noPpkb = :newValue WHERE p.noPpkb = :oldValue AND p.contNo IN :containers AND p.registration.noReg = :noReg")})
@NamedNativeQueries({
    @NamedNativeQuery(name = "PerhitunganLiftService.Native.findInvoice", query = "SELECT id FROM perhitungan_lift_service WHERE cont_no = ? AND no_ppkb = ? AND no_reg = ?"),
    @NamedNativeQuery(name = "PerhitunganLiftService.Native.findByReg", query = "SELECT id FROM perhitungan_lift_service WHERE no_reg = ?")})
public class PerhitunganLiftService implements Serializable, EntityAuditable {
    @Column(name = "payment_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date paymentDate;
    @OneToOne(mappedBy = "perhitunganLiftService")
    private CancelDocumentService cancelDocumentService;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "cont_no", length = 11)
    private String contNo;
    @Column(name = "bl_no")
    private String blNo;
    @Column(name = "charge", precision = 19, scale = 2)
    private BigDecimal charge;
    @Column(name = "no_ppkb", length = 30)
    private String noPpkb;
    @Column(name = "curr_code", length = 5)
    private String currCode;
    @JoinColumn(name = "no_reg", referencedColumnName = "no_reg")
    @ManyToOne
    private Registration registration;
    @JoinColumn(name = "activity_code", referencedColumnName = "activity_code")
    @ManyToOne
    private MasterActivity masterActivity;
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

    public PerhitunganLiftService() {
    }

    public PerhitunganLiftService(Integer id) {
        this.id = id;
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

    public String getBlNo() {
        return blNo;
    }

    public void setBlNo(String blNo) {
        this.blNo = blNo;
    }

    public BigDecimal getCharge() {
        return charge;
    }

    public void setCharge(BigDecimal charge) {
        this.charge = charge;
    }

    public String getNoPpkb() {
        return noPpkb;
    }

    public void setNoPpkb(String noPpkb) {
        this.noPpkb = noPpkb;
    }

    public String getCurrCode() {
        return currCode;
    }

    public void setCurrCode(String currCode) {
        this.currCode = currCode;
    }

    public Registration getRegistration() {
        return registration;
    }

    public void setRegistration(Registration registration) {
        this.registration = registration;
    }

    public MasterActivity getMasterActivity() {
        return masterActivity;
    }

    public void setMasterActivity(MasterActivity masterActivity) {
        this.masterActivity = masterActivity;
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
        if (!(object instanceof PerhitunganLiftService)) {
            return false;
        }
        PerhitunganLiftService other = (PerhitunganLiftService) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.PerhitunganLiftService[id=" + id + "]";
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public CancelDocumentService getCancelDocumentService() {
        return cancelDocumentService;
    }

    public void setCancelDocumentService(CancelDocumentService cancelDocumentService) {
        this.cancelDocumentService = cancelDocumentService;
    }

}
