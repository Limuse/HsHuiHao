package com.huihao.activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;

import com.huihao.R;
import com.huihao.adapter.TebateDetailAdapter;
import com.huihao.common.SystemBarTintManager;
import com.huihao.entity.RebateDetailEntity;
import com.leo.base.activity.LActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huisou on 2015/8/4.
 * 我的返利----返利明细
 */
public class Rebate_Detail extends LActivity {
    private ListView listView;
    private TebateDetailAdapter adapter;
    private List<RebateDetailEntity> list = null;


    @Override
    protected void onLCreate(Bundle bundle) {
        setContentView(R.layout.activity_rebate_detail);
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
        toolbar.setTitle("返利明细");
        toolbar.setBackgroundColor(getResources().getColor(R.color.app_white));
        toolbar.setNavigationIcon(R.mipmap.right_too);//左边图标
        //左边图标点击事件
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
        toolbar.setTitleTextColor(R.color.app_text_dark);
        listView = (ListView) findViewById(R.id.lv_redatil);
    }

    private void initData() {
        list = new ArrayList<RebateDetailEntity>();
        RebateDetailEntity entity=new RebateDetailEntity();
        entity.id=1;
        entity.dnum="12532424";
        entity.pname="张三";
        entity.pmoney="￥10.00";
        entity.dmoeny="100.00";
        entity.states=0;

        RebateDetailEntity entity1=new RebateDetailEntity();
        entity1.id=2;
        entity1.dnum="12532424";
        entity1.pname="张三";
        entity1.pmoney="￥10.00";
        entity1.dmoeny="100.00";
        entity1.states=1;

        RebateDetailEntity entity2=new RebateDetailEntity();
        entity2.id=3;
        entity2.dnum="12532424";
        entity2.pname="张三";
        entity2.pmoney="￥10.00";
        entity2.dmoeny="100.00";
        entity2.states=2;

        list.add(entity);
        list.add(entity1);
        list.add(entity2);

        adapter=new TebateDetailAdapter(this,list);
        listView.setAdapter(adapter);

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
