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
@Table(name = "planning_uc_trans")
@NamedQueries({
    @NamedQuery(name = "PlanningUcTrans.findAll", query = "SELECT p FROM PlanningUcTrans p"),
    @NamedQuery(name = "PlanningUcTrans.findById", query = "SELECT p FROM PlanningUcTrans p WHERE p.id = :id"),
    @NamedQuery(name = "PlanningUcTrans.findByNoBl", query = "SELECT p FROM PlanningUcTrans p WHERE p.noBl = :noBl"),
    @NamedQuery(name = "PlanningUcTrans.findByWeight", query = "SELECT p FROM PlanningUcTrans p WHERE p.weight = :weight"),
    @NamedQuery(name = "PlanningUcTrans.findByTruckLossing", query = "SELECT p FROM PlanningUcTrans p WHERE p.truckLossing = :truckLossing"),
    @NamedQuery(name = "PlanningUcTrans.findByLoadPort", query = "SELECT p FROM PlanningUcTrans p WHERE p.loadPort = :loadPort"),
    @NamedQuery(name = "PlanningUcTrans.findByDischPort", query = "SELECT p FROM PlanningUcTrans p WHERE p.dischPort = :dischPort"),
    @NamedQuery(name = "PlanningUcTrans.findByNewPpkb", query = "SELECT p FROM PlanningUcTrans p WHERE p.newPpkb = :newPpkb")})
public class PlanningUcTrans implements Serializable, EntityAuditable {
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
    @Column(name = "new_ppkb", nullable = false, length = 30)
    private String newPpkb;
    @JoinColumn(name = "no_ppkb", referencedColumnName = "no_ppkb")
    @ManyToOne
    private PlanningVessel planningVessel;
    @JoinColumn(name = "block", referencedColumnName = "block")
    @ManyToOne
    private MasterYard masterYard;
    @JoinColumn(name = "commodity_code", referencedColumnName = "commodity_code")
    @ManyToOne
    private MasterCommodity masterCommodity;

    public PlanningUcTrans() {
    }

    public PlanningUcTrans(Integer id) {
        this.id = id;
    }

    public PlanningUcTrans(Integer id, String noBl, String weight, String truckLossing, String newPpkb) {
        this.id = id;
        this.noBl = noBl;
        this.weight = weight;
        this.truckLossing = truckLossing;
        this.newPpkb = newPpkb;
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

    public String getNewPpkb() {
        return newPpkb;
    }

    public void setNewPpkb(String newPpkb) {
        this.newPpkb = newPpkb;
    }

    public PlanningVessel getPlanningVessel() {
        return planningVessel;
    }

    public void setPlanningVessel(PlanningVessel planningVessel) {
        this.planningVessel = planningVessel;
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
        if (!(object instanceof PlanningUcTrans)) {
            return false;
        }
        PlanningUcTrans other = (PlanningUcTrans) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.PlanningUcTrans[id=" + id + "]";
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
