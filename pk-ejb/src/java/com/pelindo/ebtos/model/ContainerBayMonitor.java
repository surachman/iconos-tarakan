/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.model;

import com.pelindo.ebtos.ejb.ShipProfileBayMonitor;
import com.pelindo.ebtos.ejb.facade.local.BaplieDischargeFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.ServiceContLoadFacadeLocal;
import com.qtasnim.graphic.BoxPattern;
import com.qtasnim.graphic.IBoxPatternable;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.io.Serializable;
import javax.ejb.EJB;

/**
 *
 * @author Eka
 */
public class ContainerBayMonitor implements Drawable, Serializable{
    @EJB ServiceContLoadFacadeLocal serviceContLoadFacadeLocal;
    @EJB BaplieDischargeFacadeLocal baplieDischargeFacade;
    private int cont_tier, cont_row, margin_bottom=5, margin_side=5;
    private String cont_position;
    private Object[] serviceContLoad;

    public ContainerBayMonitor(int cont_row, int cont_tier, String cont_position, Object[] serviceContLoad) {
        this.cont_tier = cont_tier;
        this.cont_row = cont_row;
        this.cont_position = cont_position;
        this.serviceContLoad = serviceContLoad;
    }

    @Override
    public void draw(Graphics2D g2d, Color color) {
        FontRenderContext frc = g2d.getFontRenderContext();
        TextLayout tl;

        int x = ShipProfileBayMonitor.translateRowPosition(cont_row, cont_position);
        int y = ShipProfileBayMonitor.translateTierPosition(cont_tier, cont_position);
        if (ShipProfileBayMonitor.noPpkb != null){
            Object[] cont;
            if (serviceContLoad != null) {
                Color c = g2d.getColor();
                String isCrossed = (String) serviceContLoad[6];
                System.out.println("Cobaaaa : "+ serviceContLoad[6]);
                g2d.setColor(Color.CYAN);
                g2d.fillRect(x, y, ShipProfileBayMonitor.pixelX, ShipProfileBayMonitor.pixelY);
                g2d.setColor(c);

                if (isCrossed.equals("TRUE")) {
                    g2d.setColor(Color.DARK_GRAY);
                    IBoxPatternable patternBox = BoxPattern.CROSS_CORNER;
                    patternBox.draw(x, y, ShipProfileBayMonitor.pixelX, ShipProfileBayMonitor.pixelY, g2d);
                    g2d.setColor(c);
                }

                String contNo = (String) serviceContLoad[0];

                tl = new TextLayout(contNo.substring(0, 4), new Font("Helvetica",Font.BOLD, 15), frc);
                float height = (float) (y + tl.getBounds().getHeight() + margin_bottom);
                tl.draw(g2d, x + margin_side, height);
                tl = new TextLayout(contNo.substring(4), new Font("Helvetica",Font.BOLD, 15), frc);
                height = (float) (height + tl.getBounds().getHeight() + margin_bottom);
                tl.draw(g2d, x + margin_side, height);
                tl = new TextLayout((String) serviceContLoad[8], new Font("Helvetica",Font.BOLD, 15), frc);
                height = (float) (height + tl.getBounds().getHeight() + margin_bottom);
                tl.draw(g2d, x + margin_side, height);

                tl = new TextLayout((String) serviceContLoad[7], new Font("Helvetica",Font.BOLD, 15), frc);
                height = (float) (y + tl.getBounds().getHeight() + margin_bottom);
                tl.draw(g2d, (float) (x + ShipProfileBayMonitor.pixelX - tl.getBounds().getWidth() - margin_side), height);
                tl = new TextLayout(((Integer) serviceContLoad[2]).toString(), new Font("Helvetica",Font.BOLD, 13), frc);
                height = (float) (height + tl.getBounds().getHeight() + margin_bottom);
                tl.draw(g2d, (float) (x + ShipProfileBayMonitor.pixelX - tl.getBounds().getWidth() - margin_side), height);
                tl = new TextLayout((String) serviceContLoad[1], new Font("Helvetica",Font.BOLD, 15), frc);
                height = (float) (height + tl.getBounds().getHeight() + margin_bottom);
                tl.draw(g2d, (float) (x + ShipProfileBayMonitor.pixelX - tl.getBounds().getWidth() - margin_side), height);
            }

            if (!ShipProfileBayMonitor.isLoad) {
                cont = baplieDischargeFacade.findContOnBay(ShipProfileBayMonitor.noPpkb, ShipProfileBayMonitor.bay, cont_row, cont_tier);
                if (cont != null){
                    Color c = g2d.getColor();
                    g2d.setColor(Color.CYAN);
                    g2d.fillRect(x, y, ShipProfileBayMonitor.pixelX, ShipProfileBayMonitor.pixelY);
                    g2d.setColor(c);
                    tl = new TextLayout(cont[0].toString(), new Font("Helvetica",Font.BOLD, 10), frc);
                    tl.draw(g2d, x + margin_side, (float) (y + tl.getBounds().getHeight()) + margin_bottom);
                    tl = new TextLayout(cont[1].toString() + "\" " + cont[2].toString(), new Font("Helvetica",Font.PLAIN, 9), frc);
                    tl.draw(g2d, (float) (x + ShipProfileBayMonitor.pixelX - tl.getBounds().getWidth() - margin_side), y + ShipProfileBayMonitor.pixelY - margin_bottom);
                }
            }
        }

        if (serviceContLoad == null) {
            g2d.drawRect(x, y, ShipProfileBayMonitor.pixelX, ShipProfileBayMonitor.pixelY);
        }
    }


}
