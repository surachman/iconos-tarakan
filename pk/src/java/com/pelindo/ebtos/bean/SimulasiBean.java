/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.bean;

import com.qtasnim.util.IndonesianNumberConverter;
import com.pelindo.ebtos.ejb.facade.remote.MasterTarifContFacadeRemote;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author dycoder
 */
@ManagedBean(name = "simulasiBean")
@ViewScoped
public class SimulasiBean implements Serializable {

    MasterTarifContFacadeRemote masterTarifContFacade = lookupMasterTarifContFacadeRemote();
    private IndonesianNumberConverter indonesianNumberConverter;
    private String contType;
    private String conSize;
    private Boolean overSize;
    private Boolean dg;
    private Boolean dgLabel;
    private Integer box;
    private String contStatus;
    private String crane;
    private String actHandling;
    private BigDecimal total = BigDecimal.ZERO;
    private BigDecimal charge = BigDecimal.ZERO;
    private String tipePelayaran = null;
    private BigDecimal tarifHandling = BigDecimal.ZERO;
    private BigDecimal cas = BigDecimal.ZERO;
    private String terbilang;
    private String idList;
    private Integer no = 0;
    private SimulasiBean simulasiBean;
    private int idEdit;
    // private List<PreserviceContainer> listPreserviceContainers=new ArrayList<PreserviceContainer>();
    private List<SimulasiBean> listSImulasi = new ArrayList<SimulasiBean>();

    /** Creates a new instance of SimulasiBean */
    public SimulasiBean() {
        this.crane = "L";
        this.tipePelayaran = "d";
        indonesianNumberConverter = new IndonesianNumberConverter();
    }

    public SimulasiBean(String conStatus, String contSize, boolean overSize, String contType, boolean dg, boolean dgLabel, Integer box, String crane, String tipePelayaran) {
        this.contType = contType;
        this.conSize = contSize;
        this.overSize = overSize;
        this.dg = dg;
        this.dgLabel = dgLabel;
        this.box = box;
        this.contStatus = conStatus;
        this.crane = crane;
        this.tipePelayaran = tipePelayaran;
        this.cas = BigDecimal.ZERO;
        this.tarifHandling = BigDecimal.ZERO;
    }

    public void handleAdd(ActionEvent actionEvent) {
        //simulasiBean = new SimulasiBean();
    }

