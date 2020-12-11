package com.slensl.laws;

import java.util.Map;

import static java.lang.StrictMath.sin;

public class LawOne implements Law {
    //параметры - поля класса
    //задавать через конструктор
    //поля - параметры
    private double a;
    private double w;
    private double fi;
    private double c;


    public LawOne() {

    }

    @Override
    public double getValue(double t) {
        return a * sin(w * t + fi) + c;
    }

    @Override
    public void applyParams(Map<String, Double> paramMap) {
        this.a = paramMap.get("a");
        this.w = paramMap.get("w");
        this.fi = paramMap.get("fi");
        this.c = paramMap.get("c");
    }
}
