package com.slensl.functions;

import com.slensl.laws.Law;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static java.lang.StrictMath.sin;

public class FunctionOne implements Function {

    //поля lawA...
    //нужно значение по умолчанию
    //функция возвращала список имен требуемых параметров
    public FunctionOne() {
    }

    @Override
    public double getValue(double x, Map<String, Law> parameters, double t) {
        return parameters.get("A").getValue(t) * x * x
                + parameters.get("B").getValue(t) * x + parameters.get("C").getValue(t);
    }
}
