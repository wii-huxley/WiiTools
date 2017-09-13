package com.huxley.wiitoolssample;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.huxley.wiitools.handlerBus.HandlerBus;
import com.huxley.wiitools.handlerBus.IHandler;
import com.huxley.wiitools.companyUtils.acce.AcceSubscriber;
import com.huxley.wiitools.companyUtils.acce.AcceTools;
import com.huxley.wiitools.companyUtils.acce.model.AcceHttpModel;
import com.huxley.wiitools.companyUtils.acce.model.AcceUserModel;
import com.huxley.wiitools.utils.log.WiiLog;
import com.huxley.wiitoolssample.testMultiType.TestMultiTypeActivity;

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

        AcceUserModel.getInstance().setAtUserId("15565502588");
        AcceHttpModel.getInstance()
                .setUrl(App.Url.API_AUTO)
                .setServiceCode(App.ServiceCode.LOGIN)
                .addBusiness("phoneNum", "15565502588")
                .addBusiness("pwd", AcceTools.getEnPassword("654321"))
                .post(new AcceSubscriber<String>() {
                    @Override
                    public void onSuccess(String userBean) {
                        WiiLog.i("1111");
                        WiiLog.json(userBean);
                    }
                    @Override
                    public void onError(String msg) {
                        WiiLog.i("2222");
                        WiiLog.i(msg);
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

    public void testSelectDate(View view) {
        startActivity(new Intent(this, TestSelectDateActivity.class));
    }

    public void testSopfix(View view) {
        startActivity(new Intent(this, TestSopfixActivity.class));
    }

    public void testMultiType(View view) {
        startActivity(new Intent(this, TestMultiTypeActivity.class));
    }

    public void testStatusButton(View view) {
        startActivity(new Intent(this, TestButtonActivity.class));
    }
}
