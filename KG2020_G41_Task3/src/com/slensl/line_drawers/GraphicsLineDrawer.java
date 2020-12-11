package com.slensl.line_drawers;


import com.slensl.components.ScreenPoint;

import java.awt.*;

public class GraphicsLineDrawer implements LineDrawer {
    private Graphics g;
    private Color c;
    private Type t;

    public GraphicsLineDrawer(Graphics g) {
        this.t = Type.Graphics;
        this.g = g;
    }

    @Override
    public void drawLine(ScreenPoint p1, ScreenPoint p2) {
        g.setColor(Color.BLUE);
        g.drawLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());
    }

    @Override
    public Type getType() {
        return t;
    }

    @Override
    public void setColor(Color c) {
        this.c = c;
    }

    @Override
    public String toString() {
        return "Graphics line drawer";
    }
}
