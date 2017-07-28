package com.huxley.wiitools.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Date 工具类
 * Created by huxley on 2017/4/20.
 */
public class DateUtils {

    private static final SimpleDateFormat timeFormat_1 = new SimpleDateFormat("MM-dd HH:mm:ss.SSS", Locale.getDefault());

    public static String getCurrentDateString() {
        return timeFormat_1.format(new Date());
    }

    public static int[] getDates() {
        String[] contents =  new SimpleDateFormat("yyyy-MM-dd").format(new Date()).split("-");
        int[] dates = new int[3];
        dates[0] = Integer.valueOf(contents[0]);
        dates[1] = Integer.valueOf(contents[1]);
        dates[2] = Integer.valueOf(contents[2]);
        return dates;
    }

    public static int[] getCurrentDates(){
        Calendar calendar = Calendar.getInstance();
        int[] current = new int[3];
        current[0] = calendar.get(Calendar.YEAR);
        current[1] = calendar.get(Calendar.MONTH)+1;
        current[2] = calendar.get(Calendar.DAY_OF_MONTH);
        return  current;
    }

    public static int[] getCurrentTimes(){
        Calendar calendar = Calendar.getInstance();
        int[] current = new int[3];
        current[0] = calendar.get(Calendar.HOUR_OF_DAY);
        current[1] = calendar.get(Calendar.MINUTE);
        return  current;
    }
}
