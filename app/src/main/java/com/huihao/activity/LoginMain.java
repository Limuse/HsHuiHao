package com.huihao.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.huihao.MyApplication;
import com.huihao.R;
import com.huihao.common.Bar;
import com.huihao.common.Log;
import com.huihao.common.Token;
import com.huihao.handle.ActivityHandler;
import com.leo.base.activity.LActivity;
import com.leo.base.entity.LMessage;
import com.leo.base.net.LReqEntity;
import com.leo.base.util.LSharePreference;
import com.leo.base.util.T;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

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
        et_user=(EditText) findViewById(R.id.et_user);
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
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.menu_login) {
                    Intent intent = new Intent(LoginMain.this, Registered.class);
                    startActivity(intent);
                }
                return false;
            }
        });
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
        Map<String,String> map=new HashMap<String,String>();
        map.put("mobile",et_user.getText().toString().trim());
        map.put("password",et_pwd.getText().toString().trim());
        String url=getResources().getString(R.string.app_service_url)+"/huihao/login/1/sign/aggregation/";
        LReqEntity entity=new LReqEntity(url,map);
        ActivityHandler handler=new ActivityHandler(this);
        handler.startLoadingData(entity,1);
    }

    public void onResultHandler(LMessage msg, int requestId) {
        super.onResultHandler(msg, requestId);
        if (msg != null) {
            if (requestId == 1) {
                getSmJsonData(msg.getStr());
            } else {
                T.ss("参数ID错误");
            }
        }else {
            T.ss("数据获取失败");
        }
    }
    private void getSmJsonData(String str) {
        try{
            JSONObject info=new JSONObject(str);
            int status=info.optInt("status");
            if(status==1){
                T.ss("登录成功");
                LSharePreference.getInstance(this).setBoolean("login",true);
                Token.set(this,info.opt("uuid").toString());
                finish();
            }else {
                T.ss(info.opt("info").toString());
            }
        }catch (Exception e){

        }
    }

    public void forget(View v) {
        Intent intent = new Intent(LoginMain.this, FindPwd.class);
        startActivity(intent);
    }

    public void QQ(View v) {

    }

    public void weibo(View v) {

    }

    public void weixin(View v) {

    }
}
