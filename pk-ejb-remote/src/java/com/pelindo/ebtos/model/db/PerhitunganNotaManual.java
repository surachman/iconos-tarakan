/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.model.db;

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
@Table(name = "perhitungan_nota_manual")
@NamedQueries({
    @NamedQuery(name = "PerhitunganNotaManual.findAll", query = "SELECT p FROM PerhitunganNotaManual p"),
    @NamedQuery(name = "PerhitunganNotaManual.findByNoPpkb", query = "SELECT p FROM PerhitunganNotaManual p WHERE p.noPpkb = :noPpkb"),
    @NamedQuery(name = "PerhitunganNotaManual.findByNoReg", query = "SELECT p FROM PerhitunganNotaManual p WHERE p.noReg = :noReg"),
    @NamedQuery(name = "PerhitunganNotaManual.findByCustCode", query = "SELECT p FROM PerhitunganNotaManual p WHERE p.custCode = :custCode"),
    @NamedQuery(name = "PerhitunganNotaManual.findByJumlahTagihan", query = "SELECT p FROM PerhitunganNotaManual p WHERE p.jumlahTagihan = :jumlahTagihan"),
    @NamedQuery(name = "PerhitunganNotaManual.findByPrintCounter", query = "SELECT p FROM PerhitunganNotaManual p WHERE p.printCounter = :printCounter"),
    @NamedQuery(name = "PerhitunganNotaManual.findByRevCounter", query = "SELECT p FROM PerhitunganNotaManual p WHERE p.revCounter = :revCounter"),
    @NamedQuery(name = "PerhitunganNotaManual.findByCreatedBy", query = "SELECT p FROM PerhitunganNotaManual p WHERE p.createdBy = :createdBy"),
    @NamedQuery(name = "PerhitunganNotaManual.findByCreatedDate", query = "SELECT p FROM PerhitunganNotaManual p WHERE p.createdDate = :createdDate"),
    @NamedQuery(name = "PerhitunganNotaManual.findByModifiedBy", query = "SELECT p FROM PerhitunganNotaManual p WHERE p.modifiedBy = :modifiedBy"),
    @NamedQuery(name = "PerhitunganNotaManual.findByModifiedDate", query = "SELECT p FROM PerhitunganNotaManual p WHERE p.modifiedDate = :modifiedDate"),
    @NamedQuery(name = "PerhitunganNotaManual.findById", query = "SELECT p FROM PerhitunganNotaManual p WHERE p.id = :id")})
@NamedNativeQueries({
    @NamedNativeQuery(name = "PerhitunganNotaManual.Native.findAllNative", query = "SELECT nm.no_ppkb, nm.no_reg, i.no_invoice, i.no_faktur_pajak, c.name, "
    + "i.total_tagihan, nm.print_counter, nm.rev_counter, nm.id FROM perhitungan_nota_manual nm, m_customer c, invoice i, "
    + "(SELECT p.no_reg, SUM(p.amount) as total FROM perhitungan_nota_manual_detail p GROUP BY p.no_reg ORDER BY no_reg) t "
    + "WHERE nm.no_reg = i.no_reg AND nm.cust_code = c.cust_code AND t.no_reg = nm.no_reg ORDER BY no_reg")})
public class PerhitunganNotaManual implements Serializable, EntityAuditable {

    private static final long serialVersionUID = 1L;
    @Column(name = "no_ppkb", length = 30)
    private String noPpkb;
    @Column(name = "no_reg", length = 30)
    private String noReg;
    @Column(name = "cust_code", length = 10)
    private String custCode;
    @Column(name = "jumlah_tagihan", precision = 19, scale = 2)
    private BigDecimal jumlahTagihan;
    @Column(name = "print_counter")
    private Integer printCounter;
    @Column(name = "rev_counter")
    private Integer revCounter;
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
    @Id
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigDecimal id;
    @Column(name = "no_bentuk_tiga", length = 30)
    private String noBentukTiga;
    

    public PerhitunganNotaManual() {
    }

    public PerhitunganNotaManual(BigDecimal id) {
        this.id = id;
    }

    public PerhitunganNotaManual(BigDecimal id, String createdBy, Date createdDate) {
        this.id = id;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
    }

    public String getNoPpkb() {
        return noPpkb;
    }

    public void setNoPpkb(String noPpkb) {
        this.noPpkb = noPpkb;
    }

    public String getNoReg() {
        return noReg;
    }

    public void setNoReg(String noReg) {
        this.noReg = noReg;
    }

    public String getCustCode() {
        return custCode;
    }

    public void setCustCode(String custCode) {
        this.custCode = custCode;
    }

    public BigDecimal getJumlahTagihan() {
        return jumlahTagihan;
    }

    public void setJumlahTagihan(BigDecimal jumlahTagihan) {
        this.jumlahTagihan = jumlahTagihan;
    }

    public Integer getPrintCounter() {
        return printCounter;
    }

    public void setPrintCounter(Integer printCounter) {
        this.printCounter = printCounter;
    }

    public Integer getRevCounter() {
        return revCounter;
    }

    public void setRevCounter(Integer revCounter) {
        this.revCounter = revCounter;
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

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getNoBentukTiga() {
        return noBentukTiga;
    }

    public void setNoBentukTiga(String noBentukTiga) {
        this.noBentukTiga = noBentukTiga;
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
        if (!(object instanceof PerhitunganNotaManual)) {
            return false;
        }
        PerhitunganNotaManual other = (PerhitunganNotaManual) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.PerhitunganNotaManual[id=" + id + "]";
    }
}
