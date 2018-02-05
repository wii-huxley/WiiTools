package com.huxley.wiitools.view;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.huxley.wiitools.R;
import com.huxley.wiitools.WiiTools;
import com.huxley.wiitools.utils.ResUtils;
import com.huxley.wiitools.utils.ViewUtils;

/**
 *
 * Created by huxley on 2017/4/19.
 */
public class WiiToast {

    private static final String TOAST_TYPEFACE = "sans-serif-condensed";
    public static final  int    INITIAL        = 0;

    public static int gravity  = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
    public static int duration = Toast.LENGTH_SHORT;

    private static Toast     mToast;
    private static ImageView mIvToast;
    private static TextView  mTvToast;
    private static View      mToastLayout;

    public static void show(CharSequence message) {
        show(message, INITIAL, INITIAL);
    }

    public static void info(CharSequence message) {
        show(message, R.drawable.ic_info, R.color.color_3f51b5);
    }

    public static void success(CharSequence message) {
        show(message, R.drawable.ic_success, R.color.color_388e3c);
    }

    public static void warn(CharSequence message) {
        show(message, R.drawable.ic_warn, R.color.color_ffa900);
    }

    public static void error(CharSequence message) {
        show(message, R.drawable.ic_error, R.color.color_d50000);
    }

    public synchronized static void show(CharSequence message, int iconResId, int tintColor) {
        if (mToast == null || mToastLayout == null || mIvToast == null || mTvToast == null) {
            mToast = new Toast(WiiTools.instance.mContext);
            mToastLayout = ((LayoutInflater) WiiTools.instance.mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                    .inflate(R.layout.toast_layout, null);
            mIvToast = (ImageView) mToastLayout.findViewById(R.id.iv_toast);
            mTvToast = (TextView) mToastLayout.findViewById(R.id.tv_toast);
            mToast.setView(mToastLayout);
            mToast.setGravity(gravity, 0, 100);
            mToast.setDuration(duration);
        }

        Drawable drawableFrame = ResUtils.setNinePatchDrawableTintColor(R.drawable.toast_frame, tintColor);
        ViewUtils.setBackground(mToastLayout, drawableFrame);
        ViewUtils.setBackgroundOrGone(mIvToast, iconResId);
        ViewUtils.setTextColor(mTvToast, R.color.color_f);
        ViewUtils.setText(mTvToast, message);
        ViewUtils.setTypeface(mTvToast, Typeface.create(TOAST_TYPEFACE, Typeface.NORMAL));

        mToast.show();
    }


}
