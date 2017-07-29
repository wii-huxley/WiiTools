package com.huxley.wiitools.view;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import com.huxley.wiitools.R;
import com.huxley.wiitools.commAdapter.adapter.CommonAdapter;
import com.huxley.wiitools.commAdapter.adapter.ViewHolder;
import com.huxley.wiitools.utils.DateUtils;
import com.huxley.wiitools.utils.ResUtils;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.GregorianCalendar;

import static android.text.InputType.TYPE_CLASS_NUMBER;
import static android.text.InputType.TYPE_CLASS_PHONE;
import static android.text.InputType.TYPE_CLASS_TEXT;
import static android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD;

/**
 * 自定义输入框
 * Created by huxley on 16/11/5.
 */

public class WiiInput extends LinearLayout {

    private static final int   DEFAULT_TITLE_COLOR    = 0;
    private static final int   DEFAULT_CONTENT_COLOR  = 0;
    private static final int   DEFAULT_INPUT_BOX_TYPE = 0;
    private static final float DEFAULT_TEXT_SIZE      = 0;

    public static final int TYPE_EDIT        = 0;
    public static final int TYPE_TEXT        = 1;
    public static final int TYPE_SELECT      = 2;
    public static final int TYPE_SPINNER     = 3;
    public static final int TYPE_SWITCH      = 4;
    public static final int TYPE_MPVCODE     = 5;
    public static final int TYPE_SELECT_TIME = 6;
    public static final int TYPE_SELECT_DATE = 7;

    public static final int EDIT_TYPE_USER_NAME = 0;
    public static final int EDIT_TYPE_PASSWORD  = 1;
    public static final int EDIT_TYPE_PHONE_NUM = 2;
    public static final int EDIT_TYPE_NUM       = 3;

    private int            mTitleColor;
    private int            mContentColor;
    private int            mInputType;
    private float          mTextSize;
    private String         mTitle;
    private String         mContent;
    private String         mTextHint;
    private String         mTextSeparator;
    private int            mEditType;
    private CharSequence[] mSpinnerData;
    private TextView       mTitleView;
    private View           mContentView;
    private View[]         contentViews;
    private int[]          mCurrentDate;

    private SendVerificationCodeListener mSendVerificationCodeListener;

    public WiiInput(Context context) {
        this(context, null);
    }

    public WiiInput(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WiiInput(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        obtainStyledAttributes(context, attrs);
        initDesView();
        initContentView(mInputType);
    }

    private void obtainStyledAttributes(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.input_box);
        mTitleColor = ta.getColor(R.styleable.input_box_title_color, DEFAULT_TITLE_COLOR);
        mContentColor = ta.getColor(R.styleable.input_box_content_color, DEFAULT_CONTENT_COLOR);
        mTextSize = ta.getDimension(R.styleable.input_box_text_size, DEFAULT_TEXT_SIZE);
        mContent = ta.getString(R.styleable.input_box_content);
        mTitle = ta.getString(R.styleable.input_box_title);
        mTextHint = ta.getString(R.styleable.input_box_text_hint);
        mTextSeparator = ta.getString(R.styleable.input_box_text_separator);
        mSpinnerData = ta.getTextArray(R.styleable.input_box_spinner_data);
        mInputType = ta.getInt(R.styleable.input_box_input_type, DEFAULT_INPUT_BOX_TYPE);
        mEditType = ta.getInt(R.styleable.input_box_edit_type, EDIT_TYPE_USER_NAME);
        if (mTextSeparator == null) {
            mTextSeparator = "";
        }
        ta.recycle();
    }

    private void initDesView() {
        mTitleView = new TextView(getContext());
        mTitleView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
        mTitleView.setTextColor(mTitleColor);
        mTitleView.setText(String.format("%s%s", mTitle, mTextSeparator));
        LayoutParams params = new LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER_VERTICAL;
        params.weight = 1;
        params.leftMargin = ResUtils.dpToPx(16);
        mTitleView.setLayoutParams(params);
        if (getChildCount() >= 1) removeViewAt(0);
        addView(mTitleView, 0);
    }

    private void initContentView(int type) {
        LayoutParams params = new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        params.gravity = Gravity.CENTER_VERTICAL;
        params.weight = 3;
        switch (type) {
            case TYPE_EDIT:
                mContentView = getEditView();
                break;
            case TYPE_TEXT:
                mContentView = getTextView();
                break;
            case TYPE_SELECT:
                mContentView = getSelectView();
                break;
            case TYPE_SPINNER:
                mContentView = getSpinnerView();
                params.rightMargin = ResUtils.dpToPx(16);
                break;
            case TYPE_SWITCH:
                mContentView = getSwitchView();
                params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.gravity = Gravity.CENTER_VERTICAL;
                params.rightMargin = ResUtils.dpToPx(16);
                break;
            case TYPE_MPVCODE:
                mContentView = getMPVCodeView();
                break;
            case TYPE_SELECT_DATE:
                mContentView = getSelectDateView();
                break;
            case TYPE_SELECT_TIME:
                mContentView = getSelectTimeView();
                break;
        }

        mContentView.setLayoutParams(params);
        if (getChildCount() == 2) removeViewAt(1);
        addView(mContentView);
    }

