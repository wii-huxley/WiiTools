package com.huxley.wiitoolssample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.huxley.wiidialog.WiiDialog;
import com.huxley.wiitools.view.DialogUtils;

public class TestDialogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_dialog);
    }

    public void getSimpleCenter(View view) {
        final WiiDialog simpleCenter = DialogUtils.getSimpleCenter(this, "我是标题", "我是内容", "我是确定键", new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        simpleCenter.show();
    }
}
