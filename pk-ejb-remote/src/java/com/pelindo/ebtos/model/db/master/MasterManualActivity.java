/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.model.db.master;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Dyware-Dev01
 */
@Entity
@Table(name = "m_manual_activity")
@NamedQueries({
    @NamedQuery(name = "MasterManualActivity.findAll", query = "SELECT m FROM MasterManualActivity m"),
    @NamedQuery(name = "MasterManualActivity.findById", query = "SELECT m FROM MasterManualActivity m WHERE m.id = :id"),
    @NamedQuery(name = "MasterManualActivity.findByName", query = "SELECT m FROM MasterManualActivity m WHERE m.name = :name"),
    @NamedQuery(name = "MasterManualActivity.findByDesc1", query = "SELECT m FROM MasterManualActivity m WHERE m.desc1 = :desc1"),
    @NamedQuery(name = "MasterManualActivity.findByDesc2", query = "SELECT m FROM MasterManualActivity m WHERE m.desc2 = :desc2"),
    @NamedQuery(name = "MasterManualActivity.findByTarif", query = "SELECT m FROM MasterManualActivity m WHERE m.tarif = :tarif")})
@NamedNativeQueries({
    @NamedNativeQuery(name = "MasterManualActivity.Native.findByType", query = "SELECT id, name, tarif FROM m_manual_activity WHERE id_manual_activity_type = ?")
    })
public class MasterManualActivity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @Column(name = "name", nullable = false, length = 256)
    private String name;
    @Column(name = "desc_1", length = 256)
    private String desc1;
    @Column(name = "desc_2", length = 256)
    private String desc2;
    @Basic(optional = false)
    @Column(name = "tarif", nullable = false, precision = 19, scale = 2)
    private BigDecimal tarif;
    @JoinColumn(name = "id_manual_activity_type", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private MasterManualActivityType mManualActivityType;

    public MasterManualActivity() {
    }

    public MasterManualActivity(Integer id) {
        this.id = id;
    }

    public MasterManualActivity(Integer id, String name, BigDecimal tarif) {
        this.id = id;
        this.name = name;
        this.tarif = tarif;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc1() {
        return desc1;
    }

    public void setDesc1(String desc1) {
        this.desc1 = desc1;
    }

    public String getDesc2() {
        return desc2;
    }

    public void setDesc2(String desc2) {
        this.desc2 = desc2;
    }

    public BigDecimal getTarif() {
        return tarif;
    }

    public void setTarif(BigDecimal tarif) {
        this.tarif = tarif;
    }

    public MasterManualActivityType getMasterManualActivityType() {
        return mManualActivityType;
    }

    public void setMasterManualActivityType(MasterManualActivityType mManualActivityType) {
        this.mManualActivityType = mManualActivityType;
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
        if (!(object instanceof MasterManualActivity)) {
            return false;
        }
        MasterManualActivity other = (MasterManualActivity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.master.MasterManualActivity[id=" + id + "]";
    }
}
