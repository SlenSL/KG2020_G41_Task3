package com.slensl.factories;



import com.slensl.line_drawers.LineDrawer;

import java.awt.image.BufferedImage;

public interface LineDrawerFactory {
    LineDrawer createLineDrawer(BufferedImage bi);

    void setType(LineDrawer.Type t);

    LineDrawer.Type getType();
}
