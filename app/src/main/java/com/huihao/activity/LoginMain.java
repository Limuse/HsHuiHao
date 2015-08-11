package com.huihao.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.huihao.R;
import com.huihao.common.Bar;
import com.leo.base.activity.LActivity;

/**
 * Created by admin on 2015/8/10.
 */
public class LoginMain extends LActivity {
    private int isEye = 0;
    private EditText et_user, et_pwd;
    private Button btn_look;

    protected void onLCreate(Bundle bundle) {
        setContentView(R.layout.activity_login_main);
        initBar();
        initView();
    }

    private void initView() {
        et_pwd = (EditText) findViewById(R.id.et_pwd);
        btn_look = (Button) findViewById(R.id.btn_look);
    }

    private void initBar() {
        Bar.setTrans(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getText(R.string.app_login));
        toolbar.setTitleTextColor(getResources().getColor(R.color.app_white));
        toolbar.setBackgroundColor(Color.parseColor("#00ffffff"));
        toolbar.inflateMenu(R.menu.login);
        toolbar.setNavigationIcon(R.mipmap.back_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void eye(View v) {
        if (isEye == 1) {
            et_pwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            btn_look.setBackgroundResource(R.mipmap.login_look);
            isEye = 0;
        } else if (isEye == 0) {
            et_pwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            btn_look.setBackgroundResource(R.mipmap.login_look2);
            isEye = 1;
        }
    }

    public void login(View v) {

    }

    public void forget(View v) {

    }

    public void QQ(View v) {

    }

    public void weibo(View v) {

    }

    public void weixin(View v) {

    }
}
