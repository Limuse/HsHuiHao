package com.huihao;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;

import com.leo.base.application.LApplication;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;

/**
 * Created by admin on 2015/6/29.
 */
public class MyApplication extends LApplication {

    private static MyApplication instance;

    public static Context applicationContext;

    private static boolean isLog = true;

    public static boolean isLog() {
        return isLog;
    }

    public static void setIsLog(boolean isLog) {
        MyApplication.isLog = isLog;
    }


    public void onCreate() {
        super.onCreate();

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

    public static MyApplication getInstance() {
        return instance;
    }

}
