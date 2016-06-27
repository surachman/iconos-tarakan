/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.bean;

import com.qtasnim.jsf.FacesHelper;
import com.qtasnim.util.FileBuilder;
import com.pelindo.ebtos.ejb.facade.remote.MasterContainerTypeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterSettingAppFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterVesselFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterYardCoordinatFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningVesselFacadeRemote;
import com.pelindo.ebtos.model.db.BaplieDischarge;
import com.pelindo.ebtos.model.db.LogExcel;
import com.pelindo.ebtos.model.db.PlanningContReceiving;
import com.pelindo.ebtos.model.db.PlanningVessel;
import com.pelindo.ebtos.model.db.master.MasterCommodity;
import com.pelindo.ebtos.model.db.master.MasterContainerType;
import com.pelindo.ebtos.model.db.master.MasterPort;
import com.pelindo.ebtos.model.db.master.MasterVessel;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author dycode-java
 */
@ManagedBean(name = "newGenerateBaplieBean")
@ViewScoped
public class NewGenerateBaplieBean implements Serializable {
    //declaration for connect to database
    @EJB
    private PlanningVesselFacadeRemote planningVesselFacadeRemote;
    @EJB
    private MasterSettingAppFacadeRemote masterSettingAppFacadeRemote;
    @EJB
    private MasterVesselFacadeRemote masterVesselFacadeRemote;
    @EJB
    private MasterYardCoordinatFacadeRemote masterYardCoordinatFacadeRemote;
    @EJB
    private MasterContainerTypeFacadeRemote masterContainerTypeFacadeRemote;
    //declaration for view in UI
    private Object[] planningVessel;
    private List<Object[]> planningVessels;
    private List<Object[]> baplies;
    private String action;
    private int disPort;
    private int otherPort;
    private int totalPort;
    private boolean visible;
    private boolean disableSave;
    private boolean disablePPKB;
    private LogExcel logExcel;
    private BaplieDischarge baplieDischarge;
    private PlanningContReceiving planningContReceiving;
    private PlanningVessel vessel;
    private String originalName;
    private String changeName;
    private String noPPKB;
    private String isoCode;
    private Date now;
    private boolean choosePPKB;
    private String atPort;
    private String otPort;
    private boolean visibleTotal;
    private List<Integer> invalidContainer;
    private String allConts;

    /** Creates a new instance of NewGenerateBaplieBean */
    public NewGenerateBaplieBean() {}

    @PostConstruct
    private void construct() {
        //tidak terlihat total
        visibleTotal = false;
        //inisialisasi count
        disPort = 0;
        otherPort = 0;
        totalPort = 0;
        //inisialisasi vessel
        vessel = new PlanningVessel();
        //inisialisasi baplie
        baplieDischarge = new BaplieDischarge();
        baplieDischarge.setMasterCommodity(new MasterCommodity());
        baplieDischarge.setMasterContainerType(new MasterContainerType());
        baplieDischarge.setPlanningVessel(new PlanningVessel());
        baplieDischarge.setMasterPort(new MasterPort());
        baplieDischarge.setMasterPort1(new MasterPort());

        //inisialisasi planningContReceiving
        planningContReceiving = new PlanningContReceiving();
        planningContReceiving.setMasterCommodity(new MasterCommodity());
        planningContReceiving.setMasterContainerType(new MasterContainerType());
        planningContReceiving.setPlanningVessel(new PlanningVessel());

        //isi data planningVessels
        planningVessels = new ArrayList<Object[]>();
        //inisialisasi baplies
        baplies = new ArrayList<Object[]>();
        disablePPKB = true;
        disableSave = true;
        choosePPKB = false;
        visible = false;
        noPPKB = null;
    }

    /**
     * @return the planningVessels
     */
    public List<Object[]> getPlanningVessels() {
        return planningVessels;
    }

    public void handleSelectNoPpkb(ActionEvent event) {
        noPPKB = (String) event.getComponent().getAttributes().get("noPpkb");
        planningVessel = planningVesselFacadeRemote.findPlanningVesselByPpkb(noPPKB);
        vessel = planningVesselFacadeRemote.find(noPPKB);
        choosePPKB = true;
    }

    public void handleRemoveInvalidContainer(ActionEvent event) {
        String portCode = masterSettingAppFacadeRemote.findImplementedPortCode();

        if (baplies.size() > 0 && invalidContainer.size() > 0) {
            for (int i = invalidContainer.size() - 1; i >= 0; i--) {
                Integer index = invalidContainer.get(i);
                String port = (String) baplies.get(i)[9];
                if (port != null && port.equals(portCode)) {
                    disPort--;
                } else {
                    otherPort--;
                }
                baplies.remove((int) index);
                invalidContainer.remove(i);
            }
        }
    }

