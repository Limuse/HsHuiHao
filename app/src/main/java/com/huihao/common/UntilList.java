package com.huihao.common;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by admin on 2015/8/10.
 */
public class UntilList {
    public static String getAppInfo(Context context) {
        try {
            String pkName = context.getPackageName();
            String versionName = context.getPackageManager().getPackageInfo(
                    pkName, 0).versionName;
            int versionCode = context.getPackageManager()
                    .getPackageInfo(pkName, 0).versionCode;
            return pkName + "   " + versionName + "  " + versionCode;
        } catch (Exception e) {
        }
        return null;
    }

    public static float getWindosH(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        float width = wm.getDefaultDisplay().getWidth();
        float height = wm.getDefaultDisplay().getHeight();
        return height;
    }

    public static float getWindosW(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        float width = wm.getDefaultDisplay().getWidth();
        float height = wm.getDefaultDisplay().getHeight();
        return width;
    }


    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static boolean isPhone(String phone) {
        String check = phone;
        Pattern p = Pattern
                .compile("^((13[0-9])|(147)|(17[7,8])|(15[0-9])|(18[0-9]))\\d{8}$");
        Matcher m = p.matcher(check);
        return m.find();
    }

}
