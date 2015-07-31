package com.huihao.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.huihao.R;
import com.huihao.activity.Story_details;
import com.huihao.common.Bar;
import com.leo.base.activity.fragment.LFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by admin on 2015/6/26.
 */

public class Fragment_story extends LFragment {
    private View parentView;
    private ViewPager viewPager;
    private List<Fragment>fragmentList=new ArrayList<Fragment>();
    
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
        Bar.setWhite(getActivity());
        initView();
        initData();
        initPage();
    }


    private void initView() {
        viewPager = (ViewPager) parentView.findViewById(R.id.viewpage);
    }

    private void initData() {
        for (int i = 0; i < 3; i++) {
            Fragment_story_page fragmentStoryPage = new Fragment_story_page();
            fragmentStoryPage.getData(i + "");
            fragmentList.add(fragmentStoryPage);
        }
    }


    private void initPage() {
        WindowManager wm = getActivity().getWindowManager();
        int width = wm.getDefaultDisplay().getWidth();
        viewPager.setOffscreenPageLimit(3);
        viewPager.setPageMargin(width/10);
        viewPager.setAdapter(new MyAdapter(getActivity().getSupportFragmentManager()));
        viewPager.setCurrentItem(1);
    }

    private class MyAdapter extends FragmentStatePagerAdapter {
        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
        }

        public int getCount() {
            return fragmentList.size();
        }
    }
}
