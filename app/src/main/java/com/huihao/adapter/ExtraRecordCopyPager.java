package com.huihao.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.huihao.activity.Extra_Record;
import com.huihao.activity.Extra_RecordCopy;
import com.huihao.fragment.Fragment_Extraed;
import com.huihao.fragment.Fragment_Extraedone;
import com.huihao.fragment.Fragment_Extraing;
import com.huihao.fragment.Fragment_Extraingone;

/**
 * Created by huisou on 2015/8/31.
 */
public class ExtraRecordCopyPager extends FragmentPagerAdapter {
    final String[] titles={"提取中","已提现"};

    public ExtraRecordCopyPager(Extra_RecordCopy extra_recordCopy,FragmentManager fm){
        super(fm);
    }
    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        switch(position){
            case 0:
                fragment= Fragment_Extraingone.newInstance();//提取中
                break;
            default:
                fragment= Fragment_Extraedone.newInstance();//已提现
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
