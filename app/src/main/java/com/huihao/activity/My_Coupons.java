package com.huihao.activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.huihao.adapter.CouponsAdapter;
import com.huihao.common.SystemBarTintManager;
import com.huihao.entity.CouponsEntity;
import com.huihao.R;
import com.leo.base.activity.LActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huisou on 2015/7/29.
 * 优惠卷
 */
public class My_Coupons extends LActivity {
    private ListView listview;
    private LinearLayout headview;
    private List<CouponsEntity> list = null;
    private CouponsAdapter adapter;

    @Override
    protected void onLCreate(Bundle bundle) {
        setContentView(R.layout.activity_my_coupons);
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
        toolbar.setTitle("我的优惠卷");
        toolbar.setBackgroundColor(getResources().getColor(R.color.app_white));
        toolbar.setNavigationIcon(R.mipmap.right_too);//左边图标
        //左边图标点击事件
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
        toolbar.setTitleTextColor(getResources().getColor(R.color.app_text_dark));

        listview = (ListView) findViewById(R.id.lv_coupons);

    }

    private void initData() {
        list = new ArrayList<CouponsEntity>();
        CouponsEntity entity = new CouponsEntity();
        entity.cpmoney = "￥100";
        entity.cptitile = "汇好商城优惠卷";
        entity.cptime = "使用期限：2015.11.10前";
        entity.cpuse = "满800可用";
        entity.t = -1;
        CouponsEntity entity1 = new CouponsEntity();
        entity1.cpmoney = "￥100";
        entity1.cptitile = "汇好商城优惠卷";
        entity1.cptime = "使用期限：2015.11.10前";
        entity1.cpuse = "满800可用";
        entity1.t = 1;

        CouponsEntity entity2 = new CouponsEntity();
        entity2.cpmoney = "￥100";
        entity2.cptitile = "汇好商城优惠卷";
        entity2.cptime = "使用期限：2015.11.10前";
        entity2.cpuse = "满800可用";
        entity2.t = 0;

        list.add(entity);
        list.add(entity1);
        list.add(entity2);
        adapter = new CouponsAdapter(this, list);
        listview.setAdapter(adapter);
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