    public void handleUpload(FileUploadEvent event) throws IOException {
        //return 0
        disPort = 0;
        otherPort = 0;
        totalPort = 0;
        //start upload
        UploadedFile file = event.getFile();
        now = Calendar.getInstance().getTime();
        DateFormat df = new SimpleDateFormat("ddMMyyhhmmss");
        changeName = noPPKB + "-" + df.format(now);
        String original = file.getFileName();
        originalName = original.replace(" ", "_");
        visible = true;
        invalidContainer = new ArrayList();
       
        allConts = "#";
        boolean stoploopData = false;
        try {
            baplies = input(changeName, file.getContents());
            if (action == null) {
                baplies = new ArrayList<Object[]>();
                visible = false;
                disableSave = true;
                FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_ERROR, "info", "application.execution.baplie_nochoice");
            } else if (!choosePPKB) {
                baplies = new ArrayList<Object[]>();
                visible = false;
                disableSave = true;
                FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_ERROR, "info", "application.execution.baplie_noppkb");
            } else {
                //hitung baplies
                totalPort = baplies.size();
                String portCode = masterSettingAppFacadeRemote.findImplementedPortCode();
                if (action.equalsIgnoreCase("discharge")) {
                    for (int i = 0; i < baplies.size(); i++) {
                        if (!baplies.get(i)[9].equals("")) {
                            String port = (String) baplies.get(i)[9];
                            if (port.equalsIgnoreCase(portCode)) {
                                disPort++;
                            } else {
                                otherPort++;
                            }
                        }                        
                    }
                    
//                    for (int i = 0; i < baplies.size(); i++) {
//                        
//                        
//                        for (int x = 0; x < i; x++) {
//                            MasterContainerType mct  = masterContainerTypeFacadeRemote.findByIsoCode(baplies.get(x)[3].toString());
//                            if(baplies.get(i)[3].equals(baplies.get(x)[3])){
//                                
//                                
//                                
//                                isoCode=mct.getName();
//                                stoploopData = true;
//                                break;
//                            }
//                        
//                        }
//                        
//                    if (stoploopData == true) {
//                         break;
//                        } 
//                    }
        
//        int angka = baplies.size();   
//        int duplicates = 0;
//        for (int i = 0; i < angka - 1; i++) {
//        // start from the next item after strings[i]
//        // since the ones before are checked
//        for (int j = i + 1; j < angka; j++) {
//            // no need for if ( i == j ) here
//            if (!baplies.get(j)[2].equals(baplies.get(i)[2]))
//                continue;
//            duplicates++;
//            baplies.remove(j);
//            
//            // decrease j because the array got re-indexed
//            j--;
//            // decrease the size of the array
//            angka--;
//            
////            MasterContainerType mct  = masterContainerTypeFacadeRemote.findByIsoCode(baplies.get(j)[3].toString());
////            isoCode=mct.getName();
//        
//        } // for j
//    } 
                    
                    
                } else if (action.equalsIgnoreCase("load")) {
                    for (int i = 0; i < baplies.size(); i++) {
                        if (!baplies.get(i)[8].equals("")) {
                            String port = (String) baplies.get(i)[8];
                            if (port.equalsIgnoreCase(portCode)) {
                                disPort++;
                            } else {
                                otherPort++;
                            }
                        }
                    }
                }
                disableSave = false;
                FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "info", "application.execution.baplie_succeded_uploaded");
            }

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (BiffException ex) {
            ex.printStackTrace();
        }
    }

    public void validationContainer() {
        boolean loggedIn = false;
        if (invalidContainer.isEmpty()) {
            loggedIn = true;
        }
        RequestContext.getCurrentInstance().addCallbackParam("loggedIn", loggedIn);
    }

    public String isContValid(Integer index, String contNo) {
        String message = "";

        if (contNo.length() > 11) {
            message += "Nomor kontainer melebihi 11 karakter";
        }

        if (allConts.contains("#" + contNo + "#")){
            if (!message.equals(""))
                message += ", ";
            message += "Duplikat dengan yang ada di baplie";
        }
//
//        Tarakan
//        
//        Object[] contOnYard = masterYardCoordinatFacadeRemote.checkContDuplicateWith(contNo);
//        if (contOnYard != null) {
//            if (!message.equals(""))
//                message += ", ";
//            message += "Duplikat dengan kontainer di blok "+contOnYard[1]+" slot "+contOnYard[2]+" row "+contOnYard[3]+" tier "+contOnYard[4]+"";
//        }

        if (!message.equals("")) {
            invalidContainer.add(index);
        }

        allConts = allConts + contNo + "#";
        return message;
    }

    /**
     * @return the planningVessel
     */
    public Object[] getPlanningVessel() {
        return planningVessel;
    }

    /**
     * @return the baplies
     */
    public List<Object[]> getBaplies() {
        return baplies;
    }

    public void saveData() {
        if (choosePPKB == true && isVisible() == true && action != null) {
            try {
                MasterVessel masterVessel = masterVesselFacadeRemote.find(vessel.getVesselCode());
                logExcel = new LogExcel();
                logExcel.setChangeName(changeName);
                logExcel.setNoPpkb(noPPKB);
                logExcel.setOriginalName(originalName);
                logExcel.setType(action);
                logExcel.setUploadTime(now);
                logExcel.setVesselCode(masterVessel.getVesselCode());
                logExcel.setVesselName(masterVessel.getName());
                planningVesselFacadeRemote.saveBaplieFromExcel(vessel, baplies, action, logExcel);

                planningVessels = new ArrayList<Object[]>();
                baplies = new ArrayList<Object[]>();
                planningVessel = new Object[4];
                action = null;
                noPPKB = null;
                disablePPKB = true;
                disableSave = true;
                choosePPKB = false;
                visible = false;
                disPort = 0;
                otherPort = 0;
                totalPort = 0;
                visibleTotal = false;
                
                FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "info", "application.save.succeeded");
            } catch (RuntimeException re) {
                FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "info", "application.save.failed");
            }
        }
    }

    /**
     * @return the disPort
     */
    public int getDisPort() {
        return disPort;
    }

    /**
     * @return the otherPort
     */
    public int getOtherPort() {
        return otherPort;
    }

    /**
     * @return the totalPort
     */
    public int getTotalPort() {
        return totalPort;
    }

    //uploadFile
    public List<Object[]> input(String nameFile, byte[] file) throws FileNotFoundException, IOException, BiffException {
        List<Object[]> newBaplies = new ArrayList<Object[]>();
        File data = FileBuilder.getFile(nameFile, file);
        Workbook w = Workbook.getWorkbook(data);
        Sheet sheet = w.getSheet(0);
        //variable handle loop
        boolean stoploop = false;
        int atas = 0;
        int bawah = 0;
        int pinggir = 0;
        int x;
        int y;
        int mulai;
        int sel;

        for (x = 0; x < sheet.getRows(); x++) {
            for (y = 0; y < sheet.getColumns(); y++) {
                if (sheet.getCell(y, x).getContents().equalsIgnoreCase("<BOF>")) {
                    pinggir = y;
                    atas = x + 1;
                    continue;
                }
                if (sheet.getCell(y, x).getContents().equalsIgnoreCase("<EOF>")) {
                    pinggir = y;
                    bawah = x - 1;
                    stoploop = true;
                    break;
                }
            }
            if (stoploop == true) {
                break;
            }
        }
        //loop for save data
        int ab = 0;
        for (mulai = atas; mulai <= bawah; mulai++) {
            if (!sheet.getCell(pinggir + 2, mulai).getContents().trim().equals("")) {
                Object[] newBaplie = new Object[18];
                for (sel = pinggir; sel < sheet.getColumns(); sel++) {
                    newBaplie[ab] = sheet.getCell(sel, mulai).getContents().trim();
                    if (ab == sheet.getColumns() - 2) {
                        ab = 0;
                        break;
                    }
                    ab++;
                }
                newBaplie[17] = isContValid(newBaplies.size(), (String) newBaplie[2]);
                newBaplies.add(newBaplie);
            }
        }
        //data.delete();
        return newBaplies;
    }

    /**
     * @return the visible
     */
    public boolean isVisible() {
        return visible;
    }

    public void onChangeAction(ValueChangeEvent event) {
        //kosongkan data
        planningVessels = new ArrayList<Object[]>();
        planningVessel = new Object[4];
        noPPKB = null;
        choosePPKB = false;
        //mulai alur
        action = (String) event.getNewValue();
        if (action.equalsIgnoreCase("discharge")) {
            planningVessels = planningVesselFacadeRemote.findPlanningVesselsByActivity();
            atPort = "Discharge at This Port";
            otPort = "Discharge at Other Port";
            visibleTotal = true;
        } else if (action.equalsIgnoreCase("load")) {
            planningVessels = planningVesselFacadeRemote.findPlanningVesselByActivityLoad();
            atPort = "Load at This Port";
            otPort = "Load at Other Port";
            visibleTotal = true;
        } else {
            planningVessels = new ArrayList<Object[]>();
            visibleTotal = false;
        }
        disablePPKB = false;
        //kosongkeun
        baplies = new ArrayList<Object[]>();
        disableSave = true;
        visible = false;
        disPort = 0;
        otherPort = 0;
        totalPort = 0;
    }

    /**
     * @return the action
     */
    public String getAction() {
        return action;
    }

    /**
     * @param action the action to set
     */
    public void setAction(String action) {
        this.action = action;
    }

    /**
     * @return the disableSave
     */
    public boolean isDisableSave() {
        return disableSave;
    }

    /**
     * @return the disablePPKB
     */
    public boolean isDisablePPKB() {
        return disablePPKB;
    }

    public void clear() {
        baplies = new ArrayList<Object[]>();
        disableSave = true;
        visible = false;
        disPort = 0;
        otherPort = 0;
        totalPort = 0;
    }

    /**
     * @return the title
     */
    public String getAtPort() {
        return atPort;
    }

    /**
     * @return the otPort
     */
    public String getOtPort() {
        return otPort;
    }

    /**
     * @return the visibleTotal
     */
    public boolean isVisibleTotal() {
        return visibleTotal;
    }

    public String getIsoCode() {
        return isoCode;
    }

    public void setIsoCode(String isoCode) {
        this.isoCode = isoCode;
    }
    
    
    
}
