/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.model.db;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author R. Seno Anggoro A
 */
@Entity
@Table(name = "usable_faktur_pajak", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"no_faktur_pajak"})})
@NamedQueries({
    @NamedQuery(name = "UsableFakturPajak.findAll", query = "SELECT u FROM UsableFakturPajak u"),
    @NamedQuery(name = "UsableFakturPajak.findById", query = "SELECT u FROM UsableFakturPajak u WHERE u.id = :id"),
    @NamedQuery(name = "UsableFakturPajak.findByNoFakturPajak", query = "SELECT u FROM UsableFakturPajak u WHERE u.noFakturPajak = :noFakturPajak")})
public class UsableFakturPajak implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Basic(optional = false)
    @Column(name = "no_faktur_pajak", nullable = false, length = 2147483647)
    private String noFakturPajak;

    public UsableFakturPajak() {
    }

    public UsableFakturPajak(Integer id) {
        this.id = id;
    }

    public UsableFakturPajak(String noFakturPajak) {
        this.noFakturPajak = noFakturPajak;
    }

    public UsableFakturPajak(Integer id, String noFakturPajak) {
        this.id = id;
        this.noFakturPajak = noFakturPajak;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNoFakturPajak() {
        return noFakturPajak;
    }

    public void setNoFakturPajak(String noFakturPajak) {
        this.noFakturPajak = noFakturPajak;
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
        if (!(object instanceof UsableFakturPajak)) {
            return false;
        }
        UsableFakturPajak other = (UsableFakturPajak) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.UsableFakturPajak[id=" + id + "]";
    }

}
