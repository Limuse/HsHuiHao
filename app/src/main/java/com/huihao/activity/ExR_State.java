package com.huihao.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huihao.R;
import com.leo.base.activity.LActivity;
import com.leo.base.util.T;

/**
 * Created by huisou on 2015/8/3.
 */
public class ExR_State extends LActivity {
    private TextView tv_s, tv_m, tv_n, tv_ac;
    private Button btn_no, btn_ok;
    private RelativeLayout rl_tv;
    private LinearLayout ly_btn;

    @Override
    protected void onLCreate(Bundle bundle) {
        setContentView(R.layout.activity_exr_state);
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
        toolbar.setTitleTextColor(R.color.app_text_dark);

        tv_s = (TextView) findViewById(R.id.tv_s);
        tv_m = (TextView) findViewById(R.id.tv_mmm);
        tv_n = (TextView) findViewById(R.id.tv_me1);
        tv_ac = (TextView) findViewById(R.id.tv_me2);
        rl_tv = (RelativeLayout) findViewById(R.id.rl_s);
        ly_btn = (LinearLayout) findViewById(R.id.boo);
        btn_no = (Button) findViewById(R.id.btn_no);
        btn_ok = (Button) findViewById(R.id.btn_ok);

    }

    private void initData() {
        String t = getIntent().getStringExtra("state");
        int p = Integer.parseInt(t);
        if (p == 0) {
            tv_s.setText("待打款");
            tv_m.setTextColor(getResources().getColor(R.color.app_text_dark));
            rl_tv.setVisibility(View.GONE);
            ly_btn.setVisibility(View.GONE);
        }
        else if(p==1){
            tv_s.setText("待确定");
            tv_m.setTextColor(getResources().getColor(R.color.app_orange));
            rl_tv.setVisibility(View.VISIBLE);
            ly_btn.setVisibility(View.VISIBLE);
        }
        else if(p==2){
            tv_s.setText("已拒绝");
            tv_m.setTextColor(getResources().getColor(R.color.app_text_dark));
            rl_tv.setVisibility(View.GONE);
            ly_btn.setVisibility(View.GONE);
        }
        else if(p==3){
            tv_s.setText("已完成");
            tv_m.setTextColor(getResources().getColor(R.color.app_text_dark));
            rl_tv.setVisibility(View.GONE);
            ly_btn.setVisibility(View.GONE);
        }

    }

}
