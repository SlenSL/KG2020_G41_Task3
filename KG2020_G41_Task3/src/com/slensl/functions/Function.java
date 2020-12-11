package com.slensl.functions;

import com.slensl.laws.Law;

import java.util.Map;

public interface Function {

        double getValue(double x, Map<String, Law> laws, double t);
}
