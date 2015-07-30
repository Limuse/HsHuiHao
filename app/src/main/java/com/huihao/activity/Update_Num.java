package com.huihao.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.huihao.R;
import com.leo.base.activity.LActivity;
import com.leo.base.util.T;

/**
 * Created by huisou on 2015/7/29.
 */
public class Update_Num extends LActivity {

    private EditText et_Upnum;

    @Override
    protected void onLCreate(Bundle bundle) {
        setContentView(R.layout.activity_update_num);
        initView();
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        toolbar.setTitle("邀请码设置");
        toolbar.setBackgroundColor(Color.WHITE);
        toolbar.setNavigationIcon(R.mipmap.right_too);//设置左边图标
        //左边图标点击事件
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
        toolbar.inflateMenu(R.menu.right_menu);
        //右边图片点击事件
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.menu_messages) {
                    T.ss("保存成功");
                }
                return false;
            }
        });
        toolbar.setTitleTextColor(R.color.app_text_dark);

        et_Upnum = (EditText) findViewById(R.id.et_please_num);
    }
}
