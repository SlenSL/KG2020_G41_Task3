package com.slensl.graphics;


import com.slensl.components.Line;
import com.slensl.functions.Function;
import com.slensl.laws.Law;
import com.slensl.line_drawers.DDALineDrawer;
import com.slensl.utils.ScreenConverter;
import com.slensl.factories.LDFactory;
import com.slensl.factories.LineDrawerFactory;
import com.slensl.line_drawers.LineDrawer;
import com.slensl.pixel_drawers.BufferedImagePixelDrawer;
import com.slensl.pixel_drawers.PixelDrawer;
import com.slensl.components.RealPoint;
import com.slensl.components.ScreenPoint;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.util.*;


public class DrawPanel extends JPanel implements MouseListener, MouseMotionListener, MouseWheelListener, KeyListener {
    private LineDrawerFactory f;

    public DrawPanel() {
        f = new LDFactory();
        f.setType(LineDrawer.Type.Briesenham);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.addMouseWheelListener(this);
    }

    private Function function;
    private HashMap<String, Law> parameters;
    private double timeCounter;

    private LineDrawer ld;
    private ArrayList<Line> lines = new ArrayList<>();
    private ArrayList<Line> func = new ArrayList<>();
    private ScreenConverter sc = new ScreenConverter(-2, 2, 4, 4, 800, 600);

    private Line xAxis = new Line(-sc.getScreenW(), 0, sc.getScreenW(), 0);
    private Line yAxis = new Line(0, -sc.getScreenH(), 0, sc.getScreenH());
    private ScreenPoint prevDrag;
    private Line currentLine;

    public void setFunction(Function function) {
        this.function = function;
    }

    public void restartTime() {
        timeCounter = 1;
    }

    @Override
    public void paint(Graphics g) {
        BufferedImage bi = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        sc.setScreenW(getWidth());
        sc.setScreenH(getHeight());
        Graphics bi_g = bi.createGraphics();
        bi_g.setColor(Color.white);
        bi_g.fillRect(0, 0, getWidth(), getHeight());
        drawUnitSegments(bi_g);
        bi_g.dispose();

        PixelDrawer pd = new BufferedImagePixelDrawer(bi);
        ld = new DDALineDrawer(pd);
        ld = f.createLineDrawer(bi);

        drawGrid(ld);
        //drawFunction(parameters, timeCounter);//передаем текущий экземаляр ф-ии, мап из текущих законов
        drawAll(ld);


        g.drawImage(bi, 0, 0, null);
        bi_g.dispose();
    }

    private void drawAll(LineDrawer ld) {
        drawLine(ld, xAxis);
        drawLine(ld, yAxis);


        if (currentLine != null) {
            drawLine(ld, currentLine);
        }

        for (Line l : lines) {
            drawLine(ld, l);
        }
        if (currentLine != null)
            ld.drawLine(sc.realToScreen(currentLine.getP1()), sc.realToScreen(currentLine.getP2()));
        //drawFunction(parameters);
        for (Line l : func) {
            drawLine(ld, l);
        }
//

    }

    public void increaseTime(double time) {
        timeCounter += time;
        System.out.println(timeCounter);
    }

    public void drawFunction(HashMap<String, Law> parameters) {
        func.clear();

        this.parameters = parameters;
        double step = sc.getW() / sc.getScreenW();
        double result1;
        double result2 = function.getValue(sc.getX()*10, parameters, timeCounter);
        for (double x1 = sc.getX()*10; x1 < 10 * (sc.getW() + sc.getX()); x1 += step) {
            double x2 = x1 + step;
            result1 = result2;
            result2 = function.getValue(x2, parameters, timeCounter);
//            ScreenPoint p1 = sc.realToScreen(new RealPoint(x1, result1));
//            ScreenPoint p2 = sc.realToScreen(new RealPoint(x2, result2));
            //ld.drawLine(p1, p2);
            func.add(new Line(new RealPoint(x1, result1), new RealPoint(x2, result2)));
        }
    }

    private void drawLine(LineDrawer ld, Line l) {
        ld.drawLine(sc.r2s(l.getP1()), sc.r2s(l.getP2()));
    }

