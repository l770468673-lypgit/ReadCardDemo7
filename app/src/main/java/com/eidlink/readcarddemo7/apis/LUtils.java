package com.eidlink.readcarddemo7.apis;

import android.util.Log;

/**
 * @
 * @类名称: ${type_name}
 * @类描述: $
 * @创建人：lyp
 * @创建时间：${date} ${time}
 * log输出
 * @备注：
 */
public class LUtils {
    private static String logContent;


    public static void showlogcat(String tag, String msg) {
        if (tag == null || tag.length() == 0 || msg == null || msg.length() == 0)
            return;
        int segmentSize = 9 * 1024;
        long length = msg.length();
        if (length <= segmentSize) {// 长度小于等于限制直接打印 Log.e(tag, msg);
        } else {
            while (msg.length() > segmentSize) {// 循环分段打印日志 String
                logContent = msg.substring(0, segmentSize);
                msg = msg.replace(logContent, "");
                Log.e(tag, logContent);
            }
            LUtils.d(tag, msg);// 打印剩余日志  } }

        }
    }

    /**
     * 日志输出级别
     */
    private static final int VERBOSE = 1;
    private static final int DEBUG = 2;
    private static final int INFO = 3;
    private static final int WARM = 4;
    private static final int ERROR = 5;

    /**
     * 当前允许输出的日志级别
     */
    private static final int OUT_STATE = 0;

    public static void v(String tag, String str) {
        if (OUT_STATE <= VERBOSE) {
            Log.v(tag, str);
        }
    }

    public static void d(String tag, String str) {
        if (OUT_STATE <= DEBUG) {
            Log.d(tag, str);
        }
    }

    public static void i(String tag, String str) {
        if (OUT_STATE <= INFO) {
            Log.i(tag, str);
        }
    }

    public static void w(String tag, String str) {
        if (OUT_STATE <= WARM) {
            Log.w(tag, str);
        }
    }


    public static void e(String tag, String str) {
        if (OUT_STATE <= ERROR) {
            Log.e(tag, str);
        }
    }

}
