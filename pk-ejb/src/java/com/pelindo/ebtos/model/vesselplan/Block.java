package com.pelindo.ebtos.model.vesselplan;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



import com.pelindo.ebtos.ejb.VesselScheduleVisual;
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 *
 * @author senoanggoro
 */

public class Block implements Serializable{
    public static final int TEXT_MARGIN_TOP = 3;
    public static final int DEFAULT_FONT_SIZE = 10;

    private String noPpkb, vesselName;
    private int frMeter, toMeter;
    private Date eta, etd;
    private boolean conflict;
    private int x1, x2, y1, y2;

    private int meterSpace = 5; //meters
    private int timeSpace = 10; //minutes

    private Color[][] colors = {
        {new Color(90, 195, 255), Color.BLUE},
        {new Color(237, 107, 141), Color.RED}
    };

    public Block(String noPpkb, String vesselName, int frMeter, int toMeter, Date eta, Date etd, boolean conflict){
        this.noPpkb = noPpkb;
        this.vesselName = vesselName;
        this.frMeter = frMeter;
        this.toMeter = toMeter;
        this.eta = eta;
        this.etd = etd;
        this.conflict = conflict;
        x1 = (int) ((frMeter / 10.0 * VesselScheduleVisual.pixelX) + VesselScheduleVisual.padding.getPaddingLeft());
        x2 = (int) ((toMeter / 10.0 * VesselScheduleVisual.pixelX) + VesselScheduleVisual.padding.getPaddingLeft());
//        int y1 = (int) (size.height - BerthWindow.padding.getPaddingBottom() - (((double) eta.getDate() + ((double) eta.getHours() / 24)) * pixelY));
//        int y2 = (int) (size.height - BerthWindow.padding.getPaddingBottom() - (((double) etd.getDate() + ((double) etd.getHours() / 24)) * pixelY));

        y1 = (int) (VesselScheduleVisual.size.height - VesselScheduleVisual.padding.getPaddingBottom() - ((((eta.getTime() - VesselScheduleVisual.yBoundMin.getTime()) / (double)(1000 * 60 * 60 * 24)) + 1) * VesselScheduleVisual.pixelY));
        y2 = (int) (VesselScheduleVisual.size.height - VesselScheduleVisual.padding.getPaddingBottom() - ((((etd.getTime() - VesselScheduleVisual.yBoundMin.getTime()) / (double)(1000 * 60 * 60 * 24)) + 1) * VesselScheduleVisual.pixelY));
        if (y1 > VesselScheduleVisual.size.height - VesselScheduleVisual.padding.getPaddingBottom())
            y1 = VesselScheduleVisual.size.height - VesselScheduleVisual.padding.getPaddingBottom() - 1;
        if (y2 < VesselScheduleVisual.padding.getPaddingTop())
            y2 = VesselScheduleVisual.padding.getPaddingTop();
    }

    public void draw (Graphics2D g2d){
        FontRenderContext frc = g2d.getFontRenderContext();
        TextLayout tl;
        Color[] color;

        if (conflict) {
            color = colors[1];
            g2d.setComposite(makeComposite(0.6f));
        } else {
            color = colors[0];
        }

        g2d.setColor(color[0]);

        g2d.setComposite(makeComposite(0.4f));
        g2d.fillRect(getX1(), getY2(), getX2() - getX1(), getY1() - getY2());
        
        if (conflict){
            g2d.fillRect((int) (getX1() - ((meterSpace / 10.0) * VesselScheduleVisual.pixelX)), getY2(), (int) (meterSpace / 10.0 * VesselScheduleVisual.pixelX), getY1() - getY2());
            g2d.fillRect(getX2(), getY2(), (int) (meterSpace / 10.0 * VesselScheduleVisual.pixelX), getY1() - getY2());
            g2d.setStroke(new BasicStroke(0.5f));
//            g2d.setColor(color[1]);
//            g2d.drawRect((int) (x1 - ((meterSpace / 10.0) * pixelX)), y2, (int) (meterSpace / 10.0 * pixelX), y1 - y2 - 1);
//            g2d.drawRect(x2, y2, (int) (meterSpace / 10.0 * pixelX), y1 - y2 - 1);
            //end draw meter space arround
        }

        g2d.setColor(color[1]);
        g2d.setComposite(makeComposite(1f));
        float dash[] = {4.0f, 2.0f};
        g2d.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f));
        //g2d.drawRect(x1, y2 + 1, x2 - x1 - 1, y1 - y2 - 3);
        g2d.drawLine(getX1(), getY1() - 2, getX1(), getY2() + 1);
        g2d.drawLine(getX2(), getY1() - 2, getX2(), getY2() + 1);
        int shipWidth = getY1() - getY2();

        if (shipWidth > ShipShape.DEFAULT_WIDTH) {
            shipWidth = ShipShape.DEFAULT_WIDTH;
        }

        new ShipShape(getX1(), getY1() - shipWidth, getX2() - getX1(), shipWidth - 1).draw(g2d, color);

        
//        int font_size = DEFAULT_FONT_SIZE;
//        do {
//          tl = new TextLayout(getVesselName(), new Font("Helvetica",Font.BOLD, font_size), frc);
//          font_size--;
//        } while (tl.getBounds().getWidth() > getX2() - getX1());

        g2d.setColor(new Color(50,50,50));
        tl = new TextLayout("(voyage) " + getNoPpkb() + " - " + getVesselName(), new Font("Helvetica",Font.BOLD, 8), frc);
        float startTextPoint = (float) (getY1() - ShipShape.DEFAULT_WIDTH + tl.getBounds().getHeight() + TEXT_MARGIN_TOP);
        tl.draw(g2d, (float) (getX2() - tl.getBounds().getWidth() - 2), startTextPoint);
        tl = new TextLayout("(dg meter) " + frMeter + " - " + toMeter, new Font("Helvetica",Font.PLAIN, 8), frc);
        startTextPoint = (float) (startTextPoint + tl.getBounds().getHeight() + TEXT_MARGIN_TOP);
        tl.draw(g2d, (float) (getX2() - tl.getBounds().getWidth() - 2), startTextPoint);
        tl = new TextLayout("(eta) " + eta.toString(), new Font("Helvetica",Font.PLAIN, 8), frc);
        startTextPoint = (float) (startTextPoint + tl.getBounds().getHeight() + TEXT_MARGIN_TOP);
        tl.draw(g2d, (float) (getX2() - tl.getBounds().getWidth() - 2), startTextPoint);
        tl = new TextLayout("(etd) " + etd.toString(), new Font("Helvetica",Font.PLAIN, 8), frc);
        startTextPoint = (float) (startTextPoint + tl.getBounds().getHeight() + TEXT_MARGIN_TOP);
        tl.draw(g2d, (float) (getX2() - tl.getBounds().getWidth() - 2), startTextPoint);
    }

    private AlphaComposite makeComposite(float alpha) {
        int type = AlphaComposite.SRC_OVER;
        return(AlphaComposite.getInstance(type, alpha));
    }

    /**
     * @return the noPpkb
     */
    public String getNoPpkb() {
        return noPpkb;
    }

    /**
     * @return the vesselName
     */
    public String getVesselName() {
        return vesselName;
    }

    /**
     * @return the x1
     */
    public int getX1() {
        return x1;
    }

    /**
     * @return the x2
     */
    public int getX2() {
        return x2;
    }

    /**
     * @return the y1
     */
    public int getY1() {
        return y1;
    }

    /**
     * @return the y2
     */
    public int getY2() {
        return y2;
    }
}