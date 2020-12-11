package com.slensl.functions;

import com.slensl.laws.Law;

import java.util.HashMap;
import java.util.Map;

import static java.lang.StrictMath.sin;

public class FunctionTwo implements Function{

    @Override
    public double getValue(double x, Map<String, Law> parameters, double t) {
        return parameters.get("A").getValue(t)*sin(parameters.get("W").getValue(t)*x
                + parameters.get("F").getValue(t)) + parameters.get("C").getValue(t);
    }
}
