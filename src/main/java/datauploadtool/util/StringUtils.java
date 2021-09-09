package datauploadtool.util;
/**
 * 字符串工具类
 *
 * @author wenzy
 * @date 2021/9/8 14:03
 */
public class StringUtils {

    /**
     * 将首字符大写后返回
     *
     * @author wenzy
     * @date 2021/9/8 14:04
     */
    public static String UpperFirstChar(String source) {
        String first = source.substring(0, 1).toUpperCase();
        if (source.length() > 1) {
            return first + source.substring(1);
        } else {
            return first;
        }
    }
}
