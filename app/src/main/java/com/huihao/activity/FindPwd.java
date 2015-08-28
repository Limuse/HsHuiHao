package com.huihao.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.huihao.common.Bar;
import com.huihao.R;
import com.leo.base.activity.LActivity;

/**
 * Created by admin on 2015/8/11.
 */
public class FindPwd extends LActivity {
    private int isEye = 0;
    private EditText et_user, et_pwd;
    private Button btn_look, btn_send1, btn_send2,btn_ok;
    private boolean flag = true;
    private int time = 60;
    protected void onLCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_registered);
        initBar();
        initView();
    }

    private void initView() {
        et_pwd = (EditText) findViewById(R.id.et_pwd);
        btn_look = (Button) findViewById(R.id.btn_look);
        btn_send1=(Button)findViewById(R.id.btn_send1);
        btn_send2=(Button)findViewById(R.id.btn_send2);
        btn_ok=(Button)findViewById(R.id.btn_ok);
        btn_ok.setText("修改密码");
    }

    private void initBar() {
        Bar.setTrans(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getText(R.string.app_findpwd));
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
        time = 60;
        flag = true;
        getTime();
    }
    public void ok(View v) {
        finish();
    }

    public void getTime(){
        new Thread(new Runnable() {
            public void run() {
                while (flag) {
                    handler.sendEmptyMessage(1);
                    try {
                        Thread.sleep(1000);
                        if (time > 1){
                            time--;
                        }
                        else {
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
