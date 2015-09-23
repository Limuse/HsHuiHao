package com.huihao;

import android.content.Context;
import android.content.Intent;

import com.huihao.activity.LoginMain;
import com.huihao.common.Log;
import com.huihao.common.UntilList;
import com.leo.base.application.LApplication;
import com.leo.base.util.LSharePreference;
import com.leo.base.util.T;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;


import cn.jpush.android.api.JPushInterface;

/**
 * Created by admin on 2015/6/29.
 */
public class MyApplication extends LApplication {
    private static final String TAG = "JPush";

    private static MyApplication instance;

    public static Context applicationContext;

    private static boolean isLog = false;

    public static boolean isLog() {
        return isLog;
    }

    public static void setIsLog(boolean isLog) {
        MyApplication.isLog = isLog;
    }

    public static int Hight, width;

    public void onCreate() {
        super.onCreate();

        //初始化推送
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        //初始化图片加载
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .memoryCacheExtraOptions((int)UntilList.getWindosW(this), (int)UntilList.getWindosH(this))
//                .memoryCacheExtraOptions(UntilList.getWindosW(this)*2, (UntilList.getWindosW(this)/(640/600))) // max width, max height
                .threadPoolSize(2) //线程池内加载的数量
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator()) //将保存的时候的URI名称用MD5 加密
                .memoryCache(new WeakMemoryCache()) // You can pass your own memory cache implementation/你可以通过自己的内存缓存实现
                .memoryCacheSize(10 * 1024 * 1024) // 内存缓存的最大值
                .diskCacheExtraOptions(640, 600, null)
                .diskCacheSize(100 * 1024 * 1024)  // 50 Mb sd卡(本地)缓存的最大值
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                        // 由原先的discCache -> diskCache
                .imageDownloader(new BaseImageDownloader(this, 5 * 1000, 30 * 1000)) // connectTimeout (5 s), readTimeout (30 s)超时时间
                .writeDebugLogs() // Remove for release app
                .build();
        setImageLoader(config);


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
