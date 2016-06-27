/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.model.db.master;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author dycoder
 */
@Embeddable
public class MasterAnggaranTrafikBmPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "tahun", nullable = false, length = 4)
    private String tahun;
    @Basic(optional = false)
    @Column(name = "activity", nullable = false, length = 256)
    private String activity;

    public MasterAnggaranTrafikBmPK() {
    }

    public MasterAnggaranTrafikBmPK(String tahun, String activity) {
        this.tahun = tahun;
        this.activity = activity;
    }

    public String getTahun() {
        return tahun;
    }

    public void setTahun(String tahun) {
        this.tahun = tahun;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tahun != null ? tahun.hashCode() : 0);
        hash += (activity != null ? activity.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MasterAnggaranTrafikBmPK)) {
            return false;
        }
        MasterAnggaranTrafikBmPK other = (MasterAnggaranTrafikBmPK) object;
        if ((this.tahun == null && other.tahun != null) || (this.tahun != null && !this.tahun.equals(other.tahun))) {
            return false;
        }
        if ((this.activity == null && other.activity != null) || (this.activity != null && !this.activity.equals(other.activity))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.master.MasterAnggaranTrafikBmPK[tahun=" + tahun + ", activity=" + activity + "]";
    }

}
