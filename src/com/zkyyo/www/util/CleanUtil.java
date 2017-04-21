package com.zkyyo.www.util;

/**
 * 提供同于数据清洗的方法
 */
public class CleanUtil {

    /**
     * 转化成HTML格式的文本
     * @param dirtyStr 待清洗的文本
     * @return 清洗完成的文本
     */
    public static String cleanTextToHtml(String dirtyStr) {
        String cleanStr = dirtyStr.trim();
//        cleanStr = cleanStr.replaceAll("\\n{2,}", "\n"); //最多允许间隔一行
        cleanStr = cleanStr.replaceAll("(\\r\\n|\\r|\\n|\\n\\r)+", "\n"); //替换为统一的换行符
        cleanStr = cleanStr.replaceAll("[\\f\\t\\v\\r ]{2,}", " "); //其他多余的空白字符替换为一个空格
        cleanStr = cleanStr.replaceAll("\\n", "<br>"); //替换换行符, 方便数据库储存
        return cleanStr;
    }

    /**
     * 转换为适合文本文件的格式
     * @param dirtyStr 待清洗的文本
     * @return 清洗完成的文本
     */
    public static String cleanHtmlToTxt(String dirtyStr) {
        return dirtyStr.replaceAll("<br>", "\n");
    }

    /**
     * 清洗文本
     * @param dirtyStr 待清晰的文本
     * @return 清洗完成的文本
     */
    public static String cleanTest(String dirtyStr) {
        String cleanStr = dirtyStr.trim();
        cleanStr = cleanStr.replaceAll("(\\r\\n|\\r|\\n|\\n\\r)+", "\n"); //替换为统一的换行符
        cleanStr = cleanStr.replaceAll("[\\f\\t\\v\\r ]{2,}", " "); //其他多余的空白字符替换为一个空格
        return cleanStr;
    }
}
