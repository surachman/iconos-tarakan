package com.pelindo.ebtos.model.vesselplan;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.GeneralPath;

/**
 *
 * @author senoanggoro
 */
public class ShipShape{
    public static final int DEFAULT_WIDTH = 45;

    private int x,y,w,h;

    public ShipShape(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }
    
    public void draw (Graphics2D g2, Color[] color){
        GeneralPath p = new GeneralPath(GeneralPath.WIND_EVEN_ODD);

        g2.setStroke(new BasicStroke(1.5f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f));
        //g2.setColor(color);
        p.moveTo(x + (w * 0.15f), y);
        p.quadTo(x - (w * 0.15f), y + (h/2), x + (w * 0.15f), y + h);
        p.lineTo(x + (w * 1f), y + h);
        p.lineTo(x + (w * 1f), y);
        p.lineTo(x + (w * 0.15f), y);

        g2.setColor(color[0]);
        g2.fill(p);
        g2.setColor(color[1]);
        g2.draw(p);
    }
}

//lieur juragaaann :((