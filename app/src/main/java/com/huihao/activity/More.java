package com.huihao.activity;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
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
import com.huihao.common.CacheUtill;
import com.huihao.common.SystemBarTintManager;
import com.huihao.common.Token;
import com.huihao.custom.CustomDialog;
import com.huihao.handle.ActivityHandler;
import com.leo.base.activity.LActivity;
import com.leo.base.entity.LMessage;
import com.leo.base.net.LReqEntity;
import com.leo.base.util.L;
import com.leo.base.util.LSharePreference;
import com.leo.base.util.T;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.utils.DiskCacheUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
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
    private Boolean tr = true;
    private String fils = Environment.getExternalStorageDirectory()
            + "/Android/data/com.android.hshuihao/cache";
    private File file1, file2;
    private boolean cleanFlag = false;

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
        getcache();
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
        if (tr == false) {
            btn_outline.setText("登录");
        }
        tr = LSharePreference.getInstance(this).getBoolean("login");

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
    protected void onResume() {
        super.onResume();
         boolean tts=LSharePreference.getInstance(this).getBoolean("login");
        if(tts==true){
            btn_outline.setText("退出登录");
        }else{
            btn_outline.setText("登录");
        }
        if (tr == false) {
            btn_outline.setText("登录");
        }
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
                                    "正在呼叫  " + "4006806820",
                                    Toast.LENGTH_LONG)
                                    .show();
                            Uri uri = Uri.parse("tel:"
                                    + "4006806820");
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
            if (cleanFlag) {
                T.ss("已经很干净啦！");
            } else {
                final CustomDialog alertDialog = new CustomDialog.Builder(this).
                        setMessage("清除缓存吗？").
                        setNegativeButton("确定", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                try {
                                    if (tv_clear.getText().toString().trim()
                                            .equals("0.0Byte")) {

                                        T.ss("暂无缓存");
                                    } else {
                                        boolean flag1 = CacheUtill.deleteFolderFile(file1.getPath(), true);
                                        boolean flag2 = CacheUtill.deleteFolderFile(file2.getPath(), true);
                                        if (flag1 && flag2) {
                                            getcache();
                                            T.ss("清除成功");
                                            tv_clear.setText("无缓存");
                                            cleanFlag = true;
                                        } else {
                                            T.ss("清除失败");
                                        }
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                dialog.dismiss();
                            }
                        }).setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();


                alertDialog.show();
            }

        }
        //退出登录
        if (mid == R.id.btn_outline) {
            // T.ss("退出登录");
            if (tr == false) {
                btn_outline.setText("登录");
                if (MyApplication.isLogin(this)) {
                    T.ss("已登陆");
                }
            } else {
                btn_outline.setText("退出登录");
                loades();
            }

        }

    }

    private void getcache() {
        try {
            file1 = new File(Environment.getExternalStorageDirectory(), "cache");
            if (!file1.exists()) {
                file1.mkdirs();
            }
            long folderSize = CacheUtill.getFolderSize(file1);

            file2 = new File(Environment.getExternalStorageDirectory()
                    + "/Android/data/com.android.hshuihao/");
            if (!file2.exists()) {
                file2.mkdirs();
            }
            long folderSize2 = CacheUtill.getFolderSize(file2);
            String cacheSize = CacheUtill
                    .getFormatSize((folderSize+folderSize2));

            if (cacheSize.equals("0.0B")) {
                tv_clear.setText("无缓存");
            } else {
                tv_clear.setText(cacheSize);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loades() {
        Map<String, String> map = new HashMap<>();
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
                tr = false;
                LSharePreference.getInstance(this).setBoolean("login", false);
                if (MyApplication.isLogin(this)) {
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
