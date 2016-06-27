/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.bean;

import com.qtasnim.jsf.FacesHelper;
import com.pelindo.ebtos.ejb.facade.remote.MasterCustomerFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterDiscountFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterEquipmentFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterTarifContFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceSewaAlatFacadeRemote;
import com.pelindo.ebtos.model.db.ServiceSewaAlat;
import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.apache.commons.lang3.StringEscapeUtils;
import org.primefaces.context.RequestContext;

/**
 *
 * @author dycoder
 */
@ManagedBean(name = "sewaAlatServiceBean")
@ViewScoped
public class SewaAlatServiceBean {

    private MasterCustomerFacadeRemote masterCustomerFacadeRemote = lookupMasterCustomerFacadeRemote();
    private MasterEquipmentFacadeRemote masterEquipmentFacadeRemote = lookupMasterEquipmentFacadeRemote();
    private ServiceSewaAlatFacadeRemote serviceSewaAlatFacadeRemote = lookupServiceSewaAlatFacadeRemote();
    private MasterDiscountFacadeRemote masterDiscountFacadeRemote = lookupMasterDiscountFacadeRemote();
    private ServiceSewaAlat sewaAlatService;
    private List<Object[]> equipmentList;
    private List<Object[]> customerList;
    private List<Object[]> listSewaAlat;
    private String equipmentRentActivityCode = null;

    /** Creates a new instance of SewaAlatService */
    public SewaAlatServiceBean() {
        sewaAlatService = new ServiceSewaAlat();
        listSewaAlat = serviceSewaAlatFacadeRemote.findEquipmentSewaAlat();
        sewaAlatService.setJenisSewa("Jam");
    }

    public void viewData() {

        if (sewaAlatService.getJenisSewa().equalsIgnoreCase("Jam")) {
            System.out.println("masuk jam");
            if (sewaAlatService.getEquipCode().startsWith("05")) {
                this.equipmentRentActivityCode = "RS01";
            } else if (sewaAlatService.getEquipCode().startsWith("06")) {
                this.equipmentRentActivityCode = "FK01";
            }
        } else if (sewaAlatService.getJenisSewa().equalsIgnoreCase("Box")) {
            if (sewaAlatService.getEquipCode().startsWith("05")) {
                if (sewaAlatService.getContSize() == 20) {
                    if (sewaAlatService.getContStatus().equalsIgnoreCase("MTY")) {
                        this.equipmentRentActivityCode = "RS01";
                    } else {
                        this.equipmentRentActivityCode = "RS02";
                    }
                } else if (sewaAlatService.getContSize() == 40) {
                    if (sewaAlatService.getContStatus().equalsIgnoreCase("MTY")) {
                        this.equipmentRentActivityCode = "RS01";
                    } else {
                        this.equipmentRentActivityCode = "RS03";
                    }
                }
            } else if (sewaAlatService.getEquipCode().startsWith("06")) {
                if (sewaAlatService.getContSize() == 20) {
                    if (sewaAlatService.getContStatus().equalsIgnoreCase("MTY")) {
                        this.equipmentRentActivityCode = "FK02";
                    } else {
                        this.equipmentRentActivityCode = "FK03";
                    }
                } else if (sewaAlatService.getContSize() == 40) {
                    if (sewaAlatService.getContStatus().equalsIgnoreCase("MTY")) {
                        this.equipmentRentActivityCode = "FK04";
                    } else {
                        this.equipmentRentActivityCode = "FK05";
                    }
                }
            }
        }
    }

    public void handleSelect(ActionEvent event) {
        Integer id = (Integer) event.getComponent().getAttributes().get("idSewa");
        sewaAlatService = lookupServiceSewaAlatFacadeRemote().find(id);
        equipmentList = masterEquipmentFacadeRemote.findEquipmentSewaAlat();
        customerList = masterCustomerFacadeRemote.findMasterCustomers();
    }

    public void handleAdd(ActionEvent event) {
        equipmentList = masterEquipmentFacadeRemote.findEquipmentSewaAlat();
        customerList = masterCustomerFacadeRemote.findMasterCustomers();
        sewaAlatService = new ServiceSewaAlat();
        sewaAlatService.setJenisSewa("Jam");
//        sewaAlatService.setContSize(20);
//        sewaAlatService.setContStatus("FCL");
//        this.viewData();
//        sewaAlatService.setTarifCont(lookupMasterTarifContFacadeRemote().findByActivityAndCurr("IDR", act));
    }

    public void delete(ActionEvent event) {
        try {
            lookupServiceSewaAlatFacadeRemote().remove(sewaAlatService);
            listSewaAlat = serviceSewaAlatFacadeRemote.findEquipmentSewaAlat();
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.delete.succeeded");
        } catch (EJBException e) {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.delete.failed");
            e.printStackTrace();
        }
    }

