package com.huihao.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.huihao.R;
import com.huihao.activity.LoginMain;
import com.huihao.activity.Product_details;
import com.huihao.adapter.MainGridAda;
import com.huihao.custom.ImageCycleView;
import com.huihao.custom.NoScrollGridView;
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
    private NoScrollGridView gridView;
    private List<Map<String, String>> gridList = new ArrayList<Map<String, String>>();
    private MainGridAda myGridAda;
    private RelativeLayout r1, r2, r3, r4, r5;

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
        initData();
        initView();
        initClick();
        initAda();
    }

    private void initClick() {
        r1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            Intent intent=new Intent(getActivity(),LoginMain.class);
                startActivity(intent);
            }
        });
        r2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });
        r3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });
        r4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });
        r5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });


    }


    private void initView() {
        r1 = (RelativeLayout) parentView.findViewById(R.id.product1);
        r2 = (RelativeLayout) parentView.findViewById(R.id.product2);
        r3 = (RelativeLayout) parentView.findViewById(R.id.product3);
        r4 = (RelativeLayout) parentView.findViewById(R.id.product4);
        r5 = (RelativeLayout) parentView.findViewById(R.id.product5);


        mAdView = (ImageCycleView) parentView.findViewById(R.id.ImageCycleView);
        mAdView.setImageResources(mImageUrl, mImageName, mAdCycleViewListener);
        gridView = (NoScrollGridView) parentView.findViewById(R.id.gridView);
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
            map.put("price", "￥" + i);
            map.put("image", "http://img1.3lian.com/img2011/w1/106/1/46.jpg");
            gridList.add(map);
        }
    }

    private void initAda() {
        myGridAda = new MainGridAda(getActivity(), gridList);
        gridView.setAdapter(myGridAda);
    }

    private ImageCycleView.ImageCycleViewListener mAdCycleViewListener = new ImageCycleView.ImageCycleViewListener() {
        public void onImageClick(int position, View imageView) {
            Intent intent = new Intent(getActivity(), Product_details.class);
            startActivity(intent);
        }
    };
}
