package com.slensl.laws;

import java.util.Map;

public interface Law {
    double getValue(double t);
    void applyParams(Map<String, Double> paramMap);
}
