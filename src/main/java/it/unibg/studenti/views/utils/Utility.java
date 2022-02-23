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
}
