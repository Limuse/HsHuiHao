package com.huihao.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;

import com.huihao.adapter.ChooseAddressAdapter;
import com.huihao.common.SystemBarTintManager;
import com.huihao.R;
import com.huihao.entity.AddressItemEntity;
import com.leo.base.activity.LActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huisou on 2015/8/7.
 */
public class Choose_Address extends LActivity implements View.OnClickListener {
    private Button btn_addr;
    private ListView listView;
    private ChooseAddressAdapter adapter;
    private List<AddressItemEntity> list=null;

    @Override
    protected void onLCreate(Bundle bundle) {
        setContentView(R.layout.activity_choose_address);
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
        //右边图片点击事件
        toolbar.inflateMenu(R.menu.right_menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.menu_messages) {
                    Intent intent = new Intent(Choose_Address.this, Address.class);
                    startActivity(intent);
                }
                return false;
            }
        });
        toolbar.setTitleTextColor(getResources().getColor(R.color.app_text_dark));
        listView = (ListView) findViewById(R.id.lv_cadd);
        btn_addr = (Button) findViewById(R.id.btn_add_addrs);
        btn_addr.setOnClickListener(this);
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
        adapter = new ChooseAddressAdapter(this, list);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
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
        if (v.getId() == R.id.btn_add_addrs) {
            Intent intent = new Intent(this, Add_Address.class);
            startActivity(intent);
        }
    }
}
