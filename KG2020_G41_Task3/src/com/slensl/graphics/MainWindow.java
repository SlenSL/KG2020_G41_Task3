package com.slensl.graphics;

import com.slensl.functions.Function;
import com.slensl.functions.FunctionOne;
import com.slensl.functions.FunctionTwo;
import com.slensl.laws.Law;
import com.slensl.laws.LawOne;
import com.slensl.laws.LawTwo;
import com.slensl.utils.FuncUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class MainWindow extends JFrame implements KeyListener {
    private JPanel mainPanel;
    private DrawPanel drawPanel;
    private JButton drawFuncButton;
    private JButton submitFuncButton;
    private JButton submitLawsButton;
    private JButton submitParamsButton;
    private JButton restartTimeButton;


    private JButton startButton;
    private JPanel rightPanel;
    private JComboBox<String> comboFunc = new JComboBox<>();
    private JComboBox<String> comboLawA = new JComboBox<>();
    private JComboBox<String> comboLawB = new JComboBox<>();
    private JComboBox<String> comboLawW = new JComboBox<>();
    private JComboBox<String> comboLawF = new JComboBox<>();
    private JComboBox<String> comboLawC = new JComboBox<>();
    private String func1String = "y = A*x^2 + B*x + C";
    private String func2String = "y = A*sin(W*x + F) + C";
    private String law1String = "f(t) = a*sin(w*t+fi) + c";
    private String law2String = "f(t) = a* 1/(1+e^(-b*t)) + c";
    private JLabel textLabel = new JLabel();
    private Timer timer;

    private JTextField lawAParamsField = new JTextField();
    private JTextField lawBParamsField = new JTextField();
    private JTextField lawCParamsField = new JTextField();
    private JTextField lawWParamsField = new JTextField();
    private JTextField lawFParamsField = new JTextField();

    private JTextField timeTextField = new JTextField();
    private double time = 1;
    private HashMap<String, Law> lawsMap = new HashMap<>();

    private Function function;
    private Law LawA;
    private Law LawB;
    private Law LawC;
    private Law LawW;
    private Law LawF;


    public MainWindow() {
        timer = new Timer( 40, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawPanel.increaseTime(time/300);
                drawPanel.drawFunction(lawsMap);
                drawPanel.repaint();

                //redrawFunctionOne();
            }
        });

        timer.setRepeats(true);
        this.setFocusable(true);
        this.addKeyListener(this);
        drawPanel = new DrawPanel();
        mainPanel.setLayout(new BorderLayout());
        rightPanel = new JPanel();
        rightPanel.setLayout(new GridLayout(20, 0, 50, 0));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        Container container = this.getContentPane();


        submitFuncButton = new JButton("Submit Func");
        submitFuncButton.setHorizontalAlignment(SwingConstants.CENTER);

        submitLawsButton = new JButton("Submit Laws");
        submitLawsButton.setHorizontalAlignment(SwingConstants.CENTER);

        submitParamsButton = new JButton("Submit Params");
        submitParamsButton.setHorizontalAlignment(SwingConstants.CENTER);

        drawFuncButton = new JButton("Draw Func");
        drawFuncButton.setHorizontalAlignment(SwingConstants.CENTER);

        startButton = new JButton("Start");
        startButton.setHorizontalAlignment(SwingConstants.CENTER);

        restartTimeButton = new JButton("Restart time");
        restartTimeButton.setHorizontalAlignment(SwingConstants.CENTER);

        mainPanel.add(rightPanel, BorderLayout.LINE_END);
        mainPanel.add(drawPanel);


        createComboBoxes();


        rightPanel.add(new JLabel("Chose func: "));
        rightPanel.add(comboFunc);



        rightPanel.add(submitFuncButton, BorderLayout.PAGE_END);



        container.add(mainPanel);

        submitFuncButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeComboBoxes();
                if (comboFunc.getSelectedItem() == func1String) {//y = A*x^2 + B*x + C
                    function = new FunctionOne();
                    textLabel.setText("Chose laws fow A,B,C:");
                    rightPanel.add(textLabel);
                    rightPanel.add(comboLawA);
                    rightPanel.add(comboLawB);
                    rightPanel.add(comboLawC);
                } else { //y = A*sin(W*x + F) + C
                    function = new FunctionTwo();
                    textLabel.setText("Chose laws fow A,W,F,C:");
                    rightPanel.add(textLabel);
                    rightPanel.add(comboLawA);
                    rightPanel.add(comboLawW);
                    rightPanel.add(comboLawF);
                    rightPanel.add(comboLawC);
                }
                rightPanel.add(submitLawsButton, BorderLayout.PAGE_END);
                rightPanel.validate();
            }
        });


        submitLawsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeComboBoxes();
                if (function instanceof FunctionOne) {//Если первая функция, то создаем законы именно для нее
                    LawA = getLawFromCombo((String) comboLawA.getSelectedItem());
                    rightPanel.add(lawAParamsField);

                    LawB = getLawFromCombo((String) comboLawB.getSelectedItem());
                    rightPanel.add(lawBParamsField);

                    LawC = getLawFromCombo((String) comboLawC.getSelectedItem());
                    rightPanel.add(lawCParamsField);

                } else {//Если вторая функция, то создаем законы именно для нее

                    LawA = getLawFromCombo((String) comboLawA.getSelectedItem());
                    rightPanel.add(lawAParamsField);

                    LawW = getLawFromCombo((String) comboLawW.getSelectedItem());
                    rightPanel.add(lawWParamsField);

                    LawF = getLawFromCombo((String) comboLawF.getSelectedItem());
                    rightPanel.add(lawFParamsField);

                    LawC = getLawFromCombo((String) comboLawC.getSelectedItem());
                    rightPanel.add(lawCParamsField);
                }
                rightPanel.add(submitParamsButton);
                rightPanel.validate();
            }
        });

        submitParamsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (function instanceof FunctionOne) {//Если первая функция
                    LawA.applyParams(FuncUtils.parseParams(lawAParamsField.getText()));
                    LawB.applyParams(FuncUtils.parseParams(lawBParamsField.getText()));
                    LawC.applyParams(FuncUtils.parseParams(lawCParamsField.getText()));

                    lawsMap.put("A",LawA);
                    lawsMap.put("B",LawB);
                    lawsMap.put("C",LawC);

                } else {//Если вторая функция
                    LawA.applyParams(FuncUtils.parseParams(lawAParamsField.getText()));
                    LawW.applyParams(FuncUtils.parseParams(lawWParamsField.getText()));
                    LawF.applyParams(FuncUtils.parseParams(lawFParamsField.getText()));
                    LawC.applyParams(FuncUtils.parseParams(lawCParamsField.getText()));

                    lawsMap.put("A",LawA);
                    lawsMap.put("W",LawW);
                    lawsMap.put("F",LawF);
                    lawsMap.put("C",LawC);
                }
                rightPanel.add(drawFuncButton, BorderLayout.PAGE_END);
                rightPanel.add(new JLabel("Write speed of t: "));
                rightPanel.add(timeTextField);
                rightPanel.add(startButton, BorderLayout.PAGE_END);
                rightPanel.add(restartTimeButton, BorderLayout.PAGE_END);
                rightPanel.validate();
            }
        });

        drawFuncButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawPanel.setFunction(function);
                drawPanel.drawFunction(lawsMap);
                repaint();
                time = Double.parseDouble(timeTextField.getText());
            }
        });

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (timer.isRunning()) {
                    timer.stop();
                } else {
                    timer.start();
                }

            }
        });

        restartTimeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawPanel.restartTime();
            }
        });
    }

