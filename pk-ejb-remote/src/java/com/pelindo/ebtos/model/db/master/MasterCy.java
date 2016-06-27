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
 * @author R Seno Anggoro
 */
@Entity
@Table(name = "m_cy")
@NamedQueries({
    @NamedQuery(name = "MasterCy.findAll", query = "SELECT m FROM MasterCy m"),
    @NamedQuery(name = "MasterCy.findByYard", query = "SELECT m FROM MasterCy m WHERE m.yard = :yard"),
    @NamedQuery(name = "MasterCy.findByDesc", query = "SELECT m FROM MasterCy m WHERE m.description = :description")})
public class MasterCy implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "yard", nullable = false, length = 50)
    private String yard;
    @Column(name = "description", length = 2147483647)
    private String description;
    @OneToMany(mappedBy = "masterCy")
    private List<MasterYard> masterYardList;

    public MasterCy() {
    }

    public MasterCy(String yard) {
        this.yard = yard;
    }

    public String getYard() {
        return yard;
    }

    public void setYard(String yard) {
        this.yard = yard;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<MasterYard> getMasterYardList() {
        return masterYardList;
    }

    public void setMasterYardList(List<MasterYard> masterYardList) {
        this.masterYardList = masterYardList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (yard != null ? yard.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MasterCy)) {
            return false;
        }
        MasterCy other = (MasterCy) object;
        if ((this.yard == null && other.yard != null) || (this.yard != null && !this.yard.equals(other.yard))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.master.MasterCy[yard=" + yard + "]";
    }

}
