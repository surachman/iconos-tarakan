/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.model.db.master;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Table;

/**
 *
 * @author senoanggoro
 */
@Entity
@Table(name = "m_activity_tarif_rule")
@NamedNativeQueries({
    @NamedNativeQuery(name = "MasterActivityTarifRule.Native.findAllActivityTarifRule", query = "SELECT a.activity_code, a.description, t.amount, t2.amount, r.id, t.rekening, t2.rekening,r.MAIN_ACTIVITY  "
            + "FROM m_tarif_cont t, m_tarif_cont t2, m_activity a, m_activity_tarif_rule r "
            + "WHERE t.activity_code = t2.activity_code AND t.curr_code != t2.curr_code AND t.activity_code = a.activity_code AND t.curr_code = 'IDR' AND t2.curr_code = 'USD' AND r.activity_code = a.activity_code "
            + "ORDER BY t.id ASC"),
})
public class MasterActivityTarifRule implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    
    @Basic(optional = false)
    @Column(name = "activity_code", nullable = false, length = 10)
    private String activityCode;
    
    @Column(name = "description", length = 256)
    private String description;
    
    @Column(name = "main_activity", length = 256)
    private String mainActivity;
    
    @Column(name = "cont_type", length = 256)
    private String contType;
    
    @Column(name = "cont_size")
    private Integer contSize;
    
    @Column(name = "shipping_cat")
    private String shippingCat;
    
    @Column(name = "is_over_dim")
    private String isOverDim;
    
    @Column(name = "is_use_sling")
    private String isUseSling;
    
    @Column(name = "is_danger")
    private String isDanger;
    
    @Column(name = "is_landed")
    private String isLanded;
    
    @Column(name = "is_use_special_eqpmnt")
    private String isUseSpecialEqpmnt;
    
    @Column(name = "is_open_door")
    private String isOpenDoor;
    
    @Column(name = "is_uncateg")
    private String isUncateg;
    
    @Column(name = "uncateg_min_size")
    private Integer uncategMinSize;
    
    @Column(name = "uncateg_max_size")
    private Integer uncategMaxSize;
    
    @Column(name = "shifting_cat")
    private String shiftingCat;
    
    @Column(name = "rent_forklift_type")
    private String rentForkliftType;
    
    @Column(name = "strip_stuff_fhandling_cat")
    private String stripStuffFhandlingCat;
    
    @Column(name = "mechanical_cat")
    private String mechanicalCat;
    
    @Column(name = "ship_or_land_cat")
    private String shipOrLandCat;
    
    @Column(name = "is_plugging")
    private String isPlugging;
    
    @Column(name = "cont_type_in_gen")
    private String contTypeInGen;


    public MasterActivityTarifRule() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getActivityCode() {
        return activityCode;
    }

    public void setActivityCode(String activityCode) {
        this.activityCode = activityCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMainActivity() {
        return mainActivity;
    }

    public void setMainActivity(String mainActivity) {
        this.mainActivity = mainActivity;
    }

    public String getContType() {
        return contType;
    }

    public void setContType(String contType) {
        this.contType = contType;
    }

    public Integer getContSize() {
        return contSize;
    }

    public void setContSize(Integer contSize) {
        this.contSize = contSize;
    }

    public String getShippingCat() {
        return shippingCat;
    }

    public void setShippingCat(String shippingCat) {
        this.shippingCat = shippingCat;
    }

    public String getIsOverDim() {
        return isOverDim;
    }

    public void setIsOverDim(String isOverDim) {
        this.isOverDim = isOverDim;
    }

    public String getIsUseSling() {
        return isUseSling;
    }

    public void setIsUseSling(String isUseSling) {
        this.isUseSling = isUseSling;
    }

    public String getIsDanger() {
        return isDanger;
    }

    public void setIsDanger(String isDanger) {
        this.isDanger = isDanger;
    }

    public String getIsLanded() {
        return isLanded;
    }

    public void setIsLanded(String isLanded) {
        this.isLanded = isLanded;
    }

    public String getIsUseSpecialEqpmnt() {
        return isUseSpecialEqpmnt;
    }

    public void setIsUseSpecialEqpmnt(String isUseSpecialEqpmnt) {
        this.isUseSpecialEqpmnt = isUseSpecialEqpmnt;
    }

    public String getIsOpenDoor() {
        return isOpenDoor;
    }

    public void setIsOpenDoor(String isOpenDoor) {
        this.isOpenDoor = isOpenDoor;
    }

    public String getIsUncateg() {
        return isUncateg;
    }

    public void setIsUncateg(String isUncateg) {
        this.isUncateg = isUncateg;
    }

    public Integer getUncategMinSize() {
        return uncategMinSize;
    }

    public void setUncategMinSize(Integer uncategMinSize) {
        this.uncategMinSize = uncategMinSize;
    }

    public Integer getUncategMaxSize() {
        return uncategMaxSize;
    }

    public void setUncategMaxSize(Integer uncategMaxSize) {
        this.uncategMaxSize = uncategMaxSize;
    }

    public String getShiftingCat() {
        return shiftingCat;
    }

    public void setShiftingCat(String shiftingCat) {
        this.shiftingCat = shiftingCat;
    }

    public String getRentForkliftType() {
        return rentForkliftType;
    }

    public void setRentForkliftType(String rentForkliftType) {
        this.rentForkliftType = rentForkliftType;
    }

    public String getStripStuffFhandlingCat() {
        return stripStuffFhandlingCat;
    }

    public void setStripStuffFhandlingCat(String stripStuffFhandlingCat) {
        this.stripStuffFhandlingCat = stripStuffFhandlingCat;
    }

    public String getMechanicalCat() {
        return mechanicalCat;
    }

    public void setMechanicalCat(String mechanicalCat) {
        this.mechanicalCat = mechanicalCat;
    }

    public String getShipOrLandCat() {
        return shipOrLandCat;
    }

    public void setShipOrLandCat(String shipOrLandCat) {
        this.shipOrLandCat = shipOrLandCat;
    }

    public String getIsPlugging() {
        return isPlugging;
    }

    public void setIsPlugging(String isPlugging) {
        this.isPlugging = isPlugging;
    }

    public String getContTypeInGen() {
        return contTypeInGen;
    }

    public void setContTypeInGen(String contTypeInGen) {
        this.contTypeInGen = contTypeInGen;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (activityCode != null ? activityCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MasterActivityTarifRule)) {
            return false;
        }
        MasterActivityTarifRule other = (MasterActivityTarifRule) object;
        if ((this.activityCode == null && other.activityCode != null) || (this.activityCode != null && !this.activityCode.equals(other.activityCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.MasterActivityRule[activityCode=" + activityCode + "]";
    }
}
