package com.huihao.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.huihao.R;
import com.huihao.adapter.ExtraRecodeAdapter;
import com.huihao.common.SystemBarTintManager;
import com.huihao.common.Token;
import com.huihao.entity.ExtraReEntity;
import com.huihao.handle.ActivityHandler;
import com.huihao.handle.FragmentHandler;
import com.leo.base.activity.LActivity;
import com.leo.base.entity.LMessage;
import com.leo.base.net.LReqEntity;
import com.leo.base.util.LSharePreference;
import com.leo.base.util.T;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by huisou on 2015/8/10.
 * 版权信息
 */
public class CopyRight extends LActivity {
    @Override
    protected void onLCreate(Bundle bundle) {
        setContentView(R.layout.activity_copyright);
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
        toolbar.setTitle("版权信息");
        toolbar.setBackgroundColor(getResources().getColor(R.color.app_white));
        toolbar.setNavigationIcon(R.mipmap.right_too);//左边图标
        //左边图标点击事件
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
        toolbar.setTitleTextColor(getResources().getColor(R.color.app_text_dark));
    }

    private void initData(){
        Resources res=getResources();
        String url = res.getString(R.string.app_service_url) + "/huihao/member/appver/1/sign/aggregation/?uuid=" + Token.get(this);
        LReqEntity entity = new LReqEntity(url);
        ActivityHandler handler = new ActivityHandler(CopyRight.this);
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
                JSONArray array = jsonObject.getJSONArray("list");

            } else {
                T.ss(jsonObject.getString("info"));
                String longs=jsonObject.getString("info");
                if(longs.equals("请先登录")){
                    LSharePreference.getInstance(this).setBoolean("login", false);
                    Intent intent = new Intent(this, LoginMain.class);
                    startActivity(intent);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();

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
