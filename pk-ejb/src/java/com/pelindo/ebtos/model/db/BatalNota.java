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
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
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
 * @author R Seno Anggoro
 */
@Entity
@EntityListeners({AuditTrailEntityListener.class})
@Table(name = "batal_nota")
@NamedQueries({
    @NamedQuery(name = "BatalNota.findAll", query = "SELECT b FROM BatalNota b"),
    @NamedQuery(name = "BatalNota.findByNoInvoice", query = "SELECT b FROM BatalNota b WHERE b.noInvoice = :noInvoice"),
    @NamedQuery(name = "BatalNota.findByNoReg", query = "SELECT b FROM BatalNota b WHERE b.noReg = :noReg"),
    @NamedQuery(name = "BatalNota.findByCustCode", query = "SELECT b FROM BatalNota b WHERE b.custCode = :custCode"),
    @NamedQuery(name = "BatalNota.findByPaymentDate", query = "SELECT b FROM BatalNota b WHERE b.paymentDate = :paymentDate"),
    @NamedQuery(name = "BatalNota.findByTotalTagihan", query = "SELECT b FROM BatalNota b WHERE b.totalTagihan = :totalTagihan"),
    @NamedQuery(name = "BatalNota.findByNoBeritaAcara", query = "SELECT b FROM BatalNota b WHERE b.noBeritaAcara = :noBeritaAcara"),
    @NamedQuery(name = "BatalNota.findByAlasanPembatalan", query = "SELECT b FROM BatalNota b WHERE b.alasanPembatalan = :alasanPembatalan"),
    @NamedQuery(name = "BatalNota.findByTglPembatalan", query = "SELECT b FROM BatalNota b WHERE b.tglPembatalan = :tglPembatalan"),
    @NamedQuery(name = "BatalNota.findByCreatedBy", query = "SELECT b FROM BatalNota b WHERE b.createdBy = :createdBy"),
    @NamedQuery(name = "BatalNota.findByCreatedDate", query = "SELECT b FROM BatalNota b WHERE b.createdDate = :createdDate"),
    @NamedQuery(name = "BatalNota.findByModifiedBy", query = "SELECT b FROM BatalNota b WHERE b.modifiedBy = :modifiedBy"),
    @NamedQuery(name = "BatalNota.findByModifiedDate", query = "SELECT b FROM BatalNota b WHERE b.modifiedDate = :modifiedDate")})

     @NamedNativeQueries({

/* 
* Updated by SRACH
* Mon 27 Oct 2014 05:09:24 PM WIT 
* Update Notes: Upgrade to Oracle 11g SQL Compliance
*/
     
/*     
    @NamedNativeQuery(name = "BatalNota.Native.findByBatalNotaList", query = "SELECT CASE WHEN i.no_invoice IS NOT NULL THEN i.no_invoice ELSE b.no_invoice END,i.no_reg,i.payment_date,m.name,i.total_tagihan,b.no_berita_acara,b.alasan_pembatalan,i.modified_date,i.curr_code,CASE WHEN i.no_faktur_pajak IS NOT NULL THEN i.no_faktur_pajak ELSE b.no_faktur_pajak END,r.pengurus_do,c.symbol FROM invoice i LEFT JOIN batal_nota b ON b.no_reg=i.no_reg JOIN registration r ON r.no_reg=i.no_reg JOIN m_customer m ON r.cust_code=m.cust_code JOIN m_currency c ON i.curr_code=c.curr_code WHERE i.cancel_invoice = TRUE"
*/
    
    @NamedNativeQuery(name = "BatalNota.Native.findByBatalNotaList", query = 
"SELECT " 
+"  CASE " 
+"    WHEN i.no_invoice IS NOT NULL " 
+"    THEN i.no_invoice " 
+"    ELSE b.no_invoice " 
+"  END, " 
+"  i.no_reg, " 
+"  i.payment_date, " 
+"  m.name, " 
+"  i.total_tagihan, " 
+"  b.no_berita_acara, " 
+"  b.alasan_pembatalan, " 
+"  i.modified_date, " 
+"  i.curr_code, " 
+"  CASE " 
+"    WHEN i.no_faktur_pajak IS NOT NULL " 
+"    THEN i.no_faktur_pajak " 
+"    ELSE b.no_faktur_pajak " 
+"  END, " 
+"  r.pengurus_do, " 
+"  c.symbol " 
+"FROM invoice i " 
+"LEFT JOIN batal_nota b " 
+"ON b.no_reg=i.no_reg " 
+"JOIN registration r " 
+"ON r.no_reg=i.no_reg " 
+"JOIN m_customer m " 
+"ON r.cust_code=m.cust_code " 
+"JOIN m_currency c " 
+"ON i.curr_code         =c.curr_code " 
+"WHERE i.cancel_invoice = 'TRUE'"    
    
    )})

