package com.huihao.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.huihao.activity.Product_details;
import com.huihao.fragment.Fragment_Product_info;
import com.huihao.fragment.Fragment_Product_para;

/**
 * Created by admin on 2015/7/31.
 */
public class ProductPageAdapter extends FragmentPagerAdapter{
    final String[] titles = { "商品详情", "产品参数"};

    public ProductPageAdapter(Product_details personFiles, FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new Fragment();
        switch (position) {
            case 0:
                fragment = Fragment_Product_info.newInstance();
                break;
            case 1:
                fragment = Fragment_Product_para.newInstance();
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
        return titles.length;
    }
}
