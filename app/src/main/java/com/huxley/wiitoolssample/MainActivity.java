package com.huxley.wiitoolssample;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.huxley.wiitools.handlerBus.HandlerBus;
import com.huxley.wiitools.handlerBus.IHandler;

import java.text.MessageFormat;

public class MainActivity extends AppCompatActivity {

    public Button mBtnTestHandlerBus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBtnTestHandlerBus = (Button) findViewById(R.id.btn_test_handler_bus);
        HandlerBus.getInstance().register(MainActivity.class, new IHandler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        mBtnTestHandlerBus.setText(MessageFormat.format("{0}{1}", msg.obj, msg.what));
                        break;
                    case 2:
                        mBtnTestHandlerBus.setText(MessageFormat.format("{0}{1}", msg.obj, msg.what));
                        break;
                }
            }
        });
    }

    public void testToast(View view) {
        startActivity(new Intent(this, TestToastActivity.class));
    }

    public void testHandlerBus(final View view) {
        startActivity(new Intent(this, TestHandlerBusActivity.class));
    }

    public void testInput(View view) {
        startActivity(new Intent(this, TestInputActivity.class));
    }

    public void testDialog(View view) {
        startActivity(new Intent(this, TestDialogActivity.class));
    }
}
