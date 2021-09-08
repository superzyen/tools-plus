package datauploadtool.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 日期处理工具类
 *
 * @author superzyen
 * @date 2021/9/7 16:48
 */
public class DateUtil {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter DATE_TIME_FORMATTER_YYYY_MM_DD = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter DATE_TIME_FORMATTER_YYYY_MM_DD_HH_MM = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final DateTimeFormatter DATE_TIME_FORMATTER_YYYYMMDD = DateTimeFormatter.ofPattern("yyyyMMdd");
    private static final DateTimeFormatter DATE_TIME_FORMATTER_YYYYMMDDHH = DateTimeFormatter.ofPattern("yyyyMMddHH");
    private static final DateTimeFormatter DATE_TIME_FORMATTER_YYYYMMDDHHMMSS = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    private static final DateTimeFormatter DATE_TIME_HH_MM = DateTimeFormatter.ofPattern("HH:mm");
    private static final Integer DAY_HOUR = 24;
    private static final int HOUR = 1;
    private static final int MINUTE = 2;
    private static final int SECOND = 3;
    private static final int HOUR_MINUTE = 4;
    private static final int HOUR_MINUTE_SECOND = 5;

    public static String getToday() {
        return getToday(DATE_TIME_FORMATTER_YYYY_MM_DD);
    }

    public static String getToday(DateTimeFormatter formatter) {
        LocalDateTime localDateTime = LocalDateTime.now();
        return localDateTime.format(formatter);
    }

    public static String getTime() {
        return getTime(HOUR_MINUTE_SECOND);
    }

    public static String getTime(int mode) {
        Calendar cl = new GregorianCalendar();
        String time = null;
        switch (mode) {
            case HOUR:
                time = String.valueOf(cl.get(11));
                break;
            case MINUTE:
                time = String.valueOf(cl.get(12));
                break;
            case SECOND:
                time = String.valueOf(cl.get(13));
                break;
            case HOUR_MINUTE:
                time = cl.get(11) + ":" + cl.get(12);
                break;
            case HOUR_MINUTE_SECOND:
                time = cl.get(11) + ":" + cl.get(12) + ":" + cl.get(13);
                break;
            default:
                time = "";
                break;
        }
        return time;
    }

    public static SimpleDateFormat newYMdHmsFormat() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    public static SimpleDateFormat newYMdFormat() {
        return new SimpleDateFormat("yyyy-MM-dd");
    }

    public static SimpleDateFormat newYMdFormatNoSymbol() {
        return new SimpleDateFormat("yyyyMMdd");
    }

    public static SimpleDateFormat newYMdHmsNoSymbolFormat() {
        return new SimpleDateFormat("yyyyMMddHHmmss");
    }

    public static Date toDate(String source) throws ParseException {
        return newYMdHmsFormat().parse(source);
    }

    public static String toString(Date source) {
        return newYMdHmsFormat().format(source);
    }

}
