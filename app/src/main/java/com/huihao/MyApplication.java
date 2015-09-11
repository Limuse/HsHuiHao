package com.huihao;

import android.content.Context;
import android.content.Intent;

import com.huihao.activity.LoginMain;
import com.leo.base.application.LApplication;
import com.leo.base.util.LSharePreference;
import com.leo.base.util.T;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by admin on 2015/6/29.
 */
public class MyApplication extends LApplication {
    private static final String TAG = "JPush";

    private static MyApplication instance;

    public static Context applicationContext;

    private static boolean isLog = true;

    public static boolean isLog() {
        return isLog;
    }

    public static void setIsLog(boolean isLog) {
        MyApplication.isLog = isLog;
    }

    public static int Hight, width;

    public void onCreate() {
        super.onCreate();

        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);

        ImageLoaderConfiguration configuration = ImageLoaderConfiguration
                .createDefault(this);
        setImageLoader(configuration);
        applicationContext = this;
        instance = this;
    }

    public static synchronized MyApplication get() {
        if (instance == null) {
            instance = (MyApplication) getInstance();
        }
        return instance;
    }

    public static boolean isLogin(Context context) {
        if (LSharePreference.getInstance(context).getBoolean("login")) {
            return true;
        } else {
            T.ss("请登录后操作");
            Intent intent = new Intent(context, LoginMain.class);
            context.startActivity(intent);
            return LSharePreference.getInstance(context).getBoolean("login");
        }
    }
    public static MyApplication getInstance() {
        return instance;
    }


}
