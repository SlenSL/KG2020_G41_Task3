package com.slensl.laws;

import java.util.Map;

public class LawTwo implements Law {

    private double a;
    private double b;
    private double c;



    public LawTwo() {

    }

    @Override
    public double getValue(double t) {
        return a * 1 / (1 + (int) Math.E ^ ((int) -b * (int) t)) + c;
    }

    @Override
    public void applyParams(Map<String, Double> paramMap) {
        this.a = paramMap.get("a");
        this.b = paramMap.get("b");
        this.c = paramMap.get("c");
    }
}
