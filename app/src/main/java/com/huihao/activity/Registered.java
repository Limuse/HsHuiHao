package com.huihao.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.huihao.R;
import com.huihao.common.Bar;
import com.huihao.common.Log;
import com.huihao.common.UntilList;
import com.huihao.handle.ActivityHandler;
import com.leo.base.activity.LActivity;
import com.leo.base.entity.LMessage;
import com.leo.base.net.LReqEntity;
import com.leo.base.util.T;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 2015/8/11.
 */
public class Registered extends LActivity {
    private int isEye = 0;
    private EditText et_user, et_pwd;
    private Button btn_look, btn_send, btn_send1, btn_send2;
    private boolean flag = true;
    private int time = 60;
    private String code;

    protected void onLCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_registered);
        initBar();
        initView();
    }

    private void initView() {
        et_user = (EditText) findViewById(R.id.et_user);
        et_pwd = (EditText) findViewById(R.id.et_pwd);
        btn_look = (Button) findViewById(R.id.btn_look);
        btn_send1 = (Button) findViewById(R.id.btn_send1);
        btn_send2 = (Button) findViewById(R.id.btn_send2);
    }

    private void initBar() {
        Bar.setTrans(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getText(R.string.app_registered));
        toolbar.setTitleTextColor(getResources().getColor(R.color.app_white));
        toolbar.setBackgroundColor(Color.parseColor("#00ffffff"));
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

    public void send(View v) {
        if (UntilList.isPhone(et_user.getText().toString().trim())) {
            time = 60;
            flag = true;
            getTime();
            String url = getResources().getString(R.string.app_service_url) + "/huihao/register/captchas/1/sign/aggregation/";
            ActivityHandler handler = new ActivityHandler(this);
            Map<String, String> map = new HashMap<String, String>();
            map.put("mobile", et_user.getText().toString().trim());
            LReqEntity entity = new LReqEntity(url,map);
            handler.startLoadingData(entity, 1);
        } else {
            T.ss("请输入正确的手机号码");
        }
    }

    public void onResultHandler(LMessage msg, int requestId) {
        super.onResultHandler(msg, requestId);
        if (msg != null) {
            if (requestId == 1) {
                getCode(msg.getStr());
            } else if (requestId == 2) {
            } else {
                T.ss("参数ID错误");
            }
        } else {
            T.ss("数据获取失败");
        }
    }

    public void getCode(String str) {
        Log.e(str);
    }

    public void ok(View v) {
        if (UntilList.isPhone(et_user.getText().toString().trim())) {
            ActivityHandler handler = new ActivityHandler(this);
            String url = getResources().getString(R.string.app_service_url) + "/huihao/register/registerexecute/1/sign/aggregation/";
            Map<String, String> map = new HashMap<String, String>();
            map.put("mobile", et_user.getText().toString().trim());
            map.put("captcha", code);
            map.put("password", et_pwd.getText().toString().trim());
            LReqEntity entity = new LReqEntity(url);
            handler.startLoadingData(entity, 2);
        } else {
            T.ss("请输入正确的手机号码");
            return;
        }


        if(!(code.length()>0)){

        }
        else {
            T.ss("请输入验证码");
            return;
        }


        if(!(et_pwd.getText().toString().length()>0)){

        }
        else {
            T.ss("请输入密码");
            return;
        }
//        Intent intent = new Intent(Registered.this, InvitationCode.class);
//        startActivity(intent);
    }

    public void getTime() {
        new Thread(new Runnable() {
            public void run() {
                while (flag) {
                    handler.sendEmptyMessage(1);
                    try {
                        Thread.sleep(1000);
                        if (time > 1) {
                            time--;
                        } else {
                            handler.sendEmptyMessage(2);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                btn_send1.setText(time + "");
                btn_send1.setClickable(false);
                btn_send2.setClickable(false);
            }
            if (msg.what == 2) {
                btn_send1.setText("发送验证码");
                btn_send1.setClickable(true);
                btn_send2.setClickable(true);
                flag = false;
            }
        }
    };
}
