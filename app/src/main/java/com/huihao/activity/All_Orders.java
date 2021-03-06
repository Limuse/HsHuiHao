package com.huihao.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.view.Window;
import android.annotation.TargetApi;

import com.huihao.R;
import com.huihao.adapter.MyAllOrderMainPager;
import com.huihao.common.Log;
import com.huihao.common.SystemBarTintManager;
import com.huihao.custom.PagerSlidingTabStrip;
import com.leo.base.activity.LActivity;
import com.leo.base.util.L;
import com.leo.base.util.LSharePreference;

/**
 * Created by huisou on 2015/7/31.
 */
public class All_Orders extends LActivity {
    private PagerSlidingTabStrip tabStrip;
    private ViewPager viewPager;
    private MyAllOrderMainPager pagerAdapter;
    public static All_Orders instance = null;
    @Override
    protected void onLCreate(Bundle bundle) {
        setContentView(R.layout.fragment_allorder);
        instance=this;
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
        toolbar.setTitle("我的订单");
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


        pagerAdapter = new MyAllOrderMainPager(this, getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        String t = getIntent().getStringExtra("gets");
        int p = Integer.parseInt(t);
        viewPager.setCurrentItem(p);
        tabStrip.setViewPager(viewPager);
        tabStrip.setCheckTab(p);
        tabStrip.invalidate();

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
