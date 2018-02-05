package com.huxley.wiitools.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.huxley.wiitools.WiiTools;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import static android.content.Context.TELEPHONY_SERVICE;

/**
 * Created by huxley on 2017/8/9.
 */

public class Tools {

    /**
     * 返回当前程序版本名
     */
    public static String getVersionName() {
        String versionName = "";
        try {
            // —get the package info—
            PackageManager pm = WiiTools.getInstance().mContext.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(WiiTools.getInstance().mContext.getPackageName(), 0);
            versionName = pi.versionName;
            if (StringUtils.isEmpty(versionName)) {
                return "";
            }
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return versionName;
    }


    public static String getDeviceName() {
        if (StringUtils.isEmpty(Build.DEVICE)) {
            return "";
        }
        return Build.DEVICE;
    }


    public static String getIpAddress() {
        try {
            for (Enumeration<NetworkInterface> enNetI = NetworkInterface.getNetworkInterfaces();
                 enNetI.hasMoreElements(); ) {
                NetworkInterface netI = enNetI.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = netI.getInetAddresses();
                     enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (inetAddress instanceof Inet4Address && !inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException e) {
            return "0.0.0.0";
        }
        return "0.0.0.0";
    }


    public static String getMacAddress() {
        String macSerial = "";
        try {
            Process pp = Runtime.getRuntime().exec("cat /sys/class/net/wlan0/address");
            InputStreamReader ir = new InputStreamReader(pp.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            String line;
            while ((line = input.readLine()) != null) {
                macSerial += line.trim();
            }
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return macSerial;
    }


    public static String getAppName() {
        PackageManager packageManager = null;
        ApplicationInfo applicationInfo = null;
        try {
            packageManager = WiiTools.getInstance().mContext.getPackageManager();
            applicationInfo = packageManager.getApplicationInfo(
                WiiTools.getInstance().mContext.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            applicationInfo = null;
        }
        return (String) packageManager.getApplicationLabel(applicationInfo);
    }


    public static String getSzImei() {
        TelephonyManager telephonyManager
            = (TelephonyManager) WiiTools.getInstance().mContext.getSystemService(
            TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();
    }
}
