/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.model;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.GeneralPath;
import java.io.Serializable;

/**
 *
 * @author senoanggoro
 */
public class ShipShape implements Serializable{
    public static final int DEFAULT_WIDTH = 50;
    public static final int DEFAULT_FONT_SIZE = 10;
    private static final int DEFAULT_TEXT_MARGIN = 6;

    private int x,y,w,h;
    private boolean approval;
    private String vesselName;
    private String noPpkb;
    private String estimationDate;
    private String meterAllocation;

    public ShipShape(String noPpkb, String vesselName, String estimationDate, String meterAllocation, int x, int y, int w, int h, boolean isApprove) {
        this.noPpkb = noPpkb;
        this.vesselName = vesselName;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.approval = isApprove;
        this.estimationDate = estimationDate;
        this.meterAllocation = meterAllocation;
    }

    public void draw (Graphics2D g2, Color[] color){
        GeneralPath p = new GeneralPath(GeneralPath.WIND_EVEN_ODD);
        FontRenderContext frc = g2.getFontRenderContext();
        g2.setStroke(new BasicStroke(1.5f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f));

        p.moveTo(x + (w * 0.15f), y);
        p.quadTo(x - (w * 0.15f), y + (h/2), x + (w * 0.15f), y + h);
        p.lineTo(x + (w * 1f), y + h);
        p.lineTo(x + (w * 1f), y);
        p.lineTo(x + (w * 0.15f), y);

        if (approval){
            g2.setComposite(makeComposite(.6f));
        }
        
        g2.setColor(color[0]);
        g2.fill(p);
        g2.setColor(color[1]);
        g2.draw(p);
        int font_size = DEFAULT_FONT_SIZE;

        TextLayout tl1, tl2, tl3;
        do {
            tl1 = new TextLayout(noPpkb + " - " + vesselName, new Font("Helvetica",Font.BOLD, font_size), frc);
            font_size--;
        } while (tl1.getBounds().getWidth() > w);

        font_size = DEFAULT_FONT_SIZE;
        do {
            tl2 = new TextLayout(estimationDate, new Font("Helvetica",Font.BOLD, font_size), frc);
            font_size--;
        } while (tl2.getBounds().getWidth() > w);

        font_size = DEFAULT_FONT_SIZE;
        do {
            tl3 = new TextLayout(meterAllocation, new Font("Helvetica",Font.BOLD, font_size), frc);
            font_size--;
        } while (tl3.getBounds().getWidth() > w);

        int xPosition = y + (h / 2) - ((int) ((tl1.getBounds().getHeight() + tl2.getBounds().getHeight() + tl3.getBounds().getHeight() + (DEFAULT_TEXT_MARGIN * 2)) / 2));

        xPosition += tl1.getBounds().getHeight();
        tl1.draw(g2, (float) (x + w - tl1.getBounds().getWidth() - 4), xPosition);
        xPosition += DEFAULT_TEXT_MARGIN + tl1.getBounds().getHeight();
        tl2.draw(g2, (float) (x + w - tl2.getBounds().getWidth() - 4), xPosition);
        xPosition += DEFAULT_TEXT_MARGIN + tl2.getBounds().getHeight();
        tl3.draw(g2, (float) (x + w - tl3.getBounds().getWidth() - 4), xPosition);
        
        if (approval){
            g2.setComposite(makeComposite(1f));
        }
    }

    private AlphaComposite makeComposite(float alpha) {
        int type = AlphaComposite.SRC_OVER;
        return(AlphaComposite.getInstance(type, alpha));
    }

}

//lieur juragaaann :((