package com.huxley.wiitools.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.StringRes;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
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
        if (view != null) {
            view.setVisibility(visibility);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T extends View> T getView(View parent, int viewId) {
        if (viewId > INITIAL) {
            return (T) parent.findViewById(viewId);
        }
        return null;
    }

    public static View getLayout(Context context, int resourceId) {
        return getLayout(context, resourceId, null);
    }

    public static View getLayout(Context context, int resourceId, View view) {
        LayoutInflater inflater = LayoutInflater.from(context);
        if (view != null) {
            return view;
        }
        if (resourceId != INITIAL) {
            view = inflater.inflate(resourceId, null);
        }
        return view;
    }

    public static void setOnClickListener(View view, View.OnClickListener listener) {
        if (view == null || listener == null) {
            return;
        }
        view.setOnClickListener(listener);
    }
    //------------------- View ------------------- end

    //------------------- TextView ------------------- start
    public static void setTextColor(TextView textView, int colorResId) {
        if (textView != null && colorResId > INITIAL) {
            textView.setTextColor(ResUtils.getColor(colorResId));
        }
    }

    public static void setText(TextView textView, CharSequence content) {
        if (textView != null) {
            if (content != null) {
                textView.setText(content);
            } else {
                textView.setText("");
            }
        }
    }

    public static void setText(TextView textView, @StringRes int strId) {
        if (textView != null) {
            if (strId > INITIAL) {
                textView.setText(strId);
            } else {
                textView.setText("");
            }
        }
    }

    public static void setTypeface(TextView textView, Typeface typeface) {
        if (textView != null && typeface != null) {
            textView.setTypeface(typeface);
        }
    }
    //------------------- TextView ------------------- end
}
