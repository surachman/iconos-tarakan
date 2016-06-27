/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.model.db;

import com.pelindo.ebtos.model.db.master.MasterCustomer;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author R Seno Anggoro
 */
@Entity
@Table(name = "recap_jurnal", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"no_jurnal"})})
@NamedQueries({
    @NamedQuery(name = "RecapJurnal.findAll", query = "SELECT r FROM RecapJurnal r"),
    @NamedQuery(name = "RecapJurnal.findByNoJurnal", query = "SELECT r FROM RecapJurnal r WHERE r.noJurnal = :noJurnal"),
    @NamedQuery(name = "RecapJurnal.findTotalTagihanBySumber", query = "SELECT SUM(r.invoice.totalTagihan) FROM RecapJurnalInvoice r WHERE r.recapJurnal.sumber = :sumber"),
    @NamedQuery(name = "RecapJurnal.findByTipeJurnalId", query = "SELECT r FROM RecapJurnal r WHERE r.tipeJurnalId = :tipeJurnalId"),
    @NamedQuery(name = "RecapJurnal.findByTanggal", query = "SELECT r FROM RecapJurnal r WHERE r.tanggal = :tanggal"),
    @NamedQuery(name = "RecapJurnal.findBySumber", query = "SELECT r FROM RecapJurnal r WHERE r.sumber = :sumber"),
    @NamedQuery(name = "RecapJurnal.findInvoicesBySumber", query = "SELECT r.invoice FROM RecapJurnalInvoice r WHERE r.recapJurnal.sumber = :sumber"),
    @NamedQuery(name = "RecapJurnal.findByMataUangId", query = "SELECT r FROM RecapJurnal r WHERE r.mataUangId = :mataUangId"),
    @NamedQuery(name = "RecapJurnal.findByKurs", query = "SELECT r FROM RecapJurnal r WHERE r.kurs = :kurs"),
    @NamedQuery(name = "RecapJurnal.findByKeterangan", query = "SELECT r FROM RecapJurnal r WHERE r.keterangan = :keterangan"),
    @NamedQuery(name = "RecapJurnal.findByUserName", query = "SELECT r FROM RecapJurnal r WHERE r.userName = :userName"),
    @NamedQuery(name = "RecapJurnal.findByWorkStation", query = "SELECT r FROM RecapJurnal r WHERE r.workStation = :workStation"),
    @NamedQuery(name = "RecapJurnal.findByCabangId", query = "SELECT r FROM RecapJurnal r WHERE r.cabangId = :cabangId"),
    @NamedQuery(name = "RecapJurnal.findByCaraPenerimaan", query = "SELECT r FROM RecapJurnal r WHERE r.caraPenerimaan = :caraPenerimaan")})
public class RecapJurnal implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "no_jurnal", nullable = false, length = 12)
    private String noJurnal;
    @Basic(optional = false)
    @Column(name = "tipe_jurnal_id", nullable = false, length = 3)
    private String tipeJurnalId;
    @Column(name = "tanggal")
    @Temporal(TemporalType.DATE)
    private Date tanggal;
    @Id
    @Basic(optional = false)
    @Column(name = "sumber", nullable = false, length = 12)
    private String sumber;
    @Basic(optional = false)
    @Column(name = "mata_uang_id", nullable = false, length = 3)
    private String mataUangId;
    @Basic(optional = false)
    @Column(name = "kurs", precision = 13, scale = 2)
    private BigDecimal kurs;
    @Column(name = "keterangan", length = 300)
    private String keterangan;
    @Basic(optional = false)
    @Column(name = "user_name", length = 30)
    private String userName;
    @Basic(optional = false)
    @Column(name = "work_station", length = 30)
    private String workStation;
    @Basic(optional = false)
    @Column(name = "cabang_id", length = 2)
    private String cabangId;
    @Column(name = "cara_penerimaan", length = 30)
    private String caraPenerimaan;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recapJurnal")
    @OrderBy(value="kodeRekening")
    private List<RecapJurnalDetail> recapJurnalDetailList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recapJurnal")
    private List<RecapJurnalInvoice> recapJurnalInvoiceList;
    @JoinColumn(name = "cust_code", referencedColumnName = "cust_code")
    @ManyToOne
    private MasterCustomer masterCustomer;

    public RecapJurnal() {
    }

    public RecapJurnal(String sumber) {
        this.sumber = sumber;
    }

    public RecapJurnal(String sumber, String tipeJurnalId, String mataUangId, String userName) {
        this.sumber = sumber;
        this.tipeJurnalId = tipeJurnalId;
        this.mataUangId = mataUangId;
        this.userName = userName;
    }

    public String getNoJurnal() {
        return noJurnal;
    }

    public void setNoJurnal(String noJurnal) {
        this.noJurnal = noJurnal;
    }

    public String getTipeJurnalId() {
        return tipeJurnalId;
    }

    public void setTipeJurnalId(String tipeJurnalId) {
        this.tipeJurnalId = tipeJurnalId;
    }

    public Date getTanggal() {
        return tanggal;
    }

    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }

    public String getSumber() {
        return sumber;
    }

    public void setSumber(String sumber) {
        this.sumber = sumber;
    }

    public String getMataUangId() {
        return mataUangId;
    }

    public void setMataUangId(String mataUangId) {
        this.mataUangId = mataUangId;
    }

    public BigDecimal getKurs() {
        return kurs;
    }

    public void setKurs(BigDecimal kurs) {
        this.kurs = kurs;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getWorkStation() {
        return workStation;
    }

    public void setWorkStation(String workStation) {
        this.workStation = workStation;
    }

    public String getCabangId() {
        return cabangId;
    }

    public void setCabangId(String cabangId) {
        this.cabangId = cabangId;
    }

    public String getCaraPenerimaan() {
        return caraPenerimaan;
    }

    public void setCaraPenerimaan(String caraPenerimaan) {
        this.caraPenerimaan = caraPenerimaan;
    }

    public List<RecapJurnalDetail> getRecapJurnalDetailList() {
        return recapJurnalDetailList;
    }

    public void setRecapJurnalDetailList(List<RecapJurnalDetail> recapJurnalDetailList) {
        this.recapJurnalDetailList = recapJurnalDetailList;
    }

    public List<RecapJurnalInvoice> getRecapJurnalInvoiceList() {
        return recapJurnalInvoiceList;
    }

    public void setRecapJurnalInvoiceList(List<RecapJurnalInvoice> recapJurnalInvoiceList) {
        this.recapJurnalInvoiceList = recapJurnalInvoiceList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sumber != null ? sumber.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RecapJurnal)) {
            return false;
        }
        RecapJurnal other = (RecapJurnal) object;
        if ((this.sumber == null && other.sumber != null) || (this.sumber != null && !this.sumber.equals(other.sumber))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.RecapJurnal[sumber=" + sumber + "]";
    }

    public MasterCustomer getMasterCustomer() {
        return masterCustomer;
    }

    public void setMasterCustomer(MasterCustomer masterCustomer) {
        this.masterCustomer = masterCustomer;
    }

}
