/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.model.db;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author USER
 */
@Entity
@Table(name = "NO_PERHITUNGAN_BENTUK_TIGA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NoPerhitunganBentukTiga.findAll", query = "SELECT p FROM NoPerhitunganBentukTiga p"),
    @NamedQuery(name = "NoPerhitunganBentukTiga.findByNoPpkb", query = "SELECT p FROM NoPerhitunganBentukTiga p WHERE p.noPpkb = :noPpkb"),
    @NamedQuery(name = "NoPerhitunganBentukTiga.findByNoRegistrasi", query = "SELECT p FROM NoPerhitunganBentukTiga p WHERE p.noRegistrasi = :noRegistrasi"),
    @NamedQuery(name = "NoPerhitunganBentukTiga.findByCreateDate", query = "SELECT p FROM NoPerhitunganBentukTiga p WHERE p.createDate = :createDate")})
public class NoPerhitunganBentukTiga implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "NO_PPKB")
    private String noPpkb;
    @Size(max = 40)
    @Column(name = "NO_REGISTRASI")
    private String noRegistrasi;
    @Column(name = "CREATE_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    public NoPerhitunganBentukTiga() {
    }

    public NoPerhitunganBentukTiga(String noPpkb) {
        this.noPpkb = noPpkb;
    }

    public String getNoPpkb() {
        return noPpkb;
    }

    public void setNoPpkb(String noPpkb) {
        this.noPpkb = noPpkb;
    }

    public String getNoRegistrasi() {
        return noRegistrasi;
    }

    public void setNoRegistrasi(String noRegistrasi) {
        this.noRegistrasi = noRegistrasi;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (noPpkb != null ? noPpkb.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NoPerhitunganBentukTiga)) {
            return false;
        }
        NoPerhitunganBentukTiga other = (NoPerhitunganBentukTiga) object;
        if ((this.noPpkb == null && other.noPpkb != null) || (this.noPpkb != null && !this.noPpkb.equals(other.noPpkb))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.PermohonanBentukTiga[ noPpkb=" + noPpkb + " ]";
    }
    
}
