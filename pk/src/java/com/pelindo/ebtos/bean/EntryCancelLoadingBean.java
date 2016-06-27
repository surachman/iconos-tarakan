/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.bean;

import com.qtasnim.jsf.FacesHelper;
import com.pelindo.ebtos.ejb.facade.remote.CancelLoadingServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterTarifContFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterTarifHistoryFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ReceivingServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.RegistrationFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceContLoadFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceReceivingFacadeRemote;
import com.pelindo.ebtos.model.db.CancelLoadingService;
import com.pelindo.ebtos.model.db.ReceivingService;
import com.pelindo.ebtos.model.db.Registration;
import com.pelindo.ebtos.model.db.ServiceContLoad;
import com.pelindo.ebtos.model.db.ServiceReceiving;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.context.RequestContext;

/**
 *
 * @author dycoder
 */
@ManagedBean(name = "entryCancelLoadingBean")
@ViewScoped
public class EntryCancelLoadingBean implements Serializable {

    @EJB
    RegistrationFacadeRemote registrationFacadeRemote;
    @EJB
    CancelLoadingServiceFacadeRemote cancelLoadingServiceFacadeRemote;
    MasterTarifHistoryFacadeRemote masterTarifHistoryFacade = lookupMasterTarifHistoryFacadeRemote();
    MasterTarifContFacadeRemote masterTarifContFacade = lookupMasterTarifContFacadeRemote();
    private List<Object[]> registrations = lookupRegistrationFacadeRemote().findRegistrationBatalMuat();
    private List<Object[]> cancelLoadings;
    private Object[] serviceReceivings;
    private Object[] receivingServices;
    private Registration registration;
    private Object[] serviceContLoad;
    private String no_reg;
    private String cont_no;
    private String idCancel;
    private ServiceContLoad serviceContLoadObject;
    private ServiceReceiving serviceReceiving;
    private CancelLoadingService cancelLoadingService;
    private ReceivingService receivingService;
    private Short category;
    private String actMov = null;
    private String actLift = null;
    private String actTumpuk = null;
    private String actHandling = null;
    private BigDecimal charge = BigDecimal.ZERO;
    private String currencyCode;
    private boolean visible = Boolean.FALSE;

    /** Creates a new instance of EntryCancelLoadingBean */
    public EntryCancelLoadingBean() {
        registration = new Registration();
        cancelLoadingService = new CancelLoadingService();
        serviceReceiving = new ServiceReceiving();
        receivingService = new ReceivingService();
        serviceReceivings = null;
        receivingServices = null;
        serviceContLoad = null;
        visible = Boolean.FALSE;
    }

    public void clear() {
        cancelLoadingService = new CancelLoadingService();
        serviceReceiving = new ServiceReceiving();
        receivingService = new ReceivingService();
        visible = Boolean.FALSE;
    }

    public void handleSelectRegistration(ActionEvent event) {
        setNo_reg((String) event.getComponent().getAttributes().get("noReg"));
        setRegistration(registrationFacadeRemote.find(getNo_reg()));
        setCancelLoadings(lookupCancelLoadingServiceFacadeRemote().findCancelLoadingServiceByNoreg(getNo_reg()));
    }

    public List<String> setListAutoComplete(String query) {
        List<String> results = new ArrayList<String>();
        List<String> results2 = new ArrayList<String>();
        List<String> results3 = new ArrayList<String>();
        List<String> results4 = new ArrayList<String>();
        results2 = lookupServiceContLoadFacadeRemote().findServiceContLoadByAutoComplete(getRegistration().getPlanningVessel().getNoPpkb(), query);
        results3 = lookupServiceReceivingFacadeRemote().findServiceReceivingByAutoComplete(getRegistration().getPlanningVessel().getNoPpkb(), query);
        results4 = lookupReceivingServiceFacadeRemote().findReceivingServiceByAutoCompleteCancelLoading(getRegistration().getPlanningVessel().getNoPpkb(), query);
        results.addAll(results2);
        results.addAll(results3);
        results.addAll(results4);
        return results;
    }

