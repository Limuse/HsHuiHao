package com.huihao.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.huihao.R;
import com.huihao.common.Log;
import com.leo.base.activity.fragment.LFragment;
import com.umeng.message.UmengRegistrar;

import butterknife.InjectView;

/**
 * Created by admin on 2015/6/26.
 */


public class Fragment_main extends LFragment {
    private View parentView;
    private Button button;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.fragment_main,
                container, false);
        return parentView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        button=(Button)getActivity().findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String device_token = UmengRegistrar.getRegistrationId(getActivity());
                Log.e(device_token + "!!!!");
            }
        });


        Toolbar toolbar=(Toolbar)getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("汇好");
        toolbar.setNavigationIcon(R.mipmap.logo);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.inflateMenu(R.menu.fragment_main_menu);
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
