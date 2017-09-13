package com.huxley.wiitoolssample;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.huxley.wiitools.view.WiiToast;
import com.huxley.wiitools.view.dialog.CommonDialogFragment;
import com.huxley.wiitools.view.dialog.DialogFragmentHelper;
import com.huxley.wiitools.view.dialog.IDialogResultListener;

import java.util.Calendar;

public class TestDialogActivity extends AppCompatActivity {

    private DialogFragment mDialogFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_dialog_activity);
    }

    /**
     * 选择时间的弹出窗
     */
    public void showTimeDialog(View view) {
//        String titleTime = "请选择时间";
        Calendar calendarTime = Calendar.getInstance();
        DialogFragmentHelper.showTimeDialog(getSupportFragmentManager(), null, calendarTime, new IDialogResultListener<Calendar>() {
            @Override
            public void onDataResult(Calendar result) {
                WiiToast.info(String.valueOf(result.getTime().getDate()));
            }
        }, true);
    }

    /**
     * 输入密码的弹出窗
     */
    public void showPasswordInsertDialog(View view) {
        String titlePassword = "请输入密码";
        DialogFragmentHelper.showPasswordInsertDialog(getSupportFragmentManager(), titlePassword, new IDialogResultListener<String>() {
            @Override
            public void onDataResult(String result) {
                WiiToast.info("密码为：" + result);
            }
        }, true);
    }

    /**
     * 显示列表的弹出窗
     */
    public void showListDialog(View view) {
        String titleList = "选择哪种方向？";
        final String [] languanges = new String[]{"Android", "iOS", "web 前端", "Web 后端", "老子不打码了"};

        DialogFragmentHelper.showListDialog(getSupportFragmentManager(), titleList, languanges, new IDialogResultListener<Integer>() {
            @Override
            public void onDataResult(Integer result) {
                WiiToast.info(languanges[result]);
            }
        }, true);
    }

    /**
     * 两个输入框的弹出窗
     */
    public void showIntervalInsertDialog(View view) {
        String title = "请输入想输入的内容";
        DialogFragmentHelper.showIntervalInsertDialog(getSupportFragmentManager(), title, new IDialogResultListener<String[]>() {
            @Override
            public void onDataResult(String[] result) {
                WiiToast.info(result[0] + result[1]);
            }
        }, true);
    }

    /**
     * 一个输入框的弹出窗
     */
    public void showInsertDialog(View view) {
        String titleInsert  = "请输入想输入的内容";
        DialogFragmentHelper.showInsertDialog(getSupportFragmentManager(), titleInsert, new IDialogResultListener<String>() {
            @Override
            public void onDataResult(String result) {
                WiiToast.info(result);
            }
        }, true);
    }

    /**
     * 选择日期的弹出窗
     */
    public void showDateDialog(View view) {
//        String titleDate = "请选择日期";
        Calendar calendar = Calendar.getInstance();
        mDialogFragment = DialogFragmentHelper.showDateDialog(getSupportFragmentManager(), null, calendar, new IDialogResultListener<Calendar>() {
            @Override
            public void onDataResult(Calendar result) {
                WiiToast.info(String.valueOf(result.getTime().getDate()));
            }
        }, true);
    }

    /**
     * 确认和取消的弹出窗
     */
    public void showConfirmDialog(View view) {
        DialogFragmentHelper.showConfirmDialog(getSupportFragmentManager(), "是否选择 Android？", new IDialogResultListener<Integer>() {
            @Override
            public void onDataResult(Integer result) {
                WiiToast.info("You Click Ok = " + result);
            }
        }, true, new CommonDialogFragment.OnDialogCancelListener() {
            @Override
            public void onCancel() {
                WiiToast.info("You Click Cancel");
            }
        });
    }

    public void showTips(View view) {
        DialogFragmentHelper.showTips(getSupportFragmentManager(), "你进入了无网的异次元中");
    }

    public void showProgress(View view) {
        mDialogFragment = DialogFragmentHelper.showProgress(getSupportFragmentManager(), "正在加载中");
    }
}
