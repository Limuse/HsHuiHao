package com.huihao.activity;

import android.annotation.TargetApi;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huihao.R;
import com.huihao.common.SystemBarTintManager;
import com.huihao.common.Token;
import com.huihao.entity.UsErId;
import com.huihao.handle.ActivityHandler;
import com.leo.base.activity.LActivity;
import com.leo.base.entity.LMessage;
import com.leo.base.net.LReqEntity;
import com.leo.base.util.L;
import com.leo.base.util.T;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by huisou on 2015/7/29.
 */
public class Update_Name extends LActivity {
    private EditText et_Upname;

    @Override
    protected void onLCreate(Bundle bundle) {
        setContentView(R.layout.activity_update_name);
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
        toolbar.setTitle("昵称设置");
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
                    upname();

                }
                return false;
            }
        });
        toolbar.setTitleTextColor(getResources().getColor(R.color.app_text_dark));

        et_Upname = (EditText) findViewById(R.id.et_update_name);
    }

    private void upname() {
        String name =et_Upname.getText().toString();
        if(name.equals(null)){
            T.ss("昵称不能为空！");
        }
       else {
            Map<String, String> map = new HashMap<String, String>();
            map.put("name", name);// 昵称
            map.put("uuid", Token.get(this));
            Resources res = getResources();
            String url = res.getString(R.string.app_service_url)
                    + "/huihao/member/amendname/1/sign/aggregation/";
            LReqEntity entity = new LReqEntity(url, map);
            ActivityHandler handler = new ActivityHandler(this);
            handler.startLoadingData(entity, 1);
        }
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
                T.ss("保存成功！");
            } else {
                T.ss(jsonObject.getString("info"));
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
