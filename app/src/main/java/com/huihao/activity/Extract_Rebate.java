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
import android.widget.Button;
import android.widget.EditText;
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
import com.leo.base.util.LSharePreference;
import com.leo.base.util.T;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by huisou on 2015/8/3.
 * 提取返利
 */
public class Extract_Rebate extends LActivity implements View.OnClickListener {
    private TextView tv_acc, tv_na;
    private EditText et_mm;
    private Button btn_sure;

    @Override
    protected void onLCreate(Bundle bundle) {
        setContentView(R.layout.activity_extract_rebate);
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
        toolbar.setTitle("提取返利");
        toolbar.setBackgroundColor(getResources().getColor(R.color.app_white));
        toolbar.setNavigationIcon(R.mipmap.right_too);//设置左边图标
        //左边图标点击事件
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
        toolbar.setTitleTextColor(getResources().getColor(R.color.app_text_dark));

        tv_acc = (TextView) findViewById(R.id.tv_acca);
        tv_na = (TextView) findViewById(R.id.tv_nn);
        et_mm = (EditText) findViewById(R.id.et_mm);
        btn_sure = (Button) findViewById(R.id.btn_sure);
        btn_sure.setOnClickListener(this);
        tv_acc.setText(LSharePreference.getInstance(Extract_Rebate.this)
                .getString("account"));
        tv_na.setText(LSharePreference.getInstance(Extract_Rebate.this)
                .getString("accountname"));
    }

    private void initData() {
        String mm = et_mm.getText().toString();
        String t = getIntent().getExtras().getString("t");
        Resources res = getResources();
        final Map<String, String> map = new HashMap<String, String>();
        String url = null;
        if (t.equals("1")) {
            url = res.getString(R.string.app_service_url)
                    + "/huihao/member/drawcommission/1/sign/aggregation/";
            map.put("uuid", Token.get(this));
            map.put("amount", mm);
        } else if (t.equals("2")) {
            url = res.getString(R.string.app_service_url)
                    + "/huihao/member/drawprofits/1/sign/aggregation/";
            map.put("uuid", Token.get(this));
            map.put("amount", mm);
        }
        LReqEntity entity = new LReqEntity(url, map);
//        L.e(entity.toString());
        ActivityHandler handler = new ActivityHandler(Extract_Rebate.this);
        handler.startLoadingData(entity, 1);
    }

    // 返回获取的网络数据
    public void onResultHandler(LMessage msg, int requestId) {
        super.onResultHandler(msg, requestId);
        if (msg != null) {
            if (requestId == 1) {
                getJsonSubmit(msg.getStr());
            } else if (requestId == 2) {
                getJsonSubmit(msg.getStr());
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
                T.ss("提交成功！");

            } else {

                T.ss(jsonObject.getString("info").toString());
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

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_sure) {
            initData();
        }
    }
}
