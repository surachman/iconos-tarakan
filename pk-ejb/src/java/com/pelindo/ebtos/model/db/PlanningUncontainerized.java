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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
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
@Table(name = "planning_uncontainerized")
@NamedQueries({
    @NamedQuery(name = "PlanningUncontainerized.findAll", query = "SELECT p FROM PlanningUncontainerized p"),
    @NamedQuery(name = "PlanningUncontainerized.findById", query = "SELECT p FROM PlanningUncontainerized p WHERE p.id = :id"),
    @NamedQuery(name = "PlanningUncontainerized.findByNoBl", query = "SELECT p FROM PlanningUncontainerized p WHERE p.noBl = :noBl"),
    @NamedQuery(name = "PlanningUncontainerized.findByWeight", query = "SELECT p FROM PlanningUncontainerized p WHERE p.weight = :weight"),
    @NamedQuery(name = "PlanningUncontainerized.findByTruckLossing", query = "SELECT p FROM PlanningUncontainerized p WHERE p.truckLossing = :truckLossing"),
    @NamedQuery(name = "PlanningUncontainerized.findByLoadPort", query = "SELECT p FROM PlanningUncontainerized p WHERE p.loadPort = :loadPort"),
    @NamedQuery(name = "PlanningUncontainerized.findByDischPort", query = "SELECT p FROM PlanningUncontainerized p WHERE p.dischPort = :dischPort"),
    @NamedQuery(name = "PlanningUncontainerized.findByOperation", query = "SELECT p FROM PlanningUncontainerized p WHERE p.operation = :operation")})
@NamedNativeQueries({
    @NamedNativeQuery(name="PlanningUncontainerized.Native.findPlanningUncontainerizedByPpkb",query="SELECT pu.no_bl, m.name, pu.weight, pu.truck_lossing, pu.id, pu.unit from planning_uncontainerized pu,m_commodity m where pu.commodity_code=m.commodity_code AND pu.no_ppkb = ?"),
    @NamedNativeQuery(name="PlanningUncontainerized.Native.findPlanningUncontainerizedByPpkbDischarge",query="SELECT pu.no_bl, m.name, pu.weight, pu.truck_lossing, pu.id, pu.unit from planning_uncontainerized pu,m_commodity m where pu.commodity_code=m.commodity_code AND pu.no_ppkb = ? AND pu.operation = 'DISCHARGE'"),
    @NamedNativeQuery(name="PlanningUncontainerized.Native.findPlanningUncontainerizedByPpkbLoad",query="SELECT pu.no_bl, m.name, pu.weight, pu.truck_lossing, pu.id, pu.unit from planning_uncontainerized pu,m_commodity m where pu.commodity_code=m.commodity_code AND pu.no_ppkb = ? AND pu.operation = 'LOAD' AND pu.no_bl NOT IN (SELECT no_bl FROM receiving_uc WHERE no_ppkb = pu.no_ppkb)")})

public class PlanningUncontainerized implements Serializable, EntityAuditable {
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
    //@GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "no_bl")
    private String noBl;
    @Column(name = "weight", length = 10)
    private Integer weight;
    @Column(name = "truck_lossing")
    private String truckLossing;
    @Column(name = "load_port", length = 10)
    private String loadPort;
    @Column(name = "disch_port", length = 10)
    private String dischPort;
    @Column(name = "operation", length = 10)
    private String operation;
    @Column(name = "unit")
    private Short unit;
    @JoinColumn(name = "no_ppkb", referencedColumnName = "no_ppkb")
    @ManyToOne
    private PlanningVessel planningVessel;
    @JoinColumn(name = "block", referencedColumnName = "block")
    @ManyToOne
    private MasterYard masterYard;
    @JoinColumn(name = "commodity_code", referencedColumnName = "commodity_code")
    @ManyToOne
    private MasterCommodity masterCommodity;

    public PlanningUncontainerized() {
    }

    public PlanningUncontainerized(Integer id) {
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

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
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

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public Short getUnit() {
        return unit;
    }

    public void setUnit(Short unit) {
        this.unit = unit;
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
        if (!(object instanceof PlanningUncontainerized)) {
            return false;
        }
        PlanningUncontainerized other = (PlanningUncontainerized) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.PlanningUncontainerized[id=" + id + "]";
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
