package com.huxley.wiitools.utils;

import java.text.SimpleDateFormat;
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
}
