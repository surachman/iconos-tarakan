/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.model.db.master;

import com.qtasnim.persistence.AuditTrailEntityListener;
import com.qtasnim.persistence.EntityAuditable;
import com.pelindo.ebtos.model.db.BaplieDischarge;
import com.pelindo.ebtos.model.db.PreserviceVessel;
import com.pelindo.ebtos.model.db.ReceivingService;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author senoanggoro
 */
@Entity
@EntityListeners({AuditTrailEntityListener.class})
@Table(name = "m_port")
@NamedQueries({
    @NamedQuery(name = "MasterPort.findAll", query = "SELECT m FROM MasterPort m"),
    @NamedQuery(name = "MasterPort.findByPortCode", query = "SELECT m FROM MasterPort m WHERE m.portCode = :portCode"),
    @NamedQuery(name = "MasterPort.findByName", query = "SELECT m FROM MasterPort m WHERE m.name = :name"),
    @NamedQuery(name = "MasterPort.findByDescription", query = "SELECT m FROM MasterPort m WHERE m.description = :description")})
@NamedNativeQueries({
    @NamedNativeQuery(name = "MasterPort.Native.findAll", query = "SELECT m.port_code, m.name, m.description FROM m_port m"),
    @NamedNativeQuery(name = "MasterPort.Native.findByName", query = "SELECT p.port_code, p.name FROM m_port p WHERE p.name = ?"),
/*
    @NamedNativeQuery(name = "MasterPort.Native.findLikeName", query = "SELECT p.name FROM m_port p WHERE UPPER(p.name) LIKE UPPER('%'|| ? ||'%') LIMIT 20"),
*/    
    @NamedNativeQuery(name = "MasterPort.Native.findLikeName", query =     
    "SELECT p.name FROM m_port p WHERE UPPER(p.name) LIKE UPPER('%' || ? ||'%') " ),    

    @NamedNativeQuery(name = "MasterPort.Native.findCodeByName", query = "SELECT p.port_code FROM m_port p WHERE p.name =?"),
    @NamedNativeQuery(name = "MasterPort.Native.findNameByCode", query = "SELECT p.name FROM m_port p WHERE p.port_code =?"),
    @NamedNativeQuery(name = "MasterPort.Native.findMasterPortForIdentify", query = "SELECT m.port_code, m.name FROM m_port m WHERE m.port_code = ?"),
    @NamedNativeQuery(name = "MasterPort.Native.findNoError", query = "SELECT m.port_code, m.name FROM m_port m WHERE m.port_code != 'ERR'")})
public class MasterPort implements Serializable, EntityAuditable {
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
    @Column(name = "port_code", nullable = false, length = 10)
    private String portCode;
    @Basic(optional = false)
    @Column(name = "name", nullable = false, length = 50)
    private String name;
    @Column(name = "description", length = 256)
    private String description;
    @OneToMany(mappedBy = "masterPort")
    private List<ReceivingService> receivingServiceList;
    @OneToMany(mappedBy = "masterPort1")
    private List<ReceivingService> receivingServiceList1;
    @OneToMany(mappedBy = "masterPort")
    private List<BaplieDischarge> baplieDischargeList;
    @OneToMany(mappedBy = "masterPort1")
    private List<BaplieDischarge> baplieDischargeList1;
    @OneToMany(mappedBy = "masterPort")
    private List<PreserviceVessel> preserviceVesselList;
    @OneToMany(mappedBy = "masterPort1")
    private List<PreserviceVessel> preserviceVesselList1;
    @JoinColumn(name = "country_code", referencedColumnName = "country_code")
    @ManyToOne
    private MasterCountry masterCountry;

    public MasterPort() {
    }

    public MasterPort(String portCode) {
        this.portCode = portCode;
    }

    public MasterPort(String portCode, String name) {
        this.portCode = portCode;
        this.name = name;
    }

    public String getPortCode() {
        return portCode;
    }

    public void setPortCode(String portCode) {
        this.portCode = portCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ReceivingService> getReceivingServiceList() {
        return receivingServiceList;
    }

    public void setReceivingServiceList(List<ReceivingService> receivingServiceList) {
        this.receivingServiceList = receivingServiceList;
    }

    public List<ReceivingService> getReceivingServiceList1() {
        return receivingServiceList1;
    }

    public void setReceivingServiceList1(List<ReceivingService> receivingServiceList1) {
        this.receivingServiceList1 = receivingServiceList1;
    }

    public List<BaplieDischarge> getBaplieDischargeList() {
        return baplieDischargeList;
    }

    public void setBaplieDischargeList(List<BaplieDischarge> baplieDischargeList) {
        this.baplieDischargeList = baplieDischargeList;
    }

    public List<BaplieDischarge> getBaplieDischargeList1() {
        return baplieDischargeList1;
    }

    public void setBaplieDischargeList1(List<BaplieDischarge> baplieDischargeList1) {
        this.baplieDischargeList1 = baplieDischargeList1;
    }

    public List<PreserviceVessel> getPreserviceVesselList() {
        return preserviceVesselList;
    }

    public void setPreserviceVesselList(List<PreserviceVessel> preserviceVesselList) {
        this.preserviceVesselList = preserviceVesselList;
    }

    public List<PreserviceVessel> getPreserviceVesselList1() {
        return preserviceVesselList1;
    }

    public void setPreserviceVesselList1(List<PreserviceVessel> preserviceVesselList1) {
        this.preserviceVesselList1 = preserviceVesselList1;
    }

    public MasterCountry getMasterCountry() {
        return masterCountry;
    }

    public void setMasterCountry(MasterCountry masterCountry) {
        this.masterCountry = masterCountry;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (portCode != null ? portCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MasterPort)) {
            return false;
        }
        MasterPort other = (MasterPort) object;
        if ((this.portCode == null && other.portCode != null) || (this.portCode != null && !this.portCode.equals(other.portCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.MasterPort[portCode=" + portCode + "]";
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
