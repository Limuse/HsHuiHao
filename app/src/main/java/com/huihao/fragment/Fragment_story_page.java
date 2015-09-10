package com.huihao.fragment;

import android.app.Fragment;
import android.content.Intent;
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
import com.leo.base.util.T;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2015/6/26.
 */

public class Fragment_story_page extends LFragment {
    private ViewPager viewPager;
    private ArrayList<Fragment> fragmentList = new ArrayList<Fragment>();
    private Map<String, String> imageInfo = new HashMap<String, String>();
    private String info;
    private View total;
    private ImageView image;

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
                .cacheOnDisk(true)
                .considerExifParams(true)
                 .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                .displayer(new FadeInBitmapDisplayer(200))
                .build();

        initView();
        initData();
    }

    private void initView() {
        image = (ImageView) total.findViewById(R.id.page_image);
    }

    private void initData() {
        if (!imageInfo.get("image").equals("")) {
            imageLoader.displayImage(imageInfo.get("image"), image, options);
            image.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (imageInfo.containsKey("id")) {
                        Intent intent = new Intent(getActivity(), Story_details.class);
                        intent.putExtra("id", imageInfo.get("id"));
                        startActivity(intent);
                    }
                }
            });
        } else {
            image.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    T.ss("加载中，请稍后");
                }
            });
        }
    }

    public void getData(Map<String, String> map) {
        this.imageInfo = map;
    }
}
