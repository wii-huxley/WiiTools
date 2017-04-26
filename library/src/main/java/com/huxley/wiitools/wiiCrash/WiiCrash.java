package com.huxley.wiitools.wiiCrash;

import android.os.Looper;
import android.support.annotation.NonNull;

import com.huxley.wiitools.utils.FileUtils;

import java.io.File;

/**
 * Created by huxley on 2017/4/20.
 */
public class WiiCrash implements Thread.UncaughtExceptionHandler {

    private static WiiCrash instance;

    private File                 mLogFile;
    private AbstractCrashHandler mReporter;

    private WiiCrash() {
    }

    public static WiiCrash getInstance() {
        if (instance == null) {
            synchronized (WiiCrash.class) {
                if (instance == null) {
                    instance = new WiiCrash();
                }
            }
        }
        return instance;
    }

    public void init(@NonNull AbstractCrashHandler reporter) {
        init("WiiCrash.log", reporter);
    }

    public void init(@NonNull String fileName, @NonNull AbstractCrashHandler reporter) {
        mLogFile = FileUtils.getFile(fileName);
        this.mReporter = reporter;
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(final Thread thread, final Throwable ex) {
        FileUtils.writeThrowable(mLogFile, "CrashHandler", ex.getMessage(), ex);
        mReporter.sendFile(mLogFile);
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                mReporter.closeApp(thread, ex);
                Looper.loop();
            }
        }).start();
    }
}