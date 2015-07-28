package com.huihao.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.huihao.R;
import com.huihao.common.Log;
import com.leo.base.activity.fragment.LFragment;

/**
 * Created by admin on 2015/6/26.
 */

public class Fragment_shop extends LFragment {
    private View parentView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.fragment_shop,
                container, false);
        return parentView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initBar();
    }
    private void initBar() {
        Toolbar toolbar=(Toolbar)parentView.findViewById(R.id.toolbar);
        toolbar.setTitle("购物车");//设置标题
        toolbar.setTitleTextColor(Color.WHITE);//设置标题颜色
        toolbar.setNavigationIcon(R.mipmap.logo);//设置左边图标
        //左边图标点击事件
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        //加载右边图标样式
        toolbar.inflateMenu(R.menu.fragment_main_menu);
        //右边图片点击事件
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId()==R.id.menu_message){
                    getActivity().finish();
                }
                return false;
            }
        });
    }
}
