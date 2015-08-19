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
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.huihao.R;
import com.huihao.common.SystemBarTintManager;
import com.huihao.custom.RiseNumberTextView;
import com.leo.base.activity.LActivity;
import com.leo.base.util.T;

/**
 * Created by huisou on 2015/7/30.
 * 我的返利
 */
public class My_Rebate extends LActivity implements View.OnClickListener {
    private Button btn_rb;
    private RiseNumberTextView numberTextView;
    private RelativeLayout rl_phone, rl_e, rl_f;

    @Override
    protected void onLCreate(Bundle bundle) {
        setContentView(R.layout.activity_my_rebert);
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
        toolbar.setTitle("我的返利");
        toolbar.setBackgroundColor(getResources().getColor(R.color.app_white));
        toolbar.setNavigationIcon(R.mipmap.right_too);//左边图标
        //左边图标点击事件
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
        toolbar.setTitleTextColor(getResources().getColor(R.color.app_text_dark));

        btn_rb = (Button) findViewById(R.id.login_cancel);
        rl_phone = (RelativeLayout) findViewById(R.id.rl_phone);
        rl_e = (RelativeLayout) findViewById(R.id.rl_e);
        rl_f = (RelativeLayout) findViewById(R.id.rl_f);
        numberTextView=(RiseNumberTextView)findViewById(R.id.numbertv);
        btn_rb.setOnClickListener(this);
        rl_phone.setOnClickListener(this);
        rl_e.setOnClickListener(this);
        rl_f.setOnClickListener(this);
        // 设置数据
        numberTextView.withNumber(9999.50f);
        // 设置动画播放时间
        numberTextView.setDuration(1000);
        // 开始播放动画
        numberTextView.start();

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.login_cancel) {
            T.ss("提现");
            Intent intent = new Intent(this, Extract_Rebate.class);
            startActivity(intent);
        }
        //提取中
        if (id == R.id.rl_phone) {
            Intent intent = new Intent(this, Extra_Record.class);
            intent.putExtra("gets", "0");
            startActivity(intent);
        }
        //已提现
        if (id == R.id.rl_e) {
            Intent intent = new Intent(this, Extra_Record.class);
            intent.putExtra("gets", "1");
            startActivity(intent);

        }
        //返利明细
        if (id == R.id.rl_f) {
            Intent intent = new Intent(this, Rebate_Detail.class);
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