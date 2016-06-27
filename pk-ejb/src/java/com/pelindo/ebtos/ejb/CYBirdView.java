/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb;

import com.qtasnim.graphic.BoxPattern;
import com.qtasnim.util.Padding;
import com.pelindo.ebtos.ejb.facade.local.MasterContainerTypeInGeneralFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.MasterGrossClassFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.MasterYardCoordinatFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.MasterYardFacadeLocal;
import com.pelindo.ebtos.ejb.remote.CYBirdViewRemote;
import com.pelindo.ebtos.model.Container;
import com.pelindo.ebtos.model.db.master.MasterContainerTypeInGeneral;
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
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
 * @author senoanggoro
 */
@Stateless
public class CYBirdView extends CYViewAbstract implements CYBirdViewRemote {
    @EJB MasterYardFacadeLocal masterYardFacadeLocal;
    @EJB MasterYardCoordinatFacadeLocal masterYardCoordinatFacadeLocal;
    @EJB MasterGrossClassFacadeLocal masterGrossClassFacadeLocal;
    @EJB MasterContainerTypeInGeneralFacadeLocal containerTypeInGeneralFacadeLocal;

    public static Padding padding;
    public static Dimension size;
    public static int legendHeight;
    public static int legendWidth;
    public static int secondLegendWidth;
    private int tier;
    private String block, ppkb;
    private boolean existOnly;

    //run this first!!;
    @Override
    public void init(String block, Integer tier, Integer w, Integer h, boolean existOnly, String ppkb){
        legendHeight = 20;
        legendWidth = 40;
        secondLegendWidth = 20;
        padding = new Padding(50, 50, 30, 30);
        size = new Dimension(w, h);
        space_height = 40;
        space_width = 40;
        this.block = block;
        this.tier = tier;
        this.existOnly = existOnly;
        this.ppkb = ppkb;
    }

