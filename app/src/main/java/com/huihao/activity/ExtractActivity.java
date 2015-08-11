package com.huihao.activity;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import com.huihao.R;
import com.huihao.common.SystemBarTintManager;
import com.leo.base.activity.LActivity;
import com.leo.base.util.T;

/**
 * Created by huisou on 2015/7/29.
 * 提取账户
 */
public class ExtractActivity extends LActivity {
    private EditText et_zaccount, et_zname, et_zphone;

    @Override
    protected void onLCreate(Bundle bundle) {
        setContentView(R.layout.activity_extarct_account);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }

        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(R.color.app_white);
        initView();
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        toolbar.setTitle("提取账户");
        toolbar.setBackgroundColor(getResources().getColor(R.color.app_white));
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
        toolbar.setTitleTextColor(getResources().getColor(R.color.app_text_dark));

        et_zaccount = (EditText) findViewById(R.id.setting_phone);
        et_zname = (EditText) findViewById(R.id.account_name);
        et_zphone = (EditText) findViewById(R.id.account_phone);

    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }
}
