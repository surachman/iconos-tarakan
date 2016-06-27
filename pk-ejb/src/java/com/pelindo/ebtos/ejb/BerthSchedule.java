/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb;

import com.pelindo.ebtos.ejb.facade.local.MasterDockFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.PlanningVesselFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.ServiceVesselFacadeLocal;
import com.pelindo.ebtos.ejb.remote.BerthScheduleRemote;
import com.qtasnim.util.Padding;
import com.pelindo.ebtos.model.ShipShape;
import com.pelindo.ebtos.model.db.master.MasterDock;
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
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
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
public class BerthSchedule implements BerthScheduleRemote{
    @EJB private PlanningVesselFacadeLocal planningVesselFacade;
    @EJB private ServiceVesselFacadeLocal serviceVesselFacade;
    @EJB private MasterDockFacadeLocal masterDockFacade;

    public static Padding padding = new Padding(30, 30, 30, 50);
    public static Dimension size = new Dimension(1000,500);
    public static int xBoundMin, xBoundMax;
    public static int pixelX, pixelY;
    public static int shipPadding;

    private List<Object[]> approves;
    private List<Object[]> services;

    public void init(String dock_code, int w, int h){
        size.setSize(w, h);
        MasterDock dock = masterDockFacade.find(dock_code);
        services = serviceVesselFacade.findServiceVesselsServicingByDock(dock_code);
        approves = planningVesselFacade.findPlanningVesselApprovalByDock(dock_code);
        xBoundMin = dock.getFrMeter() / 10;
        xBoundMax = dock.getToMeter() / 10;
    }

    @Override
    public Blob generate() {

        int x0 = padding.getPaddingLeft();
        int y0 = (int) (size.getHeight() - padding.getPaddingBottom());

        shipPadding = 10;

        BufferedImage outImage = new BufferedImage((int) size.getWidth(),(int) size.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D    g2d      = outImage.createGraphics();
        FontRenderContext frc  = g2d.getFontRenderContext();
        TextLayout tl;

        // ==== set image quality ==== //
        g2d.setComposite(AlphaComposite.Src);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // ==== end ==== //
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.fillRect(0, 0, (int)size.getWidth(), (int)size.getHeight());
        g2d.setColor(Color.BLACK);

        int media_height = (int) (size.getHeight() - padding.getPaddingTop() - padding.getPaddingBottom());
        int media_width = (int) (size.getWidth() - padding.getPaddingLeft() - padding.getPaddingRight());

        int service_zone_height = ShipShape.DEFAULT_WIDTH + (shipPadding * 5);

        int dockWidth = (int) Math.ceil(xBoundMax - xBoundMin);
        pixelX = media_width / dockWidth;
        media_width = pixelX * dockWidth;

        g2d.drawRect(x0, y0 - media_height,media_width,media_height);
        g2d.drawRect(x0, y0 - service_zone_height,media_width,service_zone_height);

        //set x's value
        int index = xBoundMin;
        for (int i = x0 ; i <= x0 + media_width; i += pixelX) {
            g2d.drawLine(i, (int) size.getHeight() - padding.getPaddingBottom() - 2, i, (int) size.getHeight() - padding.getPaddingBottom());
            tl = new TextLayout(String.valueOf(String.valueOf(index++ * 10)), new Font("Helvetica",Font.BOLD, 9), frc);
            tl.draw(g2d, (int) (i - (tl.getBounds().getWidth() / 2)), (int)size.getHeight() - padding.getPaddingBottom() + 10);
        }

        Color color[] = {new Color(90, 195, 255), Color.BLUE};
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");

        for (Object[] obj : services) {
            int x1 = (int) ((((BigDecimal) obj[4]).intValue() / 10.0 * pixelX) + padding.getPaddingLeft());
            int x2 = (int) ((((BigDecimal) obj[5]).intValue() / 10.0 * pixelX) + padding.getPaddingLeft());
            new ShipShape("(voyage) " + (String) obj[0],
                    (String) obj[1],
                    "(eta - edt) " + format.format(obj[2]) + " - " + format.format(obj[3]),
                    "(kd meter) " + (BigDecimal) obj[4] + " - " + (BigDecimal) obj[5],
                    x1,
                    y0 - shipPadding - ShipShape.DEFAULT_WIDTH,
                    x2 - x1,
                    ShipShape.DEFAULT_WIDTH - 1,
                    false).draw(g2d, color);
        }

        int y = y0 - service_zone_height;
        for (Object[] obj : approves) {
            int x1 = (int) ((((BigDecimal) obj[4]).intValue() / 10.0 * pixelX) + padding.getPaddingLeft());
            int x2 = (int) ((((BigDecimal) obj[5]).intValue() / 10.0 * pixelX) + padding.getPaddingLeft());
            new ShipShape("(voyage) " + (String) obj[0],
                    (String) obj[1],
                    "(eta - edt) " + format.format(obj[2]) + " - " + format.format(obj[3]),
                    "(kd meter) " + (BigDecimal) obj[4] + " - " + (BigDecimal) obj[5],
                    x1,
                    y - shipPadding - ShipShape.DEFAULT_WIDTH,
                    x2 - x1,
                    ShipShape.DEFAULT_WIDTH - 1,
                    true).draw(g2d, color);
            y = y - shipPadding - ShipShape.DEFAULT_WIDTH;
            if (y < size.getHeight() - padding.getPaddingBottom() - media_height)
                break;
        }


        g2d.setStroke(new BasicStroke(2.0f));
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
