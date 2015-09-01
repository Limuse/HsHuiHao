package com.huihao.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.huihao.activity.Extra_Record;
import com.huihao.fragment.Fragment_Extraed;
import com.huihao.fragment.Fragment_Extraing;


/**
 * Created by huisou on 2015/8/3.
 */
public class ExtraRecordPager extends FragmentPagerAdapter {
    final String[] titles={"提取中","已提现"};

    public ExtraRecordPager(Extra_Record extra_record,FragmentManager fm){
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        switch(position){
            case 0:
                fragment= Fragment_Extraing.newInstance();//提取中
                break;
            default:
                fragment= Fragment_Extraed.newInstance();//已提现
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
        return 2;
    }
}
