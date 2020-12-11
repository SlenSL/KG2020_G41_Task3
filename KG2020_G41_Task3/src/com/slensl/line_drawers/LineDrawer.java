package com.slensl.line_drawers;


import com.slensl.components.ScreenPoint;

import java.awt.*;

public interface LineDrawer {
    enum Type {
        Briesenham,
        DDA,
        Graphics,
        Wu
    }
    void drawLine(ScreenPoint p1, ScreenPoint p2);

    Type getType();

    void setColor(Color c);
}
