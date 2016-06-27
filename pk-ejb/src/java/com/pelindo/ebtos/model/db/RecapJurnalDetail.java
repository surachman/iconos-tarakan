/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.model.db;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author R Seno Anggoro
 */
@Entity
@Table(name = "recap_jurnal_detail")
@NamedQueries({
    @NamedQuery(name = "RecapJurnalDetail.findAll", query = "SELECT r FROM RecapJurnalDetail r"),
    @NamedQuery(name = "RecapJurnalDetail.findById", query = "SELECT r FROM RecapJurnalDetail r WHERE r.id = :id"),
    @NamedQuery(name = "RecapJurnalDetail.findByKodeRekening", query = "SELECT r FROM RecapJurnalDetail r WHERE r.kodeRekening = :kodeRekening"),
    @NamedQuery(name = "RecapJurnalDetail.findByMasterId", query = "SELECT r FROM RecapJurnalDetail r WHERE r.masterId = :masterId"),
    @NamedQuery(name = "RecapJurnalDetail.findByNoNota", query = "SELECT r FROM RecapJurnalDetail r WHERE r.noNota = :noNota"),
    @NamedQuery(name = "RecapJurnalDetail.findByJumlahDebit", query = "SELECT r FROM RecapJurnalDetail r WHERE r.jumlahDebit = :jumlahDebit"),
    @NamedQuery(name = "RecapJurnalDetail.findByJumlahKredit", query = "SELECT r FROM RecapJurnalDetail r WHERE r.jumlahKredit = :jumlahKredit"),
    @NamedQuery(name = "RecapJurnalDetail.findJKMDetail", query = "SELECT r FROM RecapJurnalDetail r WHERE r.recapJurnal.sumber = :sumber"),
    @NamedQuery(name = "RecapJurnalDetail.findByBiaya", query = "SELECT r FROM RecapJurnalDetail r WHERE r.biaya = :biaya")})
public class RecapJurnalDetail implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Basic(optional = false)
    @Column(name = "kode_rekening", nullable = false, length = 12)
    private String kodeRekening;
    @Column(name = "master_id", length = 17)
    private String masterId;
    @Column(name = "no_nota", length = 15)
    private String noNota;
    @Column(name = "nama_rekening", length = 15)
    private String namaRekening;
    @Basic(optional = false)
    @Column(name = "jumlah_debit", nullable = false, precision = 19, scale = 2)
    private BigDecimal jumlahDebit;
    @Basic(optional = false)
    @Column(name = "jumlah_kredit", nullable = false, precision = 19, scale = 2)
    private BigDecimal jumlahKredit;
    @Column(name = "biaya", length = 6)
    private String biaya;
    @JoinColumn(name = "sumber", referencedColumnName = "sumber", nullable = false)
    @ManyToOne(optional = false)
    private RecapJurnal recapJurnal;

    public RecapJurnalDetail() {
    }

    public RecapJurnalDetail(Integer id) {
        this.id = id;
    }

    public RecapJurnalDetail(Integer id, String kodeRekening, BigDecimal jumlahDebit, BigDecimal jumlahKredit) {
        this.id = id;
        this.kodeRekening = kodeRekening;
        this.jumlahDebit = jumlahDebit;
        this.jumlahKredit = jumlahKredit;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getKodeRekening() {
        return kodeRekening;
    }

    public void setKodeRekening(String kodeRekening) {
        this.kodeRekening = kodeRekening;
    }

    public String getMasterId() {
        return masterId;
    }

    public void setMasterId(String masterId) {
        this.masterId = masterId;
    }

    public String getNoNota() {
        return noNota;
    }

    public void setNoNota(String noNota) {
        this.noNota = noNota;
    }

    public BigDecimal getJumlahDebit() {
        return jumlahDebit;
    }

    public void setJumlahDebit(BigDecimal jumlahDebit) {
        this.jumlahDebit = jumlahDebit;
    }

    public BigDecimal getJumlahKredit() {
        return jumlahKredit;
    }

    public void setJumlahKredit(BigDecimal jumlahKredit) {
        this.jumlahKredit = jumlahKredit;
    }

    public String getBiaya() {
        return biaya;
    }

    public void setBiaya(String biaya) {
        this.biaya = biaya;
    }

    public RecapJurnal getRecapJurnal() {
        return recapJurnal;
    }

    public void setRecapJurnal(RecapJurnal recapJurnal) {
        this.recapJurnal = recapJurnal;
    }

    public String getNamaRekening() {
        return namaRekening;
    }

    public void setNamaRekening(String namaRekening) {
        this.namaRekening = namaRekening;
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
        if (!(object instanceof RecapJurnalDetail)) {
            return false;
        }
        RecapJurnalDetail other = (RecapJurnalDetail) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.RecapJurnalDetail[id=" + id + "]";
    }

}
