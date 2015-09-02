package com.huihao.activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.huihao.R;
import com.huihao.common.SystemBarTintManager;
import com.leo.base.activity.LActivity;
import com.leo.base.util.T;

/**
 * Created by huisou on 2015/7/29.
 */
public class Update_Pwd extends LActivity implements View.OnClickListener {
    private EditText et_old_pwd, et_new_pwd;
    private Button btn_del, btn_see1, btn_see2;

    @Override
    protected void onLCreate(Bundle bundle) {
        setContentView(R.layout.activity_update_pwd);
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
        toolbar.setTitle("修改密码");
        toolbar.setBackgroundColor(getResources().getColor(R.color.app_white));
        toolbar.setNavigationIcon(R.mipmap.right_too);//设置左边图标
        //左边图标点击事件
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
        toolbar.inflateMenu(R.menu.right_menu);
        //右边图片点击事件
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.menu_messages) {
                    T.ss("保存成功");
                }
                return false;
            }
        });
        toolbar.setTitleTextColor(getResources().getColor(R.color.app_text_dark));

        et_old_pwd = (EditText) findViewById(R.id.et_update_pwds);
        et_new_pwd = (EditText) findViewById(R.id.et_pwds_num);
        btn_del = (Button) findViewById(R.id.btn_del);
        btn_see1 = (Button) findViewById(R.id.btn_sees);
        btn_see2 = (Button) findViewById(R.id.btn_see);

        btn_del.setOnClickListener(this);
        btn_see1.setOnClickListener(this);
        btn_see2.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_del) {
            T.ss("删除");
        }
        if (id == R.id.btn_sees) {
            T.ss("原密码可见");
        }
        if (id == R.id.btn_see) {
            T.ss("新密码可见");
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
