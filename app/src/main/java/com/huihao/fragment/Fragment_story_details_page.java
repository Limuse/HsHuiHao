package com.huihao.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.huihao.MyApplication;
import com.huihao.R;
import com.huihao.activity.Story_details;
import com.leo.base.activity.fragment.LFragment;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 2015/6/26.
 */

public class Fragment_story_details_page extends LFragment {
    private ViewPager viewPager;
    private ArrayList<Fragment> fragmentList = new ArrayList<Fragment>();
    public static Map<String, String> imageInfo = new HashMap<String, String>();
    private String info;
    private View total;
    public static ImageView image;

    private DisplayImageOptions options;
    private ImageLoader imageLoader;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        container = (ViewGroup) inflater.inflate(
                R.layout.fragment_story_page_view, null);
        total = inflater.inflate(R.layout.fragment_story_page, null);
        container.addView(total);
        return container;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (imageLoader == null) {
            imageLoader = MyApplication.getInstance().getImageLoader();
        }
        options = new DisplayImageOptions.Builder().cacheInMemory(true)
                .cacheOnDisk(true).bitmapConfig(Bitmap.Config.RGB_565)
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                .build();

        initView();
        initData();
    }

    private void initView() {
        image = (ImageView) total.findViewById(R.id.page_image);
    }

    public void initData() {
            imageLoader.displayImage(imageInfo.get("image"), image, options);
    }

    public static void getData(Map<String, String> map) {
        imageInfo = map;
    }
}
