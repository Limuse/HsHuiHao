package com.huihao.activity;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.huihao.R;
import com.huihao.adapter.MainGridAda;
import com.huihao.adapter.ProductGridAda;
import com.huihao.common.Bar;
import com.huihao.common.SystemBarTintManager;
import com.huihao.custom.ImageCycleView;
import com.huihao.custom.MyGridView;
import com.huihao.custom.PagerSlidingTabStrip;
import com.leo.base.activity.LActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2015/7/29.
 */
public class Product_details extends LActivity {
    private View parentView;

    private MyGridView gridView;
    private List<Map<String, String>> gridList = new ArrayList<Map<String, String>>();
    private ProductGridAda myGridAda;

    private ArrayList<String> mImageUrl = null;
    private ArrayList<String> mImageName = null;
    private TextView tv_old;
    private ImageCycleView mAdView;

    private PagerSlidingTabStrip tabStrip;
    private ViewPager viewPager;
    protected void onLCreate(Bundle bundle) {
        parentView=getLayoutInflater().inflate(R.layout.activity_product_details,null);
        setContentView(parentView);
        Bar.setTrans(this);
        initData();
        initView();
        initAda();
    }
    private void initAda() {
        myGridAda = new ProductGridAda(this,gridList);
        gridView.setAdapter(myGridAda);

    }
    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(Color.parseColor("#00ffffff"));
        toolbar.setNavigationIcon(R.mipmap.back_circle);
        toolbar.inflateMenu(R.menu.menu_product_details);

        mAdView = (ImageCycleView) findViewById(R.id.ImageCycleView);
        mAdView.setImageResources(mImageUrl, mImageName, mAdCycleViewListener);
        tv_old=(TextView)findViewById(R.id.tv_money_old);
        tv_old.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG );
        gridView = (MyGridView) parentView.findViewById(R.id.gridView);

        tabStrip = (PagerSlidingTabStrip) findViewById(R.id.tab_strip);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
//        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
//            public Fragment getItem(int position) {
//                Fragment fragment = null;
//                switch (position) {
//                    case 0:
//                        fragment = Prodict_info.newInstance();
//                        break;
//                    case 1:
//                        fragment = Fragment_Parameter.newInstance();
//                        break;
//                }
//                return fragment;
//            }
//            public int getCount() {
//                return 0;
//            }
//        });
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

        for (int i = 0; i <4; i++) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("name", "不要钱");
            gridList.add(map);
        }
    }

    private void initPage() {
    }

    private ImageCycleView.ImageCycleViewListener mAdCycleViewListener = new ImageCycleView.ImageCycleViewListener() {
        @Override
        public void onImageClick(int position, View imageView) {
            Toast.makeText(Product_details.this, position+"", Toast.LENGTH_SHORT).show();
        }
    };

}