    public void save(ActionEvent actionEvent) {
        if (simulasiBean.contStatus.equals("LCL")) {
            if (simulasiBean.conSize.equals("20")) {
                if (simulasiBean.crane.equals("L")) {
                    if (simulasiBean.overSize == false) {
                        actHandling = "A013";
                    } else {
                        actHandling = "A014";
                    }
                } else if (simulasiBean.crane.equals("S")) {
                    if (simulasiBean.overSize == false) {
                        actHandling = "A015";
                    } else {
                        actHandling = "A016";
                    }
                }
            } else if (simulasiBean.conSize.equals("40")) {
                if (simulasiBean.crane.equals("L")) {
                    if (simulasiBean.overSize == false) {
                        actHandling = "A017";
                    } else {
                        actHandling = "A018";
                    }
                } else if (simulasiBean.crane.equals("S")) {
                    if (simulasiBean.overSize == false) {
                        actHandling = "A019";
                    } else {
                        actHandling = "A020";
                    }
                }
            }
        } else {
            System.out.println("false : ");
            if (simulasiBean.overSize == false) {
                System.out.println("false : ");
                if (simulasiBean.contStatus.equals("FCL")) {
                    System.out.println("fcl ");
                    if (simulasiBean.conSize.equals("20")) {
                        if (simulasiBean.crane.equals("L")) {
                            actHandling = "A001";
                            System.out.println("A001 ");
                        }
                        if (simulasiBean.crane.equals("S")) {
                            actHandling = "A003";
                            System.out.println("A003 ");
                        }
                    } else if (simulasiBean.conSize.equals("40")) {
                        if (simulasiBean.crane.equals("L")) {
                            actHandling = "A005";
                        }
                        if (simulasiBean.crane.equals("S")) {
                            actHandling = "A007";
                        }
                    }
                } else if (simulasiBean.contStatus.equals("MTY")) {
                    if (simulasiBean.conSize.equals("20")) {
                        if (simulasiBean.crane.equals("L")) {
                            actHandling = "A009";
                        }
                        if (simulasiBean.crane.equals("S")) {
                            actHandling = "A010";
                        }
                    } else if (simulasiBean.conSize.equals("40")) {
                        if (simulasiBean.crane.equals("L")) {
                            actHandling = "A011";
                        }
                        if (simulasiBean.crane.equals("S")) {
                            actHandling = "A012";
                        }
                    }
                }
            } else {
                if (simulasiBean.conSize.equals("20")) {
                    if (simulasiBean.crane.equals("L")) {
                        actHandling = "A002";
                    }
                    if (simulasiBean.crane.equals("S")) {
                        actHandling = "A004";
                    }
                } else if (simulasiBean.conSize.equals("40")) {
                    if (simulasiBean.crane.equals("L")) {
                        actHandling = "A006";
                    }
                    if (simulasiBean.crane.equals("S")) {
                        actHandling = "A008";
                    }
                }
            }
        }

        System.out.println("tipe : " + getTipePelayaran());
        System.out.println("tipe : " + actHandling);
        if (getTipePelayaran().equalsIgnoreCase("d")) {
            setTarifHandling(masterTarifContFacade.findByCurrCodeAndActivity("rp", actHandling));
        } else if (getTipePelayaran().equalsIgnoreCase("i")) {
            setTarifHandling(masterTarifContFacade.findByCurrCodeAndActivity("$", actHandling));
        }
        System.out.println("tipe : " + getTipePelayaran());
        System.out.println("tipe : " + actHandling);
        if (getTipePelayaran().equalsIgnoreCase("d")) {
            setTarifHandling(masterTarifContFacade.findByCurrCodeAndActivity("rp", actHandling));
        } else if (getTipePelayaran().equalsIgnoreCase("i")) {
            setTarifHandling(masterTarifContFacade.findByCurrCodeAndActivity("$", actHandling));
        }
        System.out.println("tarifHandling : " + getTarifHandling());
        cas = (getTarifHandling().multiply(BigDecimal.valueOf(simulasiBean.getBox())));
        // setCas(simulasiBean.getBox() * getTarifHandling());
        simulasiBean.setCharge(getCas());
        listSImulasi.set(getIdEdit(), simulasiBean);

        BigDecimal t = BigDecimal.ZERO;
        for (SimulasiBean o : listSImulasi) {
            t = t.add((BigDecimal) (o.charge));
            //t = t + (Integer) o.charge;
        }
        setTotal(t);
        //setTerbilang(indonesianNumberConverter.convertToWord(BigDecimal.valueOf(total)));
    }

    public void editSimulasi(ActionEvent event) {
        simulasiBean = (SimulasiBean) event.getComponent().getAttributes().get("itemDelete");
        System.out.println("skdjs : " + simulasiBean.getConSize());
        System.out.println("skdjs : " + simulasiBean.getContStatus());
        setIdEdit(listSImulasi.indexOf(simulasiBean));
        System.out.println("idEdit : " + getIdEdit());
    }

    public void removeSimulasi(ActionEvent event) {
        // planningReceiving = (PlanningReceiving) event.getComponent().getAttributes().get("itemDelete");
        SimulasiBean smb = (SimulasiBean) event.getComponent().getAttributes().get("itemDelete");
        //setIdList((String)event.getComponent().getAttributes().get("itemDelete"));
        System.out.println(smb.toString());
        listSImulasi.remove(smb);
        BigDecimal t = BigDecimal.ZERO;
        for (SimulasiBean o : listSImulasi) {
            t = t.add((BigDecimal) (o.charge));
            //t = t + (Integer) o.charge;
        }
        setTotal(t);
    }

