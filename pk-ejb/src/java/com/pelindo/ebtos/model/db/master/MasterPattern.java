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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author senoanggoro
 */
@Entity
@Table(name = "m_pattern")
@NamedQueries({
    @NamedQuery(name = "MasterPattern.findAll", query = "SELECT m FROM MasterPattern m"),
    @NamedQuery(name = "MasterPattern.findByName", query = "SELECT m FROM MasterPattern m WHERE m.name = :name"),
    @NamedQuery(name = "MasterPattern.findByDescription", query = "SELECT m FROM MasterPattern m WHERE m.description = :description")})
public class MasterPattern implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "name", nullable = false, length = 50)
    private String name;
    @Column(name = "description", length = 256)
    private String description;
    @OneToMany(mappedBy = "masterPattern")
    private List<MasterContainerTypeInGeneral> masterContainerTypeInGeneralList;

    public MasterPattern() {
    }

    public MasterPattern(String name) {
        this.name = name;
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

    public List<MasterContainerTypeInGeneral> getMasterContainerTypeInGeneralList() {
        return masterContainerTypeInGeneralList;
    }

    public void setMasterContainerTypeInGeneralList(List<MasterContainerTypeInGeneral> masterContainerTypeInGeneralList) {
        this.masterContainerTypeInGeneralList = masterContainerTypeInGeneralList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (name != null ? name.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MasterPattern)) {
            return false;
        }
        MasterPattern other = (MasterPattern) object;
        if ((this.name == null && other.name != null) || (this.name != null && !this.name.equals(other.name))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.master.MasterPattern[name=" + name + "]";
    }

}
