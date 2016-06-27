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
 * @author AnggiCipta
 */
@Embeddable
public class MasterAnggaranPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "tahun", nullable = false, length = 4)
    private String tahun;
    @Basic(optional = false)
    @Column(name = "activity_code", nullable = false, length = 10)
    private String activityCode;
    @Basic(optional = false)
    @Column(name = "curr_code", nullable = false, length = 4)
    private String currCode;

    public MasterAnggaranPK() {
    }

    public MasterAnggaranPK(String tahun, String activityCode, String currCode) {
        this.tahun = tahun;
        this.activityCode = activityCode;
        this.currCode = currCode;
    }

    public String getTahun() {
        return tahun;
    }

    public void setTahun(String tahun) {
        this.tahun = tahun;
    }

    public String getActivityCode() {
        return activityCode;
    }

    public void setActivityCode(String activityCode) {
        this.activityCode = activityCode;
    }

    public String getCurrCode() {
        return currCode;
    }

    public void setCurrCode(String currCode) {
        this.currCode = currCode;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tahun != null ? tahun.hashCode() : 0);
        hash += (activityCode != null ? activityCode.hashCode() : 0);
        hash += (currCode != null ? currCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MasterAnggaranPK)) {
            return false;
        }
        MasterAnggaranPK other = (MasterAnggaranPK) object;
        if ((this.tahun == null && other.tahun != null) || (this.tahun != null && !this.tahun.equals(other.tahun))) {
            return false;
        }
        if ((this.activityCode == null && other.activityCode != null) || (this.activityCode != null && !this.activityCode.equals(other.activityCode))) {
            return false;
        }
        if ((this.currCode == null && other.currCode != null) || (this.currCode != null && !this.currCode.equals(other.currCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.master.MasterAnggaranPK[tahun=" + tahun + ", activityCode=" + activityCode + ", currCode=" + currCode + "]";
    }

}