    public String createNew() {

        SimulasiBean sm = new SimulasiBean(contStatus, conSize, overSize, contType, dg, dgLabel, box, crane, tipePelayaran);

        System.out.println("contType : " + conSize);
        System.out.println("overSize : " + overSize);
        System.out.println("contStatus : " + contStatus);
        System.out.println("crane : " + crane);

        if (contStatus.equals("LCL")) {
            if (conSize.equals("20")) {
                if (crane.equals("L")) {
                    if (overSize == false) {
                        actHandling = "A013";
                    } else {
                        actHandling = "A014";
                    }
                } else if (crane.equals("S")) {
                    if (overSize == false) {
                        actHandling = "A015";
                    } else {
                        actHandling = "A016";
                    }
                }
            } else if (conSize.equals("40")) {
                if (crane.equals("L")) {
                    if (overSize == false) {
                        actHandling = "A017";
                    } else {
                        actHandling = "A018";
                    }
                } else if (crane.equals("S")) {
                    if (overSize == false) {
                        actHandling = "A019";
                    } else {
                        actHandling = "A020";
                    }
                }
            }
        } else {
            System.out.println("false : ");
            if (overSize == false) {
                System.out.println("false : ");
                if (contStatus.equals("FCL")) {
                    System.out.println("fcl ");
                    if (conSize.equals("20")) {
                        if (crane.equals("L")) {
                            actHandling = "A001";
                            System.out.println("A001 ");
                        }
                        if (crane.equals("S")) {
                            actHandling = "A003";
                            System.out.println("A003 ");
                        }
                    } else if (conSize.equals("40")) {
                        if (crane.equals("L")) {
                            actHandling = "A005";
                        }
                        if (crane.equals("S")) {
                            actHandling = "A007";
                        }
                    }
                } else if (contStatus.equals("MTY")) {
                    if (conSize.equals("20")) {
                        if (crane.equals("L")) {
                            actHandling = "A009";
                        }
                        if (crane.equals("S")) {
                            actHandling = "A010";
                        }
                    } else if (conSize.equals("40")) {
                        if (crane.equals("L")) {
                            actHandling = "A011";
                        }
                        if (crane.equals("S")) {
                            actHandling = "A012";
                        }
                    }
                }
            } else {
                if (conSize.equals("20")) {
                    if (crane.equals("L")) {
                        actHandling = "A002";
                    }
                    if (crane.equals("S")) {
                        actHandling = "A004";
                    }
                } else if (conSize.equals("40")) {
                    if (crane.equals("L")) {
                        actHandling = "A006";
                    }
                    if (crane.equals("S")) {
                        actHandling = "A008";
                    }
                }
            }
        }

        System.out.println("tipe : " + getTipePelayaran());
        System.out.println("tipe : " + actHandling);
        if (getTipePelayaran().equalsIgnoreCase("d")) {
            setTarifHandling(masterTarifContFacade.findByCurrCodeAndActivity("rp", actHandling));
        } else if (getTipePelayaran().equalsIgnoreCase("i")) {
            setTarifHandling(masterTarifContFacade.findByCurrCodeAndActivity("$", actHandling));
        }
        System.out.println("tarifHandling : " + getTarifHandling());
        setCas(getTarifHandling().multiply(BigDecimal.valueOf(box)));
        //setCas(box * getTarifHandling());
        sm.setCharge(getCas());
        listSImulasi.add(sm);

        BigDecimal t = BigDecimal.ZERO;
        for (SimulasiBean o : listSImulasi) {
            t = t.add((BigDecimal) (o.charge));
            //t = t + (Integer) o.charge;
        }
        setTotal(t);
        System.out.println("skdsjk : " + contType);
        System.out.println("charge : " + sm.getCharge());
        System.out.println("cas : " + getCas());
        System.out.println("llll : " + total);
        sm = new SimulasiBean(contStatus, conSize, overSize, contType, dg, dgLabel, box, crane, tipePelayaran);
        return null;
        /*
        int t = 0;
        for (Object[] o : angsurServices) {
        t = t + (Integer) o[8];
        }
        total = String.valueOf(t);
         */
    }