    private void drawGrid(LineDrawer ld) {
        ld.setColor(new Color(178, 178, 178));
        double step = sc.getH() / 10;
        for (double i = step; i <= sc.getW() + Math.abs(sc.getX()); i += step) {
            ScreenPoint p1 = sc.realToScreen(new RealPoint(i, sc.getH() + sc.getY()));//1 четверть
            ScreenPoint p2 = sc.realToScreen(new RealPoint(i, -(sc.getH() - sc.getY())));//4 четверть
            ld.drawLine(p1, p2);
            p1 = sc.realToScreen(new RealPoint(-i, sc.getH() + sc.getY()));//2 четверть
            p2 = sc.realToScreen(new RealPoint(-i, -(sc.getH() - sc.getY())));//3 четверть
            ld.drawLine(p1, p2);
        }

        for (double i = step; i <= sc.getH() + Math.abs(sc.getY()); i += step) {
            ScreenPoint p1 = sc.realToScreen(new RealPoint(sc.getW() + sc.getX(), i));//1 четверть
            ScreenPoint p2 = sc.realToScreen(new RealPoint(-(sc.getW() - sc.getX()), i));//2 четверть
            ld.drawLine(p1, p2);
            p1 = sc.realToScreen(new RealPoint(sc.getW() + sc.getX(), -i));//3 четверть
            p2 = sc.realToScreen(new RealPoint(-(sc.getW() - sc.getX()), -i));//4 четверть
            ld.drawLine(p1, p2);
        }
        ld.setColor(Color.BLACK);
    }

    private void drawUnitSegments(Graphics g) {
        g.setColor(Color.BLACK);
        double step = sc.getW() / 10;

        for (double x = 0; x <= sc.getW() + Math.abs(sc.getX()); x += step) {
            ScreenPoint point = sc.realToScreen(new RealPoint(x, 0));
            ScreenPoint oppositePoint = sc.realToScreen(new RealPoint(-x, 0));
            if (step >= 1) {
                g.drawString(String.valueOf((int) x), point.getX(), point.getY());
                g.drawString(String.valueOf((int) -x), oppositePoint.getX(), oppositePoint.getY());
            } else {
                String pattern = "#.#####";
                DecimalFormat f = new DecimalFormat(pattern);
                String value = f.format(x).equals("0") ? "0" : f.format(x);
                g.drawString(value, point.getX(), point.getY());
                g.drawString(value, oppositePoint.getX(), oppositePoint.getY());
            }
        }

        step = sc.getH() / 10;
        for (double y = 0; y <= sc.getH() + Math.abs(sc.getY()); y += step) {
            ScreenPoint point = sc.realToScreen(new RealPoint(0, y));
            ScreenPoint oppositePoint = sc.realToScreen(new RealPoint(0, -y));
            if (step >= 1) {
                g.drawString(String.valueOf((int) y), point.getX(), point.getY());
                g.drawString(String.valueOf((int) -y), oppositePoint.getX(), oppositePoint.getY());
            } else {
                String pattern = "#.#####";
                DecimalFormat f = new DecimalFormat(pattern);
                String value = f.format(y).equals("0") ? "0" : f.format(y);
                g.drawString(value, point.getX(), point.getY());
                g.drawString(value, oppositePoint.getX(), oppositePoint.getY());
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3)
            prevDrag = new ScreenPoint(e.getX(), e.getY());
        else if (e.getButton() == MouseEvent.BUTTON1)
            currentLine = new Line(sc.screenToReal(new ScreenPoint(e.getX(), e.getY())), sc.screenToReal(new ScreenPoint(e.getX(), e.getY())));
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3)
            prevDrag = null;
        if (e.getButton() == MouseEvent.BUTTON1) {
            lines.add(currentLine);
            currentLine = null;
        }
        repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        ScreenPoint current = new ScreenPoint(e.getX(), e.getY());
        if (currentLine != null) {
            currentLine.setP2(sc.screenToReal(current));
        }

        if (prevDrag != null) {
            ScreenPoint delta = new ScreenPoint(current.getX() - prevDrag.getX(), current.getY() - prevDrag.getY());
            RealPoint deltaToReal = sc.screenToReal(delta);
            RealPoint zeroToReal = sc.screenToReal(new ScreenPoint(0, 0));
            RealPoint vector = new RealPoint(deltaToReal.getX() - zeroToReal.getX(), deltaToReal.getY() - zeroToReal.getY());
            sc.setX(sc.getX() - vector.getX());
            sc.setY(sc.getY() - vector.getY());
            xAxis = new Line(-sc.getW() + sc.getX(), 0, sc.getW() + sc.getX(), 0);
            yAxis = new Line(0, -sc.getH() + sc.getY(), 0, sc.getH() + sc.getY());
            prevDrag = current;
        }
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        int ticks = e.getWheelRotation();
        double scale = 1;
        double cf = ticks > 0 ? 1.1 : 0.9;
        for (int i = 0; i <= Math.abs(ticks); i++) {
            scale *= cf;
        }
        sc.setW(sc.getW() * scale);
        sc.setH(sc.getH() * scale);
        sc.setX(sc.getX() * scale);
        sc.setY(sc.getY() * scale);
        repaint();
    }


    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getKeyChar() == 'b') {
            f.setType(LineDrawer.Type.Briesenham);
        } else if (e.getKeyChar() == 'd') {
            f.setType(LineDrawer.Type.DDA);
        } else if (e.getKeyChar() == 'w') {
            f.setType(LineDrawer.Type.Wu);
        }
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

}