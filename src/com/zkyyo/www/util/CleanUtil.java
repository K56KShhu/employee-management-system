package com.zkyyo.www.util;

public class CleanUtil {

    public static String cleanText(String dirtyStr) {
        String cleanStr = dirtyStr.trim();
        cleanStr = cleanStr.replaceAll("\\n{2,}", "\n");
        cleanStr = cleanStr.replaceAll("[\\f\\t\\v\\r ]{2,}", " ");
        cleanStr = cleanStr.replaceAll("(\\r\\n|\\r|\\n|\\n\\r)", "<br>");
        return cleanStr;
    }
}