    public void handleAdd(ActionEvent event) {
        this.clear();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyMM");
        String tgl = dateFormat.format(Calendar.getInstance().getTime());
        String i;
        String id = lookupCancelLoadingServiceFacadeRemote().generateId(tgl.substring(4));
        if(id == null)
            i = "00001";
        else
            i = String.valueOf(Integer.valueOf(id) + 1);

        if(i.length() == 1)
            i = tgl+"0000"+i;
        else if(i.length() == 2)
            i = tgl+"000"+i;
        else if(i.length() == 3)
            i = tgl+"00"+i;
        else if(i.length() == 4)
            i = tgl+"0"+i;
        else
            i = tgl + i;
        cancelLoadingService.setJobSlip("05"+i);
    }

    public void ambilContNo(ActionEvent event) {
        setCont_no((String) event.getComponent().getAttributes().get("jobSlip"));
        setReceivingServices(lookupReceivingServiceFacadeRemote().findReceivingServiceContNo(getRegistration().getPlanningVessel().getNoPpkb(), getCont_no()));
        setServiceContLoad(lookupServiceContLoadFacadeRemote().findServiceContLoadContNo(getRegistration().getPlanningVessel().getNoPpkb(), getCont_no()));
        setServiceReceivings(lookupServiceReceivingFacadeRemote().findServiceReceivingContNo(getRegistration().getPlanningVessel().getNoPpkb(), getCont_no()));
        if (getServiceContLoad() != null) {
            setServiceContLoadObject(lookupServiceContLoadFacadeRemote().find(getServiceContLoad()[1]));
            getCancelLoadingService().setMasterCommodity(getServiceContLoadObject().getMasterCommodity());
            getCancelLoadingService().setContGross(getServiceContLoadObject().getContGross());
            getCancelLoadingService().setContNo(getServiceContLoadObject().getContNo());
            getCancelLoadingService().setMlo(getServiceContLoadObject().getMlo());
            getCancelLoadingService().setContSize(getServiceContLoadObject().getContSize());
            getCancelLoadingService().setContStatus(getServiceContLoadObject().getContStatus());
            getCancelLoadingService().setMasterContainerType(getServiceContLoadObject().getMasterContainerType());
            getCancelLoadingService().setDgLabel(getServiceContLoadObject().getDgLabel());
            getCancelLoadingService().setDg(getServiceContLoadObject().getDangerous());
            getCancelLoadingService().setPlanningVessel(getServiceContLoadObject().getServiceVessel().getPlanningVessel());
            getCancelLoadingService().setRegistration(registration);
            getCancelLoadingService().setOverSize(getServiceContLoadObject().getOverSize());
            getCancelLoadingService().setSealNo(getServiceContLoadObject().getSealNo());
            getCancelLoadingService().setCrane(getServiceContLoadObject().getCrane());
            getCancelLoadingService().setBlNo(getServiceContLoadObject().getBlNo());
            getCancelLoadingService().setPosition("VESSEL");
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.search.found");

        } else if (getServiceReceivings() != null) {
            setServiceReceiving(lookupServiceReceivingFacadeRemote().find(getServiceReceivings()[1]));
            getCancelLoadingService().setMasterCommodity(getServiceReceiving().getMasterCommodity());
            getCancelLoadingService().setContGross(getServiceReceiving().getContGross());
            getCancelLoadingService().setContNo(getServiceReceiving().getContNo());
            getCancelLoadingService().setMlo(getServiceReceiving().getMlo());
            getCancelLoadingService().setContSize(getServiceReceiving().getContSize());
            getCancelLoadingService().setContStatus(getServiceReceiving().getContStatus());
            getCancelLoadingService().setMasterContainerType(getServiceReceiving().getMasterContainerType());
            getCancelLoadingService().setDgLabel(getServiceReceiving().getDgLabel());
            getCancelLoadingService().setDg(getServiceReceiving().getDangerous());
            getCancelLoadingService().setPlanningVessel(getServiceReceiving().getPlanningVessel());
            getCancelLoadingService().setRegistration(registration);
            getCancelLoadingService().setOverSize(getServiceReceiving().getOverSize());
            getCancelLoadingService().setSealNo(getServiceReceiving().getSealNo());
            getCancelLoadingService().setPosition("CY");
            getCancelLoadingService().setBlNo(getServiceReceiving().getBlNo());
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.search.found");

        } else if (getReceivingServices() != null) {
            setReceivingService(lookupReceivingServiceFacadeRemote().find(getReceivingServices()[1]));
            getCancelLoadingService().setMasterCommodity(getReceivingService().getMasterCommodity());
            getCancelLoadingService().setContGross(getReceivingService().getContGross());
            getCancelLoadingService().setContNo(getReceivingService().getContNo());
            getCancelLoadingService().setMlo(getReceivingService().getMlo());
            getCancelLoadingService().setContSize(getReceivingService().getContSize());
            getCancelLoadingService().setContStatus(getReceivingService().getContStatus());
            getCancelLoadingService().setMasterContainerType(getReceivingService().getMasterContainerType());
            getCancelLoadingService().setDgLabel(getReceivingService().getDgLabel());
            getCancelLoadingService().setDg(getReceivingService().getDg());
            getCancelLoadingService().setPlanningVessel(getReceivingService().getPlanningVessel());
            getCancelLoadingService().setRegistration(registration);
            getCancelLoadingService().setOverSize(getReceivingService().getOverSize());
            getCancelLoadingService().setSealNo(getReceivingService().getSealNo());
            getCancelLoadingService().setBlNo(getReceivingService().getBlNo());
            getCancelLoadingService().setPosition("OUTSIDE");
            visible = Boolean.TRUE;
            cancelLoadingService.setPullOut("FALSE");
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.search.found");

        } else {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.search.notfound");
            cancelLoadingService = new CancelLoadingService();
        }
    }

