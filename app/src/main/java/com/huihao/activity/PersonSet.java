package com.huihao.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huihao.R;
import com.huihao.common.SystemBarTintManager;
import com.leo.base.activity.LActivity;

/**
 * Created by huisou on 2015/7/29.
 * 个人设置
 */
public class PersonSet extends LActivity implements View.OnClickListener {
    private RelativeLayout rl_phone, rl_name, rl_num, rl_pwd;
    private TextView tv_phone, tv_name, tv_num;

    @Override
    protected void onLCreate(Bundle bundle) {
        setContentView(R.layout.activity_person_set);
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
        toolbar.setTitle("个人设置");
        toolbar.setBackgroundColor(getResources().getColor(R.color.app_white));
        toolbar.setNavigationIcon(R.mipmap.right_too);//左边图标
        //左边图标点击事件
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
        toolbar.setTitleTextColor(R.color.app_text_dark);

        tv_phone = (TextView) findViewById(R.id.setting_phone);
        tv_name = (TextView) findViewById(R.id.setting_name);
        tv_num = (TextView) findViewById(R.id.setting_num);

        rl_phone = (RelativeLayout) findViewById(R.id.rl_phone);
        rl_name = (RelativeLayout) findViewById(R.id.rl_name);
        rl_num = (RelativeLayout) findViewById(R.id.rl_num);
        rl_pwd = (RelativeLayout) findViewById(R.id.rl_pwd);

        rl_phone.setOnClickListener(this);
        rl_name.setOnClickListener(this);
        rl_num.setOnClickListener(this);
        rl_pwd.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        //绑定手机号
        if (id == R.id.rl_phone) {
            Intent intent = new Intent(this, Update_Phone.class);
            startActivity(intent);
        }
        //修改昵称
        if (id == R.id.rl_name) {
            Intent intent = new Intent(this, Update_Name.class);
            startActivity(intent);
        }
        //邀请码
        if (id == R.id.rl_num) {
            Intent intent = new Intent(this, Update_Num.class);
            startActivity(intent);
        }
        //修改密码
        if (id == R.id.rl_pwd) {
            Intent intent = new Intent(this, Update_Pwd.class);
            startActivity(intent);
        }
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
