package com.slensl.factories;

import com.slensl.line_drawers.*;
import com.slensl.line_drawers.LineDrawer;
import com.slensl.pixel_drawers.BufferedImagePixelDrawer;

import java.awt.image.BufferedImage;

public class LDFactory implements LineDrawerFactory {
    private LineDrawer.Type t;


    @Override
    public LineDrawer createLineDrawer(BufferedImage bi) {
        if (t == null) {
            return null;
        }
        switch (t) {
            case Briesenham -> {
                return new BresenhamLineDrawer(new BufferedImagePixelDrawer(bi));
            }
            case DDA -> {
                return new DDALineDrawer(new BufferedImagePixelDrawer(bi));
            }
            case Wu -> {
                return new WuLineDrawer(new BufferedImagePixelDrawer(bi));
            }
        }
        return null;
    }

    @Override
    public LineDrawer.Type getType() {
        return t;
    }

    @Override
    public void setType(LineDrawer.Type t) {
        this.t = t;
    }
}
