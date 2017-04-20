package com.zkyyo.www.util;

/**
 * 提供同于数据清洗的方法
 */
public class CleanUtil {

    /**
     * 清洗文本, 并转化成HTML格式的文本
     * @param dirtyStr 待清洗的文本
     * @return 清洗完成的文本
     */
    public static String cleanText(String dirtyStr) {
        String cleanStr = dirtyStr.trim();
        cleanStr = cleanStr.replaceAll("\\n{2,}", "\n");
        cleanStr = cleanStr.replaceAll("[\\f\\t\\v\\r ]{2,}", " ");
        cleanStr = cleanStr.replaceAll("(\\r\\n|\\r|\\n|\\n\\r)", "<br>");
        return cleanStr;
    }
}
