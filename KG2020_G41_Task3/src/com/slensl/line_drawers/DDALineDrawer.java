package com.slensl.line_drawers;




import com.slensl.components.ScreenPoint;
import com.slensl.pixel_drawers.PixelDrawer;

import java.awt.*;

public class DDALineDrawer implements LineDrawer {
    private PixelDrawer pd;
    private Type t;
    private Color c;

    public DDALineDrawer(PixelDrawer pd) {
        this.t = Type.DDA;
        this.pd = pd;
        this.c = Color.BLACK;
    }

    @Override
    public void drawLine(ScreenPoint p1, ScreenPoint p2) {
        int x1 = p1.getX();
        int y1 = p1.getY();
        int x2 = p2.getX();
        int y2 = p2.getY();
        double dx = x2 - x1;
        double dy = y2 - y1;
        if (Math.abs(dx) > Math.abs(dy)) {
            if (x1 > x2) {
                int tmp = x1; x1 = x2; x2 = tmp;
                tmp = y1; y1 = y2; y2 = tmp;
            }
            double k = dy / dx;
            for (int j = x1; j <= x2; j++) {
                double i = k * (j - x1) + y1;
                pd.setPixel(j, (int) i, Color.BLACK);
            }
        } else {
            if (y1 > y2) {
                int tmp = x1; x1 = x2; x2 = tmp;
                tmp = y1; y1 = y2; y2 = tmp;
            }
            double kObr = dx / dy;
            for (int i = y1; i <= y2; i++) {
                double j = kObr * (i - y1) + x1;
                pd.setPixel((int) j, i, Color.BLACK);
            }
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
        return "DDA line drawer";
    }
}
