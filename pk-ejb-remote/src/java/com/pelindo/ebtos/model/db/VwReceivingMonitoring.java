/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.model.db;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author arie
 */
@Entity
@Table(name = "vw_receiving_monitoring")
@NamedQueries({
    @NamedQuery(name = "VwReceivingMonitoring.findAll", query = "SELECT v FROM VwReceivingMonitoring v"),
    @NamedQuery(name = "VwReceivingMonitoring.findByNoPpkb", query = "SELECT v FROM VwReceivingMonitoring v WHERE v.noPpkb = :noPpkb"),
    @NamedQuery(name = "VwReceivingMonitoring.findByPortOfDelivery", query = "SELECT v FROM VwReceivingMonitoring v WHERE v.portOfDelivery = :portOfDelivery"),
    @NamedQuery(name = "VwReceivingMonitoring.findByContStatus", query = "SELECT v FROM VwReceivingMonitoring v WHERE v.contStatus = :contStatus"),
    @NamedQuery(name = "VwReceivingMonitoring.findByContNo", query = "SELECT v FROM VwReceivingMonitoring v WHERE v.contNo = :contNo"),
    @NamedQuery(name = "VwReceivingMonitoring.findByContSize", query = "SELECT v FROM VwReceivingMonitoring v WHERE v.contSize = :contSize"),
    @NamedQuery(name = "VwReceivingMonitoring.findByContType", query = "SELECT v FROM VwReceivingMonitoring v WHERE v.contType = :contType"),
    @NamedQuery(name = "VwReceivingMonitoring.findByContGross", query = "SELECT v FROM VwReceivingMonitoring v WHERE v.contGross = :contGross"),
    @NamedQuery(name = "VwReceivingMonitoring.findByCommodityCode", query = "SELECT v FROM VwReceivingMonitoring v WHERE v.commodityCode = :commodityCode"),
    @NamedQuery(name = "VwReceivingMonitoring.findByInternalstatus", query = "SELECT v FROM VwReceivingMonitoring v WHERE v.internalstatus = :internalstatus"),
    @NamedQuery(name = "VwReceivingMonitoring.findByName", query = "SELECT v FROM VwReceivingMonitoring v WHERE v.name = :name"),
    @NamedQuery(name = "VwReceivingMonitoring.findByPosition", query = "SELECT v FROM VwReceivingMonitoring v WHERE v.position = :position")})
@NamedNativeQueries({
    @NamedNativeQuery(name = "VwReceivingMonitoring.Native.findByNoPpkb", query = "SELECT v.no_ppkb, v.port_of_delivery, v.cont_status, v.cont_no, v.cont_size, v.cont_type, v.cont_gross, v.commodity_code, v.internalstatus, v.name, v.position FROM Vw_Receiving_Monitoring v where no_ppkb = ?"),
    @NamedNativeQuery(name = "VwReceivingMonitoring.Native.findByInternalstatus", query = "SELECT v.no_ppkb, v.port_of_delivery, v.cont_status, v.cont_no, v.cont_size, v.cont_type, v.cont_gross, v.commodity_code, v.internalstatus, v.name, v.position FROM Vw_Receiving_Monitoring v where internalstatus = ?"),
    @NamedNativeQuery(name = "VwReceivingMonitoring.Native.findByNoPpkbInternalStatus", query = "SELECT v.no_ppkb, v.port_of_delivery, v.cont_status, v.cont_no, v.cont_size, v.cont_type, v.cont_gross, v.commodity_code, v.internalstatus, v.name, v.position FROM Vw_Receiving_Monitoring v where no_ppkb = ? and internalstatus = ? order by port_of_delivery, cont_status")})
public class VwReceivingMonitoring implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "no_ppkb")
    private String noPpkb;
    @Column(name = "port_of_delivery")
    private String portOfDelivery;
    @Column(name = "cont_status")
    private String contStatus;
    @Column(name = "cont_no")
    @Id
    private String contNo;
    @Column(name = "cont_size")
    private Integer contSize;
    @Column(name = "cont_type")
    private String contType;
    @Column(name = "cont_gross")
    private Integer contGross;
    @Column(name = "commodity_code")
    private String commodityCode;
    @Column(name = "internalstatus")
    private String internalstatus;
    @Column(name = "name")
    private String name;
    @Column(name = "position")
    private String position;

    public VwReceivingMonitoring() {
    }

    public String getNoPpkb() {
        return noPpkb;
    }

    public void setNoPpkb(String noPpkb) {
        this.noPpkb = noPpkb;
    }

    public String getPortOfDelivery() {
        return portOfDelivery;
    }

    public void setPortOfDelivery(String portOfDelivery) {
        this.portOfDelivery = portOfDelivery;
    }

    public String getContStatus() {
        return contStatus;
    }

    public void setContStatus(String contStatus) {
        this.contStatus = contStatus;
    }

    public String getContNo() {
        return contNo;
    }

    public void setContNo(String contNo) {
        this.contNo = contNo;
    }

    public Integer getContSize() {
        return contSize;
    }

    public void setContSize(Integer contSize) {
        this.contSize = contSize;
    }

    public String getContType() {
        return contType;
    }

    public void setContType(String contType) {
        this.contType = contType;
    }

    public Integer getContGross() {
        return contGross;
    }

    public void setContGross(Integer contGross) {
        this.contGross = contGross;
    }

    public String getCommodityCode() {
        return commodityCode;
    }

    public void setCommodityCode(String commodityCode) {
        this.commodityCode = commodityCode;
    }

    public String getInternalstatus() {
        return internalstatus;
    }

    public void setInternalstatus(String internalstatus) {
        this.internalstatus = internalstatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

}