    //ambil activity handling
    public void handleActHandling() {
        if (cancelLoadingService.getContStatus().equals("LCL")) {
            if (cancelLoadingService.getContSize() == 20) {
                if (cancelLoadingService.getCrane().equals("L")) {
                    if (cancelLoadingService.getOverSize().equals("FALSE")) {
                        setActHandling("A013");
                    } else {
                        setActHandling("A014");
                    }
                } else if (cancelLoadingService.getCrane().equals("S")) {
                    if (cancelLoadingService.getOverSize().equals("FALSE")) {
                        setActHandling("A015");
                    } else {
                        setActHandling("A016");
                    }
                }
            } else if (cancelLoadingService.getContSize() == 40) {
                if (cancelLoadingService.getCrane().equals("L")) {
                    if (cancelLoadingService.getOverSize().equals("FALSE")) {
                        setActHandling("A017");
                    } else {
                        setActHandling("A018");
                    }
                } else if (cancelLoadingService.getCrane().equals("S")) {
                    if (cancelLoadingService.getOverSize().equals("FALSE")) {
                        setActHandling("A019");
                    } else {
                        setActHandling("A020");
                    }
                }
            }
        } else {
            if (cancelLoadingService.getOverSize().equals("FALSE")) {
                if (cancelLoadingService.getContStatus().equals("FCL")) {
                    if (cancelLoadingService.getContSize() == 20) {
                        if (cancelLoadingService.getCrane().equals("L")) {
                            setActHandling("A001");
                        }
                        if (cancelLoadingService.getCrane().equals("S")) {
                            setActHandling("A003");
                        }
                    } else if (cancelLoadingService.getContSize() == 40) {
                        if (cancelLoadingService.getCrane().equals("L")) {
                            setActHandling("A005");
                        }
                        if (cancelLoadingService.getCrane().equals("S")) {
                            setActHandling("A007");
                        }
                    }
                } else if (cancelLoadingService.getContStatus().equals("MTY")) {
                    if (cancelLoadingService.getContSize() == 20) {
                        if (cancelLoadingService.getCrane().equals("L")) {
                            setActHandling("A009");
                        }
                        if (cancelLoadingService.getCrane().equals("S")) {
                            setActHandling("A010");
                        }
                    } else if (cancelLoadingService.getContSize() == 40) {
                        if (cancelLoadingService.getCrane().equals("L")) {
                            setActHandling("A011");
                        }
                        if (cancelLoadingService.getCrane().equals("S")) {
                            setActHandling("A012");
                        }
                    }
                }
            } else {
                if (cancelLoadingService.getContSize() == 20) {
                    if (cancelLoadingService.getCrane().equals("L")) {
                        setActHandling("A002");
                    }
                    if (cancelLoadingService.getCrane().equals("S")) {
                        setActHandling("A004");
                    }
                } else if (cancelLoadingService.getContSize() == 40) {
                    if (cancelLoadingService.getCrane().equals("L")) {
                        setActHandling("A006");
                    }
                    if (cancelLoadingService.getCrane().equals("S")) {
                        setActHandling("A008");
                    }
                }
            }
        }
    }

