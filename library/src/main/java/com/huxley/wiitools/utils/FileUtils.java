package com.huxley.wiitools.utils;

import com.huxley.wiitools.WiiTools;

import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * File 工具类
 * Created by huxley on 2017/4/20.
 */
public class FileUtils {

    public static File getFile(String name) {
        return new File(WiiTools.instance.mContext.getFilesDir(), name);
    }

    public static boolean deleteFile(File file) {
        return file.delete();
    }

    public static void writeThrowable(File file, String tag, String message, Throwable tr) {
        if (!checkOrCreateFile(file)) {
            return;
        }
        String time = DateUtils.getCurrentTime("MM-dd HH:mm:ss.SSS");
        synchronized (file) {
            FileWriter fileWriter = null;
            BufferedWriter bufdWriter = null;
            PrintWriter printWriter = null;
            try {
                fileWriter = new FileWriter(file, true);
                bufdWriter = new BufferedWriter(fileWriter);
                printWriter = new PrintWriter(fileWriter);
                bufdWriter.append(time).append(" ").append("E").append('/').append(tag).append(" ")
                        .append(message).append('\n');
                bufdWriter.flush();
                tr.printStackTrace(printWriter);
                printWriter.flush();
                fileWriter.flush();
            } catch (IOException e) {
                closeQuietly(fileWriter, bufdWriter, printWriter);
            }
        }
    }

    public static boolean checkOrCreateFile(File file) {
        if (file == null) {
            return false;
        }
        if (!file.getParentFile().mkdirs()) {
            return false;
        }
        if (file.exists()) {
            return true;
        }
        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 安静关闭IO
     */
    public static void closeQuietly(Closeable... closeables) {
        if (closeables == null) return;
        try {
            for (Closeable closeable : closeables) {
                if (closeable != null) {
                    closeable.close();
                }
            }
        } catch (IOException ignored) {
        }
    }
}
