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
@Table(name = "recap_jurnal_invoice")
@NamedQueries({
    @NamedQuery(name = "RecapJurnalInvoice.findAll", query = "SELECT r FROM RecapJurnalInvoice r"),
    @NamedQuery(name = "RecapJurnalInvoice.findById", query = "SELECT r FROM RecapJurnalInvoice r WHERE r.id = :id")})
public class RecapJurnalInvoice implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @JoinColumn(name = "sumber", referencedColumnName = "sumber", nullable = false)
    @ManyToOne(optional = false)
    private RecapJurnal recapJurnal;
    @JoinColumn(name = "no_invoice", referencedColumnName = "no_invoice", nullable = false)
    @ManyToOne(optional = false)
    private Invoice invoice;

    public RecapJurnalInvoice() {
    }

    public RecapJurnalInvoice(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public RecapJurnal getRecapJurnal() {
        return recapJurnal;
    }

    public void setRecapJurnal(RecapJurnal recapJurnal) {
        this.recapJurnal = recapJurnal;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
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
        if (!(object instanceof RecapJurnalInvoice)) {
            return false;
        }
        RecapJurnalInvoice other = (RecapJurnalInvoice) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.RecapJurnalInvoice[id=" + id + "]";
    }

}
