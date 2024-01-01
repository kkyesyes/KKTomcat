package com.kk.tomcat.utils;

/**
 * @author KK
 * @version 1.0
 */
public class WebUtils {
    /**
     * 输入一个字符串，转换成 int 类型，如果转换失败则返回传入的 defaultVal
     * @param strNum
     * @param defaultVal
     * @return
     */
    public static int parseInt(String strNum, int defaultVal) {
        try {
            return Integer.parseInt(strNum);
        } catch (NumberFormatException e) {
            System.out.println(strNum + "格式不对，转换失败");
        }
        return defaultVal;
    }
}
