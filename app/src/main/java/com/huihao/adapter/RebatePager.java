package com.huihao.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.huihao.activity.News;
import com.huihao.activity.Rebate;
import com.huihao.fragment.Fragment_Rebateone;
import com.huihao.fragment.Fragment_Rebatetwo;

/**
 * Created by huisou on 2015/8/30.
 */
public class RebatePager extends FragmentPagerAdapter {
    final String[] titles = {"普通利润", "代理利润"};

    public RebatePager(Rebate rebate, FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        switch (position) {
            case 0:
                fragment = Fragment_Rebateone.newInstance();
                break;
            default:
                fragment = Fragment_Rebatetwo.newInstance();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
