/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.ejb;

import com.qtasnim.util.FileBuilder;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

/**
 *
 * @author dycode-java
 */
@Stateless
@LocalBean
public class UploadFile {

    private List<Object[]> baplies;
    private Object[] baplie;
    private String noPPKB = "";
    private boolean choosePPKB = false;
    private boolean visible = false;

    public List<Object[]> input(byte[] file) throws FileNotFoundException, IOException, BiffException {
        baplies = new ArrayList<Object[]>();
        File data = FileBuilder.getFile(file);
        Workbook w;
        try {
            w = Workbook.getWorkbook(data);
            Sheet sheet = w.getSheet(1);
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
                        bawah = x - 2;
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
                baplie = new Object[sheet.getColumns()-1];
                for (sel = pinggir; sel < sheet.getColumns(); sel++) {
                    baplie[ab] = sheet.getCell(sel, mulai).getContents();
                    if (ab == sheet.getColumns()-2) {
                        ab = 0;
                        break;
                    }
                    ab++;
                }
                getBaplies().add(baplie);
            }
        } catch (BiffException e) {
        }
        data.delete();
        return getBaplies();
    }

    /**
     * @return the baplies
     */
    public List<Object[]> getBaplies() {
        if(baplies == null){
            baplies = new ArrayList<Object[]>();
        }
        return baplies;
    }

    /**
     * @return the choosePPKB
     */
    public boolean isChoosePPKB() {
        return choosePPKB;
    }

    /**
     * @return the noPPKB
     */
    public String getNoPPKB() {
        choosePPKB = false;        
        return noPPKB;
    }

    /**
     * @param noPPKB the noPPKB to set
     */
    public void setNoPPKB(String noPPKB) {
        choosePPKB = true;        
        this.noPPKB = noPPKB;
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

    public void setChoosePPKB(boolean choosePPKB) {
        this.choosePPKB = choosePPKB;
    }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
