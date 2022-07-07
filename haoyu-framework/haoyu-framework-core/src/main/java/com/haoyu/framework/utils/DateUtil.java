package com.haoyu.framework.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    public static String yyyy_MM_dd = "yyyy-MM-dd";

    private static SimpleDateFormat ymdDateFormat = new SimpleDateFormat(yyyy_MM_dd);

    /**
     * 是否小于等于 less or equal
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isLe(Date date1, Date date2) {
        date1 = convertDate(date1);
        date2 = convertDate(date2);

        if (date1 == null || date2 == null) {
            return false;
        }

        if (date1.getTime() <= date2.getTime()) {
            return true;
        }
        return false;
    }

    /**
     * 是否小于 less
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isLess(Date date1, Date date2) {
        date1 = convertDate(date1);
        date2 = convertDate(date2);

        if (date1 == null || date2 == null) {
            return false;
        }

        if (date1.getTime() < date2.getTime()) {
            return true;
        }
        return false;
    }

    private static Date convertDate(Date date) {
        if (date == null) {
            return null;
        }
        String dateFirst = ymdDateFormat.format(date);
        try {
            return ymdDateFormat.parse(dateFirst);
        } catch (Exception ex) { }
        return null;
    }

    /**
     * 在区间时间内(年月日)
     * @param start
     * @param end
     * @return
     */
    public static boolean between(Date now, Date start, Date end) {
        return between(now, start, end, false);
    }

    /**
     * 在区间时间内(年月日)
     * @param start
     * @param end
     * @return
     */
    public static boolean between(Date now, Date start, Date end, boolean isEq) {
        try {
            start = convertDate(start);
            end = convertDate(end);

            if (now == null || start == null || end == null) {
                return false;
            }

            if (isEq) {
                now = convertDate(now);

                if (now.getTime() >= start.getTime() && now.getTime() <= end.getTime()) {
                    return true;
                }
            } else {
                if (now.getTime() > start.getTime() && now.getTime() < end.getTime()) {
                    return true;
                }
            }
        } catch (Exception ex) {
            return false;
        }
        return false;
    }

    /**
     * 时间转成 {}时{}分
     * @param sec
     * @param isMillisecond
     * @return
     */
    public static String sec2HourMinute(long sec, boolean isMillisecond) {
        if (isMillisecond) {
            sec /= 1000;
        }

        return (sec / 3600) + "时" + ((sec % 3600) / 60) + "分";
    }
    public static String sec2HourMinute(long sec) {
        return sec2HourMinute(sec, false);
    }
}
