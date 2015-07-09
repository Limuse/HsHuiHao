package com.huihao.common;

import com.huihao.MyApplication;
import com.leo.base.util.L;

/**
 * Created by admin on 2015/6/29.
 */

public class Log {
    private static boolean isLog = MyApplication.isLog();


    public static void e(String msg) {
        if (isLog) {
            L.e(msg);
        }
    }

    public static void i(String msg) {
        if (isLog) {
            L.i(msg);
        }
    }

    public static void d(String msg) {
        if (isLog) {
            L.d(msg);
        }
    }

    public static void v(String msg) {
        if (isLog) {
            L.v(msg);
        }
    }

    public static void i(int msg) {
        if (isLog) {
            L.i(msg);
        }
    }

    public static void d(int msg) {
        if (isLog) {
            L.d(msg);
        }
    }

    public static void e(int msg) {
        if (isLog) {
            L.e(msg);
        }
    }

    public static void v(int msg) {
        if (isLog) {
            L.v(msg);
        }
    }
}

