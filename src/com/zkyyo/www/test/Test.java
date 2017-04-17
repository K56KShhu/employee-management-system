package com.zkyyo.www.test;

public class Test {
    public static void main(String[] args) {
        String str = " 32323 ";
        str = str.trim();
        if (str.length() > 0) {
            System.out.println(Integer.valueOf(str));
        }
    }
}
