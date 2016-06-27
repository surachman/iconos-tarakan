/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb;

import com.qtasnim.util.Padding;
import com.pelindo.ebtos.ejb.facade.local.MasterDockFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.PlanningVesselFacadeLocal;
import com.pelindo.ebtos.ejb.remote.VesselScheduleVisualRemote;
import com.pelindo.ebtos.model.db.master.MasterDock;
import com.pelindo.ebtos.model.vesselplan.Block;
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
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
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
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
 * @author senoanggoro
 */
@Stateless
public class VesselScheduleVisual implements Serializable, VesselScheduleVisualRemote {
    @EJB
    private PlanningVesselFacadeLocal planningVesselFacadeLocal;
    @EJB
    private MasterDockFacadeLocal masterDockFacadeLocal;

    private static final Integer MINIMUM_MEDIA_HEIGHT = 500;
    private static final Integer MINIMUM_Y_HEIGHT = 50;
    public static Padding padding = new Padding(30, 30, 30, 50);
    public static Dimension size = new Dimension(1000,500);
    public static Date yBoundMin, yBoundMax;
    public static int pixelX, pixelY;
    private String dock_code;

    @Override
    public void init(String dock_code, Integer w, Integer h, Date yBoundMin, Date yBoundMax){
        if (h == null) {
            Calendar cal1 = Calendar.getInstance();
            Calendar cal2 = Calendar.getInstance();
            cal1.setTime(yBoundMin);
            cal2.setTime(yBoundMax);
            Integer range = Math.abs((cal2.get(Calendar.DAY_OF_YEAR) + ((Math.abs(cal2.get(Calendar.YEAR) - cal1.get(Calendar.YEAR))) * cal2.getActualMaximum(Calendar.DAY_OF_YEAR))) - cal1.get(Calendar.DAY_OF_YEAR));
            int mediaHeight = MINIMUM_Y_HEIGHT * range;

            if (mediaHeight < MINIMUM_MEDIA_HEIGHT) {
                mediaHeight = MINIMUM_MEDIA_HEIGHT;
            }

            size.setSize(w, mediaHeight);
        } else {
            size.setSize(w, h);
        }

        this.dock_code = dock_code;
        VesselScheduleVisual.yBoundMin = yBoundMin;
        VesselScheduleVisual.yBoundMax = yBoundMax;
    }

    @Override
    public Blob generate(){
        BufferedImage outImage = new BufferedImage((int) size.getWidth(),(int) size.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D    g2d      = outImage.createGraphics();
        FontRenderContext frc  = g2d.getFontRenderContext();
        TextLayout tl;
        MasterDock dock = masterDockFacadeLocal.find(dock_code);

        int x0 = padding.getPaddingLeft();
        int y0 = size.height - padding.getPaddingBottom();
        int dockWidth = (int) Math.ceil((dock.getToMeter() - dock.getFrMeter()) / 10);

        pixelY = (int) ((size.height - (padding.getPaddingTop() + padding.getPaddingBottom())) / ((yBoundMax.getTime() - yBoundMin.getTime()) / (1000 * 60 * 60 * 24) + 2));
        pixelX = (size.width - (padding.getPaddingLeft() + padding.getPaddingRight())) / dockWidth;

        if (pixelX > 1 && pixelY > 1){
            // ==== set image quality ==== //
            g2d.setComposite(AlphaComposite.Src);
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // ==== end ==== //
            g2d.setColor(Color.LIGHT_GRAY);
            g2d.fillRect(0, 0, (int)size.width, (int)size.height);
            g2d.setColor(Color.BLACK);

            g2d.setStroke(new BasicStroke(2.0f));

            List<Object[]> vesselSchedules = planningVesselFacadeLocal.findVesselScheduleByDateRangeAndDock(yBoundMin, yBoundMax, dock_code);

            for (Object[] vesselSchedule: vesselSchedules) {
                Block b = new Block((String) vesselSchedule[0],
                        (String) vesselSchedule[5],
                        ((BigDecimal) vesselSchedule[1]).intValue(),
                        ((BigDecimal) vesselSchedule[2]).intValue(),
                        (Date) vesselSchedule[3],
                        (Date) vesselSchedule[4], false);
                b.draw(g2d);
            }

            g2d.setColor(Color.BLACK);

            //draw media (x,y)
            g2d.drawLine(padding.getPaddingLeft(), (int)size.height - padding.getPaddingBottom(), (int)size.width - padding.getPaddingRight(), (int)size.height - padding.getPaddingBottom());
            g2d.drawLine(padding.getPaddingLeft(), padding.getPaddingTop(), padding.getPaddingLeft(), (int)size.height - padding.getPaddingBottom());

            //set x's value
            int index = 0;
            for (int i = x0 ; i < size.width - padding.getPaddingRight(); i += pixelX) {
                g2d.drawLine(i, (int)size.height - padding.getPaddingBottom() - 2, i, (int)size.height - padding.getPaddingBottom());
                tl = new TextLayout(String.valueOf(String.valueOf(index++ * 10)), new Font("Helvetica",Font.BOLD, 9), frc);
                tl.draw(g2d, (int) (i - (tl.getBounds().getWidth() / 2)), (int)size.height - padding.getPaddingBottom() + 10);
            }

            //set y's value
            Calendar c = Calendar.getInstance();
            c.setTime(yBoundMin);
            String prevMonth = "";

            for (int i = y0; i > padding.getPaddingTop(); i -= pixelY) {
                g2d.drawLine(padding.getPaddingLeft(), i, padding.getPaddingLeft() + 2, i);

                if (i != y0) {
                    String date = String.valueOf(c.get(Calendar.DATE));
                    String month = String.valueOf(c.get(Calendar.MONTH) + 1);
                    String year = String.valueOf(c.get(Calendar.YEAR));
                    tl = new TextLayout(date, new Font("Helvetica",Font.BOLD, 10), frc);
                    double dateTextWidth = tl.getBounds().getWidth();
                    tl.draw(g2d, padding.getPaddingLeft() - 5 - (int)(dateTextWidth), i + (int)(tl.getBounds().getHeight() / 2));

                    if (!month.equals(prevMonth)) {
                        tl = new TextLayout(year.substring(2) + "." + month, new Font("Helvetica",Font.BOLD, 11), frc);
                        tl.draw(g2d, padding.getPaddingLeft() - 5 - (int)(tl.getBounds().getWidth() + dateTextWidth + 5), i + (int)(tl.getBounds().getHeight() / 2));
                        prevMonth = month;
                        g2d.setColor(Color.BLACK);
                    }
                    
                    c.add(Calendar.DATE, 1);
                }
            }
        }

        g2d.dispose();
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
}

//lieur juragaaann :((