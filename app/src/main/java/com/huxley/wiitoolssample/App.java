package com.huxley.wiitoolssample;

import android.app.Application;

import com.huxley.wiitools.WiiTools;

/**
 * Created by huxley on 2017/4/19.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        WiiTools.init(this)
                .initLog(true, "wii_huxley")
                .initSinaEmailCrash("emailName", "emailPassword");
    }
}
