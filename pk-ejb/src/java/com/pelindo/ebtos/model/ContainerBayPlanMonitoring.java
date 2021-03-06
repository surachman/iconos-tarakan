/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.model;

import com.qtasnim.graphic.BoxPattern;
import com.qtasnim.graphic.GraphicHelper;
import com.qtasnim.graphic.IBoxPatternable;
import com.pelindo.ebtos.ejb.CYViewAbstract;
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.io.Serializable;

/**
 *
 * @author senoanggoro
 */
public class ContainerBayPlanMonitoring implements Drawable, Serializable {
    public static final int BIRD_VIEW = 0;
    public static final int SIDE_VIEW = 1;

    private String code, size, weight, status, noPpkb, pattern, pod;
    private int slot, row, tier, type, propX, propY, margin_bottom=5, margin_left=5;
    private TextLayout tl;
    private FontRenderContext frc;
    private Boolean isPlanned;
    private Boolean isOccupied;

    public ContainerBayPlanMonitoring(String code, String noPpkb, String status, String pod, String size, String weight, int slot, int row, int tier, int type, String pattern, Boolean isPlanned, Boolean isOccupied) {
        this.code = code;
        this.noPpkb = noPpkb;
        this.size = size;
        this.weight = weight;
        this.slot = slot;
        this.pod = pod == null ? "" : pod;
        this.row = row;
        this.tier = tier;
        this.type = type;
        this.status = status;
        this.isPlanned = isPlanned;
        this.isOccupied = isOccupied;
        
        if (type == ContainerBayPlanMonitoring.BIRD_VIEW){
            propX = slot;
            propY = row;
        } else if (type == ContainerBayPlanMonitoring.SIDE_VIEW){
            propX = row;
            propY = tier;
        }
        this.pattern = pattern;
//        System.out.println(tes.isAssignableFrom(Container.class));
    }

// <editor-fold defaultstate="collapsed" desc="getter setter">
    public String getCode() {
        return code;
    }

    public int getRow() {
        return row;
    }

    public int getSlot() {
        return slot;
    }

    public int getTier() {
        return tier;
    }// </editor-fold>

    public void draw(Graphics2D g2d, Color color){
        Color c = g2d.getColor();

        frc = g2d.getFontRenderContext();
        
        int x = 0;
        int y = 0;
        if (type == ContainerBayPlanMonitoring.BIRD_VIEW){
            x = CYViewAbstract.media_width + CYViewAbstract.x0 - ((propX + (size.equals("40") && type == BIRD_VIEW ? 1 : 0)) * CYViewAbstract.pixel_x);
            y = (int) (CYViewAbstract.y0 - CYViewAbstract.media_height + ((propY - 1) * CYViewAbstract.pixel_y));
        } else if (type == ContainerBayPlanMonitoring.SIDE_VIEW){
            x = CYViewAbstract.x0 + ((propX - 1) * CYViewAbstract.pixel_x);
            y = (int) (CYViewAbstract.y0 - ((propY) * CYViewAbstract.pixel_y));
        }

        int width = CYViewAbstract.pixel_x;
        short teus = (short) Math.ceil((double) Short.parseShort(size) / (double) 20);
        
        if(type == BIRD_VIEW)
            width*=teus;

        if (isOccupied) {
            g2d.setColor(Color.BLACK);
            tl = new TextLayout("[ Occupied ]", new Font("Helvetica",Font.BOLD, 14), frc);
            float x_text = 0;

            if (type == BIRD_VIEW) {
                x_text = (float) (x + (CYViewAbstract.pixel_x * (Double.valueOf(size) / 40)) - (tl.getBounds().getWidth() / 2));
            } else {
                x_text = (float) (x + (CYViewAbstract.pixel_x / 2) - (tl.getBounds().getWidth() / 2));
            }

            float y_text = (float) (y + (((double)CYViewAbstract.pixel_y) / 2) + (tl.getBounds().getHeight() / 2));
            tl.draw(g2d, x_text, y_text);
            g2d.drawRect(x, y, width, CYViewAbstract.pixel_y);
        } else {
            if (!isPlanned)
                g2d.setColor(color);
            else{
                g2d.setComposite(makeComposite(.1f));
                g2d.setColor(Color.GRAY);
            }

            g2d.fillRect(x, y, width, CYViewAbstract.pixel_y);
            g2d.setComposite(makeComposite(1f));

            if (status.equals("planning")){
                g2d.setColor(Color.BLUE);
                tl = new TextLayout("[ planning ]", new Font("Helvetica",Font.BOLD, 14), frc);
                float x_text = 0;

                if (type == BIRD_VIEW) {
                    x_text = (float) (x + (CYViewAbstract.pixel_x * (Double.valueOf(size) / 40)) - (tl.getBounds().getWidth() / 2));
                } else {
                    x_text = (float) (x + (CYViewAbstract.pixel_x / 2) - (tl.getBounds().getWidth() / 2));
                }

                float y_text = (float) (y + (((double)CYViewAbstract.pixel_y) / 2) + (tl.getBounds().getHeight() / 2));
                tl.draw(g2d, x_text, y_text);
            }

            g2d.setColor(Color.BLACK);
            g2d.drawRect(x, y, width, CYViewAbstract.pixel_y);
            IBoxPatternable patternBox = BoxPattern.patternTable.get(pattern);

            if (patternBox != null) {
                AlphaComposite com = (AlphaComposite) g2d.getComposite();
                g2d.setComposite(GraphicHelper.makeComposite(.8f));
                Stroke stroke = g2d.getStroke();
                g2d.setStroke(new BasicStroke(1f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f));
                patternBox.draw(x, y, width, CYViewAbstract.pixel_y, g2d);
                g2d.setComposite(com);
                g2d.setStroke(stroke);
            }

            if (isPlanned) {
                g2d.setComposite(makeComposite(.1f));
            }

            tl = new TextLayout(code, new Font("Helvetica",Font.BOLD, 12), frc);
            tl.draw(g2d, x + margin_left, (float) (y + tl.getBounds().getHeight()) + margin_bottom);
            tl = new TextLayout(pod + " " + size + "\" " + weight, new Font("Helvetica",Font.BOLD, 9), frc);
            tl.draw(g2d, (float) (x + width - margin_left - tl.getBounds().getWidth()), (float) (y + CYViewAbstract.pixel_y - margin_bottom));

            if (noPpkb != null) {
                tl = new TextLayout(noPpkb, new Font("Helvetica",Font.BOLD, 9), frc);
                tl.draw(g2d, x + margin_left, (float) (y + CYViewAbstract.pixel_y - margin_bottom));
            }
        }

        g2d.setComposite(makeComposite(1f));

        g2d.setColor(c);
    }

    private AlphaComposite makeComposite(float alpha) {
        int type = AlphaComposite.SRC_OVER;
        return(AlphaComposite.getInstance(type, alpha));
    }
}
