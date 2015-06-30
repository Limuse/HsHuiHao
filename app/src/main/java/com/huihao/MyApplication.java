package com.huihao;

/**
 * Created by admin on 2015/6/29.
 */
public class MyApplication {
    private static boolean isLog = true;

    public static boolean isLog() {
        return isLog;
    }

    public static void setIsLog(boolean isLog) {
        MyApplication.isLog = isLog;
    }
}
