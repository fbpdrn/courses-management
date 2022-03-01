package it.unibg.studenti.views.utils;

import java.util.Random;

public class Utility {
    public static String SimpleRandomString(int size) {
        Random rm = new Random();
        String upper = "QWERTYUIOPASDFGHJKLZXCVBNM0123456789qwertyuiopasdfghjklzxcvbnm";
        StringBuilder rnd = new StringBuilder();
        for(int i=0; i<size;i++) {
            rnd.append(upper.charAt(rm.nextInt(upper.length())));
        }
        return rnd.toString();
    }

    public static Double convertToDouble(Object o) {
        if(o == null)
            return null;
        return Double.parseDouble(o.toString());
    }

    public static String convertToString(Object o) {
        if(o == null)
            return null;
        return String.valueOf(o);
    }
}