//    private double changeTime() {
//        return time
//    }

    private HashMap<String, Law> getLaws() {
        LinkedHashMap<String, Law> parameters = new LinkedHashMap<>();
        parameters.put("A", getLawFromCombo((String) comboLawA.getSelectedItem()));
        parameters.put("B", getLawFromCombo((String) comboLawB.getSelectedItem()));
        parameters.put("W", getLawFromCombo((String) comboLawW.getSelectedItem()));
        parameters.put("F", getLawFromCombo((String) comboLawF.getSelectedItem()));
        parameters.put("C", getLawFromCombo((String) comboLawC.getSelectedItem()));
        return parameters;
    }

    private Law getLawFromCombo(String str) {

        if (str == law1String) {
            rightPanel.add(new JLabel("Write a,w,fi,c using Space: "));
            return new LawOne();

        } else {
            rightPanel.add(new JLabel("Write a,b,c using Space: "));
            return new LawTwo();
        }
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    private void removeComboBoxes() {
        rightPanel.remove(textLabel);
        rightPanel.remove(comboLawA);
        rightPanel.remove(comboLawB);
        rightPanel.remove(comboLawC);
        rightPanel.remove(comboLawW);
        rightPanel.remove(comboLawF);
        rightPanel.remove(submitLawsButton);
    }

    private void createComboBoxes() {
        comboFunc.addItem(func1String);
        comboFunc.addItem(func2String);

        comboLawA.addItem(law1String);
        comboLawA.addItem(law2String);
        comboLawB.addItem(law1String);
        comboLawB.addItem(law2String);
        comboLawW.addItem(law1String);
        comboLawW.addItem(law2String);
        comboLawF.addItem(law1String);
        comboLawF.addItem(law2String);
        comboLawC.addItem(law1String);
        comboLawC.addItem(law2String);
    }
}

