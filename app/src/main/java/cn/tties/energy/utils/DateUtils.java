package cn.tties.energy.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.tties.energy.common.Dictionary;

public class DateUtils {

    private static String defaultDatePattern = "yyyy-MM-dd";

    private static String yearPattern = "yyyy";

    private static String monthPattern = "yyyy-MM";

    private static String timePattern = "HH:mm:ss";

    private static String minPattern = "HH:mm";

    private static String mmddPattern = "MM-dd";

    private static String yyyymmddhhmmssPattern = "yyyy-MM-dd HH:mm:ss";

    /**
     * 使用预设Format格式化Date成字符串
     */
    public synchronized static String format(Date date) {
        return format(date, defaultDatePattern);
    }

    public synchronized static String formatMonth(Date date) {
        return format(date, monthPattern);
    }

    public synchronized static String formatTime(Date date) {
        return format(date, timePattern);
    }

    public synchronized static String formatMinute(Date date) {
        return format(date, minPattern);
    }

    public synchronized static String formatYear(Date date) {
        return format(date, yearPattern);
    }

    public synchronized static String formatMMDD(Date date) {
        return format(date, mmddPattern);
    }

    public synchronized static String formatYMDHMS(Date date) {
        return format(date, yyyymmddhhmmssPattern);
    }

    /**
     * 使用参数Format格式化Date成字符串
     */
    public synchronized static String format(Date date, String pattern) {
        String returnValue = "";

        if (date != null) {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            returnValue = df.format(date);
        }

        return (returnValue);
    }

    /**
     * 使用预设格式将字符串转为Date
     */
    public synchronized static Date parse(String strDate) {
        return parse(strDate, defaultDatePattern);
    }

    public synchronized static Date parseMonth(String datre) {
        return parse(datre, monthPattern);
    }

    public synchronized static Date parseYear(String datre) {
        return parse(datre, yearPattern);
    }

    public synchronized static Date parseDateHHmmss(String datre) {
        return parse(datre, yyyymmddhhmmssPattern);
    }


    /**
     * 使用参数Format将字符串转为Date
     */
    public synchronized static Date parse(String strDate, String pattern) {
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        Date date = new Date();
        try {
            date = df.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public synchronized static Object[] getMonthDate(String monthDate) {
        Calendar startCal = Calendar.getInstance();
        startCal.setTime(parseMonth(monthDate));
        Calendar endCal = Calendar.getInstance();
        endCal.setTime(parseMonth(monthDate));
        endCal.set(Calendar.DAY_OF_MONTH, 1);
        endCal.add(Calendar.MONTH, 1);
        endCal.add(Calendar.MILLISECOND, -1);
        return new Object[]{startCal.getTime(), endCal.getTime()};
    }

    public static String getNowMonth() {
        Date date = new Date();
        return formatMonth(date);
    }

    public static String getNowDay() {
        Date date = new Date();
        return format(date);
    }

    public static String getAnalysisDate(String dateStr, int type) {
        Date date = parse(dateStr);
        if (type == Dictionary.AnalysisDateType.DAY) {
            return format(date);
        } else if (type == Dictionary.AnalysisDateType.WEEK) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return String.valueOf(calendar.get(Calendar.WEEK_OF_YEAR));
        } else if (type == Dictionary.AnalysisDateType.MONTH) {
            return formatMonth(date);
        }
        return formatYear(date);
    }

    public static String getNormalDate(String dateStr, int type) {
        Date date = parse(dateStr);
        if (type == Dictionary.DateType.DAY) {
            return format(date);
        } else if (type == Dictionary.DateType.WEEK) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return String.valueOf(calendar.get(Calendar.WEEK_OF_YEAR));
        } else if (type == Dictionary.DateType.MONTH) {
            return formatMonth(date);
        }
        return formatYear(date);
    }

