
/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
 */
package com.pelindo.ebtos.ejb;

//~--- non-JDK imports --------------------------------------------------------

import com.pelindo.ebtos.ejb.remote.ShipPositionRemote;
import com.pelindo.ebtos.model.Ship;

//~--- JDK imports ------------------------------------------------------------

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import java.net.URL;

import java.sql.Blob;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;

import javax.imageio.ImageIO;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import javax.swing.ImageIcon;

/**
 * Class description
 *
 *
 * @version        $version$, 10/11/23
 * @author         R Seno Anggoro 
 * @company	   Dycode   
 */
@Stateless
public class ShipPosition implements ShipPositionRemote {
    private int pixel   = 20;
    private int scaledH = 400;
    private int scaledW = 500;
    List<Ship>  ships;

    public ShipPosition() {
        ships = new ArrayList();
        ships.add(new Ship(5));
        ships.add(new Ship(10));
        ships.add(new Ship(3));
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Override
    public Blob getPositionImage() {
        try {
            BufferedImage outImage = new BufferedImage(scaledW, scaledH, BufferedImage.TYPE_INT_RGB);
            Graphics2D    g2d      = outImage.createGraphics();

            // ==== set image quality ==== //
            g2d.setComposite(AlphaComposite.Src);
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // ==== end ==== //
            g2d.setColor(Color.WHITE);
            g2d.fillRect(0, 0, scaledW, scaledH);
            g2d.setColor(Color.red);
            g2d.drawLine(0, 375, scaledW, 375);

            int index = 0;

            for (int i = scaledW - pixel; i > 0; i -= pixel) {
                if (i == scaledW - pixel) {
                    for (int j = 0; j < ships.size(); j++) {
                        g2d.drawImage(getShipImage(ships.get(j)), (scaledW - pixel) - (ships.get(j).getSize() * pixel),
                                      j * 110, null);
                    }
                }

                g2d.drawLine(i, 370, i, 380);
                g2d.drawString(String.valueOf(index), i - 3, 392);
                index++;
            }

            g2d.dispose();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            ImageIO.write(outImage, "png", baos);

            return new SerialBlob(baos.toByteArray());
        } catch (IOException ex) {
            Logger.getLogger(ShipPosition.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SerialException ex) {
            Logger.getLogger(ShipPosition.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ShipPosition.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    private BufferedImage getShipImage(Ship ship) {
        URL url = getClass().getResource("ship.gif");

        System.out.println(url.getFile());

        ImageIcon     imageIcon    = new ImageIcon(url);
        Image         image        = imageIcon.getImage();
        BufferedImage resizedImage = new BufferedImage(pixel * ship.getSize(), 100, BufferedImage.TYPE_INT_RGB);
        Graphics2D    g            = resizedImage.createGraphics();

        g.drawImage(image, 0, 0, pixel * ship.getSize(), 100, null);
        g.dispose();

        return resizedImage;
    }

    public static BufferedImage blurImage(BufferedImage image) {
        float   ninth      = 1.0f / 9.0f;
        float[] blurKernel = {
            ninth, ninth, ninth, ninth, ninth, ninth, ninth, ninth, ninth
        };
        Map     map        = new HashMap();

        map.put(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        map.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        map.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        RenderingHints  hints = new RenderingHints(map);
        BufferedImageOp op    = new ConvolveOp(new Kernel(3, 3, blurKernel), ConvolveOp.EDGE_NO_OP, hints);

        return op.filter(image, null);
    }
}



/*
 * @(#)ShipPosition.java   10/11/23
 * 
 * Copyright (c) 2010 Dycode
 */


//~ Formatted by Jindent --- http://www.jindent.com
