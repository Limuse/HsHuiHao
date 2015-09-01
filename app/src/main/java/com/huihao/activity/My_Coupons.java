package com.huihao.activity;

import android.annotation.TargetApi;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.huihao.R;
import com.huihao.adapter.ChooseAddressAdapter;
import com.huihao.adapter.CouponsAdapter;
import com.huihao.common.SystemBarTintManager;
import com.huihao.entity.AddressItemEntity;
import com.huihao.entity.CouponsEntity;
import com.huihao.entity.UsErId;
import com.huihao.handle.ActivityHandler;
import com.leo.base.activity.LActivity;
import com.leo.base.entity.LMessage;
import com.leo.base.net.LReqEntity;
import com.leo.base.util.T;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huisou on 2015/7/29.
 * 优惠卷
 */
public class My_Coupons extends LActivity {
    private ListView listview;
    private List<CouponsEntity> list = new ArrayList<CouponsEntity>();
    ;
    private CouponsAdapter adapter;
    private LinearLayout headview;

    @Override
    protected void onLCreate(Bundle bundle) {
        setContentView(R.layout.activity_my_coupons);
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
        toolbar.setTitle("我的优惠卷");
        toolbar.setBackgroundColor(getResources().getColor(R.color.app_white));
        toolbar.setNavigationIcon(R.mipmap.right_too);//左边图标
        //左边图标点击事件
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
        toolbar.setTitleTextColor(getResources().getColor(R.color.app_text_dark));
        headview = (LinearLayout) LayoutInflater.from(My_Coupons.this).inflate(R.layout.listview_head, null);
        listview = (ListView) findViewById(R.id.lv_coupons);
        listview.addHeaderView(headview);

    }


    private void initData() {
        Resources res = getResources();
        String url = res.getString(R.string.app_service_url)
                + "/huihao/orders/coupon/1/sign/aggregation/?uuid="+ UsErId.uuid;
        LReqEntity entity = new LReqEntity(url);
        //http://huihaowfx.huisou.com/uuid=6a35c1ed7255077d57d57be679048034
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
                JSONArray array = jsonObject.getJSONArray("list");

                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);
                    CouponsEntity entity = new CouponsEntity();
                    entity.cpmoney = object.getString("money");
                    entity.cptitile = "汇好商城优惠卷";
                    String tme = object.getString("end_time").substring(0, 10);
                    entity.cptime = "使用期限" + tme + "前";
                    // entity.cpuse = "满800可用";
                    entity.cpid = object.getString("id");
                    entity.t = -1;
                    list.add(entity);
                }
                adapter = new CouponsAdapter(this, list);
                listview.setAdapter(adapter);

            } else {
                T.ss("获取数据失败");
            }
        } catch (JSONException e) {
            e.printStackTrace();
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
