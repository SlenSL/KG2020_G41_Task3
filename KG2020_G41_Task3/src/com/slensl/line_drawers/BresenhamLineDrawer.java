package com.slensl.line_drawers;



import com.slensl.components.ScreenPoint;
import com.slensl.pixel_drawers.PixelDrawer;

import java.awt.*;

public class BresenhamLineDrawer implements LineDrawer {
    private Type t;
    private Color c;
    private PixelDrawer pd;

    public BresenhamLineDrawer(PixelDrawer pd) {
        this.t = Type.Briesenham;
        this.pd = pd;
    }

    @Override
    public void drawLine(ScreenPoint p1, ScreenPoint p2) {
        int x2 = p2.getX();
        int x1 = p1.getX();
        int y2 = p2.getY();
        int y1 = p1.getY();
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);

        int dx2 = 2 * dx;
        int dy2 = 2 * dy;

        int ix = x1 < x2 ? 1 : -1;
        int iy = y1 < y2 ? 1 : -1;

        int d = 0;
        if (dx >= dy) {
            while (true) {
                pd.setPixel(x1, y1, Color.DARK_GRAY);
                if (x1 == x2)
                    break;
                x1 += ix;
                d += dy2;
                if (d > dx) {
                    y1 += iy;
                    d -= dx2;
                }
            }
        } else {
            while (true) {
                pd.setPixel(x1, y1, Color.DARK_GRAY);
                if (y1 == y2)
                    break;
                y1 += iy;
                d += dx2;
                if (d > dy) {
                    x1 += ix;
                    d -= dy2;
                }
            }
        }
    }

    @Override
    public Type getType() {
        return Type.Briesenham;
    }

    @Override
    public void setColor(Color c) {
        this.c = c;
    }

    @Override
    public String toString() {
        return "Bresenham line drawer";
    }
}
