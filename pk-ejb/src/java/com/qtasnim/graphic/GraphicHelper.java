package com.qtasnim.graphic;

import java.awt.*;

public class GraphicHelper
{
    public static AlphaComposite makeComposite(final float alpha) {
        return AlphaComposite.getInstance(3, alpha);
    }
}
