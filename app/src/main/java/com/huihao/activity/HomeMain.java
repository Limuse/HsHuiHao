package com.huihao.activity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.huihao.R;
import com.huihao.common.Bar;
import com.huihao.common.Log;
import com.huihao.common.SystemBarTintManager;
import com.huihao.common.UntilList;
import com.huihao.custom.ImageDialog;
import com.huihao.fragment.Fragment_main;
import com.huihao.fragment.Fragment_shop;
import com.huihao.fragment.Fragment_my;
import com.huihao.fragment.Fragment_story;
import com.leo.base.activity.LActivity;
import com.leo.base.util.T;
import com.umeng.message.PushAgent;

import java.util.Timer;
import java.util.TimerTask;

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

    @InjectView(R.id.relmain)
    RelativeLayout relmain;
    @InjectView(R.id.relstory)
    RelativeLayout relstory;
    @InjectView(R.id.relshop)
    RelativeLayout relshop;
    @InjectView(R.id.relmy)
    RelativeLayout relmy;

    @InjectView(R.id.main_view)
    LinearLayout linearLayout;


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
        Bar.setWhite(this);
        Log.e(UntilList.getAppInfo(this));
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @OnClick(R.id.relmain)
    void main() {
        if (hideTag.equals(MAIN))
            return;
        if (fragment_main == null) {
            fragment_main = new Fragment_main();
        }
        setBg();


        main.setBackgroundResource(R.mipmap.mainp);
        switchFragment(fragment_main, MAIN);

    }

    @OnClick(R.id.relstory)
    void story() {
        if (hideTag.equals(STORY))
            return;
        if (fragment_story == null) {
            fragment_story = new Fragment_story();
        }
        setBg();
        story.setBackgroundResource(R.mipmap.storyp);
        switchFragment(fragment_story, STORY);

        linearLayout.setFitsSystemWindows(true);
        linearLayout.setClipToPadding(true);

    }

    @OnClick(R.id.relshop)
    void shop() {
        if (hideTag.equals(SHOP))
            return;
        if (fragment_shop == null) {
            fragment_shop = new Fragment_shop();
        }
        setBg();

        shop.setBackgroundResource(R.mipmap.shopp);
        switchFragment(fragment_shop, SHOP);

    }

    @OnClick(R.id.relmy)
    void my() {
        if (hideTag.equals(MY))
            return;
        if (fragment_my == null) {
            fragment_my = new Fragment_my();
        }
        setBg();

        my.setBackgroundResource(R.mipmap.myp);
        switchFragment(fragment_my, MY);

    }

    public void setBg() {
        main.setBackgroundResource(R.mipmap.mainn);
        story.setBackgroundResource(R.mipmap.storyn);
        shop.setBackgroundResource(R.mipmap.shopn);
        my.setBackgroundResource(R.mipmap.myn);
    }

    private void initView() {

        ImageDialog dialog = new ImageDialog.Builder(this).setImage(R.mipmap.dialog_logo).setMessage("30").setInfo("20", "10").setPositiveButton("暂不分享", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).setNegativeButton("现在就去", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).create();
        dialog.show();


        fragment_main = new Fragment_main();
        main.setBackgroundResource(R.mipmap.mainp);
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

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitBy2Click();
        }
        return false;
    }

    private static Boolean isExit = false;
    private void exitBy2Click() {
        Timer tExit = null;
        if (isExit == false) {
            isExit = true;
            T.ss("再按一次退出程序");
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                public void run() {
                    isExit = false;
                }
            }, 2000);
        } else {
            finish();
            System.exit(0);
        }
    }

}