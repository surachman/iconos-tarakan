package com.qtasnim.graphic.pattern;

import com.qtasnim.graphic.*;
import java.awt.*;

public class CrossSide implements IBoxPatternable
{
    public void draw(final int x, final int y, final int width, final int height, final Graphics2D g2d) {
        g2d.drawLine(x, y + height / 2, x + width, y + height / 2);
        g2d.drawLine(x + width / 2, y, x + width / 2, y + height);
    }
}
