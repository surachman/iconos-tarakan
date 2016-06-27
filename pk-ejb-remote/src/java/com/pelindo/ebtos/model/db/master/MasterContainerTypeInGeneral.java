/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.model.db.master;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author senoanggoro
 */
@Entity
@Table(name = "m_container_type_in_general")
@NamedQueries({
    @NamedQuery(name = "MasterContainerTypeInGeneral.findAll", query = "SELECT m FROM MasterContainerTypeInGeneral m"),
    @NamedQuery(name = "MasterContainerTypeInGeneral.findById", query = "SELECT m FROM MasterContainerTypeInGeneral m WHERE m.id = :id"),
    @NamedQuery(name = "MasterContainerTypeInGeneral.findByName", query = "SELECT m FROM MasterContainerTypeInGeneral m WHERE m.name = :name"),
    @NamedQuery(name = "MasterContainerTypeInGeneral.findByDescription", query = "SELECT m FROM MasterContainerTypeInGeneral m WHERE m.description = :description")})
public class MasterContainerTypeInGeneral implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final String TYPE_REEFER = "RF";
    
    @Id
    @Basic(optional = false)
    @Column(name = "id", nullable = false, length = 10)
    private String id;
    @Column(name = "name", length = 50)
    private String name;
    @Column(name = "description", length = 256)
    private String description;
    @OneToMany(mappedBy = "masterContainerTypeInGeneral")
    private List<MasterContainerType> masterContainerTypeList;
    @JoinColumn(name = "pattern", referencedColumnName = "name")
    @ManyToOne
    private MasterPattern masterPattern;

    public MasterContainerTypeInGeneral() {
    }

    public MasterContainerTypeInGeneral(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public List<MasterContainerType> getMasterContainerTypeList() {
        return masterContainerTypeList;
    }

    public void setMasterContainerTypeList(List<MasterContainerType> masterContainerTypeList) {
        this.masterContainerTypeList = masterContainerTypeList;
    }

    public MasterPattern getMasterPattern() {
        return masterPattern;
    }

    public void setMasterPattern(MasterPattern masterPattern) {
        this.masterPattern = masterPattern;
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
        if (!(object instanceof MasterContainerTypeInGeneral)) {
            return false;
        }
        MasterContainerTypeInGeneral other = (MasterContainerTypeInGeneral) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.master.MasterContainerTypeInGeneral[id=" + id + "]";
    }

}
