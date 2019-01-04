package com.siasun.tech.servicerobot.edu.tools;

import android.util.Log;

import com.siasun.tech.servicerobot.edu.BuildConfig;


/**
 * Created by jian.shi on 2018/7/12.
 *
 * @Email shijian1@siasun.com
 */
public class SLog {
    static long time;
    private static final int V = 1;
    private static final int D = 2;
    private static final int I = 3;
    private static final int W = 4;
    private static final int E = 5;

    public SLog() {
    }

    public static void time(String tag) {
        if (time == 0L) {
            time = System.currentTimeMillis();
        } else {
            d(tag + " cost: " + (System.currentTimeMillis() - time));
            time = 0L;
        }

    }

    public static void v(String msg) {
        log(1, null, msg);
    }

    public static void d(String msg) {
        log(2, null, msg);
    }

    public static void i(String msg) {
        log(3, null, msg);
    }

    public static void w(String msg) {
        log(4, null, msg);
    }

    public static void e(String msg) {
        log(5, null, msg);
    }

    public static void v(String tag, String msg) {
        log(1, tag, msg);
    }

    public static void d(String tag, String msg) {
        log(2, tag, msg);
    }

    public static void i(String tag, String msg) {
        log(3, tag, msg);
    }

    public static void w(String tag, String msg) {
        log(4, tag, msg);
    }

    public static void w(String tag, String msg, Throwable tr) {
        log(4, tag, msg + "\n" + Log.getStackTraceString(tr));
    }

    public static void e(String tag, String msg) {
        log(5, tag, msg);
    }

    public static void e(String tag, String msg, Throwable tr) {
        log(5, tag, msg + "\n" + Log.getStackTraceString(tr));
    }

    private static void log(int type, String tag, String msg) {
        if (BuildConfig.DEBUG) {
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            int index = 4;
            String className = stackTrace[index].getFileName();
            String methodName = stackTrace[index].getMethodName();
            int lineNumber = stackTrace[index].getLineNumber();
            if (tag == null) {
                tag = className;
            }
            StringBuilder builder = new StringBuilder();
            builder.append("[ (").append(className).append(":").append(lineNumber).append(")#").append
                    (methodName).append(" ]");

            if (msg != null) {
                builder.append(msg);
            }

            String logStr = builder.toString();

            switch (type) {
                case V:
                    Log.v(tag, logStr);
                    break;
                case D:
                    Log.d(tag, logStr);
                    break;
                case I:
                    Log.i(tag, logStr);
                    break;
                case W:
                    Log.w(tag, logStr);
                    break;
                case E:
                    Log.e(tag, logStr);
            }

        }
    }
}
