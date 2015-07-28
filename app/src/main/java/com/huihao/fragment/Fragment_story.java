package com.huihao.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.huihao.R;
import com.huihao.common.Log;
import com.huihao.custom.AnimaViewPager;
import com.leo.base.activity.fragment.LFragment;

import java.util.ArrayList;

/**
 * Created by admin on 2015/6/26.
 */

public class Fragment_story extends LFragment {
    private View parentView;
    private AnimaViewPager viewPager;
    private ArrayList<Fragment> fragmentList = new ArrayList<Fragment>();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.fragment_story,
                container, false);
        return parentView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();
        initPage();
    }


    private void initView() {
        viewPager = (AnimaViewPager) parentView.findViewById(R.id.viewpage);
    }

    private void initData() {
        for (int i = 0; i < 3; i++) {
            Fragment_story_page fragmentStoryPage = new Fragment_story_page();
            fragmentStoryPage.getData(i + "");
            fragmentList.add(fragmentStoryPage);
        }
    }



    private void initPage() {
        viewPager.setAdapter(new MyAdapter(getActivity().getSupportFragmentManager()));
        AnimaViewPager.TransitionEffect effect = AnimaViewPager.TransitionEffect.ZoomIn;
        viewPager.setTransitionEffect(effect);
        viewPager.setCurrentItem(1);
    }

    private class MyAdapter extends FragmentStatePagerAdapter {
        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }
}
