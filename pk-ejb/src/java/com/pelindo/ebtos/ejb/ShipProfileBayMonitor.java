/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb;

import com.qtasnim.util.Padding;
import com.pelindo.ebtos.ejb.facade.local.*;
import com.pelindo.ebtos.ejb.remote.ShipProfileBayMonitorRemote;
import com.pelindo.ebtos.model.ContainerBayMonitor;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

/**
 *
 * @author Eka
 */
@Stateless
public class ShipProfileBayMonitor implements ShipProfileBayMonitorRemote{
    public static final Integer DEFAULT_TIER_NUMBER = 2;
    public static final Integer DEFAULT_ROW_NUMBER = 2;
    public static final Integer DEFAULT_PIXEL_X = 140;
    public static final Integer DEFAULT_PIXEL_Y = 120;

    @EJB private MasterProfileDetailFacadeLocal masterProfileDetailFacade;
    @EJB private MasterVesselProfileFacadeLocal masterVesselProfileFacade;
//    @EJB private PlanningContLoadFacadeLocal planningContLoadFacade;
    @EJB private ServiceContLoadFacadeLocal serviceContLoadFacadeLocal;
    @EJB private ServiceShiftingFacadeLocal serviceShiftingFacadeLocal;

    public static Padding padding;
    public static Dimension size;
    public static int pixelX, pixelY;

    public static int bay, tierUp, tierBottom, rowUp, rowBottom;

    public static int startTierUp, endTierUp, endTierBottom, startRowUp, startRowBottom, startTierBottom;
    public static int midX, midY, mediaHeight, mediaWidth;
    public static String vesselCode, noPpkb;

    public static boolean isUpActive;
    public static boolean isBottomActive;
    public static boolean isLoad;

    @Override
    public void initWithVesselAndPPKB(String vesselCode, String noPPKB, Integer bay, Integer w, Integer h, boolean isLoad){
        padding = new Padding(50, 30, 30, 30);
        if (w != null || h != null) {
            size = new Dimension(w,h);
        } else {
            size = null;
        }

        ShipProfileBayMonitor.vesselCode = vesselCode;
        ShipProfileBayMonitor.bay = bay;
        ShipProfileBayMonitor.noPpkb = noPPKB;
        ShipProfileBayMonitor.isLoad = isLoad;
    }

    public void initParam(){
        tierUp = DEFAULT_TIER_NUMBER;
        rowUp = DEFAULT_ROW_NUMBER;
        tierBottom = DEFAULT_TIER_NUMBER;
        rowBottom = DEFAULT_ROW_NUMBER;
        isUpActive = false;
        isBottomActive = false;
    }

