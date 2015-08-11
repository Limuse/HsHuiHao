package com.huihao.activity;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huihao.R;
import com.huihao.common.SystemBarTintManager;
import com.huihao.custom.TagGroup;
import com.leo.base.activity.LActivity;
import com.leo.base.util.T;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huisou on 2015/8/3.
 */
public class ExR_State extends LActivity implements View.OnClickListener {
    private TextView tv_s, tv_m, tv_n, tv_ac;
    private Button btn_no, btn_ok;
    private RelativeLayout rl_tv;
    private LinearLayout ly_btn;

    private Dialog dialog;

    @Override
    protected void onLCreate(Bundle bundle) {
        setContentView(R.layout.activity_exr_state);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }

        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(R.color.app_white);
        initView();
        initData();
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        toolbar.setTitle("提取记录");
        toolbar.setBackgroundColor(getResources().getColor(R.color.app_white));
        toolbar.setNavigationIcon(R.mipmap.right_too);//设置左边图标
        //左边图标点击事件
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
        toolbar.setTitleTextColor(getResources().getColor(R.color.app_text_dark));

        tv_s = (TextView) findViewById(R.id.tv_s);
        tv_m = (TextView) findViewById(R.id.tv_mmm);
        tv_n = (TextView) findViewById(R.id.tv_me1);
        tv_ac = (TextView) findViewById(R.id.tv_me2);
        rl_tv = (RelativeLayout) findViewById(R.id.rl_s);
        ly_btn = (LinearLayout) findViewById(R.id.boo);
        btn_no = (Button) findViewById(R.id.btn_no);
        btn_ok = (Button) findViewById(R.id.btn_ok);

        btn_no.setOnClickListener(this);
        btn_ok.setOnClickListener(this);


        View view = getLayoutInflater().inflate(R.layout.show_refuse, null);
        dialog = new Dialog(this,
                R.style.transparentFrameWindowStyle);
        dialog.setContentView(view, new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT));

        initDialog(view);
    }


    private void initDialog(View view) {
        EditText et_reson = (EditText) view.findViewById(R.id.et_reson);
        Button btn_sub = (Button) view.findViewById(R.id.btn_submits);
        /**
         * 弹框点击事件
         *
         */
        btn_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                T.ss("确定提交");
                dialog.dismiss();
            }
        });
    }

    private void showBuyDialog() {
        Window window = dialog.getWindow();
        window.setWindowAnimations(R.style.Dialog_animstyle);
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;
        wl.y = getWindowManager().getDefaultDisplay().getHeight();
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        dialog.onWindowAttributesChanged(wl);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    private void initData() {
        String t = getIntent().getStringExtra("state");
        int p = Integer.parseInt(t);
        if (p == 0) {
            tv_s.setText("待打款");
            tv_m.setTextColor(getResources().getColor(R.color.app_text_dark));
            rl_tv.setVisibility(View.GONE);
            ly_btn.setVisibility(View.GONE);
        } else if (p == 1) {
            tv_s.setText("待确定");
            tv_m.setTextColor(getResources().getColor(R.color.app_orange));
            rl_tv.setVisibility(View.VISIBLE);
            ly_btn.setVisibility(View.VISIBLE);
        } else if (p == 2) {
            tv_s.setText("已拒绝");
            tv_m.setTextColor(getResources().getColor(R.color.app_text_dark));
            rl_tv.setVisibility(View.GONE);
            ly_btn.setVisibility(View.GONE);
        } else if (p == 3) {
            tv_s.setText("已完成");
            tv_m.setTextColor(getResources().getColor(R.color.app_text_dark));
            rl_tv.setVisibility(View.GONE);
            ly_btn.setVisibility(View.GONE);
        }

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_no) {
            showBuyDialog();
        }
        if (id == R.id.btn_ok) {
            T.ss("确定");
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
