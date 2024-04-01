package com.ll.surl.standard.util;

public class Ut {
    public static class str {
        public static boolean isBlank(String str) {
            return str == null || str.trim().length() == 0;
        }

        public static boolean hasLength(String str) {
            return !isBlank(str);
        }
    }
}