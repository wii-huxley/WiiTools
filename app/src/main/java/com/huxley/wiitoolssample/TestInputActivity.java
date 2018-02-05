package com.huxley.wiitoolssample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.huxley.wiitools.view.WiiInput;

public class TestInputActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_input_activity);

        final WiiInput inputMpvcode = (WiiInput) findViewById(R.id.input_mpvcode);
        inputMpvcode.setSendVerificationCodeListener(new WiiInput.SendVerificationCodeListener() {
            @Override
            public void onClickListener() {
                TestInputActivity.this.getWindow().getDecorView().getHandler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        inputMpvcode.isSendComplete(true);
                    }
                }, 2000);
            }
        });
    }
}
