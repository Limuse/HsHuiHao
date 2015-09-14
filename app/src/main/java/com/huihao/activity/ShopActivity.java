package com.huihao.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.huihao.R;
import com.huihao.common.Log;
import com.huihao.fragment.Fragment_main;
import com.huihao.fragment.Fragment_shop;
import com.leo.base.activity.LActivity;

/**
 * Created by admin on 2015/9/14.
 */
public class ShopActivity extends LActivity {
    private Fragment_shop fragment_shop;
    public static final String MAIN = "main";
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;

    private String hideTag;

    protected void onLCreate(Bundle bundle) {
        setContentView(R.layout.activity_shop);

        fragment_shop = new Fragment_shop();
        switchFragment(fragment_shop, MAIN);
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
}