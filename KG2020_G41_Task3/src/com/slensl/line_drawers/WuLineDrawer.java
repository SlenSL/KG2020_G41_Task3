package com.slensl.line_drawers;




import com.slensl.components.ScreenPoint;
import com.slensl.pixel_drawers.PixelDrawer;

import java.awt.*;

public class WuLineDrawer implements LineDrawer {
    private Type t;
    private PixelDrawer pd;
    private Color c = Color.GREEN;

    public WuLineDrawer(PixelDrawer pd) {
        t = Type.Wu;
        this.pd = pd;
    }

    @Override
    public void drawLine(ScreenPoint p1, ScreenPoint p2) {
        int y2 = p2.getY();
        int x2 = p2.getX();
        int y1 = p1.getY();
        int x1 = p1.getX();

        boolean steep = Math.abs(y1 - y2) > Math.abs(x1 - x2);
        if (steep) {
            int temp = x1; x1 = y1; y1 = temp;
            temp = x2; x2 = y2; y2 = temp;
        }

        if (x1 > x2) {
            int temp = x1; x1 = x2; x2 = temp;
            temp = y1; y1 = y2; y2 = temp;
        }

        double dx = x2 - x1;
        double dy = y2 - y1;
        
        double gradient = dy / dx;
        double y = y1 + gradient;
        for (int x = x1 + 1; x < x2; ++x) {
            int intY = (int) y;
            pd.setPixel(
                    steep ? intY + 1 : x, steep ? x : intY + 1,
                    new Color(0, 0, 0, (float)  (y - intY))
            );
            pd.setPixel(
                    steep ? intY : x, steep ? x : intY,
                    new Color(0, 0, 0, (float)  (1 - (y - intY)))
            );
            y += gradient;
        }
    }

    @Override
    public void setColor(Color c) {
        this.c = c;
    }

    @Override
    public Type getType() {
        return t;
    }

    @Override
    public String toString() {
        return "Wu line drawer";
    }
}
