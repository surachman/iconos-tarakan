/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.model.db;

import com.qtasnim.persistence.AuditTrailEntityListener;
import com.qtasnim.persistence.EntityAuditable;
import com.pelindo.ebtos.model.db.master.MasterYard;
import com.pelindo.ebtos.model.db.master.MasterCommodity;
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
@Table(name = "planning_uc_receiving")
@NamedQueries({
    @NamedQuery(name = "PlanningUcReceiving.findAll", query = "SELECT p FROM PlanningUcReceiving p"),
    @NamedQuery(name = "PlanningUcReceiving.findById", query = "SELECT p FROM PlanningUcReceiving p WHERE p.id = :id"),
    @NamedQuery(name = "PlanningUcReceiving.findByNoBl", query = "SELECT p FROM PlanningUcReceiving p WHERE p.noBl = :noBl"),
    @NamedQuery(name = "PlanningUcReceiving.findByWeight", query = "SELECT p FROM PlanningUcReceiving p WHERE p.weight = :weight"),
    @NamedQuery(name = "PlanningUcReceiving.findByTruckLossing", query = "SELECT p FROM PlanningUcReceiving p WHERE p.truckLossing = :truckLossing"),
    @NamedQuery(name = "PlanningUcReceiving.findByLoadPort", query = "SELECT p FROM PlanningUcReceiving p WHERE p.loadPort = :loadPort"),
    @NamedQuery(name = "PlanningUcReceiving.findByDischPort", query = "SELECT p FROM PlanningUcReceiving p WHERE p.dischPort = :dischPort")})
public class PlanningUcReceiving implements Serializable, EntityAuditable {
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
    @Column(name = "no_bl")
    private String noBl;
    @Column(name = "weight", length = 3)
    private String weight;
    @Column(name = "truck_lossing")
    private Boolean truckLossing;
    @Column(name = "load_port", length = 10)
    private String loadPort;
    @Column(name = "disch_port", length = 10)
    private String dischPort;
    @JoinColumn(name = "block", referencedColumnName = "block")
    @ManyToOne
    private MasterYard masterYard;
    @JoinColumn(name = "commodity_code", referencedColumnName = "commodity_code")
    @ManyToOne
    private MasterCommodity masterCommodity;

    public PlanningUcReceiving() {
    }

    public PlanningUcReceiving(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Boolean getTruckLossing() {
        return truckLossing;
    }

    public void setTruckLossing(Boolean truckLossing) {
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

    public MasterYard getMasterYard() {
        return masterYard;
    }

    public void setMasterYard(MasterYard masterYard) {
        this.masterYard = masterYard;
    }

    public MasterCommodity getMasterCommodity() {
        return masterCommodity;
    }

    public void setMasterCommodity(MasterCommodity masterCommodity) {
        this.masterCommodity = masterCommodity;
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
        if (!(object instanceof PlanningUcReceiving)) {
            return false;
        }
        PlanningUcReceiving other = (PlanningUcReceiving) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.PlanningUcReceiving[id=" + id + "]";
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