    private MasterTarifContFacadeRemote lookupMasterTarifContFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (MasterTarifContFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/MasterTarifContFacade!com.pelindo.ebtos.ejb.facade.remote.MasterTarifContFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
    /*
    public String createNew() {

    preContainer.setMContainerCategory(new MContainerCategory());

    PreserviceContainer p=new PreserviceContainer(null,preContainer.getNoContainer(),preContainer.getType(),preContainer.getSize(),preContainer.getStowage());
    p.setMContainerCategory(mContainerCategoryFacadeRemote.find(idCategory));
    listPreserviceContainers.add(p);

    System.out.println("data : " + preContainer.getNoContainer());
    System.out.println("data : " + preContainer.getSize());
    System.out.println("data : " + preContainer.getType());
    return null;
    }
     */

    /**
     * @return the contType
     */
    public String getContType() {
        return contType;
    }

    /**
     * @param contType the contType to set
     */
    public void setContType(String contType) {
        this.contType = contType;
    }

    /**
     * @return the conSize
     */
    public String getConSize() {
        return conSize;
    }

    /**
     * @param conSize the conSize to set
     */
    public void setConSize(String conSize) {
        this.conSize = conSize;
    }

    /**
     * @return the overSize
     */
    public Boolean getOverSize() {
        return overSize;
    }

    /**
     * @param overSize the overSize to set
     */
    public void setOverSize(Boolean overSize) {
        this.overSize = overSize;
    }

    /**
     * @return the dg
     */
    public Boolean getDg() {
        return dg;
    }

    /**
     * @param dg the dg to set
     */
    public void setDg(Boolean dg) {
        this.dg = dg;
    }

    /**
     * @return the dgLabel
     */
    public Boolean getDgLabel() {
        return dgLabel;
    }

    /**
     * @param dgLabel the dgLabel to set
     */
    public void setDgLabel(Boolean dgLabel) {
        this.dgLabel = dgLabel;
    }

    /**
     * @return the box
     */
    public Integer getBox() {
        return box;
    }

    /**
     * @param box the box to set
     */
    public void setBox(Integer box) {
        this.box = box;
    }

    /**
     * @return the contStatus
     */
    public String getContStatus() {
        return contStatus;
    }

    /**
     * @param contStatus the contStatus to set
     */
    public void setContStatus(String contStatus) {
        this.contStatus = contStatus;
    }

    /**
     * @return the listSImulasi
     */
    public List<SimulasiBean> getListSImulasi() {
        return listSImulasi;
    }

    /**
     * @param listSImulasi the listSImulasi to set
     */
    public void setListSImulasi(List<SimulasiBean> listSImulasi) {
        this.listSImulasi = listSImulasi;
    }

    /**
     * @return the crane
     */
    public String getCrane() {
        return crane;
    }

    /**
     * @param crane the crane to set
     */
    public void setCrane(String crane) {
        this.crane = crane;
    }

    /**
     * @return the actHandling
     */
    public String getActHandling() {
        return actHandling;
    }

    /**
     * @param actHandling the actHandling to set
     */
    public void setActHandling(String actHandling) {
        this.actHandling = actHandling;
    }

    /**
     * @return the total
     */
    public BigDecimal getTotal() {
        return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    /**
     * @return the charge
     */
    public BigDecimal getCharge() {
        return charge;
    }

    /**
     * @param charge the charge to set
     */
    public void setCharge(BigDecimal charge) {
        this.charge = charge;
    }

    /**
     * @return the tipePelayaran
     */
    public String getTipePelayaran() {
        return tipePelayaran;
    }

    /**
     * @param tipePelayaran the tipePelayaran to set
     */
    public void setTipePelayaran(String tipePelayaran) {
        this.tipePelayaran = tipePelayaran;
    }

    /**
     * @return the tarifHandling
     */
    public BigDecimal getTarifHandling() {
        return tarifHandling;
    }

    /**
     * @param tarifHandling the tarifHandling to set
     */
    public void setTarifHandling(BigDecimal tarifHandling) {
        this.tarifHandling = tarifHandling;
    }

    /**
     * @return the cas
     */
    public BigDecimal getCas() {
        return cas;
    }

    /**
     * @param cas the cas to set
     */
    public void setCas(BigDecimal cas) {
        this.cas = cas;
    }

    /**
     * @return the indonesianNumberConverter
     */
    public IndonesianNumberConverter getIndonesianNumberConverter() {
        return indonesianNumberConverter;
    }

    /**
     * @param indonesianNumberConverter the indonesianNumberConverter to set
     */
    public void setIndonesianNumberConverter(IndonesianNumberConverter indonesianNumberConverter) {
        this.indonesianNumberConverter = indonesianNumberConverter;
    }

    /**
     * @return the terbilang
     */
    public String getTerbilang() {
        return terbilang;
    }

    /**
     * @param terbilang the terbilang to set
     */
    public void setTerbilang(String terbilang) {
        this.terbilang = terbilang;
    }

    /**
     * @return the idList
     */
    public String getIdList() {
        return idList;
    }

    /**
     * @param idList the idList to set
     */
    public void setIdList(String idList) {
        this.idList = idList;
    }

    /**
     * @return the no
     */
    public Integer getNo() {
        return no;
    }

    /**
     * @param no the no to set
     */
    public void setNo(Integer no) {
        this.no = no;
    }

    /**
     * @return the simulasiBean
     */
    public SimulasiBean getSimulasiBean() {
        return simulasiBean;
    }

    /**
     * @param simulasiBean the simulasiBean to set
     */
    public void setSimulasiBean(SimulasiBean simulasiBean) {
        this.simulasiBean = simulasiBean;
    }

    /**
     * @return the idEdit
     */
    public int getIdEdit() {
        return idEdit;
    }

    /**
     * @param idEdit the idEdit to set
     */
    public void setIdEdit(int idEdit) {
        this.idEdit = idEdit;
    }
}
