package com.huihao.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.huihao.activity.News;
import com.huihao.fragment.Fragment_MoneyNews;
import com.huihao.fragment.Fragment_MyPartnerOne;
import com.huihao.fragment.Fragment_MyPartnerTwo;
import com.huihao.fragment.Fragment_OrderNews;
import com.huihao.fragment.Fragment_SystemNews;

/**
 * Created by huisou on 2015/8/10.
 */
public class NewsPager extends FragmentPagerAdapter {
    final String[] titles = {"系统消息", "订单消息", "财富消息"};

    public NewsPager(News news, FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        switch (position) {
            case 0:
                fragment = Fragment_SystemNews.newInstance();//系统消息
                break;
            case 1:
                fragment = Fragment_OrderNews.newInstance();//订单消息
                break;
            default:
                fragment = Fragment_MoneyNews.newInstance();//财富消息
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
        return 3;
    }
}
