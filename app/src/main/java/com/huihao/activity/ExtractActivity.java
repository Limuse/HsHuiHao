package com.huihao.activity;

import android.annotation.TargetApi;
import android.content.res.Resources;
import android.graphics.Color;
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
import com.huihao.entity.UsErId;
import com.huihao.handle.ActivityHandler;
import com.leo.base.activity.LActivity;
import com.leo.base.entity.LMessage;
import com.leo.base.net.LReqEntity;
import com.leo.base.util.L;
import com.leo.base.util.LSharePreference;
import com.leo.base.util.T;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by huisou on 2015/7/29.
 * 提取账户
 */
public class ExtractActivity extends LActivity {
    private EditText et_zaccount, et_zname, et_zphone;
    private boolean flg = true;

    @Override
    protected void onLCreate(Bundle bundle) {
        setContentView(R.layout.activity_extarct_account);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }

        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(R.color.app_white);
        initView();
        getData();
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        toolbar.setTitle("提取账户");
        toolbar.setBackgroundColor(getResources().getColor(R.color.app_white));
        toolbar.setNavigationIcon(R.mipmap.right_too);//设置左边图标
        //左边图标点击事件
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
        toolbar.inflateMenu(R.menu.right_menu);
        if (flg == false) {
            //右边图片点击事件
            toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                public boolean onMenuItemClick(MenuItem item) {
                    if (item.getItemId() == R.id.menu_messages) {
                        // T.ss("保存成功");
                        submit();
                    }
                    return false;
                }
            });
        }

        toolbar.setTitleTextColor(getResources().getColor(R.color.app_text_dark));

        et_zaccount = (EditText) findViewById(R.id.setting_phone);
        et_zname = (EditText) findViewById(R.id.account_name);
        et_zphone = (EditText) findViewById(R.id.account_phone);

    }

    private void getData() {
        Resources res = getResources();
        String url = res.getString(R.string.app_service_url)
                + "/huihao/member/useramount/1/sign/aggregation/?uuid="+ UsErId.uuid;
        LReqEntity entity = new LReqEntity(url);
        ActivityHandler handler = new ActivityHandler(ExtractActivity.this);
        handler.startLoadingData(entity, 2);
    }


    private void submit() {
        if (et_zaccount.equals(null) || et_zname.equals(null)) {
            T.ss("请填写完整信息！");

        } else {
            String account = et_zaccount.getText().toString();
            String name = et_zname.getText().toString();
            String phone = et_zphone.getText().toString();

            Resources res = getResources();
            String url = res.getString(R.string.app_service_url)
                    + "/huihao/member/amount/1/sign/aggregation/";
            final Map<String, String> map = new HashMap<String, String>();
            map.put("uuid", UsErId.uuid);
            map.put("amount", account);
            map.put("truename", name);
            map.put("phone", phone);
            LSharePreference.getInstance(ExtractActivity.this)
                    .setString("account", account);
            LSharePreference.getInstance(ExtractActivity.this)
                    .setString("accountname", name);
            LReqEntity entity = new LReqEntity(url, map);
            L.e(entity + "");
            ActivityHandler handler = new ActivityHandler(ExtractActivity.this);
            handler.startLoadingData(entity, 1);

        }

    }

    // 返回获取的网络数据
    public void onResultHandler(LMessage msg, int requestId) {
        super.onResultHandler(msg, requestId);
        if (msg != null) {
            if (requestId == 1) {
                getJsonSubmit(msg.getStr());
            } else if (requestId == 2) {
                getJsonData(msg.getStr());
            } else {
                T.ss("获取数据失败");
            }
        }
    }

    private void getJsonSubmit(String data) {

        try {
            JSONObject jsonObject = new JSONObject(data);
            int code = jsonObject.getInt("status");
            if (code == 1) {
                T.ss("保存成功！");

            } else {

                T.ss(jsonObject.getString("info").toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void getJsonData(String data) {

        try {
            JSONObject jsonObject = new JSONObject(data);
            int code = jsonObject.getInt("status");
            if (code == 1) {
                JSONObject j = jsonObject.getJSONObject("list");
                String account = j.getString("amount");
                String truename = j.getString("truename");
                String phone = j.getString("phone");

                if (account.equals(null) || truename.equals(null) || phone.equals(null)) {

                    flg = false;
                } else {

                    et_zaccount.setText(account);
                    et_zname.setText(truename);
                    et_zphone.setText(phone);
                    et_zaccount.setEnabled(false);
                    et_zname.setEnabled(false);
                    et_zphone.setEnabled(false);

                    LSharePreference.getInstance(ExtractActivity.this)
                            .setString("account", account);
                    LSharePreference.getInstance(ExtractActivity.this)
                            .setString("accountname", truename);
                }


            } else {

                T.ss(jsonObject.getString("info").toString());
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
