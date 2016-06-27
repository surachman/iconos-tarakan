package com.qtasnim.graphic.pattern;

import com.qtasnim.graphic.*;
import java.awt.geom.*;
import java.awt.*;

public class HalfCircleRight implements IBoxPatternable
{
    public void draw(final int x, final int y, final int width, final int height, final Graphics2D g2d) {
        final Rectangle2D clip = new Rectangle2D.Double(x, y, width, height);
        g2d.setClip(clip);
        g2d.drawOval(x + width / 2, y, width, height);
        g2d.setClip(null);
    }
}
