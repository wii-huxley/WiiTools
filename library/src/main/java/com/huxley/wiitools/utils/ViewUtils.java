package com.huxley.wiitools.utils;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.widget.TextView;

/**
 * View 工具类
 * Created by huxley on 2017/4/19.
 */
public class ViewUtils {

    public static final int INITIAL = 0;

    //------------------- View ------------------- start
    public static void setBackground(View view, Drawable drawable) {
        if (view != null && drawable != null) {
            ViewCompat.setBackground(view, drawable);
        }
    }
    public static void setBackground(View view, int drawableResId) {
        if (view != null && drawableResId > INITIAL) {
            ViewCompat.setBackground(view, ResUtils.getDrawable(drawableResId));
        }
    }
    public static void setBackgroundOrGone(View view, Drawable drawable) {
        if (drawable == null) {
            setVisibility(view, View.GONE);
        } else {
            setBackground(view, drawable);
        }
    }
    public static void setBackgroundOrGone(View view, int drawableResId) {
        if (drawableResId > INITIAL) {
            setBackground(view, drawableResId);
            setVisibility(view, View.VISIBLE);
        } else {
            setVisibility(view, View.GONE);
        }
    }
    public static void setVisibility(View view, int visibility) {
        if (view !=null) {
            view.setVisibility(visibility);
        }
    }
    //------------------- View ------------------- end

    //------------------- TextView ------------------- start
    public static void setTextColor(TextView textView, int colorResId) {
        if (textView != null && colorResId > INITIAL) {
            textView.setTextColor(ResUtils.getColor(colorResId));
        }
    }
    public static void setText(TextView textView, CharSequence content) {
        if (textView != null && content != null) {
            textView.setText(content);
        }
    }
    public static void setTypeface(TextView textView, Typeface typeface) {
        if (textView != null && typeface != null) {
            textView.setTypeface(typeface);
        }
    }
    //------------------- TextView ------------------- end
}