    //ambil activity lift
    public void handleActLift() {
        if (cancelLoadingService.getOverSize().equals("FALSE")) {
            if (cancelLoadingService.getContSize() == 20) {
                if (cancelLoadingService.getContStatus().equals("FCL") || cancelLoadingService.getContStatus().equals("LCL")) {
                    setActLift("B001");
                } else if (cancelLoadingService.getContStatus().equals("MTY")) {
                    setActLift("B002");
                }
            } else if (cancelLoadingService.getContSize() == 40) {
                if (cancelLoadingService.getContStatus().equals("FCL") || cancelLoadingService.getContStatus().equals("LCL")) {
                    setActLift("B003");
                } else if (cancelLoadingService.getContStatus().equals("MTY")) {
                    setActLift("B004");
                }
            }
        } else {
            if (cancelLoadingService.getContSize() == 20) {
                setActLift("B005");
            } else if (cancelLoadingService.getContSize() == 40) {
                setActLift("B006");
            }
        }
    }

    //ambil activity penumpukan
    public void handleActTumpuk() {
        if (cancelLoadingService.getMasterContainerType().getContType() == 2 || cancelLoadingService.getMasterContainerType().getContType() == 10
                || cancelLoadingService.getMasterContainerType().getContType() == 11 || cancelLoadingService.getMasterContainerType().getContType() == 29) {
            if (cancelLoadingService.getContSize() == 20) {
                setActTumpuk("C007");
            } else if (cancelLoadingService.getContSize() == 40) {
                setActTumpuk("C008");
            }
        } else {
            if (cancelLoadingService.getOverSize().equals("FALSE")) {
                if (cancelLoadingService.getContStatus().equals("MTY")) {
                    if (cancelLoadingService.getContSize() == 20) {
                        setActTumpuk("C001");
                    } else if (cancelLoadingService.getContSize() == 40) {
                        setActTumpuk("C002");
                    }
                } else if (cancelLoadingService.getContStatus().equals("FCL") || cancelLoadingService.getContStatus().equals("LCL")) {
                    if (cancelLoadingService.getContSize() == 20) {
                        setActTumpuk("C003");
                    } else if (cancelLoadingService.getContSize() == 40) {
                        setActTumpuk("C004");
                    }
                }
            } else {
                if (cancelLoadingService.getContSize() == 20) {
                    setActTumpuk("C005");
                } else if (cancelLoadingService.getContSize() == 40) {
                    setActTumpuk("C006");
                }
            }
        }
    }

