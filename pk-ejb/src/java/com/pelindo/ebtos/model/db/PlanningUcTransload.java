/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.model.db;

import com.qtasnim.persistence.AuditTrailEntityListener;
import com.qtasnim.persistence.EntityAuditable;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author senoanggoro
 */
@Entity
@EntityListeners({AuditTrailEntityListener.class})
@Table(name = "planning_uc_transload")
@NamedQueries({
    @NamedQuery(name = "PlanningUcTransload.findAll", query = "SELECT p FROM PlanningUcTransload p"),
    @NamedQuery(name = "PlanningUcTransload.findById", query = "SELECT p FROM PlanningUcTransload p WHERE p.id = :id"),
    @NamedQuery(name = "PlanningUcTransload.findByCommodityCode", query = "SELECT p FROM PlanningUcTransload p WHERE p.commodityCode = :commodityCode"),
    @NamedQuery(name = "PlanningUcTransload.findByBlock", query = "SELECT p FROM PlanningUcTransload p WHERE p.block = :block"),
    @NamedQuery(name = "PlanningUcTransload.findByNoBl", query = "SELECT p FROM PlanningUcTransload p WHERE p.noBl = :noBl"),
    @NamedQuery(name = "PlanningUcTransload.findByWeight", query = "SELECT p FROM PlanningUcTransload p WHERE p.weight = :weight"),
    @NamedQuery(name = "PlanningUcTransload.findByTruckLossing", query = "SELECT p FROM PlanningUcTransload p WHERE p.truckLossing = :truckLossing"),
    @NamedQuery(name = "PlanningUcTransload.findByLoadPort", query = "SELECT p FROM PlanningUcTransload p WHERE p.loadPort = :loadPort"),
    @NamedQuery(name = "PlanningUcTransload.findByDischPort", query = "SELECT p FROM PlanningUcTransload p WHERE p.dischPort = :dischPort"),
    @NamedQuery(name = "PlanningUcTransload.findByLastPpkb", query = "SELECT p FROM PlanningUcTransload p WHERE p.lastPpkb = :lastPpkb")})
public class PlanningUcTransload implements Serializable, EntityAuditable {
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

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "commodity_code", length = 5)
    private String commodityCode;
    @Column(name = "block", length = 5)
    private String block;
    @Basic(optional = false)
    @Column(name = "no_bl", nullable = false)
    private String noBl;
    @Basic(optional = false)
    @Column(name = "weight", nullable = false, length = 3)
    private String weight;
    @Basic(optional = false)
    @Column(name = "truck_lossing", nullable = false)
    private String truckLossing;
    @Column(name = "load_port", length = 10)
    private String loadPort;
    @Column(name = "disch_port", length = 10)
    private String dischPort;
    @Basic(optional = false)
    @Column(name = "last_ppkb", nullable = false, length = 30)
    private String lastPpkb;
    @JoinColumn(name = "no_ppkb", referencedColumnName = "no_ppkb")
    @ManyToOne
    private PlanningVessel planningVessel;

    public PlanningUcTransload() {
    }

    public PlanningUcTransload(Integer id) {
        this.id = id;
    }

    public PlanningUcTransload(Integer id, String noBl, String weight, String truckLossing, String lastPpkb) {
        this.id = id;
        this.noBl = noBl;
        this.weight = weight;
        this.truckLossing = truckLossing;
        this.lastPpkb = lastPpkb;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCommodityCode() {
        return commodityCode;
    }

    public void setCommodityCode(String commodityCode) {
        this.commodityCode = commodityCode;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public String getNoBl() {
        return noBl;
    }

    public void setNoBl(String noBl) {
        this.noBl = noBl;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getTruckLossing() {
        return truckLossing;
    }

    public void setTruckLossing(String truckLossing) {
        this.truckLossing = truckLossing;
    }

    public String getLoadPort() {
        return loadPort;
    }

    public void setLoadPort(String loadPort) {
        this.loadPort = loadPort;
    }

    public String getDischPort() {
        return dischPort;
    }

    public void setDischPort(String dischPort) {
        this.dischPort = dischPort;
    }

    public String getLastPpkb() {
        return lastPpkb;
    }

    public void setLastPpkb(String lastPpkb) {
        this.lastPpkb = lastPpkb;
    }

    public PlanningVessel getPlanningVessel() {
        return planningVessel;
    }

    public void setPlanningVessel(PlanningVessel planningVessel) {
        this.planningVessel = planningVessel;
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
        if (!(object instanceof PlanningUcTransload)) {
            return false;
        }
        PlanningUcTransload other = (PlanningUcTransload) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.PlanningUcTransload[id=" + id + "]";
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

}
