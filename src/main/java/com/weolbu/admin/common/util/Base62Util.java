package com.weolbu.admin.common.util;

public class Base62Util {
    final private static int RADIX = 62;
    final private static String CODEC = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    public static String encoding(long param) {
        StringBuffer sb = new StringBuffer();
        while(param > 0) {
            sb.append(CODEC.charAt((int) (param % RADIX)));
            param /= RADIX;
        }
        return sb.toString();
    }

    public static long decoding(String param) {
        long sum = 0;
        long power = 1;
        for (int i = 0; i < param.length(); i++) {
            sum += CODEC.indexOf(param.charAt(i)) * power;
            power *= RADIX;
        }
        return sum;
    }
}
