package com.huihao.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.huihao.R;
import com.huihao.common.Log;
import com.huihao.common.SystemBarTintManager;
import com.huihao.fragment.Fragment_main;
import com.huihao.fragment.Fragment_shop;
import com.huihao.fragment.Fragment_my;
import com.huihao.fragment.Fragment_message;
import com.huihao.fragment.Fragment_huihao;
import com.leo.base.activity.LActivity;
import com.umeng.message.PushAgent;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

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

    private Fragment_main fragment_main;
    private Fragment_shop fragment_shop;
    private Fragment_my fragment_my;
    private Fragment_huihao fragment_huihao;
    private Fragment_message fragment_message;

    public static final String MAIN = "main";
    public static final String SHOP = "shop";
    public static final String MY = "my";
    public static final String HUIHAO = "huihao";
    public static final String MESSAGE = "message";

    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;

    private String hideTag;

    protected void onLCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_home);
        ButterKnife.inject(this);
        initBar();
        initPush();
        initView();
    }

    private void initPush() {
        PushAgent mPushAgent = PushAgent.getInstance(HomeMain.this);
        mPushAgent.enable();
        Log.e(mPushAgent.isEnabled() + "");
    }

    @OnClick(R.id.find)
    void find() {
        if (hideTag.equals(MAIN))
            return;
        if (fragment_main == null) {
            fragment_main = new Fragment_main();
        }
        switchFragment(fragment_main, MAIN);
    }

    @OnClick(R.id.friendmsg)
    void friendmsg() {
        if (hideTag.equals(SHOP))
            return;
        if (fragment_shop == null) {
            fragment_shop = new Fragment_shop();
        }
        switchFragment(fragment_shop, SHOP);
    }

    @OnClick(R.id.releaseto)
    void releaseto() {
        if (hideTag.equals(MY))
            return;
        if (fragment_my == null) {
            fragment_my = new Fragment_my();
        }
        switchFragment(fragment_my, MY);
    }

    @OnClick(R.id.my)
    void my() {
        if (hideTag.equals(HUIHAO))
            return;
        if (fragment_message == null) {
            fragment_message = new Fragment_message();
        }
        switchFragment(fragment_message, HUIHAO);
    }

    @OnClick(R.id.other)
    void other() {
        if (hideTag.equals(MESSAGE))
            return;
        if (fragment_huihao == null) {
            fragment_huihao = new Fragment_huihao();
        }
        switchFragment(fragment_huihao, MESSAGE);
    }

    private void initView() {
        fragment_main = new Fragment_main();
//        find.setBackgroundResource(R.drawable.tab_findn);
        switchFragment(fragment_main, MAIN);
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

}