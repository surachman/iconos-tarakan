/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.model.db.master;

import com.qtasnim.persistence.AuditTrailEntityListener;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author dycoder
 */
@Entity
@EntityListeners({AuditTrailEntityListener.class})
@Table(name = "m_payment_type")
@NamedQueries({
    @NamedQuery(name = "MPaymentType.findAll", query = "SELECT m FROM MPaymentType m"),
    @NamedQuery(name = "MPaymentType.findByPaymentType", query = "SELECT m FROM MPaymentType m WHERE m.paymentTool = :paymentTool"),
    @NamedQuery(name = "MPaymentType.findByKeterangan", query = "SELECT m FROM MPaymentType m WHERE m.keterangan = :keterangan")})
@NamedNativeQueries({
    @NamedNativeQuery(name = "MPaymentType.Native.findByAll", query = "SELECT m.payment_tool, m.keterangan FROM m_payment_type m")
})
public class MPaymentType implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "payment_tool", nullable = false, length = 10)
    private String paymentTool;
    @Column(name = "keterangan", length = 80)
    private String keterangan;

    public MPaymentType() {
    }

    public MPaymentType(String paymentTool) {
        this.paymentTool = paymentTool;
    }

    public String getPaymentTool() {
        return paymentTool;
    }

    public void setPaymentTool(String paymentTool) {
        this.paymentTool = paymentTool;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (paymentTool != null ? paymentTool.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MPaymentType)) {
            return false;
        }
        MPaymentType other = (MPaymentType) object;
        if ((this.paymentTool == null && other.paymentTool != null) || (this.paymentTool != null && !this.paymentTool.equals(other.paymentTool))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.master.MPaymentType[paymentTool=" + paymentTool + "]";
    }
}
