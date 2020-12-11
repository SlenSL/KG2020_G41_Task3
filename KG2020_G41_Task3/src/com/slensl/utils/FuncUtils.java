package com.slensl.utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class FuncUtils {
    public static Map<String, Double> parseTime(String string) {
        String str = string.trim();
        System.out.println(str);
        String[] strings = str.split(" "); //делим строку по пробелу

        LinkedHashMap<String, Double> times = new LinkedHashMap<>();
        times.put("t0",Double.parseDouble(strings[0]));
        times.put("tpm",Double.parseDouble(strings[1]));
        return times;
    }

    public static Map<String, Double> parseParams(String string)  {
        String str = string.trim();
        System.out.println(str);
        String[] strings = str.split(" "); //делим строку по пробелу



        Map<String, Double> params = new HashMap<>();
        if (strings.length == 4) {//если 4 параметра - 1 закон
            params.put("a",Double.parseDouble(strings[0]));
            params.put("w",Double.parseDouble(strings[1]));
            params.put("fi",Double.parseDouble(strings[2]));
            params.put("c",Double.parseDouble(strings[3]));
        } else if(strings.length == 3) {//если 3 параметра - 2 закон
            params.put("a",Double.parseDouble(strings[0]));
            params.put("b",Double.parseDouble(strings[1]));
            params.put("c",Double.parseDouble(strings[2]));
        }
        return params;
    }
}
