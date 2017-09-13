package com.huxley.wiitools.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huxley.wiitools.R;
import com.huxley.wiitools.utils.DateUtils;
import com.huxley.wiitools.utils.ResUtils;
import com.huxley.wiitools.utils.StringUtils;
import com.huxley.wiitools.utils.log.WiiLog;

import java.util.Calendar;

/**
 * Created by huxley on 2017/8/7.
 */

public class WiiSelectDateView extends LinearLayout {

    public static final String DEFAULT_FORMAT_YEAR  = "yyyy";
    public static final String DEFAULT_FORMAT_MONTH = "yyyy-MM";
    public static final String DEFAULT_FORMAT_DAY   = "yyyy-MM-dd";

    private int mType;
    private String mContent;
    private String mFormat;
    private String mMinDate;
    private String mMaxDate;

    public WiiSelectDateView(Context context) {
        this(context, null);
    }

    public WiiSelectDateView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WiiSelectDateView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setOrientation(HORIZONTAL);
        obtainStyledAttributes(context, attrs);
        initView();
    }

    private void obtainStyledAttributes(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.select_date);
        mType = ta.getInteger(R.styleable.select_date_date_type, Calendar.DAY_OF_MONTH);
        mContent = ta.getString(R.styleable.select_date_date_content);
        mFormat = ta.getString(R.styleable.select_date_date_format);
        mMinDate = ta.getString(R.styleable.select_date_date_min);
        mMaxDate = ta.getString(R.styleable.select_date_date_max);
        if (StringUtils.isEmpty(mFormat)) {
            switch (mType) {
                case Calendar.YEAR:
                    mFormat = DEFAULT_FORMAT_YEAR;
                    break;
                case Calendar.MONTH:
                    mFormat = DEFAULT_FORMAT_MONTH;
                    break;
                case Calendar.DAY_OF_MONTH:
                    mFormat = DEFAULT_FORMAT_DAY;
                    break;
            }
        }
        mContent = DateUtils.getCurrentTime(mFormat);
    }

    private void initView() {
        ImageView ivMinus = new ImageView(getContext());
        ivMinus.setImageResource(R.drawable.ic_arrow_left);
        ivMinus.setPadding(ResUtils.dpToPx(10), 0, ResUtils.dpToPx(10), 0);
        ivMinus.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                minusDate();
            }
        });
        ImageView ivAdd = new ImageView(getContext());
        ivAdd.setImageResource(R.drawable.ic_arrow_right);
        ivAdd.setPadding(ResUtils.dpToPx(10), 0, ResUtils.dpToPx(10), 0);
        ivAdd.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                addDate();
            }
        });
        TextView tv = new TextView(getContext());
        tv.setGravity(Gravity.CENTER);
        tv.setText(mContent);
        tv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                selectDate();
            }
        });
        LayoutParams tvParams = new LayoutParams(0, LayoutParams.MATCH_PARENT, 1);
        LayoutParams ivParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        addView(ivMinus, ivParams);
        addView(tv, tvParams);
        addView(ivAdd, ivParams);
    }

    private void selectDate() {
        WiiLog.i("selectDate");
    }

    private void addDate() {
        if (mContent.equals(mMaxDate)) {
            WiiToast.warn("不能超过日期范围");
            return;
        }
        mContent = DateUtils.addDate(mType, mContent, mFormat);
        ((TextView)getChildAt(1)).setText(mContent);
    }

    private void minusDate() {
        if (mContent.equals(mMinDate)) {
            WiiToast.warn("不能超过日期范围");
            return;
        }
        mContent = DateUtils.minusDate(mType, mContent, mFormat);
        ((TextView)getChildAt(1)).setText(mContent);
    }
}
