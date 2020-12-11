package com.slensl;

import com.slensl.graphics.MainWindow;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        MainWindow mw = new MainWindow();
        mw.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mw.setSize(new Dimension(800, 600));
        mw.setVisible(true);
    }

}
