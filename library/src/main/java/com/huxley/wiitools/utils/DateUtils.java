package com.huxley.wiitools.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Date 工具类
 * Created by huxley on 2017/4/20.
 */
public class DateUtils {

    private static Map<String, ThreadLocal<SimpleDateFormat>> sdfMap = new HashMap<>();

    /**
     * 根据map中的key得到对应线程的sdf实例
     *
     * @param pattern map中的key
     * @return 该实例
     */
    private static SimpleDateFormat getSdf(final String pattern) {
        ThreadLocal<SimpleDateFormat> sdfThread = sdfMap.get(pattern);
        if (sdfThread == null) {
            //双重检验,防止sdfMap被多次put进去值
            synchronized (DateUtils.class) {
                sdfThread = sdfMap.get(pattern);
                if (sdfThread == null) {
                    sdfThread = new ThreadLocal<SimpleDateFormat>() {
                        @Override
                        protected SimpleDateFormat initialValue() {
                            return new SimpleDateFormat(pattern, Constant.LOCALE);
                        }
                    };
                    sdfMap.put(pattern, sdfThread);
                }
            }
        }
        return sdfThread.get();
    }

    public static int[] getCurrentDates() {
        Calendar calendar = Calendar.getInstance();
        int[] current = new int[3];
        current[0] = calendar.get(Calendar.YEAR);
        current[1] = calendar.get(Calendar.MONTH) + 1;
        current[2] = calendar.get(Calendar.DAY_OF_MONTH);
        return current;
    }

    public static int[] getCurrentTimes() {
        Calendar calendar = Calendar.getInstance();
        int[] current = new int[3];
        current[0] = calendar.get(Calendar.HOUR_OF_DAY);
        current[1] = calendar.get(Calendar.MINUTE);
        return current;
    }


    /**
     * 按照指定pattern解析日期
     *
     * @param date    要解析的date
     * @param pattern 指定格式
     * @return 解析后date实例
     */
    public static Date parseDate(String date, String pattern) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        try {
            return getSdf(pattern).parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 按照指定pattern格式化日期
     *
     * @param date    要格式化的date
     * @param pattern 指定格式
     * @return 解析后格式
     */
    public static String formatDate(Date date, String pattern) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        } else {
            return getSdf(pattern).format(date);
        }
    }

    public static String getCurrentTime(String pattern) {
        return formatDate(new Date(), pattern);
    }
}
