/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.model.db.master;

import com.qtasnim.persistence.AuditTrailEntityListener;
import com.qtasnim.persistence.EntityAuditable;
import com.pelindo.ebtos.model.db.PreserviceVessel;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author senoanggoro
 */
@Entity
@EntityListeners({AuditTrailEntityListener.class})
@Table(name = "m_vessel")
@NamedQueries({
    @NamedQuery(name = "MasterVessel.findAll", query = "SELECT m FROM MasterVessel m"),
    @NamedQuery(name = "MasterVessel.findByVesselCode", query = "SELECT m FROM MasterVessel m WHERE m.vesselCode = :vesselCode"),
    @NamedQuery(name = "MasterVessel.findByName", query = "SELECT m FROM MasterVessel m WHERE m.name = :name"),
    @NamedQuery(name = "MasterVessel.findByCallSign", query = "SELECT m FROM MasterVessel m WHERE m.callSign = :callSign"),
    @NamedQuery(name = "MasterVessel.findByFlag", query = "SELECT m FROM MasterVessel m WHERE m.flag = :flag"),
    @NamedQuery(name = "MasterVessel.findByGrt", query = "SELECT m FROM MasterVessel m WHERE m.grt = :grt"),
    @NamedQuery(name = "MasterVessel.findByDwt", query = "SELECT m FROM MasterVessel m WHERE m.dwt = :dwt"),
    @NamedQuery(name = "MasterVessel.findByBrt", query = "SELECT m FROM MasterVessel m WHERE m.brt = :brt"),
    @NamedQuery(name = "MasterVessel.findByNrt", query = "SELECT m FROM MasterVessel m WHERE m.nrt = :nrt"),
    @NamedQuery(name = "MasterVessel.findByLoa", query = "SELECT m FROM MasterVessel m WHERE m.loa = :loa"),
    @NamedQuery(name = "MasterVessel.findByJumlahPalka", query = "SELECT m FROM MasterVessel m WHERE m.jumlahPalka = :jumlahPalka"),
    @NamedQuery(name = "MasterVessel.findByJumlahDerek", query = "SELECT m FROM MasterVessel m WHERE m.jumlahDerek = :jumlahDerek"),
    @NamedQuery(name = "MasterVessel.findByLinerTramper", query = "SELECT m FROM MasterVessel m WHERE m.linerTramper = :linerTramper"),
    @NamedQuery(name = "MasterVessel.findByTahunPembuatan", query = "SELECT m FROM MasterVessel m WHERE m.tahunPembuatan = :tahunPembuatan"),
    @NamedQuery(name = "MasterVessel.findByNote", query = "SELECT m FROM MasterVessel m WHERE m.note = :note"),
    @NamedQuery(name = "MasterVessel.findByStatus", query = "SELECT m FROM MasterVessel m WHERE m.status = :status")})


    @NamedNativeQueries({
    @NamedNativeQuery(name ="MasterVessel.Native.findMasterVessels",query="SELECT m.vessel_code,m.name,m.grt,m.dwt,m.nrt,m.loa,m.jumlah_palka,m.note,m.tahun_pembuatan,m.cust_code,m.vessel_type_code,mc.name FROM m_vessel m,m_customer mc where m.cust_code=mc.cust_code "),
    @NamedNativeQuery(name="MasterVessel.Native.findMasterVesselsByVesselCode",query="SELECT m.vessel_code,m.name,m.grt,m.dwt,m.brt,m.nrt,m.loa FROM m_vessel m WHERE m.vessel_code = ?"),
    @NamedNativeQuery(name="MasterVessel.Native.findMasterVesselsByVesselName",query="SELECT m.vessel_code,m.name,m.grt,m.dwt,m.brt,m.nrt,m.loa FROM m_vessel m WHERE UPPER (m.name) LIKE UPPER (?)"),
    @NamedNativeQuery(name ="MasterVessel.Native.findMasterVesselByCodeDelete",query="SELECT v.vessel_code, v.vessel_type_code FROM m_vessel v, m_vessel_type p WHERE v.vessel_type_code = p.vessel_type_code AND v.vessel_type_code = ?"),
    @NamedNativeQuery(name ="MasterVessel.Native.findMasterVesselByPPKB",query="SELECT mv.vessel_code, mv.name FROM planning_vessel pv, preservice_vessel pp, m_vessel mv where pv.booking_code=pp.booking_code AND pp.vessel_code = mv.vessel_code AND pv.no_ppkb=?"),
    @NamedNativeQuery(name ="MasterVessel.Native.findMasterVesselsForChoice",query="SELECT mv.vessel_code, mv.name, mv.call_sign, vt.name, mc.name FROM m_vessel mv, m_vessel_type vt, m_customer mc WHERE mv.vessel_type_code = vt.vessel_type_code AND mv.cust_code = mc.cust_code ORDER BY mv.vessel_code"),
    @NamedNativeQuery(name ="MasterVessel.Native.findMasterVesselDetail",query="SELECT mv.vessel_code, mv.name, mv.call_sign, mv.flag, mv.grt, mv.dwt, mv.brt, mv.nrt, mv.loa, mv.jumlah_palka, mv.jumlah_derek, mv.liner_tramper, mv.tahun_pembuatan, mv.status, mv.start_deck, vt.name, vt.tonage, mc.name, mc.address, mc.phone1, mc.phone2, mc.fax, mc.city, ct.name, c.name  FROM m_vessel mv, m_vessel_type vt, m_customer mc, m_cust_type ct, m_country c WHERE mv.vessel_type_code = vt.vessel_type_code AND mv.cust_code = mc.cust_code AND mc.cust_type_id = ct.cust_type_id AND mc.country_code = c.country_code AND mv.vessel_code = ?"),
    @NamedNativeQuery(name ="MasterVessel.Native.findMasterVesselsByCustCode",query="SELECT vessel_code, name, grt, dwt, nrt, loa, jumlah_palka, note, tahun_pembuatan, cust_code, vessel_type_code FROM m_vessel WHERE cust_code=?")})

