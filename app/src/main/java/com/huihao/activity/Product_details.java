package com.huihao.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.huihao.R;
import com.huihao.common.Log;
import com.huihao.custom.TextViewVertical;
import com.huihao.fragment.Fragment_story_details;
import com.leo.base.activity.LActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2015/7/29.
 */
public class Product_details extends LActivity {

    protected void onLCreate(Bundle bundle) {
        setContentView(R.layout.activity_story_details);
        initView();
        initData();
    }

    private void initView() {
    }

    private void initData() {
    }

    private void initPage() {
    }
}
