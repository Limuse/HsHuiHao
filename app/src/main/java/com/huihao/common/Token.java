package com.huihao.common;

import android.content.Context;

import com.leo.base.util.LSharePreference;

/**
 * Created by admin on 2015/9/6.
 */
public class Token {


    public static String get(Context context) {
        return LSharePreference.getInstance(context).getString("token");
    }

    public static void set(Context context, String token) {
        LSharePreference.getInstance(context).setString("token", token);
    }
}
