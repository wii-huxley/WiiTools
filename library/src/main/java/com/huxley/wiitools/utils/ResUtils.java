package com.huxley.wiitools.utils;

import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.support.v4.content.ContextCompat;

import com.huxley.wiitools.WiiTools;

/**
 * Resources 工具类
 * Created by huxley on 2017/4/19.
 */
public class ResUtils {

    public static final int INITIAL = 0;

    public static Drawable getDrawable(int drawableResId) {
        return ContextCompat.getDrawable(WiiTools.instance.mContext, drawableResId);
    }

    public static Drawable setNinePatchDrawableTintColor(int drawableResId, int tintColor) {
        NinePatchDrawable toastDrawable = null;
        if (drawableResId > 0) {
            toastDrawable = (NinePatchDrawable) getDrawable(drawableResId);
            if (tintColor > INITIAL) {
                toastDrawable.setColorFilter(new PorterDuffColorFilter(getColor(tintColor), PorterDuff.Mode.SRC_IN));
            }
        }
        return toastDrawable;
    }

    public static int getColor(int colorResId) {
        return ContextCompat.getColor(WiiTools.instance.mContext, colorResId);
    }

    public static String getString(int stringResId) {
        return WiiTools.instance.mContext.getString(stringResId);
    }


    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dp2px(float dpValue) {
        return (int) (dpValue * WiiTools.instance.mContext.getResources().getDisplayMetrics().density + 0.5f);
    }
}
