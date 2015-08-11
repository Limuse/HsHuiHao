package com.huihao.activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;

import com.huihao.R;
import com.huihao.adapter.MetailflowDerailAdapter;
import com.huihao.common.SystemBarTintManager;
import com.huihao.entity.MetailflowEntity;
import com.leo.base.activity.LActivity;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huisou on 2015/8/7.
 * 物流详情
 */
public class MetailFlow_Detail extends LActivity {
    private TextView tv_mn, tv_mnum, tv_mstate;
    private ListView listView;
    private List<MetailflowEntity> list;
    private MetailflowDerailAdapter adapter;


    @Override
    protected void onLCreate(Bundle bundle) {
        setContentView(R.layout.activity_metailflow_detail);
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
        toolbar.setTitle("物流详情");
        toolbar.setBackgroundColor(getResources().getColor(R.color.app_white));
        toolbar.setNavigationIcon(R.mipmap.right_too);//左边图标
        //左边图标点击事件
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
        toolbar.setTitleTextColor(getResources().getColor(R.color.app_text_dark));

        tv_mn = (TextView) findViewById(R.id.tv_kd);
        tv_mnum = (TextView) findViewById(R.id.tv_ydnum);
        tv_mstate = (TextView) findViewById(R.id.tv_wstate);
        listView = (ListView) findViewById(R.id.wlistvew);

    }

    private void initData() {
        list = new ArrayList<MetailflowEntity>();
        MetailflowEntity entity1 = new MetailflowEntity();
        entity1.idm = 1;
        entity1.tv1 = "物件已经扫描，正在装运中";
        entity1.tv2 = "2015-08-10 10:30:51";
        MetailflowEntity entity2 = new MetailflowEntity();
        entity2.idm = 2;
        entity2.tv1 = "物件已经从广州物资站发往杭州，正在前往杭州中转站";
        entity2.tv2 = "2015-08-10 12:30:51";
        MetailflowEntity entity3 = new MetailflowEntity();
        entity3.idm = 3;
        entity3.tv1 = "物件正在派送中";
        entity3.tv2 = "2015-08-10 18:30:51";
        MetailflowEntity entity4 = new MetailflowEntity();
        entity4.idm = 4;
        entity4.tv1 = "正在前往杭州中转站";
        entity4.tv2 = "2015-08-10 18:30:51";
        list.add(entity3);
        list.add(entity4);
        list.add(entity2);
        list.add(entity1);
        adapter = new MetailflowDerailAdapter(this, list);
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
