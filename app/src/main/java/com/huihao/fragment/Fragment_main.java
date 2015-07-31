package com.huihao.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.huihao.R;
import com.huihao.adapter.MainGridAda;
import com.huihao.common.Bar;
import com.huihao.custom.ImageCycleView;
import com.huihao.custom.MyGridView;
import com.leo.base.activity.fragment.LFragment;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2015/6/26.
 */


public class Fragment_main extends LFragment {
    private View parentView;
    private ImageCycleView mAdView;
    private ArrayList<String> mImageUrl = null;
    private ArrayList<String> mImageName = null;
    private MyGridView gridView;
    private List<Map<String, String>> gridList = new ArrayList<Map<String, String>>();
    private MainGridAda myGridAda;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.fragment_main,
                container, false);
        return parentView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initBar();
        initData();
        initView();
        initAda();
    }

    private void initBar() {
        Toolbar toolbar = (Toolbar) parentView.findViewById(R.id.toolbar);
        toolbar.setTitle("HUIHAO");
        toolbar.setTextAlignment(View.TEXT_ALIGNMENT_GRAVITY);
        toolbar.setTitleTextColor(getResources().getColor(R.color.app_green));
        toolbar.setBackgroundColor(Color.WHITE);
    }

    private void initView() {
        mAdView = (ImageCycleView) parentView.findViewById(R.id.ImageCycleView);
        mAdView.setImageResources(mImageUrl, mImageName, mAdCycleViewListener);
        gridView = (MyGridView) parentView.findViewById(R.id.gridView);
    }

    private void initData() {
        mImageUrl = new ArrayList<String>();
        mImageUrl.add("http://imgsrc.baidu.com/forum/w%3D580/sign=d2391eb40846f21fc9345e5bc6256b31/8381b4c379310a55b6ea8ebbb54543a9802610ed.jpg");
        mImageUrl.add("http://s5.tuanimg.com/deal/deal_image/3029/2612/medium/webp-23642600-7f0d-4478-8345-ddecfa91a2ce.jpg");
        mImageUrl.add("http://img2.duitang.com/uploads/item/201302/19/20130219115924_ZLNnS.thumb.600_0.jpeg");
        mImageName = new ArrayList<String>();
        mImageName.add("1");
        mImageName.add("2");
        mImageName.add("3");


        for (int i = 0; i < 10; i++) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("name", "把根留住");
            map.put("price", "￥"+i);
            map.put("image", "http://img1.3lian.com/img2011/w1/106/1/46.jpg");
            gridList.add(map);
        }
    }

    private void initAda() {
        myGridAda = new MainGridAda(getActivity(),gridList);
        gridView.setAdapter(myGridAda);
    }

    private ImageCycleView.ImageCycleViewListener mAdCycleViewListener = new ImageCycleView.ImageCycleViewListener() {
        public void onImageClick(int position, View imageView) {
            Toast.makeText(getActivity(), position+"", Toast.LENGTH_SHORT).show();
        }
    };
}
