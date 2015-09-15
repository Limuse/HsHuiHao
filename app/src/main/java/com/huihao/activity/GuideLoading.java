package com.huihao.activity;

import java.util.ArrayList;
import java.util.List;

import com.huihao.common.DepthPageTransformer;
import com.huihao.R;
import com.leo.base.activity.LActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import cn.jpush.android.api.JPushInterface;

public class GuideLoading extends LActivity {
    private Button start_future;
    private ViewPager viewPager;
    private int[] imageIds = new int[]{R.mipmap.guide_one,
            R.mipmap.guide_two, R.mipmap.guide_three};
    private List<ImageView> imageList = new ArrayList<ImageView>();
    private SharedPreferences sp;
    private Editor editor;
    private boolean isFirstIn = true;
    private ImageView load;

    @Override
    protected void onLCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_guide_loading);

        InitView();
        initLoad();
    }

    private void initLoad() {
        sp = getSharedPreferences("pref", 0);
        isFirstIn = sp.getBoolean("first_pref", true);
        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(3000);
                    if (!isFirstIn) {
                        handler.sendEmptyMessage(2);
                    } else {
                        handler.sendEmptyMessage(1);
                        handler.sendEmptyMessage(3);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (msg.what == 1) {
                load.setVisibility(View.GONE);
            }
            if (msg.what == 2) {
                getJumpIntent(HomeMain.class);
            }
            if (msg.what == 3) {
                initData();
                initViewPage();
                initClick();
            }
        }

        ;
    };

    private void InitView() {
        load = (ImageView) findViewById(R.id.load);
        start_future = (Button) findViewById(R.id.start_future);
        start_future.setVisibility(View.GONE);
        viewPager = (ViewPager) findViewById(R.id.viewpage);
    }

    private void initData() {
        for (int i = 0; i < imageIds.length; i++) {
            ImageView imageView = new ImageView(getApplicationContext());
            imageView.setScaleType(ScaleType.CENTER_CROP);
            imageView.setImageResource(imageIds[i]);
            imageList.add(imageView);
        }
    }

    private void initViewPage() {
        viewPager.setPageTransformer(true, new DepthPageTransformer());
        viewPager.setAdapter(new PagerAdapter() {

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(imageList.get(position));
                return imageList.get(position);
            }

            @Override
            public void destroyItem(ViewGroup container, int position,
                                    Object object) {

                container.removeView(imageList.get(position));
            }

            @Override
            public int getCount() {
                return imageList.size();
            }
        });
    }

    private void initClick() {
        viewPager.addOnPageChangeListener(new OnPageChangeListener() {

            public void onPageSelected(int position) {
                if (position == 2) {
                    start_future.setVisibility(View.VISIBLE);
                } else {
                    start_future.setVisibility(View.GONE);
                }
            }

            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            public void onPageScrollStateChanged(int arg0) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
    }

    public void start(View v) {
        editor = sp.edit();
        editor.putBoolean("first_pref", false).commit();
        getJumpIntent(HomeMain.class);
    }

    private void getJumpIntent(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        this.startActivity(intent);
        this.finish();
    }
}