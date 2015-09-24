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
import android.widget.EditText;

import com.huihao.R;
import com.huihao.common.SystemBarTintManager;
import com.huihao.common.Token;
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
 * Created by huisou on 2015/9/24.
 */
public class SdNum extends LActivity {
    private EditText et_add_num;

    @Override
    protected void onLCreate(Bundle bundle) {
        setContentView(R.layout.activity_sdnum);
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
        toolbar.setTitle("绑定代理商编号");
        toolbar.setBackgroundColor(getResources().getColor(R.color.app_white));
        toolbar.setNavigationIcon(R.mipmap.right_too);//设置左边图标
        //左边图标点击事件
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.putExtra("sdnum", "2");
                setResult(6, intent);
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

        et_add_num = (EditText) findViewById(R.id.et_add_num);
    }

    private void upname() {
        String sdnum = et_add_num.getText().toString();
        if (sdnum.equals(null)) {
            T.ss("代理商编号不能为空！");
        } else {
            Map<String, String> map = new HashMap<>();
            map.put("agent_no", sdnum);// 昵称
            map.put("uuid", Token.get(this));
            Resources res = getResources();
            String url = res.getString(R.string.app_service_url)
                    + "/huihao/member/agentbind/1/sign/aggregation/";
            LReqEntity entity = new LReqEntity(url, map);
           L.e(entity.toString());
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
                Intent intent = new Intent();
                String name = et_add_num.getText().toString();
                if (name.equals(null)) {
                    intent.putExtra("sdnum", "1");
                } else {
                    intent.putExtra("sdnum", et_add_num.getText().toString());
                }
                setResult(6, intent);
                finish();
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
