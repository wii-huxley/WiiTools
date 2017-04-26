package com.huxley.wiitools.wiiCrash;

import java.io.File;

/**
 * 崩溃监听
 */
public interface CrashListener {

    /**
     * 发送日志文件
     */
    void sendFile(File file);

    /**
     * 退出APP
     */
    void closeApp(Thread thread, Throwable ex);
}