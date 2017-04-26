package com.huxley.wiitoolssample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.huxley.wiitools.view.WiiToast;

/**
 * Created by huxley on 2017/4/26.
 */

public class TestToastActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.test_toast_activity);
    }

    public void show(View view) {
        WiiToast.show("show");
    }

    public void info(View view) {
        WiiToast.info("info");
    }

    public void success(View view) {
        WiiToast.success("success");
    }

    public void warn(View view) {
        WiiToast.warn("warn");
    }

    public void error(View view) {
        WiiToast.error("error");
    }
}
