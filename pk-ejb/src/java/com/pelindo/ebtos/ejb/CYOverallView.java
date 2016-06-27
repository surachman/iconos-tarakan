/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb;

import com.qtasnim.util.Padding;
import com.pelindo.ebtos.ejb.facade.local.MasterYardCoordinatFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.MasterYardFacadeLocal;
import com.pelindo.ebtos.ejb.remote.CYOverallViewRemote;
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
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
 * @author eka
 */
@Stateless
public class CYOverallView implements CYOverallViewRemote {
    @EJB MasterYardFacadeLocal masterYardFacadeLocal;
    @EJB MasterYardCoordinatFacadeLocal masterYardCoordinatFacadeLocal;

    public int spaceHeight, spaceWidth, pixelX, pixelY, mediaHeight, mediaWidth, x0, y0;
    public Padding padding;
    public Dimension dimension;
    public String block;

    @Override
    public void init(String block, Integer contWidth, Integer contHeight) {
        dimension = new Dimension();
        pixelX = contWidth;
        pixelY = contHeight;
        padding = new Padding(30, 40, 10, 10);
        this.block = block;
    }
    
    @Override
    public Blob generate() {
        int y_row = 0, y_slot = 0;
        Object[] yardDetail = masterYardFacadeLocal.findMasterYardByBlock(block);

        y_slot = (Integer) yardDetail[0]; //jumlah row pada blok q_block
        y_row = (Integer) yardDetail[1]; //jumlah tier pada blok q_block
        spaceHeight = 20;
        spaceWidth = 40;
        mediaWidth = pixelX * y_slot;
        mediaHeight = pixelY * y_row;
        dimension.setSize(mediaWidth + padding.getPaddingLeft() + padding.getPaddingRight() + spaceWidth,mediaHeight + padding.getPaddingTop() + padding.getPaddingBottom() + spaceHeight);
        x0 = padding.getPaddingLeft() + spaceWidth;
        y0 = dimension.height - padding.getPaddingBottom() - spaceHeight;

        BufferedImage outImage = new BufferedImage((int) dimension.getWidth(),(int) dimension.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D    g2d      = outImage.createGraphics();
        FontRenderContext frc = g2d.getFontRenderContext();
        TextLayout tl;

        g2d.setComposite(AlphaComposite.Src);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, dimension.width, dimension.height);
        g2d.setColor(Color.BLACK);
        g2d.drawRect(0, 0, dimension.width - 1, dimension.height - 1);

        if (pixelX > 0 && pixelY > 0){
            mediaHeight = pixelY * y_row;
            mediaWidth = pixelX * y_slot;

            g2d.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f));

            g2d.setColor(Color.LIGHT_GRAY);
            g2d.fillRect(x0, y0, mediaWidth, spaceHeight); //sumbu x
            g2d.fillRect(x0 - spaceWidth, y0 - mediaHeight, spaceWidth, mediaHeight); //sumbu y

            g2d.setColor(Color.GRAY);
            tl = new TextLayout("SLOT", new Font("Helvetica",Font.PLAIN,8), frc);
            tl.draw(g2d, x0 + mediaWidth + 10, (float) (y0 + (spaceHeight / 2) + (tl.getBounds().getHeight() / 2)));
            tl = new TextLayout("ROW", new Font("Helvetica",Font.PLAIN,8), frc);
            tl.draw(g2d, x0 - (spaceWidth / 2) - ((int) tl.getBounds().getWidth() / 2), y0 - mediaHeight - 10);

            g2d.setColor(Color.BLACK);
            g2d.drawRect(x0 - spaceWidth, y0 - mediaHeight, spaceWidth, mediaHeight); //sumbu y
            g2d.drawRect(x0, y0, mediaWidth, spaceHeight); //sumbu x
            tl = new TextLayout("Block " + block, new Font("Helvetica",Font.BOLD, 12), frc);
            tl.draw(g2d, x0 + (mediaWidth / 2) - spaceWidth, y0 - mediaHeight - 10);
            Color color = Color.WHITE;
            
            List<Object[]> containers = masterYardCoordinatFacadeLocal.findOverallContByBlockColors(block);

            Color c = g2d.getColor();
            for (Object[] cont : containers) {
                if ((Integer) cont[2] == 1) {
                    if ((Integer) cont[3] == 1) {
                        color = Color.CYAN;
                        draw((Integer) cont[0], (Integer) cont[1], g2d, color);
                    } else if ((Integer) cont[3] == 2) {
                        color = Color.GREEN;
                        draw((Integer) cont[0], (Integer) cont[1], g2d, color);
                    } else if ((Integer) cont[3] == 3) {
                        color = Color.MAGENTA;
                        draw((Integer) cont[0], (Integer) cont[1], g2d, color);
                    } else if ((Integer) cont[3] == 4) {
                        color = Color.YELLOW;
                        draw((Integer) cont[0], (Integer) cont[1], g2d, color);
                    } else if ((Integer) cont[3] > 4) {
                        color = Color.RED;
                        draw((Integer) cont[0], (Integer) cont[1], g2d, color);
                    }
                } else {
                    g2d.setColor(Color.BLACK);
                    int x = mediaWidth + x0 - ((Integer) (cont[0]) * pixelX);
                    int y = (int) (y0 - mediaHeight + (((Integer) cont[1] - 1) * pixelY));
                    Stroke def = g2d.getStroke();
                    g2d.setStroke(new BasicStroke(0.01f));
                    g2d.drawRect(x, y, pixelX, pixelY);
                    g2d.setStroke(def);
                }
            }

            int index = 1;
            for (int i = x0 + mediaWidth;i > x0;i -= pixelX){
                g2d.drawLine(i, y0, i, y0 + spaceHeight);
                tl = new TextLayout(String.valueOf(String.valueOf(index++)), new Font("Helvetica",Font.PLAIN, 7), frc);
                tl.draw(g2d, i - (pixelX / 2) + ((int) tl.getBounds().getWidth() / 2), y0 + (spaceHeight / 2) + ((int) tl.getBounds().getHeight() / 2));
            }

            index = 1;
            for (int i = y0 - mediaHeight + pixelY;i <= y0;i += pixelY){
                g2d.drawLine(x0 - spaceWidth, i, x0, i);
                tl = new TextLayout(String.valueOf(String.valueOf(index++)), new Font("Helvetica",Font.PLAIN, 7), frc);
                tl.draw(g2d, x0 - (spaceWidth / 2) - ((int) tl.getBounds().getWidth() / 2), i - (pixelY / 2) + ((int) tl.getBounds().getHeight() / 2));
            }
        }

        g2d.dispose();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            ImageIO.write(outImage, "PNG", baos);
            return new SerialBlob(baos.toByteArray());
        } catch (IOException ex) {
            Logger.getLogger(CYOverallView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SerialException ex) {
            Logger.getLogger(CYOverallView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(CYOverallView.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private void draw(int propX, int propY, Graphics2D g2d, Color color){
        int x = mediaWidth + x0 - ((propX) * pixelX);
        int y = (int) (y0 - mediaHeight + ((propY - 1) * pixelY));
        int width = pixelX;
        g2d.setColor(color);
        g2d.fillRect(x, y, width, pixelY);
        g2d.setColor(Color.BLACK);
        g2d.drawRect(x, y, width, pixelY);
    }
}