    public void handleConfirm(ActionEvent actionEvent) {
        Boolean loggedIn = true;
        try {
            if (cancelLoadingService.getContStatus().equals("EMPTY")) {
                if (cancelLoadingService.getContSize() == 20) {
                    actMov = "I002";
                } else {
                    actMov = "I004";
                }
            } else {
                if (cancelLoadingService.getContSize() == 20) {
                    actMov = "I001";
                } else {
                    actMov = "I003";
                }
            }


            if (cancelLoadingService.getPosition().equals("OUTSIDE") && cancelLoadingService.getPullOut().equals(Boolean.FALSE)) {
                category = 1;
                cancelLoadingService.setStatus("Container belum ada di CY");
                //actMov = "I001";
                //MasterTarifCont tarif = masterTarifContFacade.find(actMov);
                BigDecimal tarif = BigDecimal.ZERO;
                if (registration.getPlanningVessel().getTipePelayaran().equalsIgnoreCase("d")) {
                    tarif = masterTarifContFacade.findByCurrCodeAndActivity("IDR", actMov);
                    setCurrencyCode("IDR");
                } else if (registration.getPlanningVessel().getTipePelayaran().equalsIgnoreCase("i")) {
                    tarif = masterTarifContFacade.findByCurrCodeAndActivity("USD", actMov);
                    setCurrencyCode("USD");
                }
                charge = tarif;
            } else if (cancelLoadingService.getPosition().equals("CY") && cancelLoadingService.getPullOut().equals(Boolean.TRUE)) {
                category = 2;
                cancelLoadingService.setStatus("Container ada di CY");
                //actMov = "I001";
                BigDecimal tarifLift = BigDecimal.ZERO;
                BigDecimal tarif = BigDecimal.ZERO;
                handleActLift();
                if (registration.getPlanningVessel().getTipePelayaran().equalsIgnoreCase("d")) {
                    tarif = masterTarifContFacade.findByCurrCodeAndActivity("IDR", actMov);
                    setCurrencyCode("IDR");
                } else if (registration.getPlanningVessel().getTipePelayaran().equalsIgnoreCase("i")) {
                    tarif = masterTarifContFacade.findByCurrCodeAndActivity("USD", actMov);
                    setCurrencyCode("USD");
                }
                if (registration.getPlanningVessel().getTipePelayaran().equalsIgnoreCase("d")) {
                    tarifLift = masterTarifContFacade.findByCurrCodeAndActivity("IDR", actLift);
                    setCurrencyCode("IDR");
                } else if (registration.getPlanningVessel().getTipePelayaran().equalsIgnoreCase("i")) {
                    tarifLift = masterTarifContFacade.findByCurrCodeAndActivity("USD", actLift);
                    setCurrencyCode("USD");
                }
                //charge = tarif + tarifLift;
                charge = tarif.add(tarifLift);

            } else if (cancelLoadingService.getPosition().equals("CY") && cancelLoadingService.getPullOut().equals(Boolean.FALSE)) {
                category = 3;
                cancelLoadingService.setStatus("Container ada di CY dan di tarik keluar");
                //actMov = "I001";
                BigDecimal tarif = BigDecimal.ZERO;
                if (registration.getPlanningVessel().getTipePelayaran().equalsIgnoreCase("d")) {
                    tarif = masterTarifContFacade.findByCurrCodeAndActivity("IDR", actMov);
                    setCurrencyCode("IDR");
                } else if (registration.getPlanningVessel().getTipePelayaran().equalsIgnoreCase("i")) {
                    tarif = masterTarifContFacade.findByCurrCodeAndActivity("USD", actMov);
                    setCurrencyCode("USD");
                }
                charge = tarif;

            } else if (cancelLoadingService.getPosition().equals("VESSEL") && cancelLoadingService.getPullOut().equals(Boolean.TRUE)) {
                category = 4;
                cancelLoadingService.setStatus("Container sudah di kapal dan kembali ke CY");
                //actMov = "I001";
                BigDecimal tarifBatalMuat = BigDecimal.ZERO;
                BigDecimal tarifTumpuk = BigDecimal.ZERO;
                BigDecimal tarifHandling = BigDecimal.ZERO;
                BigDecimal tarifLift = BigDecimal.ZERO;

                handleActLift();
                handleActHandling();
                handleActTumpuk();

                if (registration.getPlanningVessel().getTipePelayaran().equalsIgnoreCase("d")) {
                    tarifBatalMuat = masterTarifContFacade.findByCurrCodeAndActivity("IDR", actMov);
                    setCurrencyCode("IDR");
                } else if (registration.getPlanningVessel().getTipePelayaran().equalsIgnoreCase("i")) {
                    tarifBatalMuat = masterTarifContFacade.findByCurrCodeAndActivity("USD", actMov);
                    setCurrencyCode("USD");
                }

                if (registration.getPlanningVessel().getTipePelayaran().equalsIgnoreCase("d")) {
                    tarifTumpuk = masterTarifContFacade.findByCurrCodeAndActivity("IDR", actTumpuk);
                    setCurrencyCode("IDR");
                } else if (registration.getPlanningVessel().getTipePelayaran().equalsIgnoreCase("i")) {
                    tarifTumpuk = masterTarifContFacade.findByCurrCodeAndActivity("USD", actTumpuk);
                    setCurrencyCode("USD");
                }

                if (registration.getPlanningVessel().getTipePelayaran().equalsIgnoreCase("d")) {
                    tarifHandling = masterTarifContFacade.findByCurrCodeAndActivity("IDR", actHandling);
                    setCurrencyCode("IDR");
                } else if (registration.getPlanningVessel().getTipePelayaran().equalsIgnoreCase("i")) {
                    tarifHandling = masterTarifContFacade.findByCurrCodeAndActivity("USD", actHandling);
                    setCurrencyCode("USD");
                }

                if (registration.getPlanningVessel().getTipePelayaran().equalsIgnoreCase("d")) {
                    tarifLift = masterTarifContFacade.findByCurrCodeAndActivity("IDR", actLift);
                    setCurrencyCode("IDR");
                } else if (registration.getPlanningVessel().getTipePelayaran().equalsIgnoreCase("i")) {
                    tarifLift = masterTarifContFacade.findByCurrCodeAndActivity("USD", actLift);
                    setCurrencyCode("USD");
                }
                charge = tarifBatalMuat.add(tarifTumpuk).add(BigDecimal.valueOf(2).multiply(tarifHandling)).add(tarifLift);
                //charge = tarifBatalMuat + tarifTumpuk + (2 * tarifHandling) + tarifLift;


            } else if (cancelLoadingService.getPosition().equals("VESSEL") && cancelLoadingService.getPullOut().equals(Boolean.FALSE)) {
                category = 5;
                cancelLoadingService.setStatus("Container sudah ada di kapal,kembali ke CY dan ditarik keluar");
                //actMov = "I001";
                BigDecimal tarifBatalMuat = BigDecimal.ZERO;
                BigDecimal tarifTumpuk = BigDecimal.ZERO;
                BigDecimal tarifHandling = BigDecimal.ZERO;

                handleActTumpuk();
                handleActHandling();

                if (registration.getPlanningVessel().getTipePelayaran().equalsIgnoreCase("d")) {
                    tarifBatalMuat = masterTarifContFacade.findByCurrCodeAndActivity("IDR", actMov);
                    setCurrencyCode("IDR");
                } else if (registration.getPlanningVessel().getTipePelayaran().equalsIgnoreCase("i")) {
                    tarifBatalMuat = masterTarifContFacade.findByCurrCodeAndActivity("USD", actMov);
                    setCurrencyCode("USD");
                }

                if (registration.getPlanningVessel().getTipePelayaran().equalsIgnoreCase("d")) {
                    tarifTumpuk = masterTarifContFacade.findByCurrCodeAndActivity("IDR", actTumpuk);
                    setCurrencyCode("IDR");
                } else if (registration.getPlanningVessel().getTipePelayaran().equalsIgnoreCase("i")) {
                    tarifTumpuk = masterTarifContFacade.findByCurrCodeAndActivity("USD", actTumpuk);
                    setCurrencyCode("USD");
                }

                if (registration.getPlanningVessel().getTipePelayaran().equalsIgnoreCase("d")) {
                    tarifHandling = masterTarifContFacade.findByCurrCodeAndActivity("IDR", actHandling);
                    setCurrencyCode("IDR");
                } else if (registration.getPlanningVessel().getTipePelayaran().equalsIgnoreCase("i")) {
                    tarifHandling = masterTarifContFacade.findByCurrCodeAndActivity("USD", actHandling);
                    setCurrencyCode("USD");
                }
                charge = tarifBatalMuat.add(tarifTumpuk).add(BigDecimal.valueOf(2).multiply(tarifHandling));
                //charge = tarifBatalMuat + tarifTumpuk + (2 * tarifHandling);

            }
            cancelLoadingService.setCurrCode(currencyCode);
            cancelLoadingService.setCharge(charge);
            cancelLoadingService.setCategory(category);
            cancelLoadingServiceFacadeRemote.edit(cancelLoadingService);
            setCancelLoadings(lookupCancelLoadingServiceFacadeRemote().findCancelLoadingServiceByNoreg(getNo_reg()));
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
        } catch (EJBException ex) {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.failed");
            ex.printStackTrace();
        }
        RequestContext.getCurrentInstance().addCallbackParam("loggedIn", loggedIn);
    }