    @Override
    public Blob generate() {
        BufferedImage outImage = new BufferedImage((int) size.getWidth(),(int) size.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D    g2d      = outImage.createGraphics();
        FontRenderContext frc  = g2d.getFontRenderContext();
        TextLayout tl;

        g2d.setComposite(AlphaComposite.Src);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int y_slot = 0,y_row = 0;

        Object[] yardDetail = masterYardFacadeLocal.findMasterYardByBlock(block);

        y_slot = ((Number) yardDetail[0]).intValue(); //jumlah row pada blok q_block
        y_row = ((Number) yardDetail[1]).intValue(); //jumlah tier pada blok q_block

        padding.setPaddingBottom(padding.getPaddingBottom() + (legendHeight * 2) + 50); //alokasi space untuk legend

        x0 = padding.getPaddingLeft() + space_width;
        y0 = (int) (size.getHeight() - padding.getPaddingBottom() - space_height);

        media_height = (int) (size.getHeight() - padding.getPaddingTop() - padding.getPaddingBottom() - space_height);
        media_width = (int) (size.getWidth() - padding.getPaddingLeft() - padding.getPaddingRight() - space_width);

        pixel_y = media_height / y_row;
        pixel_x = media_width / y_slot;

        //g2d.drawOval(x0, y0, 2, 2); //tes x0,y0

        if (pixel_x > 0 && pixel_y > 0){
            g2d.setComposite(AlphaComposite.Src);
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            media_height = pixel_y * y_row;
            media_width = pixel_x * y_slot;

            g2d.setStroke(new BasicStroke(1.5f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f));

            g2d.setColor(Color.WHITE);
            g2d.fillRect(0, 0,(int) size.getWidth(),(int) size.getHeight());
            g2d.setColor(Color.BLACK);
            g2d.drawRect(0, 0,(int) size.getWidth() - 1,(int) size.getHeight() - 1);

            Color color;

            List<Object[]> containers;
            if (existOnly)
                containers = masterYardCoordinatFacadeLocal.findContByBlockAndTierExistOnly(block, tier, ppkb);
            else
                containers = masterYardCoordinatFacadeLocal.findContByBlockAndTier(block, tier, ppkb);
            boolean draw = true;
            String contTemp = "32423235";

            Rectangle2D clip = new Rectangle2D.Double(x0 - 1, y0 - media_height - 1, media_width + 3, media_height + 3);
            g2d.setClip(clip);

            for (Object[] cont: containers){
                String contSize = cont[2].toString();
                short teus = (short) Math.ceil((double) Short.parseShort(contSize) / (double) 20);
                if (teus > 1){
                    if (cont[0].toString().equals(contTemp)){
                        draw = false;
                    } else {
                        draw = true;
                    }
                }

                if (draw) {
                    try {
                        color = Color.decode((String) cont[11]);
                    } catch (NumberFormatException e){
                        color = Color.WHITE;
                    } catch (Exception e){
                        color = Color.WHITE;
                    }
                    new Container(cont[0].toString(),
                            (String) cont[13],
                            cont[8].toString(),
                            (String) cont[15], contSize,
                            cont[3] == null ? "" : cont[3].toString(),
                            ((Number) cont[4]).intValue(),
                            ((Number) cont[5]).intValue(),
                            ((Number) cont[6]).intValue(),
                            Container.BIRD_VIEW,
                            (String) cont[14]).draw(g2d, color);
                }
                
                contTemp = cont[0].toString();
            }
            g2d.setClip(null);

            g2d.setColor(Color.LIGHT_GRAY);
            g2d.fillRect(x0, y0, media_width, space_height); //sumbu x
            g2d.fillRect(x0 - space_width, y0 - media_height, space_width, media_height); //sumbu y
//
            g2d.setColor(Color.BLACK);
            g2d.drawRect(x0, y0, media_width, space_height); //sumbu x
            tl = new TextLayout("SLOT", new Font("Helvetica",Font.PLAIN,12), frc);
            tl.draw(g2d, x0 + media_width + 10, (float) (y0 + (space_height / 2) + (tl.getBounds().getHeight() / 2)));
            g2d.drawRect(x0 - space_width, y0 - media_height, space_width, media_height); //sumbu y
            tl = new TextLayout("ROW", new Font("Helvetica",Font.PLAIN,12), frc);
            tl.draw(g2d, x0 - (space_width / 2) - ((int) tl.getBounds().getWidth() / 2), y0 - media_height - 10);

            int index = 1;
            for (int i = x0 + media_width;i > x0;i -= pixel_x){
                g2d.drawLine(i, y0, i, y0 + space_height);
                tl = new TextLayout(String.valueOf(String.valueOf(index++)), new Font("Helvetica",Font.BOLD, 9), frc);
                tl.draw(g2d, i - (pixel_x / 2) + ((int) tl.getBounds().getWidth() / 2), y0 + (space_height / 2) + ((int) tl.getBounds().getHeight() / 2));
            }

            index = 1;
            for (int i = y0 - media_height + pixel_y;i <= y0;i += pixel_y){
                g2d.drawLine(x0 - space_width, i, x0, i);
                tl = new TextLayout(String.valueOf(String.valueOf(index++)), new Font("Helvetica",Font.BOLD, 9), frc);
                tl.draw(g2d, x0 - (space_width / 2) - ((int) tl.getBounds().getWidth() / 2), i - (pixel_y / 2) + ((int) tl.getBounds().getHeight() / 2));
            }

            List<Object[]> grossClasses = masterGrossClassFacadeLocal.findGrossClasses();

            g2d.setStroke(new BasicStroke(1f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f));
            int incX = x0;

            for (Object[] gc: grossClasses){
                try {
                    color = Color.decode((String) gc[2]);
                } catch (NumberFormatException e){
                    color = Color.WHITE;
                }
                g2d.setColor(color);
                g2d.fillRect(incX, y0 + space_height + 20, legendWidth, legendHeight);
                g2d.setColor(Color.black);
                g2d.drawRect(incX, y0 + space_height + 20, legendWidth, legendHeight);
                tl = new TextLayout((String) gc[1], new Font("Helvetica",Font.PLAIN, 11), frc);
                tl.draw(g2d, incX + legendWidth + 5, (float) (y0 + space_height + 20 + (legendHeight / 2) + (tl.getBounds().getHeight() / 2)));
                incX+=legendWidth + tl.getBounds().getWidth() + 30;
            }

            List<MasterContainerTypeInGeneral> containerTypes = containerTypeInGeneralFacadeLocal.findAll();
            incX = x0;
            int incY = y0 + space_height + legendHeight + 40;
            for (MasterContainerTypeInGeneral contType: containerTypes){
                tl = new TextLayout(contType.getName(), new Font("Helvetica",Font.PLAIN, 9), frc);
                if (incX > x0 + media_width - (tl.getBounds().getWidth())) {
                    incX = x0;
                    incY += legendHeight + 5;
                }
                g2d.setColor(Color.LIGHT_GRAY);
                g2d.fillRect(incX, incY, secondLegendWidth, legendHeight);
                g2d.setColor(Color.black);
                g2d.drawRect(incX, incY, secondLegendWidth, legendHeight);
                BoxPattern.patternTable.get(contType.getMasterPattern() == null ? "CROSS_SIDE" : contType.getMasterPattern().getName()).draw(incX, y0 + space_height + legendHeight + 40, secondLegendWidth, legendHeight, g2d);
                tl.draw(g2d, incX + secondLegendWidth + 5, (float) (incY + (legendHeight / 2) + (tl.getBounds().getHeight() / 2)));
                incX+=secondLegendWidth + tl.getBounds().getWidth() + 15;
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