public class BatalNota implements Serializable, EntityAuditable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "no_invoice", nullable = false, length = 30)
    private String noInvoice;
    @Basic(optional = false)
    @Column(name = "no_reg", nullable = false, length = 30)
    private String noReg;
    @Basic(optional = false)
    @Column(name = "cust_code", nullable = false, length = 10)
    private String custCode;
    @Column(name = "payment_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date paymentDate;
    @Column(name = "total_tagihan", precision = 19, scale = 2)
    private BigDecimal totalTagihan;
    @Column(name = "no_berita_acara", length = 30)
    private String noBeritaAcara;
    @Column(name = "alasan_pembatalan", length = 256)
    private String alasanPembatalan;
    @Column(name = "tgl_pembatalan")
    @Temporal(TemporalType.TIME)
    private Date tglPembatalan;
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
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "batalNota")
    private RinciContBatalNota rinciContBatalNota;
    @Basic(optional = false)
    @Column(name = "no_faktur_pajak", nullable = false, length = 30)
    private String noFakturPajak;

    public BatalNota() {
    }

    public BatalNota(String noInvoice) {
        this.noInvoice = noInvoice;
    }

    public BatalNota(String noInvoice, String noReg, String custCode, String createdBy, Date createdDate) {
        this.noInvoice = noInvoice;
        this.noReg = noReg;
        this.custCode = custCode;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
    }

    public String getNoInvoice() {
        return noInvoice;
    }

    public void setNoInvoice(String noInvoice) {
        this.noInvoice = noInvoice;
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

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public BigDecimal getTotalTagihan() {
        return totalTagihan;
    }

    public void setTotalTagihan(BigDecimal totalTagihan) {
        this.totalTagihan = totalTagihan;
    }

    public String getNoBeritaAcara() {
        return noBeritaAcara;
    }

    public void setNoBeritaAcara(String noBeritaAcara) {
        this.noBeritaAcara = noBeritaAcara;
    }

    public String getAlasanPembatalan() {
        return alasanPembatalan;
    }

    public void setAlasanPembatalan(String alasanPembatalan) {
        this.alasanPembatalan = alasanPembatalan;
    }

    public Date getTglPembatalan() {
        return tglPembatalan;
    }

    public void setTglPembatalan(Date tglPembatalan) {
        this.tglPembatalan = tglPembatalan;
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

    public RinciContBatalNota getRinciContBatalNota() {
        return rinciContBatalNota;
    }

    public void setRinciContBatalNota(RinciContBatalNota rinciContBatalNota) {
        this.rinciContBatalNota = rinciContBatalNota;
    }

    public void setNoFakturPajak(String noFakturPajak) {
        this.noFakturPajak = noFakturPajak;
    }

    public String getNoFakturPajak() {
        return noFakturPajak;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (noInvoice != null ? noInvoice.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BatalNota)) {
            return false;
        }
        BatalNota other = (BatalNota) object;
        if ((this.noInvoice == null && other.noInvoice != null) || (this.noInvoice != null && !this.noInvoice.equals(other.noInvoice))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.BatalNota[noInvoice=" + noInvoice + "]";
    }
}
