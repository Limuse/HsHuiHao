package com.huihao.activity;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.huihao.R;
import com.huihao.common.Log;
import com.huihao.common.SystemBarTintManager;
import com.huihao.fragment.Fragment_main;
import com.huihao.fragment.Fragment_main2;
import com.huihao.fragment.Fragment_main3;
import com.huihao.fragment.Fragment_main4;
import com.huihao.fragment.Fragment_main5;
import com.huihao.handle.ActivityHandler;
import com.leo.base.activity.LActivity;
import com.leo.base.net.LReqEntity;
import com.leo.base.util.T;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import static com.huihao.R.color.app_toolbar;

/**
 * Created by admin on 2015/6/26.
 */

/**
 * Tag
 */
public class HomeMain extends LActivity {
    @InjectView(R.id.find)
    Button find;
    @InjectView(R.id.friendmsg)
    Button friendmsg;
    @InjectView(R.id.releaseto)
    Button releaseto;
    @InjectView(R.id.my)
    Button my;
    @InjectView(R.id.other)
    Button other;

    private Fragment_main fragment1;
    private Fragment_main2 fragment2;
    private Fragment_main3 fragment3;
    private Fragment_main4 fragment4;
    private Fragment_main5 fragment5;

    public static final String a = "1";
    public static final String b = "2";
    public static final String c = "3";
    public static final String d = "4";
    public static final String e = "5";

    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;

    private String hideTag;

    protected void onLCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_home);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.inject(this);
        initBar();
        initView();
    }
    @OnClick(R.id.find)
    void find() {
        if(hideTag.equals(a))
            return;
        if (fragment1 == null) {
            fragment1 = new Fragment_main();
        }
        switchFragment(fragment1, a);
    }

    @OnClick(R.id.friendmsg)
    void friendmsg() {
        if(hideTag.equals(b))
            return;
        if (fragment2 == null) {
            fragment2 = new Fragment_main2();
        }
        switchFragment(fragment2, b);
    }

    @OnClick(R.id.releaseto)
    void releaseto() {
        if(hideTag.equals(c))
            return;
        if (fragment3 == null) {
            fragment3 = new Fragment_main3();
        }
        switchFragment(fragment3, c);
    }

    @OnClick(R.id.my)
    void my() {
        if(hideTag.equals(d))
            return;
        if (fragment4 == null) {
            fragment4 = new Fragment_main4();
        }
        switchFragment(fragment4, d);
    }

    @OnClick(R.id.other)
    void other() {
        if(hideTag.equals(e))
            return;
        if (fragment5 == null) {
            fragment5 = new Fragment_main5();
        }
        switchFragment(fragment5, e);
    }

    private void initView() {
        fragment1 = new Fragment_main();
//        find.setBackgroundResource(R.drawable.tab_findn);
        switchFragment(fragment1, a);
    }

    private void switchFragment(Fragment fragment, String tag) {
        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();

        Fragment tagFragment = mFragmentManager.findFragmentByTag(tag);

        if (tagFragment == null) {
            mFragmentTransaction.add(R.id.tabcontent, fragment, tag);
        } else {
            mFragmentTransaction.show(tagFragment);
        }

        tagFragment = mFragmentManager.findFragmentByTag(hideTag);

        if (tagFragment != null) {
            mFragmentTransaction.hide(tagFragment);
        }

        hideTag = tag;
        mFragmentTransaction.commit();
    }

    private void initBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window win = getWindow();
            WindowManager.LayoutParams winParams = win.getAttributes();
            final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            winParams.flags |= bits;
            win.setAttributes(winParams);
        }
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(R.color.app_toolbar);
        // tintManager.setStatusBarTintResource(R.drawable.ic_launcher);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

}