public class MasterVessel implements Serializable, EntityAuditable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "vessel_code", nullable = false, length = 10)
    private String vesselCode;
    @Column(name = "name", length = 50)
    private String name;
    @Column(name = "call_sign", length = 10)
    private String callSign;
    @Column(name = "flag", length = 10)
    private String flag;
    @Column(name = "grt")
    private Integer grt;
    @Column(name = "dwt")
    private Integer dwt;
    @Column(name = "brt")
    private Integer brt;
    @Column(name = "nrt")
    private Integer nrt;
    @Column(name = "loa")
    private Integer loa;
    @Column(name = "jumlah_palka")
    private Short jumlahPalka;
    @Column(name = "jumlah_derek")
    private Short jumlahDerek;
    @Column(name = "liner_tramper")
    private Boolean linerTramper;
    @Column(name = "tahun_pembuatan")
    private Short tahunPembuatan;
    @Column(name = "note", length = 256)
    private String note;
    @Column(name = "status", length = 10)
    private String status;
    @Column(name = "start_deck")
    private Short startDeck;
    @Column(name = "cont_capacity")
    private Integer contCapacity;
    @JoinColumn(name = "vessel_type_code", referencedColumnName = "vessel_type_code")
    @ManyToOne
    private MasterVesselType masterVesselType;
    @JoinColumn(name = "cust_code", referencedColumnName = "cust_code")
    @ManyToOne
    private MasterCustomer masterCustomer;
    @OneToMany(mappedBy = "masterVessel")
    private List<MasterVesselProfile> masterVesselProfileList;
    @OneToMany(mappedBy = "masterVessel")
    private List<PreserviceVessel> preserviceVesselList;
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
    @Basic(optional = false)
    @Column(name = "kapal_bayangan", nullable = false)
    private boolean kapalBayangan = false;

    public MasterVessel() {
    }

    public MasterVessel(String vesselCode) {
        this.vesselCode = vesselCode;
    }

    public String getVesselCode() {
        return vesselCode;
    }

    public void setVesselCode(String vesselCode) {
        this.vesselCode = vesselCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCallSign() {
        return callSign;
    }

    public void setCallSign(String callSign) {
        this.callSign = callSign;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Integer getGrt() {
        return grt;
    }

    public void setGrt(Integer grt) {
        this.grt = grt;
    }

    public Integer getDwt() {
        return dwt;
    }

    public void setDwt(Integer dwt) {
        this.dwt = dwt;
    }

    public Integer getBrt() {
        return brt;
    }

    public void setBrt(Integer brt) {
        this.brt = brt;
    }

    public Integer getNrt() {
        return nrt;
    }

    public void setNrt(Integer nrt) {
        this.nrt = nrt;
    }

    public Integer getLoa() {
        return loa;
    }

    public void setLoa(Integer loa) {
        this.loa = loa;
    }

    public Short getJumlahPalka() {
        return jumlahPalka;
    }

    public void setJumlahPalka(Short jumlahPalka) {
        this.jumlahPalka = jumlahPalka;
    }

    public Short getJumlahDerek() {
        return jumlahDerek;
    }

    public void setJumlahDerek(Short jumlahDerek) {
        this.jumlahDerek = jumlahDerek;
    }

    public Boolean getLinerTramper() {
        return linerTramper;
    }

    public void setLinerTramper(Boolean linerTramper) {
        this.linerTramper = linerTramper;
    }

    public Short getTahunPembuatan() {
        return tahunPembuatan;
    }

    public void setTahunPembuatan(Short tahunPembuatan) {
        this.tahunPembuatan = tahunPembuatan;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the startDeck
     */
    public Short getStartDeck() {
        return startDeck;
    }

    /**
     * @param startDeck the startDeck to set
     */
    public void setStartDeck(Short startDeck) {
        this.startDeck = startDeck;
    }

    public MasterVesselType getMasterVesselType() {
        return masterVesselType;
    }

    public void setMasterVesselType(MasterVesselType masterVesselType) {
        this.masterVesselType = masterVesselType;
    }

    public MasterCustomer getMasterCustomer() {
        return masterCustomer;
    }

    public void setMasterCustomer(MasterCustomer masterCustomer) {
        this.masterCustomer = masterCustomer;
    }

    public List<MasterVesselProfile> getMasterVesselProfileList() {
        return masterVesselProfileList;
    }

    public void setMasterVesselProfileList(List<MasterVesselProfile> masterVesselProfileList) {
        this.masterVesselProfileList = masterVesselProfileList;
    }

    public List<PreserviceVessel> getPreserviceVesselList() {
        return preserviceVesselList;
    }

    public void setPreserviceVesselList(List<PreserviceVessel> preserviceVesselList) {
        this.preserviceVesselList = preserviceVesselList;
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

    public Integer getContCapacity() {
        return contCapacity;
    }

    public void setContCapacity(Integer contCapacity) {
        this.contCapacity = contCapacity;
    }

    public boolean isKapalBayangan() {
        return kapalBayangan;
    }

    public void setKapalBayangan(boolean kapalBayangan) {
        this.kapalBayangan = kapalBayangan;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (vesselCode != null ? vesselCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MasterVessel)) {
            return false;
        }
        MasterVessel other = (MasterVessel) object;
        if ((this.vesselCode == null && other.vesselCode != null) || (this.vesselCode != null && !this.vesselCode.equals(other.vesselCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.MasterVessel[vesselCode=" + vesselCode + "]";
    }
}
