package com.huxley.wiitools;

import android.content.Context;

import com.huxley.wiitools.utils.StringUtils;
import com.huxley.wiitools.utils.log.WiiLog;
import com.huxley.wiitools.wiiCrash.WiiCrash;
import com.huxley.wiitools.wiiCrash.mailreporter.CrashEmailReporter;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobConfig;

/**
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

    public WiiTools initBmob(String applicationID) {
        BmobConfig config = new BmobConfig.Builder(mContext)
                .setApplicationId(applicationID) //设置appkey
                .setConnectTimeout(30)           //请求超时时间（单位为秒）：默认15s
                .setUploadBlockSize(1024 * 1024) //文件分片上传时每片的大小（单位字节），默认512*1024
                .setFileExpiration(2500)         //文件的过期时间(单位为秒)：默认1800s
                .build();
        Bmob.initialize(config);
        return this;
    }
}