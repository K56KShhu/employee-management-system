package com.zkyyo.www.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

public class Test {
    public static void main(String[] args) {
        Map<Integer, String> map = new HashMap<>();
        map.put(1, "212");
        map.put(1, "3434");
        map.put(1, "fdff");
        map.put(2, "fdff");
        map.put(3, "fdff");
        Collection<String> col = map.values();
        for (String s : col) {
            System.out.println(s);
        }

    }
}
