/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.model.db.master;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author R Seno Anggoro
 */
@Entity
@Table(name = "m_status_message")
@NamedQueries({
    @NamedQuery(name = "MasterStatusMessage.findAll", query = "SELECT m FROM MasterStatusMessage m"),
    @NamedQuery(name = "MasterStatusMessage.findByKey", query = "SELECT m FROM MasterStatusMessage m WHERE m.key = :key"),
    @NamedQuery(name = "MasterStatusMessage.findByValue", query = "SELECT m FROM MasterStatusMessage m WHERE m.value = :value")})
public class MasterStatusMessage implements Serializable {
    public static final String PAY001 = "pay001";
    public static final String PAY002 = "pay002";
    public static final String PAY003 = "pay003";
    public static final String INQ001 = "inq001";
    public static final String INQ002 = "inq002";
    public static final String INQ003 = "inq003";
    public static final String INQ004 = "inq004";
    public static final String INQ005 = "inq005";
    public static final String INQ006 = "inq006";

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "key", nullable = false, length = 10)
    private String key;
    @Column(name = "value", length = 256)
    private String value;

    public MasterStatusMessage() {
    }

    public MasterStatusMessage(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (key != null ? key.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MasterStatusMessage)) {
            return false;
        }
        MasterStatusMessage other = (MasterStatusMessage) object;
        if ((this.key == null && other.key != null) || (this.key != null && !this.key.equals(other.key))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.master.MasterStatusMessage[key=" + key + "]";
    }

}
