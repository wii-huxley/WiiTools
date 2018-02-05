package com.huxley.wiitools.utils;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.support.annotation.ArrayRes;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.IntegerRes;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import com.huxley.wiitools.WiiTools;

import java.util.Arrays;
import java.util.List;

/**
 * Resources 工具类
 * Created by huxley on 2017/4/19.
 */
public class ResUtils {

    public static final int INITIAL = 0;
    private static Resources      sResources;
    private static DisplayMetrics sDisplayMetrics;

    private static Resources getResources() {
        if (sResources== null) {
            sResources = WiiTools.instance.mContext.getResources();
        }
        return sResources;
    }

    private static DisplayMetrics getDisplayMetrics() {
        if (sDisplayMetrics == null) {
            sDisplayMetrics = getResources().getDisplayMetrics();
        }
        return sDisplayMetrics;
    }

    public static Drawable tintDrawable(int drawableResId, int colors) {
        final Drawable wrappedDrawable = getDrawable(drawableResId);
        DrawableCompat.setTintList(wrappedDrawable, ColorStateList.valueOf(colors));
        return wrappedDrawable;
    }

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

    public static int getColor(@ColorRes int colorResId) {
        return ContextCompat.getColor(WiiTools.instance.mContext, colorResId);
    }

    public static String getString(@StringRes int stringResId) {
        return WiiTools.instance.mContext.getString(stringResId);
    }

    public static float getDimen(@DimenRes int dimenResId) {
        return getResources().getDimension(dimenResId);
    }

    public static int getInt(@StringRes int strResId){
        return Integer.valueOf(getString(strResId));
    }

    public static int getInteger(@IntegerRes int intResId){
        return getResources().getInteger(intResId);
    }

    public static String[] getStringArray(@ArrayRes int strAryResId) {
        return getResources().getStringArray(strAryResId);
    }

    public static List<String> getStringList(@ArrayRes int strAryResId) {
        return Arrays.asList(getStringArray(strAryResId));
    }

    private String[][] getTwoDimensionalArray(@ArrayRes int strAryResId) {
        String[] array = getStringArray(strAryResId);
        String[][] twoDimensionalArray = null;
        for (int i = 0; i < array.length; i++) {
            String[] tempArray = array[i].split(",");
            if (twoDimensionalArray == null) {
                twoDimensionalArray = new String[array.length][tempArray.length];
            }
            System.arraycopy(tempArray, 0, twoDimensionalArray[i], 0, tempArray.length);
        }
        return twoDimensionalArray;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dpToPx(float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, getDisplayMetrics());
    }

    /**
     * 根据手机的分辨率从 sp 的单位 转成为 px(像素)
     */
    public static int spToPx(float spValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue, getDisplayMetrics());
    }


    /**
     * 获取状态栏的高度
     * @return 状态栏的高度
     */
    public static int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        if (result <= 0) {
            result = dpToPx(25);
        }
        return result;
    }

    /**
     * 取导航栏高度
     * @return 导航栏高度
     */
    public static int getNavigationBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        if (result <= 0) {
            result = dpToPx(40);
        }
        return result;
    }

    public static int getScreenWidth() {
        return getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return getDisplayMetrics().heightPixels;
    }
}