    public void handleEditDelete(ActionEvent event) {
        setIdCancel((String) event.getComponent().getAttributes().get("jobSlip"));
        cancelLoadingService = cancelLoadingServiceFacadeRemote.find(idCancel);
    }

    public void handleDelete(ActionEvent event) {
        try {
            cancelLoadingServiceFacadeRemote.remove(cancelLoadingService);
            setCancelLoadings(lookupCancelLoadingServiceFacadeRemote().findCancelLoadingServiceByNoreg(getNo_reg()));
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.delete.succeeded");
            this.clear();
        } catch (EJBException ex) {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.delete.failed");
            ex.printStackTrace();
        }

    }

    private RegistrationFacadeRemote lookupRegistrationFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (RegistrationFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/RegistrationFacade!com.pelindo.ebtos.ejb.facade.remote.RegistrationFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private MasterTarifHistoryFacadeRemote lookupMasterTarifHistoryFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (MasterTarifHistoryFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/MasterTarifHistoryFacade!com.pelindo.ebtos.ejb.facade.remote.MasterTarifHistoryFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private CancelLoadingServiceFacadeRemote lookupCancelLoadingServiceFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (CancelLoadingServiceFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/CancelLoadingServiceFacade!com.pelindo.ebtos.ejb.facade.remote.CancelLoadingServiceFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private ServiceContLoadFacadeRemote lookupServiceContLoadFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (ServiceContLoadFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/ServiceContLoadFacade!com.pelindo.ebtos.ejb.facade.remote.ServiceContLoadFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private ReceivingServiceFacadeRemote lookupReceivingServiceFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (ReceivingServiceFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/ReceivingServiceFacade!com.pelindo.ebtos.ejb.facade.remote.ReceivingServiceFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private ServiceReceivingFacadeRemote lookupServiceReceivingFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (ServiceReceivingFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/ServiceReceivingFacade!com.pelindo.ebtos.ejb.facade.remote.ServiceReceivingFacadeRemote");
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

    /**
     * @return the registrations
     */
    public List<Object[]> getRegistrations() {
        return registrations;
    }

    /**
     * @param registrations the registrations to set
     */
    public void setRegistrations(List<Object[]> registrations) {
        this.registrations = registrations;
    }

    /**
     * @return the cancelLoadings
     */
    public List<Object[]> getCancelLoadings() {
        return cancelLoadings;
    }

    /**
     * @param cancelLoadings the cancelLoadings to set
     */
    public void setCancelLoadings(List<Object[]> cancelLoadings) {
        this.cancelLoadings = cancelLoadings;
    }

    /**
     * @return the serviceReceivings
     */
    public Object[] getServiceReceivings() {
        return serviceReceivings;
    }

    /**
     * @param serviceReceivings the serviceReceivings to set
     */
    public void setServiceReceivings(Object[] serviceReceivings) {
        this.serviceReceivings = serviceReceivings;
    }

    /**
     * @return the receivingServices
     */
    public Object[] getReceivingServices() {
        return receivingServices;
    }

    /**
     * @param receivingServices the receivingServices to set
     */
    public void setReceivingServices(Object[] receivingServices) {
        this.receivingServices = receivingServices;
    }

    /**
     * @return the registration
     */
    public Registration getRegistration() {
        return registration;
    }

    /**
     * @param registration the registration to set
     */
    public void setRegistration(Registration registration) {
        this.registration = registration;
    }

    /**
     * @return the serviceContLoad
     */
    public Object[] getServiceContLoad() {
        return serviceContLoad;
    }

    /**
     * @param serviceContLoad the serviceContLoad to set
     */
    public void setServiceContLoad(Object[] serviceContLoad) {
        this.serviceContLoad = serviceContLoad;
    }

    /**
     * @return the no_reg
     */
    public String getNo_reg() {
        return no_reg;
    }

    /**
     * @param no_reg the no_reg to set
     */
    public void setNo_reg(String no_reg) {
        this.no_reg = no_reg;
    }

    /**
     * @return the cont_no
     */
    public String getCont_no() {
        return cont_no;
    }

    /**
     * @param cont_no the cont_no to set
     */
    public void setCont_no(String cont_no) {
        this.cont_no = cont_no;
    }

    /**
     * @return the serviceContLoadObject
     */
    public ServiceContLoad getServiceContLoadObject() {
        return serviceContLoadObject;
    }

    /**
     * @param serviceContLoadObject the serviceContLoadObject to set
     */
    public void setServiceContLoadObject(ServiceContLoad serviceContLoadObject) {
        this.serviceContLoadObject = serviceContLoadObject;
    }

    /**
     * @return the serviceReceiving
     */
    public ServiceReceiving getServiceReceiving() {
        return serviceReceiving;
    }

    /**
     * @param serviceReceiving the serviceReceiving to set
     */
    public void setServiceReceiving(ServiceReceiving serviceReceiving) {
        this.serviceReceiving = serviceReceiving;
    }

    /**
     * @return the cancelLoadingService
     */
    public CancelLoadingService getCancelLoadingService() {
        return cancelLoadingService;
    }

    /**
     * @param cancelLoadingService the cancelLoadingService to set
     */
    public void setCancelLoadingService(CancelLoadingService cancelLoadingService) {
        this.cancelLoadingService = cancelLoadingService;
    }

    /**
     * @return the receivingService
     */
    public ReceivingService getReceivingService() {
        return receivingService;
    }

    /**
     * @param receivingService the receivingService to set
     */
    public void setReceivingService(ReceivingService receivingService) {
        this.receivingService = receivingService;
    }

    /**
     * @return the category
     */
    public Short getCategory() {
        return category;
    }

    /**
     * @param category the category to set
     */
    public void setCategory(Short category) {
        this.category = category;
    }

    /**
     * @return the idCancel
     */
    public String getIdCancel() {
        return idCancel;
    }

    /**
     * @param idCancel the idCancel to set
     */
    public void setIdCancel(String idCancel) {
        this.idCancel = idCancel;
    }

    /**
     * @return the actMov
     */
    public String getActMov() {
        return actMov;
    }

    /**
     * @param actMov the actMov to set
     */
    public void setActMov(String actMov) {
        this.actMov = actMov;
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
     * @return the actLift
     */
    public String getActLift() {
        return actLift;
    }

    /**
     * @param actLift the actLift to set
     */
    public void setActLift(String actLift) {
        this.actLift = actLift;
    }

    /**
     * @return the actTumpuk
     */
    public String getActTumpuk() {
        return actTumpuk;
    }

    /**
     * @param actTumpuk the actTumpuk to set
     */
    public void setActTumpuk(String actTumpuk) {
        this.actTumpuk = actTumpuk;
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
     * @return the currencyCode
     */
    public String getCurrencyCode() {
        return currencyCode;
    }

    /**
     * @param currencyCode the currencyCode to set
     */
    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    /**
     * @return the visible
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * @param visible the visible to set
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
