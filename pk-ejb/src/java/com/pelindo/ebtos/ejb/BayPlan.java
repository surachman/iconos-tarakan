/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb;


import com.pelindo.ebtos.ejb.remote.BayPlanRemote;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import java.io.ByteArrayOutputStream;
import java.io.IOException;


import java.sql.Blob;
import java.sql.SQLException;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;

import javax.imageio.ImageIO;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;



/**
 *
 * @author dyware-java
 */

@Stateless
public class BayPlan implements BayPlanRemote{
    private int pixelSize = 20;
    private int height = 300;
    private int width = 300;

    @Override
    public Blob getBayPlan(int row, int tier) {
        try {
            width = (row * pixelSize) + 100;
            height = (tier * pixelSize) + 100;
            BufferedImage outImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D    g2      = outImage.createGraphics();

            // ==== set image quality ==== //
            g2.setComposite(AlphaComposite.Src);
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.fillRect(0, 0, width, height);

            for (int i = 0;i < tier;i++)
                for (int j = 0;j < row;j++){
                    g2.setColor(Color.CYAN);
                    g2.fillRect(((width / 2) - (pixelSize * row) / 2) + (j * pixelSize), (height / 2)-(pixelSize * tier / 2) + (pixelSize * i), pixelSize, pixelSize);
                    g2.setColor(Color.BLACK);
                    g2.drawRect(((width / 2) - (pixelSize * row) / 2) + (j * pixelSize), (height / 2)-(pixelSize * tier / 2) + (pixelSize * i), pixelSize, pixelSize);
                }

            Polygon p = new Polygon();
            p.addPoint(((width / 2) - (pixelSize * row) / 2) - 30, (height / 2)-(pixelSize * tier / 2));
            p.addPoint(((width / 2) - (pixelSize * row) / 2) - 10, (height / 2)-(pixelSize * tier / 2) + (pixelSize * tier) + 10);
            p.addPoint(width / 2, (height / 2)-(pixelSize * tier / 2) + (pixelSize * tier) + 30);
            p.addPoint(((width / 2) + (pixelSize * row) / 2) + 10, (height / 2)-(pixelSize * tier / 2) + (pixelSize * tier) + 10);
            p.addPoint(((width / 2) + (pixelSize * row) / 2) + 30, (height / 2)-(pixelSize * tier / 2));

            g2.drawPolygon(p);

            g2.dispose();

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
}
