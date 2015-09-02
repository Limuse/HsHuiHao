package com.huihao.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.huihao.fragment.Fragment_Orders;
import com.huihao.fragment.Fragment_pay_money;
import com.huihao.activity.All_Orders;
import com.huihao.fragment.Fragment_back_money;
import com.huihao.fragment.Fragment_reserve;

/**
 * Created by huisou on 2015/7/31.
 */
public class MyAllOrderMainPager extends FragmentPagerAdapter {


    final String[] titles={"全部订单","代付款","待收货","退款中"};

    public MyAllOrderMainPager(All_Orders all_Orders,FragmentManager fm){
        super(fm);

    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        switch(position){
            case 0:
                fragment= Fragment_Orders.newInstance();//全部订单

                break;
            case 1:
                fragment= Fragment_pay_money.newInstance();//待付款
                break;
            case 2:
                fragment= Fragment_reserve.newInstance();//待收货
                break;
           default:
               fragment= Fragment_back_money.newInstance();//退款中
                break;
        }
        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }


    @Override
    public int getCount() {
        return 4;
    }

}
