package com.huxley.wiitools.companyUtils.acce;

import com.huxley.wiitools.utils.DataUtils;

import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Random;


/**
 * Created by huxley on 2017/8/17.
 */

public class AcceTools {

    public static String getNonce() {
        StringBuilder sb = new StringBuilder();
        Random r = new Random();
        for (int i = 0; i < 9; ++i) {
            sb.append(r.nextInt(9));
        }
        return sb.toString();
    }


    public static String getSign(String token, String timestamp, String nonce) {
        String sign = "";
        String[] arr = new String[]{token, timestamp, nonce};
        Arrays.sort(arr);
        StringBuilder buff = new StringBuilder();
        for (String anArr : arr) {
            buff.append(anArr);
        }
        try {
            MessageDigest var9 = MessageDigest.getInstance("SHA-1");
            var9.update(buff.toString().getBytes());
            sign = DataUtils.byte2hex(var9.digest());
        } catch (NoSuchAlgorithmException var8) {
            var8.printStackTrace();
        }
        return sign;
    }


    public static String getSerialNumber() {
        String serial = "";
        try {
            Class e1 = Class.forName("android.os.SystemProperties");
            Method get = e1.getMethod("get", String.class);
            serial = (String) get.invoke(e1, "ro.serialno");
        } catch (Exception var3) {
            var3.printStackTrace();
        }
        return serial;
    }

    public static String getEnPassword(String password) {
        return DataUtils.getMD5Str(DataUtils.getMD5Str(DataUtils.getMD5Str(password)));
    }
}
