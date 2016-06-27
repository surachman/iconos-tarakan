package com.qtasnim.graphic.pattern;

import com.qtasnim.graphic.*;
import java.awt.*;

public class CrossCorner implements IBoxPatternable
{
    public void draw(final int x, final int y, final int width, final int height, final Graphics2D g2d) {
        g2d.drawLine(x, y, x + width, y + height);
        g2d.drawLine(x, y + height, x + width, y);
    }
}
