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
import com.huihao.fragment.Fragment_story;
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
    @InjectView(R.id.main)
    Button main;
    @InjectView(R.id.story)
    Button story;
    @InjectView(R.id.shop)
    Button shop;
    @InjectView(R.id.my)
    Button my;


    private Fragment_main fragment_main;
    private Fragment_shop fragment_shop;
    private Fragment_my fragment_my;
    private Fragment_story fragment_story;

    public static final String MAIN = "main";
    public static final String STORY = "story";
    public static final String SHOP = "shop";
    public static final String MY = "my";

    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;

    private String hideTag;

    protected void onLCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_home);
        ButterKnife.inject(this);
        PushAgent.getInstance(this).onAppStart();
        initBar();
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initPush();
    }

    private void initPush() {
        PushAgent mPushAgent = PushAgent.getInstance(this);
        PushAgent.setAppLaunchByMessage();
        mPushAgent.enable();
        Log.e(mPushAgent.isEnabled() + "");
    }

    @OnClick(R.id.main)
    void main() {
        if (hideTag.equals(MAIN))
            return;
        if (fragment_main == null) {
            fragment_main = new Fragment_main();
        }
        switchFragment(fragment_main, MAIN);
    }

    @OnClick(R.id.story)
    void story() {
        if (hideTag.equals(STORY))
            return;
        if (fragment_story == null) {
            fragment_story = new Fragment_story();
        }
        switchFragment(fragment_story, STORY);
    }

    @OnClick(R.id.shop)
    void shop() {
        if (hideTag.equals(SHOP))
            return;
        if (fragment_shop == null) {
            fragment_shop = new Fragment_shop();
        }
        switchFragment(fragment_shop, SHOP);
    }

    @OnClick(R.id.my)
    void my() {
        if (hideTag.equals(MY))
            return;
        if (fragment_my == null) {
            fragment_my = new Fragment_my();
        }
        switchFragment(fragment_my, MY);
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