    public void saveEdit(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        boolean loggedIn = false;
        try {
            loggedIn = true;
            this.viewData();
            //mengambil nilai discount
            
            BigDecimal equipmentRentCharge = lookupMasterTarifContFacadeRemote().findByCurrCodeAndActivity("IDR", equipmentRentActivityCode);
            BigDecimal[] discount = masterDiscountFacadeRemote.getValidDiscount(sewaAlatService.getCustCode(), equipmentRentActivityCode);
            BigDecimal discountAsPercentage = discount[0];
            BigDecimal discountAsMoney = discount[1];

            if (discountAsMoney == null) {
                discountAsMoney = equipmentRentCharge.multiply(discountAsPercentage);
            }

            sewaAlatService.setTarifCont(equipmentRentCharge.subtract(discountAsMoney));

            if (sewaAlatService.getJenisSewa().equalsIgnoreCase("Jam")) {
                sewaAlatService.setTotalCharge(sewaAlatService.getTarifCont().multiply(BigDecimal.valueOf(sewaAlatService.getEstJam())));
            } else {
                sewaAlatService.setTotalCharge(sewaAlatService.getTarifCont().multiply(BigDecimal.valueOf(sewaAlatService.getJmlCont())));
            }

            serviceSewaAlatFacadeRemote.edit(sewaAlatService);
            listSewaAlat = serviceSewaAlatFacadeRemote.findEquipmentSewaAlat();
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
        } catch (EJBException e) {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.save.failed");
            e.printStackTrace();
        }
        context.addCallbackParam("loggedIn", loggedIn);
    }

    public void onChangeEventSize(ValueChangeEvent event) {
        Integer newItem = (Integer) event.getNewValue();
        sewaAlatService.setContSize(newItem);
//        this.viewData();
//        sewaAlatService.setTarifCont(lookupMasterTarifContFacadeRemote().findByActivityAndCurr("IDR", act));
    }

    public void onChangeEventStatus(ValueChangeEvent event) {
        String newItem = (String) event.getNewValue();
        sewaAlatService.setContStatus(newItem);
//        this.viewData();
//        sewaAlatService.setTarifCont(lookupMasterTarifContFacadeRemote().findByActivityAndCurr("IDR", act));
    }

    public void onChangeEventEquipment(ValueChangeEvent event) {
        String newItem = (String) event.getNewValue();
        sewaAlatService.setEquipCode(newItem);
        System.out.println("sdfdsfdsf" + sewaAlatService.getEquipCode());
    }

    public void onChangeEvent(ValueChangeEvent event) {
        String newItem = (String) event.getNewValue();
        sewaAlatService.setJenisSewa(newItem);
    }

    public void doFirstPrint(ActionEvent event) {
        Integer idSewa = (Integer) event.getComponent().getAttributes().get("idSewa");
        RequestContext context = RequestContext.getCurrentInstance();
        context.addCallbackParam("doPrint", true); //harus ada, buat ngeflag di izinkan ngeprint ato ngga
        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3("./../../../../sewaAlat.pdf?idSewa=" + idSewa));
    }

    private MasterDiscountFacadeRemote lookupMasterDiscountFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (MasterDiscountFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/MasterDiscountFacade!com.pelindo.ebtos.ejb.facade.remote.MasterDiscountFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private ServiceSewaAlatFacadeRemote lookupServiceSewaAlatFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (ServiceSewaAlatFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/ServiceSewaAlatFacade!com.pelindo.ebtos.ejb.facade.remote.ServiceSewaAlatFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private MasterCustomerFacadeRemote lookupMasterCustomerFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (MasterCustomerFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/MasterCustomerFacade!com.pelindo.ebtos.ejb.facade.remote.MasterCustomerFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private MasterEquipmentFacadeRemote lookupMasterEquipmentFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (MasterEquipmentFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/MasterEquipmentFacade!com.pelindo.ebtos.ejb.facade.remote.MasterEquipmentFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
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

    public List<Object[]> getCustomerList() {
        return customerList;
    }

    public void setCustomerList(List<Object[]> customerList) {
        this.customerList = customerList;
    }

    public List<Object[]> getEquipmentList() {
        return equipmentList;
    }

    public void setEquipmentList(List<Object[]> equipmentList) {
        this.equipmentList = equipmentList;
    }

    public ServiceSewaAlat getSewaAlatService() {
        return sewaAlatService;
    }

    public void setSewaAlatService(ServiceSewaAlat sewaAlatService) {
        this.sewaAlatService = sewaAlatService;
    }

    public List<Object[]> getListSewaAlat() {
        return listSewaAlat;
    }

    public void setListSewaAlat(List<Object[]> listSewaAlat) {
        this.listSewaAlat = listSewaAlat;
    }

    public String getAct() {
        return equipmentRentActivityCode;
    }

    public void setAct(String act) {
        this.equipmentRentActivityCode = act;
    }
}