    @Override
    public Blob generate() {
        BufferedImage outImage = new BufferedImage(1000, 500, BufferedImage.TYPE_INT_RGB);
        TextLayout tl;

        List<Object[]> bayIdentity, bayDetail;

        bayIdentity = masterVesselProfileFacade.findBayIdentityByVessel(vesselCode, bay);
        bayDetail = masterProfileDetailFacade.findBayDetailByVessel(vesselCode, bay);

        initParam();
        
        if (!bayIdentity.isEmpty() && !bayDetail.isEmpty()){
            for (Object[] data: bayIdentity){
                if (data[0].toString().equals("up")){
                    isUpActive = true;
                    tierUp = (Integer) data[1];
                    rowUp = (Integer) data[2];
                    startRowUp = (Integer) data[3];
                    startTierUp = (Integer) data[4];
                } else if (data[0].toString().equals("bottom")){
                    isBottomActive = true;
                    tierBottom = (Integer) data[1];
                    rowBottom = (Integer) data[2];
                    startRowBottom = (Integer) data[3];
                    startTierBottom = (Integer) data[4];
                }
            }

            endTierBottom = (2 * tierBottom) + startTierBottom;
            endTierUp = (2 * tierUp) + startTierUp;
            //param
            int max_row = 0;
            int max_tier = 0;
            if (rowUp > rowBottom)
                max_row = rowUp;
            else
                max_row = rowBottom;
            if (tierUp > tierBottom + 1)
                max_tier = tierUp;
            else
                max_tier = tierBottom + 1;

            if (size == null) {
                pixelY = DEFAULT_PIXEL_Y;
                pixelX = DEFAULT_PIXEL_X;
                size = new Dimension();
                size.setSize((pixelX * max_row) + padding.getPaddingLeft() + padding.getPaddingRight(), (pixelY * max_tier) + padding.getPaddingTop() + padding.getPaddingBottom());
            }

            outImage = new BufferedImage((int) size.getWidth(),(int) size.getHeight(), BufferedImage.TYPE_INT_RGB);
            Graphics2D    g2d      = outImage.createGraphics();
            FontRenderContext frc  = g2d.getFontRenderContext();

            midX = size.width / 2;
            midY = size.height / 2;
            mediaWidth = size.width - padding.getPaddingLeft() - padding.getPaddingRight();
            mediaHeight = size.height - padding.getPaddingTop() - padding.getPaddingBottom();

            pixelX = (mediaWidth - 80) / max_row;
            pixelY = (mediaHeight - 60) / 2 / max_tier;

            g2d.setColor(Color.WHITE);
            g2d.fillRect(0, 0, size.width, size.height);
            g2d.setColor(Color.BLACK);

            if (pixelX > 0 && pixelY > 0){
                g2d.setComposite(AlphaComposite.Src);
                g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                try {
                    if (isLoad) {
                        List<Object[]> serviceContainersOnBay = serviceContLoadFacadeLocal.findServiceContLoadByBayLocationAsObjectArray(noPpkb, bay);
                        for (Object[] serviceContLoad:serviceContainersOnBay) {
                            Short row = ((Integer) serviceContLoad[4]).shortValue();
                            Short tier = ((Integer) serviceContLoad[5]).shortValue();
                            String position = tier >= startTierUp && tier <= endTierUp ? "up" : "bottom";
                            new ContainerBayMonitor(row, tier, position, serviceContLoad).draw(g2d, null);
                        }
                        List<Object[]> serviceShiftings = serviceShiftingFacadeLocal.findServiceShiftingByBayLocationAsObjectArray(noPpkb, bay);
                        for (Object[] serviceShifting:serviceShiftings) {
                            Short row = ((Integer) serviceShifting[4]).shortValue();
                            Short tier = ((Integer) serviceShifting[5]).shortValue();
                            String position = tier >= startTierUp && tier <= endTierUp ? "up" : "bottom";
                            new ContainerBayMonitor(row, tier, position, serviceShifting).draw(g2d, null);
                        }
                    }
                    for (Object[] d: bayDetail){
                        if ((d[3].toString().equals("up") && isUpActive) || (d[3].toString().equals("bottom") && isBottomActive))
                            for (int i = (Integer)d[2];i <= ((((Integer)d[1] - 1) * 2) + (Integer)d[2]);i+=2){
                                new ContainerBayMonitor((Integer) d[0], i, d[3].toString(), null).draw(g2d, null);
                            }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                g2d.setColor(Color.BLACK);

                if (isUpActive) {
                    for (int i = startRowUp;i < (rowUp + startRowUp);i++){
                        tl = new TextLayout(String.format("%02d", i), new Font("Helvetica",Font.PLAIN, 11), frc);
                        tl.draw(g2d, (float) (translateRowPosition(i, "up") + (pixelX / 2) - (tl.getBounds().getWidth() / 2)), midY - 7);
                    }

                    for (int i = startTierUp;i < endTierUp;i+=2){
                        tl = new TextLayout(String.valueOf(i), new Font("Helvetica",Font.PLAIN, 11), frc);
                        tl.draw(g2d, padding.getPaddingLeft() + 10, translateTierPosition(i, "up") + (pixelY / 2) + ((int) tl.getBounds().getHeight() / 2));
                    }
                }

                if (isBottomActive) {
                    for (int i = startRowBottom;i < (rowBottom + startRowBottom);i++){
                        tl = new TextLayout(String.format("%02d", i), new Font("Helvetica",Font.PLAIN, 11), frc);
                        tl.draw(g2d, (float) (translateRowPosition(i, "bottom") + (pixelX / 2) - (tl.getBounds().getWidth() / 2)), size.height - padding.getPaddingBottom() - 7);
                    }

                    for (int i = startTierBottom;i < endTierBottom;i+=2){
                        tl = new TextLayout(String.format("%02d", i), new Font("Helvetica",Font.PLAIN, 11), frc);
                        tl.draw(g2d, padding.getPaddingLeft() + 10, translateTierPosition(i, "bottom") + (pixelY / 2) + ((int) tl.getBounds().getHeight() / 2));
                    }
                }

                if (noPpkb != null && isLoad) {
                    g2d.setColor(Color.BLUE);
                    List<Object[]> grosses;
                    
                    if (isBottomActive) {
                        grosses = serviceContLoadFacadeLocal.findGrossCapacityByNoPpkbAndBay(noPpkb, (short) bay, startRowBottom, rowBottom - (1 - startRowBottom), startTierBottom, endTierBottom);
                        for (Object[] gross: grosses){
                            tl = new TextLayout(((Integer) gross[1]).toString(), new Font("Helvetica",Font.PLAIN, 11), frc);
                            tl.draw(g2d, (float) (translateRowPosition((Integer) gross[0], "bottom") + (pixelX / 2) - (tl.getBounds().getWidth() / 2)), (int) size.getHeight() - padding.getPaddingBottom() + 10);
                        }
                    }

                    if (isUpActive) {
                        grosses = serviceContLoadFacadeLocal.findGrossCapacityByNoPpkbAndBay(noPpkb, (short) bay, startRowUp, rowUp - (1 - startRowUp), startTierUp, endTierUp);
                        g2d.setColor(Color.BLUE);
                        for (Object[] gross: grosses){
                            tl = new TextLayout(((Integer) gross[1]).toString(), new Font("Helvetica",Font.PLAIN, 11), frc);
                            tl.draw(g2d, (float) (translateRowPosition((Integer) gross[0], "up") + (pixelX / 2) - (tl.getBounds().getWidth() / 2)), midY + 10);
                        }
                    }

                    g2d.setColor(Color.BLACK);
                }

                tl = new TextLayout("BAY " + bay, new Font("Helvetica",Font.PLAIN, 14), frc);
                tl.draw(g2d, padding.getPaddingLeft(), midY - (pixelY * tierUp) - 30);
            }
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            ImageIO.write(outImage, "PNG", baos);
            return new SerialBlob(baos.toByteArray());
        } catch (IOException ex) {
            Logger.getLogger(BerthSchedule.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SerialException ex) {
            Logger.getLogger(BerthSchedule.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(BerthSchedule.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }


    public static Integer translateRowPosition(int cont_row, String cont_position){
        double x = 0;
        int actual_x = 0;
        int side_x = 0;

        if (cont_row % 2 ==0)
            side_x = -1;
        else
            side_x = 1;

        double additional_space_x = 0;
        if (cont_position.equals("up")) {
            if (cont_row % 2 != 0) {
                if (rowUp % 2 == 0)
                    additional_space_x += 1;
            } else if(cont_row % 2 == 0) {
                if (rowUp % 2 != 0)
                    additional_space_x += 1;
            }
            if (rowUp % 2 != 0){
                if (cont_row % 2 != 0)
                    additional_space_x += 0.5;
                else{
                    if (startRowUp != 0)
                        additional_space_x += 0.5;
                    else
                        additional_space_x += -0.5;
                }
            }
            else if (rowUp % 2 == 0){
                if (cont_row % 2 == 0)
                    additional_space_x += 1;
                else{
                    if (startRowUp != 0)
                        additional_space_x += 0;
                    else
                        additional_space_x += -1;
                }
            }
            additional_space_x-=ShipProfileBayMonitor.startRowUp;
        } else if(cont_position.equals("bottom")) {
            if (cont_row % 2 != 0) {
                additional_space_x = 0;
            } else if(cont_row % 2 == 0) {
                if (rowBottom % 2 != 0)
                    additional_space_x = 1;
            }
            if (rowBottom % 2 != 0){
                if (cont_row % 2 == 1)
                    additional_space_x += 0.5;
                else
                    if (startRowBottom != 0)
                        additional_space_x += 0.5;
                    else
                        additional_space_x += -0.5;
            }
            else if (rowBottom % 2 == 0){
                if (cont_row % 2 == 0)
                    additional_space_x += 1;
                else{
                    if (startRowBottom != 0)
                        additional_space_x += 1;
                    else
                        additional_space_x += 0;
                }
            }
            additional_space_x-=ShipProfileBayMonitor.startRowBottom;
        }
        x = (int) Math.ceil(cont_row / 2) + additional_space_x;
        actual_x = (int) (ShipProfileBayMonitor.midX + ((x * ShipProfileBayMonitor.pixelX) * side_x));

        return actual_x;
    }

    public static Integer translateTierPosition(double cont_tier, String cont_position){
        double y = 0;
        int actual_y = 0;
        
        if (cont_position.equals("up")) {
            y = (((cont_tier - ShipProfileBayMonitor.startTierUp) / 2) + 1);
            actual_y = ShipProfileBayMonitor.midY - (int) (y * ShipProfileBayMonitor.pixelY) - 20;
        } else if(cont_position.equals("bottom")) {
            y = (((ShipProfileBayMonitor.endTierBottom - cont_tier) / 2));
            actual_y = ShipProfileBayMonitor.midY + (int) (y * ShipProfileBayMonitor.pixelY) + 20;
        }
        return actual_y;
    }
}