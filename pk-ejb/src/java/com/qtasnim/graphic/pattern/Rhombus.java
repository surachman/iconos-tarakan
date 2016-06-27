package com.qtasnim.graphic.pattern;

import com.qtasnim.graphic.*;
import java.awt.*;

public class Rhombus implements IBoxPatternable
{
    public void draw(final int x, final int y, final int width, final int height, final Graphics2D g2d) {
        final Polygon polygon = new Polygon();
        polygon.addPoint(x + width / 2, y);
        polygon.addPoint(x, y + height / 2);
        polygon.addPoint(x + width / 2, y + height);
        polygon.addPoint(x + width, y + height / 2);
        g2d.drawPolygon(polygon);
    }
}
