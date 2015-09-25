package com.huihao.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.huihao.R;
import com.huihao.adapter.NewsPager;
import com.huihao.common.SystemBarTintManager;
import com.huihao.custom.PagerSlidingTabStrip;
import com.leo.base.activity.LActivity;
import com.leo.base.util.LSharePreference;

/**
 * Created by huisou on 2015/8/10.
 * 消息
 */
public class News extends LActivity {
    private PagerSlidingTabStrip tabStrip;
    private ViewPager viewPager;
    private NewsPager pagerAdapter;
    @Override
    protected void onLCreate(Bundle bundle) {
        setContentView(R.layout.activity_news);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }

        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(R.color.app_white);
        Boolean bols= LSharePreference.getInstance(this).getBoolean("login");
        if(bols==true){
            initView();
        }else{
            Intent intent = new Intent(this, LoginMain.class);
            startActivity(intent);
        }

    }

    private void initView() {

        Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        toolbar.setTitle("消息");
        toolbar.setBackgroundColor(getResources().getColor(R.color.app_white));
        toolbar.setNavigationIcon(R.mipmap.right_too);//左边图标
        //左边图标点击事件
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
        toolbar.setTitleTextColor(getResources().getColor(R.color.app_text_dark));

        tabStrip = (PagerSlidingTabStrip) findViewById(R.id.tab_strip);
        viewPager = (ViewPager) findViewById(R.id.view_pager);


        pagerAdapter = new NewsPager(this, getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        tabStrip.setViewPager(viewPager);


    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }
}
