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
@Table(name = "usable_invoice", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"no_invoice"})})
@NamedQueries({
    @NamedQuery(name = "UsableInvoice.findAll", query = "SELECT u FROM UsableInvoice u"),
    @NamedQuery(name = "UsableInvoice.findById", query = "SELECT u FROM UsableInvoice u WHERE u.id = :id"),
    @NamedQuery(name = "UsableInvoice.findByNoInvoice", query = "SELECT u FROM UsableInvoice u WHERE u.noInvoice = :noInvoice")})
public class UsableInvoice implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Basic(optional = false)
    @Column(name = "no_invoice", nullable = false, length = 2147483647)
    private String noInvoice;

    public UsableInvoice() {
    }

    public UsableInvoice(String noInvoice) {
        this.noInvoice = noInvoice;
    }

    public UsableInvoice(Integer id, String noInvoice) {
        this.id = id;
        this.noInvoice = noInvoice;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNoInvoice() {
        return noInvoice;
    }

    public void setNoInvoice(String noInvoice) {
        this.noInvoice = noInvoice;
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
        if (!(object instanceof UsableInvoice)) {
            return false;
        }
        UsableInvoice other = (UsableInvoice) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.UsableInvoice[id=" + id + "]";
    }

}