    public static Date getNowWeekMonday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, -1); //解决周日会出现 并到下一周的情况
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static List<Date> getTimeAxis(Date date, int dateType) {
        List<Date> dateList = new ArrayList<Date>();
        switch (dateType) {
            case Dictionary.DateType.DAY :
                dateList = getDay(date, 15);
                break;
            case Dictionary.DateType.WEEK :
                dateList = getWeek(date, 15);
                break;
            case Dictionary.DateType.MONTH :
                dateList = getMonth(date, 1440);
                break;
            case Dictionary.DateType.YEAR :
                dateList = getYear(date, 1440);
                break;
        }
        return dateList;
    }

    public static List<Date> getMonth(Date date, int min) {
        Calendar ttt = Calendar.getInstance();
        ttt.setTime(date);
        Calendar tt = Calendar.getInstance();
        tt.setTime(ttt.getTime());
        tt.set(Calendar.DAY_OF_MONTH, 1);
        tt.set(Calendar.HOUR_OF_DAY, 0);
        tt.set(Calendar.MINUTE, 0);
        tt.set(Calendar.SECOND, 0);
        Calendar t2 = Calendar.getInstance();
        t2.setTime(ttt.getTime());
        t2.add(Calendar.MONTH, 1);
        t2.set(Calendar.DAY_OF_MONTH, 1);
        t2.add(Calendar.DAY_OF_YEAR, -1);
        t2.set(Calendar.HOUR_OF_DAY, 23);
        t2.set(Calendar.MINUTE, 59);
        t2.set(Calendar.SECOND, 59);
        List<Date> dateList = new ArrayList<Date>();
        for (; tt.compareTo(t2) < 0; tt.add(Calendar.MINUTE, min)) {
            dateList.add(tt.getTime());
        }
        return dateList;
    }

    public static List<Date> getYear(Date date, int min) {
        Calendar ttt = Calendar.getInstance();
        ttt.setTime(date);
        ttt.add(Calendar.SECOND, -1);
        Calendar tt = Calendar.getInstance();
        tt.setTime(ttt.getTime());
        tt.set(Calendar.DAY_OF_YEAR, 1);
        tt.set(Calendar.HOUR_OF_DAY, 0);
        tt.set(Calendar.MINUTE, 0);
        tt.set(Calendar.SECOND, 0);
        Calendar t2 = Calendar.getInstance();
        t2.setTime(ttt.getTime());
        t2.add(Calendar.YEAR, 1);
        t2.set(Calendar.DAY_OF_YEAR, 1);
        t2.add(Calendar.DAY_OF_YEAR, -1);
        t2.set(Calendar.HOUR_OF_DAY, 23);
        t2.set(Calendar.MINUTE, 59);
        t2.set(Calendar.SECOND, 59);
        List<Date> dateList = new ArrayList<Date>();
        for (; tt.compareTo(t2) < 0; tt.add(Calendar.MINUTE, min)) {
            dateList.add(tt.getTime());
        }
        return dateList;
    }

    public static List<Date> getWeek(Date date, int min) {
        Calendar ttt = Calendar.getInstance();
        ttt.setTime(date);
        ttt.add(Calendar.SECOND, -1);
        Calendar tt = Calendar.getInstance();
        tt.setTime(ttt.getTime());
        if (tt.get(Calendar.DAY_OF_WEEK) == 2) {
        }  else if (tt.get(Calendar.DAY_OF_WEEK) != 1) {
            tt.set(Calendar.DAY_OF_WEEK, 1);
            tt.add(Calendar.DAY_OF_YEAR, 1);
        } else {
            tt.add(Calendar.DAY_OF_YEAR, -1);
            tt.set(Calendar.DAY_OF_WEEK, 1);
            tt.add(Calendar.DAY_OF_YEAR, 1);
        }

        tt.set(Calendar.HOUR_OF_DAY, 0);
        tt.set(Calendar.MINUTE, 0);
        tt.set(Calendar.SECOND, 0);
        Calendar t2 = Calendar.getInstance();
        t2.setTime(ttt.getTime());
        if (t2.get(Calendar.DAY_OF_WEEK) == 7) {
            t2.add(Calendar.DAY_OF_YEAR, 1);
        } else if (t2.get(Calendar.DAY_OF_WEEK) != 1) {
            t2.set(Calendar.DAY_OF_WEEK, 7);
            t2.add(Calendar.DAY_OF_YEAR, 1);

        }
        t2.set(Calendar.HOUR_OF_DAY, 23);
        t2.set(Calendar.MINUTE, 59);
        t2.set(Calendar.SECOND, 59);
        List<Date> dateList = new ArrayList<Date>();
        for (; tt.compareTo(t2) < 0; tt.add(Calendar.MINUTE, min)) {
            dateList.add(tt.getTime());
        }
        return dateList;
    }

    public static List<Date> getDay(Date date, int min) {
        Calendar ttt = Calendar.getInstance();
        ttt.setTime(date);
        ttt.add(Calendar.SECOND, -1);
        Calendar tt = Calendar.getInstance();
        tt.setTime(ttt.getTime());
        tt.set(Calendar.MINUTE, 0);
        tt.set(Calendar.HOUR_OF_DAY, 0);
        tt.set(Calendar.SECOND, 0);
        Calendar t2 = Calendar.getInstance();
        t2.setTime(ttt.getTime());
        t2.set(Calendar.HOUR_OF_DAY, 23);
        t2.set(Calendar.MINUTE, 59);
        t2.set(Calendar.SECOND, 59);
        List<Date> dateList = new ArrayList<Date>();
        for (; tt.compareTo(t2) < 0; tt.add(Calendar.MINUTE, min)) {
            dateList.add(tt.getTime());
        }
        return dateList;
    }

    public static void main(String[] args) throws Exception {
    }
}