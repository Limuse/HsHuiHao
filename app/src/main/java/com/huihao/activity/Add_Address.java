package com.huihao.activity;

import android.annotation.TargetApi;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.huihao.R;
import com.huihao.common.SystemBarTintManager;
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
 * Created by huisou on 2015/8/4.
 */
public class Add_Address extends LActivity implements View.OnClickListener {
    private EditText et_name, et_phone, et_shen, et_xiang;
    private ImageView iv_del;
    private int provinceID;// 省ID
    private int cityID;   //  市ID
    private int countryID; // 区ID

    @Override
    protected void onLCreate(Bundle bundle) {
        setContentView(R.layout.activity_add_address);
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
        toolbar.setTitle("新增收货地址");
        toolbar.setBackgroundColor(getResources().getColor(R.color.app_white));
        toolbar.setNavigationIcon(R.mipmap.right_too);//左边图标
        //左边图标点击事件
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
        //右边图片点击事件
        toolbar.inflateMenu(R.menu.right_menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.menu_messages) {
                    //T.ss("保存成功");
                    if (et_name.equals(null) || et_phone.equals(null) || et_shen.equals(null) || et_xiang.equals(null)) {
                        T.ss("信息不能为空！");
                    } else {
                        subm();
                    }
                }
                return false;
            }
        });
        toolbar.setTitleTextColor(getResources().getColor(R.color.app_text_dark));

        et_name = (EditText) findViewById(R.id.et_men);
        et_phone = (EditText) findViewById(R.id.et_menphone);
        et_shen = (EditText) findViewById(R.id.et_shen);
        et_xiang = (EditText) findViewById(R.id.et_xiang);
        iv_del = (ImageView) findViewById(R.id.iv_del);
        iv_del.setOnClickListener(this);
    }

    private void subm() {
        String name = et_name.getText().toString();
        String phone = et_phone.getText().toString();
        String shen = et_shen.getText().toString();
        String addr = et_xiang.getText().toString();
        provinceID = 1;// 省ID
        cityID = 1;   //  市ID
        countryID = 1;//
        Resources res = getResources();
        String url = res.getString(R.string.app_service_url)
                + "/huihao/myaddress/add/1/sign/aggregation/";
        final Map<String, String> map = new HashMap<String, String>();
        map.put("uuid", UsErId.uuid);
        //map.put("id", );
        map.put("uname", name);//收货人名
        map.put("uphone", phone);// 收货人联系号码
        map.put("province", provinceID + "");// 省ID
        map.put("city", cityID + "");//     市ID
        map.put("country", countryID + "");//  区ID
        map.put("address", addr);//
        LReqEntity entitys = new LReqEntity(url, map);
        L.e(entitys + "");
        ActivityHandler handler = new ActivityHandler(Add_Address.this);
        handler.startLoadingData(entitys, 1);
    }

    private void getJsonData(String data) {

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


    // 返回获取的网络数据
    public void onResultHandler(LMessage msg, int requestId) {
        super.onResultHandler(msg, requestId);
        if (msg != null) {
            switch (requestId) {
                case 1:
                    getJsonData(msg.getStr());
                    break;
                default:
                    T.ss("获取数据失败");
                    break;
            }
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
        int id = v.getId();
        if (id == R.id.iv_del) {
            et_phone.setText(null);
        }
    }
}
