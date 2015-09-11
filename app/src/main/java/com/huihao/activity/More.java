package com.huihao.activity;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.huihao.MyApplication;
import com.huihao.R;
import com.huihao.common.SystemBarTintManager;
import com.huihao.common.Token;
import com.huihao.custom.CustomDialog;
import com.huihao.handle.ActivityHandler;
import com.leo.base.activity.LActivity;
import com.leo.base.entity.LMessage;
import com.leo.base.net.LReqEntity;
import com.leo.base.util.LSharePreference;
import com.leo.base.util.T;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by huisou on 2015/8/5.
 * 更多
 */
public class More extends LActivity implements View.OnClickListener {

    private TextView tv_web, tv_kp, tv_clear;
    private RelativeLayout rl_web, rl_kp, rl_ban, rl_advice, rl_p, rl_clear;
    private Button btn_outline;
    private  Boolean tr;
    @Override
    protected void onLCreate(Bundle bundle) {
        setContentView(R.layout.activity_more);

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
        toolbar.setTitle("更多");
        toolbar.setBackgroundColor(getResources().getColor(R.color.app_white));
        toolbar.setNavigationIcon(R.mipmap.right_too);//左边图标
        //左边图标点击事件
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
        toolbar.setTitleTextColor(getResources().getColor(R.color.app_text_dark));
//        tv_web,tv_kp,tv_clear
//        rl_web,rl_kp,rl_ban,rl_advice,rl_p,rl_clear;
        tv_web = (TextView) findViewById(R.id.tv_web);
        tv_kp = (TextView) findViewById(R.id.tv_kp);
        tv_clear = (TextView) findViewById(R.id.tv_nc);
        rl_web = (RelativeLayout) findViewById(R.id.rl_web);
        rl_kp = (RelativeLayout) findViewById(R.id.rl_kp);
        rl_ban = (RelativeLayout) findViewById(R.id.rl_bx);
        rl_advice = (RelativeLayout) findViewById(R.id.rl_advice);
        rl_p = (RelativeLayout) findViewById(R.id.rl_pingf);
        rl_clear = (RelativeLayout) findViewById(R.id.rl_clear);
        btn_outline = (Button) findViewById(R.id.btn_outline);
        rl_web.setOnClickListener(this);
        rl_kp.setOnClickListener(this);
        rl_ban.setOnClickListener(this);
        rl_advice.setOnClickListener(this);
        rl_p.setOnClickListener(this);
        rl_clear.setOnClickListener(this);
        btn_outline.setOnClickListener(this);

        tr= LSharePreference.getInstance(this).getBoolean("login");

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
        int mid = v.getId();
        //官方网站
        if (mid == R.id.rl_web) {
            T.ss("官方网站");
        }
        //客服电话
        if (mid == R.id.rl_kp) {
            final CustomDialog alertDialog = new CustomDialog.Builder(this).
                    setMessage("确定联系客服吗？").
                    setPositiveButton("确定", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(
                                    getApplicationContext(),
                                    "正在呼叫  " + "400-123-123",
                                    Toast.LENGTH_LONG)
                                    .show();
                            Uri uri = Uri.parse("tel:"
                                    +"400-123-123");
                            Intent intent = new Intent(
                                    Intent.ACTION_CALL, uri);
                            startActivity(intent);
                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).create();


            alertDialog.show();
        }
        //版权信息
        if (mid == R.id.rl_bx) {
            T.ss("版权信息");
            Intent intent = new Intent(this, CopyRight.class);
            startActivity(intent);
        }
        //意见反馈
        if (mid == R.id.rl_advice) {
            T.ss("意见反馈");
            Intent intent = new Intent(this, FeedBack.class);
            startActivity(intent);

        }
        //给我们评分
        if (mid == R.id.rl_pingf) {
            T.ss("给我们评分");
        }
        //清除缓存
        if (mid == R.id.rl_clear) {
            // T.ss("清除缓存");
            final CustomDialog alertDialog = new CustomDialog.Builder(this).
                    setMessage("清除缓存吗？").
                    setPositiveButton("确定", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            tv_clear.setText("0M");
                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).create();


            alertDialog.show();

        }
        //退出登录
        if (mid == R.id.btn_outline) {
            T.ss("退出登录");
            if(tr==false){
                btn_outline.setText("登录");
                if(MyApplication.isLogin(this)){
                    T.ss("已登陆");
                }
            }else{
                btn_outline.setText("退出登录");
                loades();
            }

        }

    }


    private void loades() {


        Map<String, String> map = new HashMap<String, String>();
        map.put("uuid", Token.get(this));
        Resources res = getResources();
        String url = res.getString(R.string.app_service_url)
                + "/huihao/member/logout/1/sign/aggregation/";
        LReqEntity entity = new LReqEntity(url, map);
        ActivityHandler handler = new ActivityHandler(this);
        handler.startLoadingData(entity, 1);

    }

    // 返回获取的网络数据
    public void onResultHandler(LMessage msg, int requestId) {
        super.onResultHandler(msg, requestId);
        if (msg != null) {
            if (requestId == 1) {
                getJsonData(msg.getStr());
            } else {
                T.ss("获取数据失败");
            }
        }
    }

    private void getJsonData(String data) {
        try {
            JSONObject jsonObject = new JSONObject(data);
            int code = jsonObject.getInt("status");
            if (code == 1) {
                T.ss("退出成功！");
                LSharePreference.getInstance(this).setBoolean("login",false);
                if(MyApplication.isLogin(this)){
                    T.ss("已登陆");
                }
            } else {
                T.ss(jsonObject.getString("info"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
