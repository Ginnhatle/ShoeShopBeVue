package org.datn.app.util;

public class GenerateString {
    public static String generateString(int length) {
        String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = (int) (str.length() * Math.random());
            sb.append(str.charAt(index));
        }
        return sb.toString();
    }
}
