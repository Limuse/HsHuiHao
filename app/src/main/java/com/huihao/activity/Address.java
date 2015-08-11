package com.huihao.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;

import com.huihao.R;
import com.huihao.adapter.AddressHoriSliseAdapter;
import com.huihao.common.SystemBarTintManager;
import com.huihao.entity.AddressItemEntity;
import com.leo.base.activity.LActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huisou on 2015/8/4.
 */
public class Address extends LActivity implements View.OnClickListener {
    private Button btn_add_addr;
    private ListView listView;
    private AddressHoriSliseAdapter adapter;
    List<AddressItemEntity> list = null;

    @Override
    protected void onLCreate(Bundle bundle) {
        setContentView(R.layout.activity_address);
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
        toolbar.setTitle("收货地址");
        toolbar.setBackgroundColor(getResources().getColor(R.color.app_white));
        toolbar.setNavigationIcon(R.mipmap.right_too);//左边图标
        //左边图标点击事件
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
        toolbar.setTitleTextColor(getResources().getColor(R.color.app_text_dark));

        listView = (ListView) findViewById(R.id.lv_list_addr);
        btn_add_addr = (Button) findViewById(R.id.btn_add_addr);
        btn_add_addr.setOnClickListener(this);
    }

    private void initData() {
        list = new ArrayList<AddressItemEntity>();
        for (int i = 0; i < 10; i++) {
            AddressItemEntity entity = new AddressItemEntity();
            entity.ida = i;
            entity.namea = "张三";
            entity.phonea = "1802142445";
            entity.addra = "江干区江干区干区江干区江干区江干区";
            list.add(entity);
        }
        adapter = new AddressHoriSliseAdapter(this, list);
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

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_add_addr) {
            Intent intnet = new Intent(this, Add_Address.class);
            startActivity(intnet);
        }
    }
}
