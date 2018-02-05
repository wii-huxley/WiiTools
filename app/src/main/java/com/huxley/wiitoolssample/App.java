package com.huxley.wiitoolssample;

import android.app.Application;
import android.content.Context;

import com.huxley.wiitools.WiiTools;
import com.huxley.wiitools.companyUtils.acce.model.bean.UrlBean;

/**
 * Created by huxley on 2017/4/19.
 */

public class App extends Application {

    public static class Url {
        public static final UrlBean API_AUTO = new UrlBean("https://dev.accemarket.com/",
            "api/auth");
        public static final UrlBean YSCM_MOBILE_ERP_APP_SERVICE = new UrlBean(
            "http://erp.cosmosmedia.cn:8922/", "yscm/mobile/erpAppService");
    }


    public static class ServiceCode {
        public static final String LOGIN = "5002U01";
    }


    public static class Action {
        public static final String LOGIN = "login";
    }


    public final static String idSecret = "24578325-1";
    public final static String appSecret = "894ee1c34517c2649596c05102700d5d";
    public final static String rsaSecret
        = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQDAFl1hRsfNdqkBgRoFEnJCLp9pNWCD/8LKaCfnH9LphBPfV4Gc1rdEKhC8zBAEpNzyTs6fbehTzWl5q2FFafjAWoUFrXWdhf5hQIzhyRAK+tnpBoUebI9y9cAkW2k186OYMkjYJ2O+FsL04NYAkmc6jHQA4w3B58DVuC5hPuFfMKjYTGW7DMzmxZWJZ6wz/eP9vavupRAjLGe8R3Nlek7JnxNdswJAVovC7lAZV2cMUYLro5XpjaphPSLtI2f4y2lmvmX1/xn/z7ZaYlHhCi+TP9rvL5L2VN/nlXGgrOfwUdlS4YcZb/ABvHapmMYyj7U8WSv3WI684HKjN7HIqxZbAgMBAAECggEBAKuhEAyDzrCxrm5uqQ4RHlAuEjdR/wvSMXzitYyYbb5CSfNL376ARz27jDr05fcQS26hpZLkBCskpXKWA5KfDijoeGTLSQTXnaPNplNzcEcvPhcswTKe80lXdTfHCWmwaOLrQxUHlOJauhVM80mNhnjU2C2WsksoRK9xIte29G40oUBfB7ozQuNg3KU4nUceIwMi372cADWc6hJ2GRv+d8AF2C3y7S9qOkkczdzyt6BFxoBXpwyeBZg+89hIAVWtg61GFgGFV5tNheWsB+NsSBmosj5VJyBVDHGA3WrB2aaM6BE1+aqgfy/VRTLBHgc2/ypAe52m0bkxSgvZges/vdkCgYEA8mE+Y35CVJia1U0MYOCl0sI30e2eg7Pq2z6v/oykj8A3Deka0+0+B1fdMjbcB+Dzp1b87WSY5B/HEsE0zbwylopTU418nmUP4G1alR53ffiuuLdRdtBq5Ln7o3wmCGDK237zMvvsT7ntoDdbTC9fXnobWWYOFeStmxQ10Vok1P8CgYEAyuGjYCJYi2qE7rdq4uSZlAtpXqoX4jKzmzV13YZzNy0gM0pXKJC/5Kzbmf4gb4UfWt/FaGoS1qPcBagJBMU0GrnJK2P2JJFU+GRza9lYU+zHmRwEeHcorsCLcJir6NKgtUpQo1Iyt8TPIEb5sDQ1tc89jDUCRR4DD715DZ6sMqUCgYAISq9Zs/4M6tORLHuZ7NV/akmCkwBzPPpx2aknqiQPDuJTRYA3c8xFcAoPJ7VLR7TOKrmoXWKlmziyOY3W4/NPFxhWqWZz/L0YTmVk6I/p7VH827ibufGONZaYNy0N6FHIRIWJXvjsmU25x2sGJjC7CK5a1pTwxsvxnDVjHhRYfQKBgQCzAFgkdslG4Mm+0WfmHqn+O/9Q/aQq+G8NbyK6PGklcpQpjNrPuL8GZRkwik5V9s+OX0cOdCCA7nwvJW4nnlL1jZwXw4+l/fBJZF+N9WsASTZmOZkcLI5heRLOWnW66zPOIG57WwfSqjvRj623sKj+64WzUgXZs7whxButT6VA3QKBgQCKheqE4fhmh0HINhlrhMe+Lyfczz8C6m4xqkQsdNhfT70+dHtJIROYVvUayomrZVvz6s6Z09aPf6WhuCpGuMffn3o+fkpeOiBA2kyHA8XShZQe36cnyFo9VNT2DE/tfp8MWpwOSNZ5FlNUIV6iifXLuhJ3rh5x/OftNoRLet+fMA==";
    public final static String aesKey = "123456789abcdefg";


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

        // initialize最好放在attachBaseContext最前面
        //        SophixManager.getInstance().setContext(this)
        //                .setAppVersion(Tools.getAppVersionName(this))
        //                .setSecretMetaData(idSecret, appSecret, rsaSecret)
        //                .setAesKey(aesKey)
        //                .setEnableDebug(true)
        //                .setUnsupportedModel("", 0)//把不支持的设备加入黑名单，加入后不会进行热修复。modelName为该机型上Build.MODEL的值，这个值也可以通过adb shell getprop | grep ro.product.model取得。sdkVersionInt就是该机型的Android版本，也就是Build.VERSION.SDK_INT，若设为0，则对应该机型所有安卓版本。
        //                .setPatchLoadStatusStub(new PatchLoadStatusListener() {
        //                    @Override
        //                    public void onLoad(final int mode, final int code, final String info, final int handlePatchVersion) {
        //                        // 补丁加载回调通知
        //                        if (code == PatchStatus.CODE_LOAD_SUCCESS) {
        //                            // 表明补丁加载成功
        //                        } else if (code == PatchStatus.CODE_LOAD_RELAUNCH) {
        //                            // 表明新补丁生效需要重启. 开发者可提示用户或者强制重启;
        //                            // 建议: 用户可以监听进入后台事件, 然后应用自杀，以此加快应用补丁
        //                            // 建议调用killProcessSafely，详见1.3.2.3
        //                            // SophixManager.getInstance().killProcessSafely();
        //                        } else if (code == PatchStatus.CODE_LOAD_FAIL) {
        //                            // 内部引擎异常, 推荐此时清空本地补丁, 防止失败补丁重复加载
        //                            // SophixManager.getInstance().cleanPatches();
        //                        } else {
        //                            // 其它错误信息, 查看PatchStatus类说明
        //                        }
        //                    }
        //                }).initialize();
    }


    @Override
    public void onCreate() {
        super.onCreate();

        WiiTools.init(this)
            .initLog(true, "wii_huxley")
            .initStetho()
            .initSinaEmailCrash("emailName", "emailPassword");

        // queryAndLoadNewPatch不可放在attachBaseContext 中，否则无网络权限，建议放在后面任意时刻，如onCreate中
        //        SophixManager.getInstance().queryAndLoadNewPatch();
    }
}
