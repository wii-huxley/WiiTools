package com.huxley.wiitools.utils;

/**
 *
 * Created by huxley on 17/1/2.
 */
public class DoubleClickHandler {

    private static long lastClickTime;

    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < 500) {
            return true;
        }
        lastClickTime = time;
        return false;
    }
}
