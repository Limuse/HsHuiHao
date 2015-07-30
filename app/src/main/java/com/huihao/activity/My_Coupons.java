package com.huihao.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.huihao.R;
import com.leo.base.activity.LActivity;

/**
 * Created by huisou on 2015/7/29.
 */
public class My_Coupons extends LActivity {
    private ListView listview;
    @Override
    protected void onLCreate(Bundle bundle) {
        setContentView(R.layout.activity_my_coupons);
        initView();
    }
    private void initView(){
        Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        toolbar.setTitle("我的优惠卷");
        toolbar.setBackgroundColor(Color.WHITE);
        toolbar.setNavigationIcon(R.mipmap.right_too);//左边图标
        //左边图标点击事件
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
        toolbar.setTitleTextColor(R.color.app_text_dark);

        listview=(ListView)findViewById(R.id.lv_coupons);

    }
}
