package com.huihao.activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.huihao.R;
import com.huihao.common.SystemBarTintManager;
import com.leo.base.activity.LActivity;
import com.leo.base.util.T;

/**
 * Created by huisou on 2015/8/4.
 */
public class Add_Address extends LActivity implements View.OnClickListener {
    private EditText et_name, et_phone, et_shen, et_xiang;
    private ImageView iv_del;

    @Override
    protected void onLCreate(Bundle bundle) {
        setContentView(R.layout.activity_add_address);
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
        toolbar.setTitle("新增收货地址");
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
                    T.ss("保存成功");
                }
                return false;
            }
        });
        toolbar.setTitleTextColor(R.color.app_text_dark);

        et_name = (EditText) findViewById(R.id.et_men);
        et_phone = (EditText) findViewById(R.id.et_menphone);
        et_shen = (EditText) findViewById(R.id.et_shen);
        et_xiang = (EditText) findViewById(R.id.et_xiang);
        iv_del = (ImageView) findViewById(R.id.iv_del);

        iv_del.setOnClickListener(this);
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
        int id=v.getId();
        if(id==R.id.iv_del){
            et_phone.setText(null);
        }
    }
}
