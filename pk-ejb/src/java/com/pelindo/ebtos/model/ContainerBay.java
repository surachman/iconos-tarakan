/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.model;

import com.pelindo.ebtos.ejb.ShipProfile;
import com.pelindo.ebtos.ejb.facade.local.BaplieDischargeFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.PlanningContLoadFacadeLocal;
import com.pelindo.ebtos.model.db.PlanningContLoad;
import com.qtasnim.graphic.BoxPattern;
import com.qtasnim.graphic.IBoxPatternable;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.Serializable;

/**
 *
 * @author senoanggoro
 */
public class ContainerBay implements Drawable, Serializable{
    PlanningContLoadFacadeLocal planningContLoadFacade = lookupPlanningContLoadFacadeLocal();
    BaplieDischargeFacadeLocal baplieDischargeFacade = lookupBaplieDischargeFacadeLocal();
    private int cont_tier, cont_row, margin_bottom=5, margin_side=5;
    private String cont_position;
    private Object[] planningContLoad;

    public ContainerBay(int cont_row, int cont_tier, String cont_position, Object[] planningContLoad) {
        this.cont_tier = cont_tier;
        this.cont_row = cont_row;
        this.cont_position = cont_position;
        this.planningContLoad = planningContLoad;
    }

    public void draw(Graphics2D g2d, Color color) {
        FontRenderContext frc = g2d.getFontRenderContext();
        TextLayout tl;

        int x = ShipProfile.translateRowPosition(cont_row, cont_position);
        int y = ShipProfile.translateTierPosition(cont_tier, cont_position);

        if (ShipProfile.noPpkb != null){
            Object[] cont;
            if (planningContLoad != null) {
                Color c = g2d.getColor();
                Boolean isCrossed = (Boolean) planningContLoad[6];

                g2d.setColor(Color.CYAN);
                g2d.fillRect(x, y, ShipProfile.pixelX, ShipProfile.pixelY);
                g2d.setColor(c);

                if (isCrossed) {
                    g2d.setColor(Color.DARK_GRAY);
                    IBoxPatternable patternBox = BoxPattern.CROSS_CORNER;
                    patternBox.draw(x, y, ShipProfile.pixelX, ShipProfile.pixelY, g2d);
                    g2d.setColor(c);
                }

                String contNo = (String) planningContLoad[0];

                tl = new TextLayout(contNo.substring(0, 4), new Font("Helvetica",Font.BOLD, 15), frc);
                float height = (float) (y + tl.getBounds().getHeight() + margin_bottom);
                tl.draw(g2d, x + margin_side, height);
                tl = new TextLayout(contNo.substring(4), new Font("Helvetica",Font.BOLD, 15), frc);
                height = (float) (height + tl.getBounds().getHeight() + margin_bottom);
                tl.draw(g2d, x + margin_side, height);
                tl = new TextLayout((String) planningContLoad[8], new Font("Helvetica",Font.BOLD, 15), frc);
                height = (float) (height + tl.getBounds().getHeight() + margin_bottom);
                tl.draw(g2d, x + margin_side, height);

                tl = new TextLayout((String) planningContLoad[7], new Font("Helvetica",Font.BOLD, 15), frc);
                height = (float) (y + tl.getBounds().getHeight() + margin_bottom);
                tl.draw(g2d, (float) (x + ShipProfile.pixelX - tl.getBounds().getWidth() - margin_side), height);
                tl = new TextLayout(((Integer) planningContLoad[2]).toString(), new Font("Helvetica",Font.BOLD, 13), frc);
                height = (float) (height + tl.getBounds().getHeight() + margin_bottom);
                tl.draw(g2d, (float) (x + ShipProfile.pixelX - tl.getBounds().getWidth() - margin_side), height);
                tl = new TextLayout((String) planningContLoad[1], new Font("Helvetica",Font.BOLD, 15), frc);
                height = (float) (height + tl.getBounds().getHeight() + margin_bottom);
                tl.draw(g2d, (float) (x + ShipProfile.pixelX - tl.getBounds().getWidth() - margin_side), height);

//                tl = new TextLayout((String) planningContLoad[1] + " " + (Integer) planningContLoad[2], new Font("Helvetica",Font.PLAIN, 9), frc);
//                tl.draw(g2d, (float) (x + ShipProfile.pixelX - tl.getBounds().getWidth() - margin_side), y + ShipProfile.pixelY - margin_bottom);
            }

            if (!ShipProfile.isLoad) {
                cont = baplieDischargeFacade.findContOnBay(ShipProfile.noPpkb, ShipProfile.bay, cont_row, cont_tier);
                if (cont != null){
                    Color c = g2d.getColor();
                    g2d.setColor(Color.CYAN);
                    g2d.fillRect(x, y, ShipProfile.pixelX, ShipProfile.pixelY);
                    g2d.setColor(c);
                    tl = new TextLayout(cont[0].toString(), new Font("Helvetica",Font.BOLD, 10), frc);
                    tl.draw(g2d, x + margin_side, (float) (y + tl.getBounds().getHeight()) + margin_bottom);
                    tl = new TextLayout(cont[1].toString() + "\" " + cont[2].toString(), new Font("Helvetica",Font.PLAIN, 9), frc);
                    tl.draw(g2d, (float) (x + ShipProfile.pixelX - tl.getBounds().getWidth() - margin_side), y + ShipProfile.pixelY - margin_bottom);
                }
            }
        }

        if (planningContLoad == null) {
            g2d.drawRect(x, y, ShipProfile.pixelX, ShipProfile.pixelY);
        }
    }

    private BaplieDischargeFacadeLocal lookupBaplieDischargeFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (BaplieDischargeFacadeLocal) c.lookup("java:global/pkproject/pk-ejb/BaplieDischargeFacade!com.pelindo.ebtos.ejb.facade.local.BaplieDischargeFacadeLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private PlanningContLoadFacadeLocal lookupPlanningContLoadFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (PlanningContLoadFacadeLocal) c.lookup("java:global/pkproject/pk-ejb/PlanningContLoadFacade!com.pelindo.ebtos.ejb.facade.local.PlanningContLoadFacadeLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }


}
