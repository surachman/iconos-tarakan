package com.qtasnim.graphic;

import com.qtasnim.graphic.pattern.Circle;
import com.qtasnim.graphic.pattern.CrossCorner;
import com.qtasnim.graphic.pattern.CrossSide;
import com.qtasnim.graphic.pattern.HalfCircleBottom;
import com.qtasnim.graphic.pattern.HalfCircleLeft;
import com.qtasnim.graphic.pattern.HalfCircleRight;
import com.qtasnim.graphic.pattern.HalfCircleTop;
import com.qtasnim.graphic.pattern.HalfCirclesHorizontal;
import com.qtasnim.graphic.pattern.HalfCirclesVertical;
import com.qtasnim.graphic.pattern.Rhombus;
import java.util.Hashtable;

public class BoxPattern
{
  public static final IBoxPatternable CROSS_CORNER = new CrossCorner();
  public static final IBoxPatternable CROSS_SIDE = new CrossSide();
  public static final IBoxPatternable RHOMBUS = new Rhombus();
  public static final IBoxPatternable CIRCLE = new Circle();
  public static final IBoxPatternable HALF_CIRCLES_VERTICAL = new HalfCirclesVertical();
  public static final IBoxPatternable HALF_CIRCLES_HORIZONTAL = new HalfCirclesHorizontal();
  public static final IBoxPatternable HALF_CIRCLE_LEFT = new HalfCircleLeft();
  public static final IBoxPatternable HALF_CIRCLE_RIGHT = new HalfCircleRight();
  public static final IBoxPatternable HALF_CIRCLE_BOTTOM = new HalfCircleBottom();
  public static final IBoxPatternable HALF_CIRCLE_TOP = new HalfCircleTop();
  public static final Hashtable<String, IBoxPatternable> patternTable = new Hashtable();
  
  static
  {
    patternTable.put("CROSS_CORNER", CROSS_CORNER);
    patternTable.put("CROSS_SIDE", CROSS_SIDE);
    patternTable.put("RHOMBUS", RHOMBUS);
    patternTable.put("CIRCLE", CIRCLE);
    patternTable.put("HALF_CIRCLES_VERTICAL", HALF_CIRCLES_VERTICAL);
    patternTable.put("HALF_CIRCLES_HORIZONTAL", HALF_CIRCLES_HORIZONTAL);
    patternTable.put("HALF_CIRCLE_LEFT", HALF_CIRCLE_LEFT);
    patternTable.put("HALF_CIRCLE_RIGHT", HALF_CIRCLE_RIGHT);
    patternTable.put("HALF_CIRCLE_BOTTOM", HALF_CIRCLE_BOTTOM);
    patternTable.put("HALF_CIRCLE_TOP", HALF_CIRCLE_TOP);
  }
}
