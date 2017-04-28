package com.huxley.wiitools;

import android.content.Context;

import com.huxley.wiitools.utils.StringUtils;
import com.huxley.wiitools.utils.log.WiiLog;
import com.huxley.wiitools.wiiCrash.WiiCrash;
import com.huxley.wiitools.wiiCrash.mailreporter.CrashEmailReporter;

/**
 *
 * Created by huxley on 2017/4/19.
 */
public class WiiTools {

    public Context mContext;

    public static WiiTools instance;

    private WiiTools(Context context) {
        this.mContext = context;
    }

    public static WiiTools init(Context context) {
        return instance = new WiiTools(context);
    }

    public static WiiTools getInstance() {
        return instance;
    }

    public WiiTools initSinaEmailCrash(String emailName, String emailPassword) {
        return initEmailCrash(emailName, emailName, emailPassword, "smtp.sina.com", "465");
    }

    public WiiTools initEmailCrash(String receive, String sender, String sendPassword, String smtpHost, String port) {
        WiiCrash.getInstance().init(
                new CrashEmailReporter().setReceiver(receive)
                        .setSender(sender)
                        .setSendPassword(sendPassword)
                        .setSMTPHost(smtpHost)
                        .setPort(port)
        );
        return this;
    }

    public WiiTools initLog(boolean isLog, String tag) {
        WiiLog.LOG = isLog;
        if (!StringUtils.isEmpty(tag)) {
            WiiLog.TAG_ROOT = tag;
        }
        return this;
    }
}