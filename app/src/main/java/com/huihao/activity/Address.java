package com.huihao.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.huihao.R;
import com.huihao.adapter.AddressHoriSliseAdapter;
import com.huihao.common.SystemBarTintManager;
import com.huihao.common.Token;
import com.huihao.custom.SlideListView2;
import com.huihao.entity.AddressItemEntity;
import com.huihao.entity.UsErId;
import com.huihao.handle.ActivityHandler;
import com.leo.base.activity.LActivity;
import com.leo.base.entity.LMessage;
import com.leo.base.net.LReqEntity;
import com.leo.base.util.L;
import com.leo.base.util.T;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huisou on 2015/8/4.
 */
public class Address extends LActivity implements View.OnClickListener {
    private Button btn_add_addr, btn_gods;
    private SlideListView2 listView;
    private AddressHoriSliseAdapter adapter;
    private List<AddressItemEntity> list = new ArrayList<AddressItemEntity>();
    private LinearLayout headview;
    private RelativeLayout rl_gaddr, rl_all;
    private Address context;

    @Override
    protected void onLCreate(Bundle bundle) {
        setContentView(R.layout.activity_address);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }
        context = this;
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(R.color.app_white);
        initView();
        initData();
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        toolbar.setTitle("收货地址");
        toolbar.setBackgroundColor(getResources().getColor(R.color.app_white));
        toolbar.setNavigationIcon(R.mipmap.right_too);//左边图标
        //左边图标点击事件
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
        toolbar.setTitleTextColor(getResources().getColor(R.color.app_text_dark));
        headview = (LinearLayout) LayoutInflater.from(Address.this).inflate(R.layout.listview_head, null);
        listView = (SlideListView2) findViewById(R.id.lv_list_addr);
        listView.addHeaderView(headview);
        rl_all = (RelativeLayout) findViewById(R.id.rl_all);
        rl_gaddr = (RelativeLayout) findViewById(R.id.rl_gaddr);
        btn_gods = (Button) findViewById(R.id.btn_gods);
        btn_add_addr = (Button) findViewById(R.id.btn_add_addr);
        btn_gods.setOnClickListener(this);
        btn_add_addr.setOnClickListener(this);
        listView.initSlideMode(SlideListView2.MOD_RIGHT);
        // listView.setOnItemClickListener(this);

    }



    private void initData() {
        Resources res = getResources();
        String url = res.getString(R.string.app_service_url)
                + "/huihao/myaddress/1/sign/aggregation/?uuid=" + Token.get(this);
        LReqEntity entity = new LReqEntity(url);
        //http://huihaowfx.huisou.com//huihao/myaddress/1/sign/aggregation/?uuid=6a35c1ed7255077d57d57be679048034
        // Fragment用FragmentHandler/Activity用ActivityHandler
        ActivityHandler handler = new ActivityHandler(this);
        handler.startLoadingData(entity, 1);
    }

    private void getJsonData(String data) {
        list.clear();
        try {
            JSONObject jsonObject = new JSONObject(data);
            int code = jsonObject.getInt("status");
            if (code == 1) {
                JSONObject result = jsonObject.getJSONObject("list");
                if (result.length() < 1) {
                    rl_gaddr.setVisibility(View.VISIBLE);
                    rl_all.setVisibility(View.GONE);
                }else{
                    rl_gaddr.setVisibility(View.GONE);
                    rl_all.setVisibility(View.VISIBLE);

                JSONArray array = result.getJSONArray("address_list");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);
                    AddressItemEntity itementity = new AddressItemEntity();
                    itementity.setId(object.getString("id"));
                    itementity.setUname(object.getString("uname"));
                    itementity.setUphone(object.getString("uphone"));
                    itementity.setProvince(object.getString("province"));
                    itementity.setCity(object.getString("city"));
                    itementity.setCountry(object.getString("country"));
                    itementity.setAddress(object.getString("address"));
                    list.add(itementity);
                }
                adapter = new AddressHoriSliseAdapter(context, list, listView);
                listView.setAdapter(adapter);

            }
            }else {
                T.ss(jsonObject.getString("info").toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
            rl_gaddr.setVisibility(View.VISIBLE);
            rl_all.setVisibility(View.GONE);
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

    @Override
    protected void onResume() {
        super.onResume();
        initData();

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
        if (id == R.id.btn_add_addr) {
            Intent intnet = new Intent(this, Add_Address.class);
            startActivity(intnet);
        }
        if (id == R.id.btn_gods) {
            Intent intnet = new Intent(this, Add_Address.class);
            startActivity(intnet);
        }
    }


}
