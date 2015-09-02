package com.huihao.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.huihao.R;
import com.huihao.common.Bar;
import com.huihao.common.Log;
import com.huihao.handle.ActivityHandler;
import com.huihao.handle.FragmentHandler;
import com.leo.base.activity.fragment.LFragment;
import com.leo.base.entity.LMessage;
import com.leo.base.net.LReqEntity;
import com.leo.base.util.T;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2015/6/26.
 */

public class Fragment_story extends LFragment {
    private View parentView;
    private ViewPager viewPager;
    private List<Fragment> fragmentList = new ArrayList<Fragment>();
    private List<Map<String, String>> imageInfo = new ArrayList<Map<String, String>>();

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
    }


    private void initView() {
        viewPager = (ViewPager) parentView.findViewById(R.id.viewpage);
    }

    private void initData() {
        String url = getResources().getString(R.string.app_service_url) + "/huihao/product/special/1/sign/aggregation/";
        FragmentHandler handler = new FragmentHandler(Fragment_story.this);
        LReqEntity entity = new LReqEntity(url);
        handler.startLoadingData(entity, 1);
    }

    public void onResultHandler(LMessage msg, int requestId) {
        super.onResultHandler(msg, requestId);
        if (msg != null) {
            if (requestId == 1) {
                getSmJsonData(msg.getStr());
            } else {
                T.ss("参数ID错误");
            }
        } else {
            T.ss("数据获取失败");
        }
    }

    private void getSmJsonData(String str) {
        try {
            JSONObject object = new JSONObject(str);
            int status = object.optInt("status");
            if (status == 1) {
                JSONObject list = object.optJSONObject("list");
                JSONArray special_list = list.optJSONArray("special_list");
                for (int i = 0; i < special_list.length(); i++) {
                    JSONObject item = special_list.optJSONObject(i);
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("id", item.opt("id").toString());
                    map.put("image", item.opt("special_pic").toString());
                    imageInfo.add(map);
                }
            } else {
                T.ss("数据获取失败");
            }
        } catch (Exception e) {
            T.ss("数据解析失败");
        }
        initPage();
    }


    private void initPage() {
        for (int i = 0; i < imageInfo.size(); i++) {
            Fragment_story_page fragmentStoryPage = new Fragment_story_page();
            fragmentStoryPage.getData(imageInfo.get(i));
            fragmentList.add(fragmentStoryPage);
        }

        WindowManager wm = getActivity().getWindowManager();
        int width = wm.getDefaultDisplay().getWidth();
        viewPager.setOffscreenPageLimit(3);
        viewPager.setPageMargin(width / 10);
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
