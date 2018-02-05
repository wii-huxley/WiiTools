package com.huxley.wiitoolssample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.huxley.wiitools.handlerBus.HandlerBus;

/**
 * Created by huxley on 2017/4/26.
 */

public class TestHandlerBusActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.test_handler_bus_activity);
    }

    public void close(View view) {
        HandlerBus.getInstance().post(MainActivity.class, 1, "HandlerBus 成功了");
        finish();
    }
}
