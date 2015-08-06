package com.huihao.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.huihao.activity.My_Partner;
import com.huihao.fragment.Fragment_MyPartnerOne;
import com.huihao.fragment.Fragment_MyPartnerTwo;

/**
 * Created by huisou on 2015/8/4.
 */
public class MyPartnerPager extends FragmentPagerAdapter {
    final String[] titles = {"下级分销商", "下下级分销商"};

    public MyPartnerPager(My_Partner my_partner, FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        switch (position) {
            case 0:
                fragment= Fragment_MyPartnerOne.newInstance();//下级分销商
                break;
            default:
                fragment= Fragment_MyPartnerTwo.newInstance();//下下级分销商
                break;
        }
        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public int getCount() {
        return 2;
    }
}
