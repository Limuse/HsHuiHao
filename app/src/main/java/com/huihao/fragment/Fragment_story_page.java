package com.huihao.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.huihao.R;
import com.huihao.activity.Story_details;
import com.leo.base.activity.fragment.LFragment;

import java.util.ArrayList;

/**
 * Created by admin on 2015/6/26.
 */

public class Fragment_story_page extends LFragment {
    private ViewPager viewPager;
    private ArrayList<Fragment> fragmentList = new ArrayList<Fragment>();
    private String info;
    private View total;
    private ImageView image;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        container = (ViewGroup) inflater.inflate(
                R.layout.fragment_story_page_view, null);
        total = inflater.inflate(R.layout.fragment_story_page, null);
        container.addView(total);
        return container;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();
    }

    private void initView() {
        image=(ImageView)total.findViewById(R.id.page_image);
    }

    private void initData() {
        if(info.equals("0")){
            image.setImageResource(R.mipmap.story1);
        }else if(info.equals("1")){
            image.setImageResource(R.mipmap.story2);
        }
        else if(info.equals("2")){
            image.setImageResource(R.mipmap.story3);
        }
        image.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),Story_details.class);
                startActivity(intent);
            }
        });
    }

    public  void getData(String info) {
        this.info = info;
    }
}