    private View getSpinnerView() {
        contentViews = new View[1];
        contentViews[0] = new Spinner(getContext());
        ((Spinner) contentViews[0]).setAdapter(new CommonAdapter<CharSequence>(getContext(), R.layout.input_spinner_item, Arrays.asList(mSpinnerData)) {
            @Override
            protected void convert(ViewHolder viewHolder, CharSequence content, int position) {
                TextView textView = (TextView) viewHolder.getConvertView();
                textView.setTextColor(mContentColor);
                textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
                textView.setText(content);
            }
        });
        return contentViews[0];
    }

    private View getEditView() {
        contentViews = new View[2];
        View view = LayoutInflater.from(getContext()).inflate(R.layout.input_edit_layout, this, false);
        contentViews[0] = view.findViewById(R.id.et);
        contentViews[1] = view.findViewById(R.id.iv_close);
        ((EditText) contentViews[0]).setTextColor(mContentColor);
        ((EditText) contentViews[0]).setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
        ((EditText) contentViews[0]).setHint(mTextHint);
        ((EditText) contentViews[0]).setText(mContent);
        switch (mEditType) {
            case EDIT_TYPE_NUM:
                ((EditText) contentViews[0]).setInputType(TYPE_CLASS_NUMBER);
                break;
            case EDIT_TYPE_PASSWORD:
                ((EditText) contentViews[0]).setInputType(TYPE_CLASS_TEXT | TYPE_TEXT_VARIATION_PASSWORD);
                break;
            case EDIT_TYPE_PHONE_NUM:
                ((EditText) contentViews[0]).setInputType(TYPE_CLASS_PHONE);
                break;
            case EDIT_TYPE_USER_NAME:
                ((EditText) contentViews[0]).setInputType(TYPE_CLASS_TEXT);
                break;
        }
        ((EditText) contentViews[0]).setSelection(((EditText) contentViews[0]).length());
        contentViews[1].setVisibility(((EditText) contentViews[0]).length() > 0 ? VISIBLE : GONE);
        ((EditText) contentViews[0]).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                contentViews[1].setVisibility(charSequence.length() > 0 ? VISIBLE : GONE);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        contentViews[1].setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ((EditText) contentViews[0]).setText("");
            }
        });
        return view;
    }

    private View getSelectView() {
        contentViews = new View[1];
        contentViews[0] = new TextView(getContext());
        ((TextView) contentViews[0]).setGravity(Gravity.CENTER_VERTICAL);
        ((TextView) contentViews[0]).setTextColor(mContentColor);
        ((TextView) contentViews[0]).setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
        contentViews[0].setPadding(ResUtils.dpToPx(5), 0, ResUtils.dpToPx(16), 0);
        Drawable drawable = ResUtils.getDrawable(R.drawable.ic_more);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        ((TextView) contentViews[0]).setCompoundDrawables(null, null, drawable, null);
        ((TextView) contentViews[0]).setHint(mTextHint);
        ((TextView) contentViews[0]).setText(mContent);
        return contentViews[0];
    }

    public View getMPVCodeView() {
        contentViews = new View[2];
        View view = LayoutInflater.from(getContext()).inflate(R.layout.input_mpvcode_layout, this, false);
        contentViews[0] = view.findViewById(R.id.et);
        contentViews[1] = view.findViewById(R.id.tv_get_code);
        ((EditText) contentViews[0]).setTextColor(mContentColor);
        ((TextView) contentViews[1]).setTextColor(mContentColor);
        ((EditText) contentViews[0]).setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
        ((TextView) contentViews[1]).setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
        contentViews[1].setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ((TextView) contentViews[1]).setText("发送中…");
                contentViews[1].setEnabled(false);
                if (mSendVerificationCodeListener != null) {
                    mSendVerificationCodeListener.onClickListener();
                }
            }
        });
        return view;
    }

    int time = 60;
    Runnable mRunnable;

    public void isSendComplete(boolean isSuccess) {
        time = 60;
        if (isSuccess) {
            WiiToast.success("获取验证码成功");
            mRunnable = new Runnable() {
                @Override
                public void run() {
                    if (time != 0) {
                        ((TextView) contentViews[1]).setText(MessageFormat.format("{0}S 后重新获取", time));
                        if (getHandler() != null) {
                            getHandler().postDelayed(mRunnable, 1000);
                        }
                        time--;
                    } else {
                        ((TextView) contentViews[1]).setText("获取验证码");
                        contentViews[1].setEnabled(true);
                    }
                }
            };
            getHandler().post(mRunnable);
        } else {
            WiiToast.error("获取验证码失败");
            ((TextView) contentViews[1]).setText("获取验证码");
            contentViews[1].setEnabled(true);
        }
    }

    private View getTextView() {
        contentViews = new View[1];
        contentViews[0] = new TextView(getContext());
        ((TextView) contentViews[0]).setGravity(Gravity.CENTER_VERTICAL);
        ((TextView) contentViews[0]).setTextColor(mContentColor);
        contentViews[0].setPadding(ResUtils.dpToPx(5), 0, ResUtils.dpToPx(16), 0);
        ((TextView) contentViews[0]).setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
        ((TextView) contentViews[0]).setHint(mTextHint);
        ((TextView) contentViews[0]).setText(mContent);
        return contentViews[0];
    }

    public View getSwitchView() {
        contentViews = new View[1];
        contentViews[0] = new Switch(getContext());
        return contentViews[0];
    }

    public View getSelectDateView() {
        mCurrentDate = DateUtils.getCurrentDates();
        final View selectDateView = getSelectView();
        selectDateView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        ((TextView) selectDateView).setText(String.format("%d - %d - %d", year, monthOfYear + 1, dayOfMonth)/*MessageFormat.format("{0} - {1} - {2}", year, monthOfYear + 1, dayOfMonth)*/);
                    }
                }, mCurrentDate[0], mCurrentDate[1], mCurrentDate[2]);
                dialog.getDatePicker().setMinDate(new GregorianCalendar(2015, 11, 1).getTimeInMillis());
                dialog.getDatePicker().setMaxDate(new GregorianCalendar(mCurrentDate[0], mCurrentDate[1] - 1, mCurrentDate[2]).getTimeInMillis());
                dialog.setTitle("请选择日期");
                dialog.show();
            }
        });
        return selectDateView;
    }

    public View getSelectTimeView() {
        final View selectTimeView = getSelectView();
        selectTimeView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                int[] times = DateUtils.getCurrentTimes();
                TimePickerDialog dialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        ((TextView) selectTimeView).setText(String.format("%d : %d", i, i1));
                    }
                }, times[0], times[1], true);
                dialog.setTitle("请选择时间");
                dialog.show();
            }
        });
        return selectTimeView;
    }

    public String getContent() {
        switch (mInputType) {
            case TYPE_EDIT:
                return ((EditText) contentViews[0]).getText().toString();
            case TYPE_TEXT:
                return ((TextView) contentViews[0]).getText().toString();
            case TYPE_SELECT:
                return ((TextView) contentViews[0]).getText().toString();
            case TYPE_SPINNER:
                return ((Spinner) contentViews[0]).getSelectedItem().toString();
            case TYPE_SWITCH:
                return ((Switch) contentViews[0]).isChecked() ? "1" : "0";
            case TYPE_MPVCODE:
                return ((EditText) contentViews[0]).getText().toString();
            case TYPE_SELECT_TIME:
                return ((TextView) contentViews[0]).getText().toString();
            case TYPE_SELECT_DATE:
                return ((TextView) contentViews[0]).getText().toString();
            default:
                return "";
        }
    }

    public void setContent(String content) {
        switch (mInputType) {
            case TYPE_EDIT:
                ((EditText) contentViews[0]).setText(content);
                break;
            case TYPE_TEXT:
                ((TextView) contentViews[0]).setText(content);
                break;
            case TYPE_SELECT:
                ((TextView) contentViews[0]).setText(content);
                break;
            case TYPE_SPINNER:
                int position = -1;
                for (int i = 0; i < mSpinnerData.length; i++) {
                    if (mSpinnerData[i].toString().equals(content)) {
                        position = i;
                        break;
                    }
                }
                if (position >= 0) {
                    ((Spinner) contentViews[0]).setSelection(position);
                }
                break;
            case TYPE_SWITCH:
                ((Switch) contentViews[0]).setChecked("1".equals(content));
                break;
            case TYPE_MPVCODE:
                ((EditText) contentViews[0]).setText(content);
                break;
            case TYPE_SELECT_TIME:
                ((TextView) contentViews[0]).setText(content);
                break;
            case TYPE_SELECT_DATE:
                ((TextView) contentViews[0]).setText(content);
                break;
            default:
                break;
        }
    }

    public TextView getTitleView() {
        return mTitleView;
    }

    public int getSpinnerPosition() {
        return ((Spinner) contentViews[0]).getSelectedItemPosition();
    }

    public int getInputType() {
        return mInputType;
    }

    public void clearContent() {
        ((TextView) contentViews[0]).setText("");
    }

    public void setSendVerificationCodeListener(SendVerificationCodeListener listener) {
        this.mSendVerificationCodeListener = listener;
    }

    public interface SendVerificationCodeListener {
        void onClickListener();
    }
}
